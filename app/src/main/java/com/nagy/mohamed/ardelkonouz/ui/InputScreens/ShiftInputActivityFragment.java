package com.nagy.mohamed.ardelkonouz.ui.InputScreens;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.ParseException;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.nagy.mohamed.ardelkonouz.R;
import com.nagy.mohamed.ardelkonouz.calenderFeature.CurrentDate;
import com.nagy.mohamed.ardelkonouz.calenderFeature.DatePickerFragment;
import com.nagy.mohamed.ardelkonouz.component.Shift;
import com.nagy.mohamed.ardelkonouz.helper.Constants;
import com.nagy.mohamed.ardelkonouz.helper.Utility;
import com.nagy.mohamed.ardelkonouz.offlineDatabase.DatabaseController;
import com.nagy.mohamed.ardelkonouz.offlineDatabase.DbContent;
import com.nagy.mohamed.ardelkonouz.ui.ListScreens.ShiftListActivity;
import com.nagy.mohamed.ardelkonouz.ui.ViewHolder;
import com.nagy.mohamed.ardelkonouz.ui.adapter.CursorAdapterChoices;
import com.nagy.mohamed.ardelkonouz.ui.adapter.CursorAdapterSelection;
import com.nagy.mohamed.ardelkonouz.ui.adapter.DatabaseCursorAdapter;
import com.nagy.mohamed.ardelkonouz.ui.adapter.RecycleViewShiftInputAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * A placeholder fragment containing a simple view.
 */
