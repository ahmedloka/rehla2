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
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.NativeTech.rehla.model.DataManager;
import com.blankj.utilcode.util.KeyboardUtils;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONObject;

import java.util.Locale;
import java.util.Objects;

import com.NativeTech.rehla.model.data.dto.Models.ErrorsModel;
import com.NativeTech.rehla.Network.NetworkUtil;
import com.NativeTech.rehla.R;
import com.NativeTech.rehla.Utills.Constant;
import com.NativeTech.rehla.Utills.Validation;

import retrofit2.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static com.NativeTech.rehla.Utills.Constant.buildDialog;

public class RechargeWallet extends AppCompatActivity {

    private LinearLayoutCompat  credit_card;
    private LinearLayoutCompat  recharge_card;

    private LinearLayoutCompat  charge_lin;
    private LinearLayoutCompat  credit_card_lin;
    private TextView            credit_card_txt;
    private TextView            recharge_card_txt;
    private AppCompatImageView  credit_card_img;
    private AppCompatImageView  recharge_card_img;
    private Button              upload;
    private Button              charge_btn;

    private AppCompatEditText   charge_edit_txt;


    private String Language;
    private CompositeSubscription mSubscriptions;
    private SharedPreferences mSharedPreferences;
    private KProgressHUD hud;
    private String token="";
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge_wallet);


        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.recharge_your_wallet);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        credit_card_lin                     =findViewById(R.id.credit_card_lin);
        charge_lin                          =findViewById(R.id.charge_lin);
        credit_card                         =findViewById(R.id.credit_card);
        recharge_card                       =findViewById(R.id.recharge_card);
        credit_card_txt                     =findViewById(R.id.credit_card_txt);
        recharge_card_txt                   =findViewById(R.id.recharge_card_txt);
        credit_card_img                     =findViewById(R.id.credit_card_img);
        recharge_card_img                   =findViewById(R.id.recharge_card_img);
        upload                              =findViewById(R.id.upload);
        charge_btn                          =findViewById(R.id.charge_btn);

        charge_edit_txt                     =findViewById(R.id.charge_edit_txt);
        ((View)charge_edit_txt.getParent()).setOnClickListener(v -> {
            try {
                KeyboardUtils.hideSoftInput(RechargeWallet.this);
            }catch (Exception e){}
        });
        ((View)charge_edit_txt.getParent().getParent()).setOnClickListener(v -> {
            try {
                KeyboardUtils.hideSoftInput(RechargeWallet.this);
            }catch (Exception e){}
        });
        ((View)charge_edit_txt.getParent().getParent().getParent()).setOnClickListener(v -> {
            try {
                KeyboardUtils.hideSoftInput(RechargeWallet.this);
            }catch (Exception e){}
        });
        mSharedPreferences      = getSharedPreferences("tokenDetail",MODE_PRIVATE);
        mSubscriptions          = new CompositeSubscription();
        Language                = mSharedPreferences.getString(Constant.language, Locale.getDefault().getLanguage());
        token                   = DataManager.getInstance().getCashedAccessToken().getAccess_token();
        hud = KProgressHUD.create(RechargeWallet.this)
                .setStyle(KProgressHUD.Style.PIE_DETERMINATE)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .setCancellable(false)
                .setMaxProgress(100);

        if(token.equals(""))
        {
            Intent intent = new Intent(RechargeWallet.this, Login.class);
            startActivity(intent);
            overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
            finish();
        }


        credit_card.setOnClickListener(v -> {
            credit_card_txt.setTextColor(getResources().getColor(R.color.whitecolor));
            recharge_card_txt.setTextColor(getResources().getColor(R.color.gray_selected));
            credit_card_img.setImageDrawable(getResources().getDrawable(R.drawable.ic_credit_cards_payment));
            recharge_card_img.setImageDrawable(getResources().getDrawable(R.drawable.ic_recharge_wallet));
            credit_card.setBackgroundColor(getResources().getColor(R.color.green_color));
            recharge_card.setBackgroundColor(getResources().getColor(R.color.grycolor));
            credit_card_lin.setVisibility(View.VISIBLE);
            charge_lin.setVisibility(View.GONE);
        });
        recharge_card.setOnClickListener(v -> {
            credit_card_txt.setTextColor(getResources().getColor(R.color.gray_selected));
            recharge_card_txt.setTextColor(getResources().getColor(R.color.whitecolor));
            credit_card_img.setImageDrawable(getResources().getDrawable(R.drawable.ic_credit_cards_payment_white));
            recharge_card_img.setImageDrawable(getResources().getDrawable(R.drawable.ic_recharge_wallet_white));
            credit_card.setBackgroundColor(getResources().getColor(R.color.grycolor));
            recharge_card.setBackgroundColor(getResources().getColor(R.color.green_color));
            credit_card_lin.setVisibility(View.GONE);
            charge_lin.setVisibility(View.VISIBLE);
        });

        upload.setOnClickListener(v -> {
            /*Intent intent = new Intent(getApplicationContext(),SendToBank.class);
            startActivity(intent);
            finish();*/
        });
        charge_btn.setOnClickListener(v -> {
            if (Objects.requireNonNull(charge_edit_txt.getText()).toString().equals(""))
            {
                if (Language.equals("ar"))
                {
                    charge_edit_txt.setError("من فضلك ادخل الرقم");
                }
                else
                {
                    charge_edit_txt.setError("please enter the number");
                }
            }
            /*else if (Objects.requireNonNull(charge_edit_txt.getText()).toString().length()!=14)
            {
                if (Language.equals("ar"))
                {
                    charge_edit_txt.setError("من فضلك ادخل 14 رقم");
                }
                else
                {
                    charge_edit_txt.setError("please enter 14 number");
                }
            }*/
            else
            {
                //Toast.makeText(this, "ok", Toast.LENGTH_SHORT).show();
                if (Validation.isConnected(RechargeWallet.this))
                {
                    hud.show();
                    mSubscriptions.add(NetworkUtil.getRetrofitByToken(token)
                            .AddTransaction(charge_edit_txt.getText().toString())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io())
                            .subscribe(this::handleResponse, this::handleError));
                }
                else
                {
                    buildDialog(RechargeWallet.this).show().setCanceledOnTouchOutside(false);
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
        {
            Intent intent = new Intent(getApplicationContext(),Wallet.class);
            startActivity(intent);
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
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.show();
        TextView txt = mview.findViewById(R.id.txt);
        TextView ok = mview.findViewById(R.id.ok);
        txt.setText(message);
        ok.setOnClickListener(v2 -> dialog.dismiss());
        hud.dismiss();
    }



    private void handleResponse(ErrorsModel errorsModel) {
        if (errorsModel.getMessage()==null)
        {
            String message="";
            if (Language.equals("ar"))
            {
                message="تم الشحن";
            }
            else
            {
                message="Recharging successfully";
            }

            android.support.v7.app.AlertDialog.Builder builder;
            android.support.v7.app.AlertDialog dialog;
            builder = new android.support.v7.app.AlertDialog.Builder(RechargeWallet.this);

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
                Intent intent = new Intent(getApplicationContext(),Wallet.class);
                startActivity(intent);
                finish();
            });
        }
        else
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(RechargeWallet.this);
            builder.setMessage(errorsModel.getMessage());
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
        hud.dismiss();
    }
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(),Wallet.class);
        startActivity(intent);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        //overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
        finish();
    }
}
