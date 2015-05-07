package com.ylczjqnfc.test;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.ylczjqnfc.R;

public class ObjectAnimActivity2 extends Activity {

	ImageView imageView1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.xml_for_anim3);
		imageView1 = (ImageView)findViewById(R.id.imageView1);
		
	}
	
	/**
	 * ��������
	 */
	public void horizontalStretch(View view){
		// ���ض���  
        Animator anim = AnimatorInflater.loadAnimator(this, R.anim.scalex);  
        imageView1.setPivotX(0);  
        imageView1.setPivotY(0);  
        //��ʾ�ĵ���invalidate  
        imageView1.invalidate();  
        anim.setTarget(imageView1);  
        anim.start();
	}
	
	/**
	 * ����������
	 */
	public void horizontalVerticalStretch(View view){
		// ���ض���  
        Animator anim = AnimatorInflater.loadAnimator(this, R.anim.scalexy);  
        imageView1.setPivotX(0);  
        imageView1.setPivotY(0);  
        //��ʾ�ĵ���invalidate  
        imageView1.invalidate();  
        anim.setTarget(imageView1);  
        anim.start();
	}
}
