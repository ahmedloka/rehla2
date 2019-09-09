package com.NativeTech.rehla.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import com.NativeTech.rehla.R;
import de.hdodenhof.circleimageview.CircleImageView;

class MessageAdapter extends BaseAdapter {

    private final List<Message> messages;
    private final Context context;
    //String userId;
    public MessageAdapter(Context context,List<Message> messages) {
        this.context = context;
        this.messages=messages;
    }

    public void add(Message message) {
        this.messages.add(message);
        notifyDataSetChanged(); // to render the list we need to notify
    }

    @Override
    public int getCount() {
        return messages.size();
    }

    @Override
    public Object getItem(int i) {
        return messages.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    // This is the backbone of the class, it handles the creation of single ListView row (chat bubble)
    @SuppressLint("InflateParams")
    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        MessageViewHolder holder = new MessageViewHolder();
        LayoutInflater messageInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        Message message = messages.get(i);

            if (message.isBelongsToCurrentUser()) { // this message was sent by us so let's create a basic chat bubble on the right
                assert messageInflater != null;
                convertView = messageInflater.inflate(R.layout.my_message, null);
                holder.messageBody = convertView.findViewById(R.id.message_body);
                 convertView.setTag(holder);
                holder.messageBody.setText(message.getText());
            } else
                { // this message was sent by someone else so let's create an advanced chat bubble on the left
                    assert messageInflater != null;
                    convertView = messageInflater.inflate(R.layout.their_message, null);
                holder.avatar = convertView.findViewById(R.id.avatar);
                holder.name = convertView.findViewById(R.id.name);
                holder.messageBody = convertView.findViewById(R.id.message_body);
                convertView.setTag(holder);

                holder.name.setText(message.getName());
                holder.messageBody.setText(message.getText());
                //GradientDrawable drawable = (GradientDrawable) holder.avatar.getBackground();
                //drawable.setColor(Color.parseColor(message.getData().getColor()));

                    //Toast.makeText(context, message.getPhoto()+"", Toast.LENGTH_SHORT).show();
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

        return convertView;
    }

}

class MessageViewHolder {
    public CircleImageView avatar;
    public TextView name;
    TextView messageBody;

}