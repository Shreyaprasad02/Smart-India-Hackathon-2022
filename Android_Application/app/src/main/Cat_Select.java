package com.example.pibapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cat_Select extends AppCompatActivity {

    HashMap<String, Integer> sel = new HashMap<>();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("user_interests");


    CardView politics,economics,sports,research,social,defence,science,health,other;

    int total = 0;
    Button submit;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        sel.put("politics",0);
        sel.put("economics",0);
        sel.put("sports",0);
        sel.put("research",0);
        sel.put("social",0);
        sel.put("defence",0);
        sel.put("science",0);
        sel.put("health",0);
        sel.put("other",0);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.cat_select);
        politics = findViewById(R.id.politics);
        economics = findViewById(R.id.economics);
        sports = findViewById(R.id.sports);
        research = findViewById(R.id.research);
        social= findViewById(R.id.social);
        defence = findViewById(R.id.defence);
        science = findViewById(R.id.science);
        health = findViewById(R.id.health);
        other= findViewById(R.id.others);
        submit = findViewById(R.id.submitPref);


        View.OnClickListener l = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.politics:
                        if (sel.get("politics") == 0) {
                            politics.setCardElevation(20);

                            sel.put("politics",1);
                            total += 1;


                        } else {
                            politics.setCardElevation(0);

                            sel.put("politics",0);
                            total -= 1;
                        }
                        if(total >= 2){
                            submit.setEnabled(true);
                        }
                        else{
                            submit.setEnabled(false);
                        }
                        break;

                    case R.id.economics:
                        if (sel.get("economics") == 0) {
                            economics.setCardElevation(20);
                                                   sel.put("economics",1);
                            total += 1;
                            if(total >= 2){
                                submit.setEnabled(true);
                            }
                        } else {
                            economics.setCardElevation(0);
                            total -= 1;
                            sel.put("economics",0);


                        }
                        if(total >= 2){
                            submit.setEnabled(true);
                        }
                        else{
                            submit.setEnabled(false);
                        }
                        break;

                    case R.id.sports:
                        if (sel.get("sports") == 0) {
                            sports.setCardElevation(20);

                            sel.put("sports",1);
                            total += 1;
                            if(total >= 2){
                                submit.setEnabled(true);
                            }
                        } else {
                            sports.setCardElevation(0);

                            total -= 1;
                            sel.put("sports",0);

                        }
                        if(total >= 2){
                            submit.setEnabled(true);
                        }
                        else{
                            submit.setEnabled(false);
                        }
                        break;
                    case R.id.research:
                        if (sel.get("research") == 0) {
                            research.setCardElevation(20);

                            sel.put("research",1);
                            total += 1;

                        } else {
                            research.setCardElevation(0);
//                    Toast.makeText(Cat_Select.this, "2", Toast.LENGTH_SHORT).show();
                            total -= 1;
                            sel.put("research",0);

                        }
                        if(total >= 2){
                            submit.setEnabled(true);
                        }
                        else{
                            submit.setEnabled(false);
                        }
                        break;

                    case R.id.social:
                        if (sel.get("social") == 0) {
                            social.setCardElevation(20);

                            sel.put("social",1);
                            total += 1;

                        } else {
                            social.setCardElevation(0);
//                    Toast.makeText(Cat_Select.this, "2", Toast.LENGTH_SHORT).show();
                            total -= 1;
                            sel.put("social",0);

                        }
                        if(total >= 2){
                            submit.setEnabled(true);
                        }
                        else{
                            submit.setEnabled(false);
                        }
                        break;

                    case R.id.defence:
                        if (sel.get("defence")== 0) {
                            defence.setCardElevation(20);

                            sel.put("defence",1);
                            total += 1;

                        } else {
                            defence.setCardElevation(0);

                            total -= 1;

                            sel.put("defence",0);
                        }
                        if(total >= 2){
                            submit.setEnabled(true);
                        }
                        else{
                            submit.setEnabled(false);
                        }
                        break;
                    case R.id.science:
                        if (sel.get("science")== 0) {
                            science.setCardElevation(20);
                            total+=1;
                            sel.put("science",1);



                        } else {
                            science.setCardElevation(0);
//                    Toast.makeText(Cat_Select.this, "2", Toast.LENGTH_SHORT).show();
                            total -= 1;

                            sel.put("science",0);
                        }
                        if(total >= 2){
                            submit.setEnabled(true);
                        }
                        else{
                            submit.setEnabled(false);
                        }
                        break;
                    case R.id.health:
                        if (sel.get("health") == 0) {
                            health.setCardElevation(20);

                            sel.put("health",1);
                            total += 1;

                        } else {
                            health.setCardElevation(0);
//                    Toast.makeText(Cat_Select.this, "2", Toast.LENGTH_SHORT).show();
                            total -= 1;

                            sel.put("health",0);

                        }
                        if(total >= 2){
                            submit.setEnabled(true);
                        }

                        else{
                            submit.setEnabled(false);
                        }

                        break;
                    case R.id.others:
                        if (sel.get("other") == 0) {
                            other.setCardElevation(20);

                            sel.put("other",1);
                            total += 1;

                        } else {
                            other.setCardElevation(0);
//                    Toast.makeText(Cat_Select.this, "2", Toast.LENGTH_SHORT).show();

                            sel.put("other",0);
                            total -= 1;

                        }
                if(total >= 2){
                    submit.setEnabled(true);
                }
                else{
                    submit.setEnabled(false);
                }

                        break;
                }
            }


        };

        politics.setOnClickListener(l);
        economics.setOnClickListener(l);
        sports.setOnClickListener(l);
        research.setOnClickListener(l);
        social.setOnClickListener(l);
        defence.setOnClickListener(l);
        science.setOnClickListener(l);
        health.setOnClickListener(l);
        other.setOnClickListener(l);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                List<String> prefs=new ArrayList<String>();
                int i = 0;
                for (Map.Entry<String,Integer> mapElement : sel.entrySet()) {
                    String key = mapElement.getKey();

                    // Adding some bonus marks to all the students
                    int value = mapElement.getValue();
                    if(value == 1){
                       prefs.add(key);
                    }
                }

                GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(Cat_Select.this);
                if (acct != null) {

                    String personEmail = acct.getEmail();
                    String[] id = personEmail.split("@");
                    String Fin_id = id[0].replace(".","_");
                    myRef.child(Fin_id).setValue(prefs);
                }


            }
        });
    }


    @Override
    public void onBackPressed()
    {
        // code here to show dialog
        Intent i = new Intent(Cat_Select.this,Login.class);
        startActivity(i);
        finish();
    }
}
