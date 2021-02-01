package com.example.sunnyweather.logic.network;

import com.example.sunnyweather.logic.model.PlaceResponse;

public interface ILoadListener {
    void success(PlaceResponse placeResponse);
    void failed(String error);
}
