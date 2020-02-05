package com.example.conferencepro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.nearby.connection.Strategy;

public class CoordinatorMain extends AppCompatActivity {
    String conference;
    SharedPreferences sp;
    NearbyCreator nc;
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_main);
        sp=getSharedPreferences("CoordData",MODE_PRIVATE);
        conference=sp.getString("cName","");
        name="";
        nc= new NearbyCreator(this,name, Strategy.P2P_CLUSTER);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.TransferCoordinatorRole:
                if (!conference.equals("")) {
                    Toast.makeText(CoordinatorMain.this,"Please be a few feet whithin the new phone with this conference role", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(CoordinatorMain.this,"Please add a conference first", Toast.LENGTH_LONG).show();
                }

                break;
            case R.id.ReceiveCoordinatorRole:
                Toast.makeText(CoordinatorMain.this,"Please add a conference first", Toast.LENGTH_LONG).show();
                break;

            default:
                return super.onOptionsItemSelected(item);

        }

        return true;

    }

}
