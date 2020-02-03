package com.example.conferencepro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class ConferenceUserData extends AppCompatActivity {
    HashMap<String, Boolean> companies;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conference_user_data);
        companies=new HashMap<>();
        Scanner sc= new Scanner(getSharedPreferences("ConferenceData",MODE_PRIVATE).getString("cData",""));
        sc.useDelimiter(",");
        String s=sc.next();
        while(sc.hasNext()){
            companies.put(sc.next(),false);

        }
        recyclerView= findViewById(R.id.);

    }
}
