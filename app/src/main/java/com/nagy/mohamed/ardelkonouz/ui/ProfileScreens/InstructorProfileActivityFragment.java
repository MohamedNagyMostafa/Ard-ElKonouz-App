package com.nagy.mohamed.ardelkonouz.ui.ProfileScreens;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nagy.mohamed.ardelkonouz.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class InstructorProfileActivityFragment extends Fragment {

    public InstructorProfileActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_instructor_profile, container, false);
    }
}