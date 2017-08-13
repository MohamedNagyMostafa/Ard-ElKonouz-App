package com.nagy.mohamed.ardelkonouz.ui.InputScreens;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.ParseException;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.nagy.mohamed.ardelkonouz.R;
import com.nagy.mohamed.ardelkonouz.calenderFeature.CurrentDate;
import com.nagy.mohamed.ardelkonouz.calenderFeature.CurrentTime;
import com.nagy.mohamed.ardelkonouz.calenderFeature.DatePickerFragment;
import com.nagy.mohamed.ardelkonouz.calenderFeature.TimePickerFragment;
import com.nagy.mohamed.ardelkonouz.component.Shift;
import com.nagy.mohamed.ardelkonouz.helper.Constants;
import com.nagy.mohamed.ardelkonouz.helper.DoubleChoice;
import com.nagy.mohamed.ardelkonouz.helper.Utility;
import com.nagy.mohamed.ardelkonouz.offlineDatabase.DatabaseController;
import com.nagy.mohamed.ardelkonouz.offlineDatabase.DbContent;
import com.nagy.mohamed.ardelkonouz.ui.ProfileScreens.SectionProfileActivity;
import com.nagy.mohamed.ardelkonouz.ui.ViewHolder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * A placeholder fragment containing a simple view.
 */
