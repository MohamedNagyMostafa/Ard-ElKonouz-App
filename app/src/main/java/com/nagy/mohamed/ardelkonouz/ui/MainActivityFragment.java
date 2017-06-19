package com.nagy.mohamed.ardelkonouz.ui;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nagy.mohamed.ardelkonouz.R;
import com.nagy.mohamed.ardelkonouz.offlineDatabase.DatabaseController;
import com.nagy.mohamed.ardelkonouz.offlineDatabase.DbContent;
import com.nagy.mohamed.ardelkonouz.ui.ListScreens.ChildActivity;
import com.nagy.mohamed.ardelkonouz.ui.ListScreens.CourseActivty;
import com.nagy.mohamed.ardelkonouz.ui.ListScreens.EmployeeActivity;
import com.nagy.mohamed.ardelkonouz.ui.ListScreens.InstructorActivity;
import com.nagy.mohamed.ardelkonouz.ui.SalaryScreens.SalaryActivity;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {


    private ViewHolder.MainScreenViewHolder mainScreenViewHolder;

    private View.OnClickListener courseListClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent courseListScreen = new Intent(getActivity(), CourseActivty.class);
            startActivity(courseListScreen);
        }
    };

    private View.OnClickListener employeeListClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent employeeListScreen = new Intent(getActivity(), EmployeeActivity.class);
            startActivity(employeeListScreen);
        }
    };


    private View.OnClickListener instructorListClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent instructorListScreen = new Intent(getActivity(), InstructorActivity.class);
            startActivity(instructorListScreen);
        }
    };

    private View.OnClickListener salaryListClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent salaryListScreen = new Intent(getActivity(), SalaryActivity.class);
            startActivity(salaryListScreen);
        }
    };


    private View.OnClickListener childListClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent childListScreen = new Intent(getActivity(), ChildActivity.class);
            startActivity(childListScreen);
        }
    };



    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView =  inflater.inflate(R.layout.fragment_main, container, false);

        mainScreenViewHolder = new ViewHolder.MainScreenViewHolder(rootView);

        mainScreenViewHolder.CHILD_LIST_LAYOUT.setOnClickListener(childListClickListener);
        mainScreenViewHolder.COURSES_LIST_LAYOUT.setOnClickListener(courseListClickListener);
        mainScreenViewHolder.EMPLOYEE_LIST_LAYOUT.setOnClickListener(employeeListClickListener);
        mainScreenViewHolder.INSTRUCTOR_LIST_LAYOUT.setOnClickListener(instructorListClickListener);
        mainScreenViewHolder.SALARY_LIST_LAYOUT.setOnClickListener(salaryListClickListener);

        databaseTesting();

        return rootView;
    }

    private void databaseTesting(){

        // Dummy Data Child Table
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbContent.ChildTable.CHILD_NAME_COLUMN, "mohamed");
        contentValues.put(DbContent.ChildTable.CHILD_FATHER_NAME_COLUMN, "Nagy");
        contentValues.put(DbContent.ChildTable.CHILD_MOTHER_NAME_COLUMN, "Awatef");
        contentValues.put(DbContent.ChildTable.CHILD_FATHER_MOBILE_COLUMN, 1123123);
        contentValues.put(DbContent.ChildTable.CHILD_MOTHER_MOBILE_COLUMN, 1123323);
        contentValues.put(DbContent.ChildTable.CHILD_MOBILE_WHATSUP_COLUMN, 232123);
        contentValues.put(DbContent.ChildTable.CHILD_MOTHER_QUALIFICATION_COLUMN, "B.S");
        contentValues.put(DbContent.ChildTable.CHILD_FATHER_JOB_COLUMN, "Employee");
        contentValues.put(DbContent.ChildTable.CHILD_MOTHER_JOB_COLUMN, "None");
        contentValues.put(DbContent.ChildTable.CHILD_AGE_COLUMN, 12);
        contentValues.put(DbContent.ChildTable.CHILD_EDUCATION_TYPE_COLUMN, 1);
        contentValues.put(DbContent.ChildTable.CHILD_STUDY_YEAR_COLUMN, 1);
        contentValues.put(DbContent.ChildTable.CHILD_GENDER_COLUMN, 1);
        contentValues.put(DbContent.ChildTable.CHILD_FREE_TIME_COLUMN, 1);
        contentValues.put(DbContent.ChildTable.CHILD_TRAITS_COLUMN, 1);
        contentValues.put(DbContent.ChildTable.CHILD_HANDLING_COLUMN, 1);
        contentValues.put(DbContent.ChildTable.CHILD_BIRTH_ORDER_COLUMN, 1);
        getActivity().getContentResolver().insert(DatabaseController.UriDatabase.CHILD_TABLE_URI,contentValues);

        // Dummy Data Course Table.
        ContentValues contentValues1 = new ContentValues();
        contentValues1.put(DbContent.CourseTable.COURSE_NAME_COLUMN, "Android");
        contentValues1.put(DbContent.CourseTable.COURSE_HOURS_COLUMN, 22);
        contentValues1.put(DbContent.CourseTable.COURSE_COST_COLUMN, 133);
        contentValues1.put(DbContent.CourseTable.COURSE_AVAILABLE_POSITIONS_COLUMN,2);
        contentValues1.put(DbContent.CourseTable.COURSE_START_AGE_COLUMN, 10);
        contentValues1.put(DbContent.CourseTable.COURSE_END_AGE_COLUMN, 15);
        contentValues1.put(DbContent.CourseTable.COURSE_START_DATE_COLUMN, 2016);
        contentValues1.put(DbContent.CourseTable.COURSE_END_DATE_COLUMN, 2017);
        contentValues1.put(DbContent.CourseTable.COURSE_LEVEL_COLUMN, 2);
        contentValues1.put(DbContent.CourseTable.COURSE_SALARY_PER_CHILD, 12);
        getActivity().getContentResolver().insert(DatabaseController.UriDatabase.COURSE_TABLE_URI,contentValues1);

        ContentValues contentValues3 = new ContentValues();
        contentValues3.put(DbContent.CourseTable.COURSE_NAME_COLUMN, "Java");
        contentValues3.put(DbContent.CourseTable.COURSE_HOURS_COLUMN, 22);
        contentValues3.put(DbContent.CourseTable.COURSE_COST_COLUMN, 133);
        contentValues3.put(DbContent.CourseTable.COURSE_AVAILABLE_POSITIONS_COLUMN,5);
        contentValues3.put(DbContent.CourseTable.COURSE_START_AGE_COLUMN, 10);
        contentValues3.put(DbContent.CourseTable.COURSE_END_AGE_COLUMN, 15);
        contentValues3.put(DbContent.CourseTable.COURSE_START_DATE_COLUMN, 2016);
        contentValues3.put(DbContent.CourseTable.COURSE_END_DATE_COLUMN, 2017);
        contentValues3.put(DbContent.CourseTable.COURSE_LEVEL_COLUMN, 2);
        contentValues3.put(DbContent.CourseTable.COURSE_SALARY_PER_CHILD, 12);
        getActivity().getContentResolver().insert(DatabaseController.UriDatabase.COURSE_TABLE_URI,contentValues3);

        ContentValues contentValues6 = new ContentValues();
        contentValues6.put(DbContent.CourseTable.COURSE_NAME_COLUMN, "VS");
        contentValues6.put(DbContent.CourseTable.COURSE_HOURS_COLUMN, 22);
        contentValues6.put(DbContent.CourseTable.COURSE_COST_COLUMN, 133);
        contentValues6.put(DbContent.CourseTable.COURSE_AVAILABLE_POSITIONS_COLUMN,4);
        contentValues6.put(DbContent.CourseTable.COURSE_START_AGE_COLUMN, 10);
        contentValues6.put(DbContent.CourseTable.COURSE_END_AGE_COLUMN, 15);
        contentValues6.put(DbContent.CourseTable.COURSE_START_DATE_COLUMN, 2016);
        contentValues6.put(DbContent.CourseTable.COURSE_END_DATE_COLUMN, 2017);
        contentValues6.put(DbContent.CourseTable.COURSE_LEVEL_COLUMN, 2);
        contentValues6.put(DbContent.CourseTable.COURSE_SALARY_PER_CHILD, 12);
        getActivity().getContentResolver().insert(DatabaseController.UriDatabase.COURSE_TABLE_URI,contentValues6);

        // set Dummy Join CourseChild Table.
        ContentValues contentValues2 = new ContentValues();
        contentValues2.put(DbContent.ChildCourseTable.CHILD_ID_COLUMN, 1);
        contentValues2.put(DbContent.ChildCourseTable.COURSE_ID_COLUMN, 1);
        contentValues2.put(DbContent.ChildCourseTable.CHILD_COURSE_COMPLETED_COLUMN, 0);
        contentValues2.put(DbContent.ChildCourseTable._ID,1);

        getActivity().getContentResolver().insert(DatabaseController.UriDatabase.COURSE_CHILD_URI,contentValues2);
        ContentValues contentValues4 = new ContentValues();
        contentValues4.put(DbContent.ChildCourseTable.CHILD_ID_COLUMN, 1);
        contentValues4.put(DbContent.ChildCourseTable.COURSE_ID_COLUMN, 2);
        contentValues4.put(DbContent.ChildCourseTable.CHILD_COURSE_COMPLETED_COLUMN, 0);

        getActivity().getContentResolver().insert(DatabaseController.UriDatabase.COURSE_CHILD_URI,contentValues4);

        ContentValues contentValues5 = new ContentValues();
        contentValues5.put(DbContent.ChildCourseTable.CHILD_ID_COLUMN, 1);
        contentValues5.put(DbContent.ChildCourseTable.COURSE_ID_COLUMN, 3);
        contentValues5.put(DbContent.ChildCourseTable.CHILD_COURSE_COMPLETED_COLUMN, 0);

        getActivity().getContentResolver().insert(DatabaseController.UriDatabase.COURSE_CHILD_URI,contentValues5);


    }
}
