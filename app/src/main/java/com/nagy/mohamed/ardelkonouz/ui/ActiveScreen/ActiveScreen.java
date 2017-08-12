package com.nagy.mohamed.ardelkonouz.ui.ActiveScreen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.nagy.mohamed.ardelkonouz.R;
import com.nagy.mohamed.ardelkonouz.internalDatabase.SharedReferenceActiveCode;
import com.nagy.mohamed.ardelkonouz.ui.ViewHolder;
import com.nagy.mohamed.ardelkonouz.ui.mainScreen.MainActivity;

public class ActiveScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_active_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        final SharedReferenceActiveCode sharedReferenceActiveCode =
                new SharedReferenceActiveCode(this);
        setSupportActionBar(toolbar);
        setToolbarSettings();
        
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        final ViewHolder.ActiveScreenViewHolder activeScreenViewHolder =
                new ViewHolder.ActiveScreenViewHolder(this);

        activeScreenViewHolder.ACTIVE_BUTTON.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(!activeScreenViewHolder.ACTIVE_EDIT_TEXT
                                .getText().toString().isEmpty()){
                            String code = activeScreenViewHolder.ACTIVE_EDIT_TEXT.getText().toString();

                            if(code.equals(sharedReferenceActiveCode.getData())){
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
        activeScreenViewHolder.NEW_ACTIVE_CODE_BUTTON.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(!activeScreenViewHolder.ACTIVE_EDIT_TEXT
                                .getText().toString().isEmpty()) {
                            String code = activeScreenViewHolder.ACTIVE_EDIT_TEXT.getText().toString();

                            sharedReferenceActiveCode.setData(code);
                            Toast.makeText(getBaseContext(),
                                    "Your new active code is saved",
                                    Toast.LENGTH_LONG
                            ).show();

                        }else{
                            activeScreenViewHolder.ACTIVE_EDIT_TEXT.setError("Please enter the new active code");
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
