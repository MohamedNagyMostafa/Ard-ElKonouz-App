package com.nagy.mohamed.ardelkonouz.ui.ListScreens;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.nagy.mohamed.ardelkonouz.R;
import com.nagy.mohamed.ardelkonouz.component.Shift;
import com.nagy.mohamed.ardelkonouz.helper.Constants;
import com.nagy.mohamed.ardelkonouz.helper.Utility;
import com.nagy.mohamed.ardelkonouz.offlineDatabase.DatabaseController;
import com.nagy.mohamed.ardelkonouz.offlineDatabase.DbContent;
import com.nagy.mohamed.ardelkonouz.ui.InputScreens.ShiftInputActivity;
import com.nagy.mohamed.ardelkonouz.ui.ViewHolder;
import com.nagy.mohamed.ardelkonouz.ui.adapter.CursorAdapterList;
import com.nagy.mohamed.ardelkonouz.ui.adapter.DatabaseCursorAdapter;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * A placeholder fragment containing a simple view.
 */
public class ShiftListActivityFragment extends Fragment
        implements CursorAdapterList, LoaderManager.LoaderCallbacks<Cursor>{

    private DatabaseCursorAdapter databaseCursorAdapter;
    private ViewHolder.ShiftListScreenViewHolder shiftListScreenViewHolder;
    private String searchChars = "";
    private Integer dayIndex = null;
    private Long dayDate = null;
    private View shiftSearchView;
    private ArrayList<TextView> dayBarTextViews;

    private TextWatcher searchTextWatcher =
            new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    searchChars = charSequence.toString();
                    restartLoader();
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            };

    private View.OnClickListener todayTextViewListener =
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Calendar calendar = Calendar.getInstance();
                    dayDate = Utility.getCurrentDateAsMills();
                    calendar.setTimeInMillis(dayDate);

                    dayIndex = Utility.getStartDay(calendar);
                    setDayBarSelection(shiftListScreenViewHolder.TODAY_TEXT_VIEW);

                    restartLoader();

                }
            };

    private View.OnClickListener yesterdayTextViewListener =
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Calendar calendar = Calendar.getInstance();
                    dayDate = Utility.getCurrentDateAsMills() - Constants.DAY_IN_MILS;
                    calendar.setTimeInMillis(dayDate);

                    dayIndex = Utility.getStartDay(calendar);

                    setDayBarSelection(shiftListScreenViewHolder.YESTERDAY_TEXT_VIEW);
                    restartLoader();
                }
            };

    private View.OnClickListener tomorrowTextViewListener =
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Calendar calendar = Calendar.getInstance();
                    dayDate = Utility.getCurrentDateAsMills() + Constants.DAY_IN_MILS;
                    calendar.setTimeInMillis(dayDate);

                    dayIndex = Utility.getStartDay(calendar);
                    setDayBarSelection(shiftListScreenViewHolder.TOMORROW_TEXT_VIEW);
                    restartLoader();
                }
            };

    private View.OnClickListener addShiftFloatingButtonListener =
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent shiftInputScreen = new Intent(getContext(), ShiftInputActivity.class);
                    startActivity(shiftInputScreen);
                }
            };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_shift_list, container, false);
        dayBarTextViews = new ArrayList<>();
        shiftListScreenViewHolder = new ViewHolder.ShiftListScreenViewHolder(rootView);
        databaseCursorAdapter = new DatabaseCursorAdapter(getContext(), null, this);

        if(dayIndex == null){
            if(shiftListScreenViewHolder.TODAY_TEXT_VIEW == null)
                Log.e("null", "found");
            setInitialDayBar(shiftListScreenViewHolder.TODAY_TEXT_VIEW);
        }

        // searching..
        if(shiftSearchView != null){
            final EditText CHILD_SEARCH_EDIT_TEXT = (EditText) shiftSearchView;
            CHILD_SEARCH_EDIT_TEXT.addTextChangedListener(searchTextWatcher);
        }

        dayBarTextViews.add(0, shiftListScreenViewHolder.YESTERDAY_TEXT_VIEW);
        dayBarTextViews.add(1, shiftListScreenViewHolder.TODAY_TEXT_VIEW);
        dayBarTextViews.add(2, shiftListScreenViewHolder.TOMORROW_TEXT_VIEW);

        shiftListScreenViewHolder.SHIFT_LIST_VIEW.setAdapter(databaseCursorAdapter);
        shiftListScreenViewHolder.SHIFT_LIST_VIEW.setEmptyView(shiftListScreenViewHolder.SHIFT_LIST_EMPTY_VIEW);

        shiftListScreenViewHolder.TODAY_TEXT_VIEW.setOnClickListener(todayTextViewListener);
        shiftListScreenViewHolder.YESTERDAY_TEXT_VIEW.setOnClickListener(yesterdayTextViewListener);
        shiftListScreenViewHolder.TOMORROW_TEXT_VIEW.setOnClickListener(tomorrowTextViewListener);
        shiftListScreenViewHolder.ADD_SHIFT_BUTTON.setOnClickListener(addShiftFloatingButtonListener);

        getLoaderManager().initLoader(Constants.LOADER_SHIFT_LIST, null, this);

        return rootView;
    }

    @Override
    public View newListView(ViewGroup viewGroup, Cursor cursor) {
        return LayoutInflater.from(getContext())
                .inflate(R.layout.shift_list_recycle, viewGroup, false);
    }

    @Override
    public void bindListView(View view, Cursor cursor) {
        ViewHolder.ShiftListScreenViewHolder.ShiftListRecycleViewHolder shiftListRecycleViewHolder =
                new ViewHolder.ShiftListScreenViewHolder.ShiftListRecycleViewHolder(view);

        final Long SECTION_ID = cursor.getLong(
                DatabaseController.ProjectionDatabase.SHIFT_LIST_SECTION_ID
        );

        shiftListRecycleViewHolder.COURSE_NAME_TEXT_VIEW.setText(
                cursor.getString(
                        DatabaseController.ProjectionDatabase.SHIFT_LIST_COURSE_NAME
                ) + " Section " + String.valueOf(
                        cursor.getInt(
                                DatabaseController.ProjectionDatabase.SHIFT_LIST_SECTION_NAME
                        )
                )
        );

        Cursor instructorNameCursor = getActivity().getContentResolver().query(
                DatabaseController.UriDatabase.getSectionInstructorTableWithSectionIdUri(SECTION_ID),
                new String[]{DbContent.InstructorTable.INSTRUCTOR_NAME_COLUMN},
                null,
                null,
                null
        );

        if(instructorNameCursor != null){
            if(instructorNameCursor.getCount() > 0){
                instructorNameCursor.moveToNext();

                shiftListRecycleViewHolder.INSTRUCTOR_NAME_TEXT_VIEW.setText(
                        instructorNameCursor.getString(0)
                );
            }
                instructorNameCursor.close();
        }
        Cursor shiftCursor = getActivity().getContentResolver().query(
                DatabaseController.UriDatabase.getShiftWithSectionId(SECTION_ID),
                DatabaseController.ProjectionDatabase.SHIFT_TABLE_PROJECTION,
                null,
                null,
                null
        );

        ArrayList<Shift> shifts = new ArrayList<>();

        if(shiftCursor != null) {
            if(shiftCursor.getCount() > 0) {
                while (shiftCursor.moveToNext()){
                    final Long START_SHIFT_DATE =
                            shiftCursor.getLong(
                                    DatabaseController.ProjectionDatabase.SHIFT_START_DATE_COLUMN
                            );
                    final Long END_SHIFT_DATE =
                            shiftCursor.getLong(
                                    DatabaseController.ProjectionDatabase.SHIFT_END_DATE_COLUMN
                            );
                    Shift shift = new Shift(START_SHIFT_DATE, END_SHIFT_DATE, null);

                    shifts.add(shift);
                }
            }
            shiftCursor.close();
        }

        // Test.
//        Shift shift = new Shift(1499637600000l, 1499637600000l,null,null);
//        shifts.add(shift);

        shiftListRecycleViewHolder.NEXT_SECTION_TEXT_VIEW.setText(
                Utility.getNextDayAsString(
                        Utility.getNextSessionDay(
                                shifts,
                                cursor.getString(
                                        DatabaseController.ProjectionDatabase.SHIFT_LIST_SECTION_DAYS
                                ),
                                cursor.getLong(
                                        DatabaseController.ProjectionDatabase.SHIFT_LIST_SECTION_END_dATE
                                ),
                                cursor.getLong(
                                        DatabaseController.ProjectionDatabase.SHIFT_LIST_SECTION_START_dATE
                                )

                        )
                )
        );

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if(dayIndex != null)
            return new CursorLoader(
                    getContext(),
                    DatabaseController.UriDatabase.getSectionsByDaySearchUri(searchChars, dayIndex),
                    DatabaseController.ProjectionDatabase.SHIFT_LIST_PROJECTION,
                    null,
                    null,
                    null
            );

        else
            return null;
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
        getLoaderManager().restartLoader(Constants.LOADER_SHIFT_LIST, null, this);
    }

    public void setEditTextView(View shiftSearchView){
        this.shiftSearchView = shiftSearchView;
    }

    private void setDayBarSelection(TextView textView){
        for(TextView dayTextView : dayBarTextViews){
            if(dayTextView == textView){
                dayTextView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                dayTextView.setTextColor(Color.WHITE);
            }else{
                dayTextView.setBackgroundColor(Color.WHITE);
                dayTextView.setTextColor(getResources().getColor(R.color.colorAccent));
            }

        }
    }

    private void setInitialDayBar(TextView textView){
        Calendar calendar = Calendar.getInstance();
        dayDate = Utility.getCurrentDateAsMills();
        calendar.setTimeInMillis(dayDate);

        textView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        textView.setTextColor(Color.WHITE);

        dayIndex = Utility.getStartDay(calendar);
    }

}
