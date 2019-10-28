package com.stereogarage.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.stereogarage.Bean.Const_Util;
import com.stereogarage.Bean.LoginInfo;
import com.stereogarage.Bean.ShowEntity;
import com.stereogarage.R;
import com.stereogarage.activity.alipay.PayDemoActivity;
import com.stereogarage.util.NetUtil;

import java.io.IOException;

import static com.stereogarage.util.NetUtil.getOrderdata_containsurl;


/**
 *  重要说明:
 *  
 *  这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
 *  真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
 *  防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险； 
 */
public class PayShowActivity extends Activity implements View.OnClickListener,NetUtil.My_callback{



	TextView button;
	public LoginInfo li;
	public TextView current_addr,start_time,leave_time,car_price,money;
	String value,start_time1,leave_time1,money1,price,address;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pay_show);
		current_addr = (TextView) findViewById(R.id.current_addr);
		start_time = (TextView) findViewById(R.id.start_time);
		leave_time = (TextView) findViewById(R.id.leave_time);
		car_price = (TextView) findViewById(R.id.car_price);
		money = (TextView) findViewById(R.id.money);
		button = (TextView) findViewById(R.id.button);
		button.setOnClickListener(this);

		Intent intent = getIntent();
		//从Intent当中根据key取得value
		if (intent != null) {
			//price=sharedPreferences.getString("password","");
			address = intent.getStringExtra("address");
			price = intent.getStringExtra("price");
			start_time1 = intent.getStringExtra("start_time1");
			leave_time1 = intent.getStringExtra("leave_time1");
			money1 = intent.getStringExtra("money1");
			value = intent.getStringExtra("user");
			current_addr.setText(address);
			start_time.setText(start_time1);
			leave_time.setText(leave_time1);
			money.setText(money1+"元");
			car_price.setText(price+"元");
		}

	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.button:
				showNormalDialog("是否确认离开？");
				break;
			default:
				break;
		}
	}
	private void   showNormalDialog(String msg){

		final AlertDialog.Builder normalDialog = new AlertDialog.Builder(PayShowActivity.this);
		normalDialog.setMessage(msg);

		normalDialog.setPositiveButton( "确定", new  DialogInterface.OnClickListener() {
			@Override
			public   void   onClick(DialogInterface dialog, int   which) {
				try {
					getOrderdata_containsurl(Const_Util.Map_Url17,value,PayShowActivity.this);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		normalDialog.setNegativeButton("取消", new  DialogInterface.OnClickListener() {
			@Override
			public   void   onClick(DialogInterface dialog, int   which) {
			}
		});
		normalDialog.show();
	}
	@Override
	public void callback2(String param, ShowEntity showEntity) {

	}

	@Override
	public void callback(final String param) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {

				li = NetUtil.parse_logininfo(param);
				if(li.status==1)
				{
					Toast.makeText(getApplicationContext(), "成功", Toast.LENGTH_SHORT).show();
//					startActivity(intent);
//					Intent intent1 = new Intent(PayDemoActivity.this,MainActivity.class);
//					startActivity(intent1);
//					Intent intent2 = new Intent(getApplicationContext(),OrderActivity.class);
//					intent2.putExtra("delete","1");
//					startActivity(intent2);
				}
				else
					Toast.makeText(getApplicationContext(), "失败", Toast.LENGTH_SHORT).show();
			}
		});
	}

	public void finishActivity(View view) {
		finish();
	}

}
