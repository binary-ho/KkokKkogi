package com.blossom.alpacapaca.kkokkkogi.adapter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerTitleStrip;

import com.blossom.alpacapaca.kkokkkogi.Model.Medicine;
import com.blossom.alpacapaca.kkokkkogi.Model.TimeForMedicines;
import com.blossom.alpacapaca.kkokkkogi.Model.User;
import com.blossom.alpacapaca.kkokkkogi.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class TimesForMedicineAdapter extends RecyclerView.Adapter<TimesForMedicineAdapter.ViewHolder>{

    private Context mContext;
    private ArrayList<TimeForMedicines> times = new ArrayList<>();

    public TimesForMedicineAdapter(Context mContext) {
        this.mContext = mContext;
    }
    public TimesForMedicineAdapter(Context mContext, ArrayList<TimeForMedicines> times) {
        this.mContext = mContext;
        this.times = times;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.time_for_medicine_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TimeForMedicines timeForMedicines = this.times.get(position);
        holder.time.setText(timeForMedicines.getTime());

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(timeForMedicines.getUserId())
                .child("Wards").child(timeForMedicines.getWardId()).child("MedicinesState")
                .child(timeForMedicines.getHour()+timeForMedicines.getMin());

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeForMedicines.getHour()));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeForMedicines.getMin()));

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (System.currentTimeMillis() > calendar.getTimeInMillis()) {
                    String state = snapshot.getValue(String.class);
                    if(state.equals("false")) {
                        holder.scrollView.setBackgroundColor(Color.parseColor("#44ff0000"));
                    } else {
                        holder.scrollView.setBackgroundColor(Color.parseColor("#4405df29"));
                    }
                } else {
                    holder.scrollView.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //holder.time.setHighlightColor(Color.parseColor("#fdd94d"));


        ArrayList<Medicine> medicines = new ArrayList<>();
        medicines.clear();
        medicines.add(new Medicine("타이레놀", "12", "00"));
    }

    @Override
    public int getItemCount() {
        if (times != null) {
            return times.size();
        }
        return 0;
    }

    @Override
    public void onViewRecycled(@NonNull ViewHolder holder) {
        super.onViewRecycled(holder);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView time;
        public RecyclerView recyclerView;
        public HorizontalScrollView scrollView;

        public ViewHolder(View itemView) {
            super(itemView);

            time = itemView.findViewById(R.id.time_for_medicine_text);
            //profile_image = itemView.findViewById(R.id.imageView2);
            recyclerView = itemView.findViewById(R.id.medicine_recycler_view);
            scrollView = itemView.findViewById(R.id.scroll_view_for_medicine);
        }

        public void setItem(TimeForMedicines timeForMedicines) {
            time.setText(timeForMedicines.getTime());
        }
    }

    public void addItem(TimeForMedicines timeForMedicines) {
        this.times.add(timeForMedicines);
    }
    public void setMedicines(ArrayList<TimeForMedicines> times) {
        this.times = times;
    }
    public TimeForMedicines getItem(int position) {
        return times.get(position);
    }
    public void setItem(int position, TimeForMedicines timeForMedicines) {
        this.times.set(position, timeForMedicines);
    }
}
