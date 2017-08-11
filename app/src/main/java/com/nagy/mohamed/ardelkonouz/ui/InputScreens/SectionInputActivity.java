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
import com.nagy.mohamed.ardelkonouz.ui.ProfileScreens.CourseProfileActivity;
import com.nagy.mohamed.ardelkonouz.ui.ProfileScreens.SectionProfileActivity;

public class SectionInputActivity extends AppCompatActivity {

    private Long courseId = null;
    private Long sectionId = null;
    private String inputType = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section_input);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_keyboard_arrow_left_black_36dp);
        upArrow.setTint(Color.WHITE);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        courseId = getIntent().getExtras().getLong(Constants.COURSE_ID_EXTRA);
        sectionId = getIntent().getExtras().getLong(Constants.SECTION_ID_EXTRA);
        inputType = getIntent().getExtras().getString(Constants.INPUT_TYPE_EXTRA);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                openProfileScreen();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void openProfileScreen(){
        if(inputType.equals(Constants.INPUT_ADD_EXTRA)){
            Intent profileScreen = new Intent(this, CourseProfileActivity.class);
            profileScreen.putExtra(Constants.COURSE_ID_EXTRA, courseId);
            startActivity(profileScreen);
            NavUtils.navigateUpTo(this, profileScreen);
        }else{
            Intent profileScreen = new Intent(this, SectionProfileActivity.class);
            profileScreen.putExtra(Constants.COURSE_ID_EXTRA, courseId);
            profileScreen.putExtra(Constants.SECTION_ID_EXTRA, sectionId);
            startActivity(profileScreen);
            NavUtils.navigateUpTo(this, profileScreen);
        }
    }
}
