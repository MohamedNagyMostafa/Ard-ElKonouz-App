package com.nagy.mohamed.ardelkonouz.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

/**
 * Created by mohamednagy on 7/5/2017.
 */
public class ShiftInputAdapter extends ArrayAdapter {

    private ShiftAdapterList shiftAdapterList;
    private ShiftAdapterSelection shiftAdapterSelection;


    public ShiftInputAdapter(Context context, ShiftAdapterList shiftAdapterList) {
        super(context, 0);
        this.shiftAdapterList = shiftAdapterList;
    }

    public ShiftInputAdapter(Context context, ShiftAdapterSelection shiftAdapterSelection) {
        super(context, 0);
        this.shiftAdapterSelection = shiftAdapterSelection;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(shiftAdapterSelection == null)
            return shiftAdapterList.getViewList(position, parent);
        else
            return shiftAdapterSelection.getViewSelection(position, parent);
    }

    @Override
    public int getCount() {
        if(shiftAdapterSelection == null)
            return shiftAdapterList.getCountList();
        else
            return shiftAdapterSelection.getCountSelection();
    }
}
