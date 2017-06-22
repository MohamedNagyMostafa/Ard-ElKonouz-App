package com.nagy.mohamed.ardelkonouz.helper;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.nagy.mohamed.ardelkonouz.R;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by mohamednagy on 6/10/2017.
 */
public class Utility {

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
}
