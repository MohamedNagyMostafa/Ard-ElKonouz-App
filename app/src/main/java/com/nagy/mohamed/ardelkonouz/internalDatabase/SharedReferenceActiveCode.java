package com.nagy.mohamed.ardelkonouz.internalDatabase;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by mohamednagy on 8/12/2017.
 */

public class SharedReferenceActiveCode {

    private SharedPreferences sharedPreferences = null;
    private static final String SHARED_PREFERENCE_NAME = "Active Code"; // create file with name ...
    private static final String CODE_NAME = "code";

    public SharedReferenceActiveCode(Context context){
        sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    public void setData(String activeCode){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(CODE_NAME, activeCode);
        editor.apply();
    }

    public String getData(){
        return sharedPreferences.getString(CODE_NAME, "123456789");
    }

}
