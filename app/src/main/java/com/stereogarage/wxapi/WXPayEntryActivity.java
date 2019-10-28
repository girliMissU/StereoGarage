package com.stereogarage.wxapi;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.stereogarage.Bean.Const_Util;
import com.stereogarage.Bean.LoginInfo;
import com.stereogarage.Bean.ShowEntity;
import com.stereogarage.R;
import com.stereogarage.activity.Constants;
import com.stereogarage.activity.MainActivity;
import com.stereogarage.activity.PayShowActivity;
import com.stereogarage.activity.alipay.PayDemoActivity;
import com.stereogarage.callback.AllInterface;
import com.stereogarage.util.NetUtil;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.io.IOException;

import static com.stereogarage.util.NetUtil.getOrderdata_containsurl;
import static com.stereogarage.util.NetUtil.getpaydata_containsurl;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler,NetUtil.My_callback,AllInterface.Cb1 {

	private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";

	private IWXAPI api;
	public LoginInfo li;
	public String value,start_time1,leave_time1,money1,price,address;

	public SharedPreferences sp;
	public SharedPreferences.Editor editor;
	public int flag;



	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pay_result);

		api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
		api.handleIntent(getIntent(), this);


		//IntentFilter filter = new IntentFilter("test");
		//registerReceiver(broadcastReceiver, filter);

	}

//	BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
//
//		@Override
//		public void onReceive(Context context, Intent intent) {
//			String action = intent.getAction();
//			if (action.equals("test"))
//				// TODO Auto-generated method stub
//			flag = intent.getIntExtra("flag",2);
//			value = intent.getStringExtra("value");
//
//		}
//
//
//	};


	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
		api.handleIntent(intent, this);
	}



//	@Override
//	protected void onStop() {
//		super.onStop();
//		unregisterReceiver(broadcastReceiver);
//	}



	@Override
	public void onReq(BaseReq req) {
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
				if(li.status==1) {
					//showNormalDialog1("        请在五分钟之内点击'我已离开'，超过五分钟未点击将进行扣费！");
					sp = WXPayEntryActivity.this.getSharedPreferences("user", Context.MODE_WORLD_READABLE);
					value= sp.getString("username", "");
					start_time1= sp.getString("start_time", "");
					address= sp.getString("address", "");
					leave_time1= sp.getString("leave_time", "");
					money1= sp.getString("money", "");
					price= sp.getString("price", "");
					Intent intent = new Intent(getApplicationContext(),PayShowActivity.class);
					intent.putExtra("user",value);
					intent.putExtra("start_time1",start_time1);
					intent.putExtra("address",address);
					intent.putExtra("leave_time1",leave_time1);
					intent.putExtra("money1",money1);
					intent.putExtra("price",price);
					startActivity(intent);
					Toast.makeText(getApplicationContext(), "成功", Toast.LENGTH_SHORT).show();
				}

				else
					Toast.makeText(getApplicationContext(), "失败", Toast.LENGTH_SHORT).show();
			}
		});
	}

	@Override
	public void callback4(final String param) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {

				li = NetUtil.parse_logininfo(param);
				if(li.status==1) {
					Intent intent2 = new Intent(WXPayEntryActivity.this,MainActivity.class);
//					sp = WXPayEntryActivity.this.getSharedPreferences("user", Context.MODE_WORLD_READABLE);
//					value= sp.getString("username", "");
					//intent2.putExtra("user",value);
					startActivity(intent2);
					//Toast.makeText(getApplicationContext(), "成功", Toast.LENGTH_SHORT).show();
				}
				else
					Toast.makeText(getApplicationContext(), "失败", Toast.LENGTH_SHORT).show();
			}
		});
	}

	@Override
	public void onResp(BaseResp resp) {
		//Log.d(TAG, "onPayFinish, errCode = " + resp.errCode);
		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
			if (resp.errCode == 0)
			{
				sp = WXPayEntryActivity.this.getSharedPreferences("user", Context.MODE_WORLD_READABLE);
				value= sp.getString("username", "");
				start_time1= sp.getString("start_time", "");
				address= sp.getString("address", "");
				leave_time1= sp.getString("leave_time", "");
				money1= sp.getString("money", "");
				price= sp.getString("price", "");
				flag=sp.getInt("flag",2);
            if (flag == 0) {
					try {

						//responseData = null;
						getOrderdata_containsurl(Const_Util.Map_Url16, value, WXPayEntryActivity.this);
					} catch (IOException e) {
						e.printStackTrace();
					}

					//Toast.makeText(getApplicationContext(), value+flag, Toast.LENGTH_SHORT).show();
				}
				else {
					try {

						//responseData = null;
						getpaydata_containsurl(Const_Util.Map_Url20, value, WXPayEntryActivity.this);
					} catch (IOException e) {
						e.printStackTrace();
					}
					//Toast.makeText(getApplicationContext(), value+flag, Toast.LENGTH_SHORT).show();
					//Toast.makeText(PayDemoActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
				}

//
//				Intent intent = new Intent(WXPayEntryActivity.this,PayDemoActivity.class);
//				intent.putExtra("code",resp.errCode);
//				intent.putExtra("class","late");
//				startActivity(intent);
//				Toast.makeText(getApplicationContext(), value+" "+flag+"", Toast.LENGTH_SHORT).show();
			}
			else
			{
				//WXPayEntryActivity.this.finish();
				Toast.makeText(getApplicationContext(), "支付失败", Toast.LENGTH_SHORT).show();
			}
			//AlertDialog.Builder builder = new AlertDialog.Builder(this);
		//	builder.setTitle("提示");
			//builder.setMessage("微信支付结果" + String.valueOf(resp.errCode));
			//builder.show();
		}
       else{
			WXPayEntryActivity.this.finish();
		   }
	}

	private void   showNormalDialog1(String msg){

		final AlertDialog.Builder normalDialog = new AlertDialog.Builder(getApplicationContext());
		normalDialog.setMessage(msg);

		normalDialog.setPositiveButton( "确定", new  DialogInterface.OnClickListener() {
			@Override
			public   void   onClick(DialogInterface dialog, int   which) {

				sp = WXPayEntryActivity.this.getSharedPreferences("user", Context.MODE_WORLD_READABLE);
				value= sp.getString("username", "");
				start_time1= sp.getString("start_time", "");
				address= sp.getString("address", "");
				leave_time1= sp.getString("leave_time", "");
				money1= sp.getString("money", "");
				price= sp.getString("price", "");
				Intent intent = new Intent(getApplicationContext(),PayShowActivity.class);
				intent.putExtra("user",value);
				intent.putExtra("start_time1",start_time1);
				intent.putExtra("address",address);
				intent.putExtra("leave_time1",leave_time1);
				intent.putExtra("money1",money1);
				intent.putExtra("price",price);
				startActivity(intent);

			}
		});

		normalDialog.show();
	}
}