package com.nagy.mohamed.ardelkonouz.ui.ProfileScreens;

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
import com.nagy.mohamed.ardelkonouz.helper.Utility;
import com.nagy.mohamed.ardelkonouz.offlineDatabase.DatabaseController;
import com.nagy.mohamed.ardelkonouz.offlineDatabase.DbContent;
import com.nagy.mohamed.ardelkonouz.ui.ViewHolder;
import com.nagy.mohamed.ardelkonouz.ui.adapter.CursorAdapterList;
import com.nagy.mohamed.ardelkonouz.ui.adapter.DatabaseCursorAdapter;

/**
 * A placeholder fragment containing a simple view.
 */
public class ChildProfileActivityFragment extends Fragment
        implements CursorAdapterList, LoaderManager.LoaderCallbacks<Cursor> {

    private int childId;
    private DatabaseCursorAdapter databaseCursorAdapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_child_profile, container, false);
        ViewHolder.ChildProfileScreenViewHolder childProfileScreenViewHolder =
                new ViewHolder.ChildProfileScreenViewHolder(rootView);
        childId = getActivity().getIntent().getExtras().getInt(Constants.CHILD_ID_EXTRA);
        databaseCursorAdapter = new DatabaseCursorAdapter(getContext(), null, this);

        Cursor childProfileData = getQueryChildData();

        if(childProfileData != null){
            if(childProfileData.getCount() > 0){
                childProfileData.moveToFirst();
                setDataToViews(childProfileScreenViewHolder, childProfileData);
            }
            childProfileData.close();
        }

        childProfileScreenViewHolder.CHILD_COURSES_GRID_VIEW.setAdapter(databaseCursorAdapter);

        getLoaderManager().initLoader(Constants.LOADER_COURSE_CHILD_JOIN_LIST, null, this);

        return rootView;
    }

    private Cursor getQueryChildData(){
        return getActivity().getContentResolver().query(
                DatabaseController.UriDatabase.getChildTableWithIdUri(childId),
                DatabaseController.ProjectionDatabase.CHILD_PROJECTION,
                null,
                null,
                null
        );
    }

    private void setDataToViews(ViewHolder.ChildProfileScreenViewHolder childProfileScreenViewHolder,
                                Cursor childProfileData){
        childProfileScreenViewHolder.CHILD_NAME_TEXT_VIEW.setText(
                childProfileData.getString(DatabaseController.ProjectionDatabase.CHILD_NAME)
        );
        childProfileScreenViewHolder.CHILD_AGE_TEXT_VIEW.setText(
                String.valueOf(
                        childProfileData.getInt(DatabaseController.ProjectionDatabase.CHILD_AGE)
                )
        );
        childProfileScreenViewHolder.CHILD_BIRTH_ORDER_TEXT_VIEW.setText(
                Utility.decodeBirthOrderByInt(
                        childProfileData.getInt(DatabaseController.ProjectionDatabase.CHILD_BIRTH_ORDER),
                        getContext()
                )
        );
        childProfileScreenViewHolder.CHILD_GENDER_TEXT_VIEW.setText(
                Utility.decodeGenderByInt(
                        childProfileData.getInt(DatabaseController.ProjectionDatabase.CHILD_GENDER),
                        getContext()
                )
        );
        childProfileScreenViewHolder.MOTHER_NAME_TEXT_VIEW.setText(
                childProfileData.getString(DatabaseController.ProjectionDatabase.CHILD_MOTHER_NAME)
        );
        childProfileScreenViewHolder.MOTHER_MOBILE_TEXT_VIEW.setText(
                new StringBuilder(String.valueOf(
                        childProfileData.getLong(DatabaseController.ProjectionDatabase.CHILD_MOTHER_MOBILE)
                    )
                ).insert(0,'0').toString()
        );
        childProfileScreenViewHolder.MOTHER_QUALIFICATION_TEXT_VIEW.setText(
                childProfileData.getString(DatabaseController.ProjectionDatabase.CHILD_MOTHER_QUALIFICATION)
        );
        childProfileScreenViewHolder.MOTHER_JOB_TEXT_VIEW.setText(
                childProfileData.getString(DatabaseController.ProjectionDatabase.CHILD_MOTHER_JOB)
        );
        childProfileScreenViewHolder.FATHER_NAME_TEXT_VIEW.setText(
                childProfileData.getString(DatabaseController.ProjectionDatabase.CHILD_FATHER_NAME)
        );
        childProfileScreenViewHolder.FATHER_MOBILE_TEXT_VIEW.setText(
                new StringBuilder(String.valueOf(
                        childProfileData.getLong(DatabaseController.ProjectionDatabase.CHILD_FATHER_MOBILE)
                )).insert(0,'0').toString()
        );
        childProfileScreenViewHolder.FATHER_JOB_TEXT_VIEW.setText(
                childProfileData.getString(DatabaseController.ProjectionDatabase.CHILD_FATHER_JOB)
        );
        childProfileScreenViewHolder.WHATSAPP_TEXT_VIEW.setText(
                new StringBuilder(
                        String.valueOf(
                                childProfileData.getLong(DatabaseController.ProjectionDatabase.CHILD_MOBILE_WHATSUP)
                        )
                ).insert(0,'0').toString()
        );
        childProfileScreenViewHolder.EDUCATION_TYPE_TEXT_VIEW.setText(
                Utility.decodeEducationTypeByInt(
                        childProfileData.getInt(DatabaseController.ProjectionDatabase.CHILD_EDUCATION_TYPE),
                        getContext()
                )
        );
        childProfileScreenViewHolder.EDUCATION_STAGE_TEXT_VIEW.setText(
                childProfileData.getString(
                        DatabaseController.ProjectionDatabase.CHILD_STUDY_YEAR
                )
        );
        childProfileScreenViewHolder.CHILD_CHARACTERISTIC_TEXT_VIEW.setText(
                Utility.decodeCharacteristicByInt(
                        childProfileData.getInt(DatabaseController.ProjectionDatabase.CHILD_TRAITS),
                        getContext()
                )
        );
        childProfileScreenViewHolder.CHILD_HANDLING_PROBLEM_TEXT_VIEW.setText(
                Utility.decodeDealingProblemByInt(
                        childProfileData.getInt(DatabaseController.ProjectionDatabase.CHILD_HANDLING),
                        getContext()
                )
        );
        childProfileScreenViewHolder.CHILD_FREE_TIME_TEXT_VIEW.setText(
                Utility.decodeFreeTimeByInt(
                        childProfileData.getInt(DatabaseController.ProjectionDatabase.CHILD_FREE_TIME),
                        getContext()
                )
        );
    }

    @Override
    public View newListView(ViewGroup viewGroup, Cursor cursor) {
        return LayoutInflater.from(getContext())
                .inflate(R.layout.child_pf_courses_instructors_recycle, viewGroup, false);
    }

    @Override
    public void bindListView(View view, Cursor cursor) {
        ViewHolder.ChildProfileScreenViewHolder.ChildProfileListViewHolder
                childProfileListViewHolder = new ViewHolder
                .ChildProfileScreenViewHolder.ChildProfileListViewHolder(view);

        if(cursor != null && cursor.getCount() > 0){
            childProfileListViewHolder.COURSE_NAME_TEXT_VIEW.setText(
                    cursor.getString(DatabaseController.ProjectionDatabase.COURSE_CHILD_JOIN_LIST_COURSE_NAME_COLUMN)
            );
            childProfileListViewHolder.START_DATE_TEXT_VIEW.setText(
                    String.valueOf(
                            cursor.getLong(DatabaseController.ProjectionDatabase.COURSE_CHILD_JOIN_LIST_COURSE_START_DATE_COLUMN)
                    )
            );
            childProfileListViewHolder.END_DATE_TEXT_VIEW.setText(
                    String.valueOf(
                            cursor.getLong(DatabaseController.ProjectionDatabase.COURSE_CHILD_JOIN_LIST_COURSE_END_DATE_COLUMN)
                    )
            );

            final int COURSE_ID = cursor.getInt(DatabaseController.ProjectionDatabase.COURSE_CHILD_JOIN_LIST_COURSE_ID_COLUMN);
            Cursor instructorCursor =
                    getActivity().getContentResolver().query(
                            DatabaseController.UriDatabase.getCourseInstructorTableWithCourseIdUri(COURSE_ID),
                            new String[]{DbContent.InstructorTable.INSTRUCTOR_NAME_COLUMN},
                            null,
                            null,
                            null
                    );
            if(instructorCursor != null){
                if(instructorCursor.getCount() > 0){
                    instructorCursor.moveToFirst();
                    childProfileListViewHolder.INSTRUCTOR_NAME_TEXT_VIEW.setText(
                            instructorCursor.getString(0)
                    );
                }
                instructorCursor.close();
            }
        }

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(
                getContext(),
                DatabaseController.UriDatabase.getCourseChildTableWithChildIdUri(childId),
                DatabaseController.ProjectionDatabase.COURSE_CHILD_JOIN_LIST_TABLE,
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

}
