package com.nagy.mohamed.ardelkonouz.offlineDatabase;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by mohamednagy on 6/10/2017.
 */
public class DbContent {

    public static final String PRIMARY_KEY = "PRIMARY KEY";
    public static final String REFERENCES = "REFERENCES";
    public static final String FOREIGN_KEY = "FOREIGN KEY";
    public static final String SPACE = " ";
    public static final String TEXT = "TEXT";
    public static final String INTEGER = "INTEGER";
    public static final String BLOB = "BLOB";
    public static final String REAL = "REAL";
    public static final String NOT_NULL = "NOT NULL";
    public static final String CREATE_TABLE = "CREATE TABLE";
    public static final String CONTENT_AUTHORITY = "com.nagy.mohamed.ardelkonouz";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" +  CONTENT_AUTHORITY);

    public static class CourseTable implements BaseColumns{

        public static final String TABLE_NAME = "course";
        public static final Uri CONTENT_URI = BASE_CONTENT_URI
                .buildUpon().appendPath(TABLE_NAME).build();

        public static final String COURSE_NAME_COLUMN = "course_name";
        public static final String COURSE_HOURS_COLUMN = "course_hours";
        public static final String COURSE_COST_COLUMN = "course_cost";
        public static final String COURSE_AVAILABLE_POSITIONS_COLUMN = "available_positions";
        public static final String COURSE_START_DATE_COLUMN = "course_start_date";
        public static final String COURSE_END_DATE_COLUMN = "course_end_date";
        public static final String COURSE_START_AGE_COLUMN = "course_start_age";
        public static final String COURSE_END_AGE_COLUMN = "course_end_age";
        public static final String COURSE_LEVEL_COLUMN = "course_level";
        public static final String COURSE_SALARY_PER_CHILD = "salary_per_child";

        public static final String CREATE_COURSE_TABLE = CREATE_TABLE
                + SPACE + TABLE_NAME + "(" +
                _ID + SPACE + INTEGER + SPACE + PRIMARY_KEY + "," +
                COURSE_NAME_COLUMN + SPACE + TEXT + SPACE + NOT_NULL + "," +
                COURSE_HOURS_COLUMN + SPACE + REAL + SPACE + NOT_NULL + "," +
                COURSE_COST_COLUMN + SPACE + REAL + SPACE + NOT_NULL + "," +
                COURSE_AVAILABLE_POSITIONS_COLUMN + SPACE + INTEGER + SPACE + NOT_NULL + "," +
                COURSE_START_DATE_COLUMN + SPACE + INTEGER + SPACE + NOT_NULL + "," +
                COURSE_END_DATE_COLUMN + SPACE + INTEGER + SPACE + NOT_NULL + "," +
                COURSE_START_AGE_COLUMN + SPACE + INTEGER + SPACE + NOT_NULL + "," +
                COURSE_END_AGE_COLUMN + SPACE + INTEGER + SPACE + NOT_NULL + "," +
                COURSE_SALARY_PER_CHILD + SPACE + REAL + SPACE + NOT_NULL + "," +
                COURSE_LEVEL_COLUMN + SPACE + INTEGER + SPACE + NOT_NULL + ");";

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_NAME ;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_NAME;
    }

    public static class InstructorTable implements  BaseColumns{

        public static final String TABLE_NAME = "instructor";
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(TABLE_NAME).build();

        public static final String INSTRUCTOR_NAME_COLUMN = "instructor_name";
        public static final String INSTRUCTOR_CV_COLUMN = "instructor_cv";
        public static final String INSTRUCTOR_ADDRESS_COLUMN = "instructor_address";
        public static final String INSTRUCTOR_MOBILE_COLUMN = "instructor_mobile";
        public static final String INSTRUCTOR_QUALIFICATION_COLUMN = "instructor_qualification";
        public static final String INSTRUCTOR_AGE_COLUMN = "instructor_age";
        public static final String INSTRUCTOR_GENDER_COLUMN = "instructor_gender";

        public static final String CREATE_INSTRUCTOR_TABLE = CREATE_TABLE + SPACE + TABLE_NAME + "(" +
                _ID + SPACE + INTEGER + SPACE + PRIMARY_KEY + "," +
                INSTRUCTOR_NAME_COLUMN  + SPACE + TEXT + SPACE + NOT_NULL + "," +
                INSTRUCTOR_CV_COLUMN  + SPACE + BLOB   + "," +
                INSTRUCTOR_ADDRESS_COLUMN  + SPACE + TEXT + SPACE + NOT_NULL + "," +
                INSTRUCTOR_MOBILE_COLUMN  + SPACE + TEXT + SPACE + NOT_NULL + "," +
                INSTRUCTOR_GENDER_COLUMN + SPACE + INTEGER + SPACE + NOT_NULL + "," +
                INSTRUCTOR_QUALIFICATION_COLUMN  + SPACE + TEXT + SPACE + NOT_NULL + "," +
                INSTRUCTOR_AGE_COLUMN  + SPACE + INTEGER + SPACE + NOT_NULL + ");";

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_NAME ;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_NAME;
    }

