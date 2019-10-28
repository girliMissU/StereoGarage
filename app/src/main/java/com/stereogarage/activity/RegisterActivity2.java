package com.stereogarage.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.stereogarage.Bean.Const_Util;
import com.stereogarage.Bean.LoginInfo;
import com.stereogarage.Bean.ShowEntity;
import com.stereogarage.R;
import com.stereogarage.util.NetUtil;
import java.io.IOException;
import static com.stereogarage.util.NetUtil.postregisterdata_containsurl2;


public class RegisterActivity2 extends AppCompatActivity implements View.OnClickListener,NetUtil.My_callback {


    public Button btn_finish;
    public EditText et_passwd;
    public EditText et_passwd2;
    public EditText et_type;
    public EditText et_number;
    public LoginInfo LI;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        btn_finish = (Button) findViewById(R.id.btn_finish);
        et_passwd = (EditText) findViewById(R.id.et_passwd);
        et_passwd2 = (EditText) findViewById(R.id.et_passwd2);
        et_type = (EditText) findViewById(R.id.et_type);
        et_number = (EditText) findViewById(R.id.et_number);

        btn_finish.setOnClickListener(this);

        et_passwd.addTextChangedListener(watcher);
        et_passwd2.addTextChangedListener(watcher);
        et_number.addTextChangedListener(watcher);
        et_type.addTextChangedListener(watcher);


        if(et_passwd.getText().length()<6||!et_passwd.getText().toString().equals(et_passwd2.getText().toString())||TextUtils.isEmpty(et_type.getText().toString())||TextUtils.isEmpty(et_number.getText().toString()))
        {
            btn_finish.setEnabled(false);
            btn_finish.getBackground().setAlpha(100);
        }
        else{
            btn_finish.setEnabled(true);
            btn_finish.getBackground().setAlpha(250);
        }

        }

    TextWatcher watcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //只要编辑框内容有变化就会调用该方法，s为编辑框变化后的内容
            if (TextUtils.isEmpty(et_type.getText().toString())||TextUtils.isEmpty(et_number.getText().toString())||et_passwd.getText().length()<6||!et_passwd.getText().toString().equals(et_passwd2.getText().toString())){
                btn_finish.getBackground().setAlpha(100);
                btn_finish.setEnabled(false);
            }
            else {
                btn_finish.setEnabled(true);
                btn_finish.getBackground().setAlpha(250);
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
    }

    @Override
    public void callback(final String param) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LI = NetUtil.parse_logininfo(param);
                if (LI.status == 1) {
                    Intent intent1 = getIntent();
                    String msg=intent1.getStringExtra("user1");
                    Intent intent = new Intent(RegisterActivity2.this, MainActivity.class);
                    intent.putExtra("user",msg);
                    Toast.makeText(getApplicationContext(), "注册成功！", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
                else Toast.makeText(getApplicationContext(), "注册失败！", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_finish:
                String passwd=et_passwd.getText().toString();
                String passwd2 = et_passwd2.getText().toString();
                String type = et_type.getText().toString();
                String number = et_number.getText().toString();
                    Intent intent1 = getIntent();
                    String msg=intent1.getStringExtra("user1");
                    try {
                        postregisterdata_containsurl2(Const_Util.Map_Url4,msg,passwd,number,type,msg,this);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                break;
        }
    }
}