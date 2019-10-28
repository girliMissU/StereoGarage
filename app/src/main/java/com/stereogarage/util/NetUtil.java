package com.stereogarage.util;


import com.alibaba.fastjson.JSON;
import com.stereogarage.Bean.CancelInfo;
import com.stereogarage.Bean.DataInfo;
import com.stereogarage.Bean.ErrInfo;
import com.stereogarage.Bean.GarageInfo;
import com.stereogarage.Bean.Info;
import com.stereogarage.Bean.LateInfo2;
import com.stereogarage.Bean.LoginInfo;
import com.stereogarage.Bean.LateInfo;
import com.stereogarage.Bean.MapData;
import com.stereogarage.Bean.OrderInfo;
import com.stereogarage.Bean.ParkingInfo;
import com.stereogarage.Bean.ParkingInfo2;
import com.stereogarage.Bean.ShowEntity;
import com.stereogarage.Bean.ShowEntity2;
import com.stereogarage.Bean.ShowEntity4;
import com.stereogarage.callback.AllInterface;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administor on 2017/11/3.
 */

public class NetUtil {
    public static String responsedata;
    public static String responseData;
    public static String getdata_containsurl(String url){

        OkHttpClient okHttpClient = new OkHttpClient();
        // 创建请求参数
        Request request = new Request.Builder().url(url).method("GET",null).build();
        // 创建请求对象
        Call call = okHttpClient.newCall(request);
        // 发起异步的请求
        call.enqueue(new Callback() {

            @Override
            // 请求发生异常
            public void onFailure(Call call, IOException e) {
                //    Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
            }

            @Override
            // 获取到服务器数据。注意：即使是 404 等错误状态也是获取到服务器数据
            public void onResponse(Call call, final Response response) throws IOException {
                if(response.isSuccessful()){
                    responsedata = response.body().string();

                }else{
                    responsedata ="something wrong";
                }
            }
        });
        return responsedata;
    }

