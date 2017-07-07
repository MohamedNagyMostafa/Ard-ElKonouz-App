package com.nagy.mohamed.ardelkonouz.offlineDatabase;

import android.net.Uri;

import java.util.ArrayList;

/**
 * Created by mohamednagy on 6/12/2017.
 */
public class DatabaseController {

    public static class ProjectionDatabase {

        public static final String[] COURSE_PROJECTION = {
                DbContent.CourseTable.TABLE_NAME + "." + DbContent.CourseTable._ID,
                DbContent.CourseTable.COURSE_NAME_COLUMN,
                DbContent.CourseTable.COURSE_HOURS_COLUMN,
                DbContent.CourseTable.COURSE_COST_COLUMN,
                DbContent.CourseTable.COURSE_AVAILABLE_POSITIONS_COLUMN,
                DbContent.CourseTable.COURSE_START_DATE_COLUMN,
                DbContent.CourseTable.COURSE_END_DATE_COLUMN,
                DbContent.CourseTable.COURSE_START_AGE_COLUMN,
                DbContent.CourseTable.COURSE_END_AGE_COLUMN,
                DbContent.CourseTable.COURSE_LEVEL_COLUMN,
                DbContent.CourseTable.COURSE_SALARY_PER_CHILD,
                DbContent.CourseTable.COURSE_DAYS_COLUMN,
                DbContent.CourseTable.COURSE_SESSIONS_NUMBER_COLUMN
        };

        public static final int COURSE_ID = 0;
        public static final int COURSE_NAME = 1;
        public static final int COURSE_HOURS = 2;
        public static final int COURSE_COST = 3;
        public static final int COURSE_AVAILABLE_POSITIONS = 4;
        public static final int COURSE_START_DATE = 5;
        public static final int COURSE_END_DATE = 6;
        public static final int COURSE_START_AGE = 7;
        public static final int COURSE_END_AGE = 8;
        public static final int COURSE_LEVEL = 9;
        public static final int COURSE_SALARY_PER_CHILD = 10;
        public static final int COURSE_DAYS_COLUMN = 11;
        public static final int COURSE_SESSIONS_NUMBER_COLUMN = 12;

        public static final String[] COURSE_LIST_PROJECTION = {
                DbContent.CourseTable.TABLE_NAME + "." + DbContent.CourseTable._ID,
                DbContent.CourseTable.COURSE_NAME_COLUMN,
                DbContent.CourseTable.COURSE_START_DATE_COLUMN,
                DbContent.CourseTable.COURSE_END_DATE_COLUMN,
        };

        public static final int COURSE_LIST_ID = 0;
        public static final int COURSE_LIST_NAME = 1;
        public static final int COURSE_LIST_START_DATE = 2;
        public static final int COURSE_LIST_END_DATE = 3;


        public static final String[] CHILD_PROJECTION = {
                DbContent.ChildTable.TABLE_NAME + "." + DbContent.ChildTable._ID,
                DbContent.ChildTable.CHILD_NAME_COLUMN,
                DbContent.ChildTable.CHILD_FATHER_NAME_COLUMN,
                DbContent.ChildTable.CHILD_MOTHER_NAME_COLUMN,
                DbContent.ChildTable.CHILD_FATHER_MOBILE_COLUMN,
                DbContent.ChildTable.CHILD_MOTHER_MOBILE_COLUMN,
                DbContent.ChildTable.CHILD_MOBILE_WHATSUP_COLUMN,
                DbContent.ChildTable.CHILD_MOTHER_QUALIFICATION_COLUMN,
                DbContent.ChildTable.CHILD_AGE_COLUMN,
                DbContent.ChildTable.CHILD_EDUCATION_TYPE_COLUMN,
                DbContent.ChildTable.CHILD_STUDY_YEAR_COLUMN,
                DbContent.ChildTable.CHILD_GENDER_COLUMN,
                DbContent.ChildTable.CHILD_FREE_TIME_COLUMN,
                DbContent.ChildTable.CHILD_TRAITS_COLUMN,
                DbContent.ChildTable.CHILD_HANDLING_COLUMN,
                DbContent.ChildTable.CHILD_BIRTH_ORDER_COLUMN,
                DbContent.ChildTable.CHILD_FATHER_JOB_COLUMN,
                DbContent.ChildTable.CHILD_MOTHER_JOB_COLUMN
        };

