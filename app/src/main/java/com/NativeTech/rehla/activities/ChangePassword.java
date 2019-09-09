package com.NativeTech.rehla.activities;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
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

import com.NativeTech.rehla.model.data.dto.Models.ChangePassword.ChangePasswordRequest;
import com.NativeTech.rehla.model.data.dto.Models.ChangePassword.ChangePasswordResponseModel;
import com.NativeTech.rehla.Network.NetworkUtil;
import com.NativeTech.rehla.R;
import com.NativeTech.rehla.Utills.Constant;
import com.NativeTech.rehla.Utills.Validation;

import retrofit2.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static com.NativeTech.rehla.Utills.Constant.buildDialog;
import static com.NativeTech.rehla.Utills.Validation.validateFields;
import static com.NativeTech.rehla.Utills.Validation.validatePassword;

public class ChangePassword extends AppCompatActivity {


    private String Language;
    private CompositeSubscription mSubscriptions;
    private SharedPreferences mSharedPreferences;
    private KProgressHUD hud;

    private AppCompatEditText old;
    private AppCompatEditText new_pass;
    private AppCompatEditText confirm;

    private Button            submit;

    public String token="";
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);


        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
         getSupportActionBar().setDisplayShowHomeEnabled(true);


        old                 =findViewById(R.id.old);
        new_pass            =findViewById(R.id.new_pass);
        confirm             =findViewById(R.id.confirm);
        submit              =findViewById(R.id.submit);

        mSharedPreferences  =   getSharedPreferences("tokenDetail",MODE_PRIVATE);
        Language            =   mSharedPreferences.getString(Constant.language,Locale.getDefault().getLanguage());
        mSubscriptions      =   new CompositeSubscription();
        hud = KProgressHUD.create(ChangePassword.this)
                .setStyle(KProgressHUD.Style.PIE_DETERMINATE)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .setCancellable(false)
                .setMaxProgress(100);


        submit.setOnClickListener(v -> {
            if(Language.equals("en"))
            {
                if (Validation.isConnected(ChangePassword.this)) {
                    if (!validateFields(Objects.requireNonNull(old.getText()).toString())) {
                        old.setError("please Enter old password");
                    } else if (!validatePassword(old.getText().toString())) {
                        old.setError("please check password length must be greater than 6");
                    } else if (!validateFields(Objects.requireNonNull(new_pass.getText()).toString())) {
                        new_pass.setError("please Enter new password");
                    } else if (!validatePassword(new_pass.getText().toString())) {
                        new_pass.setError("please check password length must be greater than 6");
                    } else if (!validateFields(Objects.requireNonNull(confirm.getText()).toString())) {
                        confirm.setError("please Enter confirm password");
                    } else if (!validatePassword(confirm.getText().toString())) {
                        confirm.setError("please check password length must be greater than 6");
                    } else {
                        if (new_pass.getText().toString().equals(confirm.getText().toString())) {
                            //Toast.makeText(ChangePassword.this, "successfully,waiting api integration", Toast.LENGTH_LONG).show();
                            changePassword();
                        } else {
                            confirm.setError("Password does not match");
                        }
                    }
                } else {
                    buildDialog(ChangePassword.this).show().setCanceledOnTouchOutside(false);
                }

            }
            else
            {
                if (Validation.isConnected(ChangePassword.this)) {
                    if (!validateFields(old.getText().toString())) {
                        old.setError("من فضلك ادخل كلمة المرور القديمة");
                    } else if (!validatePassword(old.getText().toString())) {
                        old.setError("كلمة المرور يجب ان تكون اكبر من 6");
                    } else if (!validateFields(new_pass.getText().toString())) {
                        new_pass.setError("من فضلك ادخل كلمة المرور الجديدة");
                    } else if (!validatePassword(new_pass.getText().toString())) {
                        new_pass.setError("كلمة المرور يجب ان تكون اكبر من 6");
                    } else if (!validateFields(confirm.getText().toString())) {
                        confirm.setError("من فضلك اكد كلمة المرور الجديدة");
                    } else if (!validatePassword(confirm.getText().toString())) {
                        confirm.setError("كلمة المرور يجب ان تكون اكبر من 6");
                    } else {
                        if (new_pass.getText().toString().equals(confirm.getText().toString())) {
                            //Toast.makeText(ChangePassword.this, "successfully,waiting api integration", Toast.LENGTH_LONG).show();
                            changePassword();
                        } else {
                            confirm.setError("كلمة المرور الجديدة غير متطابقتان");
                        }
                    }
                } else {
                    buildDialog(ChangePassword.this).show().setCanceledOnTouchOutside(false);
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
    private void changePassword()
    {
        hud.show();
        ChangePasswordRequest changePasswordRequest=new ChangePasswordRequest();
        changePasswordRequest.setNewPassword(Objects.requireNonNull(new_pass.getText()).toString());
        changePasswordRequest.setOldPassword(Objects.requireNonNull(old.getText()).toString());
        mSubscriptions.add(NetworkUtil.getRetrofitByToken(DataManager.getInstance().getCashedAccessToken().getAccess_token())
                .changePassword(changePasswordRequest)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError));
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
            else {
                //Toast.makeText(AcceptOrRejectTrip.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                message = throwable.getMessage();
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

    private void handleResponse(ChangePasswordResponseModel changePasswordResponseModel) {
        if (changePasswordResponseModel.getMetas()!=null)
        {
            String message="";
            if (Language.equals("ar"))
            {
                message="تم تغيير كلمة المرور بنجاح";
            }
            else
            {
                message="Password changed successfully";
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
                finish();
            });
                //Intent intent = new Intent(ChangePassword.this, Profile.class);
                //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                //startActivity(intent);
               // finish();
        }
        //finish();
        hud.dismiss();
    }
}
