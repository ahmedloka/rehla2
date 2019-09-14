package com.NativeTech.rehla.adapters;

import android.annotation.SuppressLint;
import android.arch.paging.PagedListAdapter;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.NativeTech.rehla.R;
import com.NativeTech.rehla.Utills.Constant;
import com.NativeTech.rehla.Utills.TerhalUtils;
import com.NativeTech.rehla.model.chat.Model;
import com.NativeTech.rehla.model.data.dto.Models.Chats.ChatDetailsModel;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


public class RecyclerViewAdapterChatRoom extends PagedListAdapter<Model, RecyclerViewAdapterChatRoom.ViewHolder> {

    private static DiffUtil.ItemCallback<Model> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Model>() {
                @Override
                public boolean areItemsTheSame(Model oldItem, Model newItem) {
                    return oldItem.getSenderId().equals(newItem.getSenderId());
                }

                @SuppressLint("DiffUtilEquals")
                @Override
                public boolean areContentsTheSame(Model oldItem, Model newItem) {
                    return oldItem.equals(newItem);
                }
            };
    private Context mCtx;
    // private PagedList<ChatList> pagedList ;
    private SharedPreferences sharedPreferences;
//

    public RecyclerViewAdapterChatRoom(Context mCtx) { //PagedList<ChatList> pagedList
        super(DIFF_CALLBACK);
        this.mCtx = mCtx;
        //  this.pagedList = pagedList ;
        sharedPreferences = mCtx.getSharedPreferences("MySharedPreference", Context.MODE_PRIVATE);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.my_message, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Model message = getItem(position);

        if (message != null) {

            if (message.getSenderId().equals(Constant.PartnerId)) {
                holder.my.setVisibility(View.VISIBLE);
                holder.their.setVisibility(View.GONE);

                holder.messageBodyMy.setText(message.getMessage());
                try {
                    holder.messagemyDate.setText(TerhalUtils.getFormatedDate(message.getCreationDate()));
                } catch (Exception e) {
                    holder.messagemyDate.setVisibility(View.GONE);
                }
            } else {
                holder.my.setVisibility(View.GONE);
                holder.their.setVisibility(View.VISIBLE);
                try {
                    holder.messageOtherDate.setText(TerhalUtils.getFormatedDate(message.getCreationDate()));
                } catch (Exception e) {
                    holder.messagemyDate.setVisibility(View.GONE);
                }
                //  holder.name.setText(message.getPartnerName());
                holder.messageBodyTheir.setText(message.getMessage());
                holder.avatar.setImageResource(R.drawable.ic_user);

                //if (message.getPartnerPhoto().equals("")) {
                //   }
// else {
//                    Picasso.with(mCtx)
//                            .load(message.getPartnerPhoto())
//                            .placeholder(R.drawable.ic_user)
//                            .into(holder.avatar);
//                }


            }
        }
    }


    class ViewHolder extends RecyclerView.ViewHolder {


        final RelativeLayout my;
        final RelativeLayout their;

        final CircleImageView avatar;
        final TextView name;
        final TextView messageBodyMy;
        final TextView messageBodyTheir;
        final TextView messagemyDate;
        final TextView messageOtherDate;


        ViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            avatar = itemView.findViewById(R.id.avatar);
            messagemyDate = itemView.findViewById(R.id.message_body_mydate);
            messageOtherDate = itemView.findViewById(R.id.message_body_theirdate);
            messageBodyMy = itemView.findViewById(R.id.message_body_my);
            messageBodyTheir = itemView.findViewById(R.id.message_body_their);

            my = itemView.findViewById(R.id.my);
            their = itemView.findViewById(R.id.their);
            //messageBody = view.findViewById(R.id.message_body);
        }
    }


}


/*
extends RecyclerView.Adapter<RecyclerViewAdapterChatRoom.ViewHolder> {

    private final List<Message> messages;
    private final Context context;
    private View view;

    public RecyclerViewAdapterChatRoom(Context context,List<Message> messages) {
        this.context = context;
        this.messages=messages;
    }

    public void add(Message message) {
        this.messages.add(message);
        notifyDataSetChanged(); // to render the list we need to notify
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_message, parent, false);

        return new ViewHolder(view);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {



        Message message = messages.get(position);

        if (message.isBelongsToCurrentUser())
        {
            holder.my.setVisibility(View.VISIBLE);
            holder.their.setVisibility(View.GONE);

            holder.messageBodyMy.setText(message.getText());
           try{ holder.messagemyDate.setText(TerhalUtils.getFormatedDate(message.getDate()));}
           catch (Exception e){holder.messagemyDate.setVisibility(View.GONE);}
        }
        else
        {
            holder.my.setVisibility(View.GONE);
            holder.their.setVisibility(View.VISIBLE);
            try{ holder.messageOtherDate.setText(TerhalUtils.getFormatedDate(message.getDate()));}
            catch (Exception e){holder.messagemyDate.setVisibility(View.GONE);}
            holder.name.setText(message.getName());
            holder.messageBodyTheir.setText(message.getText());
            if (message.getPhoto().equals(""))
            {
                holder.avatar .setImageResource(R.drawable.ic_user);
            }
            else {
                Picasso.with(context)
                        .load(message.getPhoto())
                        .placeholder(R.drawable.ic_user)
                        .into(holder.avatar);
            }

        }
    }

    @Override
    public int getItemCount() {
        return  messages.size();
    }




}






 */


