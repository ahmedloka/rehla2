package com.NativeTech.rehla.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import com.NativeTech.rehla.R;
import com.NativeTech.rehla.Utills.TerhalUtils;

import de.hdodenhof.circleimageview.CircleImageView;


public class RecyclerViewAdapterNotifications extends RecyclerView.Adapter<RecyclerViewAdapterNotifications.ViewHolder> {

    private final List<NotificationsRecyclerModel> rowItem;
    private final Context mContext;

    public RecyclerViewAdapterNotifications(Context context, List<NotificationsRecyclerModel> rowItem ) {
        this.rowItem=rowItem;
        this.mContext = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {


        try {
            setImg(rowItem.get(position).getImg(), holder.img);

       /* SpannableStringBuilder builder = new SpannableStringBuilder();

        SpannableString redSpannable= new SpannableString(rowItem.get(position).getName()+" ");
        redSpannable.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.app_color)), 0, rowItem.get(position).getName().length(), 0);
        builder.append(redSpannable);

        SpannableString whiteSpannable= new SpannableString(rowItem.get(position).getBody());
        whiteSpannable.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.gray_selected)), 0, rowItem.get(position).getBody().length(), 0);
        builder.append(whiteSpannable);*/

            //holder.body.setText(builder, TextView.BufferType.SPANNABLE);

            Spannable wordtoSpan = new SpannableString(rowItem.get(position).getName()+ " "+rowItem.get(position).getBody());

            wordtoSpan.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.app_color)), 0, rowItem.get(position).getName().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
            wordtoSpan.setSpan(boldSpan, 0, rowItem.get(position).getName().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            holder.name.setText(wordtoSpan);
            holder.body.setVisibility(View.GONE);
           // holder.name.setText(rowItem.get(position).getName());
            //holder.body.setText(rowItem.get(position).getBody());
            holder.date.setText(TerhalUtils.getFormatedDate(rowItem.get(position).getDate()));

        }catch (Exception e)
        {}


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
        final TextView body;
        final TextView date;



        ViewHolder(View itemView) {
            super(itemView);
            img                     =itemView.findViewById(R.id.img);
            name                    =itemView.findViewById(R.id.name);
            body                   =itemView.findViewById(R.id.body);
            date                =itemView.findViewById(R.id.date);



        }
    }


}





