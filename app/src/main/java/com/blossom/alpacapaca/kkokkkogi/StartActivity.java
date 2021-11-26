package com.blossom.alpacapaca.kkokkkogi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.blossom.alpacapaca.kkokkkogi.useractivity.MainActivity;
import com.blossom.alpacapaca.kkokkkogi.wardacrivity.WardMainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

// 첫 화면
public class StartActivity extends AppCompatActivity {
//    private static final String TAG = "Start";
//    private FirebaseAuth mAuth;

    ImageButton register;
    ImageButton login;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        // 다크모드 비활성화
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        login = findViewById(R.id.login);
        register = findViewById(R.id.register);

        // 로그인 버튼 클릭 -> 로그인 화면 이동
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StartActivity.this, LoginActivity.class));
            }
        });

        // 회원가입 버튼 클릭 -> 회원가입 화면 이동동
       register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StartActivity.this, RegisterActivity.class));
            }
        });
        //mAuth = FirebaseAuth.getInstance();
    }

    // 자동 로그인 기능을 위한 onStart
    // 굳이 없어도 될 것 같고, 오류 발생
    // 최근 접속자를 블러오는 방식 때문에 아무래도 최근에 만든 이용인 계정을 불러와버리는 듯.
    // getCurrentUser에 대한 이해 필요
    @Override
    protected void onStart() {
        super.onStart();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        // null 체크
        if(firebaseUser != null) {
            Intent intent = null;
            if(firebaseUser.getDisplayName().equals("User")){
                intent = new Intent(StartActivity.this, MainActivity.class);
//                intent.putExtra("isWard", "false");
            } else {
                intent = new Intent(StartActivity.this, WardMainActivity.class);
//                intent.putExtra("isWard", "true");
            }
            intent.putExtra("loginUserId", firebaseUser.getUid());
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }
    }
}