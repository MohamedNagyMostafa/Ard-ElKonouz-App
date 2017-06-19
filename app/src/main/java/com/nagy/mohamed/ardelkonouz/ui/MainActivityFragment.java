package com.nagy.mohamed.ardelkonouz.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nagy.mohamed.ardelkonouz.R;
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

        return rootView;
    }

//    private void databaseTesting(){
//        // Test inserting.
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(DbContent.CourseTable.COURSE_LEVEL_COLUMN, 1);
//        contentValues.put(DbContent.CourseTable.COURSE_START_AGE_COLUMN, 1);
//        contentValues.put(DbContent.CourseTable.COURSE_END_AGE_COLUMN, 1);
//        contentValues.put(DbContent.CourseTable.COURSE_START_DATE_COLUMN, 1);
//        contentValues.put(DbContent.CourseTable.COURSE_END_DATE_COLUMN, 1);
//        contentValues.put(DbContent.CourseTable.COURSE_COMPLETE_COLUMN, 1);
//        contentValues.put(DbContent.CourseTable.COURSE_COST_COLUMN, 1);
//        contentValues.put(DbContent.CourseTable.COURSE_HOURS_COLUMN, 1);
//        contentValues.put(DbContent.CourseTable.COURSE_NAME_COLUMN, "android");
//        getContext().getContentResolver().insert(DbContent.CourseTable.CONTENT_URI, contentValues );
//        // Test Join
//        ContentValues contentValues2 = new ContentValues();
//        contentValues2.put(DbContent.InstructorTable.INSTRUCTOR_NAME_COLUMN, "Mohamed");
//        contentValues2.put(DbContent.InstructorTable.INSTRUCTOR_ADDRESS_COLUMN, "giza");
//        contentValues2.put(DbContent.InstructorTable.INSTRUCTOR_TOTAL_SALARY_COLUMN, 2);
//        contentValues2.put(DbContent.InstructorTable.INSTRUCTOR_MOBILE_COLUMN, 2);
//        contentValues2.put(DbContent.InstructorTable.INSTRUCTOR_QUALIFICATION_COLUMN, 2);
//        contentValues2.put(DbContent.InstructorTable.INSTRUCTOR_AGE_COLUMN, 2);
//        contentValues2.put(DbContent.InstructorTable.INSTRUCTOR_GENDER_COLUMN, 1);
//        contentValues2.put(DbContent.InstructorTable.INSTRUCTOR_HOURS_PER_DAY_COLUMN, 1);
//        contentValues2.put(DbContent.InstructorTable.INSTRUCTOR_ORIGINAL_HOURS_PER_DAY_COLUMN, 1);
//
//        getContext().getContentResolver().insert(DbContent.InstructorTable.CONTENT_URI, contentValues2 );
//        ContentValues contentValues1 = new ContentValues();
//        contentValues1.put(DbContent.CourseInstructorTable.COURSE_ID_COLUMN, 1);
//        contentValues1.put(DbContent.CourseInstructorTable.INSTRUCTOR_ID_COLUMN, 1);
//        getContext().getContentResolver().insert(DbContent.CourseInstructorTable.CONTENT_URI, contentValues1 );
//
//        Cursor cursor = getContext().getContentResolver().query(
//                DbContent.CourseInstructorTable.CONTENT_URI.buildUpon().appendPath(DbContent.CourseTable.TABLE_NAME).appendPath(String.valueOf(1)).build(),
//                null,
//                null,
//                null,
//                null);
//        Log.e("result",String.valueOf(cursor.getCount()));
//        for(String name: cursor.getColumnNames()){
//            Log.e("result",name);
//        }
//        if(cursor.getCount() != 0) {
//            cursor.moveToFirst();
//            Log.e("result", cursor.getString(cursor.getColumnIndex(DbContent.InstructorTable.INSTRUCTOR_NAME_COLUMN)));
//            Log.e("result", cursor.getString(cursor.getColumnIndex(DbContent.CourseTable.COURSE_NAME_COLUMN)));
//
//        }
//        cursor.close();
//    }
}
