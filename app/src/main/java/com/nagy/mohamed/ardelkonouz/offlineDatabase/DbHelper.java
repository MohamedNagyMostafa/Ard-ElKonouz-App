package com.nagy.mohamed.ardelkonouz.offlineDatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mohamednagy on 6/10/2017.
 */
public class DbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ardElKonouz.db";
    private static final String ENCODING_SETTING = "PRAGMA encoding = 'window-1256'";

    private static final int DATABASE_VERSION = 1;

    public DbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        if(!db.isReadOnly()){
            db.execSQL(ENCODING_SETTING);
        }
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(DbContent.CourseTable.CREATE_COURSE_TABLE);
        sqLiteDatabase.execSQL(DbContent.ChildTable.CREATE_CHILD_TABLE);
        sqLiteDatabase.execSQL(DbContent.InstructorTable.CREATE_INSTRUCTOR_TABLE);
        sqLiteDatabase.execSQL(DbContent.EmployeeTable.CREATE_EMPLOYEE_TABLE);
        sqLiteDatabase.execSQL(DbContent.ChildSectionTable.CREATE_CHILD_SECTION_TABLE);
        sqLiteDatabase.execSQL(DbContent.SectionInstructorTable.CREATE_SECTION_INSTRUCTOR_TABLE);
        sqLiteDatabase.execSQL(DbContent.ShiftDaysTable.CREATE_SHIFT_DAY_TABLE);
        sqLiteDatabase.execSQL(DbContent.SectionTable.CREATE_SECTION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DbContent.ChildTable.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DbContent.InstructorTable.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DbContent.CourseTable.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DbContent.SectionInstructorTable.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DbContent.ChildSectionTable.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DbContent.ShiftDaysTable.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DbContent.SectionTable.TABLE_NAME);

        onCreate(sqLiteDatabase);
    }
}
