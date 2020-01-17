package com.example.conferencepro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
     String user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user=getSharedPreferences("user",MODE_PRIVATE).getString("username","guest");
        //TODO Please add initial case to see if user has been here before

    }
}
