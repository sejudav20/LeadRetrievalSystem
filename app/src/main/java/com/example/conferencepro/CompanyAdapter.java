package com.example.conferencepro;

import android.app.Activity;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static android.content.Context.MODE_PRIVATE;

public class CompanyAdapter extends RecyclerView.Adapter<CompanyAdapter.MyViewHolder> {
    HashMap<String,Boolean> data;
    ArrayList<String> keys;
    SharedPreferences spi;
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout lI=(LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.companyadapter,parent,false);
        TextView tv= lI.findViewById(R.id.tvCompany);
        CheckBox cv= lI.findViewById(R.id.companyCheckbox);
        MyViewHolder v= new MyViewHolder(tv,cv);

        return v;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
          holder.tv.setText(keys.get(position));
          if(!spi.contains(keys.get(position))){
              spi.edit().putBoolean(keys.get(position),true).apply();

          }
          holder.b.setChecked(data.get(keys.get(position)));
          holder.b.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
              @Override
              public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                  data.put(keys.get(position),b);
                  spi.edit().putBoolean(keys.get(position),b).apply();
              }
          });
    }
    public CompanyAdapter(HashMap<String,Boolean> data, Activity context){

        this.data=data;
        keys= new ArrayList<>(data.keySet());
        spi=context.getSharedPreferences(EntrantMain.user+" conferenceJobs",MODE_PRIVATE);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv;
        CheckBox b;

        public MyViewHolder(@NonNull TextView itemView,CheckBox cb) {
            super(itemView);
            b=cb;
            tv=itemView;
        }
    }




}