public class ShiftInputActivityFragment extends Fragment
        implements CurrentDate,  LoaderManager.LoaderCallbacks<Cursor> {

    private String searchChars ="";
    private Long startDay = null;
    private Long endDay = null;
    private ArrayList<Long> selectedID;

    private DatabaseCursorAdapter databaseAdapterChoices;
    private RecycleViewShiftInputAdapter recycleViewShiftInputAdapter;

    private  ViewHolder.ShiftInputScreenViewHolder shiftInputScreenViewHolder;

    private CursorAdapterChoices cursorAdapterChoices = new CursorAdapterChoices() {
        @Override
        public View newListView(ViewGroup viewGroup, Cursor cursor) {
            return LayoutInflater.from(getContext())
                    .inflate(R.layout.shift_choices_recycle_view, viewGroup, false);
        }

        @Override
        public void bindListView(View view, final Cursor cursor) {
            ViewHolder.ShiftInputScreenViewHolder.ListCourseChoiceViewHolder listCourseChoiceViewHolder =
                    new ViewHolder.ShiftInputScreenViewHolder.ListCourseChoiceViewHolder(view);
            final Long SECTION_ID = cursor.getLong(
                    DatabaseController.ProjectionDatabase.CHOICES_SELECTION_ID
            );
            listCourseChoiceViewHolder.COURSE_NAME_TEXT_VIEW.setText(
                    cursor.getString(
                            DatabaseController.ProjectionDatabase.CHOICES_SELECTION_COURSE_NAME
                    )+ " Sec. " + cursor.getInt(
                            DatabaseController.ProjectionDatabase.CHOICES_SELECTION_SECTION_NAME
                    )
            );

            view.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            selectedID.add(SECTION_ID);
                            restartSelectionLoader();
                            shiftInputScreenViewHolder.COURSE_SEARCH_EDIT_TEXT.setText("");
                        }
                    }
            );
        }
    };

    private CursorAdapterSelection cursorAdapterSelection = new CursorAdapterSelection() {
        @Override
        public ViewHolder.ShiftInputScreenViewHolder.SelectionCoursesViewHolder onCreateViewHolder(ViewGroup parent) {
            return new ViewHolder.ShiftInputScreenViewHolder.SelectionCoursesViewHolder (
                    LayoutInflater.from(getContext())
                            .inflate(R.layout.shift_selection_chips_recycle_view, parent, false)
            );
        }

        @Override
        public void onBindViewHolder(ViewHolder.ShiftInputScreenViewHolder.SelectionCoursesViewHolder selectionCoursesViewHolder, Cursor cursor) {
            final Long SECTION_ID = cursor.getLong(
                    DatabaseController.ProjectionDatabase.CHOICES_SELECTION_ID
            );

            selectionCoursesViewHolder.COURSE_NAME_TEXT_VIEW.setText(
                    cursor.getString(
                            DatabaseController.ProjectionDatabase.CHOICES_SELECTION_COURSE_NAME
                    ) + " Sec. " + cursor.getInt(
                            DatabaseController.ProjectionDatabase.CHOICES_SELECTION_SECTION_NAME
                    )
            );

            selectionCoursesViewHolder.COURSE_DELETE_IMAGE_VIEW.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            selectedID.remove(SECTION_ID);
                            if(selectedID.size() == 0)
                                shiftInputScreenViewHolder.EMPTY_SELECTION_LIST_TEXT_VIEW.setVisibility(View.VISIBLE);
                            restartSelectionLoader();
                        }
                    }
            );

        }

    };

    private final View.OnClickListener TODAY_BUTTON_LISTENER =
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Long shiftDate = Utility.getCurrentDateAsMills();
                    startDay = shiftDate;
                    endDay = shiftDate;
                    shiftInputScreenViewHolder.COURSE_START_SHIFT_DATE_EDIT_TEXT.setText(
                            Utility.getTimeFormat(
                                    startDay
                            )
                    );

                    shiftInputScreenViewHolder.COURSE_END_SHIFT_DATE_EDIT_TEXT.setText(
                            Utility.getTimeFormat(
                                    endDay
                            )
                    );
                }
            };

    private final View.OnClickListener YESTERDAY_BUTTON_LISTENER =
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Long shiftDate = Utility.getCurrentDateAsMills() - Constants.DAY_IN_MILS;
                    startDay = shiftDate;
                    endDay = shiftDate;
                    shiftInputScreenViewHolder.COURSE_START_SHIFT_DATE_EDIT_TEXT.setText(
                            Utility.getTimeFormat(
                                    startDay
                            )
                    );

                    shiftInputScreenViewHolder.COURSE_END_SHIFT_DATE_EDIT_TEXT.setText(
                            Utility.getTimeFormat(
                                    endDay
                            )
                    );
                }
            };

    private final View.OnClickListener TOMORROW_BUTTON_LISTENER =
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Long shiftDate = Utility.getCurrentDateAsMills() + Constants.DAY_IN_MILS;

                    startDay = shiftDate;
                    endDay = shiftDate;
                    shiftInputScreenViewHolder.COURSE_START_SHIFT_DATE_EDIT_TEXT.setText(
                            Utility.getTimeFormat(
                                    startDay
                            )
                    );

                    shiftInputScreenViewHolder.COURSE_END_SHIFT_DATE_EDIT_TEXT.setText(
                            Utility.getTimeFormat(
                                    endDay
                            )
                    );
                }
            };

    private final View.OnClickListener NEXT_WEEK_BUTTON_LISTENER =
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startDay = Utility.getNextFridayDate();
                    endDay = startDay + (Constants.DAY_IN_MILS * 7);

                    shiftInputScreenViewHolder.COURSE_START_SHIFT_DATE_EDIT_TEXT.setText(
                            Utility.getTimeFormat(
                                    startDay
                            )
                    );

                    shiftInputScreenViewHolder.COURSE_END_SHIFT_DATE_EDIT_TEXT.setText(
                            Utility.getTimeFormat(
                                    endDay
                            )
                    );
                }
            };

    private final View.OnClickListener START_DATE_EDIT_TEXT_LISTENER =
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DatePickerFragment datePickerFragment = new DatePickerFragment();
                    if(startDay != null){
                        datePickerFragment.setCalendar(startDay);
                    }
                    setSettings(datePickerFragment, view, Constants.DateType.START_DATE);
                    datePickerFragment.show(getFragmentManager(), Constants.TAG);
                }
            };

    private final View.OnClickListener END_DATE_EDIT_TEXT_LISTENER =
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DatePickerFragment datePickerFragment = new DatePickerFragment();
                    if(endDay != null){
                        datePickerFragment.setCalendar(endDay);
                    }
                    setSettings(datePickerFragment, view, Constants.DateType.END_DATE);
                    datePickerFragment.show(getFragmentManager(), Constants.TAG);
                }
            };

    private TextWatcher searchTextWatcher =
            new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    searchChars = charSequence.toString();
                    if(searchChars.length() > 0) {
                        restartChoicesLoader();
                    }else{
                        databaseAdapterChoices.swapCursor(null);
                        shiftInputScreenViewHolder.COURSE_CHOICES_LIST_VIEW.setVisibility(View.GONE);
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            };

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_shift_input, container, false);

        // Initialize ..
        shiftInputScreenViewHolder = new ViewHolder.ShiftInputScreenViewHolder(rootView);
        databaseAdapterChoices = new DatabaseCursorAdapter(getContext(), null, cursorAdapterChoices);
        recycleViewShiftInputAdapter = new RecycleViewShiftInputAdapter(cursorAdapterSelection);
        selectedID = new ArrayList<>();

        // Set Listeners
        shiftInputScreenViewHolder.COURSE_START_SHIFT_DATE_EDIT_TEXT.setOnClickListener(START_DATE_EDIT_TEXT_LISTENER);
        shiftInputScreenViewHolder.COURSE_END_SHIFT_DATE_EDIT_TEXT.setOnClickListener(END_DATE_EDIT_TEXT_LISTENER);
        shiftInputScreenViewHolder.COURSE_SEARCH_EDIT_TEXT.addTextChangedListener(searchTextWatcher);
        shiftInputScreenViewHolder.TODAY_SHIFT_BUTTON.setOnClickListener(TODAY_BUTTON_LISTENER);
        shiftInputScreenViewHolder.YESTERDAY_SHIFT_BUTTON.setOnClickListener(YESTERDAY_BUTTON_LISTENER);
        shiftInputScreenViewHolder.TOMORROW_SHIFT_BUTTON.setOnClickListener(TOMORROW_BUTTON_LISTENER);
        shiftInputScreenViewHolder.NEXT_WEEK_SHIFT_BUTTON.setOnClickListener(NEXT_WEEK_BUTTON_LISTENER);
        shiftInputScreenViewHolder.APPLY_BUTTON.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if(validationInputs()) {
                            ArrayList<ContentValues> coursesSelectedContentValues = new ArrayList<>();

                            if(selectedID.size() == 0){
                                Cursor sectionCursor = getActivity().getContentResolver().query(
                                        DatabaseController.UriDatabase.SECTION_URI,
                                        new String[]{DbContent.SectionTable._ID,
                                        DbContent.SectionTable.SECTION_START_DATE_COLUMN},
                                        null,
                                        null,
                                        null
                                );
                                if(sectionCursor != null){
                                    while (sectionCursor.moveToNext()){
                                        if(sectionCursor.getLong(1) != Constants.NULL)
                                            selectedID.add(sectionCursor.getLong(0));
                                    }
                                    sectionCursor.close();
                                }
                            }
                            for (final Long SECTION_ID : selectedID) {

                                /*
                                 * Validation Block @Start ...
                                 */
                                if (innerShiftDateValidation(SECTION_ID)) {
                                    continue;
                                }
                                // get Most accurate start shift date.
                                Long newShiftStartDate = getAccurateShiftStartDate(SECTION_ID);
                                // get Most accurate end shift date.
                                Long newShiftEndDate = getAccurateShiftEndDate(SECTION_ID);
                                // delete inner shifts.
                                deleteInnerShiftsWithRespectToNewShiftsDates(
                                        SECTION_ID,
                                        newShiftStartDate,
                                        newShiftEndDate
                                );
                                /**
                                 * Validation Block @End ...
                                 */
                                ContentValues shiftContentValues = new ContentValues();
                                ContentValues courseContentValues = new ContentValues();

                                shiftContentValues.put(DbContent.ShiftDaysTable.SECTION_ID_COLUMN, SECTION_ID);
                                shiftContentValues.put(DbContent.ShiftDaysTable.START_DATE_COLUMN, newShiftStartDate);
                                shiftContentValues.put(DbContent.ShiftDaysTable.END_DATE_COLUMN, newShiftEndDate);

                                Shift shift = new Shift(
                                        newShiftStartDate,
                                        newShiftEndDate,
                                        SECTION_ID
                                );

                                // update end date for courses.
                                Cursor cursor = getActivity().getContentResolver().query(
                                        DatabaseController.UriDatabase.getSectionTableWithIdUri(SECTION_ID),
                                        DatabaseController.ProjectionDatabase.SECTION_DATE_PROJECTION,
                                        null,
                                        null,
                                        null
                                );

                                if (cursor != null) {
                                    if (cursor.getCount() > 0) {
                                        cursor.moveToFirst();
                                        ArrayList<Shift> shiftArrayList = new ArrayList<Shift>();
                                        final String COURSE_SESSION_DAYS = cursor.getString(
                                                DatabaseController.ProjectionDatabase.SECTION_DATE_DAYS
                                        );
                                        final Long COURSE_START_DATE = cursor.getLong(
                                                DatabaseController.ProjectionDatabase.SECTION_DATE_START_DATE
                                        );
                                        final Integer COURSE_SESSIONS_NUMBER = cursor.getInt(
                                                DatabaseController.ProjectionDatabase.SECTION_DATE_SESSIONS_NUMBER
                                        );

                                        shiftArrayList.add(shift);

                                        courseContentValues.put(
                                                DbContent.SectionTable.SECTION_END_DATE_COLUMN,
                                                Utility.getEndDate(
                                                        shiftArrayList,
                                                        COURSE_SESSION_DAYS,
                                                        COURSE_SESSIONS_NUMBER,
                                                        COURSE_START_DATE
                                                )
                                        );

                                    }
                                    cursor.close();
                                }

                                getActivity().getContentResolver().update(
                                        DatabaseController.UriDatabase.getSectionTableWithIdUri(SECTION_ID),
                                        courseContentValues,
                                        null,
                                        null
                                );

                                coursesSelectedContentValues.add(shiftContentValues);

                            }

                            ContentValues[] coursesSelectedContentValuesAsArray =
                                    new ContentValues[selectedID.size()];
                            coursesSelectedContentValuesAsArray =
                                    coursesSelectedContentValues.toArray(coursesSelectedContentValuesAsArray);

                            if (coursesSelectedContentValuesAsArray.length > 0) {
                                getActivity().getContentResolver().bulkInsert(
                                        DatabaseController.UriDatabase.SHIFT_URI,
                                        coursesSelectedContentValuesAsArray
                                );

                                openShiftListScreen();
                            } else {
                                Toast.makeText(
                                        getContext(),
                                        "This Shift is Founded before",
                                        Toast.LENGTH_SHORT
                                ).show();
                            }
                        }
                    }

                }
        );

        // Set Adapters
        shiftInputScreenViewHolder.COURSE_CHOICES_LIST_VIEW.setAdapter(databaseAdapterChoices);
        shiftInputScreenViewHolder.COURSE_SELECTION_RECYCLE_VIEW.setAdapter(recycleViewShiftInputAdapter);

        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);

        shiftInputScreenViewHolder.COURSE_SELECTION_RECYCLE_VIEW.setLayoutManager(linearLayoutManager);

        return rootView;
    }

    @Override
    public void onDateSet(int year, int month, int day, View view, int dateType) {
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
        Long dayDateInMills = thatDay.getTimeInMillis();

        switch (dateType){
            case Constants.DateType.START_DATE:
                startDay = dayDateInMills;
                break;
            case Constants.DateType.END_DATE:
                endDay = dayDateInMills;
                break;
        }
        EditText editText = (EditText) view;

        editText.setText(Utility.getTimeFormat(dayDateInMills));
    }

    private void setSettings(DatePickerFragment datePickerFragment, View view, int dateType){
        datePickerFragment.setCurrentDate(this);
        datePickerFragment.setView(view);
        datePickerFragment.setDateType(dateType);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case Constants.LOADER_CHOICES_LIST:
                if (searchChars.length() > 0) {
                    return new CursorLoader(
                            getContext(),
                            DatabaseController.UriDatabase.getCourseChoices(
                                    selectedID,
                                    searchChars
                            ),
                            DatabaseController.ProjectionDatabase.CHOICES_SELECTION_PROJECTION,
                            null,
                            null,
                            null
                    );
                }else{
                    return new CursorLoader(
                            getContext(),
                            DatabaseController.UriDatabase.getCourseChoices(
                                    selectedID,
                                    searchChars
                            ),
                            DatabaseController.ProjectionDatabase.CHOICES_SELECTION_PROJECTION,
                            null,
                            null,
                            null
                    );
                }
            case Constants.LOADER_SELECTED_LIST:
                if(selectedID.size() > 0){
                    return new CursorLoader(
                            getContext(),
                            DatabaseController.UriDatabase.getCourseSelection(selectedID),
                            DatabaseController.ProjectionDatabase.CHOICES_SELECTION_PROJECTION,
                            null,
                            null,
                            null
                    );

                }else{
                    recycleViewShiftInputAdapter.swapCursor(null);
                    recycleViewShiftInputAdapter.notifyDataSetChanged();
                }
                break;
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        switch (loader.getId()){
            case Constants.LOADER_SELECTED_LIST:
                if(selectedID.size() > 0) {
                    recycleViewShiftInputAdapter.swapCursor(data);
                    recycleViewShiftInputAdapter.notifyDataSetChanged();
                    shiftInputScreenViewHolder.EMPTY_SELECTION_LIST_TEXT_VIEW.setVisibility(View.GONE);
                }else{
                    recycleViewShiftInputAdapter.swapCursor(null);
                    recycleViewShiftInputAdapter.notifyDataSetChanged();
                    shiftInputScreenViewHolder.EMPTY_SELECTION_LIST_TEXT_VIEW.setVisibility(View.VISIBLE);
                }
                break;
            case Constants.LOADER_CHOICES_LIST:
                if(data.getCount() > 0 && searchChars.length() > 0) {
                    databaseAdapterChoices.swapCursor(data);
                    shiftInputScreenViewHolder.COURSE_CHOICES_LIST_VIEW.setVisibility(View.VISIBLE);
                }else{
                    databaseAdapterChoices.swapCursor(null);
                    shiftInputScreenViewHolder.COURSE_CHOICES_LIST_VIEW.setVisibility(View.GONE);
                }
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

        switch (loader.getId()){
            case Constants.LOADER_SELECTED_LIST:
                recycleViewShiftInputAdapter.swapCursor(null);
                recycleViewShiftInputAdapter.notifyDataSetChanged();
                break;
            case Constants.LOADER_CHOICES_LIST:
                databaseAdapterChoices.swapCursor(null);
                shiftInputScreenViewHolder.COURSE_CHOICES_LIST_VIEW.setVisibility(View.GONE);
                break;
        }
    }

    private void restartChoicesLoader(){
        if(getLoaderManager().getLoader(Constants.LOADER_CHOICES_LIST) != null) {
            getLoaderManager().restartLoader(Constants.LOADER_CHOICES_LIST, null, this);
        }else{
            getLoaderManager().initLoader(Constants.LOADER_CHOICES_LIST, null, this);
        }
    }

    private void restartSelectionLoader(){
            getLoaderManager().restartLoader(Constants.LOADER_SELECTED_LIST, null, this);

    }

    private void openShiftListScreen(){
        Intent shiftListScreen = new Intent(getContext(), ShiftListActivity.class);
        startActivity(shiftListScreen);
        getActivity().finish();
    }

    // Check the current shift with previous shifts.
    // if the current shift is founded within one of previous shifts
    // the result return true ,Otherwise return false.
    private boolean innerShiftDateValidation(final Long SECTION_ID){
        boolean innerShift = false;

        Cursor cursor = getActivity().getContentResolver().query(
                DatabaseController.UriDatabase.getShiftWithStartEndDate(startDay, endDay, SECTION_ID),
                null,
                null,
                null,
                null
        );

        if(cursor != null){
            if(cursor.getCount() > 0){
                innerShift = true;
            }
            cursor.close();
        }

        return innerShift;
    }

    private Long getAccurateShiftStartDate(final Long SECTION_ID){

        Long newShiftStartDate = startDay;

        Cursor cursor = getActivity().getContentResolver().query(
                DatabaseController.UriDatabase.getShiftWithStartDate(
                        startDay,
                        endDay,
                        SECTION_ID
                ),
                DatabaseController.ProjectionDatabase.SHIFT_TABLE_PROJECTION,
                null,
                null,
                null
        );

        if(cursor != null){
            while (cursor.moveToNext()){
                newShiftStartDate = cursor.getLong(
                        DatabaseController.ProjectionDatabase.SHIFT_START_DATE_COLUMN
                );
            }
            cursor.close();
        }

        return newShiftStartDate;
    }

    private Long getAccurateShiftEndDate(final Long SECTION_ID){

        Long newShiftEndDate = endDay;

        Cursor cursor = getActivity().getContentResolver().query(
                DatabaseController.UriDatabase.getShiftWithEndDate(
                        startDay,
                        endDay,
                        SECTION_ID
                ),
                DatabaseController.ProjectionDatabase.SHIFT_TABLE_PROJECTION,
                null,
                null,
                null
        );

        if(cursor != null){
            while (cursor.moveToNext()){

                newShiftEndDate = cursor.getLong(
                        DatabaseController.ProjectionDatabase.SHIFT_END_DATE_COLUMN
                );
            }
            cursor.close();
        }

        return newShiftEndDate;
    }

    private void deleteInnerShiftsWithRespectToNewShiftsDates(final Long SECTION_ID,
                                                              final Long NEW_SHIFT_START_DATE,
                                                              final Long NEW_SHIFT_END_DATE){
        getContext().getContentResolver().delete(
                DatabaseController.UriDatabase.getShiftWithStartEndDate(
                        NEW_SHIFT_START_DATE,
                        NEW_SHIFT_END_DATE,
                        SECTION_ID
                ),
                null,
                null
        );
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putStringArrayList(Constants.SaveState.SHIFT_COURSE_SELECTION_ID,
                Utility.convertCoursesIdToString(selectedID));
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if(savedInstanceState != null){
            selectedID = Utility.convertCoursesIdToLong(
                    savedInstanceState.getStringArrayList(Constants.SaveState.SHIFT_COURSE_SELECTION_ID)
            );
            restartSelectionLoader();
        }
    }

    private boolean validationInputs(){
        if(startDay == null){
            Toast.makeText(
                    getContext(),
                    "Please choose start day for shift",
                    Toast.LENGTH_SHORT
            ).show();
            return false;
        }else if(endDay == null){
            Toast.makeText(
                    getContext(),
                    "Please choose end day for shift",
                    Toast.LENGTH_SHORT
            ).show();
            return false;
        }else{
            return true;
        }
    }
}
