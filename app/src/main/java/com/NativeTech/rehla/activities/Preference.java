package com.NativeTech.rehla.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Switch;
import android.widget.TextView;

import com.NativeTech.rehla.Utills.MyTextViewBold;
import com.NativeTech.rehla.model.DataManager;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONObject;

import java.util.Locale;
import java.util.Objects;

import com.NativeTech.rehla.model.data.dto.Models.EditPreferences.EditPreferencesRequest;
import com.NativeTech.rehla.model.data.dto.Models.LoginModel.LoginResponseModel;
import com.NativeTech.rehla.Network.NetworkUtil;
import com.NativeTech.rehla.R;
import com.NativeTech.rehla.Utills.Constant;
import com.NativeTech.rehla.Utills.Validation;

import retrofit2.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static com.NativeTech.rehla.Utills.Constant.buildDialog;

public class Preference extends AppCompatActivity {


    private String Language;
    private CompositeSubscription mSubscriptions;
    private SharedPreferences mSharedPreferences;
    private KProgressHUD hud;


    private Switch music;
    private Switch air_conditioner;
    private Switch charger;
    private Switch smoking;
    private Switch rest;
    private Switch speaking;

    private MyTextViewBold save;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_notification);

        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
         getSupportActionBar().setDisplayShowHomeEnabled(true);

        music                       =findViewById(R.id.music);
        air_conditioner             =findViewById(R.id.air_conditioner);
        charger                     =findViewById(R.id.charger);
        smoking                     =findViewById(R.id.smoking);
        rest                        =findViewById(R.id.rest);
        speaking                    =findViewById(R.id.speaking);
        save                        =findViewById(R.id.save);

        mSharedPreferences  =   getSharedPreferences("tokenDetail",MODE_PRIVATE);
        Language            =   mSharedPreferences.getString(Constant.language,Locale.getDefault().getLanguage());
        mSubscriptions      =   new CompositeSubscription();
        hud = KProgressHUD.create(Preference.this)
                .setStyle(KProgressHUD.Style.PIE_DETERMINATE)
                .setAnimationSpeed(2)
                .setCancellable(false)
                .setDimAmount(0.5f)
                .setMaxProgress(100);


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Validation.isConnected(Preference.this))
                {
                    EditPreferencesRequest editPreferencesRequest=new EditPreferencesRequest();
                    editPreferencesRequest.setHaveAirCondition(air_conditioner.isChecked());
                    editPreferencesRequest.setHaveChargeMobile(charger.isChecked());
                    editPreferencesRequest.setLikeMusic(music.isChecked());
                    editPreferencesRequest.setLikePets(rest.isChecked());
                    editPreferencesRequest.setLikeSmoking(smoking.isChecked());
                    editPreferencesRequest.setLikeSpeaking(speaking.isChecked());
                    hud.show();
                    mSubscriptions.add(NetworkUtil.getRetrofitByToken(DataManager.getInstance().getCashedAccessToken().getAccess_token())
                            .EditPreferences(editPreferencesRequest)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io())
                            .subscribe(this::handleResponse, this::handleError));
                }
                else
                {
                    buildDialog(Preference.this).show().setCanceledOnTouchOutside(false);
                }
            }

            private void handleError(Throwable throwable) {
                //Toast.makeText(getActivity(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                String message="";
                try {
                    if (throwable instanceof retrofit2.HttpException) {
                        retrofit2.HttpException error = (retrofit2.HttpException
                                ) throwable;
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

                builder = new android.support.v7.app.AlertDialog.Builder(Preference.this);
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
            private void handleResponse(LoginResponseModel loginResponseModel) {
                if (loginResponseModel.getModel()!=null)
                {

                    String message="";
                    if (Language.equals("ar"))
                    {
                        message="تم الحفظ";
                    }
                    else
                    {
                        message="saved successfully";
                    }

                    android.support.v7.app.AlertDialog.Builder builder;
                    android.support.v7.app.AlertDialog dialog;
                    builder = new android.support.v7.app.AlertDialog.Builder(Preference.this);

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
                        Intent intent = new Intent(Preference.this, Profile.class);
                        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    });
                }
                hud.dismiss();
            }
        });

        if (Validation.isConnected(Preference.this))
        {
            hud.show();
            mSubscriptions.add(NetworkUtil.getRetrofitByToken(DataManager.getInstance().getCashedAccessToken().getAccess_token())
                    .GetUserProfile()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResponseGetData, this::handleError));
        }
        else
        {
            buildDialog(Preference.this).show().setCanceledOnTouchOutside(false);
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
        dialog.show();
        TextView txt = mview.findViewById(R.id.txt);
        TextView ok = mview.findViewById(R.id.ok);
        txt.setText(message);
        ok.setOnClickListener(v2 -> dialog.dismiss());
        hud.dismiss();
    }
    private void handleResponseGetData(LoginResponseModel loginResponseModel) {


        if (loginResponseModel.getModel()!=null)
        {
            music.setChecked(loginResponseModel.getModel().getLikeMusic());
            air_conditioner.setChecked(loginResponseModel.getModel().getHaveAirCondition());
            charger.setChecked(loginResponseModel.getModel().getHaveChargeMobile());
            smoking.setChecked(loginResponseModel.getModel().getLikeSmoking());
            rest.setChecked(loginResponseModel.getModel().getLikePets());
            speaking.setChecked(loginResponseModel.getModel().getLikeSpeaking());
        }
        hud.dismiss();

    }

}
