package com.example.write_sdk;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


public class AppMessageService extends FirebaseMessagingService {
    private static final String TAG = AppMessageService.class.getSimpleName();

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        createNotification(remoteMessage);
    }

    private void createNotification(RemoteMessage remoteMessage) {
        showNotification(remoteMessage);
    }

    private void showNotification(RemoteMessage remoteMessage) {
        Uri notificationSoundURI = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder mNotificationBuilder = new NotificationCompat.Builder(this, "op-japan")
                .setContentTitle(remoteMessage.getNotification().getTitle())
                .setContentText(remoteMessage.getNotification().getBody())
                .setAutoCancel(true)
                .setSound(notificationSoundURI)
                .setStyle(new NotificationCompat.BigTextStyle())
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mNotificationBuilder.setSmallIcon(R.drawable.log_app);

            mNotificationBuilder.setLargeIcon(BitmapFactory.decodeResource(getResources(),
                    R.drawable.log_app));
        } else {
            mNotificationBuilder.setSmallIcon(R.drawable.log_app);
        }
        //mNotificationBuilder.setSmallIcon(R.mipmap.app_icon);

        //mNotificationBuilder.setDefaults(Notification.DEFAULT_ALL);

        // Send notify
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Init channel with Android 8
        if (Build.VERSION.SDK_INT >= 26) {
            if (notificationManager != null) {
                NotificationChannel channel = new NotificationChannel("op-japan", "Channel op-japan", NotificationManager.IMPORTANCE_DEFAULT);
                channel.setDescription("Channel description");
                notificationManager.createNotificationChannel(channel);
            }
        }

        if (notificationManager != null) {
            notificationManager.notify(100, mNotificationBuilder.build());
        }

    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        Log.e("token_fcm", s);
//        StorageService.Companion.getInstance().setFcToken(s);
    }
}
