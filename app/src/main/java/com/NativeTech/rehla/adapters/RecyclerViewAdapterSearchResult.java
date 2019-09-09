package com.NativeTech.rehla.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.NativeTech.rehla.R;
import com.NativeTech.rehla.Utills.Constant;
import com.NativeTech.rehla.Utills.TerhalUtils;
import com.NativeTech.rehla.activities.TripDetails;
import de.hdodenhof.circleimageview.CircleImageView;


public class RecyclerViewAdapterSearchResult extends RecyclerView.Adapter<RecyclerViewAdapterSearchResult.ViewHolder> {

    private final List<SearchResultRecyclerModel> rowItem;
    private final Context mContext;

    public RecyclerViewAdapterSearchResult(Context context, List<SearchResultRecyclerModel> rowItem ) {
        this.rowItem=rowItem;
        this.mContext = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_result_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {


        setImg(rowItem.get(position).getImg(),holder.img);
        holder.name.setText(rowItem.get(position).getName());
        holder.price.setText(String.valueOf(round(Double.parseDouble(rowItem.get(position).getPrice()),2)));
        holder.car_type.setText(rowItem.get(position).getCar_type());
        holder.car_color.setText(rowItem.get(position).getCar_color());
        holder.seat_count.setText(rowItem.get(position).getSeat_num());
        holder.count_ride.setText(rowItem.get(position).getCount_ride());
        holder.count_reviews.setText(rowItem.get(position).getCount_reviews());


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
        holder.price_all.setText(String.valueOf(round(Double.parseDouble(rowItem.get(position).getPrice()),2)));
        holder.num.setText(rowItem.get(position).getSeat_num());

        holder.rate.setRating(Float.parseFloat(rowItem.get(position).getRate()));
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(mContext,TripDetails.class);
            Constant.tripIdSearch=rowItem.get(position).getId();
            Constant.TripDetailsCarColor=rowItem.get(position).getCar_color();
            mContext.startActivity(intent);
        });

        holder.btn_request.setOnClickListener(v -> {
            Intent intent = new Intent(mContext,TripDetails.class);
            Constant.tripIdSearch=rowItem.get(position).getId();
            Constant.TripDetailsCarColor=rowItem.get(position).getCar_color();
            mContext.startActivity(intent);
        });



    }
    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
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

        final CircleImageView img;
        final TextView name;
        final TextView price;
        final TextView car_type;
        final TextView car_color;
        final TextView seat_count;
        final TextView count_ride;
        final TextView count_reviews;
        final TextView from_date;
        final TextView from_address;
        final TextView to_date;
        final TextView to_address;
        final TextView date;
        final TextView price_all;
        final TextView num;
        final Button   btn_request;
        final RatingBar rate;



        ViewHolder(View itemView) {
            super(itemView);
            img                     =itemView.findViewById(R.id.img);
            name                    =itemView.findViewById(R.id.name);
            price                   =itemView.findViewById(R.id.price);
            car_type                =itemView.findViewById(R.id.car_type);
            car_color               =itemView.findViewById(R.id.car_color);
            seat_count              =itemView.findViewById(R.id.seat_count);
            count_ride              =itemView.findViewById(R.id.count_ride);
            count_reviews           =itemView.findViewById(R.id.count_reviews);
            from_date               =itemView.findViewById(R.id.from_date);
            from_address            =itemView.findViewById(R.id.from_address);
            to_date                 =itemView.findViewById(R.id.to_date);
            to_address              =itemView.findViewById(R.id.to_address);
            date                    =itemView.findViewById(R.id.date);
            price_all               =itemView.findViewById(R.id.price_all);
            num                     =itemView.findViewById(R.id.num);
            btn_request             =itemView.findViewById(R.id.btn_request);
            rate                    =itemView.findViewById(R.id.rate);


        }
    }


}





