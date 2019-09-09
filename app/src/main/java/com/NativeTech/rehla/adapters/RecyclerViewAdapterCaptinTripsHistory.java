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
import com.NativeTech.rehla.activities.CaptinHistoryRequests;
import com.NativeTech.rehla.activities.TripDetail;


public class RecyclerViewAdapterCaptinTripsHistory extends RecyclerView.Adapter<RecyclerViewAdapterCaptinTripsHistory.ViewHolder> {

    private final List<CaptinTripsHistoryRecyclerModel> rowItem;
    private final Context mContext;

    public RecyclerViewAdapterCaptinTripsHistory(Context context, List<CaptinTripsHistoryRecyclerModel> rowItem ) {
        this.rowItem=rowItem;
        this.mContext = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.captin_trip_history_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        try {

            holder.from.setText(rowItem.get(position).getFrom());
            holder.to.setText(rowItem.get(position).getTo());
            //holder.date.setText(rowItem.get(position).getDate());
            String string = rowItem.get(position).getDate();
            //String defaultTimezone = TimeZone.getDefault().getID();
            //parseDateTo_yyyyMMdd(string);


            holder.time.setText(TerhalUtils.getTimeWithCurrentZone(
                    TerhalUtils.parseDateTo_yyyyMMdd(rowItem.get(position).getDate()) + " " + rowItem.get(position).getTime()));

            holder.date.setText(TerhalUtils.parseDateTo_yyyyMMdd(string));
            holder.distance.setText(rowItem.get(position).getDistance());
            holder.available_seat.setText(rowItem.get(position).getAvailable());


            holder.btn_request.setOnClickListener(v -> {
                Intent intent = new Intent(mContext, CaptinHistoryRequests.class);
                Constant.tripIdCaptin = rowItem.get(position).getId();
                Constant.fromCaptin = rowItem.get(position).getFrom();
                Constant.toCaptin = rowItem.get(position).getTo();
                Constant.startTimeCaptin = rowItem.get(position).getTime();
                Constant.endTimeCaptin = rowItem.get(position).getEndTime();
                Constant.dateCaptin = rowItem.get(position).getDate();
                Constant.EndDateCaptin = rowItem.get(position).getEndDate();
                mContext.startActivity(intent);
            });

 /*       holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, CaptinHistoryRequests.class);
            Constant.tripIdCaptin=rowItem.get(position).getId();
            Constant.fromCaptin=rowItem.get(position).getFrom();
            Constant.toCaptin=rowItem.get(position).getTo();
            Constant.startTimeCaptin=rowItem.get(position).getTime();
            Constant.endTimeCaptin=rowItem.get(position).getEndTime();
            Constant.dateCaptin=rowItem.get(position).getDate();
            Constant.EndDateCaptin=rowItem.get(position).getEndDate();

            mContext.startActivity(intent);
        });
        */
            holder.details.setOnClickListener(v -> {
                Intent intent = new Intent(mContext, TripDetail.class);
                Constant.tripIdSearch = rowItem.get(position).getId();
                Constant.TripDetailsCarColor = rowItem.get(position).getAvailable();
                mContext.startActivity(intent);
            });
        }catch (Exception e){}

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
        final TextView            distance;
        final TextView            available_seat;
        final TextView              btn_request;
        final TextView              details;



        ViewHolder(View itemView) {
            super(itemView);
            from                        =itemView.findViewById(R.id.from);
            to                          =itemView.findViewById(R.id.to);
            date                        =itemView.findViewById(R.id.date);
            time                        =itemView.findViewById(R.id.time);
            distance                    =itemView.findViewById(R.id.distance);
            available_seat              =itemView.findViewById(R.id.available_seat);
            btn_request                 =itemView.findViewById(R.id.btn_request);
            details                     =itemView.findViewById(R.id.details);



        }
    }


}





