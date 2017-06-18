package com.nagy.mohamed.ardelkonouz.ui;

import android.view.View;
import android.widget.LinearLayout;

import com.nagy.mohamed.ardelkonouz.R;

/**
 * Created by mohamednagy on 6/18/2017.
 */
public class ViewHolder {

    public static class MainScreenViewHolder{

        public final LinearLayout CHILD_LIST_LAYOUT;
        public final LinearLayout INSTRUCTOR_LIST_LAYOUT;
        public final LinearLayout EMPLOYEE_LIST_LAYOUT;
        public final LinearLayout COURSES_LIST_LAYOUT;
        public final LinearLayout UPDATE_ONLINE_LAYOUT;
        public final LinearLayout SALARY_LIST_LAYOUT;

        public MainScreenViewHolder(View mainScreenView){
            CHILD_LIST_LAYOUT = (LinearLayout) mainScreenView.findViewById(R.id.child_list_layout_view);
            INSTRUCTOR_LIST_LAYOUT = (LinearLayout) mainScreenView.findViewById(R.id.instructor_list_layout_view);
            EMPLOYEE_LIST_LAYOUT = (LinearLayout) mainScreenView.findViewById(R.id.employee_list_layout_view);
            COURSES_LIST_LAYOUT = (LinearLayout) mainScreenView.findViewById(R.id.course_list_layout_view);
            UPDATE_ONLINE_LAYOUT = (LinearLayout) mainScreenView.findViewById(R.id.child_update_online_layout_view);
            SALARY_LIST_LAYOUT = (LinearLayout) mainScreenView.findViewById(R.id.salary_layout_view);
        }
    }
    
}
