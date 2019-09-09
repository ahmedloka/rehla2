package com.NativeTech.rehla.activities;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.Locale;
import java.util.Objects;


import com.NativeTech.rehla.R;
import com.NativeTech.rehla.Utills.Constant;


public class Settings extends AppCompatActivity {

    private TextView change_password;
    private TextView lang;
    private TextView faq;
    private TextView contact_us;
    private TextView terms_condition;
    private Dialog dialog;

    private String Language;
    private SharedPreferences mSharedPreferences;
    private KProgressHUD hud;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        setContentView(R.layout.activity_settings);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.settings);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        change_password         =findViewById(R.id.change_password);
        lang                    =findViewById(R.id.lang);
        faq                     =findViewById(R.id.faq);
        contact_us              =findViewById(R.id.contact_us);
        terms_condition         =findViewById(R.id.terms_condition);




        hud = KProgressHUD.create(Settings.this)
                .setStyle(KProgressHUD.Style.PIE_DETERMINATE)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .setCancellable(false)
                .setMaxProgress(100);



        change_password.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(),ChangePassword.class);
            startActivity(intent);
        });
        faq.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(),FAQ.class);
            startActivity(intent);
        });
        contact_us.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(),CallUs.class);
            startActivity(intent);
        });
        terms_condition.setOnClickListener(v -> {
            Intent intent = new Intent(Settings.this,TermsAndCondition.class);
            startActivity(intent);
        });
        lang.setOnClickListener(v -> {
            Rect displayRectangle = new Rect();
            Window window = Objects.requireNonNull(getWindow());
            window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
            //  builder                        = new AlertDialog.Builder(getContext());
            @SuppressLint("InflateParams")
            View mview                     = getLayoutInflater().inflate(R.layout.dialog_change_lang, null);
            dialog = new BottomSheetDialog(Settings.this);
            dialog.setContentView(mview);
            dialog.show();
            AppCompatTextView   arabic            =dialog.findViewById(R.id.arabic);
            AppCompatTextView   english           =dialog.findViewById(R.id.english);
            Button              cancel            =dialog.findViewById(R.id.cancel);
            arabic.setOnClickListener(v1 -> {

                if (Language.equals("ar"))
                {
                    dialog.cancel();
                }
                else {
                    SharedPreferences.Editor editor = mSharedPreferences.edit();
                    editor.putString(Constant.language, "ar");
                    editor.apply();

                    Intent intent = new Intent(getApplicationContext(), Settings.class);
                    startActivity(intent);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                    finish();
                }
            });
            english.setOnClickListener(v12 -> {
                if (Language.equals("en"))
                {
                    dialog.cancel();
                }
                else
                {
                    SharedPreferences.Editor editor = mSharedPreferences.edit();
                    editor.putString(Constant.language, "en");
                    editor.apply();

                    Intent intent = new Intent(getApplicationContext(),Settings.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                    finish();
                }
            });
            cancel.setOnClickListener(view -> dialog.cancel());
        });
    }
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(),Home.class);
        startActivity(intent);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
        finish();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
        {
            Intent intent = new Intent(getApplicationContext(),Home.class);
            startActivity(intent);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
