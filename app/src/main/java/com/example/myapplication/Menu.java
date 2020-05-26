package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

public class Menu extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    Button game, records;
    Switch sound;
    boolean soundd=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        game = (Button) findViewById(R.id.button5);
        sound = (Switch) findViewById(R.id.switch1);
        game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Menu.this, MainActivity.class);
                i.putExtra("a",soundd);
                startActivity(i);
            }
        });
        if (sound != null) {
            sound.setOnCheckedChangeListener(this);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(!isChecked){
            soundd=false;
        }
        else{
            soundd=true;
        }
    }
}
