package com.nagy.mohamed.ardelkonouz.ui.ProfileScreens;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nagy.mohamed.ardelkonouz.R;
import com.nagy.mohamed.ardelkonouz.helper.Constants;
import com.nagy.mohamed.ardelkonouz.helper.Utility;
import com.nagy.mohamed.ardelkonouz.offlineDatabase.DatabaseController;
import com.nagy.mohamed.ardelkonouz.ui.InputScreens.EmployeeInput;
import com.nagy.mohamed.ardelkonouz.ui.ViewHolder;

/**
 * A placeholder fragment containing a simple view.
 */
public class EmployeeProfileActivityFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_employee_profile, container, false);
        ViewHolder.EmployeeProfileScreenViewHolder employeeProfileScreenViewHolder =
                new ViewHolder.EmployeeProfileScreenViewHolder(rootView);
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
                cursor.moveToFirst();
                setDataToViews(cursor, employeeProfileScreenViewHolder);
            }
            cursor.close();
        }

        employeeProfileScreenViewHolder.EMPLOYEE_EDIT_BUTTON.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent employeeInputScreen = new Intent(getContext(), EmployeeInput.class);
                        employeeInputScreen.putExtra(Constants.EMPLOYEE_ID_EXTRA, EMPLOYEE_ID);
                        employeeInputScreen.putExtra(Constants.INPUT_TYPE_EXTRA, Constants.INPUT_EDIT_EXTRA);
                        startActivity(employeeInputScreen);
                    }
                }
        );
        return rootView;
    }

    private void setDataToViews(Cursor cursor,
                                ViewHolder.EmployeeProfileScreenViewHolder employeeProfileScreenViewHolder){
        employeeProfileScreenViewHolder.EMPLOYEE_NAME_TEXT_VIEW.setText(
                cursor.getString(
                        DatabaseController.ProjectionDatabase.EMPLOYEE_NAME
                )
        );
        employeeProfileScreenViewHolder.EMPLOYEE_AGE_TEXT_VIEW.setText(
                String.valueOf(
                        cursor.getInt(
                                DatabaseController.ProjectionDatabase.EMPLOYEE_AGE
                        )
                )
        );
        employeeProfileScreenViewHolder.EMPLOYEE_ADDRESS_TEXT_VIEW.setText(
                cursor.getString(
                        DatabaseController.ProjectionDatabase.EMPLOYEE_ADDRESS
                )
        );
        employeeProfileScreenViewHolder.EMPLOYEE_GENDER_TEXT_VIEW.setText(
                Utility.decodeGenderByInt(
                        cursor.getInt(
                                DatabaseController.ProjectionDatabase.EMPLOYEE_GENDER
                        ), getContext()
                )
        );
        employeeProfileScreenViewHolder.EMPLOYEE_MOBILE_TEXT_VIEW.setText(
                                cursor.getString(
                                DatabaseController.ProjectionDatabase.EMPLOYEE_MOBILE
                                )
        );
        employeeProfileScreenViewHolder.EMPLOYEE_QUALIFICATION_TEXT_VIEW.setText(
                cursor.getString(
                        DatabaseController.ProjectionDatabase.EMPLOYEE_QUALIFICATION
                )
        );
        employeeProfileScreenViewHolder.EMPLOYEE_SALARY_TEXT_VIEW.setText(
                String.valueOf(
                        cursor.getDouble(
                                DatabaseController.ProjectionDatabase.EMPLOYEE_ORIGINAL_SALARY
                        )
                )
        );
    }
}
