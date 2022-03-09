package com.blossom.alpacapaca.kkokkkogi;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.blossom.alpacapaca.kkokkkogi.wardactivity.WardAlarmActivity;

public class AlertReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("WardMainActivity", "!! AlerReceiver 발동 하기는 했다");


//        NotificationHelper notificationHelper = new NotificationHelper(context);
//        NotificationCompat.Builder builder =  notificationHelper.getChannelNotification();
//        builder.setFullScreenIntent(pendingIntentForAlarm, true)
//                .setContentIntent(pendingIntentForAlarm).setStyle(new NotificationCompat.BigTextStyle().bigText("테스트"))
//                .setAutoCancel(false);
//        notificationHelper.getManager().notify(1, builder.build());

        Intent intentForAlarm = new Intent(context, WardAlarmActivity.class);
        intentForAlarm.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntentForAlarm = PendingIntent.getActivity(context, 0, intentForAlarm, 0);
        //context.startActivity(pendingIntentForAlarm);

        PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK  |
                PowerManager.ACQUIRE_CAUSES_WAKEUP |
                PowerManager.ON_AFTER_RELEASE, "My:Tag");
        wakeLock.acquire(5000);

        //context.startActivity(intentForAlarm);
    }
}
