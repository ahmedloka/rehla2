package com.NativeTech.rehla.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.NativeTech.rehla.R;
import com.NativeTech.rehla.Utills.Constant;
import com.NativeTech.rehla.Utills.TerhalUtils;
import com.NativeTech.rehla.activities.RateADriver;
import de.hdodenhof.circleimageview.CircleImageView;
import rx.subscriptions.CompositeSubscription;

import static android.content.Context.MODE_PRIVATE;


public class RecyclerViewAdapterMyOfferedHistoryRide extends RecyclerView.Adapter<RecyclerViewAdapterMyOfferedHistoryRide.ViewHolder> {

    private final List<MyOfferedHistoryRideItemRecyclerModel> rowItem;
    private final Context mContext;

    private final String Language;
    private final SharedPreferences mSharedPreferences;
    private final KProgressHUD hud;

    public RecyclerViewAdapterMyOfferedHistoryRide(Context context, List<MyOfferedHistoryRideItemRecyclerModel> rowItem ) {
        this.rowItem=rowItem;
        this.mContext = context;
        mSharedPreferences  =   mContext.getSharedPreferences("tokenDetail",MODE_PRIVATE);
        Language            =   mSharedPreferences.getString(Constant.language, Locale.getDefault().getLanguage());
        CompositeSubscription mSubscriptions = new CompositeSubscription();
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_rides_item_history, parent, false);

        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {


        setImg(rowItem.get(position).getImg(),holder.img);
        holder.name.setText(rowItem.get(position).getName());
        try {
            Double totalPrice=Double.parseDouble(rowItem.get(position).getPrice())*Double.parseDouble(rowItem.get(position).getSeat_num());
            holder.price.setText(String.valueOf(totalPrice));
        }
        catch (Exception e)
        {
            Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        holder.car_type.setText(rowItem.get(position).getCar_type());
        holder.car_color.setText(rowItem.get(position).getCar_color());
        holder.seat_num.setText(rowItem.get(position).getSeat_num());
        holder.rate.setRating(Float.parseFloat(rowItem.get(position).getRate()));
        if (Language.equals("ar"))
        {
            switch (rowItem.get(position).getStatusRide()) {
                case "3":
                    holder.statusRide.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.ride_status_red));
                    holder.statusRide.setText("تم الرفض");
                    holder.canceled_ride.setVisibility(View.GONE);
                    holder.not_rated.setVisibility(View.GONE);

                    break;
                case "4":
                    holder.statusRide.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.ride_status_red));
                    holder.statusRide.setText("الغيت");
                    holder.canceled_ride.setVisibility(View.GONE);
                    holder.not_rated.setVisibility(View.GONE);

                    break;
                case "5":
                    holder.statusRide.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.ride_status_green));
                    holder.statusRide.setText("اكملت");
                    holder.canceled_ride.setVisibility(View.GONE);

                    if (rowItem.get(position).getRateStatus().equals("y"))
                    {
                        holder.not_rated.setVisibility(View.GONE);

                    }
                    else if (rowItem.get(position).getRateStatus().equals("n"))
                    {
                        holder.not_rated.setVisibility(View.VISIBLE);
                    }


                    break;
                case "6":
                    holder.statusRide.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.ride_status_green));
                    holder.statusRide.setText("تم التقييم");
                    holder.canceled_ride.setVisibility(View.GONE);


                    break;
            }
        }
        else
        {
            switch (rowItem.get(position).getStatusRide()) {
                case "3":
                    holder.statusRide.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.ride_status_red));
                    holder.statusRide.setText("Rejected");
                    holder.canceled_ride.setVisibility(View.GONE);

                    break;
                case "4":
                    holder.statusRide.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.ride_status_red));
                    holder.statusRide.setText("Canceled");
                    holder.canceled_ride.setVisibility(View.GONE);
                    break;
                case "5":
                    holder.statusRide.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.ride_status_green));
                    holder.statusRide.setText("Finished");
                    holder.canceled_ride.setVisibility(View.GONE);
                    if (rowItem.get(position).getRateStatus().equals("y"))
                    {
                        holder.not_rated.setVisibility(View.GONE);
                    }
                    else if (rowItem.get(position).getRateStatus().equals("n"))
                    {
                        holder.not_rated.setVisibility(View.VISIBLE);
                    }

                    break;
                case "6":
                    holder.statusRide.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.ride_status_green));
                    holder.statusRide.setText("Rated");
                    holder.canceled_ride.setVisibility(View.GONE);
                    break;
            }
        }


        String startT= TerhalUtils.getTimeWithCurrentZone(
                TerhalUtils.parseDateTo_yyyyMMdd(rowItem.get(position).getDate())+" "+
                        rowItem.get(position).getFrom_date() );
        String endT= TerhalUtils.getTimeWithCurrentZone(
                TerhalUtils.parseDateTo_yyyyMMdd(rowItem.get(position).getEndDate())+" "+
                        rowItem.get(position).getTo_date());




        holder.from_date.setText(startT);
        holder.from_address.setText(rowItem.get(position).getFrom_address());
        holder.to_date.setText(endT);
        holder.to_address.setText(rowItem.get(position).getTo_address());

        holder.date.setText(TerhalUtils.parseDateTo_yyyyMMdd(rowItem.get(position).getDate()));
       // holder.date.setText(rowItem.get(position).getDate());
        holder.expected_time.setText(rowItem.get(position).getExpected_time());

        holder.btn_rate.setOnClickListener(v -> {
            //Toast.makeText(mContext, "rate here", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(mContext, RateADriver.class);
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
        final TextView price;
        final TextView car_type;
        final TextView car_color;
        final TextView seat_num;
        final TextView statusRide;
        final TextView from_date;
        final TextView from_address;
        final TextView to_date;
        final TextView to_address;
        final TextView date;
        final TextView expected_time;
        final TextView reasons;
        final RatingBar rate;
        final Button btn_rate;
        final LinearLayoutCompat canceled_ride;
        final LinearLayoutCompat not_rated;
        final LinearLayoutCompat rated;
        final LinearLayoutCompat expected_time_lin;

        ViewHolder(View itemView) {
            super(itemView);
            img            =itemView.findViewById(R.id.img);
            name                    =itemView.findViewById(R.id.name);
            price            =itemView.findViewById(R.id.price);
            car_type           =itemView.findViewById(R.id.car_type);
            car_color                =itemView.findViewById(R.id.car_color);
            seat_num                 =itemView.findViewById(R.id.seat_num);
            statusRide                 =itemView.findViewById(R.id.statusRide);
            from_date                 =itemView.findViewById(R.id.from_date);
            from_address                 =itemView.findViewById(R.id.from_address);
            to_date                 =itemView.findViewById(R.id.to_date);
            to_address                 =itemView.findViewById(R.id.to_address);
            date                    =itemView.findViewById(R.id.date);
            expected_time                 =itemView.findViewById(R.id.expected_time);
            reasons                 =itemView.findViewById(R.id.reasons);
            rate                    =itemView.findViewById(R.id.rate1);
            btn_rate                    =itemView.findViewById(R.id.btn_rate);
            canceled_ride                    =itemView.findViewById(R.id.canceled_ride);
            not_rated                    =itemView.findViewById(R.id.not_rated);
            rated                    =itemView.findViewById(R.id.rated);
            expected_time_lin                    =itemView.findViewById(R.id.expected_time_lin);

        }
    }


}





