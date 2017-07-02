package com.nagy.mohamed.ardelkonouz.helper;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.nagy.mohamed.ardelkonouz.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by mohamednagy on 6/10/2017.
 */
public class Utility {
    // This Method Calculate The End Day.
    public static Long getEndDate(final Long COURSE_START_DATE, final String COURSE_SESSIONS_DAYS,
                            final Integer SESSIONS_NUMBER, final Integer COURSE_SHIFT_NUMBER){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(COURSE_START_DATE);
        calendar = getInitialCalendar(calendar);

        int startDay = getStartDay(calendar);
        long daysDiff = -1;
        Log.e("start day is ",getDayFromIndex(startDay));
        //2
        for(int i = (SESSIONS_NUMBER + COURSE_SHIFT_NUMBER) ; i > 0 ; i--){
            while(COURSE_SESSIONS_DAYS.charAt(startDay) != Constants.SELECTED) {
                startDay = (startDay + 1) % 7;

                Log.e("Day not select", getDayFromIndex(startDay));
                ++daysDiff;
            }
            startDay = (startDay + 1) % 7;
            Log.e("i = ", String.valueOf(i));
            ++daysDiff;
        }
        Log.e("calculation is end","done");
        return calendar.getTimeInMillis() +  (daysDiff * Constants.DAY_IN_MILS);
    }

    public static boolean isCourseEnd(final long COURSE_END_DATE){
        Calendar todayCalender = Calendar.getInstance();
        Calendar courseEndCalender = Calendar.getInstance();

        courseEndCalender.setTimeInMillis(COURSE_END_DATE);

        return todayCalender.after(courseEndCalender);
    }

    // get remains day for course ... if course is not ended.
    public static int getRemainDaysNumberWithNextDay(
            final Long COURSE_START_DATE,
            final String COURSE_SESSIONS_DAYS,
            final Integer COURSE_SESSIONS_NUMBER,
            StringBuilder nextSessionDay,
            final Long COURSE_SHIFT_END_DATE,
            final Long COURSE_END_DATE){

        Calendar startCalender = Calendar.getInstance();
        Calendar todayCalender = getInitialCalendar(Calendar.getInstance());

        startCalender.setTimeInMillis(COURSE_START_DATE);
        startCalender = getInitialCalendar(startCalender);

        int startDay = getStartDay(startCalender);
        int dayLeft = 0;
        long dayInMillsCounter = COURSE_START_DATE;

        while (startCalender.getTimeInMillis() < todayCalender.getTimeInMillis()){

            if(COURSE_SESSIONS_DAYS.charAt(startDay) == Constants.SELECTED ){
                ++dayLeft;
            }

            startDay = (startDay + 1) % 7;
            dayInMillsCounter += Constants.DAY_IN_MILS;

            startCalender.setTimeInMillis(dayInMillsCounter);
        }

        if(dayLeft == 0){
            // Course is not started yet
            nextSessionDay.append(getDayFromIndex(startDay));
        }else{
            // session is not completed
            if(dayLeft < COURSE_SESSIONS_NUMBER){
                // Check if today or not
                if(COURSE_SESSIONS_DAYS.charAt(startDay) == Constants.SELECTED &&
                        COURSE_SHIFT_END_DATE != COURSE_END_DATE){
                    nextSessionDay.append("Today");
                }else{
                    do {
                        startDay = (startDay + 1) % 7;
                    }while(COURSE_SESSIONS_DAYS.charAt(startDay) != Constants.SELECTED);
                    nextSessionDay.append(getDayFromIndex(startDay));
                }
            }
        }

        return COURSE_SESSIONS_NUMBER - dayLeft;
    }

    public static String getDaysAsString(final String COURSES_SESSIONS_DAYS){
        StringBuilder stringBuilder = new StringBuilder("");

        for(int i = 0 ; i < COURSES_SESSIONS_DAYS.length() ; i++){
            if(COURSES_SESSIONS_DAYS.charAt(i) == Constants.SELECTED){
                if(stringBuilder.length() > 1)
                    stringBuilder.append(" - ").append(getDayFromIndex(i));
                else
                    stringBuilder.append(getDayFromIndex(i));
            }
        }

        return stringBuilder.toString();
    }

    private static String getDayFromIndex(int index){
        switch (index){
            case Constants.SAT_DAY:
                return "SAT";
            case Constants.SUN_DAY:
                return "SUN";
            case Constants.MON_DAY:
                return "MON";
            case Constants.TUE_DAY:
                return "TUE";
            case Constants.WED_DAY:
                return "WED";
            case Constants.THU_DAY:
                return "THU";
            case Constants.FRI_DAY:
                return "FRI";
            default:
                return "";
        }
    }

