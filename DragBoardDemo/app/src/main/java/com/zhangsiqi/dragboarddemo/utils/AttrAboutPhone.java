package com.zhangsiqi.dragboarddemo.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Rect;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;


public class AttrAboutPhone {

	public static final String SharePreName = "preference_name"; // 通用的偏好设置存放name


	public static int screenHeight = 0;
	public static int screenWidth = 0;
	public static float screenDensity = 0;
	public static int statusBarHeight;// 状态栏的宽
	public static String appkey;

	public static void initScreen(Activity context) {
		getAttr(context);
		Logger.info(context.getClass().toString(), "screenDensity=" + screenDensity + "  screenHeight=" + screenHeight + "  screenWidth="
				+ screenWidth + "  statusBarHeight=" + statusBarHeight);
	}

	public static void saveAttr(Activity context) {
		Rect frame = new Rect();
		context.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		if (frame.top == 0) {
			return;
		}
		DisplayMetrics dm = new DisplayMetrics();
		context.getWindowManager().getDefaultDisplay().getMetrics(dm);
		setAttr(context, dm.heightPixels, dm.widthPixels, dm.density, frame.top);
	}

	public static String getIMEI(Context context) {
		String imei;
		try {
			TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			imei = tm.getDeviceId();
		} catch (Exception e) {
			e.printStackTrace();
			imei = "-1";
		}
		if (imei == null){
			imei = "-1";
		}
		return imei;
	}

	/**
	 * @return IMSI
	 */
	public static String getIMSI(Context context) {
		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		return tm.getSubscriberId();

	}

	public static String getAppKey(Context context) {
		if (appkey == null || appkey.equals("")) {
			ApplicationInfo info = null;
			try {
				info = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
				if (info != null) {
					appkey = info.metaData.getString("UMENG_APPKEY");
				}
			} catch (NameNotFoundException e) {
				e.printStackTrace();
			}
		} else {
		}

		return appkey;
	}

	/**  获取手机屏幕信息 */
	public static boolean getAttr(Context context) {
		SharedPreferences sp = context.getSharedPreferences(SharePreName, Context.MODE_PRIVATE);
		AttrAboutPhone.screenHeight = sp.getInt("screenHeight", 0);
		AttrAboutPhone.screenWidth = sp.getInt("screenWidth", 0);
		AttrAboutPhone.screenDensity = sp.getFloat("screenDensity", 0);
		AttrAboutPhone.statusBarHeight = sp.getInt("statusBarHeight", 0);
		if (AttrAboutPhone.statusBarHeight == 0) {
			return false;
		}
		return true;
	}

	/**  存储手机屏幕信息 */
	public static void setAttr(Context context, int screenHeight, int screenWidth, float screenDensity, int statusBarHeight) {
		SharedPreferences.Editor editor = context.getSharedPreferences(SharePreName, Context.MODE_PRIVATE).edit();
		editor.putInt("screenHeight", screenHeight);
		editor.putInt("screenWidth", screenWidth);
		editor.putFloat("screenDensity", screenDensity);
		editor.putInt("statusBarHeight", statusBarHeight);
		editor.commit();
	}
}
