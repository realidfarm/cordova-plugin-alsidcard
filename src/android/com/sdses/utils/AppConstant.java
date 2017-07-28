package com.sdses.utils;

public class AppConstant {
	/***
	 * 4.4以下(也就是kitkat以下)的版本
	 */
	public static final int KITKAT_LESS = 10;
	/***
	 * 4.4以上(也就是kitkat以上)的版本,当然也包括最新出的5.0棒棒糖
	 */
	public static final int KITKAT_ABOVE = 11;
	
	/***
	 * 裁剪图片成功后返回
	 */
	public static final int INTENT_CROP = 12;

	/***
	 * 捕捉人像
	 */
	public static final int FACE_CAPTURE= 1000;


	/***
	 * 显示人像
	 */
	public static final int FACE_SHOW= 2000;

	/***
	 * 提取人像特征结果
	 */
	public static final int FACE_EXTRACT_FEATURE_RESULT= 3000;

	/***
	 * 人像比对
	 */
	public static final int FACE_COMPARE= 4000;

	/***
	 * 人像比对异常
	 */
	public static final int FACE_EXCEPTION= 5000;
}
