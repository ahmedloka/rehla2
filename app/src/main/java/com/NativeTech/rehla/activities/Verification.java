package com.NativeTech.rehla.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.NativeTech.rehla.model.DataManager;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONObject;

import java.util.Locale;
import java.util.Objects;

import com.NativeTech.rehla.model.data.dto.Models.ErrorsModel;
import com.NativeTech.rehla.model.data.dto.Models.ErrorsModelMessageOnly;
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

public class Verification extends AppCompatActivity {

    private String Language;
    private CompositeSubscription mSubscriptions;
    private SharedPreferences mSharedPreferences;
    private KProgressHUD hud;

    private Button btn_verify_phone;
    private Button btn_verify_email;
    private Button btn_verify_identity;

    private TextView phone;
    private TextView email;
    private TextView identity;

    private AppCompatImageView phone_not_verified;
    private AppCompatImageView phone_verified;
    private AppCompatImageView email_not_verified;
    private AppCompatImageView email_verified;
    private AppCompatImageView identity_not_verified;
    private AppCompatImageView identity_verified;

    private android.support.v7.app.AlertDialog dialog1;

    AppCompatEditText txt;

    private String phone_num="";
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.verify_account);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        btn_verify_phone=findViewById(R.id.btn_verify_phone);
        btn_verify_email=findViewById(R.id.btn_verify_email);
        btn_verify_identity=findViewById(R.id.btn_verify_identity);
        phone=findViewById(R.id.phone);
        email=findViewById(R.id.email);
        identity=findViewById(R.id.identity);

        phone_not_verified=findViewById(R.id.phone_not_verified);
        phone_verified=findViewById(R.id.phone_verified);
        email_not_verified=findViewById(R.id.email_not_verified);
        email_verified=findViewById(R.id.email_verified);
        identity_not_verified=findViewById(R.id.identity_not_verified);
        identity_verified=findViewById(R.id.identity_verified);

        phone_not_verified.setVisibility(View.GONE);
        phone_verified.setVisibility(View.GONE);
        email_not_verified.setVisibility(View.GONE);
        email_verified.setVisibility(View.GONE);
        identity_not_verified.setVisibility(View.GONE);
        identity_verified.setVisibility(View.GONE);

        btn_verify_phone.setVisibility(View.GONE);
        btn_verify_email.setVisibility(View.GONE);
        btn_verify_identity.setVisibility(View.GONE);

        btn_verify_identity.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(),UploadLicence.class);
            startActivity(intent);
            //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            /*overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
            finish();*/
        });

        btn_verify_email.setOnClickListener(v -> RequestEmailVerification());

        btn_verify_phone.setOnClickListener(v -> {
            /*android.support.v7.app.AlertDialog.Builder builder1;

            builder1 = new android.support.v7.app.AlertDialog.Builder(Verification.this);
            builder1.setView(R.layout.verify_phone);
            dialog1 = builder1.create();
            Window window = dialog1.getWindow();
            if (window != null) {
                window.setGravity(Gravity.CENTER);
            }
            dialog1.show();
            txt = dialog1.findViewById(R.id.phone);
            Button send=dialog1.findViewById(R.id.submit);
            if (send != null) {
                send.setOnClickListener(v1 -> {
                    if (Validation.isConnected(Verification.this))
                    {
                        if(Language.equals("ar"))
                        {
                            if (!Validation.validateFields(txt.getText().toString()))
                            {
                                txt.setError("من فضلك ادخل رقم الهاتف");
                            }
                            else
                            {
                                RequestPhoneVerification();
                            }
                        }
                        else
                        {
                            if (!Validation.validateFields(txt.getText().toString()))
                            {
                                txt.setError("please Enter Phone Number");
                            }
                            else
                            {
                                RequestPhoneVerification();
                            }

                        }
                    }
                    else
                    {
                        buildDialog(Verification.this).show().setCanceledOnTouchOutside(false);
                    }
                });
            }*/
            RequestPhoneVerification();
        });

        mSharedPreferences  =   getSharedPreferences("tokenDetail",MODE_PRIVATE);
        Language            =   mSharedPreferences.getString(Constant.language, Locale.getDefault().getLanguage());
        mSubscriptions      =   new CompositeSubscription();
        hud = KProgressHUD.create(Verification.this)
                .setStyle(KProgressHUD.Style.PIE_DETERMINATE)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .setCancellable(false)
                .setMaxProgress(100);


        if (Validation.isConnected(Verification.this))
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
            buildDialog(Verification.this).show().setCanceledOnTouchOutside(false);
            hud.dismiss();
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
    private void RequestEmailVerification()
    {
        if (Validation.isConnected(Verification.this))
        {
            hud.show();
            mSubscriptions.add(NetworkUtil.getRetrofitByToken(DataManager.getInstance().getCashedAccessToken().getAccess_token())
                    .RequestEmailVerification()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResponseRequestEmailVerification, this::handleError));
        }
        else
        {
            buildDialog(Verification.this).show().setCanceledOnTouchOutside(false);
        }
    }

    private void RequestPhoneVerification()
    {
        if (Validation.isConnected(Verification.this))
        {
            hud.show();
            mSubscriptions.add(NetworkUtil.getRetrofitByToken(DataManager.getInstance().getCashedAccessToken().getAccess_token())
                    .RequestUserPhoneVerification(phone_num)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResponseRequestPhoneVerification, this::handleError));
        }
        else
        {
            buildDialog(Verification.this).show().setCanceledOnTouchOutside(false);
        }
    }

    private void handleResponseRequestPhoneVerification(ErrorsModelMessageOnly errorsModelMessageOnly) {
        if (errorsModelMessageOnly.getMessage()==null)
        {
           /* SharedPreferences.Editor editor_mobile = mSharedPreferences.edit();
            editor_mobile.putString(Constant.mobile, String.valueOf(Objects.requireNonNull(txt.getText()).toString()));
            editor_mobile.apply();*/

            Intent intent = new Intent(Verification.this, VerificationCodeRegister.class);
            //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            //finish();
        }
        else
        {
            //String message=jsonObject.getJSONObject("errors").getString("message");
            AlertDialog.Builder builder = new AlertDialog.Builder(Verification.this);
            builder.setMessage(errorsModelMessageOnly.getMessage());
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

    private void handleResponseRequestEmailVerification(ErrorsModel errorsModel) {
        if (errorsModel.getMessage()==null)
        {
            android.support.v7.app.AlertDialog.Builder builder1;

            builder1 = new android.support.v7.app.AlertDialog.Builder(Verification.this);
            builder1.setView(R.layout.verify_email);
            dialog1 = builder1.create();
            Window window = dialog1.getWindow();
            if (window != null) {
                window.setGravity(Gravity.CENTER);
            }
            dialog1.setCancelable(false);
            dialog1.setCanceledOnTouchOutside(false);
            dialog1.show();
            Button ok=dialog1.findViewById(R.id.submit);
            Button cancel=dialog1.findViewById(R.id.cancel);
            AppCompatEditText code=dialog1.findViewById(R.id.code);
            if (ok != null) {
                ok.setOnClickListener(v ->{
                    //dialog1.cancel();
                    if (Validation.isConnected(Verification.this))
                    {
                        hud.show();
                        if (code != null) {
                            mSubscriptions.add(NetworkUtil.getRetrofitByToken(DataManager.getInstance().getCashedAccessToken().getAccess_token())
                                    .VerifyEmail(Objects.requireNonNull(code.getText()).toString())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribeOn(Schedulers.io())
                                    .subscribe(this::handleResponseVerifyEmail, this::handleError));
                        }
                    }
                    else
                    {
                        buildDialog(Verification.this).show().setCanceledOnTouchOutside(false);
                    }
                });
            }
            if (cancel != null) {
                cancel.setOnClickListener(v ->
                {
                    dialog1.cancel();
                    btn_verify_email.setVisibility(View.VISIBLE);
                });
            }

        }
        else
        {
            //String message=jsonObject.getJSONObject("errors").getString("message");
            AlertDialog.Builder builder = new AlertDialog.Builder(Verification.this);
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
        //finish();
        hud.dismiss();
    }

    private void handleResponseVerifyEmail(ErrorsModel errorsModel) {
        if (errorsModel.getMessage()==null)
        {
            String message="";
            if (Language.equals("ar"))
            {
                message="تم تفعيل الايميل";
            }
            else
            {
                message="Email has been activated";
            }

            android.support.v7.app.AlertDialog.Builder builder;
            android.support.v7.app.AlertDialog dialog;
            builder = new android.support.v7.app.AlertDialog.Builder(Verification.this);

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
                Intent intent = new Intent(Verification.this, Verification.class);
                //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                finish();
            });

        }
        else
        {
            //String message=jsonObject.getJSONObject("errors").getString("message");
            AlertDialog.Builder builder = new AlertDialog.Builder(Verification.this);
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
        //finish();
        hud.dismiss();
    }


    private void handleResponseGetData(LoginResponseModel loginResponseModel) {
        if (loginResponseModel.getModel()!=null)
        {
            if (loginResponseModel.getModel().getVerified())
            {
                phone_verified.setVisibility(View.VISIBLE);
            }
            else
            {
                btn_verify_phone.setVisibility(View.VISIBLE);
            }
            if (loginResponseModel.getModel().getEmailVerified().equals("true"))
            {
                email_verified.setVisibility(View.VISIBLE);
            }
            else
            {
                btn_verify_email.setVisibility(View.VISIBLE);
            }
            if (loginResponseModel.getModel().getIdentityNumberVerified().equals("true"))
            {
                identity_verified.setVisibility(View.GONE);
            }
            else
            {
                btn_verify_identity.setVisibility(View.VISIBLE);
            }
            phone.setText(loginResponseModel.getModel().getPhoneNumber());
            phone_num=loginResponseModel.getModel().getPhoneNumber();
            email.setText(loginResponseModel.getModel().getEmail());

            if (loginResponseModel.getModel().getIdentityCardPhoto()!=null)
            {
                SharedPreferences.Editor editor_IdentityCardPhoto = mSharedPreferences.edit();
                editor_IdentityCardPhoto.putString(Constant.IdentityCardPhoto, String.valueOf(loginResponseModel.getModel().getIdentityCardPhoto()));
                editor_IdentityCardPhoto.apply();
            }
            else
            {
                SharedPreferences.Editor editor_IdentityCardPhoto = mSharedPreferences.edit();
                editor_IdentityCardPhoto.putString(Constant.IdentityCardPhoto, "");
                editor_IdentityCardPhoto.apply();
            }

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
        dialog.show();
        TextView txt = mview.findViewById(R.id.txt);
        TextView ok = mview.findViewById(R.id.ok);
        txt.setText(message);
        ok.setOnClickListener(v2 -> dialog.dismiss());
        hud.dismiss();
    }

}
