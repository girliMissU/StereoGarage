package com.stereogarage.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.navi.BaiduMapNavigation;
import com.baidu.mapapi.navi.NaviParaOption;
import com.baidu.mapapi.utils.DistanceUtil;
import com.stereogarage.Bean.Const_Util;
import com.stereogarage.Bean.LateInfo;
import com.stereogarage.Bean.LoginInfo;
import com.stereogarage.Bean.MapData;
import com.stereogarage.Bean.ParkingInfo;
import com.stereogarage.Bean.ShowEntity;
import com.stereogarage.R;
import com.stereogarage.activity.MyOrientationListener.OnOrientationListener;
import com.stereogarage.activity.alipay.PayDemoActivity;
import com.stereogarage.callback.AllInterface;
import com.stereogarage.custom.LeftDrawerLayout;
import com.stereogarage.fragment.LeftMenuFragment;
import com.stereogarage.util.NetUtil;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URISyntaxException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.stereogarage.util.NetUtil.getOrderdata_containsurl;
import static com.stereogarage.util.NetUtil.getOrderdata_containsurl2;
import static com.stereogarage.util.NetUtil.getdata_containsurl;
import static com.stereogarage.util.NetUtil.post_containsurl;
import static com.stereogarage.util.NetUtil.postregisterdata_containsurl4;
import static com.stereogarage.util.NetUtil.responsedata;


public class MainActivity extends FragmentActivity implements View.OnClickListener,AllInterface.OnMenuSlideListener,NetUtil.My_callback,AllInterface.Cb,AllInterface.Cb2{

    private MapView mapView;
    private BaiduMap baiduMap;
    private ImageView btn_locale,btn_refresh;
    private ImageView menu_icon;
    public LocationClient mLocationClient;
    private MyLocationListener mLocationListener;
    private boolean isFirstIn = true;
    private Context context;
    private double mLatitude;
    private double mLongtitude;

    private BitmapDescriptor mIconLocation;
    private MyOrientationListener myOrientationListener;
    private float mCurrentX;
    private LocationMode mLocationMode;

    private BitmapDescriptor mMarker;
    public static TextView current_addr;
    private LinearLayout nearest_icon,title_layout, car_layout, car_distance_layout, info_layout,confirm_book_layout,text_layout, confirm_cancel_layout,layout,button_layout;
    private TextView text1,text2,title,user_name1,book_countdown, textview_time, textview_distance, textview_price, textview_num,book_bt;
    public static TextView car_distance, car_time, car_price, car_num, navi_bt;

    private ArrayList<MapData> md;

    LeftDrawerLayout mLeftDrawerLayout;
    View shadowView;

    public LeftMenuFragment mMenuFragment;
    private String date;
    private String time;
    private String datetime;
    public String  value;
    public String money;
    private String garage_num;
    public LoginInfo LI;
    public LateInfo li;
    public ParkingInfo pi;
    private double dis;
    private LatLng nearestlatLng;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(new MyLocationListener());
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.main_content);
        this.context = this;
        initView();
        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissionList.isEmpty()) {
            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(MainActivity.this, permissions, 1);
        } else {
            initLocation();
            mLocationClient.start();
        }

        initMarker();
        getdata();
        addOverlays(md);
       // showinfowindow(md);

        FragmentManager fm = getSupportFragmentManager();
        mMenuFragment = (LeftMenuFragment) fm.findFragmentById(R.id.id_container_menu);
        mLeftDrawerLayout.setOnMenuSlideListener(this);

        if (mMenuFragment == null) {
            mMenuFragment = new LeftMenuFragment();
            fm.beginTransaction().add(R.id.id_container_menu, mMenuFragment).commit();
        }

