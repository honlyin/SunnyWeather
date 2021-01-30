package com.example.sunnyweather.logic.network;

interface IPlaceModel {
    void searchPlaces(String query, ILoadListener loadListener);
}