    public static class EmployeeTable implements  BaseColumns{

        public static final String TABLE_NAME = "employee";
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(TABLE_NAME).build();

        public static final String EMPLOYEE_NAME_COLUMN = "employee_name";
        public static final String EMPLOYEE_ADDRESS_COLUMN = "employee_address";
        public static final String EMPLOYEE_ORIGINAL_SALARY_COLUMN = "employee_original_salary";
        public static final String EMPLOYEE_MOBILE_COLUMN = "employee_mobile";
        public static final String EMPLOYEE_QUALIFICATION_COLUMN = "employee_qualification";
        public static final String EMPLOYEE_AGE_COLUMN = "employee_age";
        public static final String EMPLOYEE_GENDER_COLUMN = "employee_gender";

        public static final String CREATE_EMPLOYEE_TABLE = CREATE_TABLE + SPACE + TABLE_NAME + "(" +
                _ID + SPACE + INTEGER + SPACE + PRIMARY_KEY + "," +
                EMPLOYEE_NAME_COLUMN  + SPACE + TEXT + SPACE + NOT_NULL + "," +
                EMPLOYEE_ADDRESS_COLUMN  + SPACE + TEXT + SPACE + NOT_NULL + "," +
                EMPLOYEE_ORIGINAL_SALARY_COLUMN  + SPACE + REAL + SPACE + NOT_NULL + "," +
                EMPLOYEE_MOBILE_COLUMN  + SPACE + TEXT + SPACE + NOT_NULL + "," +
                EMPLOYEE_GENDER_COLUMN + SPACE + INTEGER + SPACE + NOT_NULL + "," +
                EMPLOYEE_QUALIFICATION_COLUMN  + SPACE + TEXT + SPACE + NOT_NULL + "," +
                EMPLOYEE_AGE_COLUMN  + SPACE + INTEGER + SPACE + NOT_NULL + ");";

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_NAME ;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_NAME;
    }

    public static class ChildTable implements BaseColumns{

        public static final String TABLE_NAME = "child";
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(TABLE_NAME).build();

        public static final String CHILD_NAME_COLUMN = "child_name";
        public static final String CHILD_FATHER_NAME_COLUMN = "child_father_name";
        public static final String CHILD_MOTHER_NAME_COLUMN = "child_mother_name";
        public static final String CHILD_FATHER_MOBILE_COLUMN = "child_father_mobile";
        public static final String CHILD_MOTHER_MOBILE_COLUMN = "child_mother_mobile";
        public static final String CHILD_MOBILE_WHATSUP_COLUMN = "child_mobile_whatsup";
        public static final String CHILD_MOTHER_QUALIFICATION_COLUMN = "child_mother_qualification";
        public static final String CHILD_AGE_COLUMN = "child_age";
        public static final String CHILD_EDUCATION_TYPE_COLUMN = "education_type";
        public static final String CHILD_STUDY_YEAR_COLUMN = "child_study_year";
        public static final String CHILD_BIRTH_ORDER_COLUMN = "child_birth_order";
        public static final String CHILD_HANDLING_COLUMN = "child_handling";
        public static final String CHILD_TRAITS_COLUMN = "child_traits";
        public static final String CHILD_FREE_TIME_COLUMN = "child_free_time";
        public static final String CHILD_GENDER_COLUMN = "child_gender";
        public static final String CHILD_FATHER_JOB_COLUMN = "father_job";
        public static final String CHILD_MOTHER_JOB_COLUMN = "mother_job";

