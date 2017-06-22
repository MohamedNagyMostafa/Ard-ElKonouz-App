package com.nagy.mohamed.ardelkonouz.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nagy.mohamed.ardelkonouz.R;
import com.nagy.mohamed.ardelkonouz.calenderFeature.CurrentDateWithTime;
import com.nagy.mohamed.ardelkonouz.calenderFeature.DatePickerFragment;
import com.nagy.mohamed.ardelkonouz.calenderFeature.TimePickerFragment;
import com.nagy.mohamed.ardelkonouz.ui.ListScreens.ChildActivity;
import com.nagy.mohamed.ardelkonouz.ui.ListScreens.CourseActivity;
import com.nagy.mohamed.ardelkonouz.ui.ListScreens.EmployeeActivity;
import com.nagy.mohamed.ardelkonouz.ui.ListScreens.InstructorActivity;
import com.nagy.mohamed.ardelkonouz.ui.SalaryScreens.SalaryActivity;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment
        implements CurrentDateWithTime{


    private ViewHolder.MainScreenViewHolder mainScreenViewHolder;
    private Calendar calendar;

    private View.OnClickListener courseListClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent courseListScreen = new Intent(getActivity(), CourseActivity.class);
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        databaseTesting();
        calendar = Calendar.getInstance();

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

        DatePickerFragment datePickerFragment = new DatePickerFragment();
        TimePickerFragment timePickerFragment = new TimePickerFragment();
        datePickerFragment.setCurrentDateWithTime(this);
        timePickerFragment.setCurrentDateWithTime(this);
        datePickerFragment.show(getFragmentManager(),"d");
        timePickerFragment.show(getFragmentManager(),"d");


        return rootView;
    }

    @Override
    public void onTimeSet(int hour, int mint) {
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, mint);
    }

    @Override
    public void onDateSet(int year, int month, int day) {
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.DAY_OF_WEEK, day);
        calendar.set(Calendar.MONTH, month);
        DateFormat dateFormat = DateFormat.getDateInstance();
        Date date = new Date();
        Date date1 = new Date();
        date.setTime(calendar.getTimeInMillis());
        Log.e(DateFormat.getDateTimeInstance().format(date), String.valueOf(calendar.getTimeInMillis()));
        Calendar calendar1 = Calendar.getInstance();
        date1.setTime(calendar1.getTimeInMillis());
        Log.e(DateFormat.getDateTimeInstance().format(date1), String.valueOf(calendar1.getTimeInMillis()));

        Log.e("time now diff",String .valueOf(calendar.getTimeInMillis() - calendar1.getTimeInMillis()));

    }

}
