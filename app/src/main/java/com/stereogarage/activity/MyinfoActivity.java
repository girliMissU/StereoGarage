package com.stereogarage.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.stereogarage.Bean.Const_Util;
import com.stereogarage.Bean.Info;
import com.stereogarage.Bean.LoginInfo;
import com.stereogarage.Bean.ShowEntity;
import com.stereogarage.R;
import com.stereogarage.callback.AllInterface;
import com.stereogarage.util.NetUtil;

import java.io.IOException;

import static com.stereogarage.util.NetUtil.getOrderdata_containsurl;
import static com.stereogarage.util.NetUtil.postinfodata_containsurl;


public class MyinfoActivity extends AppCompatActivity implements View.OnClickListener,NetUtil.My_callback,AllInterface.Cb{

    public  String value;
    TextView user_name1;
    public Button btn_chaninfo,btn_chanpass;
    public EditText et_type;
    public EditText et_number;
    public LoginInfo LI;
    public Info in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myinfo);

        btn_chaninfo = (Button) findViewById(R.id.btn_chaninfo);
        btn_chanpass = (Button) findViewById(R.id.btn_chanpass);
        et_type = (EditText) findViewById(R.id.et_type);
        et_number = (EditText) findViewById(R.id.et_number);
        et_type.setSelection(et_type.getText().length());
        et_number.setSelection(et_number.getText().length());
        user_name1 = (TextView) findViewById(R.id.user_name1);
        btn_chaninfo.setOnClickListener(this);
        btn_chanpass.setOnClickListener(this);

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
            getOrderdata_containsurl(Const_Util.Map_Url11,value,this);
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
        in = NetUtil.parse_info(param);
        et_type.setText(in.getType()+"");
        et_number.setText(in.getNumber()+"");
        et_type.setSelection(et_type.getText().length());
        et_number.setSelection(et_number.getText().length());
        user_name1.setText(value);
            }
        });
    }

    public void callback3(final String param) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LI = NetUtil.parse_logininfo(param);
                if (LI.status == 1) {
                    Toast.makeText(getApplicationContext(), "修改成功", Toast.LENGTH_SHORT).show();
                } else Toast.makeText(getApplicationContext(), "修改失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_chaninfo:
                String type = et_type.getText().toString();
                String number = et_number.getText().toString();
                if (TextUtils.isEmpty(type))
                {
                    Toast.makeText(MyinfoActivity.this, "请输入车型", Toast.LENGTH_SHORT).show();
                    break;
                }
                 else if (TextUtils.isEmpty(number))
                {
                    Toast.makeText(MyinfoActivity.this, "请输入车牌", Toast.LENGTH_SHORT).show();
                    break;
                }
                else {
                    try {
//                  responseData = null;
                        postinfodata_containsurl(Const_Util.Map_Url12, value, type, number, this);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            case R.id.btn_chanpass:
                Intent intent = new Intent(getApplicationContext(),ForgiveActivity2.class);
                startActivity(intent);
        }
   }


    public void finishActivity(View view) {
        finish();
    }
}