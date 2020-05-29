package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;

import static java.lang.Thread.sleep;

public class Loading extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading);
        Thread logoTimer = new Thread() {
            public void run() {
                try {
                    int logoTimer = 0;
                    while(logoTimer < 5000) {
                        sleep(100);
                        logoTimer = logoTimer +100;
                    };
                    Intent i=new Intent(Loading.this,Menu.class);
                    startActivity(i);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally{
                    finish();
                }
            }
        };
        logoTimer.start();
    }
}