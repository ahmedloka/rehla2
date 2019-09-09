package com.NativeTech.rehla.activities;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.NativeTech.rehla.App;
import com.NativeTech.rehla.model.DataManager;
import com.NativeTech.rehla.model.data.dto.Models.LoginModel.LoginResponseModel;
import com.NativeTech.rehla.mvp.presenter.SpalshPresenter;
import com.NativeTech.rehla.mvp.view.SplashView;
import com.crashlytics.android.Crashlytics;

import java.util.Locale;

import com.NativeTech.rehla.R;
import com.NativeTech.rehla.Utills.Constant;
import com.NativeTech.rehla.services.SignalRService;
import io.fabric.sdk.android.Fabric;
import rx.subscriptions.CompositeSubscription;

public class Spalsh extends AppCompatActivity implements SplashView {

    private String token="";
    private CompositeSubscription mSubscriptions;
    String Img_URL1;
    private String Language;
    public SharedPreferences mSharedPreferences;



    private final Context mContext = this;
    private boolean mBound = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.mContext=this;
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // Making notification bar transparent
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        new Handler().postDelayed(() -> {

            Fabric.with(this, new Crashlytics());
            mSubscriptions=new CompositeSubscription();
            SharedPreferences sharedPreferences = getSharedPreferences("tokenDetail", MODE_PRIVATE);
            token = DataManager.getInstance().getCashedAccessToken().getAccess_token();
            Language            =   sharedPreferences.getString(Constant.language,"");

            if(Language.isEmpty())
            {
                mSharedPreferences  =   mContext.getSharedPreferences("tokenDetail",MODE_PRIVATE);
                Language= Resources.getSystem().getConfiguration().locale.getLanguage();
                SharedPreferences.Editor editor = mSharedPreferences.edit();
                editor.putString(Constant.language,Language);
                editor.apply();
            }


            Constant.initService(Spalsh.this);

            Locale locale = new Locale(Language);
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config,
                    getBaseContext().getResources().getDisplayMetrics());


            new SpalshPresenter(this).getCustomerProfile();

        },2000);

        setContentView(R.layout.activity_spalsh);
        changeStatusBarColor();


    }
    /**
     * Defines callbacks for service binding, passed to bindService()
     */
    private final ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to SignalRService, cast the IBinder and get SignalRService instance
            SignalRService.LocalBinder binder = (SignalRService.LocalBinder) service;
            SignalRService mService = binder.getService();
            // Toast.makeText(mContext, "onServiceConnected", Toast.LENGTH_SHORT).show();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            Toast.makeText(mContext, "Disconnected", Toast.LENGTH_SHORT).show();
            mBound = false;
        }
    };

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

    @Override
    public void onprofile(LoginResponseModel loginResponseModel) {

        try
        {
            if(Long.parseLong(loginResponseModel.getModel().getId())>0 &&loginResponseModel.getModel().getEnabled().contentEquals("true"))
            {

                Intent intent1 = new Intent(getApplicationContext(),Home.class);
                startActivity(intent1);
                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                finish();


            }
            else{
                Intent intent = new Intent(getApplicationContext(),Login.class);
                intent.putExtra(Constant.FromSplash,true);
                startActivity(intent);
                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                finish();
            }
        }
        catch (Exception e)
        {
            Intent intent = new Intent(getApplicationContext(),Login.class);
            intent.putExtra(Constant.FromSplash,true);
            startActivity(intent);
            overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
            finish();

        }
    }

    @Override
    public void onFinished() {

    }

    @Override
    public void onDismissLoader() {

    }

    @Override
    public void onShowLoader() {

    }

    @Override
    public void onTimeOut() {
        Intent intent = new Intent(getApplicationContext(),Login.class);
        intent.putExtra(Constant.FromSplash,true);
        startActivity(intent);
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
        finish();
    }

    @Override
    public void onError(int code) {
        Intent intent = new Intent(getApplicationContext(),Login.class);
        intent.putExtra(Constant.FromSplash,true);
        startActivity(intent);
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
        finish();
    }
}
