package com.NativeTech.rehla.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.NativeTech.rehla.model.DataManager;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

import com.NativeTech.rehla.model.data.dto.Models.Driver.updateTripStatusResponse;
import com.NativeTech.rehla.Network.NetworkUtil;
import com.NativeTech.rehla.R;
import com.NativeTech.rehla.Utills.Constant;
import com.NativeTech.rehla.Utills.Validation;
import com.NativeTech.rehla.activities.Cars;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static android.content.Context.MODE_PRIVATE;
import static com.NativeTech.rehla.Utills.Constant.buildDialog;


public class RecyclerViewAdapterCars extends RecyclerView.Adapter<RecyclerViewAdapterCars.ViewHolder> {

    private final List<CarsRecyclerModel> rowItem;
    private final Context mContext;

    private final CompositeSubscription mSubscriptions;
    private final SharedPreferences mSharedPreferences;
    private final KProgressHUD hud;
    private String token="";
    private final String Language;

    public RecyclerViewAdapterCars(Context context, List<CarsRecyclerModel> rowItem ) {
        this.rowItem=rowItem;
        this.mContext = context;
        Activity activity = (Activity) context;
        mSharedPreferences      = mContext.getSharedPreferences("tokenDetail",MODE_PRIVATE);
        mSubscriptions          = new CompositeSubscription();
        token                   = DataManager.getInstance().getCashedAccessToken().getAccess_token();
        Language                = mSharedPreferences.getString(Constant.language, Locale.getDefault().getLanguage());
        hud = KProgressHUD.create(activity)
                .setStyle(KProgressHUD.Style.PIE_DETERMINATE)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .setCancellable(false)
                .setMaxProgress(100);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.car_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {


        setImg(rowItem.get(position).getImg(),holder.img);
        holder.name.setText(rowItem.get(position).getName());
        holder.car_type.setText(rowItem.get(position).getCar_type());
        holder.car_color.setText(rowItem.get(position).getCar_color());
        holder.seat_num.setText(rowItem.get(position).getSeat_num());
        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Validation.isConnected(mContext))
                {
                    hud.show();
                    mSubscriptions.add(NetworkUtil.getRetrofitByToken(token)
                            .deleteCar(rowItem.get(position).getId())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io())
                            .subscribe(this::handleResponse, this::handleError));
                }
                else
                {
                    buildDialog((Activity) mContext).show().setCanceledOnTouchOutside(false);
                }
            }

            private void handleError(Throwable throwable) {
                //Toast.makeText(getActivity(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                String message="";
                try {
                    if (throwable instanceof retrofit2.HttpException) {
                        retrofit2.HttpException error = (retrofit2.HttpException) throwable;
                        JSONObject jsonObject = new JSONObject(Objects.requireNonNull(((HttpException) throwable).response().errorBody()).string());

                        String code = jsonObject.getJSONObject("errors").getString("Code");

                        switch (code) {
                            case "1":
                                message = mContext.getString(R.string.error_1);
                                break;
                            case "2":
                                message = mContext.getString(R.string.error_2);
                                break;
                            case "3":
                                message = mContext.getString(R.string.error_3);
                                break;
                            case "4":
                                message = mContext.getString(R.string.error_4);
                                break;
                            case "5":
                                message = mContext.getString(R.string.error_5);
                                break;
                            case "6":
                                message = mContext.getString(R.string.error_6);
                                break;
                            case "7":
                                message = mContext.getString(R.string.error_7);
                                break;
                            case "8":
                                message = mContext.getString(R.string.error_8);
                                break;
                            case "9":
                                message = mContext.getString(R.string.error_9);
                                break;
                            case "10":
                                message = mContext.getString(R.string.error_10);
                                break;
                            case "11":
                                message = mContext.getString(R.string.error_11);
                                break;
                            case "12":
                                message = mContext.getString(R.string.error_12);
                                break;
                            case "13":
                                message = mContext.getString(R.string.error_13);
                                break;
                            case "14":
                                message = mContext.getString(R.string.error_14);
                                break;
                            case "15":
                                message = mContext.getString(R.string.error_15);
                                break;
                            case "16":
                                message = mContext.getString(R.string.error_16);
                                break;
                            case "17":
                                message = mContext.getString(R.string.error_17);
                                break;
                            case "18":
                                message = mContext.getString(R.string.error_18);
                                break;
                            case "19":
                                message = mContext.getString(R.string.error_19);
                                break;
                            case "20":
                                message = mContext.getString(R.string.error_20);
                                break;
                            case "21":
                                message = mContext.getString(R.string.error_21);
                                break;
                            case "22":
                                message = mContext.getString(R.string.error_22);
                                break;
                            case "23":
                                message = mContext.getString(R.string.error_23);
                                break;
                            case "24":
                                message = mContext.getString(R.string.error_24);
                                break;
                            case "25":
                                message = mContext.getString(R.string.error_25);
                                break;
                            case "26":
                                message = mContext.getString(R.string.error_26);
                                break;
                            case "27":
                                message = "Wasl Error";
                                break;
                            case "28":
                                message = "Wasl Error";
                                break;
                            case "29":
                                message = mContext.getString(R.string.error_29);
                                break;
                            case "30":
                                message = mContext.getString(R.string.error_30);
                                break;
                            case "31":
                                message = mContext.getString(R.string.error_31);
                                break;

                            default:
                                message = mContext.getString(R.string.default_message);
                        }
                    }
                    else
                    {
                        //Toast.makeText(AcceptOrRejectTrip.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        message = mContext.getString(R.string.default_message);
                    }

                } catch (Exception ex) {
                    //view.onError(e.getMessage());
                    message = mContext.getString(R.string.default_message);                }

                String error="failed to connect".toLowerCase();
                if (message.toLowerCase().contains(error))
                {
                    message=mContext.getString(R.string.check_internet);
                }


                android.support.v7.app.AlertDialog.Builder builder;
                android.support.v7.app.AlertDialog dialog;

                builder = new android.support.v7.app.AlertDialog.Builder(mContext);
                @SuppressLint("InflateParams")
                View mview = ((Activity)mContext).getLayoutInflater().inflate(R.layout.dialog_error, null);

                builder.setView(mview);
                dialog = builder.create();
                Window window = dialog.getWindow();
                if (window != null) {
                    window.setGravity(Gravity.CENTER);
                }
                dialog.show();
                TextView txt = mview.findViewById(R.id.txt);
                TextView ok = mview.findViewById(R.id.ok);
                txt.setText(message);
                ok.setOnClickListener(v2 -> dialog.dismiss());
                hud.dismiss();
            }

            private void handleResponse(updateTripStatusResponse getAllCarsResponse) {
                if (getAllCarsResponse.getErrors().getMessage()==null)
                {
                    Intent intent = new Intent(mContext, Cars.class);
                    mContext.startActivity(intent);
                    ((Activity)mContext).overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                    ((Activity)mContext).finish();
                }
            }
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
            image.setImageResource(R.drawable.gry_image);
        }
        else {
            try {
                Picasso.with(mContext)
                        .load(url)
                        .placeholder(R.drawable.gry_image)
                        .into(image);
            } catch (Exception ignored) {

            }
        }
    }
    class ViewHolder extends RecyclerView.ViewHolder{

        final CircleImageView img;
        final TextView name;
        final TextView car_type;
        final TextView car_color;
        final TextView seat_num;
        final LinearLayoutCompat remove;



        ViewHolder(View itemView) {
            super(itemView);
            img                         =itemView.findViewById(R.id.img);
            name                        =itemView.findViewById(R.id.name);
            car_type                    =itemView.findViewById(R.id.car_type);
            car_color                   =itemView.findViewById(R.id.car_color);
            seat_num                    =itemView.findViewById(R.id.seat_num);
            remove                      =itemView.findViewById(R.id.remove);



        }
    }


}





