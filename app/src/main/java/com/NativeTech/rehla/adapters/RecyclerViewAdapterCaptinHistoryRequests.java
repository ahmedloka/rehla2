package com.NativeTech.rehla.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.NativeTech.rehla.activities.Home;
import com.NativeTech.rehla.model.DataManager;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import com.NativeTech.rehla.model.data.dto.Models.Driver.updateTripStatusResponse;
import com.NativeTech.rehla.Network.NetworkUtil;
import com.NativeTech.rehla.R;
import com.NativeTech.rehla.Utills.Constant;
import com.NativeTech.rehla.Utills.TerhalUtils;
import com.NativeTech.rehla.Utills.Validation;
import com.NativeTech.rehla.activities.ChatDetails;
import com.NativeTech.rehla.activities.Login;
import com.NativeTech.rehla.activities.PassengerDetails;
import com.NativeTech.rehla.activities.RateAPassenger;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static android.content.Context.MODE_PRIVATE;
import static com.NativeTech.rehla.Utills.Constant.buildDialog;


public class RecyclerViewAdapterCaptinHistoryRequests extends RecyclerView.Adapter<RecyclerViewAdapterCaptinHistoryRequests.ViewHolder> {

    private final List<CaptinHistoryRequestsRecyclerModel> rowItem;
    private final Context mContext;

    private final CompositeSubscription mSubscriptions;
    private final SharedPreferences mSharedPreferences;
    private final KProgressHUD hud;

