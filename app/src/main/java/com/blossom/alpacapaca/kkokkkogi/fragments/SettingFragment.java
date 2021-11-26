package com.blossom.alpacapaca.kkokkkogi.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.blossom.alpacapaca.kkokkkogi.R;
import com.blossom.alpacapaca.kkokkkogi.useractivity.MainActivity;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SettingFragment extends Fragment {
    View rootView;
    TextView title;
    TextView textView1;
    Multimap<String, String> multimap;
    String keykey;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_setting, container, false);
        textView1 = rootView.findViewById(R.id.fragment_setting_text1);
        //textView1.setText(MainActivity.getLoginUserName() + "님 안녕하세요!");

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Test");

        multimap = HashMultimap.create();
        String str = "1200";
        multimap.put(str, "인슐린");
        multimap.put(str, "타이레놀");
        multimap.put(str, "개비스콘");
        multimap.put(str, "인슐린");
        multimap.put(str, "비타민 B");
        multimap.put(str, "쿠퍼스");
        multimap.put(str, "인슐린");
        textView1.setText(multimap.get(str).toString());

        ArrayList<String> arrayList = new ArrayList<>(multimap.get(str));
        reference.child("times").child(str).setValue(arrayList);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        title = getActivity().findViewById(R.id.fragment_name);
        if(title != null) {
            title.setText("설정");
        }


    }

    // 그냥 주석표시하기 싫어서
    public void guavaTest() {

        // 구아바 멀티맵 테스트
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Test");

        Multimap<String, String> scores = HashMultimap.create();
        String str = "0900";
        scores.put(str, "타이레놀");
        scores.put(str, "개비스콘");
        scores.put(str, "인슐린");
        scores.put(str, "비타민 B");
        scores.put(str, "쿠퍼스");
        textView1.setText(scores.get(str).toString());

        ArrayList<String> arrayList = new ArrayList<>(scores.get(str));
        reference.child("times").child(str).setValue(arrayList);
    }

    public void guavaRead() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Test").child("times");
        multimap = HashMultimap.create();
        ArrayList<String> strArray = new ArrayList<>();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot key : snapshot.getChildren()) {
                    strArray.add((String) key.getKey());
                    for(DataSnapshot value : key.getChildren()){
                        multimap.put((String) key.getKey(), (String) value.getValue());
                    }
                }
                for(String elem: strArray) {
                    textView1.setText(multimap.get(elem).toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}