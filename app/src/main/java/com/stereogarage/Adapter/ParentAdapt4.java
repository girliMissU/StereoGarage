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
import com.stereogarage.Bean.LoginInfo;
import com.stereogarage.Bean.ShowEntity;
import com.stereogarage.Bean.ShowEntity4;
import com.stereogarage.R;
import com.stereogarage.activity.DataActivity;
import com.stereogarage.activity.alipay.PayDemoActivity;
import com.stereogarage.util.NetUtil;

import java.io.IOException;
import java.util.List;

import static com.stereogarage.util.NetUtil.postdata_containsurl;
import static com.stereogarage.util.NetUtil.postdata_containsurl2;


public class ParentAdapt4 extends BaseObjectListAdapter implements NetUtil.My_callback3 {

	public static int mParentItem = -1;
	public LoginInfo li;
	public List<ShowEntity4> data;
	public Context context;
	public ShowEntity4 tempEntity;
	public Handler mhandle=new Handler();
	public ParentAdapt4(Context context, List<ShowEntity4> datas) {
		super(context, datas);
           this.data=datas;
           this.context=context;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder vHolder = null;
		if (convertView == null) {
			vHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.activity_data, null);
			vHolder.textViewuser = (TextView) convertView.findViewById(R.id.user);
			vHolder.textViewnum = (TextView) convertView.findViewById(R.id.num);
			vHolder.textVieworder = (TextView) convertView.findViewById(R.id.order);
			vHolder.textViewgarage = (TextView) convertView.findViewById(R.id.garage);
			vHolder.textViewplace = (TextView) convertView.findViewById(R.id.place);
			vHolder.textViewconfirm = (TextView) convertView.findViewById(R.id.confirm);
			vHolder.textViewconfirmparking = (TextView) convertView.findViewById(R.id.confirmparking);
			vHolder.textViewortime = (TextView) convertView.findViewById(R.id.ortime);
			vHolder.textViewsttime = (TextView) convertView.findViewById(R.id.sttime);
			vHolder.textViewentime = (TextView) convertView.findViewById(R.id.entime);
			vHolder.textViewtotal = (TextView) convertView.findViewById(R.id.total);
			vHolder.textViewstatus = (TextView) convertView.findViewById(R.id.status);
			vHolder.textViewiff = (TextView) convertView.findViewById(R.id.iff);
			vHolder.textViewcancel_book = (TextView) convertView.findViewById(R.id.cancel_book);
			//vHolder.listViewItem = (ListView)convertView.findViewById(R.id.listView_child);
			//vHolder.buttonStake = (Button)convertView.findViewById(R.id.button_1);
			convertView.setTag(vHolder);
		} else {
			vHolder = (ViewHolder) convertView.getTag();
		}

		tempEntity = (ShowEntity4) mDatas.get(position);
		vHolder.textViewuser.setText(tempEntity.getUser());
		vHolder.textViewnum.setText(tempEntity.getNum());
		vHolder.textVieworder.setText(tempEntity.getOrder());
		vHolder.textViewgarage.setText(tempEntity.getGarage());
		vHolder.textViewplace.setText(tempEntity.getPlace());
		vHolder.textViewconfirm.setText(tempEntity.getConfirm());
		vHolder.textViewconfirmparking.setText(tempEntity.getConfirmparking());
		vHolder.textViewortime.setText(tempEntity.getOrtime());
		vHolder.textViewsttime.setText(tempEntity.getSttime());
		vHolder.textViewentime.setText(tempEntity.getEntime());
		vHolder.textViewtotal.setText(tempEntity.getTotal());
		vHolder.textViewstatus.setText(tempEntity.getStatus());
		vHolder.textViewiff.setText(tempEntity.getIff());
		vHolder.textViewcancel_book.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				showNormalDialog("是否确认删除？");


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
					postdata_containsurl2(Const_Util.Map_Url23, tempEntity.getOrder(), ParentAdapt4.this, tempEntity);
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
		TextView textViewuser;
		TextView textViewnum;
		TextView textVieworder;
		TextView textViewgarage;
		TextView textViewplace;
		TextView textViewconfirm;
		TextView textViewconfirmparking;
		TextView textViewortime;
		TextView textViewsttime;
		TextView textViewentime;
		TextView textViewtotal;
		TextView textViewstatus;
		TextView textViewiff;
		TextView textViewcancel_book;
		//ListView listViewItem;
		//Button buttonStake;
	}



	@Override
	public void callback5(String param, ShowEntity4 showEntity4) {

		//	context.r
		//Toast.makeText(context, param, Toast.LENGTH_SHORT).show();
		li = NetUtil.parse_logininfo(param);
		if (li.status == 1) {

//			Toast.makeText(context,"删除成功！", Toast.LENGTH_SHORT).show();
//			mDatas.remove(showEntity4);
//			((Activity)context).runOnUiThread(new Runnable() {
//				@Override
//				public void run() {
//					notifyDataSetChanged();
//				}
//			});
  //      	mDatas.remove(showEntity4);
//			((Activity)context).runOnUiThread(new Runnable() {
//				@Override
//				public void run() {
//					notifyDataSetChanged();
//				}
//			});
			Intent intent1 = new Intent(context, DataActivity.class);
			context.startActivity(intent1);

		}
		else{
			Toast.makeText(context,"删除失败！", Toast.LENGTH_SHORT).show();
		}
	}

}
