package com.nagy.mohamed.ardelkonouz.offlineDatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by mohamednagy on 6/10/2017.
 */
public class DbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ardElKonouz.db";

    private static final int DATABASE_VERSION = 1;

    public DbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.e("DbHelper","cccccccccccccccccccccc");

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.e("create helper called", "cccccccccccccccccccccc");

        sqLiteDatabase.execSQL(DbContent.CourseTable.CREATE_COURSE_TABLE);
        sqLiteDatabase.execSQL(DbContent.ChildTable.CREATE_CHILD_TABLE);
        sqLiteDatabase.execSQL(DbContent.InstructorTable.CREATE_INSTRUCTOR_TABLE);
        sqLiteDatabase.execSQL(DbContent.EmployeeTable.CREATE_EMPLOYEE_TABLE);
        sqLiteDatabase.execSQL(DbContent.ChildCourseTable.CREATE_CHILD_COURSE_TABLE);
        sqLiteDatabase.execSQL(DbContent.CourseInstructorTable.CREATE_COURSE_INSTRUCTOR_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.e("upgrade called", "cccccccccccccccccccccc");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DbContent.ChildTable.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DbContent.InstructorTable.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DbContent.CourseTable.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DbContent.CourseInstructorTable.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DbContent.ChildCourseTable.TABLE_NAME);

        onCreate(sqLiteDatabase);
    }
}
