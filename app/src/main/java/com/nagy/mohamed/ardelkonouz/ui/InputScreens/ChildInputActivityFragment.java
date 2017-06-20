package com.nagy.mohamed.ardelkonouz.ui.InputScreens;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nagy.mohamed.ardelkonouz.R;
import com.nagy.mohamed.ardelkonouz.helper.Constants;
import com.nagy.mohamed.ardelkonouz.helper.DoubleChoice;
import com.nagy.mohamed.ardelkonouz.helper.Utility;
import com.nagy.mohamed.ardelkonouz.ui.ViewHolder;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class ChildInputActivityFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_child_input, container, false);
        final ViewHolder.ChildInputScreenViewHolder childInputScreenViewHolder =
                new ViewHolder.ChildInputScreenViewHolder(rootView);
        final int CHILD_ID = getActivity().getIntent().getExtras().getInt(Constants.CHILD_ID_EXTRA);
        final int INPUT_TYPE = getActivity().getIntent().getExtras().getInt(Constants.INPUT_TYPE_EXTRA);

        // Set Choices process.
        final ArrayList<DoubleChoice> BIRTH_ORDER_LIST =
                setChoiceBirthOrderItems(childInputScreenViewHolder);
        final ArrayList<DoubleChoice> GENDER_LIST =
                setChoiceGenderItems(childInputScreenViewHolder);
        final ArrayList<DoubleChoice> EDUCATION_TYPE_LIST =
                setChoiceEducationTypeItems(childInputScreenViewHolder);
        final ArrayList<DoubleChoice> EDUCATION_STAGE_LIST =
                setChoiceEducationStageItems(childInputScreenViewHolder);
        final ArrayList<DoubleChoice> YEAR_LIST =
                setChoiceYearItems(childInputScreenViewHolder);
        final ArrayList<DoubleChoice> CHARACTERISTIC_LIST =
                setChoiceCharacteristicItems(childInputScreenViewHolder);
        final ArrayList<DoubleChoice> DEAL_PROBLEM_LIST =
                setChoiceDealProblemItems(childInputScreenViewHolder);
        final ArrayList<DoubleChoice> FREE_TIME_LIST =
                setChoiceFreeTimeItems(childInputScreenViewHolder);

        // Set Choices Listeners
        

    }


    private ArrayList<DoubleChoice> setChoiceFreeTimeItems(
            ViewHolder.ChildInputScreenViewHolder childInputScreenViewHolder){
        ArrayList<DoubleChoice> freeTimeList = new ArrayList<>();
        setChoiceTextViewSystem(
                freeTimeList,
                childInputScreenViewHolder.DRAWING_TEXT_VIEW,
                childInputScreenViewHolder.ELECTRONIC_TEXT_VIEW,
                childInputScreenViewHolder.WATCHING_TV_TEXT_VIEW,
                childInputScreenViewHolder.HANDWORK_TEXT_VIEW
        );
        setChoiceImageViewSystem(
                freeTimeList,
                childInputScreenViewHolder.DRAWING_IMAGE_VIEW,
                childInputScreenViewHolder.ELECTRONIC_IMAGE_VIEW,
                childInputScreenViewHolder.WATCHING_TV_IMAGE_VIEW,
                childInputScreenViewHolder.HANDWORK_IMAGE_VIEW
        );

        return freeTimeList;
    }


    private ArrayList<DoubleChoice> setChoiceDealProblemItems(
            ViewHolder.ChildInputScreenViewHolder childInputScreenViewHolder){
        ArrayList<DoubleChoice> dealProblemList = new ArrayList<>();
        setChoiceTextViewSystem(
                dealProblemList,
                childInputScreenViewHolder.ASKS_FOR_HELP_TEXT_VIEW,
                childInputScreenViewHolder.WORRIES_TEXT_VIEW,
                childInputScreenViewHolder.LEAVES_PROBLEMS_TEXT_VIEW,
                childInputScreenViewHolder.TRIES_SOLVE_TEXT_VIEW

        );
        setChoiceImageViewSystem(
                dealProblemList,
                childInputScreenViewHolder.ASKS_FOR_HELP_IMAGE_VIEW,
                childInputScreenViewHolder.WORRIES_IMAGE_VIEW,
                childInputScreenViewHolder.LEAVES_PROBLEMS_IMAGE_VIEW,
                childInputScreenViewHolder.TRIES_SOLVE_IMAGE_VIEW
        );

        return dealProblemList;
    }

    private ArrayList<DoubleChoice> setChoiceCharacteristicItems(
            ViewHolder.ChildInputScreenViewHolder childInputScreenViewHolder){
        ArrayList<DoubleChoice> characteristicList = new ArrayList<>();
        setChoiceTextViewSystem(
                characteristicList,
                childInputScreenViewHolder.GOOD_SPEAKER_TEXT_VIEW,
                childInputScreenViewHolder.SOCIAL_TEXT_VIEW,
                childInputScreenViewHolder.LEADING_TEXT_VIEW,
                childInputScreenViewHolder.NEURAL_TEXT_VIEW,
                childInputScreenViewHolder.COLLABORATOR_TEXT_VIEW
        );
        setChoiceImageViewSystem(
                characteristicList,
                childInputScreenViewHolder.GOOD_SPEAKER_IMAGE_VIEW,
                childInputScreenViewHolder.SOCIAL_IMAGE_VIEW,
                childInputScreenViewHolder.LEADING_IMAGE_VIEW,
                childInputScreenViewHolder.NEURAL_IMAGE_VIEW,
                childInputScreenViewHolder.COLLABORATOR_IMAGE_VIEW
        );

        return characteristicList;
    }

    private ArrayList<DoubleChoice> setChoiceYearItems(
            ViewHolder.ChildInputScreenViewHolder childInputScreenViewHolder){
        ArrayList<DoubleChoice> yearList = new ArrayList<>();
        setChoiceTextViewSystem(
                yearList,
                childInputScreenViewHolder.NONE_YEAR_TEXT_VIEW,
                childInputScreenViewHolder.FIRST_YEAR_TEXT_VIEW,
                childInputScreenViewHolder.SECOND_YEAR_TEXT_VIEW,
                childInputScreenViewHolder.THIRD_YEAR_TEXT_VIEW,
                childInputScreenViewHolder.FORTH_YEAR_TEXT_VIEW,
                childInputScreenViewHolder.FIFTH_YEAR_TEXT_VIEW,
                childInputScreenViewHolder.SIXTH_YEAR_TEXT_VIEW
        );
        setChoiceImageViewSystem(
                yearList,
                childInputScreenViewHolder.NONE_YEAR_IMAGE_VIEW,
                childInputScreenViewHolder.FIRST_YEAR_IMAGE_VIEW,
                childInputScreenViewHolder.SECOND_YEAR_IMAGE_VIEW,
                childInputScreenViewHolder.THIRD_YEAR_IMAGE_VIEW,
                childInputScreenViewHolder.FORTH_YEAR_IMAGE_VIEW,
                childInputScreenViewHolder.FIFTH_YEAR_IMAGE_VIEW,
                childInputScreenViewHolder.SIXTH_YEAR_IMAGE_VIEW
        );

        return yearList;
    }

    private ArrayList<DoubleChoice> setChoiceEducationStageItems(
            ViewHolder.ChildInputScreenViewHolder childInputScreenViewHolder){
        ArrayList<DoubleChoice> educationStageList = new ArrayList<>();
        setChoiceTextViewSystem(
                educationStageList,
                childInputScreenViewHolder.NONE_EDUCATION_TEXT_VIEW,
                childInputScreenViewHolder.PRIMARY_EDUCATION_TEXT_VIEW,
                childInputScreenViewHolder.PREPARATORY_TEXT_VIEW,
                childInputScreenViewHolder.SECONDARY_EDUCATION_TEXT_VIEW

        );
        setChoiceImageViewSystem(
                educationStageList,
                childInputScreenViewHolder.NONE_EDUCATION_IMAGE_VIEW,
                childInputScreenViewHolder.PRIMARY_EDUCATION_IMAGE_VIEW,
                childInputScreenViewHolder.PREPARATORY_IMAGE_VIEW,
                childInputScreenViewHolder.SECONDARY_EDUCATION_IMAGE_VIEW
        );

        return educationStageList;
    }

    private ArrayList<DoubleChoice> setChoiceEducationTypeItems(
            ViewHolder.ChildInputScreenViewHolder childInputScreenViewHolder){
        ArrayList<DoubleChoice> educationTypeList = new ArrayList<>();
        setChoiceTextViewSystem(
                educationTypeList,
                childInputScreenViewHolder.ARABIC_GOVERNMENTAL_TEXT_VIEW,
                childInputScreenViewHolder.ENGLISH_GOVERNMENTAL_TEXT_VIEW,
                childInputScreenViewHolder.ARABIC_PRIVATE_TEXT_VIEW,
                childInputScreenViewHolder.ENGLISH_PRIVATE_TEXT_VIEW
        );

        setChoiceImageViewSystem(
                educationTypeList,
                childInputScreenViewHolder.ARABIC_GOVERNMENTAL_IMAGE_VIEW,
                childInputScreenViewHolder.ENGLISH_GOVERNMENTAL_IMAGE_VIEW,
                childInputScreenViewHolder.ARABIC_PRIVATE_IMAGE_VIEW,
                childInputScreenViewHolder.ENGLISH_PRIVATE_IMAGE_VIEW
        );
        return educationTypeList;
    }

    private ArrayList<DoubleChoice> setChoiceGenderItems(
            ViewHolder.ChildInputScreenViewHolder childInputScreenViewHolder){
        ArrayList<DoubleChoice> genderArrayList = new ArrayList<>();

        setChoiceTextViewSystem(
                genderArrayList,
                childInputScreenViewHolder.MALE_GENDER_TEXT_VIEW,
                childInputScreenViewHolder.FEMALE_GENDER_TEXT_VIEW
        );

        setChoiceImageViewSystem(
                genderArrayList,
                childInputScreenViewHolder.MALE_GENDER_IMAGE_VIEW,
                childInputScreenViewHolder.FEMALE_GENDER_IMAGE_VIEW
        );

        return genderArrayList;
    }

    private ArrayList<DoubleChoice> setChoiceBirthOrderItems(
            ViewHolder.ChildInputScreenViewHolder childInputScreenViewHolder){
        ArrayList<DoubleChoice> birthOrderArrayList = new ArrayList<>();

        setChoiceTextViewSystem(
                birthOrderArrayList,
                childInputScreenViewHolder.FIRST_ORDER_TEXT_VIEW,
                childInputScreenViewHolder.MIDDLE_ORDER_TEXT_VIEW,
                childInputScreenViewHolder.LAST_ORDER_TEXT_VIEW
        );

        setChoiceImageViewSystem(
                birthOrderArrayList,
                childInputScreenViewHolder.FIRST_ORDER_IMAGE_VIEW,
                childInputScreenViewHolder.MIDDLE_ORDER_IMAGE_VIEW,
                childInputScreenViewHolder.LAST_ORDER_IMAGE_VIEW
        );

        return birthOrderArrayList;
    }

    private void setChoiceTextViewSystem(
            ArrayList<DoubleChoice> doubleChoiceArrayList,
            TextView... textViews){
        for(TextView textView :textViews){
            doubleChoiceArrayList.add(new DoubleChoice(getContext(), textView, false));
        }
    }

    private void setChoiceImageViewSystem(
            ArrayList<DoubleChoice> doubleChoiceArrayList,
            ImageView... textViews){

        for(int i = 0 ; i < textViews.length ; i++)
            doubleChoiceArrayList.get(i).setImageView(textViews[i]);
    }

}
