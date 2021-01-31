package com.example.sunnyweather.logic.model;

import android.provider.BaseColumns;

public final class PlaceReaderContract {
    public PlaceReaderContract() {
    }

    public static abstract class PlaceEntry implements BaseColumns {
        public static final String TABLE_NAME = "place";
        public static final String COLUMN_NAME_ENTRY_ID = "adcode";
        public static final String COLUMN_NAME_LNG = "lng";
        public static final String COLUMN_NAME_LAT = "lat";
        public static final String COLUMN_NAME_PROVINCE = "province";
        public static final String COLUMN_NAME_CITY = "city";
        public static final String COLUMN_NAME_DISTRICT = "district";
        public static final String COLUMN_NAME_FORMATTED_ADDRESS = "formatted_address";
    }
}
