package com.nagy.mohamed.ardelkonouz.helper;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.nagy.mohamed.ardelkonouz.R;
import com.nagy.mohamed.ardelkonouz.component.Shift;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by mohamednagy on 6/10/2017.
 */
public class Utility {


    public static Long getNextFridayDate(){
        Long startDayDate = getCurrentDateAsMills();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(startDayDate);

        while(getStartDay(calendar) != Constants.FRI_DAY){
            startDayDate += Constants.DAY_IN_MILS;
            calendar.setTimeInMillis(startDayDate);
        }

        return startDayDate;
    }

    public static Integer getRemainDays(ArrayList<Shift> shifts,
                                        final String COURSE_SESSION_DAYS,
                                        final Long COURSE_START_DATE,
                                        final Long COURSE_END_DATE,
                                        final Integer COURSE_SESSIONS_NUMBER){

        final Long TODAY_DATE = getCurrentDateAsMills();
        Long diffTodayStart = Math.abs(COURSE_START_DATE - TODAY_DATE);
        Long diffTodayEnd = Math.abs(COURSE_END_DATE - TODAY_DATE);
        Integer remainSession;

        if(COURSE_START_DATE < TODAY_DATE) {
            if(COURSE_END_DATE > TODAY_DATE) {
                if (diffTodayEnd > diffTodayStart) {
                    int finishedSessionsNumber = 0;
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(COURSE_START_DATE);

                    int startDateIndex = getStartDay(calendar);
                    long courseStartDateCounter = COURSE_START_DATE;

                    do{
                        if(COURSE_SESSION_DAYS.charAt(startDateIndex) == Constants.SELECTED) {
                            if (shifts != null) {
                                boolean shiftFounded = false;
                                    for (Shift shift : shifts) {
                                        if ((shift.getStartShiftDay() <= courseStartDateCounter &&
                                                shift.getEndShiftDay() >= courseStartDateCounter)) {
                                            shiftFounded = true;
                                        }
                                    }
                                if(!shiftFounded){
                                    finishedSessionsNumber++;
                                }
                                }else{
                                    finishedSessionsNumber++;
                                }
                            }
                        courseStartDateCounter += Constants.DAY_IN_MILS;
                        startDateIndex = (startDateIndex + 1 ) % 7;

                    }while(courseStartDateCounter != TODAY_DATE);

                    remainSession = COURSE_SESSIONS_NUMBER - finishedSessionsNumber;

                } else {

                    remainSession = 0;
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(COURSE_END_DATE);

                    int endDateIndex = getStartDay(calendar);
                    long courseEndDateCounter = COURSE_END_DATE;

                    while (courseEndDateCounter != TODAY_DATE) {
                        if (COURSE_SESSION_DAYS.charAt(endDateIndex) == Constants.SELECTED) {
                            if(shifts != null) {
                                boolean shiftFounded = false;
                                for (Shift shift : shifts) {
                                    if ((shift.getStartShiftDay() <= courseEndDateCounter &&
                                            shift.getEndShiftDay() >= courseEndDateCounter)) {
                                        shiftFounded = true;
                                    }
                                }
                                if(!shiftFounded){
                                    remainSession++;
                                }
                            }else{
                                remainSession++;
                            }
                        }
                    endDateIndex = Math.abs(((endDateIndex + 1 ) % 7) - 6);
                        courseEndDateCounter += Constants.DAY_IN_MILS;
                    }

                }
            }else{
                remainSession = 0;
            }
        }else{
            remainSession = COURSE_SESSIONS_NUMBER;
        }

        return remainSession;

    }

