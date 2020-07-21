package com.vclassrooms.Entity;

/**
 * Created by Rahul on 20,July,2020
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GalleryDetailResponse {

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
    public class GalleryDetail {

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

        @SerializedName("GalleryDetails")
        @Expose
        private List<GalleryDetail> galleryDetails = null;
        @SerializedName("GalleryDetails1")
        @Expose
        private List<GalleryDetails1> galleryDetails1 = null;

        public List<GalleryDetail> getGalleryDetails() {
            return galleryDetails;
        }

        public void setGalleryDetails(List<GalleryDetail> galleryDetails) {
            this.galleryDetails = galleryDetails;
        }

        public List<GalleryDetails1> getGalleryDetails1() {
            return galleryDetails1;
        }

        public void setGalleryDetails1(List<GalleryDetails1> galleryDetails1) {
            this.galleryDetails1 = galleryDetails1;
        }

    }
    public class GalleryDetails1 {

        @SerializedName("Gallery_Id")
        @Expose
        private Integer galleryId;
        @SerializedName("Title")
        @Expose
        private String title;
        @SerializedName("Discription")
        @Expose
        private String discription;
        @SerializedName("FilePath")
        @Expose
        private String filePath;

        public String getFolderPath() {
            return FolderPath;
        }

        public void setFolderPath(String folderPath) {
            FolderPath = folderPath;
        }

        @SerializedName("FolderPath")
        @Expose
        private String FolderPath;
        @SerializedName("Id")
        @Expose
        private Integer id;
        @SerializedName("InsertedDate")
        @Expose
        private String insertedDate;

        public Integer getGalleryId() {
            return galleryId;
        }

        public void setGalleryId(Integer galleryId) {
            this.galleryId = galleryId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDiscription() {
            return discription;
        }

        public void setDiscription(String discription) {
            this.discription = discription;
        }

        public String getFilePath() {
            return filePath;
        }

        public void setFilePath(String filePath) {
            this.filePath = filePath;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getInsertedDate() {
            return insertedDate;
        }

        public void setInsertedDate(String insertedDate) {
            this.insertedDate = insertedDate;
        }

        @Override
        public String toString() {
            return filePath ;
        }
    }
}
