package com.nagy.mohamed.ardelkonouz.calenderFeature;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by mohamednagy on 8/13/2017.
 */

public class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    private CurrentTime currentTime;
    private View view;
    private int dateType;
    private Calendar calendar;

    public void setCurrentDateWithTimeWithView(CurrentTime currentTime, View view, int dateType){
        this.currentTime = currentTime;
        this.view = view;
        this.dateType = dateType;
    }

    public void setTime(long time){
        calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if(calendar == null) {
            calendar = Calendar.getInstance();
        }
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int mint = calendar.get(Calendar.MINUTE);

        return new TimePickerDialog(getContext(), this, hour, mint, true);
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int mint) {
        currentTime.onTimeSet(hour, mint , view, dateType);
    }
}
