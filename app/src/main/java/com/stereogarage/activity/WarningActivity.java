package com.stereogarage.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.stereogarage.Bean.Const_Util;
import com.stereogarage.Bean.LoginInfo;
import com.stereogarage.Bean.ShowEntity;
import com.stereogarage.R;
import com.stereogarage.util.NetUtil;

import java.io.IOException;

import static com.stereogarage.util.NetUtil.postchange_containsurl;


public class WarningActivity extends FragmentActivity{

    public String res;
    public LoginInfo li;
    public char[]Arr;
    public char[]B;
    public TextView jiting1,guangdian1,rejiguozai1,duandian1,fangsonglian1,jixian1,guagou1,xiangxu1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warning);
        jiting1 = (TextView) findViewById(R.id.jiting1);
        guangdian1 = (TextView) findViewById(R.id.guangdian1);
        rejiguozai1 = (TextView) findViewById(R.id.rejiguozai1);
        duandian1 = (TextView) findViewById(R.id.duandian1);
        fangsonglian1 = (TextView) findViewById(R.id.fangsonglian1);
        jixian1 = (TextView) findViewById(R.id.jixian1);
        guagou1 = (TextView) findViewById(R.id.guagou1);
        xiangxu1 = (TextView) findViewById(R.id.xiangxu1);

    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = getIntent();
        //从Intent当中根据key取得value
        if (intent != null) {
            String jiting = intent.getStringExtra("jiting1");
            String guangdian = intent.getStringExtra("guangdian1");
            String rejiguozai = intent.getStringExtra("rejiguozai1");
            String duandian = intent.getStringExtra("duandian1");
            String fangsonglian = intent.getStringExtra("fangsonglian1");
            String jixian = intent.getStringExtra("jixian1");
            String guagou = intent.getStringExtra("guagou1");
            String xiangxu = intent.getStringExtra("xiangxu1");
            if(jiting.equals("0")) {
                jiting1.setText("故障❌");
            }
            else{
                jiting1.setText("正常✅");
            }
            if(guangdian.equals("0")) {
                guangdian1.setText("故障❌");
            }
            else{
                guangdian1.setText("正常✅");
            }
            if(rejiguozai.equals("0")) {
                rejiguozai1.setText("故障❌");
            }
            else{
                rejiguozai1.setText("正常✅");
            }
            if(duandian.equals("0")) {
                duandian1.setText("故障❌");
            }
            else{
                duandian1.setText("正常✅");
            }
            if(fangsonglian.equals("0")) {
                fangsonglian1.setText("故障❌");
            }
            else{
                fangsonglian1.setText("正常✅");
            }
            if(jixian.equals("0")) {
                jixian1.setText("故障❌");
            }
            else{
                jixian1.setText("正常✅");
            }
            if(guagou.equals("1")) {
                guagou1.setText("故障❌");
            }
            else{
                guagou1.setText("正常✅");
            }
            if(xiangxu.equals("0")) {
                xiangxu1.setText("故障❌");
            }
            else{
                xiangxu1.setText("正常✅");
            }

        }
    }




    public void finishActivity(View view) {
        finish();
    }

    }