    private static int getStartDay(Calendar calendar){
        switch (calendar.get(Calendar.DAY_OF_WEEK)){
            case Calendar.SATURDAY:
                return Constants.SAT_DAY;
            case Calendar.SUNDAY:
                return Constants.SUN_DAY;
            case Calendar.MONDAY:
                return Constants.MON_DAY;
            case Calendar.TUESDAY:
                return Constants.TUE_DAY;
            case Calendar.WEDNESDAY:
                return Constants.WED_DAY;
            case Calendar.THURSDAY:
                return Constants.THU_DAY;
            case Calendar.FRIDAY:
                return Constants.FRI_DAY;
            default:
                return 0;
        }
    }

    public static String decodeBirthOrderByInt(int birthOrder, Context context){
        switch (birthOrder){
            case Constants.FIRST_BIRTH:
                return context.getString(R.string.first_birth);
            case Constants.MIDDLE_BIRTH:
                return context.getString(R.string.middle_birth);
            case Constants.LAST_BIRTH:
                return context.getString(R.string.last_birth);
            case Constants.ALONE_ORDER:
                return context.getString(R.string.alone_birth);
            default:
                return null;
        }
    }

    public static Integer encodeBirthOrderByString(String birthOrder, Context context){
       if(birthOrder.equals(context.getString(R.string.first_birth))){
           return Constants.FIRST_BIRTH;
       }else if(birthOrder.equals(context.getString(R.string.middle_birth))){
           return Constants.MIDDLE_BIRTH;
       }else if(birthOrder.equals(context.getString(R.string.last_birth))){
           return Constants.LAST_BIRTH;
       }else if(birthOrder.equals(context.getString(R.string.alone_birth))){
           return Constants.ALONE_ORDER;
       }else{
           return null;
       }
    }

    public static String decodeGenderByInt(int gender, Context context){
        switch (gender){
            case Constants.MALE:
                return context.getString(R.string.gender_male);
            case Constants.FEMALE:
                return context.getString(R.string.gender_female);
            default:
                return null;
        }
    }

    public static Integer encodeGenderByString(String gender, Context context){
        if(gender.equals(context.getString(R.string.gender_male))){
            return Constants.MALE;
        }else if(gender.equals(context.getString(R.string.gender_female))){
            return Constants.FEMALE;
        }else{
            return null;
        }
    }

    public static String encodeEducationStageByInt(int stageYear, int educationStage, Context context){
        String stringBuilder = String.valueOf(stageYear);

        String educationStageAsString = null;
        switch (educationStage){
            case Constants.PRIMARY_EDUCATION_TYPE:
                educationStageAsString = context.getString(R.string.primary_education);
                break;
            case Constants.PREPARATORY_EDUCATION_TYPE:
                educationStageAsString = context.getString(R.string.preparatory_education);
                break;
            case Constants.SECONDARY_EDUCATION_TYPE:
                educationStageAsString = context.getString(R.string.secondary_education);
                break;
            case Constants.NONE_EDUCATION_TYPE:
                educationStageAsString = context.getString(R.string.none);
                break;
        }
        if(educationStageAsString != null) {
            stringBuilder += "-" + educationStageAsString;
        }

        return stringBuilder;
    }


    public static int getYearCodeFromEducationStageString(String educationStage){
        StringBuilder stringBuilder = new StringBuilder(educationStage);
        Log.e("output", String.valueOf(stringBuilder.charAt(0)));
        return Integer.valueOf(String.valueOf(stringBuilder.charAt(0)));

    }

    public static short decodeEducationStageByString(String educationStage, Context context){
        StringBuilder stringBuilder = new StringBuilder(educationStage);
        if(stringBuilder.substring(2).equals(context.getString(R.string.none)))
            return Constants.NONE_EDUCATION_TYPE;
        else if(stringBuilder.substring(2).equals(context.getString(R.string.primary_education)))
            return Constants.PRIMARY_EDUCATION_TYPE;
        else
            return Constants.SECONDARY_EDUCATION_TYPE;
    }

    public static String decodeEducationTypeByInt(int educationType, Context context){
        switch (educationType){
            case Constants.GOVERNMENTAL_ARABIC:
                return context.getString(R.string.arabic_governmental);
            case Constants.GOVERNMENTAL_ENGLISH:
                return context.getString(R.string.english_governmental);
            case Constants.SPECIAL_ARABIC:
                return context.getString(R.string.arabic_private);
            case Constants.SPECIAL_ENGLISH:
                return context.getString(R.string.arabic_private);
            default:
                return context.getString(R.string.none);
        }
    }

    public static String decodeCharacteristicByInt(String characteristicChoice, Context context){
        ArrayList<String> choices = new ArrayList<>();

        if(characteristicChoice.charAt(0) == Constants.SELECTED)
            choices.add(context.getString(R.string.good_speaker_characteristic));
        if(characteristicChoice.charAt(1) == Constants.SELECTED)
            choices.add(context.getString(R.string.social_characteristic));
        if(characteristicChoice.charAt(2) == Constants.SELECTED)
            choices.add(context.getString(R.string.leading_characteristic));
        if(characteristicChoice.charAt(3) == Constants.SELECTED)
            choices.add(context.getString(R.string.neural_characteristic));
        if(characteristicChoice.charAt(4) == Constants.SELECTED)
            choices.add(context.getString(R.string.collaborator_characteristic));

        String result = choices.get(0);
        for(int i = 1 ; i < choices.size() ;i++)
            result += "\n" + choices.get(i);

        return result;
    }

