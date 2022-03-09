package com.blossom.alpacapaca.kkokkkogi.useractivity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import com.blossom.alpacapaca.kkokkkogi.Model.User;
import com.blossom.alpacapaca.kkokkkogi.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SetChattingTimeActivity extends AppCompatActivity {

    DatabaseReference reference;

    Toolbar toolbar;
    String sHourString, sMinString, eHourString, eMinString, userId;
    TimePicker timePicker1, timePicker2;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_chatting_time);

        toolbar = findViewById(R.id.toolbar_set_chatting_time);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");
        reference = FirebaseDatabase.getInstance().getReference("Users").child(userId);

        timePicker1 = findViewById(R.id.time_picker1);
        timePicker2 = findViewById(R.id.time_picker2);
        button = findViewById(R.id.set_chatting_button);
        button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                sHourString = makeStr(timePicker1.getHour());
                sMinString = makeStr(timePicker1.getMinute());
                eHourString = makeStr(timePicker2.getHour());
                eMinString = makeStr(timePicker2.getMinute());

                reference.child("startHour").setValue(sHourString);
                reference.child("startMin").setValue(sMinString);
                reference.child("endHour").setValue(eHourString);
                reference.child("endMin").setValue(eMinString);

                finish();
            }
        });
    }
    public String makeStr(int num) {
        if(num < 10) {
            return "0" + String.valueOf(num);
        }
        else {
            return String.valueOf(num);
        }
    }
}