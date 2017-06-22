package com.nagy.mohamed.ardelkonouz.calenderFeature;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * Created by mohamednagy on 6/22/2017.
 */
public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener{

    private CurrentDateWithTime currentDateWithTime;

    public void setCurrentDateWithTime(CurrentDateWithTime currentDateWithTime){
        this.currentDateWithTime = currentDateWithTime;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);

    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        currentDateWithTime.onDateSet(year, month, day);
    }
}
