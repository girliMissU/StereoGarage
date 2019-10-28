package com.stereogarage.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.stereogarage.R;


public class AboutusActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }



    public void finishActivity(View view) {
        finish();
    }
}