package com.blossom.alpacapaca.kkokkkogi.useractivity;

import static java.lang.Integer.parseInt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.blossom.alpacapaca.kkokkkogi.AlarmService;
import com.blossom.alpacapaca.kkokkkogi.Model.User;
import com.blossom.alpacapaca.kkokkkogi.R;
import com.blossom.alpacapaca.kkokkkogi.ResetService;
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

import java.util.Calendar;
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

        // ?????? ???????????? ???????????? ?????????
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
                //Log.d("MainActivityTest", "????????? ?????????: " + loginEmail);
                //Log.d("MainActivityTest", "????????? ????????????: " + loginPassword);
                //Log.d("MainActivityTest", "????????? ?????? ??????: " + loginUserName);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        isWard = "false";
        if(loginUserName == null) {
            loginUserName = "??????";
        }


        //Toast.makeText(MainActivity.this, loginUserId+ "??? ?????????", Toast.LENGTH_SHORT).show();
        //Toast.makeText(MainActivity.this, "CurrentUser = \n" + FirebaseAuth.getInstance().getCurrentUser().getUid(), Toast.LENGTH_SHORT).show();

        // ?????? ???????????? ?????? ??????????????? ??????????????????. onCreate ?????? ???????????? ?????? ??????

        // ????????? ?????????, ?????? ??????
        //profile_image = findViewById(R.id.profile_images);
        //username = findViewById(R.id.nameForWard);

        // ????????? ??????, ?????? ??????
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        // ?????? ?????? ??????, ?????????????????? ???????????? ????????????
        // ??? ????????? ?????? ????????? ???????????? ??? ???. ???????????? ?????????.
//        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//        userId = firebaseUser.getUid();
//        reference = FirebaseDatabase.getInstance().getReference("Users").child(userId);

        // ????????? ????????? ??????
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
//                    // ????????? ???????????? ??? ???????????? ?????????????????? ??????????????????
//                    Glide.with(MainActivity.this).load(user.getImageURL()).into(profile_image);
//                }
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {}
//        });

        // ?????? ?????? ????????????????????? ??????????????? ??? ??????
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



        // ??? ?????????
//        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
//        viewPager = findViewById(R.id.view_pager);
//        pagerAdapter = new ScreenSlidePagerAdapter(this);
//        viewPager.setAdapter(pagerAdapter);

//        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
//        viewPagerAdapter.addFragment(new MainFragment(), "?????????");
//        viewPagerAdapter.addFragment(new WardManagementFragment(), "??????");
//        viewPagerAdapter.addFragment(new SettingFragment(), "??????");
//        viewPager2.setAdapter(viewPagerAdapter);
//        bottomNavigationView.view


    }

    // ???????????? ???????????? ????????? ???
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

    // ?????? onCreate
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    // ???????????? ??????
    // ?????? ??????
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                //Toast.makeText(MainActivity.this, FirebaseAuth.getInstance().getUid() + " sign out!", Toast.LENGTH_SHORT).show();
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

    // ???????????? ?????????
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

    // ??????????????? ???????????? ????????? ?????????
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