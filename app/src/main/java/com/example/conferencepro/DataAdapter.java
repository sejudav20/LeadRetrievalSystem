package com.example.conferencepro;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

public class DataAdapter extends
        RecyclerView.Adapter<DataAdapter.Holder> {
    List<EntrantData>allData;
    ApplicantRepository ar;
    Context c;

    public DataAdapter(Context context, List<EntrantData> allData,ApplicantRepository ar){
        this.allData=allData;
        c=context;
        this.ar=ar;
    }
    public void updateAllData(List<EntrantData> ed){
        allData=ed;
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
            holder.timesVisited.setText(ed.getTimesVisited()+"");
            holder.timeStayed.setText(ed.getTimeStayed()+"");
            holder.name.setText(ed.getName());
            holder.ly.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Dialog dg= new Dialog(c);
                    dg.setContentView(R.layout.view_data_box);
                    dg.setTitle(ed.getName()+" name");
                    try {
                       ApplicantInfo ai= ar.getSpecificApplicantInfo(ed.getUserData()).get(0);
                       TextView email= dg.findViewById(R.id.emailView);

                       email.setText("Email: "+ai.getEmail());
                        TextView number= dg.findViewById(R.id.numberView);

                        number.setText("Number: "+ai.getNumber());
                        TextView education= dg.findViewById(R.id.educationView);

                        education.setText("Education: "+ai.getEducationLevel());
                        TextView linked= dg.findViewById(R.id.linkedIView);

                        linked.setText("LinkedIn Profile: "+ai.getLinkedIn());
                        TextView currentRole= dg.findViewById(R.id.currentRoleView);

                        currentRole.setText("Current Role: "+ai.getCurrentRole());
                        TextView company= dg.findViewById(R.id.companyView);

                        currentRole.setText("Company: "+ai.getCompany());


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
        LinearLayout ly;
        public Holder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.Name);
            timeStayed=itemView.findViewById(R.id.TimeStayed);
            timesVisited=itemView.findViewById(R.id.TimesVisited);
            ly=itemView.findViewById(R.id.linearL);
        }
    }
}

