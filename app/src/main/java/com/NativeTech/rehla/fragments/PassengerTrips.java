package com.NativeTech.rehla.fragments;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
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

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.NativeTech.rehla.model.data.dto.Models.Passenger.getPassengerCurrentRidesResponse;
import com.NativeTech.rehla.Network.NetworkUtil;
import com.NativeTech.rehla.R;
import com.NativeTech.rehla.Utills.Constant;
import com.NativeTech.rehla.Utills.Validation;
import com.NativeTech.rehla.adapters.MyOfferedHistoryRideItemRecyclerModel;
import com.NativeTech.rehla.adapters.MyOfferedUpcomingRideItemRecyclerModel;
import com.NativeTech.rehla.adapters.RecyclerViewAdapterMyOfferedHistoryRide;
import com.NativeTech.rehla.adapters.RecyclerViewAdapterMyOfferedUpcomingRide;
import retrofit2.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static android.content.Context.MODE_PRIVATE;
import static com.NativeTech.rehla.Utills.Constant.buildDialog;


public class PassengerTrips extends Fragment {


    private TabLayout tabLayout;
    private final int[] navLabels = {

            R.string.upcomming_rides,
            R.string.history_rides
    };
    private LinearLayoutCompat upComing_rides_lin;
    private LinearLayoutCompat history_rides_lin;


    private RecyclerView recyclerViewHistory;
    private List<MyOfferedHistoryRideItemRecyclerModel> rowItem;

    private RecyclerView recyclerViewUpcoming;
    private List<MyOfferedUpcomingRideItemRecyclerModel> rowItemUpcoming;


    private String Language;
    private CompositeSubscription mSubscriptions;
    private SharedPreferences mSharedPreferences;
    private KProgressHUD hud;

