package com.example.sunnyweather.logic.network;

import androidx.annotation.NonNull;

import com.example.sunnyweather.logic.model.PlaceResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SunnyWeatherNetworkUtil implements IPlaceModel {
    public static SunnyWeatherNetworkUtil sunnyWeatherNetworkUtil;
    private final PlaceService placeService;

    private SunnyWeatherNetworkUtil() {
        placeService = ServiceCreator.getInstance().create(PlaceService.class);
    }

    public static SunnyWeatherNetworkUtil getInstance() {
        if (sunnyWeatherNetworkUtil == null) {
            synchronized (SunnyWeatherNetworkUtil.class) {
                if (sunnyWeatherNetworkUtil == null) {
                    sunnyWeatherNetworkUtil = new SunnyWeatherNetworkUtil();
                }
            }
        }
        return sunnyWeatherNetworkUtil;
    }

    @Override
    public void searchPlaces(String query, ILoadListener loadListener) {
        Call<PlaceResponse> placeResponseCall = placeService.searchPlaces(query);
        placeResponseCall.enqueue(new Callback<PlaceResponse>() {
            @Override
            public void onResponse(@NonNull Call<PlaceResponse> call, @NonNull Response<PlaceResponse> response) {
                PlaceResponse body = response.body();
                if (body != null) {
                    loadListener.success(body);
                } else {
                    loadListener.failed("response body is null");
                }
            }

            @Override
            public void onFailure(@NonNull Call<PlaceResponse> call, @NonNull Throwable t) {
                loadListener.failed(t.toString());
            }
        });
    }
}
