package com.example.sunnyweather.ui.place;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.sunnyweather.R;
import com.example.sunnyweather.logic.model.PlaceResponse;
import com.example.sunnyweather.logic.model.RealTimeResponse;
import com.example.sunnyweather.logic.model.WeatherResponse;
import com.example.sunnyweather.ui.weather.RealTimeViewModel;
import com.example.sunnyweather.utils.LogUtils;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlaceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlaceFragment extends Fragment {

    private static final String TAG = "PlaceFragment";
    private PlaceViewModel placeViewModel;
    private RealTimeViewModel realTimeViewModel;
    private EditText searchPlaceEdit;
    private RecyclerView placeRecyclerView;
    private ImageView bgImageView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private PlaceAdapter adapter;

    public PlaceFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PlaceFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PlaceFragment newInstance(String param1, String param2) {
        PlaceFragment fragment = new PlaceFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_place, container, false);
        searchPlaceEdit = view.findViewById(R.id.search_place_edit);
        placeRecyclerView = view.findViewById(R.id.place_recyclerView);
        bgImageView = view.findViewById(R.id.bgImageView);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        LogUtils.d(TAG, "onActivityCreated");
        super.onActivityCreated(savedInstanceState);
        placeViewModel = new ViewModelProvider(this).get(PlaceViewModel.class);
        realTimeViewModel = new ViewModelProvider(this).get(RealTimeViewModel.class);
        final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        placeRecyclerView.setLayoutManager(layoutManager);
        adapter = new PlaceAdapter(this, placeViewModel.placeList);
        adapter.setOnItemClickListener(place -> {
            realTimeViewModel.searchPlaces(place.getLocation());
        });
        placeRecyclerView.setAdapter(adapter);
        initListener();
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

    }

    private void initListener() {
        searchPlaceEdit.addTextChangedListener(new TextWatcher() {
            private String query;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                query = s.toString().trim();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                final String content = s.toString().trim();
                if (content.length() >= 2 && !query.equals(content)) {
                    placeViewModel.searchPlaces(content);
                } else {
                    placeRecyclerView.setVisibility(View.GONE);
                    bgImageView.setVisibility(View.VISIBLE);
                    placeViewModel.placeList.clear();
                    adapter.notifyDataSetChanged();
                }
            }
        });
        placeViewModel.placeLiveData.observe(Objects.requireNonNull(this.getActivity()), result -> {
            if (result != null) {
                placeViewModel.placeList.clear();
                placeViewModel.placeList.addAll(result);
                if (placeViewModel.placeList.size() != 0) {
                    placeRecyclerView.setVisibility(View.VISIBLE);
                    bgImageView.setVisibility(View.GONE);
                } else {
                    Toast.makeText(PlaceFragment.this.getActivity(), "未能查询到任何地点", Toast.LENGTH_SHORT).show();
                }
                adapter.notifyDataSetChanged();
            } else {
                Toast.makeText(PlaceFragment.this.getActivity(), "未能查询到任何地点", Toast.LENGTH_SHORT).show();
            }
        });
        realTimeViewModel.realTimeLiveData.observe(Objects.requireNonNull(this.getActivity()), realTime -> {
            if (realTime != null) {
                LogUtils.d(TAG, " pressure = " + realTime.getPressure() + " temperature = " + realTime.getTemperature());
            } else {
                LogUtils.d(TAG, "未能查询到天气情报");
            }
        });
        realTimeViewModel.resultLiveData.observe(this.getActivity(), new Observer<WeatherResponse.Result>() {
            @Override
            public void onChanged(WeatherResponse.Result result) {
                if (result != null) {
                    LogUtils.d(TAG, "temp = " + result.getDailyResponse().getTemperatureList().get(0));
                } else {
                    LogUtils.d(TAG, "未能查询到未来天气情报");
                }
            }
        });

    }
}