        public static final int CHILD_ID = 0;
        public static final int CHILD_NAME = 1;
        public static final int CHILD_FATHER_NAME = 2;
        public static final int CHILD_MOTHER_NAME = 3;
        public static final int CHILD_FATHER_MOBILE = 4;
        public static final int CHILD_MOTHER_MOBILE = 5;
        public static final int CHILD_MOBILE_WHATSUP = 6;
        public static final int CHILD_MOTHER_QUALIFICATION = 7;
        public static final int CHILD_AGE = 8;
        public static final int CHILD_EDUCATION_TYPE = 9;
        public static final int CHILD_STUDY_YEAR = 10;
        public static final int CHILD_GENDER = 11;
        public static final int CHILD_FREE_TIME = 12;
        public static final int CHILD_TRAITS = 13;
        public static final int CHILD_HANDLING = 14;
        public static final int CHILD_BIRTH_ORDER = 15;
        public static final int CHILD_FATHER_JOB = 16;
        public static final int CHILD_MOTHER_JOB = 17;

        public static final String[] CHILD_LIST_PROJECTION = {
                DbContent.ChildTable.TABLE_NAME + "." + DbContent.ChildTable._ID,
                DbContent.ChildTable.CHILD_NAME_COLUMN,
                DbContent.ChildTable.CHILD_AGE_COLUMN,
        };

        public static final int CHILD_LIST_ID = 0;
        public static final int CHILD_LIST_NAME = 1;
        public static final int CHILD_LIST_AGE = 2;

        public static final String[] EMPLOYEE_PROJECTION = {
                DbContent.EmployeeTable.TABLE_NAME + "." + DbContent.EmployeeTable._ID,
                DbContent.EmployeeTable.EMPLOYEE_NAME_COLUMN,
                DbContent.EmployeeTable.EMPLOYEE_ADDRESS_COLUMN,
                DbContent.EmployeeTable.EMPLOYEE_MOBILE_COLUMN,
                DbContent.EmployeeTable.EMPLOYEE_ORIGINAL_SALARY_COLUMN,
                DbContent.EmployeeTable.EMPLOYEE_QUALIFICATION_COLUMN,
                DbContent.EmployeeTable.EMPLOYEE_AGE_COLUMN,
                DbContent.EmployeeTable.EMPLOYEE_GENDER_COLUMN
        };

        public static final int EMPLOYEE_ID = 0;
        public static final int EMPLOYEE_NAME = 1;
        public static final int EMPLOYEE_ADDRESS = 2;
        public static final int EMPLOYEE_MOBILE = 3;
        public static final int EMPLOYEE_ORIGINAL_SALARY = 4;
        public static final int EMPLOYEE_QUALIFICATION = 5;
        public static final int EMPLOYEE_AGE = 6;
        public static final int EMPLOYEE_GENDER = 7;

        public static final String[] EMPLOYEE_LIST_PROJECTION = {
                DbContent.EmployeeTable.TABLE_NAME + "." + DbContent.EmployeeTable._ID,
                DbContent.EmployeeTable.EMPLOYEE_NAME_COLUMN,
                DbContent.EmployeeTable.EMPLOYEE_MOBILE_COLUMN,
        };

        public static final int EMPLOYEE_LIST_ID = 0;
        public static final int EMPLOYEE_LIST_NAME = 1;
        public static final int EMPLOYEE_LIST_MOBILE = 2;


