package com.NativeTech.rehla.Utills;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.google.gson.Gson;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import com.NativeTech.rehla.R;
import com.NativeTech.rehla.services.MyService;

import static android.content.Context.MODE_PRIVATE;


public class Constant {
    ////http://api.rehlacar.com/api/
    public static final String BASE_URL_HTTP ="http://api.rehlacar.com/api/";
    public static final String BASE_URL_HTTP_Get_Address ="https://maps.googleapis.com/maps/api/";


    public static final String BASE_URL_profile_img ="http://api.rehlacar.com/profileimages/";
    public static final String BASE_URL__car_img ="http://api.rehlacar.com/CarImages/";


    public static final String counteryID = "getLocation";
    public static final String counteryName = "1";
    public static final String counterylogo = "counterylogo";
    public static final String cityID = "getLocation";
    public static final String cityName = "0";
    public static final String citylogo = "citylogo";
     public static final String ID = "ID";
    public static final String MerchantID = "MerchantID";
    public static final String BranchAR = "BranchAR";
    public static final String lat = "lat";
    public static final String lng = "lng";
    public static final String BranchEN = "BranchEN";
    public static final String Username = "Username";
    public static final String Useremail = "Usersemail";
    public static final String mobile = "mobile";
    public static final String mobileKey = "mobileKey";
    public static final String vCode = "vCode";
    public static final String QR = "QR";
    public static final String Activationcode = "Activationcode";
    public static final String Activationstate = "Activationstate";
    public static final String expiredate = "expiredate";
    public static final String gender = "gender";
    public static final String birthdate = "birthdate";
    public static final String nationalityid = "nationalityid";
    public static final String imageUri = "imageUri";
    public static final String verifiedStatus = "verifiedStatus";
    public static final String nationalitynameen = "nationalitynameen";
    public static final String nationalitynamear = "nationalitynamear";
    public static final String language = "en";
    public static final String IdentityCardPhoto = "IdentityCardPhoto";
    public static final int NONHTTP_EXCEPTION = 1;
    public static final String META_DATA ="meta_data" ;
    public static final int GENERAL_API_EXCEPTION = 669;


    public static       String home_position = "1";

    //search on map
    public static       String sLat = "";
    public static       String sLng = "";
    public static       String dLat = "";
    public static       String dLng = "";
    public static       String status = "";
    public static       String distance = "";
    public static       String time = "0";

    public static       String from = "";
    public static       String fromAr = "";
    public static       String to = "";
    public static       String toAr = "";


    public static       String sLatSearch = "";
    public static       String sLngSearch = "";
    public static       String dLatSearch = "";
    public static       String dLngSearch = "";
    public static       String dateSearch = "";

    public static       String sCity = "";
    public static       String dCity = "";

    public static       String sCityOffer = "";
    public static       String dCityOffer = "";


    public static       String tripIdSearch = "";
    public static       String carStatus = "0";
    public static final String devicetoken="";

    public static final String badgeCount="badgeCount";
    public static       String TripStatus="0";
    public static       String tripIdCaptinAddLocation = "";
    public static       String currentDateAddLocation = "";

    public static       String TripDetailsCarColor = "";

    public static       String tripIdCaptin = "";

    public static       String captin_id = "";

    public static       String passenger_id = "";

    public static       String reservation_id = "";


    public static       String fromCaptin = "";
    public static       String toCaptin = "";
    public static       String startTimeCaptin = "";
    public static       String endTimeCaptin = "";
    public static       String dateCaptin = "";
    public static       String ChatId="" ;
    public static       String PartnerId="" ;
    public static       String ReciverId="" ;
    public static       String ReciverName="";
    public static       String ReciverPhoto="";
    public static       String PartnerIdentityId ="";
    public static       String EndDateCaptin=""  ;
    public static       Intent servvice;
     public static final String ENGLISH = "english";
    public static final String ARABIC = "arabic";
    public static final String LANG = "language";
    public static final String USER_DATA ="user_data" ;
    public static final String TOKEN ="token" ;
    public static final String USER_PHONE ="user_phone" ;
    public static final String VCODE = "vcode";
    public static final String FromSplash="FromSplash";

