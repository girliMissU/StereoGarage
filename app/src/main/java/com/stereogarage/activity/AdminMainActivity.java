package com.stereogarage.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.stereogarage.R;
import com.stereogarage.view.CircleMenuLayout;


public class AdminMainActivity extends Activity implements View.OnClickListener{
    public String value;
    public TextView change_btn,exit;
    public TextView user_name1;
    private AdapterView<ArrayAdapter<String>> listView;
    // public TextView total_number;
    // public TextView rest_number;


    private CircleMenuLayout mCircleMenuLayout;

    private String[] mItemTexts = new String[] { "车位剩余情况 ", "车库运行情况", "所有订单信息"};
    private int[] mItemImgs = new int[] { R.mipmap.tingchechang,
            R.mipmap.parkinggarage, R.mipmap.ding_dan};

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //自已切换布局文件看效果
        setContentView(R.layout.activity_adminmain);

        change_btn = (TextView) findViewById(R.id.change_btn);
        user_name1 = (TextView) findViewById(R.id.user_name1);
        exit = (TextView) findViewById(R.id.exit);
        //  total_number = (TextView) findViewById(R.id.total_number);
        //  rest_number = (TextView) findViewById(R.id.rest_number);

        change_btn.setOnClickListener(this);
        exit.setOnClickListener(this);


        mCircleMenuLayout = (CircleMenuLayout) findViewById(R.id.id_menulayout);
        mCircleMenuLayout.setMenuItemIconsAndTexts(mItemImgs, mItemTexts);

        mCircleMenuLayout.setOnMenuItemClickListener(new CircleMenuLayout.OnMenuItemClickListener()
        {
            @Override
            public void itemClick(View view, int pos)
            {
                //Toast.makeText(AdminMainActivity.this, mItemTexts[pos],Toast.LENGTH_SHORT).show();
                if(pos==0){
                        Intent intent1 = new Intent(getApplicationContext(),NumActivity.class);
                        startActivity(intent1);

                }
                else if(pos==1){

                    Intent intent2 = new Intent(getApplicationContext(),ErrActivity.class);
                    startActivity(intent2);
                }
              else if(pos==2){

                Intent intent3 = new Intent(getApplicationContext(),DataActivity.class);
                startActivity(intent3);
            }
            }
            @Override
            public void itemCenterClick(View view)
            {
               // Toast.makeText(AdminMainActivity.this,"you can do something just like ccb  ",Toast.LENGTH_SHORT).show();
            }
        });
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
            case R.id.exit:
                Intent intent5 = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent5);
                break;
        }
    }


}
