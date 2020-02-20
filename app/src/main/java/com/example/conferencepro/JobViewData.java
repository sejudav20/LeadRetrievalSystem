package com.example.conferencepro;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class JobViewData extends AppCompatActivity {
RecyclerView rv;
DataAdapter allDataAdapter;
ApplicantRepository ar;
SearchView sv;
LiveData<List<EntrantData>> liveData;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.data_view_menu,menu);
        MenuItem searchViewItem= menu.findItem(R.id.app_bar_search);
        SearchView sv= (SearchView) searchViewItem.getActionView();
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                try {
                    LiveData<List<EntrantData>> liv=ar.getSpecificEntrantData(query);
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
        rv=findViewById(R.id.recyclerData);
        rv.setLayoutManager(new LinearLayoutManager(this));
        ar=new ApplicantRepository(this.getApplication());
        sv=findViewById(R.id.searchView);

        liveData=ar.getAllData();
        liveData.observe(this, new Observer<List<EntrantData>>() {
            @Override
            public void onChanged(List<EntrantData> entrantData) {
                allDataAdapter.updateAllData(entrantData);
            }
        });
        allDataAdapter=new DataAdapter(this,liveData.getValue(),ar);
        rv.setAdapter(allDataAdapter);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Downloading CSV file", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
