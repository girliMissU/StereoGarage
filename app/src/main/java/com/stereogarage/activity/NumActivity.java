package com.stereogarage.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.stereogarage.Adapter.ParentAdapt2;
import com.stereogarage.Bean.Const_Util;
import com.stereogarage.Bean.GarageInfo;
import com.stereogarage.Bean.ShowEntity;
import com.stereogarage.Bean.ShowEntity2;
import com.stereogarage.R;
import com.stereogarage.util.NetUtil;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static com.stereogarage.util.NetUtil.getgaragedata_containsurl;


public class NumActivity extends AppCompatActivity implements NetUtil.My_callback,AdapterView.OnItemClickListener{

    private ParentAdapt2 mParentAdapt;
    private ArrayList<ShowEntity2> mParentList;
    private ArrayList<GarageInfo> gi;
    private StringBuilder response;
    private final int what = 1;
    public String res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);
        try {
//            responseData = null;
            getgaragedata_containsurl(Const_Util.Map_Url15,this);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onResume() {

        super.onResume();

    }

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

    private Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case what:
                    res = response.toString();
                    Intent intent = new Intent(getApplicationContext(),RestActivity.class);
                    intent.putExtra("res",res);
                    startActivity(intent);

//                    char[]stringArr = res.toCharArray();
//                    char b= stringArr[1];
                   //Toast.makeText(this,A, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    public void callback2(String param, ShowEntity showEntity) {

    }

    @Override
    public void callback(final String param) {
        initData();
        gi = NetUtil.parse_mdatas(param);
        for(int i=0;i<gi.size();i++){
            if(gi.get(i) == null)
                break;
            ShowEntity2 showEntity2=new ShowEntity2(gi.get(i).getId(),gi.get(i).getTotal_number()+"个",gi.get(i).getRest_number()+"个");
            mParentList.add(showEntity2);
        }
        handler.sendEmptyMessage(0x02);

    }
    public void finishActivity(View view) {
        finish();
    }
        private void initData() {
            mParentList = new ArrayList<ShowEntity2>();
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
        mParentAdapt = new ParentAdapt2(this, mParentList);
        listView.setAdapter(mParentAdapt);
        listView.setOnItemClickListener(this);
        View title=View.inflate(this,R.layout.activity_num_title,null);
        listView.addHeaderView(title);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        sendHttpRequest(Const_Util.Map_Url24);

       // Toast.makeText(this,"你点击了第" + position + "项", Toast.LENGTH_SHORT).show();
    }

    public void sendHttpRequest(final String address) {
        new Thread(new Runnable() {
            public void run() {
                HttpURLConnection connection = null;
                try {
                    URL url = new URL(address);
                    connection = (HttpURLConnection) url.openConnection();
                    // 请求方式
                    connection.setRequestMethod("POST");
                    // 连接超时
                    connection.setConnectTimeout(8000);
                    // 读取超时
                    connection.setReadTimeout(8000);
                    connection.connect();
                    // 获取输入流
                    InputStream in = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(in, "UTF-8"));
                    response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    Message message = new Message();
                    message.what = 1;
                    myHandler.sendMessage(message);
                } catch (Exception e) {

                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }

}