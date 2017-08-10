package com.nagy.mohamed.ardelkonouz.ui.ActiveScreen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;

import com.nagy.mohamed.ardelkonouz.R;
import com.nagy.mohamed.ardelkonouz.helper.Constants;
import com.nagy.mohamed.ardelkonouz.ui.ViewHolder;
import com.nagy.mohamed.ardelkonouz.ui.mainScreen.MainActivity;

public class ActiveScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_active_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setToolbarSettings();

        final ViewHolder.ActiveScreenViewHolder activeScreenViewHolder =
                new ViewHolder.ActiveScreenViewHolder(this);

        activeScreenViewHolder.ACTIVE_BUTTON.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(!activeScreenViewHolder.ACTIVE_EDIT_TEXT
                                .getText().toString().isEmpty()){
                            String code = activeScreenViewHolder.ACTIVE_EDIT_TEXT.getText().toString();

                            if(code.equals(Constants.ACTIVE_CODE)){
                                openMainScreen();
                            }else{
                                activeScreenViewHolder.ACTIVE_EDIT_TEXT.setError("you have entered wrong code");
                            }
                        }else{
                            activeScreenViewHolder.ACTIVE_EDIT_TEXT.setError("Please Enter Active Code");
                        }
                    }
                }
        );

    }

    private void setToolbarSettings(){
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void openMainScreen(){
        Intent mainActivityScreen = new Intent(this, MainActivity.class);
        startActivity(mainActivityScreen);
        finish();
    }


}
