package com.NativeTech.rehla.adapters;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.NativeTech.rehla.activities.Home;
import com.NativeTech.rehla.activities.Spalsh;
import com.NativeTech.rehla.model.DataManager;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.List;
import java.util.Objects;

import com.NativeTech.rehla.model.data.dto.Models.Driver.updateTripStatusResponse;
import com.NativeTech.rehla.Network.NetworkUtil;
import com.NativeTech.rehla.R;
import com.NativeTech.rehla.Utills.Constant;
import com.NativeTech.rehla.Utills.TerhalUtils;
import com.NativeTech.rehla.Utills.Validation;
import com.NativeTech.rehla.activities.AcceptOrRejectTrip;
import com.NativeTech.rehla.activities.Login;
import com.NativeTech.rehla.activities.TripDetail;

import retrofit2.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static android.content.Context.MODE_PRIVATE;
import static com.NativeTech.rehla.Utills.Constant.buildDialog;
import static com.NativeTech.rehla.Utills.Constant.servvice;
import static com.NativeTech.rehla.services.MyService.MY_PERMISSIONS_REQUEST_LOCATION;


public class RecyclerViewAdapterCaptinTrips extends RecyclerView.Adapter<RecyclerViewAdapterCaptinTrips.ViewHolder> implements  ActivityCompat.OnRequestPermissionsResultCallback{

    private final List<CaptinTripsRecyclerModel> rowItem;
    private final Context mContext;

    private String Language;
    private CompositeSubscription mSubscriptions;
    private SharedPreferences mSharedPreferences;
    private KProgressHUD hud;
    Home home;

