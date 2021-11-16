package com.blossom.alpacapaca.kkokkkogi;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.blossom.alpacapaca.kkokkkogi.adapter.MessageAdapter;
import com.blossom.alpacapaca.kkokkkogi.Model.Chat;
import com.blossom.alpacapaca.kkokkkogi.Model.Ward;

import com.blossom.alpacapaca.kkokkkogi.R;

import java.util.ArrayList;
import java.util.HashMap;


public class ChatActivity extends AppCompatActivity {

    private  static String userId;
    private static String wardId;
    private static String isWard;
    String wardname;

    TextView wardnameView;
    //CircleImageView profile_image;
    ImageButton button_send;
    EditText text_send;

    //FirebaseUser fuser;
    DatabaseReference reference;

    MessageAdapter messageAdapter;
    ArrayList<Chat> chats;
    RecyclerView recyclerView;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        intent = getIntent();
        userId = intent.getStringExtra("userId");
        wardId = intent.getStringExtra("wardId");
        isWard = intent.getStringExtra("isWard");
//        Log.d("ChatActivity", userId);
//        Log.d("ChatActivity", wardId);
        Log.d("ChatActivity", isWard);

        // 리사이클러뷰 설정
        recyclerView = findViewById(R.id.chat_recycler_view);
        recyclerView.setHasFixedSize(true);

        //LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ChatActivity.this);
        linearLayoutManager.setStackFromEnd(true);  // ?? 검색 ㄱㄱ
        recyclerView.setLayoutManager(linearLayoutManager);

        wardnameView = findViewById(R.id.user_name_title);
        //profile_image = findViewById(R.id.profile_image);
        text_send = findViewById(R.id.text_send);
        button_send = findViewById(R.id.button_send);
        button_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = text_send.getText().toString();
                // 비었는지는 체크
                if(!message.equals("")) {
                    //sendMessage(userId, wardId, message);
                    if(isWard.equals("false")) {
                        // 유저일때
                        sendMessage(userId, wardId, message);
                    } else {
                        sendMessage(wardId, userId, message);
                    }
                }
                text_send.setText("");
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar_activity_chat);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        reference = FirebaseDatabase.getInstance().getReference("Users").child(userId).child("Wards").child(wardId);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Ward ward = snapshot.getValue(Ward.class);
                // 너야말로 구분이 필요했어
                //wardnameView.setText(ward.getNameForMe());

                readMessage(userId, wardId, "default");
                //profile_image.setImageResource(R.drawable.chicken);
                // 구분
//                if (isWard.equals("false")) {
//                    // ward가 아닐때
//                    readMessage(userId, wardId, "default");
//
//                } else {
//                    // ward일때
//                    readMessage(wardId, userId, "default");
//                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//    }

    private void sendMessage(String sender, String receiver, String message) {

        reference = FirebaseDatabase.getInstance().getReference("Users").child(userId).child("Wards").child(wardId).child("Chats");

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("receiver", receiver);
        hashMap.put("message", message);

        Chat chat = new Chat(sender, receiver, message);
        //referenceMassage.child("Chats").push().setValue(hashMap);
        reference.push().setValue(chat);

    }

    private void readMessage(final String senderId, final String receiverId, final String imageUrl) {
        chats = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(userId).child("Wards").child(wardId).child("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chats.clear();
                for(DataSnapshot elem : snapshot.getChildren()) {
                    Chat chat = elem.getValue(Chat.class);
                    // 내 경우엔 무조건 이 조건을 만족하는 것 같아.
//                    if(chat.getSender().equals(userId) && chat.getReceiver().equals(wardId) ||
//                    chat.getSender().equals(wardId) && chat.getReceiver().equals(userId)) {
//                        chats.add(chat);
//                    }
                    chats.add(chat);
                    // 원래는 여기인데 옮겨볼게
//                    messageAdapter = new MessageAdapter(getBaseContext(), chats, imageUrl);
//                    //messageAdapter = new MessageAdapter(ChatActivity.this, chats, imageUrl);
//                    recyclerView.setAdapter(messageAdapter);
                }
                // 너냐??
                //messageAdapter = new MessageAdapter(getBaseContext(), chats, imageUrl);
                //messageAdapter = new MessageAdapter(ChatActivity.this, chats, imageUrl);
                messageAdapter = new MessageAdapter(ChatActivity.this, chats, imageUrl);
                recyclerView.setAdapter(messageAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public static String getUserId() {return userId;}
    public static String getWardId() {return wardId;}
    public static String getIsWard() {return isWard;}
}