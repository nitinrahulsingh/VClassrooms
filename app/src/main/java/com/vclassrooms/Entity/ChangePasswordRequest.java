package com.vclassrooms.Entity;

/**
 * Created by Rahul on 03,July,2020
 */
public class ChangePasswordRequest {
    public int getRole_Id() {
        return Role_Id;
    }

    public void setRole_Id(int role_Id) {
        Role_Id = role_Id;
    }

    public int getUser_Id() {
        return User_Id;
    }

    public void setUser_Id(int user_Id) {
        User_Id = user_Id;
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

    public String getUser_Name() {
        return User_Name;
    }

    public void setUser_Name(String user_Name) {
        User_Name = user_Name;
    }

    public String getOld_Password() {
        return Old_Password;
    }

    public void setOld_Password(String old_Password) {
        Old_Password = old_Password;
    }

    public String getNew_Password() {
        return New_Password;
    }

    public void setNew_Password(String new_Password) {
        New_Password = new_Password;
    }

    public String getFCMToken() {
        return FCMToken;
    }

    public void setFCMToken(String FCMToken) {
        this.FCMToken = FCMToken;
    }

    private int Role_Id ;
    private int User_Id ;
    private int School_Id ;
    private int Academic_Id ;
    private String User_Name ;
    private String Old_Password ;
    private String New_Password ;
    private String FCMToken ;

}
