package com.NativeTech.rehla.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.NativeTech.rehla.model.DataManager;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import com.NativeTech.rehla.model.data.dto.Models.Driver.updateTripStatusResponse;
import com.NativeTech.rehla.model.data.dto.Models.MetaDataModels.MetaDataResponseModel;
import com.NativeTech.rehla.model.data.dto.Models.TripDetails.getTripDetailsResponse;
import com.NativeTech.rehla.model.data.dto.Models.addReservation.addReservationRequest;
import com.NativeTech.rehla.Network.NetworkUtil;
import com.NativeTech.rehla.R;
import com.NativeTech.rehla.Utills.Constant;
import com.NativeTech.rehla.Utills.TerhalUtils;
import com.NativeTech.rehla.Utills.Validation;
import com.NativeTech.rehla.adapters.PassengerModel;
import com.NativeTech.rehla.adapters.RecyclerViewAdapterPassenger;
import com.NativeTech.rehla.adapters.filterAreaAdapterForCountryCode;
import com.NativeTech.rehla.adapters.filterAreaModelRecycler;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static com.NativeTech.rehla.Utills.Constant.TripDetailsCarColor;
import static com.NativeTech.rehla.Utills.Constant.buildDialog;
import static com.NativeTech.rehla.Utills.Constant.dLatSearch;
import static com.NativeTech.rehla.Utills.Constant.dLngSearch;
import static com.NativeTech.rehla.Utills.Constant.sLatSearch;
import static com.NativeTech.rehla.Utills.Constant.sLngSearch;

public class TripDetails extends AppCompatActivity {

    private CircleImageView     img;
    private TextView            name;
    private TextView            car_type;
    private TextView            car_color;
    private TextView            reviews_count;
    private RatingBar           rate;
    private TextView            from_date;
    private TextView            from_address;
    private TextView            to_date;
    private TextView            to_address;
    private TextView            date;
    private TextView            between;
    private TextView            seat_count;
    private TextView            distance;
    private TextView            desc;
    private TextView            phone;
    private TextView            email;
    private TextView            licence;
    private AppCompatImageView  phone_verified;
    private AppCompatImageView  email_verified;
    private AppCompatImageView  licence_verified;
    private TextView            price;
    private TextView            fee;
    private TextView            total_price;

    private AppCompatImageView  smoking;
    private AppCompatImageView  air_conditioner;
    private AppCompatImageView  music;
    private AppCompatImageView  speaking;
    private AppCompatImageView  charger;
    private AppCompatImageView  Pets;


    private LinearLayoutCompat track_on_map;

    private LinearLayoutCompat  minus_seat_num;
    private LinearLayoutCompat  plus_seat_num;
    private TextView            seat_num;
    private LinearLayoutCompat  profile;
    private TextView              RequestBook;


    private String Language;
    private CompositeSubscription mSubscriptions;
    private SharedPreferences mSharedPreferences;
    private KProgressHUD hud;


    private RecyclerView DialogRecyclerView;
    private filterAreaAdapterForCountryCode filterAreaAdapter1;
    private final ArrayList<filterAreaModelRecycler> DialogList = new ArrayList<>();
    private TextView selectPaymentType;
    private boolean selectPaymentType_ID=true;

    private String seat_number="1";
    private String Max_seat_number="1";

    private String user_id;


    private RecyclerView recyclerView;
    private List<PassengerModel> rowItem;

    private TextView no_exist;

    private double Percentage;
    private Toolbar toolbar;
    private double kMPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        setContentView(R.layout.activity_trip_details);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        recyclerView                 = findViewById(R.id.recyclerView);
        rowItem                                 = new ArrayList<>();



        no_exist                                = findViewById(R.id.no_exist);

        no_exist.setVisibility(View.GONE);


        img                         =findViewById(R.id.img);
        RequestBook                 =findViewById(R.id.RequestBook);

        selectPaymentType           =findViewById(R.id.selectPaymentType);

