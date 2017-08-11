package com.nagy.mohamed.ardelkonouz.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;

import com.nagy.mohamed.ardelkonouz.R;
import com.nagy.mohamed.ardelkonouz.component.Shift;
import com.nagy.mohamed.ardelkonouz.helper.Constants;
import com.nagy.mohamed.ardelkonouz.helper.Utility;
import com.nagy.mohamed.ardelkonouz.offlineDatabase.DatabaseController;
import com.nagy.mohamed.ardelkonouz.offlineDatabase.DbContent;

import java.util.ArrayList;

/**
 * Created by mohamednagy on 8/9/2017.
 */

public class ReceiverBroadCast extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(context.getString(R.string.receiver))){

            Cursor sectionCoursesCursor = context.getContentResolver().query(
                    DatabaseController.UriDatabase.COURSE_SECTION_JOIN_URI,
                    DatabaseController.ProjectionDatabase.RECEIVER_PROJECTION,
                    null,
                    null,
                    null
            );

            if(sectionCoursesCursor != null){
                int counter = 1;
                while (sectionCoursesCursor.moveToNext()){
                    final Long SECTION_ID = sectionCoursesCursor.getLong(
                            DatabaseController.ProjectionDatabase.RECEIVER_SECTION_ID
                    );
                    final Long COURSE_ID = sectionCoursesCursor.getLong(
                            DatabaseController.ProjectionDatabase.RECEIVER_COURSE_ID
                    );
                    final String SECTION_DAYS = sectionCoursesCursor.getString(
                            DatabaseController.ProjectionDatabase.RECEIVER_SECTION_DAYS
                    );
                    final Long START_DATE = sectionCoursesCursor.getLong(
                            DatabaseController.ProjectionDatabase.RECEIVER_SECTION_START_DATE
                    );
                    final Long END_DATE = sectionCoursesCursor.getLong(
                            DatabaseController.ProjectionDatabase.RECEIVER_SECTION_END_DATE
                    );
                    final String COURSE_NAME = sectionCoursesCursor.getString(
                            DatabaseController.ProjectionDatabase.RECEIVER_COURSE_NAME
                    );
                    final int SECTION_NAME = sectionCoursesCursor.getInt(
                            DatabaseController.ProjectionDatabase.RECEIVER_SECTION_NAME
                    );

                    Cursor shiftsCursor = context.getContentResolver().query(
                            DatabaseController.UriDatabase.getShiftWithSectionId(SECTION_ID),
                            DatabaseController.ProjectionDatabase.SHIFT_TABLE_PROJECTION,
                            null,
                            null,
                            null
                    );

                    if(shiftsCursor != null) {
                        ArrayList<Shift> shifts = new ArrayList<>();

                        while (shiftsCursor.moveToNext()){
                            Shift shift = new Shift(
                                    shiftsCursor.getLong(
                                            DatabaseController.ProjectionDatabase.SHIFT_START_DATE_COLUMN),
                                    shiftsCursor.getLong(
                                            DatabaseController.ProjectionDatabase.SHIFT_END_DATE_COLUMN),
                                    SECTION_ID);

                            shifts.add(shift);
                        }
                        String sectionState = Utility.getNextDayAsString(
                                Utility.getNextSessionDay(
                                        shifts,
                                        SECTION_DAYS,
                                        END_DATE,
                                        START_DATE
                                )
                        );

                        if(sectionState.equals(Constants.TODAY)){
                            Cursor instructorCursor = context.getContentResolver().query(
                                    DatabaseController.UriDatabase.getSectionInstructorTableWithSectionIdUri(SECTION_ID),
                                    new String[]{DbContent.InstructorTable.INSTRUCTOR_NAME_COLUMN},
                                    null,
                                    null,
                                    null
                            );

                            if(instructorCursor != null){
                                if(instructorCursor.moveToNext()){
                                    final String INSTRUCTOR_NAME = instructorCursor.getString(0);

                                    CourseNotification courseNotification = new CourseNotification();
                                    courseNotification.create(
                                            COURSE_NAME,
                                            String.valueOf(SECTION_NAME),
                                            INSTRUCTOR_NAME,
                                            context,
                                            counter++,
                                            SECTION_ID,
                                            COURSE_ID
                                    );
                                    instructorCursor.close();
                                }
                            }
                        }

                        shiftsCursor.close();
                    }
                }
                sectionCoursesCursor.close();
            }
        }
    }

}
