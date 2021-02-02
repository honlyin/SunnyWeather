package com.example.sunnyweather.logic.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class DailyResponse {
    @SerializedName("astro")
    List<Astro> astroList;
    @SerializedName("temperature")
    List<Temperature> temperatureList;
    @SerializedName("skycon")
    List<HourlyResponse.Skycon> skyconList;
    @SerializedName("life_index")
    LifeIndex lifeIndex;

    public List<Astro> getAstroList() {
        return astroList;
    }

    public void setAstroList(List<Astro> astroList) {
        this.astroList = astroList;
    }

    public List<Temperature> getTemperatureList() {
        return temperatureList;
    }

    public void setTemperatureList(List<Temperature> temperatureList) {
        this.temperatureList = temperatureList;
    }

    public List<HourlyResponse.Skycon> getSkyconList() {
        return skyconList;
    }

    public void setSkyconList(List<HourlyResponse.Skycon> skyconList) {
        this.skyconList = skyconList;
    }

    public LifeIndex getLifeIndex() {
        return lifeIndex;
    }

    public void setLifeIndex(LifeIndex lifeIndex) {
        this.lifeIndex = lifeIndex;
    }

    public static class Astro {
        Date date;
        Sunrise sunrise;
        Sunset sunset;

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public Sunrise getSunrise() {
            return sunrise;
        }

        public void setSunrise(Sunrise sunrise) {
            this.sunrise = sunrise;
        }

        public Sunset getSunset() {
            return sunset;
        }

        public void setSunset(Sunset sunset) {
            this.sunset = sunset;
        }
    }

    public static class Sunrise {
        String time;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }

    public static class Sunset {
        String time;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }

    public static class Temperature {
        Date date;
        String max;
        String min;

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public String getMax() {
            return max;
        }

        public void setMax(String max) {
            this.max = max;
        }

        public String getMin() {
            return min;
        }

        public void setMin(String min) {
            this.min = min;
        }
    }

    public static class LifeIndex {
        @SerializedName("ultraviolet")
        List<LifeIndexInfo> ultravioletList;
        @SerializedName("carWashing")
        List<LifeIndexInfo> carWashingList;
        @SerializedName("dressing")
        List<LifeIndexInfo> dressingList;
        @SerializedName("comfort")
        List<LifeIndexInfo> comfortList;
        @SerializedName("coldRisk")
        List<LifeIndexInfo> coldRiskList;

        public List<LifeIndexInfo> getUltravioletList() {
            return ultravioletList;
        }

        public void setUltravioletList(List<LifeIndexInfo> ultravioletList) {
            this.ultravioletList = ultravioletList;
        }

        public List<LifeIndexInfo> getCarWashingList() {
            return carWashingList;
        }

        public void setCarWashingList(List<LifeIndexInfo> carWashingList) {
            this.carWashingList = carWashingList;
        }

        public List<LifeIndexInfo> getDressingList() {
            return dressingList;
        }

        public void setDressingList(List<LifeIndexInfo> dressingList) {
            this.dressingList = dressingList;
        }

        public List<LifeIndexInfo> getComfortList() {
            return comfortList;
        }

        public void setComfortList(List<LifeIndexInfo> comfortList) {
            this.comfortList = comfortList;
        }

        public List<LifeIndexInfo> getColdRiskList() {
            return coldRiskList;
        }

        public void setColdRiskList(List<LifeIndexInfo> coldRiskList) {
            this.coldRiskList = coldRiskList;
        }

        public static class LifeIndexInfo {
            Date date;
            int index;
            String desc;

            public Date getDate() {
                return date;
            }

            public void setDate(Date date) {
                this.date = date;
            }

            public int getIndex() {
                return index;
            }

            public void setIndex(int index) {
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
}