        name                        =findViewById(R.id.name);
        car_type                    =findViewById(R.id.car_type);
        car_color                   =findViewById(R.id.car_color);
        reviews_count               =findViewById(R.id.reviews_count);
        rate                        =findViewById(R.id.rate);
        from_date                   =findViewById(R.id.from_date);
        from_address                =findViewById(R.id.from_address);
        to_date                     =findViewById(R.id.to_date);
        to_address                  =findViewById(R.id.to_address);
        date                        =findViewById(R.id.date);
        between                     =findViewById(R.id.between);
        seat_count                  =findViewById(R.id.seat_count);
        distance                    =findViewById(R.id.distance);
        desc                        =findViewById(R.id.desc);
        smoking                     =findViewById(R.id.smoking);
        air_conditioner             =findViewById(R.id.air_conditioner);
        music                       =findViewById(R.id.music);
        speaking                    =findViewById(R.id.speaking);
        charger                     =findViewById(R.id.charger);
        Pets                        =findViewById(R.id.Pets);
        phone                       =findViewById(R.id.phone);
        email                       =findViewById(R.id.email);
        licence                     =findViewById(R.id.licence);
        phone_verified              =findViewById(R.id.phone_verified);
        email_verified              =findViewById(R.id.email_verified);
        licence_verified            =findViewById(R.id.licence_verified);
        price                       =findViewById(R.id.price);
        fee                         =findViewById(R.id.fee);
        total_price                 =findViewById(R.id.total_price);

        minus_seat_num              = findViewById(R.id.minus_seat_num);
        plus_seat_num               = findViewById(R.id.plus_seat_num);
        seat_num                    = findViewById(R.id.seat_num);
        profile                     = findViewById(R.id.profile);
        track_on_map                = findViewById(R.id.track_on_map);


        profile.setOnClickListener(v -> {
            Intent intent = new Intent(TripDetails.this, DriverDetails.class);
            Constant.captin_id=user_id;
            startActivity(intent);
        });


        track_on_map.setOnClickListener(v -> {
            Intent intent = new Intent(TripDetails.this, ViewOnMap.class);
            //Constant.captin_id=user_id;
            startActivity(intent);
        });

        selectPaymentType.setOnClickListener(v -> {
            AlertDialog.Builder builder1;
            final AlertDialog dialog1;
            builder1 = new AlertDialog.Builder(TripDetails.this);
            View mview = getLayoutInflater().inflate(R.layout.area_dialog, null);
            TextView header = mview.findViewById(R.id.DialogHeader);
            TextView all = mview.findViewById(R.id.All);
            all.setVisibility(View.GONE);

            header.setText(getString(R.string.paymenttype));

            DialogRecyclerView = mview.findViewById(R.id.Recycler_Dialog_cities);
            filterAreaAdapter1 = new filterAreaAdapterForCountryCode(DialogList, getApplicationContext());
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), OrientationHelper.VERTICAL, false);

            DialogRecyclerView.setLayoutManager(linearLayoutManager);
            DialogRecyclerView.setItemAnimator(new DefaultItemAnimator());
            initionilizationOFFilterCarType();

            DialogRecyclerView.setAdapter(filterAreaAdapter1);
            builder1.setView(mview);
            dialog1 = builder1.create();
            Window window = dialog1.getWindow();
            window.setGravity(Gravity.CENTER);
            dialog1.show();

