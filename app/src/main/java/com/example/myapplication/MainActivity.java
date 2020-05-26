package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Vector;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static java.lang.Thread.sleep;


public class MainActivity extends AppCompatActivity {

    int h50_50,nepravilno,otvet,pobeda,pravilno;
    int vopr = 0;
    static int idd;
    int[] money;
    boolean sound;
    ImageButton back, call, help, half, var_a, var_b, var_c, var_d;
    TextView vopros, summa,a,b,c,d;
    SoundPool soundpool_main;
    MediaPlayer mp;

    public static final String LOG_TAG = "Main_Activity";

    private QuestionResponse questions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sound = getIntent().getBooleanExtra("a", false);

        var_a = (ImageButton) findViewById(R.id.button);
        var_b = (ImageButton) findViewById(R.id.button2);
        var_c = (ImageButton) findViewById(R.id.button3);
        var_d = (ImageButton) findViewById(R.id.button4);
        back = (ImageButton) findViewById(R.id.imageButton11);
        call = (ImageButton) findViewById(R.id.imageButton8);
        help = (ImageButton) findViewById(R.id.imageButton7);
        half = (ImageButton) findViewById(R.id.imageButton6);
        vopros = (TextView) findViewById(R.id.tekstVoprosa);
        summa = (TextView) findViewById(R.id.summaVoprosa);
        a=(TextView)findViewById(R.id.textView1);
        b=(TextView)findViewById(R.id.textView2);
        c=(TextView)findViewById(R.id.textView3);
        d=(TextView)findViewById(R.id.textView4);
        money = new int[]{500, 1000, 2000, 3000, 5000, 10000, 15000, 25000, 50000, 100000, 200000, 400000, 800000, 1500000, 3000000};

