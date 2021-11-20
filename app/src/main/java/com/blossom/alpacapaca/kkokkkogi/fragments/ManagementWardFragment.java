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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blossom.alpacapaca.kkokkkogi.Model.Ward;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.blossom.alpacapaca.kkokkkogi.adapter.WardAdapter;
import com.blossom.alpacapaca.kkokkkogi.useractivity.AddWardActivity;
import com.blossom.alpacapaca.kkokkkogi.useractivity.MainActivity;
import com.blossom.alpacapaca.kkokkkogi.R;

import java.util.ArrayList;

public class ManagementWardFragment extends Fragment {

    FloatingActionButton addWardButton;
    TextView title;

    View rootView;
    private LinearLayoutManager layoutManager;
    private RecyclerView recyclerView;
    private WardAdapter wardAdapter;

    FirebaseUser loginUser;

    private DatabaseReference wardReference;
    private ArrayList<Ward> mWards;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_management_ward, container, false);
        // 책 버젼
        //layoutManager = new LinearLayoutManager(rootView.getContext(), LinearLayoutManager.VERTICAL, false);
        //recyclerView.setLayoutManager(layoutManager);
        recyclerView = rootView.findViewById(R.id.wardManagementRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mWards = new ArrayList<>();
        loginUser = FirebaseAuth.getInstance().getCurrentUser();
        readWards();
        //wardAdapter = new WardAdapter(rootView.getContext());
        //recyclerView.setAdapter(wardAdapter);

        // 이 아래를 다 뺀다
//        wardReference = FirebaseDatabase.getInstance().getReference("Users").child(MainActivity.getLoginUserId()).child("Wards");
//        wardReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for(DataSnapshot elem: snapshot.getChildren()) {
//                    String str5 = elem.getValue().toString();
//                    Log.d("WardManagementFrag", str5);
//
//                    Ward ward = elem.getValue(Ward.class);
//                    wardAdapter.addItem(ward);
//                    //mWards.add(ward);
//                    Log.d("WardManagementFrag", ward.getNameForMe() + "업뎃 성공");
//                }
//                //Ward str5 = snapshot.getValue();
//                //Log.d("WardManagementFrag", str5.indexOf(0) + str5.indexOf(1));
//                // 어레이 버젼
////                WardArray wardArray = snapshot.getValue(WardArray.class);
////                if(wardArray != null) {
////                    mWards = wardArray.getWardArray();
////                }
//                // ward 버전
////                Ward ward = snapshot.getValue(Ward.class);
////                mWards.add(ward);
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//            }
//        });



        //recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        addWardButton = rootView.findViewById(R.id.addWardButton);
        addWardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 의도대로 잘 될지 모르겠어
                Intent intent = new Intent( rootView.getContext(), AddWardActivity.class);
                intent.putExtra("loginUserId", loginUser.getUid());
                intent.putExtra("loginEmail", loginUser.getEmail());
                intent.putExtra("loginPassword", MainActivity.getLoginPassword());
                //startActivity(new Intent(rootView.getContext(), AddWardActivity.class));
                startActivity(intent);
            }
        });
        //recyclerView.setHasFixedSize(true); 우리가 쓰는 뷰는 가변 뷰이기 때문에 안 될거 같아

        //mWards = new ArrayList<>();
        // 얘네 빼봐
//        mWards.add(new Ward("진호", 96));
//        mWards.add(new Ward("지수", 96));
        //wardAdapter = new WardAdapter(getContext(), mWards);


        //readWards();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        title = getActivity().findViewById(R.id.fragment_name);
        if(title != null) {
            title.setText("조회");
        }
    }

    private void readWards() {
        wardReference = FirebaseDatabase.getInstance().getReference("Users").child(loginUser.getUid()).child("Wards");


        wardReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mWards.clear();
                for(DataSnapshot elem: snapshot.getChildren()) {
                    Log.d("ManagementWard", elem.toString());
                    Ward ward = elem.getValue(Ward.class);
                    assert ward != null;
                    mWards.add(ward);
                }
                wardAdapter = new WardAdapter(rootView.getContext(), mWards, true);
                recyclerView.setAdapter(wardAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}