    private String id1;
    public RecyclerViewAdapterCaptinTrips(Context context,Home home, List<CaptinTripsRecyclerModel> rowItem ) {
        this.rowItem=rowItem;
        this.mContext = context;
        this.home=home;
        mSharedPreferences  =   mContext.getSharedPreferences("tokenDetail",MODE_PRIVATE);
        Language            =   mSharedPreferences.getString(Constant.language,"en");
        mSubscriptions      =   new CompositeSubscription();
        hud = KProgressHUD.create(mContext)
                .setStyle(KProgressHUD.Style.PIE_DETERMINATE)
                .setAnimationSpeed(2)
                .setCancellable(false)
                .setDimAmount(0.5f)
                .setMaxProgress(100);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.captin_trip_item, parent, false);

        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        try {
            holder.from.setText(rowItem.get(position).getFrom());
            holder.to.setText(rowItem.get(position).getTo());
            //String defaultTimezone = TimeZone.getDefault().getID();
            //parseDateTo_yyyyMMdd(string);
            if (rowItem.get(position).getDate() != null) {
                holder.date.setText(TerhalUtils.parseDateTo_yyyyMMdd(rowItem.get(position).getDate()));
            }
            holder.time.setText(TerhalUtils.getTimeWithCurrentZone(
                    TerhalUtils.parseDateTo_yyyyMMdd(rowItem.get(position).getDate()) + " " + rowItem.get(position).getTime()));
            holder.distance.setText(rowItem.get(position).getDistance());
            holder.available_seat.setText(rowItem.get(position).getAvailable());

            switch (rowItem.get(position).getTripStatusId()) {
                case "1":
                    holder.start.setVisibility(View.VISIBLE);
                    holder.btn_delete.setVisibility(View.VISIBLE);
                    holder.end.setVisibility(View.GONE);
                    if (Language.equals("ar")) {
                        holder.statusRide.setText("قيد الانتظار");
                    } else {
                        holder.statusRide.setText("Pending");
                    }
                    holder.statusRide.setBackground(mContext.getResources().getDrawable(R.drawable.button_rate));
                    break;
                case "2":
                    holder.start.setVisibility(View.GONE);
                    holder.btn_delete.setVisibility(View.GONE);
                    holder.space.setVisibility(View.GONE);
                    holder.end.setVisibility(View.VISIBLE);
                    boolean flag = true;
                    if (Language.equals("ar")) {
                        holder.statusRide.setText("بدأت");
                    } else {
                        holder.statusRide.setText("Started");
                    }
                    break;
                case "3":
                    if (Language.equals("ar")) {
                        holder.statusRide.setText("انتهت");
                    } else {
                        holder.statusRide.setText("Ended");
                    }
                    break;
                case "4":
                    if (Language.equals("ar")) {
                        holder.statusRide.setText("الغيت");
                    } else {
                        holder.statusRide.setText("Canceled");
                    }
                    holder.statusRide.setBackground(mContext.getResources().getDrawable(R.drawable.ride_status_red));
                    break;
                case "5":
                    if (Language.equals("ar")) {
                        holder.statusRide.setText("انتهت");
                    } else {
                        holder.statusRide.setText("Finished");
                    }
                    break;
            }
        /*if (flag)
        {
            TripStatus="1";
            tripIdCaptinAddLocation=rowItem.get(position).getId();
            //checkLocationPermission();
            mContext.startService(new Intent(mContext, MyService.class));
        }
*/
            //checkLocationPermission();

            holder.start.setOnClickListener(v -> updateTripStatus(rowItem.get(position).getId(), "2"));
            holder.end.setOnClickListener(v -> updateTripStatus(rowItem.get(position).getId(), "3"));
            holder.btn_delete.setOnClickListener(v -> updateTripStatus(rowItem.get(position).getId(), "4"));

/*


        holder.itemView.setOnClickListener(v -> {


            Intent intent = new Intent(mContext, AcceptOrRejectTrip.class);
            Constant.tripIdCaptin=rowItem.get(position).getId();
            Constant.fromCaptin=rowItem.get(position).getFrom();
            Constant.toCaptin=rowItem.get(position).getTo();
            Constant.startTimeCaptin=rowItem.get(position).getTime();
            Constant.endTimeCaptin=rowItem.get(position).getEndTime();
            Constant.dateCaptin=rowItem.get(position).getDate();
            mContext.startActivity(intent);
        });
*/

            holder.btn_request.setOnClickListener(v -> {
                Intent intent = new Intent(mContext, AcceptOrRejectTrip.class);
                Constant.tripIdCaptin = rowItem.get(position).getId();
                Constant.fromCaptin = rowItem.get(position).getFrom();
                Constant.toCaptin = rowItem.get(position).getTo();
                Constant.startTimeCaptin = rowItem.get(position).getTime();
                Constant.endTimeCaptin = rowItem.get(position).getEndTime();
                Constant.dateCaptin = rowItem.get(position).getDate();
                mContext.startActivity(intent);
            });

            holder.details.setOnClickListener(v -> {
                Intent intent = new Intent(mContext, TripDetail.class);
                Constant.tripIdSearch = rowItem.get(position).getId();
                mContext.startActivity(intent);
            });
        }catch (Exception e){}
    }

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(Objects.requireNonNull(mContext), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions((Activity) mContext,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION );

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NotNull String[] permissions, @NotNull int[] grantResults) {
        // If request is cancelled, the result arrays are empty.
        if (requestCode == MY_PERMISSIONS_REQUEST_LOCATION) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                // permission was granted, yay! Do the
                // location-related task you need to do.
                if (ContextCompat.checkSelfPermission(mContext,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    //client.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
                    // mGoogleMap.setMyLocationEnabled(true);
                    //dialog.dismiss();
                    mContext.startService(servvice);

                } else {
                    checkLocationPermission();
                    //Toast.makeText(home, "ddddsa", Toast.LENGTH_SHORT).show();
                    //getActivity().startService(new Intent(getActivity(), MyService.class));
                }

            } else {

                // permission denied, boo! Disable the
                // functionality that depends on this permission.
                checkLocationPermission();
                //Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }


    private void updateTripStatus(String id, String s)
    {
        id1=id;
        mSharedPreferences  =   mContext.getSharedPreferences("tokenDetail",MODE_PRIVATE);
        Language            =   mSharedPreferences.getString(Constant.language,"en");
        mSubscriptions      =   new CompositeSubscription();
        hud = KProgressHUD.create(mContext)
                .setStyle(KProgressHUD.Style.PIE_DETERMINATE)
                .setAnimationSpeed(2)
                .setCancellable(false)
                .setDimAmount(0.5f)
                .setMaxProgress(100);

        if (id.equals("2"))
        {
            checkLocationPermission();
        }

        if (Validation.isConnected(mContext))
        {
           hud.show();
            mSubscriptions.add(NetworkUtil.getRetrofitByToken(DataManager.getInstance().getCashedAccessToken().getAccess_token())
                    .updateTripStatus(id,s)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResponse, this::handleError));
        }
        else {
            buildDialog((Activity) mContext).show().setCanceledOnTouchOutside(false);
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
                        message = mContext.getString(R.string.error_1);
                        break;
                    case "2":
                        message = mContext.getString(R.string.error_2);
                        break;
                    case "3":
                        message = mContext.getString(R.string.error_3);
                        break;
                    case "4":
                        message = mContext.getString(R.string.error_4);
                        break;
                    case "5":
                        message = mContext.getString(R.string.error_5);
                        break;
                    case "6":
                        message = mContext.getString(R.string.error_6);
                        break;
                    case "7":
                        message = mContext.getString(R.string.error_7);
                        break;
                    case "8":
                        message = mContext.getString(R.string.error_8);
                        break;
                    case "9":
                        message = mContext.getString(R.string.error_9);
                        break;
                    case "10":
                        message = mContext.getString(R.string.error_10);
                        break;
                    case "11":
                        message = mContext.getString(R.string.error_11);
                        break;
                    case "12":
                        message = mContext.getString(R.string.error_12);
                        break;
                    case "13":
                        message = mContext.getString(R.string.error_13);
                        break;
                    case "14":
                        message = mContext.getString(R.string.error_14);
                        break;
                    case "15":
                        message = mContext.getString(R.string.error_15);
                        break;
                    case "16":
                        message = mContext.getString(R.string.error_16);
                        break;
                    case "17":
                        message = mContext.getString(R.string.error_17);
                        break;
                    case "18":
                        message = mContext.getString(R.string.error_18);
                        break;
                    case "19":
                        message = mContext.getString(R.string.error_19);
                        break;
                    case "20":
                        message = mContext.getString(R.string.error_20);
                        break;
                    case "21":
                        message = mContext.getString(R.string.error_21);
                        break;
                    case "22":
                        message = mContext.getString(R.string.error_22);
                        break;
                    case "23":
                        message = mContext.getString(R.string.error_23);
                        break;
                    case "24":
                        message = mContext.getString(R.string.error_24);
                        break;
                    case "25":
                        message = mContext.getString(R.string.error_25);
                        break;
                    case "26":
                        message = mContext.getString(R.string.error_26);
                        break;
                    case "27":
                        message = "Wasl Error";
                        break;
                    case "28":
                        message = "Wasl Error";
                        break;
                    case "29":
                        message = mContext.getString(R.string.error_29);
                        break;
                    case "30":
                        message = mContext.getString(R.string.error_30);
                        break;
                    case "31":
                        message = mContext.getString(R.string.error_31);
                        break;

                    default:
                        message = mContext.getString(R.string.default_message);
                }
            }
            else
            {
                //Toast.makeText(AcceptOrRejectTrip.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
               // message=throwable.getMessage();
                message = mContext.getString(R.string.default_message);

            }

        } catch (Exception ex) {
            //view.onError(e.getMessage());
            message = mContext.getString(R.string.default_message);
        }
        String error="failed to connect".toLowerCase();
        if (message.toLowerCase().contains(error))
        {
            message=mContext.getString(R.string.check_internet);
        }
        android.support.v7.app.AlertDialog.Builder builder;
        android.support.v7.app.AlertDialog dialog;

        builder = new android.support.v7.app.AlertDialog.Builder(mContext);
        @SuppressLint("InflateParams")
        View mview = ((Activity)mContext).getLayoutInflater().inflate(R.layout.dialog_error, null);

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
        ok.setOnClickListener(v2 -> {
            dialog.dismiss();
            Constant.home_position="0";
            Intent intent = new Intent(mContext, Login.class);
            intent.putExtra("f",true);
             //intent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK |  Intent.FLAG_ACTIVITY_CLEAR_TOP);
            mContext.startActivity(intent);
            ((Activity)mContext).finish();
        });
        hud.dismiss();
    }

    private void handleResponse(updateTripStatusResponse updateTripStatusResponse)
    {
        if (updateTripStatusResponse.getErrors().getMessage()==null)
        {
            if (id1.equals("3"))
            {
                Objects.requireNonNull(mContext).stopService(servvice);
            }


            Constant.home_position="0";
            Intent intent = new Intent(mContext, Login.class);
            intent.putExtra("f",true);
            //intent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK |  Intent.FLAG_ACTIVITY_CLEAR_);
            mContext.startActivity(intent);
            ((Activity)mContext).finish();
        }
        hud.dismiss();
    }
    @Override
    public int getItemCount() {
        return rowItem.size();
    }

    private void setImg(String url, ImageView image)
    {

        if(url.equals(""))
        {
            image.setImageResource(R.drawable.ic_user);
        }
        else {
            try {

                Picasso.with(mContext)
                        .load(url)
                        .placeholder(R.drawable.ic_user)
                        .into(image);
            } catch (Exception ignored) {

            }
        }
    }
    class ViewHolder extends RecyclerView.ViewHolder{

        final TextView            from;
        final TextView            to;
        final TextView            date;
        final TextView            time;
        final TextView            distance;
        final TextView            available_seat;
        final TextView              start;
        final TextView              btn_delete;
        final TextView              end;
        final TextView              btn_request;
        final TextView              details;
        final LinearLayoutCompat  space;

        final TextView statusRide;

        ViewHolder(View itemView) {
            super(itemView);
            from                        =itemView.findViewById(R.id.from);
            to                          =itemView.findViewById(R.id.to);
            date                        =itemView.findViewById(R.id.date);
            time                        =itemView.findViewById(R.id.time);
            distance                    =itemView.findViewById(R.id.distance);
            available_seat              =itemView.findViewById(R.id.available_seat);
            start                       =itemView.findViewById(R.id.start);
            btn_delete                  =itemView.findViewById(R.id.btn_delete);
            end                         =itemView.findViewById(R.id.end);
            btn_request                 =itemView.findViewById(R.id.btn_request);
            statusRide                  =itemView.findViewById(R.id.statusRide);
            details                     =itemView.findViewById(R.id.details);
            space                       =itemView.findViewById(R.id.space);



        }
    }


}





