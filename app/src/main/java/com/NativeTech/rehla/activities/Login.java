package com.NativeTech.rehla.activities;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.NativeTech.rehla.model.DataManager;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONObject;

import java.util.Locale;
import java.util.Objects;

import com.NativeTech.rehla.model.data.dto.Models.ForgetPassword.ForgetPasswordResponseModel;
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

public class Login extends AppCompatActivity {

    private Button login;
    private TextView signup;
    private TextView forgetPassword;

    private AppCompatEditText  email;
    private AppCompatEditText  password;

    private android.support.v7.app.AlertDialog dialog1;

    private String Language;
    private CompositeSubscription mSubscriptions;
    private SharedPreferences mSharedPreferences;
    private KProgressHUD hud;
    private String token="";

    Dialog dialogForgetPassword;
    private AppCompatEditText txt;
    private Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("");
        mSubscriptions          = new CompositeSubscription();
        mSharedPreferences      = getSharedPreferences("tokenDetail",MODE_PRIVATE);
        Language                = mSharedPreferences.getString(Constant.language, Locale.getDefault().getLanguage());

        if (Language != null) {
            if (Language.equals("ar"))
            {
                Locale locale = new Locale("ar");
                Locale.setDefault(locale);
                Configuration config = new Configuration();
                config.locale = locale;
                getBaseContext().getResources().updateConfiguration(config,
                        getBaseContext().getResources().getDisplayMetrics());
            }
            else
            {
                Locale locale = new Locale("en");
                Locale.setDefault(locale);
                Configuration config = new Configuration();
                config.locale = locale;
                getBaseContext().getResources().updateConfiguration(config,
                        getBaseContext().getResources().getDisplayMetrics());
            }
        }
        /*if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }*/
        setContentView(R.layout.activity_login);
        //changeStatusBarColor();
        login           =findViewById(R.id.login);
        signup          =findViewById(R.id.signup);
        forgetPassword  =findViewById(R.id.forgetPassword);
        email           =findViewById(R.id.email);
        password        =findViewById(R.id.password);

        token                   = DataManager.getInstance().getCashedAccessToken().getAccess_token();
        hud = KProgressHUD.create(Login.this)
                .setStyle(KProgressHUD.Style.PIE_DETERMINATE)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .setMaxProgress(100);


        android.support.v7.app.AlertDialog.Builder builder1;

        builder1 = new android.support.v7.app.AlertDialog.Builder(Login.this);
        builder1.setView(R.layout.forget_password);
        dialog1 = builder1.create();
        Window window = dialog1.getWindow();
        if (window != null) {
            window.setGravity(Gravity.CENTER);
        }
        //Objects.requireNonNull(dialog1.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //dialog1.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        //dialog1.getWindow().setBackgroundDrawableResource(R.color.app_color);
        //dialog1.show();






        forgetPassword.setOnClickListener(v -> {
            dialog1.show();
            txt = dialog1.findViewById(R.id.phone);
            send=dialog1.findViewById(R.id.submit);
            send.setOnClickListener(v1 -> {
                if (Validation.isConnected(Login.this)) {
                    if (Language.equals("en")) {
                        if (!Validation.validateFields(txt.getText().toString())) {
                            txt.setError("please Enter Phone Number");
                        } else {
                            ForgetPassword();
                        }
                    } else {
                        if (!Validation.validateFields(txt.getText().toString())) {
                            txt.setError("من فضلك ادخل رقم الهاتف");
                        } else {
                            ForgetPassword();
                        }

                    }
                } else {
                    buildDialog(Login.this).show().setCanceledOnTouchOutside(false);
                }
            });
        });




        if(!token.equals("")||!getIntent().getBooleanExtra(Constant.FromSplash,true))
        {
            Intent intent = new Intent(getApplicationContext(),Home.class);
            startActivity(intent);
            overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
            finish();
            return;
        }



