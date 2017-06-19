package com.nagy.mohamed.ardelkonouz.ui.ListScreens;

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
import com.nagy.mohamed.ardelkonouz.ui.InputScreens.ChildInputActivity;
import com.nagy.mohamed.ardelkonouz.ui.ProfileScreens.ChildProfileActivity;
import com.nagy.mohamed.ardelkonouz.ui.ViewHolder;
import com.nagy.mohamed.ardelkonouz.ui.adapter.CursorAdapterList;
import com.nagy.mohamed.ardelkonouz.ui.adapter.DatabaseCursorAdapter;

/**
 * A placeholder fragment containing a simple view.
 */
public class ChildActivityFragment extends Fragment
        implements CursorAdapterList, LoaderManager.LoaderCallbacks<Cursor> {

    private DatabaseCursorAdapter databaseCursorAdapter;

    private View.OnClickListener addNewChildListener =
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent childInputScreen = new Intent(getContext(), ChildInputActivity.class);
                    startActivity(childInputScreen);
                }
            };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_child, container, false);
        ViewHolder.ChildListScreenViewHolder childListScreenViewHolder =
                new ViewHolder.ChildListScreenViewHolder(rootView);

        databaseCursorAdapter = new DatabaseCursorAdapter(getContext(), null, this);

        childListScreenViewHolder.ADD_NEW_CHILD_BUTTON.setOnClickListener(addNewChildListener);
        childListScreenViewHolder.CHILD_LIST_VIEW.setAdapter(databaseCursorAdapter);

        getLoaderManager().initLoader(Constants.LOADER_CHILD_LIST, null, this);

        return rootView;
    }

    @Override
    public View newListView(ViewGroup viewGroup, Cursor cursor) {
        return View.inflate(getContext(), R.layout.child_list_recycle_view, viewGroup);
    }

    @Override
    public void bindListView(View view, Cursor cursor) {

        ViewHolder.ChildListScreenViewHolder.ChildListRecycleViewHolder childListRecycleViewHolder
                = new ViewHolder.ChildListScreenViewHolder.ChildListRecycleViewHolder(view);

        if(cursor.moveToFirst()) {

            childListRecycleViewHolder.CHILD_NAME_TEXT_VIEW.setText(
                    cursor.getString(
                            DatabaseController.ProjectionDatabase
                                    .COURSE_CHILD_JOIN_CHILD_NAME_COLUMN_LIST
                    ));

            childListRecycleViewHolder.CHILD_AGE_TEXT_VIEW.setText(
                    cursor.getInt(
                            DatabaseController.ProjectionDatabase
                                    .COURSE_CHILD_JOIN_CHILD_AGE_COLUMN_LIST
                    ));


            final int childId = cursor.getInt(
                    DatabaseController.ProjectionDatabase.COURSE_CHILD_JOIN_CHILD_ID_COLUMN_LIST
            );

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent childProfile = new Intent(getActivity(), ChildProfileActivity.class);
                    childProfile.putExtra(Constants.CHILD_ID_EXTRA, childId);
                    startActivity(childProfile);
                }
            });

            StringBuilder stringBuilderCourses = new StringBuilder(
                    cursor.getString(
                            DatabaseController.ProjectionDatabase.COURSE_CHILD_JOIN_COURSE_NAME_COLUMN_LIST
                    )
            );

            while(cursor.moveToNext()){
                stringBuilderCourses.append(" - ").append(cursor.getString(
                        DatabaseController.ProjectionDatabase.COURSE_CHILD_JOIN_COURSE_NAME_COLUMN_LIST
                ));
            }

            childListRecycleViewHolder.CHILD_COURSES_TEXT_VIEW.setText(
                    stringBuilderCourses.toString()
            );

        }

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(
                getContext(),
                DatabaseController.UriDatabase.COURSE_CHILD_URI,
                DatabaseController.ProjectionDatabase.COURSE_CHILD_JOIN_TABLE_RECYCLE_LIST,
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        databaseCursorAdapter.swapCursor(data);
        Log.e("load finished","vvvvvvvvvvvvvvvvvvvv");
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        databaseCursorAdapter.swapCursor(null);
    }

}
