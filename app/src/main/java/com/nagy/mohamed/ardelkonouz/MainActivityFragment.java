package com.nagy.mohamed.ardelkonouz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nagy.mohamed.ardelkonouz.notification.SenderBroadCast;
import com.nagy.mohamed.ardelkonouz.ui.ListScreens.ChildActivity;
import com.nagy.mohamed.ardelkonouz.ui.ListScreens.CourseActivity;
import com.nagy.mohamed.ardelkonouz.ui.ListScreens.EmployeeActivity;
import com.nagy.mohamed.ardelkonouz.ui.ListScreens.InstructorActivity;
import com.nagy.mohamed.ardelkonouz.ui.ListScreens.ShiftListActivity;
import com.nagy.mohamed.ardelkonouz.ui.SalaryScreens.SalaryActivity;
import com.nagy.mohamed.ardelkonouz.ui.ViewHolder;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment{

    private ViewHolder.MainScreenViewHolder mainScreenViewHolder;

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

    private View.OnClickListener shiftListClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent shiftListScreen = new Intent(getActivity(), ShiftListActivity.class);
            startActivity(shiftListScreen);
        }
    };


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
        mainScreenViewHolder.SHIFT_LIST_LAYOUT.setOnClickListener(shiftListClickListener);

        SenderBroadCast senderBroadCast = new SenderBroadCast();
        senderBroadCast.active(this.getContext());

        return rootView;
    }


}