        public static final String[] INSTRUCTOR_PROJECTION = {
                DbContent.InstructorTable.TABLE_NAME + "." + DbContent.InstructorTable._ID,
                DbContent.InstructorTable.INSTRUCTOR_NAME_COLUMN,
                DbContent.InstructorTable.INSTRUCTOR_ADDRESS_COLUMN,
                DbContent.InstructorTable.INSTRUCTOR_MOBILE_COLUMN,
                DbContent.InstructorTable.INSTRUCTOR_QUALIFICATION_COLUMN,
                DbContent.InstructorTable.INSTRUCTOR_AGE_COLUMN,
                DbContent.InstructorTable.INSTRUCTOR_CV_COLUMN,
                DbContent.InstructorTable.INSTRUCTOR_GENDER_COLUMN

        };

        public static final int INSTRUCTOR_ID = 0;
        public static final int INSTRUCTOR_NAME = 1;
        public static final int INSTRUCTOR_ADDRESS = 2;
        public static final int INSTRUCTOR_MOBILE = 3;
        public static final int INSTRUCTOR_QUALIFICATION = 4;
        public static final int INSTRUCTOR_AGE = 5;
        public static final int INSTRUCTOR_CV = 6;
        public static final int INSTRUCTOR_GENDER = 7;

        public static final String[] INSTRUCTOR_LIST_PROJECTION = {
                DbContent.InstructorTable.TABLE_NAME + "." + DbContent.InstructorTable._ID,
                DbContent.InstructorTable.INSTRUCTOR_NAME_COLUMN,

        };

        public static final int INSTRUCTOR_LIST_ID = 0;
        public static final int INSTRUCTOR_LIST_NAME = 1;

        public static final String[] COURSE_INSTRUCTOR_TABLE = {
                DbContent.CourseInstructorTable.COURSE_ID_COLUMN,
                DbContent.CourseInstructorTable.INSTRUCTOR_ID_COLUMN,
                DbContent.CourseInstructorTable.PAID_COLUMN
        };

        public static final int COURSE_INSTRUCTOR_COURSE_ID = 0;
        public static final int COURSE_INSTRUCTOR_INSTRUCTOR_ID = 1;
        public static final int COURSE_INSTRUCTOR_PAID =2;

        public static final String[] COURSE_CHILD_JOIN_LIST_TABLE = {
                DbContent.ChildCourseTable.TABLE_NAME+"."+ DbContent.ChildCourseTable._ID,
                DbContent.CourseTable.COURSE_NAME_COLUMN,
                DbContent.CourseTable.COURSE_START_DATE_COLUMN,
                DbContent.CourseTable.COURSE_END_DATE_COLUMN,
                DbContent.ChildCourseTable.COURSE_ID_COLUMN

        };
        public static final int COURSE_CHILD_JOIN_LIST_ID = 0;
        public static final int COURSE_CHILD_JOIN_LIST_COURSE_NAME_COLUMN = 1;
        public static final int COURSE_CHILD_JOIN_LIST_COURSE_START_DATE_COLUMN = 2;
        public static final int COURSE_CHILD_JOIN_LIST_COURSE_END_DATE_COLUMN = 3;
        public static final int COURSE_CHILD_JOIN_LIST_COURSE_ID_COLUMN = 4;

        public static final String[] COURSE_INSTRUCTOR_LIST_JOIN_TABLE = {
                DbContent.CourseInstructorTable.TABLE_NAME +"."+ DbContent.CourseInstructorTable._ID,
                DbContent.CourseInstructorTable.COURSE_ID_COLUMN,
                DbContent.CourseTable.COURSE_NAME_COLUMN,
                DbContent.CourseTable.COURSE_START_DATE_COLUMN,
                DbContent.CourseTable.COURSE_END_DATE_COLUMN,
        };

        public static final int COURSE_INSTRUCTOR_LIST_JOIN_ID = 0;
        public static final int COURSE_INSTRUCTOR_LIST_JOIN_COURSE_ID = 1;
        public static final int COURSE_INSTRUCTOR_LIST_JOIN_COURSE_NAME = 2;
        public static final int COURSE_INSTRUCTOR_LIST_JOIN_COURSE_START_DATE = 3;
        public static final int COURSE_INSTRUCTOR_LIST_JOIN_COURSE_END_DATE = 4;

