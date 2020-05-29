package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

public class Menu extends AppCompatActivity {

    ImageButton game, sound;
    boolean soundd = true;
    int k;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        game = (ImageButton) findViewById(R.id.button5);
        sound = (ImageButton) findViewById(R.id.switch1);
        game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.setImageResource(R.drawable.knopka_blue_nazhata);
                new CountDownTimer(300, 1000) {
                    public void onTick(long millisUntilFinished) {
                    }

                    public void onFinish() {
                        game.setImageResource(R.drawable.knopka_blue);
                        Intent i = new Intent(Menu.this, MainActivity.class);
                        i.putExtra("a", soundd);
                        startActivity(i);
                    }
                }.start();
            }
        });
        sound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                k++;
                if (k % 2 == 0) {
                    soundd=true;
                    sound.setImageResource(R.drawable.zvuk);
                } else {
                    soundd=false;
                    sound.setImageResource(R.drawable.zvuk_vikl);
                }
            }
        });
    }
}