package com.ylczjqnfc.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class NetworkState {

	/**
	 * �ж�Android�ͻ��������Ƿ�����
	 * ��ʽ1
	 * @param context
	 * @return ���
	 */
	public static boolean isNetworkAvailable(Context context) {

		try {
			ConnectivityManager cm = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (cm == null) {

			} else { // ��������������ж���������,�����ʹ��cm.getActiveNetworkInfo().isAvailable();
				NetworkInfo[] info = cm.getAllNetworkInfo();
				if (info != null) {
					for (int i = 0; i < info.length; i++) {
						if (info[i].getState() == NetworkInfo.State.CONNECTED) {
							return true;
						}
					}
				}

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

		/*
		 * �ж�����ĵڶ��ַ�ʽ
		 * try { ConnectivityManager connectivity = (ConnectivityManager)
		 * context .getSystemService(Context.CONNECTIVITY_SERVICE); if
		 * (connectivity != null) {
		 * 
		 * NetworkInfo info = connectivity.getActiveNetworkInfo(); if (info !=
		 * null && info.isConnected()) { if (info.getState() ==
		 * NetworkInfo.State.CONNECTED) { return true; } }else{
		 * Toast.makeText(context, "�������ӳ�ʱ", Toast.LENGTH_SHORT).show(); } } }
		 * catch (Exception e) { return false; }
		 */
		return false;
	}
}
