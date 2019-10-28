package com.stereogarage.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.stereogarage.Bean.ShowEntity3;
import com.stereogarage.R;

import java.util.List;


public class ParentAdapt3 extends BaseObjectListAdapter  {

	public static int mParentItem = -1;

	public ParentAdapt3(Context context, List<ShowEntity3> datas) {
		super(context, datas);

	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder vHolder = null;
		if (convertView == null) {
			vHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.activity_err, null);
			vHolder.textViewnum = (TextView) convertView.findViewById(R.id.num);
			vHolder.textViewmachine = (TextView) convertView.findViewById(R.id.machine);
//			vHolder.textViewmachine22 = (TextView) convertView.findViewById(R.id.machine22);
//			vHolder.textViewmachine32 = (TextView) convertView.findViewById(R.id.machine32);
			//vHolder.textViewcancel_book = (TextView) convertView.findViewById(R.id.cancel_book);
			//vHolder.listViewItem = (ListView)convertView.findViewById(R.id.listView_child);
			//vHolder.buttonStake = (Button)convertView.findViewById(R.id.button_1);
			convertView.setTag(vHolder);
		} else {
			vHolder = (ViewHolder) convertView.getTag();
		}

		ShowEntity3 tempEntity = (ShowEntity3) mDatas.get(position);
		vHolder.textViewnum.setText(tempEntity.getnum());
		vHolder.textViewmachine.setText(tempEntity.getmachine());
//		vHolder.textViewmachine22.setText(tempEntity.getmachine22());
//		vHolder.textViewmachine32.setText(tempEntity.getmachine32());
		//vHolder.textViewcancel_book.setOnClickListener(new View.OnClickListener() {

		//vHolder.buttonStake.setTag(position);

		return convertView;
	}

	class ViewHolder {
		TextView textViewnum;
		TextView textViewmachine;
//		TextView textViewmachine22;
//		TextView textViewmachine32;

		//ListView listViewItem;
		//Button buttonStake;
	}




}
