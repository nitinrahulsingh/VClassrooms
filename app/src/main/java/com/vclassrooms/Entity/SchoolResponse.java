package com.vclassrooms.Entity;

/**
 * Created by Rahul on 26,June,2020
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SchoolResponse {

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

        @SerializedName("SchoolDetails")
        @Expose
        private List<SchoolDetail> schoolDetails = null;

        public List<SchoolDetail> getSchoolDetails() {
            return schoolDetails;
        }

        public void setSchoolDetails(List<SchoolDetail> schoolDetails) {
            this.schoolDetails = schoolDetails;
        }

    }
}
