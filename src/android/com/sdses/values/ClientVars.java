package com.sdses.values;

/**
 * 程序运行时使用到的全局变量
 * @author meng
 * @version 1.0
 */
public final class ClientVars {
	/**
	 * GA467协议
	 */
	public static final int protocol_69 = 1;
	/**
	 * 自定义协议 96 0A 
	 */
	public static final int protocol_0A = 2;
	
	/**
	 * 连接读卡设备。执行后读卡设备完成初始化操作。
	 */
	public static final int deviceConnect = 0x70;
	public static final int deviceConnect_OK = 0x70;
	public static final int deviceConnect_ERROR = 0xF0;
	/**
	 * 打开读卡服务。执行后创建读卡线程。
	 */
	public static final int openServer = 0x71;
	public static final int openServer_OK = 0x71;
	public static final int openServer_ERROR = 0xF1;
	/**
	 * 关闭读卡服务。执行后关闭读卡线程。
	 */
	public static final int closeServer = 0x72;
	public static final int closeServer_OK = 0x72;
	public static final int closeServer_ERROR = 0xF2;
	/**
	 * 断开读卡设备连接。执行后释放读卡设备资源，掉电。
	 */
	public static final int deviceDisConnect = 0x73;
	public static final int deviceDisConnect_OK = 0x73;
	public static final int deviceDisConnect_ERROR = 0xF3;
	/**
	 * 卡数据返回
	 */
	public static final int receivefromcard = 0x75;
	/**
	 * 卡数据返回操作类型 0x75追加参数
	 */
	public static final int resetSAM = 0x00;//10FF 复位SAM_V
	public static final int checkSAM = 0x01;//11FF SAM_V状态检测
	public static final int readSAMSN= 0x02;//12FF 读SAM_V管理信息
	public static final int seachCard= 0x03;//2001  寻找身份证
	public static final int selectCard= 0x04;//2002  选取身份证
	public static final int readBaseInfo = 0x05;//3001  读基本信息
	public static final int readAllInfo= 0x06;//3010  读基本信息和证载指纹信息
	public static final int readAddressAdd = 0x07;//3003  读追加地址
	public static final int setBaud= 0x08;//60XX 设置UART接口速率
	public static final int setBits= 0x09;//61XX 设置SAM_V与射频模块一帧数据最大字节数
	public static final int unknownError = 0x10;//未知错误
	/**
	 * 心跳超时
	 */
	public static final int timeout = 10;
	/**
	 * 客户端心跳请求
	 */
	public static final int heartbeat = 5;
	public static final String  sendtoserver ="com.sdses.sendtoserver";
	public static final String  receivefromserver ="com.sdses.receivefromserver";
	public static final String  command ="command";
	public static final String  value ="value";
	public static final String  extra ="extra";
	public static final String  data ="data";
	public static final String  ishaveFpData="isHaveFpData";
	

}