        public static final String[] SHIFT_LIST_PROJECTION = {
                DbContent.CourseInstructorTable.TABLE_NAME + "." + DbContent.CourseInstructorTable._ID,
                DbContent.CourseTable.COURSE_NAME_COLUMN,
                DbContent.CourseInstructorTable.COURSE_ID_COLUMN,
                DbContent.InstructorTable.INSTRUCTOR_NAME_COLUMN,
                DbContent.CourseTable.COURSE_START_DATE_COLUMN,
                DbContent.CourseTable.COURSE_END_DATE_COLUMN,
                DbContent.CourseTable.COURSE_DAYS_COLUMN
        };

        public static final int SHIFT_LIST_COURSE_NAME = 1;
        public static final int SHIFT_LIST_COURSE_ID = 2;
        public static final int SHIFT_LIST_INSTRUCTOR_NAME = 3;
        public static final int SHIFT_LIST_COURSE_START_dATE = 4;
        public static final int SHIFT_LIST_COURSE_END_dATE = 5;
        public static final int SHIFT_LIST_COURSE_DAYS = 6;

        public static final String[] SHIFT_TABLE_PROJECTION = {
                DbContent.ShiftDaysTable.TABLE_NAME + "."  + DbContent.ShiftDaysTable._ID,
                DbContent.ShiftDaysTable.COURSE_ID_COLUMN,
                DbContent.ShiftDaysTable.START_DATE_COLUMN,
                DbContent.ShiftDaysTable.END_DATE_COLUMN
        };

        public static final int SHIFT_COURSE_ID_COLUMN = 1;
        public static final int SHIFT_START_DATE_COLUMN = 2;
        public static final int SHIFT_END_DATE_COLUMN = 3;

        public static final String[] SHIFT_COURSE_JOIN_PROJECTION = {
                DbContent.ShiftDaysTable.TABLE_NAME + "." + DbContent.ShiftDaysTable._ID,
                DbContent.ShiftDaysTable.START_DATE_COLUMN,
                DbContent.ShiftDaysTable.END_DATE_COLUMN,
                DbContent.CourseTable.COURSE_NAME_COLUMN,
                DbContent.CourseTable.COURSE_HOURS_COLUMN,
                DbContent.CourseTable.COURSE_COST_COLUMN,
                DbContent.CourseTable.COURSE_AVAILABLE_POSITIONS_COLUMN,
                DbContent.CourseTable.COURSE_START_DATE_COLUMN,
                DbContent.CourseTable.COURSE_END_DATE_COLUMN,
                DbContent.CourseTable.COURSE_START_AGE_COLUMN,
                DbContent.CourseTable.COURSE_END_AGE_COLUMN,
                DbContent.CourseTable.COURSE_LEVEL_COLUMN,
                DbContent.CourseTable.COURSE_SALARY_PER_CHILD,
                DbContent.CourseTable.COURSE_DAYS_COLUMN,
                DbContent.CourseTable.COURSE_SESSIONS_NUMBER_COLUMN
        };

        public static final int SHIFT_COURSE_JOIN_START_DATE_COLUMN = 1;
        public static final int SHIFT_COURSE_JOIN_END_DATE_COLUMN =2;
        public static final int SHIFT_COURSE_JOIN_COURSE_NAME_COLUMN = 3;
        public static final int SHIFT_COURSE_JOIN_COURSE_HOURS_COLUMN = 4;
        public static final int SHIFT_COURSE_JOIN_COURSE_COST_COLUMN = 5;
        public static final int SHIFT_COURSE_JOIN_COURSE_AVAILABLE_POSITIONS_COLUMN = 6;
        public static final int SHIFT_COURSE_JOIN_COURSE_START_DATE_COLUMN = 7;
        public static final int SHIFT_COURSE_JOIN_COURSE_END_DATE_COLUMN = 8;
        public static final int SHIFT_COURSE_JOIN_COURSE_START_AGE_COLUMN = 9;
        public static final int SHIFT_COURSE_JOIN_COURSE_END_AGE_COLUMN = 10;
        public static final int SHIFT_COURSE_JOIN_COURSE_LEVEL_COLUMN = 11;
        public static final int SHIFT_COURSE_JOIN_COURSE_SALARY_PER_CHILD = 12;
        public static final int SHIFT_COURSE_JOIN_COURSE_DAYS_COLUMN = 13;
        public static final int SHIFT_COURSE_JOIN_COURSE_SESSIONS_NUMBER_COLUMN = 14;

