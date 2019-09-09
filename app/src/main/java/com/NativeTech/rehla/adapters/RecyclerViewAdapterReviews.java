package com.NativeTech.rehla.adapters;

import android.content.Context;
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

import de.hdodenhof.circleimageview.CircleImageView;
import rx.subscriptions.CompositeSubscription;

import static android.content.Context.MODE_PRIVATE;


public class RecyclerViewAdapterReviews extends RecyclerView.Adapter<RecyclerViewAdapterReviews.ViewHolder> {

    private final List<ReviewsRecyclerModel> rowItem;
    private final Context mContext;

    private final String Language;
    private final SharedPreferences mSharedPreferences;
    private final KProgressHUD hud;


    public RecyclerViewAdapterReviews(Context context, List<ReviewsRecyclerModel> rowItem ) {
        this.rowItem=rowItem;
        this.mContext = context;
        mSharedPreferences  =   context.getSharedPreferences("tokenDetail",MODE_PRIVATE);
        Language                = mSharedPreferences.getString(Constant.language, Locale.getDefault().getLanguage());
        CompositeSubscription mSubscriptions = new CompositeSubscription();
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reviews_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {


        setImg(rowItem.get(position).getImg(),holder.img);
        holder.name.setText(rowItem.get(position).getName());

        switch (Math.round(Float.parseFloat(rowItem.get(position).getRate())))
        {
            case 1:
                if (Language.equals("ar"))
                holder.rate_name.setText("لا أرشحه");
                else
                    holder.rate_name.setText("Not recommended");
                break;
            case 2:
                if (Language.equals("ar"))
                    holder.rate_name.setText("مقبول");
                else
                    holder.rate_name.setText("Poor");
                break;
            case 3:
                if (Language.equals("ar"))
                    holder.rate_name.setText("جيد");
                else
                    holder.rate_name.setText("Good");
                break;
            case 4:
                if (Language.equals("ar"))
                    holder.rate_name.setText("ممتاز");
                else
                    holder.rate_name.setText("Excellent");
                break;
            case 5:
                if (Language.equals("ar"))
                    holder.rate_name.setText("رائع");
                else
                    holder.rate_name.setText("Outstanding");
                break;
        }

        holder.rate.setRating(Float.parseFloat(rowItem.get(position).getRate()));
        holder.comment.setText(rowItem.get(position).getComment());




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

        final CircleImageView     img;
        final TextView            name;
        final RatingBar           rate;
        final TextView            comment;
        final TextView            rate_name;



        ViewHolder(View itemView) {
            super(itemView);
            img                     =itemView.findViewById(R.id.img);
            name                    =itemView.findViewById(R.id.name);
            rate                    =itemView.findViewById(R.id.rate);
            comment                 =itemView.findViewById(R.id.comment);
            rate_name               =itemView.findViewById(R.id.rate_name);



        }
    }


}





