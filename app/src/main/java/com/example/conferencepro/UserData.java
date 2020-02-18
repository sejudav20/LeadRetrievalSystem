package com.example.conferencepro;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Scanner;

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

        saveB=findViewById(R.id.save);
        toConferenceSettingsB=findViewById(R.id.toConferenceButton);
        email=findViewById(R.id.email);
        name=findViewById(R.id.NameEdit);
        LinkedUrl=findViewById(R.id.linked);
        educationLevel=findViewById(R.id.education);
        number=findViewById(R.id.phone);
        company=findViewById(R.id.company);
        currentRole=findViewById(R.id.currentRole);
        ArrayList<String> edu=new ArrayList<>();
        edu.add("Highschool");
        edu.add("Bachelors");
        edu.add("Some College");
        edu.add("Some High School");
        edu.add("Masters");
        edu.add("P.H.D");
        edu.add("Trade School");
        edu.add("Associates");
        edu.add("None Of the Above");
        educationLevel.setAdapter(new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,edu));
        toConferenceSettingsB.setOnClickListener(view -> {
                if(getSharedPreferences("ConferenceData",MODE_PRIVATE).getString("CData","").equals("")){
                    Toast.makeText(UserData.this,"Please add a Conference First",Toast.LENGTH_SHORT).show();
                }else{
                startActivity(new Intent(UserData.this, ConferenceUserData.class));}
            });
        saveB.setOnClickListener(view -> {
            SharedPreferences sp= getSharedPreferences("user",MODE_PRIVATE);
            sp.edit().putString(EntrantMain.getUser(),""+DELIMETER+name.getText()+" "+DELIMETER+email.getText()+" "+DELIMETER+number.getText()+" "+DELIMETER+company.getText()
                    +" "+DELIMETER+educationLevel.getSelectedItem().toString()+LinkedUrl.getText()+" ").apply();
            Toast.makeText(UserData.this,"Saved",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(UserData.this,EntrantMain.class));
        });

        SharedPreferences sp= getSharedPreferences("user",MODE_PRIVATE);
        if(!sp.getString(EntrantMain.getUser(),"").equals("")) {
            Scanner sc = new Scanner(sp.getString(EntrantMain.getUser(), ""));
            String s=sc.next();
            name.setText(s.substring(0,s.length()-2));
            s=sc.next();
            email.setText(s.substring(0,s.length()-2));
            s=sc.next();
            number.setText(s.substring(0,s.length()-2));
            s=sc.next();
           company.setText(s.substring(0,s.length()-2));
           s=sc.next();
           s=sc.next();
           LinkedUrl.setText(s.substring(0,s.length()-2));

        }



    }

}
