package com.example.sunnyweather.logic;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.sunnyweather.logic.model.PlaceResponse;
import com.example.sunnyweather.logic.network.SunnyWeatherNetworkUtil;

import java.util.List;

public class Repository {
    private static final String TAG = "Repository";
    private static Repository repository;

    private Repository() {
    }

    public static Repository getInstance() {
        if (repository == null) {
            repository = new Repository();
        }
        return repository;
    }

    private LiveData<PlaceResponse> searchPlaces(String query) {
        LiveData<PlaceResponse> responseLiveData = null;
        new Thread() {
            @Override
            public void run() {
                try {
                    PlaceResponse placeResponse = SunnyWeatherNetworkUtil.getInstance().searchPlaces(query);
                    if (placeResponse.status.equals("ok")) {
                        List<PlaceResponse.Place> placeList = placeResponse.places;
                    } else {
                        Log.e(TAG, "response status is  " + placeResponse.status);
                    }
                } catch (Exception e) {
                    Log.e(TAG, "run: ", e);
                }
            }
        };
        return responseLiveData;
    }

}
