package com.example.ml_accelerometer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class FirstPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page2);
    }

    public void receive_window(View view){
        Intent intent = new Intent(FirstPage.this, MainActivity.class);
        startActivity(intent);
    }
}