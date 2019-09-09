package com.NativeTech.rehla.activities;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.NativeTech.rehla.model.DataManager;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import com.NativeTech.rehla.model.data.dto.Models.GetDriverProfile.DriverProfileResponseModel;
import com.NativeTech.rehla.model.data.dto.Models.GetRateSummary.GetAllRatesResponse;
import com.NativeTech.rehla.Network.NetworkUtil;
import com.NativeTech.rehla.R;
import com.NativeTech.rehla.Utills.Constant;
import com.NativeTech.rehla.Utills.Validation;
import com.NativeTech.rehla.adapters.RecyclerViewAdapterReviews;
import com.NativeTech.rehla.adapters.ReviewsRecyclerModel;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static com.NativeTech.rehla.Utills.Constant.buildDialog;

public class PassengerDetails extends AppCompatActivity {

    private RecyclerView recyclerViewReviews;
    private List<ReviewsRecyclerModel> rowItem;
    private CircleImageView img;

    private RatingBar rate;
    private TextView name;
    private TextView  rate_count;
    private TextView  mobile;
    private TextView  email;
    private TextView  licence;

    private AppCompatImageView mobile_img;
    private AppCompatImageView email_img;
    private AppCompatImageView licence_img;


    private String Language;
    private CompositeSubscription mSubscriptions;
    private SharedPreferences mSharedPreferences;
    private KProgressHUD hud;


