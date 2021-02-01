package com.example.sunnyweather.logic;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.example.sunnyweather.SunnyWeatherApplication;
import com.example.sunnyweather.logic.model.db.MyContentProvider;
import com.example.sunnyweather.logic.model.db.PlaceReaderContract;
import com.example.sunnyweather.logic.model.PlaceResponse;
import com.example.sunnyweather.logic.network.ILoadListener;
import com.example.sunnyweather.logic.network.place.IQueryListener;
import com.example.sunnyweather.utils.LogUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Repository {
    private static final String TAG = "Repository";
    private static Repository repository;
    private PlaceResponse mPlaceResponse;
    private ContentResolver resolver;
    private final static Uri CONTENT_URIS = Uri.parse("content://" +
            MyContentProvider.AUTHORITY + "/" +
            PlaceReaderContract.PlaceEntry.TABLE_NAME);

    private Repository() {
        resolver = SunnyWeatherApplication.getContext().getContentResolver();

    }

    public static Repository getInstance() {
        if (repository == null) {
            repository = new Repository();
        }
        return repository;
    }

    private final ILoadListener loadListener = new ILoadListener() {
        @Override
        public void success(PlaceResponse placeResponse) {
            mPlaceResponse = placeResponse;
        }

        @Override
        public void failed(String error) {
            LogUtils.e(TAG, "failed: " + error);
        }
    };

    public void searchPlaces(String query, IQueryListener iQueryListener) {
        LogUtils.d(TAG, "searchPlaces: query = " + query);
//        MutableLiveData<List<PlaceResponse.Place>> responseLiveData = new MutableLiveData<>();
        new Thread() {
            @Override
            public void run() {
                List<PlaceResponse.Place> placeList = new ArrayList<>();
                //数据库查询
                Cursor cursor = resolver.query(CONTENT_URIS, null, null, null, null);
                if (cursor != null) {
                    if (cursor.moveToFirst()) {
                        int[] index = new int[]{
                                cursor.getColumnIndex(PlaceReaderContract.PlaceEntry.COLUMN_NAME_LNG),
                                cursor.getColumnIndex(PlaceReaderContract.PlaceEntry.COLUMN_NAME_LAT),
                                cursor.getColumnIndex(PlaceReaderContract.PlaceEntry.COLUMN_NAME_PROVINCE),
                                cursor.getColumnIndex(PlaceReaderContract.PlaceEntry.COLUMN_NAME_CITY),
                                cursor.getColumnIndex(PlaceReaderContract.PlaceEntry.COLUMN_NAME_DISTRICT),
                                cursor.getColumnIndex(PlaceReaderContract.PlaceEntry.COLUMN_NAME_FORMATTED_ADDRESS),
                        };
                        do {
                            if (cursor.getString(index[3]).contains(query)
                                    || cursor.getString(index[4]).contains(query)) {//根据市级以及区级进行地址查询
                                LogUtils.d(TAG, "get Database");
                                PlaceResponse.Location location = new PlaceResponse.Location();
                                location.setLng(cursor.getString(index[0]));
                                location.setLat(cursor.getString(index[1]));
                                placeList.add(new PlaceResponse.Place(cursor.getString(index[2]), location, cursor.getString(index[5])));
                            }
                        } while (cursor.moveToNext());
                    }
                    cursor.close();
                }
                if (placeList.size() == 0) {
                    //数据库查询为空时进行文档查询
                    InputStreamReader is = null;
                    try {
                        is = new InputStreamReader(
                                SunnyWeatherApplication.getContext().getAssets().open("place_location.csv"));
                        BufferedReader bufferedReader = new BufferedReader(is);
                        bufferedReader.readLine();
                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            String[] placeInfos = line.split(",");
                            LogUtils.d(TAG, Arrays.toString(placeInfos));
                            if (placeInfos[4].contains(query)
                                    || placeInfos[5].contains(query)) {//根据市级以及区级进行地址查询
                                //将信息加入数据库缓存
                                ContentValues values = new ContentValues();
                                values.put(PlaceReaderContract.PlaceEntry.COLUMN_NAME_ENTRY_ID, placeInfos[0]);
                                values.put(PlaceReaderContract.PlaceEntry.COLUMN_NAME_LNG, placeInfos[1]);
                                values.put(PlaceReaderContract.PlaceEntry.COLUMN_NAME_LAT, placeInfos[2]);
                                values.put(PlaceReaderContract.PlaceEntry.COLUMN_NAME_PROVINCE, placeInfos[3]);
                                values.put(PlaceReaderContract.PlaceEntry.COLUMN_NAME_CITY, placeInfos[4]);
                                values.put(PlaceReaderContract.PlaceEntry.COLUMN_NAME_DISTRICT, placeInfos[5]);
                                String address;
                                if (placeInfos[4].equals(placeInfos[3])) {
                                    address = placeInfos[4] + ", " + placeInfos[5];
                                } else {
                                    address = placeInfos[3] + ", " + placeInfos[4] + ", " + placeInfos[5];
                                }
                                if (placeInfos[4].equals("") || placeInfos[5].equals("")){
                                    address = placeInfos[6];
                                }
                                LogUtils.d(TAG, "address = " + address.trim());
                                values.put(PlaceReaderContract.PlaceEntry.COLUMN_NAME_FORMATTED_ADDRESS, address.trim());
                                LogUtils.d(TAG, "insert");
                                resolver.insert(CONTENT_URIS, values);

                                PlaceResponse.Location location = new PlaceResponse.Location();
                                location.setLng(placeInfos[1]);
                                location.setLat(placeInfos[2]);
                                placeList.add(new PlaceResponse.Place(placeInfos[3], location, address.trim()));
                            }
                        }

                    } catch (IOException e) {
                        LogUtils.e(TAG, e.toString());
                    }
                }
                if (placeList.size() == 0) {
                    iQueryListener.failed("data is null");
                } else {
                    LogUtils.d(TAG, "size = " + placeList.size());
                    iQueryListener.success(placeList);

                }
            }
        }.start();

//        new Thread() {
//            @Override
//            public void run() {
//                SunnyWeatherNetworkUtil.getInstance().searchPlaces(query, loadListener);
//                if (mPlaceResponse.status.equals("ok")) {
//                    responseLiveData.setValue(mPlaceResponse.places);
//                } else {
//                    LogUtils.e(TAG, "response status is  " + mPlaceResponse.status);
//                }
//            }
//        }.start();
    }


}
