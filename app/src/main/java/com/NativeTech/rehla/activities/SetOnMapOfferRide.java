package com.NativeTech.rehla.activities;

import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.Language;
import com.akexorcist.googledirection.constant.TransportMode;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.model.Info;
import com.akexorcist.googledirection.model.Leg;
import com.akexorcist.googledirection.model.Route;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.List;
import java.util.Locale;

import com.NativeTech.rehla.R;
import com.NativeTech.rehla.Utills.Constant;

import rx.subscriptions.CompositeSubscription;

public class SetOnMapOfferRide extends FragmentActivity implements DirectionCallback
        ,GoogleMap.OnCameraMoveStartedListener, GoogleMap.OnCameraMoveListener
        , GoogleMap.OnCameraMoveCanceledListener, GoogleMap.OnCameraIdleListener
        ,OnMapReadyCallback {

    private GoogleMap mMap;

    private TextView locationMarkertext;
    private LatLng latLng;

    private String Lan;
    private SharedPreferences mSharedPreferences;
    private KProgressHUD hud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_on_map_offer_ride);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        mSharedPreferences  =   getSharedPreferences("tokenDetail",MODE_PRIVATE);
        Lan                 =   mSharedPreferences.getString(Constant.language, Locale.getDefault().getLanguage());
        CompositeSubscription mSubscriptions = new CompositeSubscription();
        hud = KProgressHUD.create(SetOnMapOfferRide.this)
                .setStyle(KProgressHUD.Style.PIE_DETERMINATE)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .setCancellable(false)
                .setMaxProgress(100);

        Button done_marker = findViewById(R.id.doneMarker);
        locationMarkertext  = findViewById(R.id.locationMarkertextAddress);
        try
        {
            if (Constant.status.equals("from"))
            {
                latLng=new LatLng(Double.parseDouble(Constant.sLat),Double.parseDouble(Constant.sLng));
            }
            else if (Constant.status.equals("to"))
            {
                latLng=new LatLng(Double.parseDouble(Constant.dLat),Double.parseDouble(Constant.dLng));
            }
        }
        catch (Exception e)
        {
            //Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }


        done_marker.setOnClickListener(v -> {
            if (Constant.status.equals("from"))
            {
                Constant.sLat= String.valueOf(latLng.latitude);
                Constant.sLng= String.valueOf(latLng.longitude);
                if (!Constant.dLat.equals(""))
                {
                 if (!Constant.dLng.equals(""))
                 {
                     requestDirection(new LatLng(Double.parseDouble(Constant.sLat),Double.parseDouble(Constant.sLng))
                             ,new LatLng(Double.parseDouble(Constant.dLat),Double.parseDouble(Constant.dLng)));
                     //Toast.makeText(this, "full from from", Toast.LENGTH_SHORT).show();
                 }
                }
                else
                {
                    finish();
                }
            }
            else if (Constant.status.equals("to"))
            {
                Constant.dLat= String.valueOf(latLng.latitude);
                Constant.dLng= String.valueOf(latLng.longitude);
                if (!Constant.sLat.equals(""))
                {
                    if (!Constant.sLng.equals(""))
                    {
                        requestDirection(new LatLng(Double.parseDouble(Constant.sLat),Double.parseDouble(Constant.sLng))
                                ,new LatLng(Double.parseDouble(Constant.dLat),Double.parseDouble(Constant.dLng)));
                        //Toast.makeText(this, "full from to", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    finish();
                }
            }
            //finish();
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnCameraIdleListener(this);
        mMap.setOnCameraMoveStartedListener(this);
        mMap.setOnCameraMoveListener(this);
        mMap.setOnCameraMoveCanceledListener(this);

        if (Constant.status.equals("from"))
        {
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(Double.parseDouble(Constant.sLat),Double.parseDouble(Constant.sLng))) // Sets the center of the map to Maracanã
                    .bearing(300) // Sets the orientation of the camera to look west
                    .tilt(50) // Sets the tilt of the camera to 30 degrees
                    .zoom(18)
                    .build(); // Creates a CameraPosition from the builder

            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), 3000, null);
            if (Lan.equals("ar"))
            {
                locationMarkertext.setText(getAddressAr(Double.parseDouble(Constant.sLat),Double.parseDouble(Constant.sLng)));
            }
            else
            {
                locationMarkertext.setText(getAddress(Double.parseDouble(Constant.sLat),Double.parseDouble(Constant.sLng)));
            }
        }
        else if (Constant.status.equals("to"))
        {
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(Double.parseDouble(Constant.dLat),Double.parseDouble(Constant.dLng))) // Sets the center of the map to Maracanã
                    .bearing(300) // Sets the orientation of the camera to look west
                    .tilt(50) // Sets the tilt of the camera to 30 degrees
                    .zoom(18)
                    .build(); // Creates a CameraPosition from the builder

            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), 3000, null);
            if (Lan.equals("ar"))
            {
                locationMarkertext.setText(getAddressAr(Double.parseDouble(Constant.dLat),Double.parseDouble(Constant.dLng)));
            }
            else
            {
                locationMarkertext.setText(getAddress(Double.parseDouble(Constant.dLat),Double.parseDouble(Constant.dLng)));
            }
        }
    }
    private String getAddress(double lat, double lng) {
        Geocoder geocoder = null;
        Locale loc = new Locale("en");
        geocoder = new Geocoder(getApplicationContext(),loc);
        String add="";
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            Address obj = addresses.get(0);
            add = obj.getAddressLine(0);
            add=add.replaceAll("\\d","");
            add=add.replaceAll("Unnamed Road","");
            add=add.replaceAll("المملكة العربية","");
        } catch (Exception e) {
            // TODO Auto-generated catch block
           // Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return add;
    }
    private String getAddressAr(double lat, double lng) {
        Geocoder geocoder = null;
        Locale loc = new Locale("ar");
        geocoder = new Geocoder(getApplicationContext(),loc);
        String add="";
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            Address obj = addresses.get(0);
            add = obj.getAddressLine(0);
            add=add.replaceAll("\\d","");
            add=add.replaceAll("Unnamed Road","");
            add=add.replaceAll("المملكة العربية","");
        } catch (Exception e) {
            // TODO Auto-generated catch block
           // Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return add;
    }

    @Override
    public void onCameraIdle() {
        latLng =mMap.getCameraPosition().target;
        if (Lan.equals("ar"))
        {
            locationMarkertext.setText(getAddressAr(latLng.latitude,latLng.longitude));
        }
        else
        {
            locationMarkertext.setText(getAddress(latLng.latitude,latLng.longitude));
        }
    }

    @Override
    public void onCameraMoveCanceled() {

    }

    @Override
    public void onCameraMove() {

    }

    @Override
    public void onCameraMoveStarted(int i) {
        if (Lan.equals("ar"))
        {
            locationMarkertext.setText("تحميل..");
        }
        else
        {
            locationMarkertext.setText("loading..");
        }
    }

    private void requestDirection(LatLng origin, LatLng destination) {
        String serverKey = getString(R.string.google_maps_key);
        GoogleDirection.withServerKey(serverKey)
                .from(origin)
                .to(destination)
                .language(Language.ENGLISH)
                .transportMode(TransportMode.DRIVING)
                .execute(this);
    }

    @Override
    public void onDirectionSuccess(Direction direction, String rawBody) {
        if (direction.isOK()) {
            Route route = direction.getRouteList().get(0);
            Leg leg = route.getLegList().get(0);
            Info distanceInfo = leg.getDistance();
            Info durationInfo = leg.getDuration();
            Constant.distance = distanceInfo.getValue();
            Constant.time = durationInfo.getValue();
            /*Toast.makeText(this,distanceInfo.getText()+"" , Toast.LENGTH_SHORT).show();
            Toast.makeText(this,distanceInfo.getValue()+"" , Toast.LENGTH_SHORT).show();
            Toast.makeText(this,durationInfo.getText()+"" , Toast.LENGTH_SHORT).show();
            Toast.makeText(this,durationInfo.getValue()+"" , Toast.LENGTH_SHORT).show();*/
            finish();

        } else {
            //Toast.makeText(getApplicationContext(), direction.getStatus(), Toast.LENGTH_LONG).show();
            //Toast.makeText(getApplicationContext(), direction.getErrorMessage(), Toast.LENGTH_LONG).show();
        }
    }



    @Override
    public void onDirectionFailure(Throwable t) {

    }

    public void onBackPressed() {

        finish();
    }
}
