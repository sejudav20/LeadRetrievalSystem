package com.example.conferencepro;

import android.content.Intent;
import android.content.MutableContextWrapper;
import android.content.SharedPreferences;
import android.graphics.Color;
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
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

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
    ApplicantRepository repository;
    private final static int VIEWCAPACITY=12;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_main);

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
        repository=new ApplicantRepository(this.getApplication());
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
        final int c=advertise.getSolidColor();
        advertise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(advertising){nc.stopAdvertising();
                advertising=false;
                advertise.setText("Advertise");
                advertise.setBackgroundColor(c);

                }else{
                    nc.startAdvertising(jobName,optionsOfAdvertising);
                    advertising=true;
                    advertise.setBackgroundColor(Color.RED);
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
                  String s= "Data Received ";
                  for(String string:strings){
                      s+="\n"+string;
                  }

              }
            }
        };
        people=getPeople();
        people.observe(this,queueObserver);

    }
    LiveData<Queue<String>> people;
    public LiveData<Queue<String>> getPeople(){
        if(people==null){
            people=new MutableLiveData<Queue<String>>();
        }
        return people;

    }
    public void addPerson(String p){

        people.getValue().add(p);
    }
        EntrantData ed;
    ApplicantInfo applicantInfo;
    long time;
    NearbyCreator.OptionsOfAdvertising optionsOfAdvertising= new NearbyCreator.OptionsOfAdvertising() {
        @Override
        public void OnDiscoverySuccess() {
            Toast.makeText(JobMain.this,"Advertising make sure you are plugged in",Toast.LENGTH_LONG).show();

        }

        @Override
        public void OnDiscoveryFailure(Exception e) {
            Toast.makeText(JobMain.this,"Failed to discover",Toast.LENGTH_LONG).show();
        }

        @Override
        public void OnStringReceived(String s1, String s) {
            try {
            List<EntrantData> ede=repository.getSpecificEntrantData(s1).getValue();

                if(ede.size()==0){
                Scanner sc= new Scanner(s);
                if(sc.next().equals(conferenceID)) {
                    addPerson(s1);
                    String name = sc.next();
                    String email = sc.next();
                    String number = sc.next();
                    String company = sc.next();
                    String currentRole = sc.next();
                    String educationLevel = sc.next();
                    String linkedUrl = sc.next();
                    applicantInfo = new ApplicantInfo(email, number, educationLevel, currentRole, company, linkedUrl);
                    ed = new EntrantData(name, applicantInfo.getUserId(), 0, 1);
                }
                    //TODO add database stuff
                else{
                    nc.stopConnection(s1);

                }
            }else{
                    ed=ede.get(0);
                    applicantInfo=repository.getSpecificApplicantInfo(ed.getUserData()).get(0);
                }} catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void OnStringUpdate() {

        }

        @Override
        public void OnConnectionGood(String s) {
            time=System.currentTimeMillis();
        }

        @Override
        public void OnConnectionError() {

        }

        @Override
        public void OnConnectionRejected() {

        }

        @Override
        public void OnConnectionDisconnected() {
            if(ed!=null){
                ed.setTimeStayed(ed.getTimeStayed()+(time-System.currentTimeMillis()));
                ed.setTimesVisited(ed.getTimesVisited()+1);
                repository.insert(ed,applicantInfo);

            }
        }
    };

}
