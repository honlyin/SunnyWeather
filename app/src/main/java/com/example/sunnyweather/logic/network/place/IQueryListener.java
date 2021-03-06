package com.example.sunnyweather.logic.network.place;

import com.example.sunnyweather.logic.model.PlaceResponse;

import java.util.List;

public interface IQueryListener {
    void success(List<PlaceResponse.Place> placeList);

    void failed(String error);
}
