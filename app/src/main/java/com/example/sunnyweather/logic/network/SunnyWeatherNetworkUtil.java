package com.example.sunnyweather.logic.network;

import androidx.annotation.NonNull;

import com.example.sunnyweather.logic.model.PlaceResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SunnyWeatherNetworkUtil {
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


    public PlaceResponse searchPlaces(String query) {
        final PlaceResponse[] placeResponse = new PlaceResponse[1];
        Call<PlaceResponse> placeResponseCall = placeService.searchPlaces(query);
        placeResponseCall.enqueue(new Callback<PlaceResponse>() {
            @Override
            public void onResponse(@NonNull Call<PlaceResponse> call, @NonNull Response<PlaceResponse> response) {
                PlaceResponse body = response.body();
                if (body != null) {
                    placeResponse[0] = body;
                } else {
                    throw new RuntimeException("response body is null");
                }
            }

            @Override
            public void onFailure(@NonNull Call<PlaceResponse> call, @NonNull Throwable t) {
                throw new RuntimeException(t);
            }
        });
        return placeResponse[0];
    }


}
