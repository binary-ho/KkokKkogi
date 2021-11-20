package com.blossom.alpacapaca.kkokkkogi.useractivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.blossom.alpacapaca.kkokkkogi.Model.User;
import com.blossom.alpacapaca.kkokkkogi.R;
import com.blossom.alpacapaca.kkokkkogi.StartActivity;
import com.blossom.alpacapaca.kkokkkogi.fragments.ChatPreviewFragment;
import com.blossom.alpacapaca.kkokkkogi.fragments.ManagementWardFragment;
import com.blossom.alpacapaca.kkokkkogi.fragments.SettingFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
    private static final int NUM_PAGES = 3;

    Intent intent;

    ChatPreviewFragment chatPreviewFragment;
    ManagementWardFragment managementWardFragment;
    SettingFragment settingFragment;

    //private ViewPager2 viewPager;
    //private FragmentStateAdapter pagerAdapter;

    CircleImageView profile_image;
    TextView textView1MainFragment;

    private static FirebaseUser loginUser;
    private static String loginUserId;
    private static String isWard;
    private static String loginEmail;
    private static String loginPassword;
    private static String loginUserName;
    private DatabaseReference reference;

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 방금 로그인한 아이디가 누구니
//        intent = getIntent();
//        String id_temp = intent.getStringExtra("loginUserId");
//        if(id_temp != null) {
//            loginUserId = id_temp;
//        }
//        String isWard_temp = intent.getStringExtra("isWard");
//        if(isWard_temp != null) {
//            isWard = isWard_temp;
//        }
//
//        loginEmail = intent.getStringExtra("loginEmail");
//        loginPassword = intent.getStringExtra("loginPassword");


        loginUser = FirebaseAuth.getInstance().getCurrentUser();
        loginUserId = loginUser.getUid();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(loginUserId);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user = snapshot.getValue(User.class);
                loginEmail = user.getLoginEmail();
                loginPassword = user.getLoginPassword();
                loginUserName = user.getUsername();
                Log.d("MainActivityTest", "로그인 이메일: " + loginEmail);
                Log.d("MainActivityTest", "로그인 패스워드: " + loginPassword);
                Log.d("MainActivityTest", "로그인 유저 이름: " + loginUserName);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        isWard = "false";
        if(loginUserName == null) {
            loginUserName = "회원";
        }


        Toast.makeText(MainActivity.this, loginUserId+ "로 로그인", Toast.LENGTH_SHORT).show();
        Toast.makeText(MainActivity.this, "CurrentUser = \n" + FirebaseAuth.getInstance().getCurrentUser().getUid(), Toast.LENGTH_SHORT).show();

        // 계정 만들어준 이후 오류뜨는지 확인해봐야함. onCreate 할때 해주는게 맞나 고민

        // 프로필 이미지, 이름 할당
        //profile_image = findViewById(R.id.profile_images);
        //username = findViewById(R.id.nameForWard);

        // 액션바 할당, 제목 없앰
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        // 파베 유저 정보, 데이터베이스 레퍼런스 가져오기
        // 이 방법은 아직 제대로 이해하지 못 함. 문제점이 발생함.
//        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//        userId = firebaseUser.getUid();
//        reference = FirebaseDatabase.getInstance().getReference("Users").child(userId);

        // 프로필 이미지 설정
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                User user = snapshot.getValue(User.class);
//
//                username.setText(user.getUsername());
//                if(user.getImageURL().equals("default")) {
//                    //profile_image.setImageResource(R.mipmap.ic_launcher);
//                    //profile_image.setImageResource(R.drawable.common_google_signin_btn_icon_light_normal);
//                } else {
//                    // 이미지 기본으로 안 해놨으면 설정해둔거로 바꿔주나보네
//                    Glide.with(MainActivity.this).load(user.getImageURL()).into(profile_image);
//                }
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {}
//        });

        // 단순 바텀 네비게이션이랑 프레그먼트 뷰 조합
        managementWardFragment = new ManagementWardFragment();
        chatPreviewFragment = new ChatPreviewFragment();
        settingFragment = new SettingFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.container, managementWardFragment).commit();

        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.wardManagementTab:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, managementWardFragment).commit();
                        return true;
                    case R.id.chatTab:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, chatPreviewFragment).commit();
                        return true;
                    case R.id.settingTab:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, settingFragment).commit();
                        return true;
                }
                return true;
            }
        });



        // 뷰 페이저
//        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
//        viewPager = findViewById(R.id.view_pager);
//        pagerAdapter = new ScreenSlidePagerAdapter(this);
//        viewPager.setAdapter(pagerAdapter);

//        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
//        viewPagerAdapter.addFragment(new MainFragment(), "꼭꼭이");
//        viewPagerAdapter.addFragment(new WardManagementFragment(), "친구");
//        viewPagerAdapter.addFragment(new SettingFragment(), "설정");
//        viewPager2.setAdapter(viewPagerAdapter);
//        bottomNavigationView.view
    }

    // 뷰페이저 뒤로가기 눌렸을 때
//    @Override
//    public void onBackPressed() {
//        if (viewPager.getCurrentItem() == 0) {
//            // If the user is currently looking at the first step, allow the system to handle the
//            // Back button. This calls finish() on this activity and pops the back stack.
//            super.onBackPressed();
//        } else {
//            // Otherwise, select the previous step.
//            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
//        }
//    }

    // 메뉴 onCreate
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    // 옵션메뉴 버튼
    // 일단 배치
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                Toast.makeText(MainActivity.this, FirebaseAuth.getInstance().getUid() + " sign out!", Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut();
                Intent logoutIntent = new Intent(MainActivity.this, StartActivity.class);
                logoutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                //logoutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // ??
                startActivity(logoutIntent);
                finish();
                return true;
        }
        return false;
    }

    // 뷰페이저 어댑터
//    class ViewPagerAdapter extends FragmentPagerAdapter {
//        private ArrayList<Fragment> fragments;
//        private ArrayList<String> titles;
//
//        ViewPagerAdapter(FragmentManager fm) {
//            super(fm);
//            this.fragments = new ArrayList<>();
//            this.titles = new ArrayList<>();
//        }
//
//        @NonNull
//        @Override
//        public Fragment getItem(int position) {
//            return fragments.get(position);
//        }
//
//        @Override
//        public int getCount() {
//            return fragments.size();
//        }
//
//        public void addFragment(Fragment fragment, String title) {
//            fragments.add(fragment);
//            titles.add(title);
//        }
//
//        @Nullable
//        @Override
//        public CharSequence getPageTitle(int position) {
//            return super.getPageTitle(position);
//        }
//    }

    // 뷰페이저용 슬라이드 어뎁터 클래스
//    private class ScreenSlidePagerAdapter extends FragmentStateAdapter {
//        public ScreenSlidePagerAdapter(FragmentActivity fa) {
//            super(fa);
//        }
//
//        @NonNull
//        @Override
//        public Fragment createFragment(int position) {
//            return new ScreenSlidePageFragment();
//        }
//
//        @Override
//        public int getItemCount() {
//            return NUM_PAGES;
//        }
//    }



    public static FirebaseUser getUser() {
        return loginUser;
    }

    public static String getLoginUserId() {
        return loginUserId;
    }
    public static String getIsWard() {
        return isWard;
    }

    public static String getLoginEmail() { return loginEmail;}
    public static String getLoginPassword() {
        return loginPassword;
    }
    public static String getLoginUserName() {
        return loginUserName;
    }

//    public String getUserId() {
//        return this.userId;
//    }

//    public DatabaseReference getReference() {
//        assert reference != null;
//        return this.reference;
//    }

}