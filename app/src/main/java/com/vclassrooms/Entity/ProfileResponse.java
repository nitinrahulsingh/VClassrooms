package com.vclassrooms.Entity;

/**
 * Created by Rahul on 29,June,2020
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProfileResponse {

    @SerializedName("StatusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("StatusMessaage")
    @Expose
    private String statusMessaage;
    @SerializedName("data")
    @Expose
    private Data data;

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusMessaage() {
        return statusMessaage;
    }

    public void setStatusMessaage(String statusMessaage) {
        this.statusMessaage = statusMessaage;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
    public class Data {

        @SerializedName("ProfileDetails")
        @Expose
        private List<ProfileDetail> profileDetails = null;

        public List<ProfileDetail> getProfileDetails() {
            return profileDetails;
        }

        public void setProfileDetails(List<ProfileDetail> profileDetails) {
            this.profileDetails = profileDetails;
        }

    }
    public class ProfileDetail {

        @SerializedName("Student_Id")
        @Expose
        private Integer studentId;
        @SerializedName("ImageURL")
        @Expose
        private String imageURL;

        public String getDesignation() {
            return Designation;
        }

        public void setDesignation(String designation) {
            Designation = designation;
        }

        @SerializedName("Designation")
        @Expose
        private String Designation;
        @SerializedName("First_Name")
        @Expose
        private String firstName;
        @SerializedName("Father_Name")
        @Expose
        private String fatherName;
        @SerializedName("Mother_Name")
        @Expose
        private String motherName;
        @SerializedName("Email_Id")
        @Expose
        private String emailId;
        @SerializedName("Mobile_No")
        @Expose
        private String mobileNo;
        @SerializedName("Address")
        @Expose
        private String address;
        @SerializedName("Roll_No")
        @Expose
        private Integer rollNo;
        @SerializedName("Standard_name")
        @Expose
        private String standardName;
        @SerializedName("Division_Name")
        @Expose
        private String divisionName;

        public Integer getStudentId() {
            return studentId;
        }

        public void setStudentId(Integer studentId) {
            this.studentId = studentId;
        }

        public String getImageURL() {
            return imageURL;
        }

        public void setImageURL(String imageURL) {
            this.imageURL = imageURL;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getFatherName() {
            return fatherName;
        }

        public void setFatherName(String fatherName) {
            this.fatherName = fatherName;
        }

        public String getMotherName() {
            return motherName;
        }

        public void setMotherName(String motherName) {
            this.motherName = motherName;
        }

        public String getEmailId() {
            return emailId;
        }

        public void setEmailId(String emailId) {
            this.emailId = emailId;
        }

        public String getMobileNo() {
            return mobileNo;
        }

        public void setMobileNo(String mobileNo) {
            this.mobileNo = mobileNo;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public Integer getRollNo() {
            return rollNo;
        }

        public void setRollNo(Integer rollNo) {
            this.rollNo = rollNo;
        }

        public String getStandardName() {
            return standardName;
        }

        public void setStandardName(String standardName) {
            this.standardName = standardName;
        }

        public String getDivisionName() {
            return divisionName;
        }

        public void setDivisionName(String divisionName) {
            this.divisionName = divisionName;
        }

    }
}
