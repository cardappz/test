package com.ylczjqnfc.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import com.ylczjqnfc.R;
import com.ylczjqnfc.controller.GlobalVar;
import com.ylczjqnfc.http.HttpUtil;
import com.ylczjqnfc.pojo.Person;
import com.ylczjqnfc.utils.GsonTools;
import com.ylczjqnfc.utils.ImageFileCache;
import com.ylczjqnfc.utils.ImageGetFromHttp;
import com.ylczjqnfc.utils.ImageMemoryCache;
import com.ylczjqnfc.utils.LogUtil;
import com.ylczjqnfc.utils.NetworkState;

/**
 * �������࣬ͬʱҲ�ǵ�һ����
 * 
 * @author Administrator
 * 
 */
public class SplashScreenActivity extends Activity {

	private ImageView serverSplashImg;// �������Ĺ��ͼƬ
	private ImageMemoryCache memoryCache;
	private ImageFileCache fileCache;
	private Context mContext;

	private final int GET_IDIMAG = 1;// ��ȡ��������ͼƬ��(1)���ڴ滺���л� (2)���ļ������л�ȡ
										// (3)��������Դ�����ػ�ȡ��

	public Handler netHandler = new Handler(new Callback() {
		@Override
		public boolean handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case GET_IDIMAG:
				serverSplashImg.setImageBitmap(splashyBitmap);
				serverSplashImg.setVisibility(View.VISIBLE);
				break;

			default:
				break;
			}
			return false;
		}
	});

	String imgDownloadUrl = null;// �������صĵ�ַ
	Bitmap splashyBitmap = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.splashy);
		mContext = this;
		initViews();
		// �ж��Ƿ�������
		if (NetworkState.isNetworkAvailable(mContext)) {
			if (imgDownloadUrl != null) {
				new Thread() {
					@Override
					public void run() {
						splashyBitmap = getBitmap(imgDownloadUrl);
						netHandler.sendEmptyMessage(GET_IDIMAG);
					};
				}.start();
			}
		} else {
			splashyBitmap = getBitmap(imgDownloadUrl);
			netHandler.sendEmptyMessage(GET_IDIMAG);
			Toast.makeText(mContext, "�������ӳ�ʱ", 1).show();
		}
		
		new Thread(new MyThread()).start();
		splashy();
	}
	
	/**
	 * ��ʼ����ͼ
	 */
	public void initViews() {
		serverSplashImg = (ImageView) findViewById(R.id.server_img_id);
		memoryCache = new ImageMemoryCache(this);
		fileCache = new ImageFileCache();
		imgDownloadUrl = getResources().getString(R.string.ad_img);// ����ͼƬ��ַ
		GlobalVar.imgDownloadUrl = imgDownloadUrl;
	}

	private String versionMsg = "";
	
	private void splashy() {
		new Handler().postDelayed(new Runnable() {
			public void run() {
				// httpRequest(GET_ALPAY_LOGON);
				Intent intent = new Intent();
				intent.putExtra("versionMsg", versionMsg);
				intent.putExtra("sign", "");
				intent.setClass(SplashScreenActivity.this, MainActivity.class);
				startActivity(intent);
				onDestroy();
			}
		}, 3000);
	}
	
	// ��ȡ���ͼ�ķ���
	public Bitmap getBitmap(String url) {
		// ���ڴ滺���л�ȡͼƬ
		Bitmap result = null;
		try {
			result = memoryCache.getBitmapFromCache(url);
			if (result == null) {
				// �ļ������л�ȡ
				result = fileCache.getImage(url);
				if (result == null) {
					// �������ȡ
					result = ImageGetFromHttp.downloadBitmap(url);
					if (result != null) {
						fileCache.saveBitmap(result, url);
						memoryCache.addBitmapToCache(url, result);
					}
				} else {
					// ��ӵ��ڴ滺��
					memoryCache.addBitmapToCache(url, result);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splashy, menu);
		return true;
	}

}

class MyThread extends Thread {
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		try {
			HttpUtil httpUtil = new HttpUtil();
//			String jsonString = httpUtil.doHttpsPost(imgDownloadUrl, "1");
			String jsonString = httpUtil.doHttpsPost("http://192.168.1.229:8080/jsonProject2/json?action_flag=person", "1");
			
			Person person = GsonTools.getT(jsonString, Person.class);
			LogUtil.i(GlobalVar.TAG, "---->"+person.toString());
			String s = person.toString();
			System.out.println(s);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
