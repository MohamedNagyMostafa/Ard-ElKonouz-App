package com.nagy.mohamed.ardelkonouz.ui.InputScreens;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.ParseException;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.nagy.mohamed.ardelkonouz.R;
import com.nagy.mohamed.ardelkonouz.calenderFeature.DatePickerFragment;
import com.nagy.mohamed.ardelkonouz.component.Shift;
import com.nagy.mohamed.ardelkonouz.helper.Constants;
import com.nagy.mohamed.ardelkonouz.helper.DoubleChoice;
import com.nagy.mohamed.ardelkonouz.helper.Utility;
import com.nagy.mohamed.ardelkonouz.offlineDatabase.DatabaseController;
import com.nagy.mohamed.ardelkonouz.offlineDatabase.DbContent;
import com.nagy.mohamed.ardelkonouz.ui.ProfileScreens.CourseProfileActivity;
import com.nagy.mohamed.ardelkonouz.ui.ViewHolder;
import com.nagy.mohamed.ardelkonouz.ui.adapter.RecycleViewInstructorProfileAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * A placeholder fragment containing a simple view.
 */
public class SectionInputActivityFragment extends Fragment {

    private ArrayList<DoubleChoice> COURSE_STATE_LIST;
    private ArrayList<DoubleChoice> COURSE_DAYS_LIST;

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
        return inflater.inflate(R.layout.fragment_section_input, container, false);
        ViewHolder.CourseInputScreenViewHolder courseInputScreenViewHolder =
                new ViewHolder.CourseInputScreenViewHolder(rootView);

        final String INPUT_TYPE = getActivity().getIntent().getExtras().getString(Constants.INPUT_TYPE_EXTRA);

        COURSE_STATE_LIST = setCourseStateListItem(courseInputScreenViewHolder);
        COURSE_DAYS_LIST = setCourseDaysListItem(courseInputScreenViewHolder);

        courseInputScreenViewHolder.COURSE_BEGINNING_DATE_EDIT_TEXT.setOnClickListener(DATE_EDIT_TEXT_LISTENER);

        // setChoice listener.
        setCourseStateListener(COURSE_STATE_LIST);
        setDoubleChoicesListListeners(COURSE_DAYS_LIST);

        if(INPUT_TYPE.equals(Constants.INPUT_ADD_EXTRA)) {
            setOptionsAsAddNewCourse(COURSE_STATE_LIST, COURSE_DAYS_LIST, courseInputScreenViewHolder);
        }else {
            setOptionsAsEditCourse(COURSE_STATE_LIST, COURSE_DAYS_LIST, courseInputScreenViewHolder);
        }

