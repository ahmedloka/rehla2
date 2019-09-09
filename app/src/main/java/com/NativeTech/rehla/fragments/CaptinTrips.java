package com.NativeTech.rehla.fragments;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.NativeTech.rehla.model.DataManager;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import com.NativeTech.rehla.model.data.dto.Models.Driver.getCurrentTripResponse;
import com.NativeTech.rehla.Network.NetworkUtil;
import com.NativeTech.rehla.Utills.Constant;
import com.NativeTech.rehla.R;
import com.NativeTech.rehla.Utills.Validation;
import com.NativeTech.rehla.activities.Home;
import com.NativeTech.rehla.adapters.CaptinTripsHistoryRecyclerModel;
import com.NativeTech.rehla.adapters.CaptinTripsRecyclerModel;
import com.NativeTech.rehla.adapters.RecyclerViewAdapterCaptinTrips;
import com.NativeTech.rehla.adapters.RecyclerViewAdapterCaptinTripsHistory;

import retrofit2.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.content.Context.MODE_PRIVATE;
import static com.NativeTech.rehla.Utills.Constant.TripStatus;
import static com.NativeTech.rehla.Utills.Constant.buildDialog;
import static com.NativeTech.rehla.Utills.Constant.currentDateAddLocation;
import static com.NativeTech.rehla.Utills.Constant.servvice;
import static com.NativeTech.rehla.Utills.Constant.tripIdCaptinAddLocation;
import static com.NativeTech.rehla.services.MyService.MY_PERMISSIONS_REQUEST_LOCATION;


public class CaptinTrips extends Fragment implements  ActivityCompat.OnRequestPermissionsResultCallback{


    private TabLayout tabLayout;
    private final int[] navLabels = {

            R.string.upcomming_rides,
            R.string.history_rides
    };
    private LinearLayoutCompat upComing_rides_lin;
    private LinearLayoutCompat history_rides_lin;


    private RecyclerView recyclerViewHistory;
    private List<CaptinTripsHistoryRecyclerModel> rowItem;

    private RecyclerView recyclerViewWaitingTrip;
    private List<CaptinTripsRecyclerModel> rowItemWaitingTrip;

    private String Language;
    private CompositeSubscription mSubscriptions;
    private SharedPreferences mSharedPreferences;
   private KProgressHUD hud;

    private TextView no_exist;

    android.support.v7.app.AlertDialog dialog;

