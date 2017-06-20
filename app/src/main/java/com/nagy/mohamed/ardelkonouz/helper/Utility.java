package com.nagy.mohamed.ardelkonouz.helper;

import android.content.Context;

import com.nagy.mohamed.ardelkonouz.R;

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
        StringBuilder stringBuilder = new StringBuilder(stageYear);
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
            stringBuilder.append("-").append(educationStageAsString);
        }

        return stringBuilder.toString();
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

    public static String decodeCharacteristicByInt(int characteristic, Context context){
        switch (characteristic){
            case Constants.GOOD_SPEAKER:
                return context.getString(R.string.good_speaker_characteristic);
            case Constants.SOCIAL:
                return context.getString(R.string.social_characteristic);
            case Constants.LEADING:
                return context.getString(R.string.leading_characteristic);
            case Constants.NEURAL:
                return context.getString(R.string.neural_characteristic);
            case Constants.COLLABORATOR:
                return context.getString(R.string.collaborator_characteristic);
            default:
                return context.getString(R.string.none);
        }
    }

    public static String decodeDealingProblemByInt(int dealingProblem, Context context){
        switch (dealingProblem){
            case Constants.ASK_FOR_HELP:
                return context.getString(R.string.quietly_asks_for_help);
            case Constants.WORRIES_ANGRY:
                return context.getString(R.string.worries_and_get_angry);
            case Constants.LEAVE_PROBLE:
                return context.getString(R.string.leaves_the_problem_totally);
            case Constants.TRIES_TO_SOLVE:
                return context.getString(R.string.tries_to_solve_the_problem);
            default:
                return context.getString(R.string.none);
        }
    }

    public static String decodeFreeTimeByInt(int freeTime, Context context){
        switch (freeTime){
            case Constants.DRAWING:
                return context.getString(R.string.drawing_coloring);
            case Constants.ELECTRONIC_GAMES:
                return context.getString(R.string.electronic_games);
            case Constants.WATCHING_TV:
                return context.getString(R.string.watching_tv);
            case Constants.HANDWORK:
                return context.getString(R.string.handwork);
            default:
                return context.getString(R.string.none);
        }
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
}
