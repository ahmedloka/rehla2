package com.NativeTech.rehla.activities;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;


import com.NativeTech.rehla.model.DataManager;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.kaopiz.kprogresshud.KProgressHUD;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import com.NativeTech.rehla.model.data.dto.Models.AddTripModels.AddTripRequest;
import com.NativeTech.rehla.model.data.dto.Models.AddTripModels.AddTripResponseModel;
import com.NativeTech.rehla.model.data.dto.Models.CarsModels.ModelCar;
import com.NativeTech.rehla.model.data.dto.Models.CarsModels.getAllCarsResponse;
import com.NativeTech.rehla.model.data.dto.Models.MetaDataModels.MetaDataResponseModel;
import com.NativeTech.rehla.model.data.dto.Models.getCityModels.SearchResponseModel;
import com.NativeTech.rehla.Network.NetworkUtil;
import com.NativeTech.rehla.R;
import com.NativeTech.rehla.Utills.Constant;
import com.NativeTech.rehla.Utills.TerhalUtils;
import com.NativeTech.rehla.Utills.Validation;
import com.NativeTech.rehla.adapters.filterAreaAdapterForCountryCode;
import com.NativeTech.rehla.adapters.filterAreaModelRecycler;
import retrofit2.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static com.NativeTech.rehla.Utills.Constant.buildDialog;
import static com.NativeTech.rehla.Utills.Constant.carStatus;
import static com.NativeTech.rehla.Utills.TerhalUtils.getDateWithUCurrentZone;
import static com.NativeTech.rehla.Utills.TerhalUtils.getDateWithUTCZone;
import static com.NativeTech.rehla.Utills.TerhalUtils.getTimeWithUTCZone;

public class OfferRide extends AppCompatActivity implements View.OnClickListener {

    private LinearLayoutCompat minus_seat_price;
    private LinearLayoutCompat plus_seat_price;
    private LinearLayoutCompat minus_seat_num;
    private LinearLayoutCompat plus_seat_num;
    private LinearLayoutCompat back_seat_lin;
    private TextView seatprice;
    private TextView seat_num;
    private AppCompatImageView back_seat;
    private boolean flag=false;

    private Button offer_ride;


    private AutocompleteSupportFragment autocompleteFragment1;
    private AutocompleteSupportFragment autocompleteFragment2;
    private AutoCompleteTextView autocompleteFrom;
    private AutoCompleteTextView autocompleteTo;

    private TextView distanceValue;
    private TextView seatExpectedpriceValue;

    private String Language;
    private CompositeSubscription mSubscriptions;
    private SharedPreferences mSharedPreferences;
    private KProgressHUD hud;
    private String token="";

    private int mYear;
    private int mMonth;
    private int mDay;
    private String startingDate="";
    private Calendar mcurrentDate;
    private TextView date;
    private String endDate="";

    private List<ModelCar> modelCars=new ArrayList<>();


    private RecyclerView DialogRecyclerView;
    private filterAreaAdapterForCountryCode filterAreaAdapter1;
    private final ArrayList<filterAreaModelRecycler> DialogList = new ArrayList<>();

    private TextView selectCar;
    private String selectCar_Id="";
    private int selectCar_Position=-1;
    private String flagCarVerified="false";

    private String startingTime="";
    private String endTime="";

    String daysExtra="0";

    private int second=0;

    private EditText desc;

    private TextView startTime;
    private TextView EndTime;


    private double Percentage;

    private String FromCaption="";
    private String ToCaption="";

    private SimpleDateFormat dateFormat;


    private int totalSeconds=0;

    private String startDateCurrent;




