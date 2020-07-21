package com.vclassrooms.Entity;

/**
 * Created by Rahul on 21,July,2020
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StandardListResponse {

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


    public class StandardDetail {

        @SerializedName("Standard_Id")
        @Expose
        private Integer standardId;
        @SerializedName("Standard_Name")
        @Expose
        private String standardName;

        public Integer getStandardId() {
            return standardId;
        }

        public void setStandardId(Integer standardId) {
            this.standardId = standardId;
        }

        public String getStandardName() {
            return standardName;
        }

        public void setStandardName(String standardName) {
            this.standardName = standardName;
        }

        @Override
        public String toString() {
            return standardName;
        }
    }
    public class Data {

        @SerializedName("StandardDetails")
        @Expose
        private List<StandardDetail> standardDetails = null;

        public List<StandardDetail> getStandardDetails() {
            return standardDetails;
        }

        public void setStandardDetails(List<StandardDetail> standardDetails) {
            this.standardDetails = standardDetails;
        }

    }
}
