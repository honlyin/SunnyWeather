package com.example.sunnyweather.logic.model.db;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.example.sunnyweather.utils.LogUtils;

import java.io.File;

import static com.example.sunnyweather.logic.model.db.PlaceReaderContract.PlaceEntry.TABLE_NAME;

public class MyContentProvider extends ContentProvider {

    private static final String TAG = "MyContentProvider";
    public static final String AUTHORITY = "com.example.sunnyweather.MyContentProvider";
    private SQLiteDatabase database;
    private PlaceDBHelper placeDBHelper;
    public final static int ALL_PLACES = 100; //当uri匹配是整个表格时候得返回值
    public final static int ONE_PLACE = 1;//当uri匹配是整个表格的某一条目时候返回值
    public final static String DEFAULT_SORT_ORDER = PlaceReaderContract.PlaceEntry._ID + " desc";//定义默认排序


    private static final UriMatcher sUriMatcher;

    static {

        //初始化匹配器
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        //  为friends表添加匹配的uri，当匹配时返回后面的整数值
        sUriMatcher.addURI(AUTHORITY, File.separator + TABLE_NAME, ALL_PLACES);
        sUriMatcher.addURI(AUTHORITY, File.separator + TABLE_NAME + "/#", ONE_PLACE);

    }

    public MyContentProvider() {
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        placeDBHelper = new PlaceDBHelper(getContext());

        return placeDBHelper == null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        database = placeDBHelper.getWritableDatabase();
        int count = 0;
        if (sUriMatcher.match(uri) == ALL_PLACES) {
            count = database.delete(TABLE_NAME, selection, selectionArgs);
        } else {
            throw new IllegalArgumentException("Unkwon Uri:" + uri.toString());
        }
        return count;
    }

    @Override
    public String getType(Uri uri) {
        switch (sUriMatcher.match(uri)) {
            case ALL_PLACES:
                return "vnd.android.cursor.dir/Information";
            case ONE_PLACE:
                return "vnd.android.cursor.item/Information";
            default:
                throw new IllegalArgumentException("Unkwon Uri:" + uri.toString());
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        database = placeDBHelper.getWritableDatabase();
        Uri insertUri = null;
        long rowid = 0;
        switch (sUriMatcher.match(uri)) {
            case ALL_PLACES:
                rowid = database.insert(TABLE_NAME, PlaceReaderContract.PlaceEntry.COLUMN_NAME_ENTRY_ID, values);
                insertUri = ContentUris.withAppendedId(uri, rowid);
                LogUtils.i(TAG, "insert record...values:" + values.toString());
                break;
            default:
                throw new IllegalArgumentException("Unkwon Uri:" + uri.toString());
        }
        return insertUri;
    }


    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        database = placeDBHelper.getWritableDatabase();
        switch (sUriMatcher.match(uri)) {
            case ALL_PLACES:
                return database.query(TABLE_NAME, projection, selection,
                        selectionArgs, null, null, sortOrder);
            case ONE_PLACE://条件查询，
                long id = ContentUris.parseId(uri);
                String where = PlaceReaderContract.PlaceEntry.COLUMN_NAME_ENTRY_ID + "=" + id;
                if (selection != null && !"".equals(selection)) {
                    where = where + " and " + selection;
                }
                return database.query(TABLE_NAME, projection, where, selectionArgs, null,
                        null, sortOrder);
            default:
                throw new IllegalArgumentException("unknow uri" + uri.toString());
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO Auto-generated method stub
        database = placeDBHelper.getWritableDatabase();
        int count = 0;
        if (sUriMatcher.match(uri) == ALL_PLACES) {
            count = database.update(TABLE_NAME, values, selection, selectionArgs);
            return count;
        }
        throw new IllegalArgumentException("unknow uri" + uri.toString());
    }
}