package com.ylczjqnfc.test;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;

import com.ylczjqnfc.R;
import com.ylczjqnfc.utils.ImageFileCache;
import com.ylczjqnfc.utils.ImageGetFromHttp;
import com.ylczjqnfc.utils.ImageMemoryCache;

public class TestActivity extends Activity {

	private ImageView serverSplashImg;// �������Ĺ��ͼƬ
	private ImageMemoryCache memoryCache;
	private ImageFileCache fileCache;

	public Handler netHandler = new Handler(new Callback() {
		@Override
		public boolean handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case 1:
				serverSplashImg.setImageBitmap(splashyBitmap);
				serverSplashImg.setVisibility(View.VISIBLE);
				break;

			default:
				break;
			}
			return false;
		}
	});

	String imgUrl = null;
	Bitmap splashyBitmap = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splashy);
		serverSplashImg = (ImageView) findViewById(R.id.server_img_id);
		memoryCache = new ImageMemoryCache(this);
		fileCache = new ImageFileCache();
		// ����ͼƬ��ַ
		// String imgUrl = "http://...";
		imgUrl = getResources().getString(R.string.ad_img);
		if (imgUrl != null) {
			new Thread() {
				@Override
				public void run() {
					splashyBitmap = getBitmap(imgUrl);
					netHandler.sendEmptyMessage(1);
				};
			}.start();

		}

	}

	public Bitmap getBitmap(String url) {
		// ���ڴ滺���л�ȡͼƬ
		Bitmap result = memoryCache.getBitmapFromCache(url);
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
		return result;
	}
}
