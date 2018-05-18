package com.camsoulsby.oldmate;

import android.provider.BaseColumns;

import java.sql.Date;

public class FeedReaderContract {

    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private FeedReaderContract() {}

    // Add additional fields here
    /* Inner class that defines the table contents */
    public static class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "people";
        public static final String COLUMN_NAME_ENTRY_NAME = "entryName";
        public static final String COLUMN_NAME_DATE_MET = "metDate";
        //TODO: Work out how to use location and how this should be stored
        //TODO: Any other variables?
        public static final String COLUMN_NAME_NOTES = "notes";

    }

}
