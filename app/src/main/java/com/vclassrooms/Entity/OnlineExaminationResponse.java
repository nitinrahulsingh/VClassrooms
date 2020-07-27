package com.vclassrooms.Entity;

/**
 * Created by Rahul on 25,July,2020
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OnlineExaminationResponse {

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
    public class OnlineExamination {

        @SerializedName("Subject_Id")
        @Expose
        private Integer subjectId;
        @SerializedName("Examination_Id")
        @Expose
        private Integer examinationId;
        @SerializedName("Standard_Id")
        @Expose
        private Integer standardId;
        @SerializedName("Division_Id")
        @Expose
        private Integer divisionId;
        @SerializedName("SubjectName")
        @Expose
        private String subjectName;
        @SerializedName("Title")
        @Expose
        private String title;
        @SerializedName("Question_Answer")
        @Expose
        private String questionAnswer;
        @SerializedName("Total_Mark")
        @Expose
        private Integer totalMark;
        @SerializedName("Passing_Mark")
        @Expose
        private Integer passingMark;
        @SerializedName("Total_Time")
        @Expose
        private String totalTime;
        @SerializedName("Emp_Id")
        @Expose
        private Integer empId;
        @SerializedName("First_Name")
        @Expose
        private String firstName;

        public Integer getSubjectId() {
            return subjectId;
        }

        public void setSubjectId(Integer subjectId) {
            this.subjectId = subjectId;
        }

        public Integer getExaminationId() {
            return examinationId;
        }

        public void setExaminationId(Integer examinationId) {
            this.examinationId = examinationId;
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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getQuestionAnswer() {
            return questionAnswer;
        }

        public void setQuestionAnswer(String questionAnswer) {
            this.questionAnswer = questionAnswer;
        }

        public Integer getTotalMark() {
            return totalMark;
        }

        public void setTotalMark(Integer totalMark) {
            this.totalMark = totalMark;
        }

        public Integer getPassingMark() {
            return passingMark;
        }

        public void setPassingMark(Integer passingMark) {
            this.passingMark = passingMark;
        }

        public String getTotalTime() {
            return totalTime;
        }

        public void setTotalTime(String totalTime) {
            this.totalTime = totalTime;
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

    }
    public class Data {

        @SerializedName("OnlineExamination")
        @Expose
        private List<OnlineExamination> onlineExamination = null;

        public List<OnlineExamination> getOnlineExamination() {
            return onlineExamination;
        }

        public void setOnlineExamination(List<OnlineExamination> onlineExamination) {
            this.onlineExamination = onlineExamination;
        }

    }
}
