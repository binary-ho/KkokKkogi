package com.blossom.alpacapaca.kkokkkogi.wardactivity;

import static java.lang.Integer.parseInt;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blossom.alpacapaca.kkokkkogi.AlarmService;
import com.blossom.alpacapaca.kkokkkogi.AlertReceiver;
import com.blossom.alpacapaca.kkokkkogi.Model.MedicineBox;
import com.blossom.alpacapaca.kkokkkogi.Model.User;
import com.blossom.alpacapaca.kkokkkogi.Model.Ward;
import com.blossom.alpacapaca.kkokkkogi.NotificationHelper;
import com.blossom.alpacapaca.kkokkkogi.ResetService;
import com.blossom.alpacapaca.kkokkkogi.adapter.MedicineBoxAdapter;
import com.blossom.alpacapaca.kkokkkogi.adapter.WardAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.blossom.alpacapaca.kkokkkogi.ChatActivity;
import com.blossom.alpacapaca.kkokkkogi.R;
import com.blossom.alpacapaca.kkokkkogi.StartActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class WardMainActivity extends AppCompatActivity {

    Intent intent;
    int sHour, sMin, eHour, eMin;

    FirebaseUser loginWard;
    DatabaseReference referenceWard;
    DatabaseReference referenceUser;
    Ward ward;

    private static String loginWardId;
    private static String parentId;
    private static String isWard;
    private static String wardName;

    FloatingActionButton chatButton;
    TextView textView1;

    // 알림
    NotificationHelper notificationHelper;
    Button notificationButton;
    private AlarmManager alarmManager;
    //private PendingIntent alarmPendingIntent;

    // 약
    private RecyclerView recyclerView;
    private MedicineBoxAdapter medicineBoxAdapter;
    private ArrayList<MedicineBox> boxs;
    private DatabaseReference boxReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ward_main);

        Log.d("WardMainActivity", "onCreate()!");


        // 방금 로그인한 아이디가 누구니
        loginWard = FirebaseAuth.getInstance().getCurrentUser();
        loginWardId = loginWard.getUid();
        parentId = loginWard.getDisplayName();
        isWard = "true";
        //TODO: 단순히 시간을 가져오기 위해서라면, 더 깊이 파도 될거 같은데
        referenceUser = FirebaseDatabase.getInstance().getReference("Users").child(parentId);
        referenceWard = FirebaseDatabase.getInstance().getReference("Users").child(parentId).child("Wards").child(loginWardId);
        // TODO: ward를 불러오지 않고 하나 파보자
        //readWard(referenceWard);
        ward = new Ward();

