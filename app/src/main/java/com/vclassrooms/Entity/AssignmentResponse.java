package com.vclassrooms.Entity;

import java.util.List;

/**
 * Created by Rahul on 12,July,2020
 */
public class AssignmentResponse {
    private int StatusCode;

    private String StatusMessaage;

    private Data data;

    public void setStatusCode(int StatusCode){
        this.StatusCode = StatusCode;
    }
    public int getStatusCode(){
        return this.StatusCode;
    }
    public void setStatusMessaage(String StatusMessaage){
        this.StatusMessaage = StatusMessaage;
    }
    public String getStatusMessaage(){
        return this.StatusMessaage;
    }
    public void setData(Data data){
        this.data = data;
    }
    public Data getData(){
        return this.data;
    }

    public class Assignment
    {
        private int Subject_Id;

        public int getAssignment_Id() {
            return Assignment_Id;
        }

        public void setAssignment_Id(int assignment_Id) {
            Assignment_Id = assignment_Id;
        }

        public String getInsertedDate() {
            return InsertedDate;
        }

        public void setInsertedDate(String insertedDate) {
            InsertedDate = insertedDate;
        }

        private int Assignment_Id;
        private String InsertedDate;

        private int Standard_Id;

        private int Division_Id;

        private String SubjectName;

        private String dtDate;

        private String Assignment_Name;

        private String Assignment_Details;

        private int Emp_Id;

        public void setSubject_Id(int Subject_Id){
            this.Subject_Id = Subject_Id;
        }
        public int getSubject_Id(){
            return this.Subject_Id;
        }
        public void setStandard_Id(int Standard_Id){
            this.Standard_Id = Standard_Id;
        }
        public int getStandard_Id(){
            return this.Standard_Id;
        }
        public void setDivision_Id(int Division_Id){
            this.Division_Id = Division_Id;
        }
        public int getDivision_Id(){
            return this.Division_Id;
        }
        public void setSubjectName(String SubjectName){
            this.SubjectName = SubjectName;
        }
        public String getSubjectName(){
            return this.SubjectName;
        }
        public void setAssignment_Name(String Assignment_Name){
            this.Assignment_Name = Assignment_Name;
        }
        public String getAssignment_Name(){
            return this.Assignment_Name;
        }
        public void setAssignment_Details(String Assignment_Details){
            this.Assignment_Details = Assignment_Details;
        }
        public String getAssignment_Details(){
            return this.Assignment_Details;
        }
        public void setEmp_Id(int Emp_Id){
            this.Emp_Id = Emp_Id;
        }
        public int getEmp_Id(){
            return this.Emp_Id;
        }

        public String getDtDate() {
            return dtDate;
        }

        public void setDtDate(String dtDate) {
            this.dtDate = dtDate;
        }
    }
    public class Data
    {
        private List<Assignment> Assignment;

        public void setAssignment(List<Assignment> Assignment){
            this.Assignment = Assignment;
        }
        public List<Assignment> getAssignment(){
            return this.Assignment;
        }
    }

}
