package com.NativeTech.rehla.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.NativeTech.rehla.model.DataManager;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import com.NativeTech.rehla.model.data.dto.Models.SearchResult.SearchResponse;
import com.NativeTech.rehla.model.data.dto.Models.WaitingTrips.addWaitingTripsRequest;
import com.NativeTech.rehla.model.data.dto.Models.WaitingTrips.addWaitingTripsResponse;
import com.NativeTech.rehla.Network.NetworkUtil;
import com.NativeTech.rehla.R;
import com.NativeTech.rehla.Utills.Constant;
import com.NativeTech.rehla.Utills.Validation;
import com.NativeTech.rehla.adapters.RecyclerViewAdapterSearchResult;
import com.NativeTech.rehla.adapters.SearchResultRecyclerModel;

import retrofit2.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static com.NativeTech.rehla.Utills.Constant.TripDetailsCarColor;
import static com.NativeTech.rehla.Utills.Constant.buildDialog;

public class SearchResult extends AppCompatActivity {

    private RecyclerView recyclerViewSearchResult;
    private List<SearchResultRecyclerModel> rowItem;

    private CompositeSubscription mSubscriptions;
    private SharedPreferences mSharedPreferences;
    private String Language;
    private KProgressHUD hud;

    private TextView no_exist;
    private TextView create_alert;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);



        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.search);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        recyclerViewSearchResult                = findViewById(R.id.recyclerViewSearchResult);
        rowItem                                 = new ArrayList<>();

        no_exist                                = findViewById(R.id.no_exist);
        create_alert                            = findViewById(R.id.create_alert);

        no_exist.setVisibility(View.GONE);
        create_alert.setVisibility(View.GONE);

        mSharedPreferences  =   getSharedPreferences("tokenDetail",MODE_PRIVATE);
        Language            =   mSharedPreferences.getString(Constant.language,Locale.getDefault().getLanguage());
        mSubscriptions      =   new CompositeSubscription();
        hud = KProgressHUD.create(SearchResult.this)
                .setStyle(KProgressHUD.Style.PIE_DETERMINATE)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .setCancellable(false)
                .setMaxProgress(100);

        getTripResponse();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
        {

            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initRecyclerViewSearchResult() {
        RecyclerViewAdapterSearchResult adapter2 = new RecyclerViewAdapterSearchResult(SearchResult.this,rowItem);
        recyclerViewSearchResult.setHasFixedSize(true);
        LinearLayoutManager mRecyclerViewLayoutManager2 = new LinearLayoutManager(SearchResult.this, LinearLayoutManager.VERTICAL, false);
        recyclerViewSearchResult.setLayoutManager(mRecyclerViewLayoutManager2);
        recyclerViewSearchResult.setAdapter(adapter2);
        adapter2.notifyDataSetChanged();
    }
    private void getTripResponse()
    {
        if (Validation.isConnected(SearchResult.this))
        {
                /*Toast.makeText(this,Constant.sCity, Toast.LENGTH_SHORT).show();
                Toast.makeText(this, Constant.dCity, Toast.LENGTH_SHORT).show();*/
                //Toast.makeText(this, Constant.dateSearch, Toast.LENGTH_SHORT).show();
            hud.show();
            mSubscriptions.add(NetworkUtil.getRetrofitByToken(DataManager.getInstance().getCashedAccessToken().getAccess_token())
                    .findTrip(
                            Constant.sCity
                            ,Constant.dCity
                            ,Constant.dateSearch
                            ,"0")
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResponse, this::handleError));
        }
        else {
            buildDialog(SearchResult.this).show().setCanceledOnTouchOutside(false);
        }
    }

    private void handleError(Throwable throwable) {
        //Toast.makeText(getActivity(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
        String message="";
        try {
            if (throwable instanceof retrofit2.HttpException) {
                retrofit2.HttpException error = (retrofit2.HttpException) throwable;
                JSONObject jsonObject = new JSONObject(Objects.requireNonNull(((retrofit2.HttpException) throwable).response().errorBody()).string());

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


    private void handleResponse(SearchResponse searchResponse) {
        rowItem.clear();
        if (searchResponse.getModel()!=null)
        {
            if (Language.equals("ar"))
            {
                for (int i=0;i<searchResponse.getModel().size();i++)
                {
                    rowItem.add(new SearchResultRecyclerModel(
                            searchResponse.getModel().get(i).getId(),
                            Constant.BASE_URL_profile_img+searchResponse.getModel().get(i).getDriverProfilePhoto()
                            , searchResponse.getModel().get(i).getDriverName()
                            ,searchResponse.getModel().get(i).getRealCost()
                            ,searchResponse.getModel().get(i).getCarModelNameLT()
                            ,searchResponse.getModel().get(i).getCarColorNameLT()
                            ,searchResponse.getModel().get(i).getAvailableSeat()
                            ,searchResponse.getModel().get(i).getStartTime()
                            ,searchResponse.getModel().get(i).getFromCaption()
                            ,searchResponse.getModel().get(i).getEndTime()
                            ,searchResponse.getModel().get(i).getToCaption()
                            ,searchResponse.getModel().get(i).getStartDate()
                            ,searchResponse.getModel().get(i).getTotalRate()
                            ,searchResponse.getModel().get(i).getTotalCount()
                            ,searchResponse.getModel().get(i).getTripCount()
                            ,searchResponse.getModel().get(i).getEndDate()
                ));
                }
            }
            else
            {
                for (int i=0;i<searchResponse.getModel().size();i++)
                {
                    rowItem.add(new SearchResultRecyclerModel(
                            searchResponse.getModel().get(i).getId(),
                            Constant.BASE_URL_profile_img+searchResponse.getModel().get(i).getDriverProfilePhoto()
                            , searchResponse.getModel().get(i).getDriverName()
                            ,searchResponse.getModel().get(i).getRealCost()
                            ,searchResponse.getModel().get(i).getCarModelName()
                            ,searchResponse.getModel().get(i).getCarColorName()
                            ,searchResponse.getModel().get(i).getAvailableSeat()
                            ,searchResponse.getModel().get(i).getStartTime()
                            ,searchResponse.getModel().get(i).getFromCaption()
                            ,searchResponse.getModel().get(i).getEndTime()
                            ,searchResponse.getModel().get(i).getToCaption()
                            ,searchResponse.getModel().get(i).getStartDate()
                            ,searchResponse.getModel().get(i).getTotalRate()
                            ,searchResponse.getModel().get(i).getTotalCount()
                            ,searchResponse.getModel().get(i).getTripCount()
                            ,searchResponse.getModel().get(i).getEndDate()
                    ));
                }
            }
            if (searchResponse.getModel().size()==0)
            {
                no_exist.setVisibility(View.VISIBLE);
                create_alert.setVisibility(View.VISIBLE);
                create_alert.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Validation.isConnected(SearchResult.this))
                        {
                            addWaitingTripsRequest addWaitingTripsRequest=new addWaitingTripsRequest();
                            addWaitingTripsRequest.setSourceCity(Constant.sCity);
                            addWaitingTripsRequest.setDestinationCity(Constant.dCity);
                            hud.show();
                            mSubscriptions.add(NetworkUtil.getRetrofitByToken(DataManager.getInstance().getCashedAccessToken().getAccess_token())
                                    .AddWaitingTrip(addWaitingTripsRequest)
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribeOn(Schedulers.io())
                                    .subscribe(this::handleResponse, this::handleError));
                        }
                        else {
                            buildDialog(SearchResult.this).show().setCanceledOnTouchOutside(false);
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
                                message = getString(R.string.default_message);                            }

                        } catch (Exception ex) {
                            //view.onError(e.getMessage());
                            message = getString(R.string.default_message);                        }
                        android.support.v7.app.AlertDialog.Builder builder;
                        android.support.v7.app.AlertDialog dialog;

                        builder = new android.support.v7.app.AlertDialog.Builder(SearchResult.this);
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

                    private void handleResponse(addWaitingTripsResponse addWaitingTripsResponse) {
                        if (addWaitingTripsResponse.getErrors()!=null)
                        {
                            if (addWaitingTripsResponse.getErrors().getMessage()!=null)
                            {
                                AlertDialog.Builder builder = new AlertDialog.Builder(SearchResult.this);
                                builder.setMessage(addWaitingTripsResponse.getErrors().getMessage());
                                builder.setIcon(R.drawable.ic_error);
                                if (Language.equals("en"))
                                {
                                    builder.setNegativeButton("Cancel", (dialogInterface, i) -> {
                                    });
                                }
                                else
                                {
                                    builder.setNegativeButton("إلغاء", (dialogInterface, i) -> {
                                    });
                                }
                                builder.show();
                            }
                            else
                            {
                                if (Language.equals("ar"))
                                {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(SearchResult.this);
                                    builder.setMessage("تم إنشاء تنبية الرحلة بنجاح");
                                    builder.setIcon(R.drawable.ic_verified);
                                    builder.setPositiveButton("حسنا", (dialogInterface, i) -> {
                                        Constant.sLatSearch= "";
                                        Constant.sLngSearch= "";
                                        Constant.dLatSearch= "";
                                        Constant.dLngSearch= "";
                                        Constant.dateSearch= "";
                                        Constant.sCity="";
                                        Constant.dCity="";
                                        Intent intent = new Intent(SearchResult.this, WaitingTrips.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                                        finish();
                                    });
                                    builder.setCancelable(false);
                                    builder.show().setCanceledOnTouchOutside(false);
                                }
                                else
                                {
                                    Constant.sLatSearch= "";
                                    Constant.sLngSearch= "";
                                    Constant.dLatSearch= "";
                                    Constant.dLngSearch= "";
                                    Constant.dateSearch= "";
                                    Constant.sCity="";
                                    Constant.dCity="";
                                    AlertDialog.Builder builder = new AlertDialog.Builder(SearchResult.this);
                                    builder.setMessage("Alert created successfully");
                                    builder.setIcon(R.drawable.ic_verified);
                                    builder.setPositiveButton("Ok", (dialogInterface, i) -> {
                                        Intent intent = new Intent(SearchResult.this, WaitingTrips.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                                        finish();
                                    });
                                    builder.setCancelable(false);
                                    builder.show().setCanceledOnTouchOutside(false);
                                }

                                /*Intent intent = new Intent(getApplicationContext(),Login.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                                finish();*/
                            }
                        }

                        hud.dismiss();
                    }
                });
            }
            initRecyclerViewSearchResult();
        }
       hud.dismiss();
    }
    public String getAddress(double lat, double lng) {
        Geocoder geocoder = null;
        Locale loc = new Locale("en");
        geocoder = new Geocoder(getApplicationContext(),loc);
        String add="";
        try {
            /*List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            Address obj = addresses.get(0);
            add = obj.getAddressLine(0);*/

            List<Address> addresses = geocoder.getFromLocation(lat, lng, 10);
            Address obj = addresses.get(0);
            add=add.replaceAll("\\d","");
            add=add.replaceAll("Unnamed Road","");
            add=add.replaceAll("المملكة العربية","");
            //add = obj.getCountryName();
            boolean flag=true;
            if (addresses.size() > 0) {
                for (Address adr : addresses) {
                    if (adr.getLocality() != null && adr.getLocality().length() > 0) {
                        add = adr.getLocality();
                        if (!flag)
                        break;

                        flag=false;
                    }

                    if (adr.getSubLocality() != null && adr.getSubLocality().length() > 0) {
                        add =  adr.getSubLocality();

                        if (!flag)
                            break;

                        flag=false;
                    }

                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return add;
    }
    public void onBackPressed(){
        TripDetailsCarColor="";
        finish();
    }
}
