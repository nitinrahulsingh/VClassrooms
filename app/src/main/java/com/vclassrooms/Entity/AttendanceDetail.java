package com.vclassrooms.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Rahul on 12,July,2020
 */
public class AttendanceDetail {

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public Integer getRollNo() {
        return rollNo;
    }

    public void setRollNo(Integer rollNo) {
        this.rollNo = rollNo;
    }

    @SerializedName("ImageURL")
    @Expose
    private String imageURL;

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    @SerializedName("Date")
    @Expose
    private String Date;
    @SerializedName("Roll_No")
    @Expose
    private Integer rollNo;
    @SerializedName("User_Id")
    @Expose
    private Integer userId;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Standard_Id")
    @Expose
    private Integer standardId;
    @SerializedName("Division_Id")
    @Expose
    private Integer divisionId;
    @SerializedName("Gender")
    @Expose
    private Object gender;
    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("LoginTime")
    @Expose
    private String loginTime;
    @SerializedName("LogOutTime")
    @Expose
    private String logOutTime;

    private boolean IsPresent;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStandardId() {
        return standardId;
    }

    public void setStandardId(Integer standardId) {
        this.standardId = standardId;
    }

    public Integer getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(Integer divisionId) {
        this.divisionId = divisionId;
    }

    public Object getGender() {
        return gender;
    }

    public void setGender(Object gender) {
        this.gender = gender;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    public String getLogOutTime() {
        return logOutTime;
    }

    public void setLogOutTime(String logOutTime) {
        this.logOutTime = logOutTime;
    }
    public boolean isPresent() {
        return IsPresent;
    }

    public void setPresent(boolean present) {
        IsPresent = present;
    }
}
