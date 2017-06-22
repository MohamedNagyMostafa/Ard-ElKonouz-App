package com.nagy.mohamed.ardelkonouz.ui.InputScreens;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.nagy.mohamed.ardelkonouz.R;
import com.nagy.mohamed.ardelkonouz.helper.Constants;
import com.nagy.mohamed.ardelkonouz.helper.DoubleChoice;
import com.nagy.mohamed.ardelkonouz.helper.Utility;
import com.nagy.mohamed.ardelkonouz.offlineDatabase.DatabaseController;
import com.nagy.mohamed.ardelkonouz.offlineDatabase.DbContent;
import com.nagy.mohamed.ardelkonouz.ui.ProfileScreens.ConnectorsScreen.InstructorCourseConnectorActivity;
import com.nagy.mohamed.ardelkonouz.ui.ViewHolder;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class InstructorInputActivityFragment extends Fragment {
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_instructor_input, container, false);
        ViewHolder.InstructorInputScreenViewHolder instructorInputScreenViewHolder =
                new ViewHolder.InstructorInputScreenViewHolder(rootView);
        final String INPUT_TYPE = getActivity().getIntent().getExtras().getString(Constants.INPUT_TYPE_EXTRA);

        // set choice sys.
        ArrayList<DoubleChoice> GENDER_LIST =
                setChoiceSystemItems(instructorInputScreenViewHolder);

        // set choice listener.
        setChoiceGenderListener(GENDER_LIST);

        if(INPUT_TYPE.equals(Constants.INPUT_ADD_EXTRA)){
            setOptionsAsAddNewInstructor(
                    instructorInputScreenViewHolder,
                    GENDER_LIST
            );
        }else{
            setOptionsAsEditInstructor(
                    instructorInputScreenViewHolder,
                    GENDER_LIST
            );
        }

        return rootView;

    }

    private void setOptionsAsEditInstructor(
            final ViewHolder.InstructorInputScreenViewHolder instructorInputScreenViewHolder,
            final ArrayList<DoubleChoice> GENDER_LIST) {

        final long INSTRUCTOR_ID = getActivity().getIntent().getExtras().getLong(Constants.INSTRUCTOR_ID_EXTRA);

        Cursor cursor = getActivity().getContentResolver().query(
                DatabaseController.UriDatabase.getInstructorTableWithIdUri(INSTRUCTOR_ID),
                DatabaseController.ProjectionDatabase.INSTRUCTOR_PROJECTION,
                null,
                null,
                null
        );

        if(cursor != null){
            if(cursor.getCount() > 0){
                cursor.moveToFirst();
                instructorInputScreenViewHolder.INSTRUCTOR_NAME_EDIT_TEXT.setText(
                        cursor.getString(
                                DatabaseController.ProjectionDatabase.INSTRUCTOR_NAME
                        )
                );
                instructorInputScreenViewHolder.INSTRUCTOR_MOBILE_EDIT_TEXT.setText(
                        cursor.getString(
                                DatabaseController.ProjectionDatabase.INSTRUCTOR_MOBILE
                        )
                );
                instructorInputScreenViewHolder.INSTRUCTOR_AGE_EDIT_TEXT.setText(
                        String.valueOf(
                                cursor.getInt(
                                        DatabaseController.ProjectionDatabase.INSTRUCTOR_AGE
                                )
                        )
                );
                instructorInputScreenViewHolder.INSTRUCTOR_ADDRESS_EDIT_TEXT.setText(
                        cursor.getString(
                                DatabaseController.ProjectionDatabase.INSTRUCTOR_ADDRESS
                        )
                );
                instructorInputScreenViewHolder.INSTRUCTOR_QUALIFICATION_EDIT_TEXT.setText(
                        cursor.getString(
                                DatabaseController.ProjectionDatabase.INSTRUCTOR_QUALIFICATION
                        )
                );
                Utility.selectionProcess(
                        cursor.getInt(
                                DatabaseController.ProjectionDatabase.INSTRUCTOR_GENDER
                        ),
                        GENDER_LIST
                );

                instructorInputScreenViewHolder.INSTRUCTOR_SUBMIT_BUTTON.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(checkValidation(
                                        GENDER_LIST,
                                        instructorInputScreenViewHolder.INSTRUCTOR_ADDRESS_EDIT_TEXT,
                                        instructorInputScreenViewHolder.INSTRUCTOR_AGE_EDIT_TEXT,
                                        instructorInputScreenViewHolder.INSTRUCTOR_MOBILE_EDIT_TEXT,
                                        instructorInputScreenViewHolder.INSTRUCTOR_NAME_EDIT_TEXT,
                                        instructorInputScreenViewHolder.INSTRUCTOR_QUALIFICATION_EDIT_TEXT
                                )){
                                    getActivity().getContentResolver().update(
                                            DatabaseController.UriDatabase.getInstructorTableWithIdUri(INSTRUCTOR_ID),
                                            getDataInputs(
                                                    instructorInputScreenViewHolder,
                                                    GENDER_LIST
                                            ),
                                            null,
                                            null
                                    );

                                    openInstructorProfile(INSTRUCTOR_ID);
                                }
                            }
                        }
                );

            }
            cursor.close();
        }


    }

    private void setOptionsAsAddNewInstructor(
            final ViewHolder.InstructorInputScreenViewHolder instructorInputScreenViewHolder,
            final ArrayList<DoubleChoice> GENDER_LIST){

        instructorInputScreenViewHolder.INSTRUCTOR_SUBMIT_BUTTON.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(checkValidation(
                                GENDER_LIST,
                                instructorInputScreenViewHolder.INSTRUCTOR_ADDRESS_EDIT_TEXT,
                                instructorInputScreenViewHolder.INSTRUCTOR_AGE_EDIT_TEXT,
                                instructorInputScreenViewHolder.INSTRUCTOR_MOBILE_EDIT_TEXT,
                                instructorInputScreenViewHolder.INSTRUCTOR_NAME_EDIT_TEXT,
                                instructorInputScreenViewHolder.INSTRUCTOR_QUALIFICATION_EDIT_TEXT
                        )){

                            Uri instructorUri = getActivity().getContentResolver().insert(
                                    DatabaseController.UriDatabase.INSTRUCTOR_TABLE_URI,
                                    getDataInputs(instructorInputScreenViewHolder,GENDER_LIST)
                            );

                            final long INSTRUCTOR_ID = ContentUris.parseId(instructorUri);

                            openInstructorProfile(INSTRUCTOR_ID);
                        }
                    }
                }
        );
    }

    private void openInstructorProfile(final long INSTRUCTOR_ID){
        Intent instructorCourseConnectorScreen = new Intent(getContext(), InstructorCourseConnectorActivity.class);
        instructorCourseConnectorScreen.putExtra(Constants.INSTRUCTOR_ID_EXTRA, INSTRUCTOR_ID);
        startActivity(instructorCourseConnectorScreen);
    }

    private boolean checkValidation(ArrayList<DoubleChoice> GENDER_LIST, EditText... editTexts){
        boolean isValid = true;
        for(EditText editText : editTexts){
            if(!(editText.length() > 0)){
                editText.setError("This field can not be empty");
                isValid = false;
            }
        }

        if(getSelectionFromList(GENDER_LIST) == -1) {
            isValid = false;
        }

        return isValid;
    }

    private ContentValues getDataInputs(
            ViewHolder.InstructorInputScreenViewHolder instructorInputScreenViewHolder,
            ArrayList<DoubleChoice> GENDER_LIST){

        final String INSTRUCTOR_NAME =
                instructorInputScreenViewHolder.INSTRUCTOR_NAME_EDIT_TEXT.getText().toString();
        final String INSTRUCTOR_MOBILE =
                instructorInputScreenViewHolder.INSTRUCTOR_MOBILE_EDIT_TEXT.getText().toString();
        final Integer INSTRUCTOR_AGE = Integer.valueOf(
                instructorInputScreenViewHolder.INSTRUCTOR_AGE_EDIT_TEXT.getText().toString()
        );
        final String INSTRUCTOR_QUALIFICATION =
                instructorInputScreenViewHolder.INSTRUCTOR_QUALIFICATION_EDIT_TEXT.getText().toString();
        final String INSTRUCTOR_ADDRESS =
                instructorInputScreenViewHolder.INSTRUCTOR_ADDRESS_EDIT_TEXT.getText().toString();
        final Integer INSTRUCTOR_GENDER = getSelectionFromList(GENDER_LIST);

        ContentValues contentValues = new ContentValues();
        contentValues.put(
                DbContent.InstructorTable.INSTRUCTOR_NAME_COLUMN,
                INSTRUCTOR_NAME
        );
        contentValues.put(
                DbContent.InstructorTable.INSTRUCTOR_MOBILE_COLUMN,
                INSTRUCTOR_MOBILE
        );
        contentValues.put(
                DbContent.InstructorTable.INSTRUCTOR_AGE_COLUMN,
                INSTRUCTOR_AGE
        );
        contentValues.put(
                DbContent.InstructorTable.INSTRUCTOR_ADDRESS_COLUMN,
                INSTRUCTOR_ADDRESS
        );
        contentValues.put(
                DbContent.InstructorTable.INSTRUCTOR_GENDER_COLUMN,
                INSTRUCTOR_GENDER
        );
        contentValues.put(
                DbContent.InstructorTable.INSTRUCTOR_QUALIFICATION_COLUMN,
                INSTRUCTOR_QUALIFICATION
        );

        return contentValues;

    }

    private int getSelectionFromList(ArrayList<DoubleChoice> arrayList){
        for(int i = 0 ; i < arrayList.size() ; i++){
            if(arrayList.get(i).isSelected())
                return i;
        }
        return -1;
    }

    private void setChoiceGenderListener(final ArrayList<DoubleChoice> doubleChoiceArrayList){
        for(final DoubleChoice doubleChoice : doubleChoiceArrayList){
            doubleChoice.getTextView().setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(!doubleChoice.isSelected()){
                                Utility.selectionProcess(
                                        doubleChoiceArrayList.indexOf(doubleChoice),
                                        doubleChoiceArrayList
                                );
                            }
                        }
                    }
            );
        }
    }

    private ArrayList<DoubleChoice> setChoiceSystemItems(
            ViewHolder.InstructorInputScreenViewHolder instructorInputScreenViewHolder){
        ArrayList<DoubleChoice> doubleChoiceArrayList = new ArrayList<>();

        Utility.setChoiceTextViewSystem(
                doubleChoiceArrayList,
                getContext(),
                instructorInputScreenViewHolder.INSTRUCTOR_MALE_GENDER_TEXT_VIEW,
                instructorInputScreenViewHolder.INSTRUCTOR_FEMALE_GENDER_TEXT_VIEW
        );

        Utility.setChoiceImageViewSystem(
                doubleChoiceArrayList,
                instructorInputScreenViewHolder.INSTRUCTOR_MALE_GENDER_IMAGE_VIEW,
                instructorInputScreenViewHolder.INSTRUCTOR_FEMALE_GENDER_IMAGE_VIEW
        );

        return doubleChoiceArrayList;
    }
}
