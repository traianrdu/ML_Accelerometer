package com.example.ml_accelerometer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class PredictionPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prediction_page);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // back button on bar
    }
}