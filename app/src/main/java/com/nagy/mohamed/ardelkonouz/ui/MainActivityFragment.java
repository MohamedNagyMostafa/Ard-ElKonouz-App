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

//    private v//d databaseTesting(){
//        // Test inserting.
//       //ontentValues contentValues = new ContentValues();
//        contentValues//ut(DbContent.CourseTable.COURSE_LEVEL_COLUMN, 1);
//        contentValues.put//bContent.CourseTable.COURSE_START_AGE_COLUMN, 1);
//        contentValues.p//(DbContent.CourseTable.COURSE_END_AGE_COLUMN, 1);
//        contentValues.put(//Content.CourseTable.COURSE_START_DATE_COLUMN, 1);
//        contentValues.pu//DbContent.CourseTable.COURSE_END_DATE_COLUMN, 1)COURSE_COMPLETE_COLUMNues.pu//DbContent.CourseTable.COURSE_COMPLETE_COLUMN, 1);
//        contentValue//put(DbContent.CourseTable.COURSE_COST_COLUMN, 1);
//        contentValues//ut(DbContent.CourseTable.COURSE_HOURS_COLUMN, 1);
//        contentValues.put(Db//ntent.CourseTable.COURSE_NAME_COLUMN, "android");DbContent.CourseTable.CONTENT_URItResolver().insert(//Content.CourseTable.C//TENT_URI, contentValues );
//        // Test Join
//        //ntentValcontentValues2.put(DbContent.InstructorTable.INSTRUCTOR_NAME_COLUMN, "Mohamed");
//        contentValues2.put(DbContent.InstructorTable.INSTRUCTOR_ADDRESS_COLUMN, "giza");
//        contentValues2.put(DbContent.InstructorTable.INSTRUCTOR_TOTAL_SALARY_COLUMN, 2);
//        contentValues2.put(DbContent.InstructorTable.INSTRUCTOR_MOBILE_COLUMN, 2);
//        contentValues2.put(DbContent.InstructorTable.INSTRUCTOR_QUALIFICATION_COLUMN, 2);
//        contentValues2.put(DbContent.InstructorTable.INSTRUCTOR_AGE_COLUMN, 2);
//        contentValues2.put(DbContent.InstructorTable.INSTRUCTOR_GENDER_COLUMN, 1);
//        contentValues2.put(DbContent.InstructorTable.INSTRUCTOR_HOURS_PER_DAY_COLUMN, 1);
//        contentValues2.put(DbContent.InstructorTable.INSTRUCTOR_ORIGINAL_HOURS_PER_DAY_COLUMN, 1);r//b//.INSTRUCTOR_ORIGINAL_HOURS_PER_DAY_COLUMN, 1);
//
//        getContext().getContentResolver().insert(DbCon//nt.InstructorTable.CONTENT_URI, contentValues2 );
//        //ntentValues contentValues1 = new ContentValues();
//        contentValues1.put(Db//ntent.CourseInstructorTable.COURSE_ID_COLUMN, 1);
//        contentValues1.put(DbCont//t.CourseInstructorTable.INSTRUCTOR_ID_COLUMN, 1);
//        getContext().getContentResolver().insert(DbContent.C//r//InstructorTable.CONTENT_URI, contentValues1 );
//
//        Curso//cursor = getContext().getContentResolver().query(
//                DbContent.CourseInstructorTable.CONTENT_URI.buildUpon().appendPath(DbContent.CourseTable//ABLE_NAME).appendPath(//ring.valueOf(1)).build//,
//                nu//,
//                nul//
//                null,
//                null);
//       //og.e("result",String.valueOf(cursor.getCount()));
///       for(String name: cursor.get//lumnNames(//{
//            Log.e("result",name);///        }
//        if(cursor.get//unt() != 0) {
//            cursor.moveToFirst();
//            Log.e("result", cursor.getString(cursor.getColumnIndex(D//ontent.InstructorTable.INSTRUCTOR_NAME_COLUMN)));
//            Log.e("result", cursor.getString(cursor.getColum//n//x(DbConten//CourseTable.COURSE_NAME_//LUMN)));
//
//        }
//        cursor.close();
//    }
}
