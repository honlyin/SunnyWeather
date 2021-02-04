package com.example.sunnyweather.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.sunnyweather.R;
import com.example.sunnyweather.utils.LogUtils;

public class WeatherActivity extends AppCompatActivity {
    private static final String TAG = "WeatherActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LogUtils.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
    }
}