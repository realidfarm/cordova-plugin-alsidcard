package com.sdses.bean;

import java.io.Serializable;
import java.util.Arrays;

import ToBmp.Wlt;

public class ID2PicNoLic implements Serializable {
	private static final long serialVersionUID = 1L;
	private Wlt w = new Wlt();
	private byte[] mHeadFromCard = new byte[38862];
	
	public ID2PicNoLic() {
	}

	/**
	 * 使用给定的SAM模块号及授权文件解析二代证头像
	 * @param _picBuff	1024字节头像原始数据
	 * @return	-5:授权文件与SAM模块号不匹配
	 * 			38862:头像解码成功
	 */
	public int decodeNohLic(byte[] _picBuff) {
		Arrays.fill(mHeadFromCard, (byte) 0x00);
		// 图片
		return w.GetBmpByBuffNoLic(_picBuff, mHeadFromCard);
	}

	/**
	 * 获取二代证解析后的头像数据
	 * @return	38862字节bmp数据
	 */
	public byte[] getHeadFromCard() {
		return mHeadFromCard;
	}
}
