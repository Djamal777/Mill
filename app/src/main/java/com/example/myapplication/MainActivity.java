package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
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

    int vopr = 0;
    static int idd;
    int[] money;
    boolean sound;
    Button var_a, var_b, var_c, var_d;
    ImageButton back, call, help, half;
    TextView vopros, summa;

    public static final String LOG_TAG = "Main_Activity";

    private QuestionResponse questions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sound = getIntent().getBooleanExtra("a", false);

        var_a = (Button) findViewById(R.id.button);
        var_b = (Button) findViewById(R.id.button2);
        var_c = (Button) findViewById(R.id.button3);
        var_d = (Button) findViewById(R.id.button4);
        back = (ImageButton) findViewById(R.id.imageButton11);
        call = (ImageButton) findViewById(R.id.imageButton8);
        help = (ImageButton) findViewById(R.id.imageButton7);
        half = (ImageButton) findViewById(R.id.imageButton6);
        vopros = (TextView) findViewById(R.id.tekstVoprosa);
        summa = (TextView) findViewById(R.id.summaVoprosa);
        money = new int[]{500, 1000, 2000, 3000, 5000, 10000, 15000, 25000, 50000, 100000, 200000, 400000, 800000, 1500000, 3000000};

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
                //устанавливаю желтую кнопку
                //проигрываю "otvet"
                /*try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
                if (v.getId() == idd) {
                    if (vopr < 15) {
                        //устанавливаю зеленую кнопку
                        //проигрываю "pravilno"
                        //?sleep
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
                            var_a.setText(questions.data.get(vopr % 5).answers.get((int)vec.get(rand)));
                            if ((int)vec.get(rand) == 0) idd = var_a.getId();
                            vec.remove(rand);
                            rand =0+ (int) (Math.random() * vec.size());
                            var_b.setText(questions.data.get(vopr % 5).answers.get((int)vec.get(rand)));
                            if ((int)vec.get(rand) == 0) idd = var_b.getId();
                            vec.remove(rand);
                            rand =0+ (int) (Math.random() * vec.size());
                            var_c.setText(questions.data.get(vopr % 5).answers.get((int)vec.get(rand)));
                            if ((int)vec.get(rand) == 0) idd = var_c.getId();
                            vec.remove(rand);
                            rand =0+ (int) (Math.random() * vec.size());
                            var_d.setText(questions.data.get(vopr % 5).answers.get((int)vec.get(rand)));
                            if ((int)vec.get(rand) == 0) idd = var_d.getId();
                            vec.remove(rand);
                            vopr++;
                        }
                    }
                    else if (vopr == 15) {
                        //ПАБЕДА
                        //?sleep
                        //проигрываю "pobeda"
                        finish();
                    }
                }
                else {
                    //устанавливаю красную кнопку
                    //проигрываю "nepravilno"
                    //?sleep
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
        var_a.setText(questions.data.get(vopr % 5).answers.get((int)vec.get(rand)));
        if ((int)vec.get(rand) == 0) idd = var_a.getId();
        vec.remove(rand);
        rand =0+ (int) (Math.random() * vec.size());
        var_b.setText(questions.data.get(vopr % 5).answers.get((int)vec.get(rand)));
        if ((int)vec.get(rand) == 0) idd = var_b.getId();
        vec.remove(rand);
        rand =0+ (int) (Math.random() * vec.size());
        var_c.setText(questions.data.get(vopr % 5).answers.get((int)vec.get(rand)));
        if ((int)vec.get(rand) == 0) idd = var_c.getId();
        vec.remove(rand);
        rand =0+ (int) (Math.random() * vec.size());
        var_d.setText(questions.data.get(vopr % 5).answers.get((int)vec.get(rand)));
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
}
