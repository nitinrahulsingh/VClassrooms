package com.vclassrooms.Common;
import android.app.Application;


public class FagmentPageOpen extends Application {
    private static boolean sIsFragmentOpen = false;

    public static boolean isFragmentOpen() {
        return sIsFragmentOpen;
    }

    public static void setFragmentOpen(boolean isFragmentOpen) {
        FagmentPageOpen.sIsFragmentOpen = isFragmentOpen;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
