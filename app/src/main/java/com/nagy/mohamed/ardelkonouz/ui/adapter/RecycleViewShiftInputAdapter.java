package com.nagy.mohamed.ardelkonouz.ui.adapter;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.nagy.mohamed.ardelkonouz.ui.ViewHolder;

/**
 * Created by mohamednagy on 7/9/2017.
 */
public class RecycleViewShiftInputAdapter extends
        RecyclerView.Adapter<ViewHolder.ShiftInputScreenViewHolder.SelectionCoursesViewHolder> {

    private Cursor cursor;
    private CursorAdapterSelection cursorAdapterSelection;

    public RecycleViewShiftInputAdapter(CursorAdapterSelection cursorAdapterSelection){
        this.cursorAdapterSelection = cursorAdapterSelection;
    }

    public void swapCursor(Cursor cursor){
        this.cursor = cursor;
    }

    @Override
    public ViewHolder.ShiftInputScreenViewHolder.SelectionCoursesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return cursorAdapterSelection.onCreateViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(ViewHolder.ShiftInputScreenViewHolder.SelectionCoursesViewHolder selectionCoursesViewHolder, int position) {
        cursor.moveToPosition(position);
        cursorAdapterSelection.onBindViewHolder(selectionCoursesViewHolder, cursor);
    }

    @Override
    public int getItemCount() {
        if(cursor != null)
            return cursor.getCount();
        else
            return 0;
    }
}
