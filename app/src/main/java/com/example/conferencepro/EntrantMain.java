package com.example.conferencepro;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.gms.nearby.connection.DiscoveredEndpointInfo;
import com.google.android.gms.nearby.connection.Strategy;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;
import java.util.Scanner;

public class EntrantMain extends AppCompatActivity {
    TextView tx;
    String conference = "";
    Button b;
    Button toUserData;
    EditText confNumber;
    TextView txe;
    public static String user;

    public static String getUser() {
        return user;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrant_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        SharedPreferences sp= getSharedPreferences("ConferenceData",MODE_PRIVATE);
        b = findViewById(R.id.AddConference);
        txe=findViewById(R.id.textView3);
        b.setVisibility(View.INVISIBLE);
        if (sp.getString("cName","").equals("")) {

            b.setVisibility(View.VISIBLE);
            confNumber.setVisibility(View.VISIBLE);
            txe.setText("No conference Joined:");
        }else{
            txe.setText("Current Conference is:"+sp.getString("cName",""));
        }
        confNumber= findViewById(R.id.editText);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                NearbyCreator nc= new NearbyCreator(EntrantMain.this,"Usr", Strategy.P2P_CLUSTER);
                nc.startDiscovery(confNumber.getText().toString()+" "+new Random().nextInt(10000000),new NearbyCreator.OptionsOfDiscovery(){
                    @Override
                    public void OnDiscoverySuccess() {
                        Toast.makeText(EntrantMain.this,"Discovery of conferences starting",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void OnDiscoveryFailure() {
                        Toast.makeText(EntrantMain.this,"Discovery of conferences Failed",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void OnStringReceived(String s,String user) {
                    Scanner sc=new Scanner(s);
                    sc.useDelimiter(",");
                        String confname=sc.next();
                        Toast.makeText(EntrantMain.this,"Connection successful to conference"+ confname,Toast.LENGTH_SHORT).show();
                        SharedPreferences sp= getSharedPreferences("ConferenceData",MODE_PRIVATE);
                        sp.edit().putString("CData",s).apply();
                        sp.edit().putString("cName",confname).apply();
                        b.setVisibility(View.INVISIBLE);
                        confNumber.setVisibility(View.INVISIBLE);
                        txe.setText("Current Conference is:"+sp.getString("cName",""));
                    }

                    @Override
                    public void OnStringUpdate() {

                    }

                    @Override
                    public void OnConnectionGood(String s) {

                    }

                    @Override
                    public void OnConnectionError() {

                    }

                    @Override
                    public void OnConnectionRejected() {

                    }

                    @Override
                    public void OnConnectionDisconnected() {

                    }

                    @Override
                    public boolean Authenticated(@NonNull DiscoveredEndpointInfo discoveredEndpointInfo) {
                        if(discoveredEndpointInfo.getEndpointName().equals(confNumber.getText().toString())){
                        return true;}else{return false;}
                    }

                    @Override
                    public void OnConnectionSuccess() {

                    }

                    @Override
                    public void OnConnectionFailure() {
                        Toast.makeText(EntrantMain.this,"Connection did not work",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void OnConnectionLost() {

                    }
                });


            }
        });

        toUserData = findViewById(R.id.button2);
        toUserData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EntrantMain.this, UserData.class));
            }
        });


        user = getSharedPreferences("user", MODE_PRIVATE).getString("username", "guest");

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
