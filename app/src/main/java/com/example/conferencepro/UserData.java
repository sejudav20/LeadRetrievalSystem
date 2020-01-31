package com.example.conferencepro;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class UserData extends AppCompatActivity {
    Button saveB;
    Button toConferenceSettingsB;
    EditText email;
    EditText name;
    EditText LinkedUrl;
    Spinner educationLevel;
    EditText number;
    EditText company;
    public static  String DELIMETER="&/*-*/&";
    EditText currentRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        saveB=findViewById(R.id.save);
        toConferenceSettingsB=findViewById(R.id.toConferenceButton);
        email=findViewById(R.id.email);
        name=findViewById(R.id.NameEdit);
        LinkedUrl=findViewById(R.id.linked);
        educationLevel=findViewById(R.id.education);
        number=findViewById(R.id.phone);
        company=findViewById(R.id.company);
        currentRole=findViewById(R.id.currentRole);

        saveB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp= getSharedPreferences("user",MODE_PRIVATE);
                sp.edit().putString(EntrantMain.getUser(),""+DELIMETER+name.getText()+DELIMETER+email.getText()+DELIMETER+number.getText()+DELIMETER+company.getText()+DELIMETER+educationLevel.getSelectedItem().toString()+LinkedUrl.getText());
            }
        });


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
