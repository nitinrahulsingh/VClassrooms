package com.vclassrooms.Entity;

/**
 * Created by Rahul on 19,July,2020
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApplyLeaveResponse {

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

        @SerializedName("LeaveDeatils")
        @Expose
        private List<LeaveDeatil> leaveDeatils = null;

        public List<LeaveDeatil> getLeaveDeatils() {
            return leaveDeatils;
        }

        public void setLeaveDeatils(List<LeaveDeatil> leaveDeatils) {
            this.leaveDeatils = leaveDeatils;
        }

    }
    public class LeaveDeatil {

        @SerializedName("Column1")
        @Expose
        private String column1;

        public String getColumn1() {
            return column1;
        }

        public void setColumn1(String column1) {
            this.column1 = column1;
        }

    }
}
