package com.example.conferencepro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.nearby.connection.Strategy;

public class CoordinatorMain extends AppCompatActivity implements RoleTransferDialogBox.RoleTransferCallback {
    String conference;
    SharedPreferences sp;
    NearbyCreator nc;
    String name;
    boolean isReceiving;
    int conferenceNumber;
    TextView welcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_main);


        sp=getSharedPreferences("CoordData",MODE_PRIVATE);
        SharedPreferences spe=getSharedPreferences("user",MODE_PRIVATE);
        welcome.setText("Welcome "+spe.getString("username",""));

        conference=sp.getString("cName","");
        conferenceNumber=sp.getInt("cNum",0);
        if(conferenceNumber==0){



        }

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
