package com.vclassrooms.Entity;

/**
 * Created by Rahul on 08,July,2020
 */
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class StudentListResponse {

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
    public class StudentList {

        @SerializedName("Student_Id")
        @Expose
        private Integer studentId;
        @SerializedName("ImageURL")
        @Expose
        private String imageURL;
        @SerializedName("First_Name")
        @Expose
        private String firstName;
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
    public class Data {

        @SerializedName("StudentList")
        @Expose
        private List<StudentList> studentList = null;

        public List<StudentList> getStudentList() {
            return studentList;
        }

        public void setStudentList(List<StudentList> studentList) {
            this.studentList = studentList;
        }

    }
}
