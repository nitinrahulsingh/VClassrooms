package com.vclassrooms.Entity;

/**
 * Created by Rahul on 07,July,2020
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProfileDetailInsertResponse {

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
    public class ProfileDetail {

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

        @SerializedName("ProfileDetails")
        @Expose
        private List<ProfileDetail> profileDetails = null;

        public List<ProfileDetail> getProfileDetails() {
            return profileDetails;
        }

        public void setProfileDetails(List<ProfileDetail> profileDetails) {
            this.profileDetails = profileDetails;
        }

    }
}
