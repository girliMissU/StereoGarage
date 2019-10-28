package com.stereogarage.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.stereogarage.Bean.CancelInfo;
import com.stereogarage.Bean.Const_Util;
import com.stereogarage.Bean.LateInfo;
import com.stereogarage.Bean.LoginInfo;
import com.stereogarage.Bean.ShowEntity;
import com.stereogarage.R;
import com.stereogarage.activity.MainActivity;
import com.stereogarage.activity.alipay.PayDemoActivity;
import com.stereogarage.util.NetUtil;

import java.io.IOException;
import java.util.List;

import static android.support.v4.content.ContextCompat.startActivity;
import static com.stereogarage.util.NetUtil.postdata_containsurl;


public class ParentAdapt extends BaseObjectListAdapter implements NetUtil.My_callback {

	public static int mParentItem = -1;
	public CancelInfo li;
	public List<ShowEntity> data;
	public Context context;
	public ShowEntity tempEntity;
	public Handler mhandle=new Handler();
	public ParentAdapt(Context context, List<ShowEntity> datas) {
		super(context, datas);
           this.data=datas;
           this.context=context;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder vHolder = null;
		if (convertView == null) {
			vHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.activity_mybook, null);
			vHolder.textViewcurrent_addr = (TextView) convertView.findViewById(R.id.current_addr);
			vHolder.textViewcar_price = (TextView) convertView.findViewById(R.id.car_price);
			vHolder.textViewcar_time = (TextView) convertView.findViewById(R.id.car_time);
			vHolder.textViewcancel_book = (TextView) convertView.findViewById(R.id.cancel_book);
			//vHolder.listViewItem = (ListView)convertView.findViewById(R.id.listView_child);
			//vHolder.buttonStake = (Button)convertView.findViewById(R.id.button_1);
			convertView.setTag(vHolder);
		} else {
			vHolder = (ViewHolder) convertView.getTag();
		}

		tempEntity = (ShowEntity) mDatas.get(position);
		vHolder.textViewcurrent_addr.setText(tempEntity.getcurrent_addr());
		vHolder.textViewcar_price.setText(tempEntity.getcar_price());
		vHolder.textViewcar_time.setText(tempEntity.getcar_time());
		vHolder.textViewcancel_book.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				showNormalDialog("       预约时间超过15分钟，取消需要付费，是否确认取消？");


				//Toast.makeText(mContext, tempEntity.getOrder_id()+"", Toast.LENGTH_SHORT).show();
			}
		});


		//vHolder.buttonStake.setTag(position);

		return convertView;
	}

	private void   showNormalDialog(String msg){

		final AlertDialog.Builder normalDialog = new AlertDialog.Builder(context);
		normalDialog.setMessage(msg);

		normalDialog.setPositiveButton( "确定", new  DialogInterface.OnClickListener() {
			@Override
			public   void   onClick(DialogInterface dialog, int   which) {
				try {
					postdata_containsurl(Const_Util.Map_Url10, tempEntity.getOrder_id(), ParentAdapt.this, tempEntity);
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

		normalDialog.setCancelable(false);
		normalDialog.show();
	}

	class ViewHolder {
		TextView textViewcurrent_addr;
		TextView textViewcar_price;
		TextView textViewcar_time;
		TextView textViewcancel_book;
		//ListView listViewItem;
		//Button buttonStake;
	}

	@Override
	public void callback(String param) {

	}

	@Override
	public void callback2(String param, ShowEntity showEntity) {

		//	context.r
		//Toast.makeText(context, param, Toast.LENGTH_SHORT).show();
		li = NetUtil.parse_cancelinfo(param);
		if (li.status == 1&&!li.money.equals("0")) {

			//Toast.makeText(context,"取消预约成功！", Toast.LENGTH_SHORT).show();
			mDatas.remove(showEntity);
			((Activity)context).runOnUiThread(new Runnable() {
				@Override
				public void run() {
					notifyDataSetChanged();
				}
			});
			Intent intent1 = new Intent(context, PayDemoActivity.class);
			intent1.putExtra("value",li.username);
			intent1.putExtra("money",li.money);
			intent1.putExtra("flag",1);
			context.startActivity(intent1);

		} else if(li.status == 1&&li.money.equals("0")){
//Toast.makeText(context,"取消预约成功！", Toast.LENGTH_SHORT).show();
			mDatas.remove(showEntity);
			((Activity)context).runOnUiThread(new Runnable() {
				@Override
				public void run() {
					notifyDataSetChanged();
				}
			});
			//Toast.makeText(context, "取消预约失败！", Toast.LENGTH_SHORT).show();
		}
		else{
			Toast.makeText(context,"取消预约失败！", Toast.LENGTH_SHORT).show();
		}
	}

}
