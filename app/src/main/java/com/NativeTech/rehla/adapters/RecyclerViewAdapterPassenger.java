package com.NativeTech.rehla.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

import com.NativeTech.rehla.R;
import com.NativeTech.rehla.Utills.Constant;
import com.NativeTech.rehla.activities.PassengerDetails;

import de.hdodenhof.circleimageview.CircleImageView;
import rx.subscriptions.CompositeSubscription;

import static android.content.Context.MODE_PRIVATE;


public class RecyclerViewAdapterPassenger extends RecyclerView.Adapter<RecyclerViewAdapterPassenger.ViewHolder> {

    private final List<PassengerModel> rowItem;
    private final Context mContext;

    private final SharedPreferences mSharedPreferences;
    private final KProgressHUD hud;

    public RecyclerViewAdapterPassenger(Context context, List<PassengerModel> rowItem ) {
        this.rowItem=rowItem;
        this.mContext = context;
        mSharedPreferences  =   mContext.getSharedPreferences("tokenDetail",MODE_PRIVATE);
        String language = mSharedPreferences.getString(Constant.language, Locale.getDefault().getLanguage());
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.passenger_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        setImg(rowItem.get(position).getImg(),holder.img);
        holder.name.setText(rowItem.get(position).getName());
        holder.rate.setRating(Float.parseFloat(rowItem.get(position).getRate()));


        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, PassengerDetails.class);
            Constant.passenger_id=rowItem.get(position).getId();
            mContext.startActivity(intent);
        });


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
        final RatingBar rate;

        ViewHolder(View itemView) {
            super(itemView);
            img                     =itemView.findViewById(R.id.img);
            name                    =itemView.findViewById(R.id.name);
            rate                    =itemView.findViewById(R.id.rate);
        }
    }


}





