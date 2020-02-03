package com.example.conferencepro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;

public class ConferenceUserData extends AppCompatActivity {
    ArrayList<String> companies;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conference_user_data);
        companies=new ArrayList<>();

    }
}
