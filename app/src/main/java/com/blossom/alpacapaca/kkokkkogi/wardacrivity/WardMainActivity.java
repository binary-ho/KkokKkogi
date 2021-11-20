package com.blossom.alpacapaca.kkokkkogi.wardacrivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.blossom.alpacapaca.kkokkkogi.Model.Ward;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.blossom.alpacapaca.kkokkkogi.ChatActivity;
import com.blossom.alpacapaca.kkokkkogi.R;
import com.blossom.alpacapaca.kkokkkogi.StartActivity;

import java.util.HashMap;

public class WardMainActivity extends AppCompatActivity {

    Intent intent;

    FirebaseUser loginWard;
    DatabaseReference reference;
    Ward ward;

    private static String loginWardId;
    private static String parentId;
    private static String isWard;
    private static String wardName;

    FloatingActionButton chatButton;
    TextView textView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ward_main);

        Log.d("WardMainActivity", "onCreate()!");

        // 방금 로그인한 아이디가 누구니
        loginWard = FirebaseAuth.getInstance().getCurrentUser();
        loginWardId = loginWard.getUid();
        isWard = "true";
//        intent = getIntent();
//        String id_temp = intent.getStringExtra("loginUserId");
//        if(id_temp != null) {
//            loginWardId = id_temp;
//        }
//        String isWard_temp = intent.getStringExtra("isWard");
//        if(isWard_temp != null) {
//            isWard = isWard_temp;
//        }
        Toast.makeText(WardMainActivity.this, loginWardId + "로 로그인", Toast.LENGTH_SHORT).show();

        // 액션바 할당, 타이틀 지우기
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        reference = FirebaseDatabase.getInstance().getReference("Wards").child(loginWardId);
        Log.d("WardMain", "reference: " + reference.toString());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                parentId = snapshot.getValue(String.class);
                Log.d("WardMain", "Load parentId!");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        textView1 = findViewById(R.id.ward_main_text1);
        if(parentId != null){
            reference = FirebaseDatabase.getInstance().getReference("Users").child(parentId).child("Wards").child(loginWardId);
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    ward = snapshot.getValue(Ward.class);
                    wardName = ward.getNameForWard();
                    Log.d("WardMainActivity", "wardName: " + wardName);
                    textView1.setText(wardName + "님 안녕하세요!");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        if(wardName == null) {
            wardName = "회원";
            textView1.setText(wardName + "님 안녕하세요!");
        }

        chatButton = findViewById(R.id.chat_button);
        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(WardMainActivity.this , WardChatActivity.class);
                Intent intent = new Intent(WardMainActivity.this , ChatActivity.class);
                intent.putExtra("wardId", loginWardId);
                intent.putExtra("userId", parentId);
                intent.putExtra("isWard", "true");

                startActivity(intent);
            }
        });
        online(true);



        // 바텀 네비게이션 삭제
        // 바텀 네비게이션 뷰랑 프레그먼트
//        wardMainFragment = new WardMainFragment();
//        wardChatFrgment = new WardChatFrgment();

//        getSupportFragmentManager().beginTransaction().replace(R.id.container, wardMainFragment).commit();
//        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
//        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                switch (item.getItemId()) {
//                    case R.id.mainTab:
//                        getSupportFragmentManager().beginTransaction().replace(R.id.container, wardMainFragment).commit();
//                        return true;
//                    case R.id.chatTab:
//                        getSupportFragmentManager().beginTransaction().replace(R.id.container, wardChatFrgment).commit();
//                        return true;
//                }
//                return true;
//            }
//        });
    }

    // 메뉴 onCreate
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    // 옵션메뉴 버튼
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                Toast.makeText(WardMainActivity.this, FirebaseAuth.getInstance().getUid() + " sign out!", Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut();
                Intent logoutIntent = new Intent(WardMainActivity.this, StartActivity.class);
                logoutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                //logoutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // ???
                startActivity(logoutIntent);
                this.finish();
                return true;
        }
        return false;
    }

//    public static FirebaseUser getUser() {
//        return firebaseUser;
//    }

    // 1: online
    // 0: offline
    public void online(boolean online) {
        if(parentId == null) {
            Log.d("WardMainActivity", "Pass online()!");
            return; }
        reference = FirebaseDatabase.getInstance().getReference("Users").child(parentId).child("Wards").child(loginWardId);
        // why Object?
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("online", online);
        reference.updateChildren(hashMap);
        Log.d("WardMainActivity", "Load online()!");
    }

    @Override
    protected void onStop() {
        super.onStop();
        online(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        online(true);
    }

    public static String getLoginWardId() {
        return loginWardId;
    }
    public static String getLoginWardParentId() {
        return parentId;
    }
    public static String getIsWard() {
        return isWard;
    }
    public static String getWardName() {
        return wardName;
    }
}