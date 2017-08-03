package com.nagy.mohamed.ardelkonouz.ui.SalaryScreens;


/**
 * Created by mohamednagy on 8/2/2017.
 */

public class SalaryPair {

    private final Double COURSE_COST;
    private final Double COURSE_PERCENT_PER_CHILD;
    private Integer CHILD_NUMBERS;

    public SalaryPair(final Double COURSE_PERCENT_PER_CHILD,
                      final Double COURSE_COST, final Integer CHILD_NUMBERS){
        this.COURSE_PERCENT_PER_CHILD = COURSE_PERCENT_PER_CHILD;
        this.COURSE_COST = COURSE_COST;
        this.CHILD_NUMBERS = CHILD_NUMBERS;
    }

    public Double getTotalSalary(){
        return (CHILD_NUMBERS) * ((COURSE_PERCENT_PER_CHILD/100d) * COURSE_COST);
    }

}