//        SharedPreferences sp = getSharedPreferences("rememberpassword", Context.MODE_WORLD_READABLE);
//        value= sp.getString("name", "");
//


        baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Bundle extraInfo = marker.getExtraInfo();
                final MapData info = (MapData) extraInfo.getSerializable("info");
                garage_num =String.valueOf(info.getId());
                car_num.setText(info.getFreenum() + "");
                car_price.setText(info.getPrice_per_hour() + "元");
                current_addr.setText(info.getAddress());
                LatLng ptMine = new LatLng(mLatitude, mLongtitude);
                LatLng ptPosition = new LatLng(info.getLatitude(), info.getLongtitude());
                dis = DistanceUtil.getDistance(ptMine,ptPosition);
                if (dis < 500) {
                    String temp =  String.format("%.2f",(dis))+"米";
                    car_distance.setText(temp);
                    String temp1 = String.format("%d",(((int)dis+1000)/500))+"分钟";
                    car_time.setText(temp1);
                }
                else if(dis<1000)
                {
                    String temp =  String.format("%.2f",(dis))+"米";
                    car_distance.setText(temp);
                    String temp1 =String.format("%d",((int)dis/500))+"分钟";
                    car_time.setText(temp1);
                }
                else if(dis < 30000){
                    String temp =  String.format("%.2f",(dis)/1000)+"千米";
                    car_distance.setText(temp);
                    String temp1 = String.format("%d",((int)dis/500))+"分钟";
                    car_time.setText(temp1);
                } else{
                    String temp =  String.format("%.2f",(dis)/1000)+"千米";
                    car_distance.setText(temp);
                    String temp1 = String.format("%d",((int)dis/60000))+"小时";
                    car_time.setText(temp1);
                }
                car_layout.setVisibility(View.VISIBLE);
                button_layout.setVisibility(View.VISIBLE);

                navi_bt = (TextView) findViewById(R.id.navi_bt);
                navi_bt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = null;
                        try {
                            intent = Intent.getIntent("intent://map/navi?location=" + info.getLatitude() + "," + info.getLongtitude() +
                                    "&type=TIME&src=thirdapp.navi.hndist.sydt#Intent;scheme=bdapp;" +
                                    "package=com.baidu.BaiduMap;end");
                        } catch (URISyntaxException e) {
                            e.printStackTrace();
                        }
                        if (isAvilible(context,"com.baidu.BaiduMap")) {
                            startActivity(intent);
                        } else {
                            LatLng ptMine = new LatLng(mLatitude, mLongtitude);
                            LatLng ptPosition = new LatLng(info.getLatitude(), info.getLongtitude());
                            NaviParaOption para = new NaviParaOption()
                                    .startPoint(ptMine)
                                    .endPoint(ptPosition);
                            BaiduMapNavigation.openWebBaiduMapNavi(para, getApplicationContext());
                        }
                    }
                });
                return true;
            }
        });

        baiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {

            @Override
            public boolean onMapPoiClick(MapPoi arg0) {
                return false;
            }

            @Override
            public void onMapClick(LatLng arg0) {
                car_layout.setVisibility(View.GONE);
                // baiduMap.hideInfoWindow();
            }
        });

    }

    public void getdata() {
        getdata_containsurl(Const_Util.Map_Url);
        while (true) {
            if (responsedata != "" && responsedata != null) {
                //Toast.makeText(getApplicationContext(),responsedata,Toast.LENGTH_SHORT).show();
                md = NetUtil.parse_mapdatas(responsedata);
                break;
            }
        }
    }
    private void initView() {
        mapView = (MapView) findViewById(R.id.bmapView);
        btn_locale = (ImageView) findViewById(R.id.btn_locale);
        btn_refresh = (ImageView) findViewById(R.id.btn_refresh);
        current_addr = (TextView) findViewById(R.id.current_addr);
        menu_icon = (ImageView) findViewById(R.id.menu_icon);
        nearest_icon = (LinearLayout) findViewById(R.id.nearest_icon);
        menu_icon.setVisibility(View.VISIBLE);
        nearest_icon.setVisibility(View.VISIBLE);
        title_layout = (LinearLayout) findViewById(R.id.title_layout);
        title_layout.setVisibility(View.VISIBLE);
        shadowView = (View) findViewById(R.id.shadow);
        car_distance_layout = (LinearLayout) findViewById(R.id.car_distance_layout);
        info_layout = (LinearLayout) findViewById(R.id.info_layout);
        //confirm_cancel_layout = (LinearLayout) findViewById(R.id.confirm_cancel_layout);
        title = (TextView) findViewById(R.id.title);
        //book_countdown = (TextView) findViewById(R.id.book_countdown);
        textview_time = (TextView) findViewById(R.id.textview_time);
        textview_distance = (TextView) findViewById(R.id.textview_distance);
        textview_price = (TextView) findViewById(R.id.textview_price);
        textview_num = (TextView) findViewById(R.id.textview_num);
        car_distance = (TextView) findViewById(R.id.car_distance);
        car_time = (TextView) findViewById(R.id.car_time);
        car_num = (TextView) findViewById(R.id.car_num);
        car_price = (TextView) findViewById(R.id.car_price);
        mLeftDrawerLayout = (LeftDrawerLayout) findViewById(R.id.id_drawerlayout);
        book_bt = (TextView) findViewById(R.id.book_bt);
        button_layout=(LinearLayout) findViewById(R.id.button_layout);

       // text1=(TextView) findViewById(R.id.text1);
        text2=(TextView) findViewById(R.id.text2);

        book_bt.setOnClickListener(this);
        btn_locale.setOnClickListener(this);
        btn_refresh.setOnClickListener(this);
        menu_icon.setOnClickListener(this);
        nearest_icon.setOnClickListener(this);
        shadowView.setOnClickListener(this);


        baiduMap = mapView.getMap();
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(18.0f);
        baiduMap.setMapStatus(msu);


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
                    try {
                        postregisterdata_containsurl4(Const_Util.Map_Url6, value, garage_num, datetime, MainActivity.this);
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

    public void callback3(final String param) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                li = NetUtil.parse_lateinfo(param);

                if (li.status==1) {
//                    String address = PI.address;
//                    Double price = PI.price_per_hour;
//                    String start_time = PI.start_time;
//                    String leave_time = PI.leave_time;
                      money = li.money;
//                    Intent intent1 = new Intent(OrderActivity.this, PayDemoActivity.class);
//                    intent1.putExtra("address",address);
//                    intent1.putExtra("price",price+"");
//                    intent1.putExtra("start_time",start_time);
//                    intent1.putExtra("leave_time",leave_time);
//                    intent1.putExtra("money",money);
//                    intent1.putExtra("value",value);
//                    startActivity(intent1);
                    showNormalDialog("        您有一个超时或未支付订单，点击确定进入付费！");
                }
//                else
//                    Toast.makeText(getApplicationContext(), "结束停车失败", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void   showNormalDialog(String msg){

        final AlertDialog.Builder normalDialog = new AlertDialog.Builder(MainActivity.this);
        normalDialog.setMessage(msg);

        normalDialog.setPositiveButton( "确定", new  DialogInterface.OnClickListener() {
            @Override
            public   void   onClick(DialogInterface dialog, int   which) {
                Intent intent1 = new Intent(getApplicationContext(), PayDemoActivity.class);
                intent1.putExtra("value",value);
                intent1.putExtra("money",money);
                intent1.putExtra("flag",1);
                startActivity(intent1);
            }
        });

        normalDialog.setCancelable(false);
        normalDialog.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_locale:
                getMyLocation();
                break;
            case R.id.btn_refresh:
                initMarker();
                getdata();
                addOverlays(md);
               // showinfowindow(md);
                getMyLocation();
                Toast.makeText(getApplicationContext(), "刷新成功！", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nearest_icon:
                findNearest(md);
                break;
            case R.id.menu_icon:
                openMenu();
                break;
            case R.id.shadow:
                closeMenu();
                break;
            case R.id.book_bt:
                car_layout.setVisibility(View.GONE);
                button_layout.setVisibility(View.GONE);
                showNormalDialog1("        只能预约一小时之内的车位，预约十五分钟后将开始收取车位保留费用，请勿随意预约，若确定预约，请选择预约时间！");
                break;
        }
    }

    private void   showNormalDialog1(String msg){

        final AlertDialog.Builder normalDialog = new AlertDialog.Builder(MainActivity.this);
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
                car_layout.setVisibility(View.VISIBLE);
                button_layout.setVisibility(View.VISIBLE);
            }
        });
        normalDialog.show();
    }

    private void   showNormalDialog2(String msg){

        final AlertDialog.Builder normalDialog = new AlertDialog.Builder(MainActivity.this);
        normalDialog.setMessage(msg);

        normalDialog.setPositiveButton( "确定", new  DialogInterface.OnClickListener() {
            @Override
            public   void   onClick(DialogInterface dialog, int   which) {
            }
        });
        normalDialog.show();
    }


    public void openMenu() {
        mLeftDrawerLayout.openDrawer();
        shadowView.setVisibility(View.VISIBLE);
    }

    public void closeMenu() {
        mLeftDrawerLayout.closeDrawer();
        shadowView.setVisibility(View.GONE);
    }

    @Override
    public void onMenuSlide(float offset) {
        shadowView.setVisibility(offset == 0 ? View.INVISIBLE : View.VISIBLE);
        int alpha = (int) Math.round(offset * 255 * 0.4);
        String hex = Integer.toHexString(alpha).toUpperCase();
        shadowView.setBackgroundColor(Color.argb(alpha, 0, 0, 0));
    }

    public void getMyLocation() {
        LatLng latLng = new LatLng(mLatitude, mLongtitude);
        MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);
        baiduMap.animateMapStatus(msu);
    }

    private void initLocation() {
        mLocationMode = LocationMode.NORMAL;
        mLocationClient = new LocationClient(this);
        mLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(mLocationListener);

        LocationClientOption option = new LocationClientOption();
        option.setCoorType("bd09ll");
        option.setIsNeedAddress(true);
        option.setOpenGps(true);
        option.setScanSpan(1000);
        mLocationClient.setLocOption(option);
        mIconLocation = BitmapDescriptorFactory.fromResource(R.mipmap.navi_map_gps_locked);
        myOrientationListener = new MyOrientationListener(context);

        myOrientationListener.setOnOrientationListener(new OnOrientationListener() {
            @Override
            public void onOrientationChanged(float x) {
                mCurrentX = x;
            }
        });
    }

    private void initMarker() {
        mMarker = BitmapDescriptorFactory.fromResource(R.mipmap.maker);
        car_layout = (LinearLayout) findViewById(R.id.car_layout);
    }

    private void addOverlays(List<MapData> infos)
    {
        baiduMap.clear();
        LatLng latLng = null;
        Marker marker = null;
        OverlayOptions options;

        for (MapData info : infos)
        {
            latLng = new LatLng(info.getLatitude(), info.getLongtitude());
           // nearestlatLng = new LatLng(info.getLatitude(), info.getLongtitude());
            options = new MarkerOptions().position(latLng).icon(mMarker).zIndex(5);
            marker = (Marker) baiduMap.addOverlay(options);
            Bundle arg0 = new Bundle();
            arg0.putSerializable("info", info);
            marker.setExtraInfo(arg0);
        }

        MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);
        baiduMap.setMapStatus(msu);
    }


    private void findNearest(List<MapData> infos) {

        nearestlatLng = new LatLng(infos.get(0).getLatitude(), infos.get(0).getLongtitude());
        LatLng ptMine = new LatLng(mLatitude, mLongtitude);
        double min = DistanceUtil.getDistance(ptMine, nearestlatLng);
        for (int i = 1; i < infos.size(); i++) {

            LatLng ptPosition = new LatLng(infos.get(i).getLatitude(), infos.get(i).getLongtitude());
            double d = DistanceUtil.getDistance(ptMine, ptPosition);
            if (min > d) {
                nearestlatLng = new LatLng(infos.get(i).getLatitude(), infos.get(i).getLongtitude());
            }

        }
        MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(nearestlatLng);
        baiduMap.animateMapStatus(msu);

    }

