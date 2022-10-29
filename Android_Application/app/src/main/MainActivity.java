package com.example.pibapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("Messagging", "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        // Log and toast
//                        String msg = getString(R.string.msg_token_fmt, token);
                        Log.d("Token", token);
//                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });



        Thread thread = new Thread(){

            @SuppressLint("ResourceType")
            public void run(){
                try {
                    sleep(2000);

                }catch (Exception e){
                    e.printStackTrace();
                }
                finally {

                    GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(MainActivity.this);

                    if (acct == null) {


                        Intent intent = new Intent(MainActivity.this,Login.class);
                        startActivity(intent);

                    }
                    else{
                        Intent intent = new Intent(MainActivity.this,Main_Home.class);
                        startActivity(intent);
                    }



//

                }
                finish();
            }
        };thread.start();

    }
}