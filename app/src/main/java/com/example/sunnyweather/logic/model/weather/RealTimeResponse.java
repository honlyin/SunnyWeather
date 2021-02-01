package com.example.sunnyweather.logic.model.weather;

public class RealTimeResponse {
    public static class Result {
        RealTime realTime;
    }

    public static class RealTime {
        float temperature;
        float humidity;
        String skycon;
        float visibility;
        Wind wind;
        float pressure;
        float apparent_temperature;
        AirQuality airQuality;
        LifeIndex lifeIndex;
    }

    public static class AirQuality {
        float pm25;
        float pm10;
        float o3;
        float so2;
        float no2;
        float co;
        AQI aqi;
        Description description;
    }

    public static class Wind {
        float speed;
        float direction;
    }

    public static class LifeIndex {
        Ultraviolet ultraviolet;
        Comfort comfort;
    }

    public static class AQI {
        String chn;
    }

    public static class Description {
        String chn;
    }

    public static class Comfort {
        String index;
        String desc;
    }

    public static class Ultraviolet {
        String index;
        String desc;
    }
}
