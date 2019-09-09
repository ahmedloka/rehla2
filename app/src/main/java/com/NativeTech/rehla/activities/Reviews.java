package com.NativeTech.rehla.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import android.widget.RatingBar;
import android.widget.TextView;

import com.NativeTech.rehla.model.DataManager;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import com.NativeTech.rehla.model.data.dto.Models.GetRateSummary.GetAllRatesResponse;
import com.NativeTech.rehla.model.data.dto.Models.GetRateSummary.getRateSummaryResponse;
import com.NativeTech.rehla.Network.NetworkUtil;
import com.NativeTech.rehla.R;
import com.NativeTech.rehla.Utills.Constant;
import com.NativeTech.rehla.Utills.Validation;
import com.NativeTech.rehla.adapters.RecyclerViewAdapterReviews;
import com.NativeTech.rehla.adapters.ReviewsRecyclerModel;

import retrofit2.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static com.NativeTech.rehla.Utills.Constant.buildDialog;

public class Reviews extends AppCompatActivity {

    private RecyclerView recyclerViewReviews;
    private List<ReviewsRecyclerModel> rowItem;

    private RatingBar   rate;
    private TextView    rate_count;
    private TextView    _5;
    private TextView    _4;
    private TextView    _3;
    private TextView    _2;
    private TextView    _1;

    private String Language;
    private CompositeSubscription mSubscriptions;
    private SharedPreferences mSharedPreferences;
    private KProgressHUD hud;
    private String token="";

