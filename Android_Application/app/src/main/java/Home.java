package com.example.pibapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Home extends Fragment {

    Context thiscontext;





    String reqDate,lang;

//    initilizing variables
    Spinner region,language,d;
    String PIB,l;
    RecyclerView recyclerView;
    // Declare an adapter
    RecyclerView.Adapter programAdapter;
    RecyclerView.LayoutManager layoutmanager;
    RelativeLayout r;

    //initialize Database__________________________________________________________
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("News_pib");
    DatabaseReference mDatabase = database.getReference("User_language");

    List<String> nids = new ArrayList<>();
    List<String> TitleText = new ArrayList<>();
    List<String> DescText = new ArrayList<>();
    List<String> languages = new ArrayList<>();
    List<String> ministries = new ArrayList<>();



    String Research[] = {"RE AICTE, MIC-Student Innovation","SC Department of Space, Indian Space Research Organisation (ISRO)","India Meteorological Department (IMD), Ministry of Earth Sciences (MoES)"," Indian National Centre for Ocean Information Services (INCOIS), Ministry of Earth Sciences (MoES)"};
    String Education[] = {"RE All India Council for Technical Education (AICTE)","ED Department of School Education Literacy (DoSEL), Ministry of Education"," Indian Knowledge System (IKS), All India Council for Technical Education (AICTE)","Indian Knowledge System (IKS), All India Council for Technical Education (AICTE)",
            "MathWorks","National Assessment and Accreditation Council (NAAC)","National Council of Educational Research and Training (NCERT), Department of School Education Literacy (DoSEL)","ational Digital Education Architecture (NDEAR), Department of School Education Literacy (DoSEL)","National Institute of Design Madhya Pradesh",
            "University Grants Commission (UGC)"};
    String social[] = {"CW Central Ground Water Board (CGWB)", "Ministry of Jal Shakti","CW Central Water Commission (CWC), Ministry of Jal Shakti"," SW Dairy Division, Department of Animal Husbandry Dairying, Ministry of Fisheries, Animal Husbandry Dairying",
            "DE Defence Research and Development Organisation (DRDO), Ministry of Defence.","SW Department of Empowerment of Persons with Disabilities(Divyangjan) , Ministry of Social Justice and Empowerment","SW Department of Food, Public Distribution, Ministry of Consumer Affairs Food and Public Distribution",
            "SW Department of Social Justice Empowerment", "Ministry of Social Justice and Empowerment","Ministry of Culture","Ministry of Housing and Urban Affairs","Ministry of Labour Employment","Ministry of Rural Development","Ministry of Skill Development and Entrepreneurship (MSDE)",
            "Ministry of Skill Development and Entrepreneurship (MSDE)","National Institute for Empowerment of Persons with Multiple Disabilities (NIEPMD), Ministry of Social Justice Empowerment","National Mission  for Clean Ganga (NMCG), Ministry of Jal Shakti","National Water Mission (NWM), Ministry of Jal Shakti",
            "NIFTEM Thanjavur ,Ministry of Food Processing Industries (MoFPI)","Swachh Bharat Mission (Grameen), Department of Drinking Water Sanitation, Ministry of Jal Shakti"};
    String Science[]={"RE Communication and Information service, MHA","RE Dell Technologies, Bangalore, India","SC Department of Science Technology (DoST), Ministry of Science and Technology","Ministry of Electronics and Information Technology (MEITY)","National Institute of Ocean Technology (NIOT), Ministry of Earth Sciences (MoES)",
            "Policy Planning Cell, Ministry of Information Broadcasting"};
    String Defence[] = {"DE Cyber Crime Unit, Special Cell, Delhi Police, MHA","Department of Telecommunications (DoT), Ministry of Communications","ITBP, MHA","Madhya Pradesh Police"," National Disaster Response Force (NDRF)"};
    String Other[]={"RE Communication and Information service, MHA","RE Dell Technologies, Bangalore, India","SC Department of Science Technology (DoST), Ministry of Science and Technology","Ministry of Electronics and Information Technology (MEITY)","National Institute of Ocean Technology (NIOT), Ministry of Earth Sciences (MoES)",
            "Policy Planning Cell, Ministry of Information Broadcasting","SW Department of Post, Ministry of Post","eCourts, Department of Justice, Ministry of Law &amp; Justice","Gas Authority of India Ltd (GAIL)"," Indian Council for Cultural Relations (ICCR)","Ministry of Chemicals and Fertilizers","Ministry of MinesMinistry of Mines","Ministry of Tourism","NATGRID, MHA",
            "North Eastern Cane and Bamboo Development Council (NECBDC)"," Volvo","White Hour Solutions","DE Cyber Crime Unit, Special Cell, Delhi Police, MHA","Department of Telecommunications (DoT), Ministry of Communications","ITBP, MHA","Madhya Pradesh Police"," National Disaster Response Force (NDRF)"};
    String sports[]={"SPO Department of Sports (DoS), Ministry of Youth Affairs  Sports"};
    String health[] = {"GE Healthcare","MIC","Ministry of Ayurveda, Yoga, Naturopathy, Unani, Siddha, Sowa-Rigpa and Homoeopathy (AYUSH)","Narcotics Control Bureau (NCB)"};
    String politics[]={"Government of Maharashtra","Government of Rajasthan","Government of Uttarakhand","Ministry of External Affairs (MEA)"};
    String economy[]= {"Indian National Centre for Ocean Information Services (INCOIS), Ministry of Earth Sciences (MoES)","Ministry of Consumer Affairs","Ministry of Finance","Ministry of Micro, Small Medium Enterprises (MSME)","Ministry of Ports, Shipping, Waterways",
            "Startup India Section, Department for Promotion of Industry and Internal Trade (DPIT), Ministry of Commerce and Industry"};


String am []= {"Ministry of Jal Shakti"};


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        thiscontext = container.getContext();
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.activity_home,container,false);
        region = (Spinner) root.findViewById(R.id.region);
        language = (Spinner) root.findViewById(R.id.language);
        d = (Spinner) root.findViewById(R.id.Date);



        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(thiscontext);
        if(account != null) {
            String personEmail = account.getEmail();
            String[] id = personEmail.split("@");
            String Fin_id = id[0].replace(".","_");



            mDatabase.child(Fin_id).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    l = snapshot.getValue().toString();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }










