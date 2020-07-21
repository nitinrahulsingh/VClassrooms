package com.vclassrooms.photopicker;

import java.util.ArrayList;

public class Constants {
    public static ArrayList<String> FORMAT_IMAGE = new ImageTypeList();

    //5072558092319769821
    //public static final String PINTEREST_APP_ID = "5069627169790021413";
    public static final String PINTEREST_APP_ID = "5072558092319769821";

    public static final String PINTEREST_APP_SECRET = "e654806bcb2448ee33ab5e273d8cb4fe434ff807f2be33e4db8373da5219bdb7";

    /**
     * Twitter consumerkey and  consumer secret
     */


    public static final String CONSUMER_KEY = "7nOmYxXpBDXffKW2d4NsBtreq";
    public static final String CONSUMER_SECRET = "1myfRnQIM69NN7K5vyDmbBZ9d4kdKNpafs8kj26UZfXB1bUzYU";

//    public static final String MEMBER_NO = "998";

    /*

    LinkedIn Client Id And app secret
    * */
    public static  final String LINKEDIN_COSUMER_KEY="866ncy4hwc4wbl";
    public static  final String LINKEDIN_APP_SECRET="O6xJCx2lQxIUodCp";




    /*
     * Instagram ID
     * */
    public static final String INSTAGRAM_APP_ID = "";

    static class ImageTypeList extends ArrayList<String> {
        ImageTypeList() {
            add(".PNG");
            add(".JPEG");
            add(".jpg");
            add(".png");
            add(".jpeg");
            add(".JPG");
            add(".GIF");
            add(".gif");
        }
    }



    public static final String SOCIAL_CONNECT_TYPE="type";

    /**
     *
     */





}
