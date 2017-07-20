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
import com.nagy.mohamed.ardelkonouz.ui.InputScreens.InstructorInputActivity;
import com.nagy.mohamed.ardelkonouz.ui.ViewHolder;
import com.nagy.mohamed.ardelkonouz.ui.adapter.RecycleViewInstructorProfileAdapter;

/**
 * A placeholder fragment containing a simple view.
 */
public class InstructorProfileActivityFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<Cursor>{

    private RecycleViewInstructorProfileAdapter recycleViewInstructorProfileAdapter;
    private long instructorId;
    private ViewHolder.InstructorProfileScreenViewHolder instructorProfileScreenViewHolder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_instructor_profile, container, false);
        instructorProfileScreenViewHolder = new ViewHolder.InstructorProfileScreenViewHolder(rootView);
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        LinearSnapHelper linearSnapHelper = new LinearSnapHelper();

        recycleViewInstructorProfileAdapter = new RecycleViewInstructorProfileAdapter(getContext());
        instructorId = getActivity().getIntent().getExtras().getLong(Constants.INSTRUCTOR_ID_EXTRA);


        instructorProfileScreenViewHolder.INSTRUCTOR_COURSES_CHILD_RECYCLE_VIEW.setAdapter(recycleViewInstructorProfileAdapter);
        instructorProfileScreenViewHolder.INSTRUCTOR_COURSES_CHILD_RECYCLE_VIEW.setLayoutManager(linearLayoutManager);
        linearSnapHelper.attachToRecyclerView(instructorProfileScreenViewHolder.INSTRUCTOR_COURSES_CHILD_RECYCLE_VIEW);

        Cursor cursor = getActivity().getContentResolver().query(
                DatabaseController.UriDatabase.getInstructorTableWithIdUri(instructorId),
                DatabaseController.ProjectionDatabase.INSTRUCTOR_PROJECTION,
                null,
                null,
                null
        );

        if(cursor != null){
            if(cursor.getCount() > 0){
                setDataToViews(cursor, instructorProfileScreenViewHolder);
            }
            cursor.close();
        }

        instructorProfileScreenViewHolder.INSTRUCTOR_EDIT_BUTTON.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent instructorInputScreen = new Intent(getContext(), InstructorInputActivity.class);
                        instructorInputScreen.putExtra(Constants.INSTRUCTOR_ID_EXTRA, instructorId);
                        instructorInputScreen.putExtra(Constants.INPUT_TYPE_EXTRA, Constants.INPUT_EDIT_EXTRA);
                        startActivity(instructorInputScreen);
                    }
                }
        );

        getLoaderManager().initLoader(Constants.LOADER_INSTRUCTOR_COURSE_JOIN_LIST, null, this);

        return rootView;
    }

    private void setDataToViews(Cursor cursor,
                                ViewHolder.InstructorProfileScreenViewHolder instructorProfileScreenViewHolder){
        cursor.moveToFirst();
        instructorProfileScreenViewHolder.INSTRUCTOR_NAME_TEXT_VIEW.setText(
                cursor.getString(
                        DatabaseController.ProjectionDatabase.INSTRUCTOR_NAME
                )
        );
        instructorProfileScreenViewHolder.INSTRUCTOR_AGE_TEXT_VIEW.setText(
                String.valueOf(
                        cursor.getInt(
                                DatabaseController.ProjectionDatabase.INSTRUCTOR_AGE
                        )
                )
        );
        instructorProfileScreenViewHolder.INSTRUCTOR_ADDRESS_TEXT_VIEW.setText(
                cursor.getString(
                        DatabaseController.ProjectionDatabase.INSTRUCTOR_ADDRESS
                )
        );
        instructorProfileScreenViewHolder.INSTRUCTOR_MOBILE_TEXT_VIEW.setText(
                                cursor.getString(
                                        DatabaseController.ProjectionDatabase.INSTRUCTOR_MOBILE
                                )
        );
        instructorProfileScreenViewHolder.INSTRUCTOR_GENDER_TEXT_VIEW.setText(
                Utility.decodeGenderByInt(
                        cursor.getInt(
                                DatabaseController.ProjectionDatabase.INSTRUCTOR_GENDER
                        ),
                        getContext()
                )
        );
        instructorProfileScreenViewHolder.INSTRUCTOR_QUALIFICATION_TEXT_VIEW.setText(
                cursor.getString(
                        DatabaseController.ProjectionDatabase.INSTRUCTOR_QUALIFICATION
                )
        );
    }
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(
                getContext(),
                DatabaseController.UriDatabase.getSectionInstructorTableWithInstructorIdUri(instructorId),
                DatabaseController.ProjectionDatabase.SECTION_INSTRUCTOR_LIST_JOIN_TABLE,
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if(data.getCount() > 0){
            instructorProfileScreenViewHolder.INSTRUCTOR_COURSES_LIST_EMPTY_VIEW.setVisibility(View.GONE);
        }else{
            instructorProfileScreenViewHolder.INSTRUCTOR_COURSES_LIST_EMPTY_VIEW.setVisibility(View.VISIBLE);
        }

        recycleViewInstructorProfileAdapter.setCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        recycleViewInstructorProfileAdapter.setCursor(null);
    }
}
