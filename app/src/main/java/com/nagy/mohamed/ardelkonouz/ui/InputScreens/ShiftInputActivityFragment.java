package com.nagy.mohamed.ardelkonouz.ui.InputScreens;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nagy.mohamed.ardelkonouz.R;
import com.nagy.mohamed.ardelkonouz.ui.adapter.ShiftAdapterList;
import com.nagy.mohamed.ardelkonouz.ui.adapter.ShiftAdapterSelection;

/**
 * A placeholder fragment containing a simple view.
 */
public class ShiftInputActivityFragment extends Fragment
        implements ShiftAdapterList, ShiftAdapterSelection{

    public ShiftInputActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_shift_input, container, false);
    }

    @Override
    public View getViewList(int position, ViewGroup parent) {
        return null;
    }

    @Override
    public int getCountList() {
        return 0;
    }

    @Override
    public View getViewSelection(int position, ViewGroup parent) {
        return null;
    }

    @Override
    public int getCountSelection() {
        return 0;
    }
}
