package com.nagy.mohamed.ardelkonouz.ui.ListScreens;

import android.content.Intent;
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
import com.nagy.mohamed.ardelkonouz.offlineDatabase.DatabaseController;
import com.nagy.mohamed.ardelkonouz.offlineDatabase.DbContent;
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
        return LayoutInflater.from(getContext())
                .inflate(R.layout.child_list_recycle_view, viewGroup, false);
    }

    @Override
    public void bindListView(View view, Cursor cursor) {

        ViewHolder.ChildListScreenViewHolder.ChildListRecycleViewHolder childListRecycleViewHolder
                = new ViewHolder.ChildListScreenViewHolder.ChildListRecycleViewHolder(view);


            childListRecycleViewHolder.CHILD_NAME_TEXT_VIEW.setText(
                    cursor.getString(
                            DatabaseController.ProjectionDatabase
                                    .CHILD_LIST_NAME
                    ));

            childListRecycleViewHolder.CHILD_AGE_TEXT_VIEW.setText(
                    String.valueOf(
                        cursor.getInt(
                                DatabaseController.ProjectionDatabase
                                        .CHILD_LIST_AGE
                        )
                    )
            );


            final int childId = cursor.getInt(
                    DatabaseController.ProjectionDatabase.CHILD_LIST_ID
            );

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent childProfile = new Intent(getActivity(), ChildProfileActivity.class);
                    childProfile.putExtra(Constants.CHILD_ID_EXTRA, childId);
                    startActivity(childProfile);
                }
            });

        Cursor cursorCourses = getActivity().getContentResolver().query(
                DatabaseController.UriDatabase.getCourseChildTableWithChildIdUri(childId),
                new String[]{DbContent.CourseTable.COURSE_NAME_COLUMN},
                null,
                null,
                null
        );

        if(cursorCourses != null) {
            if(cursorCourses.getCount() != 0) {
                cursorCourses.moveToFirst();
                StringBuilder stringBuilderCourses = new StringBuilder(
                        cursorCourses.getString(0)
                );

                while (cursorCourses.moveToNext()) {
                    stringBuilderCourses.append(" - ").append(cursorCourses.getString(0));
                }

                childListRecycleViewHolder.CHILD_COURSES_TEXT_VIEW.setText(
                        stringBuilderCourses.toString()
                );
            }

            cursorCourses.close();

        }

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(
                getContext(),
                DatabaseController.UriDatabase.CHILD_TABLE_URI,
                DatabaseController.ProjectionDatabase.CHILD_LIST_PROJECTION,
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
