package com.example.sunnyweather.logic;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import androidx.lifecycle.MutableLiveData;

import com.example.sunnyweather.SunnyWeatherApplication;
import com.example.sunnyweather.logic.model.RealTimeResponse;
import com.example.sunnyweather.logic.model.WeatherResponse;
import com.example.sunnyweather.logic.model.db.MyContentProvider;
import com.example.sunnyweather.logic.model.db.PlaceReaderContract;
import com.example.sunnyweather.logic.model.PlaceResponse;
import com.example.sunnyweather.logic.network.ILoadListener;
import com.example.sunnyweather.logic.network.place.IQueryListener;
import com.example.sunnyweather.logic.network.weather.WeatherNetwork;
import com.example.sunnyweather.utils.LogUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {
    private static final String TAG = "Repository";
    private static Repository repository;
    private PlaceResponse mPlaceResponse;
    private final ExecutorService singleThreadExecutor;
    private final CyclicBarrier cyclicBarrier;
    private final ContentResolver resolver;
    private final static Uri CONTENT_URIS = Uri.parse("content://" +
            MyContentProvider.AUTHORITY + "/" +
            PlaceReaderContract.PlaceEntry.TABLE_NAME);
    private final MutableLiveData<RealTimeResponse.RealTime> realTimeMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<WeatherResponse.Result> resultMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<PlaceResponse.Place>> placeListLiveData = new MutableLiveData<>();

    private Repository() {
        resolver = SunnyWeatherApplication.getContext().getContentResolver();
        singleThreadExecutor = Executors.newSingleThreadExecutor();
        cyclicBarrier = new CyclicBarrier(2);
    }

    public static Repository getInstance() {
        if (repository == null) {
            repository = new Repository();
        }
        return repository;
    }

    private final ILoadListener loadListener = new ILoadListener() {
        @Override
        public void success(Object response) {
            LogUtils.d(TAG, "response = " + response);
            if (response instanceof RealTimeResponse) {
                realTimeMutableLiveData.postValue(((RealTimeResponse) response).getResult().getRealTime());
            }
            if (response instanceof WeatherResponse) {
                resultMutableLiveData.postValue(((WeatherResponse) response).getResult());
            }
        }

        @Override
        public void failed(String error) {
            LogUtils.e(TAG, "failed: " + error);
        }
    };

    public MutableLiveData<List<PlaceResponse.Place>> searchPlaces(String query) {
        LogUtils.d(TAG, "searchPlaces: query = " + query);
        singleThreadExecutor.execute(() -> {
            List<PlaceResponse.Place> placeList = new ArrayList<>();
            //数据库查询
//            Cursor cursor = resolver.query(CONTENT_URIS, null, null, null, null);
//            if (cursor != null) {
//                if (cursor.moveToFirst()) {
//                    int[] index = new int[]{
//                            cursor.getColumnIndex(PlaceReaderContract.PlaceEntry.COLUMN_NAME_LNG),
//                            cursor.getColumnIndex(PlaceReaderContract.PlaceEntry.COLUMN_NAME_LAT),
//                            cursor.getColumnIndex(PlaceReaderContract.PlaceEntry.COLUMN_NAME_PROVINCE),
//                            cursor.getColumnIndex(PlaceReaderContract.PlaceEntry.COLUMN_NAME_CITY),
//                            cursor.getColumnIndex(PlaceReaderContract.PlaceEntry.COLUMN_NAME_DISTRICT),
//                            cursor.getColumnIndex(PlaceReaderContract.PlaceEntry.COLUMN_NAME_FORMATTED_ADDRESS),
//                    };
//                    do {
//                        if (cursor.getString(index[3]).contains(query)
//                                || cursor.getString(index[4]).contains(query)) {//根据市级以及区级进行地址查询
//                            LogUtils.d(TAG, "get Database");
//                            PlaceResponse.Location location = new PlaceResponse.Location();
//                            location.setLng(cursor.getString(index[0]));
//                            location.setLat(cursor.getString(index[1]));
//                            placeList.add(new PlaceResponse.Place(cursor.getString(index[2]), location, cursor.getString(index[5])));
//                        }
//                    } while (cursor.moveToNext());
//                }
//                cursor.close();
//            }
            if (placeList.size() == 0) {
                //数据库查询为空时进行文档查询
                InputStreamReader is;
                try {
                    is = new InputStreamReader(
                            SunnyWeatherApplication.getContext().getAssets().open("place_location.csv"));
                    BufferedReader bufferedReader = new BufferedReader(is);
                    bufferedReader.readLine();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        String[] placeInfos = line.split(",");
                        LogUtils.d(TAG, Arrays.toString(placeInfos));
                        String address;
                        if (placeInfos[4].equals(placeInfos[3])) {
                            address = placeInfos[4] + ", " + placeInfos[5];
                        } else {
                            address = placeInfos[3] + ", " + placeInfos[4] + ", " + placeInfos[5];
                        }
                        if (placeInfos[4].equals("") || placeInfos[5].equals(""))
                            address = placeInfos[placeInfos.length - 1];
//                        ContentValues values = new ContentValues();
//                        values.put(PlaceReaderContract.PlaceEntry.COLUMN_NAME_ENTRY_ID, placeInfos[0]);
//                        values.put(PlaceReaderContract.PlaceEntry.COLUMN_NAME_LNG, placeInfos[1]);
//                        values.put(PlaceReaderContract.PlaceEntry.COLUMN_NAME_LAT, placeInfos[2]);
//                        values.put(PlaceReaderContract.PlaceEntry.COLUMN_NAME_PROVINCE, placeInfos[3]);
//                        values.put(PlaceReaderContract.PlaceEntry.COLUMN_NAME_CITY, placeInfos[4]);
//                        values.put(PlaceReaderContract.PlaceEntry.COLUMN_NAME_DISTRICT, placeInfos[5]);
//                        LogUtils.d(TAG, "address = " + address.trim());
//                        values.put(PlaceReaderContract.PlaceEntry.COLUMN_NAME_FORMATTED_ADDRESS, address.trim());
//                        LogUtils.d(TAG, "insert");
//                        resolver.insert(CONTENT_URIS, values);
                        if (placeInfos[4].contains(query)
                                || placeInfos[5].contains(query)) {//根据市级以及区级进行地址查询
                            //将信息加入数据库缓存
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
            placeListLiveData.postValue(placeList);
        });

        return placeListLiveData;
    }

    public MutableLiveData<RealTimeResponse.RealTime> refreshWeather(String lng, String lat) {

        singleThreadExecutor.execute(() ->
                WeatherNetwork.getInstance().getRealtimeWeather(lng, lat, loadListener));
        return realTimeMutableLiveData;
    }

    public MutableLiveData<WeatherResponse.Result> getWeather(String lng, String lat) {

        singleThreadExecutor.execute(() ->
                WeatherNetwork.getInstance().getWeatherResponse(lng, lat, loadListener));
        return resultMutableLiveData;
    }

}
