package com.realidfarm.idcard;

import java.io.File;
import java.util.Arrays;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.ComponentName;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;

import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.IBinder;
import android.os.SystemClock;
import android.os.RemoteException;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import com.sdses.fingerJar.SSFingerInterfaceImp;
import com.sdses.id2CardInterface.ID2CardInterface;
import com.sdses.tool.SSUtil;

import java.util.ArrayList;
import java.util.List;

public class IdCard extends CordovaPlugin{

	private CallbackContext context;
	private Activity activity;
	private static final int SERVICE_FACTORY_INIT_DONE = 0;
	private static final int SERVICE_FACTORY_INIT_FAILED = 1;

	ID2CardInterface id2Handle = null;
	String id2Result[] = null;
	public String TAG="ss_500";
	private int openRet=0,closeRet=0;
    private ProgressDialog mProgressDialog;
	
	private ImageView iv_fp;
	private SSFingerInterfaceImp ssF=null;
	private ProgressDialog pd=null;
	private String fingerInfo1,fingerInfo2;
	private int iFpCompareThreshold=40;
	private TextView tv_fpHint;
	private boolean openFp=false;
	byte[] fingerInfo = new byte[93238];
	
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        activity = this.cordova.getActivity();
		context = callbackContext;
        if (action.equals("open")) {
    		id2Handle = new ID2CardInterface();
    		openRet=id2Handle.openReadCard();
			callbackContext.success("设备已开启");
        }else if (action.equals("read")) {
        	if(openRet!=1){
				id2Result[0] = "读卡上电失败";
			}else{
				id2Result = id2Handle.readCardInfo();
				if(id2Result[0].equalsIgnoreCase("0")){
					byte[] bid2Result = SSUtil.hexStringToByte(id2Result[12]);
					id2Result[12] = Base64.encodeToString(bid2Result, 0);
		            callbackContext.success(Arrays.toString(id2Result));
				}else{
		            callbackContext.success("读卡失败");
				}
			}
        }else if (action.equals("close")) {
			closeRet=id2Handle.closeReadCard();
			if(closeRet!=1){
				callbackContext.success("读卡下电失败");
			}else{
				callbackContext.success("设备已关闭");
			}
        }else if (action.equals("ssFopen")) {
        	startInit();
			callbackContext.success("上电成功");
        }else if (action.equals("ssFget")) {
			fingerInfo = ssF.getFingerByteData();
			if(fingerInfo==null){
				callbackContext.success("采集图像数据失败");
			}else{
				int s = ssF.getFingerQuality(fingerInfo);
				callbackContext.success(s);
			}
        }else if (action.equals("ssFgetHex")) {
			fingerInfo = ssF.getFingerByteData();
			if(fingerInfo==null){
				callbackContext.success("采集图像数据失败");
			}else{
				fingerInfo1 = ssF.getFingerInfoQuick(1, fingerInfo);
				callbackContext.success(fingerInfo1);
			}
        }else if (action.equals("ssUp")) {
			fingerInfo1 = ssF.getFingerInfoQuick(1, fingerInfo);
			if(fingerInfo1!=null&&!fingerInfo1.equals("")){
				callbackContext.success(fingerInfo1);
			}else{
				callbackContext.success("请采集指纹");
			}
        }else if (action.equals("Comparison")) {
        	fingerInfo2 = args.getString(0);
    		int iScore=-100;
			if(fingerInfo1!=null&&fingerInfo2!=null){
				 iScore=ssF.fingerComparison(fingerInfo1, fingerInfo2);
			}else{
				callbackContext.success("请采集指纹");
			}
			if(iScore==0){
				callbackContext.success("指纹比对失败");
			}else if (iScore == -1){
				callbackContext.success("指纹比对失败" + iScore);
			} else if (iScore > iFpCompareThreshold) {
				callbackContext.success("匹配成功");
			} else {
				callbackContext.success("指纹不匹配,值为:" + iScore);
			}
        }else if (action.equals("idComparison")) {
        	try{
				int iScore=-100;
			    if(fingerInfo1!=null&&id2Result[15]!=null){
					iScore=ssF.fingerComparison(fingerInfo1, id2Result[17]);
				}else{
					callbackContext.success("是否采集指纹,所读身份证是否存在指纹信息");
				}
				if(iScore==0){
					callbackContext.success("指纹比对失败");
				}else if (iScore == -1) {
					callbackContext.success("验证身份证指位1失败" + iScore);
				} else if (iScore > iFpCompareThreshold) {
					callbackContext.success("验证身份证指位1匹配成功,值为:" + iScore);
				} else {
					callbackContext.success("验证身份证指位1不匹配,值为:" + iScore);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
        }
        return false;
    }
    
	private void  startInit() {
		HandlerThread 	loadHandlerThread = new HandlerThread("LoadThread");
		loadHandlerThread.start();
		Handler loadHandler = new Handler(loadHandlerThread.getLooper());
		loadHandler.post(loadRunnable);
	}
	
	Runnable loadRunnable=new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			if(Init()){
				Log.e(TAG, "上电成功");
				new Thread(fpRun).start();
			}
		}
	};
	
	public boolean Init() {
		// TODO Auto-generated method stub
		if (ssF == null) {
			Context context = activity.getApplicationContext(); 
			ssF = new SSFingerInterfaceImp(context);
		}
		int power_res = ssF.f_powerOn();
		if (power_res == 0) {
			int fpCon = -1;
			for (int i = 0; i < 5; i++) {
				fpCon = ssF.SS_USBConnect();
				SystemClock.sleep(1000);
				Log.e(TAG, "循环中");
				if (fpCon == 0) {
					 Log.e(TAG, "指纹上电成功");
					break;
				} else if (i == 4) {
					 Log.e(TAG, "指纹上电失败");
					break;
				}
			}
			if (fpCon == 0) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	Runnable fpRun=new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			//大约需要3s时间 5s连接不上 可当做失败
			Log.e(TAG, "btn_openFP5");
			for(int i=0;i<5;i++){
				int fpCon=ssF.SS_USBConnect();
				SystemClock.sleep(1000);
				if(fpCon==0){
					Log.e(TAG, "指纹上电成功");
					handler.sendEmptyMessage(0x01);
					break;
				}else if(i==4){
					Log.e(TAG, "指纹上电失败");
					handler.sendEmptyMessage(0x02);
					break;
				}
			}
		}
	};
	
	Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch(msg.what){
			case 0x01:
				if(pd!=null)
					pd.dismiss();
					openFp=true;
				break;
			case 0x02:
				if(pd!=null){
					pd.dismiss();
				}
				break;
			}
		};
	};
}