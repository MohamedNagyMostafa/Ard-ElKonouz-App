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
import com.nagy.mohamed.ardelkonouz.ui.ViewHolder;

/**
 * Created by mohamednagy on 7/8/2017.
 */
public class RecycleViewSectionProfileAdapter extends
        RecyclerView.Adapter<ViewHolder.SectionProfileViewHolder.SectionShiftRecycleView> {

    private Cursor cursor;
    private Context context;
    private OnShiftDeleteListener onShiftDeleteListener;

    public RecycleViewSectionProfileAdapter(Context context,
                                            OnShiftDeleteListener onShiftDeleteListener){
        this.context = context;
        this.onShiftDeleteListener = onShiftDeleteListener;
    }

    public void setCursor(Cursor cursor){
        this.cursor = cursor;
    }

    @Override
    public ViewHolder.SectionProfileViewHolder.SectionShiftRecycleView onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder.SectionProfileViewHolder.SectionShiftRecycleView(
                LayoutInflater.from(context).inflate(R.layout.section_pf_shifts_recycle_view, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(ViewHolder.SectionProfileViewHolder
                                             .SectionShiftRecycleView sectionShiftRecycleView,
                                 int position) {
        if(cursor != null && cursor.getCount() > 0) {
            cursor.moveToPosition(position);

            final Long _ID = cursor.getLong(
                    DatabaseController.ProjectionDatabase.SHIFT_ID
            );

            sectionShiftRecycleView.SHIFT_BEGINNING_DATE_TEXT_VIEW.setText(
                    Utility.getTimeFormat(
                            cursor.getLong(
                                    DatabaseController.ProjectionDatabase.SHIFT_START_DATE_COLUMN
                            )
                    )
            );

            sectionShiftRecycleView.SHIFT_ENDING_DATE_TEXT_VIEW.setText(
                    Utility.getTimeFormat(
                            cursor.getLong(
                                    DatabaseController.ProjectionDatabase.SHIFT_END_DATE_COLUMN
                            )
                    )
            );

            sectionShiftRecycleView.SHIFT_DELETE_IMAGE_VIEW.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            onShiftDeleteListener.OnClickListener(_ID);
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
