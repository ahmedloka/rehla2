package com.NativeTech.rehla.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.NativeTech.rehla.R;
import com.NativeTech.rehla.Utills.TerhalUtils;


public class RecyclerViewAdapterWallet extends RecyclerView.Adapter<RecyclerViewAdapterWallet.ViewHolder> {

    private final List<CarsRecyclerWallet> rowItem;
    private final Context mContext;

    public RecyclerViewAdapterWallet(Context context, List<CarsRecyclerWallet> rowItem ) {
        this.rowItem=rowItem;
        this.mContext = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wallet_item_latest, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {


        try
        {
            double price=round(Double.parseDouble(rowItem.get(position).getPrice()),2);
            holder.price.setText(String.valueOf(price));
            if(price<0)
                holder.price.setTextColor(mContext.getResources().getColor(R.color.redcolor));
            else
                holder.price.setTextColor(mContext.getResources().getColor(R.color.app_color));
            holder.date.setText(TerhalUtils.getFormatedDate(rowItem.get(position).getDate()));
            holder.details.setText(rowItem.get(position).getDetails());

        }catch (Exception e)
        {}



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


        final TextView price;
        final TextView date;
        final TextView details;



        ViewHolder(View itemView) {
            super(itemView);
            price                           =itemView.findViewById(R.id.price);
            date                            =itemView.findViewById(R.id.date);
            details                         =itemView.findViewById(R.id.details);



        }
    }


}





