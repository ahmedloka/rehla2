package com.NativeTech.rehla.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.NativeTech.rehla.adapters.DayAdapter;
import com.NativeTech.rehla.adapters.MonthAdapter;
import com.NativeTech.rehla.adapters.YearAdapter;
import com.NativeTech.rehla.application.AppManager;
import com.NativeTech.rehla.calendarmanager.CalendarManager;
import com.NativeTech.rehla.model.DataManager;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import com.NativeTech.rehla.model.data.dto.Models.LoginModel.LoginResponseModel;
import com.NativeTech.rehla.model.data.dto.Models.MetaDataModels.Cities;
import com.NativeTech.rehla.model.data.dto.Models.MetaDataModels.Countries;
import com.NativeTech.rehla.model.data.dto.Models.MetaDataModels.MetaDataResponseModel;
import com.NativeTech.rehla.model.data.dto.Models.Register.RegisterRequestModel;
import com.NativeTech.rehla.Network.NetworkUtil;
import com.NativeTech.rehla.R;
import com.NativeTech.rehla.Utills.Constant;
import com.NativeTech.rehla.Utills.Validation;
import com.NativeTech.rehla.adapters.filterAreaAdapterForCountryCode;
import com.NativeTech.rehla.adapters.filterAreaModelRecycler;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static com.NativeTech.rehla.Utills.Constant.buildDialog;
import static droidninja.filepicker.FilePickerConst.REQUEST_CODE_PHOTO;

public class Signup extends AppCompatActivity {

    private Button signup;

    private TextView spinner_countries;
    private TextView city;
    //ImageView countryLogo;
    private RadioButton male;
    private RadioButton female;

    private RadioButton Saudi;
    private RadioButton Foreigner;


    private int mYear;
    private int mMonth;
    private int mDay;
    private String BirthOfDate = "-1";
    public Calendar mcurrentDate;
    private RecyclerView DialogRecyclerView;
    private filterAreaAdapterForCountryCode filterAreaAdapter1;
    private final ArrayList<filterAreaModelRecycler> DialogList = new ArrayList<>();


    private String Language;
    private CompositeSubscription mSubscriptions;
    private SharedPreferences mSharedPreferences;
    private KProgressHUD hud;
    private String token = "";
    private String phoneCode = "-1";
    private String cityId = "-1";
    private boolean flag = false;

    private boolean genderSelected = true;
    private boolean SaudiSelected = true;
    private List<Countries> countries = new ArrayList<>();
    private List<Cities> cities = new ArrayList<>();


    private CircleImageView img;
    private AppCompatImageView imgPicker;
    private String image_url = "";
    String imageType;
    private final String[] permissions = new String[]{

            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
    };

    String img_url = "";

    private AppCompatEditText name;
    private AppCompatEditText email;
    private AppCompatEditText password;
    private AppCompatEditText identity_num;
    private AppCompatEditText phone;
    private CheckBox checkBox_max2;


    private Spinner mYearSpinner;
    private Spinner mMonthSpinner;
    private Spinner day_spinner;
    private ArrayList<String> yearDataList;
    private ArrayList<String> monthDataList;
    private final ArrayList<String>  dayDataList= new ArrayList<>();
    private ArrayList<HashMap<String, String>> mCalendarDatesList;
    private final String[] month = CalendarManager.month;
    private YearAdapter yearAdapter;
    private MonthAdapter monthAdapter;
    private DayAdapter dayAdapter;


    private Resources res;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
         toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
         getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
         getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        signup = findViewById(R.id.signup);
        spinner_countries = findViewById(R.id.spinner_countries);
        city = findViewById(R.id.city);


        //countryLogo                   =  findViewById(R.id.countryLogo);
        Typeface font = Typeface.createFromAsset(getAssets(), "Cairo-Regular.ttf");


        mYearSpinner = findViewById(R.id.year_spinner);
        mMonthSpinner = findViewById(R.id.month_spinner);
        day_spinner = findViewById(R.id.day_spinner);
        //mCalendarView = findViewById(R.id.calendar_history);




        yearDataList = new ArrayList<>();
        monthDataList = new ArrayList<>();
        mCalendarDatesList = new ArrayList<>();