    public static String decodeDealingProblemByInt(String dealingProblemChoice, Context context){
        ArrayList<String> choices = new ArrayList<>();

        if(dealingProblemChoice.charAt(0) == Constants.SELECTED)
            choices.add(context.getString(R.string.quietly_asks_for_help));
        if(dealingProblemChoice.charAt(1) == Constants.SELECTED)
            choices.add(context.getString(R.string.worries_and_get_angry));
        if(dealingProblemChoice.charAt(2) == Constants.SELECTED)
            choices.add(context.getString(R.string.leaves_the_problem_totally));
        if(dealingProblemChoice.charAt(3) == Constants.SELECTED)
            choices.add(context.getString(R.string.tries_to_solve_the_problem));

        String result = choices.get(0);
        for(int i = 1 ; i < choices.size() ;i++)
            result += "\n" + choices.get(i);

        return result;
    }

    public static String decodeFreeTimeByInt(String freeTimeChoice, Context context){
        ArrayList<String> choices = new ArrayList<>();

        if(freeTimeChoice.charAt(0) == Constants.SELECTED)
            choices.add(context.getString(R.string.drawing_coloring));
        if(freeTimeChoice.charAt(1) == Constants.SELECTED)
            choices.add(context.getString(R.string.electronic_games));
        if(freeTimeChoice.charAt(2) == Constants.SELECTED)
            choices.add(context.getString(R.string.watching_tv));
        if(freeTimeChoice.charAt(3) == Constants.SELECTED)
            choices.add(context.getString(R.string.handwork));

        String result = choices.get(0);
        for(int i = 1 ; i < choices.size() ;i++)
            result += "\n" + choices.get(i);

        return result;
    }

    public static String decodeCourseStateByInt(int courseState, Context context){
        switch (courseState){
            case Constants.COURSE_COMPLETE:
                return context.getString(R.string.complete);
            case Constants.COURSE_INCOMPLETE:
                return context.getString(R.string.incomplete);
            default:
                return context.getString(R.string.empty_info);
        }
    }

    public static String decodeAgeRangeByInt(int firstAge, int lastAge){
        return String.valueOf(firstAge) + (" ~ ") + String.valueOf(lastAge);
    }

    public static void selectionProcess(
            int selectedIndex, ArrayList<DoubleChoice> doubleChoiceArrayList){
        for(int i = 0 ; i < doubleChoiceArrayList.size(); i++){
            if(i == selectedIndex){
                doubleChoiceArrayList.get(i).setChoiceSelect();
            }else{
                doubleChoiceArrayList.get(i).setChoiceNotSelect();
            }

        }
    }

    private static Calendar getInitialCalendar(Calendar calendar){
        calendar.set(Calendar.HOUR,0);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MILLISECOND,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        return calendar;
    }

    private static boolean isSelectedIQChoice(String iqAnswer, int index){
        return (iqAnswer.charAt(index) == Constants.SELECTED);
    }

    public static void doubleSelectionProcess(ArrayList<DoubleChoice> doubleChoiceArrayList,
                                              String result){
        for(int i = 0 ; i < doubleChoiceArrayList.size() ; i ++){
            if(result.charAt(i) == Constants.SELECTED)
                doubleChoiceArrayList.get(i).setChoiceSelect();
        }
    }

    public static void setChoiceTextViewSystem(
            ArrayList<DoubleChoice> doubleChoiceArrayList, Context context,
            TextView... textViews){
        for(TextView textView :textViews){
            doubleChoiceArrayList.add(new DoubleChoice(context, textView, false));
        }
    }

    public static void setChoiceImageViewSystem(
            ArrayList<DoubleChoice> doubleChoiceArrayList,
            ImageView... textViews){

        for(int i = 0 ; i < textViews.length ; i++)
            doubleChoiceArrayList.get(i).setImageView(textViews[i]);
    }

    public static long getCurrentDateAsMills(){
        Calendar calendar = Calendar.getInstance();
        return calendar.getTimeInMillis();
    }

    public static String getTimeFormat(long date){
        Calendar calendar = Calendar.getInstance();

        calendar.setTimeInMillis(date);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, dd MMM yyyy", Locale.ENGLISH);
        Date date1 = new Date();
        date1.setMonth(Calendar.MONTH);
        date1.setYear(Calendar.YEAR);
        date1.setHours(Calendar.HOUR);
        date1.setDate(Calendar.DATE);

        return simpleDateFormat.format(date);
    }
}
