package com.example.conferencepro;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.gms.nearby.connection.Strategy;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

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
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
