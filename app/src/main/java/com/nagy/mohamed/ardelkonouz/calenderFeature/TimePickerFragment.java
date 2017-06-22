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
 * Created by mohamednagy on 6/22/2017.
 */
public class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener{

    private CurrentDateWithTime currentDateWithTime;
    private View view;
    private int year;
    private int month;
    private int day;

    public void setCurrentDateWithTime(CurrentDateWithTime currentDateWithTime){
        this.currentDateWithTime = currentDateWithTime;
    }

    public void setView(View view){
        this.view = view;
    }

    public void setDate(int year, int month, int day){
        this.day = day;
        this.month = month;
        this.year = year;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR);
        int mint = c.get(Calendar.MINUTE);

        // Create a new instance of DatePickerDialog and return it
        return new TimePickerDialog(getContext(), this, hour, mint, true);

    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int mint) {
        currentDateWithTime.onTimeSet(year, month, day, hour, mint, view);
    }
}