    public static String parseXML(String response) {
        String json="";
        DocumentBuilder builder = null;
        try {
            builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            InputSource src = new InputSource();
            src.setCharacterStream(new StringReader(response));

            Document doc = builder.parse(src);

            json = doc.getElementsByTagName("string").item(0).getTextContent();
            Log.i("1", json);
            Gson gson = new Gson();
            // Toast.makeText(getApplicationContext(),likeModel.getResultMsg(),Toast.LENGTH_SHORT).show();


        } catch (ParserConfigurationException e) {


            e.printStackTrace();
        } catch (SAXException e) {


            e.printStackTrace();
        } catch (IOException e) {


            e.printStackTrace();
        }
        return json;
    }
    public static void initService(Context context) {
        servvice=new Intent(context, MyService.class)  ;
    }


    public static boolean isRTL(Locale locale) {
        final int directionality = Character.getDirectionality(locale.getDisplayName().charAt(0));
        return directionality == Character.DIRECTIONALITY_RIGHT_TO_LEFT ||
                directionality == Character.DIRECTIONALITY_RIGHT_TO_LEFT_ARABIC;
    }
    public static boolean isLTR(Locale locale) {
        final int directionality = Character.getDirectionality(locale.getDisplayName().charAt(0));
        return directionality == Character.DIRECTIONALITY_LEFT_TO_RIGHT||
                directionality == Character.DIRECTIONALITY_LEFT_TO_RIGHT_EMBEDDING;
    }

    public static AlertDialog.Builder buildDialog(Activity c) {

        /*SharedPreferences mSharedPreferences;
        String Language;

        mSharedPreferences  =   c.getSharedPreferences("tokenDetail",MODE_PRIVATE);
        Language            =   mSharedPreferences.getString(Constant.language,Locale.getDefault().getLanguage());
*/
        AlertDialog.Builder builder = new AlertDialog.Builder(c);

       /* if (Language.equals("ar"))
        {*/

            builder.setTitle(c.getResources().getString(R.string.no_internet));
            builder.setMessage(c.getResources().getString(R.string.check_internet_str));
            builder.setIcon(R.drawable.wifi_ic);
            builder.setCancelable(false);

            builder.setPositiveButton(c.getResources().getString(R.string.reload), (dialog, which) -> {

                //Toast.makeText(c, "please check internet connection", Toast.LENGTH_LONG).show();
                //builder.show();
                c.finish();
                c.startActivities(new Intent[]{c.getIntent()});
            });
                builder.setNegativeButton(c.getResources().getString(R.string.esc), (dialogInterface, i) -> {});

       /* }
        else
        {

            builder.setTitle("No Internet connection.");
            builder.setMessage("please check internet connection");
            builder.setIcon(R.drawable.wifi_ic);
            builder.setCancelable(false);

            builder.setPositiveButton("Reload", (dialog, which) -> {

                //Toast.makeText(c, "please check internet connection", Toast.LENGTH_LONG).show();
                //builder.show();
                c.finish();
                c.startActivities(new Intent[]{c.getIntent()});
            });
            builder.setNegativeButton("Cancel", (dialogInterface, i) -> c.finish());

        }*/


        return builder;
    }
    public static AlertDialog.Builder buildDialogCall(Activity c,String num) {

        /*SharedPreferences mSharedPreferences;
        String Language;

        mSharedPreferences  =   c.getSharedPreferences("tokenDetail",MODE_PRIVATE);
        Language            =   mSharedPreferences.getString(Constant.language,Locale.getDefault().getLanguage());
*/
        android.support.v7.app.AlertDialog.Builder builder;
        android.support.v7.app.AlertDialog dialog;

        builder = new android.support.v7.app.AlertDialog.Builder(c);
        @SuppressLint("InflateParams")
        View mview = c.getLayoutInflater().inflate(R.layout.dialog_call, null);

        builder.setView(mview);
        dialog = builder.create();
        Window window = dialog.getWindow();
        if (window != null) {
            window.setGravity(Gravity.CENTER);
        }
        TextView phone_number=mview.findViewById(R.id.num);
        TextView cancel=mview.findViewById(R.id.cancel);
        TextView call=mview.findViewById(R.id.call);
        phone_number.setText(num);
        call.setOnClickListener(v -> {

        });
        cancel.setOnClickListener(v -> {
            //dialog.dismiss();
            //Toast.makeText(c, "ddd", Toast.LENGTH_SHORT).show();
        });


        return builder;
    }






}
