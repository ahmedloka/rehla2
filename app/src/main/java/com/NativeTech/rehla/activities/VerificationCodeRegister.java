package com.NativeTech.rehla.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.NativeTech.rehla.model.DataManager;
import com.NativeTech.rehla.model.data.source.preferences.SharedManager;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONObject;

import java.util.Locale;
import java.util.Objects;

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

public class VerificationCodeRegister extends AppCompatActivity {

    private AppCompatEditText code;
    private Button submit;

    private AppCompatTextView skip;

    private String Language;
    private CompositeSubscription mSubscriptions;
    private SharedPreferences mSharedPreferences;
    private KProgressHUD hud;
    private String token="";
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_code_register);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        submit                  =findViewById(R.id.submit);
        skip                    =findViewById(R.id.skip);
        code                    =findViewById(R.id.code);
        mSubscriptions          = new CompositeSubscription();
        mSharedPreferences      = getSharedPreferences("tokenDetail",MODE_PRIVATE);
        //Language                = mSharedPreferences.getString(Constant.language,Locale.getDefault().getLanguage());
        token                   = DataManager.getInstance().getCashedAccessToken().getAccess_token();
        //code.setText(mSharedPreferences.getString(Constant.vCode, ""));
        hud = KProgressHUD.create(VerificationCodeRegister.this)
                .setStyle(KProgressHUD.Style.PIE_DETERMINATE)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .setCancellable(false)
                .setMaxProgress(100);

       // LinearLayoutCompat back                                = findViewById(R.id.back);
       // back.setOnClickListener(v -> finish());


        Language                = Locale.getDefault().getLanguage();
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(Constant.language, Language);
        editor.apply();

        skip.setOnClickListener(v -> {
            Intent intent = new Intent(VerificationCodeRegister.this, Home.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
        submit.setOnClickListener(v -> {
            if (Validation.isConnected(VerificationCodeRegister.this))
            {
                if(Language.equals("en"))
                {
                    if (!Validation.validateFields(code.getText().toString()))
                    {
                        code.setError("please Enter Verification Code");
                    }
                    else
                    {
                        verify();
                    }
                }
                else
                {
                    if (!Validation.validateFields(code.getText().toString()))
                    {
                        code.setError("من فضلك ادخل كود التفعيل");
                    }
                    else
                    {
                        verify();
                    }

                }
            }
            else
            {
                buildDialog(VerificationCodeRegister.this).show().setCanceledOnTouchOutside(false);
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
    private void verify()
    {
        if (Validation.isConnected(VerificationCodeRegister.this))
        {
            token                   = DataManager.getInstance().getCashedAccessToken().getAccess_token();
            hud.show();
            mSubscriptions.add(NetworkUtil.getRetrofitByToken(DataManager.getInstance().getCashedAccessToken().getAccess_token())
                    .verify(mSharedPreferences.getString(Constant.mobile, ""),code.getText().toString())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResponse, this::handleError));
        }
        else
        {
            buildDialog(VerificationCodeRegister.this).show().setCanceledOnTouchOutside(false);
        }
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

    private void handleResponse(LoginResponseModel loginResponseModel) {
        //Toast.makeText(this, "ok", Toast.LENGTH_SHORT).show();

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

            if (loginResponseModel.getModel().getPhoneKey()!=null)
            {
                SharedPreferences.Editor editor_mobileKey = mSharedPreferences.edit();
                editor_mobileKey.putString(Constant.mobileKey, String.valueOf(loginResponseModel.getModel().getPhoneKey()));
                editor_mobileKey.apply();
            }

            if (loginResponseModel.getModel().getId()!=null)
            {
                SharedPreferences.Editor editor_Id = mSharedPreferences.edit();
                editor_Id.putString(Constant.ID, String.valueOf(loginResponseModel.getModel().getId()));
                editor_Id.apply();
            }

            //StringTokenizer tk = new StringTokenizer(dataResponseModelLogin.getData().getDoctorBirthDate());
            //holder.date.setText(tk.nextToken());

                    /*SharedPreferences.Editor editor_birthdate = mSharedPreferences.edit();
                    editor_birthdate.putString(Constant.birthdate, String.valueOf(tk.nextToken()));
                    editor_birthdate.apply();*/

            if (loginResponseModel.getModel().getVerified())
            {
                String message="";
                if (Language.equals("ar"))
                {
                    message="تم التفعيل بنجاح";
                }
                else
                {
                    message="activated successfully";
                }

                android.support.v7.app.AlertDialog.Builder builder;
                android.support.v7.app.AlertDialog dialog;
                builder = new android.support.v7.app.AlertDialog.Builder(VerificationCodeRegister.this);

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
                    Intent intent = new Intent(VerificationCodeRegister.this, Home.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                });
            }
            else
            {
                Intent intent = new Intent(VerificationCodeRegister.this, VerificationCodeRegister.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        }
        hud.dismiss();
    }
}
