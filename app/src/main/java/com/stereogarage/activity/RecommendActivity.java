package com.stereogarage.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.navi.BaiduMapNavigation;
import com.baidu.mapapi.navi.NaviParaOption;
import com.stereogarage.Bean.Const_Util;
import com.stereogarage.Bean.LoginInfo;
import com.stereogarage.Bean.MapData;
import com.stereogarage.Bean.ShowEntity;
import com.stereogarage.R;
import com.stereogarage.util.NetUtil;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.smssdk.SMSSDK;

import static com.stereogarage.util.NetUtil.postregisterdata_containsurl;
import static com.stereogarage.util.NetUtil.postregisterdata_containsurl4;


public class RecommendActivity extends AppCompatActivity implements  View.OnClickListener,NetUtil.My_callback {

    public String  value;
    private String date;
    private String time;
    private String datetime;
    private int flag;
    private  String garage_num;
    public LoginInfo LI;
    public LatLng latLngMinToCurrent,latLngMinToDest,latLngFreeMax,latLngPriceLow;
    public Double priceMinToCurrent,priceMinToDest,priceFreeMax,pricePriceLow;
    public int freeNumMinToCurrent,garageNumMinToCurrent,freeNumMinToDest,garageNumMinToDest,freeNumFreeMax,garageNumFreeMax,freeNumPriceLow,garageNumPriceLow;
    public MapData MinToCurrent,MinToDest,FreeMax,PriceLow;
    private TextView current_addr,current_addr2,current_addr3,current_addr4,current_addr5,car_time,car_time2,car_time3,car_time4,car_time5;
    private TextView car_distance,car_distance2,car_distance3,car_distance4,car_distance5,car_price,car_price2,car_price3,car_price4,car_price5;
    private TextView car_num,car_num2,car_num3,car_num4,car_num5;
    private ImageView btn_yuyue,btn_yuyue2,btn_yuyue3,btn_yuyue4,btn_yuyue5,btn_daohang,btn_daohang2,btn_daohang3,btn_daohang4,btn_daohang5;
    private double dMinToCurrent,d2MinToCurrent,dMinToDest,d2MinToDest,dFreeMax,d2FreeMax,dPriceLow,d2PriceLow,mLatitude,mLongtitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);
        Intent intent = getIntent();
        //从Intent当中根据key取得value
        if (intent != null) {
            value = intent.getStringExtra("user");
//            latLngMinToCurrent = intent.getIntExtra("latLngMinToCurrent");
//            priceMinToCurrent = intent.getDoubleExtra("priceMinToCurrent");
//            intent1.putExtra("freeNumMinToCurrent");
//            intent1.putExtra("garageNumMinToCurrent");
//            intent1.putExtra("latLngMinToDest");
//            intent1.putExtra("priceMinToDest");
//            intent1.putExtra("freeNumMinToDest");
//            intent1.putExtra("garageNumMinToDest");
//            intent1.putExtra("latLngFreeMax");
//            intent1.putExtra("priceFreeMax");
//            intent1.putExtra("freeNumFreeMax");
//            intent1.putExtra("garageNumFreeMax");
//            intent1.putExtra("latLngPriceLow");
//            intent1.putExtra("pricePriceLow");
//            intent1.putExtra("freeNumPriceLow");
//            intent1.putExtra("garageNumPriceLow");
//            MapData ZongHe = (MapData)intent.getSerializableExtra("MinToCurrent");
            MinToCurrent = (MapData)intent.getSerializableExtra("MinToCurrent");
            MinToDest = (MapData)intent.getSerializableExtra("MinToDest");
            FreeMax = (MapData)intent.getSerializableExtra("FreeMax");
            PriceLow = (MapData)intent.getSerializableExtra("PriceLow");

            dMinToCurrent = intent.getDoubleExtra("dMinToCurrent",0.0);
            d2MinToCurrent = intent.getDoubleExtra("d2MinToCurrent",0.0);
            dMinToDest = intent.getDoubleExtra("dMinToDest",0.0);
            d2MinToDest = intent.getDoubleExtra("d2MinToDest",0.0);
            dFreeMax = intent.getDoubleExtra("dFreeMax",0.0);
            d2FreeMax = intent.getDoubleExtra("d2FreeMax",0.0);
            dPriceLow = intent.getDoubleExtra("dPriceLow",0.0);
            d2PriceLow = intent.getDoubleExtra("d2PriceLow",0.0);
            mLatitude = intent.getDoubleExtra("mLatitude",0.0);
            mLongtitude = intent.getDoubleExtra("mLongtitude",0.0);
        }


        current_addr = (TextView) findViewById(R.id.current_addr);
        current_addr2 = (TextView) findViewById(R.id.current_addr2);
        current_addr3 = (TextView) findViewById(R.id.current_addr3);
        current_addr4 = (TextView) findViewById(R.id.current_addr4);
        current_addr5 = (TextView) findViewById(R.id.current_addr5);
        car_time = (TextView) findViewById(R.id.car_time);
        car_time2 = (TextView) findViewById(R.id.car_time2);
        car_time3 = (TextView) findViewById(R.id.car_time3);
        car_time4 = (TextView) findViewById(R.id.car_time4);
        car_time5 = (TextView) findViewById(R.id.car_time5);
        car_distance = (TextView) findViewById(R.id.car_distance);
        car_distance2 = (TextView) findViewById(R.id.car_distance2);
        car_distance3 = (TextView) findViewById(R.id.car_distance3);
        car_distance4 = (TextView) findViewById(R.id.car_distance4);
        car_distance5 = (TextView) findViewById(R.id.car_distance5);
        car_price = (TextView) findViewById(R.id.car_price);
        car_price2 = (TextView) findViewById(R.id.car_price2);
        car_price3 = (TextView) findViewById(R.id.car_price3);
        car_price4 = (TextView) findViewById(R.id.car_price4);
        car_price5 = (TextView) findViewById(R.id.car_price5);
        car_num = (TextView) findViewById(R.id.car_num);
        car_num2 = (TextView) findViewById(R.id.car_num2);
        car_num3 = (TextView) findViewById(R.id.car_num3);
        car_num4 = (TextView) findViewById(R.id.car_num4);
        car_num5 = (TextView) findViewById(R.id.car_num5);


        current_addr.setText(MinToCurrent.address);
        current_addr2.setText(MinToCurrent.address);
        current_addr3.setText(MinToDest.address);
        current_addr4.setText(FreeMax.address);
        current_addr5.setText(PriceLow.address);
        DecimalFormat df = new DecimalFormat("0.00");

        car_time.setText(df.format(dMinToCurrent/1000)+"千米");
        car_time2.setText(df.format(dMinToCurrent/1000)+"千米");
        car_time3.setText(df.format(dMinToDest/1000)+"千米");
        car_time4.setText(df.format(dFreeMax/1000)+"千米");
        car_time5.setText(df.format(dPriceLow/1000)+"千米");

        car_distance.setText(df.format(d2MinToCurrent/1000)+"千米");
        car_distance2.setText(df.format(d2MinToCurrent/1000)+"千米");
        car_distance3.setText(df.format(d2MinToDest/1000)+"千米");
        car_distance4.setText(df.format(d2FreeMax/1000)+"千米");
        car_distance5.setText(df.format(d2PriceLow/1000)+"千米");

        car_price.setText(MinToCurrent.price_per_hour+"元");
        car_price2.setText(MinToCurrent.price_per_hour+"元");
        car_price3.setText(MinToDest.price_per_hour+"元");
        car_price4.setText(FreeMax.price_per_hour+"元");
        car_price5.setText(PriceLow.price_per_hour+"元");

        car_num.setText(MinToCurrent.getFreenum()+"");
        car_num2.setText(MinToCurrent.getFreenum()+"");
        car_num3.setText(MinToDest.getFreenum()+"");
        car_num4.setText(FreeMax.getFreenum()+"");
        car_num5.setText(PriceLow.getFreenum()+"");

        btn_yuyue = (ImageView) findViewById(R.id.btn_yuyue);
        btn_yuyue2 = (ImageView) findViewById(R.id.btn_yuyue2);
        btn_yuyue3 = (ImageView) findViewById(R.id.btn_yuyue3);
        btn_yuyue4 = (ImageView) findViewById(R.id.btn_yuyue4);
        btn_yuyue5 = (ImageView) findViewById(R.id.btn_yuyue5);
        btn_daohang = (ImageView) findViewById(R.id.btn_daohang);
        btn_daohang2 = (ImageView) findViewById(R.id.btn_daohang2);
        btn_daohang3 = (ImageView) findViewById(R.id.btn_daohang3);
        btn_daohang4 = (ImageView) findViewById(R.id.btn_daohang4);
        btn_daohang5 = (ImageView) findViewById(R.id.btn_daohang5);

        btn_yuyue.setOnClickListener(this);
        btn_yuyue2.setOnClickListener(this);
        btn_yuyue3.setOnClickListener(this);
        btn_yuyue4.setOnClickListener(this);
        btn_yuyue5.setOnClickListener(this);
        btn_daohang.setOnClickListener(this);
        btn_daohang2.setOnClickListener(this);
        btn_daohang3.setOnClickListener(this);
        btn_daohang4.setOnClickListener(this);
        btn_daohang5.setOnClickListener(this);

    }


    @Override
    protected void onResume() {
        super.onResume();



    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_yuyue :
                flag = 1;
                showNormalDialog1("        只能预约一小时之内的车位，预约十五分钟后将开始收取车位保留费用，请勿随意预约，若确定预约，请选择预约时间！");

                break;
            case R.id.btn_yuyue2 :
                flag = 2;
                showNormalDialog1("        只能预约一小时之内的车位，预约十五分钟后将开始收取车位保留费用，请勿随意预约，若确定预约，请选择预约时间！");

                break;
            case R.id.btn_yuyue3 :
                flag = 3;
                showNormalDialog1("        只能预约一小时之内的车位，预约十五分钟后将开始收取车位保留费用，请勿随意预约，若确定预约，请选择预约时间！");

                break;
            case R.id.btn_yuyue4 :
                flag = 4;
                showNormalDialog1("        只能预约一小时之内的车位，预约十五分钟后将开始收取车位保留费用，请勿随意预约，若确定预约，请选择预约时间！");

                break;
            case R.id.btn_yuyue5 :
                flag = 5;
                showNormalDialog1("        只能预约一小时之内的车位，预约十五分钟后将开始收取车位保留费用，请勿随意预约，若确定预约，请选择预约时间！");

                break;
            case R.id.btn_daohang:
                Intent intent = null;
                        try {
                            intent = Intent.getIntent("intent://map/navi?location=" + MinToCurrent.getLatitude() + "," + MinToCurrent.getLongtitude() +
                                    "&type=TIME&src=thirdapp.navi.hndist.sydt#Intent;scheme=bdapp;" +
                                    "package=com.baidu.BaiduMap;end");
                        } catch (URISyntaxException e) {
                            e.printStackTrace();
                        }
                        if (isAvilible(this, "com.baidu.BaiduMap")) {
                            startActivity(intent);
                        } else {
                            LatLng ptMine = new LatLng(mLatitude, mLongtitude);
                            LatLng ptPosition = new LatLng(MinToCurrent.getLatitude(), MinToCurrent.getLongtitude());
                            NaviParaOption para = new NaviParaOption()
                                    .startPoint(ptMine)
                                    .endPoint(ptPosition);
                            BaiduMapNavigation.openWebBaiduMapNavi(para, getApplicationContext());
                        }


                break;
            case R.id.btn_daohang2:
                Intent intent2 = null;
                try {
                    intent2 = Intent.getIntent("intent://map/navi?location=" + MinToCurrent.getLatitude() + "," + MinToCurrent.getLongtitude() +
                            "&type=TIME&src=thirdapp.navi.hndist.sydt#Intent;scheme=bdapp;" +
                            "package=com.baidu.BaiduMap;end");
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                if (isAvilible(this, "com.baidu.BaiduMap")) {
                    startActivity(intent2);
                } else {
                    LatLng ptMine = new LatLng(mLatitude, mLongtitude);
                    LatLng ptPosition = new LatLng(MinToCurrent.getLatitude(), MinToCurrent.getLongtitude());
                    NaviParaOption para = new NaviParaOption()
                            .startPoint(ptMine)
                            .endPoint(ptPosition);
                    BaiduMapNavigation.openWebBaiduMapNavi(para, getApplicationContext());
                }
                break;
            case R.id.btn_daohang3:
                Intent intent3 = null;
                try {
                    intent3 = Intent.getIntent("intent://map/navi?location=" + MinToDest.getLatitude() + "," + MinToDest.getLongtitude() +
                            "&type=TIME&src=thirdapp.navi.hndist.sydt#Intent;scheme=bdapp;" +
                            "package=com.baidu.BaiduMap;end");
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                if (isAvilible(this, "com.baidu.BaiduMap")) {
                    startActivity(intent3);
                } else {
                    LatLng ptMine = new LatLng(mLatitude, mLongtitude);
                    LatLng ptPosition = new LatLng(MinToDest.getLatitude(), MinToDest.getLongtitude());
                    NaviParaOption para = new NaviParaOption()
                            .startPoint(ptMine)
                            .endPoint(ptPosition);
                    BaiduMapNavigation.openWebBaiduMapNavi(para, getApplicationContext());
                }
                break;
            case R.id.btn_daohang4:
                Intent intent4 = null;
                try {
                    intent4 = Intent.getIntent("intent://map/navi?location=" + FreeMax.getLatitude() + "," + FreeMax.getLongtitude() +
                            "&type=TIME&src=thirdapp.navi.hndist.sydt#Intent;scheme=bdapp;" +
                            "package=com.baidu.BaiduMap;end");
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                if (isAvilible(this, "com.baidu.BaiduMap")) {
                    startActivity(intent4);
                } else {
                    LatLng ptMine = new LatLng(mLatitude, mLongtitude);
                    LatLng ptPosition = new LatLng(FreeMax.getLatitude(), FreeMax.getLongtitude());
                    NaviParaOption para = new NaviParaOption()
                            .startPoint(ptMine)
                            .endPoint(ptPosition);
                    BaiduMapNavigation.openWebBaiduMapNavi(para, getApplicationContext());
                }
                break;
            case R.id.btn_daohang5:
                Intent intent5 = null;
                try {
                    intent5 = Intent.getIntent("intent://map/navi?location=" + PriceLow.getLatitude() + "," + PriceLow.getLongtitude() +
                            "&type=TIME&src=thirdapp.navi.hndist.sydt#Intent;scheme=bdapp;" +
                            "package=com.baidu.BaiduMap;end");
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                if (isAvilible(this, "com.baidu.BaiduMap")) {
                    startActivity(intent5);
                } else {
                    LatLng ptMine = new LatLng(mLatitude, mLongtitude);
                    LatLng ptPosition = new LatLng(PriceLow.getLatitude(), PriceLow.getLongtitude());
                    NaviParaOption para = new NaviParaOption()
                            .startPoint(ptMine)
                            .endPoint(ptPosition);
                    BaiduMapNavigation.openWebBaiduMapNavi(para, getApplicationContext());
                }
                break;

        }
    }

    private boolean isAvilible(Context context, String packageName){
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        List<String> packageNames = new ArrayList<String>();
        if(packageInfos != null){
            for(int i = 0; i < packageInfos.size(); i++){
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        return packageNames.contains(packageName);
    }

    private void   showNormalDialog1(String msg){

        final AlertDialog.Builder normalDialog = new AlertDialog.Builder(RecommendActivity.this);
        normalDialog.setMessage(msg);

        normalDialog.setPositiveButton( "确定", new  DialogInterface.OnClickListener() {
            @Override
            public   void   onClick(DialogInterface dialog, int   which) {
                initdatepicker1();
            }
        });
        normalDialog.setNegativeButton("取消", new  DialogInterface.OnClickListener() {
            @Override
            public   void   onClick(DialogInterface dialog, int   which) {
            }
        });
        normalDialog.show();
    }

    public void initdatepicker1() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int monthOfYear = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dpd = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                inittimepicker1();
                date = String.format("%d-%02d-%02d", year, monthOfYear + 1, dayOfMonth);
            }
        }, year, monthOfYear, dayOfMonth);
        dpd.getDatePicker().setMinDate(new Date().getTime());
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DAY_OF_MONTH, 0); //第2天，第x天，照加。如果是负数，表示前n天。
        Date Max = cal.getTime();
        dpd.getDatePicker().setMaxDate(Max.getTime());
        dpd.show();

    }

    public void inittimepicker1(){
        Calendar calendar = Calendar.getInstance();
        final int hour = calendar.get(Calendar.HOUR_OF_DAY);
        final int min = calendar.get(Calendar.MINUTE);
        final TimePickerDialog tpd =new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                if ((hourOfDay == hour && minute > min) || (hourOfDay - hour == 1 && minute <= min)) {
                    time = String.format("%02d:%02d", hourOfDay, minute);
                    datetime = date + " " + time;
                    //setMinAndMaxHour(hour,hour+1,view);
                    if(flag == 1)
                        garage_num = MinToCurrent.getId()+"";
                    if(flag == 2)
                        garage_num = MinToCurrent.getId()+"";
                    if(flag == 3)
                        garage_num = MinToDest.getId()+"";
                    if(flag == 4)
                        garage_num = FreeMax.getId()+"";
                    if(flag == 5)
                        garage_num = PriceLow.getId()+"";
                    try {
                        postregisterdata_containsurl4(Const_Util.Map_Url6, value, garage_num, datetime, RecommendActivity.this);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }


                else
                {
                    Toast.makeText(getApplicationContext(), "只能预约一小时之内的车位，请重新预约！", Toast.LENGTH_SHORT).show();
                }
            }
        },hour,min,true);

        tpd.show();
    }

    @Override
    public void callback(final String param) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LI = NetUtil.parse_logininfo(param);
                if (LI.status == 1) {
                    showNormalDialog2("        您已成功预约时间为" + datetime +"的车位！可在我的预约里查看详细信息！");
                }
                else Toast.makeText(getApplicationContext(), "预约失败！", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void   showNormalDialog2(String msg){

        final AlertDialog.Builder normalDialog = new AlertDialog.Builder(RecommendActivity.this);
        normalDialog.setMessage(msg);

        normalDialog.setPositiveButton( "确定", new  DialogInterface.OnClickListener() {
            @Override
            public   void   onClick(DialogInterface dialog, int   which) {
            }
        });
        normalDialog.show();
    }

    @Override
    public void callback2(String param, ShowEntity showEntity) {

    }

    public void finishActivity(View view) {
        finish();
    }
}