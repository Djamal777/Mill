package com.example.myapplication;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static android.content.ContentValues.TAG;

public class Congratulations extends AppCompatActivity {

    int money;
    TextView congr;
    EditText edit;
    Button go, no;
    FirebaseFirestore db;
    public List<Man> menn;
    String symbols = "abcdefghijklmnopqrstuvwxyz";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.congratulations);
        congr=(TextView)findViewById(R.id.congr);
        edit=(EditText)findViewById(R.id.editTextTextPersonName);
        go=(Button)findViewById(R.id.gotovo);
        no=(Button)findViewById(R.id.otmena);
        congr.setText("Поздравляем! Вы вошли в топ-5 игроков! Введите свое имя.");
        db=FirebaseFirestore.getInstance();
        menn=new ArrayList<>();
        Bundle extras = getIntent().getExtras();
        for(int i=0;i<5;i++){
            Man man=new Man();
            menn.add(man);
        }
        Comparator<Man> comparator=new ManCompare();
        db.collection("top").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            Log.d(TAG, "list size " + list.size());
                            int i = 0;
                            for (DocumentSnapshot d : list) {
                                Man m = d.toObject(Man.class);
                                menn.get(i).setId(d.getId());
                                menn.get(i).setName(m.getName());
                                menn.get(i).setMoney(m.getMoney());
                                i++;
                            }
                            for (int k = 0; k < 4; k++) {
                                for (int j = 1; j < 5; j++) {
                                    if (menn.get(k).getMoney() < menn.get(j).getMoney()) {
                                        Collections.swap(menn, k, j);
                                    }
                                }
                            }
                            menn.sort(comparator);
                        }
                    }
                });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(1);
                finish();
            }
        });
        go.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                Log.d(TAG,edit.getText().toString());
                if(edit.getText().toString().length()>=2){
                    CollectionReference collRef = db.collection("top");
                    DocumentReference pdfRef = collRef.document(menn.get(4).getId());
                    pdfRef.delete();
                    money=extras.getInt("деньга");
                    String random = new Random().ints(15, 0, symbols.length())
                            .mapToObj(symbols::charAt)
                            .map(Object::toString)
                            .collect(Collectors.joining());
                    Man man=new Man(edit.getText().toString(),money,random);
                    db.collection("top").document(random).set(man);
                    setResult(1);
                    finish();
                }
            }
        });

    }
}
