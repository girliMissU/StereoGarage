package com.stereogarage.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.stereogarage.Bean.ShowEntity5;
import com.stereogarage.R;

import java.util.List;


public class ParentAdapt5 extends BaseObjectListAdapter  {

	public static int mParentItem = -1;

	public ParentAdapt5(Context context, List<ShowEntity5> datas) {
		super(context, datas);

	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder vHolder = null;
		if (convertView == null) {
			vHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.activity_triger, null);
			vHolder.textViewuser = (TextView) convertView.findViewById(R.id.user);
			vHolder.textViewnum = (TextView) convertView.findViewById(R.id.num);
			vHolder.textViewmail = (TextView) convertView.findViewById(R.id.mail);
//			vHolder.textViewmachine32 = (TextView) convertView.findViewById(R.id.machine32);
			//vHolder.textViewcancel_book = (TextView) convertView.findViewById(R.id.cancel_book);
			//vHolder.listViewItem = (ListView)convertView.findViewById(R.id.listView_child);
			//vHolder.buttonStake = (Button)convertView.findViewById(R.id.button_1);
			convertView.setTag(vHolder);
		} else {
			vHolder = (ViewHolder) convertView.getTag();
		}

		ShowEntity5 tempEntity = (ShowEntity5) mDatas.get(position);
		vHolder.textViewuser.setText(tempEntity.getUser());
		vHolder.textViewnum.setText(tempEntity.getNum());
		vHolder.textViewmail.setText(tempEntity.getMail());
//		vHolder.textViewmachine22.setText(tempEntity.getmachine22());
//		vHolder.textViewmachine32.setText(tempEntity.getmachine32());
		//vHolder.textViewcancel_book.setOnClickListener(new View.OnClickListener() {

		//vHolder.buttonStake.setTag(position);

		return convertView;
	}

	class ViewHolder {
		TextView textViewuser;
		TextView textViewnum;
		TextView textViewmail;
//		TextView textViewmachine32;

		//ListView listViewItem;
		//Button buttonStake;
	}




}
