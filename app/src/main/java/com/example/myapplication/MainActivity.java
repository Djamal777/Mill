package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Vector;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class MainActivity extends AppCompatActivity {

    int h50_50, nepravilno, otvet, pobeda, pravilno;
    int vopr = 0;
    static int idd;
    int[] money;
    int id_a, id_b, id_c, id_d;
    int sound;
    char ch;
    ImageButton back, call, help, half, var_a, var_b, var_c, var_d;
    TextView vopros, summa, zvonok, a, b, c, d;
    SoundPool soundpool_main;
    MediaPlayer mp;
    String prav;
    String[] otveti = {"Мне кажется, правильный ответ ", "Я уверен, что ", "Хмм.. Я скажу ", "Точно не знаю, но по-моему "};

    public static final String LOG_TAG = "Main_Activity";

    private QuestionResponse questions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sound = getIntent().getIntExtra("a", 0);

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
        zvonok = (TextView) findViewById(R.id.otvett);

        a = (TextView) findViewById(R.id.textView1);
        b = (TextView) findViewById(R.id.textView2);
        c = (TextView) findViewById(R.id.textView3);
        d = (TextView) findViewById(R.id.textView4);
        id_a = var_a.getId();
        id_b = var_b.getId();
        id_c = var_c.getId();
        id_d = var_d.getId();

        money = new int[]{500, 1000, 2000, 3000, 5000, 10000, 15000, 25000, 50000, 100000, 200000, 400000, 800000, 1500000, 3000000};

        soundpool_main = new SoundPool(6, AudioManager.STREAM_MUSIC, 0);
        soundpool_main.setOnLoadCompleteListener(this::onLoadComplete);
        mp = MediaPlayer.create(this, R.raw.igra);
        mp.setLooping(true);
        h50_50 = soundpool_main.load(this, R.raw.h50_50, 1);
        nepravilno = soundpool_main.load(this, R.raw.nepravilno, 1);
        otvet = soundpool_main.load(this, R.raw.otvet, 1);
        pobeda = soundpool_main.load(this, R.raw.pobeda, 1);
        pravilno = soundpool_main.load(this, R.raw.pravilno, 1);
        if (sound % 2 == 0) {
            mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                }
            });
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                }
            });
        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mp.isPlaying()) mp.stop();
                finish();
            }
        });
        half.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.pause();
                soundpool_main.play(h50_50, 1, 1, 0, 0, 1);
                half.setImageResource(R.drawable.knopka_50_nazh);
                half.setClickable(false);
                half.setEnabled(false);
                Vector otv = new Vector();
                if (ch != 'A') otv.add(0);
                if (ch != 'B') otv.add(1);
                if (ch != 'C') otv.add(2);
                if (ch != 'D') otv.add(3);
                for (int i = 0; i < 2; i++) {
                    int r = 0 + (int) (Math.random() * otv.size());
                    switch ((int) otv.get(r)) {
                        case 0:
                            var_a.setEnabled(false);
                            a.setText("");
                            break;
                        case 1:
                            var_b.setEnabled(false);
                            b.setText("");
                            break;
                        case 2:
                            var_c.setEnabled(false);
                            c.setText("");
                            break;
                        case 3:
                            var_d.setEnabled(false);
                            d.setText("");
                            break;
                    }
                    otv.remove(r);
                    mp.start();
                }
            }
        });
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call.setEnabled(false);
                call.setClickable(false);
                call.setImageResource(R.drawable.knopka_zvonok_nazh);
                int r = 0 + (int) (Math.random() * 4);
                zvonok.setText(otveti[r] + ch);
            }
        });
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                help.setEnabled(false);
                help.setClickable(false);
                help.setImageResource(R.drawable.knopka_pomosh_nazh);
                int[] mas = new int[4];
                mas[0] = 50 + (int) (Math.random() * 25);
                mas[1] = 0 + (int) (Math.random() * (100 - mas[0]));
                mas[2] = 0 + (int) (Math.random() * (100 - mas[0] - mas[1]));
                mas[3] = 100 - mas[0] - mas[1] - mas[2];
                String s="";
                switch (ch) {
                    case 'A':
                        s = "A - " + mas[0] + "%\n" + "B - " + mas[1] + "%\n" + "C - " + mas[2] + "%\n" + "D - " + mas[3] + "%";
                        break;
                    case 'B':
                        s = "A - " + mas[1] + "%\n" + "B - " + mas[0] + "%\n" + "C - " + mas[2] + "%\n" + "D - " + mas[3] + "%";
                        break;
                    case 'C':
                        s = "A - " + mas[1] + "%\n" + "B - " + mas[2] + "%\n" + "C - " + mas[0] + "%\n" + "D - " + mas[3] + "%";
                        break;
                    case 'D':
                        s = "A - " + mas[3] + "%\n" + "B - " + mas[1] + "%\n" + "C - " + mas[2] + "%\n" + "D - " + mas[0] + "%";
                        break;
                }
                zvonok.setText(s);
            }
        });
        QuestionRequest qr = new QuestionRequest();
        qr.qType = 1;
        qr.count = 5;
        new RequestAsync().execute(qr);
        new Thread(() -> {
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                var_a.setEnabled(false);
                var_b.setEnabled(false);
                var_c.setEnabled(false);
                var_d.setEnabled(false);
                half.setEnabled(false);
                call.setEnabled(false);
                help.setEnabled(false);
                int id = v.getId();
                new Thread(() -> {
                    if (id == id_a) var_a.setImageResource(R.drawable.knopka_yellow);
                    else if (id == id_b) var_b.setImageResource(R.drawable.knopka_yellow);
                    else if (id == id_c) var_c.setImageResource(R.drawable.knopka_yellow);
                    else if (id == id_d) var_d.setImageResource(R.drawable.knopka_yellow);
                    if (sound % 2 == 0) {
                        mp.pause();
                        soundpool_main.play(otvet, 1, 1, 0, 0, 1);
                    }
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (sound % 2 == 0) {
                        soundpool_main.stop(otvet);
                    }
                    if (id == idd) {
                        if (id == id_a) var_a.setImageResource(R.drawable.knopka_green);
                        else if (id == id_b) var_b.setImageResource(R.drawable.knopka_green);
                        else if (id == id_c) var_c.setImageResource(R.drawable.knopka_green);
                        else if (id == id_d) var_d.setImageResource(R.drawable.knopka_green);
                        if (vopr < 15) {
                            if (sound % 2 == 0) {
                                soundpool_main.play(pravilno, 1, 1, 0, 0, 1);
                            }
                            try {
                                Thread.sleep(3000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            if (vopr == 4) {
                                QuestionRequest qr = new QuestionRequest();
                                qr.count = 5;
                                qr.qType = 2;
                                new RequestAsync().execute(qr);
                            } else if (vopr == 9) {
                                QuestionRequest qr = new QuestionRequest();
                                qr.count = 5;
                                qr.qType = 3;
                                new RequestAsync().execute(qr);
                            }
                            if (sound % 2 == 0) {
                                mp.start();
                            }
                            if (id == id_a) var_a.setImageResource(R.drawable.knopka_blue);
                            else if (id == id_b) var_b.setImageResource(R.drawable.knopka_blue);
                            else if (id == id_c) var_c.setImageResource(R.drawable.knopka_blue);
                            else if (id == id_d) var_d.setImageResource(R.drawable.knopka_blue);
                        } else if (vopr == 15) {
                            zvonok.setText("ВЫ ВЫИГРАЛИ 3 МИЛЛИОНА РУБЛЕЙ!");
                            if (sound % 2 == 0) {
                                soundpool_main.play(pobeda, 1, 1, 0, 0, 1);
                            }
                            try {
                                Thread.sleep(3000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            finish();
                        }
                    } else {
                        if (id == id_a) var_a.setImageResource(R.drawable.knopka_red);
                        else if (id == id_b) var_b.setImageResource(R.drawable.knopka_red);
                        else if (id == id_c) var_c.setImageResource(R.drawable.knopka_red);
                        else if (id == id_d) var_d.setImageResource(R.drawable.knopka_red);
                        if (idd == id_a) var_a.setImageResource(R.drawable.knopka_green);
                        else if (idd == id_b) var_b.setImageResource(R.drawable.knopka_green);
                        else if (idd == id_c) var_c.setImageResource(R.drawable.knopka_green);
                        else if (idd == id_d) var_d.setImageResource(R.drawable.knopka_green);
                        if (sound % 2 == 0) {
                            soundpool_main.play(nepravilno, 1, 1, 0, 0, 1);
                        }
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        finish();
                    }
                }).start();
                if (id == idd && vopr < 14 && vopr != 4 && vopr != 9) {
                    new CountDownTimer(5000, 1000) {
                        public void onTick(long millisUntilFinished) {
                        }

                        public void onFinish() {
                            summa.setText(vopr + 1 + ". " + money[vopr]);
                            vopros.setText(questions.data.get(vopr % 5).question);
                            Vector vec = new Vector();
                            vec.add(0);
                            vec.add(1);
                            vec.add(2);
                            vec.add(3);
                            int rand = 0 + (int) (Math.random() * vec.size());
                            a.setText("A. " + questions.data.get(vopr % 5).answers.get((int) vec.get(rand)));
                            if ((int) vec.get(rand) == 0) {
                                idd = var_a.getId();
                                prav = a.toString();
                                ch = 'A';
                            }
                            vec.remove(rand);
                            rand = 0 + (int) (Math.random() * vec.size());
                            b.setText("B. " + questions.data.get(vopr % 5).answers.get((int) vec.get(rand)));
                            if ((int) vec.get(rand) == 0) {
                                idd = var_b.getId();
                                prav = b.toString();
                                ch = 'B';
                            }
                            vec.remove(rand);
                            rand = 0 + (int) (Math.random() * vec.size());
                            c.setText("C. " + questions.data.get(vopr % 5).answers.get((int) vec.get(rand)));
                            if ((int) vec.get(rand) == 0) {
                                idd = var_c.getId();
                                prav = c.toString();
                                ch = 'C';
                            }
                            vec.remove(rand);
                            rand = 0 + (int) (Math.random() * vec.size());
                            d.setText("D. " + questions.data.get(vopr % 5).answers.get((int) vec.get(rand)));
                            if ((int) vec.get(rand) == 0) {
                                idd = var_d.getId();
                                prav = d.toString();
                                ch = 'D';
                            }
                            vec.remove(rand);
                            vopr++;
                            var_a.setEnabled(true);
                            var_b.setEnabled(true);
                            var_c.setEnabled(true);
                            var_d.setEnabled(true);
                            half.setEnabled(true);
                            call.setEnabled(true);
                            help.setEnabled(true);
                            if(zvonok.toString()!="") zvonok.setText("");
                        }
                    }.start();
                }
            }
        };
        var_a.setOnClickListener(listener);
        var_b.setOnClickListener(listener);
        var_c.setOnClickListener(listener);
        var_d.setOnClickListener(listener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mp.isPlaying()) {
            mp.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (sound % 2 == 0) {
            mp.start();
        }
    }

    private void OnResponse() {
        summa.setText(vopr + 1 + ". " + money[vopr]);
        vopros.setText(questions.data.get(vopr % 5).question);
        Vector vec = new Vector();
        vec.add(0);
        vec.add(1);
        vec.add(2);
        vec.add(3);
        int rand = 0 + (int) (Math.random() * vec.size());
        a.setText("A. " + questions.data.get(vopr % 5).answers.get((int) vec.get(rand)));
        if ((int) vec.get(rand) == 0) {
            idd = var_a.getId();
            prav = a.toString();
            ch = 'A';
        }
        vec.remove(rand);
        rand = 0 + (int) (Math.random() * vec.size());
        b.setText("B. " + questions.data.get(vopr % 5).answers.get((int) vec.get(rand)));
        if ((int) vec.get(rand) == 0) {
            idd = var_b.getId();
            prav = b.toString();
            ch = 'B';
        }
        vec.remove(rand);
        rand = 0 + (int) (Math.random() * vec.size());
        c.setText("C. " + questions.data.get(vopr % 5).answers.get((int) vec.get(rand)));
        if ((int) vec.get(rand) == 0) {
            idd = var_c.getId();
            prav = c.toString();
            ch = 'C';
        }
        vec.remove(rand);
        rand = 0 + (int) (Math.random() * vec.size());
        d.setText("D. " + questions.data.get(vopr % 5).answers.get((int) vec.get(rand)));
        if ((int) vec.get(rand) == 0) {
            idd = var_d.getId();
            prav = d.toString();
            ch = 'D';
        }
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

    @Override
    public void onBackPressed() {
        mp.stop();
        finish();
    }
}