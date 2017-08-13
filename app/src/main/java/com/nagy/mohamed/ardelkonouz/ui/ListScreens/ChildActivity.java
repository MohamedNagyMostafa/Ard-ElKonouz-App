package com.nagy.mohamed.ardelkonouz.ui.ListScreens;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;

import com.nagy.mohamed.ardelkonouz.R;

public class ChildActivity extends AppCompatActivity {

    public static final String CHILD_TAG = "child";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_keyboard_arrow_left_black_36dp);
        upArrow.setTint(Color.WHITE);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        if(savedInstanceState == null) {

            ChildActivityFragment childActivityFragment = new ChildActivityFragment();

            childActivityFragment.setEditTextView(findViewById(R.id.child_list_search_edit_view));

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment, childActivityFragment, CHILD_TAG).commit();
        }else{

            ChildActivityFragment childActivityFragment =(ChildActivityFragment) getSupportFragmentManager()
                    .findFragmentByTag(CHILD_TAG);

            childActivityFragment.setEditTextView(findViewById(R.id.child_list_search_edit_view));

        }

    }

}
