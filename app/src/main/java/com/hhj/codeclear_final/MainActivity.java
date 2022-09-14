package com.hhj.codeclear_final;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.tabs.TabLayout;
import com.hhj.codeclear_final.databinding.ActivityMainBinding;
import com.hhj.codeclear_final.fragment.home.HomeFragment;
import com.hhj.codeclear_final.fragment.map.MapFragment;
import com.hhj.codeclear_final.fragment.myPage.MyPageFragment;
import com.hhj.codeclear_final.fragment.selfCheck.SelfCheckFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private long backKeyPressedTime = 0;
    private Toast toast;
    // 뷰 바인딩
    private ActivityMainBinding binding;

    // 프래그먼트
    private HomeFragment homeFragment;
    private SelfCheckFragment selfCheckFragment;
    private MapFragment mapFragment;
    private MyPageFragment myPageFragment;

    private static final int GPS_ENABLE_REQUEST_CODE = 2001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 프래그먼트 초기화 및 초기화면설정
        initFragment();

        // 탭에 이미지 추가
        tabIconAdd();

        // 탭 선택시 프래그먼트 이동
        tabSelectListener();

        //퍼미션 권한
        checkDangerousPermissions();

    }

    // 프래그먼트 초기화
    private void initFragment() {
        homeFragment = new HomeFragment();
        selfCheckFragment = new SelfCheckFragment();
        mapFragment = new MapFragment();
        myPageFragment = new MyPageFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, homeFragment).commit();
    }

    // 탭 아이콘 설정
    private void tabIconAdd() {
        ArrayList<Integer> image = new ArrayList<Integer>();
        image.add(R.drawable.main_menu_1);
        image.add(R.drawable.main_menu_2);
        image.add(R.drawable.main_menu_3);
        image.add(R.drawable.main_menu_4);

        for (int i = 0; i < image.size(); i++) {
            binding.tabLayout.getTabAt(i).setIcon(image.get(i));
        }
    }

    // 탭 선택시 프래그먼트 이동
    private void tabSelectListener() {
        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, homeFragment).commit();
                        break;
                    case 1:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, selfCheckFragment).commit();
                        break;
                    case 2:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, mapFragment).commit();
                        break;
                    case 3:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, myPageFragment).commit();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    // 뒤로가기 버튼
    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + 2500) {
            backKeyPressedTime = System.currentTimeMillis();
            toast = Toast.makeText(this, "뒤로 가기 버튼을 한 번 더 누르시면 종료됩니다.", Toast.LENGTH_LONG);
            toast.show();
            return;
        }
        // 마지막으로 뒤로 가기 버튼을 눌렀던 시간에 2.5초를 더해 현재 시간과 비교 후
        // 마지막으로 뒤로 가기 버튼을 눌렀던 시간이 2.5초가 지나지 않았으면 종료
        if (System.currentTimeMillis() <= backKeyPressedTime + 2500) {
            ActivityCompat.finishAffinity(this);
        }
    }

    private void checkDangerousPermissions() {

        if (!checkLocationServicesStatus()) {
            showDialogForLocationServiceSetting();
        }

        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.INTERNET)) {
            Toast.makeText(this, "ACCESS_FINE_LOCATION  권한 설명 필요함.", Toast.LENGTH_LONG).show();
        } else {
            String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION};

            ActivityCompat.requestPermissions(this, permissions, 1);
        }

        permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.INTERNET)) {
            Toast.makeText(this, "ACCESS_COARSE_LOCATION  권한 설명 필요함.", Toast.LENGTH_LONG).show();
        } else {
            String[] permissions = {Manifest.permission.ACCESS_COARSE_LOCATION};

            ActivityCompat.requestPermissions(this, permissions, 2);
        }

        permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET);
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.INTERNET)) {
            Toast.makeText(this, "INTERNET 권한 설명 필요함.", Toast.LENGTH_LONG).show();
        } else {
            String[] permissions = {Manifest.permission.INTERNET};

            ActivityCompat.requestPermissions(this, permissions, 3);
        }
    }

    public boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    //여기부터는 GPS 활성화를 위한 메소드들
    private void showDialogForLocationServiceSetting() {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("위치 서비스 비활성화");
        builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.");
        builder.setCancelable(true);
        builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent callGPSSettingIntent
                        = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }
}