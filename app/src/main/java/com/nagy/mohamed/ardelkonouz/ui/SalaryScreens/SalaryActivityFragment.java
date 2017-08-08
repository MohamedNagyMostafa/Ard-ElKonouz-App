package com.nagy.mohamed.ardelkonouz.ui.SalaryScreens;

import android.content.Intent;
import android.database.Cursor;
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
import com.nagy.mohamed.ardelkonouz.ui.ViewHolder;
import com.nagy.mohamed.ardelkonouz.ui.adapter.CursorAdapterList;
import com.nagy.mohamed.ardelkonouz.ui.adapter.DatabaseCursorAdapter;

import java.text.DecimalFormat;
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
        salaryScreenViewHolder.INSTRUCTORS_LIST_VIEW.setEmptyView(
                salaryScreenViewHolder.EMPTY_SALARY_LAYOUT
        );
        getLoaderManager().initLoader(Constants.LOADER_SALARY_LIST, null, this);
        return rootView;
    }

    private void setPaidValues(ViewHolder.SalaryScreenViewHolder salaryScreenViewHolder){
        Double totalPaidValue = 0d;
        Double totalUnpaidValue = 0d;
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
                    while(sectionCourseCursor.moveToNext()) {
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

                        if (sectionChildCursor != null) {

                            final int CHILD_NUMBER = sectionChildCursor.getCount();
                            if(CHILD_NUMBER != 0) {
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
                            }
                            sectionChildCursor.close();
                        }
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

        salaryScreenViewHolder.TOTAL_PAID_SALARY_TEXT_VIEW.setText(
                String.valueOf(
                        new DecimalFormat(".###").format(totalPaidValue)
                )
        );
        salaryScreenViewHolder.TOTAL_UNPAID_SALARY_TEXT_VIEW.setText(
                String.valueOf(
                        new DecimalFormat(".###").format(totalUnpaidValue)
                )
        );
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
        int paidCoursesNumber = 0;
        int unpaidCoursesNumber = 0;
        final Long INSTRUCTOR_ID =
                cursor.getLong(
                        DatabaseController.ProjectionDatabase.INSTRUCTOR_SALARY_ID
                );

        instructorsViewHolder.INSTRUCTOR_NAME_TEXT_VIEW.setText(
                cursor.getString(
                        DatabaseController.ProjectionDatabase.INSTRUCTOR_SALARY_INSTRUCTOR_NAME
                )
        );

        Cursor sectionInstructorCursor = getActivity().getContentResolver().query(
                DatabaseController.UriDatabase.getSectionInstructorTableWithInstructorIdUri(INSTRUCTOR_ID),
                DatabaseController.ProjectionDatabase.INSTRUCTOR_SECTION_SALARY_PROJECTION,
                null,
                null,
                null
        );

        if (sectionInstructorCursor != null) {
            while (sectionInstructorCursor.moveToNext()){
                final Long SECTION_ID = sectionInstructorCursor.getLong(
                        DatabaseController.ProjectionDatabase.INSTRUCTOR_SECTION_SALARY_SECTION_ID
                );
                final Integer SECTION_STATE = sectionInstructorCursor.getInt(
                        DatabaseController.ProjectionDatabase.INSTRUCTOR_SECTION_SALARY_SECTION_PAID
                );

                Cursor childSection = getActivity().getContentResolver().query(
                        DatabaseController.UriDatabase.getSectionChildTableWithSectionIdUri(SECTION_ID),
                        null,
                        null,
                        null,
                        null
                );

                if(childSection != null){
                    if(childSection.getCount() > 0){
                        switch (SECTION_STATE){
                            case Constants.PAID_SECTION:
                                paidCoursesNumber++;
                                break;
                            case Constants.NOT_PAID_SECTION:
                                unpaidCoursesNumber++;
                                break;
                        }
                    }
                    childSection.close();
                }
            }
            sectionInstructorCursor.close();
        }

        Log.e("paid courses", String.valueOf(paidCoursesNumber));

        instructorsViewHolder.PAID_COURSES_NUMBER_TEXT_VIEW.setText(
                String.valueOf(
                        paidCoursesNumber
                )
        );
        instructorsViewHolder.UNPAID_COURSES_NUMBER_TEXT_VIEW.setText(
                String.valueOf(
                        unpaidCoursesNumber
                )
        );
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openInstructorScreen = new Intent(getContext(), InstructorSalaryActivity.class);
                openInstructorScreen.putExtra(Constants.INSTRUCTOR_ID_EXTRA, INSTRUCTOR_ID);
                startActivity(openInstructorScreen);
            }
        });
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(
                getContext(),
                DatabaseController.UriDatabase.INSTRUCTOR_TABLE_URI,
                DatabaseController.ProjectionDatabase.INSTRUCTOR_SALARY_PROJECTION,
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
    }
}