            filterAreaAdapter1.setOnItemClickListener(v1 -> {
                int position = DialogRecyclerView.getChildAdapterPosition(v1);
                //String position1 = (String.valueOf(position));
//                        AreaID = (Integer.parseInt(DialogList.get(position).ID));
//                        SelectedAreaName = DialogList.get(position).shopName;
//                        select_Area_All.setText(SelectedAreaName);
                //String countrylogo = DialogList.get(position).flag;
                //setImg(countrylogo,countryLogo);

                if (DialogList.get(position).id==0)
                {
                    selectPaymentType_ID=true;
                }
                else if (DialogList.get(position).id==1)
                {
                    selectPaymentType_ID=false;
                }
                selectPaymentType.setText(String.valueOf(DialogList.get(position).CityName));
                //phoneCode=DialogList.get(position).CityName;
                dialog1.dismiss();
            });
        });
        minus_seat_num.setOnClickListener(v -> {
            try
            {
                int p = Integer.parseInt(seat_num.getText().toString());
                p = p - 1;
                if (p<1)
                {
                    if (Language.equals("ar"))
                    {
                        Toast.makeText(TripDetails.this, "يجب ان يكون عدد المقاعد اكبر من صفر", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(TripDetails.this, "must be greater than Zero", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    seat_num.setText(String.valueOf(p));
                    seat_number=String.valueOf(p);
                }
            }
            catch (Exception e)
            {
                Toast.makeText(TripDetails.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        plus_seat_num.setOnClickListener(v -> {
            try {
                int p = Integer.parseInt(seat_num.getText().toString());
                p = p + 1;
                if (p>Integer.parseInt(Max_seat_number))
                {
                    if (Language.equals("ar"))
                    {
                        Toast.makeText(TripDetails.this, "هذا اقصي عدد مسموح به", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(TripDetails.this, "This is the maximum number allowed", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    seat_num.setText(String.valueOf(p));
                    seat_number=String.valueOf(p);
                }
            }
            catch (Exception e)
            {
                Toast.makeText(TripDetails.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        mSharedPreferences  =   getSharedPreferences("tokenDetail",MODE_PRIVATE);
        Language                = mSharedPreferences.getString(Constant.language, Locale.getDefault().getLanguage());
        mSubscriptions      =   new CompositeSubscription();
        hud = KProgressHUD.create(TripDetails.this)
                .setStyle(KProgressHUD.Style.PIE_DETERMINATE)
                .setAnimationSpeed(2)
                .setCancellable(false)
                .setDimAmount(0.5f)
                .setMaxProgress(100);

        getTripDetails();

        RequestBook.setOnClickListener(v -> {
            if (Language.equals("ar"))
            {
                if (seat_number.equals("0"))
                {
                    Toast.makeText(this, "من فضلك اختر عدد المقاعد", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if (Validation.isConnected(TripDetails.this))
                    {
                        addReservationRequest addReservationRequest=new addReservationRequest();
                        addReservationRequest.setPaymentType(String.valueOf(selectPaymentType_ID));
                        addReservationRequest.setTripId(Constant.tripIdSearch);
                        addReservationRequest.setSeatCount(seat_number);
                        addReservationRequest.setSeatsCost(Double.parseDouble(seat_number)*Double.parseDouble(total_price.getText().toString())+"");

                        hud.show();
                        mSubscriptions.add(NetworkUtil.getRetrofitByToken(DataManager.getInstance().getCashedAccessToken().getAccess_token())
                                .addReservation(addReservationRequest)
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribeOn(Schedulers.io())
                                .subscribe(this::handleResponse2, this::handleError));
                    }
                    else {
                        buildDialog((Activity) getApplicationContext()).show().setCanceledOnTouchOutside(false);
                    }
                }
            }
            else
            {
                if (seat_number.equals("0"))
                {
                    Toast.makeText(this, "please select the number of seat", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if (Validation.isConnected(TripDetails.this))
                    {
                        addReservationRequest addReservationRequest=new addReservationRequest();
                        addReservationRequest.setPaymentType(String.valueOf(selectPaymentType_ID));
                        addReservationRequest.setTripId(Constant.tripIdSearch);
                        addReservationRequest.setSeatCount(seat_number);
                        addReservationRequest.setSeatsCost(Double.parseDouble(seat_number)*Double.parseDouble(total_price.getText().toString())+"");
                        hud.show();
                        /*Toast.makeText(this,String.valueOf(selectPaymentType_ID) , Toast.LENGTH_SHORT).show();
                        Toast.makeText(this,Constant.tripIdSearch , Toast.LENGTH_SHORT).show();
                        Toast.makeText(this,seat_number , Toast.LENGTH_SHORT).show();
                        Toast.makeText(this,DataManager.getInstance().getCashedAccessToken().getAccess_token() , Toast.LENGTH_SHORT).show();*/
                        mSubscriptions.add(NetworkUtil.getRetrofitByToken(DataManager.getInstance().getCashedAccessToken().getAccess_token())
                                .addReservation(addReservationRequest)
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribeOn(Schedulers.io())
                                .subscribe(this::handleResponse2, this::handleError));
                    }
                    else {
                        buildDialog((Activity) getApplicationContext()).show().setCanceledOnTouchOutside(false);
                    }
                }
            }
        });




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
    private void handleResponse2(updateTripStatusResponse addReservationResponse) {

        //Toast.makeText(this, "ok", Toast.LENGTH_SHORT).show();
        try {
            if (addReservationResponse.getErrors().getMessage()==null)
            {
                Constant.sLatSearch= "";
                Constant.sLngSearch= "";
                Constant.dLatSearch= "";
                Constant.dLngSearch= "";
                Constant.dateSearch= "";
                Constant.sCity="";
                Constant.dCity="";
                Constant.home_position="2";
                TripDetailsCarColor="";
                hud.dismiss();


                String message="";
                if (Language.equals("ar"))
                {
                    message="تم حجز الرحلة بنجاح";
                }
                else
                {
                    message="Trip booked successfully";
                }

                android.support.v7.app.AlertDialog.Builder builder;
                android.support.v7.app.AlertDialog dialog;
                builder = new android.support.v7.app.AlertDialog.Builder(TripDetails.this);

                @SuppressLint("InflateParams")
                View mview = getLayoutInflater().inflate(R.layout.dialog_success, null);
                builder.setView(mview);
                dialog = builder.create();
                Window window = dialog.getWindow();
                if (window != null) {
                    window.setGravity(Gravity.CENTER);
                }
                dialog.show();
                dialog.setCanceledOnTouchOutside(false);
                dialog.setCancelable(false);
                TextView txt = mview.findViewById(R.id.txt);
                TextView ok = mview.findViewById(R.id.ok);
                txt.setText(message);
                ok.setOnClickListener(v2 -> {
                    dialog.dismiss();
                    Intent intent = new Intent(TripDetails.this, Login.class);
                     intent.putExtra("f",true);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                    finish();
                });
            }
        }
        catch (Exception e)
        {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        //Toast.makeText(this,Constant.tripIdSearch, Toast.LENGTH_SHORT).show();
    }

    private void setImg(String url, ImageView image)
    {

        if(url.equals(""))
        {
            image.setImageResource(R.drawable.ic_user);
        }
        else {
            try {

                Picasso.with(TripDetails.this)
                        .load(url)
                        .placeholder(R.drawable.ic_user)
                        .into(image);
            } catch (Exception ignored) {

            }
        }
    }

    private void getTripDetails()
    {
        if (Validation.isConnected(TripDetails.this))
        {
            hud.show();
            mSubscriptions.add(NetworkUtil.getRetrofitByToken(DataManager.getInstance().getCashedAccessToken().getAccess_token())
                    .metaData()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResponseMetaData, this::handleError));
        }
        else {
            buildDialog(TripDetails.this).show().setCanceledOnTouchOutside(false);
        }
    }



    private void handleResponseMetaData(MetaDataResponseModel metaDataResponseModel) {
        if (metaDataResponseModel.getModel()!=null)
        {
            if (metaDataResponseModel.getModel().getSettings()!=null)
            {
                Percentage   = Double.parseDouble(metaDataResponseModel.getModel().getSettings().get(0).getPercentage());
                kMPrice   = Double.parseDouble(metaDataResponseModel.getModel().getSettings().get(0).getKMPrice());
            }
            mSubscriptions.add(NetworkUtil.getRetrofitByToken(DataManager.getInstance().getCashedAccessToken().getAccess_token())
                    .getTripDetails(Constant.tripIdSearch)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResponse, this::handleError));
        }
        //hud.dismiss();
    }

    @SuppressLint("SetTextI18n")
    private void handleResponse(getTripDetailsResponse getTripDetailsResponse) {


        setImg(Constant.BASE_URL_profile_img+getTripDetailsResponse.getModel().getDriver().getProfilePhoto(),img);

        name.setText(getTripDetailsResponse.getModel().getDriver().getName());
        reviews_count.setText(getTripDetailsResponse.getModel().getDriver().getTotalCount());
        rate.setRating(Float.parseFloat(getTripDetailsResponse.getModel().getDriver().getTotalRate()));
        String startT=TerhalUtils.getTimeWithCurrentZone(
                TerhalUtils.parseDateTo_yyyyMMdd(getTripDetailsResponse.getModel().getStartDate())+" "+
                        getTripDetailsResponse.getModel().getStartTime());
        String endT=TerhalUtils.getTimeWithCurrentZone(
                TerhalUtils.parseDateTo_yyyyMMdd(getTripDetailsResponse.getModel().getEndDate())+" "+
                        getTripDetailsResponse.getModel().getEndTime());
        from_date.setText(startT);
        to_date.setText(endT);
        date.setText(TerhalUtils.parseDateTo_yyyyMMdd(getTripDetailsResponse.getModel().getStartDate()));

        from_address.setText(getTripDetailsResponse.getModel().getFromCaption());
        to_address.setText(getTripDetailsResponse.getModel().getToCaption());
        if (Language.equals("ar"))
        {
            between.setText(" بين "+startT+" الي "+endT);
        }
        else
        {
            between.setText(" between "+startT+" to "+endT);
        }

        sLatSearch= getTripDetailsResponse.getModel().getFromLatitude();
        sLngSearch= getTripDetailsResponse.getModel().getFromLongitude();
        dLatSearch= getTripDetailsResponse.getModel().getToLatitude();
        dLngSearch= getTripDetailsResponse.getModel().getToLongitude();

        seat_count.setText(getTripDetailsResponse.getModel().getAvailableSeat());
        distance.setText(getTripDetailsResponse.getModel().getExpectedDistance());
        desc.setText(getTripDetailsResponse.getModel().getDescription());
        //seat_count.setText(getTripDetailsResponse.getModel().getAvailableSeat());

        if (getTripDetailsResponse.getModel().getDriver().getLikeSmoking().equals("true"))
            smoking.setImageDrawable(getResources().getDrawable(R.drawable.ic_smoke_green));
        else
            smoking.setImageDrawable(getResources().getDrawable(R.drawable.ic_smoke));

        if (getTripDetailsResponse.getModel().getDriver().getHaveAirCondition().equals("true"))
            air_conditioner.setImageDrawable(getResources().getDrawable(R.drawable.ic_air_green));
        else
            air_conditioner.setImageDrawable(getResources().getDrawable(R.drawable.ic_air));

        if (getTripDetailsResponse.getModel().getDriver().getLikeMusic().equals("true"))
            music.setImageDrawable(getResources().getDrawable(R.drawable.ic_music1_green));
        else
            music.setImageDrawable(getResources().getDrawable(R.drawable.ic_music1));

        if (getTripDetailsResponse.getModel().getDriver().getLikeSpeaking().equals("true"))
            speaking.setImageDrawable(getResources().getDrawable(R.drawable.ic_talk_green));
        else
            speaking.setImageDrawable(getResources().getDrawable(R.drawable.ic_talk));

        if (getTripDetailsResponse.getModel().getDriver().getHaveChargeMobile().equals("true"))
            charger.setImageDrawable(getResources().getDrawable(R.drawable.ic_charger_green));
        else
            charger.setImageDrawable(getResources().getDrawable(R.drawable.ic_charger));

        if (getTripDetailsResponse.getModel().getDriver().getLikePets().equals("true"))
            Pets.setImageDrawable(getResources().getDrawable(R.drawable.ic_bag_green));
        else
            Pets.setImageDrawable(getResources().getDrawable(R.drawable.ic_bag));


        if (Language.equals("ar"))
        {
            car_type.setText(getTripDetailsResponse.getModel().getCar().getCarModel().getNameLT());
        }
        else
        {
            car_type.setText(getTripDetailsResponse.getModel().getCar().getCarModel().getName());
        }
        if (Language.equals("ar"))
        {
            car_color.setText(TripDetailsCarColor);
        }
        else
        {
            car_color.setText(TripDetailsCarColor);
        }

        //car_color.setText(getTripDetailsResponse.getModel().getCarId());

        phone.setText(getTripDetailsResponse.getModel().getDriver().getPhoneKey()+getTripDetailsResponse.getModel().getDriver().getPhoneNumber());
        email.setText(getTripDetailsResponse.getModel().getDriver().getEmail());
        licence.setText("");



        double fee_value=Double.parseDouble(getTripDetailsResponse.getModel().getRealCost())-Double.parseDouble(getTripDetailsResponse.getModel().getRealCostWithoutCommission());
        price.setText(  String.format(Locale.ENGLISH,"%.2f", Double.parseDouble(getTripDetailsResponse.getModel().getRealCostWithoutCommission()))+"");

        fee.setText(  String.format(Locale.ENGLISH,"%.2f",fee_value ));

        total_price.setText( String.format(Locale.ENGLISH,"%.2f",Double.parseDouble(getTripDetailsResponse.getModel().getRealCost()))+"");



        if (getTripDetailsResponse.getModel().getDriver().getVerified().equals("true"))
        {
            phone_verified.setImageDrawable(getResources().getDrawable(R.drawable.ic_verified));

        }
        else
        {
            phone_verified.setImageDrawable(getResources().getDrawable(R.drawable.ic_error));

        }
        if (getTripDetailsResponse.getModel().getDriver().getEmailVerified().equals("true"))
        {
            email_verified.setImageDrawable(getResources().getDrawable(R.drawable.ic_verified));
        }
        else
        {
            email_verified.setImageDrawable(getResources().getDrawable(R.drawable.ic_error));
        }
        if (getTripDetailsResponse.getModel().getDriver().getIdentityNumberVerified().equals("true"))
        {
            licence_verified.setImageDrawable(getResources().getDrawable(R.drawable.ic_verified));
        }
        else
        {
            licence_verified.setImageDrawable(getResources().getDrawable(R.drawable.ic_error));
        }

        Max_seat_number=getTripDetailsResponse.getModel().getAvailableSeat();

        user_id=getTripDetailsResponse.getModel().getDriverId();

        rowItem.clear();
        for (int i=0;i<getTripDetailsResponse.getModel().getReservations().size();i++)
        {
            rowItem.add(new PassengerModel(
                    getTripDetailsResponse.getModel().getReservations().get(i).getPassengerId()
                    ,Constant.BASE_URL_profile_img+getTripDetailsResponse.getModel().getReservations().get(i).getPassengerProfilePhoto()
                    ,getTripDetailsResponse.getModel().getReservations().get(i).getPassengerName()
                    ,getTripDetailsResponse.getModel().getReservations().get(i).getTotalRate()
            ));
        }
        if (rowItem.size()==0)
        {
            no_exist.setVisibility(View.VISIBLE);
        }
        else
        {
            initRecyclerView();
        }

        /*if (Validation.isConnected(TripDetails.this))
        {
            hud.show();
            mSubscriptions.add(NetworkUtil.getRetrofitByToken(DataManager.getInstance().getCashedAccessToken().getAccess_token())
                    .metaData()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResponseCars, this::handleError));
        }
        else
        {
            buildDialog(TripDetails.this).show().setCanceledOnTouchOutside(false);
        }*/

        hud.dismiss();
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }


    private String parseDateTo_yyyyMMdd(String time) {
        String inputPattern = "yyyy-MM-dd'T'HH:mm:ss";
        String outputPattern = "yyyy-MM-dd";
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = "";

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            Toast.makeText(TripDetails.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return str;
    }

    private void handleResponseCars() {
       /* if (metaDataResponseModel.getModel()!=null)
        {
            if (metaDataResponseModel.getModel().getCarTypes()!=null)
            {
                for (int i=0;i<metaDataResponseModel.getModel().getCarTypes().size();i++)
                {
                    if ()
                }
            }
        }
        car_type.setText(getTripDetailsResponse.getModel().getCarId());
        car_color.setText(getTripDetailsResponse.getModel().getCarId());*/
    }
    private void initRecyclerView() {
        RecyclerViewAdapterPassenger adapter2 = new RecyclerViewAdapterPassenger(TripDetails.this,rowItem);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager mRecyclerViewLayoutManager2 = new LinearLayoutManager(TripDetails.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mRecyclerViewLayoutManager2);
        recyclerView.setAdapter(adapter2);
        adapter2.notifyDataSetChanged();
    }

    private void handleError(Throwable throwable) {
        //Toast.makeText(getActivity(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
        boolean flag=false;
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
                        flag=true;
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

        View mview;
        if (flag)
        {
            mview = getLayoutInflater().inflate(R.layout.dialog_add_balance, null);
            builder.setView(mview);
            dialog = builder.create();
            Window window = dialog.getWindow();
            if (window != null) {
                window.setGravity(Gravity.CENTER);
            }
            dialog.show();
            TextView txt = mview.findViewById(R.id.txt);
            TextView ok = mview.findViewById(R.id.ok);
            TextView add = mview.findViewById(R.id.add);
            txt.setText(message);
            ok.setOnClickListener(v2 -> dialog.dismiss());
            add.setOnClickListener(v2 -> {
                Intent intent = new Intent(getApplicationContext(),RechargeWallet.class);
                startActivity(intent);
                //finish();
            });

        }
        else
        {

            mview = getLayoutInflater().inflate(R.layout.dialog_error, null);
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
        }
        hud.dismiss();
    }

    private void initionilizationOFFilterCarType() {
        DialogList.clear();
        try {
            if (Language.equals("ar"))
            {
                DialogList.add(new filterAreaModelRecycler(
                        "كاش"
                        ,""
                        ,""
                        ,0));
                DialogList.add(new filterAreaModelRecycler(
                        "المحفظة"
                        ,""
                        ,""
                        ,1));
            }
            else
            {
                DialogList.add(new filterAreaModelRecycler(
                        "cash"
                        ,""
                        ,""
                        ,0));
                DialogList.add(new filterAreaModelRecycler(
                        "wallet"
                        ,""
                        ,""
                        ,1));
            }

            filterAreaAdapter1.update(DialogList);
        }
        catch (Exception e)
        {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }


}
