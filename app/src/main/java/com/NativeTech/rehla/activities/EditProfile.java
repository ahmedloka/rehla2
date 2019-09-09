package com.NativeTech.rehla.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.NativeTech.rehla.model.DataManager;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import com.NativeTech.rehla.model.data.dto.Models.LoginModel.LoginResponseModel;
import com.NativeTech.rehla.model.data.dto.Models.MetaDataModels.Cities;
import com.NativeTech.rehla.model.data.dto.Models.MetaDataModels.Countries;
import com.NativeTech.rehla.model.data.dto.Models.MetaDataModels.MetaDataResponseModel;
import com.NativeTech.rehla.model.data.dto.Models.Register.RegisterRequestModel;
import com.NativeTech.rehla.model.data.dto.Models.UpdateImage.UploadProfileImageRequest;
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
import static com.NativeTech.rehla.Utills.TerhalUtils.getDateWithUTCZone;
import static com.NativeTech.rehla.Utills.TerhalUtils.parseDateTo_yyyyMMdd;
import static droidninja.filepicker.FilePickerConst.REQUEST_CODE_PHOTO;

public class EditProfile extends AppCompatActivity {


    private String Language;
    private CompositeSubscription mSubscriptions;
    private SharedPreferences mSharedPreferences;
    private KProgressHUD hud;



    private CircleImageView             img;
    private TextView                    name;
    private TextView                    spinner_countries;
    private TextView                    date;
    private TextView                    city;
    private AppCompatEditText           email;
    private AppCompatEditText           fullname;
    private AppCompatEditText           phone;
    private Button                      save;



    private String  phoneCode ="-1";
    private String  cityId ="-1";
    private int mYear;
    private int mMonth;
    private int mDay;
    private String BirthOfDate="-1";
    private Calendar mcurrentDate;
    private RecyclerView DialogRecyclerView;
    private filterAreaAdapterForCountryCode filterAreaAdapter1;
    private final ArrayList<filterAreaModelRecycler> DialogList = new ArrayList<>();
    private List<Countries> countries=new ArrayList<>();
    private List<Cities> cities=new ArrayList<>();


