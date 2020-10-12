package com.vclassrooms.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rahul on 17,August,2020
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 4;

    // Database Name
    private static final String DATABASE_NAME = "notes_db";
    private Context mContext;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        // create notes table
        db.execSQL(NotificationDetails.CREATE_TABLE);

    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + NotificationDetails.TABLE_NAME);
        // Create tables again
        onCreate(db);
    }
    public long insertNotification(NotificationDetails notificationDetails) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(NotificationDetails.COLUMN_Tittle, notificationDetails.getTittle());
        values.put(NotificationDetails.COLUMN_Details, notificationDetails.getDetails());
        values.put(NotificationDetails.COLUMN_Date, notificationDetails.getDate());
        values.put(NotificationDetails.COLUMN_Flag, notificationDetails.getFlag());
        values.put(NotificationDetails.COLUMN_ISRead, notificationDetails.getIsread());

        values.putAll(values);

        long  id = db.insert(NotificationDetails.TABLE_NAME, null, values);
        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public boolean updateNotification(String isRead,String strFlag) {
        boolean status = false;
        SQLiteDatabase db = this.getWritableDatabase();
  
        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(NotificationDetails.COLUMN_ISRead, isRead);
        values.putAll(values);

        // insert row
       status = db.update(NotificationDetails.TABLE_NAME, values, NotificationDetails.COLUMN_Flag + "=" + strFlag, null) > 0;

        // close db connection
        db.close();
        return status;

    }
    public List<NotificationDetails> getNotification(String strFlag) {
        List<NotificationDetails> contacts = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + NotificationDetails.TABLE_NAME + " ORDER BY " +
                NotificationDetails.COLUMN_ID + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                NotificationDetails notificationDetails = new NotificationDetails();
                notificationDetails.setDate(cursor.getString(cursor.getColumnIndex(NotificationDetails.COLUMN_Date)));
                notificationDetails.setTittle(cursor.getString(cursor.getColumnIndex(NotificationDetails.COLUMN_Tittle)));
                notificationDetails.setDetails(cursor.getString(cursor.getColumnIndex(NotificationDetails.COLUMN_Details)));
                notificationDetails.setIsread(cursor.getString(cursor.getColumnIndex(NotificationDetails.COLUMN_ISRead)));
                notificationDetails.setFlag(cursor.getString(cursor.getColumnIndex(NotificationDetails.COLUMN_Flag)));
                if(strFlag.contentEquals(cursor.getString(cursor.getColumnIndex(NotificationDetails.COLUMN_Flag)))){
                    contacts.add(notificationDetails);
                }

            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return contacts;
    }

    public int getUnreadCount(String strFlag) {
        String countQuery = "SELECT  * FROM " + NotificationDetails.TABLE_NAME +" Where "+ NotificationDetails.COLUMN_Flag+ " = " + strFlag;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

}
