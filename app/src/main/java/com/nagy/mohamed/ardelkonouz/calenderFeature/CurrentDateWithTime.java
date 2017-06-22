package com.nagy.mohamed.ardelkonouz.calenderFeature;

import android.view.View;

/**
 * Created by mohamednagy on 6/22/2017.
 */
public interface CurrentDateWithTime {
    void onTimeSet(int year, int month, int day, int hour, int mint, View view);
    void onDateSet(int year, int month, int day, View view);
}