        public static final String CREATE_CHILD_TABLE = CREATE_TABLE + SPACE + TABLE_NAME + "(" +
                _ID + SPACE + INTEGER +  SPACE +PRIMARY_KEY + "," +
                CHILD_NAME_COLUMN  + SPACE + TEXT + SPACE + NOT_NULL + "," +
                CHILD_FATHER_NAME_COLUMN  + SPACE + TEXT + SPACE + NOT_NULL + "," +
                CHILD_MOTHER_NAME_COLUMN  + SPACE + TEXT + SPACE + NOT_NULL + "," +
                CHILD_FATHER_MOBILE_COLUMN  + SPACE + TEXT  + "," +
                CHILD_MOTHER_MOBILE_COLUMN  + SPACE + TEXT  + "," +
                CHILD_MOBILE_WHATSUP_COLUMN  + SPACE + TEXT + NOT_NULL + "," +
                CHILD_MOTHER_QUALIFICATION_COLUMN  + SPACE + TEXT + SPACE + NOT_NULL + "," +
                CHILD_AGE_COLUMN  + SPACE + INTEGER + SPACE + NOT_NULL + "," +
                CHILD_EDUCATION_TYPE_COLUMN  + SPACE + INTEGER + SPACE + NOT_NULL + "," +
                CHILD_BIRTH_ORDER_COLUMN  + SPACE + INTEGER + SPACE + NOT_NULL + "," +
                CHILD_HANDLING_COLUMN  + SPACE + INTEGER + SPACE + NOT_NULL + "," +
                CHILD_TRAITS_COLUMN  + SPACE + INTEGER + SPACE + NOT_NULL + "," +
                CHILD_FREE_TIME_COLUMN  + SPACE + INTEGER + SPACE + NOT_NULL + "," +
                CHILD_GENDER_COLUMN  + SPACE + INTEGER + SPACE + NOT_NULL + "," +
                CHILD_STUDY_YEAR_COLUMN  + SPACE + INTEGER + SPACE + NOT_NULL +
                CHILD_FATHER_JOB_COLUMN  + SPACE + TEXT + NOT_NULL + "," +
                CHILD_MOTHER_JOB_COLUMN  + SPACE + TEXT + NOT_NULL + ");";

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_NAME ;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_NAME;
    }

    public static class CourseInstructorTable{

        public static final String TABLE_NAME = "course_instructor";
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(TABLE_NAME).build();

        public static final String COURSE_ID_COLUMN = "course_id";
        public static final String INSTRUCTOR_ID_COLUMN = "instructor_id";
        public static final String TOTAL_SALARY_COLUMN = "total_salary";
        public static final String PAY_STATE_COLUMN =" pay_state";

        public static final String CREATE_COURSE_INSTRUCTOR_TABLE = CREATE_TABLE + SPACE + TABLE_NAME
                + "(" + COURSE_ID_COLUMN + SPACE + INTEGER + SPACE + NOT_NULL + ","
                + INSTRUCTOR_ID_COLUMN + SPACE + INTEGER + SPACE + NOT_NULL + ","
                + TOTAL_SALARY_COLUMN + SPACE + REAL + SPACE + NOT_NULL + ","
                + PAY_STATE_COLUMN + SPACE + INTEGER + SPACE + NOT_NULL + ","
                + FOREIGN_KEY + SPACE + "(" + INSTRUCTOR_ID_COLUMN + ")" + SPACE + REFERENCES + SPACE
                + InstructorTable.TABLE_NAME + SPACE + "(" + InstructorTable._ID + ")"+
                FOREIGN_KEY + SPACE + "(" +COURSE_ID_COLUMN + ")" + SPACE + REFERENCES + SPACE
                + CourseTable.TABLE_NAME + SPACE + "(" + CourseTable._ID + ")" +");";

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_NAME ;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_NAME;
    }

    public static class ChildCourseTable{

        public static final String TABLE_NAME = "child_course_table";
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(TABLE_NAME).build();

        public static final String COURSE_ID_COLUMN = "course_id";
        public static final String CHILD_ID_COLUMN = "child_id";
        public static final String CHILD_COURSE_COMPLETED = "child_course_completed";

        public static final String CREATE_CHILD_COURSE_TABLE = CREATE_TABLE + SPACE + TABLE_NAME
                + "(" + COURSE_ID_COLUMN + SPACE + INTEGER + SPACE + NOT_NULL + ","
                + CHILD_ID_COLUMN + SPACE + INTEGER + SPACE + NOT_NULL + ","
                + CHILD_COURSE_COMPLETED + SPACE + INTEGER + SPACE + NOT_NULL + ","
                + FOREIGN_KEY + SPACE + "(" + COURSE_ID_COLUMN + ")" + SPACE + REFERENCES + SPACE
                + CourseTable.TABLE_NAME + SPACE + "(" + CourseTable._ID + ")" + ","
                + FOREIGN_KEY + SPACE + "(" + CHILD_ID_COLUMN + ")" + SPACE + REFERENCES + SPACE
                + ChildTable.TABLE_NAME + SPACE + "(" + ChildTable._ID + ")" +")";

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_NAME ;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_NAME;
    }



}
