package com.nagy.mohamed.ardelkonouz.ui.ListScreens;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;

import com.nagy.mohamed.ardelkonouz.R;

public class EmployeeActivity extends AppCompatActivity {

    public static final String EMPLOYEE_TAG = "child";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        if(savedInstanceState == null) {

            EmployeeActivityFragment employeeActivityFragment = new EmployeeActivityFragment();

            employeeActivityFragment.setEditTextView(findViewById(R.id.employee_list_search_edit_view));

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment, employeeActivityFragment, EMPLOYEE_TAG).commit();
        }else{

            EmployeeActivityFragment employeeActivityFragment =(EmployeeActivityFragment) getSupportFragmentManager()
                    .findFragmentByTag(EMPLOYEE_TAG);

            employeeActivityFragment.setEditTextView(findViewById(R.id.employee_list_search_edit_view));

        }
    }

}
