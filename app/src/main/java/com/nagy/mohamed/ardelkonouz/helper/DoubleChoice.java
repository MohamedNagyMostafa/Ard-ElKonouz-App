package com.nagy.mohamed.ardelkonouz.helper;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nagy.mohamed.ardelkonouz.R;

/**
 * Created by mohamednagy on 6/21/2017.
 */
public class DoubleChoice {
    TextView textView;
    ImageView imageView;
    Context context;
    boolean isSelect;

    public DoubleChoice(TextView textView, ImageView imageView, Context context){
        this.textView = textView;
        this.imageView = imageView;
        this.context = context;
        isSelect = false;
    }

    public DoubleChoice(TextView textView, ImageView imageView, Context context, boolean isSelect){
        this.textView = textView;
        this.imageView = imageView;
        this.isSelect = isSelect;
        this.context = context;
    }

    public void setChoiceNotSelect(){
        imageView.setVisibility(View.INVISIBLE);
        textView.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
    }

    public void setChoiceSelect(){
        imageView.setVisibility(View.VISIBLE);
        textView.setTextColor(Color.BLACK);
    }

}