        return rootView;

    }

    private ArrayList<DoubleChoice> setCourseDaysListItem(
            ViewHolder.CourseInputScreenViewHolder courseInputScreenViewHolder){
        ArrayList<DoubleChoice> courseDaysList = new ArrayList<>();

        Utility.setChoiceTextViewSystem(
                courseDaysList, getContext(),
                courseInputScreenViewHolder.COURSE_SAT_TEXT_VIEW,
                courseInputScreenViewHolder.COURSE_SUN_TEXT_VIEW,
                courseInputScreenViewHolder.COURSE_MON_TEXT_VIEW,
                courseInputScreenViewHolder.COURSE_TUE_TEXT_VIEW,
                courseInputScreenViewHolder.COURSE_WED_TEXT_VIEW,
                courseInputScreenViewHolder.COURSE_THU_TEXT_VIEW,
                courseInputScreenViewHolder.COURSE_FRI_TEXT_VIEW
        );

        Utility.setChoiceImageViewSystem(
                courseDaysList,
                courseInputScreenViewHolder.COURSE_SAT_IMAGE_VIEW,
                courseInputScreenViewHolder.COURSE_SUN_IMAGE_VIEW,
                courseInputScreenViewHolder.COURSE_MON_IMAGE_VIEW,
                courseInputScreenViewHolder.COURSE_TUE_IMAGE_VIEW,
                courseInputScreenViewHolder.COURSE_WED_IMAGE_VIEW,
                courseInputScreenViewHolder.COURSE_THU_IMAGE_VIEW,
                courseInputScreenViewHolder.COURSE_FRI_IMAGE_VIEW
        );

        return courseDaysList;
    }


    private void setOptionsAsEditCourse(final ArrayList<DoubleChoice> COURSE_STATE_LIST,
                                        final ArrayList<DoubleChoice> COURSE_DAYS_LIST,
                                        final ViewHolder.CourseInputScreenViewHolder courseInputScreenViewHolder){
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
                Utility.doubleSelectionProcess(
                        COURSE_DAYS_LIST,
                        cursor.getString(
                                DatabaseController.ProjectionDatabase.COURSE_DAYS_COLUMN
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
                courseInputScreenViewHolder.COURSE_SESSIONS_NUMBER_EDIT_TEXT.setText(
                        String.valueOf(
                                cursor.getInt(
                                        DatabaseController.ProjectionDatabase.COURSE_SESSIONS_NUMBER_COLUMN
                                )
                        )
                );

                courseInputScreenViewHolder.SUBMIT_COURSE_BUTTON.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(checkValidation(COURSE_STATE_LIST, COURSE_DAYS_LIST,

                                        courseInputScreenViewHolder.COURSE_AGE_RANGE_FROM_EDIT_TEXT,
                                        courseInputScreenViewHolder.COURSE_AGE_RANGE_TO_EDIT_TEXT,
                                        courseInputScreenViewHolder.COURSE_BEGINNING_DATE_EDIT_TEXT,
                                        courseInputScreenViewHolder.COURSE_COST_EDIT_TEXT,
                                        courseInputScreenViewHolder.COURSE_SESSIONS_NUMBER_EDIT_TEXT,
                                        courseInputScreenViewHolder.COURSE_HOURS_EDIT_TEXT,
                                        courseInputScreenViewHolder.COURSE_LEVEL_EDIT_TEXT,
                                        courseInputScreenViewHolder.COURSE_NAME_EDIT_TEXT,
                                        courseInputScreenViewHolder.COURSE_SALARY_PER_CHILD_EDIT_TEXT)) {


                                    getActivity().getContentResolver().update(
                                            DatabaseController.UriDatabase.getCourseTableWithIdUri(COURSE_ID),
                                            getDataFromInputs(
                                                    COURSE_STATE_LIST,
                                                    COURSE_DAYS_LIST,
                                                    COURSE_ID,
                                                    courseInputScreenViewHolder),
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
                                          final ArrayList<DoubleChoice> COURSE_DAYS_LIST,
                                          final ViewHolder.CourseInputScreenViewHolder courseInputScreenViewHolder){
        courseInputScreenViewHolder.SUBMIT_COURSE_BUTTON.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(checkValidation(COURSE_STATE_LIST, COURSE_DAYS_LIST,
                                courseInputScreenViewHolder.COURSE_AGE_RANGE_FROM_EDIT_TEXT,
                                courseInputScreenViewHolder.COURSE_AGE_RANGE_TO_EDIT_TEXT,
                                courseInputScreenViewHolder.COURSE_BEGINNING_DATE_EDIT_TEXT,
                                courseInputScreenViewHolder.COURSE_COST_EDIT_TEXT,
                                courseInputScreenViewHolder.COURSE_SESSIONS_NUMBER_EDIT_TEXT,
                                courseInputScreenViewHolder.COURSE_HOURS_EDIT_TEXT,
                                courseInputScreenViewHolder.COURSE_LEVEL_EDIT_TEXT,
                                courseInputScreenViewHolder.COURSE_NAME_EDIT_TEXT,
                                courseInputScreenViewHolder.COURSE_SALARY_PER_CHILD_EDIT_TEXT)) {


                            Uri uri = getActivity().getContentResolver().insert(
                                    DatabaseController.UriDatabase.COURSE_TABLE_URI,
                                    getDataFromInputs(
                                            COURSE_STATE_LIST,
                                            COURSE_DAYS_LIST,
                                            null,
                                            courseInputScreenViewHolder)
                            );

                            final long COURSE_ID = ContentUris.parseId(uri);

                            Uri uri2 = getActivity().getContentResolver().insert(
                                    DatabaseController.UriDatabase.COURSE_INSTRUCTOR_URI,
                                    getData(COURSE_ID)
                            );

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

    @SafeVarargs
    private final void setDoubleChoicesListListeners(ArrayList<DoubleChoice>... doubleChoiceArrayLists){
        for(ArrayList<DoubleChoice> doubleChoiceArrayList : doubleChoiceArrayLists){
            for(final DoubleChoice doubleChoice : doubleChoiceArrayList){
                doubleChoice.getTextView().setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(doubleChoice.isSelected())
                                    doubleChoice.setChoiceNotSelect();
                                else
                                    doubleChoice.setChoiceSelect();
                            }
                        }
                );
            }
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
                                            ArrayList<DoubleChoice> COURSE_DAYS_LIST,
                                            final Long COURSE_ID,
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
        final Integer COURSE_SESSIONS_NUMBER =
                Integer.valueOf(
                        courseInputScreenViewHolder.COURSE_SESSIONS_NUMBER_EDIT_TEXT.getText().toString()
                );

        final Integer COURSE_STATE = getSelectionFromList(COURSE_STATE_LIST);

        final String COURSE_SESSION_DAYS = getDoubleChoicesResult(COURSE_DAYS_LIST);

        final ArrayList<Shift> SHIFT_ARRAY_LIST = new ArrayList<>();

        if(COURSE_ID != null){

            Cursor cursor = getActivity().getContentResolver().query(
                    DatabaseController.UriDatabase.getShiftWithCourseId(COURSE_ID),
                    DatabaseController.ProjectionDatabase.SHIFT_TABLE_PROJECTION,
                    null,
                    null,
                    null
            );

            if(cursor != null){
                if(cursor.getCount() > 0){
                    while(cursor.moveToNext()){
                        Shift shift = new Shift(
                                cursor.getLong(
                                        DatabaseController.ProjectionDatabase.SHIFT_START_DATE_COLUMN
                                ),cursor.getLong(
                                DatabaseController.ProjectionDatabase.SHIFT_END_DATE_COLUMN
                        ),
                                COURSE_ID

                        );

                        SHIFT_ARRAY_LIST.add(shift);
                    }
                }
                cursor.close();
            }
        }

        final Long COURSE_END_DATE = Utility.getEndDate(
                SHIFT_ARRAY_LIST,
                COURSE_SESSION_DAYS,
                COURSE_SESSIONS_NUMBER,
                COURSE_START_DATE
        );

        Log.e("course_days", COURSE_SESSION_DAYS);
        Log.e("course_days", COURSE_SESSION_DAYS);
        Log.e("course_sessions_number", String.valueOf(COURSE_SESSIONS_NUMBER));

        ContentValues contentValues = new ContentValues();
        contentValues.put(DbContent.CourseTable.COURSE_LEVEL_COLUMN, COURSE_LEVEL);
        contentValues.put(DbContent.CourseTable.COURSE_SALARY_PER_CHILD, COURSE_SALARY_PER_CHILD);
        contentValues.put(DbContent.CourseTable.COURSE_START_DATE_COLUMN, COURSE_START_DATE);
        contentValues.put(DbContent.CourseTable.COURSE_END_DATE_COLUMN, COURSE_END_DATE);
        contentValues.put(DbContent.CourseTable.COURSE_AVAILABLE_POSITIONS_COLUMN, COURSE_STATE);
        contentValues.put(DbContent.CourseTable.COURSE_COST_COLUMN, COURSE_COST);
        contentValues.put(DbContent.CourseTable.COURSE_NAME_COLUMN, COURSE_NAME);
        contentValues.put(DbContent.CourseTable.COURSE_START_AGE_COLUMN, COURSE_START_AGE);
        contentValues.put(DbContent.CourseTable.COURSE_END_AGE_COLUMN, COURSE_END_AGE);
        contentValues.put(DbContent.CourseTable.COURSE_HOURS_COLUMN, COURSE_HOURS);
        contentValues.put(DbContent.CourseTable.COURSE_DAYS_COLUMN, COURSE_SESSION_DAYS);
        contentValues.put(DbContent.CourseTable.COURSE_SESSIONS_NUMBER_COLUMN, COURSE_SESSIONS_NUMBER);

        Log.e("set data to database",String.valueOf(COURSE_START_DATE));
        return contentValues;
    }

    private String getDoubleChoicesResult(ArrayList<DoubleChoice> doubleChoiceArrayList){
        StringBuilder stringBuilder = new StringBuilder("");
        for(DoubleChoice doubleChoice : doubleChoiceArrayList){
            if(doubleChoice.isSelected()) {
                stringBuilder.append(Constants.SELECTED);
                Log.e("day select", doubleChoice.getTextView().getText().toString());
            }else{
                stringBuilder.append(Constants.NOT_SELECTED);
            }
        }
        Log.e("result ", stringBuilder.toString());
        return stringBuilder.toString();
    }

    private void openProfileCourseScreen(final long COURSE_ID){
        Intent profileCourseScreen = new Intent(getContext(), CourseProfileActivity.class);
        profileCourseScreen.putExtra(Constants.COURSE_ID_EXTRA, COURSE_ID);
        startActivity(profileCourseScreen);
        getActivity().finish();
    }

    private boolean checkValidation(ArrayList<DoubleChoice> doubleChoiceStateArrayList,
                                    ArrayList<DoubleChoice> doubleChoiceDaysArrayList,
                                    EditText... editTexts){
        boolean isValid = true;
        for(EditText editText : editTexts){
            if(!(editText.length() > 0)){
                isValid = false;
                editText.setError("This field can not be empty");
            }
        }
        if(getSelectionFromList(doubleChoiceStateArrayList) == -1) {
            isValid = false;
            Toast.makeText(getContext(), "Please choose the state of course",Toast.LENGTH_SHORT).show();
        }
        if(getSelectionFromList(doubleChoiceDaysArrayList) == -1) {
            isValid = false;
            Toast.makeText(getContext(), "Please choose the days of sessions",Toast.LENGTH_SHORT).show();
        }

        return isValid;
    }

    @Override
    public void onDateSet(int year, int month, int day, View view) {
        String strThatDay =
                String.valueOf(year) + "/" +
                        String.valueOf(month) + "/" +
                        String.valueOf(day);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
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

    private void setSettings(DatePickerFragment datePickerFragment, View view){
        datePickerFragment.setCurrentDateWithTime(this);
        datePickerFragment.setView(view);
    }

    private ContentValues getData(final long COURSE_ID){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbContent.CourseInstructorTable.COURSE_ID_COLUMN, COURSE_ID);
        contentValues.put(DbContent.CourseInstructorTable.INSTRUCTOR_ID_COLUMN, Constants.NO_INSTRUCTOR);
        contentValues.put(DbContent.CourseInstructorTable.PAID_COLUMN, Constants.NOT_PAID_COURSE);

        return contentValues;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        outState.putInt(Constants.SaveState.COURSE_STATE,
                Utility.getSelectedDoubleChoices(COURSE_STATE_LIST));
        outState.putString(Constants.SaveState.COURSE_DAYS,
                Utility.getMultiDoubleSelectionAsString(COURSE_DAYS_LIST));

        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if(savedInstanceState != null){
            Utility.selectionProcess(
                    savedInstanceState.getInt(Constants.SaveState.COURSE_STATE),
                    COURSE_STATE_LIST);
            Utility.doubleSelectionProcess(COURSE_DAYS_LIST,
                    savedInstanceState.getString(Constants.SaveState.COURSE_DAYS));
        }
    }
}
