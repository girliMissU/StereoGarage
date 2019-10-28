package com.stereogarage.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.stereogarage.activity.alipay.PayDemoActivity;
import com.stereogarage.wxapi.WXPayEntryActivity;
import com.tencent.mm.opensdk.constants.Build;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.conn.util.InetAddressUtils;
import cz.msebera.android.httpclient.message.BasicNameValuePair;

/**
 * 创建人 : skyCracks<br>
 * 创建时间 : 2016-7-18上午11:02:34<br>
 * 版本 : [v1.0]<br>
 * 类描述 : 微信支付实现服务端操作及后续调起支付<br>
 */
@SuppressLint("DefaultLocale")
public class WeChatPayService {

	private static final String TAG = WeChatPayService.class.getSimpleName();

	private IWXAPI api;
	private Context context;
	/** 订单类型 */
	//private int type;
	/** 内部订单 */
	private String out_trade_no;
	/** 商品描述 */
	private String body;
	/** 商品金额费用, 单位是分 */
	private String total_fee;
	//public String preid;

	/**
	 * @param context

	 * @param total_fee
	 *            商品金额费用, 单位是分
	 */
	public WeChatPayService(Context context,  String total_fee) {
		this.context = context;
		//this.type = type;
		//this.out_trade_no = out_trade_no;

		this.total_fee = total_fee;
		this.api = WXAPIFactory.createWXAPI(context, Constants.APP_ID, false);
	}


	public void pay() {
		// 检测是否安装了微信
		boolean isWeChat = api.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
		if (isWeChat) {
			new GetPrepayIdTask().execute("https://api.mch.weixin.qq.com/pay/unifiedorder");
		}
		else
			Toast.makeText(context, "未安装微信", Toast.LENGTH_SHORT).show();
	}
	
	/**
	 * 异步网络请求获取预付Id
	 */
	private class GetPrepayIdTask extends AsyncTask<String, Void, String> {

		private ProgressDialog dialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = ProgressDialog.show(context, "提示", "正在提交订单");
		}

		@Override
		protected void onPostExecute(String result) {
			// 第三步, 发送支付请求
			super.onPostExecute(result);
			if (dialog != null) {
				dialog.dismiss();
			}
			sendPayReq(result);
		}


