package com.NativeTech.rehla.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.NativeTech.rehla.model.DataManager;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import com.NativeTech.rehla.model.data.dto.Models.AddCar.AddCarRequestModel;
import com.NativeTech.rehla.model.data.dto.Models.AddCar.AddCarResponseModelFinal;
import com.NativeTech.rehla.model.data.dto.Models.AddCarPhotoModels.AddCarPhotoRequest;
import com.NativeTech.rehla.model.data.dto.Models.AddCarPhotoModels.AddCarResponse;
import com.NativeTech.rehla.model.data.dto.Models.MetaDataModels.CarColors;
import com.NativeTech.rehla.model.data.dto.Models.MetaDataModels.CarModels;
import com.NativeTech.rehla.model.data.dto.Models.MetaDataModels.CarBrands;
import com.NativeTech.rehla.model.data.dto.Models.MetaDataModels.CarTypes;
import com.NativeTech.rehla.model.data.dto.Models.MetaDataModels.MetaDataResponseModel;
import com.NativeTech.rehla.Network.NetworkUtil;
import com.NativeTech.rehla.R;
import com.NativeTech.rehla.Utills.Constant;
import com.NativeTech.rehla.Utills.Validation;
import com.NativeTech.rehla.adapters.filterAreaAdapterForCountryCode;
import com.NativeTech.rehla.adapters.filterAreaModelRecycler;

import retrofit2.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static com.NativeTech.rehla.Utills.Constant.buildDialog;
import static com.NativeTech.rehla.Utills.Constant.carStatus;
import static droidninja.filepicker.FilePickerConst.REQUEST_CODE_PHOTO;

public class UploadCarPhoto extends AppCompatActivity {

    private LinearLayoutCompat car_licence;
    private LinearLayoutCompat car_insurance;
    private LinearLayoutCompat car_paper;
    private TextView car_licence_txt;
    private TextView car_insurance_txt;
    private TextView car_paper_txt;
    private AppCompatImageView car_licence_img;
    private AppCompatImageView car_insurance_img;
    private AppCompatImageView car_paper_img;
    private Button upload;


    private EditText editText1;
    private EditText editText2;
    private EditText editText3;
    private EditText editText4;


    private TextView plate_num1;
    private TextView plate_num2;
    private TextView plate_num3;


    private AppCompatImageView img;
    private AppCompatImageView back_img;
    private AppCompatImageView car_photo;
    private AppCompatImageView back_img_car_photo;

    private String image_url = "";
    String imageType;
    private final String[] permissions = new String[]
            {
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA,
            };


    private int index = 1;

    private int finalIndex;

    private boolean flag = false;

    private String img1 = "";
    private String img2 = "";
    private String img3 = "";
    private String img4 = "";

    String img1Uri = "";
    String img2Uri = "";
    String img3Uri = "";
    String img4Uri = "";

    private String Language;
    private CompositeSubscription mSubscriptions;
    private SharedPreferences mSharedPreferences;
    private KProgressHUD hud;


    private TextView car_brand;
    private String car_brand_Id = "";
    private int car_brand_Position = -1;

    private TextView car_model;
    private String car_model_Id = "";

    private TextView car_type;
    private String car_type_Id = "";

    private TextView car_color;
    private String car_color_Id = "";

    private TextView production_year;
    private String production_year_Id = "2019";

    private TextView seat_number;
    private String seat_number_Id = "1";

    private RecyclerView DialogRecyclerView;
    private filterAreaAdapterForCountryCode filterAreaAdapter1;
    private final ArrayList<filterAreaModelRecycler> DialogList = new ArrayList<>();


    private List<CarBrands> carBrands = new ArrayList<>();
    private List<CarModels> carModels = new ArrayList<>();
    private List<CarTypes> carTypes = new ArrayList<>();
    private List<CarColors> carColor = new ArrayList<>();
    private final List<String> productionYear = new ArrayList<>();
    private final List<String> seatNumber = new ArrayList<>();

