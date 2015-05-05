package com.ylczjqnfc.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.ylczjqnfc.R;
import com.ylczjqnfc.utils.ImageFileCache;
import com.ylczjqnfc.utils.ImageGetFromHttp;
import com.ylczjqnfc.utils.ImageMemoryCache;
import com.ylczjqnfc.utils.NetworkState;

public class SplashScreenActivity extends Activity {

	private ImageView serverSplashImg;//�������Ĺ��ͼƬ
	private ImageMemoryCache memoryCache;
	private ImageFileCache fileCache;
	private Context mContext;
	
	private final int GET_IDIMAG = 1;//��ȡ��������ͼƬ��(1)���ڴ滺���л� (2)���ļ������л�ȡ (3)��������Դ�����ػ�ȡ��
	
	
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

	String imgDownloadUrl = null;//�������صĵ�ַ
	Bitmap splashyBitmap = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splashy);
		mContext = this;
		initViews();
		
		if(NetworkState.isNetworkAvailable(mContext)){
			if (imgDownloadUrl != null) {
				new Thread() {
					@Override
					public void run() {
						splashyBitmap = getBitmap(imgDownloadUrl);
						netHandler.sendEmptyMessage(GET_IDIMAG);
					};
				}.start();
			}
		}else{
			splashyBitmap = getBitmap(imgDownloadUrl);
			netHandler.sendEmptyMessage(GET_IDIMAG);
			Toast.makeText(mContext, "�������ӳ�ʱ", 1).show();
		}
		
	}
	
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
	
	public void initViews(){
		serverSplashImg = (ImageView)findViewById(R.id.server_img_id);
		memoryCache = new ImageMemoryCache(this);
		fileCache = new ImageFileCache();
		imgDownloadUrl = getResources().getString(R.string.ad_img);// ����ͼƬ��ַ
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splashy, menu);
		return true;
	}

}
