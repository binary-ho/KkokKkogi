package com.blossom.alpacapaca.kkokkkogi.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blossom.alpacapaca.kkokkkogi.Model.MedicineBox;
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

public class MedicineBoxAdapter extends RecyclerView.Adapter<MedicineBoxAdapter.ViewHolder>{


    private Context mContext;
    private ArrayList<MedicineBox> boxs = new ArrayList<>();

    public MedicineBoxAdapter(Context mContext) {
        this.mContext = mContext;
    }
    public MedicineBoxAdapter(Context mContext, ArrayList<MedicineBox> box) {
        this.mContext = mContext;
        this.boxs = box;
    }

    @NonNull
    @Override
    public MedicineBoxAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.medicine_box_item, parent, false);
        return new MedicineBoxAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicineBoxAdapter.ViewHolder holder, int position) {
        MedicineBox box = this.boxs.get(position);
        holder.medicine_time.setText(box.getTime());
        holder.medicine_name.setText(box.getName());
        //holder.setItem(box);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(box.getHour()));
        calendar.set(Calendar.MINUTE, Integer.parseInt(box.getMin()));

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(user.getDisplayName()).child("Wards").child(user.getUid())
                .child("MedicinesState").child(box.getHour() + box.getMin());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                box.setState(snapshot.getValue(String.class));
                Log.d("State", "read: " + snapshot.getValue(String.class));
                if (System.currentTimeMillis() < calendar.getTimeInMillis()) {
                    // 스테이스 바꾸고 색 되돌리기
                    box.stateFalse();
                    reference.setValue("false");
                    holder.relative_layout.setBackgroundColor(Color.parseColor("#aaffd94d"));
                } else {
                    if(box.getState().equals("true")) {
                        holder.relative_layout.setBackgroundColor(Color.parseColor("#4405df29"));
                        Log.d("State", "green");
                    } else {
                        holder.relative_layout.setBackgroundColor(Color.parseColor("#44ff0000"));
                        Log.d("State", "red");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        if (System.currentTimeMillis() < calendar.getTimeInMillis()) {
            // 스테이스 바꾸고 색 되돌리기
            reference.setValue("false");
            box.stateFalse();
            holder.relative_layout.setBackgroundColor(Color.parseColor("#aaffd94d"));
        }
//        else {
//            if(box.getState().equals("true")) {
//                holder.relative_layout.setBackgroundColor(Color.parseColor("#4405df29"));
//                Log.d("State", "green");
//            } else {
//                holder.relative_layout.setBackgroundColor(Color.parseColor("#44ff0000"));
//                Log.d("State", "red");
//            }
//        }
        holder.relative_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (System.currentTimeMillis() < calendar.getTimeInMillis()) {
                    Toast.makeText(mContext, "아직 복용시간이 되지 않았습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    box.stateTrue();
                    reference.setValue("true");
                    holder.relative_layout.setBackgroundColor(Color.parseColor("#4405df29"));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (boxs != null) {
            return boxs.size();
        }
        return 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView medicine_time;
        public TextView medicine_name;
        public RelativeLayout relative_layout;

        public ViewHolder(View itemView) {
            super(itemView);
            relative_layout = itemView.findViewById(R.id.medicine_box_relative_layout);
            medicine_time = itemView.findViewById(R.id.medicine_box_time);
            medicine_name = itemView.findViewById(R.id.medicine_box_medicine);
            //profile_image = itemView.findViewById(R.id.imageView2);
        }

        public void setItem(MedicineBox box) {
            medicine_name.setText(box.getName());
            //medicine_name.setText(box.getName());
        }
    }

//    @Override
//    public void onViewRecycled(@NonNull ViewHolder holder) {
//        super.onViewRecycled(holder);
//
//        MedicineBox box = this.box.get(holder.getAdapterPosition());
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTimeInMillis(System.currentTimeMillis());
//        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(box.getHour()));
//        calendar.set(Calendar.MINUTE, Integer.parseInt(box.getMin()));
//        if (System.currentTimeMillis() > calendar.getTimeInMillis()) {
//            holder.relative_layout.setBackgroundColor(Color.parseColor("#aaffd94d"));
//        }
//    }


    public void addItem(MedicineBox box) {
        this.boxs.add(box);
    }
    public void setBoxs(ArrayList<MedicineBox> boxs) {
        this.boxs = boxs;
    }
    public MedicineBox getItem(int position) {
        return boxs.get(position);
    }
    public void setItem(int position, MedicineBox box) {
        this.boxs.set(position, box);
    }
}