package com.nagy.mohamed.ardelkonouz.ui.SalaryScreens;

import android.content.Intent;
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
        implements CursorAdapterList, LoaderManager.LoaderCallbacks<Cursor>{

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

        return rootView;
    }

    private void setPaidValues(ViewHolder.SalaryScreenViewHolder salaryScreenViewHolder){
        long totalPaidValue = 0;
        long totalUnpaidValue = 0;
        long unpaidInstructorNumber = 0;
        ArrayList<Long> paidCoursesId = new ArrayList<>();
        ArrayList<Long> unpaidCoursesId = new ArrayList<>();

        Cursor instructorCourseCursor = getActivity().getContentResolver().query(
                DatabaseController.UriDatabase.COURSE_INSTRUCTOR_URI,
                DatabaseController.ProjectionDatabase.COURSE_INSTRUCTOR_TABLE,
                null,
                null,
                null
        );

        if(instructorCourseCursor != null){
            if(instructorCourseCursor.getCount() > 0){
                switch (instructorCourseCursor.getInt(
                        DatabaseController.ProjectionDatabase.COURSE_INSTRUCTOR_PAID)){

                    case Constants.PAID_COURSE:
                        paidCoursesId.add(instructorCourseCursor.getLong(
                                DatabaseController.ProjectionDatabase.COURSE_INSTRUCTOR_COURSE_ID
                        ));
                        break;
                    case Constants.NOT_PAID_COURSE:
                        unpaidCoursesId.add(instructorCourseCursor.getLong(
                                DatabaseController.ProjectionDatabase.COURSE_INSTRUCTOR_COURSE_ID
                        ));
                        unpaidInstructorNumber++;
                        break;
                }
            }
            instructorCourseCursor.close();
        }

        Cursor coursesCursor = getActivity().getContentResolver().query(
                DatabaseController.UriDatabase.COURSE_CHILD_URI,
                new String[]{DbContent.CourseTable.COURSE_SALARY_PER_CHILD,
                    DbContent.ChildCourseTable.COURSE_ID_COLUMN},
                null,
                null,
                null
        );

        if(coursesCursor != null){
            if(coursesCursor.getCount() > 0){
                while (coursesCursor.moveToNext()){
                    if(paidCoursesId.contains(coursesCursor.getLong(1))){
                        totalPaidValue += coursesCursor.getDouble(0);
                    }else if(unpaidCoursesId.contains(coursesCursor.getLong(1))){
                        totalUnpaidValue += coursesCursor.getDouble(0);
                    }
                }
            }
            coursesCursor.close();
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

        instructorsViewHolder.INSTRUCTOR_NAME_TEXT_VIEW.setText(
                cursor.getString(2)
        );
        instructorsViewHolder.INSTRUCTOR_COURSE_TEXT_VIEW.setText(
                cursor.getString(1)
        );

        if(cursor.getInt(0) == Constants.PAID_COURSE){
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
                openInstructorScreen.putExtra(Constants.INSTRUCTOR_ID_EXTRA, cursor.getLong(3));
                startActivity(openInstructorScreen);
            }
        });
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(
                getContext(),
                DatabaseController.UriDatabase.COURSE_INSTRUCTOR_URI,
                new String[]{
                        DbContent.CourseInstructorTable.PAID_COLUMN,
                        DbContent.CourseTable.COURSE_NAME_COLUMN,
                        DbContent.InstructorTable.INSTRUCTOR_NAME_COLUMN,
                        DbContent.CourseInstructorTable.INSTRUCTOR_ID_COLUMN
                },
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
