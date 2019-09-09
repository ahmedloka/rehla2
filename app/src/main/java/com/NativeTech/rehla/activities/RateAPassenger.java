package com.NativeTech.rehla.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.NativeTech.rehla.model.DataManager;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import com.NativeTech.rehla.model.data.dto.Models.MetaDataModels.MetaDataResponseModel;
import com.NativeTech.rehla.model.data.dto.Models.MetaDataModels.RateFactors;
import com.NativeTech.rehla.model.data.dto.Models.RateDriver.RateDriverRequest;
import com.NativeTech.rehla.model.data.dto.Models.RateDriver.RateDriverResponse;
import com.NativeTech.rehla.Network.NetworkUtil;
import com.NativeTech.rehla.R;
import com.NativeTech.rehla.Utills.Constant;
import com.NativeTech.rehla.Utills.Validation;
import com.NativeTech.rehla.adapters.filterAreaAdapterForCountryCode;
import com.NativeTech.rehla.adapters.filterAreaModelRecycler;

import retrofit2.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static com.NativeTech.rehla.Utills.Constant.buildDialog;

public class RateAPassenger extends AppCompatActivity {

    private String Language;
    private CompositeSubscription mSubscriptions;
    private SharedPreferences mSharedPreferences;
    private KProgressHUD hud;


    private TextView rateFactors;
    private String rateFactors_id="-1";

    private List<RateFactors> rateFactorsList=new ArrayList<>();

    private RecyclerView DialogRecyclerView;
    private filterAreaAdapterForCountryCode filterAreaAdapter1;
    private final ArrayList<filterAreaModelRecycler> DialogList = new ArrayList<>();

    private Button submit;