    private TextView no_exist;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_details);
        recyclerViewReviews                     = findViewById(R.id.recyclerViewReviews);
        rowItem                                 = new ArrayList<>();
        img                                     = findViewById(R.id.img);
        rate                                     = findViewById(R.id.rate);
        name                                     = findViewById(R.id.name);
        rate_count                                     = findViewById(R.id.rate_count);
        mobile                                     = findViewById(R.id.mobile);
        email                                     = findViewById(R.id.email);
        licence                                     = findViewById(R.id.licence);
        mobile_img                                     = findViewById(R.id.mobile_img);
        email_img                                     = findViewById(R.id.email_img);
        licence_img                                     = findViewById(R.id.licence_img);

        no_exist                                = findViewById(R.id.no_exist);

        no_exist.setVisibility(View.GONE);

        mSharedPreferences  =   getSharedPreferences("tokenDetail",MODE_PRIVATE);
        Language                = mSharedPreferences.getString(Constant.language, Locale.getDefault().getLanguage());
        mSubscriptions      =   new CompositeSubscription();
        hud = KProgressHUD.create(PassengerDetails.this)
                .setStyle(KProgressHUD.Style.PIE_DETERMINATE)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .setCancellable(false)
                .setMaxProgress(100);

        getUserDetails();




        /* setImg("https://engineering.unl.edu/images/staff/Kayla_Person-small.jpg",img);
         */
        /*rowItem.add(new ReviewsRecyclerModel("1","https://cdn.pixabay.com/photo/2015/06/26/16/29/person-822681_960_720.jpg","mohammed NativeTech","5","approved your request for a ride approved your request for a ride approved your request for a ride approved your request for a rideapproved your request for a ride"));
        rowItem.add(new ReviewsRecyclerModel("2","https://engineering.unl.edu/images/staff/Kayla_Person-small.jpg","Omar","3.5","rejected your request for a ride approved your request for a ride approved your request for a ride approved your request for a ride approved your request for a ride"));
        rowItem.add(new ReviewsRecyclerModel("6","https://cdn1.thr.com/sites/default/files/2017/08/gettyimages-630421358_-_h_2017.jpg","mohammed NativeTech","4","approved your request for a ride approved your request for a ride approved your request for a ride approved your request for a ride"));
        rowItem.add(new ReviewsRecyclerModel("3","https://engineering.unl.edu/images/staff/Kayla_Person-small.jpg","sherif","5","rejected your request for a ride approved your request for a ride approved your request for a ride approved your request for a ride approved your request for a ride"));
        rowItem.add(new ReviewsRecyclerModel("4","https://cdn.pixabay.com/photo/2015/06/26/16/29/person-822681_960_720.jpg","mohammed ","4.5","approved your request for a ride approved your request for a ride approved your request for a ride approved your request for a ride approved your request for a ride"));
        rowItem.add(new ReviewsRecyclerModel("5","https://engineering.unl.edu/images/staff/Kayla_Person-small.jpg","NativeTech","1","approved your request for a ride approved your request for a ride approved your request for a ride approved your request for a ride approved your request for a ride approved your request for a ride"));
        rowItem.add(new ReviewsRecyclerModel("7","https://cdn1.thr.com/sites/default/files/2017/08/gettyimages-630421358_-_h_2017.jpg","mohammed NativeTech","5","approved your request for a ride approved your request for a ride approved your request for a ride approved your request for a ride approved your request for a ride"));
*/
        //initRecyclerViewReviews();

       /* if (rowItem.size()==0)
        {
            no_exist.setVisibility(View.VISIBLE);
        }*/
    }

    private void initRecyclerViewReviews() {
        RecyclerViewAdapterReviews adapter2 = new RecyclerViewAdapterReviews(PassengerDetails.this,rowItem);
        recyclerViewReviews.setHasFixedSize(true);
        LinearLayoutManager mRecyclerViewLayoutManager2 = new LinearLayoutManager(PassengerDetails.this, LinearLayoutManager.VERTICAL, false);
        recyclerViewReviews.setLayoutManager(mRecyclerViewLayoutManager2);
        recyclerViewReviews.setAdapter(adapter2);
        adapter2.notifyDataSetChanged();
    }
    private void setImg(String url, ImageView image)
    {

        if(url.equals(""))
        {
            image.setImageResource(R.drawable.ic_user);
        }
        else {
            try {

                Picasso.with(PassengerDetails.this)
                        .load(url)
                        .placeholder(R.drawable.ic_user)
                        .into(image);
            } catch (Exception ignored) {

            }
        }
    }


    private void getUserDetails()
    {
        if (Validation.isConnected(PassengerDetails.this))
        {
            /*Toast.makeText(this, getAddress(Double.parseDouble(Constant.sLatSearch),Double.parseDouble(Constant.sLngSearch)), Toast.LENGTH_SHORT).show();
            Toast.makeText(this, getAddress(Double.parseDouble(Constant.dLatSearch),Double.parseDouble(Constant.dLngSearch)), Toast.LENGTH_SHORT).show();*/
            hud.show();
            mSubscriptions.add(NetworkUtil.getRetrofitByToken(DataManager.getInstance().getCashedAccessToken().getAccess_token())
                    .GetDriverProfile(Constant.passenger_id)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResponse, this::handleError));
        }
        else {
            buildDialog(PassengerDetails.this).show().setCanceledOnTouchOutside(false);
        }
    }

    private void handleResponse(DriverProfileResponseModel driverProfileResponseModel) {

        setImg(Constant.BASE_URL_profile_img+driverProfileResponseModel.getModel().getProfilePhoto(),img);

        img                                     = findViewById(R.id.img);
        rate                                     = findViewById(R.id.rate);
        name                                     = findViewById(R.id.name);
        rate_count                                     = findViewById(R.id.rate_count);
        mobile                                     = findViewById(R.id.mobile);
        email                                     = findViewById(R.id.email);
        licence                                     = findViewById(R.id.licence);
        mobile_img                                     = findViewById(R.id.mobile_img);
        email_img                                     = findViewById(R.id.email_img);
        licence_img                                     = findViewById(R.id.licence_img);

        name.setText(driverProfileResponseModel.getModel().getName());
        rate_count.setText(driverProfileResponseModel.getModel().getTotalCount());
        rate.setRating(Float.parseFloat(driverProfileResponseModel.getModel().getTotalRate()));
        mobile.setText(driverProfileResponseModel.getModel().getPhoneNumber());
        email.setText(driverProfileResponseModel.getModel().getEmail());
        licence.setText("");

        if (driverProfileResponseModel.getModel().getVerified().equals("true"))
        {
            mobile_img.setImageDrawable(getResources().getDrawable(R.drawable.ic_verified));

        }
        else
        {
            mobile_img.setImageDrawable(getResources().getDrawable(R.drawable.ic_error));

        }
        if (driverProfileResponseModel.getModel().getEmailVerified().equals("true"))
        {
            email_img.setImageDrawable(getResources().getDrawable(R.drawable.ic_verified));
        }
        else
        {
            email_img.setImageDrawable(getResources().getDrawable(R.drawable.ic_error));
        }
        if (driverProfileResponseModel.getModel().getIdentityNumberVerified().equals("true"))
        {
            licence_img.setImageDrawable(getResources().getDrawable(R.drawable.ic_verified));
        }
        else
        {
            licence_img.setImageDrawable(getResources().getDrawable(R.drawable.ic_error));
        }
        if (Validation.isConnected(PassengerDetails.this))
        {
            //hud.show();
            mSubscriptions.add(NetworkUtil.getRetrofitByToken(DataManager.getInstance().getCashedAccessToken().getAccess_token())
                    .getAllRatesByUserId(Constant.passenger_id,"0")
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResponseGetRate, this::handleError));
        }
        else
        {
            buildDialog(PassengerDetails.this).show().setCanceledOnTouchOutside(false);
        }
        hud.dismiss();
    }
    private void handleResponseGetRate(GetAllRatesResponse getAllRatesResponse) {
        rowItem.clear();
        if (getAllRatesResponse.getModel()!=null)
        {
            for (int i=0;i<getAllRatesResponse.getModel().size();i++)
            {
                rowItem.add(new ReviewsRecyclerModel(getAllRatesResponse.getModel().get(i).getId()
                        ,Constant.BASE_URL_profile_img+getAllRatesResponse.getModel().get(i).getProfilePhoto()
                        ,getAllRatesResponse.getModel().get(i).getName()
                        ,getAllRatesResponse.getModel().get(i).getValue()
                        ,getAllRatesResponse.getModel().get(i).getComment()));
            }
            if (getAllRatesResponse.getModel().size()==0)
            {
                no_exist.setVisibility(View.VISIBLE);
            }
            else
                initRecyclerViewReviews();
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

}