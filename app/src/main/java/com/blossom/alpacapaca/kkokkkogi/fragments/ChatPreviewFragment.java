package com.blossom.alpacapaca.kkokkkogi.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.blossom.alpacapaca.kkokkkogi.Model.Chat;
import com.blossom.alpacapaca.kkokkkogi.Model.Ward;
import com.blossom.alpacapaca.kkokkkogi.R;
import com.blossom.alpacapaca.kkokkkogi.adapter.ChatPreviewAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Comment;

import java.util.ArrayList;
import java.util.HashMap;

public class ChatPreviewFragment extends Fragment {
    private View rootView;
    private TextView title;
    private RecyclerView recyclerView;

    FirebaseUser loginUser;
    String loginId;
    DatabaseReference reference;

    private ArrayList<Ward> chatPreviewList;
    private ChatPreviewAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView =inflater.inflate(R.layout.fragment_chat_preview, container, false);

        recyclerView = rootView.findViewById(R.id.fragment_chat_preview_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        chatPreviewList = new ArrayList<>();
        loginUser = FirebaseAuth.getInstance().getCurrentUser();
        loginId = loginUser.getUid();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(loginId).child("Wards");
        readChat2(reference);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        title = getActivity().findViewById(R.id.fragment_name);
        if(title != null) {
            title.setText("대화");
        }
        Toast.makeText(getContext(), "onResume() 실행", Toast.LENGTH_SHORT).show();
        if(reference!=null){
            readChat2(reference);
        }
    }
    // 두 가지 방법으로 데이터 불러오기 테스트
    // 기존 방식
    public void readChat1() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chatPreviewList.clear();
                for(DataSnapshot elem: snapshot.getChildren()){
                    Log.d("PreviewFragments", elem.toString());
                    Ward ward =  elem.getValue(Ward.class);
                    if(ward.chatsSize() != 0) {
                        chatPreviewList.add(ward);
                    }
                }

                adapter = new ChatPreviewAdapter(getContext(), chatPreviewList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    // 새로 해보려는 방식
    public void readChat2(DatabaseReference reference) {
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                //Log.d("PreviewFragments", dataSnapshot.toString());
                Ward ward =  dataSnapshot.getValue(Ward.class);
                chatPreviewList.clear();
                if(ward.chatsSize() != 0) {
                    chatPreviewList.add(ward);
                }
//                for(DataSnapshot elem: dataSnapshot.getChildren()){
//                    Log.d("PreviewFragments", elem.toString());
//                    Ward ward =  elem.getValue(Ward.class);
//                    if(ward.chatsSize() != 0) {
//                        chatPreviewList.add(ward);
//                    }
//                }
                adapter = new ChatPreviewAdapter(getContext(), chatPreviewList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                //Log.d("PreviewFragments", dataSnapshot.toString());
                Ward ward =  dataSnapshot.getValue(Ward.class);
                chatPreviewList.clear();
                if(ward.chatsSize() != 0) {
                    chatPreviewList.add(ward);
                }
                adapter = new ChatPreviewAdapter(getContext(), chatPreviewList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}