package com.NativeTech.rehla.activities;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.NativeTech.rehla.model.DataManager;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.NativeTech.rehla.model.data.dto.Models.AcceptOrReject.AcceptOrRejectResponse;
import com.NativeTech.rehla.Network.NetworkUtil;
import com.NativeTech.rehla.R;
import com.NativeTech.rehla.Utills.Constant;
import com.NativeTech.rehla.Utills.Validation;
import com.NativeTech.rehla.adapters.CaptinHistoryRequestsRecyclerModel;
import com.NativeTech.rehla.adapters.RecyclerViewAdapterCaptinHistoryRequests;
import retrofit2.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static com.NativeTech.rehla.Utills.Constant.buildDialog;

public class CaptinHistoryRequests extends AppCompatActivity {


    private RecyclerView recyclerView;
    private List<CaptinHistoryRequestsRecyclerModel> rowItem;

    private String Language;
    private CompositeSubscription mSubscriptions;
    private SharedPreferences mSharedPreferences;
    private KProgressHUD hud;

    private TextView no_exist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_captin_history_requests);
        recyclerView                 = findViewById(R.id.recyclerView);
        rowItem                      = new ArrayList<>();

        LinearLayoutCompat back      = findViewById(R.id.back);
        back.setOnClickListener(v -> finish());


        no_exist                      = findViewById(R.id.no_exist);
        no_exist.setVisibility(View.GONE);
        mSharedPreferences  =   getSharedPreferences("tokenDetail",MODE_PRIVATE);
        Language            =   mSharedPreferences.getString(Constant.language,"en");
        mSubscriptions      =   new CompositeSubscription();
        hud = KProgressHUD.create(CaptinHistoryRequests.this)
                .setStyle(KProgressHUD.Style.PIE_DETERMINATE)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .setCancellable(false)
                .setMaxProgress(100);



        getTripDetail();


        //initRecyclerView();

    }

    private void initRecyclerView() {
        RecyclerViewAdapterCaptinHistoryRequests adapter2 = new RecyclerViewAdapterCaptinHistoryRequests(CaptinHistoryRequests.this,rowItem);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager mRecyclerViewLayoutManager2 = new LinearLayoutManager(CaptinHistoryRequests.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mRecyclerViewLayoutManager2);
        recyclerView.setAdapter(adapter2);
        adapter2.notifyDataSetChanged();
    }
    private void getTripDetail()
    {
        if (Validation.isConnected(CaptinHistoryRequests.this))
        {
           /*
            Toast.makeText(this, getAddress(Double.parseDouble(Constant.sLatSearch),Double.parseDouble(Constant.sLngSearch)), Toast.LENGTH_SHORT).show();
            Toast.makeText(this, getAddress(Double.parseDouble(Constant.dLatSearch),Double.parseDouble(Constant.dLngSearch)), Toast.LENGTH_SHORT).show();
            */
            hud.show();
            mSubscriptions.add(NetworkUtil.getRetrofitByToken(DataManager.getInstance().getCashedAccessToken().getAccess_token())
                    .getReservationsBYTripID(Constant.tripIdCaptin)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResponse, this::handleError));
        }
        else {
            buildDialog(CaptinHistoryRequests.this).show().setCanceledOnTouchOutside(false);
        }
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
                message = getString(R.string.default_message);
            }

        } catch (Exception ex) {
            //view.onError(e.getMessage());
            message = getString(R.string.default_message);
        }


        String error="failed to connect".toLowerCase();
        if (message.toLowerCase().contains(error))
        {
            message=getString(R.string.check_internet);
        }

        android.support.v7.app.AlertDialog.Builder builder;
        android.support.v7.app.AlertDialog dialog;

        builder = new android.support.v7.app.AlertDialog.Builder(this);
        @SuppressLint("InflateParams")
        View mview = getLayoutInflater().inflate(R.layout.dialog_error, null);

        builder.setView(mview);
        dialog = builder.create();
        Window window = dialog.getWindow();
        if (window != null) {
            window.setGravity(Gravity.CENTER);
        }
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.show();
        TextView txt = mview.findViewById(R.id.txt);
        TextView ok = mview.findViewById(R.id.ok);
        txt.setText(message);
        ok.setOnClickListener(v2 -> dialog.dismiss());
        hud.dismiss();
    }


    private void handleResponse(AcceptOrRejectResponse acceptOrRejectResponse) {
        rowItem.clear();
        if (acceptOrRejectResponse.getModel()!=null)
        {
            if (Language.equals("ar"))
            {
                for (int i=0;i<acceptOrRejectResponse.getModel().size();i++)
                {
                    /*if (acceptOrRejectResponse.getModel().get(i).getStatusId().equals("1")||acceptOrRejectResponse.getModel().get(i).getStatusId().equals("2"))
                    {*/
                        rowItem.add(new CaptinHistoryRequestsRecyclerModel(
                                acceptOrRejectResponse.getModel().get(i).getId()
                                ,Constant.BASE_URL_profile_img+ acceptOrRejectResponse.getModel().get(i).getPassengerProfilePhoto()
                                ,acceptOrRejectResponse.getModel().get(i).getPassengerName()
                                ,acceptOrRejectResponse.getModel().get(i).getTotalCount()
                                ,acceptOrRejectResponse.getModel().get(i).getTotalRate()
                                ,acceptOrRejectResponse.getModel().get(i).getPaymentType()
                                ,acceptOrRejectResponse.getModel().get(i).getStatusId()
                                ,""
                                ,""
                                ,""
                                ,""
                                ,""
                                ,acceptOrRejectResponse.getModel().get(i).getSeatCount()
                                ,acceptOrRejectResponse.getModel().get(i).getRatedByDriver()
                                ,acceptOrRejectResponse.getModel().get(i).getPassengerId()
                                ,acceptOrRejectResponse.getModel().get(i).getPassengerPhoneKey()+acceptOrRejectResponse.getModel().get(i).getPassengerPhoneNumber()
                                ,acceptOrRejectResponse.getModel().get(i).getPassengerName()
                                ,acceptOrRejectResponse.getModel().get(i).getPassengerIdentityId()
                                ,acceptOrRejectResponse.getModel().get(i).getPassengerProfilePhoto()
                        ));
                    /*}*/
                }
            }
            else
            {
                for (int i=0;i<acceptOrRejectResponse.getModel().size();i++)
                {
                    /*if (acceptOrRejectResponse.getModel().get(i).getStatusId().equals("1")||acceptOrRejectResponse.getModel().get(i).getStatusId().equals("2"))
                    {*/
                        rowItem.add(new CaptinHistoryRequestsRecyclerModel(
                                acceptOrRejectResponse.getModel().get(i).getId()
                                ,Constant.BASE_URL_profile_img+ acceptOrRejectResponse.getModel().get(i).getPassengerProfilePhoto()
                                ,acceptOrRejectResponse.getModel().get(i).getPassengerName()
                                , acceptOrRejectResponse.getModel().get(i).getTotalCount()
                                , acceptOrRejectResponse.getModel().get(i).getTotalRate()
                                ,acceptOrRejectResponse.getModel().get(i).getPaymentType()
                                ,acceptOrRejectResponse.getModel().get(i).getStatusId()
                                ,""
                                ,""
                                ,""
                                ,""
                                ,""
                                ,acceptOrRejectResponse.getModel().get(i).getSeatCount()
                                ,acceptOrRejectResponse.getModel().get(i).getRatedByDriver()
                                ,acceptOrRejectResponse.getModel().get(i).getPassengerId()
                                ,acceptOrRejectResponse.getModel().get(i).getPassengerPhoneKey()+acceptOrRejectResponse.getModel().get(i).getPassengerPhoneNumber()
                                ,acceptOrRejectResponse.getModel().get(i).getPassengerName()
                                ,acceptOrRejectResponse.getModel().get(i).getPassengerIdentityId()
                                ,acceptOrRejectResponse.getModel().get(i).getPassengerProfilePhoto()
                        ));
                    /*}*/
                }
            }
            if (rowItem.size()==0)
            {
                no_exist.setVisibility(View.VISIBLE);
            }
            initRecyclerView();
        }
        hud.dismiss();
    }
}