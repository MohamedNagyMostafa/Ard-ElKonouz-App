package com.nagy.mohamed.ardelkonouz.ui.ListScreens;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

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
    private String searchChars = "";
    private View childSearchView;

    private TextWatcher searchTextWatcher =
            new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    searchChars = charSequence.toString();
                    restartLoader();
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            };

    private View.OnClickListener addNewChildListener =
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent childInputScreen = new Intent(getContext(), ChildInputActivity.class);
                    childInputScreen.putExtra(Constants.INPUT_TYPE_EXTRA, Constants.INPUT_ADD_EXTRA);
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

        // searching..
        if(childSearchView != null){
            final EditText CHILD_SEARCH_EDIT_TEXT = (EditText) childSearchView;
            CHILD_SEARCH_EDIT_TEXT.addTextChangedListener(searchTextWatcher);
        }

        childListScreenViewHolder.ADD_NEW_CHILD_BUTTON.setOnClickListener(addNewChildListener);
        childListScreenViewHolder.CHILD_LIST_VIEW.setAdapter(databaseCursorAdapter);
        childListScreenViewHolder.CHILD_LIST_VIEW.setEmptyView(childListScreenViewHolder.CHILD_LIST_EMPTY_VIEW);

        getLoaderManager().initLoader(Constants.LOADER_CHILD_LIST, null, this);

        return rootView;
    }

    @Override
    public View newListView(ViewGroup viewGroup, Cursor cursor) {
        return LayoutInflater.from(getContext())
                .inflate(R.layout.child_list_recycle_view, viewGroup, false);
    }

    @Override
    public void bindListView(final View view, final Cursor cursor) {

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

        final long CHILD_ID = cursor.getLong(
                DatabaseController.ProjectionDatabase.CHILD_LIST_ID
        );

        childListRecycleViewHolder.CHILD_DELETE_IMAGE_VIEW.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view1) {
                        // Delete child from child table.
                        getActivity().getContentResolver().delete(
                                DatabaseController.UriDatabase.getChildTableWithIdUri(CHILD_ID),
                                null,
                                null
                        );
                        // Delete child from child section table.
                        getActivity().getContentResolver().delete(
                                DatabaseController.UriDatabase.getSectionChildTableWithChildIdUri(CHILD_ID),
                                null,
                                null
                        );

                        restartLoader();
                    }
                }
        );

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent childProfile = new Intent(getActivity(), ChildProfileActivity.class);
                childProfile.putExtra(Constants.CHILD_ID_EXTRA, CHILD_ID);
                Log.e("child id ", String.valueOf(CHILD_ID));
                startActivity(childProfile);
            }
        });

        Cursor cursorChildSection = getActivity().getContentResolver().query(
                DatabaseController.UriDatabase.getSectionChildTableWithChildIdUri(CHILD_ID),
                new String[]{DbContent.ChildSectionTable.SECTION_ID_COLUMN},
                null,
                null,
                null
        );
        String courseSectionName = "";

        if(cursorChildSection != null) {
            if(cursorChildSection.getCount() != 0) {
                while(cursorChildSection.moveToNext()) {
                    Cursor sectionCursor = getActivity().getContentResolver().query(
                            DatabaseController.UriDatabase.getSectionWithId(
                                    cursorChildSection.getLong(0)
                            ),
                            DatabaseController.ProjectionDatabase.CHILD_LIST_SECTION_PROJECTION,
                            null,
                            null,
                            null
                    );

                    if(sectionCursor != null) {
                        sectionCursor.moveToFirst();
                        Integer sectionName = sectionCursor.getInt(
                                        DatabaseController.ProjectionDatabase.CHILD_LIST_SECTION_NAME
                                );
                        final Long COURSE_ID = sectionCursor.getLong(
                                DatabaseController.ProjectionDatabase.CHILD_LIST_SECTION_COURSE_ID
                        );
                        Cursor cursorCourse = getActivity().getContentResolver().query(
                                DatabaseController.UriDatabase.getCourseTableWithIdUri(COURSE_ID),
                                new String[]{DbContent.CourseTable.COURSE_NAME_COLUMN},
                                null,
                                null,
                                null
                        );

                        if (cursorCourse != null) {
                            if (cursorCourse.getCount() > 0) {
                                cursorCourse.moveToFirst();
                                String courseName =
                                        cursorCourse.getString(0);
                                if (courseSectionName.isEmpty())
                                    courseSectionName = courseName + ".Sec " + sectionName.toString();
                                else
                                    courseSectionName += ", " + courseName + ".Sec " + sectionName.toString();
                            }
                            cursorCourse.close();
                        }
                        sectionCursor.close();
                    }
                }
            }
            cursorChildSection.close();
        }

        if(!courseSectionName.isEmpty())
            childListRecycleViewHolder.CHILD_COURSES_TEXT_VIEW.setText(courseSectionName);
        else
            childListRecycleViewHolder.CHILD_COURSES_TEXT_VIEW.setText(getActivity().getString(R.string.empty_info));

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(
                getContext(),
                DatabaseController.UriDatabase.getChildTableWithSearchUri(searchChars),
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

    private void restartLoader(){
        getLoaderManager().restartLoader(Constants.LOADER_CHILD_LIST, null, this);

    }

    public void setEditTextView(View childSearchView){
        this.childSearchView = childSearchView;
    }
}
