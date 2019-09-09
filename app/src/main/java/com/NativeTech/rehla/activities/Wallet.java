package com.NativeTech.rehla.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.NativeTech.rehla.model.DataManager;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import com.NativeTech.rehla.model.data.dto.Models.GetAllTransactions.GetAllTransactionsResponse;
import com.NativeTech.rehla.Network.NetworkUtil;
import com.NativeTech.rehla.R;
import com.NativeTech.rehla.Utills.Constant;
import com.NativeTech.rehla.Utills.Validation;
import com.NativeTech.rehla.adapters.CarsRecyclerWallet;
import com.NativeTech.rehla.adapters.RecyclerViewAdapterWallet;

import retrofit2.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static com.NativeTech.rehla.Utills.Constant.buildDialog;

public class Wallet extends AppCompatActivity {

    private RecyclerView recyclerViewWallet;
    private List<CarsRecyclerWallet> rowItem;

    private AppCompatTextView charge;
    private AppCompatTextView send_to_bank;


    private AppCompatTextView coupons_saving;

    private String Language;
    private SharedPreferences mSharedPreferences;
    private KProgressHUD hud;
    private String token="";

    private TextView no_exist;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);

        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.wallet);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        recyclerViewWallet                      = findViewById(R.id.recyclerViewWallet);
        charge                                  = findViewById(R.id.charge);
        send_to_bank                            = findViewById(R.id.send_to_bank);
        coupons_saving                          = findViewById(R.id.coupons_saving);
        rowItem                                 = new ArrayList<>();
        no_exist                                = findViewById(R.id.no_exist);
        no_exist.setVisibility(View.GONE);
        mSharedPreferences      = getSharedPreferences("tokenDetail",MODE_PRIVATE);
        CompositeSubscription mSubscriptions = new CompositeSubscription();
        Language                = mSharedPreferences.getString(Constant.language, Locale.getDefault().getLanguage());
        token                   = DataManager.getInstance().getCashedAccessToken().getAccess_token();
        hud = KProgressHUD.create(Wallet.this)
                .setStyle(KProgressHUD.Style.PIE_DETERMINATE)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .setCancellable(false)
                .setMaxProgress(100);

        if(token.equals(""))
        {
            Intent intent = new Intent(Wallet.this, Login.class);
            startActivity(intent);
            overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
            finish();
        }

        if (Validation.isConnected(Wallet.this))
        {
            hud.show();
            mSubscriptions.add(NetworkUtil.getRetrofitByToken(token)
                    .GetAllTransactions("0")
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResponse, this::handleError));
        }
        else
        {
            buildDialog(Wallet.this).show().setCanceledOnTouchOutside(false);
        }



        //initRecyclerViewWallet();

        charge.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(),RechargeWallet.class);
            startActivity(intent);
            finish();
        });

        send_to_bank.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(),SendToBank.class);
            startActivity(intent);
            finish();
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
        {

            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
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
                        message = getString(R.string.error_1);
                        break;
                    case "2":
                        message = getString(R.string.error_2);
                        break;
                    case "3":
                        message = getString(R.string.error_3);
                        break;
                    case "4":
                        message = getString(R.string.error_4);
                        break;
                    case "5":
                        message = getString(R.string.error_5);
                        break;
                    case "6":
                        message = getString(R.string.error_6);
                        break;
                    case "7":
                        message = getString(R.string.error_7);
                        break;
                    case "8":
                        message = getString(R.string.error_8);
                        break;
                    case "9":
                        message = getString(R.string.error_9);
                        break;
                    case "10":
                        message = getString(R.string.error_10);
                        break;
                    case "11":
                        message = getString(R.string.error_11);
                        break;
                    case "12":
                        message = getString(R.string.error_12);
                        break;
                    case "13":
                        message = getString(R.string.error_13);
                        break;
                    case "14":
                        message = getString(R.string.error_14);
                        break;
                    case "15":
                        message = getString(R.string.error_15);
                        break;
                    case "16":
                        message = getString(R.string.error_16);
                        break;
                    case "17":
                        message = getString(R.string.error_17);
                        break;
                    case "18":
                        message = getString(R.string.error_18);
                        break;
                    case "19":
                        message = getString(R.string.error_19);
                        break;
                    case "20":
                        message = getString(R.string.error_20);
                        break;
                    case "21":
                        message = getString(R.string.error_21);
                        break;
                    case "22":
                        message = getString(R.string.error_22);
                        break;
                    case "23":
                        message = getString(R.string.error_23);
                        break;
                    case "24":
                        message = getString(R.string.error_24);
                        break;
                    case "25":
                        message = getString(R.string.error_25);
                        break;
                    case "26":
                        message = getString(R.string.error_26);
                        break;
                    case "27":
                        message = "Wasl Error";
                        break;
                    case "28":
                        message = "Wasl Error";
                        break;
                    case "29":
                        message = getString(R.string.error_29);
                        break;
                    case "30":
                        message = getString(R.string.error_30);
                        break;
                    case "31":
                        message = getString(R.string.error_31);
                        break;

                    default:
                        message = getString(R.string.default_message);
                }
            }
            else
            {
                //Toast.makeText(AcceptOrRejectTrip.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                message = getString(R.string.default_message);
            }

        } catch (Exception ex) {
            //view.onError(e.getMessage());
            message = getString(R.string.default_message);        }

        String error="failed to connect".toLowerCase();
        if (message.toLowerCase().contains(error))
        {
            message=getString(R.string.check_internet);
        }


        android.support.v7.app.AlertDialog.Builder builder;
        android.support.v7.app.AlertDialog dialog;

        builder = new android.support.v7.app.AlertDialog.Builder(this);
        @SuppressLint("InflateParams")
        View mview = getLayoutInflater().inflate(R.layout.dialog_error, null);

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

    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    private void handleResponse(GetAllTransactionsResponse getAllTransactionsResponse) {
        rowItem.clear();
        if (getAllTransactionsResponse.getModel()!=null)
        {
            //int x= Integer.parseInt(getAllTransactionsResponse.getModel().getTotalAmount());
            coupons_saving.setText(String.valueOf(round(Double.parseDouble(getAllTransactionsResponse.getModel().getTotalAmount()),2)));
            if (getAllTransactionsResponse.getModel().getTransactions()!=null)
            {
                if (getAllTransactionsResponse.getModel().getTransactions().size()>0)
                {
                    for (int i=0;i<getAllTransactionsResponse.getModel().getTransactions().size();i++)
                    {
                        rowItem.add(new CarsRecyclerWallet(getAllTransactionsResponse.getModel().getTransactions().get(i).getId()
                                ,"","",getAllTransactionsResponse.getModel().getTransactions().get(i).getAmount()
                                ,getAllTransactionsResponse.getModel().getTransactions().get(i).getTransactionDate()
                                ,"",getAllTransactionsResponse.getModel().getTransactions().get(i).getReason()));
                    }
                }
            }
            if (rowItem.size()==0)
            {
                no_exist.setVisibility(View.VISIBLE);
            }
            else
                initRecyclerViewWallet();
        }
        hud.dismiss();
    }

    private void initRecyclerViewWallet() {
        RecyclerViewAdapterWallet adapter2 = new RecyclerViewAdapterWallet(Wallet.this,rowItem);
        recyclerViewWallet.setHasFixedSize(true);
        LinearLayoutManager mRecyclerViewLayoutManager2 = new LinearLayoutManager(Wallet.this, LinearLayoutManager.VERTICAL, false);
        recyclerViewWallet.setLayoutManager(mRecyclerViewLayoutManager2);
        recyclerViewWallet.setAdapter(adapter2);
        adapter2.notifyDataSetChanged();
    }
}
