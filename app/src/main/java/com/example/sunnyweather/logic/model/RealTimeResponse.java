package com.example.sunnyweather.logic.model;

import com.google.gson.annotations.SerializedName;

public class RealTimeResponse {
    Result result;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public static class Result {
        @SerializedName("realtime")
        RealTime realTime;

        public RealTime getRealTime() {
            return realTime;
        }

        public void setRealTime(RealTime realTime) {
            this.realTime = realTime;
        }
    }

    public static class RealTime {
        float temperature;
        float humidity;
        String skycon;
        float visibility;
        Wind wind;
        float pressure;
        float apparent_temperature;
        @SerializedName("air_quality")
        AirQuality airQuality;
        @SerializedName("life_index")
        LifeIndex lifeIndex;

        public float getTemperature() {
            return temperature;
        }

        public void setTemperature(float temperature) {
            this.temperature = temperature;
        }

        public float getHumidity() {
            return humidity;
        }

        public void setHumidity(float humidity) {
            this.humidity = humidity;
        }

        public String getSkycon() {
            return skycon;
        }

        public void setSkycon(String skycon) {
            this.skycon = skycon;
        }

        public float getVisibility() {
            return visibility;
        }

        public void setVisibility(float visibility) {
            this.visibility = visibility;
        }

        public Wind getWind() {
            return wind;
        }

        public void setWind(Wind wind) {
            this.wind = wind;
        }

        public float getPressure() {
            return pressure;
        }

        public void setPressure(float pressure) {
            this.pressure = pressure;
        }

        public float getApparent_temperature() {
            return apparent_temperature;
        }

        public void setApparent_temperature(float apparent_temperature) {
            this.apparent_temperature = apparent_temperature;
        }

        public AirQuality getAirQuality() {
            return airQuality;
        }

        public void setAirQuality(AirQuality airQuality) {
            this.airQuality = airQuality;
        }

        public LifeIndex getLifeIndex() {
            return lifeIndex;
        }

        public void setLifeIndex(LifeIndex lifeIndex) {
            this.lifeIndex = lifeIndex;
        }
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

        public float getPm25() {
            return pm25;
        }

        public void setPm25(float pm25) {
            this.pm25 = pm25;
        }

        public float getPm10() {
            return pm10;
        }

        public void setPm10(float pm10) {
            this.pm10 = pm10;
        }

        public float getO3() {
            return o3;
        }

        public void setO3(float o3) {
            this.o3 = o3;
        }

        public float getSo2() {
            return so2;
        }

        public void setSo2(float so2) {
            this.so2 = so2;
        }

        public float getNo2() {
            return no2;
        }

        public void setNo2(float no2) {
            this.no2 = no2;
        }

        public float getCo() {
            return co;
        }

        public void setCo(float co) {
            this.co = co;
        }

        public AQI getAqi() {
            return aqi;
        }

        public void setAqi(AQI aqi) {
            this.aqi = aqi;
        }

        public Description getDescription() {
            return description;
        }

        public void setDescription(Description description) {
            this.description = description;
        }
    }

    public static class Wind {
        float speed;
        float direction;

        public float getSpeed() {
            return speed;
        }

        public void setSpeed(float speed) {
            this.speed = speed;
        }

        public float getDirection() {
            return direction;
        }

        public void setDirection(float direction) {
            this.direction = direction;
        }
    }

    public static class LifeIndex {
        Ultraviolet ultraviolet;
        Comfort comfort;

        public Ultraviolet getUltraviolet() {
            return ultraviolet;
        }

        public void setUltraviolet(Ultraviolet ultraviolet) {
            this.ultraviolet = ultraviolet;
        }

        public Comfort getComfort() {
            return comfort;
        }

        public void setComfort(Comfort comfort) {
            this.comfort = comfort;
        }
    }

    public static class AQI {
        String chn;

        public String getChn() {
            return chn;
        }

        public void setChn(String chn) {
            this.chn = chn;
        }
    }

    public static class Description {
        String chn;

        public String getChn() {
            return chn;
        }

        public void setChn(String chn) {
            this.chn = chn;
        }
    }

    public static class Comfort {
        String index;
        String desc;

        public String getIndex() {
            return index;
        }

        public void setIndex(String index) {
            this.index = index;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }

    public static class Ultraviolet {
        String index;
        String desc;

        public String getIndex() {
            return index;
        }

        public void setIndex(String index) {
            this.index = index;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }
}