    public static Long getNextSessionDay(ArrayList<Shift> shifts,
                                         final String COURSE_SESSION_DAYS,
                                         final Long COURSE_END_DATE,
                                         final Long COURSE_START_DATE){
        long nextSessionDay = 0;
        long todayDate = getCurrentDateAsMills();
        Calendar calendar = Calendar.getInstance();

        if(COURSE_START_DATE < todayDate){

            if(COURSE_END_DATE < todayDate){
                nextSessionDay = COURSE_END_DATE;
            }else{

                nextSessionDay =  todayDate;
                int counter = 0;

                calendar.setTimeInMillis(nextSessionDay);
                int dayIndex = getStartDay(calendar);
                //get near session day.
                while (COURSE_SESSION_DAYS.charAt(dayIndex) != Constants.SELECTED){
                    counter++;
                    dayIndex = (dayIndex + 1) % 7;
                }

                nextSessionDay = nextSessionDay + (Constants.DAY_IN_MILS * counter);
                counter = 0;

                if(shifts != null && shifts.size() > 0) {
                    // Check shift.
                    for (Shift shift : shifts) {
                        if (nextSessionDay >= shift.getStartShiftDay() && nextSessionDay <= shift.getEndShiftDay()) {
                            nextSessionDay = shift.getEndShiftDay() + Constants.DAY_IN_MILS;

                            calendar = Calendar.getInstance();
                            calendar.setTimeInMillis(nextSessionDay);

                            int startDayIndex = getStartDay(calendar);

                            while(COURSE_SESSION_DAYS.charAt(startDayIndex) != Constants.SELECTED){
                                startDayIndex = (startDayIndex + 1) % 7;
                                 nextSessionDay += Constants.DAY_IN_MILS;
                            }
                        }
                    }
                }
            }

        }else{
            nextSessionDay = COURSE_START_DATE;

            if(shifts != null && shifts.size() > 0) {
                for (Shift shift : shifts) {
                    if (nextSessionDay >= shift.getStartShiftDay() && nextSessionDay <= shift.getEndShiftDay()) {
                        nextSessionDay = shift.getEndShiftDay() + Constants.DAY_IN_MILS;

                        calendar.setTimeInMillis(nextSessionDay);

                        int startDayIndex = getStartDay(calendar);

                        while(COURSE_SESSION_DAYS.charAt(startDayIndex) != Constants.SELECTED){
                            startDayIndex = (startDayIndex + 1) % 7;
                            nextSessionDay += Constants.DAY_IN_MILS;
                        }

                    }
                }
            }
        }

        return nextSessionDay;
    }

    public static Long getEndDate(ArrayList<Shift> shifts,
                                  final String COURSE_SESSION_DAYS,
                                  final int COURSE_SESSIONS_NUMBER,
                                  final Long COURSE_START_DATE){

        long endDateCounter = COURSE_START_DATE;
        int sessionNumberCounter = 0;
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(endDateCounter);

        int endDateCounterDayIndex = getStartDay(calendar);

        /**
         * Cases 1- course has no shift.
         * Case  2- course has shift.
         */

        while(sessionNumberCounter < COURSE_SESSIONS_NUMBER){ // 0 1
            if(COURSE_SESSION_DAYS.charAt(endDateCounterDayIndex) == Constants.SELECTED){
                if(shifts.size() > 0) {
                    boolean shiftFounded = false;
                    for (Shift shift : shifts) {
                        if (endDateCounter >= shift.getStartShiftDay() && endDateCounter <= shift.getEndShiftDay()) {
                            shiftFounded = true;
                            while (endDateCounter < shift.getEndShiftDay()) {
                                endDateCounter += Constants.DAY_IN_MILS;
                                endDateCounterDayIndex = (endDateCounterDayIndex + 1) % 7;
                            }
                        }
                    }
                    if(!shiftFounded){
                        sessionNumberCounter++;
                    }
                }else{
                    sessionNumberCounter++;
                }

                endDateCounter += Constants.DAY_IN_MILS; //1
                endDateCounterDayIndex = (endDateCounterDayIndex + 1) % 7; //4

            }else {
                endDateCounter += Constants.DAY_IN_MILS;
                endDateCounterDayIndex = (endDateCounterDayIndex + 1) % 7;
            }
        }

        return endDateCounter - Constants.DAY_IN_MILS;
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

    public static int getStartDay(Calendar calendar){
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
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
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
