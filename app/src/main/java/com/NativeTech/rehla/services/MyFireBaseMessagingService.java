package com.NativeTech.rehla.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.NativeTech.rehla.activities.Spalsh;
import com.NativeTech.rehla.model.DataManager;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;
import java.util.Objects;
import java.util.Random;
import com.NativeTech.rehla.R;
import com.NativeTech.rehla.Utills.Constant;
import com.NativeTech.rehla.activities.Home;
import me.leolin.shortcutbadger.ShortcutBadger;


public class MyFireBaseMessagingService extends FirebaseMessagingService {
    private SharedPreferences mSharedPreferences;
    public String token = "";
    private String id = "";
    private int badgeCount ;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        mSharedPreferences = getSharedPreferences("tokenDetail", MODE_PRIVATE);
        id = DataManager.getInstance().getCashedAccessToken().getAccess_token();
        if (mSharedPreferences.getString(Constant.badgeCount, "").equals(""))
        {
            badgeCount=0;
            SharedPreferences.Editor editor_newToken = mSharedPreferences.edit();
            editor_newToken.putString(Constant.badgeCount, "0");
            editor_newToken.apply();
        }
        else
        {
            if (id.equals(""))
            {
                badgeCount=0;
                SharedPreferences.Editor editor_newToken = mSharedPreferences.edit();
                editor_newToken.putString(Constant.badgeCount, "0");
                editor_newToken.apply();
            }
            else
            {
                badgeCount = Integer.parseInt(mSharedPreferences.getString(Constant.badgeCount, ""));
            }
        }
        if (!id.equals("")) {
            if (remoteMessage.getData().isEmpty())
                showNotification(Objects.requireNonNull(remoteMessage.getNotification()).getTitle(), remoteMessage.getNotification().getBody());
            else
                showNotification(remoteMessage.getData());
        }


    }

    private void showNotification(Map<String, String> data) {
        String title = "";
        String body = Objects.requireNonNull(data.get("message"));
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICATION_CHANEL_ID = "com.NativeTech.rehla";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANEL_ID, "notification", NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription("approval");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(R.color.colorPrimary);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }
        Intent intent = new Intent(this, Spalsh.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANEL_ID);
        notificationBuilder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_logo_notification)
                .setContentTitle(title)
                .setContentText(body)
                .setVibrate(new long[]{0, 1000, 500, 1000})
                .setColor(getResources().getColor(R.color.colorPrimary))
                .setContentIntent(pendingIntent);
        if (notificationManager != null) {
            notificationManager.notify(new Random().nextInt(), notificationBuilder.build());
        }
        badgeCount++;
        ShortcutBadger.applyCount(getApplicationContext(), badgeCount); //for 1.1.4+
        //ShortcutBadger.with(getApplicationContext()).count(badgeCount); //for 1.1.3
        SharedPreferences.Editor editor_newToken = mSharedPreferences.edit();
        editor_newToken.putString(Constant.badgeCount, String.valueOf(badgeCount));
        editor_newToken.apply();
    }


    private void showNotification(String title, String body) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICATION_CHANEL_ID = "com.NativeTech.rehla";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANEL_ID, "notification", NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription("approval");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(R.color.colorPrimary);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }
        Intent intent = new Intent(this, Spalsh.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANEL_ID);
        notificationBuilder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_logo_notification)
                .setColor(getResources().getColor(R.color.colorPrimary))
                .setContentTitle(title)
                .setVibrate(new long[]{0, 1000, 500, 1000})
                .setContentText(body)
                .setContentIntent(pendingIntent);
        if (notificationManager != null) {
            notificationManager.notify(new Random().nextInt(), notificationBuilder.build());
        }

        badgeCount++;
        ShortcutBadger.applyCount(getApplicationContext(), badgeCount); //for 1.1.4+

        SharedPreferences.Editor editor_newToken = mSharedPreferences.edit();
        editor_newToken.putString(Constant.badgeCount, String.valueOf(badgeCount));
        editor_newToken.apply();
    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.d("on new token", s);
        mSharedPreferences = getSharedPreferences("tokenDetail", MODE_PRIVATE);
        SharedPreferences.Editor editor_newToken = mSharedPreferences.edit();
        editor_newToken.putString(Constant.devicetoken, s);
        editor_newToken.apply();
    }
}