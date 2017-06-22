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
import com.nagy.mohamed.ardelkonouz.offlineDatabase.DbContent;
import com.nagy.mohamed.ardelkonouz.ui.InputScreens.InstructorInputActivity;
import com.nagy.mohamed.ardelkonouz.ui.ProfileScreens.InstructorProfileActivity;
import com.nagy.mohamed.ardelkonouz.ui.ViewHolder;
import com.nagy.mohamed.ardelkonouz.ui.adapter.CursorAdapterList;
import com.nagy.mohamed.ardelkonouz.ui.adapter.DatabaseCursorAdapter;

/**
 * A placeholder fragment containing a simple view.
 */
public class InstructorActivityFragment extends Fragment
        implements CursorAdapterList, LoaderManager.LoaderCallbacks<Cursor>{

    private DatabaseCursorAdapter databaseCursorAdapter;

    private View.OnClickListener addNewInstructor =
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent instructorInputScreen = new Intent(getContext(), InstructorInputActivity.class);
                    instructorInputScreen.putExtra(Constants.INPUT_TYPE_EXTRA, Constants.INPUT_ADD_EXTRA);
                    startActivity(instructorInputScreen);
                }
            };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_instructor, container, false);
        ViewHolder.InstructorListScreenViewHolder instructorListScreenViewHolder =
                new ViewHolder.InstructorListScreenViewHolder(rootView);

        databaseCursorAdapter = new DatabaseCursorAdapter(getContext(), null, this);

        instructorListScreenViewHolder.ADD_NEW_INSTRUCTOR_BUTTON.setOnClickListener(addNewInstructor);
        instructorListScreenViewHolder.INSTRUCTOR_LIST_VIEW.setAdapter(databaseCursorAdapter);

        getLoaderManager().initLoader(Constants.LOADER_INSTRUCTOR_LIST, null, this);

        return rootView;
    }

    @Override
    public View newListView(ViewGroup viewGroup, Cursor cursor) {
        return LayoutInflater.from(getContext())
                .inflate(R.layout.instructor_list_recycle_view, viewGroup, false);
    }

    @Override
    public void bindListView(final View view, final Cursor cursor) {
        ViewHolder.InstructorListScreenViewHolder.InstructorListRecycleViewHolder
                instructorListRecycleViewHolder = new ViewHolder.InstructorListScreenViewHolder
                .InstructorListRecycleViewHolder(view);
        final long INSTRUCTOR_ID =
                cursor.getLong(DatabaseController.ProjectionDatabase.INSTRUCTOR_LIST_ID);
        instructorListRecycleViewHolder.INSTRUCTOR_NAME_TEXT_VIEW.setText(
                cursor.getString(
                        DatabaseController.ProjectionDatabase.INSTRUCTOR_LIST_NAME
                )
        );

        Cursor coursesCursor = getActivity().getContentResolver().query(
                DatabaseController.UriDatabase
                        .getCourseInstructorTableWithInstructorIdUri(INSTRUCTOR_ID),
                new String[]{DbContent.CourseTable.COURSE_NAME_COLUMN},
                null,
                null,
                null
        );

        if(coursesCursor != null){
            if(coursesCursor.getCount() > 0){
                coursesCursor.moveToFirst();
                StringBuilder stringBuilder = new StringBuilder(coursesCursor.getString(0));

                while (coursesCursor.moveToNext()){
                    stringBuilder.append(" - ").append(coursesCursor.getString(0));
                }

                instructorListRecycleViewHolder.INSTRUCTOR_COURSES_TEXT_VIEW
                        .setText(stringBuilder.toString());
            }
            coursesCursor.close();
        }

        instructorListRecycleViewHolder.INSTRUCTOR_DELETE_IMAGE_VIEW
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Delete Instructor from InstructorTable.
                        getContext().getContentResolver().delete(
                                DatabaseController.UriDatabase
                                        .getInstructorTableWithIdUri(INSTRUCTOR_ID),
                                null,
                                null
                        );
                        // Delete Instructor from InstructorCoursesTable
                        getContext().getContentResolver().delete(
                                DatabaseController.UriDatabase
                                        .getCourseInstructorTableWithInstructorIdUri(INSTRUCTOR_ID),
                                null,
                                null
                        );

                        restartLoader();
                    }
                });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent instructorProfileScreen = new Intent(getContext(), InstructorProfileActivity.class);
                instructorProfileScreen.putExtra(Constants.INSTRUCTOR_ID_EXTRA, INSTRUCTOR_ID);
                startActivity(instructorProfileScreen);
            }
        });

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(
                getContext(),
                DatabaseController.UriDatabase.INSTRUCTOR_TABLE_URI,
                DatabaseController.ProjectionDatabase.INSTRUCTOR_LIST_PROJECTION,
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
        getLoaderManager().restartLoader(Constants.LOADER_INSTRUCTOR_LIST, null, this);
    }
}
