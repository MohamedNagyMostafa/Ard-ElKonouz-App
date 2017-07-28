package com.nagy.mohamed.ardelkonouz.ui.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nagy.mohamed.ardelkonouz.R;
import com.nagy.mohamed.ardelkonouz.helper.Utility;
import com.nagy.mohamed.ardelkonouz.offlineDatabase.DatabaseController;
import com.nagy.mohamed.ardelkonouz.offlineDatabase.DbContent;
import com.nagy.mohamed.ardelkonouz.ui.Listner.OnDeleteListener;
import com.nagy.mohamed.ardelkonouz.ui.ViewHolder;

/**
 * Created by mohamednagy on 7/12/2017.
 */
public class RecycleViewCourseProfileAdapter extends
        RecyclerView.Adapter<ViewHolder.CourseProfileScreenViewHolder.SectionRecycleViewHolder> {

    private Cursor cursor;
    private Context context;
    private OnDeleteListener onDeleteListener;

    public RecycleViewCourseProfileAdapter(Context context,
                                           OnDeleteListener onDeleteListener) {
        this.context = context;
        this.onDeleteListener = onDeleteListener;
    }

    public void setCursor(Cursor cursor){
        this.cursor = cursor;
    }

    @Override
    public ViewHolder.CourseProfileScreenViewHolder.SectionRecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder.CourseProfileScreenViewHolder.SectionRecycleViewHolder(
                LayoutInflater.from(context).inflate(R.layout.course_pf_sections_recycle_view, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(ViewHolder.CourseProfileScreenViewHolder
                                             .SectionRecycleViewHolder sectionRecycleViewHolder,
                                 int position) {
        if(cursor != null && cursor.getCount() > 0) {
            cursor.moveToPosition(position);

            final Long _ID = cursor.getLong(
                DatabaseController.ProjectionDatabase.SECTION_ID
            );




            sectionRecycleViewHolder.SECTION_NAME_TEXT_VIEW.setText(
                    String.valueOf(
                            "Section" +
                                    String.valueOf(
                                            cursor.getLong(
                                                    DatabaseController.ProjectionDatabase.SECTION_ID
                                            )
                                    )
                    )
            );

            sectionRecycleViewHolder.SECTION_INSTRUCTOR_TEXT_VIEW.setText(
                    getInstructorName(_ID)
            );

            sectionRecycleViewHolder.SECTION_DELETE_IMAGE_VIEW.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            onDeleteListener.OnClickListener(_ID);
                        }
                    }
            );

        }

    }

    @Override
    public int getItemCount() {
        if(cursor != null)
            return cursor.getCount();
        else
            return 0;
    }

    private String getInstructorName(Long SECTION_ID){
        String instructorName = context.getString(R.string.empty_info);

        Cursor instructorCursor =  context.getContentResolver().query(
                DatabaseController.UriDatabase.getSectionInstructorTableWithSectionIdUri(SECTION_ID),
                new String[]{DbContent.InstructorTable.INSTRUCTOR_NAME_COLUMN},
                null,
                null,
                null
        );

        if(instructorCursor != null){
            if(instructorCursor.getCount() > 0){
                instructorCursor.moveToFirst();
                instructorName = instructorCursor.getString(0);
            }
            instructorCursor.close();
        }

        return instructorName;
    }
}
