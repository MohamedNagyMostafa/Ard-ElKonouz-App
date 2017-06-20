package com.nagy.mohamed.ardelkonouz.ui.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.nagy.mohamed.ardelkonouz.R;
import com.nagy.mohamed.ardelkonouz.ui.ViewHolder;

/**
 * Created by mohamednagy on 6/20/2017.
 */
public class RecycleViewInstructorProfileAdapter extends
        RecyclerView.Adapter<ViewHolder.InstructorProfileScreenViewHolder.InstructorCoursesViewHolder> {

    private Cursor cursor;
    private Context context;

    public RecycleViewInstructorProfileAdapter(Context context){
        this.context = context;
    }

    public void setCursor(Cursor cursor){
        this.cursor = cursor;
    }

    @Override
    public ViewHolder.InstructorProfileScreenViewHolder.InstructorCoursesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder.InstructorProfileScreenViewHolder.InstructorCoursesViewHolder(
                LayoutInflater.from(context).inflate(R.layout.instructor_pf_courses_children_recycle, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(ViewHolder.InstructorProfileScreenViewHolder.InstructorCoursesViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return (cursor == null)? 0: cursor.getCount();
    }
}
