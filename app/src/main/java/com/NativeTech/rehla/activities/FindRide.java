package com.NativeTech.rehla.activities;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;


import java.util.Arrays;
import com.kaopiz.kprogresshud.KProgressHUD;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import com.NativeTech.rehla.model.data.dto.Models.getCityModels.SearchResponseModel;
import com.NativeTech.rehla.Network.NetworkUtil;
import com.NativeTech.rehla.R;
import com.NativeTech.rehla.Utills.Constant;
import com.NativeTech.rehla.Utills.Validation;

import retrofit2.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static com.NativeTech.rehla.Utills.Constant.buildDialog;
import static com.NativeTech.rehla.Utills.Constant.dCity;
import static com.NativeTech.rehla.Utills.Constant.sCity;
import static com.NativeTech.rehla.Utills.TerhalUtils.getDateWithUTCZone;

public class FindRide extends AppCompatActivity {

    private Button search;
    private String Language;
    private CompositeSubscription mSubscriptions;
    private SharedPreferences mSharedPreferences;
    private KProgressHUD hud;


    private AutocompleteSupportFragment autocompleteFragment1;
    private AutocompleteSupportFragment autocompleteFragment2;
    private AutoCompleteTextView autocompleteFrom;
    private AutoCompleteTextView autocompleteTo;