    public RecyclerViewAdapterCaptinHistoryRequests(Context context, List<CaptinHistoryRequestsRecyclerModel> rowItem ) {
        this.rowItem=rowItem;
        this.mContext = context;
        mSharedPreferences  =   mContext.getSharedPreferences("tokenDetail",MODE_PRIVATE);
        String language = mSharedPreferences.getString(Constant.language, Locale.getDefault().getLanguage());
        mSubscriptions      =   new CompositeSubscription();
        hud = KProgressHUD.create(mContext)
                .setStyle(KProgressHUD.Style.PIE_DETERMINATE)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .setCancellable(false)
                .setMaxProgress(100);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.captin_history_trip_item_requests, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        setImg(rowItem.get(position).getImg(),holder.img);
        holder.name.setText(rowItem.get(position).getName());
        holder.reviews_count.setText(rowItem.get(position).getReviews_count());
        holder.rate.setRating(Float.parseFloat(rowItem.get(position).getRate()));
        holder.seat_number.setText(rowItem.get(position).getSeat_number());

        String startT= TerhalUtils.getTimeWithCurrentZone(
                TerhalUtils.parseDateTo_yyyyMMdd(Constant.dateCaptin)+" "+
                        Constant.startTimeCaptin  );
        String endT= TerhalUtils.getTimeWithCurrentZone(
                TerhalUtils.parseDateTo_yyyyMMdd(Constant.EndDateCaptin)+" "+
                        Constant.endTimeCaptin);



        holder.from_date.setText(startT);
        holder.from_address.setText(Constant.fromCaptin);
        holder.to_date.setText(endT);
        holder.to_address.setText(Constant.toCaptin);

        holder.date.setText(TerhalUtils.parseDateTo_yyyyMMdd(Constant.EndDateCaptin));
       // holder.date.setText(rowItem.get(position).getDate());
        holder.call.setVisibility(View.GONE);
        holder.chat.setVisibility(View.GONE);
        if (Integer.parseInt(rowItem.get(position).getTrip_status())>3 && rowItem.get(position).getRatedByDriver().equals("false"))
        {
            holder.btn_rate.setVisibility(View.VISIBLE);
        }


        if (rowItem.get(position).getPayment_type().equals("true"))
        {
            holder.cash.setText(mContext.getResources().getString(R.string.cash));
        }
        else
        {
            holder.cash.setText(mContext.getResources().getString(R.string.wallet));
        }

        if (rowItem.get(position).getRatedByDriver().equals("false")&& rowItem.get(position).getTrip_status().equals("5"))
        {
            holder.btn_rate.setVisibility(View.VISIBLE);
        }
        else
        {
            holder.btn_rate.setVisibility(View.GONE);
            holder.call.setVisibility(View.VISIBLE);
            holder.chat.setVisibility(View.VISIBLE);
        }
        holder.call.setOnClickListener(v ->
        {
            android.support.v7.app.AlertDialog.Builder builder;
            android.support.v7.app.AlertDialog dialog;

            builder = new android.support.v7.app.AlertDialog.Builder(mContext);
            @SuppressLint("InflateParams")
            View mview = ((Activity) mContext).getLayoutInflater().inflate(R.layout.dialog_call, null);

            builder.setView(mview);
            dialog = builder.create();
            Window window = dialog.getWindow();
            if (window != null) {
                window.setGravity(Gravity.CENTER);
            }
            Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            dialog.show();
            TextView phone_number = mview.findViewById(R.id.num);
            TextView cancel = mview.findViewById(R.id.cancel);
            TextView call = mview.findViewById(R.id.call);
            phone_number.setText(rowItem.get(position).getPhone_num());
            call.setOnClickListener(v2 -> {
                Intent intent1 = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + rowItem.get(position).getPhone_num()));
                mContext.startActivity(intent1);
            });
            cancel.setOnClickListener(v3-> {
                dialog.dismiss();
                //Toast.makeText(c, "ddd", Toast.LENGTH_SHORT).show();
            });
        });


        holder.chat.setOnClickListener(view -> {
            Intent intent = new Intent(mContext, ChatDetails.class);
           /* if (ID.equals(rowItem.get(position).getSenderId()))
            {
                Constant.PartnerId=rowItem.get(position).getReciverId();
            }
            else {
                Constant.PartnerId=rowItem.get(position).getSenderId();
            }*/

            hud.show();
            Constant.PartnerId=rowItem.get(position).getPassenger_id();
            Constant.PartnerIdentityId=rowItem.get(position).getPassengerIdentityId();
            Constant.ReciverId=rowItem.get(position).getPassenger_id();
            Constant.ReciverName=rowItem.get(position).getPassengerName();
            Constant.ReciverPhoto=rowItem.get(position).getPassengerProfilePhoto();
            new Handler().postDelayed(() -> {
                mContext.startActivity(intent);
                hud.dismiss();

            },500);
        });

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, PassengerDetails.class);
            Constant.passenger_id=rowItem.get(position).getPassenger_id();
            mContext.startActivity(intent);
        });

        holder.btn_rate.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, RateAPassenger.class);
            Constant.reservation_id=rowItem.get(position).getId();
            mContext.startActivity(intent);
        });



    }
    private String parseDateTo_yyyyMMdd(String time) {
        String inputPattern = "yyyy-MM-dd'T'HH:mm:ss";
        String outputPattern = "yyyy-MM-dd";
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date;
        String str = "";

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return str;
    }

    private void updateTripStatus(String id, String s)
    {
        if (Validation.isConnected(mContext))
        {
            hud.show();
            try {
                mSubscriptions.add(NetworkUtil.getRetrofitByToken(DataManager.getInstance().getCashedAccessToken().getAccess_token())
                        .AcceptOrRejectReservation(id,s)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(this::handleResponse, this::handleError));
            }
            catch (Exception e)
            {
                Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        else {
            buildDialog((Activity) mContext).show().setCanceledOnTouchOutside(false);
        }
    }

    private void handleResponse(updateTripStatusResponse updateTripStatusResponse) {
        if (updateTripStatusResponse.getErrors().getMessage() == null)
        {
            Intent intent = new Intent(mContext, Login.class);
            intent.putExtra("f",true);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            mContext.startActivity(intent);
            ((Activity)mContext).finish();
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
            message = mContext.getString(R.string.default_message);        }
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




        final CircleImageView img;
        final TextView name;
        final TextView reviews_count;
        final RatingBar rate;
        final TextView cash;
        final TextView from_date;
        final TextView from_address;
        final TextView to_date;
        final TextView to_address;
        final TextView date;
        final TextView seat_number;

        final AppCompatImageView call;
        final AppCompatImageView chat;

        final Button btn_rate;



        ViewHolder(View itemView) {
            super(itemView);
            img                     =itemView.findViewById(R.id.img);
            name                    =itemView.findViewById(R.id.name);
            reviews_count           =itemView.findViewById(R.id.reviews_count);
            rate                    =itemView.findViewById(R.id.rate);
            cash                    =itemView.findViewById(R.id.cash);
            seat_number             =itemView.findViewById(R.id.seat_number);
            from_date               =itemView.findViewById(R.id.from_date);
            from_address            =itemView.findViewById(R.id.from_address);
            to_date                 =itemView.findViewById(R.id.to_date);
            to_address              =itemView.findViewById(R.id.to_address);
            date                    =itemView.findViewById(R.id.date);
            call                    =itemView.findViewById(R.id.call);
            chat                    =itemView.findViewById(R.id.chat);
            btn_rate                =itemView.findViewById(R.id.btn_rate);
            //reject                  =itemView.findViewById(R.id.reject);

        }
    }


}





