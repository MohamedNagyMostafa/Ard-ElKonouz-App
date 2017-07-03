package com.nagy.mohamed.ardelkonouz.ui.ProfileScreens;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nagy.mohamed.ardelkonouz.R;
import com.nagy.mohamed.ardelkonouz.helper.Constants;
import com.nagy.mohamed.ardelkonouz.helper.Utility;
import com.nagy.mohamed.ardelkonouz.offlineDatabase.DatabaseController;
import com.nagy.mohamed.ardelkonouz.offlineDatabase.DbContent;
import com.nagy.mohamed.ardelkonouz.ui.InputScreens.CourseInputActivity;
import com.nagy.mohamed.ardelkonouz.ui.ViewHolder;

/**
 * A placeholder fragment containing a simple view.
 */
public class CourseProfileActivityFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_course_profile, container, false);
        ViewHolder.CourseProfileScreenViewHolder courseProfileScreenViewHolder =
                new ViewHolder.CourseProfileScreenViewHolder(rootView);
        final long COURSE_ID = getActivity().getIntent().getExtras().getLong(Constants.COURSE_ID_EXTRA);
        Log.e("course id is ",String.valueOf(COURSE_ID));
        Cursor courseData = getActivity().getContentResolver().query(
                DatabaseController.UriDatabase.getCourseTableWithIdUri(COURSE_ID),
                DatabaseController.ProjectionDatabase.COURSE_PROJECTION,
                null,
                null,
                null
        );
        if(courseData != null){
            if(courseData.getCount() > 0){
                Log.e("course id is ","founded");

                courseData.moveToFirst();
                setDataToView(courseData, courseProfileScreenViewHolder, COURSE_ID);
            }
            courseData.close();
        }

        courseProfileScreenViewHolder.COURSE_EDIT_BUTTON.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent courseInputScreen = new Intent(getContext(), CourseInputActivity.class);
                        courseInputScreen.putExtra(Constants.COURSE_ID_EXTRA, COURSE_ID);
                        courseInputScreen.putExtra(Constants.INPUT_TYPE_EXTRA, Constants.INPUT_EDIT_EXTRA);
                        startActivity(courseInputScreen);
                    }
                }
        );

        return rootView;
    }

    private void setDataToView(Cursor cursor,
                               ViewHolder.CourseProfileScreenViewHolder courseProfileScreenViewHolder,
                               final long COURSE_ID){

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
//        final Integer COURSE_SHIFTS_NUMBER =
//                cursor.getInt(DatabaseController.ProjectionDatabase.COURSE_SHIFT_NUMBER_COLUMN);
        final Long COURSE_SHIFT_END_DATE =
                cursor.getLong(DatabaseController.ProjectionDatabase.COURSE_SHIFT_END_DATE_COLUMN);

        StringBuilder nextSessionDay = new StringBuilder("");
        int REMAINING_SESSIONS;
        int FINISHED_SESSIONS;

            REMAINING_SESSIONS = Utility.getRemainsDaysWithNextDay(
                    COURSE_END_DATE,
                    COURSE_START_DATE,
                    COURSE_SHIFT_END_DATE,
                    COURSE_SESSIONS_DAYS,
                    nextSessionDay,
                    getContext()
            );

            FINISHED_SESSIONS = COURSE_SESSIONS_NUMBER - REMAINING_SESSIONS ;

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
        courseProfileScreenViewHolder.COURSE_NEXT_SESSION_DAY_TEXT_VIEW.setText(nextSessionDay);
        courseProfileScreenViewHolder.COURSE_SESSIONS_DAYS_TEXT_VIEW.setText(
                Utility.getDaysAsString(COURSE_SESSIONS_DAYS)
        );
        courseProfileScreenViewHolder.COURSE_STATE_TEXT_VIEW.setText(
                Utility.decodeCourseStateByInt(COURSE_STATE, getContext())
        );
        courseProfileScreenViewHolder.COURSE_INSTRUCTOR_NAME_TEXT_VIEW.setText(
                getCourseInstructorName(COURSE_ID)
        );
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
}
