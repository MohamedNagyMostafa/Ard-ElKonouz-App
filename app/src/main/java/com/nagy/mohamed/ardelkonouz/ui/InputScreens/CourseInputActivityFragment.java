package com.nagy.mohamed.ardelkonouz.ui.InputScreens;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.nagy.mohamed.ardelkonouz.R;
import com.nagy.mohamed.ardelkonouz.helper.Constants;
import com.nagy.mohamed.ardelkonouz.offlineDatabase.DatabaseController;
import com.nagy.mohamed.ardelkonouz.offlineDatabase.DbContent;
import com.nagy.mohamed.ardelkonouz.ui.ProfileScreens.CourseProfileActivity;
import com.nagy.mohamed.ardelkonouz.ui.ViewHolder;

/**
 * A placeholder fragment containing a simple view.
 */
public class CourseInputActivityFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_course_input, container, false);
        ViewHolder.CourseInputScreenViewHolder courseInputScreenViewHolder =
                new ViewHolder.CourseInputScreenViewHolder(rootView);

        final String INPUT_TYPE = getActivity().getIntent().getExtras().getString(Constants.INPUT_TYPE_EXTRA);

        if(INPUT_TYPE.equals(Constants.INPUT_ADD_EXTRA)) {
            setOptionsAsAddNewCourse(courseInputScreenViewHolder);
        }else {
            setOptionsAsEditCourse(courseInputScreenViewHolder);
        }

        return rootView;

    }


    private void setOptionsAsEditCourse(final ViewHolder.CourseInputScreenViewHolder courseInputScreenViewHolder){
        final long COURSE_ID = getActivity().getIntent().getExtras().getLong(Constants.COURSE_ID_EXTRA);

        final Cursor cursor = getActivity().getContentResolver().query(
                DatabaseController.UriDatabase.getCourseTableWithIdUri(COURSE_ID),
                DatabaseController.ProjectionDatabase.COURSE_PROJECTION,
                null,
                null,
                null
        );

        if(cursor != null){
            if(cursor.getCount() > 0){
                cursor.moveToFirst();

                courseInputScreenViewHolder.COURSE_NAME_EDIT_TEXT.setText(
                        cursor.getString(
                                DatabaseController.ProjectionDatabase.COURSE_NAME
                        )
                );
                courseInputScreenViewHolder.COURSE_COST_EDIT_TEXT.setText(
                        String.valueOf(
                                cursor.getDouble(
                                        DatabaseController.ProjectionDatabase.COURSE_COST
                                )
                        )
                );
                courseInputScreenViewHolder.COURSE_SALARY_PER_CHILD_EDIT_TEXT.setText(
                        String.valueOf(
                                cursor.getDouble(
                                        DatabaseController.ProjectionDatabase.COURSE_SALARY_PER_CHILD
                                )
                        )
                );
                courseInputScreenViewHolder.COURSE_AGE_RANGE_FROM_EDIT_TEXT.setText(
                        String.valueOf(
                                cursor.getInt(
                                        DatabaseController.ProjectionDatabase.COURSE_START_AGE
                                )
                        )
                );
                courseInputScreenViewHolder.COURSE_AGE_RANGE_TO_EDIT_TEXT.setText(
                        String.valueOf(
                                cursor.getInt(
                                        DatabaseController.ProjectionDatabase.COURSE_END_AGE
                                )
                        )
                );
                courseInputScreenViewHolder.SUBMIT_COURSE_BUTTON.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(checkValidation(courseInputScreenViewHolder.COURSE_AGE_RANGE_FROM_EDIT_TEXT,
                                        courseInputScreenViewHolder.COURSE_AGE_RANGE_TO_EDIT_TEXT,
                                        courseInputScreenViewHolder.COURSE_COST_EDIT_TEXT,
                                        courseInputScreenViewHolder.COURSE_NAME_EDIT_TEXT,
                                        courseInputScreenViewHolder.COURSE_SALARY_PER_CHILD_EDIT_TEXT)) {


                                    getActivity().getContentResolver().update(
                                            DatabaseController.UriDatabase.getCourseTableWithIdUri(COURSE_ID),
                                            getDataFromInputs(
                                                    courseInputScreenViewHolder),
                                            null,
                                            null
                                    );
                                    openProfileCourseScreen(COURSE_ID);
                                }
                            }
                        }
                );
            }
            cursor.close();
        }
    }

    private void setOptionsAsAddNewCourse(final ViewHolder.CourseInputScreenViewHolder courseInputScreenViewHolder){
        courseInputScreenViewHolder.SUBMIT_COURSE_BUTTON.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(checkValidation(
                                courseInputScreenViewHolder.COURSE_AGE_RANGE_FROM_EDIT_TEXT,
                                courseInputScreenViewHolder.COURSE_AGE_RANGE_TO_EDIT_TEXT,
                                courseInputScreenViewHolder.COURSE_COST_EDIT_TEXT,
                                courseInputScreenViewHolder.COURSE_NAME_EDIT_TEXT,
                                courseInputScreenViewHolder.COURSE_SALARY_PER_CHILD_EDIT_TEXT)) {


                            Uri uri = getActivity().getContentResolver().insert(
                                    DatabaseController.UriDatabase.COURSE_TABLE_URI,
                                    getDataFromInputs(courseInputScreenViewHolder)
                            );

                            final long COURSE_ID = ContentUris.parseId(uri);

                            openProfileCourseScreen(COURSE_ID);
                        }
                    }
                }
        );
    }

    private ContentValues getDataFromInputs(ViewHolder.CourseInputScreenViewHolder courseInputScreenViewHolder){
        final String COURSE_NAME =
                courseInputScreenViewHolder.COURSE_NAME_EDIT_TEXT.getText().toString();
        final Double COURSE_COST =
                Double.valueOf(
                        courseInputScreenViewHolder.COURSE_COST_EDIT_TEXT.getText().toString()
                );
        final Double COURSE_SALARY_PER_CHILD =
                Double.valueOf(
                        courseInputScreenViewHolder.COURSE_SALARY_PER_CHILD_EDIT_TEXT.getText().toString()
                );
        final Integer COURSE_START_AGE =
                Integer.valueOf(
                        courseInputScreenViewHolder.COURSE_AGE_RANGE_FROM_EDIT_TEXT.getText().toString()
                );
        final Integer COURSE_END_AGE =
                Integer.valueOf(
                        courseInputScreenViewHolder.COURSE_AGE_RANGE_TO_EDIT_TEXT.getText().toString()
                );
        final Integer COURSE_SECTION_NUMBER = 0;

        ContentValues contentValues = new ContentValues();
        contentValues.put(DbContent.CourseTable.COURSE_SALARY_PER_CHILD, COURSE_SALARY_PER_CHILD);
        contentValues.put(DbContent.CourseTable.COURSE_COST_COLUMN, COURSE_COST);
        contentValues.put(DbContent.CourseTable.COURSE_NAME_COLUMN, COURSE_NAME);
        contentValues.put(DbContent.CourseTable.COURSE_START_AGE_COLUMN, COURSE_START_AGE);
        contentValues.put(DbContent.CourseTable.COURSE_END_AGE_COLUMN, COURSE_END_AGE);
        contentValues.put(DbContent.CourseTable.COURSE_SECTIONS_NUMBER_COLUMN, COURSE_SECTION_NUMBER);

        return contentValues;
    }

    private void openProfileCourseScreen(final long COURSE_ID){
        Intent profileCourseScreen = new Intent(getContext(), CourseProfileActivity.class);
        profileCourseScreen.putExtra(Constants.COURSE_ID_EXTRA, COURSE_ID);
        startActivity(profileCourseScreen);
        getActivity().finish();
    }

    private boolean checkValidation(EditText... editTexts){
        boolean isValid = true;
        for(EditText editText : editTexts){
            if(!(editText.length() > 0)){
                isValid = false;
                editText.setError("This field can not be empty");
            }
        }
        return isValid;
    }

}
