package com.NativeTech.rehla.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.NativeTech.rehla.model.DataManager;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.squareup.picasso.Picasso;

import java.util.Locale;

import com.NativeTech.rehla.R;
import com.NativeTech.rehla.Utills.Constant;
import de.hdodenhof.circleimageview.CircleImageView;
import rx.subscriptions.CompositeSubscription;

public class Profile extends AppCompatActivity {

    private CircleImageView image_profile;
    private TextView car;
    private TextView edit_profile;
    private TextView licence;
    private TextView preference;
    private TextView rating;
    private TextView verification;
    private TextView name;
    private TextView email;
    private TextView phone;


    private String Language;
    private SharedPreferences mSharedPreferences;
    private KProgressHUD hud;
    private String token="";
    private String img="";
    private String nameValue="";
    private String phoneValue="";
    private String emailValue="";
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



        CompositeSubscription mSubscriptions = new CompositeSubscription();
        mSharedPreferences      = getSharedPreferences("tokenDetail",MODE_PRIVATE);
        Language                = mSharedPreferences.getString(Constant.language, Locale.getDefault().getLanguage());
        token                   = DataManager.getInstance().getCashedAccessToken().getAccess_token();
        img                     = mSharedPreferences.getString(Constant.imageUri, "");
        nameValue               = mSharedPreferences.getString(Constant.Username, "");
        phoneValue              = mSharedPreferences.getString(Constant.mobile, "");
        emailValue              = mSharedPreferences.getString(Constant.Useremail, "");

        hud = KProgressHUD.create(Profile.this)
                .setStyle(KProgressHUD.Style.PIE_DETERMINATE)
                .setAnimationSpeed(2)
                .setCancellable(false)
                .setDimAmount(0.5f)
                .setMaxProgress(100);

        /*ShortcutBadger.applyCount(getApplicationContext(), 0); //for 1.1.4+

        SharedPreferences.Editor editor_newToken = mSharedPreferences.edit();
        editor_newToken.putString(Constant.badgeCount, "0");
        editor_newToken.apply();*/

        //Toast.makeText(this, img, Toast.LENGTH_SHORT).show();

        if(token.equals(""))
        {
            Intent intent = new Intent(Profile.this, Login.class);
            startActivity(intent);
            overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
            finish();
        }



        image_profile                       = findViewById(R.id.image_profile);
        car                                 = findViewById(R.id.car);
        name                                = findViewById(R.id.name);
        email                               = findViewById(R.id.email);
        phone                               = findViewById(R.id.phone);
        edit_profile                        = findViewById(R.id.edit_profile);
        licence                             = findViewById(R.id.licence);
        verification                        = findViewById(R.id.verification);
        preference                          = findViewById(R.id.preference);
        rating                              = findViewById(R.id.rating);
        setImg(img,image_profile);

        name.setText(nameValue);
        email.setText(emailValue);
        phone.setText(phoneValue);

        car.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(),Cars.class);
            startActivity(intent);
        });
        preference.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(),Preference.class);
            startActivity(intent);
        });

        edit_profile.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(),EditProfile.class);
            startActivity(intent);
            finish();
        });
        licence.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(),UploadLicence.class);
            startActivity(intent);
        });
        rating.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(),Reviews.class);
            startActivity(intent);
        });
        verification.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(),Verification.class);
            startActivity(intent);
        });

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
    private void setImg(String url, ImageView image)
    {

        if(url.equals(""))
        {
            image.setImageResource(R.drawable.ic_user);
        }
        else {
            try {

                Picasso.with(Profile.this)
                        .load(url)
                        .placeholder(R.drawable.ic_user)
                        .into(image);
            } catch (Exception ignored) {

            }
        }
    }
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(),Home.class);
        startActivity(intent);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
        finish();
    }
}
