package com.ylczjqnfc.http;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.ylczjqnfc.R;

/**
 * ��װ�����Ĺ�����
 * @author Administrator
 *
 */
public class HttpUtil {
	private static Context mContext;
	public static final String URL = mContext.getResources().getString(R.string.bj_urlb);
	private static final String norTime = "12000";
	private static final String rechangeTime = "30000";

	public HttpUtil(Context mContext){
		this.mContext = mContext;
	}
	//���������������
	public static String Apdu_SendMassage(List<NameValuePair> firstParams)
			throws KeyManagementException, NoSuchAlgorithmException,
			KeyStoreException, UnrecoverableKeyException, IOException {
		// ����һ������Cookie�洢��ʵ��
		CookieStore cookieStore = new BasicCookieStore();
		String apduOnlineResult = "";
		// ���ó�ʱʱ��
		HttpParams httpParameters = new BasicHttpParams();
		int conTime = Integer.parseInt(rechangeTime);
		HttpConnectionParams.setConnectionTimeout(httpParameters, conTime);// Set
		int soTime = Integer.parseInt(rechangeTime);
		HttpConnectionParams.setSoTimeout(httpParameters, soTime);
		// �������Ӷ���
		DefaultHttpClient httpclient = new DefaultHttpClient(httpParameters);
		// ����֤���������ȫ��֤�飩
		TrustAllSSLSocketFactory socketFactory = new TrustAllSSLSocketFactory();
		Scheme sch = new Scheme("https", socketFactory, 443);
		httpclient.getConnectionManager().getSchemeRegistry().register(sch);
		// ���ؽ��ն���
		HttpResponse httpResponse = null;
		// ����״̬
		int sta = 200;
		String strResult = null;
		// �������ӵ�ַ
		String httpUrl = URL;
		// Toast.makeText(GlobalVar.conditions, httpUrl, Toast.LENGTH_SHORT)
		// .show();
		// HttpPost���Ӷ���
		HttpPost httpRequest = new HttpPost(httpUrl);
		// �����ַ���
		HttpEntity httpentity = new UrlEncodedFormEntity(firstParams, "utf-8");
		// ���ò���
		httpRequest.setEntity(httpentity);
		// ����user-agent
		httpRequest
				.setHeader(
						"USER-AGENT",
						"Mozilla/5.0 (Linux; U; AndroidPhoneBoco; zh-cn; HTC Desire Build/FRF91) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1");
		String user_agent =
		// �ֻ��ͺţ�HTC Desire��
		android.os.Build.MODEL + ","
		// ����ϵͳ�汾�ţ�Android 2.2��
				+ "Android " + android.os.Build.VERSION.RELEASE;
		httpRequest.setHeader("USER-AGENT", user_agent);
		// ���ñ����ʽ
		httpRequest.setHeader("accept-charset", "utf-8");
		// ���������ļ�
//		if (GlobalVar.JSESSIONID != null) {
//			httpRequest.setHeader("Cookie", "JSESSIONID="
//					+ GlobalVar.JSESSIONID);
//		}������Ϣ����,,,
		// ��������ȡ��HttpResponse
		try {
			httpResponse = httpclient.execute(httpRequest);
		} catch (Exception e) {
			JSONObject jsonObj = new JSONObject();
			try {
				jsonObj.put("errorMsg", "����ʧ��");
				strResult = "msgType=" + HTTPMap.HTTP_FAILURE + "&data="
						+ jsonObj;
			} catch (JSONException e1) {

				e1.printStackTrace();
			}
			return strResult;
		}
		// �������״̬
		sta = httpResponse.getStatusLine().getStatusCode();
		// ͨ���쳣����
		if (sta != HttpStatus.SC_OK) {
			// ��������ȡ��HttpResponse
			try {
				httpResponse = httpclient.execute(httpRequest);
			} catch (Exception e) {
				JSONObject jsonObj = new JSONObject();
				try {
					jsonObj.put("errorMsg", "����ʧ��");
					strResult = "msgType=" + HTTPMap.HTTP_FAILURE + "&data="
							+ jsonObj;
				} catch (JSONException e1) {

					e1.printStackTrace();
				}
				return strResult;
			}
			// �������״̬
			sta = httpResponse.getStatusLine().getStatusCode();
			if (sta != HttpStatus.SC_OK) {
				String err = "���������󣬴�����룺" + String.valueOf(sta);
				JSONObject jsonObj = new JSONObject();
				try {
					jsonObj.put("errorMsg", err);
					strResult = "msgType=" + HTTPMap.HTTP_FAILURE + "&data="
							+ jsonObj;
				} catch (JSONException e1) {

					e1.printStackTrace();
				}
				return strResult;
			}
		}

		if (httpRequest != null) {
			strResult = EntityUtils.toString(httpResponse.getEntity());
			// DswLog.e("", "" + strResult);
			// GetGsonData(strResult);
			apduOnlineResult = strResult;
		}
		cookieStore = ((AbstractHttpClient) httpclient).getCookieStore();
		List<Cookie> cookies = cookieStore.getCookies();
		
		for (int i = 0; i < cookies.size(); i++){
			// �����Ƕ�ȡCookie['JSESSIONID']��ֵ���ھ�̬�����У���֤ÿ�ζ���ͬһ��ֵ
			if ("JSESSIONID".equals(cookies.get(i).getName())) {
//				GlobalVar.JSESSIONID = cookies.get(i).getValue();������Ϣ����,,,
				break;
			}
		}
		String type = GetType(apduOnlineResult);
		MyAsynctask.GetGsonData(apduOnlineResult);
		return apduOnlineResult;
	}
	
	private static String GetType(String result) {
		String msgType = "";
		try {
			int end = result.indexOf("&");
			String head = result.substring(0, end);
			int head_head = head.indexOf("=");
			String head_text = head.substring(head_head + 1); // 0002
			msgType = head_text;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return msgType;
	}
	
}
