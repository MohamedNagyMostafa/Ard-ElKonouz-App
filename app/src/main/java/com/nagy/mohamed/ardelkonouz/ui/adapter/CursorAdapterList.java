package com.nagy.mohamed.ardelkonouz.ui.adapter;

import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by mohamednagy on 6/19/2017.
 */
public interface CursorAdapterList {

    View newListView(ViewGroup viewGroup, Cursor cursor);
    void bindListView(View view, Cursor cursor);
}
