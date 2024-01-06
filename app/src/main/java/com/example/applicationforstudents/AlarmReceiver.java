package com.example.applicationforstudents;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.preference.PreferenceManager;

import com.example.applicationforstudents.Activity.MainActivity;

import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Intent i = new Intent(context, MainActivity.class);
        PendingIntent pendingIntentOpen = PendingIntent.getActivity(context,0,i,PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(context);
        boolean b = prefs.getBoolean("notification", false);
        Log.d("MyLog","notification === " + b);

        if(b) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "notify_001");

            builder.setSmallIcon(R.drawable.ic_dropdownicon);
            builder.setContentTitle("Через 10 минут начнется пара по предмету " + intent.getStringExtra("Name"));
        /*builder.setContentText("В " + ((Calendar) intent.getSerializableExtra("timeToStart")).getTime().getHours() + ":" +
                ((Calendar) intent.getSerializableExtra("timeToStart")).getTime().getMinutes() + " начнется пара по предмету " +
                intent.getStringExtra("Name"));*/
            builder.setContentIntent(pendingIntentOpen);

            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
            notificationManagerCompat.notify(200, builder.build());
        }

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, intent.getIntExtra("timeId",0), intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);

        Log.d("MyLog", "END Receiver");
    }
}
