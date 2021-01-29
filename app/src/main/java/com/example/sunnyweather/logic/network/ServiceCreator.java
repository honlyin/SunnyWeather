package com.example.sunnyweather.logic.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceCreator {
    private final String BASE_URL = "https://api.caiyunapp.com";
    private static ServiceCreator serviceCreator;
    private final Retrofit retrofit;

    private ServiceCreator() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static ServiceCreator getInstance() {
        if (serviceCreator == null) {
            serviceCreator = new ServiceCreator();
        }
        return serviceCreator;
    }


    public <T> T create(Class<T> serviceClass) {
        return retrofit.create(serviceClass);
    }
}
