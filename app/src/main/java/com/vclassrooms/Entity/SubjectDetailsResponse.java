package com.vclassrooms.Entity;

/**
 * Created by Rahul on 23,July,2020
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SubjectDetailsResponse {

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
    public class Subject {

        @SerializedName("Subject_Id")
        @Expose
        private Integer subjectId;
        @SerializedName("Standard_Id")
        @Expose
        private Integer standardId;
        @SerializedName("Division_Id")
        @Expose
        private Integer divisionId;
        @SerializedName("SubjectName")
        @Expose
        private String subjectName;

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

        @Override
        public String toString() {
            return subjectName;
        }
    }
    public class Data {

        @SerializedName("Subject")
        @Expose
        private List<Subject> subject = null;

        public List<Subject> getSubject() {
            return subject;
        }

        public void setSubject(List<Subject> subject) {
            this.subject = subject;
        }

    }
}
