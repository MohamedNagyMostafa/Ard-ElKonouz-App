package com.nagy.mohamed.ardelkonouz.ui.SalaryScreens;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nagy.mohamed.ardelkonouz.R;
import com.nagy.mohamed.ardelkonouz.helper.Constants;
import com.nagy.mohamed.ardelkonouz.helper.Utility;
import com.nagy.mohamed.ardelkonouz.offlineDatabase.DatabaseController;
import com.nagy.mohamed.ardelkonouz.ui.ViewHolder;
import com.nagy.mohamed.ardelkonouz.ui.adapter.CursorAdapterList;
import com.nagy.mohamed.ardelkonouz.ui.adapter.DatabaseCursorAdapter;

import java.text.DecimalFormat;

/**
 * A placeholder fragment containing a simple view.
 */
public class InstructorSalaryActivityFragment extends Fragment
        implements CursorAdapterList, LoaderManager.LoaderCallbacks<Cursor>{

    DatabaseCursorAdapter databaseCursorAdapter;
    Long instructorId;
    ViewHolder.InstructorSalaryScreenViewHolder instructorSalaryScreenViewHolder;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_instructor_salary, container, false);
         instructorSalaryScreenViewHolder = new ViewHolder.InstructorSalaryScreenViewHolder(rootView);
        databaseCursorAdapter = new DatabaseCursorAdapter(getContext(), null, this);
        instructorId = getActivity().getIntent().getExtras().getLong(Constants.INSTRUCTOR_ID_EXTRA);

        // set paid settings.
        setSettingsData();
        // set list adapter.
        instructorSalaryScreenViewHolder.INSTRUCTOR_COURSES_LIST_VIEW.setAdapter(databaseCursorAdapter);
        // initialize loader.
        getLoaderManager().initLoader(Constants.LOADER_INSTRUCTOR_SALARY, null, this);
        // return view.
        return rootView;
    }

    private void setSettingsData(){
        long completeCourses = 0;
        long underProgressCourses = 0;
        Double totalPaidSalary = 0d;
        Double totalUnpaidSalary = 0d;
        long paidCoursesNumber = 0;
        long unpaidCoursesNumber = 0;

        Cursor sectionInstructorCursor = getActivity().getContentResolver().query(
                DatabaseController.UriDatabase.getSectionInstructorTableWithInstructorIdUri(instructorId),
                DatabaseController.ProjectionDatabase.INSTRUCTOR_SECTION_SALARY_PROJECTION,
                null,
                null,
                null
        );

        if(sectionInstructorCursor != null){
            while (sectionInstructorCursor.moveToNext()){
                // section state.
                final Long SECTION_START_DATE = sectionInstructorCursor.getLong(
                        DatabaseController.ProjectionDatabase.INSTRUCTOR_SECTION_SALARY_START_DATE
                );
                final Long SECTION_END_DATE = sectionInstructorCursor.getLong(
                        DatabaseController.ProjectionDatabase.INSTRUCTOR_SECTION_SALARY_END_DATE
                );
                final Long SECTION_ID = sectionInstructorCursor.getLong(
                        DatabaseController.ProjectionDatabase.INSTRUCTOR_SECTION_SALARY_SECTION_ID
                );
                final Integer SECTION_PAID = sectionInstructorCursor.getInt(
                        DatabaseController.ProjectionDatabase.INSTRUCTOR_SECTION_SALARY_SECTION_PAID
                );
                final Long TODAY_DATE = Utility.getCurrentDateAsMills();

                if(SECTION_START_DATE < TODAY_DATE){
                    if(SECTION_END_DATE < TODAY_DATE){
                        completeCourses++;
                    }else{
                        underProgressCourses++;
                    }
                }

                Cursor courseSectionCursor = getActivity().getContentResolver().query(
                        DatabaseController.UriDatabase.getCourseSectionJoinWithSectionId(SECTION_ID),
                        DatabaseController.ProjectionDatabase.SALARY_PROJECTION,
                        null,
                        null,
                        null
                );

                if(courseSectionCursor != null){
                    if(courseSectionCursor.moveToNext()){
                        final Double SALARY_PER_CHILD = courseSectionCursor.getDouble(
                                        DatabaseController.ProjectionDatabase.SALARY_COURSE_PERCENT_PER_CHILD
                        );
                        final Double COURSE_COST = courseSectionCursor.getDouble(
                                        DatabaseController.ProjectionDatabase.SALARY_COURSE_COST
                        );

                        Cursor childSectionCursor = getActivity().getContentResolver().query(
                                DatabaseController.UriDatabase.getSectionChildTableWithSectionIdUri(SECTION_ID),
                                null,
                                null,
                                null,
                                null
                        );

                        if(childSectionCursor != null){
                            final Integer CHILD_NUMBER = childSectionCursor.getCount();

                            SalaryPair salaryPair = new SalaryPair(
                                    SALARY_PER_CHILD,
                                    COURSE_COST,
                                    CHILD_NUMBER
                            );

                            switch (SECTION_PAID){
                                case Constants.PAID_SECTION:
                                    paidCoursesNumber++;
                                    totalPaidSalary += salaryPair.getTotalSalary();
                                    break;
                                case Constants.NOT_PAID_SECTION:
                                    unpaidCoursesNumber++;
                                    totalUnpaidSalary += salaryPair.getTotalSalary();
                                    break;
                            }
                            childSectionCursor.close();
                        }
                    }
                    courseSectionCursor.close();
                }
            }
            sectionInstructorCursor.close();
        }

        instructorSalaryScreenViewHolder.COMPLETED_COURSES_TEXT_VIEW.setText(
                String.valueOf(completeCourses)
        );
        instructorSalaryScreenViewHolder.TOTAL_PAID_SALARY_TEXT_VIEW.setText(
                String.valueOf(
                        new DecimalFormat(".###").format(totalPaidSalary)
                )
        );
        instructorSalaryScreenViewHolder.UNDER_PROGRESS_COURSES_TEXT_VIEW.setText(
                String.valueOf(underProgressCourses)
        );
        instructorSalaryScreenViewHolder.NUMBER_OF_PAID_COURSES.setText(
                String.valueOf(paidCoursesNumber)
        );
        instructorSalaryScreenViewHolder.NUMBER_OF_UNPAID_COURSES.setText(
                String.valueOf(unpaidCoursesNumber)
        );
        instructorSalaryScreenViewHolder.TOTAL_UNPAID_SALARY_TEXT_VIEW.setText(
                String.valueOf(
                        new DecimalFormat(".###").format(totalUnpaidSalary)
                )
        );
    }

    @Override
    public View newListView(ViewGroup viewGroup, Cursor cursor) {
        return LayoutInflater.from(getContext())
                .inflate(R.layout.instructor_salary_recycle_view, viewGroup, false);
    }

    @Override
    public void bindListView(View view, final Cursor cursor) {
        double totalSalary = 0;
        final ViewHolder.InstructorSalaryScreenViewHolder.InstructorCoursesViewHolder instructorCoursesViewHolder =
                new ViewHolder.InstructorSalaryScreenViewHolder.InstructorCoursesViewHolder(view);
        final long COURSE_ID = cursor.getLong(
                DatabaseController.ProjectionDatabase.INSTRUCTOR_SECTION_SALARY_SECTION_LIST_COURSE_ID
        );
        final Integer SECTION_NAME = cursor.getInt(
                DatabaseController.ProjectionDatabase.INSTRUCTOR_SECTION_SALARY_SECTION_LIST_SECTION_NAME
        );
        final Long START_DATE = cursor.getLong(
                DatabaseController.ProjectionDatabase.INSTRUCTOR_SECTION_SALARY_SECTION_LIST_START_DATE
        );
        final Long END_DATE = cursor.getLong(
                DatabaseController.ProjectionDatabase.INSTRUCTOR_SECTION_SALARY_SECTION_LIST_END_DATE
        );

        instructorCoursesViewHolder.COURSE_START_DATE_TEXT_VIEW.setText(
               Utility.getTimeFormat(
                        START_DATE
                )
        );
        instructorCoursesViewHolder.COURSE_END_DATE_TEXT_VIEW.setText(
                Utility.getTimeFormat(
                        END_DATE
                )
        );

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(
                getContext(),
                DatabaseController.UriDatabase.getSectionInstructorTableWithInstructorIdUri(instructorId),
                DatabaseController.ProjectionDatabase.INSTRUCTOR_SECTION_SALARY_LIST_PROJECTION,
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

    private void restartLoader(){
        getLoaderManager().restartLoader(Constants.LOADER_INSTRUCTOR_SALARY, null, this);
    }
}
