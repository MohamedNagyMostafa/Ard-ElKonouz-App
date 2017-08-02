package com.nagy.mohamed.ardelkonouz.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nagy.mohamed.ardelkonouz.R;
import com.nagy.mohamed.ardelkonouz.component.Shift;
import com.nagy.mohamed.ardelkonouz.helper.Constants;
import com.nagy.mohamed.ardelkonouz.helper.Utility;
import com.nagy.mohamed.ardelkonouz.offlineDatabase.DatabaseController;
import com.nagy.mohamed.ardelkonouz.offlineDatabase.DbContent;
import com.nagy.mohamed.ardelkonouz.ui.InputScreens.SectionInputActivity;
import com.nagy.mohamed.ardelkonouz.ui.Listner.OnDeleteListener;
import com.nagy.mohamed.ardelkonouz.ui.ProfileScreens.SectionProfileActivity;
import com.nagy.mohamed.ardelkonouz.ui.ViewHolder;

import java.util.ArrayList;

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
                LayoutInflater.from(context).inflate(R.layout.course_pf_recycle_view, parent, false)
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
                            "Section " +
                                    String.valueOf(
                                            cursor.getInt(
                                                    DatabaseController.ProjectionDatabase.SECTION_NAME_COLUMN
                                            )
                                    )
                    )
            );

            Cursor shiftsCursor = context.getContentResolver().query(
                    DatabaseController.UriDatabase.getShiftWithSectionId(_ID),
                    DatabaseController.ProjectionDatabase.SHIFT_TABLE_PROJECTION,
                    null,
                    null,
                    null
            );

            ArrayList<Shift> shifts = new ArrayList<>();

            if(shiftsCursor != null){
                while (shiftsCursor.moveToNext()){
                    Shift shift = new Shift(
                            shiftsCursor.getLong(
                                    DatabaseController.ProjectionDatabase.SHIFT_START_DATE_COLUMN
                            ),
                            shiftsCursor.getLong(
                                    DatabaseController.ProjectionDatabase.SHIFT_END_DATE_COLUMN
                            ),
                            _ID
                    );

                    shifts.add(shift);
                }
                shiftsCursor.close();
            }

            sectionRecycleViewHolder.NEXT_SECTION_TEXT_VIEW.setText(
                    Utility.getNextDayAsString(
                            Utility.getNextSessionDay(
                                    shifts,
                                    cursor.getString(
                                            DatabaseController.ProjectionDatabase.SECTION_DAYS
                                    ),
                                    cursor.getLong(
                                            DatabaseController.ProjectionDatabase.SECTION_END_DATE
                                    ),
                                    cursor.getLong(
                                            DatabaseController.ProjectionDatabase.SECTION_START_DATE
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

            sectionRecycleViewHolder.SECTION_EDIT_IMAGE_VIEW.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent sectionInputScreen = new Intent(context, SectionInputActivity.class);
                            sectionInputScreen.putExtra(Constants.SECTION_ID_EXTRA, _ID);
                            sectionInputScreen.putExtra(Constants.INPUT_TYPE_EXTRA, Constants.INPUT_EDIT_EXTRA);
                            context.startActivity(sectionInputScreen);
                        }
                    }
            );

            sectionRecycleViewHolder.SECTION_BODY_LAYOUT.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent sectionProfileScreen = new Intent(context, SectionProfileActivity.class);
                            sectionProfileScreen.putExtra(Constants.SECTION_ID_EXTRA, _ID);
                            sectionProfileScreen.putExtra(Constants.COURSE_ID_EXTRA,
                                    cursor.getLong(
                                            DatabaseController.ProjectionDatabase.SECTION_COURSE_ID_COLUMN
                                    )
                            );
                            context.startActivity(sectionProfileScreen);
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