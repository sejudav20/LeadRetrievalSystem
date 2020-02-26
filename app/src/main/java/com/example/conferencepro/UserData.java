package com.example.conferencepro;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


import android.util.Log;
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
    public static  String DELIMETER="&/;-;/&";
    EditText currentRole;
    String user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data);
         user=getSharedPreferences("user",0).getString("username","");
        saveB=findViewById(R.id.save);
        toConferenceSettingsB=findViewById(R.id.toConferenceButton);
        email=findViewById(R.id.TimesVisited);
        name=findViewById(R.id.NameEdit);
        LinkedUrl=findViewById(R.id.linked);
        educationLevel=findViewById(R.id.education);
        number=findViewById(R.id.phone);
        company=findViewById(R.id.Company);
        currentRole=findViewById(R.id.currentRoleView);
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
                if(getSharedPreferences("ConferenceData",MODE_PRIVATE).getString(user+" CData","").equals("")){
                    Toast.makeText(UserData.this,"Please add a Conference First",Toast.LENGTH_SHORT).show();
                }else{

                    Intent i=new Intent(UserData.this, ConferenceUserData.class);
                    i.putExtra("user",user);
                startActivity(i);}
            });
        saveB.setOnClickListener(view -> {
            Log.d("testing","user "+user);
            SharedPreferences sp= getSharedPreferences(user,MODE_PRIVATE);
            sp.edit().putString("userData",""+DELIMETER+name.getText()+" "+DELIMETER+email.getText()+" "+DELIMETER+number.getText()+" "+DELIMETER+company.getText()
                    +" "+DELIMETER+currentRole.getText()+" "+DELIMETER+educationLevel.getSelectedItem().toString()+DELIMETER+LinkedUrl.getText()+" ").apply();
            Toast.makeText(UserData.this,"Saved",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(UserData.this,EntrantMain.class));
        });

        SharedPreferences sp= getSharedPreferences(user,MODE_PRIVATE);
        if(!sp.getString("userData","").equals("")) {
            Scanner sc = new Scanner(sp.getString("userData", ""));
            Log.d("testing",sp.getString("userData",""));
            sc.useDelimiter(DELIMETER);
            String s=sc.next();
            Log.d("testing","Name "+s);
            name.setText(s.substring(0,s.length()-1));
            s=sc.next();
            email.setText(s.substring(0,s.length()-1));
            s=sc.next();
            number.setText(s.substring(0,s.length()-1));
            s=sc.next();
           company.setText(s.substring(0,s.length()-1));
           s=sc.next();
           currentRole.setText(s.substring(0,s.length()-1));
           s=sc.next();
           s=sc.next();
           LinkedUrl.setText(s.substring(0,s.length()-1));

        }



    }

}
