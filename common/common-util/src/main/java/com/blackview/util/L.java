package com.blackview.util;

import android.util.Log;

public class L {

	private static boolean DBG = BuildConfig.DEBUG;

	public static final String TAG = "ddd";

	public static void logV(String TAG, String msg) {
		Log.d("ddd", "log is " + DBG);
		if (DBG) {
			Log.v(TAG, msg);
		}
	}

	public static void logE(String TAG, String msg) {
		if (DBG) {
			Log.e(TAG, msg);
		}
	}

	public static void logD(String tag, String msg) {
		if (DBG) {
			Log.d(tag, msg);
		}
	}

	public static synchronized void setLogDebug(boolean isDebug) {
		DBG = isDebug;
	}

	// 下面四个是默认tag的函数
	public static void i(String msg) {
		if (DBG)
			Log.i(TAG, msg);
	}

	public static void d(String msg) {
		if (DBG)
			Log.d(TAG, msg);
	}

	public static void e(String msg) {
		if (DBG)
			Log.e(TAG, msg);
	}

	public static void v(String msg) {
		if (DBG)
			Log.v(TAG, msg);
	}

	// 下面是传入自定义tag的函数
	public static void i(String tag, String msg) {
		if (DBG)
			Log.i(tag, msg);
	}

	public static void d(String tag, String msg) {
		if (DBG)
			Log.d(tag, msg);
	}

	public static void e(String tag, String msg) {
		if (DBG)
			Log.e(tag, msg);
	}

	public static void v(String tag, String msg) {
		if (DBG)
			Log.v(tag, msg);
	}


	/*public static void logD(String tag, String msg) {
		if (DBG) {
			if (tag == null || tag.length() == 0
					|| msg == null || msg.length() == 0)
				return;

			int segmentSize = 3 * 1024;
			long length = msg.length();
			if (length <= segmentSize) {// 长度小于等于限制直接打印
				Log.d(tag, msg);
			} else {
				while (msg.length() > segmentSize) {// 循环分段打印日志
					String logContent = msg.substring(0, segmentSize);
					msg = msg.replace(logContent, "");
					Log.d(tag, logContent);
				}
				Log.d(tag, msg);// 打印剩余日志    
			}
		}

	}*/


}