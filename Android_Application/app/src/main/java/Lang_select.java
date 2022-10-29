package com.example.pibapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Lang_select extends AppCompatActivity {


    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("User_language");


    CardView English,Hindi,Marathi,Urdu,Bengali,Gujarati,telugu;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.language_selector);

        English = findViewById(R.id.English);
        Hindi = findViewById(R.id.Hindi);
        Marathi = findViewById(R.id.Marathi);
        Urdu = findViewById(R.id.Urdu);
        Bengali = findViewById(R.id.Bengali);
        Gujarati = findViewById(R.id.Gujarati);
        telugu = findViewById(R.id.telugu);


        View.OnClickListener l= new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Fin_id = "";
                GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(Lang_select.this);
                if (acct != null) {

                    String personEmail = acct.getEmail();
                    String[] id = personEmail.split("@");
                     Fin_id = id[0].replace(".","_");

                }



                switch (view.getId()){
                    case R.id.English:

                        myRef.child(Fin_id).setValue("English");
                        break;
                    case R.id.Hindi:
                        myRef.child(Fin_id).setValue("Hindi");

                        break;
                    case R.id.Marathi:
                        myRef.child(Fin_id).setValue("Marathi");

                        break;

                    case R.id.Urdu:
                        myRef.child(Fin_id).setValue("Urdu");

                        break;
                    case R.id.Gujarati:
                        myRef.child(Fin_id).setValue("Gujarati");

                        break;
                    case R.id.telugu:
                        myRef.child(Fin_id).setValue("Telugu");

                        break;
                    case R.id.Bengali:
                        myRef.child(Fin_id).setValue("Bengali");

                        break;
                }

                Intent i = new Intent(Lang_select.this,Main_Home.class);
                startActivity(i);
                finish();
            }
        };

        English.setOnClickListener(l);
        Hindi.setOnClickListener(l);
        Marathi.setOnClickListener(l);
        Urdu.setOnClickListener(l);
        Bengali.setOnClickListener(l);
        telugu.setOnClickListener(l);
        Gujarati.setOnClickListener(l);




    }
}
