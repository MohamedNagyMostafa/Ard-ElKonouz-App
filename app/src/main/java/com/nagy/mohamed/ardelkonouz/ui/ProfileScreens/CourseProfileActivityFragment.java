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
        courseProfileScreenViewHolder.COURSE_NAME_TEXT_VIEW.setText(
                cursor.getString(
                        DatabaseController.ProjectionDatabase.COURSE_NAME
                )
        );
        courseProfileScreenViewHolder.COURSE_COST_TEXT_VIEW.setText(
                String.valueOf(
                        cursor.getDouble(
                                DatabaseController.ProjectionDatabase.COURSE_COST
                        )
                )
        );
        courseProfileScreenViewHolder.COURSE_HOURS_TEXT_VIEW.setText(
                String.valueOf(
                        cursor.getDouble(
                                DatabaseController.ProjectionDatabase.COURSE_HOURS
                        )
                )
        );
        courseProfileScreenViewHolder.COURSE_LEVEL_TEXT_VIEW.setText(
                String.valueOf(
                        cursor.getInt(
                                DatabaseController.ProjectionDatabase.COURSE_LEVEL
                        )
                )
        );
        courseProfileScreenViewHolder.COURSE_START_DATE_TEXT_VIEW.setText(
                Utility.getTimeFormat(
                        cursor.getLong(
                                DatabaseController.ProjectionDatabase.COURSE_START_DATE
                        )
                )
        );
        courseProfileScreenViewHolder.COURSE_END_DATE_TEXT_VIEW.setText(
                Utility.getTimeFormat(
                        cursor.getLong(
                                DatabaseController.ProjectionDatabase.COURSE_END_DATE
                        )
                )
        );
        courseProfileScreenViewHolder.COURSE_SALARY_PER_CHILD_TEXT_VIEW.setText(
                String.valueOf(
                        cursor.getDouble(
                                DatabaseController.ProjectionDatabase.COURSE_SALARY_PER_CHILD
                        )
                )
        );
        courseProfileScreenViewHolder.COURSE_START_TEXT_VIEW.setText(
                Utility.decodeCourseStateByInt(
                        cursor.getInt(
                                DatabaseController.ProjectionDatabase.COURSE_AVAILABLE_POSITIONS
                        ),getContext()
                )
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
