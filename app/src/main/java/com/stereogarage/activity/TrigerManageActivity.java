package com.stereogarage.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.stereogarage.R;


public class TrigerManageActivity extends AppCompatActivity{



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trigermanage);

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    public void gotoPeople(View view) {

        Intent intent = new Intent(getApplicationContext(),TrigerActivity.class);
        startActivity(intent);

    }
    public void gotoRecord(View view) {

        Intent intent = new Intent(getApplicationContext(),RecordActivity.class);
        startActivity(intent);

    }
    public void addTriger(View view) {

        Intent intent = new Intent(getApplicationContext(),TrigerAddActivity.class);
        startActivity(intent);

    }
    public void trigerList(View view) {

        Intent intent = new Intent(getApplicationContext(),TrigerListActivity.class);
        startActivity(intent);

    }
    public void finishActivity(View view) {
        finish();
    }
    }


