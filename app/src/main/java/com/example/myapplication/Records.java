package com.example.myapplication;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

import static android.content.ContentValues.TAG;

public class Records extends AppCompatActivity {

    FirebaseFirestore db;
    TextView name1, name2, name3, name4, name5, money1, money2, money3, money4, money5;
    ImageButton exit;
    public List<Man> menn;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.records);
        exit=(ImageButton)findViewById(R.id.exit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        db=FirebaseFirestore.getInstance();
        menn=new ArrayList<>();
        for(int i=0;i<5;i++){
            Man man=new Man();
            menn.add(man);
        }
        name1=(TextView) findViewById(R.id.name1);
        name2=(TextView) findViewById(R.id.name2);
        name3=(TextView) findViewById(R.id.name3);
        name4=(TextView) findViewById(R.id.name4);
        name5=(TextView) findViewById(R.id.name5);
        money1=(TextView) findViewById(R.id.money1);
        money2=(TextView) findViewById(R.id.money2);
        money3=(TextView) findViewById(R.id.money3);
        money4=(TextView) findViewById(R.id.money4);
        money5=(TextView) findViewById(R.id.money5);

        Comparator<Man> comparator=new ManCompare();

        db.collection("top").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(!queryDocumentSnapshots.isEmpty()){
                            List<DocumentSnapshot> list=queryDocumentSnapshots.getDocuments();
                            Log.d(TAG,"list size "+list.size());
                            int i=0;
                            for(DocumentSnapshot d: list){
                                Man m=d.toObject(Man.class);
                                menn.get(i).setName(m.getName());
                                menn.get(i).setMoney(m.getMoney());
                                i++;
                            }
                            for(int k=0;k<4;k++){
                                for(int j=1;j<5;j++){
                                    if(menn.get(k).getMoney()<menn.get(j).getMoney()){
                                        Collections.swap(menn,k,j);
                                    }
                                }
                            }
                            menn.sort(comparator);
                            name1.setText(String.valueOf(menn.get(0).getName()));
                            name2.setText(String.valueOf(menn.get(1).getName()));
                            name3.setText(String.valueOf(menn.get(2).getName()));
                            name4.setText(String.valueOf(menn.get(3).getName()));
                            name5.setText(String.valueOf(menn.get(4).getName()));
                            money1.setText(String.valueOf(menn.get(0).getMoney()));
                            money2.setText(String.valueOf(menn.get(1).getMoney()));
                            money3.setText(String.valueOf(menn.get(2).getMoney()));
                            money4.setText(String.valueOf(menn.get(3).getMoney()));
                            money5.setText(String.valueOf(menn.get(4).getMoney()));
                        }
                    }
                });
    }
}