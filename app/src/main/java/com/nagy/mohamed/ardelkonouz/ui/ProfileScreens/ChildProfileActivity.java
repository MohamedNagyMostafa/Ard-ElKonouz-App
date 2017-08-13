package com.nagy.mohamed.ardelkonouz.ui.ProfileScreens;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;

import com.nagy.mohamed.ardelkonouz.R;
import com.nagy.mohamed.ardelkonouz.ui.mainScreen.MainActivity;

public class ChildProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_keyboard_arrow_left_black_36dp);
        upArrow.setTint(Color.WHITE);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        (findViewById(R.id.child_pf_main_icon_image_view)).setOnClickListener(
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

}
