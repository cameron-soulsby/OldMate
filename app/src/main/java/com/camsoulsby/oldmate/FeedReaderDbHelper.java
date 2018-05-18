package com.camsoulsby.oldmate;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class FeedReaderDbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "FeedReader.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedReaderContract.FeedEntry.TABLE_NAME + " (" +
                    FeedReaderContract.FeedEntry._ID + " INTEGER PRIMARY KEY," +
                    FeedReaderContract.FeedEntry.COLUMN_NAME_ENTRY_NAME + " TEXT," +
                    FeedReaderContract.FeedEntry.COLUMN_NAME_DATE_MET + " TEXT,"+
                    FeedReaderContract.FeedEntry.COLUMN_NAME_NOTES + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedReaderContract.FeedEntry.TABLE_NAME;

    public FeedReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
    public List<Mate> listMates() {
        String sql = "select * from " + FeedReaderContract.FeedEntry.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        List<Mate> allMates = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {

                String name = cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedEntry.COLUMN_NAME_ENTRY_NAME));
                String notes = cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedEntry.COLUMN_NAME_NOTES));
                allMates.add(new Mate(name, notes));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return allMates;
    }

    /**
     *  public void addProduct(Product product){
     ContentValues values = new ContentValues();
     values.put(COLUMN_PRODUCTNAME, product.getName());
     values.put(COLUMN_QUANTITY, product.getQuantity());
     SQLiteDatabase db = this.getWritableDatabase();
     db.insert(TABLE_PRODUCTS, null, values);
     }
     public void updateProduct(Product product){
     ContentValues values = new ContentValues();
     values.put(COLUMN_PRODUCTNAME, product.getName());
     values.put(COLUMN_QUANTITY, product.getQuantity());
     SQLiteDatabase db = this.getWritableDatabase();
     db.update(TABLE_PRODUCTS, values, COLUMN_ID    + "    = ?", new String[] { String.valueOf(product.getId())});
     }
     public Product findProduct(String name){
     String query = "Select * FROM "    + TABLE_PRODUCTS + " WHERE " + COLUMN_PRODUCTNAME + " = " + "name";
     SQLiteDatabase db = this.getWritableDatabase();
     Product mProduct = null;
     Cursor cursor = db.rawQuery(query, null);
     if (cursor.moveToFirst()){
     int id = Integer.parseInt(cursor.getString(0));
     String productName = cursor.getString(1);
     int productQuantity = Integer.parseInt(cursor.getString(2));
     mProduct = new Product(id, productName, productQuantity);
     }
     cursor.close();
     return mProduct;
     }
     public void deleteProduct(int id){
     SQLiteDatabase db = this.getWritableDatabase();
     db.delete(TABLE_PRODUCTS, COLUMN_ID    + "    = ?", new String[] { String.valueOf(id)});
     }
     */
}