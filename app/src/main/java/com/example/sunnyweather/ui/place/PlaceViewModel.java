package com.example.sunnyweather.ui.place;

import android.util.Log;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.sunnyweather.logic.Repository;
import com.example.sunnyweather.logic.model.PlaceResponse;
import com.example.sunnyweather.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

public class PlaceViewModel extends ViewModel {
    private static final String TAG = "PlaceViewModel";
    private final MutableLiveData<String> searchLiveData = new MutableLiveData<>();
    final List<PlaceResponse.Place> placeList = new ArrayList<>();
    final LiveData<List<PlaceResponse.Place>> placeLiveData = Transformations.switchMap(searchLiveData,
            input -> {
                Log.d(TAG, "apply: input = " + input);
                return Repository.getInstance().searchPlaces(input);
            });

    public void searchPlaces(String query) {
        LogUtils.d(TAG, "searchPlaces: query = " + query);
        searchLiveData.setValue(query);
    }

}
