package com.example.sunnyweather.ui.weather;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.sunnyweather.logic.Repository;
import com.example.sunnyweather.logic.model.PlaceResponse;
import com.example.sunnyweather.logic.model.RealTimeResponse;
import com.example.sunnyweather.logic.network.weather.WeatherNetwork;
import com.example.sunnyweather.utils.LogUtils;

import java.util.List;

public class RealTimeViewModel extends ViewModel {
    private static final String TAG = "RealTimeViewModel";
    private final MutableLiveData<PlaceResponse.Location> searchLiveData = new MutableLiveData<>();

    public final LiveData<RealTimeResponse.RealTime> realTimeLiveData = Transformations.switchMap(searchLiveData,
            input -> Repository.getInstance().refreshWeather(input.getLng(), input.getLat()));

    public void searchPlaces(PlaceResponse.Location location) {
        searchLiveData.setValue(location);
    }
}
