package com.NativeTech.rehla.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import com.NativeTech.rehla.R;
import com.NativeTech.rehla.interfaces.RecyclerViewButtonClickListener;
import de.hdodenhof.circleimageview.CircleImageView;


public class filterAreaAdapterForCountryCode extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener
        , View.OnLongClickListener {

    private ArrayList<filterAreaModelRecycler> dataSet;
   private final Context mContext;

    private View.OnClickListener itemListener;
    private RecyclerViewButtonClickListener btnListener;

    public filterAreaAdapterForCountryCode(ArrayList<filterAreaModelRecycler> data, Context context) {
        this.dataSet = data;
        this.mContext = context;
        int total_types = dataSet.size();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }


    static class TextTypeViewHolder extends RecyclerView.ViewHolder {



        final CardView cardView;
        final TextView CitytName;
        //ImageView countryLogo;
        CircleImageView categoryLogo;
        TextView categoryName;
        TextView fristDesc;
        String id;




        TextTypeViewHolder(View itemView) {
            super(itemView);


            this.cardView = (CardView) itemView.findViewById(R.id.card_view);


            this.CitytName     = (TextView) itemView.findViewById(R.id.CitytName);
            //this.countryLogo   =  itemView.findViewById(R.id.countryLogo);


        }

    }







    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;

                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.country_code_dialog_recycler_row, parent, false);
                return new TextTypeViewHolder(view);




    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int listPosition) {


        if (itemListener != null) {
            holder.itemView.setOnClickListener(itemListener);
        }
        if (btnListener != null) {

        }
        filterAreaModelRecycler object = dataSet.get(listPosition);

        if (object != null)
        {
                  //   setImg(object.MerchantLogo, ((TextTypeViewHolder) holder).merchantImage);
                    ((TextTypeViewHolder) holder).CitytName.setText(String.valueOf(object.CityName));
                    String id = dataSet.get(listPosition).flag;

                    //setImg(object.flag, ((TextTypeViewHolder) holder).countryLogo);
//                    ((TextTypeViewHolder) holder).categoryName.setText(object.categoryname);
//                    ((TextTypeViewHolder) holder).fristDesc.setText(object.fristDesc);


        }
    }

    public void update(ArrayList<filterAreaModelRecycler> list )
    {
        this.dataSet=list;
        this.notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public void setOnItemClickListener(View.OnClickListener clickListener) {
        this.itemListener = clickListener;
    }

    public void setBtnLocationListener(RecyclerViewButtonClickListener listener) {
        this.btnListener = listener;
    }

    public void setImg(String url, ImageView image)
    {

        try
        {

            Picasso.with(mContext)
                    .load(url)
                    .fit()
                    .placeholder(R.drawable.gry_image)
                    .into(image);
        }
        catch (Exception ignored)
        {

        }

    }


}
