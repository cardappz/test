package com.ylczjqnfc.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.ylczjqnfc.R;
import com.ylczjqnfc.utils.viewTools.SlidingMenu;

public class MainActivity extends Activity{

	private SlidingMenu mMenu;//�໬�˵�
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		initViews();
		
	}
	public void initViews(){
		mMenu = (SlidingMenu) findViewById(R.id.id_menu);
	}
	
	//����˵���ť����໬���˵�
	public void toggleMenu(View view)
	{
		mMenu.toggle();
	}
}
