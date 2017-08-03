package com.nagy.mohamed.ardelkonouz.ui.SalaryScreens;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nagy.mohamed.ardelkonouz.R;
import com.nagy.mohamed.ardelkonouz.helper.Constants;
import com.nagy.mohamed.ardelkonouz.offlineDatabase.DatabaseController;
import com.nagy.mohamed.ardelkonouz.offlineDatabase.DbContent;
import com.nagy.mohamed.ardelkonouz.ui.ViewHolder;
import com.nagy.mohamed.ardelkonouz.ui.adapter.CursorAdapterList;
import com.nagy.mohamed.ardelkonouz.ui.adapter.DatabaseCursorAdapter;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class SalaryActivityFragment extends Fragment
        implements CursorAdapterList, LoaderManager.LoaderCallbacks<Cursor> {

    private DatabaseCursorAdapter databaseCursorAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_salary, container, false);
        ViewHolder.SalaryScreenViewHolder salaryScreenViewHolder =
                new ViewHolder.SalaryScreenViewHolder(rootView);
        databaseCursorAdapter = new DatabaseCursorAdapter(getContext(), null, this);

        //set paid, unpaid, values views.
        setPaidValues(salaryScreenViewHolder);
        salaryScreenViewHolder.INSTRUCTORS_LIST_VIEW.setAdapter(databaseCursorAdapter);
        getLoaderManager().initLoader(Constants.LOADER_SALARY_LIST, null, this);
        return rootView;
    }

    private void setPaidValues(ViewHolder.SalaryScreenViewHolder salaryScreenViewHolder){
        double totalPaidValue = 0;
        double totalUnpaidValue = 0;
        long unpaidInstructorNumber = 0;
        ArrayList<SalaryPair> paidSectionId = new ArrayList<>();
        ArrayList<SalaryPair> unpaidSectionId = new ArrayList<>();

        Cursor instructorSectionCursor = getActivity().getContentResolver().query(
                DatabaseController.UriDatabase.SECTION_INSTRUCTOR_URI,
                DatabaseController.ProjectionDatabase.SECTION_INSTRUCTOR_TABLE,
                null,
                null,
                null
        );

        if(instructorSectionCursor != null){
            while (instructorSectionCursor.moveToNext()){
                int sectionState = instructorSectionCursor.getInt(
                        DatabaseController.ProjectionDatabase.SECTION_INSTRUCTOR_PAID
                );
                final Long SECTION_ID = instructorSectionCursor.getLong(
                        DatabaseController.ProjectionDatabase.SECTION_INSTRUCTOR_SECTION_ID
                );

                Cursor sectionCourseCursor = getActivity().getContentResolver().query(
                        DatabaseController.UriDatabase.getCourseSectionJoinWithSectionId(SECTION_ID),
                        DatabaseController.ProjectionDatabase.SALARY_PROJECTION,
                        null,
                        null,
                        null
                );

                if(sectionCourseCursor != null) {

                    final Double COURSE_COST = sectionCourseCursor.getDouble(
                            DatabaseController.ProjectionDatabase.SALARY_COURSE_COST
                    );
                    final Double COURSE_PERCENT_PER_CHILD = sectionCourseCursor.getDouble(
                            DatabaseController.ProjectionDatabase.SALARY_COURSE_PERCENT_PER_CHILD
                    );

                    Cursor sectionChildCursor = getActivity().getContentResolver().query(
                            DatabaseController.UriDatabase.getSectionChildTableWithSectionIdUri(SECTION_ID),
                            null,
                            null,
                            null,
                            null
                    );

                    if(sectionChildCursor != null) {

                        final int CHILD_NUMBER = sectionChildCursor.getCount();
                        SalaryPair salaryPair = new SalaryPair(
                                COURSE_PERCENT_PER_CHILD,
                                COURSE_COST,
                                CHILD_NUMBER);

                        switch (sectionState) {
                            case Constants.PAID_SECTION:
                                paidSectionId.add(salaryPair);
                                break;
                            case Constants.NOT_PAID_SECTION:
                                unpaidSectionId.add(salaryPair);
                                unpaidInstructorNumber++;
                                break;
                        }

                        sectionChildCursor.close();
                    }

                    sectionCourseCursor.close();
                }
            }
            instructorSectionCursor.close();
        }

        for(SalaryPair salaryPair : paidSectionId){
            totalPaidValue += salaryPair.getTotalSalary();
        }

        for(SalaryPair salaryPair : unpaidSectionId){
            totalUnpaidValue += salaryPair.getTotalSalary();
        }

        salaryScreenViewHolder.TOTAL_PAID_SALARY_TEXT_VIEW.setText(String.valueOf(totalPaidValue));
        salaryScreenViewHolder.TOTAL_UNPAID_SALARY_TEXT_VIEW.setText(String.valueOf(totalUnpaidValue));
        salaryScreenViewHolder.UNPAID_SALARY_NUMBER_TEXT_VIEW.setText(String.valueOf(unpaidInstructorNumber));

    }

    @Override
    public View newListView(ViewGroup viewGroup, Cursor cursor) {
        return LayoutInflater.from(getContext())
                .inflate(R.layout.salary_recycle_view, viewGroup, false);
    }

    @Override
    public void bindListView(View view, final Cursor cursor) {
        ViewHolder.SalaryScreenViewHolder.InstructorsViewHolder instructorsViewHolder =
                new ViewHolder.SalaryScreenViewHolder.InstructorsViewHolder(view);
        Log.e("cursor",String.valueOf(cursor.getCount()));
        instructorsViewHolder.INSTRUCTOR_NAME_TEXT_VIEW.setText(
                cursor.getString(3)
        );
        instructorsViewHolder.INSTRUCTOR_COURSE_TEXT_VIEW.setText(
                cursor.getString(2)
        );

        if(cursor.getInt(1) == Constants.PAID_SECTION){
            instructorsViewHolder.INSTRUCTOR_SALARY_PROGRESS_STATE_TEXT_VIEW.setText(
                    getString(R.string.paid)
            );
            instructorsViewHolder.INSTRUCTOR_SALARY_PROGRESS_STATE_TEXT_VIEW.setTextColor(Color.GREEN);
        }else{
            instructorsViewHolder.INSTRUCTOR_SALARY_PROGRESS_STATE_TEXT_VIEW.setText(
                    getString(R.string.unpaid)
            );
            instructorsViewHolder.INSTRUCTOR_SALARY_PROGRESS_STATE_TEXT_VIEW.setTextColor(Color.RED);
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openInstructorScreen = new Intent(getContext(), InstructorSalaryActivity.class);
                openInstructorScreen.putExtra(Constants.INSTRUCTOR_ID_EXTRA, cursor.getLong(4));
                startActivity(openInstructorScreen);
            }
        });
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(
                getContext(),
                DatabaseController.UriDatabase.SECTION_INSTRUCTOR_URI,
                DatabaseController.ProjectionDatabase.SECTION_INSTRUCTOR_SALARY_PROJECTION,
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        databaseCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        databaseCursorAdapter.swapCursor(null);
        return null;
    }
}
