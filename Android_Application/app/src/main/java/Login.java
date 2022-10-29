package com.example.pibapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;


public class Login extends AppCompatActivity {

    private static final int RC_SIGN_IN = 100;
    // Configure sign-in to request the user's ID, email address, and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.
    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build();

    GoogleSignInClient mGoogleSignInClient;


    private DatabaseReference mDatabase;
// ...



    Button email,skip;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

//        create database variable

        mDatabase = FirebaseDatabase.getInstance().getReference("user_interests");


        // Check for existing Google Sign In account, if the user is already signed in
// the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account != null) {
            String personEmail = account.getEmail();
            String[] id = personEmail.split("@");
            String Fin_id = id[0].replace(".","_");


            updateUI(Fin_id);
        }

        email = findViewById(R.id.email_login_redirect);
        skip = findViewById(R.id.skip_login);
//        google = findViewById(R.id.google_signin);
        SignInButton google = findViewById(R.id.google_signin);

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();



            }


        });





        View.OnClickListener l = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch(view.getId()){
                    case R.id.skip_login:
                        Intent i =  new Intent(Login.this,Main_Home.class);
                        startActivity(i);
                        finish();
                        break;


                    case R.id.email_login_redirect:
                        Intent email =  new Intent(Login.this,Email_Login.class);
                        startActivity(email);

                        break;
                }
            }
        };

        email.setOnClickListener(l);
        skip.setOnClickListener(l);

    }

    @Override
    public void onBackPressed() {
        System.exit(0);
    }

    private void updateUI(String Fin_id) {


        redirects(Fin_id,2);

    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);






        }
    }


    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
            if (acct != null) {

                String personEmail = acct.getEmail();
                String[] id = personEmail.split("@");
                String Fin_id = id[0].replace(".","_");

                redirects(Fin_id,1);


            }

            // Signed in successfully, show authenticated UI.

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            System.out.println("Error occurred here");
            e.printStackTrace();
        }
    }

    private void redirects(String Fin_id ,int id ) {

        try {
            mDatabase.child(Fin_id).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    List<String> a = (List<String>) snapshot.getValue();

                    if(a != null){
                        System.out.print("Happened");
                        Intent i = new Intent(Login.this,Main_Home.class);
                        startActivity(i);
                        finish();
                    }
                    else if(id == 1){
                        Intent i = new Intent(Login.this,Cat_Select.class);
                        startActivity(i);
                        finish();
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.d("Error", "onCancelled: error happened  ");
                }
            });




        }

        catch (Exception e){
            e.printStackTrace();
        }


    }
}



