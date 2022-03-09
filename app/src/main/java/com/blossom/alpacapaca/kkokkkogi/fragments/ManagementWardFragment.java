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
    private ArrayList<Ward> mWards;
    private DatabaseReference wardReference;

    FirebaseUser loginUser;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_management_ward, container, false);
        //layoutManager = new LinearLayoutManager(rootView.getContext(), LinearLayoutManager.VERTICAL, false);
        //recyclerView.setLayoutManager(layoutManager);

        title = getActivity().findViewById(R.id.fragment_name);

        recyclerView = rootView.findViewById(R.id.wardManagementRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mWards = new ArrayList<>();
        loginUser = FirebaseAuth.getInstance().getCurrentUser();
        readWards(loginUser);

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
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        title = getActivity().findViewById(R.id.fragment_name);
        if(title != null) {
            title.setText("조회");
        }
        // 의도는 다시 시작할때도 언제든지 다시 불러오게끔 하고 싶었는데, 오히려 과하게 불러오게 만든거 같음
        // readWards();
    }

    private void readWards(FirebaseUser user) {
        wardReference = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid()).child("Wards");
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