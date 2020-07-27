package com.vclassrooms.Entity;

/**
 * Created by Rahul on 25,July,2020
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AddOnlineLecResponse {

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

        @SerializedName("OnlineLecture")
        @Expose
        private List<OnlineLecture> onlineLecture = null;

        public List<OnlineLecture> getOnlineLecture() {
            return onlineLecture;
        }

        public void setOnlineLecture(List<OnlineLecture> onlineLecture) {
            this.onlineLecture = onlineLecture;
        }

    }
    public class OnlineLecture {

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
