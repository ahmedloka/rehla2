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
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutCompat;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import com.NativeTech.rehla.model.data.dto.Models.LoginModel.LoginResponseModel;
import com.NativeTech.rehla.model.data.dto.Models.UploadIdentityCard.UploadIdentityCardRequest;
import com.NativeTech.rehla.Network.NetworkUtil;
import com.NativeTech.rehla.R;
import com.NativeTech.rehla.Utills.Constant;
import com.NativeTech.rehla.Utills.Validation;

import retrofit2.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static com.NativeTech.rehla.Utills.Constant.buildDialog;
import static droidninja.filepicker.FilePickerConst.REQUEST_CODE_PHOTO;

public class UploadLicence extends AppCompatActivity {

    private LinearLayoutCompat  identity;
    private LinearLayoutCompat  passport;
    private TextView            identity_txt;
    private TextView            passport_txt;
    private AppCompatImageView  identity_img;
    private AppCompatImageView  passport_img;
    private Button              upload;


    private String image_url="";
    String imageType;
    private final String[] permissions = new String[]{

            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
    };

    private AppCompatImageView img;
    private AppCompatImageView back_img;


    private String Language;
    private CompositeSubscription mSubscriptions;
    private SharedPreferences mSharedPreferences;
    private KProgressHUD hud;

    private String identityCardPhoto="";
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_licence);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mSharedPreferences  =   getSharedPreferences("tokenDetail",MODE_PRIVATE);
        Language            =   mSharedPreferences.getString(Constant.language, Locale.getDefault().getLanguage());
        identityCardPhoto   =   mSharedPreferences.getString(Constant.IdentityCardPhoto, "");
        mSubscriptions      =   new CompositeSubscription();
        hud = KProgressHUD.create(UploadLicence.this)
                .setStyle(KProgressHUD.Style.PIE_DETERMINATE)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .setCancellable(false)
                .setMaxProgress(100);



        img                         =findViewById(R.id.img);
        back_img                    =findViewById(R.id.back_img);
        identity                    =findViewById(R.id.identity);
        passport                    =findViewById(R.id.passport);
        identity_txt                =findViewById(R.id.identity_txt);
        passport_txt                =findViewById(R.id.passport_txt);
        identity_img                =findViewById(R.id.identity_img);
        passport_img                =findViewById(R.id.passport_img);
        upload                      =findViewById(R.id.upload);

        back_img.setVisibility(View.GONE);
        setImg(identityCardPhoto,img);
        identity.setOnClickListener(v -> {
            identity_txt.setTextColor(getResources().getColor(R.color.whitecolor));
            passport_txt.setTextColor(getResources().getColor(R.color.gray_selected));
            identity_img.setImageDrawable(getResources().getDrawable(R.drawable.ic_certificate_white));
            passport_img.setImageDrawable(getResources().getDrawable(R.drawable.ic_certificate));
            identity.setBackgroundColor(getResources().getColor(R.color.green_color));
            passport.setBackgroundColor(getResources().getColor(R.color.grycolor));
        });
        passport.setOnClickListener(v -> {
            identity_txt.setTextColor(getResources().getColor(R.color.gray_selected));
            passport_txt.setTextColor(getResources().getColor(R.color.whitecolor));
            identity_img.setImageDrawable(getResources().getDrawable(R.drawable.ic_certificate));
            passport_img.setImageDrawable(getResources().getDrawable(R.drawable.ic_certificate_white));
            identity.setBackgroundColor(getResources().getColor(R.color.grycolor));
            passport.setBackgroundColor(getResources().getColor(R.color.green_color));
        });

        img.setOnClickListener(v -> change_img());

        upload.setOnClickListener(v -> {
            if (image_url.equals(""))
            {
                if (Language.equals("ar"))
                {
                    Toast.makeText(UploadLicence.this, "من فضلك قم بتحميل الصورة", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(UploadLicence.this, "please upload photo first!", Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                UploadImage();
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
                        back_img.setVisibility(View.GONE);
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
                    back_img.setVisibility(View.GONE);
                    String path = getPath(tempUri);
                    Bitmap bm = BitmapFactory.decodeFile(path);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bm.compress(Bitmap.CompressFormat.JPEG, 50  , baos); //bm is the bitmap object
                    byte[] b = baos.toByteArray();
                    image_url= ""+Base64.encodeToString(b, Base64.DEFAULT);

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
            PackageManager pm = UploadLicence.this.getPackageManager();
            int hasPerm  = pm.checkPermission(Manifest.permission.CAMERA, UploadLicence.this.getPackageName());
            if (hasPerm == PackageManager.PERMISSION_GRANTED) {
                android.support.v7.app.AlertDialog.Builder builder;
                android.support.v7.app.AlertDialog dialog;
                builder = new android.support.v7.app.AlertDialog.Builder(UploadLicence.this);

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
    private void UploadImage()
    {
        if (Validation.isConnected(UploadLicence.this))
        {
            UploadIdentityCardRequest uploadIdentityCardRequest=new UploadIdentityCardRequest();
            uploadIdentityCardRequest.setIdentityCardPhoto(image_url);
            hud.show();
            mSubscriptions.add(NetworkUtil.getRetrofitByToken(DataManager.getInstance().getCashedAccessToken().getAccess_token())
                    .UploadIdentityCard(uploadIdentityCardRequest)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResponseUpdateImage, this::handleError));
        }
        else
        {
            buildDialog(UploadLicence.this).show().setCanceledOnTouchOutside(false);
        }
    }
    private void handleResponseUpdateImage(LoginResponseModel loginResponseModel) {

        if (loginResponseModel.getModel()!=null)
        {

            if (loginResponseModel.getModel().getIdentityCardPhoto()!=null)
            {
                SharedPreferences.Editor editor_image = mSharedPreferences.edit();
                editor_image.putString(Constant.IdentityCardPhoto, String.valueOf(loginResponseModel.getModel().getIdentityCardPhoto()));
                editor_image.apply();
            }


            String message="";
            if (Language.equals("ar"))
            {
                message="تم رفع الصورة بنجاح";
            }
            else
            {
                message="Image Uploaded successfully";
            }

            android.support.v7.app.AlertDialog.Builder builder;
            android.support.v7.app.AlertDialog dialog;
            builder = new android.support.v7.app.AlertDialog.Builder(UploadLicence.this);

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
                Intent intent = new Intent(UploadLicence.this, Home.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            });


        }
        hud.dismiss();

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
    private void setImg(String url, ImageView image)
    {

        if(url.equals(""))
        {
            image.setImageResource(R.drawable.ic_add_image);
            back_img.setVisibility(View.VISIBLE);
        }
        else
        {
            try {
                String urlfinal=Constant.BASE_URL_profile_img+url;
                Picasso.with(UploadLicence.this)
                        .load(urlfinal)
                        .placeholder(R.drawable.gry_image)
                        .into(image);
            } catch (Exception ignored) {

            }
        }
    }
}
