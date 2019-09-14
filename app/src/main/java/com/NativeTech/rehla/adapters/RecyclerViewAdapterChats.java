package com.NativeTech.rehla.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.NativeTech.rehla.R;
import com.NativeTech.rehla.Utills.Constant;
import com.NativeTech.rehla.Utills.TerhalUtils;
import com.NativeTech.rehla.activities.ChatDetails;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class RecyclerViewAdapterChats extends RecyclerView.Adapter<RecyclerViewAdapterChats.ViewHolder> {

    private final List<ChatRecyclerModel> rowItem;
    private final Context mContext;
    private final String ID;
    private final KProgressHUD hud;

    public RecyclerViewAdapterChats(Context context, List<ChatRecyclerModel> rowItem, String ID) {
        this.rowItem = rowItem;
        this.mContext = context;
        this.ID = ID;
        hud = KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.PIE_DETERMINATE)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .setMaxProgress(100);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") final int position) {


        setImg(rowItem.get(position).getImg(), holder.img);


        holder.name.setText(rowItem.get(position).getName());
        holder.body.setText(rowItem.get(position).getBody());
        holder.date.setText(TerhalUtils.getFormatedDate(rowItem.get(position).getDate()));


        holder.itemView.setOnClickListener(v -> {
          //  hud.show();
            Intent intent = new Intent(mContext, ChatDetails.class);
            if (ID.contentEquals(rowItem.get(position).getSenderId())) {
                Constant.PartnerId = rowItem.get(position).getReciverId();
            } else {
                Constant.PartnerId = rowItem.get(position).getSenderId();
            }
            Constant.PartnerIdentityId = rowItem.get(position).getPartnerIdentityId();
            Constant.ReciverId = rowItem.get(position).getReciverId();
            Constant.ReciverName = rowItem.get(position).getName();
            Constant.ReciverPhoto = rowItem.get(position).getImg();
            mContext.startActivity(intent);
            ((Activity) mContext).finish();
        });

    }

    @Override
    public int getItemCount() {
        return rowItem.size();
    }

    private void setImg(String url, ImageView image) {

        if (url.equals("")) {
            image.setImageResource(R.drawable.ic_user);
        } else {
            try {

                Picasso.with(mContext)
                        .load(url)
                        .placeholder(R.drawable.ic_user)
                        .into(image);
            } catch (Exception ignored) {

            }
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        final CircleImageView img;
        final TextView name;
        final TextView body;
        final TextView date;


        ViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            name = itemView.findViewById(R.id.name);
            body = itemView.findViewById(R.id.body);
            date = itemView.findViewById(R.id.date);


        }
    }


}





