package com.vclassrooms.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Rahul on 09,July,2020
 */


public class StudentAttendanceDetailResponse {

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

        @SerializedName("AttendanceDetail")
        @Expose
        private List<AttendanceDetail> attendanceDetail = null;

        public List<AttendanceDetail> getAttendanceDetail() {
            return attendanceDetail;
        }

        public void setAttendanceDetail(List<AttendanceDetail> attendanceDetail) {
            this.attendanceDetail = attendanceDetail;
        }

    }

}
