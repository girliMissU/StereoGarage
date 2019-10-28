package com.stereogarage.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mob.MobSDK;
import com.stereogarage.Bean.Const_Util;
import com.stereogarage.Bean.LoginInfo;
import com.stereogarage.Bean.ShowEntity;
import com.stereogarage.R;
import com.stereogarage.util.NetUtil;

import org.json.JSONObject;

import java.io.IOException;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

import static com.stereogarage.util.NetUtil.postregisterdata_containsurl;


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener,NetUtil.My_callback {

    public Button send_v_code;
    public Button btn_next;
    public EditText et_phone;
    public EditText et_vcode;
    private String PhoneNumber;

    EventHandler eventHandler;
    public LoginInfo LI;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        MobSDK.init(getApplicationContext(),"22e5b80c0f36e","8bcbb6563e68d47d055c853f91680d6d");

        send_v_code = (Button) findViewById(R.id.send_v_code);
        btn_next = (Button) findViewById(R.id.btn_next);
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_vcode = (EditText) findViewById(R.id.et_vcode);

        send_v_code.setOnClickListener(this);
        btn_next.setOnClickListener(this);
        et_phone.addTextChangedListener(phonewatcher);
        et_vcode.addTextChangedListener(vcodewatcher);

        PhoneNumber = et_phone.getText().toString();

        if (null == PhoneNumber || "".equals(PhoneNumber) || PhoneNumber.length() != 11) {
            send_v_code.getBackground().setAlpha(100);
            send_v_code.setEnabled(false);
            btn_next.getBackground().setAlpha(100);
            btn_next.setEnabled(false);

        }
        else {
            send_v_code.getBackground().setAlpha(250);
            send_v_code.setEnabled(true);
            if (!TextUtils.isEmpty(et_phone.getText().toString()) && !TextUtils.isEmpty(et_vcode.getText().toString())) {
                btn_next.setEnabled(true);
                btn_next.getBackground().setAlpha(250);
            }
            else {
                btn_next.setEnabled(false);
                btn_next.getBackground().setAlpha(100);
            }
        }

        eventHandler = new EventHandler() {

            @Override
            public void afterEvent(int event, int result, Object data) {
                Message message = myHandler.obtainMessage(0x00);
                message.arg1 = event;
                message.arg2 = result;
                message.obj = data;
                myHandler.sendMessage(message);
            }
    };
        SMSSDK.registerEventHandler(eventHandler);
        }

    TextWatcher phonewatcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //只要编辑框内容有变化就会调用该方法，s为编辑框变化后的内容
            if (et_phone.getText().length() != 11){
                send_v_code.getBackground().setAlpha(100);
                send_v_code.setEnabled(false);
            }
            else {
                send_v_code.setEnabled(true);
                send_v_code.getBackground().setAlpha(250);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    TextWatcher vcodewatcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //只要编辑框内容有变化就会调用该方法，s为编辑框变化后的内容
            if (!TextUtils.isEmpty(et_vcode.getText().toString())&&et_phone.getText().length() == 11){
                btn_next.getBackground().setAlpha(250);
                btn_next.setEnabled(true);
            }
            else {
                btn_next.setEnabled(false);
                btn_next.getBackground().setAlpha(100);
            }
        }
        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    public void callback2(String param, ShowEntity showEntity) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eventHandler);
    }

    @Override
    public void callback(final String param) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LI = NetUtil.parse_logininfo(param);
                if (LI.status == 1) {
                    Toast.makeText(getApplicationContext(), "手机号码可用！", Toast.LENGTH_SHORT).show();
                    SMSSDK.getVerificationCode("86", et_phone.getText().toString());
                    send_v_code.setEnabled(false);
                    //开启线程去更新button的text
                    new Thread() {
                        @Override
                        public void run() {
                            int totalTime = 60;
                            for (int i = 0; i < totalTime; i++) {
                                Message message = myHandler.obtainMessage(0x01);
                                message.arg1 = totalTime - i;
                                myHandler.sendMessage(message);
                                try {
                                    sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            myHandler.sendEmptyMessage(0x02);
                        }
                    }.start();
                }
                else Toast.makeText(getApplicationContext(), "手机号码已注册，请直接登录！", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.send_v_code :

                try {
                    postregisterdata_containsurl(Const_Util.Map_Url3,et_phone.getText().toString(),this);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            //验证通过 将发送短信到这个号码上面
                break;
            case R.id.btn_next:
                String code = et_vcode.getText().toString().trim();
                SMSSDK.submitVerificationCode("86", et_phone.getText().toString(), code);
                break;

        }
    }

    Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x00:
                    int event = msg.arg1;
                    int result = msg.arg2;
                    Object data = msg.obj;
                    if (result == SMSSDK.RESULT_COMPLETE) { //回调  当返回的结果是complete
                        if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) { //获取验证码
                            Toast.makeText(RegisterActivity.this, "发送验证码成功", Toast.LENGTH_SHORT).show();
                        }
                        else if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) { //提交验证码
                            Toast.makeText(RegisterActivity.this, "提交验证码成功", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegisterActivity.this, RegisterActivity2.class);
                                intent.putExtra("user1",et_phone.getText().toString());
                                startActivity(intent);
                        } else {

                        }
                    }
                    else { //进行操作出错，通过下面的信息区分析错误原因
                        try {
                            Throwable throwable = (Throwable) data;
                            throwable.printStackTrace();
                            JSONObject object = new JSONObject(throwable.getMessage());
                            String des = object.optString("detail");//错误描述
                            int status = object.optInt("status");//错误代码
                            //错误代码：  http://wiki.mob.com/android-api-%E9%94%99%E8%AF%AF%E7%A0%81%E5%8F%82%E8%80%83/
                            if (status > 0 && !TextUtils.isEmpty(des)) {
                                Toast.makeText(RegisterActivity.this, des, Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 0x01:
                    send_v_code.setText("重新发送(" + msg.arg1 + ")");
                    break;
                case 0x02:
                    send_v_code.setText("获取验证码");
                    send_v_code.setEnabled(true);
                    break;
            }
        }
    };
}