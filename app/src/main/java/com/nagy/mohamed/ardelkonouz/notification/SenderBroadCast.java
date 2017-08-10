package com.nagy.mohamed.ardelkonouz.notification;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.nagy.mohamed.ardelkonouz.R;

import java.util.Calendar;

/**
 * Created by mohamednagy on 8/9/2017.
 */

public class SenderBroadCast {
    private static final int HOURS = 7;
    private static final int MINUTES = 0;
    private static final int SECONDS = 0;

    public void active(Context context){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR, HOURS);
        calendar.set(Calendar.MINUTE, MINUTES);
        calendar.set(Calendar.SECOND, SECONDS);
        calendar.set(Calendar.AM_PM, Calendar.AM);

        Intent receiverIntent = new Intent(context, ReceiverBroadCast.class);
        receiverIntent.setAction(context.getString(R.string.receiver));

        PendingIntent pendingIntent =
                PendingIntent.getBroadcast(context, 0, receiverIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY,
                pendingIntent
        );
    }

}
