package com.blossom.alpacapaca.kkokkkogi.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blossom.alpacapaca.kkokkkogi.Model.HourMin;
import com.blossom.alpacapaca.kkokkkogi.R;
import com.blossom.alpacapaca.kkokkkogi.useractivity.AddMedicinesActivity;

import java.util.ArrayList;

public class RegisterMedicineTimeAdapter  extends RecyclerView.Adapter<RegisterMedicineTimeAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<HourMin> times = new ArrayList<>();

    Intent intent;

    public RegisterMedicineTimeAdapter(Context mContext, ArrayList<HourMin> times) {
        this.mContext = mContext;
        this.times = times;
    }

    @NonNull
    @Override
    public RegisterMedicineTimeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.add_medicine_time_item, parent, false);
        return new RegisterMedicineTimeAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RegisterMedicineTimeAdapter.ViewHolder holder, int position) {
        HourMin hourMin = times.get(position);
        int hour = hourMin.getHour();
        int min = hourMin.getMin();
        String am = "AM";
        // pm 12시는 오후
        if(hour >= 12) {
            am = "PM";
            if(hour > 13) {
                hour -= 12;
            }
        }
        holder.time_box.setText((position+1) + ". " + am + " " + hour + "시 " + min + "분");
        holder.delete_time_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                times.remove(position);
                AddMedicinesActivity.notifyDataSetChanged();
            }
        });
    }


    @Override
public int getItemCount() {
        if(times != null){
        return times.size();
        }
        return 0;
        }

static class ViewHolder extends RecyclerView.ViewHolder {
    public TextView time_box;
    public Button delete_time_button;

    public ViewHolder(View itemView) {
        super(itemView);
        time_box = itemView.findViewById(R.id.time_box);
        delete_time_button = itemView.findViewById(R.id.time_delete);
    }
}


    public void addItem(HourMin item) {
        times.add(item);
    }
    public void setTimes(ArrayList<HourMin> times) {
        this.times = times;
    }
    public HourMin getItem(int position) {
        return times.get(position);
    }
    public void setItem(int position, HourMin item) {
        times.set(position, item);
    }
}
