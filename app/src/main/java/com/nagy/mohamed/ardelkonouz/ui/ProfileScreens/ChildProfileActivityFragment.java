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
import com.nagy.mohamed.ardelkonouz.offlineDatabase.DbContent;
import com.nagy.mohamed.ardelkonouz.ui.InputScreens.ChildInputActivity;
import com.nagy.mohamed.ardelkonouz.ui.ProfileScreens.ConnectorsScreen.ChildCourseConnectorActivity;
import com.nagy.mohamed.ardelkonouz.ui.ViewHolder;
import com.nagy.mohamed.ardelkonouz.ui.adapter.RecycleViewChildProfileAdapter;

/**
 * A placeholder fragment containing a simple view.
 */
public class ChildProfileActivityFragment extends Fragment
        implements  LoaderManager.LoaderCallbacks<Cursor> {

    private long childId;
    private RecycleViewChildProfileAdapter recycleViewChildProfileAdapter;
    private ViewHolder.ChildProfileScreenViewHolder childProfileScreenViewHolder;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_child_profile, container, false);
        childProfileScreenViewHolder = new ViewHolder.ChildProfileScreenViewHolder(rootView);
        childId = getActivity().getIntent().getExtras().getLong(Constants.CHILD_ID_EXTRA);
        recycleViewChildProfileAdapter = new RecycleViewChildProfileAdapter();

        Cursor childProfileData = getQueryChildData();

        if(childProfileData != null){
            if(childProfileData.getCount() > 0){
                childProfileData.moveToFirst();
                setDataToViews(childProfileScreenViewHolder, childProfileData);
            }
            childProfileData.close();
        }

        childProfileScreenViewHolder.CHILD_COURSES_RECYCLE_VIEW.setAdapter(recycleViewChildProfileAdapter);

        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        LinearSnapHelper snapHelper = new LinearSnapHelper();

        childProfileScreenViewHolder.CHILD_COURSES_RECYCLE_VIEW.setLayoutManager(linearLayoutManager);

        snapHelper.attachToRecyclerView(childProfileScreenViewHolder.CHILD_COURSES_RECYCLE_VIEW);

        childProfileScreenViewHolder.CHILD_EDIT_BUTTON.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inputChildScreen = new Intent(getContext(), ChildInputActivity.class);
                inputChildScreen.putExtra(Constants.CHILD_ID_EXTRA, childId);
                inputChildScreen.putExtra(Constants.INPUT_TYPE_EXTRA, Constants.INPUT_EDIT_EXTRA);
                startActivity(inputChildScreen);
            }
        });
        childProfileScreenViewHolder.ADD_SECTION_BUTTON.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        openCoursesSelectionWindow(childId);
                    }
                }
        );
        getLoaderManager().initLoader(Constants.LOADER_COURSE_CHILD_JOIN_LIST, null, this);

        return rootView;
    }

    private Cursor getQueryChildData(){
        return getActivity().getContentResolver().query(
                DatabaseController.UriDatabase.getChildTableWithIdUri(childId),
                DatabaseController.ProjectionDatabase.CHILD_PROJECTION,
                null,
                null,
                null
        );
    }

    private void setDataToViews(ViewHolder.ChildProfileScreenViewHolder childProfileScreenViewHolder,
                                Cursor childProfileData){
        childProfileScreenViewHolder.CHILD_NAME_TEXT_VIEW.setText(
                childProfileData.getString(DatabaseController.ProjectionDatabase.CHILD_NAME)
        );
        childProfileScreenViewHolder.CHILD_AGE_TEXT_VIEW.setText(
                String.valueOf(
                        childProfileData.getInt(DatabaseController.ProjectionDatabase.CHILD_AGE)
                )
        );
        childProfileScreenViewHolder.CHILD_BIRTH_ORDER_TEXT_VIEW.setText(
                Utility.decodeBirthOrderByInt(
                        childProfileData.getInt(DatabaseController.ProjectionDatabase.CHILD_BIRTH_ORDER),
                        getContext()
                )
        );
        childProfileScreenViewHolder.CHILD_GENDER_TEXT_VIEW.setText(
                Utility.decodeGenderByInt(
                        childProfileData.getInt(DatabaseController.ProjectionDatabase.CHILD_GENDER),
                        getContext()
                )
        );
        childProfileScreenViewHolder.MOTHER_NAME_TEXT_VIEW.setText(
                childProfileData.getString(DatabaseController.ProjectionDatabase.CHILD_MOTHER_NAME)
        );
        childProfileScreenViewHolder.MOTHER_MOBILE_TEXT_VIEW.setText(
                        childProfileData.getString(DatabaseController.ProjectionDatabase.CHILD_MOTHER_MOBILE)
        );
        childProfileScreenViewHolder.MOTHER_QUALIFICATION_TEXT_VIEW.setText(
                childProfileData.getString(DatabaseController.ProjectionDatabase.CHILD_MOTHER_QUALIFICATION)
        );
        childProfileScreenViewHolder.MOTHER_JOB_TEXT_VIEW.setText(
                childProfileData.getString(DatabaseController.ProjectionDatabase.CHILD_MOTHER_JOB)
        );
        childProfileScreenViewHolder.FATHER_NAME_TEXT_VIEW.setText(
                childProfileData.getString(DatabaseController.ProjectionDatabase.CHILD_FATHER_NAME)
        );
        childProfileScreenViewHolder.FATHER_MOBILE_TEXT_VIEW.setText(
                        childProfileData.getString(DatabaseController.ProjectionDatabase.CHILD_FATHER_MOBILE)
        );
        childProfileScreenViewHolder.FATHER_JOB_TEXT_VIEW.setText(
                childProfileData.getString(DatabaseController.ProjectionDatabase.CHILD_FATHER_JOB)
        );
        childProfileScreenViewHolder.WHATSAPP_TEXT_VIEW.setText(
                                childProfileData.getString(DatabaseController.ProjectionDatabase.CHILD_MOBILE_WHATSUP)
        );
        childProfileScreenViewHolder.CHILD_TOTAL_COST_TEXT_VIEW.setText(
                String.valueOf(getTotalCostForChild()) + " L.E"
        );
        childProfileScreenViewHolder.EDUCATION_TYPE_TEXT_VIEW.setText(
                Utility.decodeEducationTypeByInt(
                        childProfileData.getInt(DatabaseController.ProjectionDatabase.CHILD_EDUCATION_TYPE),
                        getContext()
                )
        );
        childProfileScreenViewHolder.EDUCATION_STAGE_TEXT_VIEW.setText(
                childProfileData.getString(
                        DatabaseController.ProjectionDatabase.CHILD_STUDY_YEAR
                )
        );
        childProfileScreenViewHolder.CHILD_CHARACTERISTIC_TEXT_VIEW.setText(
                Utility.decodeCharacteristicByInt(
                        childProfileData.getString(DatabaseController.ProjectionDatabase.CHILD_TRAITS),
                        getContext()
                )
        );
        childProfileScreenViewHolder.CHILD_HANDLING_PROBLEM_TEXT_VIEW.setText(
                Utility.decodeDealingProblemByInt(
                        childProfileData.getString(DatabaseController.ProjectionDatabase.CHILD_HANDLING),
                        getContext()
                )
        );
        childProfileScreenViewHolder.CHILD_FREE_TIME_TEXT_VIEW.setText(
                Utility.decodeFreeTimeByInt(
                        childProfileData.getString(DatabaseController.ProjectionDatabase.CHILD_FREE_TIME),
                        getContext()
                )
        );
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(
                getContext(),
                DatabaseController.UriDatabase.getSectionChildTableWithChildIdUri(childId),
                DatabaseController.ProjectionDatabase.SECTION_CHILD_JOIN_LIST_TABLE,
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // set empty view
        if(data.getCount() > 0){
            childProfileScreenViewHolder.EMPTY_VIEW_LAYOUT.setVisibility(View.GONE);
        }else{
            childProfileScreenViewHolder.EMPTY_VIEW_LAYOUT.setVisibility(View.VISIBLE);
        }
        recycleViewChildProfileAdapter.setCursor(data, getContext());
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        recycleViewChildProfileAdapter.setCursor(null, getContext());

    }

    private void openCoursesSelectionWindow(long childId){
        Intent coursesSelectionWindow = new Intent(getContext(), ChildCourseConnectorActivity.class);
        coursesSelectionWindow.putExtra(Constants.CHILD_ID_EXTRA, childId);
        startActivity(coursesSelectionWindow);
        getActivity().finish();
    }

    private double getTotalCostForChild(){
        double totalCost = 0;

        Cursor childSectionCursor = getActivity().getContentResolver().query(
                DatabaseController.UriDatabase.getSectionChildTableWithChildIdUri(childId),
                new String[]{DbContent.SectionTable.SECTION_COURSE_ID_COLUMN},
                null,
                null,
                null
        );

        if(childSectionCursor != null){
            while (childSectionCursor.moveToNext()){
                Cursor courseCursor = getActivity().getContentResolver().query(
                        DatabaseController.UriDatabase.getCourseTableWithIdUri(childSectionCursor.getLong(0)),
                        new String[]{DbContent.CourseTable.COURSE_COST_COLUMN},
                        null,
                        null,
                        null
                );

                if(courseCursor != null){
                    courseCursor.moveToNext();
                    totalCost += courseCursor.getDouble(0);
                    courseCursor.close();
                }
            }
            childSectionCursor.close();
        }
        return totalCost;
    }
}
