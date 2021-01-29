package com.example.sunnyweather.logic.model;

public class PlaceResponse {
    String status;
    Place place;

    public PlaceResponse(String status, Place place) {
        this.status = status;
        this.place = place;
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