//    private void showinfowindow(List<MapData> infos) {
//
//        LatLng latLng = null;
//        for (MapData info : infos) {
//            latLng = new LatLng(info.getLatitude(), info.getLongtitude());
//
//            TextView location = new TextView(getApplicationContext());
//            location.setPadding(30, 20, 30, 50);
//            location.setText("剩余车位数");
//            InfoWindow.OnInfoWindowClickListener listener = null;
//            listener = new InfoWindow.OnInfoWindowClickListener() {
//                public void onInfoWindowClick() {
//                    baiduMap.hideInfoWindow();
//                }
//            };
//
//            InfoWindow infowindow = new InfoWindow(BitmapDescriptorFactory.fromView(location), latLng, -108, listener);
//            baiduMap.showInfoWindow(infowindow);
//        }
//    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
        Intent intent = getIntent();
        //从Intent当中根据key取得value
        if (intent != null) {
            value = intent.getStringExtra("user");
            mMenuFragment.showtext(value);
        }


    }

    @Override
    protected void onStart() {
        super.onStart();
        baiduMap.setMyLocationEnabled(true);
        if (!mLocationClient.isStarted())
            mLocationClient.start();
        myOrientationListener.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        baiduMap.setMyLocationEnabled(false);
        mLocationClient.stop();
        myOrientationListener.stop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void callback2(String param, ShowEntity showEntity) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.stop();
        mapView.onDestroy();
        baiduMap.setMyLocationEnabled(false);
    }

    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            MyLocationData data = new MyLocationData.Builder()
                    .direction(mCurrentX)
                    .accuracy(location.getRadius())
                    .latitude(location.getLatitude())
                    .longitude(location.getLongitude())
                    .build();
            baiduMap.setMyLocationData(data);
            MyLocationConfiguration config = new MyLocationConfiguration(mLocationMode, true, mIconLocation);
            baiduMap.setMyLocationConfigeration(config);
            mLatitude = location.getLatitude();
            mLongtitude = location.getLongitude();

            if (isFirstIn) {
                LatLng latLng = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);
                baiduMap.animateMapStatus(msu);
                isFirstIn = false;
               // Toast.makeText(context, location.getAddrStr(), Toast.LENGTH_SHORT).show();
                try {
//                  responseData = null;
                    post_containsurl(Const_Util.Map_Url18, value,MainActivity.this);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
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

    public void gotoMybook(View view) {

        Intent intent = new Intent(getApplicationContext(),MybookActivity.class);
        intent.putExtra("user",value);
        startActivity(intent);

    }
    public void gotoMyinfo(View view) {
        Intent intent1 = new Intent(getApplicationContext(),MyinfoActivity.class);
        intent1.putExtra("user",value);
        startActivity(intent1);
    }
    public void gotoMyorder(View view) {
        try {
//            responseData = null;
            getOrderdata_containsurl2(Const_Util.Map_Url13,value,this);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void gotoAboutus(View view) {
        Intent intent3 = new Intent(getApplicationContext(),AboutusActivity.class);
        startActivity(intent3);
    }

    public void gotoExit(View view) {
        showNormalDialog3("是否确认退出登录？");
    }



    @Override
    public void callback5(final String param) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                pi = NetUtil.parse_parkinginfo(param);

                if (pi.status == 1) {
                    if(pi.finish_parking ==1){
                        if(pi.pay_status==1){
                            if(pi.confirm_out==1){
                                getMyLocation();
                            }
                            else {
                                Intent intent1 = new Intent(MainActivity.this, PayShowActivity.class);
                                //intent1.putExtra("value",value);
                                intent1.putExtra("user", value);
                                intent1.putExtra("start_time1", pi.start_time);
                                intent1.putExtra("address", pi.address);
                                intent1.putExtra("leave_time1", pi.leave_time);
                                intent1.putExtra("money1", pi.money);
                                intent1.putExtra("price", pi.price_per_hour);
                                startActivity(intent1);
                                //intent1.putExtra("flag",0);
                                // startActivity(intent1);
                            }
                        }else {
                            Intent intent1 = new Intent(MainActivity.this, OrderActivity1.class);
                            intent1.putExtra("value", value);
                            //intent1.putExtra("flag",0);
                            startActivity(intent1);
                        }
                    }
                    else {
                        Intent intent2 = new Intent(getApplicationContext(),OrderActivity.class);
                        intent2.putExtra("user",value);
                        startActivity(intent2);
//                        current_addr.setText(pi.getAddress() + "");
//                        car_price.setText(pi.getPrice_per_hour() + "元");
//                        car_time.setText(pi.getStart_time() + "");
                    }
                }
                else
                {
                    Intent intent2 = new Intent(getApplicationContext(),OrderActivity.class);
                    intent2.putExtra("user",value);
                    startActivity(intent2);
                }



            }
        });
    }

    private void   showNormalDialog3(String msg){

        final AlertDialog.Builder normalDialog = new AlertDialog.Builder(MainActivity.this);
        normalDialog.setMessage(msg);

        normalDialog.setPositiveButton( "确定", new  DialogInterface.OnClickListener() {
            @Override
            public   void   onClick(DialogInterface dialog, int   which) {
                Intent intent4 = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent4);
            }
        });

        normalDialog.setNegativeButton("取消", new  DialogInterface.OnClickListener() {
            @Override
            public   void   onClick(DialogInterface dialog, int   which) {

            }
        });
        normalDialog.show();
    }
}

