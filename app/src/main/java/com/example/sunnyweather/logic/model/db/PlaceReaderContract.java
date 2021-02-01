package com.example.sunnyweather.logic.model.db;

import android.provider.BaseColumns;

public final class PlaceReaderContract {
    public PlaceReaderContract() {
    }

    public static abstract class PlaceEntry implements BaseColumns {
        public static final String TABLE_NAME = "place";//表名
        public static final String COLUMN_NAME_ENTRY_ID = "adcode";//地址ID
        public static final String COLUMN_NAME_LNG = "lng";//经度
        public static final String COLUMN_NAME_LAT = "lat";//纬度
        public static final String COLUMN_NAME_PROVINCE = "province";//省名
        public static final String COLUMN_NAME_CITY = "city";//市名
        public static final String COLUMN_NAME_DISTRICT = "district";//区(县)名
        public static final String COLUMN_NAME_FORMATTED_ADDRESS = "formatted_address";//详细地址
    }
}
