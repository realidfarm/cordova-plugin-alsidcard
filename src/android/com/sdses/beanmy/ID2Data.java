package com.sdses.beanmy;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;

import android.util.Log;
import com.sdses.bean.*;

/**
 * 二代证信息封装类
 * @author meng
 * @version	1.0
 */
public class ID2Data extends ID2DataRAW implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final String TAG = ID2Data.class.getSimpleName();
	private ID2Txt mID2Txt;
	private ID2PicNoLic mID2Pic;
	private ID2FP mID2FP;
	private String mID2NewAddress;

	private boolean mHaveFP = false;


	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return mID2Txt.toString();
	}


	public ID2Data() {
		Log.i(TAG, "ID2Data 构造函数");
		mID2Txt = new ID2Txt();
		mID2Pic = new ID2PicNoLic();
		mID2FP = new ID2FP();
	}


	/**
	 * 将二代证原始数据重新装包
	 * @return	1装包成功
	 * 			0装包失败
	 */
	public int rePackage() {
		mID2Txt.decode(this.getID2TxtRAW());
		mID2Pic.decodeNohLic(this.getID2PicRAW());
		mHaveFP = mID2FP.initFP(this.getID2FPRAW());
		try {
			mID2NewAddress = this.getID2AddRAW() == null ? null : new String(
					this.getID2AddRAW(), 3, 70, "UTF-16LE");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 获取追加地址
	 * @return	如果有追加地址则返回String类型的追加地址，长度15位，没有追加地址则返回null。
	 */
	public String getID2NewAddress() {
		return mID2NewAddress;
	}

	/**
	 * 获取一个二代证文本的封装类
	 * @return	ID2Txt类型的对象
	 */
	public ID2Txt getID2Txt() {
		return mID2Txt;
	}

	/**
	 * 获取一个二代证头像的封装类
	 * @return	ID2Pic类型的对象
	 */
	public ID2PicNoLic getID2Pic() {
		return mID2Pic;
	}

	/**
	 * 获取指纹封装对象
	 * @return	ID2FP类型的对象,如果没有指纹则返回null。
	 */
	public ID2FP getID2FP() {
		if (mHaveFP)
			return mID2FP;
		else
			return null;
	}

}
