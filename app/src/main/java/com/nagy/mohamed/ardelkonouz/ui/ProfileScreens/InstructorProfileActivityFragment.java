package com.nagy.mohamed.ardelkonouz.ui.ProfileScreens;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nagy.mohamed.ardelkonouz.R;
import com.nagy.mohamed.ardelkonouz.helper.Constants;
import com.nagy.mohamed.ardelkonouz.helper.Utility;
import com.nagy.mohamed.ardelkonouz.offlineDatabase.DatabaseController;
import com.nagy.mohamed.ardelkonouz.ui.InputScreens.InstructorInputActivity;
import com.nagy.mohamed.ardelkonouz.ui.ViewHolder;
import com.nagy.mohamed.ardelkonouz.ui.adapter.RecycleViewInstructorProfileAdapter;

/**
 * A placeholder fragment containing a simple view.
 */
public class InstructorProfileActivityFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<Cursor>{

    private RecycleViewInstructorProfileAdapter recycleViewInstructorProfileAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_instructor_profile, container, false);
        ViewHolder.InstructorProfileScreenViewHolder instructorProfileScreenViewHolder =
                new ViewHolder.InstructorProfileScreenViewHolder(rootView);
        final int INSTRUCTOR_ID = getActivity().getIntent().getExtras().getInt(Constants.INSTRUCTOR_ID_EXTRA);

        Cursor cursor = getActivity().getContentResolver().query(
                DatabaseController.UriDatabase.getInstructorTableWithIdUri(INSTRUCTOR_ID),
                DatabaseController.ProjectionDatabase.INSTRUCTOR_PROJECTION,
                null,
                null,
                null
        );

        if(cursor != null){
            if(cursor.getCount() > 0){
                setDataToViews(cursor, instructorProfileScreenViewHolder);
            }
            cursor.close();
        }

        instructorProfileScreenViewHolder.INSTRUCTOR_EDIT_BUTTON.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent instructorInputScreen = new Intent(getContext(), InstructorInputActivity.class);
                        instructorInputScreen.putExtra(Constants.INSTRUCTOR_ID_EXTRA, INSTRUCTOR_ID);
                        startActivity(instructorInputScreen);
                    }
                }
        );

        getLoaderManager().initLoader(Constants.LOADER_INSTRUCTOR_COURSE_JOIN_LIST, null, this);

        return rootView;
    }

    private void setDataToViews(Cursor cursor,
                                ViewHolder.InstructorProfileScreenViewHolder instructorProfileScreenViewHolder){
        instructorProfileScreenViewHolder.INSTRUCTOR_NAME_TEXT_VIEW.setText(
                cursor.getString(
                        DatabaseController.ProjectionDatabase.INSTRUCTOR_NAME
                )
        );
        instructorProfileScreenViewHolder.INSTRUCTOR_AGE_TEXT_VIEW.setText(
                String.valueOf(
                        cursor.getInt(
                                DatabaseController.ProjectionDatabase.INSTRUCTOR_AGE
                        )
                )
        );
        instructorProfileScreenViewHolder.INSTRUCTOR_ADDRESS_TEXT_VIEW.setText(
                cursor.getString(
                        DatabaseController.ProjectionDatabase.INSTRUCTOR_ADDRESS
                )
        );
        instructorProfileScreenViewHolder.INSTRUCTOR_MOBILE_TEXT_VIEW.setText(
                new StringBuilder(
                        String.valueOf(
                                cursor.getLong(
                                        DatabaseController.ProjectionDatabase.INSTRUCTOR_MOBILE
                                )
                        )
                ).insert(0, '0').toString()
        );
        instructorProfileScreenViewHolder.INSTRUCTOR_GENDER_TEXT_VIEW.setText(
                Utility.decodeGenderByInt(
                        cursor.getInt(
                                DatabaseController.ProjectionDatabase.INSTRUCTOR_GENDER
                        ),
                        getContext()
                )
        );
        instructorProfileScreenViewHolder.INSTRUCTOR_QUALIFICATION_TEXT_VIEW.setText(
                cursor.getString(
                        DatabaseController.ProjectionDatabase.INSTRUCTOR_QUALIFICATION
                )
        );
    }
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
