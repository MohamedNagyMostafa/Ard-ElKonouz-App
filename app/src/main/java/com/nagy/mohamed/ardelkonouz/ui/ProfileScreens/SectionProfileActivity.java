package com.nagy.mohamed.ardelkonouz.ui.ProfileScreens;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.nagy.mohamed.ardelkonouz.R;
import com.nagy.mohamed.ardelkonouz.helper.Constants;
import com.nagy.mohamed.ardelkonouz.ui.mainScreen.MainActivity;

public class SectionProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_keyboard_arrow_left_black_36dp);
        upArrow.setTint(Color.WHITE);

        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        findViewById(R.id.section_pf_main_icon_image_view).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        openMainScreen();
                    }
                }
        );

    }

    private void openMainScreen(){
        Intent mainScreen = new Intent(this, MainActivity.class);
        startActivity(mainScreen);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                openCourseScreen();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void openCourseScreen(){
        final Long COURSE_ID = getIntent().getExtras().getLong(Constants.COURSE_ID_EXTRA);

        Intent courseScreen = new Intent(this, CourseProfileActivity.class);
        courseScreen.putExtra(Constants.COURSE_ID_EXTRA, COURSE_ID);
        startActivity(courseScreen);
        NavUtils.navigateUpTo(this, courseScreen);
    }

    @Override
    public void onBackPressed() {
        openCourseScreen();
    }
}
