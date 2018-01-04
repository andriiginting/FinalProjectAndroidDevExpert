package com.example.andriginting.myapplication.util;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.example.andriginting.myapplication.R;
import com.example.andriginting.myapplication.activity.MainUIActivity;

import java.util.Calendar;

/**
 * Created by Andri Ginting on 1/4/2018.
 */

public class DailyReminderMovie extends BroadcastReceiver {
    public static final String TYPE_DAILY_REMINDER = "DailyReminderMovie";
    public static final String EXTRA_MESSAGE = "message";
    public static final String EXTRA_TYPE = "type";
    private final int NOTIF_ID_REPEATING = 50;

    public DailyReminderMovie() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String type = intent.getStringExtra(EXTRA_TYPE);
        String pesan = intent.getStringExtra(EXTRA_MESSAGE);
        String judul = type.equalsIgnoreCase(TYPE_DAILY_REMINDER)? "Daily Reminder Movie ": "Not set";

        int notifyID = type.equalsIgnoreCase(TYPE_DAILY_REMINDER) ? NOTIF_ID_REPEATING : 0 ;
        showDailyNotification(context,judul,pesan,notifyID);
    }

    private void showDailyNotification(Context context, String judul, String pesan, int notifyID){
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Uri notifAlarm = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(judul)
                .setContentText(pesan)
                .setSound(notifAlarm)
                .setVibrate(new long[]{1000,1000,1000,1000,1000});

        Intent notificationIntent = new Intent(context, MainUIActivity.class);
        PendingIntent intent = PendingIntent.getActivity(context,0,notificationIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(intent);
        builder.setAutoCancel(true);
        notificationManager.notify(notifyID,builder.build());
    }

    public void showDailyReminderMovie(Context context,String type,String waktu, String pesan){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, DailyReminderMovie.class);
        intent.putExtra(EXTRA_TYPE, type);
        intent.putExtra(EXTRA_MESSAGE, pesan);

        String timeArray[] = waktu.split(":");

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]));
        calendar.set(Calendar.SECOND,0);

        int requestCode = NOTIF_ID_REPEATING;

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,requestCode, intent,0);

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pendingIntent);

        Toast.makeText(context, R.string.message_setup_daily, Toast.LENGTH_SHORT).show();
    }

    public void cancelReminder(Context context, String type){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, DailyReminderMovie.class);

        int requestCode = type.equalsIgnoreCase(TYPE_DAILY_REMINDER) ? NOTIF_ID_REPEATING : 0;

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0);
        alarmManager.cancel(pendingIntent);

    }
}
