package com.stereogarage.activity.alipay;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.AuthTask;
import com.alipay.sdk.app.PayTask;
import com.stereogarage.Bean.Const_Util;
import com.stereogarage.Bean.LateInfo2;
import com.stereogarage.Bean.LoginInfo;
import com.stereogarage.Bean.ShowEntity;
import com.stereogarage.R;
import com.stereogarage.activity.MainActivity;
import com.stereogarage.activity.PayShowActivity;
import com.stereogarage.activity.WeChatPayService;
import com.stereogarage.callback.AllInterface;
import com.stereogarage.util.NetUtil;
import com.stereogarage.util.OrderInfoUtil2_0;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.io.IOException;
import java.util.Map;

import static com.stereogarage.util.NetUtil.getOrderdata_containsurl;
import static com.stereogarage.util.NetUtil.getpaydata_containsurl;
import static com.stereogarage.util.NetUtil.post_containsurl;


/**
 *  重要说明:
 *  
 *  这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
 *  真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
 *  防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险； 
 */
public class PayDemoActivity extends Activity implements View.OnClickListener,NetUtil.My_callback,AllInterface.Cb,AllInterface.Cb1{
	
	/** 支付宝支付业务：入参app_id */
	public static final String APPID = "2018012102010421";
	
	/** 支付宝账户登录授权业务：入参pid值 */
	public static final String PID = "";
	/** 支付宝账户登录授权业务：入参target_id值 */
	public static final String TARGET_ID = "";

	/** 商户私钥，pkcs8格式 */
	/** 如下私钥，RSA2_PRIVATE 或者 RSA_PRIVATE 只需要填入一个 */
	/** 如果商户两个都设置了，优先使用 RSA2_PRIVATE */
	/** RSA2_PRIVATE 可以保证商户交易在更加安全的环境下进行，建议使用 RSA2_PRIVATE */
	/** 获取 RSA2_PRIVATE，建议使用支付宝提供的公私钥生成工具生成， */
	/** 工具地址：https://doc.open.alipay.com/docs/doc.htm?treeId=291&articleId=106097&docType=1 */
	public static final String RSA2_PRIVATE = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQClxWdEFLdCRfb9o3T1+MoYjuKHQIeLKIHzqFcC4RO11gdWjeqWxh3jC4+wAAMkiYBWgf3O1VWPE9zApwLDXxpdIcsoEqTK/RZptN9KBXwn10+P94C3cy8AtdV2Ygqn1o41wU7eVsYvs4PHahYkSKZLOmm+ShxgMNs1Rw8wkTgP+LiXfxMU0NDKTYftKnFmFHtZhz8/VhfmAergDQbrKpjGh9p+hMP1+OoD7g8Y0xPdiIDuc6AFXNdZro9h2nGiI22KAEtPpo4X+3ipOWJtcRDreypvcR8MFAeJtj3drjWEXFw1QSA5xGxWOrdDGa1oizutukFmXISNWr7vINDzdBLbAgMBAAECggEBAKPLCD0Bxw1puiFlh+SefXmEfRmszgDW9og9djP3FxWdM8T3FqsBeHhlbQU3DePlJPTVHmZm7lIgCwgkPRuz9YW+JVEkEsYrCiFKQTKPdtya5xdWM02QNwoZoKCHQQYTfpJovbf8v3hUhMnd7aDOU9mTDvahnaYBwCbFXqHbayyDOxuedBtBW86zXvkYvWM1UOEk0taXr+4CqFjbkvrZzdlxBEj/peCcgEgDgyoksOgZm26jv8OZS+ckT/xTPpC8TVKYROX0DbsgzJDrsIC6C3QXyflAHRW9/WVBv0iW8drO6Vn7pBLiMjC3Uq1j3Rcc/k8cHDGXqeq5k3LrFfz2yIECgYEA0iZmkJF+LsDiVoLtPKUYT/6ic6R0vPH3w0Ncm575b1eghEoDmAnXOxFFsqbo4UU/xkBmGNQib73CBMRtmXAjtx/2vOySXzcRItZ10Lsr+AsVC1XusWDY+nXbGjPyBNeDeVSX16RuHKRSGvR5pWE6EC5r2Cvb5xHNRyaRaPyfF5sCgYEAyfBJ/Lt6fI8Fm9RrbUd7yp1Mro7ZH1BlU2XA5r5fuMb4I72IR7u3mm2KdQlIIRwmgsEwuy+AEqZK9Yj6O6ZvEA+iKXXzz26L4//Krvou8Rrtb9nTDUxek1Ek+WBkNM+2fMZFyPqEuTbp3b6L8D/LYu5RqtwGh4QmM5DzN48bxcECgYBVXaAATfnSCCxpNBomrnHn3Ooky8NYFXqt6SkvVi7BGflsg5xeIHE731JOthqXl8fGQta2c0Xh2lV8FkbnM3RuqR9Ts03qO7ZMTo3hUocT5XyQ9EFX8J1zcHimdj9z1A/mM4TS5kqMe/uWU4UutwYdGJ5aW8aiYMrggjxDR4tgzQKBgDtRZTpjC9jY3knm/ONt0xQyXpsMBQVoxUrZIe/lzhJmB+/ftc90ebtYIAq+/jwTm9Be1WS20EUqKey0HuU0v30VSoopRsQQ8fC4a544iV9zjHmFn7RRUZquhFDomqbUuQMv7sHZrPEHC22CknKxGppl7QFmfliCXgJZusgANqVBAoGBALm+ALhv5LeqO/xuKmU7z+ZkYl/ox8S9t+JcsvUaRMyDGGzZ1Aaq14p26p61G3aqfxvGcM/FOleRdGqfBVMuiZ2zK/AabysX4odk0Lo5xsvvAo99AmhX9iHT89YAvW3TklbNMvm6lJXyQhsQmByaupbUEjxBVDjRU6yI3VYvQZ7i";
	public static final String RSA_PRIVATE = "";
	
