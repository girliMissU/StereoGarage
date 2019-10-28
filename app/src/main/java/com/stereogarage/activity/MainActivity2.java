package com.stereogarage.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.stereogarage.R;


public class MainActivity2 extends FragmentActivity implements View.OnClickListener{

    public String value;
    public TextView change_btn,exit;
    public TextView user_name1;
    private Button gotonum,gotoerr,gotodata,gototriger;
   // public TextView total_number;
   // public TextView rest_number;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        change_btn = (TextView) findViewById(R.id.change_btn);
        user_name1 = (TextView) findViewById(R.id.user_name1);
        exit = (TextView) findViewById(R.id.exit);
        gotonum = (Button) findViewById(R.id.gotonum);
        gotoerr = (Button) findViewById(R.id.gotoerr);
        gotodata = (Button) findViewById(R.id.gotodata);

        gototriger = (Button) findViewById(R.id.gototriger);
      //  total_number = (TextView) findViewById(R.id.total_number);
      //  rest_number = (TextView) findViewById(R.id.rest_number);

        change_btn.setOnClickListener(this);
        exit.setOnClickListener(this);
        gotonum.setOnClickListener(this);
        gotoerr.setOnClickListener(this);
        gotodata.setOnClickListener(this);
        gototriger.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        //从Intent当中根据key取得value
        if (intent != null) {
            value = intent.getStringExtra("user");
            user_name1.setText(value);
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.change_btn:
                Intent intent = new Intent(getApplicationContext(),ChangeActivity2.class);
                intent.putExtra("user",value);
                startActivity(intent);
                break;
            case R.id.gotonum:
                Intent intent1 = new Intent(getApplicationContext(),NumActivity.class);
                startActivity(intent1);
                break;
            case R.id.gotoerr:
                Intent intent2 = new Intent(getApplicationContext(),ErrActivity.class);
                startActivity(intent2);
                break;
            case R.id.gotodata:
                Intent intent3 = new Intent(getApplicationContext(),DataActivity.class);
                startActivity(intent3);
                break;
            case R.id.gototriger:
                Intent intent4 = new Intent(getApplicationContext(),TrigerManageActivity.class);
                startActivity(intent4);
                break;
            case R.id.exit:
                Intent intent5 = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent5);
                break;
        }
    }

    }


