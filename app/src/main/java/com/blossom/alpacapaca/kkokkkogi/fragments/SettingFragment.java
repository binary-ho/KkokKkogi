package com.blossom.alpacapaca.kkokkkogi.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.blossom.alpacapaca.kkokkkogi.Model.HourMin;
import com.blossom.alpacapaca.kkokkkogi.Model.User;
import com.blossom.alpacapaca.kkokkkogi.R;
import com.blossom.alpacapaca.kkokkkogi.useractivity.MainActivity;
import com.blossom.alpacapaca.kkokkkogi.useractivity.SetChattingTimeActivity;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.firebase.auth.FirebaseUser;
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
    TextView textView2;
    CardView cardView1;

    Multimap<String, String> multimap;
    String keykey;

    FirebaseUser user;

    DatabaseReference reference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_setting, container, false);

        cardView1 = rootView.findViewById(R.id.setting_card1);
        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(rootView.getContext(), SetChattingTimeActivity.class);
                intent.putExtra("userId", user.getUid());
                startActivity(intent);
            }
        });
        textView2 = rootView.findViewById(R.id.fragment_setting_text2);

        user = MainActivity.getUser();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                Log.d("Setting", user.toString());
                String str ="(?????? " + user.getStartHour() + ":" + user.getStartMin() +" ~ " + user.getEndHour() + ":" + user.getEndMin() + " ??????)";
                textView2.setText(str);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        title = getActivity().findViewById(R.id.fragment_name);
        if(title != null) {
            title.setText("??????");
        }


    }

    // ?????? ?????????????????? ?????????
//    public void guavaTest() {
//
//        // ????????? ????????? ?????????
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Test");
//
//        Multimap<String, String> scores = HashMultimap.create();
//        String str = "0900";
//        scores.put(str, "????????????");
//        scores.put(str, "????????????");
//        scores.put(str, "?????????");
//        scores.put(str, "????????? B");
//        scores.put(str, "?????????");
//        //textView1.setText(scores.get(str).toString());
//
//        ArrayList<String> arrayList = new ArrayList<>(scores.get(str));
//        reference.child("times").child(str).setValue(arrayList);
//    }

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
                    //textView1.setText(multimap.get(elem).toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}