	private static final int SDK_PAY_FLAG = 1;
	private static final int SDK_AUTH_FLAG = 2;
	//static String money1;

	//private final String NORMAL_ACTION = "com.example.normal.receiver";

	ImageView wechat, alipay;
	TextView button;
	RelativeLayout wechat_layout, alipay_layout;
	LinearLayout lin1,lin2;
	public int i = 0;
	public TextView current_addr,start_time,leave_time,car_price,money,textview_price,cost;
	public View divider;
	public String value,start_time1,leave_time1,money1,money2,price,address;
	public LoginInfo li;
	public LateInfo2 li2;
	private StringBuffer sb;
	private Map<String,String> resultunifiedorder;
	private PayReq req;
	private  IWXAPI msgApi;
	private int payFlag = 11;
	public SharedPreferences sp;
	public SharedPreferences.Editor editor;
	public int flag;
	/**
	 * 订单ID
	 */


	/**
	 * 最后需要支付的金额
	 */
	String fastAmount="0.01";
	/**
	 * 不同类型的订单
	 */
	//int type = "APP";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pay_main);
		msgApi = WXAPIFactory.createWXAPI(this, "wxecac44563f42e45f");
		msgApi.registerApp("wxecac44563f42e45f");
		wechat = (ImageView) findViewById(R.id.wechat);
		alipay = (ImageView) findViewById(R.id.alipay);
		current_addr = (TextView) findViewById(R.id.current_addr);
		start_time = (TextView) findViewById(R.id.start_time);
		leave_time = (TextView) findViewById(R.id.leave_time);
		car_price = (TextView) findViewById(R.id.car_price);
		textview_price = (TextView) findViewById(R.id.textview_price);
		cost = (TextView) findViewById(R.id.cost);
		money = (TextView) findViewById(R.id.money);
		divider = (View) findViewById(R.id.divider);
		lin1 = (LinearLayout) findViewById(R.id.line1);
		lin2 = (LinearLayout) findViewById(R.id.line2);
		wechat_layout = (RelativeLayout) findViewById(R.id.wechat_layout);
		alipay_layout = (RelativeLayout) findViewById(R.id.alipay_layout);
		button = (TextView) findViewById(R.id.button);
		wechat_layout.setOnClickListener(this);
		alipay_layout.setOnClickListener(this);
		button.setOnClickListener(this);

		sb = new StringBuffer();
		req = new PayReq();

