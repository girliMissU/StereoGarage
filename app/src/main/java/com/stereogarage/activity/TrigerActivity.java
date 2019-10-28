package com.stereogarage.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import com.stereogarage.Adapter.ParentAdapt4;
import com.stereogarage.Adapter.ParentAdapt5;
import com.stereogarage.Bean.Const_Util;
import com.stereogarage.Bean.DataInfo;
import com.stereogarage.Bean.PersonInfo;
import com.stereogarage.Bean.ShowEntity;
import com.stereogarage.Bean.ShowEntity4;
import com.stereogarage.Bean.ShowEntity5;
import com.stereogarage.R;
import com.stereogarage.util.NetUtil;

import java.io.IOException;
import java.util.ArrayList;

import static com.stereogarage.util.NetUtil.getgaragedata_containsurl;


public class TrigerActivity extends AppCompatActivity{


    private ParentAdapt5 mParentAdapt;
    private ArrayList<ShowEntity5> mParentList;
    private ArrayList<PersonInfo> pi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);
        initData();
        ShowEntity5 tempEntity = new ShowEntity5("程洋","18036134694","404786970@qq.com");
        mParentList.add(tempEntity);
        initView();

    }

    @Override
    protected void onResume() {

        super.onResume();

    }




    public void finishActivity(View view) {
        finish();
    }
    private void initData() {
        mParentList = new ArrayList<ShowEntity5>();
        mParentList.clear();

    }

    private void getData(String a,String b,String c) {
        ShowEntity5 tempEntity = new ShowEntity5(a , b, c);
        mParentList.add(tempEntity);


    }

    private void initView() {
        ListView listView = (ListView)findViewById(R.id.listView1);
        mParentAdapt = new ParentAdapt5(this, mParentList);
        listView.setAdapter(mParentAdapt);
        View title=View.inflate(this,R.layout.activity_triger_title,null);
        listView.addHeaderView(title);

    }


}