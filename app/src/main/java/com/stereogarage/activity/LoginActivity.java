package com.stereogarage.activity;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.stereogarage.Bean.Const_Util;
import com.stereogarage.Bean.CustomVideoView;
import com.stereogarage.Bean.JellyInterpolator;
import com.stereogarage.Bean.LoginInfo;
import com.stereogarage.Bean.ShowEntity;
import com.stereogarage.R;
import com.stereogarage.util.NetUtil;

import java.io.IOException;

import static com.stereogarage.util.NetUtil.postuserdata_containsurl;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener,NetUtil.My_callback {

    public Button login_btn;
    public Button register_btn;
    public Button admin_btn;
    public EditText login_input_username;
    public EditText login_input_password;
    public LoginInfo LI;
    public Button forgive_pwd;
    public CheckBox remember_pwd;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private ProgressBar pro;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();

    }

    private void initView() {
        login_btn = (Button) findViewById(R.id.login_btn);
        register_btn = (Button) findViewById(R.id.register_btn);
        admin_btn = (Button) findViewById(R.id.admin_btn);


        login_input_username = (EditText) findViewById(R.id.login_input_username);
        login_input_username.setSelection(login_input_username.getText().length());
        login_input_username.addTextChangedListener(userwatcher);
        login_input_password = (EditText) findViewById(R.id.login_input_password);
        login_input_password.setSelection(login_input_password.getText().length());
        login_input_password.addTextChangedListener(passwdwatcher);
        forgive_pwd = (Button) findViewById(R.id.forgive_pwd);
        remember_pwd = (CheckBox) findViewById(R.id.remember_pwd);
        pro = (ProgressBar) findViewById(R.id.reg_req_code_gif_view);

        login_btn.setOnClickListener(this);
        register_btn.setOnClickListener(this);
        forgive_pwd.setOnClickListener(this);
        remember_pwd.setOnClickListener(this);
        admin_btn.setOnClickListener(this);


        sharedPreferences = getSharedPreferences("rememberpassword", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        boolean isRemember=sharedPreferences.getBoolean("rememberpassword",false);

        if(isRemember){
            String name=sharedPreferences.getString("name","");
            String password=sharedPreferences.getString("password","");
            login_input_username.setText(name);
            login_input_password.setText(password);
            login_input_username.setSelection(login_input_username.getText().length());
            login_input_password.setSelection(login_input_password.getText().length());
            remember_pwd.setChecked(true);
        }
        if (TextUtils.isEmpty(login_input_username.getText().toString()) || TextUtils.isEmpty(login_input_password.getText().toString())) {

            login_btn.getBackground().setAlpha(100);
            login_btn.setEnabled(false);
        }
        else
            {
                login_btn.setEnabled(true);
                login_btn.getBackground().setAlpha(250);
            }
    }

    TextWatcher userwatcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //只要编辑框内容有变化就会调用该方法，s为编辑框变化后的内容
            if (TextUtils.isEmpty(login_input_username.getText().toString())){
                login_btn.getBackground().setAlpha(100);
                login_btn.setEnabled(false);
            }
            else if(!TextUtils.isEmpty(login_input_username.getText().toString())&&!TextUtils.isEmpty(login_input_password.getText().toString())){
                login_btn.setEnabled(true);
                login_btn.getBackground().setAlpha(250);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    TextWatcher passwdwatcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //只要编辑框内容有变化就会调用该方法，s为编辑框变化后的内容
            if (TextUtils.isEmpty(login_input_password.getText().toString())){
                login_btn.getBackground().setAlpha(100);
                login_btn.setEnabled(false);
            }
            else if(!TextUtils.isEmpty(login_input_username.getText().toString())&&!TextUtils.isEmpty(login_input_password.getText().toString())){
                login_btn.setEnabled(true);
                login_btn.getBackground().setAlpha(250);
            }
        }
        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    public void callback(final String param) {
    runOnUiThread(new Runnable() {
    @Override
    public void run() {
        LI = NetUtil.parse_logininfo(param);
        if (LI.status == 1) {
           if(remember_pwd.isChecked()) {
                String username = login_input_username.getText().toString();//账号
                String password = login_input_password.getText().toString();//密码
                editor.putBoolean("rememberpassword",true);
                editor.putString("name",username);
                editor.putString("password",password);
            }
            else {
                //editor.clear();
               String username = login_input_username.getText().toString();//账号
               String password = login_input_password.getText().toString();//密码
               //editor.putBoolean("rememberpassword",true);
               editor.putString("name",username);
               editor.putBoolean("rememberpassword",false);
               //editor.putString("password",password);
            }
            editor.commit();
           // Toast.makeText(getApplicationContext(), "登陆成功！", Toast.LENGTH_SHORT).show();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    login_btn.setVisibility(View.VISIBLE);
                    pro.setVisibility(View.GONE);
                    Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
                    intent1.putExtra("user",login_input_username.getText().toString());
                    startActivity(intent1);
                    finish();
                }
            }, 500);
        }

        else if (LI.status == -1){

            Handler handler1 = new Handler();
            handler1.postDelayed(new Runnable() {
                @Override
                public void run() {
                    login_btn.setVisibility(View.VISIBLE);
                    pro.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "用户不存在！", Toast.LENGTH_SHORT).show();

                }
            }, 500);

        }

        else {
            Handler handler2 = new Handler();
            handler2.postDelayed(new Runnable() {
                @Override
                public void run() {
                    login_btn.setVisibility(View.VISIBLE);
                    pro.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "密码错误！", Toast.LENGTH_SHORT).show();

                }
            }, 500);


        }
    }
    });
    }

    @Override
    public void callback2(String param, ShowEntity showEntity) {

    }

    @Override
                public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.login_btn:

                        login_btn.setVisibility(View.GONE);
                        pro.setVisibility(View.VISIBLE);

                            try {
                                postuserdata_containsurl(Const_Util.Map_Url2,login_input_username.getText().toString(),login_input_password.getText().toString(),this);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

            break;
            case R.id.register_btn:
                Intent intent2 = new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(intent2);
                break;
            case R.id.forgive_pwd:
                Intent intent3 = new Intent(getApplicationContext(),ForgiveActivity.class);
                intent3.putExtra("user",login_input_username.getText().toString());
                startActivity(intent3);
                break;
            case R.id.admin_btn:
                Intent intent4 = new Intent(getApplicationContext(),AdminloginActivity.class);
                startActivity(intent4);
                break;
        }
    }


}

