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
import android.widget.EditText;

import com.nagy.mohamed.ardelkonouz.R;
import com.nagy.mohamed.ardelkonouz.helper.Constants;
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
    private View courseSearchView;

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
                new ViewHolder.CourseListScreenViewHolder(rootView);

        databaseCursorAdapter = new DatabaseCursorAdapter(getContext(), null, this);

        courseListScreenViewHolder.ADD_NEW_COURSE_BUTTON.setOnClickListener(addNewCourseListener);
        courseListScreenViewHolder.COURSE_LIST_VIEW.setAdapter(databaseCursorAdapter);
        courseListScreenViewHolder.COURSE_LIST_VIEW.setEmptyView(courseListScreenViewHolder.COURSE_LIST_EMPTY_VIEW);

        // searching..
        if(courseSearchView != null){
            final EditText COURSE_SEARCH_EDIT_TEXT = (EditText) courseSearchView;
            COURSE_SEARCH_EDIT_TEXT.addTextChangedListener(searchTextWatcher);

        }

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

        courseListRecycleViewHolder.COURSE_AGE_RANGE_TEXT_VIEW.setText(
                String.valueOf(
                    cursor.getInt(
                            DatabaseController.ProjectionDatabase.COURSE_LIST_START_AGE
                    )
                )+ " ~ " +
                        String.valueOf(
                            cursor.getInt(
                                    DatabaseController.ProjectionDatabase.COURSE_LIST_END_AGE
                            )
                        )
        );

        courseListRecycleViewHolder.COURSE_DELETE_IMAGE_VIEW
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        // Delete Course Sections With Child and Instructor
                        Cursor sectionCourseCursor = getActivity().getContentResolver().query(
                                DatabaseController.UriDatabase.getSectionWithCourseId(COURSE_ID),
                                new String[]{DbContent.SectionTable._ID},
                                null,
                                null,
                                null
                        );

                        if(sectionCourseCursor != null){
                            while(sectionCourseCursor.moveToNext()){
                                Long SECTION_ID = sectionCourseCursor.getLong(0);

                                getActivity().getContentResolver().delete(
                                        DatabaseController.UriDatabase.getSectionChildTableWithSectionIdUri(SECTION_ID),
                                        null,
                                        null
                                );

                                getActivity().getContentResolver().delete(
                                        DatabaseController.UriDatabase.getSectionInstructorTableWithSectionIdUri(SECTION_ID),
                                        null,
                                        null
                                );

                                getActivity().getContentResolver().delete(
                                        DatabaseController.UriDatabase.getShiftWithSectionId(SECTION_ID),
                                        null,
                                        null
                                );

                                getActivity().getContentResolver().delete(
                                        DatabaseController.UriDatabase.getSectionWithId(SECTION_ID),
                                        null,
                                        null
                                );
                            }
                            sectionCourseCursor.close();
                        }

                        // Delete Course From Course Table.
                        getActivity().getContentResolver().delete(
                                DatabaseController.UriDatabase.getCourseTableWithIdUri(COURSE_ID),
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

    public void setEditTextView(View courseSearchView){
        this.courseSearchView = courseSearchView;
    }
}
