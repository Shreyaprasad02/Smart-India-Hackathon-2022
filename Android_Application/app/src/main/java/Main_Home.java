package com.example.pibapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.internal.Objects;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class Main_Home extends AppCompatActivity {

    LinearLayout home,settings,profile;

    private DatabaseReference mDatabase;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home);


        String Fin_id= "";
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(Main_Home.this);
        if(account != null) {
            String personEmail = account.getEmail();
            String[] id = personEmail.split("@");
             Fin_id = id[0].replace(".","_");

        }


        mDatabase = FirebaseDatabase.getInstance().getReference("User_language");



        mDatabase.child(Fin_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.getValue()  == null){
                    Intent i = new Intent(Main_Home.this, Lang_select.class);
                    startActivity(i);
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        if(mDatabase.child(Fin_id).get().getClass() == null){
            Intent i = new Intent(Main_Home.this, Lang_select.class);
            startActivity(i);
            finish();
        }


        getSupportFragmentManager().beginTransaction().replace(R.id.Fragment_container,new Home()).commit();
        home = findViewById(R.id.homebtn);
        settings = findViewById(R.id.imgbtn);
        profile = findViewById(R.id.profilebtn);
//        settings = findViewById(R.id.settingpage);



        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.Fragment_container,new Home()).commit();

            }
        });
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getSupportFragmentManager().beginTransaction().replace(R.id.Fragment_container,new Image_Fragment()).commit();

            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(Main_Home.this);
                if (acct != null) {
                    String personName = acct.getDisplayName();

                    getSupportFragmentManager().beginTransaction().replace(R.id.Fragment_container,new Profile_Fragment()).commit();

                }

                getSupportFragmentManager().beginTransaction().replace(R.id.Fragment_container,new Profile_Fragment()).commit();

//                getSupportFragmentManager().beginTransaction().replace(R.id.Fragment_container,new not_logged_profile()).commit();

            }
        });



    }

}