    private TextView date;
    private int mYear;
    private int mMonth;
    private int mDay;
    private Calendar mcurrentDate;
    private String startingDate="";
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_ride);
        search          = findViewById(R.id.search);
        date            = findViewById(R.id.date);


        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
         getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        startingDate= getDateWithUTCZone(dateFormat.format(c.getTime()));
        //Toast.makeText(this,startingDate , Toast.LENGTH_SHORT).show();



        date.setText(startingDate);

        date.setOnClickListener(v ->{
            DatePickerDialog mDatePicker = new DatePickerDialog(FindRide.this , (datepicker, selectedyear, selectedmonth, selectedday) -> {
                mcurrentDate = Calendar.getInstance();
                mcurrentDate.set(Calendar.YEAR, selectedyear);
                mcurrentDate.set(Calendar.MONTH, selectedmonth);
                mcurrentDate.set(Calendar.DAY_OF_MONTH, selectedday);
                date.setText(getDateWithUTCZone(dateFormat.format(mcurrentDate.getTime())));
                mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);
                mMonth = mcurrentDate.get(Calendar.MONTH);
                mYear = mcurrentDate.get(Calendar.YEAR);

                startingDate= getDateWithUTCZone(dateFormat.format(mcurrentDate.getTime()));


            },mYear,mMonth , mDay);
            mDatePicker.show();
        });
        search.setOnClickListener(v -> {
            if (Validation.isConnected(FindRide.this))
            {
                if (Language.equals("ar"))
                {
                    if (Constant.sCity.equals(""))
                    {
                        Toast.makeText(this, "من فضلك اختر مكان بدء الرحلة", Toast.LENGTH_SHORT).show();
                    }
                    else if (Constant.dCity.equals(""))
                    {
                        Toast.makeText(this, "من فضلك اختر مكان انتهاء الرحلة", Toast.LENGTH_SHORT).show();
                    }
                    else if (startingDate.equals(""))
                    {
                        Toast.makeText(this, "من فضلك اختر وقت بدء الرحلة", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Constant.dateSearch=startingDate;
                        Intent intent = new Intent(FindRide.this,SearchResult.class);
                        startActivity(intent);
                    }
                }
                else
                {
                    if (Constant.sCity.equals(""))
                    {
                        Toast.makeText(this, "please Set Starting Location", Toast.LENGTH_SHORT).show();
                    }
                    else if (Constant.dCity.equals(""))
                    {
                        Toast.makeText(this, "please Set End Location", Toast.LENGTH_SHORT).show();
                    }
                    else if (startingDate.equals(""))
                    {
                        Toast.makeText(this, "please Set Data", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Constant.dateSearch=startingDate;
                        Intent intent = new Intent(FindRide.this,SearchResult.class);
                        startActivity(intent);
                    }
                }
            }
            else
            {
                buildDialog(FindRide.this).show().setCanceledOnTouchOutside(false);
            }
        });

        mSharedPreferences  =   getSharedPreferences("tokenDetail",MODE_PRIVATE);
        Language            =   mSharedPreferences.getString(Constant.language,"en");
        mSubscriptions      =   new CompositeSubscription();
        hud = KProgressHUD.create(FindRide.this)
                .setStyle(KProgressHUD.Style.PIE_DETERMINATE)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .setCancellable(false)
                .setMaxProgress(100);

        autocompleteFrom=findViewById(R.id.autocomplete_from);
      //  Typeface tf1 = Typeface.createFromAsset(getAssets(), "Cairo-Regular.ttf");
      //  autocompleteFrom.setTypeface(tf1);

        autocompleteTo=findViewById(R.id.autocomplete_to);
        //Typeface tf2 = Typeface.createFromAsset(getAssets(), "Cairo-Regular.ttf");
       // autocompleteTo.setTypeface(tf2);

        // Initialize Places.
        Places.initialize(getApplicationContext(), getString(R.string.google_maps_key));
        // Create a new Places client instance.
        PlacesClient placesClient = Places.createClient(this);

        autocompleteFragment1 = (AutocompleteSupportFragment) getSupportFragmentManager().findFragmentById(R.id.place_autocomplete_fragment_src);
        autocompleteFragment1.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG));
        autocompleteFragment1.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                Constant.sLatSearch=String .valueOf(Objects.requireNonNull(place.getLatLng()).latitude);
                Constant.sLngSearch=String .valueOf(place.getLatLng().longitude);
                Constant.status="from";

                getAddressCityAR(place.getLatLng().latitude,place.getLatLng().longitude);
                autocompleteFrom.setHint(Objects.requireNonNull(place.getAddress()));
            }
            @Override
            public void onError(@NonNull Status status) {
                // TODO: Handle the error.
                //Log.i(TAG, "An error occurred: " + status);
                Toast.makeText(FindRide.this, status.getStatusMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        autocompleteFragment2 = (AutocompleteSupportFragment) getSupportFragmentManager().findFragmentById(R.id.place_autocomplete_fragment_dis);
        autocompleteFragment2.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG));
        autocompleteFragment2.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                Constant.dLatSearch=String .valueOf(Objects.requireNonNull(place.getLatLng()).latitude);
                Constant.dLngSearch=String .valueOf(place.getLatLng().longitude);
                Constant.status="to";
                getAddressCityAR(place.getLatLng().latitude,place.getLatLng().longitude);
                autocompleteTo.setHint(Objects.requireNonNull(place.getAddress()).toString());
            }

            @Override
            public void onError(@NonNull Status status) {
                // TODO: Handle the error.
                //Log.i(TAG, "An error occurred: " + status);
                Toast.makeText(FindRide.this, status.getStatusMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        if (sCity.equals(""))
        {
            if(Language.equals("ar"))
            {
                autocompleteFragment1.setHint("من اين ستبدأ الرحلة؟");
            }
            else
            {
                autocompleteFragment1.setHint("Set Starting Location");
            }
        }
        else
        {
            autocompleteFrom.setText(sCity);
        }
        autocompleteFrom.setOnClickListener(view -> {
            View view1=autocompleteFragment1.getView();
            if (view1 != null) {
                view1.post(() -> view1.findViewById(R.id.places_autocomplete_search_input).performClick());
            }
        });


        autocompleteTo.setOnClickListener(view -> {
            View view1=autocompleteFragment2.getView();
            if (view1 != null) {
                view1.post(() -> view1.findViewById(R.id.places_autocomplete_search_input).performClick());
            }
        });

        if (dCity.equals("")) {
            if (Language.equals("ar")) {
                autocompleteFragment2.setHint("اين ستنتهي الرحلة؟");
            } else {
                autocompleteFragment2.setHint("Set End Location");
            }
        }
        else
        {
            autocompleteTo.setHint(dCity);
        }




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
    public void onBackPressed() {

        Constant.sLatSearch= "";
        Constant.sLngSearch= "";
        Constant.dLatSearch= "";
        Constant.dLngSearch= "";
        Constant.dateSearch= "";
        Constant.sCity="";
        Constant.dCity="";
        finish();
    }
    private void getAddressCityAR(double lat, double lng) {
        hud.show();
        String Latlng=String.valueOf(lat).concat(",").concat(String.valueOf(lng));
        try {
            mSubscriptions.add(NetworkUtil.getRetrofit_Get_Address()
                    .getCityAR(Latlng,"ar",getString(R.string.google_maps_key))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResponsegetCityAR, this::handleError));
        }
        catch (Exception e)
        {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
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
                message = getString(R.string.default_message);            }

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

    private void handleResponsegetCityAR(SearchResponseModel searchResponseModel) {

        //Toast.makeText(this, "ok", Toast.LENGTH_SHORT).show();
        String flag="n";
        for (int i=0;i<searchResponseModel.getResults().get(0).getAddress_components().size();i++)
        {
            for (int j=0;j<searchResponseModel.getResults().get(0).getAddress_components().get(i).getTypes().length;j++)
            {
                if (flag.equals("n"))
                {
                    if (searchResponseModel.getResults().get(0).getAddress_components().get(i).getTypes()[j].equals("locality"))
                    {
                        flag="y";
                        if (Constant.status.equals("from"))
                        {
                            Constant.sCity=searchResponseModel.getResults().get(0).getAddress_components().get(i).getLong_name();
                        }
                        else if (Constant.status.equals("to"))
                        {
                            Constant.dCity=searchResponseModel.getResults().get(0).getAddress_components().get(i).getLong_name();
                        }
                    }
                }
            }
        }
        if (flag.equals("n"))
        {
            for (int i=0;i<searchResponseModel.getResults().get(0).getAddress_components().size();i++)
            {
                for (int j=0;j<searchResponseModel.getResults().get(0).getAddress_components().get(i).getTypes().length;j++)
                {
                    if (flag.equals("n"))
                    {
                        if (searchResponseModel.getResults().get(0).getAddress_components().get(i).getTypes()[j].equals("sublocality"))
                        {
                            flag="y";
                            if (Constant.status.equals("from"))
                            {
                                Constant.sCity=searchResponseModel.getResults().get(0).getAddress_components().get(i).getLong_name();
                            }
                            else if (Constant.status.equals("to"))
                            {
                                Constant.dCity=searchResponseModel.getResults().get(0).getAddress_components().get(i).getLong_name();
                            }
                        }
                    }
                }
            }
        }

        if (flag.equals("n"))
        {
            for (int i=0;i<searchResponseModel.getResults().get(0).getAddress_components().size();i++)
            {
                for (int j=0;j<searchResponseModel.getResults().get(0).getAddress_components().get(i).getTypes().length;j++)
                {
                    if (flag.equals("n"))
                    {
                        if (searchResponseModel.getResults().get(0).getAddress_components().get(i).getTypes()[j].equals("administrative_area_level_1"))
                        {
                            flag="y";
                            if (Constant.status.equals("from"))
                            {
                                Constant.sCity=searchResponseModel.getResults().get(0).getAddress_components().get(i).getLong_name();
                            }
                            else if (Constant.status.equals("to"))
                            {
                                Constant.dCity=searchResponseModel.getResults().get(0).getAddress_components().get(i).getLong_name();
                            }
                        }
                    }
                }
            }
        }
        hud.dismiss();
        /*if (Constant.status.equals("from"))
        {
            Toast.makeText(this,  "from", Toast.LENGTH_SHORT).show();
            Toast.makeText(this,  Constant.sCity, Toast.LENGTH_SHORT).show();
        }
        else if (Constant.status.equals("to"))
        {
            Toast.makeText(this,  "to", Toast.LENGTH_SHORT).show();
            Toast.makeText(this,  Constant.dCity, Toast.LENGTH_SHORT).show();
        }*/

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
            //add = obj.get();
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

}
