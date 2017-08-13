package com.nagy.mohamed.ardelkonouz.ui.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.nagy.mohamed.ardelkonouz.R;
import com.nagy.mohamed.ardelkonouz.helper.Constants;
import com.nagy.mohamed.ardelkonouz.helper.Utility;
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
            final Long START_DATE = cursor.getLong(
                    DatabaseController.ProjectionDatabase.SECTION_INSTRUCTOR_LIST_JOIN_SECTION_START_DATE
            );

            instructorCoursesViewHolder.COURSE_NAME_TEXT_VIEW.setText(
                    getCourseName(
                            cursor.getInt(
                                    DatabaseController.ProjectionDatabase.SECTION_INSTRUCTOR_LIST_JOIN_SECTION_NAME
                            ),
                        cursor.getLong(
                                DatabaseController.ProjectionDatabase.SECTION_INSTRUCTOR_LIST_JOIN_SECTION_ID
                        )
                    )
            );
            if(!START_DATE.equals(Constants.NULL)) {
                instructorCoursesViewHolder.COURSE_END_DATE_TEXT_VIEW.setText(
                        Utility.getTimeFormat(
                                cursor.getLong(DatabaseController.ProjectionDatabase.SECTION_INSTRUCTOR_LIST_JOIN_SECTION_END_DATE)
                        )
                );
                instructorCoursesViewHolder.COURSE_START_DATE_TEXT_VIEW.setText(
                        Utility.getTimeFormat(START_DATE)
                );
            }else{
                instructorCoursesViewHolder.COURSE_END_DATE_TEXT_VIEW.setText(
                        context.getString(R.string.empty_info)
                );
                instructorCoursesViewHolder.COURSE_START_DATE_TEXT_VIEW.setText(
                        context.getString(R.string.empty_info)
                );
            }


            final long SECTION_ID = cursor.getLong(DatabaseController.ProjectionDatabase.SECTION_INSTRUCTOR_LIST_JOIN_SECTION_ID);
            Cursor courseCursor = context.getContentResolver().query(
                    DatabaseController.UriDatabase.getSectionChildTableWithSectionIdUri(SECTION_ID),
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

    private String getCourseName(final Integer SECTION_NAME, final Long SECTION_ID){
        String courseName = "";

        Cursor courseCursor = context.getContentResolver().query(
                DatabaseController.UriDatabase.getCourseSectionJoinWithSectionId(SECTION_ID),
                new String[]{DbContent.CourseTable.COURSE_NAME_COLUMN},
                null,
                null,
                null
        );

        if(courseCursor != null){
            if(courseCursor.moveToFirst()){
                courseName = courseCursor.getString(0) + " Sec. " + String.valueOf(SECTION_NAME);
            }
            courseCursor.close();
        }

        return courseName;
    }


    @Override
    public int getItemCount() {
        return (cursor == null)? 0: cursor.getCount();
    }
}
