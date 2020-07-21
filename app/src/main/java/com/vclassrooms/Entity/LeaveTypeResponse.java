package com.vclassrooms.Entity;

/**
 * Created by Rahul on 19,July,2020
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LeaveTypeResponse {

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
    public class LeaveTypeDetail {

        @SerializedName("LeaveType_Id")
        @Expose
        private Integer leaveTypeId;
        @SerializedName("LeaveType_Name")
        @Expose
        private String leaveTypeName;
        @SerializedName("School_Id")
        @Expose
        private Integer schoolId;
        @SerializedName("ActiveFlg")
        @Expose
        private String activeFlg;

        public Integer getLeaveTypeId() {
            return leaveTypeId;
        }

        public void setLeaveTypeId(Integer leaveTypeId) {
            this.leaveTypeId = leaveTypeId;
        }

        public String getLeaveTypeName() {
            return leaveTypeName;
        }

        public void setLeaveTypeName(String leaveTypeName) {
            this.leaveTypeName = leaveTypeName;
        }

        public Integer getSchoolId() {
            return schoolId;
        }

        public void setSchoolId(Integer schoolId) {
            this.schoolId = schoolId;
        }

        public String getActiveFlg() {
            return activeFlg;
        }

        public void setActiveFlg(String activeFlg) {
            this.activeFlg = activeFlg;
        }

        @Override
        public String toString() {
            return leaveTypeName ;
        }
    }
    public class Data {

        @SerializedName("LeaveTypeDetails")
        @Expose
        private List<LeaveTypeDetail> leaveTypeDetails = null;

        public List<LeaveTypeDetail> getLeaveTypeDetails() {
            return leaveTypeDetails;
        }

        public void setLeaveTypeDetails(List<LeaveTypeDetail> leaveTypeDetails) {
            this.leaveTypeDetails = leaveTypeDetails;
        }

    }
}
