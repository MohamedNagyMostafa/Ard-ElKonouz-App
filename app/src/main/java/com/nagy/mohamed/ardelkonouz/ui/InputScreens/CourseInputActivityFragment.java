package com.nagy.mohamed.ardelkonouz.ui.InputScreens;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.ParseException;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.nagy.mohamed.ardelkonouz.R;
import com.nagy.mohamed.ardelkonouz.calenderFeature.CurrentDateWithTime;
import com.nagy.mohamed.ardelkonouz.calenderFeature.DatePickerFragment;
import com.nagy.mohamed.ardelkonouz.calenderFeature.TimePickerFragment;
import com.nagy.mohamed.ardelkonouz.helper.Constants;
import com.nagy.mohamed.ardelkonouz.helper.DoubleChoice;
import com.nagy.mohamed.ardelkonouz.helper.Utility;
import com.nagy.mohamed.ardelkonouz.offlineDatabase.DatabaseController;
import com.nagy.mohamed.ardelkonouz.offlineDatabase.DbContent;
import com.nagy.mohamed.ardelkonouz.ui.ProfileScreens.CourseProfileActivity;
import com.nagy.mohamed.ardelkonouz.ui.ViewHolder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * A placeholder fragment containing a simple view.
 */
public class CourseInputActivityFragment extends Fragment
        implements CurrentDateWithTime{

    private final View.OnClickListener DATE_EDIT_TEXT_LISTENER =
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DatePickerFragment datePickerFragment = new DatePickerFragment();
                    setSettings(datePickerFragment, view);
                    datePickerFragment.show(getFragmentManager(), Constants.TAG);
                }
            };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_course_input, container, false);
        ViewHolder.CourseInputScreenViewHolder courseInputScreenViewHolder =
                new ViewHolder.CourseInputScreenViewHolder(rootView);

        final String INPUT_TYPE = getActivity().getIntent().getExtras().getString(Constants.INPUT_TYPE_EXTRA);

        final ArrayList<DoubleChoice> COURSE_STATE_LIST =
                setCourseStateListItem(courseInputScreenViewHolder);

        courseInputScreenViewHolder.COURSE_BEGINNING_DATE_EDIT_TEXT.setOnClickListener(DATE_EDIT_TEXT_LISTENER);
        courseInputScreenViewHolder.COURSE_ENDING_DATE_EDIT_TEXT.setOnClickListener(DATE_EDIT_TEXT_LISTENER);
        // setChoice listener.
        setCourseStateListener(COURSE_STATE_LIST);

        if(INPUT_TYPE.equals(Constants.INPUT_ADD_EXTRA)) {
            setOptionsAsAddNewCourse(COURSE_STATE_LIST, courseInputScreenViewHolder);
        }else {
            setOptionsAsEditCourse(COURSE_STATE_LIST, courseInputScreenViewHolder);
        }

        return rootView;

    }


    private void setOptionsAsEditCourse(final ArrayList<DoubleChoice> COURSE_STATE_LIST,
                                        final ViewHolder.CourseInputScreenViewHolder courseInputScreenViewHolder){
        final long COURSE_ID = getActivity().getIntent().getExtras().getLong(Constants.COURSE_ID_EXTRA);

        Cursor cursor = getActivity().getContentResolver().query(
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
                courseInputScreenViewHolder.COURSE_HOURS_EDIT_TEXT.setText(
                        String.valueOf(
                                cursor.getInt(
                                        DatabaseController.ProjectionDatabase.COURSE_HOURS
                                )
                        )
                );
                courseInputScreenViewHolder.COURSE_LEVEL_EDIT_TEXT.setText(
                        String.valueOf(
                                cursor.getInt(
                                        DatabaseController.ProjectionDatabase.COURSE_LEVEL
                                )
                        )
                );
                courseInputScreenViewHolder.COURSE_BEGINNING_DATE_EDIT_TEXT.setText(
                        String.valueOf(
                                cursor.getLong(
                                        DatabaseController.ProjectionDatabase.COURSE_START_DATE
                                )
                        )
                );
                courseInputScreenViewHolder.COURSE_ENDING_DATE_EDIT_TEXT.setText(
                        String.valueOf(
                                cursor.getLong(
                                        DatabaseController.ProjectionDatabase.COURSE_END_DATE
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
                Utility.selectionProcess(
                        cursor.getInt(
                                DatabaseController.ProjectionDatabase.COURSE_AVAILABLE_POSITIONS
                        ),
                        COURSE_STATE_LIST
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
                                if(checkValidation(COURSE_STATE_LIST,
                                        courseInputScreenViewHolder.COURSE_AGE_RANGE_FROM_EDIT_TEXT,
                                        courseInputScreenViewHolder.COURSE_AGE_RANGE_TO_EDIT_TEXT,
                                        courseInputScreenViewHolder.COURSE_BEGINNING_DATE_EDIT_TEXT,
                                        courseInputScreenViewHolder.COURSE_COST_EDIT_TEXT,
                                        courseInputScreenViewHolder.COURSE_ENDING_DATE_EDIT_TEXT,
                                        courseInputScreenViewHolder.COURSE_HOURS_EDIT_TEXT,
                                        courseInputScreenViewHolder.COURSE_LEVEL_EDIT_TEXT,
                                        courseInputScreenViewHolder.COURSE_NAME_EDIT_TEXT,
                                        courseInputScreenViewHolder.COURSE_SALARY_PER_CHILD_EDIT_TEXT)) {

                                    getActivity().getContentResolver().update(
                                            DatabaseController.UriDatabase.getCourseTableWithIdUri(COURSE_ID),
                                            getDataFromInputs(COURSE_STATE_LIST, courseInputScreenViewHolder),
                                            null,
                                            null
                                    );
                                    Log.e("Course id", String.valueOf(COURSE_ID));


                                    openProfileCourseScreen(COURSE_ID);
                                }
                            }
                        }
                );
            }
            cursor.close();
        }
    }

    private void setOptionsAsAddNewCourse(final ArrayList<DoubleChoice> COURSE_STATE_LIST,
                                          final ViewHolder.CourseInputScreenViewHolder courseInputScreenViewHolder){
        courseInputScreenViewHolder.SUBMIT_COURSE_BUTTON.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(checkValidation(COURSE_STATE_LIST,
                                courseInputScreenViewHolder.COURSE_AGE_RANGE_FROM_EDIT_TEXT,
                                courseInputScreenViewHolder.COURSE_AGE_RANGE_TO_EDIT_TEXT,
                                courseInputScreenViewHolder.COURSE_BEGINNING_DATE_EDIT_TEXT,
                                courseInputScreenViewHolder.COURSE_COST_EDIT_TEXT,
                                courseInputScreenViewHolder.COURSE_ENDING_DATE_EDIT_TEXT,
                                courseInputScreenViewHolder.COURSE_HOURS_EDIT_TEXT,
                                courseInputScreenViewHolder.COURSE_LEVEL_EDIT_TEXT,
                                courseInputScreenViewHolder.COURSE_NAME_EDIT_TEXT,
                                courseInputScreenViewHolder.COURSE_SALARY_PER_CHILD_EDIT_TEXT)) {

                            Uri uri = getActivity().getContentResolver().insert(
                                    DatabaseController.UriDatabase.COURSE_TABLE_URI,
                                    getDataFromInputs(COURSE_STATE_LIST, courseInputScreenViewHolder)
                            );
                            final long COURSE_ID = ContentUris.parseId(uri);

                            Uri uri2 = getActivity().getContentResolver().insert(
                                    DatabaseController.UriDatabase.COURSE_INSTRUCTOR_URI,
                                    getData(COURSE_ID)
                            );

                            Log.e("Course id", String.valueOf(ContentUris.parseId(uri)));
                            openProfileCourseScreen(COURSE_ID);
                        }
                    }
                }
        );
    }

    private void setCourseStateListener(final ArrayList<DoubleChoice> doubleChoiceArrayList){
        for(final DoubleChoice doubleChoice : doubleChoiceArrayList){
            doubleChoice.getTextView().setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(!doubleChoice.isSelected())
                                Utility.selectionProcess(
                                        doubleChoiceArrayList.indexOf(doubleChoice),
                                        doubleChoiceArrayList
                                );
                        }
                    }
            );
        }
    }

    private ArrayList<DoubleChoice> setCourseStateListItem(
            ViewHolder.CourseInputScreenViewHolder courseInputScreenViewHolder){
        ArrayList<DoubleChoice> doubleChoiceArrayList = new ArrayList<>();

        Utility.setChoiceTextViewSystem(
                doubleChoiceArrayList,
                getContext(),
                courseInputScreenViewHolder.COURSE_COMPLETE_TEXT_VIEW,
                courseInputScreenViewHolder.COURSE_INCOMPLETE_TEXT_VIEW
        );

        Utility.setChoiceImageViewSystem(
                doubleChoiceArrayList,
                courseInputScreenViewHolder.COURSE_COMPLETE_IMAGE_VIEW,
                courseInputScreenViewHolder.COURSE_INCOMPLETE_IMAGE_VIEW
        );

        return doubleChoiceArrayList;
    }

    private int getSelectionFromList(ArrayList<DoubleChoice> arrayList){
        for(int i = 0 ; i < arrayList.size() ; i++){
            if(arrayList.get(i).isSelected())
                return i;
        }
        return -1;
    }

    private ContentValues getDataFromInputs(ArrayList<DoubleChoice> COURSE_STATE_LIST,
                                            ViewHolder.CourseInputScreenViewHolder courseInputScreenViewHolder){
        final String COURSE_NAME =
                courseInputScreenViewHolder.COURSE_NAME_EDIT_TEXT.getText().toString();
        final Double COURSE_COST =
                Double.valueOf(
                        courseInputScreenViewHolder.COURSE_COST_EDIT_TEXT.getText().toString()
                );
        final Double COURSE_HOURS =
                Double.valueOf(
                        courseInputScreenViewHolder.COURSE_HOURS_EDIT_TEXT.getText().toString()
                );
        final Integer COURSE_LEVEL =
                Integer.valueOf(
                        courseInputScreenViewHolder.COURSE_LEVEL_EDIT_TEXT.getText().toString()
                );
        final Long COURSE_START_DATE =
                Long.valueOf(
                        courseInputScreenViewHolder.COURSE_BEGINNING_DATE_EDIT_TEXT.getText().toString()
                );
        final Long COURSE_END_DATE =
                Long.valueOf(
                        courseInputScreenViewHolder.COURSE_ENDING_DATE_EDIT_TEXT.getText().toString()
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
        final Integer COURSE_STATE = getSelectionFromList(COURSE_STATE_LIST);

        ContentValues contentValues = new ContentValues();
        contentValues.put(DbContent.CourseTable.COURSE_LEVEL_COLUMN, COURSE_LEVEL);
        contentValues.put(DbContent.CourseTable.COURSE_SALARY_PER_CHILD, COURSE_SALARY_PER_CHILD);
        contentValues.put(DbContent.CourseTable.COURSE_END_DATE_COLUMN, COURSE_END_DATE);
        contentValues.put(DbContent.CourseTable.COURSE_START_DATE_COLUMN, COURSE_START_DATE);
        contentValues.put(DbContent.CourseTable.COURSE_AVAILABLE_POSITIONS_COLUMN, COURSE_STATE);
        contentValues.put(DbContent.CourseTable.COURSE_COST_COLUMN, COURSE_COST);
        contentValues.put(DbContent.CourseTable.COURSE_NAME_COLUMN, COURSE_NAME);
        contentValues.put(DbContent.CourseTable.COURSE_START_AGE_COLUMN, COURSE_START_AGE);
        contentValues.put(DbContent.CourseTable.COURSE_END_AGE_COLUMN, COURSE_END_AGE);
        contentValues.put(DbContent.CourseTable.COURSE_HOURS_COLUMN, COURSE_HOURS);
        Log.e("set data to database",String.valueOf(COURSE_START_DATE));
        return contentValues;
    }

    private void openProfileCourseScreen(final long COURSE_ID){
        Intent profileCourseScreen = new Intent(getContext(), CourseProfileActivity.class);
        profileCourseScreen.putExtra(Constants.COURSE_ID_EXTRA, COURSE_ID);
        startActivity(profileCourseScreen);
        getActivity().finish();
    }

    private boolean checkValidation(ArrayList<DoubleChoice> doubleChoiceArrayList,
                                    EditText... editTexts){
        boolean isValid = true;
        for(EditText editText : editTexts){
            if(!(editText.length() > 0)){
                isValid = false;
                editText.setError("This field can not be empty");
            }
        }
        if(getSelectionFromList(doubleChoiceArrayList) == -1) {
            isValid = false;
            Toast.makeText(getContext(), "Please choose the state of course",Toast.LENGTH_SHORT).show();
        }

        return isValid;
    }

    @Override
    public void onTimeSet(int year, int month, int day, int hour, int mint, View view) {
        String strThatDay =
                String.valueOf(year) + "/" +
                        String.valueOf(month) + "/" +
                        String.valueOf(day) + " - " +
                        String.valueOf(hour) + ":" +
                        String.valueOf(mint);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd - hh:mm");
        Date d = null;
        try {
            d = formatter.parse(strThatDay);//catch exception
        } catch (ParseException | java.text.ParseException e) {
            e.printStackTrace();
        }
        d.setMonth(d.getMonth()  + 1);
        Calendar thatDay = Calendar.getInstance();
        thatDay.setTime(d);

        EditText editText = (EditText) view;

        editText.setText(String.valueOf(thatDay.getTimeInMillis()));
    }

    @Override
    public void onDateSet(int year, int month, int day, View view) {
        TimePickerFragment timePickerFragment = new TimePickerFragment();
        setSettings(timePickerFragment, view, year, month, day);
        timePickerFragment.show(getFragmentManager(), Constants.TAG);
    }

    private void setSettings(DatePickerFragment datePickerFragment, View view){
        datePickerFragment.setCurrentDateWithTime(this);
        datePickerFragment.setView(view);
    }

    private void setSettings(TimePickerFragment timePickerFragment, View view, int year, int month, int day){
        timePickerFragment.setCurrentDateWithTime(this);
        timePickerFragment.setView(view);
        timePickerFragment.setDate(year, month, day);
    }

    private ContentValues getData(final long COURSE_ID){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbContent.CourseInstructorTable.COURSE_ID_COLUMN, COURSE_ID);
        contentValues.put(DbContent.CourseInstructorTable.INSTRUCTOR_ID_COLUMN, Constants.NO_INSTRUCTOR);
        contentValues.put(DbContent.CourseInstructorTable.PAID_COLUMN, Constants.NOT_PAID_COURSE);

        return contentValues;
    }
}
