package com.nagy.mohamed.ardelkonouz.ui.adapter;

import android.database.Cursor;
import android.view.ViewGroup;

import com.nagy.mohamed.ardelkonouz.ui.ViewHolder;

/**
 * Created by mohamednagy on 7/6/2017.
 */
public interface CursorAdapterSelection {
    ViewHolder.ShiftInputScreenViewHolder.SelectionCoursesViewHolder onCreateViewHolder(ViewGroup parent);

    void onBindViewHolder(
            ViewHolder.ShiftInputScreenViewHolder.SelectionCoursesViewHolder selectionCoursesViewHolder,
            Cursor cursor);
}
