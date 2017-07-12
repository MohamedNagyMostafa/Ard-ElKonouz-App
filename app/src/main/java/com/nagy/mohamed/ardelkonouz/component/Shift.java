package com.nagy.mohamed.ardelkonouz.component;

import android.support.annotation.Nullable;

/**
 * Created by mohamednagy on 7/4/2017.
 */
public class Shift {

    private Long startShiftDay;
    private Long endShiftDay;
    private Long sectionId;

    public Shift(Long startShiftDay, Long endShiftDay, @Nullable Long sectionId){
        this.startShiftDay = startShiftDay;
        this.endShiftDay = endShiftDay;
        this.sectionId = sectionId;
    }

    public Long getSectionId() {
        return sectionId;
    }

    public Long getEndShiftDay() {
        return endShiftDay;
    }


    public Long getStartShiftDay() {
        return startShiftDay;
    }
}
