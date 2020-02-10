package com.example.conferencepro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.nearby.connection.DiscoveredEndpointInfo;
import com.google.android.gms.nearby.connection.Strategy;

import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class CoordinatorMain extends AppCompatActivity implements RoleTransferDialogBox.RoleTransferCallback {
    String conference;
    SharedPreferences sp;
    NearbyCreator nc;
    String name;
    boolean isReceiving;
    int conferenceNumber;
    TextView welcome;
    TextView confNumbers;
    Button createConference;
    EditText editConferenceName;
    Set<String> companies;
    EditText addCompName;
    Button addComp;
    TextView companyView;
    String companyString="";
    String conferenceString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_main);

        confNumbers= findViewById(R.id.conferenceViewText);
companies= new HashSet<>();
        addComp=findViewById(R.id.addCompany);
        addCompName=findViewById(R.id.editAddCompany);
        companyView=findViewById(R.id.companyView);
        sp=getSharedPreferences("CoordData",MODE_PRIVATE);
        SharedPreferences spe=getSharedPreferences("user",MODE_PRIVATE);
        welcome.setText("Welcome "+spe.getString("username",""));
        createConference=findViewById(R.id.createNewConference);
        companies=sp.getStringSet("companies",new HashSet<String>());

        for(String s:companies){
            companyString+=s+",";
        }
        companyView.setText("Companies added: "+companyString);
        editConferenceName=findViewById(R.id.addConferenceName);
        conference=sp.getString("cName","");
        conferenceNumber=sp.getInt("cNum",0);
        conferenceString= conferenceNumber+","+companyString+",";
        sp.edit().putString("CData", conferenceString).apply();

        addComp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String company=addCompName.getText().toString();
                if(!(company.equals("")||companies.contains(company))){
                    companies.add(company);
                    companyString+=company+",";
                    conferenceString +=company+",";
                }else{
                    addComp.setError("Enter a company that is not already added");
                }
            }
        });
        if(conferenceNumber==0){
            createConference.setVisibility(View.VISIBLE);
            editConferenceName.setVisibility(View.VISIBLE);
            addComp.setVisibility(View.INVISIBLE);
            addCompName.setVisibility(View.INVISIBLE);
            createConference.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    if(editConferenceName.getText().toString().equals("")){
                        editConferenceName.setError("Please enter a name");

                    }else{
                        sp.edit().putString("cName",editConferenceName.getText().toString()).apply();
                        sp.edit().putInt("cNum",new Random().nextInt(200000)+1).apply();
                        addComp.setVisibility(View.VISIBLE);
                        addCompName.setVisibility(View.VISIBLE);
                        createConference.setVisibility(View.INVISIBLE);
                        editConferenceName.setVisibility(View.INVISIBLE);

                    }
                }
            });


        }else{confNumbers.setText("Conference ID: "+conferenceNumber);
            createConference.setVisibility(View.INVISIBLE);
            editConferenceName.setVisibility(View.INVISIBLE);}
        addComp.setVisibility(View.VISIBLE);
        addCompName.setVisibility(View.VISIBLE);

        name="ConfName";
        nc= new NearbyCreator(this,name, Strategy.P2P_CLUSTER);



    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.TransferCoordinatorRole:
                if (!conference.equals("")) {
                    Toast.makeText(CoordinatorMain.this,"Please be a few feet within the new phone with this conference role", Toast.LENGTH_LONG).show();
                    isReceiving=false;

                } else {
                    Toast.makeText(CoordinatorMain.this,"Please add a conference first", Toast.LENGTH_LONG).show();

                }

                break;
            case R.id.ReceiveCoordinatorRole:
                Toast.makeText(CoordinatorMain.this,"Please add a conference first", Toast.LENGTH_LONG).show();
                isReceiving=true;
                break;

            default:
                return super.onOptionsItemSelected(item);

        }

        return true;

    }

    @Override
    public void whenPositiveButtonClicked() {
        if(isReceiving){
            nc.startDiscovery("Conference receiver" ,);

        }else{
            nc.startAdvertising("",optionsOfAdvertising);

        }
    }

    @Override
    public void whenNegativeButtonClicked() {

    }

    public NearbyCreator.OptionsOfDiscovery optionsOfDiscovery=new NearbyCreator.OptionsOfDiscovery() {
        @Override
        public void OnDiscoverySuccess() {
            Toast.makeText(CoordinatorMain.this,"Discovering you have 1 minute to find other conference app",Toast.LENGTH_SHORT).show();
            Toast.makeText(CoordinatorMain.this,"Discovering you have 1 minute",Toast.LENGTH_SHORT).show();
            new CountDownTimer(60000,50000){

                @Override
                public void onTick(long l) {

                }

                @Override
                public void onFinish() {
                    Toast.makeText(CoordinatorMain.this,"Discovering Time Out",Toast.LENGTH_SHORT).show();
                    nc.stopDiscovery();
                }
            }.start();

        }

        @Override
        public void OnDiscoveryFailure() {

        }

        @Override
        public void OnStringReceived(String s) {
            Scanner sc= new Scanner(s);
            sc.useDelimiter(",");
            if(s.equals("ConfName")) {
                sp.edit().putString("CData", s).apply();
                Toast.makeText(CoordinatorMain.this, "Discovering Time Out", Toast.LENGTH_SHORT).show();
            }
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
            return false;
        }

        @Override
        public void OnConnectionSuccess() {

        }

        @Override
        public void OnConnectionFailure() {

        }

        @Override
        public void OnConnectionLost() {

        }
    };
    public NearbyCreator.OptionsOfAdvertising optionsOfAdvertising= new NearbyCreator.OptionsOfAdvertising() {
        @Override
        public void OnDiscoverySuccess() {
            Toast.makeText(CoordinatorMain.this,"Discovering you have 1 minute",Toast.LENGTH_SHORT).show();
            new CountDownTimer(60000,50000){

                @Override
                public void onTick(long l) {

                }

                @Override
                public void onFinish() {
                    Toast.makeText(CoordinatorMain.this,"Discovering Time Out",Toast.LENGTH_SHORT).show();
                    nc.stopDiscovery();
                }
            }.start();

        }

        @Override
        public void OnDiscoveryFailure() {
            Toast.makeText(CoordinatorMain.this,"DiscoveryFailure",Toast.LENGTH_SHORT).show();

        }

        @Override
        public void OnStringReceived(String s) {

        }

        @Override
        public void OnStringUpdate() {

        }

        @Override
        public void OnConnectionGood(String s) {
            nc.stopDiscovery();
            nc.sendMessage(s,conferenceString);
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
    };





}