    private AppCompatEditText SequenceNumber;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_car_photo);


        android.support.v7.app.AlertDialog.Builder builder10;
        android.support.v7.app.AlertDialog dialog10;
        builder10 = new android.support.v7.app.AlertDialog.Builder(UploadCarPhoto.this);

        @SuppressLint("InflateParams")
        View mview10 = getLayoutInflater().inflate(R.layout.dialog_add_car_note, null);
        builder10.setView(mview10);
        dialog10 = builder10.create();
        Window window10 = dialog10.getWindow();
        if (window10 != null) {
            window10.setGravity(Gravity.CENTER);
        }
        dialog10.show();
        Objects.requireNonNull(dialog10.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog10.setCanceledOnTouchOutside(true);
        dialog10.setCancelable(true);

        Button ok = mview10.findViewById(R.id.ok);

        ok.setOnClickListener(view -> dialog10.dismiss());


        car_licence = findViewById(R.id.car_licence);
        car_insurance = findViewById(R.id.car_insurance);
        car_paper = findViewById(R.id.car_paper);
        car_licence_txt = findViewById(R.id.car_licence_txt);
        car_insurance_txt = findViewById(R.id.car_insurance_txt);
        car_paper_txt = findViewById(R.id.car_paper_txt);
        car_licence_img = findViewById(R.id.car_licence_img);
        car_insurance_img = findViewById(R.id.car_insurance_img);
        car_paper_img = findViewById(R.id.car_paper_img);
        upload = findViewById(R.id.upload);

        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.add_car);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        editText1 = findViewById(R.id.edit_txt1);
        editText2 = findViewById(R.id.edit_txt2);
        editText3 = findViewById(R.id.edit_txt3);
        editText4 = findViewById(R.id.edit_txt4);
        plate_num1 = findViewById(R.id.plate_num1);
        plate_num2 = findViewById(R.id.plate_num2);
        plate_num3 = findViewById(R.id.plate_num3);


        editText1.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (editText1.length() == 1) {

                    editText1.clearFocus();
                }

            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {


            }

            public void afterTextChanged(Editable s) {

            }
        });
        editText2.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (editText2.length() == 1) {

                    editText2.clearFocus();
                    editText1.requestFocus();
                    editText1.setCursorVisible(true);

                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {


            }

            public void afterTextChanged(Editable s) {

            }
        });
        editText3.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (editText3.length() == 1) {

                    editText3.clearFocus();
                    editText2.requestFocus();
                    editText2.setCursorVisible(true);

                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {


            }

            public void afterTextChanged(Editable s) {

            }
        });
        editText4.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (editText4.length() == 1) {

                    editText4.clearFocus();
                    editText3.requestFocus();
                    editText3.setCursorVisible(true);

                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {


            }

            public void afterTextChanged(Editable s) {

            }
        });


        plate_num1.setOnClickListener(v -> {
            AlertDialog.Builder builder1;
            final AlertDialog dialog1;
            builder1 = new AlertDialog.Builder(UploadCarPhoto.this);
            View mview = getLayoutInflater().inflate(R.layout.area_dialog, null);
            TextView header = mview.findViewById(R.id.DialogHeader);
            TextView all = mview.findViewById(R.id.All);
            all.setVisibility(View.GONE);

            header.setText(getString(R.string.car_plate));

            DialogRecyclerView = mview.findViewById(R.id.Recycler_Dialog_cities);
            filterAreaAdapter1 = new filterAreaAdapterForCountryCode(DialogList, getApplicationContext());
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), OrientationHelper.VERTICAL, false);

            DialogRecyclerView.setLayoutManager(linearLayoutManager);
            DialogRecyclerView.setItemAnimator(new DefaultItemAnimator());
            initionilizationOFFilterCarPlateNumber();

            DialogRecyclerView.setAdapter(filterAreaAdapter1);
            builder1.setView(mview);
            dialog1 = builder1.create();
            Window window = dialog1.getWindow();
            if (window != null) {
                window.setGravity(Gravity.CENTER);
            }
            dialog1.show();

            filterAreaAdapter1.setOnItemClickListener(v1 -> {
                int position = DialogRecyclerView.getChildAdapterPosition(v1);
                //String position1 = (String.valueOf(position));
//                        AreaID = (Integer.parseInt(DialogList.get(position).ID));
//                        SelectedAreaName = DialogList.get(position).shopName;
//                        select_Area_All.setText(SelectedAreaName);
                //String countrylogo = DialogList.get(position).flag;
                //setImg(countrylogo,countryLogo);
                //car_brand_Id=String.valueOf(DialogList.get(position).id);
                plate_num1.setText(String.valueOf(DialogList.get(position).CityName));
                //Toast.makeText(UploadCarPhoto.this, plate_num1.getText().toString()+"", Toast.LENGTH_SHORT).show();
                //phoneCode=DialogList.get(position).CityName;
                dialog1.dismiss();

            });
        });
        plate_num2.setOnClickListener(v -> {
            AlertDialog.Builder builder1;
            final AlertDialog dialog1;
            builder1 = new AlertDialog.Builder(UploadCarPhoto.this);
            View mview = getLayoutInflater().inflate(R.layout.area_dialog, null);
            TextView header = mview.findViewById(R.id.DialogHeader);
            TextView all = mview.findViewById(R.id.All);
            all.setVisibility(View.GONE);

            header.setText(getString(R.string.car_plate));

            DialogRecyclerView = mview.findViewById(R.id.Recycler_Dialog_cities);
            filterAreaAdapter1 = new filterAreaAdapterForCountryCode(DialogList, getApplicationContext());
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), OrientationHelper.VERTICAL, false);

            DialogRecyclerView.setLayoutManager(linearLayoutManager);
            DialogRecyclerView.setItemAnimator(new DefaultItemAnimator());
            initionilizationOFFilterCarPlateNumber();

            DialogRecyclerView.setAdapter(filterAreaAdapter1);
            builder1.setView(mview);
            dialog1 = builder1.create();
            Window window = dialog1.getWindow();
            if (window != null) {
                window.setGravity(Gravity.CENTER);
            }
            dialog1.show();

            filterAreaAdapter1.setOnItemClickListener(v1 -> {
                int position = DialogRecyclerView.getChildAdapterPosition(v1);
                //String position1 = (String.valueOf(position));
//                        AreaID = (Integer.parseInt(DialogList.get(position).ID));
//                        SelectedAreaName = DialogList.get(position).shopName;
//                        select_Area_All.setText(SelectedAreaName);
                //String countrylogo = DialogList.get(position).flag;
                //setImg(countrylogo,countryLogo);
                //car_brand_Id=String.valueOf(DialogList.get(position).id);
                plate_num2.setText(String.valueOf(DialogList.get(position).CityName));
                //Toast.makeText(UploadCarPhoto.this, plate_num2.getText().toString()+"", Toast.LENGTH_SHORT).show();
                //phoneCode=DialogList.get(position).CityName;
                dialog1.dismiss();

            });
        });
        plate_num3.setOnClickListener(v -> {
            AlertDialog.Builder builder1;
            final AlertDialog dialog1;
            builder1 = new AlertDialog.Builder(UploadCarPhoto.this);
            View mview = getLayoutInflater().inflate(R.layout.area_dialog, null);
            TextView header = mview.findViewById(R.id.DialogHeader);
            TextView all = mview.findViewById(R.id.All);
            all.setVisibility(View.GONE);

            header.setText(getString(R.string.car_plate));

            DialogRecyclerView = mview.findViewById(R.id.Recycler_Dialog_cities);
            filterAreaAdapter1 = new filterAreaAdapterForCountryCode(DialogList, getApplicationContext());
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), OrientationHelper.VERTICAL, false);

            DialogRecyclerView.setLayoutManager(linearLayoutManager);
            DialogRecyclerView.setItemAnimator(new DefaultItemAnimator());
            initionilizationOFFilterCarPlateNumber();

            DialogRecyclerView.setAdapter(filterAreaAdapter1);
            builder1.setView(mview);
            dialog1 = builder1.create();
            Window window = dialog1.getWindow();
            if (window != null) {
                window.setGravity(Gravity.CENTER);
            }
            dialog1.show();

            filterAreaAdapter1.setOnItemClickListener(v1 -> {
                int position = DialogRecyclerView.getChildAdapterPosition(v1);
                //String position1 = (String.valueOf(position));
//                        AreaID = (Integer.parseInt(DialogList.get(position).ID));
//                        SelectedAreaName = DialogList.get(position).shopName;
//                        select_Area_All.setText(SelectedAreaName);
                //String countrylogo = DialogList.get(position).flag;
                //setImg(countrylogo,countryLogo);
                //car_brand_Id=String.valueOf(DialogList.get(position).id);
                plate_num3.setText(String.valueOf(DialogList.get(position).CityName));
                // Toast.makeText(UploadCarPhoto.this, plate_num3.getText().toString()+"", Toast.LENGTH_SHORT).show();
                //phoneCode=DialogList.get(position).CityName;
                dialog1.dismiss();

            });
        });


        img = findViewById(R.id.img);
        back_img = findViewById(R.id.back_img);
        car_photo = findViewById(R.id.car_photo);
        back_img_car_photo = findViewById(R.id.back_img_car_photo);

        car_brand = findViewById(R.id.car_brand);
        car_model = findViewById(R.id.car_model);
        car_type = findViewById(R.id.car_type);
        car_color = findViewById(R.id.car_color);
        production_year = findViewById(R.id.production_year);
        seat_number = findViewById(R.id.seat_number);
        SequenceNumber = findViewById(R.id.SequenceNumber);


        for (int i = 2000; i < 2020; i++) {
            productionYear.add(String.valueOf(i));
        }


        for (int i = 1; i < 9; i++) {
            seatNumber.add(String.valueOf(i));
        }
        car_brand.setOnClickListener(v -> {
            AlertDialog.Builder builder1;
            final AlertDialog dialog1;
            builder1 = new AlertDialog.Builder(UploadCarPhoto.this);
            View mview = getLayoutInflater().inflate(R.layout.area_dialog, null);
            TextView header = mview.findViewById(R.id.DialogHeader);
            TextView all = mview.findViewById(R.id.All);
            all.setVisibility(View.GONE);

            header.setText(getString(R.string.select_car_brand));

            DialogRecyclerView = mview.findViewById(R.id.Recycler_Dialog_cities);
            filterAreaAdapter1 = new filterAreaAdapterForCountryCode(DialogList, getApplicationContext());
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), OrientationHelper.VERTICAL, false);

            DialogRecyclerView.setLayoutManager(linearLayoutManager);
            DialogRecyclerView.setItemAnimator(new DefaultItemAnimator());
            initionilizationOFFilterCarBrand();

            DialogRecyclerView.setAdapter(filterAreaAdapter1);
            builder1.setView(mview);
            dialog1 = builder1.create();
            Window window = dialog1.getWindow();
            window.setGravity(Gravity.CENTER);
            dialog1.show();

            filterAreaAdapter1.setOnItemClickListener(v1 -> {
                car_brand_Position = DialogRecyclerView.getChildAdapterPosition(v1);
                //String position1 = (String.valueOf(position));
//                        AreaID = (Integer.parseInt(DialogList.get(position).ID));
//                        SelectedAreaName = DialogList.get(position).shopName;
//                        select_Area_All.setText(SelectedAreaName);
                //String countrylogo = DialogList.get(position).flag;
                //setImg(countrylogo,countryLogo);

                car_brand_Id = String.valueOf(DialogList.get(car_brand_Position).id);
                car_brand.setText(String.valueOf(DialogList.get(car_brand_Position).CityName));
                car_model.setText("");
                car_model.setHint(getString(R.string.car_model));
                car_model_Id = "";

                //Toast.makeText(UploadCarPhoto.this, car_brand_Position+"", Toast.LENGTH_SHORT).show();
                //phoneCode=DialogList.get(position).CityName;
                dialog1.dismiss();

            });
        });
        car_model.setOnClickListener(v -> {
            if (car_brand_Id.equals("")) {
                if (Language.equals("ar")) {
                    Toast.makeText(UploadCarPhoto.this, "من فضلك اختر ماركة السيارة اولا !", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UploadCarPhoto.this, "please Select Car Brand first !", Toast.LENGTH_SHORT).show();
                }
            } else {
                AlertDialog.Builder builder1;
                final AlertDialog dialog1;
                builder1 = new AlertDialog.Builder(UploadCarPhoto.this);
                View mview = getLayoutInflater().inflate(R.layout.area_dialog, null);
                TextView header = mview.findViewById(R.id.DialogHeader);
                TextView all = mview.findViewById(R.id.All);
                all.setVisibility(View.GONE);

                header.setText(getString(R.string.select_car_model));

                DialogRecyclerView = mview.findViewById(R.id.Recycler_Dialog_cities);
                filterAreaAdapter1 = new filterAreaAdapterForCountryCode(DialogList, getApplicationContext());
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), OrientationHelper.VERTICAL, false);

                DialogRecyclerView.setLayoutManager(linearLayoutManager);
                DialogRecyclerView.setItemAnimator(new DefaultItemAnimator());
                initionilizationOFFilterCarModel();

                DialogRecyclerView.setAdapter(filterAreaAdapter1);
                builder1.setView(mview);
                dialog1 = builder1.create();
                Window window = dialog1.getWindow();
                window.setGravity(Gravity.CENTER);
                dialog1.show();

                filterAreaAdapter1.setOnItemClickListener(v1 -> {
                    int position = DialogRecyclerView.getChildAdapterPosition(v1);
                    //String position1 = (String.valueOf(position));
//                        AreaID = (Integer.parseInt(DialogList.get(position).ID));
//                        SelectedAreaName = DialogList.get(position).shopName;
//                        select_Area_All.setText(SelectedAreaName);
                    //String countrylogo = DialogList.get(position).flag;
                    //setImg(countrylogo,countryLogo);
                    car_model_Id = String.valueOf(DialogList.get(position).id);
                    car_model.setText(String.valueOf(DialogList.get(position).CityName));
                    //phoneCode=DialogList.get(position).CityName;
                    dialog1.dismiss();

                });

            }

        });
        car_type.setOnClickListener(v -> {
            AlertDialog.Builder builder1;
            final AlertDialog dialog1;
            builder1 = new AlertDialog.Builder(UploadCarPhoto.this);
            View mview = getLayoutInflater().inflate(R.layout.area_dialog, null);
            TextView header = mview.findViewById(R.id.DialogHeader);
            TextView all = mview.findViewById(R.id.All);
            all.setVisibility(View.GONE);

            header.setText(getString(R.string.select_car_type));

            DialogRecyclerView = mview.findViewById(R.id.Recycler_Dialog_cities);
            filterAreaAdapter1 = new filterAreaAdapterForCountryCode(DialogList, getApplicationContext());
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), OrientationHelper.VERTICAL, false);

            DialogRecyclerView.setLayoutManager(linearLayoutManager);
            DialogRecyclerView.setItemAnimator(new DefaultItemAnimator());
            initionilizationOFFilterCarType();

            DialogRecyclerView.setAdapter(filterAreaAdapter1);
            builder1.setView(mview);
            dialog1 = builder1.create();
            Window window = dialog1.getWindow();
            window.setGravity(Gravity.CENTER);
            dialog1.show();

            filterAreaAdapter1.setOnItemClickListener(v1 -> {
                int position = DialogRecyclerView.getChildAdapterPosition(v1);
                //String position1 = (String.valueOf(position));
//                        AreaID = (Integer.parseInt(DialogList.get(position).ID));
//                        SelectedAreaName = DialogList.get(position).shopName;
//                        select_Area_All.setText(SelectedAreaName);
                //String countrylogo = DialogList.get(position).flag;
                //setImg(countrylogo,countryLogo);

                car_type_Id = String.valueOf(DialogList.get(position).id);
                car_type.setText(String.valueOf(DialogList.get(position).CityName));

                //phoneCode=DialogList.get(position).CityName;
                dialog1.dismiss();

            });
        });
        car_color.setOnClickListener(v -> {
            AlertDialog.Builder builder1;
            final AlertDialog dialog1;
            builder1 = new AlertDialog.Builder(UploadCarPhoto.this);
            View mview = getLayoutInflater().inflate(R.layout.area_dialog, null);
            TextView header = mview.findViewById(R.id.DialogHeader);
            TextView all = mview.findViewById(R.id.All);
            all.setVisibility(View.GONE);

            header.setText(getString(R.string.select_car_color));

            DialogRecyclerView = mview.findViewById(R.id.Recycler_Dialog_cities);
            filterAreaAdapter1 = new filterAreaAdapterForCountryCode(DialogList, getApplicationContext());
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), OrientationHelper.VERTICAL, false);

            DialogRecyclerView.setLayoutManager(linearLayoutManager);
            DialogRecyclerView.setItemAnimator(new DefaultItemAnimator());
            initionilizationOFFilterCarColor();

            DialogRecyclerView.setAdapter(filterAreaAdapter1);
            builder1.setView(mview);
            dialog1 = builder1.create();
            Window window = dialog1.getWindow();
            window.setGravity(Gravity.CENTER);
            dialog1.show();

            filterAreaAdapter1.setOnItemClickListener(v1 -> {
                int position = DialogRecyclerView.getChildAdapterPosition(v1);
                //String position1 = (String.valueOf(position));
//                        AreaID = (Integer.parseInt(DialogList.get(position).ID));
//                        SelectedAreaName = DialogList.get(position).shopName;
//                        select_Area_All.setText(SelectedAreaName);
                //String countrylogo = DialogList.get(position).flag;
                //setImg(countrylogo,countryLogo);

                car_color_Id = String.valueOf(DialogList.get(position).id);
                car_color.setText(String.valueOf(DialogList.get(position).CityName));

                //phoneCode=DialogList.get(position).CityName;
                dialog1.dismiss();

            });
        });
        production_year.setOnClickListener(v -> {
            AlertDialog.Builder builder1;
            final AlertDialog dialog1;
            builder1 = new AlertDialog.Builder(UploadCarPhoto.this);
            View mview = getLayoutInflater().inflate(R.layout.area_dialog, null);
            TextView header = mview.findViewById(R.id.DialogHeader);
            TextView all = mview.findViewById(R.id.All);
            all.setVisibility(View.GONE);

            header.setText(getString(R.string.select_production_year));

            DialogRecyclerView = mview.findViewById(R.id.Recycler_Dialog_cities);
            filterAreaAdapter1 = new filterAreaAdapterForCountryCode(DialogList, getApplicationContext());
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), OrientationHelper.VERTICAL, false);

            DialogRecyclerView.setLayoutManager(linearLayoutManager);
            DialogRecyclerView.setItemAnimator(new DefaultItemAnimator());
            initionilizationOFFilterproduction_year();

            DialogRecyclerView.setAdapter(filterAreaAdapter1);
            builder1.setView(mview);
            dialog1 = builder1.create();
            Window window = dialog1.getWindow();
            window.setGravity(Gravity.CENTER);
            dialog1.show();

            filterAreaAdapter1.setOnItemClickListener(v1 -> {
                int position = DialogRecyclerView.getChildAdapterPosition(v1);
                //String position1 = (String.valueOf(position));
//                        AreaID = (Integer.parseInt(DialogList.get(position).ID));
//                        SelectedAreaName = DialogList.get(position).shopName;
//                        select_Area_All.setText(SelectedAreaName);
                //String countrylogo = DialogList.get(position).flag;
                //setImg(countrylogo,countryLogo);

                production_year_Id = String.valueOf(DialogList.get(position).CityName);
                production_year.setText(String.valueOf(DialogList.get(position).CityName));

                //phoneCode=DialogList.get(position).CityName;
                dialog1.dismiss();

            });
        });
        seat_number.setOnClickListener(v -> {
            AlertDialog.Builder builder1;
            final AlertDialog dialog1;
            builder1 = new AlertDialog.Builder(UploadCarPhoto.this);
            View mview = getLayoutInflater().inflate(R.layout.area_dialog, null);
            TextView header = mview.findViewById(R.id.DialogHeader);
            TextView all = mview.findViewById(R.id.All);
            all.setVisibility(View.GONE);

            header.setText(getString(R.string.select_car_seats));

            DialogRecyclerView = mview.findViewById(R.id.Recycler_Dialog_cities);
            filterAreaAdapter1 = new filterAreaAdapterForCountryCode(DialogList, getApplicationContext());
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), OrientationHelper.VERTICAL, false);

            DialogRecyclerView.setLayoutManager(linearLayoutManager);
            DialogRecyclerView.setItemAnimator(new DefaultItemAnimator());
            initionilizationOFFilterSeatNumber();

            DialogRecyclerView.setAdapter(filterAreaAdapter1);
            builder1.setView(mview);
            dialog1 = builder1.create();
            Window window = dialog1.getWindow();
            window.setGravity(Gravity.CENTER);
            dialog1.show();

            filterAreaAdapter1.setOnItemClickListener(v1 -> {
                int position = DialogRecyclerView.getChildAdapterPosition(v1);
                //String position1 = (String.valueOf(position));
//                        AreaID = (Integer.parseInt(DialogList.get(position).ID));
//                        SelectedAreaName = DialogList.get(position).shopName;
//                        select_Area_All.setText(SelectedAreaName);
                //String countrylogo = DialogList.get(position).flag;
                //setImg(countrylogo,countryLogo);

                seat_number_Id = String.valueOf(DialogList.get(position).CityName);
                seat_number.setText(String.valueOf(DialogList.get(position).CityName));

                //phoneCode=DialogList.get(position).CityName;
                dialog1.dismiss();

            });
        });

        mSharedPreferences = getSharedPreferences("tokenDetail", MODE_PRIVATE);
        Language = mSharedPreferences.getString(Constant.language, Locale.getDefault().getLanguage());
        mSubscriptions = new CompositeSubscription();
        hud = KProgressHUD.create(UploadCarPhoto.this)
                .setStyle(KProgressHUD.Style.PIE_DETERMINATE)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .setCancellable(false)
                .setMaxProgress(100);


        img.setOnClickListener(v -> change_img(4));
        car_photo.setOnClickListener(v -> change_img(index));


        car_licence.setOnClickListener(v -> {
            index = 1;
            if (!img1.equals("")) {
                back_img_car_photo.setVisibility(View.INVISIBLE);
                setImg(Constant.BASE_URL__car_img + img1, car_photo);
            } else {
                back_img_car_photo.setVisibility(View.VISIBLE);
                car_photo.setImageResource(R.drawable.ic_add_image2);
            }
            // car_photo.setImageURI(Uri.parse(Constant.BASE_URL__car_img+img1));
            car_licence_txt.setTextColor(getResources().getColor(R.color.whitecolor));
            car_insurance_txt.setTextColor(getResources().getColor(R.color.gray_selected));
            car_paper_txt.setTextColor(getResources().getColor(R.color.gray_selected));
            car_licence_img.setImageDrawable(getResources().getDrawable(R.drawable.ic_driver_license_white));
            car_insurance_img.setImageDrawable(getResources().getDrawable(R.drawable.ic_insurance));
            car_paper_img.setImageDrawable(getResources().getDrawable(R.drawable.ic_document));
            car_licence.setBackgroundColor(getResources().getColor(R.color.app_color));
            car_insurance.setBackgroundColor(getResources().getColor(R.color.grycolor));
            car_paper.setBackgroundColor(getResources().getColor(R.color.grycolor));
        });
        car_insurance.setOnClickListener(v -> {
            index = 2;
            if (!img2.equals("")) {
                back_img_car_photo.setVisibility(View.INVISIBLE);
                setImg(Constant.BASE_URL__car_img + img2, car_photo);
            } else {
                back_img_car_photo.setVisibility(View.VISIBLE);
                car_photo.setImageResource(R.drawable.ic_add_image2);
            }
            //setImg(Constant.BASE_URL__car_img+img2,car_photo);
            car_licence_txt.setTextColor(getResources().getColor(R.color.gray_selected));
            car_insurance_txt.setTextColor(getResources().getColor(R.color.whitecolor));
            car_paper_txt.setTextColor(getResources().getColor(R.color.gray_selected));
            car_licence_img.setImageDrawable(getResources().getDrawable(R.drawable.ic_driver_license));
            car_insurance_img.setImageDrawable(getResources().getDrawable(R.drawable.ic_insurance_white));
            car_paper_img.setImageDrawable(getResources().getDrawable(R.drawable.ic_document));
            car_licence.setBackgroundColor(getResources().getColor(R.color.grycolor));
            car_insurance.setBackgroundColor(getResources().getColor(R.color.app_color));
            car_paper.setBackgroundColor(getResources().getColor(R.color.grycolor));
        });
        car_paper.setOnClickListener(v -> {
            index = 3;
            if (!img3.equals("")) {
                back_img_car_photo.setVisibility(View.INVISIBLE);
                setImg(Constant.BASE_URL__car_img + img3, car_photo);
            } else {
                back_img_car_photo.setVisibility(View.VISIBLE);
                car_photo.setImageResource(R.drawable.ic_add_image2);
            }
            //setImg(Constant.BASE_URL__car_img+img3,car_photo);
            car_licence_txt.setTextColor(getResources().getColor(R.color.gray_selected));
            car_insurance_txt.setTextColor(getResources().getColor(R.color.gray_selected));
            car_paper_txt.setTextColor(getResources().getColor(R.color.whitecolor));
            car_licence_img.setImageDrawable(getResources().getDrawable(R.drawable.ic_driver_license));
            car_insurance_img.setImageDrawable(getResources().getDrawable(R.drawable.ic_insurance));
            car_paper_img.setImageDrawable(getResources().getDrawable(R.drawable.ic_document_white));
            car_licence.setBackgroundColor(getResources().getColor(R.color.grycolor));
            car_insurance.setBackgroundColor(getResources().getColor(R.color.grycolor));
            car_paper.setBackgroundColor(getResources().getColor(R.color.app_color));
        });
        upload.setOnClickListener(v -> {
            /*Intent intent = new Intent(getApplicationContext(),RechargeWallet.class);
            startActivity(intent);*/
            if (img4.equals("")) {
                if (Language.equals("ar")) {
                    Toast.makeText(this, "من فضلك قم بتحميل صور السيارة ", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "please upload car image", Toast.LENGTH_LONG).show();
                }
            } else if (car_brand_Id.equals("")) {
                if (Language.equals("ar")) {
                    Toast.makeText(this, "من فضلك اختر ماركة السيارة ", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "please select car brand", Toast.LENGTH_LONG).show();
                }
            } else if (car_model_Id.equals("")) {
                if (Language.equals("ar")) {
                    Toast.makeText(this, "من فضلك اختر موديل السيارة ", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "please select car Model", Toast.LENGTH_LONG).show();
                }
            } else if (car_type_Id.equals("")) {
                if (Language.equals("ar")) {
                    Toast.makeText(this, "من فضلك اختر نوع السيارة ", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "please select car type", Toast.LENGTH_LONG).show();
                }
            } else if (car_color_Id.equals("")) {
                if (Language.equals("ar")) {
                    Toast.makeText(this, "من فضلك اختر لون السيارة ", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "please select car Color", Toast.LENGTH_LONG).show();
                }
            } else if (production_year_Id.equals("")) {
                if (Language.equals("ar")) {
                    Toast.makeText(this, "من فضلك اختر سنة إنتاج السيارة ", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "please select car Production Year", Toast.LENGTH_LONG).show();
                }
            }
            if (seat_number_Id.equals("")) {
                if (Language.equals("ar")) {
                    Toast.makeText(this, "من فضلك اختر عدد ركاب السيارة ", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "please select Seat Number ", Toast.LENGTH_LONG).show();
                }
            } else if (editText1.getText().toString().equals("")
                    || editText2.getText().toString().equals("")
                    || editText3.getText().toString().equals("")
                    || editText4.getText().toString().equals("")
                    || plate_num1.getText().toString().equals("")
                    || plate_num2.getText().toString().equals("")
                    || plate_num3.getText().toString().equals("")
            ) {
                if (Language.equals("ar")) {
                    Toast.makeText(this, "من فضلك تأكد من رقم السيارة", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "please check car plate", Toast.LENGTH_LONG).show();
                }
            } else if (!Validation.validateFields(SequenceNumber.getText().toString())) {
                if (Language.equals("ar")) {
                    Toast.makeText(this, "من فضلك ادخل الرقم التسلسلي ", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "please Enter Sequence Number ", Toast.LENGTH_LONG).show();
                }
            } else if (img1.equals("")) {
                if (Language.equals("ar")) {
                    Toast.makeText(this, "من فضلك قم بتحميل رخصة السيارة ", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "please upload car licence image", Toast.LENGTH_LONG).show();
                }
            } else if (img2.equals("")) {
                if (Language.equals("ar")) {
                    Toast.makeText(this, "من فضلك قم بتحميل تأمين السيارة ", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "please upload car insurance image", Toast.LENGTH_LONG).show();
                }
            } else if (img3.equals("")) {
                if (Language.equals("ar")) {
                    Toast.makeText(this, "من فضلك قم بتحميل ورق السيارة ", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "please upload car paper image", Toast.LENGTH_LONG).show();
                }
            } else {
                //Toast.makeText(this, "ok", Toast.LENGTH_SHORT).show();
                if (Validation.isConnected(UploadCarPhoto.this)) {
                    AddCarRequestModel addCarRequestModel = new AddCarRequestModel();
                    addCarRequestModel.setCarTypeId(car_type_Id);
                    addCarRequestModel.setCarModelId(car_model_Id);
                    addCarRequestModel.setCarColorId(car_color_Id);
                    addCarRequestModel.setProductionYear(production_year_Id);

                    String plate_num =
                            editText1.getText().toString() + ","
                                    + editText2.getText().toString() + ","
                                    + editText3.getText().toString() + ","
                                    + editText4.getText().toString() + ","
                                    + plate_num1.getText().toString() + ","
                                    + plate_num2.getText().toString() + ","
                                    + plate_num3.getText().toString();

                    addCarRequestModel.setVehicleSequenceNumber(SequenceNumber.getText().toString());
                    addCarRequestModel.setPlateNumber(plate_num);
                    addCarRequestModel.setSeatCount(seat_number_Id);
                    addCarRequestModel.setLicencePhoto(img1);
                    addCarRequestModel.setInsurancePhoto(img2);
                    addCarRequestModel.setCarPaperPhoto(img3);
                    addCarRequestModel.setCarPhoto(img4);
                   /* Toast.makeText(this, car_type_Id, Toast.LENGTH_LONG).show();
                    Toast.makeText(this, car_model_Id, Toast.LENGTH_LONG).show();
                    Toast.makeText(this, car_color_Id, Toast.LENGTH_LONG).show();
                    Toast.makeText(this, production_year_Id, Toast.LENGTH_LONG).show();
                    Toast.makeText(this, plate_number.getText().toString(), Toast.LENGTH_LONG).show();
                    Toast.makeText(this, seat_number_Id, Toast.LENGTH_LONG).show();
                    Toast.makeText(this, img1, Toast.LENGTH_LONG).show();
                    Toast.makeText(this, img2, Toast.LENGTH_LONG).show();
                    Toast.makeText(this, img3, Toast.LENGTH_LONG).show();
                    Toast.makeText(this, img4, Toast.LENGTH_LONG).show();*/
                    hud.show();
                    mSubscriptions.add(NetworkUtil.getRetrofitByToken(DataManager.getInstance().getCashedAccessToken().getAccess_token())
                            .AddCar(addCarRequestModel)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io())
                            .subscribe(this::handleResponse, this::handleError));
                } else {
                    buildDialog(UploadCarPhoto.this).show().setCanceledOnTouchOutside(false);
                }
            }

        });


        if (Validation.isConnected(UploadCarPhoto.this)) {
            hud.show();
            mSubscriptions.add(NetworkUtil.getRetrofitByToken(DataManager.getInstance().getCashedAccessToken().getAccess_token())
                    .metaData()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResponse, this::handleError));
        } else {
            buildDialog(UploadCarPhoto.this).show().setCanceledOnTouchOutside(false);
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
    private void handleResponse(AddCarResponseModelFinal addCarResponseModelFinal) {

        if (addCarResponseModelFinal.getModel() != null) {

            String message = "";
            if (Language.equals("ar")) {
                message = "تم اضافة السيارة بنجاح";
            } else {
                message = "Car Added successfully";
            }

            android.support.v7.app.AlertDialog.Builder builder;
            android.support.v7.app.AlertDialog dialog;
            builder = new android.support.v7.app.AlertDialog.Builder(UploadCarPhoto.this);

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
                Intent intent = new Intent(UploadCarPhoto.this, Cars.class);
                //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            });
        }
        hud.dismiss();

    }

    private void handleResponse(MetaDataResponseModel metaDataResponseModel) {
        //Toast.makeText(this, "11", Toast.LENGTH_SHORT).show();
        if (metaDataResponseModel.getModel() != null) {
            if (metaDataResponseModel.getModel().getCarBrands() != null) {
                carBrands = metaDataResponseModel.getModel().getCarBrands();
                carTypes = metaDataResponseModel.getModel().getCarTypes();
                carColor = metaDataResponseModel.getModel().getCarColors();
                // carModels =metaDataResponseModel.getModel().getCarBrands().get(car_brand_Position).getCarModels();
                // Toast.makeText(this, metaDataResponseModel.getModel().getCountries().size()+"", Toast.LENGTH_SHORT).show();
            }
        }
        hud.dismiss();
    }

    private void setImg(String url, ImageView image) {

        if (url.equals("")) {
            if (flag) {
                back_img.setVisibility(View.INVISIBLE);
            } else {
                back_img.setVisibility(View.VISIBLE);
            }
            image.setImageResource(R.drawable.ic_add_image2);
            // back_img_car_photo.setVisibility(View.GONE);

        } else {
            try {

                Picasso.with(getApplicationContext())
                        .load(url)
                        .placeholder(R.drawable.ic_add_image2)
                        .into(image);
            } catch (Exception ignored) {

            }
        }
    }

    private void change_img(int finalIndex) {
        this.finalIndex = finalIndex;
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
                        if (finalIndex == 4) {
                            img.setImageURI(selectedImageURI);
                            back_img.setVisibility(View.INVISIBLE);
                        } else {
                            car_photo.setImageURI(selectedImageURI);
                            back_img_car_photo.setVisibility(View.INVISIBLE);
                        }
                        Bitmap bm = BitmapFactory.decodeFile(path);
                        //BitmapFactory.Options options = new BitmapFactory.Options();
                        //options.inJustDecodeBounds = true;
                        //options.inSampleSize = 3;
                        //bm.decodeFile(path);
                        //BitmapFactory.decodeResource(getResources(), R.mipmap.hqimage, options);
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bm.compress(Bitmap.CompressFormat.JPEG, 50, baos); //bm is the bitmap object
                        byte[] b = baos.toByteArray();
                        image_url = "" + Base64.encodeToString(b, Base64.DEFAULT);
                        UploadImage();
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
                    if (finalIndex == 4) {
                        img.setImageURI(tempUri);
                        back_img.setVisibility(View.INVISIBLE);
                    } else {
                        car_photo.setImageURI(tempUri);
                        back_img_car_photo.setVisibility(View.INVISIBLE);

                    }
                    String path = getPath(tempUri);
                    Bitmap bm = BitmapFactory.decodeFile(path);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bm.compress(Bitmap.CompressFormat.JPEG, 50, baos); //bm is the bitmap object
                    byte[] b = baos.toByteArray();
                    image_url = "" + Base64.encodeToString(b, Base64.DEFAULT);
                    UploadImage();

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
            PackageManager pm = UploadCarPhoto.this.getPackageManager();
            int hasPerm = pm.checkPermission(Manifest.permission.CAMERA, UploadCarPhoto.this.getPackageName());
            if (hasPerm == PackageManager.PERMISSION_GRANTED) {
                android.support.v7.app.AlertDialog.Builder builder;
                android.support.v7.app.AlertDialog dialog;
                builder = new android.support.v7.app.AlertDialog.Builder(UploadCarPhoto.this);

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

    private void UploadImage() {
        if (Validation.isConnected(UploadCarPhoto.this)) {
            AddCarPhotoRequest addCarPhotoRequest = new AddCarPhotoRequest();
            addCarPhotoRequest.setNumberType(finalIndex);
            addCarPhotoRequest.setImage(image_url);
            hud.show();
            mSubscriptions.add(NetworkUtil.getRetrofitByToken(DataManager.getInstance().getCashedAccessToken().getAccess_token())
                    .AddCarPhoto(addCarPhotoRequest)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResponseUpdateImage, this::handleError));
        } else {
            buildDialog(UploadCarPhoto.this).show().setCanceledOnTouchOutside(false);
        }
    }

    private void handleError(Throwable throwable) {
        //Toast.makeText(getActivity(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
        String message = "";
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

                        String wasl_27 = jsonObject.getJSONObject("errors").getString("message");
                        JSONObject obj_27 = new JSONObject(wasl_27.replace("\\", ""));
                        String waslCode_27 = obj_27.getString("resultCode");

                        switch (waslCode_27) {
                            case "503":
                                message = getString(R.string.wasl_error_addCar_503);
                                break;
                            case "420":
                                message = getString(R.string.wasl_error_addCar_420);
                                break;
                            case "421":
                                message = getString(R.string.wasl_error_addCar_421);
                                break;
                            default:
                                message = getString(R.string.default_message);
                        }

                        // message = waslCode_27;
                        break;
                    case "28":
                        String wasl_28 = jsonObject.getJSONObject("errors").getString("message");
                        JSONObject obj_28 = new JSONObject(wasl_28.replace("\\", ""));
                        String waslCode_28 = obj_28.getString("resultCode");
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
            } else {
                //Toast.makeText(AcceptOrRejectTrip.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                message = throwable.getMessage();
            }

        } catch (Exception ex) {
            //view.onError(e.getMessage());
            message = throwable.getMessage();
        }

        String error = "failed to connect".toLowerCase();
        if (message.toLowerCase().contains(error)) {
            message = getString(R.string.check_internet);
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

    private void handleResponseUpdateImage(AddCarResponse addCarResponse) {
        //Toast.makeText(this, addCarResponse.getModel().getImageName(), Toast.LENGTH_SHORT).show();
        if (finalIndex == 1) {
            img1 = addCarResponse.getModel().getImageName();
        } else if (finalIndex == 2) {
            img2 = addCarResponse.getModel().getImageName();
        } else if (finalIndex == 3) {
            img3 = addCarResponse.getModel().getImageName();
        } else if (finalIndex == 4) {
            img4 = addCarResponse.getModel().getImageName();
            flag = true;

        }
        /*Toast.makeText(this, addCarResponse.getModel().getImageName()+"", Toast.LENGTH_SHORT).show();
        Toast.makeText(this, addCarResponse.getModel().getNumberType()+"", Toast.LENGTH_SHORT).show();*/
        hud.dismiss();
    }

    private void initionilizationOFFilterCarBrand() {
        DialogList.clear();
        try {
            for (int k = 0; k < carBrands.size(); k++) {
                String name = "";
                if (Language.equals("en")) {
                    name = carBrands.get(k).getName();
                } else {
                    name = carBrands.get(k).getNameLT();

                }
                DialogList.add(new filterAreaModelRecycler(
                        name
                        , ""
                        , ""
                        , car_brand.getId()));
            }
            filterAreaAdapter1.update(DialogList);
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    private void initionilizationOFFilterCarModel() {
        DialogList.clear();
        carModels = carBrands.get(car_brand_Position).getCarModels();
        try {
            for (int k = 0; k < carModels.size(); k++) {
                String name = "";
                if (Language.equals("en")) {
                    name = carModels.get(k).getName();
                } else {
                    name = carModels.get(k).getNameLT();

                }
                DialogList.add(new filterAreaModelRecycler(
                        name
                        , ""
                        , ""
                        , carModels.get(k).getId()));
            }
            filterAreaAdapter1.update(DialogList);
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    private void initionilizationOFFilterCarType() {
        DialogList.clear();
        try {
            for (int k = 0; k < carTypes.size(); k++) {
                String name = "";
                if (Language.equals("en")) {
                    name = carTypes.get(k).getName();
                } else {
                    name = carTypes.get(k).getNameLT();

                }
                DialogList.add(new filterAreaModelRecycler(
                        name
                        , ""
                        , ""
                        , carTypes.get(k).getId()));
            }
            filterAreaAdapter1.update(DialogList);
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    private void initionilizationOFFilterCarColor() {
        DialogList.clear();
        try {
            for (int k = 0; k < carColor.size(); k++) {
                String name = "";
                if (Language.equals("en")) {
                    name = carColor.get(k).getName();
                } else {
                    name = carColor.get(k).getNameLT();

                }
                DialogList.add(new filterAreaModelRecycler(
                        name
                        , ""
                        , ""
                        , carColor.get(k).getId()));
            }
            filterAreaAdapter1.update(DialogList);
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    private void initionilizationOFFilterproduction_year() {
        DialogList.clear();
        try {
            for (int k = 0; k < productionYear.size(); k++) {
                DialogList.add(new filterAreaModelRecycler(
                        productionYear.get(k)
                        , ""
                        , ""
                        , 1));
            }
            filterAreaAdapter1.update(DialogList);
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    private void initionilizationOFFilterSeatNumber() {
        DialogList.clear();
        try {
            for (int k = 0; k < seatNumber.size(); k++) {
                DialogList.add(new filterAreaModelRecycler(
                        seatNumber.get(k)
                        , ""
                        , ""
                        , 1));
            }
            filterAreaAdapter1.update(DialogList);
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    private void initionilizationOFFilterCarPlateNumber() {
        DialogList.clear();
        try {
            DialogList.add(new filterAreaModelRecycler(
                    "ا"
                    , ""
                    , ""
                    , 1));
            DialogList.add(new filterAreaModelRecycler(
                    "ب"
                    , ""
                    , ""
                    , 2));
            DialogList.add(new filterAreaModelRecycler(
                    "ح"
                    , ""
                    , ""
                    , 3));
            DialogList.add(new filterAreaModelRecycler(
                    "د"
                    , ""
                    , ""
                    , 4));
            DialogList.add(new filterAreaModelRecycler(
                    "ر"
                    , ""
                    , ""
                    , 5));
            DialogList.add(new filterAreaModelRecycler(
                    "س"
                    , ""
                    , ""
                    , 6));
            DialogList.add(new filterAreaModelRecycler(
                    "ص"
                    , ""
                    , ""
                    , 7));
            DialogList.add(new filterAreaModelRecycler(
                    "ط"
                    , ""
                    , ""
                    , 8));
            DialogList.add(new filterAreaModelRecycler(
                    "ع"
                    , ""
                    , ""
                    , 9));
            DialogList.add(new filterAreaModelRecycler(
                    "ق"
                    , ""
                    , ""
                    , 10));
            DialogList.add(new filterAreaModelRecycler(
                    "ك"
                    , ""
                    , ""
                    , 11));
            DialogList.add(new filterAreaModelRecycler(
                    "ل"
                    , ""
                    , ""
                    , 12));
            DialogList.add(new filterAreaModelRecycler(
                    "م"
                    , ""
                    , ""
                    , 13));
            DialogList.add(new filterAreaModelRecycler(
                    "ن"
                    , ""
                    , ""
                    , 14));
            DialogList.add(new filterAreaModelRecycler(
                    "ه"
                    , ""
                    , ""
                    , 15));
            DialogList.add(new filterAreaModelRecycler(
                    "و"
                    , ""
                    , ""
                    , 16));
            DialogList.add(new filterAreaModelRecycler(
                    "ي"
                    , ""
                    , ""
                    , 17));
            filterAreaAdapter1.update(DialogList);
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    public void onBackPressed() {
        if (carStatus.equals("1")) {
            finish();
        } else {
            Intent intent = new Intent(getApplicationContext(), Cars.class);
            startActivity(intent);
            //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
            finish();
        }
    }
}
