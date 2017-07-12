package com.nagy.mohamed.ardelkonouz.ui.ProfileScreens;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nagy.mohamed.ardelkonouz.R;
import com.nagy.mohamed.ardelkonouz.helper.Constants;
import com.nagy.mohamed.ardelkonouz.helper.Utility;
import com.nagy.mohamed.ardelkonouz.offlineDatabase.DatabaseController;
import com.nagy.mohamed.ardelkonouz.ui.InputScreens.CourseInputActivity;
import com.nagy.mohamed.ardelkonouz.ui.ViewHolder;
import com.nagy.mohamed.ardelkonouz.ui.Listner.OnDeleteListener;
import com.nagy.mohamed.ardelkonouz.ui.adapter.RecycleViewCourseProfileAdapter;

/**
 * A placeholder fragment containing a simple view.
 */
public class CourseProfileActivityFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<Cursor>{
    
    private RecycleViewCourseProfileAdapter recycleViewCourseProfileAdapter;
    private Long courseId;
    private OnDeleteListener onDeleteListener =
            new OnDeleteListener() {
                @Override
                public void OnClickListener(Long SECTION_ID) {
                    // Delete section from child.
                    getActivity().getContentResolver().delete(
                            DatabaseController.UriDatabase.getSectionChildTableWithSectionIdUri(SECTION_ID),
                            null,
                            null
                    );
                    // Delete section from instructor.
                    getActivity().getContentResolver().delete(
                            DatabaseController.UriDatabase.getSectionInstructorTableWithSectionIdUri(SECTION_ID),
                            null,
                            null
                    );
                    // Delete shifts from section.
                    getActivity().getContentResolver().delete(
                            DatabaseController.UriDatabase.getShiftWithSectionId(SECTION_ID),
                            null,
                            null
                    );
                    // Delete section from course.
                    getActivity().getContentResolver().delete(
                            DatabaseController.UriDatabase.getSectionWithCourseId(courseId),
                            null,
                            null
                    );

                    restartLoader();
                }
            };

    private ViewHolder.CourseProfileScreenViewHolder courseProfileScreenViewHolder;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_course_profile, container, false);
        courseProfileScreenViewHolder = new ViewHolder.CourseProfileScreenViewHolder(rootView);
        courseId = getActivity().getIntent().getExtras().getLong(Constants.COURSE_ID_EXTRA);
        recycleViewCourseProfileAdapter = new RecycleViewCourseProfileAdapter(getContext(), onDeleteListener);
        
        courseProfileScreenViewHolder.COURSE_SECTION_RECYCLE_VIEW.setAdapter(recycleViewCourseProfileAdapter);
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        LinearSnapHelper snapHelper = new LinearSnapHelper();
        
        courseProfileScreenViewHolder.COURSE_SECTION_RECYCLE_VIEW.setLayoutManager(linearLayoutManager);
        snapHelper.attachToRecyclerView(courseProfileScreenViewHolder.COURSE_SECTION_RECYCLE_VIEW);


        Cursor cursorCourses = getActivity().getContentResolver().query(
                DatabaseController.UriDatabase.getCourseTableWithIdUri(courseId),
                DatabaseController.ProjectionDatabase.COURSE_PROJECTION,
                null,
                null,
                null
        );

        if(cursorCourses != null){
            if(cursorCourses.getCount() > 0){
                cursorCourses.moveToFirst();
                setDataToView(cursorCourses, courseProfileScreenViewHolder);
            }
            cursorCourses.close();
        }
        
        courseProfileScreenViewHolder.COURSE_EDIT_BUTTON.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent courseInputScreen = new Intent(getContext(), CourseInputActivity.class);
                        courseInputScreen.putExtra(Constants.COURSE_ID_EXTRA, courseId);
                        courseInputScreen.putExtra(Constants.INPUT_TYPE_EXTRA, Constants.INPUT_EDIT_EXTRA);
                        startActivity(courseInputScreen);
                    }
                }
        );

        
        getLoaderManager().initLoader(Constants.LOADER_COURSE_SECTION_LOADER, null, this);

        return rootView;
    }

    private void setDataToView(Cursor cursor,
                               ViewHolder.CourseProfileScreenViewHolder courseProfileScreenViewHolder){

        final String COURSE_NAME =
                cursor.getString(DatabaseController.ProjectionDatabase.COURSE_NAME);
        final double COURSE_COST =
                cursor.getDouble(DatabaseController.ProjectionDatabase.COURSE_COST);
        final double COURSE_HOURS =
                cursor.getDouble(DatabaseController.ProjectionDatabase.COURSE_HOURS);
        final int COURSE_LEVEL =
                cursor.getInt(DatabaseController.ProjectionDatabase.COURSE_LEVEL);
        final double COURSE_PERCENT_PER_CHILD =
                cursor.getDouble(DatabaseController.ProjectionDatabase.COURSE_SALARY_PER_CHILD);

        courseProfileScreenViewHolder.COURSE_NAME_TEXT_VIEW.setText(COURSE_NAME);
        courseProfileScreenViewHolder.COURSE_COST_TEXT_VIEW.setText(String.valueOf(COURSE_COST));
        courseProfileScreenViewHolder.COURSE_HOURS_TEXT_VIEW.setText(String.valueOf(COURSE_HOURS));
        courseProfileScreenViewHolder.COURSE_LEVEL_TEXT_VIEW.setText(String.valueOf(COURSE_LEVEL));
        courseProfileScreenViewHolder.COURSE_SALARY_PER_CHILD_TEXT_VIEW.setText(new StringBuilder("").append(
                String.valueOf(COURSE_PERCENT_PER_CHILD)).append("%"));
        courseProfileScreenViewHolder.COURSE_AGE_RANGE_TEXT_VIEW.setText(
                Utility.decodeAgeRangeByInt(
                        cursor.getInt(
                                DatabaseController.ProjectionDatabase.COURSE_START_AGE
                        ),
                        cursor.getInt(
                                DatabaseController.ProjectionDatabase.COURSE_END_AGE
                        )
                )
        );

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(
                getContext(),
                DatabaseController.UriDatabase.getShiftWithSectionJoinId(courseId),
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
             courseProfileScreenViewHolder.COURSE_SECTION_EMPTY_LAYOUT.setVisibility(View.GONE);
        }else{
            courseProfileScreenViewHolder.COURSE_SECTION_EMPTY_LAYOUT.setVisibility(View.VISIBLE);
        }
        recycleViewCourseProfileAdapter.setCursor(data);
        recycleViewCourseProfileAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        recycleViewCourseProfileAdapter.setCursor(null);
        recycleViewCourseProfileAdapter.notifyDataSetChanged();
    }

    public void restartLoader(){
        getLoaderManager().restartLoader(Constants.LOADER_COURSE_SECTION_LOADER, null, this);
    }
}
