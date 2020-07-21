package com.vclassrooms.Entity;

/**
 * Created by Rahul on 11,July,2020
 */
public class UploadImageDetails {

    private String FilePath;
    private String ImageName;



    public String getFilePath() {
        return FilePath;
    }

    public void setFilePath(String filePath) {
        FilePath = filePath;
    }



    public UploadImageDetails() {

    }

    public UploadImageDetails( String filePath, String qImageName) {
        FilePath = filePath;
        ImageName = qImageName;
    }

    public String getImageName() {
        return ImageName;
    }

    public void setImageName(String imageName) {
        ImageName = imageName;
    }
}
