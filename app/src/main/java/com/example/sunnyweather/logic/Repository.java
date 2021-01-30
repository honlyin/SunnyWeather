package com.example.sunnyweather.logic;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.sunnyweather.logic.model.PlaceResponse;
import com.example.sunnyweather.logic.network.ILoadListener;
import com.example.sunnyweather.logic.network.SunnyWeatherNetworkUtil;
import com.example.sunnyweather.utils.LogUtils;

import java.util.List;

public class Repository {
    private static final String TAG = "Repository";
    private static Repository repository;
    private PlaceResponse mPlaceResponse;

    private Repository() {
    }

    public static Repository getInstance() {
        if (repository == null) {
            repository = new Repository();
        }
        return repository;
    }

    private final ILoadListener loadListener = new ILoadListener() {
        @Override
        public void success(PlaceResponse placeResponse) {
            mPlaceResponse = placeResponse;
        }

        @Override
        public void failed(String error) {
            LogUtils.e(TAG, "failed: " + error);
        }
    };

    public MutableLiveData<List<PlaceResponse.Place>> searchPlaces(String query) {
        LogUtils.d(TAG, "searchPlaces: query = " + query);
        MutableLiveData<List<PlaceResponse.Place>> responseLiveData = new MutableLiveData<>();
        new Thread() {
            @Override
            public void run() {
                SunnyWeatherNetworkUtil.getInstance().searchPlaces(query, loadListener);
                if (mPlaceResponse.status.equals("ok")) {
                    responseLiveData.setValue(mPlaceResponse.places);
                } else {
                    LogUtils.e(TAG, "response status is  " + mPlaceResponse.status);
                }
            }
        }.start();
        return responseLiveData;
    }

}
