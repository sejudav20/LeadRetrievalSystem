package com.example.conferencepro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {
     String user;
     SharedPreferences sp;
    SharedPreferences.Editor spe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sp=getSharedPreferences("user",MODE_PRIVATE);
        spe= sp.edit();
        user=sp.getString("username","guest");
        //TODO Please add initial case to see if user has been here before
        if(user.equals("guest")||sp.getString(user+" role", "").equals("")){
            setContentView(R.layout.activity_main);
            final RadioGroup rg= findViewById(R.id.radioGroup);
            final Button b=findViewById(R.id.SubmitForm);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switch(rg.getCheckedRadioButtonId()){
                        case R.id.radioButton1:
                            spe.putString(user+" role","entrant").apply();
                            break;
                        case R.id.radioButton2:
                            spe.putString(user+" role","company").apply();
                            break;
                        case R.id.radioButton3:
                            spe.putString(user+" role","coordinator").apply();
                            break;
                        default:
                            b.setError("Please Choose a Role Above");
                            break;
                    }
                }
            });

            if(sp.getString(user+" role","").equals("entrant")){


            }else if(sp.getString(user+" role","").equals("entrant")){}else{



            }





        }



    }
}