    private String image_url="";
    String imageType;
    private final String[] permissions = new String[]{

            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
    };
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.edit_profile);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        img               =  findViewById(R.id.img);
        name               =  findViewById(R.id.name);
        spinner_countries               =  findViewById(R.id.spinner_countries);
        date               =  findViewById(R.id.date);
        city               =  findViewById(R.id.city);
        email               =  findViewById(R.id.email);
        fullname               =  findViewById(R.id.fullname);
        phone               =  findViewById(R.id.phone);
        save               =  findViewById(R.id.save);



        mSharedPreferences  =   getSharedPreferences("tokenDetail",MODE_PRIVATE);
        Language            =   mSharedPreferences.getString(Constant.language,Locale.getDefault().getLanguage());
        mSubscriptions      =   new CompositeSubscription();
        hud = KProgressHUD.create(EditProfile.this)
                .setStyle(KProgressHUD.Style.PIE_DETERMINATE)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .setCancellable(false)
                .setMaxProgress(100);


        setImg( mSharedPreferences.getString(Constant.imageUri,""),img);

        Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH);
        BirthOfDate= getDateWithUTCZone(dateFormat.format(c.getTime()));
        //Toast.makeText(this,startingDate , Toast.LENGTH_SHORT).show();



        //date.setText(BirthOfDate);

        date.setOnClickListener(v ->{
            DatePickerDialog mDatePicker = new DatePickerDialog(EditProfile.this , (datepicker, selectedyear, selectedmonth, selectedday) -> {
                mcurrentDate = Calendar.getInstance();
                mcurrentDate.set(Calendar.YEAR, selectedyear);
                mcurrentDate.set(Calendar.MONTH, selectedmonth);
                mcurrentDate.set(Calendar.DAY_OF_MONTH, selectedday);
                date.setText(getDateWithUTCZone(dateFormat.format(mcurrentDate.getTime())));
                mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);
                mMonth = mcurrentDate.get(Calendar.MONTH);
                mYear = mcurrentDate.get(Calendar.YEAR);

                BirthOfDate= getDateWithUTCZone(dateFormat.format(mcurrentDate.getTime()));


            },mYear,mMonth , mDay);
            //mDatePicker.setTitle("Select date");
            mDatePicker.show();
        });
        img.setOnClickListener(v -> change_img());
        spinner_countries.setOnClickListener(v -> {

            AlertDialog.Builder builder1;
            final AlertDialog dialog1;
            builder1 = new AlertDialog.Builder(EditProfile.this);
            View mview = getLayoutInflater().inflate(R.layout.area_dialog, null);
            TextView  header = mview.findViewById(R.id.DialogHeader);
            TextView  all = mview.findViewById(R.id.All);
            all.setVisibility(View.GONE);
            if(Language.equals("en"))
            {

                header.setText("Select Country Code");

            }
            else
            {

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
               // spinner_countries.setText(String.valueOf(DialogList.get(position).CityName));
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
            builder1 = new AlertDialog.Builder(EditProfile.this);
            View mview = getLayoutInflater().inflate(R.layout.area_dialog, null);
            TextView  header = mview.findViewById(R.id.DialogHeader);
            TextView  all = mview.findViewById(R.id.All);
            all.setVisibility(View.GONE);
            if(Language.equals("en"))
            {

                header.setText("Select City");

            }
            else
            {

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
        save.setOnClickListener(v -> {
            if (Validation.isConnected(EditProfile.this))
            {
                if(Language.equals("en"))
                {
                    if (!Validation.validateFields(email.getText().toString()))
                    {
                        email.setError("please Enter Email");
                    }
                    else if (!Validation.validateFields(fullname.getText().toString()))
                    {
                        fullname.setError("please Enter Full Name");
                    }
                    else if (!Validation.validateFields(phone.getText().toString()))
                    {
                        phone.setError("please Enter Phone");
                    }
                    else
                    {
                        updateProfile();
                    }
                }
                else
                {
                    if (!Validation.validateFields(email.getText().toString()))
                    {
                        email.setError("من فضلك ادخل الايميل");
                    }
                    else if (!Validation.validateFields(fullname.getText().toString()))
                    {
                        fullname.setError("من فضلك ادخل الاسم");
                    }
                    else if (!Validation.validateFields(phone.getText().toString()))
                    {
                        phone.setError("من فضلك ادخل رقم الهاتف");
                    }
                    else
                    {
                        updateProfile();
                    }

                }
            }
            else
            {
                buildDialog(EditProfile.this).show().setCanceledOnTouchOutside(false);
            }
        });
        //Toast.makeText(this, DataManager.getInstance().getCashedAccessToken().getAccess_token(), Toast.LENGTH_SHORT).show();
        if (Validation.isConnected(EditProfile.this))
        {
            hud.show();
            mSubscriptions.add(NetworkUtil.getRetrofitByToken(DataManager.getInstance().getCashedAccessToken().getAccess_token())
                    .metaData()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResponse, this::handleError));
        }
        else
        {
            buildDialog(EditProfile.this).show().setCanceledOnTouchOutside(false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
        {
         onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void handleError(Throwable throwable) {
        //Toast.makeText(getActivity(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
        String message="";
        try {
            if (throwable instanceof HttpException) {
                HttpException error = (HttpException) throwable;
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
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.show();
        TextView txt = mview.findViewById(R.id.txt);
        TextView ok = mview.findViewById(R.id.ok);
        txt.setText(message);
        ok.setOnClickListener(v2 -> dialog.dismiss());



        hud.dismiss();
    }
    private void handleResponse(MetaDataResponseModel metaDataResponseModel) {
        //Toast.makeText(this, "11", Toast.LENGTH_SHORT).show();
        //hud.dismiss();
        if (metaDataResponseModel.getModel()!=null)
        {
            if (metaDataResponseModel.getModel().getCities()!=null)
            {
                cities=metaDataResponseModel.getModel().getCities();
                //Toast.makeText(this, metaDataResponseModel.getModel().getCountries().size()+"", Toast.LENGTH_SHORT).show();
            }
            if (metaDataResponseModel.getModel().getCountries()!=null)
            {
                countries=metaDataResponseModel.getModel().getCountries();
                if (Validation.isConnected(EditProfile.this))
                {
                    //hud.show();
                    mSubscriptions.add(NetworkUtil.getRetrofitByToken(DataManager.getInstance().getCashedAccessToken().getAccess_token())
                            .GetUserProfile()
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io())
                            .subscribe(this::handleResponseGetData, this::handleError));
                }
                else
                {
                    buildDialog(EditProfile.this).show().setCanceledOnTouchOutside(false);
                    hud.dismiss();
                }
                // Toast.makeText(this, metaDataResponseModel.getModel().getCountries().size()+"", Toast.LENGTH_SHORT).show();
            }

            //Toast.makeText(this, "model not null", Toast.LENGTH_SHORT).show();

        }
        else
        {
            hud.dismiss();
        }
        //Toast.makeText(this, "model null", Toast.LENGTH_SHORT).show();
    }
    private void handleResponseGetData(LoginResponseModel loginResponseModel) {
        if (loginResponseModel.getModel()!=null)
        {
            setImg(Constant.BASE_URL_profile_img+loginResponseModel.getModel().getProfilePhoto(),img);
            name.setText(loginResponseModel.getModel().getName());
            email.setText(loginResponseModel.getModel().getEmail());
            fullname.setText(loginResponseModel.getModel().getName());
            spinner_countries.setText(loginResponseModel.getModel().getPhoneKey());
            if (loginResponseModel.getModel().getPhoneKey().equals("+2"))
            {
                //spinner_countries.setText("+2");
                phoneCode="0";
            }
            else
            {
                phoneCode="1";
            }
            phone.setText(loginResponseModel.getModel().getPhoneNumber());
            if(loginResponseModel.getModel().getDateOfBirth()!=null)
            {
                BirthOfDate=parseDateTo_yyyyMMdd(loginResponseModel.getModel().getDateOfBirth());
                date.setText(BirthOfDate);
            }
            //System.out.println(formattedDate); // prints 10-04-2018
            //String[] items1 = formattedDate.split("-");
            /*mDay= Integer.parseInt(items1[0]);
            mMonth= Integer.parseInt(items1[1]);
            mYear= Integer.parseInt(items1[2]);*/
            cityId=loginResponseModel.getModel().getCityId();
            //initionilizationOFFilterRecyclerCity();
            for (int i=0;i<cities.size();i++)
            {
                if (cityId.equals(String.valueOf(cities.get(i).getId())))
                {
                    if (Language.equals("ar"))
                    city.setText(cities.get(i).getNameLT());
                    else if (Language.equals("en"))
                        city.setText(cities.get(i).getName());
                }
            }
        }
        hud.dismiss();

    }
    private void setImg(String url, ImageView image)
    {

        if(url.equals(""))
        {
        }
        else {
            try {

                Picasso.with(getApplicationContext())
                        .load(url)
                        .placeholder(R.drawable.ic_user)
                        .into(image);
            } catch (Exception ignored) {

            }
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
    private void initionilizationOFFilterCountryCodeRecycler() {
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
    private void change_img() {
        try {
            if(checkPermissions())
            {} else
            {
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
        switch (requestCode)
        {
            case REQUEST_CODE_PHOTO:

                if(resultCode==RESULT_OK && data!=null)
                {


                    try
                    {
                        Uri selectedImageURI = data.getData();
                        String path = getPath(selectedImageURI);
                        img.setImageURI(selectedImageURI);
                        Bitmap bm = BitmapFactory.decodeFile(path);
                        //BitmapFactory.Options options = new BitmapFactory.Options();
                        //options.inJustDecodeBounds = true;
                        //options.inSampleSize = 3;
                        //bm.decodeFile(path);
                        //BitmapFactory.decodeResource(getResources(), R.mipmap.hqimage, options);
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bm.compress(Bitmap.CompressFormat.JPEG, 50, baos); //bm is the bitmap object
                        byte[] b = baos.toByteArray();
                        image_url= ""+ Base64.encodeToString(b, Base64.DEFAULT);
                        UploadImage();
                    }catch (Exception e)
                    {
                        //e.printStackTrace();
                        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                break;

            case 0:

                Bitmap bitmap;

                if(data!=null)
                {
                    bitmap = (Bitmap) data.getExtras().get("data");

                    // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
                    Uri tempUri = getImageUri(this, bitmap);
                    img.setImageURI(tempUri);
                    String path = getPath(tempUri);
                    Bitmap bm = BitmapFactory.decodeFile(path);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bm.compress(Bitmap.CompressFormat.JPEG, 50  , baos); //bm is the bitmap object
                    byte[] b = baos.toByteArray();
                    image_url= ""+Base64.encodeToString(b, Base64.DEFAULT);
                    UploadImage();

                }
        }
    }
    private Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        byte[] b = bytes.toByteArray();
        String encodedString = Base64.encodeToString(b, Base64.DEFAULT);
        image_url = ""+encodedString;
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }
    private String getPath(Uri contentUri) {
        String result = null;
        String[] projection = {MediaStore.Images.Media.DATA};

        try {
            Cursor cursor = getApplicationContext().getContentResolver().query(contentUri, projection, null,null,null);
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
        }
        else
        {
            PackageManager pm = EditProfile.this.getPackageManager();
            int hasPerm  = pm.checkPermission(Manifest.permission.CAMERA, EditProfile.this.getPackageName());
            if (hasPerm == PackageManager.PERMISSION_GRANTED) {
                android.support.v7.app.AlertDialog.Builder builder;
                android.support.v7.app.AlertDialog dialog;
                builder = new android.support.v7.app.AlertDialog.Builder(EditProfile.this);

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

    private void updateProfile()
    {
        if (Validation.isConnected(EditProfile.this))
        {
            RegisterRequestModel registerRequestModel=new RegisterRequestModel();
            registerRequestModel.setName(fullname.getText().toString());
            registerRequestModel.setEmail(Objects.requireNonNull(email.getText()).toString());
            //registerRequestModel.setPassword(password.getText().toString());
            if (phoneCode.equals("0"))
            {
                registerRequestModel.setPhoneKey("+2");
            }
            else
            {
                registerRequestModel.setPhoneKey("+966");
            }
            registerRequestModel.setPhoneNumber(phone.getText().toString());
            registerRequestModel.setDateOfBirth(BirthOfDate);
            registerRequestModel.setCityId(Integer.parseInt(cityId));
            //registerRequestModel.setProfilePhoto(image_url);
           // Toast.makeText(this, phone.getText().toString()+"", Toast.LENGTH_SHORT).show();
            hud.show();
            mSubscriptions.add(NetworkUtil.getRetrofitByToken(DataManager.getInstance().getCashedAccessToken().getAccess_token())
                    .updateProfile(registerRequestModel)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResponse, this::handleError));
        }
        else
        {
            buildDialog(EditProfile.this).show().setCanceledOnTouchOutside(false);
        }
    }
    private void handleResponse(LoginResponseModel loginResponseModel) {
        //Toast.makeText(this, "ok", Toast.LENGTH_SHORT).show();

        if (loginResponseModel.getModel()!=null)
        {
            /*if (loginResponseModel.getModel().getToken()!=null)
            {
                SharedPreferences.Editor editor_userID = mSharedPreferences.edit();
                editor_userID.putString(Constant.UserID, String.valueOf(loginResponseModel.getModel().getToken().get(0).getAccess_token()));
                editor_userID.apply();
            }*/
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
            if (loginResponseModel.getModel().getVCode()!=null)
            {
                SharedPreferences.Editor editor_vCode = mSharedPreferences.edit();
                editor_vCode.putString(Constant.vCode, String.valueOf(loginResponseModel.getModel().getVCode()));
                editor_vCode.apply();
            }

            if (loginResponseModel.getModel().getPhoneKey()!=null)
            {
                SharedPreferences.Editor editor_mobileKey = mSharedPreferences.edit();
                editor_mobileKey.putString(Constant.mobileKey, String.valueOf(loginResponseModel.getModel().getPhoneKey()));
                editor_mobileKey.apply();
            }

            if (loginResponseModel.getModel().getEmail()!=null)
            {
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

            String message="";
            if (Language.equals("ar"))
            {
                message="تم تحديث بياناتك بنجاح";
            }
            else
            {
                message="profile updated successfully";
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
                Intent intent = new Intent(EditProfile.this, Profile.class);
                //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            });
            //Intent intent = new Intent(ChangePassword.this, Profile.class);
            //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            //startActivity(intent);
            // finish();
        }
        //finish();
        //hud.dismiss();
            /*if (!loginResponseModel.getModel().getVerified())
            {
                Intent intent = new Intent(EditProfile.this, VerificationCodeRegister.class);
                //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                //finish();
            }
            else
            {
                Intent intent = new Intent(EditProfile.this, Profile.class);
                //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }*/
        hud.dismiss();
    }

    private void UploadImage()
    {
        if (Validation.isConnected(EditProfile.this))
        {
            UploadProfileImageRequest uploadProfileImageRequest=new UploadProfileImageRequest();
            uploadProfileImageRequest.setProfilePhoto(image_url);
            hud.show();
            mSubscriptions.add(NetworkUtil.getRetrofitByToken(DataManager.getInstance().getCashedAccessToken().getAccess_token())
                    .UploadImage(uploadProfileImageRequest)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResponseUpdateImage, this::handleError));
        }
        else
        {
            buildDialog(EditProfile.this).show().setCanceledOnTouchOutside(false);
        }
    }
    private void handleResponseUpdateImage(LoginResponseModel loginResponseModel) {

        if (loginResponseModel.getModel()!=null)
        {

            if (loginResponseModel.getModel().getProfilePhoto()!=null)
            {
                SharedPreferences.Editor editor_image = mSharedPreferences.edit();
                editor_image.putString(Constant.imageUri, Constant.BASE_URL_profile_img + loginResponseModel.getModel().getProfilePhoto());
                editor_image.apply();
            }
            Intent intent = new Intent(EditProfile.this, EditProfile.class);
            //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
        hud.dismiss();

    }
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(),Profile.class);
        startActivity(intent);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
        finish();
    }

}