        mYear = AppManager.getCurrentYear();
        mMonth = AppManager.getCurrentMonth();
        mDay = AppManager.getCurrentDayOfMonth();



        int yearPos = 0;
        int c = 0;
        for (int i = 1900; i < 2019; i++) {
            yearDataList.add("" + i);

            if(i==mYear)
                yearPos = c;

            c++;
        }

        Collections.addAll(monthDataList, month);

        res = getResources();

        yearAdapter = new YearAdapter(this, R.layout.calendar_data, yearDataList, res);
        mYearSpinner.setAdapter(yearAdapter);

        monthAdapter = new MonthAdapter(this, R.layout.calendar_data, monthDataList, res);
        mMonthSpinner.setAdapter(monthAdapter);

        dayAdapter = new DayAdapter(this, R.layout.calendar_data, dayDataList, res);
        day_spinner.setAdapter(dayAdapter);

        /* mCalendarAdapter = new CalendarAdapter(this, mCalendarDatesList);
        mCalendarView.setAdapter(mCalendarAdapter);*/

        AppManager.setSelectedYear(mYear);
        AppManager.setSelectedMonth(mMonth + 1);
        prepareCalendar(mYear, mMonth);

        mYearSpinner.setSelection(yearPos);
        mMonthSpinner.setSelection(AppManager.getCurrentMonth());


        BirthOfDate=mYear+"-"+(mMonth+1)+"-"+(mDay+1);

        //Toast.makeText(Signup.this, BirthOfDate, Toast.LENGTH_SHORT).show();

        mYearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mYear = Integer.parseInt(yearDataList.get(position));
                AppManager.setSelectedYear(mYear);
                prepareCalendar(mYear, mMonth);
                BirthOfDate=mYear+"-"+(mMonth+1)+"-"+(mDay+1);
                //Toast.makeText(Signup.this, BirthOfDate, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mMonthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mMonth = position;
                AppManager.setSelectedMonth(mMonth+ 1);
                prepareCalendar(mYear, mMonth);
                BirthOfDate=mYear+"-"+(mMonth+1)+"-"+(mDay+1);

                //Toast.makeText(Signup.this, BirthOfDate, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        day_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mDay = position;
                BirthOfDate=mYear+"-"+(mMonth+1)+"-"+(mDay+1);
                //Toast.makeText(Signup.this, BirthOfDate, Toast.LENGTH_SHORT).show();

                //AppManager.setSelectedHoliday(mDay);
                //prepareCalendar(mYear, mMonth);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        RadioGroup radioGroup = findViewById(R.id.gender);
        RadioGroup nation = findViewById(R.id.nation);
        male = findViewById(R.id.male);
        female = findViewById(R.id.female);
        Saudi = findViewById(R.id.Saudi);
        Foreigner = findViewById(R.id.Foreigner);
        img = findViewById(R.id.img);
        imgPicker = findViewById(R.id.imgpicker);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        identity_num = findViewById(R.id.identity_num);
        phone = findViewById(R.id.phone);
        checkBox_max2 = findViewById(R.id.checkBox_max2);


        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //BirthOfDate= getDateWithUTCZone(dateFormat.format(c.getTime()));
        //Toast.makeText(this,startingDate , Toast.LENGTH_SHORT).show();


        Saudi.setTypeface(font);
        Foreigner.setTypeface(font);
        male.setTypeface(font);
        female.setTypeface(font);
        checkBox_max2.setTypeface(font);



        checkBox_max2.setOnClickListener(v -> {
            if (!flag) {
                flag = true;
                Intent intent = new Intent(Signup.this, TermsAndCondition.class);
                //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                //finish();
            }

        });

        img.setOnClickListener(v -> change_img());

        /*radioGroup.setOnCheckedChangeListener((view, checkedId) -> {
            if (checkedId == R.id.male) {
                getScore(1);
            } else {
                getScore(0);
            }
        });*/

