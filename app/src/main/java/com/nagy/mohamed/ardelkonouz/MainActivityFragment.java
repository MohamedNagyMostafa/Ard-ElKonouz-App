package com.nagy.mohamed.ardelkonouz;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nagy.mohamed.ardelkonouz.offlineDatabase.DbContent;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {


    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        databaseTesting();


        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    private void databaseTesting(){
        // Test inserting.
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbContent.CourseTable.COURSE_LEVEL_COLUMN, 1);
        contentValues.put(DbContent.CourseTable.COURSE_START_AGE_COLUMN, 1);
        contentValues.put(DbContent.CourseTable.COURSE_END_AGE_COLUMN, 1);
        contentValues.put(DbContent.CourseTable.COURSE_START_DATE_COLUMN, 1);
        contentValues.put(DbContent.CourseTable.COURSE_END_DATE_COLUMN, 1);
        contentValues.put(DbContent.CourseTable.COURSE_COMPLETE_COLUMN, 1);
        contentValues.put(DbContent.CourseTable.COURSE_COST_COLUMN, 1);
        contentValues.put(DbContent.CourseTable.COURSE_HOURS_COLUMN, 1);
        contentValues.put(DbContent.CourseTable.COURSE_NAME_COLUMN, "android");
        getContext().getContentResolver().insert(DbContent.CourseTable.CONTENT_URI, contentValues );
        // Test Join
        ContentValues contentValues2 = new ContentValues();
        contentValues2.put(DbContent.InstructorTable.INSTRUCTOR_NAME_COLUMN, "Mohamed");
        contentValues2.put(DbContent.InstructorTable.INSTRUCTOR_ADDRESS_COLUMN, "giza");
        contentValues2.put(DbContent.InstructorTable.INSTRUCTOR_TOTAL_SALARY_COLUMN, 2);
        contentValues2.put(DbContent.InstructorTable.INSTRUCTOR_MOBILE_COLUMN, 2);
        contentValues2.put(DbContent.InstructorTable.INSTRUCTOR_QUALIFICATION_COLUMN, 2);
        contentValues2.put(DbContent.InstructorTable.INSTRUCTOR_AGE_COLUMN, 2);
        contentValues2.put(DbContent.InstructorTable.INSTRUCTOR_GENDER_COLUMN, 1);
        contentValues2.put(DbContent.InstructorTable.INSTRUCTOR_HOURS_PER_DAY_COLUMN, 1);
        contentValues2.put(DbContent.InstructorTable.INSTRUCTOR_ORIGINAL_HOURS_PER_DAY_COLUMN, 1);

        getContext().getContentResolver().insert(DbContent.InstructorTable.CONTENT_URI, contentValues2 );
        ContentValues contentValues1 = new ContentValues();
        contentValues1.put(DbContent.CourseInstructorTable.COURSE_ID_COLUMN, 1);
        contentValues1.put(DbContent.CourseInstructorTable.INSTRUCTOR_ID_COLUMN, 1);
        getContext().getContentResolver().insert(DbContent.CourseInstructorTable.CONTENT_URI, contentValues1 );

        Cursor cursor = getContext().getContentResolver().query(
                DbContent.CourseInstructorTable.CONTENT_URI.buildUpon().appendPath(DbContent.CourseTable.TABLE_NAME).appendPath(String.valueOf(1)).build(),
                null,
                null,
                null,
                null);
        Log.e("result",String.valueOf(cursor.getCount()));
        for(String name: cursor.getColumnNames()){
            Log.e("result",name);
        }
        if(cursor.getCount() != 0) {
            cursor.moveToFirst();
            Log.e("result", cursor.getString(cursor.getColumnIndex(DbContent.InstructorTable.INSTRUCTOR_NAME_COLUMN)));
            Log.e("result", cursor.getString(cursor.getColumnIndex(DbContent.CourseTable.COURSE_NAME_COLUMN)));

        }
        cursor.close();
    }
}
