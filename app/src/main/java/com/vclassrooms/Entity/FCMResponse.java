package com.vclassrooms.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Rahul on 01,August,2020
 */
public class FCMResponse {
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
    public class LoginDetails {

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
    public class Data {

        @SerializedName("LoginDetails")
        @Expose
        private List<LoginDetails> LoginDetails = null;

        public List<LoginDetails> getLoginDetails() {
            return LoginDetails;
        }

        public void setLoginDetails(List<LoginDetails> LoginDetails) {
            this.LoginDetails = LoginDetails;
        }

    }
}
