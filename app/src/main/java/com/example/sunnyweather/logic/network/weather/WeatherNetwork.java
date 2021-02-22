package com.example.sunnyweather.logic.network.weather;

import androidx.annotation.NonNull;

import com.example.sunnyweather.logic.model.ActualResponse;
import com.example.sunnyweather.logic.model.PredictionResponse;
import com.example.sunnyweather.logic.network.ILoadListener;
import com.example.sunnyweather.logic.network.ServiceCreator;
import com.example.sunnyweather.utils.LogUtils;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherNetwork {
    private static final String TAG = "WeatherNetwork";
    private static WeatherNetwork weatherNetwork;
    private final WeatherService weatherService;
    private final CyclicBarrier cyclicBarrier;

    private WeatherNetwork() {
        weatherService = ServiceCreator.getInstance().create(WeatherService.class);
        cyclicBarrier = new CyclicBarrier(2, () -> LogUtils.d(TAG, "执行完毕"));
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
        Call<ActualResponse> realTimeResponseCall = weatherService.getRealTimeWeather(lng, lat);
        realTimeResponseCall.enqueue(new Callback<ActualResponse>() {
            @Override
            public void onResponse(@NonNull Call<ActualResponse> call, @NonNull Response<ActualResponse> response) {
                if (response.body() != null) {
                    iLoadListener.success(response.body());
                } else {
                    iLoadListener.failed("实时天气数据为空！");
                }
            }

            @Override
            public void onFailure(@NonNull Call<ActualResponse> call, @NonNull Throwable t) {
                iLoadListener.failed("实时天气" + t.toString());
            }

        });
    }

    public void getWeatherResponse(String lng, String lat, ILoadListener iLoadListener) {
        Call<PredictionResponse> weatherResponseCall = weatherService.getWeather(lng, lat);
        weatherResponseCall.enqueue(new Callback<PredictionResponse>() {
            @Override
            public void onResponse(@NonNull Call<PredictionResponse> call, @NonNull Response<PredictionResponse> response) {
                if (response.body() != null) {
                    try {
                        cyclicBarrier.await();
                    } catch (BrokenBarrierException | InterruptedException e) {
                        LogUtils.e(TAG, e.toString());
                    }
                    iLoadListener.success(response.body());
                } else {
                    iLoadListener.failed("天气预报数据为空！");
                }

            }

            @Override
            public void onFailure(@NonNull Call<PredictionResponse> call, @NonNull Throwable t) {
                iLoadListener.failed("预报天气" + t.toString());
            }
        });
    }

}
