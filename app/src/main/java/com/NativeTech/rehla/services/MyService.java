package com.NativeTech.rehla.services;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.IBinder;
import android.os.Looper;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.NativeTech.rehla.model.DataManager;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import org.json.JSONObject;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

import com.NativeTech.rehla.model.data.dto.Models.WaitingTrips.addWaitingTripsResponse;
import com.NativeTech.rehla.Network.NetworkUtil;
import com.NativeTech.rehla.R;
import com.NativeTech.rehla.Utills.Constant;
import com.NativeTech.rehla.Utills.Validation;

import retrofit2.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static com.NativeTech.rehla.Utills.Constant.buildDialog;
import static com.NativeTech.rehla.Utills.Constant.currentDateAddLocation;
import static com.NativeTech.rehla.Utills.Constant.tripIdCaptinAddLocation;

public class MyService extends Service {

    private static final String TAG = "BOOMBOOMTESTGPS";

    private String Language;
    private SharedPreferences mSharedPreferences;
    private CompositeSubscription mSubscriptions;

    private LocationRequest mLocationRequest;
    private Location mLastLocation;
    private FusedLocationProviderClient client;



    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand");
        //super.onStartCommand(intent, flags, startId);
        mSharedPreferences = getSharedPreferences("tokenDetail", MODE_PRIVATE);
        Language = mSharedPreferences.getString(Constant.language, Locale.getDefault().getLanguage());
        mSubscriptions      =   new CompositeSubscription();
        //Toast.makeText(MyService.this, "ok2", Toast.LENGTH_SHORT).show();
        getMyLocation();
        //checkLocationPermission();
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        Log.e(TAG, "onCreate");
        //Toast.makeText(MyService.this, "ok1", Toast.LENGTH_SHORT).show();
        //getMyLocation();

    }

    private void getMyLocation() {


        //checkLocationPermission();
        //requestLocationPermissions();


            client = LocationServices.getFusedLocationProviderClient(getApplicationContext());

            mLocationRequest = new LocationRequest();
            mLocationRequest.setInterval(5000); // two minute interval
            mLocationRequest.setFastestInterval(1200);
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);


            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    //Location Permission already granted

                    client.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
                    // mGoogleMap.setMyLocationEnabled(true);
                }
            }
            else {
                client.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
                //mGoogleMap.setMyLocationEnabled(true);
            }
    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;


    @SuppressLint("SimpleDateFormat")
    private void addLocationRequest(){
        if (Validation.isConnected(getApplicationContext()))
        {

            mSubscriptions.add(NetworkUtil.getRetrofitByToken(DataManager.getInstance().getCashedAccessToken().getAccess_token())
                    .addCurrentLocation(
                            tripIdCaptinAddLocation
                            , String.valueOf(mLastLocation.getLatitude())
                            , String.valueOf(mLastLocation.getLongitude())
                            ,currentDateAddLocation)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResponse, this::handleError));
        }
        else {
            buildDialog((Activity) getApplicationContext()).show().setCanceledOnTouchOutside(false);
        }
    }
    private void handleError(Throwable throwable) {
        String message="";
        try {
            if (throwable instanceof HttpException) {
                HttpException error = (HttpException) throwable;
                JSONObject jsonObject = new JSONObject(Objects.requireNonNull(((HttpException) throwable).response().errorBody()).string());

                String code = jsonObject.getJSONObject("errors").getString("Code");

                switch (code) {
                    case "1":
                        message = getApplicationContext().getString(R.string.error_1);
                        break;
                    case "2":
                        message = getApplicationContext().getString(R.string.error_2);
                        break;
                    case "3":
                        message = getApplicationContext().getString(R.string.error_3);
                        break;
                    case "4":
                        message = getApplicationContext().getString(R.string.error_4);
                        break;
                    case "5":
                        message = getApplicationContext().getString(R.string.error_5);
                        break;
                    case "6":
                        message = getApplicationContext().getString(R.string.error_6);
                        break;
                    case "7":
                        message = getApplicationContext().getString(R.string.error_7);
                        break;
                    case "8":
                        message = getApplicationContext().getString(R.string.error_8);
                        break;
                    case "9":
                        message = getApplicationContext().getString(R.string.error_9);
                        break;
                    case "10":
                        message = getApplicationContext().getString(R.string.error_10);
                        break;
                    case "11":
                        message = getApplicationContext().getString(R.string.error_11);
                        break;
                    case "12":
                        message = getApplicationContext().getString(R.string.error_12);
                        break;
                    case "13":
                        message = getApplicationContext().getString(R.string.error_13);
                        break;
                    case "14":
                        message = getApplicationContext().getString(R.string.error_14);
                        break;
                    case "15":
                        message = getApplicationContext().getString(R.string.error_15);
                        break;
                    case "16":
                        message = getApplicationContext().getString(R.string.error_16);
                        break;
                    case "17":
                        message = getApplicationContext().getString(R.string.error_17);
                        break;
                    case "18":
                        message = getApplicationContext().getString(R.string.error_18);
                        break;
                    case "19":
                        message = getApplicationContext().getString(R.string.error_19);
                        break;
                    case "20":
                        message = getApplicationContext().getString(R.string.error_20);
                        break;
                    case "21":
                        message = getApplicationContext().getString(R.string.error_21);
                        break;
                    case "22":
                        message = getApplicationContext().getString(R.string.error_22);
                        break;
                    case "23":
                        message = getApplicationContext().getString(R.string.error_23);
                        break;
                    case "24":
                        message = getApplicationContext().getString(R.string.error_24);
                        break;
                    case "25":
                        message = getApplicationContext().getString(R.string.error_25);
                        break;
                    case "26":
                        message = getApplicationContext().getString(R.string.error_26);
                        break;
                    case "27":
                        message = "Wasl Error";
                        break;
                    case "28":
                        message = "Wasl Error";
                        break;
                    case "29":
                        message = getApplicationContext().getString(R.string.error_29);
                        break;
                    case "30":
                        message = getApplicationContext().getString(R.string.error_30);
                        break;
                    case "31":
                        message = getApplicationContext().getString(R.string.error_31);
                        break;

                    default:
                        message = getApplicationContext().getString(R.string.default_message);
                }
            }
            else
            {
                //Toast.makeText(AcceptOrRejectTrip.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                message = getString(R.string.default_message);
            }

        } catch (Exception ex) {
            //view.onError(e.getMessage());
            message = getString(R.string.default_message);
        }
        //Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        /*android.support.v7.app.AlertDialog.Builder builder;
        android.support.v7.app.AlertDialog dialog;

        builder = new android.support.v7.app.AlertDialog.Builder();
        @SuppressLint("InflateParams")
        View mview = ((Activity)getApplicationContext()).getLayoutInflater().inflate(R.layout.dialog_error, null);

        builder.setView(mview);
        dialog = builder.create();
        Window window = dialog.getWindow();
        if (window != null) {
            window.setGravity(Gravity.CENTER);
        }
        dialog.show();
        TextView txt = mview.findViewById(R.id.txt);
        TextView ok = mview.findViewById(R.id.ok);
        txt.setText(message);
        ok.setOnClickListener(v2 -> {
            dialog.dismiss();
        });*/
       // hud.dismiss();
    }

    private void handleResponse(addWaitingTripsResponse addWaitingTripsResponse)
    {
        if (addWaitingTripsResponse.getMetas().getMessage()!=null)
        {
          //Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_SHORT).show();
        }

    }
    private final LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            List<Location> locationList = locationResult.getLocations();
            if (locationList.size() > 0) {
                //The last location in the list is the newest
                //Log.i("MapsActivity", "Location: " + location.getLatitude() + " " + location.getLongitude());
                mLastLocation = locationList.get(locationList.size() - 1);
               // Toast.makeText(MyService.this, "ok2", Toast.LENGTH_SHORT).show();

/*

                Toast.makeText(getApplicationContext(), String.valueOf(location.getLatitude())
                        + " , " +
                        String.valueOf(location.getLongitude()), Toast.LENGTH_LONG).show();
*/

                addLocationRequest();
            }
        }
    };

    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy");
        if (client != null) {
            client.removeLocationUpdates(mLocationCallback);
        }
        super.onDestroy();
    }
}