package com.sample.ui.google;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sample.ui.R;


public class GoogleMapActivity extends AppCompatActivity implements OnMapReadyCallback {

    public static final int REQUEST_PERMISSION = 1300;

    private GoogleMap m_GoogleMap;
    private LocationManager m_LocationManager = null;
    private ProgressDialog m_waitDialog;
    private Location m_MyLocation = null;
    private Location m_SetLocation = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the layout file as the content view.
        setContentView(R.layout.activity_googlemap);

        // Get a handle to the fragment and register the callback.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        m_LocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            showPerMissionRequest();
            return;
        }
        m_LocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, m_LocationListener_GPS);
        m_LocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, m_LocationListener_WIFI);
        m_waitDialog = new ProgressDialog(GoogleMapActivity.this);
        m_waitDialog.setMessage("定位中");
        m_waitDialog.show();
    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        m_GoogleMap = googleMap;
        m_GoogleMap.getUiSettings().setZoomControlsEnabled(false);    //取消zoom icon
        m_GoogleMap.getUiSettings().setCompassEnabled(false);    //取消指南針
        m_GoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        m_GoogleMap.clear();


    }

    private LocationListener m_LocationListener_GPS = new LocationListener() {
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onProviderDisabled(String provider) {
        }

        @Override
        public void onLocationChanged(Location location) {
            m_waitDialog.dismiss();
            m_MyLocation = location;
            setLocation();
        }
    };

    private LocationListener m_LocationListener_WIFI = new LocationListener() {
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onProviderDisabled(String provider) {
        }

        @Override
        public void onLocationChanged(Location location) {
            m_waitDialog.dismiss();
            m_MyLocation = location;
            setLocation();
        }
    };

    private void setLocation() {
        if (null == m_GoogleMap) return;
        LatLng latLng = new LatLng(m_MyLocation.getLatitude(), m_MyLocation.getLongitude());
        m_GoogleMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title("Marker"));
        m_GoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 11.0f));
    }

    @Override
    protected void onDestroy() {
        m_LocationManager.removeUpdates(m_LocationListener_GPS);
        m_LocationManager.removeUpdates(m_LocationListener_WIFI);
        super.onDestroy();
    }

    private void showPerMissionRequest() {
        new AlertDialog.Builder(GoogleMapActivity.this).setTitle("權限請求").setMessage("App將需要取得手機系資訊及定位資訊\n請允許App取得權限").setNegativeButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION,
                    }, REQUEST_PERMISSION);
                }
            }
        }).setNeutralButton("否", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        }).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION) {
            boolean pass = false;
            for (int i = 0; i < grantResults.length; ++i) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    pass = true;
                } else {
                    pass = false;
                    i = grantResults.length + 1;
                }
            }

            if (pass) {
                //service start
                m_LocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, m_LocationListener_GPS);
                m_LocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, m_LocationListener_WIFI);
                m_waitDialog = new ProgressDialog(GoogleMapActivity.this);
                m_waitDialog.setMessage("定位中");
                m_waitDialog.show();

            } else {
                // 授权请求被拒绝
                showPerMissionRequest();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}