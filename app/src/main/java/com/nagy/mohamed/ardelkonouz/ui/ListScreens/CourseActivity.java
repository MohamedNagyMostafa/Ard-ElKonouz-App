package com.nagy.mohamed.ardelkonouz.ui.ListScreens;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;

import com.nagy.mohamed.ardelkonouz.R;

public class CourseActivity extends AppCompatActivity {

    public static final String COURSE_TAG = "course";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_keyboard_arrow_left_black_36dp);
        upArrow.setTint(Color.WHITE);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        if(savedInstanceState == null) {

            CourseActivityFragment courseActivityFragment = new CourseActivityFragment();

            courseActivityFragment.setEditTextView(findViewById(R.id.course_list_search_edit_view));

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment, courseActivityFragment, COURSE_TAG).commit();
        }else{

            CourseActivityFragment courseActivityFragment =(CourseActivityFragment) getSupportFragmentManager()
                    .findFragmentByTag(COURSE_TAG);

            courseActivityFragment.setEditTextView(findViewById(R.id.course_list_search_edit_view));

        }
    }

}
