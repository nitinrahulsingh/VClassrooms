package com.vclassrooms.Entity;

/**
 * Created by Rahul on 25,July,2020
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AnnouncementDetailResponse {

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

        @SerializedName("AnnouncementDetail")
        @Expose
        private List<AnnouncementDetail> announcementDetail = null;

        public List<AnnouncementDetail> getAnnouncementDetail() {
            return announcementDetail;
        }

        public void setAnnouncementDetail(List<AnnouncementDetail> announcementDetail) {
            this.announcementDetail = announcementDetail;
        }

    }
    public class AnnouncementDetail {

        @SerializedName("AnnouncementDet_Id")
        @Expose
        private Integer announcementDetId;
        @SerializedName("Announcement_Id")
        @Expose
        private Integer announcementId;
        @SerializedName("Announcement_Name")
        @Expose
        private String announcementName;
        @SerializedName("Announcement_Details")
        @Expose
        private String announcementDetails;
        @SerializedName("FromDate")
        @Expose
        private String fromDate;
        @SerializedName("ToDate")
        @Expose
        private String toDate;
        @SerializedName("NoOfDays")
        @Expose
        private Integer noOfDays;
        @SerializedName("School_Id")
        @Expose
        private Integer schoolId;
        @SerializedName("ActiveFlg")
        @Expose
        private String activeFlg;

        public Integer getAnnouncementDetId() {
            return announcementDetId;
        }

        public void setAnnouncementDetId(Integer announcementDetId) {
            this.announcementDetId = announcementDetId;
        }

        public Integer getAnnouncementId() {
            return announcementId;
        }

        public void setAnnouncementId(Integer announcementId) {
            this.announcementId = announcementId;
        }

        public String getAnnouncementName() {
            return announcementName;
        }

        public void setAnnouncementName(String announcementName) {
            this.announcementName = announcementName;
        }

        public String getAnnouncementDetails() {
            return announcementDetails;
        }

        public void setAnnouncementDetails(String announcementDetails) {
            this.announcementDetails = announcementDetails;
        }

        public String getFromDate() {
            return fromDate;
        }

        public void setFromDate(String fromDate) {
            this.fromDate = fromDate;
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

    }
}
