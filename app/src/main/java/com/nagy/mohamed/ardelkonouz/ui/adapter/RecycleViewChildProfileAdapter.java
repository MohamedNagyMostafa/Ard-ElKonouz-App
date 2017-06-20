package com.nagy.mohamed.ardelkonouz.ui.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.nagy.mohamed.ardelkonouz.R;
import com.nagy.mohamed.ardelkonouz.offlineDatabase.DatabaseController;
import com.nagy.mohamed.ardelkonouz.offlineDatabase.DbContent;
import com.nagy.mohamed.ardelkonouz.ui.ViewHolder;

/**
 * Created by mohamednagy on 6/20/2017.
 */
public class RecycleViewChildProfileAdapter extends RecyclerView.Adapter<ViewHolder.ChildProfileScreenViewHolder.ChildProfileListViewHolder> {

    private Cursor cursor;
    private Context context;

    public void setCursor(Cursor cursor, Context context){
        this.cursor = cursor;
        this.context =context;
        this.notifyDataSetChanged();
    }

    @Override
    public ViewHolder.ChildProfileScreenViewHolder.ChildProfileListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder.ChildProfileScreenViewHolder.ChildProfileListViewHolder(LayoutInflater.from(context).inflate(R.layout.child_pf_courses_instructors_recycle,parent,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder.ChildProfileScreenViewHolder.ChildProfileListViewHolder childProfileListViewHolder, int position) {
        Log.e("done","eeeeeeeeeeeeeeeeeeeeeeeeeeee");
        if(cursor != null && cursor.getCount() > 0){
            cursor.moveToPosition(position);
            childProfileListViewHolder.COURSE_NAME_TEXT_VIEW.setText(
                    cursor.getString(DatabaseController.ProjectionDatabase.COURSE_CHILD_JOIN_LIST_COURSE_NAME_COLUMN)
            );
            childProfileListViewHolder.START_DATE_TEXT_VIEW.setText(
                    String.valueOf(
                            cursor.getLong(DatabaseController.ProjectionDatabase.COURSE_CHILD_JOIN_LIST_COURSE_START_DATE_COLUMN)
                    )
            );
            childProfileListViewHolder.END_DATE_TEXT_VIEW.setText(
                    String.valueOf(
                            cursor.getLong(DatabaseController.ProjectionDatabase.COURSE_CHILD_JOIN_LIST_COURSE_END_DATE_COLUMN)
                    )
            );

            final int COURSE_ID = cursor.getInt(DatabaseController.ProjectionDatabase.COURSE_CHILD_JOIN_LIST_COURSE_ID_COLUMN);
            Cursor instructorCursor =
                    context.getContentResolver().query(
                            DatabaseController.UriDatabase.getCourseInstructorTableWithCourseIdUri(COURSE_ID),
                            new String[]{DbContent.InstructorTable.INSTRUCTOR_NAME_COLUMN},
                            null,
                            null,
                            null
                    );
            if(instructorCursor != null){
                if(instructorCursor.getCount() > 0){
                    instructorCursor.moveToFirst();
                    childProfileListViewHolder.INSTRUCTOR_NAME_TEXT_VIEW.setText(
                            instructorCursor.getString(0)
                    );
                }
                instructorCursor.close();
            }
        }
    }

    @Override
    public int getItemCount() {
        if(cursor != null)
            return cursor.getCount();
        else
            return 0;
    }
}