    private Home home;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_captin_trips, container, false);
        home=(Home)getActivity();
        tabLayout                               = view.findViewById(R.id.tab_category);
        upComing_rides_lin                      = view.findViewById(R.id.upComing_rides_lin);
        history_rides_lin                       = view.findViewById(R.id.history_rides_lin);
        recyclerViewHistory                     = view.findViewById(R.id.recyclerViewHistory);
        recyclerViewWaitingTrip                 = view.findViewById(R.id.recyclerViewWaitingTrips);
        rowItemWaitingTrip                      = new ArrayList<>();
        rowItem                                 = new ArrayList<>();
        no_exist                                = view.findViewById(R.id.no_exist);

        no_exist.setVisibility(View.GONE);

        mSharedPreferences  =   Objects.requireNonNull(getActivity()).getSharedPreferences("tokenDetail",MODE_PRIVATE);
        Language            =   mSharedPreferences.getString(Constant.language,"en");
        mSubscriptions      =   new CompositeSubscription();
        hud = KProgressHUD.create(getActivity())
                .setStyle(KProgressHUD.Style.PIE_DETERMINATE)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .setCancellable(false)
                .setMaxProgress(100);


        //initRecyclerViewWaitingTrips();

        getCurrentTripResponse();

        initionilizationOFTabs();


        SpannableString content = new SpannableString(getResources().getString(navLabels[0]));
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        content.setSpan(new StyleSpan(Typeface.BOLD), 0, content.length(), 0);


       // Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "Cairo-Regular.ttf");
        //TabLayout.setTypeface(tf);


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:

                        upComing_rides_lin.setVisibility(View.VISIBLE);
                        history_rides_lin.setVisibility(View.GONE);
                        no_exist.setVisibility(View.GONE);
                        getCurrentTripResponse();
                        break;

                    case 1:
                        upComing_rides_lin.setVisibility(View.GONE);
                        history_rides_lin.setVisibility(View.VISIBLE);
                        no_exist.setVisibility(View.GONE);
                        getDriverPreviousTrip();
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
        TabLayout.Tab tab = tabLayout.getTabAt(0);
       // initRecyclerViewWaitingTrips();
        return view;
    }

    private void getCurrentTripResponse()
    {
        if (Validation.isConnected(Objects.requireNonNull(getContext())))
        {
           hud.show();
            mSubscriptions.add(NetworkUtil.getRetrofitByToken(DataManager.getInstance().getCashedAccessToken().getAccess_token())
                    .getDriverCurrentTrip("0")
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResponse, this::handleError));
        }
        else {
            buildDialog((Activity) getContext()).show().setCanceledOnTouchOutside(false);
        }
    }
    private void getDriverPreviousTrip()
    {
        if (Validation.isConnected(Objects.requireNonNull(getActivity())))
        {
            hud.show();
            mSubscriptions.add(NetworkUtil.getRetrofitByToken(DataManager.getInstance().getCashedAccessToken().getAccess_token())
                    .getDriverPreviousTrip("0")
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResponsegetDriverPreviousTrip, this::handleError));
        }
        else {
            buildDialog((Activity) Objects.requireNonNull(getContext())).show().setCanceledOnTouchOutside(false);
        }
    }

    private void handleResponsegetDriverPreviousTrip(getCurrentTripResponse getCurrentTripResponse) {
        try {
            rowItem.clear();
            if (getCurrentTripResponse.getModel() != null) {
                for (int i = 0; i < getCurrentTripResponse.getModel().size(); i++) {
                    if (Language.equals("ar")) {

                        rowItem.add(new CaptinTripsHistoryRecyclerModel(
                                getCurrentTripResponse.getModel().get(i).getId()
                                , getCurrentTripResponse.getModel().get(i).getFromCaption()
                                , getCurrentTripResponse.getModel().get(i).getToCaption()
                                , getCurrentTripResponse.getModel().get(i).getStartDate()
                                , getCurrentTripResponse.getModel().get(i).getStartTime()
                                , getCurrentTripResponse.getModel().get(i).getExpectedDistance()
                                , getCurrentTripResponse.getModel().get(i).getAvailableSeat()
                                , getCurrentTripResponse.getModel().get(i).getTripStatusId()
                                , getCurrentTripResponse.getModel().get(i).getEndTime()
                                , getCurrentTripResponse.getModel().get(i).getEndDate()
                        ));
                    } else {

                        rowItem.add(new CaptinTripsHistoryRecyclerModel(
                                getCurrentTripResponse.getModel().get(i).getId()
                                , getCurrentTripResponse.getModel().get(i).getFromCaption()
                                , getCurrentTripResponse.getModel().get(i).getToCaption()
                                , getCurrentTripResponse.getModel().get(i).getStartDate()
                                , getCurrentTripResponse.getModel().get(i).getStartTime()
                                , getCurrentTripResponse.getModel().get(i).getExpectedDistance()
                                , getCurrentTripResponse.getModel().get(i).getAvailableSeat()
                                , getCurrentTripResponse.getModel().get(i).getTripStatusId()
                                , getCurrentTripResponse.getModel().get(i).getEndTime()
                                , getCurrentTripResponse.getModel().get(i).getEndDate()
                        ));
                    }
                }
                initRecyclerViewHistory();
                if (getCurrentTripResponse.getModel().size() == 0) {
                    no_exist.setVisibility(View.VISIBLE);
                }
            }

        }catch (Exception e){}

        try{  hud.dismiss();}catch (Exception e){}
    }

    private void handleError(Throwable throwable) {

        //Toast.makeText(getActivity(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
        String message="";
        try {
            if (throwable instanceof retrofit2.HttpException) {
                retrofit2.HttpException error = (retrofit2.HttpException) throwable;
                JSONObject jsonObject = new JSONObject(Objects.requireNonNull(((HttpException) throwable).response().errorBody()).string());

                String code = jsonObject.getJSONObject("errors").getString("Code");

                switch (code) {
                    case "1":
                        message = getString(R.string.error_1);
                        break;
                    case "2":
                        message = getString(R.string.error_2);
                        break;
                    case "3":
                        message = getString(R.string.error_3);
                        break;
                    case "4":
                        message = getString(R.string.error_4);
                        break;
                    case "5":
                        message = getString(R.string.error_5);
                        break;
                    case "6":
                        message = getString(R.string.error_6);
                        break;
                    case "7":
                        message = getString(R.string.error_7);
                        break;
                    case "8":
                        message = getString(R.string.error_8);
                        break;
                    case "9":
                        message = getString(R.string.error_9);
                        break;
                    case "10":
                        message = getString(R.string.error_10);
                        break;
                    case "11":
                        message = getString(R.string.error_11);
                        break;
                    case "12":
                        message = getString(R.string.error_12);
                        break;
                    case "13":
                        message = getString(R.string.error_13);
                        break;
                    case "14":
                        message = getString(R.string.error_14);
                        break;
                    case "15":
                        message = getString(R.string.error_15);
                        break;
                    case "16":
                        message = getString(R.string.error_16);
                        break;
                    case "17":
                        message = getString(R.string.error_17);
                        break;
                    case "18":
                        message = getString(R.string.error_18);
                        break;
                    case "19":
                        message = getString(R.string.error_19);
                        break;
                    case "20":
                        message = getString(R.string.error_20);
                        break;
                    case "21":
                        message = getString(R.string.error_21);
                        break;
                    case "22":
                        message = getString(R.string.error_22);
                        break;
                    case "23":
                        message = getString(R.string.error_23);
                        break;
                    case "24":
                        message = getString(R.string.error_24);
                        break;
                    case "25":
                        message = getString(R.string.error_25);
                        break;
                    case "26":
                        message = getString(R.string.error_26);
                        break;
                    case "27":
                        message = "Wasl Error";
                        break;
                    case "28":
                        message = "Wasl Error";
                        break;
                    case "29":
                        message = getString(R.string.error_29);
                        break;
                    case "30":
                        message = getString(R.string.error_30);
                        break;
                    case "31":
                        message = getString(R.string.error_31);
                        break;

                    default:
                        message = getString(R.string.default_message);
                }
            }
            else
            {
                //Toast.makeText(AcceptOrRejectTrip.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                message = getString(R.string.default_message);            }

        } catch (Exception ex) {
            //view.onError(e.getMessage());
            message = getString(R.string.default_message);        }
        /*String error="failed to connect".toLowerCase();
        if (message.toLowerCase().contains(error))
        {
            message=.getString(R.string.check_internet);
        }*/
        android.support.v7.app.AlertDialog.Builder builder;
        android.support.v7.app.AlertDialog dialog;
        builder = new android.support.v7.app.AlertDialog.Builder(getContext());
        @SuppressLint("InflateParams")
        View mview = getLayoutInflater().inflate(R.layout.dialog_error, null);
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
        });
        //Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
      try{  hud.dismiss();}catch (Exception e){}
    }


    private void handleResponse(getCurrentTripResponse getCurrentTripResponse) {
        rowItemWaitingTrip.clear();
        if (getCurrentTripResponse.getModel()!=null) {
            boolean flag = false;
            for (int i = 0; i < getCurrentTripResponse.getModel().size(); i++) {
                if (Language.equals("ar")) {

                    rowItemWaitingTrip.add(new CaptinTripsRecyclerModel(
                              getCurrentTripResponse.getModel().get(i).getId()
                            , getCurrentTripResponse.getModel().get(i).getFromCaption()
                            , getCurrentTripResponse.getModel().get(i).getToCaption()
                            , getCurrentTripResponse.getModel().get(i).getStartDate()
                            , getCurrentTripResponse.getModel().get(i).getStartTime()
                            , getCurrentTripResponse.getModel().get(i).getExpectedDistance()
                            , getCurrentTripResponse.getModel().get(i).getAvailableSeat()
                            , getCurrentTripResponse.getModel().get(i).getTripStatusId()
                            , getCurrentTripResponse.getModel().get(i).getEndTime()
                    ));
                    if (getCurrentTripResponse.getModel().get(i).getTripStatusId().equals("2")) {
                        flag = true;
                        TripStatus = "1";
                        tripIdCaptinAddLocation = getCurrentTripResponse.getModel().get(i).getId();
                    }

                } else {

                    rowItemWaitingTrip.add(new CaptinTripsRecyclerModel(
                            getCurrentTripResponse.getModel().get(i).getId()
                            , getCurrentTripResponse.getModel().get(i).getFromCaption()
                            , getCurrentTripResponse.getModel().get(i).getToCaption()
                            , getCurrentTripResponse.getModel().get(i).getStartDate()
                            , getCurrentTripResponse.getModel().get(i).getStartTime()
                            , getCurrentTripResponse.getModel().get(i).getExpectedDistance()
                            , getCurrentTripResponse.getModel().get(i).getAvailableSeat()
                            , getCurrentTripResponse.getModel().get(i).getTripStatusId()
                            , getCurrentTripResponse.getModel().get(i).getEndTime()
                    ));
                    if (getCurrentTripResponse.getModel().get(i).getTripStatusId().equals("2")) {
                        flag = true;
                        TripStatus = "1";
                        tripIdCaptinAddLocation = getCurrentTripResponse.getModel().get(i).getId();
                    }
                }
            }
            if (flag) {
                if (!checkPermission(getActivity())) {
                    requestLocationPermissions();
                } else
                {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
                    Calendar cal = Calendar.getInstance();
                    //System.out.println(dateFormat.format(cal)); //2016/11/16 12:08:43

                    /*DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    Date date = new Date();
                    // System.out.println(dateFormat.format(date)); //2016/11/16 12:08:43
*/
                    currentDateAddLocation =dateFormat.format(cal.getTime());
                    Objects.requireNonNull(getActivity()).startService(servvice);
                }

            } else {
                Objects.requireNonNull(getActivity()).stopService(servvice);
            }

            //checkLocationPermission();
            initRecyclerViewWaitingTrips();
            if (getCurrentTripResponse.getModel().size()==0)
            {
                no_exist.setVisibility(View.VISIBLE);
            }
        }
       hud.dismiss();
    }

    private void requestLocationPermissions() {
        ActivityCompat.requestPermissions(
                Objects.requireNonNull(getActivity()), new String[]{ACCESS_FINE_LOCATION,ACCESS_COARSE_LOCATION}, 1);
    }
    private static boolean checkPermission(final Context context) {
        return ActivityCompat.checkSelfPermission(
                context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context,
                ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(Objects.requireNonNull(getActivity()), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {


//            if (ContextCompat.checkSelfPermission(getActivity(),
//                    Manifest.permission.ACCESS_FINE_LOCATION)
//                    == PackageManager.PERMISSION_GRANTED) {
//                //Location Permission already granted
//                dialog.dismiss();
//                //client.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
//                // mGoogleMap.setMyLocationEnabled(true);
//            } else {
//                //Request Location Permission
//                //checkLocationPermission();
//            }
//
            // Should we show an explanation?
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                /*if (Language.equals("en")) {

                    android.support.v7.app.AlertDialog.Builder builder;
                    android.support.v7.app.AlertDialog dialog;

                    builder = new android.support.v7.app.AlertDialog.Builder(Objects.requireNonNull(getActivity()));
                    @SuppressLint("InflateParams")
                    View mview = getLayoutInflater().inflate(R.layout.dialog_error, null);

                    builder.setView(mview);
                    dialog = builder.create();
                    Window window = dialog.getWindow();
                    if (window != null) {
                        window.setGravity(Gravity.CENTER);
                    }
                    dialog.show();
                    dialog.setCancelable(false);
                    dialog.setCanceledOnTouchOutside(false);
                    TextView txt = mview.findViewById(R.id.txt);
                    TextView title = mview.findViewById(R.id.title);
                    TextView ok = mview.findViewById(R.id.ok);
                    title.setText("Location Permission Needed");
                    txt.setText("This app needs the Location permission, please accept to use location functionality");
                    ok.setOnClickListener(v2 -> {
                        ActivityCompat.requestPermissions(Objects.requireNonNull(getActivity()),
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                MY_PERMISSIONS_REQUEST_LOCATION );
                        dialog.dismiss();

                    });
                } else {
                    android.support.v7.app.AlertDialog.Builder builder;
                    android.support.v7.app.AlertDialog dialog;

                    builder = new android.support.v7.app.AlertDialog.Builder(Objects.requireNonNull(getActivity()));
                    @SuppressLint("InflateParams")
                    View mview = getLayoutInflater().inflate(R.layout.dialog_error, null);

                    builder.setView(mview);
                    dialog = builder.create();
                    Window window = dialog.getWindow();
                    if (window != null) {
                        window.setGravity(Gravity.CENTER);
                    }
                    dialog.show();
                    dialog.setCancelable(false);
                    dialog.setCanceledOnTouchOutside(false);
                    TextView txt = mview.findViewById(R.id.txt);
                    TextView title = mview.findViewById(R.id.title);
                    TextView ok = mview.findViewById(R.id.ok);
                    title.setText("اذن الوصول للموقع مطلوب");
                    txt.setText("هذا التطبيق يريد الوصول الى الموقع");
                    ok.setOnClickListener(v2 -> {
                        ActivityCompat.requestPermissions(Objects.requireNonNull(getActivity()),
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                MY_PERMISSIONS_REQUEST_LOCATION );
                        dialog.dismiss();
                    });
                }*/
            ActivityCompat.requestPermissions(Objects.requireNonNull(getActivity()),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION );

                // No explanation needed, we can request the permission.
                //getActivity().startService(new Intent(getActivity(), MyService.class));

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NotNull String[] permissions, @NotNull int[] grantResults) {
        // If request is cancelled, the result arrays are empty.
        if (requestCode == MY_PERMISSIONS_REQUEST_LOCATION) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                // permission was granted, yay! Do the
                // location-related task you need to do.
                if (ContextCompat.checkSelfPermission(
                        Objects.requireNonNull(getActivity()),
                        ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {

                    //client.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
                    Objects.requireNonNull(getActivity()).startService(servvice);
                    //mGoogleMap.setMyLocationEnabled(true);
                }

            } else {

                // permission denied, boo! Disable the
                // functionality that depends on this permission.
                requestLocationPermissions();
                //Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private void initRecyclerViewWaitingTrips() {
        RecyclerViewAdapterCaptinTrips adapter2 = new RecyclerViewAdapterCaptinTrips(home,(Home) getActivity(),rowItemWaitingTrip);
        recyclerViewWaitingTrip.setHasFixedSize(true);
        LinearLayoutManager mRecyclerViewLayoutManager2 = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerViewWaitingTrip.setLayoutManager(mRecyclerViewLayoutManager2);
        recyclerViewWaitingTrip.setAdapter(adapter2);
        adapter2.notifyDataSetChanged();
    }
    private void initionilizationOFTabs()
    {
        tabLayout.addTab(tabLayout.newTab().setText(navLabels[0]));
        tabLayout.addTab(tabLayout.newTab().setText(navLabels[1]));

    }
    private void initRecyclerViewHistory() {
        RecyclerViewAdapterCaptinTripsHistory adapter2 = new RecyclerViewAdapterCaptinTripsHistory(home,rowItem);
        recyclerViewHistory.setHasFixedSize(true);
        LinearLayoutManager mRecyclerViewLayoutManager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerViewHistory.setLayoutManager(mRecyclerViewLayoutManager2);
        recyclerViewHistory.setAdapter(adapter2);
        adapter2.notifyDataSetChanged();
    }
}