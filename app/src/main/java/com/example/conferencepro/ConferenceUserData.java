package com.example.conferencepro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class ConferenceUserData extends AppCompatActivity {
    HashMap<String, Boolean> companies;
    RecyclerView recyclerView;
    Button b;
    RecyclerView.LayoutManager lm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conference_user_data);
        companies=new HashMap<>();
        Scanner sc= new Scanner(getSharedPreferences("ConferenceData",MODE_PRIVATE).getString("CData",""));
        sc.useDelimiter(",");
        String s=sc.next();
        while(sc.hasNext()){
            companies.put(sc.next(),false);

        }
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
        recyclerView.setAdapter(new CompanyAdapter(companies));

    }
}
