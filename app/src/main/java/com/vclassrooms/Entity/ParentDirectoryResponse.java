package com.vclassrooms.Entity;

/**
 * Created by Rahul on 01,July,2020
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ParentDirectoryResponse {

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

        private  boolean isExpand=false;
        public boolean isExpand() {
            return isExpand;
        }

        public void setExpand(boolean expand) {
            isExpand = expand;
        }
        @SerializedName("Father_Name")
        @Expose
        private String fatherName;
        @SerializedName("Mother_Name")
        @Expose
        private String motherName;
        @SerializedName("Mobile_No")
        @Expose
        private String mobileNo;
        @SerializedName("DirectoryeDetail")
        @Expose
        private List<StudentDetails> directoryeDetail = null;

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

        public String getMobileNo() {
            return mobileNo;
        }

        public void setMobileNo(String mobileNo) {
            this.mobileNo = mobileNo;
        }

        public List<StudentDetails> getDirectoryeDetail() {
            return directoryeDetail;
        }

        public void setDirectoryeDetail(List<StudentDetails> directoryeDetail) {
            this.directoryeDetail = directoryeDetail;
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
