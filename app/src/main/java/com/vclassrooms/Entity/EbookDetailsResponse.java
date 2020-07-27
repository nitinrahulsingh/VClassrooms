package com.vclassrooms.Entity;

/**
 * Created by Rahul on 22,July,2020
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EbookDetailsResponse {

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
    public class EbookDetail {

        @SerializedName("vchFilePath")
        @Expose
        private String vchFilePath;

        public String getFileSize() {
            return FileSize;
        }

        public void setFileSize(String fileSize) {
            FileSize = fileSize;
        }

        @SerializedName("FileSize")
        @Expose
        private String FileSize;
        @SerializedName("Emp_Id")
        @Expose
        private Integer empId;
        @SerializedName("Title")
        @Expose
        private String title;
        @SerializedName("Discription")
        @Expose
        private String discription;
        @SerializedName("Academic_Id")
        @Expose
        private Integer academicId;
        @SerializedName("School_Id")
        @Expose
        private Integer schoolId;
        @SerializedName("InsertedBy")
        @Expose
        private Object insertedBy;
        @SerializedName("InsertedDate")
        @Expose
        private String insertedDate;
        @SerializedName("TeacherName")
        @Expose
        private String teacherName;

        public String getVchFilePath() {
            return vchFilePath;
        }

        public void setVchFilePath(String vchFilePath) {
            this.vchFilePath = vchFilePath;
        }

        public Integer getEmpId() {
            return empId;
        }

        public void setEmpId(Integer empId) {
            this.empId = empId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDiscription() {
            return discription;
        }

        public void setDiscription(String discription) {
            this.discription = discription;
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

        public Object getInsertedBy() {
            return insertedBy;
        }

        public void setInsertedBy(Object insertedBy) {
            this.insertedBy = insertedBy;
        }

        public String getInsertedDate() {
            return insertedDate;
        }

        public void setInsertedDate(String insertedDate) {
            this.insertedDate = insertedDate;
        }

        public String getTeacherName() {
            return teacherName;
        }

        public void setTeacherName(String teacherName) {
            this.teacherName = teacherName;
        }

    }
    public class Data {

        @SerializedName("EbookDetails")
        @Expose
        private List<EbookDetail> ebookDetails = null;

        public List<EbookDetail> getEbookDetails() {
            return ebookDetails;
        }

        public void setEbookDetails(List<EbookDetail> ebookDetails) {
            this.ebookDetails = ebookDetails;
        }

    }
}