        try {
            if (getIntent().getBooleanExtra("f", false)) {
                Intent intent = new Intent(this, Home.class);
                startActivity(intent);
                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                finish();
                return;
            }
        }catch (Exception e){};



/*
        Language                =Locale.getDefault().getLanguage();
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(Constant.language, Language);
        editor.apply();
*/


        signup.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(),Signup.class);
            startActivity(intent);
        });


        login.setOnClickListener(v -> {
            if (Validation.isConnected(Login.this))
            {
                if(Language.equals("en"))
                {
                    if (!Validation.validateFields(email.getText().toString()))
                    {
                        email.setError("please Enter Phone or Email");
                    }
                    else if (!Validation.validateFields(password.getText().toString()))
                    {
                        password.setError("please Enter password");
                    }
                    else if (!Validation.validateFields(password.getText().toString()))
                    {
                        password.setError("password must be greater than 8");
                    }
                    else
                    {
                        login();
                    }
                }
                else
                {

                    if (!Validation.validateFields(email.getText().toString()))
                    {
                        email.setError("من فضلك ادخل رقم الهاتف او الايميل");
                    }
                    else if (!Validation.validateFields(password.getText().toString()))
                    {
                        password.setError("من فضلك ادخل كلمة المرور");
                    }
                    else if (!Validation.validateFields(password.getText().toString()))
                    {
                        password.setError("من فضلك ادخل كلمة المرور اكبر من 8 حروف");
                    }
                    else
                    {
                        login();
                    }

                }
            }
            else
            {
                buildDialog(Login.this).show().setCanceledOnTouchOutside(false);
            }

        });

    }
    private void login()
    {
        if (Validation.isConnected(Login.this))
        {
            hud.show();
            mSubscriptions.add(NetworkUtil.getRetrofitByToken(token)
                    .login(email.getText().toString(),password.getText().toString())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResponse, this::handleError));
        }
        else
        {
            buildDialog(Login.this).show().setCanceledOnTouchOutside(false);
        }
    }
    private void ForgetPassword()
    {
        if (Validation.isConnected(Login.this))
        {
            hud.show();
            mSubscriptions.add(NetworkUtil.getRetrofitByToken(token)
                    .forgetPassword(Objects.requireNonNull(txt.getText()).toString())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResponseForgetPassword, this::handleError));
        }
        else
        {
            buildDialog(Login.this).show().setCanceledOnTouchOutside(false);
        }
    }

    private void handleResponseForgetPassword(ForgetPasswordResponseModel forgetPasswordResponseModel) {
        if (forgetPasswordResponseModel.getMetas()!=null)
        {
            if (forgetPasswordResponseModel.getModel()!=null)
            {
                //password.setText(forgetPasswordResponseModel.getModel());
            }
            String message="";
            if (Language.equals("ar"))
            {
                message="تم ارسال الكود بنجاح";
            }
            else
            {
                message="Code sent successfully";
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
            ok.setOnClickListener(v2 -> dialog.dismiss());
            dialog1.cancel();

            //Intent intent = new Intent(ChangePassword.this, Profile.class);
            //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            //startActivity(intent);
        }
        //finish();
        hud.dismiss();
    }

    private void handleError(Throwable throwable) {
        //Toast.makeText(getActivity(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
        String message="";
        try {
            if (throwable instanceof retrofit2.HttpException) {
                retrofit2.HttpException error = (HttpException) throwable;
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

    private void handleResponse(LoginResponseModel loginResponseModel) {

        try
        {
            if (loginResponseModel.getErrors().getMessage()!=null)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                builder.setMessage(loginResponseModel.getErrors().getMessage());
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
            else {
                if (loginResponseModel.getModel()!=null)
                {
                    if (loginResponseModel.getModel().getToken()!=null)
                    {
                        DataManager.getInstance().saveAccessToken(loginResponseModel.getModel().getToken().get(0));
                    }
                    if (loginResponseModel.getModel().getName()!=null) {
                        SharedPreferences.Editor editor_UserName = mSharedPreferences.edit();
                        editor_UserName.putString(Constant.Username, loginResponseModel.getModel().getName());
                        editor_UserName.apply();
                    }
                    if (loginResponseModel.getModel().getPhoneNumber()!=null)
                    {
                        SharedPreferences.Editor editor_mobile = mSharedPreferences.edit();
                        editor_mobile.putString(Constant.mobile, String.valueOf(loginResponseModel.getModel().getPhoneNumber()));
                        editor_mobile.apply();
                    }

                    if (loginResponseModel.getModel().getProfilePhoto()!=null)
                    {
                        SharedPreferences.Editor editor_image = mSharedPreferences.edit();
                        editor_image.putString(Constant.imageUri, Constant.BASE_URL_profile_img + loginResponseModel.getModel().getProfilePhoto());
                        editor_image.apply();
                    }

                    if (loginResponseModel.getModel().getEmail()!=null)
                    {
                        SharedPreferences.Editor editor_email = mSharedPreferences.edit();
                        editor_email.putString(Constant.Useremail, String.valueOf(loginResponseModel.getModel().getEmail()));
                        editor_email.apply();
                    }

                    if (loginResponseModel.getModel().getPhoneKey()!=null)
                    {
                        SharedPreferences.Editor editor_mobileKey = mSharedPreferences.edit();
                        editor_mobileKey.putString(Constant.mobileKey, String.valueOf(loginResponseModel.getModel().getPhoneKey()));
                        editor_mobileKey.apply();
                    }
                    if (loginResponseModel.getModel().getVCode()!=null)
                    {
                        SharedPreferences.Editor editor_vCode = mSharedPreferences.edit();
                        editor_vCode.putString(Constant.vCode, String.valueOf(loginResponseModel.getModel().getVCode()));
                        editor_vCode.apply();
                    }
                    if (loginResponseModel.getModel().getIdentityCardPhoto()!=null)
                    {
                        SharedPreferences.Editor editor_image = mSharedPreferences.edit();
                        editor_image.putString(Constant.IdentityCardPhoto, String.valueOf(loginResponseModel.getModel().getIdentityCardPhoto()));
                        editor_image.apply();
                    }
                    if (loginResponseModel.getModel().getId()!=null)
                    {
                        SharedPreferences.Editor editor_Id = mSharedPreferences.edit();
                        editor_Id.putString(Constant.ID, String.valueOf(loginResponseModel.getModel().getId()));
                        editor_Id.apply();
                    }

                    SharedPreferences.Editor editor_Verified = mSharedPreferences.edit();
                    editor_Verified.putString(Constant.verifiedStatus, String.valueOf(loginResponseModel.getModel().getVerified()));
                    editor_Verified.apply();

                    //StringTokenizer tk = new StringTokenizer(dataResponseModelLogin.getData().getDoctorBirthDate());
                    //holder.date.setText(tk.nextToken());

                    /*SharedPreferences.Editor editor_birthdate = mSharedPreferences.edit();
                    editor_birthdate.putString(Constant.birthdate, String.valueOf(tk.nextToken()));
                    editor_birthdate.apply();*/

                    if(Long.parseLong(loginResponseModel.getModel().getId())>0 &&loginResponseModel.getModel().getEnabled().contentEquals("true")) {
                        Intent intent = new Intent(Login.this, Home.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        Toast.makeText(Login.this,getString(R.string.you_are_disabled),Toast.LENGTH_LONG).show();
                    }
            /*
            if (loginResponseModel.getModel().getVerified())
            {
                Intent intent = new Intent(Login.this, Home.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }*/
            /*else
            {
                Intent intent = new Intent(Login.this, VerificationCodeRegister.class);
                //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                //finish();
            }*/
                }
            }
        }
        catch (Exception e)
        {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        hud.dismiss();
    }
    /**
     * Making notification bar transparent
     */
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }
}
