package com.blossom.alpacapaca.kkokkkogi;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.blossom.alpacapaca.kkokkkogi.wardactivity.WardAlarmActivity;

public class AlarmService extends Service {
    public AlarmService() {
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate() {
        super.onCreate();
        String CHANNEL_ID = "channel_1";
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "For Alarm", NotificationManager.IMPORTANCE_LOW);
        ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(channel);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("").setContentText("").build();
        startForeground(2, notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("AlarmService", "온스타트 커맨드 호출");

        if(intent == null) {
            //Log.d("AlarmService", "실패애");
            return Service.START_STICKY;
        } else if (intent.getStringExtra("alarm").equals("true")) {
            Log.d("AlarmService", "통과1");
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if(Settings.canDrawOverlays(getApplicationContext())) {
                    Log.d("AlarmService", "약이름: " + intent.getStringExtra("name"));
                    Intent intentForAlarm = new Intent(getApplicationContext(), WardAlarmActivity.class);
                    intentForAlarm.putExtra("name", intent.getStringExtra("name"));
                    intentForAlarm.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intentForAlarm.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getApplicationContext().startActivity(intentForAlarm);
                }
            }
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}