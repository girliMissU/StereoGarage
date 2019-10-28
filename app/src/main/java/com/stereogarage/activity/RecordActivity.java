package com.stereogarage.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.stereogarage.R;


public class RecordActivity extends AppCompatActivity{



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    public void finishActivity(View view) {
        finish();
    }

    }