    private TextView no_exist;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_passenger_trips, container, false);


        tabLayout = view.findViewById(R.id.tab_category);
        upComing_rides_lin = view.findViewById(R.id.upComing_rides_lin);
        history_rides_lin = view.findViewById(R.id.history_rides_lin);
        recyclerViewHistory = view.findViewById(R.id.recyclerViewHistory);
        recyclerViewUpcoming = view.findViewById(R.id.recyclerViewUpcoming);

        rowItem = new ArrayList<>();
        rowItemUpcoming = new ArrayList<>();

        no_exist = view.findViewById(R.id.no_exist);

        no_exist.setVisibility(View.GONE);

        mSharedPreferences = Objects.requireNonNull(getActivity()).getSharedPreferences("tokenDetail", MODE_PRIVATE);
        Language = mSharedPreferences.getString(Constant.language, "en");
        mSubscriptions = new CompositeSubscription();
        hud = KProgressHUD.create(getActivity())
                .setStyle(KProgressHUD.Style.PIE_DETERMINATE)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .setCancellable(false)
                .setMaxProgress(100);


        getCurrentTripResponse();

        initionilizationOFTabs();


        SpannableString content = new SpannableString(getResources().getString(navLabels[0]));
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        content.setSpan(new StyleSpan(Typeface.BOLD), 0, content.length(), 0);


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
                        getPreviousTrip();
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
        initRecyclerViewUpcoming();


        return view;
    }

    private void initionilizationOFTabs() {
        tabLayout.addTab(tabLayout.newTab().setText(navLabels[0]));
        tabLayout.addTab(tabLayout.newTab().setText(navLabels[1]));
    }

    private void getCurrentTripResponse() {
        if (Validation.isConnected(Objects.requireNonNull(getActivity()))) {
            hud.show();
            mSubscriptions.add(NetworkUtil.getRetrofitByToken(DataManager.getInstance().getCashedAccessToken().getAccess_token())
                    .getPassengerCurrentRides("0")
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResponse, this::handleError));
        } else {
            buildDialog(getActivity()).show().setCanceledOnTouchOutside(false);
        }
    }

    private void getPreviousTrip() {
        if (Validation.isConnected(Objects.requireNonNull(getActivity()))) {
            hud.show();
            mSubscriptions.add(NetworkUtil.getRetrofitByToken(DataManager.getInstance().getCashedAccessToken().getAccess_token())
                    .getPassengerPreviousRides("0")
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResponsegetPreviousTrip, this::handleError));
        } else {
            buildDialog(getActivity()).show().setCanceledOnTouchOutside(false);
        }
    }

    private void handleResponsegetPreviousTrip(getPassengerCurrentRidesResponse getPassengerCurrentRidesResponse) {
        rowItem.clear();
        if (getPassengerCurrentRidesResponse.getModel() != null) {
            for (int i = 0; i < getPassengerCurrentRidesResponse.getModel().size(); i++) {
                if (Integer.parseInt(getPassengerCurrentRidesResponse.getModel().get(i).getReservationStatusId()) > 2) {
                  //  Toast.makeText(getActivity(), getPassengerCurrentRidesResponse.getModel().get(i).getTripStatusId() + "", Toast.LENGTH_SHORT).show();
                    if (Language.equals("ar")) {
                        String statusRate = "n";
                        if (getPassengerCurrentRidesResponse.getModel().get(i).getReservationStatusId().equals("6")) {
                            statusRate = "y";
                        }
                        rowItem.add(new MyOfferedHistoryRideItemRecyclerModel(
                                getPassengerCurrentRidesResponse.getModel().get(i).getReservationId()
                                , Constant.BASE_URL_profile_img + getPassengerCurrentRidesResponse.getModel().get(i).getDriverProfilePhoto()
                                , getPassengerCurrentRidesResponse.getModel().get(i).getDriverName()
                                , getPassengerCurrentRidesResponse.getModel().get(i).getRealCost()
                                , getPassengerCurrentRidesResponse.getModel().get(i).getCarModelNameLT()
                                , getPassengerCurrentRidesResponse.getModel().get(i).getCarColorNameLT()
                                , getPassengerCurrentRidesResponse.getModel().get(i).getPassengerBookedSeats()
                                , getPassengerCurrentRidesResponse.getModel().get(i).getReservationStatusId()
                                , getPassengerCurrentRidesResponse.getModel().get(i).getStartTime()
                                , getPassengerCurrentRidesResponse.getModel().get(i).getFromCaption()
                                , getPassengerCurrentRidesResponse.getModel().get(i).getEndTime()
                                , getPassengerCurrentRidesResponse.getModel().get(i).getToCaption()
                                , getPassengerCurrentRidesResponse.getModel().get(i).getStartDate()
                                , getPassengerCurrentRidesResponse.getModel().get(i).getExpectedCost()
                                , statusRate
                                , getPassengerCurrentRidesResponse.getModel().get(i).getTotalRate()
                                ,
                                getPassengerCurrentRidesResponse.getModel().get(i).getEndDate()
                        ));
                    } else {
                        String statusRate = "n";
                        if (getPassengerCurrentRidesResponse.getModel().get(i).getReservationStatusId().equals("6")) {
                            statusRate = "y";
                        }
                        rowItem.add(new MyOfferedHistoryRideItemRecyclerModel(
                                getPassengerCurrentRidesResponse.getModel().get(i).getReservationId()
                                , Constant.BASE_URL_profile_img + getPassengerCurrentRidesResponse.getModel().get(i).getDriverProfilePhoto()
                                , getPassengerCurrentRidesResponse.getModel().get(i).getDriverName()
                                , getPassengerCurrentRidesResponse.getModel().get(i).getRealCost()
                                , getPassengerCurrentRidesResponse.getModel().get(i).getCarModelName()
                                , getPassengerCurrentRidesResponse.getModel().get(i).getCarColorName()
                                , getPassengerCurrentRidesResponse.getModel().get(i).getPassengerBookedSeats()
                                , getPassengerCurrentRidesResponse.getModel().get(i).getReservationStatusId()
                                , getPassengerCurrentRidesResponse.getModel().get(i).getStartTime()
                                , getPassengerCurrentRidesResponse.getModel().get(i).getFromCaption()
                                , getPassengerCurrentRidesResponse.getModel().get(i).getEndTime()
                                , getPassengerCurrentRidesResponse.getModel().get(i).getToCaption()
                                , getPassengerCurrentRidesResponse.getModel().get(i).getStartDate()
                                , getPassengerCurrentRidesResponse.getModel().get(i).getExpectedCost()
                                , statusRate
                                , getPassengerCurrentRidesResponse.getModel().get(i).getTotalRate()
                                ,
                                getPassengerCurrentRidesResponse.getModel().get(i).getEndDate()

                        ));
                    }
                }
            }
            initRecyclerViewHistory();
            if (getPassengerCurrentRidesResponse.getModel().size() == 0) {
                no_exist.setVisibility(View.VISIBLE);
            }
        }
        hud.dismiss();
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

        }
        catch (Exception ex) {
            //view.onError(e.getMessage());
            message = getString(R.string.default_message);
        }
        String error="failed to connect".toLowerCase();
        if (message.toLowerCase().contains(error))
        {
            message=getActivity().getString(R.string.check_internet);
        }
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
        TextView txt = mview.findViewById(R.id.txt);
        TextView ok = mview.findViewById(R.id.ok);
        txt.setText(message);
        ok.setOnClickListener(v2 -> dialog.dismiss());
        hud.dismiss();
    }


    private void handleResponse(getPassengerCurrentRidesResponse getPassengerCurrentRidesResponse) {
        rowItemUpcoming.clear();
        if (getPassengerCurrentRidesResponse.getModel() != null) {
            for (int i = 0; i < getPassengerCurrentRidesResponse.getModel().size(); i++) {
               // Toast.makeText(getActivity(), getPassengerCurrentRidesResponse.getModel().get(i).getTripStatusId() + "", Toast.LENGTH_SHORT).show();
                if (Language.equals("ar")) {
                    if (Integer.parseInt(getPassengerCurrentRidesResponse.getModel().get(i).getReservationStatusId()) < 3) {
                        rowItemUpcoming.add(new MyOfferedUpcomingRideItemRecyclerModel(
                                getPassengerCurrentRidesResponse.getModel().get(i).getReservationId()
                                , Constant.BASE_URL_profile_img + getPassengerCurrentRidesResponse.getModel().get(i).getDriverProfilePhoto()
                                , getPassengerCurrentRidesResponse.getModel().get(i).getDriverName()
                                , getPassengerCurrentRidesResponse.getModel().get(i).getRealCost()
                                , getPassengerCurrentRidesResponse.getModel().get(i).getCarModelNameLT()
                                , getPassengerCurrentRidesResponse.getModel().get(i).getCarColorNameLT()
                                , getPassengerCurrentRidesResponse.getModel().get(i).getPassengerBookedSeats()
                                , getPassengerCurrentRidesResponse.getModel().get(i).getReservationStatusId()
                                , getPassengerCurrentRidesResponse.getModel().get(i).getStartTime()
                                , getPassengerCurrentRidesResponse.getModel().get(i).getFromCaption()
                                , getPassengerCurrentRidesResponse.getModel().get(i).getEndTime()
                                , getPassengerCurrentRidesResponse.getModel().get(i).getToCaption()
                                , getPassengerCurrentRidesResponse.getModel().get(i).getStartDate()
                                , getPassengerCurrentRidesResponse.getModel().get(i).getExpectedCost()
                                , "n"
                                , getPassengerCurrentRidesResponse.getModel().get(i).getTotalRate()
                                , ""
                                ,  getPassengerCurrentRidesResponse.getModel().get(i).getDriverPhoneKey()+ getPassengerCurrentRidesResponse.getModel().get(i).getDriverPhoneNumber()
                                ,  getPassengerCurrentRidesResponse.getModel().get(i).getEndDate()
                                ,  getPassengerCurrentRidesResponse.getModel().get(i).getDriverId()
                                ,  getPassengerCurrentRidesResponse.getModel().get(i).getDriverName()
                                ,  getPassengerCurrentRidesResponse.getModel().get(i).getDriverIdentityId()
                                ,  Constant.BASE_URL_profile_img +getPassengerCurrentRidesResponse.getModel().get(i).getDriverProfilePhoto()
                        ));
                    }
                } else {
                    if (Integer.parseInt(getPassengerCurrentRidesResponse.getModel().get(i).getReservationStatusId()) < 3) {
                        rowItemUpcoming.add(new MyOfferedUpcomingRideItemRecyclerModel(
                                getPassengerCurrentRidesResponse.getModel().get(i).getReservationId()
                                , Constant.BASE_URL_profile_img + getPassengerCurrentRidesResponse.getModel().get(i).getDriverProfilePhoto()
                                , getPassengerCurrentRidesResponse.getModel().get(i).getDriverName()
                                , getPassengerCurrentRidesResponse.getModel().get(i).getRealCost()
                                , getPassengerCurrentRidesResponse.getModel().get(i).getCarModelName()
                                , getPassengerCurrentRidesResponse.getModel().get(i).getCarColorName()
                                , getPassengerCurrentRidesResponse.getModel().get(i).getPassengerBookedSeats()
                                , getPassengerCurrentRidesResponse.getModel().get(i).getReservationStatusId()
                                , getPassengerCurrentRidesResponse.getModel().get(i).getStartTime()
                                , getPassengerCurrentRidesResponse.getModel().get(i).getFromCaption()
                                , getPassengerCurrentRidesResponse.getModel().get(i).getEndTime()
                                , getPassengerCurrentRidesResponse.getModel().get(i).getToCaption()
                                , getPassengerCurrentRidesResponse.getModel().get(i).getStartDate()
                                , getPassengerCurrentRidesResponse.getModel().get(i).getExpectedCost()
                                , "n"
                                ,  getPassengerCurrentRidesResponse.getModel().get(i).getTotalRate()
                                , ""
                                ,  getPassengerCurrentRidesResponse.getModel().get(i).getDriverPhoneKey()+ getPassengerCurrentRidesResponse.getModel().get(i).getDriverPhoneNumber()
                                ,  getPassengerCurrentRidesResponse.getModel().get(i).getEndDate()
                                ,  getPassengerCurrentRidesResponse.getModel().get(i).getDriverId()
                                ,  getPassengerCurrentRidesResponse.getModel().get(i).getDriverName()
                                ,  getPassengerCurrentRidesResponse.getModel().get(i).getDriverIdentityId()
                                ,  Constant.BASE_URL_profile_img +getPassengerCurrentRidesResponse.getModel().get(i).getDriverProfilePhoto()
                        ));
                    }
                }

            }
            initRecyclerViewUpcoming();
            if (getPassengerCurrentRidesResponse.getModel().size() == 0) {
                no_exist.setVisibility(View.VISIBLE);
            }
        }
        hud.dismiss();
    }

    private void initRecyclerViewHistory() {
        RecyclerViewAdapterMyOfferedHistoryRide adapter2 = new RecyclerViewAdapterMyOfferedHistoryRide(getActivity(), rowItem);
        recyclerViewHistory.setHasFixedSize(true);
        LinearLayoutManager mRecyclerViewLayoutManager2 = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerViewHistory.setLayoutManager(mRecyclerViewLayoutManager2);
        recyclerViewHistory.setAdapter(adapter2);
        adapter2.notifyDataSetChanged();
    }

    private void initRecyclerViewUpcoming() {
        RecyclerViewAdapterMyOfferedUpcomingRide adapter2 = new RecyclerViewAdapterMyOfferedUpcomingRide(getActivity(), rowItemUpcoming);
        recyclerViewUpcoming.setHasFixedSize(true);
        LinearLayoutManager mRecyclerViewLayoutManager2 = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerViewUpcoming.setLayoutManager(mRecyclerViewLayoutManager2);
        recyclerViewUpcoming.setAdapter(adapter2);
        adapter2.notifyDataSetChanged();
    }
}