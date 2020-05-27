package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

public class Menu extends AppCompatActivity {

    ImageButton game, sound;
    int soundd = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        game = (ImageButton) findViewById(R.id.button5);
        sound = (ImageButton) findViewById(R.id.switch1);
        game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(()->{
                    game.setImageResource(R.drawable.knopka_blue_nazhata);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    game.setImageResource(R.drawable.knopka_blue);
                }).start();
                Intent i = new Intent(Menu.this, MainActivity.class);
                i.putExtra("a", soundd);
                startActivity(i);
            }
        });
        sound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundd++;
                if (soundd % 2 == 0) {
                    sound.setImageResource(R.drawable.zvuk);
                } else {
                    sound.setImageResource(R.drawable.zvuk_vikl);
                }
            }
        });
    }
}

