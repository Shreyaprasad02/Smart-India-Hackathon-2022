package com.example.pibapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class News extends Fragment {
    Context thiscontext;

    TextView ministry,posted_by,matter;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("News_pib");

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        thiscontext = container.getContext();
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.activity_news,container,false);

//        ministry = root.findViewById(R.id.ministry);
//        posted_by = root.findViewById(R.id.Posted_by);
//        matter = root.findViewById(R.id.matter);
        Bundle b = this.getArguments();
        String value = ""; // or other values
        String id = "";
        if(b != null){

            value = b.getString("val");
            id = b.getString("key");
        }

        myRef.child(value).child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ministry.setText(snapshot.child("Ministry").getValue().toString());
                posted_by.setText(snapshot.child("Posted by").getValue().toString());
                matter.setText(snapshot.child("Description").getValue().toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return root;
    }
}
