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

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class EntrantMain extends AppCompatActivity {
    TextView tx;
    String conference = "";
    Button b;
    Button toUserData;
    EditText confNumber;
    TextView txe;
    TextView tx23;
    public static String user;
    NearbyCreator nc;
    ToggleButton discoTog;
    SharedPreferences sp;
    SharedPreferences spi;
    Set<String> companies;
    public static String getUser() {
        return user;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrant_main);
        spi=getSharedPreferences(user+" conferenceJobs",MODE_PRIVATE);
        sp= getSharedPreferences("ConferenceData",MODE_PRIVATE);
        b = findViewById(R.id.AddConference);
        txe=findViewById(R.id.textView3);

        tx23=findViewById(R.id.tv23);
        b.setVisibility(View.INVISIBLE);
        tx23.setVisibility(View.INVISIBLE);
        confNumber=findViewById(R.id.confNumber);
        tx = findViewById(R.id.welcomeText);
        discoTog=findViewById(R.id.toggleButton);


        if (sp.getString(user+" cName","").equals("")) {

            b.setVisibility(View.VISIBLE);
            confNumber.setVisibility(View.VISIBLE);
            tx23.setVisibility(View.VISIBLE);
            txe.setText("No conference Joined:");


        }else{
            txe.setText("Current Conference is:"+sp.getString(user+" cName",""));
            nc= new NearbyCreator(EntrantMain.this,"ConferencePro "+sp.getString(user+" cName",""), Strategy.P2P_CLUSTER);
            updateCompanies(sp.getString(user+" CData",null));

        }
        confNumber= findViewById(R.id.confNumber);

        b.setOnClickListener(view -> {

            nc= new NearbyCreator(EntrantMain.this,"ConferencePro", Strategy.P2P_CLUSTER);
            nc.startDiscovery(confNumber.getText().toString()+" "+new Random().nextInt(10000000),new NearbyCreator.OptionsOfDiscovery(){
                @Override
                public void OnDiscoverySuccess() {
                    Toast.makeText(EntrantMain.this,"Discovery of conferences starting",Toast.LENGTH_SHORT).show();
                }

                @Override
                public void OnDiscoveryFailure(Exception e) {
                    Toast.makeText(EntrantMain.this,"Discovery of conferences Failed",Toast.LENGTH_LONG).show();
                }

                @Override
                public void OnStringReceived(String s,String user) {
                Scanner sc=new Scanner(s);
                sc.useDelimiter(",");
                    String confname=sc.next();
                    Toast.makeText(EntrantMain.this,"Connection successful to conference"+ confname,Toast.LENGTH_SHORT).show();
                    SharedPreferences sp1 = getSharedPreferences("ConferenceData",MODE_PRIVATE);
                    sp1.edit().putString(user+" CData",s).apply();
                    sp1.edit().putString(user+" cName",confname).apply();
                    updateCompanies(sp1.getString(user+" CData",null));
                    b.setVisibility(View.INVISIBLE);
                    confNumber.setVisibility(View.INVISIBLE);
                    txe.setText("Current Conference is:"+ sp1.getString(user+" cName",""));
                    tx23.setVisibility(View.INVISIBLE);
                    nc.stopAllConnections();
                    nc.stopDiscovery();
                    nc= new NearbyCreator(EntrantMain.this,"ConferencePro "+sp.getString(user+" cName",""), Strategy.P2P_CLUSTER);
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
                    return true;}else{
                        Toast.makeText(EntrantMain.this,"Check the Conference id number you inputted",Toast.LENGTH_LONG).show();
                        return false;}
                }

                @Override
                public void OnConnectionSuccess() {

                }

                @Override
                public void OnConnectionFailure(Exception e) {
                    Toast.makeText(EntrantMain.this,"Connection did not work",Toast.LENGTH_LONG).show();
                }

                @Override
                public void OnConnectionLost() {

                }
            });


        });

        toUserData = findViewById(R.id.button2);
        toUserData.setOnClickListener(view -> startActivity(new Intent(EntrantMain.this, UserData.class)));


        user = getSharedPreferences("user", MODE_PRIVATE).getString("username", "guest");


        tx.setText("Welcome " + user);

        discoTog.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    if(sp.getString(user+" cName",null)!=null){
                        nc.startDiscovery(user,optionsOfDiscovery);
                    }else if(getSharedPreferences(EntrantMain.getUser(),MODE_PRIVATE).getString("userData","")
                            .equals("")){
                        Toast.makeText(EntrantMain.this,"Please Enter your Data and save first",Toast.LENGTH_LONG).show();


                    }else{
                        Toast.makeText(EntrantMain.this,"Please Add Conference First",Toast.LENGTH_LONG).show();
                        discoTog.setChecked(false);
                    }
                }else{
                    nc.stopAllConnections();
                    nc.stopDiscovery();
                }
            }
        });



    }

    public void updateCompanies(String data){
        if(data==null){
            return;
        }
        companies.clear();

        Scanner sc=new Scanner(data);
        sc.useDelimiter(",");

        String s=sc.next();


        while(sc.hasNext()){
            companies.add(sc.next());
        }
    }
        NearbyCreator.OptionsOfDiscovery optionsOfDiscovery= new NearbyCreator.OptionsOfDiscovery() {
            @Override
            public void OnDiscoverySuccess() {
                Toast.makeText(EntrantMain.this,"You are ready to explore this conference",Toast.LENGTH_LONG).show();
            }

            @Override
            public void OnDiscoveryFailure(Exception e) {
                Toast.makeText(EntrantMain.this,"Discovery Failed",Toast.LENGTH_LONG).show();
            }

            @Override
            public void OnStringReceived(String user, String s) {

            }

            @Override
            public void OnStringUpdate() {
            }

            @Override
            public void OnConnectionGood(String s) {
                Toast.makeText(EntrantMain.this,"Transferring data",Toast.LENGTH_SHORT).show();
                nc.sendMessage(s,sp.getString(user+" cName",null)+","+getSharedPreferences(EntrantMain.getUser(),MODE_PRIVATE).getString("userData",""));
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
                if(!sp.contains(discoveredEndpointInfo.getEndpointName() )){
                    if(companies.contains(discoveredEndpointInfo.getEndpointName()
                    )){
                        return true;
                    }else{
                        return false;
                    }
                }
                if(!sp.getBoolean(discoveredEndpointInfo.getEndpointName(),false)){

                    return false;
                }
                return true;
            }

            @Override
            public void OnConnectionSuccess() {
                Toast.makeText(EntrantMain.this,"Got Connection",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void OnConnectionFailure(Exception e) {

            }

            @Override
            public void OnConnectionLost() {

            }
        };

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
