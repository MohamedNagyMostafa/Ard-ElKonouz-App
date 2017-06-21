package com.nagy.mohamed.ardelkonouz.ui.adapter;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

/**
 * Created by mohamednagy on 6/19/2017.
 */
public class DatabaseCursorAdapter extends CursorAdapter {

    CursorAdapterList m_cursorAdapterList;

    public DatabaseCursorAdapter(Context context, Cursor cursor, CursorAdapterList cursorAdapterList){
        super(context, cursor, 0);
        m_cursorAdapterList = cursorAdapterList;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return m_cursorAdapterList.newListView(viewGroup, cursor);
    }


    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        m_cursorAdapterList.bindListView(view, cursor);
    }

    @Override
    public int getCount() {
        if(getCursor() != null)
            Log.e("result get",String.valueOf(getCursor().getCount()));

        return super.getCount();
    }
}
