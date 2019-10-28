package com.stereogarage.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.stereogarage.Adapter.ParentAdapt2;
import com.stereogarage.Adapter.ParentAdapt3;
import com.stereogarage.Bean.Const_Util;
import com.stereogarage.Bean.DataInfo;
import com.stereogarage.Bean.ErrInfo;
import com.stereogarage.Bean.ShowEntity;
import com.stereogarage.Bean.ShowEntity3;
import com.stereogarage.Bean.ShowEntity4;
import com.stereogarage.R;
import com.stereogarage.util.NetUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static com.stereogarage.util.NetUtil.getgaragedata_containsurl;


public class ErrActivity extends AppCompatActivity implements  NetUtil.My_callback,AdapterView.OnItemClickListener{

    private ParentAdapt3 mParentAdapt;
    private ArrayList<ShowEntity3> mParentList;
    private ArrayList<ErrInfo> ei;
    private StringBuilder response;
    private final int what = 1;
    public String res;

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




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);
        try {
//            responseData = null;
            getgaragedata_containsurl(Const_Util.Map_Url26,this);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @Override
    protected void onResume() {

        super.onResume();
    }


    public void finishActivity(View view) {
        finish();
    }
    private void initData() {
        mParentList = new ArrayList<ShowEntity3>();
        mParentList.clear();

    }



    @Override
    public void callback2(String param, ShowEntity showEntity) {

    }

    @Override
    public void callback(final String param) {
        initData();
        ei = NetUtil.parse_mdatas3(param);
        for(int i=0;i<ei.size();i++){
            if(ei.get(i) == null)
                break;
            if(ei.get(i).getJi_ting().equals("1")&&ei.get(i).getGuang_dian().equals("1")&&ei.get(i).getRe_ji_guo_zai().equals("1")&&ei.get(i).getDuan_dian().equals("1")&&ei.get(i).getFang_song_lian().equals("1")&&ei.get(i).getJi_xian().equals("1")&&ei.get(i).getGua_gou().equals("0")&&ei.get(i).getXiang_xu().equals("1")) {

                ShowEntity3 tempEntity = new ShowEntity3(ei.get(i).getId(), "正常✅");
                mParentList.add(tempEntity);
            }
            else{

                ShowEntity3 tempEntity = new ShowEntity3(ei.get(i).getId(), "故障❌");
                mParentList.add(tempEntity);
            }

        }
        handler.sendEmptyMessage(0x02);

    }





    private void initView() {
        ListView listView = (ListView)findViewById(R.id.listView1);
        mParentAdapt = new ParentAdapt3(this, mParentList);
        listView.setAdapter(mParentAdapt);
        listView.setOnItemClickListener(this);
        View title=View.inflate(this,R.layout.activity_err_title,null);
        listView.addHeaderView(title);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String jiting1 = ei.get(position-1).getJi_ting();
        String guangdian1 = ei.get(position-1).getGuang_dian();
        String rejiguozai1 = ei.get(position-1).getRe_ji_guo_zai();
        String duandian1 = ei.get(position-1).getDuan_dian();
        String fangsonglian1 = ei.get(position-1).getFang_song_lian();
        String jixian1 = ei.get(position-1).getJi_xian();
        String guagou1 = ei.get(position-1).getGua_gou();
        String xiangxu1 = ei.get(position-1).getXiang_xu();
        Intent intent = new Intent(getApplicationContext(),WarningActivity.class);
        intent.putExtra("jiting1",jiting1);
        intent.putExtra("guangdian1",guangdian1);
        intent.putExtra("rejiguozai1",rejiguozai1);
        intent.putExtra("duandian1",duandian1);
        intent.putExtra("fangsonglian1",fangsonglian1);
        intent.putExtra("jixian1",jixian1);
        intent.putExtra("guagou1",guagou1);
        intent.putExtra("xiangxu1",xiangxu1);
        startActivity(intent);
        // Toast.makeText(this,"你点击了第" + position + "项", Toast.LENGTH_SHORT).show();
    }


}