package com.vclassrooms.Entity;

/**
 * Created by Rahul on 14,July,2020
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HolidayDetailResponse {

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

        @SerializedName("HolidayEvent")
        @Expose
        private List<HolidayEvent> holidayEvent = null;

        public List<HolidayEvent> getHolidayEvent() {
            return holidayEvent;
        }

        public void setHolidayEvent(List<HolidayEvent> holidayEvent) {
            this.holidayEvent = holidayEvent;
        }

    }
    public class HolidayEvent {

        @SerializedName("Holiday_Id")
        @Expose
        private Integer holidayId;
        @SerializedName("Holiday_Name")
        @Expose
        private String holidayName;
        @SerializedName("Holiday_Details")
        @Expose
        private String holidayDetails;
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

        public Integer getHolidayId() {
            return holidayId;
        }

        public void setHolidayId(Integer holidayId) {
            this.holidayId = holidayId;
        }

        public String getHolidayName() {
            return holidayName;
        }

        public void setHolidayName(String holidayName) {
            this.holidayName = holidayName;
        }

        public String getHolidayDetails() {
            return holidayDetails;
        }

        public void setHolidayDetails(String holidayDetails) {
            this.holidayDetails = holidayDetails;
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

    }
}
