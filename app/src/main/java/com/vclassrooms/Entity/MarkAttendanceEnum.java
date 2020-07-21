package com.vclassrooms.Entity;

/**
 * Created by Rahul on 19,July,2020
 */
public class MarkAttendanceEnum {
    public int getIntSchool_id() {
        return intSchool_id;
    }

    public void setIntSchool_id(int intSchool_id) {
        this.intSchool_id = intSchool_id;
    }


    public int getStandard_id() {
        return Standard_id;
    }

    public void setStandard_id(int standard_id) {
        Standard_id = standard_id;
    }

    public int getIntDivision_id() {
        return intDivision_id;
    }

    public void setIntDivision_id(int intDivision_id) {
        this.intDivision_id = intDivision_id;
    }

    public int getIntAcademic_id() {
        return intAcademic_id;
    }

    public void setIntAcademic_id(int intAcademic_id) {
        this.intAcademic_id = intAcademic_id;
    }

    public String getDtDate() {
        return dtDate;
    }

    public void setDtDate(String dtDate) {
        this.dtDate = dtDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFCMToken() {
        return FCMToken;
    }

    public void setFCMToken(String FCMToken) {
        this.FCMToken = FCMToken;
    }

    public String getAttendance_Source() {
        return Attendance_Source;
    }

    public void setAttendance_Source(String attendance_Source) {
        Attendance_Source = attendance_Source;
    }

    public String getLoginTime() {
        return LoginTime;
    }

    public void setLoginTime(String loginTime) {
        LoginTime = loginTime;
    }

    public String getRole_Id() {
        return Role_Id;
    }

    public void setRole_Id(String role_Id) {
        Role_Id = role_Id;
    }

    public int intSchool_id ;
    public int Standard_id ;
    public int intDivision_id ;
    public int intAcademic_id ;
    public String dtDate ;
    public String status ;
    public int userId ;

    public String FCMToken ;

    public String Attendance_Source ;

    public String LoginTime ;

    public String Role_Id ;
}