//        Setting the regions in the spinnner_______________________________________________________

        String regions[] = {"All","PIB Delhi","PIB Mumbai","PIB Hyderabad","PIB Chennai",
                "PIB Chandigarh","PIB Kolkata" ,"PIB Bengaluru" ,"PIB Bhubaneshwar" ,
                "PIB Ahmedabad" ,"PIB Guwahati","PIB Thiruvananthapuram", "PIB Imphal"};


        String dates[] = {"Today","Yesterday","Day Before"};
        region.setAdapter(new ArrayAdapter<String>(getContext(),R.layout.spinner_list,regions));
        region.setDropDownVerticalOffset(70);
        language.setDropDownVerticalOffset(70);



// Read from the database

        d.setAdapter(new ArrayAdapter<String>(getContext(),R.layout.spinner_list,dates));





        region.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Object item = parent.getItemAtPosition(pos);
                PIB = item.toString();
                System.out.println("l is"+l);

                String Mumbai[] = {"Marathi","English"};
                String Delhi[] = {"English","Hindi","Urdu"};
                String Hyderabad[] = {"Telugu","English"};
                String Bengaluru[] = {"Kannada","English"};
                String Chennai[] = {"Tamil","English"};
                String Kolkata[] = {"Bengali","English"};
                String Chandugarh[] = {"Punjabi","English"};
                String Ahmedabad[] = {"Gujarati","English"};
                String Thiruvunantpuram[] = {"Malayalam","English"};
                String imphal[] = {"English"};
                String Bhubaneshwar[] = {"Odia","English"};
                String Guwahati[] = {"Assamese","English"};
                String all[] = {"English","Hindi","Urdu","Marathi","Telugu","Kannada","Tamil","Bengali","Punjabi","Gujarati","Malayalam","Odia","Assamese"};




