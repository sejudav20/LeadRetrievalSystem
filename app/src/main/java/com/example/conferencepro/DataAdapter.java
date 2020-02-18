package com.example.conferencepro;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

public class DataAdapter extends
        RecyclerView.Adapter<DataAdapter.Holder> {
    List<EntrantData>allData;

    public DataAdapter(List<EntrantData> allData){
        this.allData=allData;

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

