package com.nagy.mohamed.ardelkonouz.ui.InputScreens;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nagy.mohamed.ardelkonouz.R;
import com.nagy.mohamed.ardelkonouz.helper.Constants;
import com.nagy.mohamed.ardelkonouz.helper.DoubleChoice;
import com.nagy.mohamed.ardelkonouz.helper.Utility;
import com.nagy.mohamed.ardelkonouz.ui.ViewHolder;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class CourseInputActivityFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_course_input, container, false);
        ViewHolder.CourseInputScreenViewHolder courseInputScreenViewHolder =
                new ViewHolder.CourseInputScreenViewHolder(rootView);

        final String INPUT_TYPE = getActivity().getIntent().getExtras().getString(Constants.INPUT_TYPE_EXTRA);

//        final ArrayList<DoubleChoice> COURSE_STATE_LIST = setCourseStateListItem();

    }


}
