package com.nagy.mohamed.ardelkonouz.ui.ListScreens;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;

import com.nagy.mohamed.ardelkonouz.R;

public class CourseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        if(savedInstanceState == null) {

            CourseActivityFragment courseActivityFragment = new CourseActivityFragment();

            courseActivityFragment.setEditTextView(findViewById(R.id.course_list_search_edit_view));

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment, courseActivityFragment, "").commit();
        }
    }

}