		Intent intent = getIntent();
		//从Intent当中根据key取得value
	//	Bundle bundle = intent.getExtras();
//以下为判断bundle是否为空，以及bundle是否包含关键词“ABC”
//		if (bundle != null && bundle.containsKey("value"))
//		{
//			flag = intent.getIntExtra("flag", 0);
//			//errcode = getIntent().getIntExtra("code", 2);
//			address = intent.getStringExtra("address");
//			price = intent.getStringExtra("price");
//			start_time1 = intent.getStringExtra("start_time");
//			leave_time1 = intent.getStringExtra("leave_time");
//			money1 = intent.getStringExtra("money");
//			value = intent.getStringExtra("value");
//			errcode=2;
//		}else if(bundle != null && bundle.containsKey("class")){
//			errcode = intent.getIntExtra("code", 2);
//		}
		if (intent!= null) {
			flag = getIntent().getIntExtra("flag", 2);
			//errcode = getIntent().getIntExtra("code", 2);
			address = intent.getStringExtra("address");
			price = intent.getStringExtra("price");
			start_time1 = intent.getStringExtra("start_time");
			leave_time1 = intent.getStringExtra("leave_time");
			money1 = intent.getStringExtra("money");
			value = intent.getStringExtra("value");
//			errcode = 2;

//			editor = sp.edit();
//			editor.putInt("flag", flag);
//			editor.commit();

//			Intent intent3 = new Intent("test");
//			intent.putExtra("flag", flag);
//			intent.putExtra("value", value);
//			sendBroadcast(intent3);

			if (flag == 0) {
//				Intent intent1 = getIntent();
//				//从Intent当中根据key取得value
//				if (intent1.getExtras().containsKey("class")) {
//					errcode = intent1.getIntExtra("code", 2);
//				}
//				if (errcode == 0) {
//					try {
//
//						//responseData = null;
//						getOrderdata_containsurl(Const_Util.Map_Url16, value, PayDemoActivity.this);
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
//
//				} else {
					address = intent.getStringExtra("address");
					price = intent.getStringExtra("price");
					start_time1 = intent.getStringExtra("start_time");
					leave_time1 = intent.getStringExtra("leave_time");
					money1 = intent.getStringExtra("money");
					value = intent.getStringExtra("value");
					current_addr.setText(address);
					start_time.setText(start_time1);
					leave_time.setText(leave_time1);
					money.setText(money1 + "元");
					car_price.setText(price + "元");
//				Toast.makeText(getApplicationContext(), "成功", Toast.LENGTH_SHORT).show();
					sp = this.getSharedPreferences("user", Context.MODE_WORLD_READABLE);
					editor = sp.edit();
					editor.putString("username", value);
					editor.putString("address", address);
					editor.putString("price", price);
					editor.putString("start_time", start_time1);
					editor.putString("leave_time", leave_time1);
					editor.putString("money", money1);
				    editor.putInt("flag", flag);
					editor.commit();
	//			}
			}
			if (flag == 1) {
//				Intent intent2 = getIntent();
//				//从Intent当中根据key取得value
//				if (intent2.getExtras().containsKey("class")) {
//					errcode = intent2.getIntExtra("code", 2);
//				}
//				if (errcode == 0) {
//					value = intent.getStringExtra("value");
//					money1 = intent.getStringExtra("money");
//					try {
//
//						//responseData = null;
//						getpaydata_containsurl(Const_Util.Map_Url20, value, PayDemoActivity.this);
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
//				} else {
					value = intent.getStringExtra("value");
					money1 = intent.getStringExtra("money");
					try {
//                  responseData = null;
						post_containsurl(Const_Util.Map_Url19, value, PayDemoActivity.this);
					} catch (IOException e) {
						e.printStackTrace();
					}

					lin1.setVisibility(View.GONE);
					lin2.setVisibility(View.GONE);
					textview_price.setText("预约时间");
					cost.setText("超时费用");
					sp = PayDemoActivity.this.getSharedPreferences("user", Context.MODE_WORLD_READABLE);
					editor = sp.edit();
					editor.putString("username", value);
					editor.putString("money", money1);
				    editor.putInt("flag", flag);
					editor.commit();
	//			}

			}
		}
		    money2 = money1;

	}

