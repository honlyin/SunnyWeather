package com.example.sunnyweather.logic;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.sunnyweather.logic.model.PlaceResponse;
import com.example.sunnyweather.logic.network.ILoadListener;
import com.example.sunnyweather.logic.network.SunnyWeatherNetworkUtil;

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

    public MutableLiveData<PlaceResponse> searchPlaces(String query) {
        MutableLiveData<PlaceResponse> responseLiveData = new MutableLiveData<>();
        new Thread() {
            @Override
            public void run() {
                try {
                    final PlaceResponse[] mPlaceResponse = new PlaceResponse[1];
                    SunnyWeatherNetworkUtil.getInstance().searchPlaces(query, new ILoadListener() {
                        @Override
                        public void success(PlaceResponse placeResponse) {
                            mPlaceResponse[0] = placeResponse;
                        }

                        @Override
                        public void failed(String error) {

                        }
                    });
                    if (mPlaceResponse[0].status.equals("ok")) {
                        responseLiveData.setValue(mPlaceResponse[0]);
                    } else {
                        Log.e(TAG, "response status is  " + mPlaceResponse[0].status);
                    }
                } catch (Exception e) {
                    Log.e(TAG, "run: ", e);
                }
            }
        };
        return responseLiveData;
    }

}
