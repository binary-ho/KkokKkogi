package com.blossom.alpacapaca.kkokkkogi;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationChannelCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotificationHelper extends ContextWrapper {
    public static final String channelID = "channelId";
    public static final String  channelName = "Channel 1";

    private NotificationManager notificationManager;

    public NotificationHelper(Context base) {
        super(base);
        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.O) {
            createChannels();
        }
    }

    //@TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createChannels() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH);
            channel.enableLights(true);
            channel.enableVibration(true);
            channel.setLightColor(R.color.yellow1);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

            getManager().createNotificationChannel(channel);

        }
//        else {
//            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelID)
//                    .setSmallIcon(R.drawable.chicken_small)
//                    .setContentTitle("꼭꼭이")
//                    .setContentText("약 드세요!")
//                    .setPriority(NotificationCompat.PRIORITY_HIGH);
//        }
    }

    public NotificationManager getManager() {
        if(notificationManager == null) {
            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return  notificationManager;
    }

    // title, message 일단 수정 X 상태
    //public NotificationCompat.Builder getChannelNotification(String title, String message) {
    public NotificationCompat.Builder getChannelNotification() {
        return new NotificationCompat.Builder(getApplicationContext(), channelID)
                .setSmallIcon(R.drawable.chicken_small)
                .setContentTitle("꼭꼭이") // title
                .setContentText("약 드세요!") // message
                .setPriority(NotificationCompat.PRIORITY_HIGH);
    }
}
