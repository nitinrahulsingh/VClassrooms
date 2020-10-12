package com.vclassrooms.Database;

import java.io.Serializable;

/**
 * Created by rahul singh on 15/05/18.
 */

public class NotificationDetails implements Serializable {



    public NotificationDetails() {

    }

    public static final String TABLE_NAME = "notification_details";


    public static final String COLUMN_ID = "id";
    public static final String COLUMN_Tittle = "tittle";
    public static final String COLUMN_Details = "details";
    public static final String COLUMN_Flag = "flag";
    public static final String COLUMN_Date = "date";
    public static final String COLUMN_ISRead = "isread";

    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_Tittle + " TEXT," + COLUMN_Details + " TEXT,"
                    + COLUMN_Flag + " TEXT," + COLUMN_Date + " TEXT,"+ COLUMN_ISRead + " TEXT"
                    + ")";// Create table SQL query
    //Create table GROUP SQL query


    //isread  (read=1) (unread=0)
    private int id;
    private String tittle;
    private String details;
    private String flag;

    public String getIsread() {
        return isread;
    }

    public void setIsread(String isread) {
        this.isread = isread;
    }

    private String isread;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    private String date;

}
