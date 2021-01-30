package com.example.sunnyweather.ui.place;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.sunnyweather.logic.Repository;
import com.example.sunnyweather.logic.model.PlaceResponse;

import java.util.ArrayList;
import java.util.List;

public class PlaceViewModel extends ViewModel {
    private final MutableLiveData<String> searchLiveData = new MutableLiveData<>();
    final List<PlaceResponse.Place> placeList = new ArrayList<>();
    final LiveData<PlaceResponse.Place> placeLiveData = Transformations.switchMap(searchLiveData,
            input -> Repository.getInstance().searchPlaces(input));

    public void searchPlaces(String query) {
        searchLiveData.setValue(query);
    }

}
