package com.NativeTech.rehla.activities;

import android.graphics.Color;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.TransportMode;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.model.Route;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import com.NativeTech.rehla.R;

import static com.NativeTech.rehla.Utills.Constant.dLatSearch;
import static com.NativeTech.rehla.Utills.Constant.dLngSearch;
import static com.NativeTech.rehla.Utills.Constant.sLatSearch;
import static com.NativeTech.rehla.Utills.Constant.sLngSearch;

public class ViewOnMap extends FragmentActivity  implements
        OnMapReadyCallback, ActivityCompat.OnRequestPermissionsResultCallback, DirectionCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_on_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
        try {
            requestDirection(new LatLng(Double.parseDouble(sLatSearch),Double.parseDouble(sLngSearch))
                    ,new LatLng(Double.parseDouble(dLatSearch),Double.parseDouble(dLngSearch)));
        }
        catch (Exception e)
        {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDirectionSuccess(Direction direction, String rawBody) {
        //Snackbar.make(btnRequestDirection, "Success with status : " + direction.getStatus(), Snackbar.LENGTH_SHORT).show();
        //Toast.makeText(getApplicationContext(), "Success with status : " + direction.getStatus(), Toast.LENGTH_LONG).show();
        try
        {
            if (direction.isOK()) {
                Route route = direction.getRouteList().get(0);
                ArrayList<LatLng> directionPositionList = route.getLegList().get(0).getDirectionPoint();
                mMap.addPolyline(DirectionConverter.createPolyline(getApplicationContext(), directionPositionList, 5, Color.BLUE));
                setCameraWithCoordinationBounds(route);
            /*Leg leg = route.getLegList().get(0);
            Info distanceInfo = leg.getDistance();
            Info durationInfo = leg.getDuration();
            String distance = distanceInfo.getText();
            String duration = durationInfo.getText();
            String sourceName = leg.getStartAddress();
            String destinationName = leg.getEndAddress();
*/
            /*publicmarkerOptions.snippet("Distance = "+distance+"\n"+"Duration = "+duration);
            //Toast.makeText(getApplicationContext(), distance+"-"+duration+"-"+sourceName+"-"+destinationName, Toast.LENGTH_LONG).show();
            map.addMarker(publicmarkerOptions).showInfoWindow();
*/

                //btnRequestDirection.setVisibility(View.GONE);
            } else {
                Toast.makeText(getApplicationContext(), direction.getStatus(), Toast.LENGTH_LONG).show();
            }
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
    private void setCameraWithCoordinationBounds(Route route) {
        LatLng southwest = route.getBound().getSouthwestCoordination().getCoordination();
        MarkerOptions options = new MarkerOptions();

        // Setting the position of the marker
        //options.position(map.getCameraPosition().target);
        try
        {
            options.icon(BitmapDescriptorFactory.fromResource(R.drawable.pickup))
                    .draggable(true);
            //map.addMarker(options).showInfoWindow();
            mMap.addMarker(options.position(new LatLng(Double.parseDouble(sLatSearch),Double.parseDouble(sLngSearch))));

            options.icon(BitmapDescriptorFactory.fromResource(R.drawable.distination_location))
                    .draggable(true);
            LatLng northeast = route.getBound().getNortheastCoordination().getCoordination();
            //map.addMarker(options.position(new LatLng(29.292163,31.847731)));
            mMap.addMarker(options.position(new LatLng(Double.parseDouble(dLatSearch),Double.parseDouble(dLngSearch))));
            LatLngBounds bounds = new LatLngBounds(southwest, northeast);
            mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    public void onDirectionFailure(Throwable t) {
        //Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
    }

    private void requestDirection(LatLng origin, LatLng destination) {
        //Snackbar.make(btnRequestDirection, "Direction Requesting...", Snackbar.LENGTH_SHORT).show();
        String serverKey = getString(R.string.google_api_key);
        GoogleDirection.withServerKey(serverKey)
                .from(origin)
                .to(destination)
                .transportMode(TransportMode.DRIVING)
                .execute(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera`
        /*LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/
    }

}
