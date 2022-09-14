package com.hhj.codeclear_final.fragment.map;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.hhj.codeclear_final.R;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    // 구글맵
    private MapView map = null;

    private ImageButton btnPlus;
    private ImageButton btnMinus;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mapView = inflater.inflate(R.layout.fragment_map, container, false);

        // 맵 zoom 버튼
        btnPlus = mapView.findViewById(R.id.btnPlus);
        btnMinus = mapView.findViewById(R.id.btnMinus);

        // 구글맵 api
        map = mapView.findViewById(R.id.map);
        map.getMapAsync(this);

        return mapView;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        // 초기위치 서울로 지정
        defaultMyLocation(googleMap);

        // 내위치를 표시하는 기능 설정--GoogleMap자체적으로 현재위치 표시기능 활성화
        myLocation(googleMap);

        // 지도 zoomIn, Out 버튼
        mapZoomIn(googleMap);
        mapZoomOut(googleMap);

    }





    // 지도 zoomOut 버튼
    private void mapZoomOut(@NonNull GoogleMap googleMap) {
        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleMap.animateCamera(CameraUpdateFactory.zoomOut());
            }
        });
    }

    // 지도 zoomIn 버튼
    private void mapZoomIn(@NonNull GoogleMap googleMap) {
        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleMap.animateCamera(CameraUpdateFactory.zoomIn());
            }
        });
    }

    // 내위치를 표시하는 기능 설정--GoogleMap자체적으로 현재위치 표시기능 활성화
    private void myLocation(@NonNull GoogleMap googleMap) {
        try {
            googleMap.setMyLocationEnabled(true);
        }catch (SecurityException e){
            Log.w("TAG", "권한설정정보 참조");
        }
    }

    // 초기위치 서울로 지정
    private void defaultMyLocation(@NonNull GoogleMap googleMap) {
        LatLng seoul = new LatLng(37.56, 126.97);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(seoul, 10));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("TAG", "onActivityCreated:: 작동함");
        if (map != null) {
            map.onCreate(savedInstanceState);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("TAG", "onStart:: 작동함");
        map.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("TAG", "onResume:: 작동함");
        map.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("TAG", "onStop:: 작동함");
        map.onStop();
    }
}