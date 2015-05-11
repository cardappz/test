package com.ylczjqnfc.activity;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ylczjqnfc.R;
import com.ylczjqnfc.adapter.MyFragmentPagerAdapter;
import com.ylczjqnfc.utils.viewTools.SlidingMenu;

/**
 * �����ܵĽ���
 * 
 * @author �쿡��
 * 
 */
public class MainActivity extends FragmentBaseActivity implements
		OnClickListener {

	private SlidingMenu mMenu;// �໬�˵�
	// ����չʾ"��ֵ"ҳ���Fragment
	private RechargeFragment rechargeFragment;
	// ����չʾ"��ѯ"ҳ���Fragment
	private QueryFragment queryFragment;
	// ����չʾ"����"ҳ���Fragment
	private DiscoveryFragment discoveryFragment;
	// ����չʾ"��"ҳ���Fragment
	private AccountFragment accountFragment;

	// ��ֵ
	private View recharge_layout;
	// ��ѯ
	private View query_layout;
	// ����
	private View discovery_layout;
	// ��
	private View account_layout;

	/**
	 * ��ֵ��ͼƬ������
	 */
	private ImageView recharge_image;
	private TextView recharge_text;
	/**
	 * ��ѯ��ͼƬ������
	 */
	private ImageView query_image;
	private TextView query_text;
	/**
	 * ���ֵ�ͼƬ������
	 */
	private ImageView discovery_image;
	private TextView discovery_text;
	/**
	 * �ҵ�ͼƬ������
	 */
	private ImageView account_image;
	private TextView account_text;
	
	private FragmentManager fragmentManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		//��ʼ������Ԫ��
		initViews();
		//��ȡfragmentManager
		fragmentManager = getSupportFragmentManager();
        // ��һ������ʱѡ�е�0��tab  
        setTabSelection(0);
		List<Fragment> fragList = new ArrayList<Fragment>();// ���ڴ�š���ֵ��������ѯ���������֡������ҡ�
		fragList.add(rechargeFragment);
		fragList.add(queryFragment);
		fragList.add(discoveryFragment);
		fragList.add(accountFragment);

		MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(
				getSupportFragmentManager(), fragList);

	}

	public void initViews() {
		mMenu = (SlidingMenu) findViewById(R.id.id_menu);
		rechargeFragment = new RechargeFragment();
		queryFragment = new QueryFragment();
		discoveryFragment = new DiscoveryFragment();
		accountFragment = new AccountFragment();
		recharge_layout = findViewById(R.id.recharge_layout);// ��ֵ
		recharge_layout.setOnClickListener(this);
		query_layout = findViewById(R.id.query_layout);// ��ѯ
		query_layout.setOnClickListener(this);
		discovery_layout = findViewById(R.id.discovery_layout);// ����
		discovery_layout.setOnClickListener(this);
		account_layout = findViewById(R.id.account_layout);// ��
		account_layout.setOnClickListener(this);
		// ��ֵ
		recharge_image = (ImageView) findViewById(R.id.recharge_image);
		recharge_text = (TextView) findViewById(R.id.recharge_text);
		// ��ѯ
		query_image = (ImageView) findViewById(R.id.query_image);
		query_text = (TextView) findViewById(R.id.query_text);
		// ����
		discovery_image = (ImageView) findViewById(R.id.discovery_image);
		discovery_text = (TextView) findViewById(R.id.discovery_text);
		// ��
		account_image = (ImageView) findViewById(R.id.account_image);
		account_text = (TextView) findViewById(R.id.account_text);
	}

	// ����˵���ť����໬���˵�
	public void toggleMenu(View view) {
		mMenu.toggle();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.recharge_layout:
			// ���������Ϣtabʱ��ѡ�е�1��tab
			setTabSelection(0);
			Toast.makeText(MainActivity.this, "recharge", 1).show();
			break;
		case R.id.query_layout:
			// ���������Ϣtabʱ��ѡ�е�2��tab
			setTabSelection(1);
			Toast.makeText(MainActivity.this, "query", 1).show();
			break;
		case R.id.discovery_layout:
			// ���������Ϣtabʱ��ѡ�е�3��tab
			setTabSelection(2);
			Toast.makeText(MainActivity.this, "discovery", 1).show();
			break;
		case R.id.account_layout:
			// ���������Ϣtabʱ��ѡ�е�4��tab
			setTabSelection(3);
			Toast.makeText(MainActivity.this, "account", 1).show();
			break;
		default:
			break;
		}
	}

	/**
	 * ���ݴ����index����������ѡ�е�tabҳ�� ����NFC�ֻ���
	 * 
	 * @param index
	 *            ÿ��tabҳ��Ӧ���±ꡣ0��ʾ��ֵ��1��ʾ��ѯ��2��ʾ���֣�3��ʾ�ҡ�
	 */
	private void setTabSelection(int index) {  
		// ÿ��ѡ��֮ǰ��������ϴε�ѡ��״̬  
        clearSelection();  
        // ����һ��Fragment����  
        FragmentTransaction transaction = fragmentManager.beginTransaction();  
        // �����ص����е�Fragment���Է�ֹ�ж��Fragment��ʾ�ڽ����ϵ����  
        hideFragments(transaction);  
        switch (index) {
        //��ֵ
		case 0:
			// ���������Ϣtabʱ���ı�ؼ���ͼƬ��������ɫ  
			recharge_image.setImageResource(R.drawable.ico_paychecked);
			recharge_text.setTextColor(Color.WHITE);
			// ���rechargeFragmentΪ�գ��򴴽�һ������ӵ������� 
			if(rechargeFragment==null){
				rechargeFragment = new RechargeFragment();
				transaction.add(R.id.content, rechargeFragment);
			}else{
				transaction.show(rechargeFragment);
			}
			break;
		//��ѯ
		case 1:
			// ���������Ϣtabʱ���ı�ؼ���ͼƬ��������ɫ 
			query_image.setImageResource(R.drawable.tab_query_selected);
			query_text.setTextColor(Color.WHITE);
			// ���queryFragmentΪ�գ��򴴽�һ������ӵ������� 
			if(queryFragment==null){
				queryFragment = new QueryFragment();
				transaction.add(R.id.content, queryFragment);
			}else{
				transaction.show(queryFragment);
			}
			break;
		//����
		case 2:
			// ���������Ϣtabʱ���ı�ؼ���ͼƬ��������ɫ 
			discovery_image.setImageResource(R.drawable.finding_selected);
			discovery_text.setTextColor(Color.WHITE);
			// ���discoveryFragmentΪ�գ��򴴽�һ������ӵ������� 
			if(discoveryFragment==null){
				discoveryFragment = new DiscoveryFragment();
				transaction.add(R.id.content, discoveryFragment);
			}else{
				transaction.show(discoveryFragment);
			}
			break;
		//��
		case 3:
		default:
			// ���������Ϣtabʱ���ı�ؼ���ͼƬ��������ɫ 
			account_image.setImageResource(R.drawable.ico_safechecked);
			account_text.setTextColor(Color.WHITE);
			// ���accountFragmentΪ�գ��򴴽�һ������ӵ������� 
			if(accountFragment==null){
				accountFragment = new AccountFragment();
				transaction.add(R.id.content, accountFragment);
			}else{
				transaction.show(accountFragment);
			}
			break;
		}
        transaction.commit();  
	}
	
	/** 
     * ��������е�ѡ��״̬�� 
     */  
    private void clearSelection() {  
    	recharge_image.setImageResource(R.drawable.ico_payunchecked);  
    	recharge_text.setTextColor(Color.parseColor("#82858b"));  
    	query_image.setImageResource(R.drawable.tab_query);  
    	query_text.setTextColor(Color.parseColor("#82858b"));  
        discovery_image.setImageResource(R.drawable.finding_normal);  
        discovery_text.setTextColor(Color.parseColor("#82858b"));  
        account_image.setImageResource(R.drawable.ico_safeunchecked);  
        account_text.setTextColor(Color.parseColor("#82858b"));  
    }  
    /** 
     * �����е�Fragment����Ϊ����״̬�� 
     *  
     * @param transaction 
     *            ���ڶ�Fragmentִ�в��������� 
     */  
    private void hideFragments(FragmentTransaction transaction) {  
        if (rechargeFragment != null) {  
            transaction.hide(rechargeFragment);  
        }  
        if (accountFragment != null) {  
            transaction.hide(accountFragment);  
        }  
        if (queryFragment != null) {  
            transaction.hide(queryFragment);  
        }  
        if (discoveryFragment != null) {  
            transaction.hide(discoveryFragment);  
        }  
    }  
}