//	public void sendBroadcast(View view) {
//		Intent intent = new Intent(NORMAL_ACTION);
//		intent.putExtra("Msg", "Hi");
//		sendBroadcast(intent);
//	}


	@Override
	public void callback4(final String param) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {

				li = NetUtil.parse_logininfo(param);
				if(li.status==1) {
					Intent intent2 = new Intent(getApplicationContext(),MainActivity.class);
					//intent2.putExtra("user",value);
					startActivity(intent2);
					//Toast.makeText(getApplicationContext(), "成功", Toast.LENGTH_SHORT).show();
				}
				else
					Toast.makeText(getApplicationContext(), "失败", Toast.LENGTH_SHORT).show();
			}
		});
	}

	public void callback3(final String param) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				li2 = NetUtil.parse_lateinfo2(param);
				address = li2.address;
				price = li2.money+"";
				start_time1 = li2.order_time;
				leave_time1 = li2.cancel_time;
				//money2 = String.valueOf(li2.money);

				car_price.setText(start_time1);
				money.setText(money1 + "元");
				current_addr.setText(address);

				sp = PayDemoActivity.this.getSharedPreferences("user", Context.MODE_WORLD_READABLE);
				editor = sp.edit();
				editor.putString("username", value);
				editor.putString("address", address);
				editor.putString("price", price);
				editor.putString("start_time", start_time1);
				editor.putString("leave_time", leave_time1);
				editor.putString("money", money1);
				editor.commit();


			}
		});
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.wechat_layout:
				wechat.setImageResource(R.mipmap.type_select);
				alipay.setImageResource(R.mipmap.type_unselect);
				i=0;
				break;
			case R.id.alipay_layout:
				wechat.setImageResource(R.mipmap.type_unselect);
				alipay.setImageResource(R.mipmap.type_select);
				i=1;
				break;
			case R.id.button:
				if(i==1)
					payV2(view);
				else {

					WeChatPayService weChatPay = new WeChatPayService(this, fastAmount);
					weChatPay.pay();
				//	Toast.makeText(getApplicationContext(), "1111111111111", Toast.LENGTH_SHORT).show();

				}
				break;
			default:
				break;
		}
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
					showNormalDialog1("        请在五分钟之内点击'我已离开'，超过五分钟未点击将进行扣费！");
					//Toast.makeText(getApplicationContext(), "成功", Toast.LENGTH_SHORT).show();