public class SectionInputActivityFragment extends Fragment
        implements CurrentDate, CurrentTime {

    private ArrayList<DoubleChoice> SECTION_STATE_LIST;
    private ArrayList<DoubleChoice> SECTION_DAYS_LIST;
    private Long sectionStartDate = null;
    private Long courseId;
    private Long sectionStartTime;
    private Long sectionEndTime;

    private final View.OnClickListener START_DATE_EDIT_TEXT_LISTENER =
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DatePickerFragment datePickerFragment = new DatePickerFragment();
                    if(sectionStartDate != null){
                        datePickerFragment.setCalendar(sectionStartDate);
                    }
                    setSettings(datePickerFragment, view, Constants.DateType.START_DATE);
                    datePickerFragment.show(getFragmentManager(), Constants.TAG);
                }
            };
    private final View.OnClickListener START_TIME_EDIT_TEXT_LISTENT =
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TimePickerFragment timePickerFragment = new TimePickerFragment();
                    setSettings(timePickerFragment, view, Constants.DateType.START_TIME);
                    timePickerFragment.show(getFragmentManager(), Constants.TAG);
                }
            };

    private final View.OnClickListener END_TIME_EDIT_TEXT_LISTENT =
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TimePickerFragment timePickerFragment = new TimePickerFragment();
                    setSettings(timePickerFragment, view, Constants.DateType.END_TIME);
                    timePickerFragment.show(getFragmentManager(), Constants.TAG);
                }
            };

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_section_input, container, false);
        ViewHolder.SectionInputScreenViewHolder sectionInputScreenViewHolder =
                new ViewHolder.SectionInputScreenViewHolder(rootView);

        final String INPUT_TYPE = getActivity().getIntent().getExtras().getString(Constants.INPUT_TYPE_EXTRA);
        courseId = getActivity().getIntent().getExtras().getLong(Constants.COURSE_ID_EXTRA);

        SECTION_STATE_LIST = setSectionStateListItem(sectionInputScreenViewHolder);
        SECTION_DAYS_LIST = setSectionDaysListItem(sectionInputScreenViewHolder);

        sectionInputScreenViewHolder.SECTION_BEGINNING_DATE_EDIT_TEXT.setOnClickListener(START_DATE_EDIT_TEXT_LISTENER);
        sectionInputScreenViewHolder.SECTION_START_TIME_EDIT_TEXT.setOnClickListener(START_TIME_EDIT_TEXT_LISTENT);
        sectionInputScreenViewHolder.SECTION_END_TIME_EDIT_TEXT.setOnClickListener(END_TIME_EDIT_TEXT_LISTENT);

        // setChoice listener.
        setSectionStateListener(SECTION_STATE_LIST);
        setDoubleChoicesListListeners(SECTION_DAYS_LIST);

        assert INPUT_TYPE != null;
        if(INPUT_TYPE.equals(Constants.INPUT_ADD_EXTRA)) {
            setOptionsAsAddNewSection(SECTION_STATE_LIST, SECTION_DAYS_LIST, sectionInputScreenViewHolder, courseId);
        }else {
            setOptionsAsEditSection(SECTION_STATE_LIST, SECTION_DAYS_LIST, sectionInputScreenViewHolder, courseId);
        }

        return rootView;

    }

    private ArrayList<DoubleChoice> setSectionDaysListItem(
            ViewHolder.SectionInputScreenViewHolder sectionInputScreenViewHolder){
        ArrayList<DoubleChoice> sectionDaysList = new ArrayList<>();

        Utility.setChoiceTextViewSystem(
                sectionDaysList, getContext(),
                sectionInputScreenViewHolder.SECTION_SAT_TEXT_VIEW,
                sectionInputScreenViewHolder.SECTION_SUN_TEXT_VIEW,
                sectionInputScreenViewHolder.SECTION_MON_TEXT_VIEW,
                sectionInputScreenViewHolder.SECTION_TUE_TEXT_VIEW,
                sectionInputScreenViewHolder.SECTION_WED_TEXT_VIEW,
                sectionInputScreenViewHolder.SECTION_THU_TEXT_VIEW,
                sectionInputScreenViewHolder.SECTION_FRI_TEXT_VIEW
        );

        Utility.setChoiceImageViewSystem(
                sectionDaysList,
                sectionInputScreenViewHolder.SECTION_SAT_IMAGE_VIEW,
                sectionInputScreenViewHolder.SECTION_SUN_IMAGE_VIEW,
                sectionInputScreenViewHolder.SECTION_MON_IMAGE_VIEW,
                sectionInputScreenViewHolder.SECTION_TUE_IMAGE_VIEW,
                sectionInputScreenViewHolder.SECTION_WED_IMAGE_VIEW,
                sectionInputScreenViewHolder.SECTION_THU_IMAGE_VIEW,
                sectionInputScreenViewHolder.SECTION_FRI_IMAGE_VIEW
        );

        return sectionDaysList;
    }


    private void setOptionsAsEditSection(final ArrayList<DoubleChoice> SECTION_STATE_LIST,
                                        final ArrayList<DoubleChoice> SECTION_DAYS_LIST,
                                        final ViewHolder.SectionInputScreenViewHolder sectionInputScreenViewHolder,
                                         final Long COURSE_ID){
        final long SECTION_ID = getActivity().getIntent().getExtras().getLong(Constants.SECTION_ID_EXTRA);

        final Cursor cursor = getActivity().getContentResolver().query(
                DatabaseController.UriDatabase.getSectionTableWithIdUri(SECTION_ID),
                DatabaseController.ProjectionDatabase.SECTION_PROJECTION,
                null,
                null,
                null
        );

        if(cursor != null){
            if(cursor.getCount() > 0){
                cursor.moveToFirst();


                sectionInputScreenViewHolder.SECTION_SESSION_HOUR_EDIT_TEXT.setText(
                        String.valueOf(
                                cursor.getInt(
                                        DatabaseController.ProjectionDatabase.SECTION_HOURS_COLUMN
                                )
                        )
                );
                sectionStartDate = cursor.getLong(
                        DatabaseController.ProjectionDatabase.SECTION_START_DATE
                );

                if(!sectionStartDate.equals(Constants.NULL)) {
                    sectionInputScreenViewHolder.SECTION_BEGINNING_DATE_EDIT_TEXT.setText(
                            String.valueOf(Utility.getTimeFormat(sectionStartDate))
                    );
                }else{
                    sectionStartDate = null;
                }
                Utility.selectionProcess(
                        cursor.getInt(
                                DatabaseController.ProjectionDatabase.SECTION_AVAILABLE_POSITIONS
                        ),
                        SECTION_STATE_LIST
                );
                Utility.doubleSelectionProcess(
                        SECTION_DAYS_LIST,
                        cursor.getString(
                                DatabaseController.ProjectionDatabase.SECTION_DAYS
                        )
                );

                sectionInputScreenViewHolder.SECTION_LEVEL_EDIT_TEXT.setText(
                        String.valueOf(
                                cursor.getInt(
                                        DatabaseController.ProjectionDatabase.SECTION_LEVEL_COLUMN
                                )
                        )
                );
                sectionInputScreenViewHolder.SECTION_SESSIONS_NUMBER_EDIT_TEXT.setText(
                        String.valueOf(
                                cursor.getInt(
                                        DatabaseController.ProjectionDatabase.SECTION_SESSIONS_NUMBER_COLUMN
                                )
                        )
                );

                sectionInputScreenViewHolder.SUBMIT_SECTION_BUTTON.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(checkValidation(SECTION_STATE_LIST,
                                        sectionInputScreenViewHolder.SECTION_SESSIONS_NUMBER_EDIT_TEXT,
                                        sectionInputScreenViewHolder.SECTION_SESSION_HOUR_EDIT_TEXT,
                                        sectionInputScreenViewHolder.SECTION_LEVEL_EDIT_TEXT)) {


                                    getActivity().getContentResolver().update(
                                            DatabaseController.UriDatabase.getSectionTableWithIdUri(SECTION_ID),
                                            getDataFromInputsAsEdit(
                                                    SECTION_STATE_LIST,
                                                    SECTION_DAYS_LIST,
                                                    SECTION_ID,
                                                    sectionInputScreenViewHolder,
                                                    COURSE_ID),
                                            null,
                                            null
                                    );

                                    openProfileSectionScreen(SECTION_ID);
                                }
                            }
                        }
                );
            }
            cursor.close();
        }
    }

    private void setOptionsAsAddNewSection(final ArrayList<DoubleChoice> SECTION_STATE_LIST,
                                          final ArrayList<DoubleChoice> SECTION_DAYS_LIST,
                                          final ViewHolder.SectionInputScreenViewHolder sectionInputScreenViewHolder,
                                           final Long COURSE_ID){
        sectionInputScreenViewHolder.SUBMIT_SECTION_BUTTON.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(checkValidation(SECTION_STATE_LIST,
                                sectionInputScreenViewHolder.SECTION_SESSIONS_NUMBER_EDIT_TEXT,
                                sectionInputScreenViewHolder.SECTION_SESSION_HOUR_EDIT_TEXT,
                                sectionInputScreenViewHolder.SECTION_LEVEL_EDIT_TEXT)) {


                            Uri uri = getActivity().getContentResolver().insert(
                                    DatabaseController.UriDatabase.SECTION_URI,
                                    getDataFromInputsAsAdd(
                                            SECTION_STATE_LIST,
                                            SECTION_DAYS_LIST,
                                            sectionInputScreenViewHolder,
                                            COURSE_ID)
                            );

                            final long SECTION_ID = ContentUris.parseId(uri);

                            Uri uri2 = getActivity().getContentResolver().insert(
                                    DatabaseController.UriDatabase.SECTION_INSTRUCTOR_URI,
                                    getData(SECTION_ID)
                            );
                            openProfileSectionScreen(SECTION_ID);
                        }
                    }
                }
        );
    }

    private void setSectionStateListener(final ArrayList<DoubleChoice> doubleChoiceArrayList){
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

    private ArrayList<DoubleChoice> setSectionStateListItem(
            ViewHolder.SectionInputScreenViewHolder sectionInputScreenViewHolder){
        ArrayList<DoubleChoice> doubleChoiceArrayList = new ArrayList<>();

        Utility.setChoiceTextViewSystem(
                doubleChoiceArrayList,
                getContext(),
                sectionInputScreenViewHolder.SECTION_COMPLETE_TEXT_VIEW,
                sectionInputScreenViewHolder.SECTION_INCOMPLETE_TEXT_VIEW
        );

        Utility.setChoiceImageViewSystem(
                doubleChoiceArrayList,
                sectionInputScreenViewHolder.SECTION_COMPLETE_IMAGE_VIEW,
                sectionInputScreenViewHolder.SECTION_INCOMPLETE_IMAGE_VIEW
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

    private ContentValues getDataFromInputsAsAdd(ArrayList<DoubleChoice> SECTION_STATE_LIST,
                                            ArrayList<DoubleChoice> SECTION_DAYS_LIST,
                                            ViewHolder.SectionInputScreenViewHolder sectionInputScreenViewHolder,
                                            final Long COURSE_ID){
        final Double SECTION_HOURS =
                Double.valueOf(
                        sectionInputScreenViewHolder.SECTION_SESSION_HOUR_EDIT_TEXT.getText().toString()
                );
        final Long SECTION_START_DATE = sectionStartDate;

        final Integer SECTION_SESSIONS_NUMBER =
                Integer.valueOf(
                        sectionInputScreenViewHolder.SECTION_SESSIONS_NUMBER_EDIT_TEXT.getText().toString()
                );
        final Integer SECTION_LEVEL =
                Integer.valueOf(
                        sectionInputScreenViewHolder.SECTION_LEVEL_EDIT_TEXT.getText().toString()
                );

        final Integer SECTION_STATE = getSelectionFromList(SECTION_STATE_LIST);

        final String SECTION_SESSION_DAYS = getDoubleChoicesResult(SECTION_DAYS_LIST);

        final ArrayList<Shift> SHIFT_ARRAY_LIST = new ArrayList<>();

        ContentValues contentValues = new ContentValues();

        Long SECTION_END_DATE = null;

        if(SECTION_START_DATE != null && Utility.isDaysSelected(SECTION_SESSION_DAYS)) {
            SECTION_END_DATE = Utility.getEndDate(
                    SHIFT_ARRAY_LIST,
                    SECTION_SESSION_DAYS,
                    SECTION_SESSIONS_NUMBER,
                    SECTION_START_DATE
            );

            contentValues.put(DbContent.SectionTable.SECTION_END_DATE_COLUMN, SECTION_END_DATE);
            contentValues.put(DbContent.SectionTable.SECTION_START_DATE_COLUMN, SECTION_START_DATE);

        }else{
            if(SECTION_START_DATE != null)
                contentValues.put(DbContent.SectionTable.SECTION_START_DATE_COLUMN, SECTION_START_DATE);
            else
                contentValues.put(DbContent.SectionTable.SECTION_START_DATE_COLUMN, Constants.NULL);

        }

        Cursor courseCursor = getActivity().getContentResolver().query(
                DatabaseController.UriDatabase.getCourseSectionJoinWithCourseId(courseId),
                new String[]{
                        DbContent.CourseTable.COURSE_SECTIONS_NUMBER_COLUMN,
                        DbContent.SectionTable.SECTION_NAME_COLUMN
                },
                null,
                null,
                null
        );


        if(courseCursor != null){
            int sectionName;
            if(courseCursor.getCount() > 0) {
                courseCursor.moveToFirst();

                final int SECTIONS_NUMBER = courseCursor.getInt(0); //2
                final int SECTION_NAME_COL = 1;
                for (sectionName = 1; sectionName <= SECTIONS_NUMBER; sectionName++) {
                    if (sectionName != courseCursor.getInt(SECTION_NAME_COL)) {
                        break;
                    }
                    courseCursor.moveToNext();
                }
            }else{
                sectionName = 1;
            }

            contentValues.put(DbContent.SectionTable.SECTION_AVAILABLE_POSITIONS_COLUMN, SECTION_STATE);
            contentValues.put(DbContent.SectionTable.SECTION_HOURS_COLUMN, SECTION_HOURS);
            contentValues.put(DbContent.SectionTable.SECTION_DAYS_COLUMN, SECTION_SESSION_DAYS);
            contentValues.put(DbContent.SectionTable.SECTION_SESSIONS_NUMBER_COLUMN, SECTION_SESSIONS_NUMBER);
            contentValues.put(DbContent.SectionTable.SECTION_COURSE_ID_COLUMN, COURSE_ID);
            contentValues.put(DbContent.SectionTable.SECTION_NAME_COLUMN, sectionName);
            contentValues.put(DbContent.SectionTable.SECTION_LEVEL_COLUMN, SECTION_LEVEL);
            contentValues.put(DbContent.SectionTable.SECTION_DAYS_COLUMN, SECTION_SESSION_DAYS);

            // ** set New Course Sections Number **//
            ContentValues courseContentValue = new ContentValues();
            courseContentValue.put(DbContent.CourseTable.COURSE_SECTIONS_NUMBER_COLUMN, courseCursor.getCount() + 1);
            getActivity().getContentResolver().update(
                    DatabaseController.UriDatabase.getCourseTableWithIdUri(courseId),
                    courseContentValue,
                    null,
                    null
            );

            courseCursor.close();
        }

        return contentValues;
    }

    private ContentValues getDataFromInputsAsEdit(ArrayList<DoubleChoice> SECTION_STATE_LIST,
                                                 ArrayList<DoubleChoice> SECTION_DAYS_LIST,
                                                 final Long SECTION_ID,
                                                 ViewHolder.SectionInputScreenViewHolder sectionInputScreenViewHolder,
                                                 final Long COURSE_ID){
        final Double SECTION_HOURS =
                Double.valueOf(
                        sectionInputScreenViewHolder.SECTION_SESSION_HOUR_EDIT_TEXT.getText().toString()
                );
        final Long SECTION_START_DATE = sectionStartDate;

        final Integer SECTION_SESSIONS_NUMBER =
                Integer.valueOf(
                        sectionInputScreenViewHolder.SECTION_SESSIONS_NUMBER_EDIT_TEXT.getText().toString()
                );
        final Integer SECTION_LEVEL =
                Integer.valueOf(
                        sectionInputScreenViewHolder.SECTION_LEVEL_EDIT_TEXT.getText().toString()
                );

        final Integer SECTION_STATE = getSelectionFromList(SECTION_STATE_LIST);

        final String SECTION_SESSION_DAYS = getDoubleChoicesResult(SECTION_DAYS_LIST);

        final ArrayList<Shift> SHIFT_ARRAY_LIST = new ArrayList<>();
        ContentValues contentValues = new ContentValues();

        if(SECTION_START_DATE != null && Utility.isDaysSelected(SECTION_SESSION_DAYS)) {
            Cursor cursor = getActivity().getContentResolver().query(
                    DatabaseController.UriDatabase.getShiftWithSectionId(SECTION_ID),
                    DatabaseController.ProjectionDatabase.SHIFT_TABLE_PROJECTION,
                    null,
                    null,
                    null
            );

            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    while (cursor.moveToNext()) {
                        Shift shift = new Shift(
                                cursor.getLong(
                                        DatabaseController.ProjectionDatabase.SHIFT_START_DATE_COLUMN
                                ), cursor.getLong(
                                DatabaseController.ProjectionDatabase.SHIFT_END_DATE_COLUMN
                        ),
                                SECTION_ID

                        );

                        SHIFT_ARRAY_LIST.add(shift);
                    }
                }
                cursor.close();
            }

            final Long SECTION_END_DATE = Utility.getEndDate(
                    SHIFT_ARRAY_LIST,
                    SECTION_SESSION_DAYS,
                    SECTION_SESSIONS_NUMBER,
                    SECTION_START_DATE
            );

            contentValues.put(DbContent.SectionTable.SECTION_END_DATE_COLUMN, SECTION_END_DATE);
            contentValues.put(DbContent.SectionTable.SECTION_START_DATE_COLUMN, SECTION_START_DATE);

        }else{
            if(SECTION_START_DATE != null)
                contentValues.put(DbContent.SectionTable.SECTION_START_DATE_COLUMN, SECTION_START_DATE);
            else
                contentValues.put(DbContent.SectionTable.SECTION_START_DATE_COLUMN, Constants.NULL);
        }

        contentValues.put(DbContent.SectionTable.SECTION_AVAILABLE_POSITIONS_COLUMN, SECTION_STATE);
        contentValues.put(DbContent.SectionTable.SECTION_HOURS_COLUMN, SECTION_HOURS);
        contentValues.put(DbContent.SectionTable.SECTION_DAYS_COLUMN, SECTION_SESSION_DAYS);
        contentValues.put(DbContent.SectionTable.SECTION_SESSIONS_NUMBER_COLUMN, SECTION_SESSIONS_NUMBER);
        contentValues.put(DbContent.SectionTable.SECTION_COURSE_ID_COLUMN, COURSE_ID);
        contentValues.put(DbContent.SectionTable.SECTION_LEVEL_COLUMN, SECTION_LEVEL);

        return contentValues;
    }

    private String getDoubleChoicesResult(ArrayList<DoubleChoice> doubleChoiceArrayList){
        StringBuilder stringBuilder = new StringBuilder("");
        for(DoubleChoice doubleChoice : doubleChoiceArrayList){
            if(doubleChoice.isSelected()) {
                stringBuilder.append(Constants.SELECTED);
            }else{
                stringBuilder.append(Constants.NOT_SELECTED);
            }
        }
        return stringBuilder.toString();
    }

    private void openProfileSectionScreen(final long SECTION_ID){
        Intent profileSectionScreen = new Intent(getContext(), SectionProfileActivity.class);
        profileSectionScreen.putExtra(Constants.SECTION_ID_EXTRA, SECTION_ID);
        profileSectionScreen.putExtra(Constants.COURSE_ID_EXTRA, courseId);
        startActivity(profileSectionScreen);
        getActivity().finish();
    }

    private boolean checkValidation(ArrayList<DoubleChoice> doubleChoiceStateArrayList,
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
            Toast.makeText(getContext(), "Please choose the state of section",Toast.LENGTH_SHORT).show();
        }else if(getSelectionFromList(doubleChoiceStateArrayList) == Constants.COURSE_COMPLETE){
            if(sectionStartDate == null){
                Toast.makeText(
                        getContext(),
                        "Please set section start date",
                        Toast.LENGTH_SHORT
                ).show();
                isValid = false;
            }else{
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(sectionStartDate);
                String SECTION_DAYS_AS_STRING = Utility.getMultiDoubleSelectionAsString(SECTION_DAYS_LIST);

                if(!Utility.isDaySelected(SECTION_DAYS_AS_STRING, Utility.getStartDay(calendar))){
                    Toast.makeText(
                            getContext(),
                            "The section start date must be selected in section days",
                            Toast.LENGTH_SHORT
                    ).show();
                    isValid = false;
                }
            }
        }

        return isValid;
    }

    @Override
    public void onDateSet(int year, int month, int day, View view, int dateType) {
        String setYearMonth =
                String.valueOf(year) + "/" +
                        String.valueOf(month) + "/";
        SimpleDateFormat simpleDateFormatYearMonth = new SimpleDateFormat("yyyy/MM");
        Date dateYearMonth = null;

        try {
            dateYearMonth = simpleDateFormatYearMonth.parse(setYearMonth);//catch exception
            dateYearMonth.setTime(dateYearMonth.getTime());

        } catch (ParseException | java.text.ParseException e) {
            e.printStackTrace();
        }
        dateYearMonth.setMonth(dateYearMonth.getMonth()  + 1);
        dateYearMonth.setDate(day);

        Calendar thatDay = Calendar.getInstance();
        thatDay.setTime(dateYearMonth);
        Long dayDateAsMills = thatDay.getTimeInMillis();

        switch (dateType) {
            case Constants.DateType.START_DATE:
                sectionStartDate = dayDateAsMills;
                break;
        }

        EditText editText = (EditText) view;

        editText.setText(Utility.getTimeFormat(dayDateAsMills));
    }

    private void setSettings(DatePickerFragment datePickerFragment, View view, int dateType){
        datePickerFragment.setCurrentDate(this);
        datePickerFragment.setView(view);
        datePickerFragment.setDateType(dateType);
    }

    private void setSettings(TimePickerFragment timePickerFragment, View view, int dateType){
        timePickerFragment.setCurrentDateWithTimeWithView(this, view, dateType);
        switch (dateType){
            case Constants.DateType.START_TIME:
                if(sectionStartTime != null)
                    timePickerFragment.setTime(sectionStartTime);
                break;
            case Constants.DateType.END_TIME:
                if(sectionEndTime != null)
                    timePickerFragment.setTime(sectionEndTime);
                break;
        }
    }


    private ContentValues getData(final long SECTION_ID){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbContent.SectionInstructorTable.SECTION_ID_COLUMN, SECTION_ID);
        contentValues.put(DbContent.SectionInstructorTable.INSTRUCTOR_ID_COLUMN, Constants.NO_INSTRUCTOR);
        contentValues.put(DbContent.SectionInstructorTable.PAID_COLUMN, Constants.NOT_PAID_SECTION);

        return contentValues;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        outState.putInt(Constants.SaveState.SECTION_STATE,
                Utility.getSelectedDoubleChoices(SECTION_STATE_LIST));
        outState.putString(Constants.SaveState.SECTION_DAYS,
                Utility.getMultiDoubleSelectionAsString(SECTION_DAYS_LIST));

        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if(savedInstanceState != null){
            Utility.selectionProcess(
                    savedInstanceState.getInt(Constants.SaveState.SECTION_STATE),
                    SECTION_STATE_LIST);
            Utility.doubleSelectionProcess(SECTION_DAYS_LIST,
                    savedInstanceState.getString(Constants.SaveState.SECTION_DAYS));
        }
    }

    @Override
    public void onTimeSet(int hour, int mint, View view, int dateType) {
        String setMintHour =
                String.valueOf(hour) + ":" +
                        String.valueOf(mint);
        SimpleDateFormat simpleDateFormatYearMonth = new SimpleDateFormat("hh:mm");
        Date dateTime = null;

        try {
            dateTime = simpleDateFormatYearMonth.parse(setMintHour);//catch exception
            dateTime.setTime(dateTime.getTime());

        } catch (ParseException | java.text.ParseException e) {
            e.printStackTrace();
        }

        Calendar thatTime = Calendar.getInstance();
        thatTime.setTime(dateTime);
        Long timeInMillis = thatTime.getTimeInMillis();

        switch (dateType) {
            case Constants.DateType.START_TIME:
                sectionStartTime = timeInMillis;
                break;
            case Constants.DateType.END_TIME:
                sectionEndTime = timeInMillis;
                break;
        }

        EditText editText = (EditText) view;

        editText.setText(Utility.getDateTimeFormat(timeInMillis));
    }
}
