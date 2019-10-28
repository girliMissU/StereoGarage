package com.stereogarage.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.stereogarage.Bean.ShowEntity2;
import com.stereogarage.R;

import java.util.List;


public class ParentAdapt2 extends BaseObjectListAdapter  {

	public static int mParentItem = -1;

	public ParentAdapt2(Context context, List<ShowEntity2> datas) {
		super(context, datas);

	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder vHolder = null;
		if (convertView == null) {
			vHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.activity_num, null);
			vHolder.textViewid = (TextView) convertView.findViewById(R.id.id);
			vHolder.textViewtotal_number = (TextView) convertView.findViewById(R.id.total_number);
			vHolder.textViewrest_number = (TextView) convertView.findViewById(R.id.rest_number);
			//vHolder.textViewcancel_book = (TextView) convertView.findViewById(R.id.cancel_book);
			//vHolder.listViewItem = (ListView)convertView.findViewById(R.id.listView_child);
			//vHolder.buttonStake = (Button)convertView.findViewById(R.id.button_1);
			convertView.setTag(vHolder);
		} else {
			vHolder = (ViewHolder) convertView.getTag();
		}

		ShowEntity2 tempEntity = (ShowEntity2) mDatas.get(position);
		vHolder.textViewid.setText(tempEntity.getid());
		vHolder.textViewtotal_number.setText(tempEntity.gettotal_number());
		vHolder.textViewrest_number.setText(tempEntity.getrest_number());
		//vHolder.textViewcancel_book.setOnClickListener(new View.OnClickListener() {

		//vHolder.buttonStake.setTag(position);

		return convertView;
	}

	class ViewHolder {
		TextView textViewid;
		TextView textViewtotal_number;
		TextView textViewrest_number;

		//ListView listViewItem;
		//Button buttonStake;
	}





}
