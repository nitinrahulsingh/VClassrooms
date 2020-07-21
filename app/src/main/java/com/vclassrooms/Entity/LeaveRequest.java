package com.vclassrooms.Entity;

/**
 * Created by Rahul on 19,July,2020
 */
public class LeaveRequest {
    public int getRole_Id() {
        return Role_Id;
    }

    public void setRole_Id(int role_Id) {
        Role_Id = role_Id;
    }

    public int getEmp_Id() {
        return Emp_Id;
    }

    public void setEmp_Id(int emp_Id) {
        Emp_Id = emp_Id;
    }

    public int getLeaveType_Id() {
        return LeaveType_Id;
    }

    public void setLeaveType_Id(int leaveType_Id) {
        LeaveType_Id = leaveType_Id;
    }

    public String getLeave_Name() {
        return Leave_Name;
    }

    public void setLeave_Name(String leave_Name) {
        Leave_Name = leave_Name;
    }

    public String getLeave_Details() {
        return Leave_Details;
    }

    public void setLeave_Details(String leave_Details) {
        Leave_Details = leave_Details;
    }

    public String getFromDate() {
        return FromDate;
    }

    public void setFromDate(String fromDate) {
        FromDate = fromDate;
    }

    public String getToDate() {
        return ToDate;
    }

    public void setToDate(String toDate) {
        ToDate = toDate;
    }

    public int getNoOfDays() {
        return NoOfDays;
    }

    public void setNoOfDays(int noOfDays) {
        NoOfDays = noOfDays;
    }

    public int getAcademic_Id() {
        return Academic_Id;
    }

    public void setAcademic_Id(int academic_Id) {
        Academic_Id = academic_Id;
    }

    public int getSchool_Id() {
        return School_Id;
    }

    public void setSchool_Id(int school_Id) {
        School_Id = school_Id;
    }

    public int getInsertedBy() {
        return InsertedBy;
    }

    public void setInsertedBy(int insertedBy) {
        InsertedBy = insertedBy;
    }


    public int Role_Id ;
    public int Emp_Id ;
    public int LeaveType_Id ;
    public String Leave_Name ;
    public String Leave_Details ;
    public String FromDate ;
    public String ToDate ;
    public int NoOfDays ;
    public int Academic_Id ;
    public int School_Id ;
    public int InsertedBy ;

    //InsertLeave
}
