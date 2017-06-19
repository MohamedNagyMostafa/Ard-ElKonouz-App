package com.nagy.mohamed.ardelkonouz.helper;

/**
 * Created by mohamednagy on 6/10/2017.
 */
public class Constants {

    public static enum EDUCATION{
        NONE_EDUCATION_TYPE(-1),
        PRIMARY_EDUCATION_TYPE(100000),
        PREPARATORY_EDUCATION_TYPE(100),
        SECONDARY_EDUCATION_TYPE(200),
        ACADEMIC_EDUCATION_TYPE(10000),
        SPECIAL_ENGLISH(12),SPECIAL_ARABIC(21),
        GOVERNMENTAL_ARABIC(22),GOVERNMENTAL_ENGLISH(122);


        final int EDUCATION_TYPE;
        EDUCATION(int educationType){
            this.EDUCATION_TYPE = educationType;
        }
    }

    // Qualification Type
    public static final int NONE_QUALIFICATION = -1;
    public static final int UNDER_BACHELOR_QUALIFICATION= 0;
    public static final int BACHELOR_QUALIFICATION = 1;
    public static final int MASTER_QUALIFICATION = 2;
    public static final int PHD_QUALIFICATION = 3;

    // GENDER
    public static final int MALE = 0;
    public static final int FEMALE = 1;

    // EXTRAS
    public static final String CHILD_ID_EXTRA = "child extra";
    public static final String EMPLOYEE_ID_EXTRA = "employee extra";
    public static final String COURSE_ID_EXTRA = "course extra";


    // Loaders Id
    public static final int LOADER_CHILD_LIST = 11;
    public static final int LOADER_EMPLOYEE_LIST = 10;
    public static final int LOADER_INSTRUCTOR_LIST = 111;
    public static final int LOADER_COURSE_LIST = 110;


}