    static AlertDialog d ;
    private final Calendar c = Calendar.getInstance();
    private final int hour = c.get(Calendar.HOUR_OF_DAY);
    private final int minute = c.get(Calendar.MINUTE);
    private Toolbar toolbar;
    private double kmPrice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_ride);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        minus_seat_price = findViewById(R.id.minus_seat_price);
        plus_seat_price = findViewById(R.id.plus_seat_price);
        minus_seat_num = findViewById(R.id.minus_seat_num);
        plus_seat_num = findViewById(R.id.plus_seat_num);
        back_seat_lin = findViewById(R.id.back_seat_lin);
        seatprice = findViewById(R.id.price);
        seat_num = findViewById(R.id.seat_num);
        back_seat = findViewById(R.id.back_seat);
        offer_ride = findViewById(R.id.offer_ride);
        distanceValue = findViewById(R.id.distanceValue);
        seatExpectedpriceValue = findViewById(R.id.priceValue);
        date = findViewById(R.id.date);
        selectCar = findViewById(R.id.selectCar);
        desc = findViewById(R.id.desc);
        startTime = findViewById(R.id.startTime);
        EndTime = findViewById(R.id.endTime);

        mSharedPreferences = getSharedPreferences("tokenDetail", MODE_PRIVATE);
        Language = mSharedPreferences.getString(Constant.language, Locale.getDefault().getLanguage());
        token = DataManager.getInstance().getCashedAccessToken().getAccess_token();
        mSubscriptions = new CompositeSubscription();
        hud = KProgressHUD.create(OfferRide.this)
                .setStyle(KProgressHUD.Style.PIE_DETERMINATE)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .setCancellable(false)
                .setMaxProgress(100);


        //Toast.makeText(this, mSharedPreferences.getString(Constant.IdentityCardPhoto, "")+"", Toast.LENGTH_SHORT).show();
        if (Objects.equals(mSharedPreferences.getString(Constant.IdentityCardPhoto, ""), "")) {

            android.support.v7.app.AlertDialog.Builder builder;
            android.support.v7.app.AlertDialog dialog;

            builder = new android.support.v7.app.AlertDialog.Builder(OfferRide.this);
            @SuppressLint("InflateParams")
            View mview =  OfferRide.this.getLayoutInflater().inflate(R.layout.dialog_verify, null);

            builder.setView(mview);
            dialog = builder.create();
            Window window = dialog.getWindow();
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            if (window != null) {
                window.setGravity(Gravity.CENTER);
            }
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


            dialog.show();
            TextView cancel = mview.findViewById(R.id.cancel);
            TextView verify = mview.findViewById(R.id.verify);
            //phone_number.setText(rowItem.get(position).getPhone_num());
            verify.setOnClickListener(v2 -> {
                Intent intent1 = new Intent(OfferRide.this,Verification.class);
                startActivity(intent1);
            });
            cancel.setOnClickListener(v3-> {
                dialog.dismiss();
                Constant.home_position="1";
                Intent intent = new Intent(OfferRide.this, Home.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                finish();
                //Toast.makeText(c, "ddd", Toast.LENGTH_SHORT).show();
            });
        }

        if (token.equals("")) {
            Intent intent = new Intent(OfferRide.this, Login.class);
            startActivity(intent);
            overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
            finish();
        }


        Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        startingDate = getDateWithUTCZone(dateFormat.format(c.getTime()));
        startDateCurrent = getDateWithUCurrentZone(dateFormat.format(c.getTime()));
        //Toast.makeText(this,startingDate , Toast.LENGTH_SHORT).show();


        date.setText(startDateCurrent);

        date.setOnClickListener(v -> {
            DatePickerDialog mDatePicker = new DatePickerDialog(OfferRide.this, (datepicker, selectedyear, selectedmonth, selectedday) -> {
                mcurrentDate = Calendar.getInstance();
                mcurrentDate.set(Calendar.YEAR, selectedyear);
                mcurrentDate.set(Calendar.MONTH, selectedmonth);
                mcurrentDate.set(Calendar.DAY_OF_MONTH, selectedday);
                date.setText(getDateWithUTCZone(dateFormat.format(mcurrentDate.getTime())));
                mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);
                mMonth = mcurrentDate.get(Calendar.MONTH);
                mYear = mcurrentDate.get(Calendar.YEAR);

                startingDate = getDateWithUTCZone(dateFormat.format(mcurrentDate.getTime()));
                startDateCurrent = dateFormat.format(mcurrentDate.getTime());


            }, mYear, mMonth, mDay);
            //mDatePicker.setTitle("Select date");
            mDatePicker.show();
        });

       // b = findViewById(R.id.year);
       // startTime.setText(hour + ":" + minute);
        startTime.setOnClickListener(v -> showYearDialog());

        selectCar.setOnClickListener(v -> {
            AlertDialog.Builder builder1;
            final AlertDialog dialog1;
            builder1 = new AlertDialog.Builder(OfferRide.this);
            View mview = getLayoutInflater().inflate(R.layout.area_dialog, null);
            TextView  header = mview.findViewById(R.id.DialogHeader);
            TextView  all = mview.findViewById(R.id.All);
            all.setVisibility(View.GONE);

            header.setText(getString(R.string.select_car_type));

            DialogRecyclerView = mview.findViewById(R.id.Recycler_Dialog_cities);
            filterAreaAdapter1 = new filterAreaAdapterForCountryCode(DialogList, getApplicationContext());
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), OrientationHelper.VERTICAL, false);

            DialogRecyclerView.setLayoutManager(linearLayoutManager);
            DialogRecyclerView.setItemAnimator(new DefaultItemAnimator());
            if (modelCars.size()==0)
            {
                if (Language.equals("ar"))
                {
                    Toast.makeText(this, "لم تقم بإضافة سيارة من قبل", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(this, "You have not added a car before", Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                initionilizationOFFilterCarType();
                DialogRecyclerView.setAdapter(filterAreaAdapter1);
                builder1.setView(mview);
                dialog1 = builder1.create();
                Window window = dialog1.getWindow();
                window.setGravity(Gravity.CENTER);
                dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                dialog1.show();
                filterAreaAdapter1.setOnItemClickListener(v1 -> {
                    selectCar_Position = DialogRecyclerView.getChildAdapterPosition(v1);
                    //String position1 = (String.valueOf(position));
//                        AreaID = (Integer.parseInt(DialogList.get(position).ID));
//                        SelectedAreaName = DialogList.get(position).shopName;
//                        select_Area_All.setText(SelectedAreaName);
                    //String countrylogo = DialogList.get(position).flag;
                    //setImg(countrylogo,countryLogo);

                    selectCar_Id=String.valueOf(DialogList.get(selectCar_Position).id);
                    flagCarVerified=String.valueOf(DialogList.get(selectCar_Position).flag);
                    selectCar.setText(String.valueOf(DialogList.get(selectCar_Position).CityName));
                    seat_num.setText(String.valueOf(modelCars.get(selectCar_Position).getSeatCount()));

                    //phoneCode=DialogList.get(position).CityName;
                    dialog1.dismiss();

                });
            }

        });

        autocompleteFrom=findViewById(R.id.autocomplete_from);
        Typeface tf1 = Typeface.createFromAsset(getAssets(), "Cairo-Regular.ttf");
        autocompleteFrom.setTypeface(tf1);

        autocompleteTo=findViewById(R.id.autocomplete_to);
        Typeface tf2 = Typeface.createFromAsset(getAssets(), "Cairo-Regular.ttf");
        autocompleteTo.setTypeface(tf2);

        // Initialize Places.
        Places.initialize(getApplicationContext(), "AIzaSyCDtUDYrRcdQh-Ay7PwI-goQ82nZmN4MhQ");
        // Create a new Places client instance.
        PlacesClient placesClient = Places.createClient(this);

        autocompleteFragment1 = (AutocompleteSupportFragment) getSupportFragmentManager().findFragmentById(R.id.place_autocomplete_fragment_src);
        autocompleteFragment1.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG));

        autocompleteFragment1.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                Constant.sLat=String .valueOf(place.getLatLng().latitude);
                Constant.sLng=String .valueOf(place.getLatLng().longitude);
                Constant.status="from";
                getAddressCityAR(place.getLatLng().latitude,place.getLatLng().longitude);
                FromCaption= Objects.requireNonNull(place.getAddress());
                autocompleteFrom.setText(Objects.requireNonNull(place.getAddress()));
                Intent intent1 =new Intent(getApplicationContext(),SetOnMapOfferRide.class);
                startActivity(intent1);
            }
            @Override
            public void onError(@NonNull Status status) {
                // TODO: Handle the error.
                //Log.i(TAG, "An error occurred: " + status);
                Toast.makeText(OfferRide.this, status.getStatusMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        autocompleteFragment2 = (AutocompleteSupportFragment) getSupportFragmentManager().findFragmentById(R.id.place_autocomplete_fragment_dis);
        autocompleteFragment2.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG));
        autocompleteFragment2.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                Constant.dLat=String .valueOf(place.getLatLng().latitude);
                Constant.dLng=String .valueOf(place.getLatLng().longitude);

                Constant.status="to";
                getAddressCityAR(place.getLatLng().latitude,place.getLatLng().longitude);
                ToCaption= Objects.requireNonNull(place.getAddress()).toString();
                autocompleteTo.setText(Objects.requireNonNull(place.getAddress()).toString());
                Intent intent1 =new Intent(getApplicationContext(),SetOnMapOfferRide.class);
                startActivity(intent1);
            }

            @Override
            public void onError(@NonNull Status status) {
                // TODO: Handle the error.
                //Log.i(TAG, "An error occurred: " + status);
                Toast.makeText(OfferRide.this, status.getStatusMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        if(Language.equals("ar"))
        {
            autocompleteFragment1.setHint("من اين ستبدأ الرحلة؟");
        }
        else
        {
            autocompleteFragment1.setHint("Set Starting Location");
        }

        autocompleteFrom.setOnClickListener(view -> {
            View view1=autocompleteFragment1.getView();
            if (view1 != null) {
                view1.post(() -> view1.findViewById(R.id.places_autocomplete_search_input).performClick());
            }
        });


        if(Language.equals("ar"))
        {
            autocompleteFragment2.setHint("اين ستنتهي الرحلة؟");
        }
        else
        {
            autocompleteFragment2.setHint("Set End Location");
        }

        autocompleteTo.setOnClickListener(view -> {
            View view1=autocompleteFragment2.getView();
            if (view1 != null) {
                view1.post(() -> view1.findViewById(R.id.places_autocomplete_search_input).performClick());
            }
        });

        minus_seat_price.setOnClickListener(v -> {
            try {
                int p = Integer.parseInt(seatprice.getText().toString());
                p = p - 1;
                if (p < 1) {
                    if (Language.equals("ar"))
                    {
                        Toast.makeText(this, "يجب ان يكون سعر المقعد علي الاقل واحد ريال", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(OfferRide.this, "must be greater than Zero", Toast.LENGTH_SHORT).show();
                    }
                    //Toast.makeText(OfferRide.this, "must be greater than Zero", Toast.LENGTH_SHORT).show();
                } else {
                    seatprice.setText(String.valueOf(p));
                }
            } catch (Exception e) {
                Toast.makeText(OfferRide.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });
        plus_seat_price.setOnClickListener(v -> {
            try {
                int p = Integer.parseInt(seatprice.getText().toString());
                p = p + 1;
                seatprice.setText(String.valueOf(p));
            }
            catch (Exception e)
            {
                Toast.makeText(OfferRide.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        minus_seat_num.setOnClickListener(v -> {
            try {
                if (selectCar_Id.equals(""))
                {
                    if (Language.equals("ar"))
                    {
                        Toast.makeText(this, "من فضلك اضف سيارة اولا", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(this, "please add car first", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    int p = Integer.parseInt(seat_num.getText().toString());
                    p = p - 1;
                    if (p<1)
                    {
                        if (Language.equals("ar"))
                        {
                            Toast.makeText(this, "يجب ان يكون علي الاقل مقعد واحد", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(OfferRide.this, "must be greater than Zero", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        seat_num.setText(String.valueOf(p));
                    }
                }
            }
            catch (Exception e)
            {
                Toast.makeText(OfferRide.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        plus_seat_num.setOnClickListener(v -> {

            try {
                if (selectCar_Id.equals(""))
                {
                    if (Language.equals("ar"))
                    {
                        Toast.makeText(this, "من فضلك اضف سيارة اولا", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(this, "please add car first", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    int p = Integer.parseInt(seat_num.getText().toString());
                    p = p + 1;
                    if (p>Integer.parseInt(modelCars.get(selectCar_Position).getSeatCount()))
                    {
                        if (Language.equals("ar"))
                        {
                            Toast.makeText(this, "هذا اقصي عدد مسموح به", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(this, "This is the maximum number allowed", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        seat_num.setText(String.valueOf(p));
                    }
                    //seat_num.setText(String.valueOf(p));
                }
            }
            catch (Exception e)
            {
                Toast.makeText(OfferRide.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        back_seat_lin.setOnClickListener(v -> {
            try {

                flag = !flag;
                if (flag) {
                    back_seat.setImageDrawable(getResources().getDrawable(R.drawable.ic_verified));
                }
                else {
                    back_seat.setImageDrawable(getResources().getDrawable(R.drawable.button_shape_border_gray));
                }
            }
            catch (Exception e)
            {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        offer_ride.setOnClickListener(v -> {
            if (Validation.isConnected(OfferRide.this))
            {
                if (Language.equals("ar"))
                {
                    if (Constant.sLat.equals(""))
                    {
                        Toast.makeText(this, "من فضلك اختر مكان بدء الرحلة", Toast.LENGTH_SHORT).show();
                    }
                    else if (startingTime.equals(""))
                    {
                        Toast.makeText(this, "من فضلك اختر وقت بدء الرحلة", Toast.LENGTH_SHORT).show();
                    }
                    else if (Constant.dLat.equals(""))
                    {
                        Toast.makeText(this, "من فضلك اختر مكان انتهاء الرحلة", Toast.LENGTH_SHORT).show();
                    }
                    else if (startingDate.equals(""))
                    {
                        Toast.makeText(this, "من فضلك اختر يوم الرحلة", Toast.LENGTH_SHORT).show();
                    }
                    else if (seatprice.getText().toString().equals("0"))
                    {
                        Toast.makeText(this, "من فضلك اختر سعر الرحلة", Toast.LENGTH_SHORT).show();
                    }
                    else if (selectCar_Id.equals(""))
                    {
                        Toast.makeText(this, "من فضلك اضف سيارة اولا", Toast.LENGTH_SHORT).show();
                    }
                    else if (seat_num.getText().toString().equals("0"))
                    {
                        Toast.makeText(this, "من فضلك اختر عدد المقاعد", Toast.LENGTH_SHORT).show();
                    }
                    else if (desc.getText().toString().equals(""))
                    {
                        Toast.makeText(this, "من فضلك ادخل تفاصيل عن الرحلة", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                       try {
                           String SourceCity = Constant.sCityOffer;
                           String DistinationCity = Constant.dCityOffer;
                           AddTripRequest addTripRequest = new AddTripRequest();
                           addTripRequest.setFromCaption(FromCaption);
                           addTripRequest.setToCaption(ToCaption);
                           addTripRequest.setFromLatitude(Constant.sLat);
                           addTripRequest.setFromLongitude(Constant.sLng);
                           addTripRequest.setToLatitude(Constant.dLat);
                           addTripRequest.setToLongitude(Constant.dLng);
                           addTripRequest.setStartTime(startingTime);
                           addTripRequest.setStartDate(startingDate);
                           addTripRequest.setEndTime(endTime);
                           addTripRequest.setEndDate(endDate);
                           addTripRequest.setDurationInSeconds(String.valueOf(second));
                           double d = Double.parseDouble(Constant.distance) / 1000;
                           addTripRequest.setExpectedDistance(String.valueOf(d));
                           double ExpectedCost = d * kmPrice;
                           addTripRequest.setExpectedCost(String.format(Locale.ENGLISH,"%.2f", ExpectedCost));
                           double realcostWthoutCommision = Double.parseDouble(seatprice.getText().toString());
                           double realcost=realcostWthoutCommision+(realcostWthoutCommision*Percentage/100);
                           addTripRequest.setRealCost(realcost+"");
                           addTripRequest.setRealCostWithoutCommission(realcostWthoutCommision+"");
                           addTripRequest.setSeatCount(seat_num.getText().toString());
                           addTripRequest.setIs3PassengerBack(String.valueOf(flag));
                           addTripRequest.setDescription(desc.getText().toString());
                           addTripRequest.setCarId(selectCar_Id);
                           addTripRequest.setSourceCity(SourceCity);
                           addTripRequest.setDistinationCity(DistinationCity);

                           AddTrip(addTripRequest);
                       }catch (Exception e){}
                    }
                }
                else
                {
                    if (Constant.sLat.equals(""))
                    {
                        Toast.makeText(this, "please Set Starting Location", Toast.LENGTH_SHORT).show();
                    }
                    else if (startingTime.equals(""))
                    {
                        Toast.makeText(this, "please Set Starting Time", Toast.LENGTH_SHORT).show();
                    }
                    else if (Constant.dLat.equals(""))
                    {
                        Toast.makeText(this, "please Set End Location", Toast.LENGTH_SHORT).show();
                    }
                    else if (startingDate.equals(""))
                    {
                        Toast.makeText(this, "please Set Data", Toast.LENGTH_SHORT).show();
                    }
                    else if (seatprice.getText().toString().equals("0"))
                    {
                        Toast.makeText(this, "please Set seatprice", Toast.LENGTH_SHORT).show();
                    }
                    else if (selectCar_Id.equals(""))
                    {
                        Toast.makeText(this, "please add car first", Toast.LENGTH_SHORT).show();
                    }
                    else if (seat_num.getText().toString().equals("0"))
                    {
                        Toast.makeText(this, "please Set seat Number", Toast.LENGTH_SHORT).show();
                    }
                    else if (desc.getText().toString().equals(""))
                    {
                        Toast.makeText(this, "please Enter description", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                         try {
                             String SourceCity = Constant.sCityOffer;
                             String DistinationCity = Constant.dCityOffer;
                             AddTripRequest addTripRequest = new AddTripRequest();
                             addTripRequest.setFromCaption(FromCaption);
                             addTripRequest.setToCaption(ToCaption);
                             addTripRequest.setFromLatitude(Constant.sLat);
                             addTripRequest.setFromLongitude(Constant.sLng);
                             addTripRequest.setToLatitude(Constant.dLat);
                             addTripRequest.setToLongitude(Constant.dLng);
                             addTripRequest.setStartTime(startingTime);
                             addTripRequest.setStartDate(startingDate);
                             addTripRequest.setDurationInSeconds(String.valueOf(second));
                             addTripRequest.setEndTime(endTime);
                             addTripRequest.setEndDate(endDate);
                             double d = Double.parseDouble(Constant.distance) / 1000;
                             addTripRequest.setExpectedDistance(String.valueOf(d));
                             double p = d * kmPrice;
                             addTripRequest.setExpectedCost(String.format(Locale.ENGLISH,"%.2f", p));
                               addTripRequest.setRealCostWithoutCommission(seatprice.getText().toString());
                             addTripRequest.setSeatCount(seat_num.getText().toString());
                             addTripRequest.setIs3PassengerBack(String.valueOf(flag));
                             addTripRequest.setDescription(desc.getText().toString());
                             addTripRequest.setCarId(selectCar_Id);
                             addTripRequest.setSourceCity(SourceCity);
                             addTripRequest.setDistinationCity(DistinationCity);

                             double realcostWthoutCommision = Double.parseDouble(seatprice.getText().toString());
                             double realcost=realcostWthoutCommision+(realcostWthoutCommision*Percentage/100);
                             addTripRequest.setRealCost(realcost+"");
                             addTripRequest.setRealCostWithoutCommission(realcostWthoutCommision+"");
                             AddTrip(addTripRequest);
                         }catch (Exception e){}
                    }
                }
            }
            else
            {
                buildDialog(OfferRide.this).show().setCanceledOnTouchOutside(false);
            }
        });

        if (Validation.isConnected(OfferRide.this))
        {
            hud.show();
            mSubscriptions.add(NetworkUtil.getRetrofitByToken(token)
                    .getAllCars()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResponsegetAllCars, this::handleError));
        }
        else
        {
            buildDialog(OfferRide.this).show().setCanceledOnTouchOutside(false);
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

    private void showYearDialog() {


        android.support.v7.app.AlertDialog.Builder builder;
        android.support.v7.app.AlertDialog d;
        builder = new android.support.v7.app.AlertDialog.Builder(this);

        @SuppressLint("InflateParams")
        View mview = getLayoutInflater().inflate(R.layout.dialog_timer, null);
        builder.setView(mview);
        d = builder.create();
        Window window = d.getWindow();
        if (window != null) {
            window.setGravity(Gravity.CENTER);
        }
        d.show();


        Button set = d.findViewById(R.id.button1);

        Button cancel = d.findViewById(R.id.button2);

        //TextView year_text = d.findViewById(R.id.title_text);

        // title_text.setText(hour+":"+minute);

        final NumberPicker nopicker = d.findViewById(R.id.numberPicker1);

        final NumberPicker nopicker1 = d.findViewById(R.id.numberPicker2);

        if (nopicker != null) {
            nopicker.setMaxValue(23);
            nopicker.setMinValue(0);
            nopicker.setWrapSelectorWheel(false);
            nopicker.setValue(hour);
            nopicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        }


        if (nopicker1 != null) {
            nopicker1.setMaxValue(59);
            nopicker1.setMinValue(0);
            nopicker1.setWrapSelectorWheel(false);
            nopicker1.setValue(minute);
            nopicker1.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        }


        if (nopicker != null) {
            nopicker.setOnValueChangedListener((picker, oldVal, newVal) -> {

                //Display the newly selected number from picker

                //  tv.setText("Selected Number : " + newVal);

                if (nopicker.getValue() == hour) {

                    nopicker1.setMinValue(minute);

                } else {

                    nopicker1.setMinValue(0);

                }

            });
        }


        if (set != null) {
            set.setOnClickListener(v -> {

                second = nopicker.getValue() * 3600;
                second += nopicker1.getValue() * 60;
                totalSeconds = second;
                startingTime = getTimeWithUTCZone(startDateCurrent + " " + nopicker.getValue() + ":" + nopicker1.getValue() + ":00");
                //Toast.makeText(this, startingTime, Toast.LENGTH_SHORT).show();
                //Toast.makeText(this, selectedHour + ":" + selectedMinute+":00", Toast.LENGTH_SHORT).show();
                startTime.setText(nopicker.getValue() + ":" + nopicker1.getValue() + ":00");

                if (!Constant.time.equals("0")) {

                    try
                    {
                        second = Integer.parseInt(Constant.time);
                    }catch (Exception e){  d.dismiss();}
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
                    SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                    SimpleDateFormat dateFormat3 = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);


                    Calendar cal = Calendar.getInstance();
                    try {
                        cal.setTime(dateFormat.parse(startingDate + " " + startingTime));
                    } catch (ParseException e) {
                        Toast.makeText(OfferRide.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
               /* cal.add(Calendar.DATE, numberOfDays);  // number of days to add
                cal.add(Calendar.HOUR, numberOfHours);  // number of hours to add
                cal.add(Calendar.MINUTE, numberOfMinutes);  // number of minutes to add*/
                   try{ cal.add(Calendar.SECOND, Integer.parseInt(Constant.time));}catch (Exception e){} // number of minutes to add
                    endDate = dateFormat2.format(cal.getTime());  // dt is now the new date
                    endTime = dateFormat3.format(cal.getTime());  // dt is now the new date
                    //endTime=TerhalUtils.getTimeWithUTCZone(endDate+" "+numberOfHours + ":" + numberOfMinutes+":00");
                    EndTime.setText(TerhalUtils.getTimeWithCurrentZone(dateFormat.format(cal.getTime())));


                }
                d.dismiss();

            });
        }

        if (cancel != null) {
            cancel.setOnClickListener(v -> d.dismiss());
        }
        d.show();
    }




    private void AddTrip(AddTripRequest addTripRequest)
    {
        if (flagCarVerified.equals("true"))
        {
            hud.show();
            mSubscriptions.add(NetworkUtil.getRetrofitByToken(token)
                    .AddTrip(addTripRequest)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResponse, this::handleError));
        }
        else
        {
            android.support.v7.app.AlertDialog.Builder builder;
            android.support.v7.app.AlertDialog dialog;

            builder = new android.support.v7.app.AlertDialog.Builder(this);
            @SuppressLint("InflateParams")
            View mview = getLayoutInflater().inflate(R.layout.dialog_success, null);

            builder.setView(mview);
            dialog = builder.create();
            Window window = dialog.getWindow();

            if (window != null) {
                window.setGravity(Gravity.CENTER);
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }

            //Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
            TextView txt = mview.findViewById(R.id.txt);
            TextView ok = mview.findViewById(R.id.ok);
            txt.setText(getString(R.string.car_not_verified));
            ok.setOnClickListener(v2 -> dialog.dismiss());
        }
    }

    private void handleResponse(AddTripResponseModel addTripResponseModel) {
        if (addTripResponseModel.getModel()!=null)
        {
            Constant.sLat= "";
            Constant.sLng= "";
            Constant.dLat= "";
            Constant.dLng= "";
            Constant.dCityOffer= "";
            Constant.sCityOffer= "";
            Constant.time= "";
            Constant.distance= "";
            Constant.home_position="0";

            String message="";
            if (Language.equals("ar"))
            {
                message="تم إنشاء الرحلة بنجاح";
            }
            else
            {
                message="Trip created successfully";
            }

            android.support.v7.app.AlertDialog.Builder builder;
            android.support.v7.app.AlertDialog dialog;
            builder = new android.support.v7.app.AlertDialog.Builder(this);

            @SuppressLint("InflateParams")
            View mview = getLayoutInflater().inflate(R.layout.dialog_success, null);
            builder.setView(mview);
            dialog = builder.create();
            Window window = dialog.getWindow();
            if (window != null) {
                window.setGravity(Gravity.CENTER);
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                //Toast.makeText(this, "dd", Toast.LENGTH_SHORT).show();
            }

            dialog.show();
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            TextView txt = mview.findViewById(R.id.txt);
            TextView ok = mview.findViewById(R.id.ok);
            txt.setText(message);
            ok.setOnClickListener(v2 -> {
                dialog.dismiss();
                Intent intent = new Intent(OfferRide.this, Home.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                finish();
            });
        }
        hud.dismiss();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!Constant.distance.equals(""))
        {
           // Toast.makeText(this, "resume", Toast.LENGTH_SHORT).show();
            try
            {
                double d = Double.parseDouble(Constant.distance) / 1000;
                distanceValue.setText( String.format(Locale.ENGLISH,"%.2f", d));
                int seatExpectedprice = (int) (d * kmPrice);

                seatExpectedpriceValue.setText(String.valueOf(seatExpectedprice));


                seatprice.setText(String.valueOf(seatExpectedprice));
            }catch (Exception e){}

        }
       /* if (!Constant.sLat.equals(""))
        {
            if(Language.equals("ar"))
            {
                autocompleteFragment1.setText(getAddressAr(Double.parseDouble(Constant.sLat),Double.parseDouble(Constant.sLng)));
                //autocompleteFragment1.setHint("من اين ستبدأ الرحلة؟");
            }
            else
            {
                autocompleteFragment1.setText(getAddress(Double.parseDouble(Constant.sLat),Double.parseDouble(Constant.sLng)));
                //autocompleteFragment1.setHint("Set Starting Location");
            }
        }
        if (!Constant.dLat.equals(""))
        {
            if(Language.equals("ar"))
            {
                autocompleteFragment2.setText(getAddressAr(Double.parseDouble(Constant.dLat),Double.parseDouble(Constant.dLng)));
                //autocompleteFragment1.setHint("من اين ستبدأ الرحلة؟");
            }
            else
            {
                autocompleteFragment2.setText(getAddress(Double.parseDouble(Constant.dLat),Double.parseDouble(Constant.dLng)));
                //autocompleteFragment1.setHint("Set Starting Location");
            }
        }*/
        if (!Constant.time.equals("0"))
        {
            if (second>0)
            {

                /*second=totalSeconds+Integer.parseInt(Constant.time);

                int numberOfDays;
                int numberOfHours;
                int numberOfMinutes;
                int numberOfSeconds;

                numberOfDays = second / 86400;
                numberOfHours = (second % 86400 ) / 3600 ;
                numberOfMinutes = ((second % 86400 ) % 3600 ) / 60;
                //numberOfSeconds = ((second % 86400 ) % 3600 ) % 60;


                daysExtra= String.valueOf(numberOfDays);

                endDate = startingDate;  // Start date
*/
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
                SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                SimpleDateFormat dateFormat3 = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);



                Calendar cal = Calendar.getInstance();
                try {
                    cal.setTime(dateFormat.parse(startingDate+" "+startingTime));
                } catch (ParseException e) {
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                   /* cal.add(Calendar.DATE, numberOfDays);  // number of days to add
                    cal.add(Calendar.HOUR, numberOfHours);  // number of hours to add
                    cal.add(Calendar.MINUTE, numberOfMinutes);  // number of minutes to add*/
                   try {
                       cal.add(Calendar.SECOND, Integer.parseInt(Constant.time));  // number of minutes to add
                   }catch (Exception e){}
                endDate = dateFormat2.format(cal.getTime());  // dt is now the new date
                endTime = dateFormat3.format(cal.getTime());  // dt is now the new date
                //endTime=TerhalUtils.getTimeWithUTCZone(endDate+" "+numberOfHours + ":" + numberOfMinutes+":00");
                EndTime.setText(TerhalUtils.getTimeWithCurrentZone(dateFormat.format(cal.getTime())));
            }
        }
    }
    public void onBackPressed(){
        Constant.sLat= "";
        Constant.sLng= "";
        Constant.dLat= "";
        Constant.dLng= "";
        Constant.dCityOffer= "";
        Constant.sCityOffer= "";
        Constant.time= "";
        Constant.distance= "";
        Constant.home_position="0";
        finish();

    }
    private void handleError(Throwable throwable) {
        //Toast.makeText(getActivity(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
        String message="";
        try {
            if (throwable instanceof HttpException) {
                HttpException error = (HttpException) throwable;
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
                        String wasl_27 = jsonObject.getJSONObject("errors").getString("message");

                        JSONObject obj_27 = new JSONObject(wasl_27.replace("\\",""));
                        String waslCode_27 =obj_27.getString("resultCode");

                        switch (waslCode_27) {
                            case "413":
                                message = getString(R.string.wasl_error_addTrip_413);
                                break;
                            case "420":
                                message = getString(R.string.wasl_error_addTrip_420);
                                break;
                            case "421":
                                message = getString(R.string.wasl_error_addTrip_421);
                                break;
                            default:
                                message = getString(R.string.default_message);
                        }

                        // message = waslCode_27;
                        break;
                    case "28":
                        String wasl_28 = jsonObject.getJSONObject("errors").getString("message");
                        JSONObject obj_28 = new JSONObject(wasl_28.replace("\\",""));
                        String waslCode_28 =obj_28.getString("resultCode");
                        switch (waslCode_28) {
                            case "413":
                                message = getString(R.string.wasl_error_addTrip_413);
                                break;
                            case "420":
                                message = getString(R.string.wasl_error_addTrip_420);
                                break;
                            case "421":
                                message = getString(R.string.wasl_error_addTrip_421);
                                break;
                            default:
                                message = getString(R.string.default_message);
                        }

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
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        //Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        TextView txt = mview.findViewById(R.id.txt);
        TextView ok = mview.findViewById(R.id.ok);
        txt.setText(message);
        ok.setOnClickListener(v2 -> dialog.dismiss());
        hud.dismiss();
    }

    public String getAddress(double lat, double lng) {
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
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return add;
    }
    public String getAddressAr(double lat, double lng) {
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
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return add;
    }
    private void getAddressCityAR(double lat, double lng) {
        String Latlng=String.valueOf(lat).concat(",").concat(String.valueOf(lng));
        try {
            mSubscriptions.add(NetworkUtil.getRetrofit_Get_Address()
                    .getCityAR(Latlng,"ar", getString(R.string.google_maps_key))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResponsegetCityAR, this::handleError));
        }
        catch (Exception e)
        {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
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
                            Constant.sCityOffer=searchResponseModel.getResults().get(0).getAddress_components().get(i).getLong_name();
                        }
                        else if (Constant.status.equals("to"))
                        {
                            Constant.dCityOffer=searchResponseModel.getResults().get(0).getAddress_components().get(i).getLong_name();
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
                                Constant.sCityOffer=searchResponseModel.getResults().get(0).getAddress_components().get(i).getLong_name();
                            }
                            else if (Constant.status.equals("to"))
                            {
                                Constant.dCityOffer=searchResponseModel.getResults().get(0).getAddress_components().get(i).getLong_name();
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
                                Constant.sCityOffer=searchResponseModel.getResults().get(0).getAddress_components().get(i).getLong_name();
                            }
                            else if (Constant.status.equals("to"))
                            {
                                Constant.dCityOffer=searchResponseModel.getResults().get(0).getAddress_components().get(i).getLong_name();
                            }
                        }
                    }
                }
            }
        }
        /*if (Constant.status.equals("from"))
        {
            Toast.makeText(this,  "from", Toast.LENGTH_SHORT).show();
            Toast.makeText(this,  Constant.sCityOffer, Toast.LENGTH_SHORT).show();
        }
        else if (Constant.status.equals("to"))
        {
            Toast.makeText(this,  "to", Toast.LENGTH_SHORT).show();
            Toast.makeText(this,  Constant.dCityOffer, Toast.LENGTH_SHORT).show();
        }*/

    }


    private void handleResponsegetAllCars(getAllCarsResponse getAllCarsResponse) {
        if (getAllCarsResponse.getModel()!=null)
        {
            if (getAllCarsResponse.getModel().size()>0)
            {
                modelCars=getAllCarsResponse.getModel();
                if (Language.equals("ar"))
                {
                    selectCar.setText(getAllCarsResponse.getModel().get(0).getCarModel().getNameLT());
                }
                else
                {
                    selectCar.setText(getAllCarsResponse.getModel().get(0).getCarModel().getName());
                }
                selectCar_Id=getAllCarsResponse.getModel().get(0).getId();
                flagCarVerified=String.valueOf(getAllCarsResponse.getModel().get(0).getVerified());
                selectCar_Position=0;

                mSubscriptions.add(NetworkUtil.getRetrofitByToken(DataManager.getInstance().getCashedAccessToken().getAccess_token())
                    .metaData()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResponseMetaData, this::handleError));
            }
            else
            {
                android.support.v7.app.AlertDialog.Builder builder;
                android.support.v7.app.AlertDialog dialog;

                builder = new android.support.v7.app.AlertDialog.Builder(OfferRide.this);
                @SuppressLint("InflateParams")
                View mview =  OfferRide.this.getLayoutInflater().inflate(R.layout.dialog_add_car, null);

                builder.setView(mview);
                dialog = builder.create();
                Window window = dialog.getWindow();
                if (window != null) {
                    window.setGravity(Gravity.CENTER);
                }
                dialog.setCancelable(false);
                dialog.setCanceledOnTouchOutside(false);
                Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                dialog.show();
                TextView cancel = mview.findViewById(R.id.cancel);
                TextView add_car = mview.findViewById(R.id.add_car);
                //phone_number.setText(rowItem.get(position).getPhone_num());
                add_car.setOnClickListener(v2 -> {
                    Intent intent1 = new Intent(OfferRide.this,UploadCarPhoto.class);
                    carStatus="1";
                    startActivity(intent1);
                });
                cancel.setOnClickListener(v3-> {
                    dialog.dismiss();
                    Constant.home_position="1";
                    Intent intent = new Intent(OfferRide.this, Home.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                    finish();
                });
            }
        }
        hud.dismiss();
    }
    private void handleResponseMetaData(MetaDataResponseModel metaDataResponseModel) {
        //Toast.makeText(this, "11", Toast.LENGTH_SHORT).show();
        if (metaDataResponseModel.getModel()!=null)
        {
            if (metaDataResponseModel.getModel().getSettings()!=null)
            {
                Percentage   = Double.parseDouble(metaDataResponseModel.getModel().getSettings().get(0).getPercentage());
                kmPrice   = Double.parseDouble(metaDataResponseModel.getModel().getSettings().get(0).getKMPrice());
            }
        }
        hud.dismiss();
    }

    private void initionilizationOFFilterCarType() {
        DialogList.clear();
        try {
            for (int k=0 ;k<modelCars.size();k++)
            {
                String name="";
                if (Language.equals("en"))
                {
                    name=modelCars.get(k).getCarModel().getName();
                }
                else
                {
                    name=modelCars.get(k).getCarModel().getNameLT();
                }
                DialogList.add(new filterAreaModelRecycler(
                        name
                        ,String.valueOf(modelCars.get(k).getVerified())
                        ,""
                        ,Integer.parseInt(modelCars.get(k).getId())));
            }
            filterAreaAdapter1.update(DialogList);
        }
        catch (Exception e)
        {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    @SuppressLint("DefaultLocale")
    String secToTime(int sec) {
        int seconds = sec % 60;
        int minutes = sec / 60;
        if (minutes >= 60) {
            int hours = minutes / 60;
            minutes %= 60;
            if( hours >= 24) {
                int days = hours / 24;
                return String.format("%d days %02d:%02d:%02d", days,hours%24, minutes, seconds);
            }
            return String.format("%02d:%02d:%02d", hours, minutes, seconds);
        }
        return String.format("00:%02d:%02d",
                minutes, seconds);
    }
    @Override
    public void onClick(View v) {

    }
}
