package com.nagy.mohamed.ardelkonouz.notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.nagy.mohamed.ardelkonouz.ui.mainScreen.MainActivity;
import com.nagy.mohamed.ardelkonouz.R;
import com.nagy.mohamed.ardelkonouz.helper.Constants;
import com.nagy.mohamed.ardelkonouz.ui.ProfileScreens.SectionProfileActivity;

/**
 * Created by mohamednagy on 8/9/2017.
 */

public class CourseNotification {

    public void create(String courseName, String sectionName, String instructorName,
                       Context context, int number, Long sectionId,
                       Long courseId){

        Intent sectionProfileIntent = new Intent(context, SectionProfileActivity.class);
        sectionProfileIntent.putExtra(Constants.SECTION_ID_EXTRA, sectionId);
        sectionProfileIntent.putExtra(Constants.COURSE_ID_EXTRA, courseId);

        NotificationCompat.Builder notificationCompat = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_event_note_black_48dp)
                .setNumber(number)
                .setContentTitle(courseName  + " Section " + sectionName + " is today")
                .setContentText(instructorName + " has section today for course " + courseName + " Section " + sectionName);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(sectionProfileIntent);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(
                0,
                PendingIntent.FLAG_UPDATE_CURRENT
        );

        notificationCompat.setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(number, notificationCompat.build());
    }
}
