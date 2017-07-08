package com.nagy.mohamed.ardelkonouz.ui.ProfileScreens;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nagy.mohamed.ardelkonouz.R;
import com.nagy.mohamed.ardelkonouz.component.Shift;
import com.nagy.mohamed.ardelkonouz.helper.Constants;
import com.nagy.mohamed.ardelkonouz.helper.Utility;
import com.nagy.mohamed.ardelkonouz.offlineDatabase.DatabaseController;
import com.nagy.mohamed.ardelkonouz.offlineDatabase.DbContent;
import com.nagy.mohamed.ardelkonouz.ui.InputScreens.CourseInputActivity;
import com.nagy.mohamed.ardelkonouz.ui.ViewHolder;
import com.nagy.mohamed.ardelkonouz.ui.adapter.OnShiftDeleteListener;
import com.nagy.mohamed.ardelkonouz.ui.adapter.RecycleViewCourseProfileAdapter;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class CourseProfileActivityFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<Cursor>{
    
    private RecycleViewCourseProfileAdapter recycleViewCourseProfileAdapter;
    private Long courseId;
    private OnShiftDeleteListener onShiftDeleteListener =
            new OnShiftDeleteListener() {
                @Override
                public void OnClickListener(Long SHIFT_ID) {
                    getActivity().getContentResolver().delete(
                            DatabaseController.UriDatabase.getShiftTableWithIdUri(SHIFT_ID),
                            null,
                            null
                    );

                    // get current shifts.
                    Cursor shiftCursor = getActivity().getContentResolver().query(
                            DatabaseController.UriDatabase.getShiftWithCourseId(courseId),
                            DatabaseController.ProjectionDatabase.SHIFT_TABLE_PROJECTION,
                            null,
                            null,
                            null
                    );
                    ArrayList<Shift> shifts = new ArrayList<>();

                    if(shiftCursor != null){
                        while(shiftCursor.moveToNext()){
                            Shift shift = new Shift(
                                    shiftCursor.getLong(
                                            DatabaseController.ProjectionDatabase.SHIFT_START_DATE_COLUMN
                                    ),
                                    shiftCursor.getLong(
                                            DatabaseController.ProjectionDatabase.SHIFT_END_DATE_COLUMN
                                    ),
                                    courseId
                            );

                            shifts.add(shift);
                        }
                        shiftCursor.close();
                    }

                    // get Course date data.

                    Cursor courseCursor = getActivity().getContentResolver().query(
                            DatabaseController.UriDatabase.getCourseTableWithIdUri(courseId),
                            DatabaseController.ProjectionDatabase.COURSE_DATE_PROJECTION,
                            null,
                            null,
                            null
                    );


                    if(courseCursor != null){
                        if(courseCursor.getCount() > 0){
                            courseCursor.moveToNext();

                            final String COURSE_SESSION_DAYS = courseCursor.getString(
                                    DatabaseController.ProjectionDatabase.COURSE_DATE_DAYS
                            );
                            final int COURSE_SESSION_NUMBER = courseCursor.getInt(
                                    DatabaseController.ProjectionDatabase.COURSE_DATE_SESSIONS_NUMBER
                            );
                            final Long COURSE_START_DATE = courseCursor.getLong(
                                    DatabaseController.ProjectionDatabase.COURSE_DATE_START_DATE
                            );

                            final Long COURSE_END_DATE = Utility.getEndDate(
                                    shifts,
                                    COURSE_SESSION_DAYS,
                                    COURSE_SESSION_NUMBER,
                                    COURSE_START_DATE
                            );

                            final int REMAINS_SESSIONS = Utility.getRemainDays(
                                    shifts,
                                    COURSE_SESSION_DAYS,
                                    COURSE_START_DATE,
                                    COURSE_END_DATE,
                                    COURSE_SESSION_NUMBER
                            );

                            final int FINISHED_SESSIONS = COURSE_SESSION_NUMBER - REMAINS_SESSIONS;

                            ContentValues contentValues = new ContentValues();
                            contentValues.put(DbContent.CourseTable.COURSE_END_DATE_COLUMN, COURSE_END_DATE);

                            getActivity().getContentResolver().update(
                                    DatabaseController.UriDatabase.getCourseTableWithIdUri(courseId),
                                    contentValues,
                                    null,
                                    null
                            );

                            courseProfileScreenViewHolder.COURSE_END_DATE_TEXT_VIEW.setText(
                                    String.valueOf(
                                            Utility.getTimeFormat(
                                            COURSE_END_DATE
                                            )
                                    )
                            );

                            courseProfileScreenViewHolder.COURSE_NEXT_SESSION_DAY_TEXT_VIEW.setText(
                                    String.valueOf(
                                            Utility.getTimeFormat(
                                                    Utility.getNextSessionDay(
                                                            shifts,
                                                            COURSE_SESSION_DAYS,
                                                            COURSE_END_DATE,
                                                            COURSE_START_DATE
                                                    )
                                            )
                                    )
                            );

                            courseProfileScreenViewHolder.COURSE_REMAINING_SESSIONS_TEXT_VIEW.setText(
                                    String.valueOf(
                                            REMAINS_SESSIONS
                                    )
                            );

                            courseProfileScreenViewHolder.COURSE_FINISHED_SESSIONS_TEXT_VIEW.setText(
                                    String.valueOf(
                                            FINISHED_SESSIONS
                                    )
                            );


                        }
                        courseCursor.close();
                    }

                    restartLoader();
                }
            };

    private ViewHolder.CourseProfileScreenViewHolder courseProfileScreenViewHolder;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_course_profile, container, false);
        courseProfileScreenViewHolder = new ViewHolder.CourseProfileScreenViewHolder(rootView);
        courseId = getActivity().getIntent().getExtras().getLong(Constants.COURSE_ID_EXTRA);
        recycleViewCourseProfileAdapter = new RecycleViewCourseProfileAdapter(getContext(), onShiftDeleteListener);
        
        courseProfileScreenViewHolder.COURSE_SHIFTS_RECYCLE_VIEW.setAdapter(recycleViewCourseProfileAdapter);
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        LinearSnapHelper snapHelper = new LinearSnapHelper();
        
        courseProfileScreenViewHolder.COURSE_SHIFTS_RECYCLE_VIEW.setLayoutManager(linearLayoutManager);
        snapHelper.attachToRecyclerView(courseProfileScreenViewHolder.COURSE_SHIFTS_RECYCLE_VIEW);
        
        Cursor cursor = getActivity().getContentResolver().query(
                DatabaseController.UriDatabase.getShiftWithCourseJoinId(courseId),
                DatabaseController.ProjectionDatabase.SHIFT_COURSE_JOIN_PROJECTION,
                null,
                null,
                null
        );

        if(cursor != null){
            if(cursor.getCount() > 0){
                Log.e("course id is ","founded");
                cursor.moveToFirst();
                ArrayList<Shift> shifts = new ArrayList<>();

                do {
                    Shift shift =
                            new Shift(
                                    cursor.getLong(
                                            DatabaseController.ProjectionDatabase.SHIFT_COURSE_JOIN_START_DATE_COLUMN
                                    ),
                                    cursor.getLong(
                                            DatabaseController.ProjectionDatabase.SHIFT_COURSE_JOIN_END_DATE_COLUMN
                                    ),
                                    courseId
                            );

                    shifts.add(shift);

                }while (cursor.moveToNext());

                cursor.moveToFirst();

                setDataToView(cursor, courseProfileScreenViewHolder, courseId, shifts);
            }else{
                Cursor cursorCourses = getActivity().getContentResolver().query(
                        DatabaseController.UriDatabase.getCourseTableWithIdUri(courseId),
                        DatabaseController.ProjectionDatabase.COURSE_PROJECTION,
                        null,
                        null,
                        null
                );

                if(cursorCourses != null){
                    if(cursorCourses.getCount() > 0){
                        cursorCourses.moveToFirst();
                        setDataToView(cursorCourses, courseProfileScreenViewHolder, courseId);
                    }
                    cursorCourses.close();
                }
            }
            cursor.close();
        }

        courseProfileScreenViewHolder.COURSE_EDIT_BUTTON.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent courseInputScreen = new Intent(getContext(), CourseInputActivity.class);
                        courseInputScreen.putExtra(Constants.COURSE_ID_EXTRA, courseId);
                        courseInputScreen.putExtra(Constants.INPUT_TYPE_EXTRA, Constants.INPUT_EDIT_EXTRA);
                        startActivity(courseInputScreen);
                    }
                }
        );

        
        getLoaderManager().initLoader(Constants.LOADER_SHIFT_COURSE_PROFILE, null, this);

        return rootView;
    }

    private void setDataToView(Cursor cursor,
                               ViewHolder.CourseProfileScreenViewHolder courseProfileScreenViewHolder,
                               final long courseId, ArrayList<Shift> shifts){

        final String COURSE_NAME =
                cursor.getString(DatabaseController.ProjectionDatabase.SHIFT_COURSE_JOIN_COURSE_NAME_COLUMN);
        final double COURSE_COST =
                cursor.getDouble(DatabaseController.ProjectionDatabase.SHIFT_COURSE_JOIN_COURSE_COST_COLUMN);
        final double COURSE_HOURS =
                cursor.getDouble(DatabaseController.ProjectionDatabase.SHIFT_COURSE_JOIN_COURSE_HOURS_COLUMN);
        final int COURSE_LEVEL =
                cursor.getInt(DatabaseController.ProjectionDatabase.SHIFT_COURSE_JOIN_COURSE_LEVEL_COLUMN);
        final long COURSE_START_DATE =
                cursor.getLong(DatabaseController.ProjectionDatabase.SHIFT_COURSE_JOIN_COURSE_START_DATE_COLUMN);
        final long COURSE_END_DATE =
                cursor.getLong(DatabaseController.ProjectionDatabase.SHIFT_COURSE_JOIN_COURSE_END_DATE_COLUMN);
        final double COURSE_PERCENT_PER_CHILD =
                cursor.getDouble(DatabaseController.ProjectionDatabase.SHIFT_COURSE_JOIN_COURSE_SALARY_PER_CHILD);
        final int COURSE_STATE =
                cursor.getInt(DatabaseController.ProjectionDatabase.SHIFT_COURSE_JOIN_COURSE_AVAILABLE_POSITIONS_COLUMN);
        final int COURSE_SESSIONS_NUMBER =
                cursor.getInt(DatabaseController.ProjectionDatabase.SHIFT_COURSE_JOIN_COURSE_SESSIONS_NUMBER_COLUMN);
        final String COURSE_SESSIONS_DAYS =
                cursor.getString(DatabaseController.ProjectionDatabase.SHIFT_COURSE_JOIN_COURSE_DAYS_COLUMN);
        final int COURSE_START_AGE =
                cursor.getInt(DatabaseController.ProjectionDatabase.SHIFT_COURSE_JOIN_COURSE_START_AGE_COLUMN);
        final int COURSE_END_AGE =
                cursor.getInt(DatabaseController.ProjectionDatabase.SHIFT_COURSE_JOIN_COURSE_END_AGE_COLUMN);

        final int REMAINING_SESSIONS = Utility.getRemainDays(
                shifts,
                COURSE_SESSIONS_DAYS,
                COURSE_START_DATE,
                COURSE_END_DATE,
                COURSE_SESSIONS_NUMBER
        );

        int FINISHED_SESSIONS = COURSE_SESSIONS_NUMBER - REMAINING_SESSIONS;

        final Long NEXT_SESSION_DAY = Utility.getNextSessionDay(
                shifts,
                COURSE_SESSIONS_DAYS,
                COURSE_END_DATE,
                COURSE_START_DATE
        );
        final String NEXT_SESSION_DAY_STRING =
                (NEXT_SESSION_DAY == Utility.getCurrentDateAsMills())? "Today" :
                        Utility.getTimeFormat(NEXT_SESSION_DAY);



        courseProfileScreenViewHolder.COURSE_NAME_TEXT_VIEW.setText(COURSE_NAME);
        courseProfileScreenViewHolder.COURSE_COST_TEXT_VIEW.setText(String.valueOf(COURSE_COST));
        courseProfileScreenViewHolder.COURSE_HOURS_TEXT_VIEW.setText(String.valueOf(COURSE_HOURS));
        courseProfileScreenViewHolder.COURSE_LEVEL_TEXT_VIEW.setText(String.valueOf(COURSE_LEVEL));
        courseProfileScreenViewHolder.COURSE_START_DATE_TEXT_VIEW.setText(Utility.getTimeFormat(COURSE_START_DATE));
        courseProfileScreenViewHolder.COURSE_END_DATE_TEXT_VIEW.setText(Utility.getTimeFormat(COURSE_END_DATE));
        courseProfileScreenViewHolder.COURSE_SALARY_PER_CHILD_TEXT_VIEW.setText(new StringBuilder("").append(
                String.valueOf(COURSE_PERCENT_PER_CHILD)).append("%"));
        courseProfileScreenViewHolder.COURSE_SESSIONS_NUMBER_TEXT_VIEW.setText(String.valueOf(COURSE_SESSIONS_NUMBER));
        courseProfileScreenViewHolder.COURSE_REMAINING_SESSIONS_TEXT_VIEW.setText(String.valueOf(REMAINING_SESSIONS));
        courseProfileScreenViewHolder.COURSE_FINISHED_SESSIONS_TEXT_VIEW.setText(String.valueOf(FINISHED_SESSIONS));
        courseProfileScreenViewHolder.COURSE_NEXT_SESSION_DAY_TEXT_VIEW.setText(NEXT_SESSION_DAY_STRING);
        courseProfileScreenViewHolder.COURSE_SESSIONS_DAYS_TEXT_VIEW.setText(Utility.getDaysAsString(COURSE_SESSIONS_DAYS));
        courseProfileScreenViewHolder.COURSE_STATE_TEXT_VIEW.setText(Utility.decodeCourseStateByInt(COURSE_STATE, getContext()));
        courseProfileScreenViewHolder.COURSE_INSTRUCTOR_NAME_TEXT_VIEW.setText(getCourseInstructorName(courseId));
        courseProfileScreenViewHolder.COURSE_AGE_RANGE_TEXT_VIEW.setText(
                Utility.decodeAgeRangeByInt(COURSE_START_AGE,COURSE_END_AGE));

    }

    private void setDataToView(Cursor cursor,
                               ViewHolder.CourseProfileScreenViewHolder courseProfileScreenViewHolder,
                               final long courseId){

        final String COURSE_NAME =
                cursor.getString(DatabaseController.ProjectionDatabase.COURSE_NAME);
        final double COURSE_COST =
                cursor.getDouble(DatabaseController.ProjectionDatabase.COURSE_COST);
        final double COURSE_HOURS =
                cursor.getDouble(DatabaseController.ProjectionDatabase.COURSE_HOURS);
        final int COURSE_LEVEL =
                cursor.getInt(DatabaseController.ProjectionDatabase.COURSE_LEVEL);
        final long COURSE_START_DATE =
                cursor.getLong(DatabaseController.ProjectionDatabase.COURSE_START_DATE);
        final long COURSE_END_DATE =
                cursor.getLong(DatabaseController.ProjectionDatabase.COURSE_END_DATE);
        final double COURSE_PERCENT_PER_CHILD =
                cursor.getDouble(DatabaseController.ProjectionDatabase.COURSE_SALARY_PER_CHILD);
        final int COURSE_STATE =
                cursor.getInt(DatabaseController.ProjectionDatabase.COURSE_AVAILABLE_POSITIONS);
        final int COURSE_SESSIONS_NUMBER =
                cursor.getInt(DatabaseController.ProjectionDatabase.COURSE_SESSIONS_NUMBER_COLUMN);
        final String COURSE_SESSIONS_DAYS =
                cursor.getString(DatabaseController.ProjectionDatabase.COURSE_DAYS_COLUMN);

        final int REMAINING_SESSIONS = Utility.getRemainDays(
                null,
                COURSE_SESSIONS_DAYS,
                COURSE_START_DATE,
                COURSE_END_DATE,
                COURSE_SESSIONS_NUMBER
        );

        int FINISHED_SESSIONS = COURSE_SESSIONS_NUMBER - REMAINING_SESSIONS;
        final Long NEXT_SESSION_DAY = Utility.getNextSessionDay(
                null,
                COURSE_SESSIONS_DAYS,
                COURSE_END_DATE,
                COURSE_START_DATE
        );


        courseProfileScreenViewHolder.COURSE_NAME_TEXT_VIEW.setText(COURSE_NAME);
        courseProfileScreenViewHolder.COURSE_COST_TEXT_VIEW.setText(String.valueOf(COURSE_COST));
        courseProfileScreenViewHolder.COURSE_HOURS_TEXT_VIEW.setText(String.valueOf(COURSE_HOURS));
        courseProfileScreenViewHolder.COURSE_LEVEL_TEXT_VIEW.setText(String.valueOf(COURSE_LEVEL));
        courseProfileScreenViewHolder.COURSE_START_DATE_TEXT_VIEW.setText(Utility.getTimeFormat(COURSE_START_DATE));
        courseProfileScreenViewHolder.COURSE_END_DATE_TEXT_VIEW.setText(Utility.getTimeFormat(COURSE_END_DATE));
        courseProfileScreenViewHolder.COURSE_SALARY_PER_CHILD_TEXT_VIEW.setText(new StringBuilder("").append(
                String.valueOf(COURSE_PERCENT_PER_CHILD)).append("%"));
        courseProfileScreenViewHolder.COURSE_SESSIONS_NUMBER_TEXT_VIEW.setText(String.valueOf(COURSE_SESSIONS_NUMBER));
        courseProfileScreenViewHolder.COURSE_REMAINING_SESSIONS_TEXT_VIEW.setText(String.valueOf(REMAINING_SESSIONS));
        courseProfileScreenViewHolder.COURSE_FINISHED_SESSIONS_TEXT_VIEW.setText(String.valueOf(FINISHED_SESSIONS));
        courseProfileScreenViewHolder.COURSE_NEXT_SESSION_DAY_TEXT_VIEW.setText(Utility.getTimeFormat(NEXT_SESSION_DAY));
        courseProfileScreenViewHolder.COURSE_SESSIONS_DAYS_TEXT_VIEW.setText(Utility.getDaysAsString(COURSE_SESSIONS_DAYS));
        courseProfileScreenViewHolder.COURSE_STATE_TEXT_VIEW.setText(Utility.decodeCourseStateByInt(COURSE_STATE, getContext()));
        courseProfileScreenViewHolder.COURSE_INSTRUCTOR_NAME_TEXT_VIEW.setText(getCourseInstructorName(courseId));
        courseProfileScreenViewHolder.COURSE_AGE_RANGE_TEXT_VIEW.setText(
                Utility.decodeAgeRangeByInt(
                        cursor.getInt(
                                DatabaseController.ProjectionDatabase.COURSE_START_AGE
                        ),
                        cursor.getInt(
                                DatabaseController.ProjectionDatabase.COURSE_END_AGE
                        )
                )
        );

    }


    private String getCourseInstructorName(long courseId){
        String instructorName = null;
        Cursor cursor = getActivity().getContentResolver().query(
                DatabaseController.UriDatabase.getCourseInstructorTableWithCourseIdUri(courseId),
                new String[]{DbContent.InstructorTable.INSTRUCTOR_NAME_COLUMN},
                null,
                null,
                null
        );

        if(cursor != null){
            if(cursor.getCount() > 0){
                cursor.moveToFirst();
                instructorName = cursor.getString(0);
            }
            cursor.close();
        }

        return (instructorName == null)? getContext().getString(R.string.empty_info):
                instructorName;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(
                getContext(),
                DatabaseController.UriDatabase.getShiftWithCourseId(courseId),
                DatabaseController.ProjectionDatabase.SHIFT_TABLE_PROJECTION,
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // set empty view
        if(data.getCount() > 0){
             courseProfileScreenViewHolder.COURSE_SHIFT_EMPTY_LAYOUT.setVisibility(View.GONE);
        }else{
            courseProfileScreenViewHolder.COURSE_SHIFT_EMPTY_LAYOUT.setVisibility(View.VISIBLE);
        }
        recycleViewCourseProfileAdapter.setCursor(data);
        recycleViewCourseProfileAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        recycleViewCourseProfileAdapter.setCursor(null);
        recycleViewCourseProfileAdapter.notifyDataSetChanged();
    }

    public void restartLoader(){
        getLoaderManager().restartLoader(Constants.LOADER_SHIFT_COURSE_PROFILE, null, this);
    }
}