    public static String getOrderdata_containsurl(String url, String username,final My_callback mc) throws IOException {
        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("username", username)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call,final Response response) throws IOException {

                if(response.isSuccessful()){
                    responseData=response.body().string();
                    mc.callback(responseData);

                }else{
                    responseData ="something wrong";
                }
            }
        });
    //    String a=responsedata;
        return responseData;
    }

    public static String getOrderdata_containsurl2(String url, String username,final AllInterface.Cb2 mc) throws IOException {
        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("username", username)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call,final Response response) throws IOException {

                if(response.isSuccessful()){
                    responseData=response.body().string();
                    mc.callback5(responseData);

                }else{
                    responseData ="something wrong";
                }
            }
        });
        //    String a=responsedata;
        return responseData;
    }

    public static String getpaydata_containsurl(String url, String username,final AllInterface.Cb1 mc) throws IOException {
        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("username", username)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call,final Response response) throws IOException {
                if(response.isSuccessful()){
                    mc.callback4(response.body().string());
                }else{
                    responsedata ="something wrong";
                }
            }
        });
        return responsedata;
    }

    public static String postuserdata_containsurl(String url, String username, String password,final My_callback mc) throws IOException {
        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("userid", username)
                .add("passwd", password)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call,final Response response) throws IOException {
                if(response.isSuccessful()){
                    mc.callback(response.body().string());
                }else{
                    responsedata ="something wrong";
                }
            }
        });
        return responsedata;
    }

    public static String postregisterdata_containsurl(String url, String PhoneNumber,final My_callback mc) throws IOException {
        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("PhoneNumber", PhoneNumber)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call,final Response response) throws IOException {
                if(response.isSuccessful()){
                    mc.callback(response.body().string());
                }else{
                    responsedata ="something wrong";
                }
            }
        });
        return responsedata;
    }

    public static String postchange_containsurl(String url, String cp_status,final My_callback mc) throws IOException {
        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("cp_status", cp_status)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call,final Response response) throws IOException {
                if(response.isSuccessful()){
                    mc.callback(response.body().string());
                }else{
                    responsedata ="something wrong";
                }
            }
        });
        return responsedata;
    }

    public static String postregisterdata_containsurl2(String url, String userid,String passwd,String car_num,String car_type,String tel,final My_callback mc) throws IOException {
        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("userid", userid)
                .add("passwd", passwd)
                .add("car_num", car_num)
                .add("car_type", car_type)
                .add("tel", tel)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call,final Response response) throws IOException {
                if(response.isSuccessful()){
                    mc.callback(response.body().string());
                }else{
                    responsedata ="something wrong";
                }
            }
        });
        return responsedata;
    }

    public static String postregisterdata_containsurl3(String url, String userid,String passwd,final My_callback mc) throws IOException {
        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("userid", userid)
                .add("passwd", passwd)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call,final Response response) throws IOException {
                if(response.isSuccessful()){
                    mc.callback(response.body().string());
                }else{
                    responsedata ="something wrong";
                }
            }
        });
        return responsedata;
    }

    public static String postinfodata_containsurl(String url, String userid,String type,String number,final AllInterface.Cb mc) throws IOException {
        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("userid", userid)
                .add("type", type)
                .add("number", number)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call,final Response response) throws IOException {
                if(response.isSuccessful()){
                    mc.callback3(response.body().string());
                }else{
                    responsedata ="something wrong";
                }
            }
        });
        return responsedata;
    }

    public static String post_containsurl(String url, String username,final AllInterface.Cb mc) throws IOException {
        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("username", username)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call,final Response response) throws IOException {
                if(response.isSuccessful()){
                    mc.callback3(response.body().string());
                }else{
                    responsedata ="something wrong";
                }
            }
        });
        return responsedata;
    }

    public static String postregisterdata_containsurl4(String url, String userid,String garage_num,String start_time,final My_callback mc) throws IOException {
        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("userid", userid)
                .add("garage_num", garage_num)
                .add("start_time", start_time)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call,final Response response) throws IOException {
                if(response.isSuccessful()){
                    mc.callback(response.body().string());
                }else{
                    responsedata ="something wrong";
                }
            }
        });
        return responsedata;
    }

    public static String postdata_containsurl(String url, String order_id, final My_callback mc,final ShowEntity showEntity) throws IOException {
        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("order_id",order_id)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call,final Response response) throws IOException {
                if(response.isSuccessful()){
                    mc.callback2(response.body().string(),showEntity);
                }else{
                    responsedata ="something wrong";
                }
            }
        });
        return responsedata;
    }

    public static String postdata_containsurl2(String url, String parking_id, final My_callback3 mc,final ShowEntity4 showEntity) throws IOException {
        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("parking_id",parking_id)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call,final Response response) throws IOException {
                if(response.isSuccessful()){
                    mc.callback5(response.body().string(),showEntity);
                }else{
                    responsedata ="something wrong";
                }
            }
        });
        return responsedata;
    }

    public static String getgaragedata_containsurl(String url,final My_callback mc) throws IOException {
        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call,final Response response) throws IOException {

                if(response.isSuccessful()){
                    responseData=response.body().string();
                    mc.callback(responseData);

                }else{
                    responseData ="something wrong";
                }
            }
        });
        //    String a=responsedata;
        return responseData;
    }

    public static ArrayList<MapData> parse_mapdatas(String json){
        ArrayList<MapData>datas=new ArrayList<>();
        MapData mp = new MapData();
        String []arrays=json.split("<br>");
        for(int i=0;i<arrays.length;i++){
            mp=JSON.parseObject(arrays[i], MapData.class);
            datas.add(mp);
        }
        return datas;
    }

    public static ArrayList<OrderInfo> parse_mapdatas1(String json){
        ArrayList<OrderInfo>datas=new ArrayList<>();
        OrderInfo oi = new OrderInfo();
        String []arrays=json.split("<br>");
        String a=arrays[0];
        for(int i=0;i<arrays.length;i++){
            oi=JSON.parseObject(arrays[i], OrderInfo.class);
            datas.add(oi);
        }
        return datas;
    }

    public static ArrayList<GarageInfo> parse_mdatas(String json){
        ArrayList<GarageInfo>datas=new ArrayList<>();
        GarageInfo gi = new GarageInfo();
        String []arrays=json.split("<br>");
        String a=arrays[0];
        for(int i=0;i<arrays.length;i++){
            gi=JSON.parseObject(arrays[i], GarageInfo.class);
            datas.add(gi);
        }
        return datas;
    }

    public static ArrayList<DataInfo> parse_mdatas2(String json){
        ArrayList<DataInfo>datas=new ArrayList<>();
        DataInfo di = new DataInfo();
        String []arrays=json.split("<br>");
        String a=arrays[0];
        for(int i=0;i<arrays.length;i++){
            di=JSON.parseObject(arrays[i], DataInfo.class);
            datas.add(di);
        }
        return datas;
    }

    public static ArrayList<ErrInfo> parse_mdatas3(String json){
        ArrayList<ErrInfo>datas=new ArrayList<>();
        ErrInfo ei = new ErrInfo();
        String []arrays=json.split("<br>");
        String a=arrays[0];
        for(int i=0;i<arrays.length;i++){
            ei=JSON.parseObject(arrays[i], ErrInfo.class);
            datas.add(ei);
        }
        return datas;
    }

    public static LoginInfo parse_logininfo(String json){
            LoginInfo li = new LoginInfo();
            li=JSON.parseObject(json, LoginInfo.class);
            return li;
    }

    public static ParkingInfo parse_parkinginfo(String json){
        ParkingInfo pi = new ParkingInfo();
        pi=JSON.parseObject(json, ParkingInfo.class);
        return pi;
    }

    public static ParkingInfo2 parse_parkinginfo2(String json){
        ParkingInfo2 pi = new ParkingInfo2();
        pi=JSON.parseObject(json, ParkingInfo2.class);
        return pi;
    }

    public static LateInfo parse_lateinfo(String json){
        LateInfo li = new LateInfo();
        li=JSON.parseObject(json, LateInfo.class);
        return li;
    }

    public static CancelInfo parse_cancelinfo(String json){
        CancelInfo li = new CancelInfo();
        li=JSON.parseObject(json, CancelInfo.class);
        return li;
    }

    public static LateInfo2 parse_lateinfo2(String json){
        LateInfo2 li2 = new LateInfo2();
        li2=JSON.parseObject(json, LateInfo2.class);
        return li2;
    }
    public static Info parse_info(String json){
        Info in = new Info();
        in=JSON.parseObject(json, Info.class);
        return in;
    }

  public  interface My_callback{

        void callback(String param);
        void  callback2(String param,ShowEntity showEntity);
    }

    public  interface My_callback2{

        void  callback4(String param,ShowEntity2 showEntity2);
    }
    public  interface My_callback3{

        void  callback5(String param,ShowEntity4 showEntity4);
    }

}
