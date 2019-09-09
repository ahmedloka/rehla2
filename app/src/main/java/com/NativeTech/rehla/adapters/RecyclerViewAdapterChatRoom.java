package com.NativeTech.rehla.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.NativeTech.rehla.Utills.TerhalUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

import com.NativeTech.rehla.R;

import de.hdodenhof.circleimageview.CircleImageView;


public class RecyclerViewAdapterChatRoom extends RecyclerView.Adapter<RecyclerViewAdapterChatRoom.ViewHolder> {

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


    class ViewHolder extends RecyclerView.ViewHolder{





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

            name = view.findViewById(R.id.name);
            avatar = view.findViewById(R.id.avatar);
            messagemyDate = view.findViewById(R.id.message_body_mydate);
            messageOtherDate = view.findViewById(R.id.message_body_theirdate);
            messageBodyMy = view.findViewById(R.id.message_body_my);
            messageBodyTheir = view.findViewById(R.id.message_body_their);

            my      = view.findViewById(R.id.my);
            their   = view.findViewById(R.id.their);
            //messageBody = view.findViewById(R.id.message_body);
        }
    }


}





