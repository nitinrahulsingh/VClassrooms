package com.vclassrooms.Entity;

/**
 * Created by Rahul on 30,June,2020
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TeacherDirectoryResponse {

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

    public class DirectoryeDetail {

        @SerializedName("Emp_Id")
        @Expose
        private Integer empId;
        @SerializedName("ImageURL")
        @Expose
        private String imageURL;
        @SerializedName("First_Name")
        @Expose
        private String firstName;
        @SerializedName("Email_Id")
        @Expose
        private String emailId;
        @SerializedName("Mobile_No")
        @Expose
        private String mobileNo;
        @SerializedName("Address")
        @Expose
        private Object address;
        @SerializedName("Standard_name")
        @Expose
        private String standardName;
        @SerializedName("Division_Name")
        @Expose
        private String divisionName;
        @SerializedName("SubjectName")
        @Expose
        private String subjectName;
        @SerializedName("Designation")
        @Expose
        private String designation;

        public Integer getEmpId() {
            return empId;
        }

        public void setEmpId(Integer empId) {
            this.empId = empId;
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

        public Object getAddress() {
            return address;
        }

        public void setAddress(Object address) {
            this.address = address;
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

        public String getSubjectName() {
            return subjectName;
        }

        public void setSubjectName(String subjectName) {
            this.subjectName = subjectName;
        }

        public String getDesignation() {
            return designation;
        }

        public void setDesignation(String designation) {
            this.designation = designation;
        }

    }
    public class Data {

        @SerializedName("DirectoryeDetail")
        @Expose
        private List<DirectoryeDetail> directoryeDetail = null;

        public List<DirectoryeDetail> getDirectoryeDetail() {
            return directoryeDetail;
        }

        public void setDirectoryeDetail(List<DirectoryeDetail> directoryeDetail) {
            this.directoryeDetail = directoryeDetail;
        }

    }
}
