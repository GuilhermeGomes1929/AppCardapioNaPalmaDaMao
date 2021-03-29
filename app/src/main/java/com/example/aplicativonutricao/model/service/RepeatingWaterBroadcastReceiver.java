package com.example.aplicativonutricao.model.service;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;

import com.example.aplicativonutricao.R;
import com.example.aplicativonutricao.view.WaterReminder;

import java.util.Calendar;

public class RepeatingWaterBroadcastReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, new Intent(context, WaterReminder.class), 0);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(context.getString(R.string.notification_channel_id),
                    context.getString(R.string.notification_channel_name), NotificationManager.IMPORTANCE_DEFAULT);
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build();
            channel.setSound(Uri.parse("android.resource://"+context.getPackageName()+"/"+R.raw.water_drop_notification),audioAttributes);
            channel.setVibrationPattern(new long[]{300,300,300,300,300});
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, context.getString(R.string.notification_channel_id));
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);
        builder.setSmallIcon(R.drawable.water);
        builder.setContentTitle("Hora de beber Água.");
        builder.setContentText("Hidrate-se agora mesmo! :)");
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setVibrate(new long[]{300,300,300,300,300});
        builder.setSound(Uri.parse("android.resource://"+context.getPackageName()+"/"+R.raw.water_drop_notification));
        builder.addAction(R.drawable.cup,"Beber agora",
                PendingIntent.getBroadcast(context, 0, new Intent(context, DrinkBroadcastReceiver.class),0));

        notificationManager.notify(22062003, builder.build());



        PendingIntent oldPendingIntent = PendingIntent.getBroadcast(context, 1, intent , 0);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.SECOND, 0);
        AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarm.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() + 24*60*60*1000, oldPendingIntent);
        }


    }
}
