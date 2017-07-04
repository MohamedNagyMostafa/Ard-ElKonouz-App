package com.nagy.mohamed.ardelkonouz.component;

import android.support.annotation.Nullable;

/**
 * Created by mohamednagy on 7/4/2017.
 */
public class Shift {

    private Long startShiftDay;
    private Long endShiftDay;
    private Long shiftDaysNumber;
    private Long courseId;

    public Shift(Long startShiftDay, Long endShiftDay, @Nullable Long shiftDaysNumber, @Nullable Long courseId){
        this.startShiftDay = startShiftDay;
        this.endShiftDay = endShiftDay;
        this.shiftDaysNumber = shiftDaysNumber;
        this.courseId = courseId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public Long getEndShiftDay() {
        return endShiftDay;
    }

    public Long getShiftDaysNumber() {
        return shiftDaysNumber;
    }

    public Long getStartShiftDay() {
        return startShiftDay;
    }
}
