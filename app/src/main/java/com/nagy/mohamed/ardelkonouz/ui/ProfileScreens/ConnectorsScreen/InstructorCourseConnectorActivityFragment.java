package com.nagy.mohamed.ardelkonouz.ui.ProfileScreens.ConnectorsScreen;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nagy.mohamed.ardelkonouz.R;
import com.nagy.mohamed.ardelkonouz.helper.Constants;
import com.nagy.mohamed.ardelkonouz.helper.Utility;
import com.nagy.mohamed.ardelkonouz.offlineDatabase.DatabaseController;
import com.nagy.mohamed.ardelkonouz.offlineDatabase.DbContent;
import com.nagy.mohamed.ardelkonouz.ui.ProfileScreens.InstructorProfileActivity;
import com.nagy.mohamed.ardelkonouz.ui.ViewHolder;
import com.nagy.mohamed.ardelkonouz.ui.adapter.CursorAdapterList;
import com.nagy.mohamed.ardelkonouz.ui.adapter.DatabaseCursorAdapter;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class InstructorCourseConnectorActivityFragment extends Fragment
        implements CursorAdapterList, LoaderManager.LoaderCallbacks<Cursor>{

    private ArrayList<Long> selectedCourses;
    private ArrayList<Long> previousSelectedCourses;
    private DatabaseCursorAdapter databaseCursorAdapter;
    private Long instructorId;


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_instructor_course_connector, container, false);
        ViewHolder.InstructorCourseConnectorScreenViewHolder instructorCourseConnectorScreenViewHolder =
                new ViewHolder.InstructorCourseConnectorScreenViewHolder(rootView);
        instructorId = getActivity().getIntent().getExtras().getLong(Constants.INSTRUCTOR_ID_EXTRA);
        selectedCourses = new ArrayList<>();
        previousSelectedCourses = new ArrayList<>();

        databaseCursorAdapter = new DatabaseCursorAdapter(getContext(), null, this);


        // set previous courses.
        setPreviousCourses(previousSelectedCourses);

        // set listener.
        instructorCourseConnectorScreenViewHolder.COURSES_LIST_VIEW.setAdapter(databaseCursorAdapter);
        instructorCourseConnectorScreenViewHolder.COURSES_LIST_VIEW.setEmptyView(
                instructorCourseConnectorScreenViewHolder.INSTRUCTOR_COURSE_LIST_EMPTY_VIEW
        );
        instructorCourseConnectorScreenViewHolder.REST_BUTTON.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        selectedCourses = new ArrayList<Long>();
                        previousSelectedCourses = new ArrayList<Long>();

                        setPreviousCourses(previousSelectedCourses);
                        restartLoader();
                    }
                }
        );
        instructorCourseConnectorScreenViewHolder.SUBMIT_BUTTON.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        ContentValues selectedContentValues = new ContentValues();
                        selectedContentValues.put(DbContent.CourseInstructorTable.INSTRUCTOR_ID_COLUMN, instructorId);
                        Log.e("selected course count", String.valueOf(selectedCourses.size()));
                        Log.e("preselected corse count", String.valueOf(previousSelectedCourses.size()));

                        for(final Long COURSE_ID : selectedCourses){

                            getActivity().getContentResolver().update(
                                    DatabaseController.UriDatabase.getCourseInstructorTableWithCourseIdUri(COURSE_ID),
                                    selectedContentValues,
                                    null,
                                    null
                            );

                            if(previousSelectedCourses.contains(COURSE_ID)){
                                previousSelectedCourses.remove(COURSE_ID);
                            }
                        }

                        ContentValues unselectedContentValues = new ContentValues();
                        unselectedContentValues.put(
                                DbContent.CourseInstructorTable.INSTRUCTOR_ID_COLUMN,
                                Constants.NO_INSTRUCTOR);

                        for(final Long COURSE_ID : previousSelectedCourses) {
                            getActivity().getContentResolver().update(
                                    DatabaseController.UriDatabase.getCourseInstructorTableWithCourseIdUri(COURSE_ID),
                                    unselectedContentValues,
                                    null,
                                    null
                            );
                        }

                        openInstructorProfile();
                    }
                }
        );

        getLoaderManager().initLoader(Constants.LOADER_INSTRUCTOR_COURSE_CONNECTOR, null, this);
        return rootView;

    }

    private void openInstructorProfile(){
        Intent instructorProfileScreen = new Intent(getContext(), InstructorProfileActivity.class);
        instructorProfileScreen.putExtra(Constants.INSTRUCTOR_ID_EXTRA, instructorId);
        startActivity(instructorProfileScreen);
    }

    private void setPreviousCourses(ArrayList<Long> previousCourses){
        Cursor cursor = getActivity().getContentResolver().query(
                DatabaseController.UriDatabase.getCourseInstructorTableWithInstructorIdUri(instructorId),
                new String[]{DbContent.CourseInstructorTable.COURSE_ID_COLUMN},
                null,
                null,
                null
        );

        if(cursor != null){
            if(cursor.getCount() > 0){
                while (cursor.moveToNext()){
                    previousCourses.add(cursor.getLong(0));
                    selectedCourses.add(cursor.getLong(0));
                }
            }
            cursor.close();
        }
    }

    @Override
    public View newListView(ViewGroup viewGroup, Cursor cursor) {
        return LayoutInflater.from(getContext())
                .inflate(R.layout.instructor_course_recycle_view, viewGroup, false);
    }

    @Override
    public void bindListView(View view, Cursor cursor) {
        final ViewHolder.InstructorCourseConnectorScreenViewHolder.CoursesViewHolder coursesViewHolder =
                new ViewHolder.InstructorCourseConnectorScreenViewHolder.CoursesViewHolder(view);
        final Long COURSE_ID = cursor.getLong(DatabaseController.ProjectionDatabase.COURSE_ID);

        coursesViewHolder.COURSE_START_DATE_TEXT_VIEW.setText(
                Utility.getTimeFormat(
                        cursor.getLong(
                                4
                        )
                )
        );
        coursesViewHolder.COURSE_END_DATE_TEXT_VIEW.setText(
                Utility.getTimeFormat(
                        cursor.getLong(
                                5
                        )
                )
        );

        coursesViewHolder.COURSE_HOURS_TEXT_VIEW.setText(
                String.valueOf(
                        cursor.getDouble(
                                3
                        )
                )
        );
        coursesViewHolder.COURSE_NAME_TEXT_VIEW.setText(
                cursor.getString(
                        1
                )
        );
        coursesViewHolder.COURSE_SALARY_PER_CHILD_TEXT_VIEW.setText(
                String.valueOf(
                        cursor.getDouble(
                                2
                        )
                )
        );

        view.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(selectedCourses.contains(COURSE_ID)){
                            selectedCourses.remove(COURSE_ID);
                            coursesViewHolder.COURSE_SELECT_IMAGE_VIEW.setVisibility(View.INVISIBLE);
                        }else{
                            selectedCourses.add(COURSE_ID);
                            coursesViewHolder.COURSE_SELECT_IMAGE_VIEW.setVisibility(View.VISIBLE);
                        }
                    }
                }
        );

        if(selectedCourses.contains(COURSE_ID) || previousSelectedCourses.contains(COURSE_ID)){
            coursesViewHolder.COURSE_SELECT_IMAGE_VIEW.setVisibility(View.VISIBLE);
        }else{
            coursesViewHolder.COURSE_SELECT_IMAGE_VIEW.setVisibility(View.INVISIBLE);

        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        long dateAsMills = Utility.getCurrentDateAsMills();
        return new CursorLoader(
                getContext(),
                DatabaseController.UriDatabase.getCourseTableWithIdWithEndDate(dateAsMills, instructorId),
                DatabaseController.ProjectionDatabase.COURSE_PROJECTION,
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
        getLoaderManager().restartLoader(Constants.LOADER_INSTRUCTOR_COURSE_CONNECTOR, null, this);

    }
}
