package com.stereogarage.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.stereogarage.Bean.Const_Util;
import com.stereogarage.Bean.LoginInfo;
import com.stereogarage.Bean.ParkingInfo;
import com.stereogarage.Bean.ParkingInfo2;
import com.stereogarage.Bean.ShowEntity;
import com.stereogarage.R;
import com.stereogarage.activity.alipay.PayDemoActivity;
import com.stereogarage.callback.AllInterface;
import com.stereogarage.util.NetUtil;

import java.io.IOException;

import static com.stereogarage.util.NetUtil.getOrderdata_containsurl;
import static com.stereogarage.util.NetUtil.post_containsurl;


public class OrderActivity extends AppCompatActivity  implements View.OnClickListener,NetUtil.My_callback,AllInterface.Cb{

    public TextView finish_parking;
    public TextView current_addr,car_price,car_time;
    public LinearLayout book_layout;
    public  String value;
    public ParkingInfo pi;
    public LoginInfo PI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        book_layout  = (LinearLayout) findViewById(R.id.book_layout);
        finish_parking = (TextView) findViewById(R.id.finish_parking);
        current_addr = (TextView) findViewById(R.id.current_addr);
        car_price = (TextView) findViewById(R.id.car_price);
        car_time = (TextView) findViewById(R.id.car_time);
        finish_parking.setOnClickListener(this);
        Intent intent = getIntent();
        //从Intent当中根据key取得value
        if (intent != null) {
                value = intent.getStringExtra("user");
            }


        }

    @Override
    protected void onResume() {
        super.onResume();
        try {
//            responseData = null;
            getOrderdata_containsurl(Const_Util.Map_Url13,value,this);
        } catch (IOException e) {
            e.printStackTrace();
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

                pi = NetUtil.parse_parkinginfo(param);

                    if (pi.status == 1) {
                        if(pi.confirm_out ==1) {
                            book_layout.setVisibility(View.GONE);
                        }
                        else {
                            book_layout.setVisibility(View.VISIBLE);
                            current_addr.setText(pi.getAddress() + "");
                            car_price.setText(pi.getPrice_per_hour() + "元");
                            car_time.setText(pi.getStart_time() + "");
                        }
                    } else
                        book_layout.setVisibility(View.GONE);

            }
        });
    }


    public void callback3(final String param) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                PI = NetUtil.parse_logininfo(param);
                if (PI.status==1) {
                    Intent intent1 = new Intent(OrderActivity.this, OrderActivity1.class);
                    intent1.putExtra("value",value);
                    //intent1.putExtra("flag",0);
                    startActivity(intent1);
                } else
                    Toast.makeText(getApplicationContext(), "停车失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.finish_parking:
                try {
//                  responseData = null;
                    post_containsurl(Const_Util.Map_Url21, value,this);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;
        }
    }

    public void finishActivity(View view) {
        finish();
    }
}