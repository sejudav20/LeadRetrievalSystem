package com.example.conferencepro;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.ParcelFileDescriptor;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class JobViewData extends AppCompatActivity {
    RecyclerView rv;
    DataAdapter allDataAdapter;
    ApplicantRepository ar;

    LiveData<List<EntrantData>> liveData;
    private static final int REQUEST_CODE = 1;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.data_view_menu, menu);
        MenuItem searchViewItem = menu.findItem(R.id.app_bar_search);
        SearchView sv = (SearchView) searchViewItem.getActionView();
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                try {
                    LiveData<List<EntrantData>> liv = ar.getSpecificEntrantData(query);
                    liv.observe(JobViewData.this, new Observer<List<EntrantData>>() {
                        @Override
                        public void onChanged(List<EntrantData> entrantData) {
                            allDataAdapter.updateAllData(entrantData);
                        }
                    });
                    allDataAdapter.updateAllData(liv.getValue());
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_view_data);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        rv = findViewById(R.id.recyclerData);
        rv.setLayoutManager(new LinearLayoutManager(this));
        ar = new ApplicantRepository(this.getApplication());

        liveData = ar.getAllData();
        liveData.observe(this, new Observer<List<EntrantData>>() {
            @Override
            public void onChanged(List<EntrantData> entrantData) {
                allDataAdapter.updateAllData(entrantData);
            }
        });
        allDataAdapter = new DataAdapter(this, liveData.getValue(), ar);
        rv.setAdapter(allDataAdapter);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    if(ar.getAllData().getValue()!=null){
                    Snackbar.make(view, "Downloading CSV file", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("application/csv");
                intent.putExtra(Intent.EXTRA_TITLE, "Conference_"+JobMain.jobName+"_data.csv");
                startActivityForResult(intent, REQUEST_CODE);}else{
                        Snackbar.make(view, "No Data Exists", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();

                    }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE){
            Uri uri = null;
            if (data != null) {

                uri = data.getData();

                try {
                    ParcelFileDescriptor pfd = this.getContentResolver().
                            openFileDescriptor(uri, "w");
                    FileOutputStream fileOutputStream =
                            new FileOutputStream(pfd.getFileDescriptor());

                    fileOutputStream.write(("Name,Times Visited,Total Time Stayed,Email,Phone,Current Company,Current Role" +
                            ",Education,LinkedIn").getBytes());

                    List<EntrantData> allData=ar.getAllData().getValue();

                    for(EntrantData gd:allData){
                        EntrantData ed=gd;
                        ApplicantInfo ai=ar.getSpecificApplicantInfo(ed.getName()).get(0);
                        fileOutputStream.write(("\n"+ed.getName()+","+ed.getTimesVisited()+","+ed.getTimeStayed()+","+ai.getEmail()+","
                        +ai.getNumber()+","+ai.getCompany()+","+ai.getCurrentRole()+","+ai.getEducationLevel()+","+ai.getLinkedIn()).getBytes());
                    }
                    // closing stream
                    fileOutputStream.close();
                    pfd.close();

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }


            }
        }
}
}
