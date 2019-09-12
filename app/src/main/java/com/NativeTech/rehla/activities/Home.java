package com.NativeTech.rehla.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.location.Location;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.NativeTech.rehla.model.DataManager;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.NativeTech.rehla.model.data.dto.Models.AddToken.AddTokenRequest;
import com.NativeTech.rehla.model.data.dto.Models.ChatCount.ChatResponseModel;
import com.NativeTech.rehla.Network.NetworkUtil;
import com.NativeTech.rehla.R;
import com.NativeTech.rehla.Utills.Constant;
import com.NativeTech.rehla.fragments.CaptinTrips;
import com.NativeTech.rehla.fragments.PassengerTrips;

import de.hdodenhof.circleimageview.CircleImageView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class Home extends AppCompatActivity {

    private static ViewPager viewPager;
    private ImageView tab_icon;
    private final int[] navIcons = {

            R.drawable.ic_captin_trips,
            R.drawable.ic_logo,
            R.drawable.ic_seat
    };

    private final int[] navLabels = {

            R.string.captin_trips,
            R.string.home,
            R.string.passenger_trips
    };
    private static TabLayout tabLayout;
    private ViewPagerAdapter adapter;

    private String Language;
    private SharedPreferences mSharedPreferences;
    //KProgressHUD hud;


    private FusedLocationProviderClient client;



    private DrawerLayout drawer;
   // private LinearLayoutCompat imageNavigationIcon;
    private CircleImageView image_profile;
    private TextView name;
    private TextView captin_rides;
    private TextView passenger_rides;
    private TextView waiting_trip;
    private TextView profile;
    //TextView message;
    private TextView notification;
    private TextView wallet;
    private TextView setting;
    private TextView logout;


    private TextView count_chat;

    private String token="";
    private String img="";
    private String nameValue="";

    private LinearLayoutCompat message_lin;

    LocationRequest mLocationRequest;
    Location mLastLocation;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CompositeSubscription mSubscriptions = new CompositeSubscription();
        mSharedPreferences      = getSharedPreferences("tokenDetail",MODE_PRIVATE);
        Language                = mSharedPreferences.getString(Constant.language, Locale.getDefault().getLanguage());

        Log.d("TOKEN_", "onCreate: " + DataManager.getInstance().getCashedAccessToken().getAccess_token());

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
//            requestWindowFeature(Window.getDefaultFeatures(Home.this));
//        }
       // this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

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
        setContentView(R.layout.activity_home);


        token                   = DataManager.getInstance().getCashedAccessToken().getAccess_token();
        Log.d("TOKEN_", "onCreate: "+token);
        Toast.makeText(this, token, Toast.LENGTH_SHORT).show();
        img                     = mSharedPreferences.getString(Constant.imageUri, "");
        nameValue               = mSharedPreferences.getString(Constant.Username, "");



       /* hud = KProgressHUD.create(getApplicationContext())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .setMaxProgress(100);*/

        if(token.equals(""))
        {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
            finish();
        }

        //hud.show();
       try
        {
            AddTokenRequest addTokenRequest=new AddTokenRequest();
            addTokenRequest.setOS("Android");
            addTokenRequest.setToken(mSharedPreferences.getString(Constant.devicetoken, ""));
            mSubscriptions.add(NetworkUtil.getRetrofitByToken(DataManager.getInstance().getCashedAccessToken().getAccess_token())
                    .AddToken(addTokenRequest)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(errorsModel -> handleResponse(), throwable -> handeError()));

            mSubscriptions.add(NetworkUtil.getRetrofitByToken(DataManager.getInstance().getCashedAccessToken().getAccess_token())
                    .getUnseenMessagesCount()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResponseGetCount, throwable -> handeError()));

        }
        catch (Exception e)
        {
           // Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

//        if (Validation.isConnected(Home.this)) {
//            //hud.show();
//            mSubscriptions.add(NetworkUtil.getRetrofitByToken(DataManager.getInstance().getCashedAccessToken().getAccess_token())
//                    .checkDriverStartedTrip()
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribeOn(Schedulers.io())
//                    .subscribe(this::handleResponse, this::handleError));
//        } else {
//            buildDialog(Home.this).show().setCanceledOnTouchOutside(false);
//        }



       /* ShortcutBadger.applyCount(getApplicationContext(), 0); //for 1.1.4+

        SharedPreferences.Editor editor_newToken = mSharedPreferences.edit();
        editor_newToken.putString(Constant.badgeCount, "0");
        editor_newToken.apply();
        */
        //Toast.makeText(this, mSharedPreferences.getString(Constant.devicetoken, ""), Toast.LENGTH_SHORT).show();
        /*Log.e("on new token", mSharedPreferences.getString(Constant.devicetoken, ""));
*/
        count_chat                      = findViewById(R.id.count_chat);
        name                            = findViewById(R.id.name);
        drawer                          = findViewById(R.id.drawer_layout);
       // imageNavigationIcon             = findViewById(R.id.side_menu);
        image_profile                   = findViewById(R.id.img);
        captin_rides                    = findViewById(R.id.captin_rides);
        passenger_rides                 = findViewById(R.id.passenger_rides);
        waiting_trip                    = findViewById(R.id.waiting_trip);
        profile                         = findViewById(R.id.profile);
        //message                         = findViewById(R.id.message);
        wallet                          = findViewById(R.id.wallet);
        notification                    = findViewById(R.id.notification);
        setting                         = findViewById(R.id.setting);
        logout                          = findViewById(R.id.logout);
        message_lin                     = findViewById(R.id.message_lin);

        setImg(img,image_profile);
        name.setText(nameValue);





        //
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

         ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toggle.setDrawerSlideAnimationEnabled(true);
        toggle.setToolbarNavigationClickListener(view -> drawer.openDrawer(GravityCompat.START));

       /* imageNavigationIcon.setOnClickListener(view1 -> {
            if (drawer.isDrawerOpen(Gravity.START)) {
                drawer.closeDrawer(Gravity.START);
            } else {
                drawer.openDrawer(Gravity.START);
            }
        });*/
        message_lin.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(),AllChats.class);
            startActivity(intent);
        });

        profile.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(),Profile.class);
            startActivity(intent);
            finish();
        });
        waiting_trip.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(),WaitingTrips.class);
            startActivity(intent);
        });
        notification.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(),Notifications.class);
            startActivity(intent);
        });

        setting.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(),Settings.class);
            startActivity(intent);
        });
        wallet.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(),Wallet.class);
            startActivity(intent);
        });
        logout.setOnClickListener(v -> {
            SharedPreferences.Editor editor_userID = mSharedPreferences.edit();
           DataManager.getInstance().removeToken();
            editor_userID.apply();
            SharedPreferences.Editor editor_UserName = mSharedPreferences.edit();
            editor_UserName.putString(Constant.Username, "");
            editor_UserName.apply();
            SharedPreferences.Editor editor_email = mSharedPreferences.edit();
            editor_email.putString(Constant.Useremail, "");
            editor_email.apply();
            SharedPreferences.Editor editor_mobileKey = mSharedPreferences.edit();
            editor_mobileKey.putString(Constant.mobileKey, "");
            editor_mobileKey.apply();
            SharedPreferences.Editor editor_mobile = mSharedPreferences.edit();
            editor_mobile.putString(Constant.mobile, "");
            editor_mobile.apply();
            SharedPreferences.Editor editor_image = mSharedPreferences.edit();
            editor_image.putString(Constant.imageUri, "");
            editor_image.apply();
            SharedPreferences.Editor editor_vCode = mSharedPreferences.edit();
            editor_vCode.putString(Constant.vCode, "");
            editor_vCode.apply();
            SharedPreferences.Editor verifiedStatus = mSharedPreferences.edit();
            verifiedStatus.putString(Constant.verifiedStatus, "");
            verifiedStatus.apply();
            Intent intent = new Intent(getApplicationContext(), Login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
            finish();
        });
        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.main_tab_content);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);


         tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_captin_trips_selector).setText( getString(R.string.captin_trips));
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_home_selector).setText(getString(R.string.home));
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_seat_selector).setText(getString(R.string.passenger_trips) );
        tabLayout.getTabAt(1).select();

    }

    private void handleResponseGetCount(ChatResponseModel chatResponseModel) {

        switch (chatResponseModel.getModel()) {
            case "":
                count_chat.setVisibility(View.GONE);
                break;
            case "0":
                count_chat.setVisibility(View.GONE);
                break;
            default:
                count_chat.setVisibility(View.VISIBLE);
                count_chat.setText(chatResponseModel.getModel());
                break;
        }

    }

    private void handleError() {
    }



    private void handeError() {
       // Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
    }

    private void handleResponse() {
        //Toast.makeText(this, errorsModel.getMessage(), Toast.LENGTH_SHORT).show();
    }

    private void setupViewPager(ViewPager viewPager) {
            adapter = new ViewPagerAdapter(getSupportFragmentManager());
            adapter.insertNewFragment(new CaptinTrips());
            adapter.insertNewFragment(new com.NativeTech.rehla.fragments.Home());
            adapter.insertNewFragment(new PassengerTrips());
        //Toast.makeText(getActivity(),Category_ID,Toast.LENGTH_LONG).show();
        viewPager.setAdapter(adapter);
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        private boolean locked = false;
        private int lockedIndex;


        public void setLocked(boolean locked, int page) {
            this.locked = locked;
            lockedIndex = page;
            notifyDataSetChanged();
        }
        @Override
        public Fragment getItem(int position) {
            if (locked)
                return mFragmentList.get(lockedIndex);
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            if (locked) return 1;
            return mFragmentList.size();
        }

        void insertNewFragment(Fragment fragment) {
            mFragmentList.add(fragment);
        }


    }

    public class LockableViewPager extends ViewPager {
        private boolean swipeable;
        private final List<Fragment> mFragmentList = new ArrayList<>();

        public LockableViewPager(Context context) {
            super(context);
        }

        public LockableViewPager(Context context, AttributeSet attrs) {
            super(context, attrs);
            this.swipeable = true;
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            if (this.swipeable) {
                return super.onTouchEvent(event);
            }
            return false;
        }

        @Override

        public boolean onInterceptTouchEvent(MotionEvent event) {
            if (this.swipeable) {
                return super.onInterceptTouchEvent(event);
            }
            return false;
        }

        public void setSwipeable(boolean swipeable) {
            this.swipeable = swipeable;
        }
        public void insertNewFragment(Fragment fragment) {
            mFragmentList.add(fragment);
        }

    }
    private void clearBackStack() {
        FragmentManager fm = getSupportFragmentManager();
        for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();

        }



    }
    private void setImg(String url, ImageView image)
    {

        if(url.equals(""))
        {
            image.setImageResource(R.drawable.ic_user);
        }
        else {
            try {

                Picasso.with(Home.this)
                        .load(url)
                        .placeholder(R.drawable.ic_user)
                        .into(image);
            } catch (Exception ignored) {

            }
        }
    }


}
