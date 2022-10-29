package com.example.pibapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.net.URISyntaxException;

public class NewsActivity extends AppCompatActivity {
    TextView ministry,posted_by,matter;
    WebView web;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("News_pib");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
//
//        ministry = findViewById(R.id.ministry);
//        posted_by = findViewById(R.id.Posted_by);
//        matter = findViewById(R.id.matter);
        Bundle b = getIntent().getExtras();
        String value = ""; // or other values
        String id = "";
//        Toast.makeText(this, b.toString(), Toast.LENGTH_SHORT).show();
        if(b != null){

            value = b.getString("val");
            id = b.getString("key");
        }
//
//
//
//        myRef.child(value).child(id).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                ministry.setText(snapshot.child("Ministry").getValue().toString());
//                posted_by.setText("Posted by "+snapshot.child("Posted by").getValue().toString() +" On "+snapshot.child("Date").getValue().toString());
//                matter.setText(snapshot.child("Description").getValue().toString());
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//

        web = findViewById(R.id.webView);
        // Create a WebSettings object
        WebSettings webSettings = web.getSettings();
        // For using Zoom feature
        // webSettings.setBuiltInZoomControls(true);
        // Enable JavaScript using the WebSettings object
        webSettings.setJavaScriptEnabled(true);
        /*
         Now, when the user clicks a link from a web page in your WebView, the
         default behavior for Android is to launch an application that handles
         URLs. Usually, the default web browser opens and loads the
         destination URL. However, you can override this behavior for your
         WebView, so that links open within your WebView. You can then allow the
         user to navigate backward and forward through their web page history
         that's maintained by your WebView. To open links clicked by the user,
         simply provide a WebViewClient for your WebView, using
         setWebViewClient(). Now all links the user clicks load in your WebView. If you want more control
         over where a clicked link load, create your own WebViewClient that
         overrides the shouldOverrideUrlLoading() method.
         */
        web.setWebViewClient(new Callback());
        web.loadUrl("https://www.pib.gov.in/PressReleasePage.aspx?PRID="+id);
    }

    private class Callback extends WebViewClient {
        @Override
        public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
            // By returning false you're telling Android that, this is my website, so, do not override;
            // let WebView load the page.
            return false;
        }

        /*
        Additional code.
        Sometimes the WebView can't deal with special URL scheme other than http or
        https, for example intent://, app://, tel:, mailto:, whatsapp:// etc.
        So, we are required to override shouldOverrideUrlLoading method in
        the custom WebViewClient class that we created, called Callback, and
        handle these schemes or to disable these schemes. The solution that works
        in your case is given below. You can add or remove few if blocks if you need to.
        Enjoy!
         */
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            // Open some links (it can be external links also) on Chrome
            // or any default web browser.
            // For example: following code will open contact.php
            // in Chrome or any default web browser of your mobile.
            /*if (url.equalsIgnoreCase("http://sandipBhattacharya.com/contact.php") || url.equalsIgnoreCase("https://sandipbhattacharya.com/contact.php")) {
                view.getContext().startActivity(
                        new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                return true;
            } else{
                return false;
            }
            */
            if (url.startsWith("intent")) {
                try {
                    Intent intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                    String fallbackUrl = intent.getStringExtra("browser_fallback_url");
                    if (fallbackUrl != null) {
                        view.loadUrl(fallbackUrl);
                        return true;
                    }
                } catch (URISyntaxException e) {
                    // Syntax problem with uri
                }
            }
            if (url.startsWith("tel:")) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
                startActivity(intent);
            } else if (url.startsWith("mailto:")) {
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse(url));
                startActivity(intent);
            }
            view.loadUrl(url);
            return false;
        }

    }

    }
