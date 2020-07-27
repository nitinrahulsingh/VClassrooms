package com.vclassrooms.Entity;

/**
 * Created by Rahul on 25,July,2020
 */
public class AnnouncementEnum {
    public int getAnnouncement_Id() {
        return Announcement_Id;
    }

    public void setAnnouncement_Id(int announcement_Id) {
        Announcement_Id = announcement_Id;
    }

    public String getAnnouncement_Name() {
        return Announcement_Name;
    }

    public void setAnnouncement_Name(String announcement_Name) {
        Announcement_Name = announcement_Name;
    }

    public String getAnnouncement_Details() {
        return Announcement_Details;
    }

    public void setAnnouncement_Details(String announcement_Details) {
        Announcement_Details = announcement_Details;
    }

    public String getFromDate() {
        return FromDate;
    }

    public void setFromDate(String fromDate) {
        FromDate = fromDate;
    }

    public String getToDate() {
        return ToDate;
    }

    public void setToDate(String toDate) {
        ToDate = toDate;
    }

    public int getNoOfDays() {
        return NoOfDays;
    }

    public void setNoOfDays(int noOfDays) {
        NoOfDays = noOfDays;
    }

    public int getSchool_Id() {
        return School_Id;
    }

    public void setSchool_Id(int school_Id) {
        School_Id = school_Id;
    }

    public int getAcademic_Id() {
        return Academic_Id;
    }

    public void setAcademic_Id(int academic_Id) {
        Academic_Id = academic_Id;
    }

    public int getInsertedby() {
        return Insertedby;
    }

    public void setInsertedby(int insertedby) {
        Insertedby = insertedby;
    }

    public int Announcement_Id ;
    public String Announcement_Name ;
    public String Announcement_Details ;
    public String FromDate ;
    public String ToDate ;
    public int NoOfDays ;
    public int School_Id ;
    public int Academic_Id ;
    public int Insertedby ;
}
