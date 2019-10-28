package com.stereogarage.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import com.stereogarage.Adapter.ParentAdapt;
import com.stereogarage.Bean.Const_Util;
import com.stereogarage.Bean.OrderInfo;
import com.stereogarage.Bean.ShowEntity;
import com.stereogarage.R;
import com.stereogarage.util.NetUtil;

import java.io.IOException;
import java.util.ArrayList;

import static com.stereogarage.util.NetUtil.getOrderdata_containsurl;


public class MybookActivity extends AppCompatActivity implements  NetUtil.My_callback{


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

    private ParentAdapt mParentAdapt;
    private ArrayList<ShowEntity> mParentList;
    private ArrayList<OrderInfo> oi;
    public  String A,C;
    public  String value;
    public  Double B;
    public String order_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);
        Intent intent = getIntent();

        //从Intent当中根据key取得value
        if (intent != null) {
            value = intent.getStringExtra("user");
        }
        try {
//            responseData = null;
            getOrderdata_containsurl(Const_Util.Map_Url9,value,this);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void callback2(String param, ShowEntity showEntity) {

    }



    @Override
    public void callback(final String param) {
        initData();
        oi = NetUtil.parse_mapdatas1(param);
        for(int i=0;i<oi.size();i++){
            if(oi.get(i) == null)
                break;
            ShowEntity showEntity=new ShowEntity(oi.get(i).getAddress(),oi.get(i).getPrice_per_hour()+"元",oi.get(i).getStart_time(),oi.get(i).getOrder_id());
            mParentList.add(showEntity);
        }
        handler.sendEmptyMessage(0x02);

    }



    @Override
    protected void onResume() {

        super.onResume();

    }

    public void finishActivity(View view) {
        finish();
    }
    private void initData() {
        mParentList = new ArrayList<ShowEntity>();
        mParentList.clear();

    }

    private void initView() {
        ListView listView = (ListView)findViewById(R.id.listView1);
        mParentAdapt = new ParentAdapt(MybookActivity.this, mParentList);
        View title=View.inflate(this,R.layout.activity_mybook_title,null);
        listView.addHeaderView(title);
        listView.setAdapter(mParentAdapt);
    }

}