    private TextView no_exist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);


        LinearLayoutCompat back                                = findViewById(R.id.back);
        back.setOnClickListener(v -> finish());


        recyclerViewReviews                     = findViewById(R.id.recyclerViewReviews);
        rate                                    = findViewById(R.id.rate);
        rate_count                              = findViewById(R.id.rate_count);
        _5                                      = findViewById(R.id._5);
        _4                                      = findViewById(R.id._4);
        _3                                      = findViewById(R.id._3);
        _2                                      = findViewById(R.id._2);
        _1                                      = findViewById(R.id._1);
        rowItem                                 = new ArrayList<>();
        no_exist                                = findViewById(R.id.no_exist);
        no_exist.setVisibility(View.GONE);
        mSharedPreferences      = getSharedPreferences("tokenDetail",MODE_PRIVATE);
        mSubscriptions          = new CompositeSubscription();
        Language                = mSharedPreferences.getString(Constant.language, Locale.getDefault().getLanguage());
        token                   = DataManager.getInstance().getCashedAccessToken().getAccess_token();
        hud = KProgressHUD.create(Reviews.this)
                .setStyle(KProgressHUD.Style.PIE_DETERMINATE)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .setCancellable(false)
                .setMaxProgress(100);

        if(token.equals(""))
        {
            Intent intent = new Intent(Reviews.this, Login.class);
            startActivity(intent);
            overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
            finish();
        }

        if (Validation.isConnected(Reviews.this))
        {
            hud.show();
            mSubscriptions.add(NetworkUtil.getRetrofitByToken(token)
                    .getRateSummary()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResponse, this::handleError));
        }
        else
        {
            buildDialog(Reviews.this).show().setCanceledOnTouchOutside(false);
        }

       /* rowItem.add(new ReviewsRecyclerModel("1","https://cdn.pixabay.com/photo/2015/06/26/16/29/person-822681_960_720.jpg","mohammed NativeTech","5","approved your request for a ride approved your request for a ride approved your request for a ride approved your request for a rideapproved your request for a ride"));
        rowItem.add(new ReviewsRecyclerModel("2","https://engineering.unl.edu/images/staff/Kayla_Person-small.jpg","Omar","3.5","rejected your request for a ride approved your request for a ride approved your request for a ride approved your request for a ride approved your request for a ride"));
        rowItem.add(new ReviewsRecyclerModel("6","https://cdn1.thr.com/sites/default/files/2017/08/gettyimages-630421358_-_h_2017.jpg","mohammed NativeTech","4","approved your request for a ride approved your request for a ride approved your request for a ride approved your request for a ride"));
        rowItem.add(new ReviewsRecyclerModel("3","https://engineering.unl.edu/images/staff/Kayla_Person-small.jpg","sherif","5","rejected your request for a ride approved your request for a ride approved your request for a ride approved your request for a ride approved your request for a ride"));
        rowItem.add(new ReviewsRecyclerModel("4","https://cdn.pixabay.com/photo/2015/06/26/16/29/person-822681_960_720.jpg","mohammed ","4.5","approved your request for a ride approved your request for a ride approved your request for a ride approved your request for a ride approved your request for a ride"));
        rowItem.add(new ReviewsRecyclerModel("5","https://engineering.unl.edu/images/staff/Kayla_Person-small.jpg","NativeTech","1","approved your request for a ride approved your request for a ride approved your request for a ride approved your request for a ride approved your request for a ride approved your request for a ride"));
        rowItem.add(new ReviewsRecyclerModel("7","https://cdn1.thr.com/sites/default/files/2017/08/gettyimages-630421358_-_h_2017.jpg","mohammed NativeTech","5","approved your request for a ride approved your request for a ride approved your request for a ride approved your request for a ride approved your request for a ride"));

        initRecyclerViewReviews();*/

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

    private void handleResponse(getRateSummaryResponse getRateSummaryResponse) {
        //rowItem.clear();
        if (getRateSummaryResponse.getModel()!=null)
        {
            //rate_txt.setText(getRateSummaryResponse.getModel().getTotalRate().getTotalValue());
            rate.setRating(Float.parseFloat(getRateSummaryResponse.getModel().getTotalRate().getTotalValue()));
            rate_count.setText(getRateSummaryResponse.getModel().getTotalRate().getTotalCount());

            int int_5=0;
            int int_4=0;
            int int_3=0;
            int int_2=0;
            int int_1=0;


            for (int i=0;i<getRateSummaryResponse.getModel().getRateSummary().size();i++)
            {
                switch (getRateSummaryResponse.getModel().getRateSummary().get(i).getValue()) {
                    case "5":
                        _5.setText(getRateSummaryResponse.getModel().getRateSummary().get(i).getCount());
                        int_5= Integer.parseInt(getRateSummaryResponse.getModel().getRateSummary().get(i).getCount());
                        break;
                    case "4":
                        _4.setText(getRateSummaryResponse.getModel().getRateSummary().get(i).getCount());
                        int_4= Integer.parseInt(getRateSummaryResponse.getModel().getRateSummary().get(i).getCount());
                        break;
                    case "3":
                        _3.setText(getRateSummaryResponse.getModel().getRateSummary().get(i).getCount());
                        int_3= Integer.parseInt(getRateSummaryResponse.getModel().getRateSummary().get(i).getCount());
                        break;
                    case "2":
                        _2.setText(getRateSummaryResponse.getModel().getRateSummary().get(i).getCount());
                        int_2= Integer.parseInt(getRateSummaryResponse.getModel().getRateSummary().get(i).getCount());
                        break;
                    case "1":
                        _1.setText(getRateSummaryResponse.getModel().getRateSummary().get(i).getCount());
                        int_1= Integer.parseInt(getRateSummaryResponse.getModel().getRateSummary().get(i).getCount());
                        break;
                }
            }
            LinearLayoutCompat progress_bar_excellent=findViewById(R.id.progress_bar_excellent);
            LinearLayoutCompat progress_bar_good=findViewById(R.id.progress_bar_good);
            LinearLayoutCompat progress_bar_average=findViewById(R.id.progress_bar_average);
            LinearLayoutCompat progress_bar_below_average=findViewById(R.id.progress_bar_below_average);
            LinearLayoutCompat progress_bar_poor=findViewById(R.id.progress_bar_poor);

            int total_count=Integer.parseInt(getRateSummaryResponse.getModel().getTotalRate().getTotalCount());

            float x_5=(float) int_5/total_count;
            float x_4=(float) int_4/total_count;
            float x_3=(float) int_3/total_count;
            float x_2=(float) int_2/total_count;
            float x_1=(float) int_1/total_count;


            LinearLayoutCompat.LayoutParams params_5 = (LinearLayoutCompat.LayoutParams)
                    progress_bar_excellent.getLayoutParams();
            params_5.weight =x_5;
            progress_bar_excellent.setLayoutParams(params_5);

            LinearLayoutCompat.LayoutParams params_4 = (LinearLayoutCompat.LayoutParams)
                    progress_bar_good.getLayoutParams();
            params_4.weight =x_4;
            progress_bar_good.setLayoutParams(params_4);

            LinearLayoutCompat.LayoutParams params_3 = (LinearLayoutCompat.LayoutParams)
                    progress_bar_average.getLayoutParams();
            params_3.weight =x_3;
            progress_bar_average.setLayoutParams(params_3);

            LinearLayoutCompat.LayoutParams params_2 = (LinearLayoutCompat.LayoutParams)
                    progress_bar_below_average.getLayoutParams();
            params_2.weight =x_2;
            progress_bar_below_average.setLayoutParams(params_2);

            LinearLayoutCompat.LayoutParams params_1 = (LinearLayoutCompat.LayoutParams)
                    progress_bar_poor.getLayoutParams();
            params_1.weight =x_1;
            progress_bar_poor.setLayoutParams(params_1);

            if (Validation.isConnected(Reviews.this))
            {
                hud.show();
                mSubscriptions.add(NetworkUtil.getRetrofitByToken(token)
                        .getAllRates("0")
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(this::handleResponsegetAllRates, this::handleError));
            }
            else
            {
                buildDialog(Reviews.this).show().setCanceledOnTouchOutside(false);
            }
        }
        hud.dismiss();
    }

    private void handleResponsegetAllRates(GetAllRatesResponse getAllRatesResponse) {
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
    }

    private void initRecyclerViewReviews() {
        RecyclerViewAdapterReviews adapter2 = new RecyclerViewAdapterReviews(Reviews.this,rowItem);
        recyclerViewReviews.setHasFixedSize(true);
        LinearLayoutManager mRecyclerViewLayoutManager2 = new LinearLayoutManager(Reviews.this, LinearLayoutManager.VERTICAL, false);
        recyclerViewReviews.setLayoutManager(mRecyclerViewLayoutManager2);
        recyclerViewReviews.setAdapter(adapter2);
        adapter2.notifyDataSetChanged();
    }
}
