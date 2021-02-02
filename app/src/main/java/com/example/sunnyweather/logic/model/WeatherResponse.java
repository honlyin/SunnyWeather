package com.example.sunnyweather.logic.model;

import com.google.gson.annotations.SerializedName;

public class WeatherResponse {
    Result result;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public static class Result {
        @SerializedName("hourly")
        HourlyResponse hourlyResponse;
        @SerializedName("daily")
        DailyResponse dailyResponse;

        public HourlyResponse getHourlyResponse() {
            return hourlyResponse;
        }

        public void setHourlyResponse(HourlyResponse hourlyResponse) {
            this.hourlyResponse = hourlyResponse;
        }

        public DailyResponse getDailyResponse() {
            return dailyResponse;
        }

        public void setDailyResponse(DailyResponse dailyResponse) {
            this.dailyResponse = dailyResponse;
        }
    }
}
