package com.blossom.alpacapaca.kkokkkogi.wardactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.blossom.alpacapaca.kkokkkogi.R;
import com.blossom.alpacapaca.kkokkkogi.StartActivity;
import com.bumptech.glide.Glide;

public class WardAlarmActivity extends AppCompatActivity {

    //MediaPlayer player;
    Button button;
    Ringtone ringtone;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ward_alarm);

        //textView = findViewById(R.id.alarm_text);
        //textView.setText(getIntent().getStringExtra("name"));

        ImageView gif = (ImageView) findViewById(R.id.alarm_image);
        Glide.with(this).load(R.raw.chickengif).into(gif);

        Log.d("WardMainActivity", "ring ring ah ha");
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        ringtone = RingtoneManager.getRingtone(getApplicationContext(), uri);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            ringtone.setLooping(true);
            ringtone.play();
        }


        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if(Build.VERSION.SDK_INT >= 26) {
            vibrator.vibrate(VibrationEffect.createOneShot(5000, 100));
        } else {
            vibrator.vibrate(1000);
        }

//        player = MediaPlayer.create(getApplicationContext(), R.raw.beep);
//        player.setLooping(true);
//        player.start();

        button = findViewById(R.id.off_alarm);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), StartActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                if(ringtone.isPlaying()) {
                    ringtone.stop();
                }
                finish();
            }
        });
    }
}