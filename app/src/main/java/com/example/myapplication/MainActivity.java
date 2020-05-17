package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

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
    QuestionData[] questionData;
    public static final String LOG_TAG = "Main_Activity";

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

        questionData=new QuestionData[5];

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://engine.lifeis.porn")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Database database = retrofit.create(Database.class);
        Call<QuestionDataList> call = (Call<QuestionDataList>) database.data(1, 5);

        call.enqueue(new Callback<QuestionDataList>() {

            @Override
            public void onResponse(Call<QuestionDataList> call, Response<QuestionDataList> response) {
                if (response.isSuccessful()) {
                    QuestionDataList lists = response.body();
                    for(int i=0;i<5;i++){
                        questionData[i] = lists.data.get(i);
                        i++;
                    }
                    Log.d(LOG_TAG, "response " + response.body().data.size());
                } else {
                    Log.d(LOG_TAG, "response code " + response.code());
                }
            }

            @Override
            public void onFailure(Call<QuestionDataList> call, Throwable t) {
                Log.e(LOG_TAG, "failure" + t);
            }
        });
        try {
            sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //while(vopr<=15){
        if(questionData[0]!=null) {
            vopros.setText(questionData[0].question);
        }
        else{
            vopros.setText("null");
        }
        /*var_a.setText(questionData[0].answers[0]);
        var_b.setText(questionData[0].answers[1]);
        var_c.setText(questionData[0].answers[2]);
        var_d.setText(questionData[0].answers[3]);*/
        //break;
    }
}
