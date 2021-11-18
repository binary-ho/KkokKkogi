package com.blossom.alpacapaca.kkokkkogi.useractivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.blossom.alpacapaca.kkokkkogi.Model.Chat;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.blossom.alpacapaca.kkokkkogi.Model.Ward;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.blossom.alpacapaca.kkokkkogi.R;

import java.util.ArrayList;
import java.util.HashMap;

public class AddWardActivity extends AppCompatActivity {

    MainActivity parentInfo;
    Intent intent;

    MaterialEditText name_for_ward, name_for_me, born, email, password, password_confirm;
    Button button;

    FirebaseAuth authForWard;

    FirebaseUser parent;
    String parentId;
    String loginEmail;
    String loginPassword;
    DatabaseReference referenceParent;
    DatabaseReference referenceWard;
    DatabaseReference referenceBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ward);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("친구 등록");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        name_for_ward = findViewById(R.id.name_for_ward);
        name_for_me = findViewById(R.id.name_for_me);
        born = findViewById(R.id.born);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password1);
        password_confirm = findViewById(R.id.password2);

        intent = getIntent();
        parentId = intent.getStringExtra("loginUserId");
        loginEmail = intent.getStringExtra("loginEmail");
        loginPassword = intent.getStringExtra("loginPassword");
        Toast.makeText(AddWardActivity.this, parentId + "로 로그인", Toast.LENGTH_SHORT).show();

        //parent = FirebaseAuth.getInstance().getCurrentUser();
//        parentInfo = new MainActivity();
////        parent = parentInfo.getUser();
//        parentId = parentInfo.getUserId();
//        Log.d("AddWard", parentId);
        referenceParent = FirebaseDatabase.getInstance().getReference("Users").child(parentId);
        referenceBase = FirebaseDatabase.getInstance().getReference("Wards");
        authForWard = FirebaseAuth.getInstance();

        button = findViewById(R.id.button_register);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text_name_for_ward = name_for_ward.getText().toString();
                String text_name_for_me = name_for_me.getText().toString();
                String text_born = born.getText().toString();
                String text_email = email.getText().toString();
                String text_password = password.getText().toString();
                String text_password_confirm = password_confirm.getText().toString();

                if(TextUtils.isEmpty(text_name_for_ward) || TextUtils.isEmpty(text_name_for_me) || TextUtils.isEmpty(text_born)
                ||TextUtils.isEmpty(text_email)||TextUtils.isEmpty(text_password)||TextUtils.isEmpty(text_password_confirm)) {
                    Toast.makeText(AddWardActivity.this , "모든 칸을 채워주세요.", Toast.LENGTH_SHORT).show();
                } else if(!text_password_confirm.equals(text_password)) {
                    Toast.makeText(AddWardActivity.this , "비밀번호와 비밀번호 확인이 서로 다릅니다.", Toast.LENGTH_SHORT).show();
                } else {
                    wardRegister(text_email, text_password, parentId, text_name_for_ward, text_name_for_me, text_born);
                }
            }
        });
    }

    private void wardRegister( String email, String password, String parentId, String nameForWard, String nameForMe, String born) {
        authForWard.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            FirebaseUser FirebaseWard = authForWard.getCurrentUser();
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName("Ward")
                                    .build();
                            FirebaseWard.updateProfile(profileUpdates);

                            String wardId = FirebaseWard.getUid();
                            Toast.makeText(AddWardActivity.this, wardId + "로 만들어짐!", Toast.LENGTH_SHORT).show();
                            Log.d("AddWardActivity", wardId + "로 ward 생성");
                            //referenceWard = referenceParent.getDatabase().getReference("Ward").child(wardId);

                            referenceWard = referenceParent.child("Wards").child(wardId);

                            //ArrayList<Chat> chats = new ArrayList<>();
                            HashMap<String, Chat> chats = new HashMap<String, Chat>();
                            Ward ward = new Ward(wardId, email, password, parentId, nameForWard, nameForMe, born, chats);
                            referenceBase = referenceBase.child(wardId);
                            referenceBase.setValue(parentId);
                            //reference.setValue(hashmap).addOnCompleteListener(new OnCompleteListener<Void>()
                            referenceWard.setValue(ward).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()) {
                                        //plusNumWard();
                                        Intent intent = new Intent(AddWardActivity.this, MainActivity.class);
                                        // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        // startActivity(intent);
                                        Log.d("AddWardActivity", authForWard.getUid() + " 로그아웃");
                                        authForWard.signOut();
                                        authForWard.signInWithEmailAndPassword(loginEmail, loginPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                if(task.isSuccessful()) {
                                                    Log.d("AddWardActivity", authForWard.getUid() + "로 재로그인");
                                                } else {
                                                    Toast.makeText(AddWardActivity.this, "경고: 재로그인 실패", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                        finish();
                                    }
                                    else {
                                        Toast.makeText(AddWardActivity.this, "실패", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                            // Ward가 부모 아이디를 기억하게 하기

                        } else {
                            Toast.makeText(AddWardActivity.this, "해당 이메일과 비밀번호로는 가입할 수 없습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}