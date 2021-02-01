package com.example.sunnyweather.logic.network.place;

import com.example.sunnyweather.logic.network.ILoadListener;

public interface IPlaceModel {
    void searchPlaces(String query, ILoadListener loadListener);
}
