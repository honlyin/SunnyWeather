package com.example.sunnyweather.logic.network.weather;

import androidx.annotation.NonNull;

import com.example.sunnyweather.logic.model.RealTimeResponse;
import com.example.sunnyweather.logic.network.ILoadListener;
import com.example.sunnyweather.logic.network.ServiceCreator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherNetwork {
    private static WeatherNetwork weatherNetwork;
    private final WeatherService weatherService;

    private WeatherNetwork() {
        weatherService = ServiceCreator.getInstance().create(WeatherService.class);
    }

    public static WeatherNetwork getInstance() {
        synchronized (WeatherNetwork.class) {
            if (weatherNetwork == null) {
                synchronized (WeatherNetwork.class) {
                    weatherNetwork = new WeatherNetwork();
                }
            }
        }
        return weatherNetwork;
    }

    public void getRealtimeWeather(String lng, String lat, ILoadListener iLoadListener) {
        Call<RealTimeResponse> realTimeResponseCall = weatherService.getRealTimeWeather(lng, lat);
        realTimeResponseCall.enqueue(new Callback<RealTimeResponse>() {
            @Override
            public void onResponse(@NonNull Call<RealTimeResponse> call, @NonNull Response<RealTimeResponse> response) {
                if (response.body() != null) {
                    iLoadListener.success(response.body());
                } else {
                    iLoadListener.failed("realTime is null");
                }
            }

            @Override
            public void onFailure(@NonNull Call<RealTimeResponse> call, @NonNull Throwable t) {
                iLoadListener.failed(t.toString());
            }

        });
    }

}
