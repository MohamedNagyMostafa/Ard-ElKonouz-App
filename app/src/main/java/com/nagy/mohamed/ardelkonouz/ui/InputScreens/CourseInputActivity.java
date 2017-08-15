package com.nagy.mohamed.ardelkonouz.ui.InputScreens;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.WindowManager;

import com.nagy.mohamed.ardelkonouz.R;
import com.nagy.mohamed.ardelkonouz.helper.Constants;
import com.nagy.mohamed.ardelkonouz.ui.ListScreens.CourseActivity;
import com.nagy.mohamed.ardelkonouz.ui.ProfileScreens.CourseProfileActivity;

public class CourseInputActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_input);
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
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                openScreen();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void openScreen(){
        final String INPUT_TYPE = getIntent().getExtras().getString(Constants.INPUT_TYPE_EXTRA);

        if(INPUT_TYPE.equals(Constants.INPUT_ADD_EXTRA)){
            Intent courseListScreen = new Intent(this, CourseActivity.class);
            startActivity(courseListScreen);
            NavUtils.navigateUpTo(this, courseListScreen);
        }else{
            final Long COURSE_ID = getIntent().getExtras().getLong(Constants.COURSE_ID_EXTRA);

            Intent courseProfileScreen = new Intent(this, CourseProfileActivity.class);
            courseProfileScreen.putExtra(Constants.COURSE_ID_EXTRA, COURSE_ID);
            startActivity(courseProfileScreen);
            NavUtils.navigateUpTo(this, courseProfileScreen);
        }
    }

    @Override
    public void onBackPressed() {
        openScreen();
    }
}
