package com.example.conferencepro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.nearby.connection.Strategy;

import java.util.HashSet;
import java.util.Random;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_main);

        confNumbers= findViewById(R.id.conferenceViewText);
companies= new HashSet<>();
        addComp=findViewById(R.id.addCompany);
        addCompName=findViewById(R.id.editAddCompany);

        sp=getSharedPreferences("CoordData",MODE_PRIVATE);
        SharedPreferences spe=getSharedPreferences("user",MODE_PRIVATE);
        welcome.setText("Welcome "+spe.getString("username",""));
        createConference=findViewById(R.id.createNewConference);
        companies=sp.getStringSet("companies",new HashSet<String>());
        editConferenceName=findViewById(R.id.addConferenceName);
        conference=sp.getString("cName","");
        conferenceNumber=sp.getInt("cNum",0);
        addComp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String company=addCompName.getText().toString();
                if(company.equals("")||companies.contains(company)){
                    
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

        name="";
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
            nc.startAdvertising("");

        }
    }

    @Override
    public void whenNegativeButtonClicked() {

    }
}
