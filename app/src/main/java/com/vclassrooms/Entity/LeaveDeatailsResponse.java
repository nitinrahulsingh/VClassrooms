package com.vclassrooms.Entity;

/**
 * Created by Rahul on 18,July,2020
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LeaveDeatailsResponse {

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

        @SerializedName("LeaveDetail")
        @Expose
        private List<LeaveDetail> leaveDetail = null;

        public List<LeaveDetail> getLeaveDetail() {
            return leaveDetail;
        }

        public void setLeaveDetail(List<LeaveDetail> leaveDetail) {
            this.leaveDetail = leaveDetail;
        }

    }
    public class LeaveDetail {
        private  boolean isExpand=false;
        public boolean isExpand() {
            return isExpand;
        }

        public void setExpand(boolean expand) {
            isExpand = expand;
        }
        @SerializedName("Leave_Id")
        @Expose
        private Integer leaveId;
        @SerializedName("Role_Id")
        @Expose
        private Integer roleId;
        @SerializedName("Emp_Id")
        @Expose
        private Integer empId;
        @SerializedName("LeaveType_Name")
        @Expose
        private String leaveTypeName;
        @SerializedName("Leave_Name")
        @Expose
        private String leaveName;
        @SerializedName("Leave_Details")
        @Expose
        private String leaveDetails;
        @SerializedName("FromDay")
        @Expose
        private String fromDay;
        @SerializedName("FromDate")
        @Expose
        private String fromDate;
        @SerializedName("ToDay")
        @Expose
        private String toDay;
        @SerializedName("ToDate")
        @Expose
        private String toDate;
        @SerializedName("NoOfDays")
        @Expose
        private Integer noOfDays;
        @SerializedName("Academic_Id")
        @Expose
        private Integer academicId;
        @SerializedName("School_Id")
        @Expose
        private Integer schoolId;

        public Integer getLeaveId() {
            return leaveId;
        }

        public void setLeaveId(Integer leaveId) {
            this.leaveId = leaveId;
        }

        public Integer getRoleId() {
            return roleId;
        }

        public void setRoleId(Integer roleId) {
            this.roleId = roleId;
        }

        public Integer getEmpId() {
            return empId;
        }

        public void setEmpId(Integer empId) {
            this.empId = empId;
        }

        public String getLeaveTypeName() {
            return leaveTypeName;
        }

        public void setLeaveTypeName(String leaveTypeName) {
            this.leaveTypeName = leaveTypeName;
        }

        public String getLeaveName() {
            return leaveName;
        }

        public void setLeaveName(String leaveName) {
            this.leaveName = leaveName;
        }

        public String getLeaveDetails() {
            return leaveDetails;
        }

        public void setLeaveDetails(String leaveDetails) {
            this.leaveDetails = leaveDetails;
        }

        public String getFromDay() {
            return fromDay;
        }

        public void setFromDay(String fromDay) {
            this.fromDay = fromDay;
        }

        public String getFromDate() {
            return fromDate;
        }

        public void setFromDate(String fromDate) {
            this.fromDate = fromDate;
        }

        public String getToDay() {
            return toDay;
        }

        public void setToDay(String toDay) {
            this.toDay = toDay;
        }

        public String getToDate() {
            return toDate;
        }

        public void setToDate(String toDate) {
            this.toDate = toDate;
        }

        public Integer getNoOfDays() {
            return noOfDays;
        }

        public void setNoOfDays(Integer noOfDays) {
            this.noOfDays = noOfDays;
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

    }
}
