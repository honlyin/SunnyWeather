package com.example.sunnyweather.ui.weather;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.sunnyweather.logic.Repository;
import com.example.sunnyweather.logic.model.PlaceResponse;
import com.example.sunnyweather.logic.model.ActualResponse;
import com.example.sunnyweather.logic.model.PredictionResponse;

public class WeatherViewModel extends ViewModel {
    private static final String TAG = "WeatherViewModel";
    private final MutableLiveData<PlaceResponse.Location> searchLiveData = new MutableLiveData<>();

    public final LiveData<ActualResponse.RealTime> realTimeLiveData = Transformations.switchMap(searchLiveData,
            input -> Repository.getInstance().refreshActualWeather(input.getLng(), input.getLat()));

    public final LiveData<PredictionResponse.Result> predictionLiveData = Transformations.switchMap(searchLiveData,
            input -> Repository.getInstance().getPredictionWeather(input.getLng(), input.getLat()));

    public void searchPlaces(PlaceResponse.Location location) {
        searchLiveData.setValue(location);
    }
}
