package com.example.pibapp;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.Task;
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

public class HomeNLI extends Fragment {

    Context thiscontext;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("News_pib");
    DatabaseReference lang = database.getReference();

    //    initilizing variables
    Spinner region,language;
    String PIB;
    RecyclerView recyclerView;
    // Declare an adapter
    RecyclerView.Adapter programAdapter;
    RecyclerView.LayoutManager layoutmanager;

    List<String> nids = new ArrayList<>();
    List<String> TitleText = new ArrayList<>();
    List<String> DescText = new ArrayList<>();
    List<String> languages = new ArrayList<>();

    //                init categories

    String Research[] = {"RE AICTE, MIC-Student Innovation","SC Department of Space, Indian Space Research Organisation (ISRO)","India Meteorological Department (IMD), Ministry of Earth Sciences (MoES)"," Indian National Centre for Ocean Information Services (INCOIS), Ministry of Earth Sciences (MoES)"};
    String Education[] = {"RE All India Council for Technical Education (AICTE)","ED Department of School Education Literacy (DoSEL), Ministry of Education"," Indian Knowledge System (IKS), All India Council for Technical Education (AICTE)","Indian Knowledge System (IKS), All India Council for Technical Education (AICTE)",
            "MathWorks","National Assessment and Accreditation Council (NAAC)","National Council of Educational Research and Training (NCERT), Department of School Education Literacy (DoSEL)","ational Digital Education Architecture (NDEAR), Department of School Education Literacy (DoSEL)","National Institute of Design Madhya Pradesh",
            "University Grants Commission (UGC)"};
    String social[] = {"CW Central Ground Water Board (CGWB), Ministry of Jal Shakti","CW Central Water Commission (CWC), Ministry of Jal Shakti"," SW Dairy Division, Department of Animal Husbandry Dairying, Ministry of Fisheries, Animal Husbandry Dairying",
            "DE Defence Research and Development Organisation (DRDO), Ministry of Defence.","SW Department of Empowerment of Persons with Disabilities(Divyangjan) , Ministry of Social Justice and Empowerment","SW Department of Food, Public Distribution, Ministry of Consumer Affairs Food and Public Distribution",
            "SW Department of Social Justice Empowerment, Ministry of Social Justice and Empowerment","Ministry of Culture","Ministry of Housing and Urban Affairs","Ministry of Labour Employment","Ministry of Rural Development","Ministry of Skill Development and Entrepreneurship (MSDE)",
            "Ministry of Skill Development and Entrepreneurship (MSDE)","National Institute for Empowerment of Persons with Multiple Disabilities (NIEPMD), Ministry of Social Justice Empowerment","National Mission  for Clean Ganga (NMCG), Ministry of Jal Shakti","National Water Mission (NWM), Ministry of Jal Shakti",
            "NIFTEM Thanjavur ,Ministry of Food Processing Industries (MoFPI)","Swachh Bharat Mission (Grameen), Department of Drinking Water Sanitation, Ministry of Jal Shakti"};
    String Science[]={"RE Communication and Information service, MHA","RE Dell Technologies, Bangalore, India","SC Department of Science Technology (DoST), Ministry of Science and Technology","Ministry of Electronics and Information Technology (MEITY)","National Institute of Ocean Technology (NIOT), Ministry of Earth Sciences (MoES)",
            "Policy Planning Cell, Ministry of Information Broadcasting"};
    String Defence[] = {"DE Cyber Crime Unit, Special Cell, Delhi Police, MHA","Department of Telecommunications (DoT), Ministry of Communications","ITBP, MHA","Madhya Pradesh Police"," National Disaster Response Force (NDRF)"};
    String Other[]={"SW Department of Post, Ministry of Post","eCourts, Department of Justice, Ministry of Law &amp; Justice","Gas Authority of India Ltd (GAIL)"," Indian Council for Cultural Relations (ICCR)","Ministry of Chemicals and Fertilizers","Ministry of MinesMinistry of Mines","Ministry of Tourism","NATGRID, MHA",
            "North Eastern Cane and Bamboo Development Council (NECBDC)"," Volvo","White Hour Solutions"};
    String sports[]={"SPO Department of Sports (DoS), Ministry of Youth Affairs  Sports"};
    String health[] = {"GE Healthcare","MIC","Ministry of Ayurveda, Yoga, Naturopathy, Unani, Siddha, Sowa-Rigpa and Homoeopathy (AYUSH)","Narcotics Control Bureau (NCB)"};
    String politics[]={"Government of Maharashtra","Government of Rajasthan","Government of Uttarakhand","Ministry of External Affairs (MEA)"};
    String economy[]= {"Indian National Centre for Ocean Information Services (INCOIS), Ministry of Earth Sciences (MoES)","Ministry of Consumer Affairs","Ministry of Finance","Ministry of Micro, Small Medium Enterprises (MSME)","Ministry of Ports, Shipping, Waterways",
            "Startup India Section, Department for Promotion of Industry and Internal Trade (DPIT), Ministry of Commerce and Industry"};


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
     ViewGroup root = (ViewGroup) inflater.inflate(R.layout.home_nli,container,false);
        thiscontext = container.getContext();


        try {
            String language = "";



            GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(thiscontext);
            if(account != null) {

                String personEmail = account.getEmail();
                String[] id = personEmail.split("@");
                String Fin_id = id[0].replace(".","_");


                lang.child("User_language").child(Fin_id).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



            }



            myRef.child("English").addValueEventListener(new ValueEventListener() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    TitleText.clear();
                    DescText.clear();
                    nids.clear();
                    languages.clear();
                    for(DataSnapshot snap : dataSnapshot.getChildren()){


                        String title = snap.child("Title").getValue().toString();
                        String date = snap.child("Date").getValue().toString();
                        String time = snap.child("Time").getValue().toString();
                        String ministry = snap.child("Ministry").getValue().toString();

                        LocalDateTime myDateObj = LocalDateTime.now();

                        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd MMM yyyy");

                        String formattedDate = myDateObj.format(myFormatObj).toUpperCase();

                        if (date.equals(formattedDate)) {
                            TitleText.add(title);
                            DescText.add(date+" "+time);
                            nids.add(snap.getKey());

                        }



                    }

                    Collections.reverse(TitleText);
                    Collections.reverse(DescText);
                    Collections.reverse(nids);
                    Collections.reverse(languages);


                    recyclerView = root.findViewById(R.id.rvProgram);
                    // You may use this setting to improve performance if you know that changes
                    // in content do not change the layout size of the RecyclerView
                    recyclerView.setHasFixedSize(true);
                    // Use a linear layout manager
                    layoutmanager = new LinearLayoutManager(getContext());
                    recyclerView.setLayoutManager(layoutmanager);
                    // Create an instance of ProgramAdapter. Pass context and all the array elements to the constructor
                    programAdapter = new com.example.pibapp.LoginAdapter(thiscontext, TitleText, DescText,nids);
                    // Finally, attach the adapter with the RecyclerView

//                            Toast.makeText(getContext(), "hello", Toast.LENGTH_SHORT).show();
                    recyclerView.setAdapter(programAdapter);


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
////                            Toast.makeText(HomeActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            });
        }
        catch (Exception e){
            e.printStackTrace();
        }
     return root;
    }
}
