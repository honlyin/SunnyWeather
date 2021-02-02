package com.example.sunnyweather.logic.network.weather;

import com.example.sunnyweather.SunnyWeatherApplication;
import com.example.sunnyweather.logic.model.RealTimeResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface WeatherService {
    @GET("v2.5/" + SunnyWeatherApplication.TOKEN + "/{lng},{lat}/realtime.json")
    Call<RealTimeResponse> getRealTimeWeather(@Path("lng") String lng, @Path("lat") String lat);

    @GET("v2.5/" + SunnyWeatherApplication.TOKEN + "/{lng},{lat}/daily.json")
    Call<RealTimeResponse> getDailyWeather(@Path("lng") String lng, @Path("lat") String lat);

    @GET("v2.5/" + SunnyWeatherApplication.TOKEN + "/{lng},{lat}/hourly.json")
    Call<RealTimeResponse> getHourlyWeather(@Path("lng") String lng, @Path("lat") String lat);
}
