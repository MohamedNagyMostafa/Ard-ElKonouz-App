package com.nagy.mohamed.ardelkonouz.calenderFeature;

import android.app.TimePickerDialog;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.TimePicker;

/**
 * Created by mohamednagy on 8/13/2017.
 */

public class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    private CurrentTime currentTime;
    private View view;
    private int dateType;

    public void setCurrentDateWithTimeWithView(CurrentTime currentTime, View view, int dateType){
        this.currentTime = currentTime;
        this.view = view;
        this.dateType = dateType;
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int mint) {
        currentTime.onTimeSet(hour, mint , view, dateType);
    }
}
