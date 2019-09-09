package com.NativeTech.rehla.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.NativeTech.rehla.Utills.MyTextViewBold;
import com.NativeTech.rehla.model.DataManager;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import com.NativeTech.rehla.model.data.dto.Models.WaitingTrips.addWaitingTripsResponse;
import com.NativeTech.rehla.Network.NetworkUtil;
import com.NativeTech.rehla.R;
import com.NativeTech.rehla.Utills.Constant;
import com.NativeTech.rehla.Utills.TerhalUtils;
import com.NativeTech.rehla.Utills.Validation;
import com.NativeTech.rehla.activities.FindRide;
import com.NativeTech.rehla.activities.WaitingTrips;

import retrofit2.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static android.content.Context.MODE_PRIVATE;
import static com.NativeTech.rehla.Utills.Constant.buildDialog;


public class RecyclerViewAdapterwaitingTrips extends RecyclerView.Adapter<RecyclerViewAdapterwaitingTrips.ViewHolder> {

    private final List<WaitingTripsRecyclerModel> rowItem;
    private final Context mContext;


    private final String Language;
    private final CompositeSubscription mSubscriptions;
    private final SharedPreferences mSharedPreferences;
    private final KProgressHUD hud;



    public RecyclerViewAdapterwaitingTrips(Context context, List<WaitingTripsRecyclerModel> rowItem ) {
        this.rowItem=rowItem;
        this.mContext = context;


        mSharedPreferences  =   context.getSharedPreferences("tokenDetail",MODE_PRIVATE);
        Language            =   mSharedPreferences.getString(Constant.language,"en");
        mSubscriptions      =   new CompositeSubscription();
        hud = KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.PIE_DETERMINATE)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .setCancellable(false)
                .setMaxProgress(100);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.waiting_trip_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") final int position) {


        holder.from.setText(rowItem.get(position).getFrom());
        holder.to.setText(rowItem.get(position).getTo());
        holder.date.setText(TerhalUtils.parseDateTo_yyyyMMdd(rowItem.get(position).getDate()));

        String startT= TerhalUtils.getTimeWithCurrentZone(
                TerhalUtils.parseDateTo_yyyyMMdd(rowItem.get(position).getDate())+" "+
                        rowItem.get(position).getTime() );


        holder.time.setText(startT);

        holder.btn_delete.setOnClickListener(v -> DeleteWaitingTrip(position));
        holder.btn_search.setOnClickListener(v -> {
            //DeleteWaitingTrip(position);
            Constant.sCity=rowItem.get(position).getFrom();
            Constant.dCity=rowItem.get(position).getTo();
            Intent intent = new Intent(mContext,FindRide.class);
            mContext.startActivity(intent);
        });




    }
    private void DeleteWaitingTrip(int position)
    {
        if (Validation.isConnected(mContext))
        {
                /*Toast.makeText(this,Constant.sCity, Toast.LENGTH_SHORT).show();
                Toast.makeText(this, Constant.dCity, Toast.LENGTH_SHORT).show();*/
            //Toast.makeText(this, Constant.dateSearch, Toast.LENGTH_SHORT).show();
            hud.show();
            mSubscriptions.add(NetworkUtil.getRetrofitByToken(DataManager.getInstance().getCashedAccessToken().getAccess_token())
                    .DeleteWaitingTrip(rowItem.get(position).getId())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResponse, this::handleError));
        }
        else {
            buildDialog((Activity) mContext).show().setCanceledOnTouchOutside(false);
        }
    }


    private void handleResponse(addWaitingTripsResponse addWaitingTripsResponse) {
        if (addWaitingTripsResponse.getErrors()!=null)
        {
            if (addWaitingTripsResponse.getErrors().getMessage()!=null)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setMessage(addWaitingTripsResponse.getErrors().getMessage());
                builder.setIcon(R.drawable.ic_error);
                if (Language.equals("en"))
                {
                    builder.setNegativeButton("Cancel", (dialogInterface, i) -> {
                    });
                }
                else
                {
                    builder.setNegativeButton("إلغاء", (dialogInterface, i) -> {
                    });
                }
                builder.show();
            }
            else
            {
                Intent intent = new Intent(mContext, WaitingTrips.class);
                //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                mContext.startActivity(intent);
                ((Activity)mContext).overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                ((Activity)mContext).finish();
            }
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
                message = mContext.getString(R.string.default_message);            }

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
        dialog.show();
        TextView txt = mview.findViewById(R.id.txt);
        TextView ok = mview.findViewById(R.id.ok);
        txt.setText(message);
        ok.setOnClickListener(v2 -> dialog.dismiss());
        hud.dismiss();
    }

    private String parseDateTo_yyyyMMdd(String time) {
        String inputPattern = "yyyy-MM-dd'T'HH:mm:ss";
        String outputPattern = "yyyy-MM-dd";
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = "";

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return str;
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
        final MyTextViewBold              btn_delete;
        final MyTextViewBold btn_search;



        ViewHolder(View itemView) {
            super(itemView);
            from                        =itemView.findViewById(R.id.from);
            to                          =itemView.findViewById(R.id.to);
            date                        =itemView.findViewById(R.id.date);
            time                        =itemView.findViewById(R.id.time);
            btn_delete                  =itemView.findViewById(R.id.btn_delete);
            btn_search                  =itemView.findViewById(R.id.btn_search);



        }
    }


}





