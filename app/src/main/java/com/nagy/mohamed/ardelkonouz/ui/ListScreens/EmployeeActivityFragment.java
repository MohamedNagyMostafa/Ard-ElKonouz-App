package com.nagy.mohamed.ardelkonouz.ui.ListScreens;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nagy.mohamed.ardelkonouz.R;
import com.nagy.mohamed.ardelkonouz.helper.Constants;
import com.nagy.mohamed.ardelkonouz.offlineDatabase.DatabaseController;
import com.nagy.mohamed.ardelkonouz.ui.InputScreens.EmployeeInput;
import com.nagy.mohamed.ardelkonouz.ui.ProfileScreens.EmployeeProfileActivity;
import com.nagy.mohamed.ardelkonouz.ui.ViewHolder;
import com.nagy.mohamed.ardelkonouz.ui.adapter.CursorAdapterList;
import com.nagy.mohamed.ardelkonouz.ui.adapter.DatabaseCursorAdapter;

/**
 * A placeholder fragment containing a simple view.
 */
public class EmployeeActivityFragment extends Fragment
        implements CursorAdapterList, LoaderManager.LoaderCallbacks<Cursor>{

    private DatabaseCursorAdapter databaseCursorAdapter;

    private View.OnClickListener addNewEmployeeListener =
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent employeeInputScreen = new Intent(getContext(), EmployeeInput.class);
                    employeeInputScreen.putExtra(Constants.INPUT_TYPE_EXTRA, Constants.INPUT_ADD_EXTRA);
                    startActivity(employeeInputScreen);
                }
            };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_employee, container, false);
        ViewHolder.EmployeeListScreenViewHolder employeeListScreenViewHolder =
                new ViewHolder.EmployeeListScreenViewHolder(rootView);

        databaseCursorAdapter = new DatabaseCursorAdapter(getContext(), null, this);

        employeeListScreenViewHolder.ADD_NEW_EMPLOYEE_BUTTON.setOnClickListener(addNewEmployeeListener);
        employeeListScreenViewHolder.EMPLOYEE_LIST_VIEW.setAdapter(databaseCursorAdapter);

        getLoaderManager().initLoader(Constants.LOADER_EMPLOYEE_LIST, null, this);

        return rootView;
    }

    @Override
    public View newListView(ViewGroup viewGroup, Cursor cursor) {
        return LayoutInflater.from(getContext())
                .inflate(R.layout.employee_list_recycle_view, viewGroup,false);
    }

    @Override
    public void bindListView(final View view, final Cursor cursor) {
        ViewHolder.EmployeeListScreenViewHolder.EmployeeListRecycleViewHolder
                employeeListRecycleViewHolder = new ViewHolder.EmployeeListScreenViewHolder
                .EmployeeListRecycleViewHolder(view);

        employeeListRecycleViewHolder.EMPLOYEE_NAME_TEXT_VIEW.setText(
                cursor.getString(DatabaseController.ProjectionDatabase.EMPLOYEE_LIST_NAME)
        );

        employeeListRecycleViewHolder.EMPLOYEE_MOBILE_TEXT_VIEW.setText(
               new StringBuilder(
                       cursor.getString(DatabaseController.ProjectionDatabase.EMPLOYEE_LIST_MOBILE))
                .insert(0,'0').toString()
        );

        final long EMPLOYEE_ID = cursor.getLong(DatabaseController.ProjectionDatabase.EMPLOYEE_LIST_ID);

        employeeListRecycleViewHolder.EMPLOYEE_DELETE_IMAGE_VIEW.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view1) {
                        // Delete Employee from employee table.
                        getActivity().getContentResolver().delete(
                                DatabaseController.UriDatabase.getEmployeeTableWithIdUri(EMPLOYEE_ID),
                                null,
                                null
                        );

                        restartLoader();
                    }
                }
        );

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent employeeProfile = new Intent(getContext(), EmployeeProfileActivity.class);
                employeeProfile.putExtra(Constants.EMPLOYEE_ID_EXTRA, EMPLOYEE_ID);
                startActivity(employeeProfile);
            }
        });
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(
                getContext(),
                DatabaseController.UriDatabase.EMPLOYEE_TABLE_URI,
                DatabaseController.ProjectionDatabase.EMPLOYEE_LIST_PROJECTION,
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        databaseCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        databaseCursorAdapter.swapCursor(null);
    }

    private void restartLoader(){
        getLoaderManager().restartLoader(Constants.LOADER_EMPLOYEE_LIST, null, this);

    }
}
