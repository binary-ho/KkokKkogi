package com.blossom.alpacapaca.kkokkkogi;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.blossom.alpacapaca.kkokkkogi.useractivity.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.blossom.alpacapaca.kkokkkogi.wardactivity.WardMainActivity;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;

// 로그인 화면
public class LoginActivity extends AppCompatActivity {


    ArrayList<Boolean> isWard;
    MaterialEditText email, password;
    Button button_login;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        isWard = new ArrayList<>();

        // 액션바 설정
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        auth = FirebaseAuth.getInstance();

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        button_login = findViewById(R.id.button_login);

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text_email = email.getText().toString();
                String text_password = password.getText().toString();

                // 로그인 양식 확인
                // 빈칸을 채우지 않은 경우 체크
                // 추후 이메일 형태가 아닌 경우도 체크할 예정, 이메일은 맞는데 비밀번호가 틀린 경우, 이메일 자체가 등록 안 되어있는 경우 세분 필요
                if(TextUtils.isEmpty(text_email) || TextUtils.isEmpty(text_password)) {
                    Toast.makeText(LoginActivity.this, "빈칸을 채워주세요", Toast.LENGTH_SHORT).show();
                } else {
                    auth.signInWithEmailAndPassword(text_email, text_password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()) {
                                        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                                        String loginUserId = firebaseUser.getUid();
                                        //DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(loginUserId);
                                        //DatabaseReference checkWard = reference.child("isWard");
                                        Intent intent = null;
                                        if(firebaseUser.getDisplayName() == null){
                                            Toast.makeText(LoginActivity.this, "없는 아이디 입니다.", Toast.LENGTH_SHORT).show();
                                        }
                                        else if(firebaseUser.getDisplayName().equals("User")) {
                                            intent = new Intent(getApplicationContext(), MainActivity.class);
                                            //intent.putExtra("isWard", "false");
                                        } else  {
                                            intent = new Intent(getApplicationContext(), WardMainActivity.class);
                                            //intent.putExtra("isWard", "true");
                                        }
                                        // getCurrentUser 도입으로 삭제 가능 테스트
//                                        intent.putExtra("loginUserId", loginUserId);
//                                        intent.putExtra("loginEmail", text_email);
//                                        intent.putExtra("loginPassword", text_password);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();


                                        // 11/15 지옥의 디버깅

//                                        reference.addValueEventListener(new ValueEventListener() {
//                                            @Override
//                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                                Intent intent = null;
//                                                User user = snapshot.getValue(User.class);
//                                                if (user == null) {
//                                                    // 유저 아님 피보호자임
//                                                    //isWard[0] = true;
//                                                    isWard.add(Boolean.TRUE);
//                                                    Log.d("LoginActivity", "isWard add true, size: " + isWard.size());
////                                                    intent[0] = new Intent(getApplicationContext(), WardMainActivity.class);
////                                                    intent[0].putExtra("isWard", "true");
////                                                    Log.d("LoginActivity", "is Ward login");
//                                                } else {
//                                                    //isWard[0] = false;
//                                                    isWard.add(Boolean.FALSE);
//                                                    Log.d("LoginActivity", "isWard add false, size: " + isWard.size());
////                                                    intent[0] = new Intent(getApplicationContext(), MainActivity.class);
////                                                    intent[0].putExtra("isWard", "false");
////                                                    Log.d("LoginActivity", "is User login");
//                                                }
//
//                                                if(isWard.get(isWard.size()-1).equals(false)) {
//                                                    intent = new Intent(getApplicationContext(), MainActivity.class);
//                                                    intent.putExtra("isWard", "false");
//                                                    Log.d("LoginActivity", "is User login");
//                                                } else {
//                                                    intent = new Intent(getApplicationContext(), WardMainActivity.class);
//                                                    intent.putExtra("isWard", "true");
//                                                    Log.d("LoginActivity", "is Ward login");
//                                                }
//                                                intent.putExtra("loginUserId", loginUserId);
//                                                intent.putExtra("loginEmail", text_email);
//                                                intent.putExtra("loginPassword", text_password);
//                                                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                                //loginCnt++;
//                                                startActivity(intent);
//                                                finish();
//
//
//                                            }
//
//                                            @Override
//                                            public void onCancelled(@NonNull DatabaseError error) {
//
//                                            }
//                                        });

                                        // 11/15 10시 15분
//                                        if(isWard.get(isWard.size()).equals(false)) {
//                                            intent = new Intent(getApplicationContext(), MainActivity.class);
//                                            intent.putExtra("isWard", "false");
//                                            Log.d("LoginActivity", "is User login");
//                                        } else {
//                                            intent = new Intent(getApplicationContext(), WardMainActivity.class);
//                                            intent.putExtra("isWard", "true");
//                                            Log.d("LoginActivity", "is Ward login");
//                                        }
//                                        intent.putExtra("loginUserId", loginUserId);
//                                        intent.putExtra("loginEmail", text_email);
//                                        intent.putExtra("loginPassword", text_password);
//                                        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                        //loginCnt++;
//                                        startActivity(intent);
//                                        finish();


//                                        // 폐기
//                                        if(reference.get().isComplete()){
//                                            // 유저다
//                                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                            startActivity(intent);
//                                            finish();
//                                        }
//                                        else {
//                                            // 이용인이다.
//                                        }

                                    } else {
                                        Toast.makeText(LoginActivity.this, "인증 실패", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
    }
}