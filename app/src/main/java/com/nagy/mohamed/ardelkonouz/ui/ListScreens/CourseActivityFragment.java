package com.nagy.mohamed.ardelkonouz.ui.ListScreens;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nagy.mohamed.ardelkonouz.R;
import com.nagy.mohamed.ardelkonouz.helper.Constants;
import com.nagy.mohamed.ardelkonouz.helper.Utility;
import com.nagy.mohamed.ardelkonouz.offlineDatabase.DatabaseController;
import com.nagy.mohamed.ardelkonouz.offlineDatabase.DbContent;
import com.nagy.mohamed.ardelkonouz.ui.InputScreens.CourseInputActivity;
import com.nagy.mohamed.ardelkonouz.ui.ProfileScreens.CourseProfileActivity;
import com.nagy.mohamed.ardelkonouz.ui.ViewHolder;
import com.nagy.mohamed.ardelkonouz.ui.adapter.CursorAdapterList;
import com.nagy.mohamed.ardelkonouz.ui.adapter.DatabaseCursorAdapter;

/**
 * A placeholder fragment containing a simple view.
 */
public class CourseActivityFragment extends Fragment
        implements CursorAdapterList, LoaderManager.LoaderCallbacks<Cursor>{

    private DatabaseCursorAdapter databaseCursorAdapter;
    private String searchChars = "";
    private TextWatcher searchTextWatcher =
            new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    searchChars = charSequence.toString();
                    restartLoader();
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            };

    private View.OnClickListener addNewCourseListener =
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent courseInputScreen = new Intent(getContext(), CourseInputActivity.class);
                    courseInputScreen.putExtra(Constants.INPUT_TYPE_EXTRA, Constants.INPUT_ADD_EXTRA);
                    startActivity(courseInputScreen);
                }
            };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_course, container, false);
        ViewHolder.CourseListScreenViewHolder courseListScreenViewHolder =
                new ViewHolder.CourseListScreenViewHolder(rootView,
                        getActivity().findViewById(R.id.course_list_search_edit_view));

        databaseCursorAdapter = new DatabaseCursorAdapter(getContext(), null, this);

        courseListScreenViewHolder.ADD_NEW_COURSE_BUTTON.setOnClickListener(addNewCourseListener);
        courseListScreenViewHolder.COURSE_LIST_VIEW.setAdapter(databaseCursorAdapter);
        courseListScreenViewHolder.COURSE_LIST_VIEW.setEmptyView(courseListScreenViewHolder.COURSE_LIST_EMPTY_VIEW);
        courseListScreenViewHolder.COURSE_LIST_SEARCH_EDIT_tEXT.addTextChangedListener(searchTextWatcher);

        getLoaderManager().initLoader(Constants.LOADER_COURSE_LIST, null, this);

        return rootView;
    }

    @Override
    public View newListView(ViewGroup viewGroup, Cursor cursor) {
        return LayoutInflater.from(getContext())
                .inflate(R.layout.course_list_recycle_view, viewGroup, false);
    }

    @Override
    public void bindListView(View view, Cursor cursor) {
        ViewHolder.CourseListScreenViewHolder.CourseListRecycleViewHolder
                courseListRecycleViewHolder = new ViewHolder.CourseListScreenViewHolder
                .CourseListRecycleViewHolder(view);
        final long COURSE_ID = cursor.getLong(DatabaseController.ProjectionDatabase.COURSE_LIST_ID);

        courseListRecycleViewHolder.COURSE_NAME_TEXT_VIEW.setText(
                cursor.getString(
                        DatabaseController.ProjectionDatabase.COURSE_LIST_NAME
                )
        );

        String stringBuilderDuration = getActivity().getString(R.string.from) +
                " " +
                Utility.getTimeFormat(
                        cursor.getLong(DatabaseController.ProjectionDatabase.COURSE_LIST_START_DATE)
                )+
                "\n" +
                getActivity().getString(R.string.to) +
                Utility.getTimeFormat(
                        cursor.getLong(DatabaseController.ProjectionDatabase.COURSE_LIST_END_DATE)
                );

        courseListRecycleViewHolder.COURSE_DURATION_TEXT_VIEW.setText(stringBuilderDuration);

        final Cursor instructorCourse =
                getActivity().getContentResolver().query(
                        DatabaseController.UriDatabase.getCourseInstructorTableWithCourseIdUri(COURSE_ID),
                        new String[]{DbContent.InstructorTable.INSTRUCTOR_NAME_COLUMN},
                        null,
                        null,
                        null
                );

        if(instructorCourse != null){
            if(instructorCourse.getCount() > 0){
                instructorCourse.moveToFirst();
                StringBuilder stringBuilder = new StringBuilder(instructorCourse.getString(0));
                while (instructorCourse.moveToNext()){
                    stringBuilder.append(" - ").append(instructorCourse.getString(0));
                }
                courseListRecycleViewHolder.COURSE_INSTRUCTOR_TEXT_VIEW.setText(stringBuilder.toString());
            }
            instructorCourse.close();
        }

        courseListRecycleViewHolder.COURSE_DELETE_IMAGE_VIEW
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Delete Course From Course Table.
                        getActivity().getContentResolver().delete(
                                DatabaseController.UriDatabase.getCourseTableWithIdUri(COURSE_ID),
                                null,
                                null
                        );
                        // Delete Course From CourseChild Table.
                        getActivity().getContentResolver().delete(
                                DatabaseController.UriDatabase.getCourseChildTableWithCourseIdUri(COURSE_ID),
                                null,
                                null
                        );
                        // Delete Course From InstructorCourse Table.
                        getActivity().getContentResolver().delete(
                                DatabaseController.UriDatabase.getCourseInstructorTableWithCourseIdUri(COURSE_ID),
                                null,
                                null
                        );

                        restartLoader();
                    }
                });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent courseProfileScreen = new Intent(getContext(), CourseProfileActivity.class);
                courseProfileScreen.putExtra(Constants.COURSE_ID_EXTRA, COURSE_ID);
                startActivity(courseProfileScreen);
            }
        });
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(
                getContext(),
                DatabaseController.UriDatabase.getCourseTableWithSearchUri(searchChars),
                DatabaseController.ProjectionDatabase.COURSE_LIST_PROJECTION,
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
        getLoaderManager().restartLoader(Constants.LOADER_COURSE_LIST, null, this);
    }
}
