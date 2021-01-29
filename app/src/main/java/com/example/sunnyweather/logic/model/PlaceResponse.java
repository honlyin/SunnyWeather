package com.example.sunnyweather.logic.model;

import java.util.List;

public class PlaceResponse {
    public String status;
    public List<Place> places;

    public PlaceResponse(String status, List<Place> places) {
        this.status = status;
        this.places = places;
    }

    public static class Place {
        String name;
        Location location;
        String address;

        public Place(String name, Location location, String address) {
            this.name = name;
            this.location = location;
            this.address = address;
        }
    }

    public static class Location {
        String longitude;
        String latitude;

        public Location(String longitude, String latitude) {
            this.longitude = longitude;
            this.latitude = latitude;
        }
    }
}
