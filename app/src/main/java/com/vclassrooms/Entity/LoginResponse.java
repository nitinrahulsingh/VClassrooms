package com.vclassrooms.Entity;

/**
 * Created by Rahul on 25,June,2020
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LoginResponse {

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
    public class LoginDetail {

        @SerializedName("LoginBy")
        @Expose
        private Integer loginBy;
        @SerializedName("UserType_Id")
        @Expose
        private Integer userTypeId;
        @SerializedName("User_Id")
        @Expose
        private Integer userId;
        @SerializedName("User_Name")
        @Expose
        private String userName;
        @SerializedName("Academic_Id")
        @Expose
        private Integer academicId;
        @SerializedName("School_Id")
        @Expose
        private Integer schoolId;
        @SerializedName("Student_Id")
        @Expose
        private Integer studentId;
        @SerializedName("Standard_Id")
        @Expose
        private Integer standardId;
        @SerializedName("Division_Id")
        @Expose
        private Integer divisionId;
        @SerializedName("Standard_name")
        @Expose
        private String standardName;
        @SerializedName("Division_Name")
        @Expose
        private String divisionName;
        @SerializedName("ImageURL")
        @Expose
        private Object imageURL;
        @SerializedName("Child_Name")
        @Expose
        private String childName;

        public Integer getLoginBy() {
            return loginBy;
        }

        public void setLoginBy(Integer loginBy) {
            this.loginBy = loginBy;
        }

        public Integer getUserTypeId() {
            return userTypeId;
        }

        public void setUserTypeId(Integer userTypeId) {
            this.userTypeId = userTypeId;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public Integer getAcademicId() {
            return academicId;
        }

        public void setAcademicId(Integer academicId) {
            this.academicId = academicId;
        }

        public Integer getSchoolId() {
            return schoolId;
        }

        public void setSchoolId(Integer schoolId) {
            this.schoolId = schoolId;
        }

        public Integer getStudentId() {
            return studentId;
        }

        public void setStudentId(Integer studentId) {
            this.studentId = studentId;
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

        public Object getImageURL() {
            return imageURL;
        }

        public void setImageURL(Object imageURL) {
            this.imageURL = imageURL;
        }

        public String getChildName() {
            return childName;
        }

        public void setChildName(String childName) {
            this.childName = childName;
        }

    }
    public class Data {

        @SerializedName("LoginDetails")
        @Expose
        private List<LoginDetail> loginDetails = null;

        public List<LoginDetail> getLoginDetails() {
            return loginDetails;
        }

        public void setLoginDetails(List<LoginDetail> loginDetails) {
            this.loginDetails = loginDetails;
        }

    }
}