    private AppCompatEditText message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_apassenger);

        LinearLayoutCompat back                                = findViewById(R.id.back);
        back.setOnClickListener(v -> finish());


        rateFactors                   =findViewById(R.id.rateFactors);
        submit                        =findViewById(R.id.submit);
        message                       =findViewById(R.id.message);
        mSharedPreferences  =   getSharedPreferences("tokenDetail",MODE_PRIVATE);
        Language            =   mSharedPreferences.getString(Constant.language, Locale.getDefault().getLanguage());
        mSubscriptions      =   new CompositeSubscription();
        hud = KProgressHUD.create(RateAPassenger.this)
                .setStyle(KProgressHUD.Style.PIE_DETERMINATE)
                .setAnimationSpeed(2)
                .setCancellable(false)
                .setDimAmount(0.5f)
                .setMaxProgress(100);

        rateFactors.setOnClickListener(v -> {
            AlertDialog.Builder builder1;
            final AlertDialog dialog1;
            builder1 = new AlertDialog.Builder(RateAPassenger.this);
            View mview = getLayoutInflater().inflate(R.layout.area_dialog, null);
            TextView  header = mview.findViewById(R.id.DialogHeader);
            TextView  all = mview.findViewById(R.id.All);
            all.setVisibility(View.GONE);

            header.setText(getString(R.string.select_car_brand));

            DialogRecyclerView = mview.findViewById(R.id.Recycler_Dialog_cities);
            filterAreaAdapter1 = new filterAreaAdapterForCountryCode(DialogList, getApplicationContext());
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), OrientationHelper.VERTICAL, false);

            DialogRecyclerView.setLayoutManager(linearLayoutManager);
            DialogRecyclerView.setItemAnimator(new DefaultItemAnimator());
            initionilizationOFFilter();

            DialogRecyclerView.setAdapter(filterAreaAdapter1);
            builder1.setView(mview);
            dialog1 = builder1.create();
            Window window = dialog1.getWindow();
            if (window != null) {
                window.setGravity(Gravity.CENTER);
            }
            dialog1.show();

            filterAreaAdapter1.setOnItemClickListener(v1 -> {
                int position = DialogRecyclerView.getChildAdapterPosition(v1);
                //String position1 = (String.valueOf(position));
//                        AreaID = (Integer.parseInt(DialogList.get(position).ID));
//                        SelectedAreaName = DialogList.get(position).shopName;
//                        select_Area_All.setText(SelectedAreaName);
                //String countrylogo = DialogList.get(position).flag;
                //setImg(countrylogo,countryLogo);

                rateFactors_id=String.valueOf(DialogList.get(position).id);
                rateFactors.setText(String.valueOf(DialogList.get(position).CityName));


                //Toast.makeText(UploadCarPhoto.this, car_brand_Position+"", Toast.LENGTH_SHORT).show();
                //phoneCode=DialogList.get(position).CityName;
                dialog1.dismiss();

            });
        });

        if (Validation.isConnected(RateAPassenger.this))
        {
            hud.show();
            mSubscriptions.add(NetworkUtil.getRetrofitByToken(DataManager.getInstance().getCashedAccessToken().getAccess_token())
                    .metaData()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResponse, this::handleError));
        }
        else
        {
            buildDialog(RateAPassenger.this).show().setCanceledOnTouchOutside(false);
        }

        submit.setOnClickListener(v -> {
            if (Language.equals("ar"))
            {
                if (Objects.requireNonNull(message.getText()).toString().equals(""))
                {
                    Toast.makeText(this, "من فضلك ادخل تعليقك", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    sendRate();
                }
            }
            else
            {
                if (Objects.requireNonNull(message.getText()).toString().equals(""))
                {
                    Toast.makeText(this, "please enter Your Comment", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    sendRate();
                }
            }
        });
    }

    private void sendRate(){
        if (Validation.isConnected(RateAPassenger.this))
        {
            RateDriverRequest rateDriverRequest=new RateDriverRequest();
            rateDriverRequest.setComment(Objects.requireNonNull(message.getText()).toString());
            rateDriverRequest.setRateFactorId(rateFactors_id);
            rateDriverRequest.setReservationId(Constant.reservation_id);
            hud.show();
            mSubscriptions.add(NetworkUtil.getRetrofitByToken(DataManager.getInstance().getCashedAccessToken().getAccess_token())
                    .RateAPassenger(rateDriverRequest)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResponseRate, this::handleError));
        }
        else
        {
            buildDialog(RateAPassenger.this).show().setCanceledOnTouchOutside(false);
        }

    }

    private void handleResponseRate(RateDriverResponse rateDriverResponse) {
        if (rateDriverResponse.getModel()!=null)
        {
            String message="";
            if (Language.equals("ar"))
            {
                message="تم التقييم";
            }
            else
            {
                message="rated successfully";
            }

            android.support.v7.app.AlertDialog.Builder builder;
            android.support.v7.app.AlertDialog dialog;
            builder = new android.support.v7.app.AlertDialog.Builder(RateAPassenger.this);

            @SuppressLint("InflateParams")
            View mview = getLayoutInflater().inflate(R.layout.dialog_success, null);
            builder.setView(mview);
            dialog = builder.create();
            Window window = dialog.getWindow();
            if (window != null) {
                window.setGravity(Gravity.CENTER);
            }
            Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            dialog.show();
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            TextView txt = mview.findViewById(R.id.txt);
            TextView ok = mview.findViewById(R.id.ok);
            txt.setText(message);
            ok.setOnClickListener(v2 -> {
                dialog.dismiss();
                Intent intent = new Intent(getApplicationContext(),Home.class);
                startActivity(intent);
                //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                finish();
            });
        }
        hud.dismiss();
    }

    private void handleError(Throwable throwable) {
        //Toast.makeText(getActivity(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
        String message="";
        try {
            if (throwable instanceof retrofit2.HttpException) {
                retrofit2.HttpException error = (retrofit2.HttpException
                        ) throwable;
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

    private void handleResponse(MetaDataResponseModel metaDataResponseModel) {
        //Toast.makeText(this, "11", Toast.LENGTH_SHORT).show();
        if (metaDataResponseModel.getModel()!=null)
        {
            if (metaDataResponseModel.getModel().getRateFactors()!=null)
            {
                rateFactorsList   =metaDataResponseModel.getModel().getRateFactors();
                rateFactors_id=metaDataResponseModel.getModel().getRateFactors().get(0).getId();
                if (Language.equals("ar"))
                {
                    rateFactors.setText(metaDataResponseModel.getModel().getRateFactors().get(0).getNameLT());
                }
                else
                {
                    rateFactors.setText(metaDataResponseModel.getModel().getRateFactors().get(0).getName());
                }
                // carModels =metaDataResponseModel.getModel().getCarBrands().get(car_brand_Position).getCarModels();
                // Toast.makeText(this, metaDataResponseModel.getModel().getCountries().size()+"", Toast.LENGTH_SHORT).show();
            }
        }
        hud.dismiss();
    }


    private void initionilizationOFFilter() {
        DialogList.clear();
        try {
            for (int k=0 ;k<rateFactorsList.size();k++)
            {
                String name="";
                if (Language.equals("en"))
                {
                    name=rateFactorsList.get(k).getName();
                }
                else
                {
                    name=rateFactorsList.get(k).getNameLT();

                }
                DialogList.add(new filterAreaModelRecycler(
                        name
                        ,""
                        ,""
                        ,Integer.parseInt(rateFactorsList.get(k).getId())));
            }
            filterAreaAdapter1.update(DialogList);
        }
        catch (Exception e)
        {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

}