        public static final String[] CHOICES_SELECTION_PROJECTION = {
                DbContent.CourseTable.TABLE_NAME + "." + DbContent.CourseTable._ID,
                DbContent.CourseTable.COURSE_NAME_COLUMN,
        };

        public static final int CHOICES_SELECTION_ID = 0;
        public static final int CHOICES_SELECTION_COURSE_NAME = 1;

        public static final String[] COURSE_DATE_PROJECTION = {
                DbContent.CourseTable.COURSE_START_DATE_COLUMN,
                DbContent.CourseTable.COURSE_DAYS_COLUMN,
                DbContent.CourseTable.COURSE_SESSIONS_NUMBER_COLUMN
        };

        public static final int COURSE_DATE_START_DATE = 0;
        public static final int COURSE_DATE_DAYS= 1;
        public static final int COURSE_DATE_SESSIONS_NUMBER = 2;
    }

    public static class UriDatabase{

        public static final Uri CHILD_TABLE_URI = DbContent.ChildTable.CONTENT_URI;
        public static final Uri EMPLOYEE_TABLE_URI = DbContent.EmployeeTable.CONTENT_URI;
        public static final Uri INSTRUCTOR_TABLE_URI = DbContent.InstructorTable.CONTENT_URI;
        public static final Uri COURSE_TABLE_URI = DbContent.CourseTable.CONTENT_URI;
        public static final Uri COURSE_CHILD_URI = DbContent.ChildCourseTable.CONTENT_URI;
        public static final Uri COURSE_INSTRUCTOR_URI = DbContent.CourseInstructorTable.CONTENT_URI;
        public static final Uri SHIFT_URI = DbContent.ShiftDaysTable.CONTENT_URI;


        public static Uri getChildTableWithIdUri(long id){
            return CHILD_TABLE_URI.buildUpon().appendPath(String.valueOf(id)).build();
        }

        public static Uri getEmployeeTableWithIdUri(long id){
            return EMPLOYEE_TABLE_URI.buildUpon().appendPath(String.valueOf(id)).build();
        }

        public static Uri getInstructorTableWithIdUri(long id){
            return INSTRUCTOR_TABLE_URI.buildUpon().appendPath(String.valueOf(id)).build();
        }

        public static Uri getCourseTableWithIdUri(long id){
            return COURSE_TABLE_URI.buildUpon().appendPath(String.valueOf(id)).build();
        }

        public static Uri getCourseTableWithEndDateIdWithCompleteIdWithAgeRangeUri(long age, long date){
            return COURSE_TABLE_URI.buildUpon()
                    .appendPath(DbContent.CourseTable.COURSE_END_DATE_COLUMN)
                    .appendPath(DbContent.CourseTable.COURSE_AVAILABLE_POSITIONS_COLUMN)
                    .appendPath(String.valueOf(date))
                    .appendPath(String.valueOf(age))
                    .build();
        }

        public static Uri getCourseTableWithIdWithEndDate(long date, long id){
            return COURSE_TABLE_URI.buildUpon()
                    .appendPath(DbContent.CourseTable.COURSE_END_DATE_COLUMN)
                    .appendPath(String.valueOf(id))
                    .appendPath(String.valueOf(date)).build();
        }

        public static Uri getCourseChildTableWithChildIdUri(long id){
            return COURSE_CHILD_URI.buildUpon().appendPath(DbContent.ChildTable.TABLE_NAME)
                    .appendPath(String.valueOf(id)).build();
        }

