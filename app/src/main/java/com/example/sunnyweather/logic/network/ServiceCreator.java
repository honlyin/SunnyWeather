package com.example.sunnyweather.logic.network;

import com.example.sunnyweather.utils.LogUtils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceCreator {
    private static final String TAG = "ServiceCreator";
    private final String BASE_URL = "https://api.caiyunapp.com";
    private static ServiceCreator serviceCreator;
    private final Retrofit retrofit;

    private ServiceCreator() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    static ServiceCreator getInstance() {
        if (serviceCreator == null) {
            serviceCreator = new ServiceCreator();
        }
        return serviceCreator;
    }


    public <T> T create(Class<T> serviceClass) {
        LogUtils.d(TAG, "create");
        return retrofit.create(serviceClass);
    }
}
