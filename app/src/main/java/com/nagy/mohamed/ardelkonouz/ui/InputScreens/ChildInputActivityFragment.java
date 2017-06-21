package com.nagy.mohamed.ardelkonouz.ui.InputScreens;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nagy.mohamed.ardelkonouz.R;
import com.nagy.mohamed.ardelkonouz.helper.Constants;
import com.nagy.mohamed.ardelkonouz.helper.DoubleChoice;
import com.nagy.mohamed.ardelkonouz.helper.Utility;
import com.nagy.mohamed.ardelkonouz.offlineDatabase.DatabaseController;
import com.nagy.mohamed.ardelkonouz.offlineDatabase.DbContent;
import com.nagy.mohamed.ardelkonouz.ui.ProfileScreens.ConnectorsScreen.ChildCourseConnectorActivity;
import com.nagy.mohamed.ardelkonouz.ui.ViewHolder;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class ChildInputActivityFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_child_input, container, false);
        final ViewHolder.ChildInputScreenViewHolder childInputScreenViewHolder =
                new ViewHolder.ChildInputScreenViewHolder(rootView);
        final int CHILD_ID = getActivity().getIntent().getExtras().getInt(Constants.CHILD_ID_EXTRA);
        final String INPUT_TYPE = getActivity().getIntent().getExtras().getString(Constants.INPUT_TYPE_EXTRA);

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
        setChoiceListListeners(
                BIRTH_ORDER_LIST,
                GENDER_LIST,
                EDUCATION_TYPE_LIST,
                EDUCATION_STAGE_LIST,
                YEAR_LIST,
                CHARACTERISTIC_LIST,
                DEAL_PROBLEM_LIST,
                FREE_TIME_LIST
        );

        if(INPUT_TYPE != null) {
            childInputScreenViewHolder.SUBMIT_CHILD_BUTTON.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if(checkValidation(
                                    childInputScreenViewHolder.CHILD_NAME_EDIT_TEXT,
                                    childInputScreenViewHolder.CHILD_AGE_EDIT_TEXT,
                                    childInputScreenViewHolder.MOTHER_NAME_EDIT_TEXT,
                                    childInputScreenViewHolder.FATHER_NAME_EDIT_TEXT,
                                    childInputScreenViewHolder.MOTHER_JOB_EDIT_TEXT,
                                    childInputScreenViewHolder.FATHER_JOB_EDIT_TEXT,
                                    childInputScreenViewHolder.MOTHER_QUALIFICATION_EDIT_TEXT,
                                    childInputScreenViewHolder.WHATSAPP_EDIT_TEXT,
                                    childInputScreenViewHolder.MOTHER_MOBILE_EDIT_TEXT,
                                    childInputScreenViewHolder.FATHER_MOBILE_EDIT_TEXT
                            )){
                                if(getSelectionFromList(BIRTH_ORDER_LIST) == -1){
                                    Toast.makeText(getContext(), "Please choice your birth order", Toast.LENGTH_SHORT).show();
                                    return;
                                }else if(getSelectionFromList(GENDER_LIST) == -1){
                                    Toast.makeText(getContext(), "Please choice your gender", Toast.LENGTH_SHORT).show();
                                    return;
                                }else if(getSelectionFromList(EDUCATION_STAGE_LIST) == -1){
                                    Toast.makeText(getContext(), "Please choice your education stage", Toast.LENGTH_SHORT).show();
                                    return;
                                }else if(getSelectionFromList(EDUCATION_TYPE_LIST) == -1){
                                    Toast.makeText(getContext(), "Please choice your education type", Toast.LENGTH_SHORT).show();
                                    return;
                                }else if(getSelectionFromList(YEAR_LIST) == -1){
                                    Toast.makeText(getContext(), "Please choice your current education year", Toast.LENGTH_SHORT).show();
                                    return;
                                }else if(getSelectionFromList(CHARACTERISTIC_LIST) == -1 ||getSelectionFromList(DEAL_PROBLEM_LIST) == -1 ||
                                getSelectionFromList(FREE_TIME_LIST) == -1){
                                    Toast.makeText(getContext(), "Please complete your iq questions", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }else {
                                return;
                            }

                            ContentValues contentValues = new ContentValues();
                            contentValues.put(
                                    DbContent.ChildTable.CHILD_NAME_COLUMN,
                                    childInputScreenViewHolder.CHILD_NAME_EDIT_TEXT.getText().toString()
                            );
                            contentValues.put(
                                    DbContent.ChildTable.CHILD_AGE_COLUMN,
                                    childInputScreenViewHolder.CHILD_AGE_EDIT_TEXT.getText().toString()
                            );
                            contentValues.put(
                                    DbContent.ChildTable.CHILD_BIRTH_ORDER_COLUMN,
                                    getSelectionFromList(BIRTH_ORDER_LIST)
                            );
                            contentValues.put(
                                    DbContent.ChildTable.CHILD_GENDER_COLUMN,
                                    getSelectionFromList(GENDER_LIST)
                            );
                            contentValues.put(
                                    DbContent.ChildTable.CHILD_MOTHER_NAME_COLUMN,
                                    childInputScreenViewHolder.MOTHER_NAME_EDIT_TEXT.getText().toString()
                            );
                            contentValues.put(
                                    DbContent.ChildTable.CHILD_MOTHER_MOBILE_COLUMN,
                                    childInputScreenViewHolder.MOTHER_MOBILE_EDIT_TEXT.getText().toString()
                            );
                            contentValues.put(
                                    DbContent.ChildTable.CHILD_MOTHER_JOB_COLUMN,
                                    childInputScreenViewHolder.MOTHER_JOB_EDIT_TEXT.getText().toString()
                            );
                            contentValues.put(
                                    DbContent.ChildTable.CHILD_MOTHER_QUALIFICATION_COLUMN,
                                    childInputScreenViewHolder.MOTHER_QUALIFICATION_EDIT_TEXT.getText().toString()
                            );
                            contentValues.put(
                                    DbContent.ChildTable.CHILD_FATHER_NAME_COLUMN,
                                    childInputScreenViewHolder.FATHER_NAME_EDIT_TEXT.getText().toString()
                            );
                            contentValues.put(
                                    DbContent.ChildTable.CHILD_FATHER_JOB_COLUMN,
                                    childInputScreenViewHolder.FATHER_JOB_EDIT_TEXT.getText().toString()
                            );
                            contentValues.put(
                                    DbContent.ChildTable.CHILD_FATHER_MOBILE_COLUMN,
                                    childInputScreenViewHolder.FATHER_MOBILE_EDIT_TEXT.getText().toString()
                            );
                            contentValues.put(
                                    DbContent.ChildTable.CHILD_MOBILE_WHATSUP_COLUMN,
                                    childInputScreenViewHolder.WHATSAPP_EDIT_TEXT.getText().toString()
                            );
                            contentValues.put(
                                    DbContent.ChildTable.CHILD_EDUCATION_TYPE_COLUMN,
                                    getSelectionFromList(EDUCATION_TYPE_LIST)
                            );
                            contentValues.put(
                                    DbContent.ChildTable.CHILD_STUDY_YEAR_COLUMN,
                                    Utility.encodeEducationStageByInt(
                                            getSelectionFromList(YEAR_LIST),
                                            getSelectionFromList(EDUCATION_STAGE_LIST),
                                            getContext()
                                    )
                            );
                            contentValues.put(
                                    DbContent.ChildTable.CHILD_TRAITS_COLUMN,
                                    getSelectionFromList(CHARACTERISTIC_LIST)
                            );
                            contentValues.put(
                                    DbContent.ChildTable.CHILD_FREE_TIME_COLUMN,
                                    getSelectionFromList(FREE_TIME_LIST)
                            );
                            contentValues.put(
                                    DbContent.ChildTable.CHILD_HANDLING_COLUMN,
                                    getSelectionFromList(DEAL_PROBLEM_LIST)
                            );

                            switch (INPUT_TYPE) {
                                case Constants.INPUT_ADD_EXTRA:
                                    getActivity().getContentResolver().insert(
                                            DatabaseController.UriDatabase.CHILD_TABLE_URI,
                                            contentValues
                                    );
                                    openCoursesSelectionWindow(INPUT_TYPE, CHILD_ID);
                                    break;
                                case Constants.INPUT_EDIT_EXTRA:
                                    getActivity().getContentResolver().update(
                                            DatabaseController.UriDatabase.getChildTableWithIdUri(CHILD_ID),
                                            contentValues,
                                            null,
                                            null
                                    );
                                    openCoursesSelectionWindow(INPUT_TYPE, CHILD_ID);
                                    break;
                            }

                        }
                    }
            );

            if (INPUT_TYPE.equals(Constants.INPUT_EDIT_EXTRA)) {
                // Set Current Data.
                Cursor cursor = getActivity().getContentResolver().query(
                        DatabaseController.UriDatabase.getChildTableWithIdUri(CHILD_ID),
                        DatabaseController.ProjectionDatabase.CHILD_PROJECTION,
                        null,
                        null,
                        null
                );

                if(cursor != null){
                    if(cursor.getCount() > 0){
                        cursor.moveToFirst();
                        final String CHILD_NAME =
                                cursor.getString(DatabaseController.ProjectionDatabase.CHILD_NAME);
                        final short CHILD_AGE =
                                cursor.getShort(DatabaseController.ProjectionDatabase.CHILD_AGE);
                        final short BIRTH_ORDER =
                                cursor.getShort(DatabaseController.ProjectionDatabase.CHILD_BIRTH_ORDER);
                        final short GENDER =
                                cursor.getShort(DatabaseController.ProjectionDatabase.CHILD_GENDER);
                        final String MOTHER_NAME =
                                cursor.getString(DatabaseController.ProjectionDatabase.CHILD_MOTHER_NAME);
                        final String MOTHER_MOBILE =
                                cursor.getString(DatabaseController.ProjectionDatabase.CHILD_MOTHER_MOBILE);
                        final String MOTHER_JOB =
                                cursor.getString(DatabaseController.ProjectionDatabase.CHILD_MOTHER_JOB);
                        final String MOTHER_QUALIFICATION =
                                cursor.getString(DatabaseController.ProjectionDatabase.CHILD_MOTHER_QUALIFICATION);
                        final String FATHER_NAME =
                                cursor.getString(DatabaseController.ProjectionDatabase.CHILD_FATHER_NAME);
                        final String FATHER_MOBILE =
                                cursor.getString(DatabaseController.ProjectionDatabase.CHILD_FATHER_MOBILE);
                        final String WHATS_APP =
                                cursor.getString(DatabaseController.ProjectionDatabase.CHILD_MOBILE_WHATSUP);
                        final String FATHER_JOB =
                                cursor.getString(DatabaseController.ProjectionDatabase.CHILD_FATHER_JOB);

                        final short EDUCATION_TYPE =
                                cursor.getShort(DatabaseController.ProjectionDatabase.CHILD_EDUCATION_TYPE);

                        final int EDUCATION_STAGE =
                                Utility.decodeEducationStageByString(
                                        cursor.getString(DatabaseController.ProjectionDatabase.CHILD_STUDY_YEAR),
                                        getContext()
                                );

                        final int EDUCATION_YEAR =
                                Utility.getYearCodeFromEducationStageString(
                                        cursor.getString(DatabaseController.ProjectionDatabase.CHILD_STUDY_YEAR)
                                );

                        final short CHARACTERISTIC =
                                cursor.getShort(DatabaseController.ProjectionDatabase.CHILD_TRAITS);
                        final short DEAL_PROBLEM =
                                cursor.getShort(DatabaseController.ProjectionDatabase.CHILD_HANDLING);
                        final short FREE_TIME =
                                cursor.getShort(DatabaseController.ProjectionDatabase.CHILD_FREE_TIME);

                        // set data
                        childInputScreenViewHolder.CHILD_NAME_EDIT_TEXT.setText(CHILD_NAME);
                        childInputScreenViewHolder.CHILD_AGE_EDIT_TEXT.setText(String.valueOf(CHILD_AGE));
                        childInputScreenViewHolder.FATHER_NAME_EDIT_TEXT.setText(FATHER_NAME);
                        childInputScreenViewHolder.FATHER_JOB_EDIT_TEXT.setText(FATHER_JOB);
                        childInputScreenViewHolder.FATHER_MOBILE_EDIT_TEXT.setText(FATHER_MOBILE);
                        childInputScreenViewHolder.MOTHER_JOB_EDIT_TEXT.setText(MOTHER_JOB);
                        childInputScreenViewHolder.MOTHER_NAME_EDIT_TEXT.setText(MOTHER_NAME);
                        childInputScreenViewHolder.MOTHER_MOBILE_EDIT_TEXT.setText(MOTHER_MOBILE);
                        childInputScreenViewHolder.MOTHER_QUALIFICATION_EDIT_TEXT.setText(MOTHER_QUALIFICATION);
                        childInputScreenViewHolder.WHATSAPP_EDIT_TEXT.setText(WHATS_APP);
                        // set choices
                        Utility.selectionProcess(BIRTH_ORDER, BIRTH_ORDER_LIST);
                        Utility.selectionProcess(GENDER, GENDER_LIST);
                        Utility.selectionProcess(EDUCATION_TYPE, EDUCATION_TYPE_LIST);
                        Utility.selectionProcess(EDUCATION_STAGE, EDUCATION_STAGE_LIST);
                        Utility.selectionProcess(EDUCATION_YEAR, YEAR_LIST);
                        Utility.selectionProcess(CHARACTERISTIC, CHARACTERISTIC_LIST);
                        Utility.selectionProcess(FREE_TIME, FREE_TIME_LIST);
                        Utility.selectionProcess(DEAL_PROBLEM, DEAL_PROBLEM_LIST);

                    }

                    cursor.close();
                }
            }
        }

        return rootView;

    }

    @SafeVarargs
    private final void setChoiceListListeners(final ArrayList<DoubleChoice>... arrayLists){
        for(final ArrayList<DoubleChoice> list : arrayLists){
            for(int i = 0 ; i < list.size(); i++){
                final int INDEX = i;
                list.get(i).getTextView().setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(!list.get(INDEX).isSelected())
                                    Utility.selectionProcess(INDEX, list);
                            }
                        }
                );
            }
        }
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

    private int getSelectionFromList(ArrayList<DoubleChoice> arrayList){
        for(int i = 0 ; i < arrayList.size() ; i++){
            if(arrayList.get(i).isSelected())
                return i;
        }
        return -1;
    }

    private void openCoursesSelectionWindow(String inputType, int childId){
        Intent coursesSelectionWindow = new Intent(getContext(), ChildCourseConnectorActivity.class);
        coursesSelectionWindow.putExtra(Constants.CHILD_ID_EXTRA, childId);
        coursesSelectionWindow.putExtra(Constants.INPUT_TYPE_EXTRA, inputType);
        startActivity(coursesSelectionWindow);
    }

    private boolean checkValidation(
            EditText... editTexts){
        boolean validation = true;
        // check IQ questions section.
        for(EditText editText : editTexts){
            if(editText.length() == 0) {
                validation = false;
                editText.setError("This Field Can Not Be Empty");
            }

        }

        return validation;
    }

}
