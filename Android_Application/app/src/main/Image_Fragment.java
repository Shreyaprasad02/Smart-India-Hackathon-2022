package com.example.pibapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Image_Fragment extends Fragment {

    Context thiscontext;

    //    initilizing variables

    RecyclerView recyclerView;
    // Declare an adapter
    RecyclerView.Adapter ImageAdapter;
    RecyclerView.LayoutManager layoutmanager;

    //initialize Database__________________________________________________________
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("pib_images");

    List<String> urls = new ArrayList<>();
    List<String> TitleText = new ArrayList<>();

    Spinner date,mon,yr;

    String[] Dates = {"1","2","3","4","5","6","7","8","9","10"};
    String[] Months = {"January","February","March","April","May","June","July","August","September","October"};
    String[] Years = {"2019","2020","2021","2022"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.images_fragment,container,false);
        thiscontext = container.getContext();

        date = root.findViewById(R.id.date);
        mon = root.findViewById(R.id.month);
        yr = root.findViewById(R.id.year);


        date.setAdapter(new ArrayAdapter<String>(getContext(),R.layout.spinner_list,Dates));
        mon.setAdapter(new ArrayAdapter<String>(getContext(),R.layout.spinner_list,Months));
        yr.setAdapter(new ArrayAdapter<String>(getContext(),R.layout.spinner_list,Years));

        date.setDropDownVerticalOffset(70);
        mon.setDropDownVerticalOffset(70);
       yr.setDropDownVerticalOffset(70);

       String y = yr.getSelectedItem().toString();
       String m = mon.getSelectedItem().toString();
       String d = date.getSelectedItem().toString();


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                TitleText.clear();
                urls.clear();

                for(DataSnapshot snap : snapshot.getChildren()){



                    String title = snap.child("Title").getValue().toString();
                    String url = snap.child("url").getValue().toString();




                    TitleText.add(title);
                    urls.add(url);

                    Collections.reverse(TitleText);
                    Collections.reverse(urls);


                    recyclerView = root.findViewById(R.id.rvImages);
                    // You may use this setting to improve performance if you know that changes
                    // in content do not change the layout size of the RecyclerView
                    recyclerView.setHasFixedSize(true);
                    // Use a linear layout manager
                    layoutmanager = new LinearLayoutManager(getContext());
                    recyclerView.setLayoutManager(layoutmanager);
                    // Create an instance of ProgramAdapter. Pass context and all the array elements to the constructor
                    ImageAdapter = new com.example.pibapp.ImageAdapter(thiscontext, TitleText,urls);
//                    // Finally, attach the adapter with the RecyclerView
                    ImageAdapter.notifyDataSetChanged();
//
////                            Toast.makeText(getContext(), "hello", Toast.LENGTH_SHORT).show();
                    recyclerView.setAdapter(ImageAdapter);


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return root;
    }
}