        mSubscriptions = new CompositeSubscription();
        mSharedPreferences = getSharedPreferences("tokenDetail", MODE_PRIVATE);
        Language = mSharedPreferences.getString(Constant.language, Locale.getDefault().getLanguage());
        token = DataManager.getInstance().getCashedAccessToken().getAccess_token();
        hud = KProgressHUD.create(Signup.this)
                .setStyle(KProgressHUD.Style.PIE_DETERMINATE)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .setCancellable(false)
                .setMaxProgress(100);


        if (Validation.isConnected(Signup.this)) {
            hud.show();
            mSubscriptions.add(NetworkUtil.getRetrofitByToken(token)
                    .metaData()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResponse, this::handleError));
        } else {
            buildDialog(Signup.this).show().setCanceledOnTouchOutside(false);
        }

        spinner_countries.setOnClickListener(v -> {

            AlertDialog.Builder builder1;
            final AlertDialog dialog1;
            builder1 = new AlertDialog.Builder(Signup.this);
            View mview = getLayoutInflater().inflate(R.layout.area_dialog, null);
            TextView header = mview.findViewById(R.id.DialogHeader);
            TextView all = mview.findViewById(R.id.All);
            all.setVisibility(View.GONE);
            if (Language.equals("en")) {

                header.setText("Select Country Code");

            } else {

                header.setText("اختر كود الدولة");


            }
            DialogRecyclerView = mview.findViewById(R.id.Recycler_Dialog_cities);
            filterAreaAdapter1 = new filterAreaAdapterForCountryCode(DialogList, getApplicationContext());
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), OrientationHelper.VERTICAL, false);

            DialogRecyclerView.setLayoutManager(linearLayoutManager);
            DialogRecyclerView.setItemAnimator(new DefaultItemAnimator());
            initionilizationOFFilterCountryCodeRecycler();

            DialogRecyclerView.setAdapter(filterAreaAdapter1);
            builder1.setView(mview);
            dialog1 = builder1.create();
            Window window = dialog1.getWindow();
            window.setGravity(Gravity.CENTER);
            dialog1.show();

            filterAreaAdapter1.setOnItemClickListener(v1 -> {
                int position = DialogRecyclerView.getChildAdapterPosition(v1);
                String position1 = (String.valueOf(position));
//                        AreaID = (Integer.parseInt(DialogList.get(position).ID));
//                        SelectedAreaName = DialogList.get(position).shopName;
//                        select_Area_All.setText(SelectedAreaName);
                String countrylogo = DialogList.get(position).flag;
                //setImg(countrylogo,countryLogo);
                phoneCode = String.valueOf(DialogList.get(position).id);
                if (phoneCode.equals("0"))
                {
                    spinner_countries.setText("+2");
                }
                else
                {
                    spinner_countries.setText("+966");
                }
                dialog1.dismiss();

            });

        });
        city.setOnClickListener(v -> {

            AlertDialog.Builder builder1;
            final AlertDialog dialog1;
            builder1 = new AlertDialog.Builder(Signup.this);
            View mview = getLayoutInflater().inflate(R.layout.area_dialog, null);
            TextView header = mview.findViewById(R.id.DialogHeader);
            TextView all = mview.findViewById(R.id.All);
            all.setVisibility(View.GONE);
            if (Language.equals("en")) {

                header.setText("Select City");

            } else {

                header.setText("اختر المدينة");


            }
            DialogRecyclerView = mview.findViewById(R.id.Recycler_Dialog_cities);
            filterAreaAdapter1 = new filterAreaAdapterForCountryCode(DialogList, getApplicationContext());
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), OrientationHelper.VERTICAL, false);

            DialogRecyclerView.setLayoutManager(linearLayoutManager);
            DialogRecyclerView.setItemAnimator(new DefaultItemAnimator());
            initionilizationOFFilterRecyclerCity();

            DialogRecyclerView.setAdapter(filterAreaAdapter1);
            builder1.setView(mview);
            dialog1 = builder1.create();
            Window window = dialog1.getWindow();
            window.setGravity(Gravity.CENTER);
            dialog1.show();

            filterAreaAdapter1.setOnItemClickListener(v1 -> {
                int position = DialogRecyclerView.getChildAdapterPosition(v1);
                String position1 = (String.valueOf(position));
//                        AreaID = (Integer.parseInt(DialogList.get(position).ID));
//                        SelectedAreaName = DialogList.get(position).shopName;
//                        select_Area_All.setText(SelectedAreaName);
                String countrylogo = DialogList.get(position).flag;
                //setImg(countrylogo,countryLogo);
                city.setText(String.valueOf(DialogList.get(position).CityName));
                cityId = String.valueOf(DialogList.get(position).id);
                dialog1.dismiss();

            });

        });


        signup.setOnClickListener(v -> {
            genderSelected = male.isChecked();
            SaudiSelected =false/* Saudi.isChecked()*/;
            if (Validation.isConnected(Signup.this)) {
                if (Language.equals("en")) {
                    if (!Validation.validateFields(name.getText().toString())) {
                        name.setError("please Enter Name");
                    } else if (!Validation.validateEmail(email.getText().toString())) {
                        email.setError("please Enter Email");
                    } else if (!Validation.validateFields(password.getText().toString())) {
                        password.setError("please Enter password");
                    }  else if (!Validation.validatePassword(password.getText().toString())) {
                        password.setError("password Length must br greater than 6");
                    }
                   /* else if (!Validation.validateFields(identity_num.getText().toString())) {
                        identity_num.setError("please Enter identity Number");
                    }*/
                    else if (!Validation.validateFields(phone.getText().toString())) {
                        phone.setError("please Enter Phone");
                    } else if (phoneCode.equals("-1")) {
                        Toast.makeText(this, "please select Country Code", Toast.LENGTH_SHORT).show();
                    } /*else if (BirthOfDate.equals("-1")) {
                        Toast.makeText(this, "please select Birth Of Date", Toast.LENGTH_SHORT).show();
                    }*/
                    /*else if (cityId.equals("-1")) {
                        Toast.makeText(this, "please select City", Toast.LENGTH_SHORT).show();
                    } */else if (!checkBox_max2.isChecked()) {
                        Toast.makeText(this, "please accept terms and conditions", Toast.LENGTH_SHORT).show();
                    } else {
                        register();
                    }
                } else {
                    if (!Validation.validateFields(name.getText().toString())) {
                        name.setError("من فضلك ادخل اسمك");
                    } else if (!Validation.validateEmail(email.getText().toString())) {
                        email.setError("من فضلك ادخل الايميل");
                    } else if (!Validation.validateFields(password.getText().toString())) {
                        password.setError("من فضلك ادخل كلمة المرور");
                    }
                    else if (!Validation.validatePassword(password.getText().toString())) {
                        password.setError("كلمة المرور يجب ان تكون اكبر من 6");
                    }
                   /* else if (!Validation.validateFields(identity_num.getText().toString())) {
                        identity_num.setError("من فضلك ادخل رقم الهوية");
                    }*/else if (!Validation.validateFields(phone.getText().toString())) {
                        phone.setError("من فضلك ادخل رقم الهاتف");
                    } else if (phoneCode.equals("-1")) {
                        Toast.makeText(this, "من فضلك اختر كود الدولة", Toast.LENGTH_SHORT).show();
                    } /*else if (BirthOfDate.equals("-1")) {
                        Toast.makeText(this, "من فضلك اختر تاريخ ميلادك", Toast.LENGTH_SHORT).show();
                    }*/ /*else if (cityId.equals("-1")) {
                        Toast.makeText(this, "من فضلك اختر مدينتك", Toast.LENGTH_SHORT).show();
                    } */else if (!checkBox_max2.isChecked()) {
                        Toast.makeText(this, "من فضلك اقبل الشروط والاخكام", Toast.LENGTH_SHORT).show();
                    } else {
                        register();
                    }

                }
            } else {
                buildDialog(Signup.this).show().setCanceledOnTouchOutside(false);
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
                        String wasl_27 = jsonObject.getJSONObject("errors").getString("message");

                        //Toast.makeText(this, wasl_27.replace("\\",""), Toast.LENGTH_SHORT).show();
                        JSONObject obj_27 = new JSONObject(wasl_27.replace("\\",""));
                        String waslCode_27 =obj_27.getString("resultCode");

                        switch (waslCode_27) {
                            case "503":
                                message = getString(R.string.wasl_error_register_503);
                                break;
                            case "420":
                                message = getString(R.string.wasl_error_register_420);
                                break;
                            case "421":
                                message = getString(R.string.wasl_error_register_420);
                                break;
                                default:
                                    message = getString(R.string.default_message);
                        }

                       // message = waslCode_27;
                        break;
                    case "28":
                        String wasl_28 = jsonObject.getJSONObject("errors").getString("message");
                        JSONObject obj_28 = new JSONObject(wasl_28.replace("\\",""));
                        String waslCode_28 =obj_28.getString("resultCode");
                        switch (waslCode_28) {
                            case "503":
                                message = getString(R.string.wasl_error_register_503);
                                break;
                            case "420":
                                message = getString(R.string.wasl_error_register_420);
                                break;
                            case "421":
                                message = getString(R.string.wasl_error_register_421);
                                break;
                            default:
                                message = getString(R.string.default_message);
                        }
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

    private void handleResponse(MetaDataResponseModel metaDataResponseModel) {

        if (metaDataResponseModel.getModel() != null) {
            if (metaDataResponseModel.getModel().getCountries() != null) {
                countries = metaDataResponseModel.getModel().getCountries();
                // Toast.makeText(this, metaDataResponseModel.getModel().getCountries().size()+"", Toast.LENGTH_SHORT).show();
            }
            if (metaDataResponseModel.getModel().getCities() != null) {
                cities = metaDataResponseModel.getModel().getCities();
                // Toast.makeText(this, metaDataResponseModel.getModel().getCountries().size()+"", Toast.LENGTH_SHORT).show();
            }
            //Toast.makeText(this, "model not null", Toast.LENGTH_SHORT).show();

        }
        //Toast.makeText(this, "model null", Toast.LENGTH_SHORT).show();
        hud.dismiss();
    }

    public void setImg(String url, ImageView image) {

        if (url.equals("")) {
        } else {
            try {

                Picasso.with(getApplicationContext())
                        .load(url)
                        .into(image);
            } catch (Exception ignored) {

            }
        }
    }

    private void initionilizationOFFilterCountryCodeRecycler() {
       /* DialogList.clear();
        try {
            for (int k = 0; k < countries.size(); k++) {
                String name = "";
                if (Language.equals("en")) {
                    name = countries.get(k).getName();
                } else {
                    name = countries.get(k).getNameLT();

                }
                DialogList.add(new filterAreaModelRecycler(
                        name
                        , ""
                        , countries.get(k).getKMPrice()
                        , Integer.parseInt(countries.get(k).getId())));
            }
            filterAreaAdapter1.update(DialogList);*/
        DialogList.clear();

        try {

            if (Language.equals("ar"))
            {
                DialogList.add(new filterAreaModelRecycler(
                        "السعودية"
                        ,""
                        ,""
                        ,1));

                DialogList.add(new filterAreaModelRecycler(
                        "مصر"
                        ,""
                        ,""
                        ,0));


            }
            else
            {

                DialogList.add(new filterAreaModelRecycler(
                        "saudi"
                        ,""
                        ,""
                        ,1));



                DialogList.add(new filterAreaModelRecycler(
                        "cairo"
                        ,""
                        ,""
                        ,0));
            }

            filterAreaAdapter1.update(DialogList);
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    private void initionilizationOFFilterRecyclerCity() {
        DialogList.clear();
        try {
            for (int k = 0; k < cities.size(); k++) {
                String name = "";
                if (Language.equals("en")) {
                    name = cities.get(k).getName();
                } else {
                    name = cities.get(k).getNameLT();
                }
                DialogList.add(new filterAreaModelRecycler(
                        name
                        , ""
                        , cities.get(k).getCountry()
                        , Integer.parseInt(cities.get(k).getId())));
            }
            filterAreaAdapter1.update(DialogList);
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    private void change_img() {
        try {
            if (checkPermissions()) {
            } else {
                //final String[] PERMISSIONS_STORAGE = {Manifest.permission.CAMERA};
                //Asking request Permissions
                //ActivityCompat.requestPermissions(Settings.this, PERMISSIONS_STORAGE, 10);
                //Toast.makeText(Settings.this, "Camera Permission error", Toast.LENGTH_SHORT).show();
                checkPermissions();
            }
        } catch (Exception e) {
            //Toast.makeText(Settings.this, "Camera Permission error", Toast.LENGTH_SHORT).show();
            //e.printStackTrace();
            checkPermissions();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_PHOTO:

                if (resultCode == RESULT_OK && data != null) {


                    try {
                        Uri selectedImageURI = data.getData();
                        String path = getPath(selectedImageURI);

                        Bitmap bm = BitmapFactory.decodeFile(path);
                        img.setImageBitmap(bm);
                        //BitmapFactory.Options options = new BitmapFactory.Options();
                        //options.inJustDecodeBounds = true;
                        //options.inSampleSize = 3;
                        //bm.decodeFile(path);
                        //BitmapFactory.decodeResource(getResources(), R.mipmap.hqimage, options);
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bm.compress(Bitmap.CompressFormat.JPEG, 50, baos); //bm is the bitmap object
                        byte[] b = baos.toByteArray();
                        image_url = "" + Base64.encodeToString(b, Base64.DEFAULT);
                    } catch (Exception e) {
                        //e.printStackTrace();
                        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                break;

            case 0:

                Bitmap bitmap;

                if (data != null) {
                    bitmap = (Bitmap) data.getExtras().get("data");

                    // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
                    Uri tempUri = getImageUri(this, bitmap);
                     String path = getPath(tempUri);
                    Bitmap bm = BitmapFactory.decodeFile(path);
                    img.setImageBitmap(bm);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bm.compress(Bitmap.CompressFormat.JPEG, 50, baos); //bm is the bitmap object
                    byte[] b = baos.toByteArray();
                    image_url = "" + Base64.encodeToString(b, Base64.DEFAULT);

                }
        }
    }

    private Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        byte[] b = bytes.toByteArray();
        String encodedString = Base64.encodeToString(b, Base64.DEFAULT);
        image_url = "" + encodedString;
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    private String getPath(Uri contentUri) {
        String result = null;
        String[] projection = {MediaStore.Images.Media.DATA};

        try {
            Cursor cursor = getApplicationContext().getContentResolver().query(contentUri, projection, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(projection[0]);
            result = cursor.getString(columnIndex);
            cursor.close();
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return result;
    }

    private boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(this, p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[0]), 100);
            return false;
        } else {
            PackageManager pm = Signup.this.getPackageManager();
            int hasPerm = pm.checkPermission(Manifest.permission.CAMERA, Signup.this.getPackageName());
            if (hasPerm == PackageManager.PERMISSION_GRANTED) {
                android.support.v7.app.AlertDialog.Builder builder;
                android.support.v7.app.AlertDialog dialog;
                builder = new android.support.v7.app.AlertDialog.Builder(Signup.this);

                @SuppressLint("InflateParams")
                View mview = getLayoutInflater().inflate(R.layout.dialog_upload_image, null);
                builder.setView(mview);
                dialog = builder.create();
                Window window = dialog.getWindow();
                if (window != null) {
                    window.setGravity(Gravity.CENTER);
                }
                dialog.show();
                //Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                dialog.setCanceledOnTouchOutside(true);
                dialog.setCancelable(true);
                TextView take_photo = mview.findViewById(R.id.take_photo);
                TextView photo_album = mview.findViewById(R.id.photo_album);
                Button cancel = mview.findViewById(R.id.cancel);
                take_photo.setOnClickListener(view -> {
                    dialog.dismiss();
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, 0);
                });
                photo_album.setOnClickListener(view -> {
                    dialog.dismiss();
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto, REQUEST_CODE_PHOTO);
                });

                cancel.setOnClickListener(view -> dialog.dismiss());
            }
        }
        return true;
    }

    private void register() {
        if (Validation.isConnected(Signup.this)) {
            RegisterRequestModel registerRequestModel = new RegisterRequestModel();
            registerRequestModel.setCityId(Integer.parseInt("1"));
            registerRequestModel.setDateOfBirth("2019-01-01");
            registerRequestModel.setEmail(email.getText().toString());
            registerRequestModel.setGender(genderSelected);
            registerRequestModel.setSaudi(SaudiSelected);
            registerRequestModel.setName(name.getText().toString());
            registerRequestModel.setPassword(password.getText().toString());
            if (phoneCode.equals("0"))
            {
                registerRequestModel.setPhoneKey("+2");
            }
            else
            {
                registerRequestModel.setPhoneKey("+966");
            }
            registerRequestModel.setProfilePhoto(image_url);
            registerRequestModel.setPhoneNumber(phone.getText().toString());
            registerRequestModel.setIdentityNumber(""/*identity_num.getText().toString()*/);
            hud.show();
            mSubscriptions.add(NetworkUtil.getRetrofitByToken(token)
                    .register(registerRequestModel)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResponse, this::handleError));
        } else {
            buildDialog(Signup.this).show().setCanceledOnTouchOutside(false);
        }
    }

    private void handleResponse(LoginResponseModel loginResponseModel) {
        //Toast.makeText(this, "ok", Toast.LENGTH_SHORT).show();

        if (loginResponseModel.getModel() != null) {
            if (loginResponseModel.getModel().getToken() != null) {
                 DataManager.getInstance().saveAccessToken(loginResponseModel.getModel().getToken().get(0));
            }
            if (loginResponseModel.getModel().getName() != null) {
                SharedPreferences.Editor editor_UserName = mSharedPreferences.edit();
                editor_UserName.putString(Constant.Username, loginResponseModel.getModel().getName());
                editor_UserName.apply();
            }
            if (loginResponseModel.getModel().getPhoneNumber() != null) {
                SharedPreferences.Editor editor_mobile = mSharedPreferences.edit();
                editor_mobile.putString(Constant.mobile, String.valueOf(loginResponseModel.getModel().getPhoneNumber()));
                editor_mobile.apply();
            }

            if (loginResponseModel.getModel().getProfilePhoto() != null) {
                SharedPreferences.Editor editor_image = mSharedPreferences.edit();
                editor_image.putString(Constant.imageUri, Constant.BASE_URL_profile_img + loginResponseModel.getModel().getProfilePhoto());
                editor_image.apply();
            }
            if (loginResponseModel.getModel().getVCode() != null) {
                SharedPreferences.Editor editor_vCode = mSharedPreferences.edit();
                editor_vCode.putString(Constant.vCode, String.valueOf(loginResponseModel.getModel().getVCode()));
                editor_vCode.apply();
            }

            if (loginResponseModel.getModel().getPhoneKey() != null) {
                SharedPreferences.Editor editor_mobileKey = mSharedPreferences.edit();
                editor_mobileKey.putString(Constant.mobileKey, String.valueOf(loginResponseModel.getModel().getPhoneKey()));
                editor_mobileKey.apply();
            }

            if (loginResponseModel.getModel().getEmail() != null) {
                SharedPreferences.Editor editor_email = mSharedPreferences.edit();
                editor_email.putString(Constant.Useremail, String.valueOf(loginResponseModel.getModel().getEmail()));
                editor_email.apply();
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


           /* if (loginResponseModel.getModel().getVerified()) {
                Intent intent = new Intent(Signup.this, Home.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
            else
            {*/
                Intent intent = new Intent(Signup.this, VerificationCodeRegister.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            //}
        }
        hud.dismiss();
    }

    private void prepareCalendar(int sYear, int sMonth) {

        dayDataList.clear();
        mCalendarDatesList.clear();
        mCalendarDatesList.addAll(CalendarManager.prepareCalendar(sYear, sMonth));
        // mCalendarAdapter.notifyDataSetChanged();
        getDays();

    }
    private void getDays(){
        for (int i=0;i<mCalendarDatesList.size();i++)
        {
            HashMap<String, String> dateList = mCalendarDatesList.get(i);
            String result = "";
            if(dateList.containsKey(com.NativeTech.rehla.application.Constant.PRESENT_DAYS))
            {
                result = dateList.get(com.NativeTech.rehla.application.Constant.PRESENT_DAYS);
                dayDataList.add(String.valueOf(result));
            }

        }
        dayAdapter = new DayAdapter(this, R.layout.calendar_data, dayDataList, res);
        day_spinner.setAdapter(dayAdapter);

    }


}
