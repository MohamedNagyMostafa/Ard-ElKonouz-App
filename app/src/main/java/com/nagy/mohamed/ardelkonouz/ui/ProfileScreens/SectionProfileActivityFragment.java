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
import com.nagy.mohamed.ardelkonouz.ui.InputScreens.SectionInputActivity;
import com.nagy.mohamed.ardelkonouz.ui.Listner.OnDeleteListener;
import com.nagy.mohamed.ardelkonouz.ui.ViewHolder;
import com.nagy.mohamed.ardelkonouz.ui.adapter.RecycleViewSectionProfileAdapter;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class SectionProfileActivityFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<Cursor>{

    private ViewHolder.SectionProfileViewHolder sectionProfileViewHolder;
    private Long sectionId;
    private RecycleViewSectionProfileAdapter recycleViewSectionProfileAdapter;

    private OnDeleteListener onDeleteListener =
            new OnDeleteListener() {
                @Override
                public void OnClickListener(Long SHIFT_ID) {
                    getActivity().getContentResolver().delete(
                            DatabaseController.UriDatabase.getShiftTableWithIdUri(SHIFT_ID),
                            null,
                            null
                    );

                    // get current shifts.
                    Cursor shiftCursor = getActivity().getContentResolver().query(
                            DatabaseController.UriDatabase.getShiftWithSectionId(sectionId),
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
                                    sectionId
                            );

                            shifts.add(shift);
                        }
                        shiftCursor.close();
                    }

                    // TODO ... update database.
                    Cursor courseCursor = getActivity().getContentResolver().query(
                            DatabaseController.UriDatabase.getSectionWithId(sectionId),
                            DatabaseController.ProjectionDatabase.SECTION_DATE_PROJECTION,
                            null,
                            null,
                            null
                    );


                    if(courseCursor != null){
                        if(courseCursor.getCount() > 0){
                            courseCursor.moveToNext();

                            final String SECTION_SESSION_DAYS = courseCursor.getString(
                                    DatabaseController.ProjectionDatabase.SECTION_DAYS
                            );
                            final int SECTION_SESSION_NUMBER = courseCursor.getInt(
                                    DatabaseController.ProjectionDatabase.SECTION_SESSIONS_NUMBER_COLUMN
                            );
                            final Long SECTION_START_DATE = courseCursor.getLong(
                                    DatabaseController.ProjectionDatabase.SECTION_START_DATE
                            );

                            final Long SECTION_END_DATE = Utility.getEndDate(
                                    shifts,
                                    SECTION_SESSION_DAYS,
                                    SECTION_SESSION_NUMBER,
                                    SECTION_START_DATE
                            );

                            final int REMAINS_SESSIONS = Utility.getRemainDays(
                                    shifts,
                                    SECTION_SESSION_DAYS,
                                    SECTION_START_DATE,
                                    SECTION_END_DATE,
                                    SECTION_SESSION_NUMBER
                            );

                            final int FINISHED_SESSIONS = SECTION_SESSION_NUMBER - REMAINS_SESSIONS;

                            ContentValues contentValues = new ContentValues();
                            contentValues.put(DbContent.SectionTable.SECTION_END_DATE_COLUMN, SECTION_END_DATE);

                            // TODO ... update database to be for sections
                            getActivity().getContentResolver().update(
                                    DatabaseController.UriDatabase.getSectionWithId(sectionId),
                                    contentValues,
                                    null,
                                    null
                            );

                            sectionProfileViewHolder.SECTION_ENDING_DATE_TEXT_VIEW.setText(
                                    String.valueOf(
                                            Utility.getTimeFormat(
                                                    SECTION_END_DATE
                                            )
                                    )
                            );

                            sectionProfileViewHolder.SECTION_NEXT_SESSION_DAY_TEXT_VIEW.setText(
                                    String.valueOf(
                                            Utility.getTimeFormat(
                                                    Utility.getNextSessionDay(
                                                            shifts,
                                                            SECTION_SESSION_DAYS,
                                                            SECTION_END_DATE,
                                                            SECTION_START_DATE
                                                    )
                                            )
                                    )
                            );

                            sectionProfileViewHolder.SECTION_REMAINING_SESSIONS_TEXT_VIEW.setText(
                                    String.valueOf(
                                            REMAINS_SESSIONS
                                    )
                            );

                            sectionProfileViewHolder.SECTION_FINISHED_SESSIONS_TEXT_VIEW.setText(
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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_section_profile, container, false);
        sectionId = getActivity().getIntent().getExtras().getLong(Constants.SECTION_ID_EXTRA);
        Long COURSE_ID = getActivity().getIntent().getExtras().getLong(Constants.COURSE_ID_EXTRA);
        sectionProfileViewHolder = new ViewHolder.SectionProfileViewHolder(rootView);

        recycleViewSectionProfileAdapter = new RecycleViewSectionProfileAdapter(getContext(), onDeleteListener);

        sectionProfileViewHolder.SECTION_SHIFTS_RECYCLE_VIEW.setAdapter(recycleViewSectionProfileAdapter);
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        LinearSnapHelper snapHelper = new LinearSnapHelper();

        sectionProfileViewHolder.SECTION_SHIFTS_RECYCLE_VIEW.setLayoutManager(linearLayoutManager);
        snapHelper.attachToRecyclerView(sectionProfileViewHolder.SECTION_SHIFTS_RECYCLE_VIEW);

        // TODO ... update Database.
        Cursor cursor = getActivity().getContentResolver().query(
                DatabaseController.UriDatabase.getShiftWithSectionJoinId(sectionId),
                DatabaseController.ProjectionDatabase.SHIFT_SECTION_JOIN_PROJECTION,
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
                                            DatabaseController.ProjectionDatabase.SHIFT_SECTION_JOIN_START_DATE_COLUMN
                                    ),
                                    cursor.getLong(
                                            DatabaseController.ProjectionDatabase.SHIFT_SECTION_JOIN_END_DATE_COLUMN
                                    ),
                                    sectionId
                            );

                    shifts.add(shift);

                }while (cursor.moveToNext());

                cursor.moveToFirst();

                setDataToView(cursor, sectionProfileViewHolder, sectionId, shifts);
            }else{
                // TODO .. update Database
                Cursor cursorSection = getActivity().getContentResolver().query(
                        DatabaseController.UriDatabase.getSectionTableWithIdUri(sectionId),
                        DatabaseController.ProjectionDatabase.SECTION_PROJECTION,
                        null,
                        null,
                        null
                );

                if(cursorSection != null){
                    if(cursorSection.getCount() > 0){
                        cursorSection.moveToFirst();
                        setDataToView(cursorSection, sectionProfileViewHolder, sectionId);
                    }
                    cursorSection.close();
                }
            }
            cursor.close();
        }

        sectionProfileViewHolder.SECTION_EDIT_FLOATING_BUTTON.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent sectionInputScreen = new Intent(getContext(), SectionInputActivity.class);
                        sectionInputScreen.putExtra(Constants.SECTION_ID_EXTRA, sectionId);
                        sectionInputScreen.putExtra(Constants.INPUT_TYPE_EXTRA, Constants.INPUT_EDIT_EXTRA);
                        startActivity(sectionInputScreen);
                    }
                }
        );


        getLoaderManager().initLoader(Constants.LOADER_SHIFT_SECTION_PROFILE, null, this);

        setOnBackPressed(COURSE_ID);

        return rootView;

    }

    private void setOnBackPressed(final Long COURSE_ID){
        SectionProfileActivity sectionProfileActivity =  (SectionProfileActivity) getActivity();
        sectionProfileActivity.setCourseId(COURSE_ID);
    }

    private void setDataToView(Cursor cursor,
                               ViewHolder.SectionProfileViewHolder sectionProfileViewHolder,
                               final long sectionId, ArrayList<Shift> shifts){

        /// TODO ... update projection
        final String SECTION_NAME =
                "Section " +
                        cursor.getString(DatabaseController.ProjectionDatabase.SHIFT_SECTION_JOIN_ID);
        final long SECTION_START_DATE =
                cursor.getLong(DatabaseController.ProjectionDatabase.SHIFT_SECTION_JOIN_SECTION_START_DATE_COLUMN);
        final long SECTION_END_DATE =
                cursor.getLong(DatabaseController.ProjectionDatabase.SHIFT_SECTION_JOIN_SECTION_END_DATE_COLUMN);
        final int SECTION_STATE =
                cursor.getInt(DatabaseController.ProjectionDatabase.SHIFT_SECTION_JOIN_SECTION_AVAILABLE_POSITIONS_COLUMN);
        final int SECTION_SESSIONS_NUMBER =
                cursor.getInt(DatabaseController.ProjectionDatabase.SHIFT_SECTION_JOIN_SECTION_SESSIONS_NUMBER_COLUMN);
        final String SECTION_SESSIONS_DAYS =
                cursor.getString(DatabaseController.ProjectionDatabase.SHIFT_SECTION_JOIN_SECTION_DAYS_COLUMN);

        final int REMAINING_SESSIONS = Utility.getRemainDays(
                shifts,
                SECTION_SESSIONS_DAYS,
                SECTION_START_DATE,
                SECTION_END_DATE,
                SECTION_SESSIONS_NUMBER
        );

        int FINISHED_SESSIONS = SECTION_SESSIONS_NUMBER - REMAINING_SESSIONS;

        final Long NEXT_SESSION_DAY = Utility.getNextSessionDay(
                shifts,
                SECTION_SESSIONS_DAYS,
                SECTION_END_DATE,
                SECTION_START_DATE
        );
        final String NEXT_SESSION_DAY_STRING =
                (NEXT_SESSION_DAY == Utility.getCurrentDateAsMills())? "Today" :
                        Utility.getTimeFormat(NEXT_SESSION_DAY);

        sectionProfileViewHolder.SECTION_NAME_TEXT_VIEW.setText(SECTION_NAME);
        sectionProfileViewHolder.SECTION_BEGINNING_DATE_TEXT_VIEW.setText(Utility.getTimeFormat(SECTION_START_DATE));
        sectionProfileViewHolder.SECTION_ENDING_DATE_TEXT_VIEW.setText(Utility.getTimeFormat(SECTION_END_DATE));
        sectionProfileViewHolder.SECTION_SESSIONS_NUMBER_TEXT_VIEW.setText(String.valueOf(SECTION_SESSIONS_NUMBER));
        sectionProfileViewHolder.SECTION_REMAINING_SESSIONS_TEXT_VIEW.setText(String.valueOf(REMAINING_SESSIONS));
        sectionProfileViewHolder.SECTION_FINISHED_SESSIONS_TEXT_VIEW.setText(String.valueOf(FINISHED_SESSIONS));
        sectionProfileViewHolder.SECTION_NEXT_SESSION_DAY_TEXT_VIEW.setText(NEXT_SESSION_DAY_STRING);
        sectionProfileViewHolder.SECTION_SESSION_DAYS_TEXT_VIEW.setText(Utility.getDaysAsString(SECTION_SESSIONS_DAYS));
        sectionProfileViewHolder.SECTION_STATE_TEXT_VIEW.setText(Utility.decodeCourseStateByInt(SECTION_STATE, getContext()));
        sectionProfileViewHolder.SECTION_INSTRUCTOR_NAME_TEXT_VIEW.setText(getSectionInstructorName(sectionId));

    }

    private void setDataToView(Cursor cursor,
                               ViewHolder.SectionProfileViewHolder sectionProfileViewHolder,
                               final long sectionId){

        final String SECTION_NAME =
                "Section " +
                        cursor.getString(DatabaseController.ProjectionDatabase.SECTION_NAME_COLUMN);
        final long SECTION_START_DATE =
                cursor.getLong(DatabaseController.ProjectionDatabase.SECTION_START_DATE);
        final long SECTION_END_DATE =
                cursor.getLong(DatabaseController.ProjectionDatabase.SECTION_END_DATE);
        final int SECTION_STATE =
                cursor.getInt(DatabaseController.ProjectionDatabase.SECTION_AVAILABLE_POSITIONS);
        final int SECTION_SESSIONS_NUMBER =
                cursor.getInt(DatabaseController.ProjectionDatabase.SECTION_SESSIONS_NUMBER_COLUMN);
        final String SECTION_SESSIONS_DAYS =
                cursor.getString(DatabaseController.ProjectionDatabase.SECTION_DAYS);

        final int REMAINING_SESSIONS = Utility.getRemainDays(
                null,
                SECTION_SESSIONS_DAYS,
                SECTION_START_DATE,
                SECTION_END_DATE,
                SECTION_SESSIONS_NUMBER
        );

        int FINISHED_SESSIONS = SECTION_SESSIONS_NUMBER - REMAINING_SESSIONS;
        final Long NEXT_SESSION_DAY = Utility.getNextSessionDay(
                null,
                SECTION_SESSIONS_DAYS,
                SECTION_END_DATE,
                SECTION_START_DATE
        );


        final String NEXT_SESSION_DAY_STRING =
                (NEXT_SESSION_DAY == Utility.getCurrentDateAsMills())? "Today" :
                        Utility.getTimeFormat(NEXT_SESSION_DAY);

        sectionProfileViewHolder.SECTION_NAME_TEXT_VIEW.setText(SECTION_NAME);
        sectionProfileViewHolder.SECTION_BEGINNING_DATE_TEXT_VIEW.setText(Utility.getTimeFormat(SECTION_START_DATE));
        sectionProfileViewHolder.SECTION_ENDING_DATE_TEXT_VIEW.setText(Utility.getTimeFormat(SECTION_END_DATE));
        sectionProfileViewHolder.SECTION_SESSIONS_NUMBER_TEXT_VIEW.setText(String.valueOf(SECTION_SESSIONS_NUMBER));
        sectionProfileViewHolder.SECTION_REMAINING_SESSIONS_TEXT_VIEW.setText(String.valueOf(REMAINING_SESSIONS));
        sectionProfileViewHolder.SECTION_FINISHED_SESSIONS_TEXT_VIEW.setText(String.valueOf(FINISHED_SESSIONS));
        sectionProfileViewHolder.SECTION_NEXT_SESSION_DAY_TEXT_VIEW.setText(NEXT_SESSION_DAY_STRING);
        sectionProfileViewHolder.SECTION_SESSION_DAYS_TEXT_VIEW.setText(Utility.getDaysAsString(SECTION_SESSIONS_DAYS));
        sectionProfileViewHolder.SECTION_STATE_TEXT_VIEW.setText(Utility.decodeCourseStateByInt(SECTION_STATE, getContext()));
        sectionProfileViewHolder.SECTION_INSTRUCTOR_NAME_TEXT_VIEW.setText(getSectionInstructorName(sectionId));


    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // TODO ... update database
        return new CursorLoader(
                getContext(),
                DatabaseController.UriDatabase.getShiftWithSectionId(sectionId),
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
            sectionProfileViewHolder.SECTION_SHIFT_EMPTY_VIEW.setVisibility(View.GONE);
        }else{
            sectionProfileViewHolder.SECTION_SHIFT_EMPTY_VIEW.setVisibility(View.VISIBLE);
        }
        recycleViewSectionProfileAdapter.setCursor(data);
        recycleViewSectionProfileAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        recycleViewSectionProfileAdapter.setCursor(null);
        recycleViewSectionProfileAdapter.notifyDataSetChanged();
    }

    private String getSectionInstructorName(long courseId){
        String instructorName = null;

        // TODO .. update database
        Cursor cursor = getActivity().getContentResolver().query(
                DatabaseController.UriDatabase.getSectionInstructorTableWithSectionIdUri(courseId),
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

    public void restartLoader(){
        getLoaderManager().restartLoader(Constants.LOADER_SHIFT_SECTION_PROFILE, null, this);
    }

}
