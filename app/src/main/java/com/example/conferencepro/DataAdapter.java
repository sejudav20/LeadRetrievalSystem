package com.example.conferencepro;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

public class DataAdapter extends
        RecyclerView.Adapter<DataAdapter.Holder> {
    List<EntrantData>allData;
    ApplicantRepository ar;
    Activity c;

    public DataAdapter(Activity context, List<EntrantData> allData, ApplicantRepository ar){
        if(allData!=null) {
            this.allData = allData;
        }else{ this.allData = new ArrayList<>();}
        c=context;
        this.ar=ar;
    }
    public void updateAllData(List<EntrantData> ed){
        if(ed!=null) {
            allData = ed;
        }else{
            allData=new ArrayList<>();
        }
        notifyDataSetChanged();

    }


    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout li=(LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.data_display,parent,false);
        return new Holder(li);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
            EntrantData ed=allData.get(position);
            holder.timesVisited.setText("Times Visited: "+ed.getTimesVisited()+"");
            holder.timeStayed.setText("Time Stayed(Seconds): "+ed.getTimeStayed()+"");
            holder.name.setText(ed.getName());
            holder.ly.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        AlertDialog.Builder dg= new AlertDialog.Builder(c);
                    LayoutInflater inflater = c.getLayoutInflater();
                    View dialogView=inflater.inflate(R.layout.view_data_box, null);

                    dg.setTitle(ed.getName()+"'s info");
                    try {
                       ApplicantInfo ai= ar.getSpecificApplicantInfo(ed.getName()).get(0);
                       TextView email= dialogView.findViewById(R.id.emailView);

                       email.setText("Email: "+ai.getEmail());
                        TextView number= dialogView.findViewById(R.id.numberView);

                        number.setText("Number: "+ai.getNumber());
                        TextView education= dialogView.findViewById(R.id.educationView);

                        education.setText("Education: "+ai.getEducationLevel());
                        TextView linked= dialogView.findViewById(R.id.linkedIView);

                        linked.setText("LinkedIn Profile: "+ai.getLinkedIn());
                        TextView currentRole= dialogView.findViewById(R.id.currentRoleView);

                        currentRole.setText("Current Role: "+ai.getCurrentRole());
                        TextView company= dialogView.findViewById(R.id.companyView);

                        company.setText("Company: "+ai.getCompany());
                        dg.setView(dialogView);
                        AlertDialog de=dg.create();
                        de.show();

                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                }
            });

    }

    @Override
    public int getItemCount() {
        return allData.size();
    }

    class Holder extends RecyclerView.ViewHolder {

        TextView name;
        TextView timesVisited;
        TextView timeStayed;
        Button ly;
        public Holder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.Name);
            timeStayed=itemView.findViewById(R.id.TimeStayed);
            timesVisited=itemView.findViewById(R.id.TimesVisited);
            ly=itemView.findViewById(R.id.viewMoreInfo);
        }
    }
}

