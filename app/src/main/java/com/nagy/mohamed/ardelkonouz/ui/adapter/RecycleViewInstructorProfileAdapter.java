package com.nagy.mohamed.ardelkonouz.ui.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.nagy.mohamed.ardelkonouz.R;
import com.nagy.mohamed.ardelkonouz.offlineDatabase.DatabaseController;
import com.nagy.mohamed.ardelkonouz.offlineDatabase.DbContent;
import com.nagy.mohamed.ardelkonouz.ui.ViewHolder;

/**
 * Created by mohamednagy on 6/20/2017.
 */
public class RecycleViewInstructorProfileAdapter extends
        RecyclerView.Adapter<ViewHolder.InstructorProfileScreenViewHolder.InstructorCoursesViewHolder> {

    private Cursor cursor;
    private Context context;

    public RecycleViewInstructorProfileAdapter(Context context){
        this.context = context;
    }

    public void setCursor(Cursor cursor){
        this.cursor = cursor;
    }

    @Override
    public ViewHolder.InstructorProfileScreenViewHolder.InstructorCoursesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder.InstructorProfileScreenViewHolder.InstructorCoursesViewHolder(
                LayoutInflater.from(context).inflate(R.layout.instructor_pf_courses_children_recycle, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(ViewHolder.InstructorProfileScreenViewHolder
                                             .InstructorCoursesViewHolder instructorCoursesViewHolder,
                                 int position) {
        if(cursor != null && cursor.getCount() > 0){
            cursor.moveToPosition(position);
            instructorCoursesViewHolder.COURSE_NAME_TEXT_VIEW.setText(
                    cursor.getString(
                            DatabaseController.ProjectionDatabase.COURSE_INSTRUCTOR_LIST_JOIN_COURSE_NAME
                    )
            );
            instructorCoursesViewHolder.COURSE_END_DATE_TEXT_VIEW.setText(
                    String.valueOf(
                            cursor.getLong(DatabaseController.ProjectionDatabase.COURSE_INSTRUCTOR_LIST_JOIN_COURSE_END_DATE)
                    )
            );
            instructorCoursesViewHolder.COURSE_START_DATE_TEXT_VIEW.setText(
                    String.valueOf(
                            cursor.getLong(DatabaseController.ProjectionDatabase.COURSE_INSTRUCTOR_LIST_JOIN_COURSE_START_DATE)
                    )
            );


            final int COURSE_ID = cursor.getInt(DatabaseController.ProjectionDatabase.COURSE_INSTRUCTOR_LIST_JOIN_COURSE_ID);
            Cursor courseCursor = context.getContentResolver().query(
                    DatabaseController.UriDatabase.getCourseChildTableWithCourseIdUri(COURSE_ID),
                    new String[]{DbContent.ChildTable.CHILD_NAME_COLUMN},
                    null,
                    null,
                    null
            );

            String children = null;

            if(courseCursor != null){
                if(courseCursor.getCount() > 0){
                    courseCursor.moveToFirst();
                    StringBuilder stringBuilder = new StringBuilder(courseCursor.getString(0));

                    while (courseCursor.moveToNext()){
                        stringBuilder.append("\n").append(courseCursor.getString(0));
                    }

                    children = stringBuilder.toString();
                }
                courseCursor.close();
            }

            if(children == null) {
                instructorCoursesViewHolder.COURSE_CHILDREN_TEXT_VIEW.setText(
                        context.getString(R.string.empty_info)
                );
            }else {
                instructorCoursesViewHolder.COURSE_CHILDREN_TEXT_VIEW.setText(
                        children
                );
            }
        }
    }

    @Override
    public int getItemCount() {
        return (cursor == null)? 0: cursor.getCount();
    }
}
