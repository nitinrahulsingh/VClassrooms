package com.vclassrooms.Retrofit;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vclassrooms.R;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiService {
    private static ApiEndPoints zService;

    private final static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .readTimeout(5, TimeUnit.MINUTES)
            .connectTimeout(5, TimeUnit.MINUTES)
            .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .writeTimeout(5, TimeUnit.MINUTES)
            .build();

    private static Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    public static ApiEndPoints buildService(Context context) {
        if (zService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(context.getString(R.string.BASE_URL))
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(okHttpClient)
                    .build();
            zService = retrofit.create(ApiEndPoints.class);
        }
        return zService;
    }
}
