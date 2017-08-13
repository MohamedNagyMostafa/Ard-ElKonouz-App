package com.nagy.mohamed.ardelkonouz.calenderFeature;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * Created by mohamednagy on 6/22/2017.
 */
public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener{

    private CurrentDate currentDate;
    private View view;
    private int dateType;
    private Calendar calendar;

    public void setCurrentDate(CurrentDate currentDate){
        this.currentDate = currentDate;
    }

    public void setCalendar(Long dateAsMills){
        calendar = Calendar.getInstance();
        calendar.setTimeInMillis(dateAsMills);
    }
    public void setView(View view){
        this.view = view;
    }

    public void setDateType(int dateType){
        this.dateType = dateType;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        if(calendar == null) {
            calendar = Calendar.getInstance();
        }
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);

    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        currentDate.onDateSet(year, month, day, view, dateType);
    }
}
