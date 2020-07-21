package com.vclassrooms.Entity;

/**
 * Created by Rahul on 10,July,2020
 */
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;



public class StandardDivisionResponse {

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
    public class Division {

        @SerializedName("Standard_Id")
        @Expose
        private Integer standardId;
        @SerializedName("Division_Id")
        @Expose
        private Integer divisionId;
        @SerializedName("Standard_Name")
        @Expose
        private String standardName;
        @SerializedName("Division_Name")
        @Expose
        private String divisionName;

        public Integer getStandardId() {
            return standardId;
        }

        public void setStandardId(Integer standardId) {
            this.standardId = standardId;
        }

        public Integer getDivisionId() {
            return divisionId;
        }

        public void setDivisionId(Integer divisionId) {
            this.divisionId = divisionId;
        }

        public String getStandardName() {
            return standardName;
        }

        public void setStandardName(String standardName) {
            this.standardName = standardName;
        }

        public String getDivisionName() {
            return divisionName;
        }

        public void setDivisionName(String divisionName) {
            this.divisionName = divisionName;
        }

        @Override
        public String toString() {
            return  standardName + " (" + divisionName + ")";
        }
    }
    public class Data {

        @SerializedName("Division")
        @Expose
        private List<Division> division = null;

        public List<Division> getDivision() {
            return division;
        }

        public void setDivision(List<Division> division) {
            this.division = division;
        }

    }
}
