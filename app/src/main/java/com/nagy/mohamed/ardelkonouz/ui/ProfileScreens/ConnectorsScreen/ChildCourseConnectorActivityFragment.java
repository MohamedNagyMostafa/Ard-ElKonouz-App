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
import com.nagy.mohamed.ardelkonouz.ui.ProfileScreens.ChildProfileActivity;
import com.nagy.mohamed.ardelkonouz.ui.ViewHolder;
import com.nagy.mohamed.ardelkonouz.ui.adapter.CursorAdapterList;
import com.nagy.mohamed.ardelkonouz.ui.adapter.DatabaseCursorAdapter;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class ChildCourseConnectorActivityFragment extends Fragment
        implements CursorAdapterList, LoaderManager.LoaderCallbacks<Cursor>{

    private DatabaseCursorAdapter databaseCursorAdapter;
    private ArrayList<Long> selectedCourses;
    private long childId;
    private int childAge;


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_child_course_connector, container, false);
        selectedCourses = new ArrayList<>();
        ViewHolder.ChildCourseConnectorScreenViewHolder childCourseConnectorScreenViewHolder =
                new ViewHolder.ChildCourseConnectorScreenViewHolder(rootView);

        childId = getActivity().getIntent().getExtras().getLong(Constants.CHILD_ID_EXTRA);

        databaseCursorAdapter = new DatabaseCursorAdapter(getContext(), null, this);
        childCourseConnectorScreenViewHolder.COURSES_LIST_VIEW.setAdapter(databaseCursorAdapter);
        childCourseConnectorScreenViewHolder.COURSES_LIST_VIEW
                .setEmptyView(childCourseConnectorScreenViewHolder.EMPTY_LIST_LAYOUT);
        // get child age.
        getChildAge();

        childCourseConnectorScreenViewHolder.REST_BUTTON.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        selectedCourses = new ArrayList<Long>();
                        restartLoader();
                    }
                }
        );

        childCourseConnectorScreenViewHolder.SUBMIT_BUTTON.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        // delete all previous selections.
                        getActivity().getContentResolver().delete(
                                DatabaseController.UriDatabase.getCourseChildTableWithChildIdUri(childId),
                                null,
                                null
                        );
                        Log.e("submit for items size",String.valueOf(selectedCourses.size()));

                        // Insert all selection
                        ArrayList<ContentValues> contentValuesArrayList = new ArrayList<ContentValues>();

                        for(final Long COURSE_ID : selectedCourses){
                            ContentValues contentValues = new ContentValues();
                            contentValues.put(
                                    DbContent.ChildCourseTable.CHILD_ID_COLUMN,
                                    childId
                            );
                            contentValues.put(
                                    DbContent.ChildCourseTable.COURSE_ID_COLUMN,
                                    COURSE_ID
                            );

                            contentValuesArrayList.add(contentValues);
                        }

                        ContentValues[] contentValues = new ContentValues[contentValuesArrayList.size()];
                        contentValuesArrayList.toArray(contentValues);

                        getActivity().getContentResolver().bulkInsert(
                                DatabaseController.UriDatabase.COURSE_CHILD_URI,
                                contentValues
                        );

                        openChildProfile();
                    }
                }
        );

        getLoaderManager().initLoader(Constants.LOADER_CHILD_COURSE_CONNECTOR, null, this);

        return rootView;

    }

    @Override
    public View newListView(ViewGroup viewGroup, Cursor cursor) {
        return LayoutInflater.from(getContext())
                .inflate(R.layout.child_course_recycle_view, viewGroup, false);
    }

    @Override
    public void bindListView(View view, final Cursor cursor) {
        Log.e("bind called","done");
        final ViewHolder.ChildCourseConnectorScreenViewHolder.CoursesViewHolder coursesViewHolder
                = new ViewHolder.ChildCourseConnectorScreenViewHolder.CoursesViewHolder(view);
        final Long COURSE_ID = cursor.getLong(DatabaseController.ProjectionDatabase.COURSE_ID);

        coursesViewHolder.COURSE_COST_TEXT_VIEW.setText(
                String.valueOf(
                        cursor.getDouble(DatabaseController.ProjectionDatabase.COURSE_COST)
                )
        );
        coursesViewHolder.COURSE_START_DATE_TEXT_VIEW.setText(
                Utility.getTimeFormat(
                        cursor.getLong(
                                DatabaseController.ProjectionDatabase.COURSE_START_DATE
                        )
                )
        );
        coursesViewHolder.COURSE_END_DATE_TEXT_VIEW.setText(
                Utility.getTimeFormat(
                        cursor.getLong(
                                DatabaseController.ProjectionDatabase.COURSE_END_DATE
                        )
                )
        );
        coursesViewHolder.COURSE_HOURS_TEXT_VIEW.setText(
                String.valueOf(
                        cursor.getInt(DatabaseController.ProjectionDatabase.COURSE_HOURS)
                )
        );
        coursesViewHolder.COURSE_NAME_TEXT_VIEW.setText(
                cursor.getString(DatabaseController.ProjectionDatabase.COURSE_NAME)
        );

        //check if course is selected before or not.
        Cursor cursor1 = getActivity().getContentResolver().query(
                DatabaseController.UriDatabase.getCourseChildTableWithChildIDAndCourseIdUri(
                        childId,
                        COURSE_ID
                ),
                new String[]{DbContent.ChildCourseTable.COURSE_ID_COLUMN},
                null,
                null,
                null
        );

        if(cursor1 != null){
            if(cursor1.getCount() > 0){
                Log.e("courses selected before",String.valueOf(cursor1.getCount()));
                if(!selectedCourses.contains(COURSE_ID))
                    selectedCourses.add(COURSE_ID);
            }
            cursor1.close();
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedCourses.contains(COURSE_ID)){
                    Log.e("b remove one item size",String.valueOf(selectedCourses.size()));
                    for(Long c : selectedCourses){
                        Log.e("id is ", String.valueOf(c));
                    }
                    selectedCourses.remove(COURSE_ID);
                    coursesViewHolder.COURSE_SELECT_IMAGE_VIEW.setVisibility(View.INVISIBLE);
                    Log.e("a remove one item size",String.valueOf(selectedCourses.size()));

                }else{
                    Log.e("b add one item size",String.valueOf(selectedCourses.size()));

                    selectedCourses.add(COURSE_ID);
                    coursesViewHolder.COURSE_SELECT_IMAGE_VIEW.setVisibility(View.VISIBLE);
                    Log.e("a add one item size",String.valueOf(selectedCourses.size()));
                }
            }
        });

        if(selectedCourses.contains(COURSE_ID)){
            coursesViewHolder.COURSE_SELECT_IMAGE_VIEW.setVisibility(View.VISIBLE);
        }else{
            coursesViewHolder.COURSE_SELECT_IMAGE_VIEW.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * get courses only have available positions with child's age range.
     * @param id
     * @param args
     * @return
     */
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        long dateAsMills = Utility.getCurrentDateAsMills();

        return new CursorLoader(
                getContext(),
                DatabaseController.UriDatabase.getCourseTableWithEndDateIdWithCompleteIdWithAgeRangeUri(childAge, dateAsMills),
                DatabaseController.ProjectionDatabase.COURSE_PROJECTION,
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.e("no of coming courses",String.valueOf(data.getCount()));
        databaseCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        databaseCursorAdapter.swapCursor(null);
    }

    private void restartLoader(){
        getLoaderManager().restartLoader(Constants.LOADER_CHILD_COURSE_CONNECTOR, null, this);
    }


    private void getChildAge(){
        Cursor cursor = getActivity().getContentResolver().query(
                DatabaseController.UriDatabase.getChildTableWithIdUri(childId),
                new String[]{DbContent.ChildTable.CHILD_AGE_COLUMN},
                null,
                null,
                null
        );

        if(cursor != null){
            if(cursor.getCount() > 0){
                cursor.moveToFirst();
                childAge = cursor.getInt(0);
            }
            cursor.close();
        }
    }

    private void openChildProfile(){
        Intent childProfile = new Intent(getContext(), ChildProfileActivity.class);
        childProfile.putExtra(Constants.CHILD_ID_EXTRA, childId);
        startActivity(childProfile);
        getActivity().finish();
    }

}
