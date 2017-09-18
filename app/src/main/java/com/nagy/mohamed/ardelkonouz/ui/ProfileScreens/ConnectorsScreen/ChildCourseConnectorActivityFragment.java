package com.nagy.mohamed.ardelkonouz.ui.ProfileScreens.ConnectorsScreen;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.nagy.mohamed.ardelkonouz.R;
import com.nagy.mohamed.ardelkonouz.component.Shift;
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
    private ArrayList<Long> previousCourseSelected;
    private long childId;
    private int childAge;


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_child_course_connector, container, false);
        selectedCourses = new ArrayList<>();
        previousCourseSelected = new ArrayList<>();
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
                                DatabaseController.UriDatabase.getSectionChildTableWithChildIdUri(childId),
                                null,
                                null
                        );

                        // Insert all selection
                        ArrayList<ContentValues> contentValuesArrayList = new ArrayList<ContentValues>();

                        for(final Long SECTION_ID : selectedCourses){
                            ContentValues contentValues = new ContentValues();
                            contentValues.put(
                                    DbContent.ChildSectionTable.CHILD_ID_COLUMN,
                                    childId
                            );
                            contentValues.put(
                                    DbContent.ChildSectionTable.SECTION_ID_COLUMN,
                                    SECTION_ID
                            );

                            contentValuesArrayList.add(contentValues);
                        }

                        ContentValues[] contentValues = new ContentValues[contentValuesArrayList.size()];
                        contentValuesArrayList.toArray(contentValues);

                        getActivity().getContentResolver().bulkInsert(
                                DatabaseController.UriDatabase.SECTION_CHILD_URI,
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
        final ViewHolder.ChildCourseConnectorScreenViewHolder.CoursesViewHolder coursesViewHolder
                = new ViewHolder.ChildCourseConnectorScreenViewHolder.CoursesViewHolder(view);
        final Long SECTION_ID = cursor.getLong(DatabaseController.ProjectionDatabase.CHILD_COURSE_CONNECTOR_ID);
        final Long SECTION_START_DATE = cursor.getLong(
                DatabaseController.ProjectionDatabase.CHILD_COURSE_CONNECTOR_SECTION_START_DATE
        );
        final String SECTION_DAYS = cursor.getString(
                DatabaseController.ProjectionDatabase.CHILD_COURSE_CONNECTOR_SECTION_DAYS
        );

        coursesViewHolder.COURSE_COST_TEXT_VIEW.setText(
                String.valueOf(
                        cursor.getDouble(DatabaseController.ProjectionDatabase.CHILD_COURSE_CONNECTOR_COURSE_COST)
                )
        );

        if(!SECTION_START_DATE.equals(Constants.NULL)) {
            coursesViewHolder.COURSE_START_DATE_TEXT_VIEW.setText(
                    Utility.getTimeFormat(
                            cursor.getLong(
                                    DatabaseController.ProjectionDatabase.CHILD_COURSE_CONNECTOR_SECTION_START_DATE
                            )
                    )
            );
            coursesViewHolder.COURSE_END_DATE_TEXT_VIEW.setText(
                    Utility.getTimeFormat(
                            cursor.getLong(
                                    DatabaseController.ProjectionDatabase.CHILD_COURSE_CONNECTOR_SECTION_END_DATE
                            )
                    )
            );
        }else{
            coursesViewHolder.COURSE_START_DATE_TEXT_VIEW.setText(
                    getString(R.string.empty_info)
            );
            coursesViewHolder.COURSE_END_DATE_TEXT_VIEW.setText(
                    getString(R.string.empty_info)
            );
        }

        if(Utility.isDaysSelected(SECTION_DAYS)) {
            coursesViewHolder.COURSE_DAYS_TEXT_VIEW.setText(
                    Utility.getDaysAsString(SECTION_DAYS)
            );
        }else{
            coursesViewHolder.COURSE_DAYS_TEXT_VIEW.setText(
                    getString(R.string.empty_info)
            );
        }
        coursesViewHolder.SECTION_LEVEL_TEXT_VIEW.setText(
                String.valueOf(
                        cursor.getInt(
                                DatabaseController.ProjectionDatabase.CHILD_COURSE_CONNECTOR_SECTION_LEVEL
                        )
                )
        );
        coursesViewHolder.COURSE_NAME_TEXT_VIEW.setText(
                cursor.getString(DatabaseController.ProjectionDatabase.CHILD_COURSE_CONNECTOR_COURSE_NAME)
        );

        coursesViewHolder.SECTION_NAME_TEXT_VIEW.setText(
                String.valueOf(
                        "Sec. " +
                        String.valueOf(
                                cursor.getLong(DatabaseController.ProjectionDatabase.CHILD_COURSE_CONNECTOR_SECTION_NAME)
                        )
                )
        );

        //check if course is selected before or not.
        Cursor cursor1 = getActivity().getContentResolver().query(
                DatabaseController.UriDatabase.getSectionChildTableWithChildIDAndSectionIdUri(
                        childId,
                        SECTION_ID
                ),
                new String[]{DbContent.ChildSectionTable.SECTION_ID_COLUMN},
                null,
                null,
                null
        );

        if(cursor1 != null){
            if(cursor1.getCount() > 0){
                if(!selectedCourses.contains(SECTION_ID)) {
                    selectedCourses.add(SECTION_ID);
                    previousCourseSelected.add(SECTION_ID);
                }
            }
            cursor1.close();
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedCourses.contains(SECTION_ID)){
                    selectedCourses.remove(SECTION_ID);
                    coursesViewHolder.COURSE_SELECT_IMAGE_VIEW.setVisibility(View.INVISIBLE);

                }else{

                    selectedCourses.add(SECTION_ID);
                    coursesViewHolder.COURSE_SELECT_IMAGE_VIEW.setVisibility(View.VISIBLE);
                }
            }
        });

        if(selectedCourses.contains(SECTION_ID)){
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
                DatabaseController.UriDatabase.getSectionTableWithEndDateIdWithCompleteIdWithAgeRangeUri(childAge, dateAsMills),
                DatabaseController.ProjectionDatabase.CHILD_COURSE_CONNECTOR_PROJECTION,
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
        if(selectedCourses.size() - previousCourseSelected.size() > 0)
            displayTotalCost();
        startActivity(childProfile);
        getActivity().finish();
    }

    private void displayTotalCost(){
        double totalCost = 0;
        ArrayList<Long> newSelectionCourses =
                new ArrayList<>(selectedCourses.size() - previousCourseSelected.size());

        for(Long id: selectedCourses){
            if(!previousCourseSelected.contains(id))
                newSelectionCourses.add(id);
        }

        Cursor coursesCost = getActivity().getContentResolver().query(
                DatabaseController.UriDatabase.getCourseSelection(newSelectionCourses),
                DatabaseController.ProjectionDatabase.SECTION_DATE_COST_PROJECTION,
                null,
                null,
                null
        );

        if (coursesCost != null) {

            totalCost = getCost(coursesCost,newSelectionCourses);

            coursesCost.close();
        }

        Toast.makeText(getContext(), "Total Cost Is : " + String.valueOf(totalCost),Toast.LENGTH_LONG).show();
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putStringArrayList(Constants.SaveState.CONNECTOR_CHILD_COURSE_SELECTION,
                Utility.convertCoursesIdToString(selectedCourses));
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if(savedInstanceState != null) {
            selectedCourses = Utility.convertCoursesIdToLong(
                    savedInstanceState.getStringArrayList(
                            Constants.SaveState.CONNECTOR_CHILD_COURSE_SELECTION
                    ));
        }
    }

    private double getCost(Cursor coursesCostCursor, ArrayList<Long> selectionCoursesIds){
        double totalCost = 0;
        while(coursesCostCursor.moveToNext()){
            final Double COURSE_COST = coursesCostCursor.getDouble(DatabaseController.ProjectionDatabase.SECTION_DATE_COST_COURSE_COST);
            final Long SECTION_ID = coursesCostCursor.getLong(DatabaseController.ProjectionDatabase.SECTION_DATE_COST_SECTION_ID);
            final String SECTION_DAYS = coursesCostCursor.getString(DatabaseController.ProjectionDatabase.SECTION_DATE_COST_DAYS);
            final Long SECTION_START_DATE = coursesCostCursor.getLong(DatabaseController.ProjectionDatabase.SECTION_DATE_COST_START_DATE);
            final Long SECTION_END_DATE = coursesCostCursor.getLong(DatabaseController.ProjectionDatabase.SECTION_DATE_COST_START_DATE);
            final Integer SECTIONS_NUMBER = coursesCostCursor.getInt(DatabaseController.ProjectionDatabase.SECTION_DATE_COST_SESSIONS_NUMBER);

            if(SECTION_START_DATE.equals(Constants.NULL)){
                ArrayList<Shift> sectionShifts = new ArrayList<>();

                Cursor shiftCursor = getActivity().getContentResolver().query(
                        DatabaseController.UriDatabase.getShiftWithSectionId(SECTION_ID),
                        DatabaseController.ProjectionDatabase.SHIFT_TABLE_PROJECTION,
                        null,
                        null,
                        null
                );
                if(shiftCursor != null){
                    while(shiftCursor.moveToNext()) {
                        final Long SHIFT_START_DATE = shiftCursor.getLong(DatabaseController.ProjectionDatabase.SHIFT_START_DATE_COLUMN);
                        final Long SHIFT_END_DATE = shiftCursor.getLong(DatabaseController.ProjectionDatabase.SHIFT_END_DATE_COLUMN);
                        sectionShifts.add(new Shift(SHIFT_START_DATE, SHIFT_END_DATE, SECTION_ID));
                    }
                    
                    shiftCursor.close();

                    int remainsSections = Utility.getRemainDays(sectionShifts, SECTION_DAYS, SECTION_START_DATE,  SECTION_END_DATE, SECTIONS_NUMBER);

                    if(remainsSections + 1 < SECTIONS_NUMBER){
                        totalCost = (COURSE_COST / Long.parseLong(String.valueOf(SECTIONS_NUMBER))) * remainsSections;
                    }

                }
            }else{
                totalCost += COURSE_COST;
            }
        }
        return totalCost;
    }
}
