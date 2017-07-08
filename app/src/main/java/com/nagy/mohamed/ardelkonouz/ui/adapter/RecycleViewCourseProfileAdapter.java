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
import com.nagy.mohamed.ardelkonouz.ui.ProfileScreens.CourseProfileActivityFragment;
import com.nagy.mohamed.ardelkonouz.ui.ViewHolder;

/**
 * Created by mohamednagy on 7/8/2017.
 */
public class RecycleViewCourseProfileAdapter extends
        RecyclerView.Adapter<ViewHolder.CourseProfileScreenViewHolder.ShiftRecycleViewHolder> {

    private Cursor cursor;
    private Context context;
    private CourseProfileActivityFragment courseProfileActivityFragment;

    public RecycleViewCourseProfileAdapter(Context context,
                                           CourseProfileActivityFragment courseProfileActivityFragment){
        this.context = context;
        this.courseProfileActivityFragment = courseProfileActivityFragment;
    }

    public void setCursor(Cursor cursor){
        this.cursor = cursor;
    }

    @Override
    public ViewHolder.CourseProfileScreenViewHolder.ShiftRecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder.CourseProfileScreenViewHolder.ShiftRecycleViewHolder(
                LayoutInflater.from(context).inflate(R.layout.course_pf_shifts_recycle_view, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(ViewHolder.CourseProfileScreenViewHolder
                                             .ShiftRecycleViewHolder shiftRecycleViewHolder,
                                 int position) {
        if(cursor != null && cursor.getCount() > 0) {
            cursor.move(position);

            final Long _ID = cursor.getLong(
                    DatabaseController.ProjectionDatabase.SHIFT_ID
            );

            shiftRecycleViewHolder.SHIFT_START_DATE_TEXT_VIEW.setText(
                    Utility.getTimeFormat(
                            cursor.getLong(
                                    DatabaseController.ProjectionDatabase.SHIFT_START_DATE_COLUMN
                            )
                    )
            );

            shiftRecycleViewHolder.SHIFT_END_DATE_TEXT_VIEW.setText(
                    Utility.getTimeFormat(
                            cursor.getLong(
                                    DatabaseController.ProjectionDatabase.SHIFT_END_DATE_COLUMN
                            )
                    )
            );

            shiftRecycleViewHolder.SHIFT_DELETE_IMAGE_VIEW.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            context.getContentResolver().delete(
                                    DatabaseController.UriDatabase.getShiftTableWithIdUri(_ID),
                                    null,
                                    null
                            );

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
}
