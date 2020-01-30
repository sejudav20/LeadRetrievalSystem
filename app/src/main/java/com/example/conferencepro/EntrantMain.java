package com.example.conferencepro;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class EntrantMain extends AppCompatActivity {
    TextView tx;
    String conference = "";
    Button b;
    Button toUserData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrant_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        b = findViewById(R.id.AddConference);
        b.setVisibility(View.INVISIBLE);
        if (conference.equals("")) {
            b.setVisibility(View.VISIBLE);

        }

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO  add connection to nearby conference beacon

            }
        });

        toUserData = findViewById(R.id.button2);
        toUserData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EntrantMain.this, UserData.class));
            }
        });


        String user = getSharedPreferences("user", MODE_PRIVATE).getString("username", "guest");

        tx = findViewById(R.id.WelcomeText);
        tx.setText("Welcome " + user);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.conferenceSettings:
                if (!conference.equals("")) {
                    startActivity(new Intent(EntrantMain.this, ConferenceUserData.class));
                } else {
                    Toast.makeText(EntrantMain.this,"Please add a conference first", Toast.LENGTH_LONG).show();
                }

                break;
            case R.id.UserDataButton:
                startActivity(new Intent(EntrantMain.this, UserData.class));
                break;

            default:
                return super.onOptionsItemSelected(item);

        }

        return true;

    }
}
