package com.nagy.mohamed.ardelkonouz.ui.InputScreens;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.ParseException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.nagy.mohamed.ardelkonouz.R;
import com.nagy.mohamed.ardelkonouz.calenderFeature.CurrentDateWithTime;
import com.nagy.mohamed.ardelkonouz.calenderFeature.DatePickerFragment;
import com.nagy.mohamed.ardelkonouz.helper.Constants;
import com.nagy.mohamed.ardelkonouz.helper.Utility;
import com.nagy.mohamed.ardelkonouz.offlineDatabase.DatabaseController;
import com.nagy.mohamed.ardelkonouz.offlineDatabase.DbContent;
import com.nagy.mohamed.ardelkonouz.ui.ViewHolder;
import com.nagy.mohamed.ardelkonouz.ui.adapter.CursorAdapterChoices;
import com.nagy.mohamed.ardelkonouz.ui.adapter.CursorAdapterSelection;
import com.nagy.mohamed.ardelkonouz.ui.adapter.DatabaseCursorAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * A placeholder fragment containing a simple view.
 */
public class ShiftInputActivityFragment extends Fragment
        implements CurrentDateWithTime,  LoaderManager.LoaderCallbacks<Cursor> {

    private String searchChars ="";

    private ArrayList<Long> selectedID;

    private DatabaseCursorAdapter databaseAdapterChoices;
    private DatabaseCursorAdapter databaseAdapterSelection;

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
            final Long COURSE_ID = cursor.getLong(
                    DatabaseController.ProjectionDatabase.CHOICES_SELECTION_ID
            );
            listCourseChoiceViewHolder.COURSE_NAME_TEXT_VIEW.setText(
                    cursor.getString(
                            DatabaseController.ProjectionDatabase.CHOICES_SELECTION_COURSE_NAME
                    )
            );

            view.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            selectedID.add(COURSE_ID);
                            restartChoicesLoader();
                            restartSelectionLoader();
                        }
                    }
            );
        }
    };

    private CursorAdapterSelection cursorAdapterSelection = new CursorAdapterSelection() {
        @Override
        public View newListView(ViewGroup viewGroup, Cursor cursor) {
            return LayoutInflater.from(getContext())
                    .inflate(R.layout.shift_selection_chips_recycle_view, viewGroup, false);
        }

        @Override
        public void bindListView(View view, Cursor cursor) {
            ViewHolder.ShiftInputScreenViewHolder.SelectionCoursesViewHolder selectionCoursesViewHolder =
                    new ViewHolder.ShiftInputScreenViewHolder.SelectionCoursesViewHolder(view);

            final Long COURSE_ID = cursor.getLong(
                    DatabaseController.ProjectionDatabase.CHOICES_SELECTION_ID
            );

            selectionCoursesViewHolder.COURSE_NAME_TEXT_VIEW.setText(
                    cursor.getString(
                            DatabaseController.ProjectionDatabase.CHOICES_SELECTION_COURSE_NAME
                    )
            );

            selectionCoursesViewHolder.COURSE_DELETE_IMAGE_VIEW.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            selectedID.remove(COURSE_ID);
                            restartChoicesLoader();
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

                    shiftInputScreenViewHolder.COURSE_START_SHIFT_DATE_EDIT_TEXT.setText(
                            String.valueOf(shiftDate)
                    );

                    shiftInputScreenViewHolder.COURSE_END_SHIFT_DATE_EDIT_TEXT.setText(
                            String.valueOf(shiftDate)
                    );
                }
            };

    private final View.OnClickListener YESTERDAY_BUTTON_LISTENER =
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Long shiftDate = Utility.getCurrentDateAsMills() - Constants.DAY_IN_MILS;

                    shiftInputScreenViewHolder.COURSE_START_SHIFT_DATE_EDIT_TEXT.setText(
                            String.valueOf(shiftDate)
                    );

                    shiftInputScreenViewHolder.COURSE_END_SHIFT_DATE_EDIT_TEXT.setText(
                            String.valueOf(shiftDate)
                    );
                }
            };

    private final View.OnClickListener TOMORROW_BUTTON_LISTENER =
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Long shiftDate = Utility.getCurrentDateAsMills() + Constants.DAY_IN_MILS;

                    shiftInputScreenViewHolder.COURSE_START_SHIFT_DATE_EDIT_TEXT.setText(
                            String.valueOf(shiftDate)
                    );

                    shiftInputScreenViewHolder.COURSE_END_SHIFT_DATE_EDIT_TEXT.setText(
                            String.valueOf(shiftDate)
                    );
                }
            };

    private final View.OnClickListener NEXT_WEEK_BUTTON_LISTENER =
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Long startDate = Utility.getNextFridayDate();
                    Long endDate = startDate * 7;

                    shiftInputScreenViewHolder.COURSE_START_SHIFT_DATE_EDIT_TEXT.setText(
                            String.valueOf(startDate)
                    );

                    shiftInputScreenViewHolder.COURSE_END_SHIFT_DATE_EDIT_TEXT.setText(
                            String.valueOf(endDate)
                    );
                }
            };

    private final View.OnClickListener DATE_EDIT_TEXT_LISTENER =
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DatePickerFragment datePickerFragment = new DatePickerFragment();
                    setSettings(datePickerFragment, view);
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
                    restartChoicesLoader();
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
        databaseAdapterSelection = new DatabaseCursorAdapter(getContext(), null, cursorAdapterSelection);
        selectedID = new ArrayList<>();

        // Set Listeners
        shiftInputScreenViewHolder.COURSE_START_SHIFT_DATE_EDIT_TEXT.setOnClickListener(DATE_EDIT_TEXT_LISTENER);
        shiftInputScreenViewHolder.COURSE_END_SHIFT_DATE_EDIT_TEXT.setOnClickListener(DATE_EDIT_TEXT_LISTENER);
        shiftInputScreenViewHolder.COURSE_SEARCH_EDIT_TEXT.addTextChangedListener(searchTextWatcher);
        shiftInputScreenViewHolder.TODAY_SHIFT_BUTTON.setOnClickListener(TODAY_BUTTON_LISTENER);
        shiftInputScreenViewHolder.YESTERDAY_SHIFT_BUTTON.setOnClickListener(YESTERDAY_BUTTON_LISTENER);
        shiftInputScreenViewHolder.TOMORROW_SHIFT_BUTTON.setOnClickListener(TOMORROW_BUTTON_LISTENER);
        shiftInputScreenViewHolder.NEXT_WEEK_SHIFT_BUTTON.setOnClickListener(NEXT_WEEK_BUTTON_LISTENER);
        shiftInputScreenViewHolder.APPLY_BUTTON.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ContentValues[] coursesSelectedContentValuesAsArray = new ContentValues[selectedID.size()];
                        ArrayList<ContentValues> coursesSelectedContentValues= new ArrayList<>();

                        for(final Long COURSE_ID : selectedID){

                            ContentValues contentValues = new ContentValues();
                            contentValues.put(DbContent.ShiftDaysTable.COURSE_ID_COLUMN, COURSE_ID);
                            contentValues.put(DbContent.ShiftDaysTable.START_DATE_COLUMN,
                                    Long.valueOf(
                                    shiftInputScreenViewHolder.COURSE_START_SHIFT_DATE_EDIT_TEXT
                                            .getText().toString()
                                    )
                            );
                            contentValues.put(DbContent.ShiftDaysTable.END_DATE_COLUMN,
                                    Long.valueOf(
                                            shiftInputScreenViewHolder.COURSE_END_SHIFT_DATE_EDIT_TEXT
                                                    .getText().toString()
                                    )
                            );
                            contentValues.put(DbContent.ShiftDaysTable.DAYS_NUMBER_COLUMN,
                                    Utility.getDaysNumber(
                                            Long.valueOf(
                                                    shiftInputScreenViewHolder.COURSE_START_SHIFT_DATE_EDIT_TEXT
                                                            .getText().toString()
                                            ),
                                            Long.valueOf(
                                                    shiftInputScreenViewHolder.COURSE_END_SHIFT_DATE_EDIT_TEXT
                                                            .getText().toString()
                                            )
                                    ));

                            coursesSelectedContentValues.add(contentValues);
                        }

                        coursesSelectedContentValues.toArray(coursesSelectedContentValuesAsArray);

                        getActivity().getContentResolver().bulkInsert(
                                DatabaseController.UriDatabase.SHIFT_URI,
                                coursesSelectedContentValuesAsArray
                        );

                    }
                }
        );

        // Set Adapters
        shiftInputScreenViewHolder.COURSE_CHOICES_LIST_VIEW.setAdapter(databaseAdapterChoices);
        shiftInputScreenViewHolder.COURSE_SELECTION_GRID_VIEW.setAdapter(databaseAdapterSelection);

        // Loaders..
        getLoaderManager().initLoader(Constants.LOADER_CHOICES_LIST, null, this);
        getLoaderManager().initLoader(Constants.LOADER_SELECTED_LIST, null, this);

        return rootView;
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
                }
                break;
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
                }
                break;
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        switch (getId()){
            case Constants.LOADER_SELECTED_LIST:
                databaseAdapterSelection.swapCursor(data);
                break;
            case Constants.LOADER_CHOICES_LIST:
                databaseAdapterChoices.swapCursor(data);
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        switch (getId()){
            case Constants.LOADER_SELECTED_LIST:
                databaseAdapterSelection.swapCursor(null);
                break;
            case Constants.LOADER_CHOICES_LIST:
                databaseAdapterChoices.swapCursor(null);
                break;
        }
    }

    private void restartChoicesLoader(){
        getLoaderManager().restartLoader(Constants.LOADER_CHOICES_LIST, null, this);
    }

    private void restartSelectionLoader(){
        getLoaderManager().restartLoader(Constants.LOADER_SELECTED_LIST, null, this);
    }
}
