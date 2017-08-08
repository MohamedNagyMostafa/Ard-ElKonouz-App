package com.nagy.mohamed.ardelkonouz.ui.SalaryScreens;

import android.content.ContentValues;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.nagy.mohamed.ardelkonouz.R;
import com.nagy.mohamed.ardelkonouz.helper.Constants;
import com.nagy.mohamed.ardelkonouz.helper.Utility;
import com.nagy.mohamed.ardelkonouz.offlineDatabase.DatabaseController;
import com.nagy.mohamed.ardelkonouz.offlineDatabase.DbContent;
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
        instructorSalaryScreenViewHolder.INSTRUCTOR_COURSES_LIST_VIEW.setEmptyView(
                instructorSalaryScreenViewHolder.INSTRUCTOR_SALARY_EMPTY_LIST
        );
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
        final Integer SECTION_STATE = cursor.getInt(
                DatabaseController.ProjectionDatabase.INSTRUCTOR_SECTION_SALARY_SECTION_LIST_PAID
        );
        final Long TODAY = Utility.getCurrentDateAsMills();
        final Long SECTION_ID = cursor.getLong(
                DatabaseController.ProjectionDatabase.INSTRUCTOR_SECTION_SALARY_SECTION_LIST_SECTION_ID
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

        Cursor courseCursor = getActivity().getContentResolver().query(
                DatabaseController.UriDatabase.getCourseTableWithIdUri(COURSE_ID),
                DatabaseController.ProjectionDatabase.SALARY_PROJECTION,
                null,
                null,
                null
        );

        if(courseCursor != null){
            courseCursor.moveToNext();
            final Double COURSE_COST = courseCursor.getDouble(
                    DatabaseController.ProjectionDatabase.SALARY_COURSE_COST
            );
            final Double SALARY_PER_CHILD = courseCursor.getDouble(
                    DatabaseController.ProjectionDatabase.SALARY_COURSE_PERCENT_PER_CHILD
            );
            final String COURSE_NAME = courseCursor.getString(
                    DatabaseController.ProjectionDatabase.SALARY_COURSE_NAME
            );

            Cursor childCourse = getActivity().getContentResolver().query(
                    DatabaseController.UriDatabase.getSectionChildTableWithSectionIdUri(SECTION_ID),
                    null,
                    null,
                    null,
                    null
            );

            if(childCourse != null){
                if(childCourse.getCount() > 0){
                    instructorCoursesViewHolder.COURSE_SALARY_TEXT_VIEW.setText(
                            String.valueOf(
                                    new DecimalFormat(".###").format(
                                            new SalaryPair(
                                                    SALARY_PER_CHILD,
                                                    COURSE_COST,
                                                    childCourse.getCount()
                                    ).getTotalSalary()
                            )
                        )
                    );
                }else{
                    instructorCoursesViewHolder.COURSE_SALARY_STATE_BUTTON.setOnClickListener(
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Toast.makeText(
                                            getContext(),
                                            "There are no child in this course",
                                            Toast.LENGTH_SHORT
                                    ).show();
                                }
                            }
                    );
                    instructorCoursesViewHolder.COURSE_SALARY_TEXT_VIEW.setText("0");
                }
                childCourse.close();

                instructorCoursesViewHolder.COURSE_NAME_TEXT_VIEW.setText(
                        COURSE_NAME + " Section " + String.valueOf(
                                SECTION_NAME
                        )
                );
            }
            courseCursor.close();
        }

        if(START_DATE < TODAY){
            if(END_DATE < TODAY){
                switch (SECTION_STATE){
                    case Constants.PAID_SECTION:
                        instructorCoursesViewHolder.COURSE_SALARY_STATE_BUTTON
                                .setBackgroundTintList(
                                        ColorStateList.valueOf(Color.GREEN)
                                );
                        instructorCoursesViewHolder.COURSE_SALARY_STATE_BUTTON.setText(
                                "paid"
                        );
                        break;
                    case Constants.NOT_PAID_SECTION:

                        instructorCoursesViewHolder.COURSE_SALARY_STATE_BUTTON
                                .setBackgroundTintList(
                                        ColorStateList.valueOf(Color.RED)
                                );
                        instructorCoursesViewHolder.COURSE_SALARY_STATE_BUTTON.setText(
                                "pay"
                        );
                        break;
                }

                instructorCoursesViewHolder.COURSE_SALARY_STATE_BUTTON.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Cursor instructorSectionCursor = getActivity().getContentResolver()
                                        .query(
                                                DatabaseController.UriDatabase.getSectionInstructorTableWithSectionIdUri(SECTION_ID),
                                                new String[]{DbContent.SectionInstructorTable.PAID_COLUMN},
                                                null,
                                                null,
                                                null
                                        );

                                if(instructorSectionCursor != null) {
                                    instructorSectionCursor.moveToFirst();
                                    Integer SECTION_PAID = instructorSectionCursor.getInt(0);

                                    instructorSectionCursor.close();
                                    // update database.
                                    ContentValues contentValues = new ContentValues();
                                    // update static data.
                                    Double totalPaidSalary = Double.valueOf(
                                            instructorSalaryScreenViewHolder.TOTAL_PAID_SALARY_TEXT_VIEW.getText().toString()
                                    );
                                    Double totalUnpaidSalary = Double.valueOf(
                                            instructorSalaryScreenViewHolder.TOTAL_UNPAID_SALARY_TEXT_VIEW.getText().toString()
                                    );
                                    Long paidCoursesNumber = Long.valueOf(
                                            instructorSalaryScreenViewHolder.NUMBER_OF_PAID_COURSES.getText().toString()
                                    );
                                    Long unpaidCoursesNumber = Long.valueOf(
                                            instructorSalaryScreenViewHolder.NUMBER_OF_UNPAID_COURSES.getText().toString()
                                    );
                                    Double INSTRUCTOR_SALARY = Double.valueOf(
                                            instructorCoursesViewHolder.COURSE_SALARY_TEXT_VIEW.getText().toString()
                                    );

                                    // set Button.
                                    switch (SECTION_PAID) {
                                        case Constants.NOT_PAID_SECTION:
                                            instructorCoursesViewHolder.COURSE_SALARY_STATE_BUTTON.setText(
                                                    "paid"
                                            );
                                            instructorCoursesViewHolder.COURSE_SALARY_STATE_BUTTON.setBackgroundTintList(
                                                    ColorStateList.valueOf(
                                                            Color.GREEN
                                                    )
                                            );

                                            // update database.
                                            contentValues.put(DbContent.SectionInstructorTable.PAID_COLUMN,
                                                    Constants.PAID_SECTION);
                                            getActivity().getContentResolver().update(
                                                    DatabaseController.UriDatabase.getSectionInstructorTableWithSectionIdUri(SECTION_ID),
                                                    contentValues,
                                                    null,
                                                    null
                                            );

                                            // update static data.
                                            paidCoursesNumber++;
                                            unpaidCoursesNumber--;
                                            totalPaidSalary += INSTRUCTOR_SALARY;
                                            totalUnpaidSalary -= INSTRUCTOR_SALARY;

                                            instructorSalaryScreenViewHolder.TOTAL_PAID_SALARY_TEXT_VIEW.setText(
                                                    String.valueOf(
                                                            totalPaidSalary
                                                    )
                                            );
                                            instructorSalaryScreenViewHolder.TOTAL_UNPAID_SALARY_TEXT_VIEW.setText(
                                                    String.valueOf(
                                                            totalUnpaidSalary
                                                    )
                                            );
                                            instructorSalaryScreenViewHolder.NUMBER_OF_PAID_COURSES.setText(
                                                    String.valueOf(
                                                            paidCoursesNumber
                                                    )
                                            );
                                            instructorSalaryScreenViewHolder.NUMBER_OF_UNPAID_COURSES.setText(
                                                    String.valueOf(
                                                            unpaidCoursesNumber
                                                    )
                                            );
                                            break;

                                        case Constants.PAID_SECTION:
                                            instructorCoursesViewHolder.COURSE_SALARY_STATE_BUTTON.setText(
                                                    "pay"
                                            );
                                            instructorCoursesViewHolder.COURSE_SALARY_STATE_BUTTON.setBackgroundTintList(
                                                    ColorStateList.valueOf(
                                                            Color.RED
                                                    )
                                            );

                                            contentValues.put(DbContent.SectionInstructorTable.PAID_COLUMN,
                                                    Constants.NOT_PAID_SECTION);
                                            getActivity().getContentResolver().update(
                                                    DatabaseController.UriDatabase.getSectionInstructorTableWithSectionIdUri(SECTION_ID),
                                                    contentValues,
                                                    null,
                                                    null
                                            );


                                            paidCoursesNumber--;
                                            unpaidCoursesNumber++;
                                            totalPaidSalary -= INSTRUCTOR_SALARY;
                                            totalUnpaidSalary += INSTRUCTOR_SALARY;

                                            instructorSalaryScreenViewHolder.TOTAL_PAID_SALARY_TEXT_VIEW.setText(
                                                    String.valueOf(
                                                            totalPaidSalary
                                                    )
                                            );
                                            instructorSalaryScreenViewHolder.TOTAL_UNPAID_SALARY_TEXT_VIEW.setText(
                                                    String.valueOf(
                                                            totalUnpaidSalary
                                                    )
                                            );
                                            instructorSalaryScreenViewHolder.NUMBER_OF_PAID_COURSES.setText(
                                                    String.valueOf(
                                                            paidCoursesNumber
                                                    )
                                            );
                                            instructorSalaryScreenViewHolder.NUMBER_OF_UNPAID_COURSES.setText(
                                                    String.valueOf(
                                                            unpaidCoursesNumber
                                                    )
                                            );
                                            break;
                                    }
                                }
                            }

                        }
                );
            }else{
                instructorCoursesViewHolder.COURSE_SALARY_STATE_BUTTON.setBackgroundTintList(
                        ColorStateList.valueOf(Color.GRAY)
                );
                instructorCoursesViewHolder.COURSE_SALARY_STATE_BUTTON.setText("Under Progress");
            }
        }else{
            instructorCoursesViewHolder.COURSE_SALARY_STATE_BUTTON.setText(
                    "paid"
            );
            instructorCoursesViewHolder.COURSE_SALARY_STATE_BUTTON.setBackgroundTintList(
                    ColorStateList.valueOf(Color.GRAY));
            instructorCoursesViewHolder.COURSE_SALARY_STATE_BUTTON.setEnabled(false);
        }
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
