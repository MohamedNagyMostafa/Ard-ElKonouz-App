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
import com.nagy.mohamed.ardelkonouz.ui.ProfileScreens.EmployeeProfileActivity;
import com.nagy.mohamed.ardelkonouz.ui.ViewHolder;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class EmployeeInputFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_employee_input, container, false);
        final String INPUT_TYPE = getActivity().getIntent().getExtras().getString(Constants.INPUT_TYPE_EXTRA);
        ViewHolder.EmployeeInputScreenViewHolder employeeInputScreenViewHolder =
                new ViewHolder.EmployeeInputScreenViewHolder(rootView);

        // set choice sys.
        ArrayList<DoubleChoice> GENDER_LIST =
                setChoiceSystemItems(employeeInputScreenViewHolder);

        // set choice listener.
        setChoiceGenderListener(GENDER_LIST);

        if(INPUT_TYPE.equals(Constants.INPUT_ADD_EXTRA)){
            setOptionsAsAddNewEmployee(
                    employeeInputScreenViewHolder,
                    GENDER_LIST
            );
        }else{
            setOptionsAsEditEmployee(
                    employeeInputScreenViewHolder,
                    GENDER_LIST
            );
        }

        return rootView;

    }

    private void setOptionsAsEditEmployee(
            final ViewHolder.EmployeeInputScreenViewHolder employeeInputScreenViewHolder,
            final ArrayList<DoubleChoice> GENDER_LIST) {

        final long EMPLOYEE_ID = getActivity().getIntent().getExtras().getLong(Constants.EMPLOYEE_ID_EXTRA);

        Cursor cursor = getActivity().getContentResolver().query(
                DatabaseController.UriDatabase.getEmployeeTableWithIdUri(EMPLOYEE_ID),
                DatabaseController.ProjectionDatabase.EMPLOYEE_PROJECTION,
                null,
                null,
                null
        );

        if(cursor != null){
            if(cursor.getCount() > 0){

                employeeInputScreenViewHolder.EMPLOYEE_NAME_EDIT_TEXT.setText(
                        cursor.getString(
                                DatabaseController.ProjectionDatabase.EMPLOYEE_NAME
                        )
                );
                employeeInputScreenViewHolder.EMPLOYEE_MOBILE_EDIT_TEXT.setText(
                        cursor.getString(
                                DatabaseController.ProjectionDatabase.EMPLOYEE_MOBILE
                        )
                );
                employeeInputScreenViewHolder.EMPLOYEE_AGE_EDIT_TEXT.setText(
                        String.valueOf(
                                cursor.getInt(
                                        DatabaseController.ProjectionDatabase.EMPLOYEE_AGE
                                )
                        )
                );
                employeeInputScreenViewHolder.EMPLOYEE_ADDRESS_EDIT_TEXT.setText(
                        cursor.getString(
                                DatabaseController.ProjectionDatabase.EMPLOYEE_ADDRESS
                        )
                );
                employeeInputScreenViewHolder.EMPLOYEE_SALARY_EDIT_TEXT.setText(
                        String.valueOf(
                                cursor.getDouble(
                                        DatabaseController.ProjectionDatabase.EMPLOYEE_ORIGINAL_SALARY
                                )
                        )
                );
                employeeInputScreenViewHolder.EMPLOYEE_QUALIFICATION_EDIT_TEXT.setText(
                        cursor.getString(
                                DatabaseController.ProjectionDatabase.EMPLOYEE_QUALIFICATION
                        )
                );
                Utility.selectionProcess(
                        cursor.getInt(
                                DatabaseController.ProjectionDatabase.EMPLOYEE_GENDER
                        ),
                        GENDER_LIST
                );

                employeeInputScreenViewHolder.EMPLOYEE_SUBMIT_BUTTON.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(checkValidation(
                                        GENDER_LIST,
                                        employeeInputScreenViewHolder.EMPLOYEE_ADDRESS_EDIT_TEXT,
                                        employeeInputScreenViewHolder.EMPLOYEE_AGE_EDIT_TEXT,
                                        employeeInputScreenViewHolder.EMPLOYEE_MOBILE_EDIT_TEXT,
                                        employeeInputScreenViewHolder.EMPLOYEE_NAME_EDIT_TEXT,
                                        employeeInputScreenViewHolder.EMPLOYEE_SALARY_EDIT_TEXT,
                                        employeeInputScreenViewHolder.EMPLOYEE_QUALIFICATION_EDIT_TEXT
                                )){
                                    getActivity().getContentResolver().update(
                                            DatabaseController.UriDatabase.getEmployeeTableWithIdUri(EMPLOYEE_ID),
                                            getDataInputs(
                                                    employeeInputScreenViewHolder,
                                                    GENDER_LIST
                                            ),
                                            null,
                                            null
                                    );

                                    openEmployeeProfile(EMPLOYEE_ID);
                                }
                            }
                        }
                );

            }
            cursor.close();
        }


    }

    private void setOptionsAsAddNewEmployee(
            final ViewHolder.EmployeeInputScreenViewHolder employeeInputScreenViewHolder,
            final ArrayList<DoubleChoice> GENDER_LIST){

        employeeInputScreenViewHolder.EMPLOYEE_SUBMIT_BUTTON.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(checkValidation(
                                GENDER_LIST,
                                employeeInputScreenViewHolder.EMPLOYEE_ADDRESS_EDIT_TEXT,
                                employeeInputScreenViewHolder.EMPLOYEE_AGE_EDIT_TEXT,
                                employeeInputScreenViewHolder.EMPLOYEE_MOBILE_EDIT_TEXT,
                                employeeInputScreenViewHolder.EMPLOYEE_SALARY_EDIT_TEXT,
                                employeeInputScreenViewHolder.EMPLOYEE_NAME_EDIT_TEXT,
                                employeeInputScreenViewHolder.EMPLOYEE_QUALIFICATION_EDIT_TEXT
                                )){

                            Uri employeeUri = getActivity().getContentResolver().insert(
                                    DatabaseController.UriDatabase.EMPLOYEE_TABLE_URI,
                                    getDataInputs(employeeInputScreenViewHolder,GENDER_LIST)
                            );

                            final long EMPLOYEE_ID = ContentUris.parseId(employeeUri);

                            openEmployeeProfile(EMPLOYEE_ID);
                        }
                    }
                }
        );
    }

    private void openEmployeeProfile(final long EMPLOYEE_ID){
        Intent employeeProfileScreen = new Intent(getContext(), EmployeeProfileActivity.class);
        employeeProfileScreen.putExtra(Constants.EMPLOYEE_ID_EXTRA, EMPLOYEE_ID);
        startActivity(employeeProfileScreen);
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
            ViewHolder.EmployeeInputScreenViewHolder employeeInputScreenViewHolder,
            ArrayList<DoubleChoice> GENDER_LIST){

        final String EMPLOYEE_NAME =
                employeeInputScreenViewHolder.EMPLOYEE_NAME_EDIT_TEXT.getText().toString();
        final String EMPLOYEE_MOBILE =
                employeeInputScreenViewHolder.EMPLOYEE_MOBILE_EDIT_TEXT.getText().toString();
        final Integer EMPLOYEE_AGE = Integer.valueOf(
                employeeInputScreenViewHolder.EMPLOYEE_AGE_EDIT_TEXT.getText().toString()
        );
        final String EMPLOYEE_QUALIFICATION =
                employeeInputScreenViewHolder.EMPLOYEE_QUALIFICATION_EDIT_TEXT.getText().toString();
        final String EMPLOYEE_ADDRESS =
                employeeInputScreenViewHolder.EMPLOYEE_ADDRESS_EDIT_TEXT.getText().toString();
        final Integer EMPLOYEE_GENDER = getSelectionFromList(GENDER_LIST);
        final Double EMPLOYEE_SALARY = Double.valueOf(
                employeeInputScreenViewHolder.EMPLOYEE_SALARY_EDIT_TEXT.getText().toString()
        );

        ContentValues contentValues = new ContentValues();
        contentValues.put(
                DbContent.EmployeeTable.EMPLOYEE_NAME_COLUMN,
                EMPLOYEE_NAME
        );
        contentValues.put(
                DbContent.EmployeeTable.EMPLOYEE_MOBILE_COLUMN,
                EMPLOYEE_MOBILE
        );
        contentValues.put(
                DbContent.EmployeeTable.EMPLOYEE_AGE_COLUMN,
                EMPLOYEE_AGE
        );
        contentValues.put(
                DbContent.EmployeeTable.EMPLOYEE_ADDRESS_COLUMN,
                EMPLOYEE_ADDRESS
        );
        contentValues.put(
                DbContent.EmployeeTable.EMPLOYEE_GENDER_COLUMN,
                EMPLOYEE_GENDER
        );
        contentValues.put(
                DbContent.EmployeeTable.EMPLOYEE_ORIGINAL_SALARY_COLUMN,
                EMPLOYEE_SALARY
        );
        contentValues.put(
                DbContent.EmployeeTable.EMPLOYEE_QUALIFICATION_COLUMN,
                EMPLOYEE_QUALIFICATION
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
            ViewHolder.EmployeeInputScreenViewHolder employeeInputScreenViewHolder){
        ArrayList<DoubleChoice> doubleChoiceArrayList = new ArrayList<>();

        Utility.setChoiceTextViewSystem(
                doubleChoiceArrayList,
                getContext(),
                employeeInputScreenViewHolder.EMPLOYEE_MALE_GENDER_TEXT_VIEW,
                employeeInputScreenViewHolder.EMPLOYEE_FEMALE_GENDER_TEXT_VIEW
        );

        Utility.setChoiceImageViewSystem(
                doubleChoiceArrayList,
                employeeInputScreenViewHolder.EMPLOYEE_MALE_GENDER_IMAGE_VIEW,
                employeeInputScreenViewHolder.EMPLOYEE_FEMALE_GENDER_IMAGE_VIEW
        );

        return doubleChoiceArrayList;
    }
}