        soundpool_main=new SoundPool(6, AudioManager.STREAM_MUSIC, 0);
        soundpool_main.setOnLoadCompleteListener(this::onLoadComplete);
        mp=MediaPlayer.create(this,R.raw.igra);
        mp.setLooping(true);
        h50_50=soundpool_main.load(this,R.raw.h50_50,1);
        nepravilno=soundpool_main.load(this,R.raw.nepravilno,1);
        otvet=soundpool_main.load(this,R.raw.otvet,1);
        pobeda=soundpool_main.load(this,R.raw.pobeda,1);
        pravilno=soundpool_main.load(this,R.raw.pravilno,1);
        if(sound){
            mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener()
            {
                public void onPrepared(MediaPlayer mp)
                {
                    mp.start();
                }
            });
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
            {
                public void onCompletion(MediaPlayer mp)
                {
                    mp.release();
                }
            });
        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Menu.class);
                startActivity(i);
            }
        });
        QuestionRequest qr = new QuestionRequest();
        qr.qType = 1;
        qr.count = 5;
        new RequestAsync().execute(qr);

        try {
            sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId()==var_a.getId()) var_a.setImageResource(R.drawable.knopka_yellow);
                else if(v.getId()==var_b.getId()) var_b.setImageResource(R.drawable.knopka_yellow);
                else if(v.getId()==var_c.getId()) var_c.setImageResource(R.drawable.knopka_yellow);
                else if(v.getId()==var_d.getId()) var_d.setImageResource(R.drawable.knopka_yellow);
                if(sound) {
                    mp.pause();
                    soundpool_main.play(otvet, 1, 1, 0, 0, 1);
                }
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(sound) {
                    soundpool_main.stop(otvet);
                }
                if (v.getId() == idd) {
                    if(v.getId()==var_a.getId()) var_a.setImageResource(R.drawable.knopka_green);
                    else if(v.getId()==var_b.getId()) var_b.setImageResource(R.drawable.knopka_green);
                    else if(v.getId()==var_c.getId()) var_c.setImageResource(R.drawable.knopka_green);
                    else if(v.getId()==var_d.getId()) var_d.setImageResource(R.drawable.knopka_green);
                    if (vopr < 14) {
                        if(sound) {
                            soundpool_main.play(pravilno, 1, 1, 0, 0, 1);
                        }
                        try {
                            sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (vopr == 4) {
                            QuestionRequest qr = new QuestionRequest();
                            qr.count = 5;
                            qr.qType = 2;
                            new RequestAsync().execute(qr);
                        }
                        else if (vopr == 9) {
                            QuestionRequest qr = new QuestionRequest();
                            qr.count = 5;
                            qr.qType = 3;
                            new RequestAsync().execute(qr);
                        }
                        else {
                            summa.setText(vopr+1 + ". " + money[vopr]);
                            vopros.setText(questions.data.get(vopr % 5).question);
                            Vector vec = new Vector();
                            vec.add(0);
                            vec.add(1);
                            vec.add(2);
                            vec.add(3);
                            int rand =0+ (int) (Math.random() * vec.size());
                            a.setText(questions.data.get(vopr % 5).answers.get((int)vec.get(rand)));
                            if ((int)vec.get(rand) == 0) idd = var_a.getId();
                            vec.remove(rand);
                            rand =0+ (int) (Math.random() * vec.size());
                            b.setText(questions.data.get(vopr % 5).answers.get((int)vec.get(rand)));
                            if ((int)vec.get(rand) == 0) idd = var_b.getId();
                            vec.remove(rand);
                            rand =0+ (int) (Math.random() * vec.size());
                            c.setText(questions.data.get(vopr % 5).answers.get((int)vec.get(rand)));
                            if ((int)vec.get(rand) == 0) idd = var_c.getId();
                            vec.remove(rand);
                            rand =0+ (int) (Math.random() * vec.size());
                            d.setText(questions.data.get(vopr % 5).answers.get((int)vec.get(rand)));
                            if ((int)vec.get(rand) == 0) idd = var_d.getId();
                            vec.remove(rand);
                            vopr++;
                        }
                        if(sound) {
                            mp.start();
                        }
                    }
                    else if (vopr == 14) {
                        //ПАБЕДА
                        if(sound) {
                            soundpool_main.play(pobeda, 1, 1, 0, 0, 1);
                        }
                        try {
                            sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        finish();
                    }
                }
                else {
                    if(v.getId()==var_a.getId()) var_a.setImageResource(R.drawable.);
                    else if(v.getId()==var_b.getId()) var_b.setImageResource(R.drawable.knopka_red);
                    else if(v.getId()==var_c.getId()) var_c.setImageResource(R.drawable.knopka_red);
                    else if(v.getId()==var_d.getId()) var_d.setImageResource(R.drawable.knopka_red);
                    if(sound) {
                        soundpool_main.play(nepravilno, 1, 1, 0, 0, 1);
                    }
                    try {
                        sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    finish();
                }
            }
        };
        var_a.setOnClickListener(listener);
        var_b.setOnClickListener(listener);
        var_c.setOnClickListener(listener);
        var_d.setOnClickListener(listener);
    }

    private void OnResponse() {
        summa.setText(vopr+1 + ". " + money[vopr]);
        vopros.setText(questions.data.get(vopr % 5).question);
        Vector vec = new Vector();
        vec.add(0);
        vec.add(1);
        vec.add(2);
        vec.add(3);
        int rand =0+ (int) (Math.random() * vec.size());
        a.setText(questions.data.get(vopr % 5).answers.get((int)vec.get(rand)));
        if ((int)vec.get(rand) == 0) idd = var_a.getId();
        vec.remove(rand);
        rand =0+ (int) (Math.random() * vec.size());
        b.setText(questions.data.get(vopr % 5).answers.get((int)vec.get(rand)));
        if ((int)vec.get(rand) == 0) idd = var_b.getId();
        vec.remove(rand);
        rand =0+ (int) (Math.random() * vec.size());
        c.setText(questions.data.get(vopr % 5).answers.get((int)vec.get(rand)));
        if ((int)vec.get(rand) == 0) idd = var_c.getId();
        vec.remove(rand);
        rand =0+ (int) (Math.random() * vec.size());
        d.setText(questions.data.get(vopr % 5).answers.get((int)vec.get(rand)));
        if ((int)vec.get(rand) == 0) idd = var_d.getId();
        vec.remove(rand);
        vopr++;
    }

    private void OnFailure() {
        Toast.makeText(this, "Ошибка соединения", Toast.LENGTH_LONG).show();
    }

    class RequestAsync extends AsyncTask<QuestionRequest, Void, Void> {

        @Override
        protected Void doInBackground(QuestionRequest... params) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(JsonPlaceHolderAPI.HOST)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            JsonPlaceHolderAPI JsonPlaceHolderAPI = retrofit.create(JsonPlaceHolderAPI.class);
            Call<QuestionResponse> call =
                    JsonPlaceHolderAPI.getQuestions(params[0].qType, params[0].count);

            call.enqueue(new Callback<QuestionResponse>() {
                @Override
                public void onResponse(Call<QuestionResponse> call, Response<QuestionResponse> response) {
                    MainActivity.this.questions = response.body();
                    MainActivity.this.OnResponse();
                }

                @Override
                public void onFailure(Call<QuestionResponse> call, Throwable t) {
                    MainActivity.this.OnFailure();
                }
            });
            return null;
        }
    }
    public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
        Log.d(LOG_TAG, "onLoadComplete, sampleId = " + sampleId + ", status = " + status);
    }
}