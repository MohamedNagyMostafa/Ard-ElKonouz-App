package com.nagy.mohamed.ardelkonouz;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nagy.mohamed.ardelkonouz.offlineDatabase.ContentProviderDatabase;
import com.nagy.mohamed.ardelkonouz.offlineDatabase.DbHelper;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private ContentProviderDatabase  contentProviderDatabase;

    private DbHelper dbHelper;
    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dbHelper = new DbHelper(getContext());
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        return inflater.inflate(R.layout.fragment_main, container, false);
    }
}
