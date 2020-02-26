package com.example.conferencepro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class ConferenceUserData extends AppCompatActivity {
    HashSet<String> companies;
    RecyclerView recyclerView;
    Button b;
    String user;

    SharedPreferences spi;



    RecyclerView.LayoutManager lm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conference_user_data);
        if(companies==null){
        companies=new HashSet<>();}
        user=getIntent().getStringExtra("user");
        spi= getSharedPreferences(user+" conferenceJobs",MODE_PRIVATE);
        companies= (HashSet<String>) spi.getStringSet("all",new HashSet<>());
//        Scanner sc= new Scanner(getSharedPreferences("ConferenceData",MODE_PRIVATE).getString(user+" CData",""));
//       Log.d("testing",getSharedPreferences("ConferenceData",MODE_PRIVATE).getString(user+" CData",""));
//        sc.useDelimiter(UserData.DELIMETER);
//        String s=sc.next();
//        while(sc.hasNext()){
//            companies.put(sc.next());
//
//        }

        recyclerView= findViewById(R.id.rview);

        lm= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(lm);
        b= findViewById(R.id.retrunHome);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ConferenceUserData.this,EntrantMain.class));
            }
        });
        Log.d("testing",companies.toString());
        recyclerView.setAdapter(new CompanyAdapter(companies,this,user));
    }
}
