package com.stereogarage.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import com.stereogarage.Adapter.ParentAdapt2;
import com.stereogarage.Adapter.ParentAdapt3;
import com.stereogarage.Adapter.ParentAdapt4;
import com.stereogarage.Bean.Const_Util;
import com.stereogarage.Bean.DataInfo;
import com.stereogarage.Bean.GarageInfo;
import com.stereogarage.Bean.ShowEntity;
import com.stereogarage.Bean.ShowEntity2;
import com.stereogarage.Bean.ShowEntity3;
import com.stereogarage.Bean.ShowEntity4;
import com.stereogarage.R;
import com.stereogarage.util.NetUtil;

import java.io.IOException;
import java.util.ArrayList;

import static com.stereogarage.util.NetUtil.getgaragedata_containsurl;


public class DataActivity extends AppCompatActivity implements  NetUtil.My_callback{

    Handler handler =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case 0x02:
                    initView();
                    break;
            }
        }
    };

    private ParentAdapt4 mParentAdapt;
    private ArrayList<ShowEntity4> mParentList;
    private ArrayList<DataInfo> di;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);

        try {
//            responseData = null;
            getgaragedata_containsurl(Const_Util.Map_Url22,this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {

        super.onResume();

    }



    @Override
    public void callback2(String param, ShowEntity showEntity) {

    }

    @Override
    public void callback(final String param) {
        initData();
        di = NetUtil.parse_mdatas2(param);
        for(int i=0;i<di.size();i++){
            if(di.get(i) == null)
                break;
            ShowEntity4 showEntity4=new ShowEntity4(di.get(i).getUsername(),di.get(i).getCar_num(),di.get(i).getParking_id(),di.get(i).getGarage_num(),di.get(i).getCp_num(),di.get(i).getFinish_parking(),di.get(i).getConfirm_parking(),di.get(i).getOrder_time(),di.get(i).getStart_time(),di.get(i).getLeave_time(),di.get(i).getMoney(),di.get(i).getPay_status(),di.get(i).getConfirm_out());
            mParentList.add(showEntity4);
        }
        handler.sendEmptyMessage(0x02);

    }
    public void finishActivity(View view) {
        finish();
    }
    private void initData() {
        mParentList = new ArrayList<ShowEntity4>();
        mParentList.clear();

    }

//    private void getData(String a,String b,String c) {
//        ShowEntity2 tempEntity = new ShowEntity2(a , b, c);
//        mParentList.add(tempEntity);
//
//
//    }

    private void initView() {
        ListView listView = (ListView)findViewById(R.id.listView1);
        mParentAdapt = new ParentAdapt4(this, mParentList);
        listView.setAdapter(mParentAdapt);
        View title=View.inflate(this,R.layout.activity_data_title,null);
        listView.addHeaderView(title);
    }


}