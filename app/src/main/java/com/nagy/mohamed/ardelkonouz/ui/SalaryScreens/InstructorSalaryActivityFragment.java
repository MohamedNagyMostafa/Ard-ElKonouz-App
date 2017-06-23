package com.nagy.mohamed.ardelkonouz.ui.SalaryScreens;

import android.content.ContentValues;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_instructor_salary, container, false);
        ViewHolder.InstructorSalaryScreenViewHolder instructorSalaryScreenViewHolder =
                new ViewHolder.InstructorSalaryScreenViewHolder(rootView);
        instructorId = getActivity().getIntent().getExtras().getLong(Constants.INSTRUCTOR_ID_EXTRA);

        // set paid settings.
        setSettingsData(instructorSalaryScreenViewHolder, instructorId);
        // set list adapter.
        instructorSalaryScreenViewHolder.INSTRUCTOR_COURSES_LIST_VIEW.setAdapter(databaseCursorAdapter);
        // initialize loader.
        getLoaderManager().initLoader(Constants.LOADER_INSTRUCTOR_SALARY, null, this);
        // return view.
        return rootView;
    }

    private void setSettingsData(ViewHolder.InstructorSalaryScreenViewHolder instructorSalaryScreenViewHolder,
                                 final long INSTRUCTOR_ID){
        long completeCourses = 0;
        long underProgressCourses = 0;
        double totalPaidSalary = 0;

        Cursor cursor = getActivity().getContentResolver().query(
                DatabaseController.UriDatabase.getCourseInstructorTableWithInstructorIdUri(INSTRUCTOR_ID),
                new String[]{
                DbContent.CourseTable.COURSE_END_DATE_COLUMN,
                DbContent.CourseInstructorTable.PAID_COLUMN,
                DbContent.CourseTable.COURSE_SALARY_PER_CHILD,
                DbContent.CourseInstructorTable.COURSE_ID_COLUMN},
                null,
                null,
                null
        );

        if(cursor != null){
            if(cursor.getCount() > 0){
                while(cursor.moveToNext()){
                    if(cursor.getLong(0) - Utility.getCurrentDateAsMills() > 0){
                        underProgressCourses++;
                    }else{
                        completeCourses++;
                    }
                    Cursor coursesCursor = getActivity().getContentResolver().query(
                            DatabaseController.UriDatabase.getCourseChildTableWithCourseIdUri(
                                    cursor.getLong(3)),
                            null,
                            null,
                            null,
                            null

                            );
                    if(coursesCursor != null){
                        totalPaidSalary += coursesCursor.getCount() * cursor.getDouble(2);
                        coursesCursor.close();
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

        instructorCoursesViewHolder.COURSE_DURATION_TEXT_VIEW.setText(
                new StringBuilder("").append(getString(R.string.from)).append(" ")
                .append(Utility.getTimeFormat(
                        cursor.getLong(2)
                )).append(" ").append(getString(R.string.to)).append(" ")
                        .append(Utility.getTimeFormat(
                                cursor.getLong(3)
                            )
                        )
        );
        instructorCoursesViewHolder.COURSE_NAME_TEXT_VIEW.setText(
                cursor.getString(4)
        );

        if(cursor.getInt(5) == Constants.PAID_COURSE){
            instructorCoursesViewHolder.COURSE_SALARY_STATE_BUTTON.setBackgroundColor(Color.GREEN);
            instructorCoursesViewHolder.COURSE_SALARY_TEXT_VIEW.setText(getString(R.string.paid));
        }else{
            instructorCoursesViewHolder.COURSE_SALARY_STATE_BUTTON.setBackgroundColor(Color.RED);
            instructorCoursesViewHolder.COURSE_SALARY_TEXT_VIEW.setText(getString(R.string.unpaid));

        }

        Cursor coursesCursor = getActivity().getContentResolver().query(
                DatabaseController.UriDatabase.getCourseChildTableWithCourseIdUri(cursor.getLong(6)),
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

        instructorCoursesViewHolder.COURSE_SALARY_STATE_BUTTON.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(cursor.getInt(5) == Constants.PAID_COURSE){
                            instructorCoursesViewHolder.COURSE_SALARY_STATE_BUTTON.setBackgroundColor(Color.GREEN);
                            instructorCoursesViewHolder.COURSE_SALARY_TEXT_VIEW.setText(getString(R.string.paid));

                            ContentValues contentValues = new ContentValues();
                            contentValues.put(DbContent.CourseInstructorTable.PAID_COLUMN, Constants.NOT_PAID_COURSE);
                            getActivity().getContentResolver().update(
                                    DatabaseController.UriDatabase.getCourseInstructorTableWithCourseIdUri(
                                            cursor.getLong(6)
                                    ),
                                    contentValues,
                                    null,
                                    null
                            );

                        }else{
                            instructorCoursesViewHolder.COURSE_SALARY_STATE_BUTTON.setBackgroundColor(Color.RED);
                            instructorCoursesViewHolder.COURSE_SALARY_TEXT_VIEW.setText(getString(R.string.unpaid));

                            ContentValues contentValues = new ContentValues();
                            contentValues.put(DbContent.CourseInstructorTable.PAID_COLUMN, Constants.PAID_COURSE);
                            getActivity().getContentResolver().update(
                                    DatabaseController.UriDatabase.getCourseInstructorTableWithCourseIdUri(
                                            cursor.getLong(6)
                                    ),
                                    contentValues,
                                    null,
                                    null
                            );
                        }
                    }
                }
        );
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(
                getContext(),
                DatabaseController.UriDatabase.getCourseInstructorTableWithInstructorIdUri(instructorId),
                new String[]{DbContent.CourseInstructorTable.TABLE_NAME + "." +
                        DbContent.CourseInstructorTable._ID,
                        DbContent.CourseTable.COURSE_SALARY_PER_CHILD,
                        DbContent.CourseTable.COURSE_START_DATE_COLUMN,
                        DbContent.CourseTable.COURSE_END_DATE_COLUMN,
                        DbContent.CourseTable.COURSE_NAME_COLUMN,
                        DbContent.CourseInstructorTable.PAID_COLUMN,
                DbContent.CourseInstructorTable.COURSE_ID_COLUMN},
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
