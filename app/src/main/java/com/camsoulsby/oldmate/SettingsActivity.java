package com.camsoulsby.oldmate;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SettingsActivity extends AppCompatActivity {

    SQLiteDatabase db;
    FeedReaderDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mDbHelper = new FeedReaderDbHelper(this);

        db = mDbHelper.getReadableDatabase();
    }

    public void clearDataBase (View view){

// Issue SQL statement.
        db.delete(FeedReaderContract.FeedEntry.TABLE_NAME, null, null);
    }

}