        public static Uri getCourseChildTableWithCourseIdUri(long id){
            return COURSE_CHILD_URI.buildUpon().appendPath(DbContent.CourseTable.TABLE_NAME)
                    .appendPath(String.valueOf(id)).build();
        }

        public static Uri getCourseChildTableWithChildIDAndCourseIdUri(long childId, long courseId){
            return COURSE_CHILD_URI.buildUpon().appendPath(DbContent.ChildTable.TABLE_NAME)
                    .appendPath(DbContent.CourseTable.TABLE_NAME)
                    .appendPath(String.valueOf(childId))
                    .appendPath(String.valueOf(courseId)).build();
        }

        public static Uri getCourseInstructorTableWithCourseIdUri(long id){
            return COURSE_INSTRUCTOR_URI.buildUpon().appendPath(DbContent.CourseTable.TABLE_NAME)
                    .appendPath(String.valueOf(id)).build();
        }

        public static Uri getCourseInstructorTableWithInstructorIdUri(long id){
            return COURSE_INSTRUCTOR_URI.buildUpon().appendPath(DbContent.InstructorTable.TABLE_NAME)
                    .appendPath(String.valueOf(id)).build();
        }

        public static Uri getChildTableWithSearchUri(String searchChars){
            return CHILD_TABLE_URI.buildUpon().appendPath(DbContent.ChildTable.CHILD_NAME_COLUMN)
                    .appendPath(searchChars).build();
        }

        public static Uri getCourseTableWithSearchUri(String searchChars){
            return COURSE_TABLE_URI.buildUpon().appendPath(DbContent.CourseTable.COURSE_NAME_COLUMN)
                    .appendPath(searchChars).build();
        }

        public static Uri getEmployeeTableWithSearchUri(String searchChars){
            return EMPLOYEE_TABLE_URI.buildUpon().appendPath(DbContent.EmployeeTable.EMPLOYEE_NAME_COLUMN)
                    .appendPath(searchChars).build();
        }

        public static Uri getInstructorTableWithSearchUri(String searchChars){
            return INSTRUCTOR_TABLE_URI.buildUpon().appendPath(DbContent.InstructorTable.INSTRUCTOR_NAME_COLUMN)
                    .appendPath(searchChars).build();
        }

        public static Uri getCoursesByDaySearchUri(String searchChars, long indexDay){
            return COURSE_TABLE_URI.buildUpon().appendPath("day")
                    .appendPath(String.valueOf(indexDay))
                    .appendPath(searchChars)
                    .build();
        }

        public static Uri getShiftWithCourseId(long id){
            return SHIFT_URI.buildUpon().appendPath(String.valueOf(id)).build();
        }

        public static Uri getShiftWithCourseJoinId(long id){
            return SHIFT_URI.buildUpon().appendPath(DbContent.ShiftDaysTable.COURSE_ID_COLUMN)
                    .appendPath(String.valueOf(id)).build();
        }

        public static Uri getCourseChoices(ArrayList<Long> ids, String searchChars){
            StringBuilder encodeIds  = new StringBuilder("");
            if(ids.size() > 0)
                encodeIds.append(ids.get(0));

            for(int i = 1 ; i < ids.size(); i++){
                encodeIds.append("k").append(ids.get(i));
            }

            return COURSE_TABLE_URI.buildUpon()
                    .appendPath(DbContent.CourseTable.COURSE_NAME_COLUMN)
                    .appendPath(encodeIds.toString())
                    .appendPath(searchChars).build();
        }

        public static Uri getCourseSelection(ArrayList<Long> ids){
            StringBuilder encodeIds  = new StringBuilder("");
            encodeIds.append(ids.get(0));

            for(int i = 1 ; i < ids.size(); i++){
                encodeIds.append("k").append(ids.get(i));
            }

            return COURSE_TABLE_URI.buildUpon()
                    .appendPath(DbContent.CourseTable._ID)
                    .appendPath(encodeIds.toString())
                    .build();
        }

    }

}
