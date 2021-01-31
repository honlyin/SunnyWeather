package com.example.sunnyweather.logic.network;

import androidx.annotation.NonNull;

import com.example.sunnyweather.logic.model.PlaceResponse;
import com.example.sunnyweather.utils.LogUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SunnyWeatherNetworkUtil implements IPlaceModel {
    private static final String TAG = "SunnyWeatherNetworkUtil";
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
        LogUtils.d(TAG, "searchPlaces query = " + query);
        Call<PlaceResponse> placeResponseCall = placeService.searchPlaces(query);
        placeResponseCall.enqueue(new Callback<PlaceResponse>() {
            @Override
            public void onResponse(@NonNull Call<PlaceResponse> call, @NonNull Response<PlaceResponse> response) {
                LogUtils.d(TAG, "onResponse: ");
                PlaceResponse body = response.body();
                if (body != null) {
                    LogUtils.d(TAG, "onResponse: !=  null");
                    loadListener.success(body);
                } else {
                    LogUtils.d(TAG, "onResponse: null");
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
