package com.blossom.alpacapaca.kkokkkogi.wardacrivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.blossom.alpacapaca.kkokkkogi.ChatActivity;
import com.blossom.alpacapaca.kkokkkogi.R;
import com.blossom.alpacapaca.kkokkkogi.StartActivity;

public class WardMainActivity extends AppCompatActivity {

    Intent intent;

    DatabaseReference reference;

    private static String loginWardId;
    private static String parentId;
    private static String isWard;

    FloatingActionButton chatButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ward_main);

        // 방금 로그인한 아이디가 누구니
        intent = getIntent();
        String id_temp = intent.getStringExtra("loginUserId");
        if(id_temp != null) {
            loginWardId = id_temp;
        }
        String isWard_temp = intent.getStringExtra("isWard");
        if(isWard_temp != null) {
            isWard = isWard_temp;
        }
        Toast.makeText(WardMainActivity.this, loginWardId + "로 로그인", Toast.LENGTH_SHORT).show();

        // 액션바 할당, 타이틀 지우기
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        reference = FirebaseDatabase.getInstance().getReference("Wards").child(loginWardId);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                parentId = snapshot.getValue(String.class);
                Log.d("WardMain", parentId);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        chatButton = findViewById(R.id.chat_button);
        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(WardMainActivity.this , WardChatActivity.class);
                Intent intent = new Intent(WardMainActivity.this , ChatActivity.class);
                intent.putExtra("wardId", loginWardId);
                intent.putExtra("userId", parentId);
                intent.putExtra("isWard", isWard);

                startActivity(intent);
            }
        });




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
                Toast.makeText(WardMainActivity.this, FirebaseAuth.getInstance().toString() + " sign out!", Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut();
                Intent logoutIntent = new Intent(WardMainActivity.this, StartActivity.class);
                logoutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(logoutIntent);
                finish();
                return true;
        }
        return false;
    }

//    public static FirebaseUser getUser() {
//        return firebaseUser;
//    }
    public static String getLoginWardId() {
        return loginWardId;
    }
    public static String getLoginWardParentId() {
        return parentId;
    }
    public static String getIsWard() {
        return isWard;
    }
}