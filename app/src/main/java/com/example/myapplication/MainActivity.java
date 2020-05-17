package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity {

    int vopr = 1;
    boolean sound;
    Button var_a, var_b, var_c, var_d;
    ImageButton back, call, help, half;
    TextView vopros, summa;

    ProgressBar progressBar;
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

        progressBar = findViewById(R.id.progressBar2);

        QuestionRequest qr = new QuestionRequest();

        qr.count = 2;
        qr.qType = 3;

        progressBar.setVisibility(ProgressBar.VISIBLE);
        new RequestAsync().execute(qr);
    }

    private void OnResponse() {
        progressBar.setVisibility(ProgressBar.INVISIBLE);
        vopros.setText(questions.data.get(0).question);
        var_a.setText(questions.data.get(0).answers.get(0));
        var_b.setText(questions.data.get(0).answers.get(1));
        var_c.setText(questions.data.get(0).answers.get(2));
        var_d.setText(questions.data.get(0).answers.get(3));
    }

    private void OnFailure() {
        progressBar.setVisibility(ProgressBar.INVISIBLE);
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
