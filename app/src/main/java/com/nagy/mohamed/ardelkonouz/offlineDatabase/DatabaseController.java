package com.nagy.mohamed.ardelkonouz.offlineDatabase;

import android.net.Uri;

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
                DbContent.CourseTable.COURSE_SALARY_PER_CHILD
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


        public static final String[] COURSE_INSTRUCTOR_TABLE = {
                DbContent.CourseInstructorTable.COURSE_ID_COLUMN,
                DbContent.CourseInstructorTable.INSTRUCTOR_ID_COLUMN,
                DbContent.CourseInstructorTable.TOTAL_SALARY_COLUMN,
                DbContent.CourseInstructorTable.PAY_STATE_COLUMN
        };

        public static final int COURSE_INSTRUCTOR_COURSE_ID = 0;
        public static final int COURSE_INSTRUCTOR_INSTRUCTOR_ID = 1;
        public static final int COURSE_INSTRUCTOR_TOTAL_SALARY = 2;
        public static final int COURSE_INSTRUCTOR_PAY_STATE = 3;


        public static final String[] COURSE_CHILD_TABLE_PROJECTION = {
                DbContent.ChildCourseTable.COURSE_ID_COLUMN,
                DbContent.ChildCourseTable.CHILD_ID_COLUMN,
                DbContent.ChildCourseTable.CHILD_COURSE_COMPLETED_COLUMN
        };

        public static final int COURSE_CHILD_COURSE_ID = 0;
        public static final int COURSE_CHILD_CHILD_ID = 1;
        public static final int COURSE_CHILD_COURSE_COMPLETED = 2;

        public static final String[] COURSE_CHILD_JOIN_TABLE = {
                DbContent.CourseTable.COURSE_NAME_COLUMN,
                DbContent.CourseTable.COURSE_HOURS_COLUMN,
                DbContent.CourseTable.COURSE_COST_COLUMN,
                DbContent.CourseTable.COURSE_AVAILABLE_POSITIONS_COLUMN,
                DbContent.CourseTable.COURSE_START_DATE_COLUMN,
                DbContent.CourseTable.COURSE_END_DATE_COLUMN,
                DbContent.CourseTable.COURSE_START_AGE_COLUMN,
                DbContent.CourseTable.COURSE_END_AGE_COLUMN,
                DbContent.CourseTable.COURSE_LEVEL_COLUMN,
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
                DbContent.ChildTable.CHILD_MOTHER_JOB_COLUMN,
                DbContent.ChildCourseTable.COURSE_ID_COLUMN,
                DbContent.ChildCourseTable.CHILD_ID_COLUMN,
                DbContent.ChildCourseTable.CHILD_COURSE_COMPLETED_COLUMN,
                DbContent.ChildCourseTable._ID

        };

        public static final int COURSE_CHILD_JOIN_COURSE_NAME_COLUMN = 0;
        public static final int COURSE_CHILD_JOIN_COURSE_HOURS_COLUMN = 1;
        public static final int COURSE_CHILD_JOIN_COURSE_COST_COLUMN = 2;
        public static final int COURSE_CHILD_JOIN_COURSE_AVAILABLE_POSITIONS = 3;
        public static final int COURSE_CHILD_JOIN_COURSE_START_DATE_COLUMN = 4;
        public static final int COURSE_CHILD_JOIN_COURSE_END_DATE_COLUMN = 5;
        public static final int COURSE_CHILD_JOIN_COURSE_START_AGE_COLUMN = 6;
        public static final int COURSE_CHILD_JOIN_COURSE_END_AGE_COLUMN = 7;
        public static final int COURSE_CHILD_JOIN_COURSE_LEVEL_COLUMN = 8;
        public static final int COURSE_CHILD_JOIN_CHILD_NAME_COLUMN = 9;
        public static final int COURSE_CHILD_JOIN_CHILD_FATHER_NAME_COLUMN = 10;
        public static final int COURSE_CHILD_JOIN_CHILD_MOTHER_NAME_COLUMN = 11;
        public static final int COURSE_CHILD_JOIN_CHILD_FATHER_MOBILE_COLUMN = 12;
        public static final int COURSE_CHILD_JOIN_CHILD_MOTHER_MOBILE_COLUMN = 13;
        public static final int COURSE_CHILD_JOIN_CHILD_MOBILE_WHATSUP_COLUMN = 14;
        public static final int COURSE_CHILD_JOIN_CHILD_MOTHER_QUALIFICATION_COLUMN = 15;
        public static final int COURSE_CHILD_JOIN_CHILD_AGE_COLUMN = 16;
        public static final int COURSE_CHILD_JOIN_CHILD_EDUCATION_TYPE_COLUMN = 17;
        public static final int COURSE_CHILD_JOIN_CHILD_STUDY_YEAR_COLUMN = 18;
        public static final int COURSE_CHILD_JOIN_CHILD_GENDER_COLUMN = 19;
        public static final int COURSE_CHILD_JOIN_CHILD_FREE_TIME_COLUMN = 20;
        public static final int COURSE_CHILD_JOIN_CHILD_TRAITS_COLUMN = 21;
        public static final int COURSE_CHILD_JOIN_CHILD_HANDLING_COLUMN = 22;
        public static final int COURSE_CHILD_JOIN_CHILD_FATHER_JOB_COLUMN = 23;
        public static final int COURSE_CHILD_JOIN_CHILD_MOTHER_JOB_COLUMN = 24;
        public static final int COURSE_CHILD_JOIN_CHILD_BIRTH_ORDER_COLUMN = 25;
        public static final int COURSE_CHILD_JOIN_COURSE_ID_COLUMN = 26;
        public static final int COURSE_CHILD_JOIN_CHILD_ID_COLUMN = 27;
        public static final int COURSE_CHILD_JOIN_CHILD_COURSE_COMPLETED = 28;
        public static final int COURSE_CHILD_JOIN_CHILD_ID = 29;

        public static final String[] COURSE_INSTRUCTOR_JOIN_TABLE = {
                DbContent.CourseInstructorTable.COURSE_ID_COLUMN,
                DbContent.CourseInstructorTable.INSTRUCTOR_ID_COLUMN,
                DbContent.CourseInstructorTable.TOTAL_SALARY_COLUMN,
                DbContent.CourseInstructorTable.PAY_STATE_COLUMN,
                DbContent.InstructorTable.INSTRUCTOR_NAME_COLUMN,
                DbContent.InstructorTable.INSTRUCTOR_ADDRESS_COLUMN,
                DbContent.InstructorTable.INSTRUCTOR_MOBILE_COLUMN,
                DbContent.InstructorTable.INSTRUCTOR_QUALIFICATION_COLUMN,
                DbContent.InstructorTable.INSTRUCTOR_AGE_COLUMN,
                DbContent.InstructorTable.INSTRUCTOR_CV_COLUMN,
                DbContent.InstructorTable.INSTRUCTOR_GENDER_COLUMN,
                DbContent.CourseTable.COURSE_NAME_COLUMN,
                DbContent.CourseTable.COURSE_HOURS_COLUMN,
                DbContent.CourseTable.COURSE_COST_COLUMN,
                DbContent.CourseTable.COURSE_AVAILABLE_POSITIONS_COLUMN,
                DbContent.CourseTable.COURSE_START_DATE_COLUMN,
                DbContent.CourseTable.COURSE_END_DATE_COLUMN,
                DbContent.CourseTable.COURSE_START_AGE_COLUMN,
                DbContent.CourseTable.COURSE_END_AGE_COLUMN,
                DbContent.CourseTable.COURSE_LEVEL_COLUMN,
                DbContent.CourseTable.COURSE_SALARY_PER_CHILD
        };

        public static final int COURSE_INSTRUCTOR_JOIN_COURSE_ID = 0;
        public static final int COURSE_INSTRUCTOR_JOIN_INSTRUCTOR_ID = 1;
        public static final int COURSE_INSTRUCTOR_JOIN_TOTAL_SALARY = 2;
        public static final int COURSE_INSTRUCTOR_JOIN_PAY_STATE = 3;
        public static final int COURSE_INSTRUCTOR_JOIN_INSTRUCTOR_NAME = 4;
        public static final int COURSE_INSTRUCTOR_JOIN_INSTRUCTOR_ADDRESS = 5;
        public static final int COURSE_INSTRUCTOR_JOIN_INSTRUCTOR_MOBILE = 6;
        public static final int COURSE_INSTRUCTOR_JOIN_INSTRUCTOR_QUALIFICATION = 7;
        public static final int COURSE_INSTRUCTOR_JOIN_INSTRUCTOR_AGE = 8;
        public static final int COURSE_INSTRUCTOR_JOIN_INSTRUCTOR_CV = 9;
        public static final int COURSE_INSTRUCTOR_JOIN_INSTRUCTOR_GENDER = 10;
        public static final int COURSE_INSTRUCTOR_COURSE_NAME = 11;
        public static final int COURSE_INSTRUCTOR_COURSE_HOURS = 12;
        public static final int COURSE_INSTRUCTOR_COURSE_COST = 13;
        public static final int COURSE_INSTRUCTOR_COURSE_AVAILABLE_POSITIONS = 14;
        public static final int COURSE_INSTRUCTOR_COURSE_START_DATE = 15;
        public static final int COURSE_INSTRUCTOR_COURSE_END_DATE = 16;
        public static final int COURSE_INSTRUCTOR_COURSE_START_AGE = 17;
        public static final int COURSE_INSTRUCTOR_COURSE_END_AGE = 18;
        public static final int COURSE_INSTRUCTOR_COURSE_LEVEL = 19;
        public static final int COURSE_INSTRUCTOR_COURSE_SALARY_PER_CHILD = 20;

    }

    public static class UriDatabase{

        public static final Uri CHILD_TABLE_URI = DbContent.ChildTable.CONTENT_URI;
        public static final Uri EMPLOYEE_TABLE_URI = DbContent.EmployeeTable.CONTENT_URI;
        public static final Uri INSTRUCTOR_TABLE_URI = DbContent.InstructorTable.CONTENT_URI;
        public static final Uri COURSE_TABLE_URI = DbContent.CourseTable.CONTENT_URI;
        public static final Uri COURSE_CHILD_URI = DbContent.ChildCourseTable.CONTENT_URI;
        public static final Uri COURSE_INSTRUCTOR_URI = DbContent.CourseInstructorTable.CONTENT_URI;


        public static Uri getChildTableWithIdUri(int id){
            return CHILD_TABLE_URI.buildUpon().appendPath(String.valueOf(id)).build();
        }

        public static Uri getEmployeeTableWithIdUri(int id){
            return EMPLOYEE_TABLE_URI.buildUpon().appendPath(String.valueOf(id)).build();
        }

        public static Uri getInstructorTableWithIdUri(int id){
            return INSTRUCTOR_TABLE_URI.buildUpon().appendPath(String.valueOf(id)).build();
        }

        public static Uri getCourseTableWithIdUri(int id){
            return COURSE_TABLE_URI.buildUpon().appendPath(String.valueOf(id)).build();
        }

        public static Uri getCourseChildTableWithChildIdUri(int id){
            return COURSE_CHILD_URI.buildUpon().appendPath(DbContent.ChildTable.TABLE_NAME)
                    .appendPath(String.valueOf(id)).build();
        }

        public static Uri getCourseChildTableWithCourseIdUri(int id){
            return COURSE_CHILD_URI.buildUpon().appendPath(DbContent.CourseTable.TABLE_NAME)
                    .appendPath(String.valueOf(id)).build();
        }

        public static Uri getCourseInstructorTableWithCourseIdUri(int id){
            return COURSE_INSTRUCTOR_URI.buildUpon().appendPath(DbContent.CourseTable.TABLE_NAME)
                    .appendPath(String.valueOf(id)).build();
        }

        public static Uri getCourseInstructorTableWithInstructorIdUri(int id){
            return COURSE_INSTRUCTOR_URI.buildUpon().appendPath(DbContent.InstructorTable.TABLE_NAME)
                    .appendPath(String.valueOf(id)).build();
        }

    }

}