		@Override
		protected String doInBackground(String... params) {
			// 网络请求获取预付Id
			String url = String.format("https://api.mch.weixin.qq.com/pay/unifiedorder");
			String entity = genEntity();
			byte[] buf = WeChatHttpClient.httpPost(url, entity);
			if (buf != null && buf.length > 0) {
				try {
					Map map = XmlUtil.doXMLParse(new String(buf));
					return (String) map.get("prepay_id");
					//Toast.makeText(context, a+"", Toast.LENGTH_SHORT).show();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return null;
		}
	}

	/**
	 * 发送支付请求
	 * @param prepayId 预付Id
	 */
	private void sendPayReq(String prepayId) {

		IWXAPI api = WXAPIFactory.createWXAPI(context,Constants.APP_ID);
		api.registerApp(Constants.APP_ID);
		PayReq req = new PayReq();
		req.appId = Constants.APP_ID;
		req.partnerId = Constants.MCH_ID;
		req.prepayId = prepayId;
		req.nonceStr = genNonceStr();
		req.timeStamp = String.valueOf(genTimeStamp());
		req.packageValue = "Sign=WXPay";

		List<NameValuePair> signParams = new LinkedList<NameValuePair>();
		signParams.add(new BasicNameValuePair("appid", req.appId));
		signParams.add(new BasicNameValuePair("noncestr", req.nonceStr));
		signParams.add(new BasicNameValuePair("package", req.packageValue));
		signParams.add(new BasicNameValuePair("partnerid", req.partnerId));
		signParams.add(new BasicNameValuePair("prepayid", req.prepayId));
		signParams.add(new BasicNameValuePair("timestamp", req.timeStamp));
		req.sign = genPackageSign(signParams);
		// 传递的额外信息,字符串信息,自定义格式
		// req.extData = type +"#" + out_trade_no + "#" +total_fee;
		// 微信支付结果界面对调起支付Activity的处理
		//APPCache.payActivity.put("调起支付的Activity",WeChatPayService.class);
		//APPCache.payActivity.put("调起支付的Activity",(PayDemoActivity)context);
		// 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
		api.registerApp(Constants.APP_ID);
		api.sendReq(req);
		//Toast.makeText(context, api.sendReq(req)+"", Toast.LENGTH_SHORT).show();
	}

	/**
	 * 微信支付，构建统一下单请求参数
	 */
	public String genEntity() {
		List<NameValuePair> packageParams = new ArrayList<NameValuePair>();
		// APPID
		packageParams.add(new BasicNameValuePair("appid", Constants.APP_ID));
		// 商品描述
		packageParams.add(new BasicNameValuePair("body", "test"));
		// 商户ID
		packageParams.add(new BasicNameValuePair("mch_id", Constants.MCH_ID));
		// 随机字符串
		packageParams.add(new BasicNameValuePair("nonce_str", genNonceStr()));
		// 回调接口地址
		packageParams.add(new BasicNameValuePair("notify_url", "https://weixin.qq.com"));
		// 我们的订单号
		packageParams.add(new BasicNameValuePair("out_trade_no", genOutTradNo()));
		// 提交用户端ip
		packageParams.add(new BasicNameValuePair("spbill_create_ip", getIPAddress()));
		BigDecimal totalFeeBig = new BigDecimal(total_fee);
		int totalFee = totalFeeBig.multiply(new BigDecimal(100)).intValue();
		// 总金额，单位为 分 !
		packageParams.add(new BasicNameValuePair("total_fee",  String.valueOf(totalFee)));
		// 支付类型， APP
		packageParams.add(new BasicNameValuePair("trade_type", "APP"));
		// 生成签名
		String sign = genPackageSign(packageParams);
		packageParams.add(new BasicNameValuePair("sign", sign));
		String xmlstring = XmlUtil.toXml(packageParams);
		try {
			return new String(xmlstring.toString().getBytes(), "ISO-8859-1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 生成签名
	 */
	public static String genPackageSign(List<NameValuePair> params) {
		try {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < params.size(); i++) {
				sb.append(params.get(i).getName());
				sb.append('=');
				sb.append(params.get(i).getValue());
				sb.append('&');
			}
			sb.append("key=");
			sb.append(Constants.API_KEY);

			String packageSign = MD5.getMessageDigest(sb.toString().getBytes("utf-8")).toUpperCase();
			return packageSign;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 微信支付调用统一下单接口，随机字符串
	 */
	public static String genNonceStr() {
		try {
			Random random = new Random();
			String rStr = MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes("utf-8"));
			return rStr;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

	private String genOutTradNo() {
		Random random = new Random();
		//   return "dasgfsdg1234"; //订单号写死的话只能支付一次，第二次不能生成订单
		return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
	}

	/**
	 * 得到本机IP地址，WIFI下获取的是局域网IP，数据流量下获取的是公网IP
	 * 
	 * @return
	 */
	public static String getIPAddress() {
		try {
			Enumeration<NetworkInterface> en = NetworkInterface
					.getNetworkInterfaces();
			// 遍历所用的网络接口
			while (en.hasMoreElements()) {
				NetworkInterface nif = en.nextElement();// 得到每一个网络接口绑定的所有ip
				Enumeration<InetAddress> inet = nif.getInetAddresses();
				// 遍历每一个接口绑定的所有ip
				while (inet.hasMoreElements()) {
					InetAddress ip = inet.nextElement();
					if (!ip.isLoopbackAddress()
							&& InetAddressUtils.isIPv4Address(ip
									.getHostAddress())) {
						return ip.getHostAddress();
					}
				}
			}
		} catch (SocketException e) {
			Log.e(TAG, "获取本地ip地址失败", e);
		}
		return null;
	}

	private long genTimeStamp() {
		return System.currentTimeMillis() / 1000;
	}


}
