package com.ylczjqnfc.http;

import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HashMap;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.os.AsyncTask;

import com.ylczjqnfc.utils.viewTools.WaitDialog;

public class MyAsynctask extends AsyncTask<String, Integer, String> {

	WaitDialog waitDialog = null;
	String networkRequest = null;
	HttpService httpService;
	
	public MyAsynctask(Context mContext){
		waitDialog = new WaitDialog(mContext);
	}
	
	public MyAsynctask(Context mContext,String networkRequest,boolean hide){//(�������ӣ��Ƿ�����)
		this.networkRequest = networkRequest;
		httpService = new HttpService();
		// apduPageNo = -1;
		waitDialog = new WaitDialog(mContext);
		if (!hide) {
			// ʹ�Ի�����ȡ��
			waitDialog.setCanceled(hide);
		}
	}
	
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		waitDialog.show();
	}
	
	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		waitDialog.dismiss();
	}
	
	/**
	 * ���ڴ�ŷ�������ȷ��Ӧ�Ĵ���
	 * ����ʹ��gson�������ݽ���
	 * @param gsonData
	 * @return
	 */
	public static HashMap<String, Object> GetGsonData(String gsonData) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		return result;
	}
	
	
	
}
