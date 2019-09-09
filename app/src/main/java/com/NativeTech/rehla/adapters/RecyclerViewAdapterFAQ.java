package com.NativeTech.rehla.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import com.NativeTech.rehla.R;


public class RecyclerViewAdapterFAQ extends RecyclerView.Adapter<RecyclerViewAdapterFAQ.ViewHolder> {

    private final List<FAQRecyclerModel> rowItem;
    private final Context mContext;

    public RecyclerViewAdapterFAQ(Context context, List<FAQRecyclerModel> rowItem ) {
        this.rowItem=rowItem;
        this.mContext = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.faq_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.question.setText(rowItem.get(position).getQuestion());
        holder.answer.setText(rowItem.get(position).getAnswer());
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


        final TextView question;
        final TextView answer;



        ViewHolder(View itemView) {
            super(itemView);

            question                    =itemView.findViewById(R.id.question);
            answer                      =itemView.findViewById(R.id.answer);



        }
    }


}





