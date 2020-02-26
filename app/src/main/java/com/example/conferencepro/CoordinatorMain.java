package com.example.conferencepro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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
    Button advertiseConference;
    String user;
    TextView entrantNumView;
    int entrantNum=0;
    LinearLayout ll;
    MutableLiveData<Integer> entrantNumLive;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_main);
        entrantNumView=findViewById(R.id.entrantNumber);
        entrantNumView.setText("Number of Entrants: "+entrantNum);
        confNumbers= findViewById(R.id.conferenceViewText);
companies= new HashSet<>();
        addComp=findViewById(R.id.addCompany);
        addCompName=findViewById(R.id.editAddCompany);
        companyView=findViewById(R.id.companyView);
        welcome=findViewById(R.id.WelcomeTextC);
        entrantNumLive= new MutableLiveData<>();
        entrantNumLive.setValue(entrantNum);
        sp=getSharedPreferences("CoordData",MODE_PRIVATE);
        SharedPreferences spe=getSharedPreferences("user",MODE_PRIVATE);
        welcome.setText("Welcome "+spe.getString("username",""));
        createConference=findViewById(R.id.createNewConference);
        companies=sp.getStringSet("companies",new HashSet<String>());
        user= getSharedPreferences("user",MODE_PRIVATE).getString("username","");
        ll=findViewById(R.id.linearLayout2);
        advertiseConference=findViewById(R.id.button3);
        companyView.setText("Companies added: ");
        editConferenceName=findViewById(R.id.addConferenceName);
        conference=sp.getString(user+ " cName","");
        conferenceNumber=sp.getInt(user+" cNum",0);
        entrantNum=sp.getInt(user+" eNum",0);
        conferenceString= conference+","+companyString+",";
        sp.edit().putString(user+" CData", conferenceString).apply();
        updateCompanies();
        if(sp.getStringSet("companies",null)!=null){
            companies=sp.getStringSet("companies",null);
            updateCompanies();
        }

        entrantNumLive.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                entrantNumView.setText("Number of Entrants: "+integer);
            }
        });
        addComp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String company=addCompName.getText().toString();
                if(!(company.equals("")||companies.contains(company))){
                    companies.add(company);

                    updateCompanies();
                }else{
                    addCompName.setError("Enter a company that is not already added");
                }
            }
        });


        if(conferenceNumber==0){
            createConference.setVisibility(View.VISIBLE);
            editConferenceName.setVisibility(View.VISIBLE);
            ll.setVisibility(View.INVISIBLE);
            confNumbers.setText("");
            advertiseConference.setVisibility(View.INVISIBLE);
            createConference.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    if(editConferenceName.getText().toString().equals("")){
                        editConferenceName.setError("Please enter a name");

                    }else{
                        sp.edit().putString(user+" cName",editConferenceName.getText().toString()).apply();
                       conference=sp.getString(user+" cName",editConferenceName.getText().toString());
                        sp.edit().putInt(user+" cNum",new Random().nextInt(200000)+1).apply();
                        sp.edit().putInt(user+" eNum",0).apply();
                        confNumbers.setText(sp.getInt(user+" cNum",0)+"");
                        addComp.setVisibility(View.VISIBLE);
                        addCompName.setVisibility(View.VISIBLE);
                        createConference.setVisibility(View.INVISIBLE);
                        editConferenceName.setVisibility(View.INVISIBLE);
                        ll.setVisibility(View.VISIBLE);
                        advertiseConference.setVisibility(View.VISIBLE);
                    }
                }
            });


        }else{confNumbers.setText("Conference ID: "+conferenceNumber);
            createConference.setVisibility(View.INVISIBLE);
            editConferenceName.setVisibility(View.INVISIBLE);
        }
        addComp.setVisibility(View.VISIBLE);
        addCompName.setVisibility(View.VISIBLE);
        advertiseConference.setVisibility(View.VISIBLE);
        name=conference;
        nc= new NearbyCreator(this,"ConferencePro", Strategy.P2P_CLUSTER);


        advertiseConference.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(advertiseConference.getText().toString().toLowerCase().equals("advertise conference")){
                if(conferenceNumber!=0){
                    String s= conference;
                    nc.startAdvertising(conferenceNumber+"",optionsToAdvertiseToUser);
                    advertiseConference.setText("Stop Advertising...");
                }}else{
                   nc.stopAdvertising();
                   advertiseConference.setText("Advertise Conference");

               }
            }
        });
    }

    NearbyCreator.OptionsOfAdvertising optionsToAdvertiseToUser= new NearbyCreator.OptionsOfAdvertising() {
        @Override
        public void OnDiscoverySuccess() {
         Toast.makeText(CoordinatorMain.this,"Advertising started succesfully",Toast.LENGTH_SHORT).show();
            Log.d("testing","Conference Name "+conference);
        }

        @Override
        public void OnDiscoveryFailure(Exception e) {
            Toast.makeText(CoordinatorMain.this,"Advertising failed Check Permissions or already advertising",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void OnStringReceived(String user, String s) {

        }

        @Override
        public void OnStringUpdate() {

        }

        @Override
        public void OnConnectionGood(String s) {
            Toast.makeText(CoordinatorMain.this,"Sent Conference data",Toast.LENGTH_SHORT).show();
            Log.d("testing","Conference Name "+conference);
            nc.sendMessage(s,conference+","+companyString);
            entrantNum-=-1;
        }

        @Override
        public void OnConnectionError() {
            Toast.makeText(CoordinatorMain.this,"Connection Error",Toast.LENGTH_SHORT).show();

        }

        @Override
        public void OnConnectionRejected() {
            Toast.makeText(CoordinatorMain.this,"Connection rejected",Toast.LENGTH_SHORT).show();

        }

        @Override
        public void OnConnectionDisconnected() {
            Toast.makeText(CoordinatorMain.this,"Disconnected",Toast.LENGTH_SHORT).show();

        }
    };

    @Override
    public void onPause() {
        super.onPause();
        sp.edit().putStringSet("companies",companies).apply();
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
                if(conference.equals("")){
                Toast.makeText(CoordinatorMain.this,"Please add a conference first", Toast.LENGTH_LONG).show();}
                isReceiving=true;
                break;

            default:
                return super.onOptionsItemSelected(item);

        }

        return true;

    }

    @Override
    protected void onStop() {
        super.onStop();
        sp.edit().putInt(user+" eNum",entrantNum).apply();
        sp.edit().putStringSet("companies",companies).apply();
    }

    @Override
    public void whenPositiveButtonClicked() {
        if(isReceiving){
            nc.startDiscovery("Conference receiver" ,optionsOfDiscovery);

        }else{
            nc.startAdvertising("master",optionsOfAdvertising);

        }
    }

    @Override
    public void whenNegativeButtonClicked() {

    }

    public NearbyCreator.OptionsOfDiscovery optionsOfDiscovery=new NearbyCreator.OptionsOfDiscovery() {
        @Override
        public void OnDiscoverySuccess() {
            Toast.makeText(CoordinatorMain.this,"Discovering you have 1 minute to find other conference app",Toast.LENGTH_SHORT).show();
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
        public void OnDiscoveryFailure(Exception e) {

        }

        @Override
        public void OnStringReceived(String user,String s) {
            Scanner sc= new Scanner(s);
            sc.useDelimiter(",");
            boolean n=false;
            if(s.equals("ConfName")) {
                n=true;
                sp.edit().putString("CData", s).apply();
                Toast.makeText(CoordinatorMain.this, "Received updated string", Toast.LENGTH_SHORT).show();
                sp.edit().putString("cNum",sc.next()).apply();
                sp.edit().putString("cName",sc.next()).apply();
                HashSet<String> str=new HashSet<>();
                while(sc.hasNext()){
                    str.add(sc.next());
                }
                sp.edit().putStringSet("companies",str).apply();
            }
            Toast.makeText(CoordinatorMain.this,"Successful disconnecting",Toast.LENGTH_SHORT).show();
            nc.stopAllConnections();
            if(n){
                recreate();

            }
        }

        @Override
        public void OnStringUpdate() {

        }

        @Override
        public void OnConnectionGood(String s) {
            Toast.makeText(CoordinatorMain.this, "Connection is good", Toast.LENGTH_SHORT).show();

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
        public void OnConnectionFailure(Exception e) {

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
        public void OnDiscoveryFailure(Exception e) {
            Toast.makeText(CoordinatorMain.this,"DiscoveryFailure",Toast.LENGTH_SHORT).show();

        }

        @Override
        public void OnStringReceived(String s1, String s) {

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

public void updateCompanies(){
    companyString="";
    conference=sp.getString(user+" cName","");
    if(conference==""){
        Log.d("testing","conferenceName is not working");
    }
    for(String s:companies){
        companyString+=s+",";
    }
    companyView.setText("Companies Added: "+companyString);
    conferenceString= conference+","+companyString+",";
    Log.d("testing","Conference String "+conferenceString);
}



}
