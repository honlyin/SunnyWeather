package com.example.sunnyweather.logic.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class HourlyResponse {
    @SerializedName("temperature")
    List<Temperature> temperatureList;
    @SerializedName("skycon")
    List<Skycon> skyconList;

    public List<Temperature> getTemperatureList() {
        return temperatureList;
    }

    public void setTemperatureList(List<Temperature> temperatureList) {
        this.temperatureList = temperatureList;
    }

    public List<Skycon> getSkyconList() {
        return skyconList;
    }

    public void setSkyconList(List<Skycon> skyconList) {
        this.skyconList = skyconList;
    }

    public static class Temperature {
        Date datetime;
        String value;

        public Date getDatetime() {
            return datetime;
        }

        public void setDatetime(Date datetime) {
            this.datetime = datetime;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public static class Skycon {
        Date datetime;
        String value;

        public Date getDatetime() {
            return datetime;
        }

        public void setDatetime(Date datetime) {
            this.datetime = datetime;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

}
