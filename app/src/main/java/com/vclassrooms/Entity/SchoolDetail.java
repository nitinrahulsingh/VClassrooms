package com.vclassrooms.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Rahul on 26,June,2020
 */
public class SchoolDetail {

    @SerializedName("School_Id")
    @Expose
    private Integer schoolId;
    @SerializedName("School_Name")
    @Expose
    private String schoolName;
    @SerializedName("State")
    @Expose
    private String state;
    @SerializedName("City")
    @Expose
    private String city;
    @SerializedName("Address")
    @Expose
    private String address;
    @SerializedName("ContactPerson1")
    @Expose
    private Object contactPerson1;
    @SerializedName("ContactNo1")
    @Expose
    private Object contactNo1;

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Object getContactPerson1() {
        return contactPerson1;
    }

    public void setContactPerson1(Object contactPerson1) {
        this.contactPerson1 = contactPerson1;
    }

    public Object getContactNo1() {
        return contactNo1;
    }

    public void setContactNo1(Object contactNo1) {
        this.contactNo1 = contactNo1;
    }

    @Override
    public String toString() {
        return schoolName;
    }
}
