package com.NativeTech.rehla.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.arch.paging.PagedListAdapter;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
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

import de.hdodenhof.circleimageview.CircleImageView;

import static com.NativeTech.rehla.Utills.Constant.ID;


public class RecyclerViewAdapterChats extends PagedListAdapter<ChatRecyclerModel, RecyclerViewAdapterChats.ViewHolder> {
    private static DiffUtil.ItemCallback<ChatRecyclerModel> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<ChatRecyclerModel>() {
                @Override
                public boolean areItemsTheSame(ChatRecyclerModel oldItem, ChatRecyclerModel newItem) {
                    return oldItem.getSenderId().equals(newItem.getSenderId());
                }

                @SuppressLint("DiffUtilEquals")
                @Override
                public boolean areContentsTheSame(ChatRecyclerModel oldItem, ChatRecyclerModel newItem) {
                    return oldItem.equals(newItem);
                }
            };
    private KProgressHUD hud;
    private Context mCtx;


    public RecyclerViewAdapterChats(Context mCtx) { //PagedList<ChatList> pagedList
        super(DIFF_CALLBACK);
        this.mCtx = mCtx;
        hud = KProgressHUD.create(mCtx)
                .setStyle(KProgressHUD.Style.PIE_DETERMINATE)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .setMaxProgress(100);
    }

    protected RecyclerViewAdapterChats(@NonNull DiffUtil.ItemCallback<ChatRecyclerModel> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.chat_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {

        ChatRecyclerModel item = getItem(i);

        setImg(item.getImg(), holder.img);
//
//
        holder.name.setText(item.getName());
        holder.body.setText(item.getBody());
        holder.date.setText(TerhalUtils.getFormatedDate(item.getDate()));


        holder.itemView.setOnClickListener(v -> {
            hud.show();
            Intent intent = new Intent(mCtx, ChatDetails.class);
            if (ID.contentEquals(item.getSenderId())) {
                Constant.PartnerId = item.getReciverId();
            } else {
                Constant.PartnerId = item.getSenderId();
            }
            Constant.PartnerIdentityId = item.getPartnerIdentityId();
            Constant.ReciverId = item.getReciverId();
            Constant.ReciverName = item.getName();
            Constant.ReciverPhoto = item.getImg();
            mCtx.startActivity(intent);
            ((Activity) mCtx).finish();
        });
    }

    private void setImg(String url, ImageView image) {

        if (url.equals("")) {
            image.setImageResource(R.drawable.ic_user);
        } else {
            try {

                Picasso.with(mCtx)
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
//
//    private final List<ChatRecyclerModel> rowItem;
//    private final Context mContext;
//    private final String ID;
//    private final KProgressHUD hud;
//
//    public RecyclerViewAdapterChats(Context context, List<ChatRecyclerModel> rowItem,String ID ) {
//        this.rowItem=rowItem;
//        this.mContext = context;
//        this.ID = ID;
//        hud = KProgressHUD.create(context)
//                .setStyle(KProgressHUD.Style.PIE_DETERMINATE)
//                .setAnimationSpeed(2)
//                .setDimAmount(0.5f)
//                .setMaxProgress(100);
//    }

//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
//
//
//        setImg(rowItem.get(position).getImg(),holder.img);
//
//
//        holder.name.setText(rowItem.get(position).getName());
//        holder.body.setText(rowItem.get(position).getBody());
//        holder.date.setText(TerhalUtils.getFormatedDate(rowItem.get(position).getDate()));
//
//
//        holder.itemView.setOnClickListener(v -> {
//            hud.show();
//            Intent intent = new Intent(mContext, ChatDetails.class);
//            if (ID.contentEquals(rowItem.get(position).getSenderId()))
//            {
//                Constant.PartnerId=rowItem.get(position).getReciverId();
//            }
//            else {
//                Constant.PartnerId=rowItem.get(position).getSenderId();
//            }
//            Constant.PartnerIdentityId=rowItem.get(position).getPartnerIdentityId();
//            Constant.ReciverId=rowItem.get(position).getReciverId();
//            Constant.ReciverName=rowItem.get(position).getName();
//            Constant.ReciverPhoto=rowItem.get(position).getImg();
//            mContext.startActivity(intent);
//            ((Activity)mContext).finish();
//        });
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return rowItem.size();
//    }
//
//    private void setImg(String url, ImageView image)
//    {
//
//        if(url.equals(""))
//        {
//            image.setImageResource(R.drawable.ic_user);
//        }
//        else {
//            try {
//
//                Picasso.with(mContext)
//                        .load(url)
//                        .placeholder(R.drawable.ic_user)
//                        .into(image);
//            } catch (Exception ignored) {
//
//            }
//        }
//    }

//
//}
//
//
//
//
//
