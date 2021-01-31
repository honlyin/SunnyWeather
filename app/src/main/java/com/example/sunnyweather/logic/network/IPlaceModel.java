package com.example.sunnyweather.logic.network;

public interface IPlaceModel {
    void searchPlaces(String query, ILoadListener loadListener);
}
