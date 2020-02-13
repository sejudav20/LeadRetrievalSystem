package com.example.conferencepro;

import android.content.Intent;
import android.content.MutableContextWrapper;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.gms.nearby.connection.Strategy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class JobMain extends AppCompatActivity {
    Button viewData;
    Button advertise;
    NearbyCreator nc;
    TextView welcome;
    ConstraintLayout afterConf;
    LinearLayout beforeConf;
    EditText enterCompany;
    EditText enterConference;
    Button submitForm;
    SharedPreferences sp;
    SharedPreferences.Editor spe;
    String conferenceID;
    String jobName;
    boolean advertising=false;
    TextView viewPeople;
    private final static int VIEWCAPACITY=12;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sp = getSharedPreferences("jobData", MODE_PRIVATE);
        spe = sp.edit();
        conferenceID = sp.getString("ID", "");
        jobName = sp.getString("JobName", "");
        afterConf = findViewById(R.id.AfterConferenceAdded);
        beforeConf = findViewById(R.id.AddConferenceForm);
        welcome = findViewById(R.id.welcomeText);
        viewPeople= findViewById(R.id.connectionRecords);
        if (conferenceID.equals("")) {
            beforeConf.setVisibility(View.VISIBLE);
            afterConf.setVisibility(View.INVISIBLE);
            welcome.setText("Fill Out Form Below");

        } else {
            beforeConf.setVisibility(View.INVISIBLE);
            afterConf.setVisibility(View.VISIBLE);
            welcome.setText("Welcome: " + jobName);

        }
        enterCompany = findViewById(R.id.editConpNameJo);
        enterConference = findViewById(R.id.editConfIDJo);
        submitForm = findViewById(R.id.AddConference);
        submitForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                beforeConf.setVisibility(View.INVISIBLE);
                afterConf.setVisibility(View.VISIBLE);
                if (enterConference.getText().toString().equals("") || enterCompany.getText().toString().equals("") ||
                        enterConference.getText().toString().contains(",") || enterCompany.getText().toString().contains(",")) {
                    enterConference.setError("Empty Field or Comma is included");
                } else {
                    spe.putString("ID", enterConference.getText().toString()).apply();
                    spe.putString("JobName", enterCompany.getText().toString()).apply();
                    recreate();
                }
            }
        });
        viewData=findViewById(R.id.viewwDataButtonTo);
        advertise=findViewById(R.id.AdvertiseButtion);
        if(afterConf.getVisibility()==View.VISIBLE) {
            nc = new NearbyCreator(this, jobName, Strategy.P2P_CLUSTER);
        }

        viewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(JobMain.this,JobViewData.class));

            }
        });
        advertise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(advertising){nc.stopAdvertising();}else{
                    nc.startAdvertising(jobName,optionsOfAdvertising);

                }
            }
        });
        final Observer<Queue<String>> queueObserver= new Observer<Queue<String>>() {
            @Override
            public void onChanged(Queue<String> strings) {
              if(strings.size()>VIEWCAPACITY){
                  while(!(strings.size()>VIEWCAPACITY)){
                      strings.remove();
                  }
              }
            }
        };


    }
    MutableLiveData<Queue<String>> people;
    public LiveData<Queue<String>> getPeople(){
        if(people==null){
            people=new MutableLiveData<Queue<String>>();
        }
        return people;

    }

    NearbyCreator.OptionsOfAdvertising optionsOfAdvertising= new NearbyCreator.OptionsOfAdvertising() {
        @Override
        public void OnDiscoverySuccess() {
            Toast.makeText(JobMain.this,"Advertising make sure you are plugged in",Toast.LENGTH_LONG).show();

        }

        @Override
        public void OnDiscoveryFailure() {

        }

        @Override
        public void OnStringReceived(String s1, String s) {
            Scanner sc= new Scanner(s);
            if(sc.next().equals(conferenceID)) {
                people.getValue().add(s);
                //TODO add database stuff
            }else{
                nc.stopConnection(s);

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
    };

}