//
                switch(item.toString()){
                    case "All":

                        language.setAdapter(new ArrayAdapter<String>(getContext(),R.layout.spinner_list,all));
                        break;
                        case "PIB Delhi":

                        language.setAdapter(new ArrayAdapter<String>(getContext(),R.layout.spinner_list,Delhi));
                        break;
                    case "PIB Mumbai":
                        language.setAdapter(new ArrayAdapter<String>(getContext(),R.layout.spinner_list,Mumbai));
                        break;
                    case "PIB Hyderabad":
                        language.setAdapter(new ArrayAdapter<String>(getContext(),R.layout.spinner_list,Hyderabad));
                        break;
                    case "PIB Bengaluru":
                        language.setAdapter(new ArrayAdapter<String>(getContext(),R.layout.spinner_list,Bengaluru));
                        break;
                    case "PIB Chennai":
                        language.setAdapter(new ArrayAdapter<String>(getContext(),R.layout.spinner_list,Chennai));
                        break;
                    case "PIB Ahmedabad":
                        language.setAdapter(new ArrayAdapter<String>(getContext(),R.layout.spinner_list,Ahmedabad));
                        break;
                    case "PIB Kolkata":
                        language.setAdapter(new ArrayAdapter<String>(getContext(),R.layout.spinner_list,Kolkata));
                        break;
                    case "PIB Chandigarh":

                        language.setAdapter(new ArrayAdapter<String>(getContext(),R.layout.spinner_list,Chandugarh));
                        break;
                    case "PIB Thiruvunantpuram":

                        language.setAdapter(new ArrayAdapter<String>(getContext(),R.layout.spinner_list,Thiruvunantpuram));
                        break;
                    case "PIB Imphal":

                        language.setAdapter(new ArrayAdapter<String>(getContext(),R.layout.spinner_list, imphal));
                        break;
                    case "PIB Bhubaneshwar":

                        language.setAdapter(new ArrayAdapter<String>(getContext(),R.layout.spinner_list, Bhubaneshwar));
                        break;
                    case "PIB Guwahati":

                        language.setAdapter(new ArrayAdapter<String>(getContext(),R.layout.spinner_list, Guwahati));
                        break;
                }




            }

            // Read from the database

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });



        d.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Object item = adapterView.getItemAtPosition(i);

                LocalDateTime myDateObj = LocalDateTime.now();
                switch(item.toString()){
                    case "Today":

                        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd MMM yyyy");
                        reqDate = myDateObj.format(myFormatObj).toUpperCase();
                        lang = language.getSelectedItem().toString();

                        myRef.child(lang).addValueEventListener(new ValueEventListener() {
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




                                    if (date.equals(reqDate)) {
                                        TitleText.add(title);
                                        DescText.add(date+" "+time);
                                        nids.add(snap.getKey());
                                        languages.add(lang);
                                        ministries.add(ministry);
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
                                programAdapter = new com.example.pibapp.ProgramAdapter(thiscontext, TitleText, DescText,nids,languages);
                                // Finally, attach the adapter with the RecyclerView


                                recyclerView.setAdapter(programAdapter);


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
////
                            }
                        });


                        break;
                    case "Yesterday":
                        DateTimeFormatter aa = DateTimeFormatter.ofPattern("dd");
                        DateTimeFormatter myFormatObj2 = DateTimeFormatter.ofPattern("MMM yyyy");
                        String my = myDateObj.format(myFormatObj2).toUpperCase();

                        int dt = Integer.parseInt(myDateObj.format(aa))-1;

                        reqDate = dt + " " + my;
                        lang = language.getSelectedItem().toString();

                        myRef.child(lang).addValueEventListener(new ValueEventListener() {
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



                                    if (date.equals(reqDate)) {
                                        TitleText.add(title);
                                        DescText.add(date+" "+time);
                                        nids.add(snap.getKey());
                                        languages.add(lang);
                                        ministries.add(ministry);
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
                                programAdapter = new com.example.pibapp.ProgramAdapter(thiscontext, TitleText, DescText,nids,languages);
                                // Finally, attach the adapter with the RecyclerView


                                recyclerView.setAdapter(programAdapter);


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
////
                            }
                        });

                        break;
                    case "Day Before":
                        DateTimeFormatter a = DateTimeFormatter.ofPattern("dd");
                        DateTimeFormatter myFormatObj3 = DateTimeFormatter.ofPattern("MMM yyyy");
                        String my1 = myDateObj.format(myFormatObj3).toUpperCase();

                        int dt1 = Integer.parseInt(myDateObj.format(a))-2;

                        reqDate = dt1 + " " + my1;
                        lang = language.getSelectedItem().toString();

                        myRef.child(lang).addValueEventListener(new ValueEventListener() {
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



                                    if (date.equals(reqDate)) {
                                        TitleText.add(title);
                                        DescText.add(date+" "+time);
                                        nids.add(snap.getKey());
                                        languages.add(lang);
                                        ministries.add(ministry);
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
                                programAdapter = new ProgramAdapter(thiscontext, TitleText, DescText,nids,languages);
                                // Finally, attach the adapter with the RecyclerView


                                recyclerView.setAdapter(programAdapter);


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
////
                            }
                        });



                }

                language.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        Object item = parent.getItemAtPosition(position);
                        String ln = item.toString();

                        System.out.println(ln);

                        lang =  item.toString();
                        try {

                            String Fin_id= "";
                            GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(thiscontext);
                            if(account != null) {


                            }



                            myRef.child(item.toString()).addValueEventListener(new ValueEventListener() {
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



                                        if (date.equals(reqDate)) {
                                            TitleText.add(title);
                                            DescText.add(date+" "+time);
                                            nids.add(snap.getKey());
                                            languages.add(ln);
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
                                    programAdapter = new com.example.pibapp.ProgramAdapter(thiscontext, TitleText, DescText,nids,languages);
                                    // Finally, attach the adapter with the RecyclerView


                                    recyclerView.setAdapter(programAdapter);


                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
////
                                }
                            });
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });











        return root;

    }


}
