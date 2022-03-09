package com.blossom.alpacapaca.kkokkkogi.useractivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import com.blossom.alpacapaca.kkokkkogi.Model.HourMin;
import com.blossom.alpacapaca.kkokkkogi.R;
import com.blossom.alpacapaca.kkokkkogi.adapter.RegisterMedicineTimeAdapter;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class AddMedicinesActivity extends AppCompatActivity {

    Intent intent;

    private RecyclerView recyclerView;
    private static RegisterMedicineTimeAdapter registerMedicineTimeAdapter;
    ArrayList<HourMin> times;

    DatabaseReference referenceUser, referenceMedicine;

    MaterialEditText medicineName;
    Button apply_button, add_medicine_button;
    TimePicker timePicker;
    String userId, wardId;

    int hour;
    int min;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicines);

        //정보 가져오기
        intent = getIntent();
        userId = intent.getStringExtra("userId");
        wardId = intent.getStringExtra("wardId");

        referenceUser = FirebaseDatabase.getInstance().getReference("Users").child(userId);
        referenceMedicine = FirebaseDatabase.getInstance().getReference("Users").child(userId).child("Wards").child(wardId).child("Medicines");

        timePicker = findViewById(R.id.time_picker);
        medicineName = findViewById(R.id.name_medicine);

        recyclerView = findViewById(R.id.add_medicine_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        times = new ArrayList<>();

        Toolbar toolbar = findViewById(R.id.toolbar_add_medicines);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        apply_button = findViewById(R.id.apply_button);
        add_medicine_button = findViewById(R.id.add_medicine_time_button);

        apply_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    hour = timePicker.getHour();
                    min = timePicker.getMinute();
                } else {
                    hour = timePicker.getCurrentHour();
                    min = timePicker.getCurrentMinute();
                }
                addTime(hour, min);
            }
        });

        add_medicine_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerMedicineAndTimes(medicineName.getText().toString(), times);
            }
        });

        registerMedicineTimeAdapter = new RegisterMedicineTimeAdapter(getApplicationContext(), times);
        recyclerView.setAdapter(registerMedicineTimeAdapter);
    }

    public void addTime(int hour, int min) {
        HourMin hourMin = new HourMin(hour, min);
        boolean canPush = true;
        for(HourMin time: times) {
            if(hour == time.getHour() && min == time.getMin()) { canPush = false; break; }
        }
        if(canPush) {
            this.times.add(hourMin);
            registerMedicineTimeAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(getApplicationContext(), "시간이 중복 됐습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    public static void notifyDataSetChanged() {
        registerMedicineTimeAdapter.notifyDataSetChanged();
    }


    // 의문점: 등록 될 때도 맵으로 등록되나? 그러니까 가져와서 겹치는 것이 있는지 확인하는 과정 없이, 그냥 푸쉬한다면 어떻게 되는 걸까?
    public void registerMedicineAndTimes(String name, ArrayList<HourMin> timesForRegister) {
        // 데이터 읽어오기
        Multimap<String, String> multimap = HashMultimap.create();
        HashSet<String> keyArray = new HashSet<>();
        referenceMedicine.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot key : snapshot.getChildren()) {
                    keyArray.add((String) key.getKey());
                    for(DataSnapshot value : key.getChildren()){
                        multimap.put((String) key.getKey(), (String) value.getValue());
                    }
                }

                // 이제부터 등록.
                if(keyArray == null || multimap == null) {
//                    Log.d("AddMedicineTest", "등록 실패!");
                } else {
                    ArrayList<String> valueArray;
                    for(HourMin time: timesForRegister) {
                        String hourMin = time.getTimeString();
                        keyArray.add(hourMin);
                        multimap.put(hourMin, name);
                    }
                    for (Iterator<String> iterator = keyArray.iterator(); iterator.hasNext(); ) {
                        String key = iterator.next();
                        valueArray = new ArrayList<>(multimap.get(key));
                        referenceMedicine.child(key).setValue(valueArray);
                        referenceUser.child("Wards").child(wardId).child("MedicinesState").child(key).setValue("false");
                    }
                }
                Toast.makeText(getApplicationContext(), "약 추가 성공", Toast.LENGTH_SHORT).show();
                finish();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

}