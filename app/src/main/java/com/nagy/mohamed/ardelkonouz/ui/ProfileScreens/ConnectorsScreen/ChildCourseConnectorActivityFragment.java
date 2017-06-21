package com.nagy.mohamed.ardelkonouz.ui.ProfileScreens.ConnectorsScreen;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
public class ChildCourseConnectorActivityFragment extends Fragment
        implements CursorAdapterList, LoaderManager.LoaderCallbacks<Cursor>{

    private DatabaseCursorAdapter databaseCursorAdapter;
    private ArrayList<Integer> selectedCourses;
    private int childId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        selectedCourses = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_child_course_connector, container, false);
        ViewHolder.ChildCourseConnectorScreenViewHolder childCourseConnectorScreenViewHolder =
                new ViewHolder.ChildCourseConnectorScreenViewHolder(rootView);

        childId = getActivity().getIntent().getExtras().getInt(Constants.CHILD_ID_EXTRA);
        final String INPUT_TYPE = getActivity().getIntent().getExtras().getString(Constants.INPUT_TYPE_EXTRA);

        databaseCursorAdapter = new DatabaseCursorAdapter(getContext(), null, this);
        childCourseConnectorScreenViewHolder.COURSES_LIST_VIEW.setAdapter(databaseCursorAdapter);

        childCourseConnectorScreenViewHolder.REST_BUTTON.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        selectedCourses = new ArrayList<Integer>();
                        restartLoader();
                    }
                }
        );

        childCourseConnectorScreenViewHolder.SUBMIT_BUTTON.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        // delete all previous selections.
                        getActivity().getContentResolver().delete(
                                DatabaseController.UriDatabase.getCourseChildTableWithChildIdUri(childId),
                                null,
                                null
                        );

                        // Insert all selection
                        ArrayList<ContentValues> contentValuesArrayList = new ArrayList<ContentValues>();

                        for(final Integer COURSE_ID : selectedCourses){
                            ContentValues contentValues = new ContentValues();
                            contentValues.put(
                                    DbContent.ChildCourseTable.CHILD_COURSE_COMPLETED_COLUMN,
                                    Constants.CHILD_NOT_COMPLETE_COURSE
                            );
                            contentValues.put(
                                    DbContent.ChildCourseTable.CHILD_ID_COLUMN,
                                    childId
                            );
                            contentValues.put(
                                    DbContent.ChildCourseTable.COURSE_ID_COLUMN,
                                    COURSE_ID
                            );

                            contentValuesArrayList.add(contentValues);
                        }

                        getActivity().getContentResolver().bulkInsert(
                                DatabaseController.UriDatabase.COURSE_CHILD_URI,
                                (ContentValues[]) contentValuesArrayList.toArray()
                        );
                    }
                }
        );

        getLoaderManager().initLoader(Constants.LOADER_CHILD_COURSE_CONNECTOR, null, this);

        return rootView;

    }

    @Override
    public View newListView(ViewGroup viewGroup, Cursor cursor) {
        return LayoutInflater.from(getContext())
                .inflate(R.layout.child_course_recycle_view, viewGroup, false);
    }

    @Override
    public void bindListView(View view, Cursor cursor) {
        ViewHolder.ChildCourseConnectorScreenViewHolder.CoursesViewHolder coursesViewHolder
                = new ViewHolder.ChildCourseConnectorScreenViewHolder.CoursesViewHolder(view);

        coursesViewHolder.COURSE_COST_TEXT_VIEW.setText(
                String.valueOf(
                        cursor.getDouble(DatabaseController.ProjectionDatabase.COURSE_COST)
                )
        );

    }

    /**
     * get courses only have available positions.
     * @param id
     * @param args
     * @return
     */
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(
                getContext(),
                DatabaseController.UriDatabase.getCourseTableWithCompleteIdUri(Constants.COURSE_INCOMPLETE),
                DatabaseController.ProjectionDatabase.COURSE_PROJECTION,
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
        getLoaderManager().restartLoader(Constants.LOADER_CHILD_COURSE_CONNECTOR, null, this);
    }


}
