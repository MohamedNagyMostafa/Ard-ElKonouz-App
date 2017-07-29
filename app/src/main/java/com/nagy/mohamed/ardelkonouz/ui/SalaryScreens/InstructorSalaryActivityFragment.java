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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nagy.mohamed.ardelkonouz.R;
import com.nagy.mohamed.ardelkonouz.helper.Constants;
import com.nagy.mohamed.ardelkonouz.helper.Utility;
import com.nagy.mohamed.ardelkonouz.offlineDatabase.DatabaseController;
import com.nagy.mohamed.ardelkonouz.offlineDatabase.DbContent;
import com.nagy.mohamed.ardelkonouz.ui.ViewHolder;
import com.nagy.mohamed.ardelkonouz.ui.adapter.CursorAdapterList;
import com.nagy.mohamed.ardelkonouz.ui.adapter.DatabaseCursorAdapter;

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
        double totalPaidSalary = 0;
        double totalUnpaidSalary = 0;
        long paidCoursesNumber = 0;
        long unpaidCoursesNumber = 0;


        Cursor cursor = getActivity().getContentResolver().query(
                DatabaseController.UriDatabase.getSectionInstructorTableWithInstructorIdUri(instructorId),
                new String[]{
                DbContent.SectionTable.SECTION_END_DATE_COLUMN,
                DbContent.SectionInstructorTable.PAID_COLUMN,
                DbContent.CourseTable.COURSE_SALARY_PER_CHILD,
                DbContent.SectionInstructorTable.SECTION_ID_COLUMN},
                null,
                null,
                null
        );

        if(cursor != null){
            if(cursor.getCount() > 0){
                while(cursor.moveToNext()){
                    // course state
                    if(cursor.getLong(0) - Utility.getCurrentDateAsMills() > 0){
                        underProgressCourses++;
                    }else{
                        completeCourses++;

                        Cursor coursesCursor = getActivity().getContentResolver().query(
                                DatabaseController.UriDatabase.getSectionChildTableWithSectionIdUri(
                                        cursor.getLong(3)),
                                null,
                                null,
                                null,
                                null

                        );

                        // paid
                        if(cursor.getInt(1) == Constants.PAID_SECTION) {
                            paidCoursesNumber++;

                            if (coursesCursor != null) {
                                totalPaidSalary += coursesCursor.getCount() * cursor.getDouble(2);
                                coursesCursor.close();
                            }
                        }else{
                            unpaidCoursesNumber++;

                            if (coursesCursor != null) {
                                totalUnpaidSalary += coursesCursor.getCount() * cursor.getDouble(2);
                                coursesCursor.close();
                            }
                        }
                    }


                }
            }
            cursor.close();
        }

        instructorSalaryScreenViewHolder.COMPLETED_COURSES_TEXT_VIEW.setText(
                String.valueOf(completeCourses)
        );
        instructorSalaryScreenViewHolder.TOTAL_PAID_SALARY_TEXT_VIEW.setText(
                String.valueOf(totalPaidSalary)
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
                String.valueOf(totalUnpaidSalary)
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
        final long COURSE_ID = cursor.getLong(6);
        instructorCoursesViewHolder.COURSE_START_DATE_TEXT_VIEW.setText(
               Utility.getTimeFormat(
                        cursor.getLong(2)
                )
        );
        instructorCoursesViewHolder.COURSE_END_DATE_TEXT_VIEW.setText(
                        Utility.getTimeFormat(
                                cursor.getLong(3)

                        )
        );

        instructorCoursesViewHolder.COURSE_NAME_TEXT_VIEW.setText(
                cursor.getString(4)
        );

        if(cursor.getLong(3) - Utility.getCurrentDateAsMills() > 0){
            instructorCoursesViewHolder.COURSE_SALARY_STATE_BUTTON.setBackgroundTintList(ColorStateList.valueOf(Color.GRAY));
            instructorCoursesViewHolder.COURSE_SALARY_STATE_BUTTON.setText("Under Progress");
        }else {
            if (cursor.getInt(5) == Constants.PAID_SECTION) {
                instructorCoursesViewHolder.COURSE_SALARY_STATE_BUTTON.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                instructorCoursesViewHolder.COURSE_SALARY_STATE_BUTTON.setText(getString(R.string.paid));
            } else {
                instructorCoursesViewHolder.COURSE_SALARY_STATE_BUTTON.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                instructorCoursesViewHolder.COURSE_SALARY_STATE_BUTTON.setText(getString(R.string.unpaid));
            }

            instructorCoursesViewHolder.COURSE_SALARY_STATE_BUTTON.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if((instructorCoursesViewHolder.COURSE_SALARY_STATE_BUTTON.getText().toString()).equals(
                                    getString(R.string.paid))){
                                instructorCoursesViewHolder.COURSE_SALARY_STATE_BUTTON.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                                instructorCoursesViewHolder.COURSE_SALARY_STATE_BUTTON.setText(getString(R.string.unpaid));

                                ContentValues contentValues = new ContentValues();
                                contentValues.put(DbContent.SectionInstructorTable.PAID_COLUMN, Constants.NOT_PAID_SECTION);
                                int l = getActivity().getContentResolver().update(
                                        DatabaseController.UriDatabase.getSectionInstructorTableWithSectionIdUri(
                                                COURSE_ID
                                        ),
                                        contentValues,
                                        null,
                                        null
                                );
                                Log.e("update result1 ", String.valueOf(l));

                            }else{
                                instructorCoursesViewHolder.COURSE_SALARY_STATE_BUTTON.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                                instructorCoursesViewHolder.COURSE_SALARY_STATE_BUTTON.setText(getString(R.string.paid));

                                ContentValues contentValues = new ContentValues();
                                contentValues.put(DbContent.SectionInstructorTable.PAID_COLUMN, Constants.PAID_SECTION);
                                int l = getActivity().getContentResolver().update(
                                        DatabaseController.UriDatabase.getSectionInstructorTableWithSectionIdUri(
                                                COURSE_ID
                                        ),
                                        contentValues,
                                        null,
                                        null
                                );
                                Log.e("update result 2", String.valueOf(l));

                            }

                            setSettingsData();
                            restartLoader();
                        }
                    }
            );
        }

        Cursor coursesCursor = getActivity().getContentResolver().query(
                DatabaseController.UriDatabase.getSectionChildTableWithSectionIdUri(cursor.getLong(6)),
                null,
                null,
                null,
                null
        );

        if(coursesCursor != null){
            totalSalary += coursesCursor.getCount() * cursor.getDouble(1);
            coursesCursor.close();
        }

        instructorCoursesViewHolder.COURSE_SALARY_TEXT_VIEW.setText(
                String.valueOf(
                        totalSalary
                )
        );

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(
                getContext(),
                DatabaseController.UriDatabase.getSectionInstructorTableWithInstructorIdUri(instructorId),
                new String[]{DbContent.SectionInstructorTable.TABLE_NAME + "." +
                        DbContent.SectionInstructorTable._ID,
                        DbContent.CourseTable.COURSE_SALARY_PER_CHILD,
                        DbContent.SectionTable.SECTION_START_DATE_COLUMN,
                        DbContent.SectionTable.SECTION_END_DATE_COLUMN,
                        DbContent.CourseTable.COURSE_NAME_COLUMN,
                        DbContent.SectionInstructorTable.PAID_COLUMN,
                DbContent.SectionInstructorTable.SECTION_ID_COLUMN},
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
