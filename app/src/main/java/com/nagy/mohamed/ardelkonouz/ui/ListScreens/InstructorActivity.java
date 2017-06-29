package com.nagy.mohamed.ardelkonouz.ui.ListScreens;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;

import com.nagy.mohamed.ardelkonouz.R;

public class InstructorActivity extends AppCompatActivity {

    public static final String INSTRUCTOR_TAG = "child";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        if(savedInstanceState == null) {
            InstructorActivityFragment instructorActivityFragment = new InstructorActivityFragment();

            instructorActivityFragment.setEditTextView(findViewById(R.id.instructor_list_search_edit_view));

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment, instructorActivityFragment, INSTRUCTOR_TAG).commit();
        }else{

            InstructorActivityFragment instructorActivityFragment =(InstructorActivityFragment) getSupportFragmentManager()
                    .findFragmentByTag(INSTRUCTOR_TAG);

            instructorActivityFragment.setEditTextView(findViewById(R.id.instructor_list_search_edit_view));

        }
    }

}
