package com.example.chesserschedulingapp.UI;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.core.app.NotificationBuilderWithBuilderAccessor;
import androidx.core.app.NotificationCompat;

import com.example.chesserschedulingapp.R;

import static android.content.Context.NOTIFICATION_SERVICE;

public class TACReceiver extends BroadcastReceiver {
//Check this out!
    static int alertId;
    String channel = "test";
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,intent.getStringExtra("message"),Toast.LENGTH_LONG).show();
        alertChannel(context,channel);
        Notification notification = new NotificationCompat.Builder(context, channel)
                .setSmallIcon(R.drawable.ic_baseline_alarm_24)
                .setContentText(intent.getStringExtra("message"))
                .setContentTitle("TAC Alert!").build();
        NotificationManager notificationManager=(NotificationManager)context.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(alertId++, notification);
    }
    private void alertChannel(Context context, String channel) {
        CharSequence channelName = "TAC Channel";
        String description = "TAC Notification Channel";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel myChannel = new NotificationChannel(channel, channelName, importance);
        myChannel.setDescription(description);
        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(myChannel);
    }
}
