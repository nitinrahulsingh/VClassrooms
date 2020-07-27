package com.vclassrooms.Entity;

/**
 * Created by Rahul on 23,July,2020
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OnlineLectureDetailsResponse {

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

        @SerializedName("OnlineLecture")
        @Expose
        private List<OnlineLecture> onlineLecture = null;

        public List<OnlineLecture> getOnlineLecture() {
            return onlineLecture;
        }

        public void setOnlineLecture(List<OnlineLecture> onlineLecture) {
            this.onlineLecture = onlineLecture;
        }

    }
    public class OnlineLecture {

        @SerializedName("Subject_Id")
        @Expose
        private Integer subjectId;
        @SerializedName("InsertedDate")
        @Expose
        private String InsertedDate;
        @SerializedName("Standard_Id")
        @Expose
        private Integer standardId;
        @SerializedName("Division_Id")
        @Expose
        private Integer divisionId;
        @SerializedName("SubjectName")
        @Expose
        private String subjectName;
        @SerializedName("Lesson_Name")
        @Expose
        private String lessonName;
        @SerializedName("Lesson_Details")
        @Expose
        private String lessonDetails;
        @SerializedName("Emp_Id")
        @Expose
        private Integer empId;
        @SerializedName("First_Name")
        @Expose
        private String firstName;
        @SerializedName("FilePath")
        @Expose
        private String filePath;

        public Integer getSubjectId() {
            return subjectId;
        }

        public void setSubjectId(Integer subjectId) {
            this.subjectId = subjectId;
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

        public String getSubjectName() {
            return subjectName;
        }

        public void setSubjectName(String subjectName) {
            this.subjectName = subjectName;
        }

        public String getLessonName() {
            return lessonName;
        }

        public void setLessonName(String lessonName) {
            this.lessonName = lessonName;
        }

        public String getLessonDetails() {
            return lessonDetails;
        }

        public void setLessonDetails(String lessonDetails) {
            this.lessonDetails = lessonDetails;
        }

        public Integer getEmpId() {
            return empId;
        }

        public void setEmpId(Integer empId) {
            this.empId = empId;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getFilePath() {
            return filePath;
        }

        public void setFilePath(String filePath) {
            this.filePath = filePath;
        }

        public String getInsertedDate() {
            return InsertedDate;
        }

        public void setInsertedDate(String insertedDate) {
            InsertedDate = insertedDate;
        }
    }
}