//        intent = getIntent();
//        String id_temp = intent.getStringExtra("loginUserId");
//        if(id_temp != null) {
//            loginWardId = id_temp;
//        }
//        String isWard_temp = intent.getStringExtra("isWard");
//        if(isWard_temp != null) {
//            isWard = isWard_temp;
//        }
        //Toast.makeText(WardMainActivity.this, loginWardId + "로 로그인", Toast.LENGTH_SHORT).show();

        // 액션바 할당, 타이틀 지우기
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        // 리사이클러 뷰
        recyclerView = findViewById(R.id.ward_main_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        boxs = new ArrayList<>();

        // TODO: 알림 삭제
//        notificationHelper = new NotificationHelper(this);
//        notificationButton = findViewById(R.id.notification_button);
//        notificationButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ringRing();
//            }
//        });

        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        // Intent intent = new Intent(this, AlarmReceiver.class);
        // alarmIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

        // 방법 교체

//        reference = FirebaseDatabase.getInstance().getReference("Wards").child(loginWardId);
//        Log.d("WardMain", "reference: " + reference.toString());
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                parentId = snapshot.getValue(String.class);
//                Log.d("WardMain", "Load parentId!");
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

        // TODO: 이름 표기 삭제
//        textView1 = findViewById(R.id.ward_main_text1);
//        // 여기서 한번 호출 밖에서도 한번 호출
//        referenceWard.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                ward = snapshot.getValue(Ward.class);
//                wardName = ward.getNameForWard();
//                Log.d("WardMainActivity", "onCreate의 레퍼런스 호출");
//                textView1.setText(wardName + "님 안녕하세요!");
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

        // 알람을 등록해보자
        referenceWard.child("Medicines").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (ward.getMedicineArray().size() == 0) {
                    Log.d("WardMainActivity", "readAlarm 실패!");
                    //return;
                }
                Log.d("WardMainActivity", "readAlarm의 레퍼런스 호출");
                ward.resetMedicineArray();
                boxs.clear();
                int num = 0;
                for (DataSnapshot key : snapshot.getChildren()) {
                    Log.d("WardMainActivity", "key 탐색");
                    // calendar.set(Calendar.HOUR_OF_DAY, 14);
                    String keyString = key.getKey();
                    String hour;
                    String min;
                    String medicines = "";
                    String hourMin;

                    if (keyString.length() == 3) {
                        hour = "0" + keyString.charAt(0);
                        min = keyString.substring(1, 3);
                    } else {
                        hour = keyString.substring(0, 2);
                        min = keyString.substring(2, 4);
                    }
                    hourMin = hour + ":" + min + " : ";

                    for (DataSnapshot value : key.getChildren()) {
                        // multimap.put((String) key.key(), (String) value.getValue());
                        //hourMin += value.getValue() + ", ";
                        medicines += value.getValue(String.class) + ", ";
                        Log.d("WardMainActivity", "box 추가" + value.getValue(String.class) );
                    }
                    medicines = medicines.substring(0, medicines.length() - 2);
                    hourMin += medicines;

                    ward.addMedicine(hourMin, hour, min, parentId, loginWardId);
                    MedicineBox box = new MedicineBox(medicines, hour, min);
                    boxs.add(box);
                    Log.d("WardMainActivity", hourMin + " 알람 등록 -> " + medicines);

                    // 리사이클러 뷰
                    medicineBoxAdapter = new MedicineBoxAdapter(getApplicationContext(), boxs);
                    recyclerView.setAdapter(medicineBoxAdapter);

                    // 알람 등록
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(System.currentTimeMillis());
                    calendar.set(Calendar.HOUR_OF_DAY, parseInt(hour));
                    calendar.set(Calendar.MINUTE, parseInt(min));

                    // TODO: 뜯어 고쳐!
                    // 1. 브로드캐스트
                    // Intent alarmIntent = new Intent(getBaseContext(), AlertReceiver.class);
                    // alarmIntent.putExtra("Medicines", medicines);
                    // alarmPendingIntent = PendingIntent.getBroadcast(getApplicationContext(), num, alarmIntent, PendingIntent.FLAG_MUTABLE);

                    // 2. 액티비티
                    //Intent alarmIntentForGetActivity = new Intent(getBaseContext(), WardAlarmActivity.class);
                    //alarmPendingIntent = PendingIntent.getActivity(getApplicationContext(), num, alarmIntentForGetActivity, PendingIntent.FLAG_UPDATE_CURRENT|PendingIntent.FLAG_MUTABLE);

                    // 3. 서비스
                    Intent alarmIntentForGetService = new Intent(getApplicationContext(), AlarmService.class);
                    alarmIntentForGetService.putExtra("alarm", "true");
                    alarmIntentForGetService.putExtra("name", medicines);
                    PendingIntent alarmPendingIntent = PendingIntent.getService(getApplicationContext(), num, alarmIntentForGetService, PendingIntent.FLAG_MUTABLE);
                    num++;

                    // 시간 지난 알람은 울리지 않기로 해요.
                    long timeUntilTrigger;
                    if (System.currentTimeMillis() >= calendar.getTimeInMillis()){
                        timeUntilTrigger = calendar.getTimeInMillis() + 86400000;
                    }else{
                        timeUntilTrigger = calendar.getTimeInMillis();
                    }
                    alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, timeUntilTrigger, AlarmManager.INTERVAL_DAY, alarmPendingIntent);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


//        if(wardName == null) {
//            wardName = "회원";
//            textView1.setText(wardName + "님 안녕하세요!");
//        }

        // 연락가능시간 불러오기
        chatButton = findViewById(R.id.chat_button);
        referenceUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                sHour = parseInt(user.getStartHour());
                sMin = parseInt(user.getStartMin());
                eHour = parseInt(user.getEndHour());
                eMin = parseInt(user.getEndMin());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 시간이 없어서 일단 이런 식으로 ㅠㅠ
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                calendar.set(Calendar.HOUR_OF_DAY, sHour);
                calendar.set(Calendar.MINUTE, sMin);

                Calendar calendar2 = Calendar.getInstance();
                calendar2.setTimeInMillis(System.currentTimeMillis());
                calendar2.set(Calendar.HOUR_OF_DAY, eHour);
                calendar2.set(Calendar.MINUTE, eMin);

                if (System.currentTimeMillis() < calendar.getTimeInMillis() || System.currentTimeMillis() > calendar2.getTimeInMillis()){
                    Toast.makeText(getApplicationContext(), "연락가능 시간이 아닙니다.", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(WardMainActivity.this , ChatActivity.class);
                    intent.putExtra("wardId", loginWardId);
                    intent.putExtra("userId", parentId);
                    intent.putExtra("isWard", "true");
                    startActivity(intent);
                }
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

        // TODO: 권한 설정창 호출
//        ToggleButton toggleButton = findViewById(R.id.toggle_draw);
//        toggleButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
//                    startActivityForResult(intent, OVERLAY_PERMISSION_REQ_CODE);
//                }
//            }
//        });

         //서비스 시작
        Intent intentForService = new Intent(WardMainActivity.this, AlarmService.class);
        intentForService.putExtra("alarm", "false");
        //intentForService.setPackage("com.blossom.alpacapaca.kkokkkogi");
        //startService(intentForService);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            this.startForegroundService(intentForService);
        } else {
            this.startService(intentForService);
        }

//        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTimeInMillis(System.currentTimeMillis());
//        calendar.set(Calendar.HOUR_OF_DAY, 2);
//        calendar.set(Calendar.MINUTE, 0);
//
//        Intent alarmIntentForGetService = new Intent(getApplicationContext(), ResetService.class);
//        PendingIntent alarmPendingIntent = PendingIntent.getService(getApplicationContext(), 0, alarmIntentForGetService, PendingIntent.FLAG_IMMUTABLE);
//        alarmManager.setInexactRepeating(AlarmManager.RTC, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, alarmPendingIntent);
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
                //.makeText(WardMainActivity.this, FirebaseAuth.getInstance().getUid() + " sign out!", Toast.LENGTH_SHORT).show();
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
        // 개선된 parent 찾기 덕분에 아래와 같은 일은 이제 없을거 같아
        //        if(parentId == null) {
//            Log.d("WardMainActivity", "Pass online()!");
//            return; }

        // why Object?
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("online", online);
        if(online == true) {
            hashMap.put("lastOnline", "활동중");
        } else {
            SimpleDateFormat timeFormat = new SimpleDateFormat("yy.mm.dd hh:mm");
            String timeStr = timeFormat.format(new Date(System.currentTimeMillis()));
            hashMap.put("lastOnline", timeStr);
        }
        referenceWard.updateChildren(hashMap);
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
        //referenceWard = FirebaseDatabase.getInstance().getReference("Users").child(parentId).child("Wards").child(loginWardId);
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


    public void ringRing() {    // 아하
        NotificationCompat.Builder builder = notificationHelper.getChannelNotification();
        notificationHelper.getManager().notify(1, builder.build());
    }


    // ㅘㅇ정 째조헤
//    @Override
//    public void onTimeSet(TimePicker timePicker, int i, int ii) {
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.HOUR_OF_DAY, i);
//        calendar.set(Calendar.MINUTE, ii);
//        calendar.set(Calendar.SECOND, 0);
//
//        //updateTimeText(calendar);   // for 디버그
//        startAlarm(calendar);
//    }

//    private void updateTimeText(Calendar calendar) {
//        String timeText = "Alarm set for: ";
//        timeText += DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar.getTime());
//
//        Log.d("Alarm Setting", timeText);
//    }

    private void startAlarm (Calendar calendar) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    private void cancelAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        alarmManager.cancel(pendingIntent);
    }


    public void readWard(DatabaseReference reference) {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ward = snapshot.getValue(Ward.class);
                Log.d("WardMainActivity", "Ward 로드 성공");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}