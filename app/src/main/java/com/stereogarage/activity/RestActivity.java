package com.stereogarage.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.stereogarage.Bean.Const_Util;
import com.stereogarage.Bean.LoginInfo;
import com.stereogarage.Bean.ShowEntity;
import com.stereogarage.R;
import com.stereogarage.util.NetUtil;

import java.io.IOException;

import static com.stereogarage.util.NetUtil.post_containsurl;
import static com.stereogarage.util.NetUtil.postchange_containsurl;


public class RestActivity extends FragmentActivity implements View.OnClickListener,NetUtil.My_callback{

    public String res;
    public LoginInfo li;
    public char[]Arr;
    public char[]B;
    public TextView change_btn;
    public TextView num101,num102,num201,num202,num301,num302,num401,num402,num501,num502,num601,num602,num603;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest);
        change_btn = (TextView) findViewById(R.id.cpchange);
        num101 = (TextView) findViewById(R.id.num101);
        num102 = (TextView) findViewById(R.id.num102);
        num201 = (TextView) findViewById(R.id.num201);
        num202 = (TextView) findViewById(R.id.num202);
        num301 = (TextView) findViewById(R.id.num301);
        num302 = (TextView) findViewById(R.id.num302);
        num401 = (TextView) findViewById(R.id.num401);
        num402 = (TextView) findViewById(R.id.num402);
        num501 = (TextView) findViewById(R.id.num501);
        num502 = (TextView) findViewById(R.id.num502);
        num601 = (TextView) findViewById(R.id.num601);
        num602 = (TextView) findViewById(R.id.num602);
        num603 = (TextView) findViewById(R.id.num603);
        change_btn.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = getIntent();
        //从Intent当中根据key取得value
        if (intent != null) {
            res = intent.getStringExtra("res");
            Arr = res.toCharArray();

            num101.setText(Arr[1]+"");
            num102.setText(Arr[2]+"");
            num201.setText(Arr[3]+"");
            num202.setText(Arr[4]+"");
            num301.setText(Arr[5]+"");
            num302.setText(Arr[6]+"");
            num401.setText(Arr[7]+"");
            num402.setText(Arr[8]+"");
            num501.setText(Arr[9]+"");
            num502.setText(Arr[10]+"");
            num601.setText(Arr[11]+"");
            num602.setText(Arr[12]+"");
            num603.setText(Arr[13]+"");
        }
    }

    @Override
    public void callback2(String param, ShowEntity showEntity) {

    }


    @Override
    public void callback(final String param) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                li = NetUtil.parse_logininfo(param);
              if(li.status==1)
              {

                  Toast.makeText(getApplicationContext(),"修改成功", Toast.LENGTH_SHORT).show();
              }
            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cpchange:

             String c = num101.getText().toString()+num102.getText().toString()+num201.getText().toString()+num202.getText().toString()+num301.getText().toString()+num302.getText().toString()+num401.getText().toString()+num402.getText().toString()+num501.getText().toString()+num502.getText().toString()+num601.getText().toString()+num602.getText().toString()+num603.getText().toString();
                try {
//                  responseData = null;
                    postchange_containsurl(Const_Util.Map_Url25, c,this);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //  Intent intent = new Intent(getApplicationContext(),ChangeActivity2.class);
//                intent.putExtra("user",value);
//                startActivity(intent);
                break;
        }
    }
    public void finishActivity(View view) {
        finish();
    }

    }


