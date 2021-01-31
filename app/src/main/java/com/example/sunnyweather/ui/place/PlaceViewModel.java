package com.example.sunnyweather.ui.place;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.sunnyweather.logic.Repository;
import com.example.sunnyweather.logic.model.PlaceResponse;
import com.example.sunnyweather.logic.network.IQueryListener;
import com.example.sunnyweather.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

public class PlaceViewModel extends ViewModel {
    private static final String TAG = "PlaceViewModel";
    private final MutableLiveData<String> searchLiveData = new MutableLiveData<>();
    final List<PlaceResponse.Place> placeList = new ArrayList<>();
    MutableLiveData<List<PlaceResponse.Place>> responseLiveData = new MutableLiveData<>();
    private final List<PlaceResponse.Place> places = new ArrayList<>();


    private final IQueryListener iQueryListener = new IQueryListener() {
        @Override
        public void success(List<PlaceResponse.Place> placeList) {
            responseLiveData.postValue(placeList);
            LogUtils.d(TAG, System.currentTimeMillis() + " success");
        }

        @Override
        public void failed(String error) {
            LogUtils.d(TAG, error);
        }
    };

    final LiveData<List<PlaceResponse.Place>> placeLiveData = Transformations.switchMap(searchLiveData,
            input -> {
                LogUtils.d(TAG, "apply: input = " + input);
                synchronized (PlaceViewModel.class) {
                    Repository.getInstance().searchPlaces(input, iQueryListener);
                }
                return responseLiveData;
            });

    public void searchPlaces(String query) {
        LogUtils.d(TAG, "searchPlaces: query = " + query);
        searchLiveData.setValue(query);
    }

}
