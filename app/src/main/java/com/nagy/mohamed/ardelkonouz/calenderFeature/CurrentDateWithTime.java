package com.nagy.mohamed.ardelkonouz.calenderFeature;

/**
 * Created by mohamednagy on 6/22/2017.
 */
public interface CurrentDateWithTime {
    void onTimeSet(int hour, int mint);
    void onDateSet(int year, int month, int day);
}