//					Intent intent1 = new Intent(PayDemoActivity.this,MainActivity.class);
//					startActivity(intent1);
				}

				else
					Toast.makeText(getApplicationContext(), "失败", Toast.LENGTH_SHORT).show();
			}
		});
	}

	private void   showNormalDialog1(String msg){

		final AlertDialog.Builder normalDialog = new AlertDialog.Builder(PayDemoActivity.this);
		normalDialog.setMessage(msg);

		normalDialog.setPositiveButton( "确定", new  DialogInterface.OnClickListener() {
			@Override
			public   void   onClick(DialogInterface dialog, int   which) {
				Intent intent = new Intent(PayDemoActivity.this, PayShowActivity.class);
				intent.putExtra("user", value);
				intent.putExtra("start_time1", start_time1);
				intent.putExtra("address", address);
				intent.putExtra("leave_time1", leave_time1);
				intent.putExtra("money1", money1);
				intent.putExtra("price", price);
				startActivity(intent);
			}
		});

		normalDialog.show();
	}

	public void finishActivity(View view) {
		finish();
	}


	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		@SuppressWarnings("unused")
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case SDK_PAY_FLAG: {
					@SuppressWarnings("unchecked")
					PayResult payResult = new PayResult((Map<String, String>) msg.obj);
					/**
					 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
					 */
					String resultInfo = payResult.getResult();// 同步返回需要验证的信息
					String resultStatus = payResult.getResultStatus();
					// 判断resultStatus 为9000则代表支付成功
					if (TextUtils.equals(resultStatus, "9000")) {

						// 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
						//Toast.makeText(PayDemoActivity.this, "支付成功", Toast.LENGTH_SHORT).show();

						Intent intent1 = getIntent();
						//从Intent当中根据key取得value
						if (intent1 != null) {
							int flag = getIntent().getIntExtra("flag", 0);
//							sp = PayDemoActivity.this.getSharedPreferences("user", Context.MODE_WORLD_READABLE);
//							editor = sp.edit();
//							editor.putInt("flag", flag);
//							editor.commit();
							if (flag == 0) {
								try {

									//responseData = null;
									getOrderdata_containsurl(Const_Util.Map_Url16, value, PayDemoActivity.this);
								} catch (IOException e) {
									e.printStackTrace();
								}

							}
							else {
								try {

									//responseData = null;
									getpaydata_containsurl(Const_Util.Map_Url20, value, PayDemoActivity.this);
								} catch (IOException e) {
									e.printStackTrace();
								}
								//Toast.makeText(PayDemoActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
							}
						}
					}
							else
						{
						// 该笔订单真实的支付结果，需要依赖服务端的异步通知。
						Toast.makeText(PayDemoActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
					}
					break;
				}
				case SDK_AUTH_FLAG: {
					@SuppressWarnings("unchecked")
					AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
					String resultStatus = authResult.getResultStatus();

					// 判断resultStatus 为“9000”且result_code
					// 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
					if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
						// 获取alipay_open_id，调支付时作为参数extern_token 的value
						// 传入，则支付账户为该授权账户
						Toast.makeText(PayDemoActivity.this,
								"授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
								.show();
					} else {
						// 其他状态值则为授权失败
						Toast.makeText(PayDemoActivity.this,
								"授权失败" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT).show();

					}
					break;
				}
				default:
					break;
			}
		};
	};

	/**
	 * 支付宝支付业务
	 *
	 *
	 */
	public void payV2(View v) {
		if (TextUtils.isEmpty(APPID) || (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))) {
			new AlertDialog.Builder(this).setTitle("警告").setMessage("需要配置APPID | RSA_PRIVATE")
					.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialoginterface, int i) {
							//
							finish();
						}
					}).show();
			return;
		}

		/**
		 * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
		 * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
		 * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
		 *
		 * orderInfo的获取必须来自服务端；
		 */
		boolean rsa2 = (RSA2_PRIVATE.length() > 0);
		Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa2,money2);
		String orderParam = OrderInfoUtil2_0.buildOrderParam(params);

		String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
		String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
		final String orderInfo = orderParam + "&" + sign;

		Runnable payRunnable = new Runnable() {

			@Override
			public void run() {
				PayTask alipay = new PayTask(PayDemoActivity.this);
				Map<String, String> result = alipay.payV2(orderInfo, true);
				Log.i("msp", result.toString());

				Message msg = new Message();
				msg.what = SDK_PAY_FLAG;
				msg.obj = result;
				mHandler.sendMessage(msg);
			}
		};

		Thread payThread = new Thread(payRunnable);
		payThread.start();
	}

	/**
	 * 支付宝账户授权业务
	 *
	 * @param v
	 */
	public void authV2(View v) {
		if (TextUtils.isEmpty(PID) || TextUtils.isEmpty(APPID)
				|| (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))
				|| TextUtils.isEmpty(TARGET_ID)) {
			new AlertDialog.Builder(this).setTitle("警告").setMessage("需要配置PARTNER |APP_ID| RSA_PRIVATE| TARGET_ID")
					.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialoginterface, int i) {
						}
					}).show();
			return;
		}

		/**
		 * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
		 * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
		 * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
		 *
		 * authInfo的获取必须来自服务端；
		 */
		boolean rsa2 = (RSA2_PRIVATE.length() > 0);
		Map<String, String> authInfoMap = OrderInfoUtil2_0.buildAuthInfoMap(PID, APPID, TARGET_ID, rsa2);
		String info = OrderInfoUtil2_0.buildOrderParam(authInfoMap);

		String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
		String sign = OrderInfoUtil2_0.getSign(authInfoMap, privateKey, rsa2);
		final String authInfo = info + "&" + sign;
		Runnable authRunnable = new Runnable() {

			@Override
			public void run() {
				// 构造AuthTask 对象
				AuthTask authTask = new AuthTask(PayDemoActivity.this);
				// 调用授权接口，获取授权结果
				Map<String, String> result = authTask.authV2(authInfo, true);

				Message msg = new Message();
				msg.what = SDK_AUTH_FLAG;
				msg.obj = result;
				mHandler.sendMessage(msg);
			}
		};

		// 必须异步调用
		Thread authThread = new Thread(authRunnable);
		authThread.start();
	}

	/**
	 * get the sdk version. 获取SDK版本号
	 *
	 */
	public void getSDKVersion() {
		PayTask payTask = new PayTask(this);
		String version = payTask.getVersion();
		Toast.makeText(this, version, Toast.LENGTH_SHORT).show();
	}



}
