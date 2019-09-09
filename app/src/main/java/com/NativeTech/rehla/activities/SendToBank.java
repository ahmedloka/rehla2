package com.NativeTech.rehla.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.NativeTech.rehla.model.DataManager;
import com.blankj.utilcode.util.KeyboardUtils;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import com.NativeTech.rehla.model.data.dto.Models.ErrorsModel;
import com.NativeTech.rehla.model.data.dto.Models.MetaDataModels.BankModel;
import com.NativeTech.rehla.model.data.dto.Models.MetaDataModels.MetaDataResponseModel;
import com.NativeTech.rehla.model.data.dto.Models.SendToBank.sendToBankRequest;
import com.NativeTech.rehla.Network.NetworkUtil;
import com.NativeTech.rehla.R;
import com.NativeTech.rehla.Utills.Constant;
import com.NativeTech.rehla.Utills.Validation;
import com.NativeTech.rehla.adapters.filterAreaAdapterForCountryCode;
import com.NativeTech.rehla.adapters.filterAreaModelRecycler;
import retrofit2.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static com.NativeTech.rehla.Utills.Constant.buildDialog;

public class SendToBank extends AppCompatActivity {

    private AppCompatEditText   amount_money;
    private AppCompatEditText   Card_Number;
    private TextView            bank_name;
    private Button              send;

    private String Language;
    private CompositeSubscription mSubscriptions;
    private SharedPreferences mSharedPreferences;
    private KProgressHUD hud;
    private String token="";

    //TextView bank_name;
    private String bank_Id="-1";
    private List<BankModel> banks=new ArrayList<>();

    private RecyclerView DialogRecyclerView;
    private filterAreaAdapterForCountryCode filterAreaAdapter1;
    private final ArrayList<filterAreaModelRecycler> DialogList = new ArrayList<>();
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_to_bank);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.send_to_bank);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



        amount_money        =findViewById(R.id.amount_money);
        Card_Number         =findViewById(R.id.Card_Number);
        bank_name           =findViewById(R.id.bank_name);
        send                =findViewById(R.id.send);
         ((View)Card_Number.getParent()).setOnClickListener(v -> {
            try {
                KeyboardUtils.hideSoftInput(SendToBank.this);
            }catch (Exception e){}
        });
        ((View)Card_Number.getParent().getParent()).setOnClickListener(v -> {
            try {
                KeyboardUtils.hideSoftInput(SendToBank.this);
            }catch (Exception e){}
        });
        mSharedPreferences      = getSharedPreferences("tokenDetail",MODE_PRIVATE);
        mSubscriptions          = new CompositeSubscription();
        Language                = mSharedPreferences.getString(Constant.language, Locale.getDefault().getLanguage());
        token                   = DataManager.getInstance().getCashedAccessToken().getAccess_token();
        hud = KProgressHUD.create(SendToBank.this)
                .setStyle(KProgressHUD.Style.PIE_DETERMINATE)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .setCancellable(false)
                .setMaxProgress(100);

        if(token.equals(""))
        {
            Intent intent = new Intent(SendToBank.this, Login.class);
            startActivity(intent);
            overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
            finish();
        }

        bank_name.setOnClickListener(v -> {
            AlertDialog.Builder builder1;
            final AlertDialog dialog1;
            builder1 = new AlertDialog.Builder(SendToBank.this);
            View mview = getLayoutInflater().inflate(R.layout.area_dialog, null);
            TextView  header = mview.findViewById(R.id.DialogHeader);
            TextView  all = mview.findViewById(R.id.All);
            all.setVisibility(View.GONE);

            header.setText(getString(R.string.bank_name));

            DialogRecyclerView = mview.findViewById(R.id.Recycler_Dialog_cities);
            filterAreaAdapter1 = new filterAreaAdapterForCountryCode(DialogList, getApplicationContext());
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), OrientationHelper.VERTICAL, false);

            DialogRecyclerView.setLayoutManager(linearLayoutManager);
            DialogRecyclerView.setItemAnimator(new DefaultItemAnimator());
            initionilizationOFFilter();

            DialogRecyclerView.setAdapter(filterAreaAdapter1);
            builder1.setView(mview);
            dialog1 = builder1.create();
            Window window = dialog1.getWindow();
            window.setGravity(Gravity.CENTER);
            dialog1.show();

            filterAreaAdapter1.setOnItemClickListener(v1 -> {
                int position = DialogRecyclerView.getChildAdapterPosition(v1);
                //String position1 = (String.valueOf(position));
//                        AreaID = (Integer.parseInt(DialogList.get(position).ID));
//                        SelectedAreaName = DialogList.get(position).shopName;
//                        select_Area_All.setText(SelectedAreaName);
                //String countrylogo = DialogList.get(position).flag;
                //setImg(countrylogo,countryLogo);

                bank_Id=String.valueOf(DialogList.get(position).id);
                bank_name.setText(String.valueOf(DialogList.get(position).CityName));

                //phoneCode=DialogList.get(position).CityName;
                dialog1.dismiss();

            });
        });
        send.setOnClickListener(v -> {
            if (Objects.requireNonNull(amount_money.getText()).toString().equals(""))
            {
                if (Language.equals("ar"))
                {
                    amount_money.setError("من فضلك ادخل المبلغ");
                }
                else
                {
                    amount_money.setError("please enter amount of money");
                }
            }
            else if (Objects.requireNonNull(Card_Number.getText()).toString().equals(""))
            {
                if (Language.equals("ar"))
                {
                    Card_Number.setError("من فضلك ادخل رقم الكارت");
                }
                else
                {
                    Card_Number.setError("please enter Card Number");
                }
            }
            else if (bank_Id.equals("-1"))
            {
                if (Language.equals("ar"))
                {
                    Toast.makeText(this, "من فضلك اختر البنك", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(this, "please select Bank Name", Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                //Toast.makeText(this, "ok", Toast.LENGTH_SHORT).show();
                if (Validation.isConnected(SendToBank.this))
                {
                    hud.show();
                    sendToBankRequest   sendToBankRequest=new sendToBankRequest();
                    sendToBankRequest.setAccountNumber(Card_Number.getText().toString());
                    sendToBankRequest.setAmount(amount_money.getText().toString());
                    sendToBankRequest.setBankId(bank_Id);
                    mSubscriptions.add(NetworkUtil.getRetrofitByToken(token)
                            .SendToBank(sendToBankRequest)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io())
                            .subscribe(this::handleResponseSendToBank, this::handleError));
                }
                else
                {
                    buildDialog(SendToBank.this).show().setCanceledOnTouchOutside(false);
                }
            }
        });
        if (Validation.isConnected(SendToBank.this))
        {
            hud.show();
            mSubscriptions.add(NetworkUtil.getRetrofitByToken(DataManager.getInstance().getCashedAccessToken().getAccess_token())
                    .metaData()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResponse, this::handleError));
        }
        else
        {
            buildDialog(SendToBank.this).show().setCanceledOnTouchOutside(false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
        {
            Intent intent = new Intent(getApplicationContext(),Wallet.class);
            startActivity(intent);
            //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            //overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void handleResponseSendToBank(ErrorsModel errorsModel) {
        if (errorsModel.getMessage()==null)
        {
            String message="";
            if (Language.equals("ar"))
            {
                message="تم الارسال بنجاح";
            }
            else
            {
                message="Sent successfully";
            }

            android.support.v7.app.AlertDialog.Builder builder;
            android.support.v7.app.AlertDialog dialog;
            builder = new android.support.v7.app.AlertDialog.Builder(SendToBank.this);

            @SuppressLint("InflateParams")
            View mview = getLayoutInflater().inflate(R.layout.dialog_success, null);
            builder.setView(mview);
            dialog = builder.create();
            Window window = dialog.getWindow();
            if (window != null) {
                window.setGravity(Gravity.CENTER);
            }
            Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            dialog.show();
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            TextView txt = mview.findViewById(R.id.txt);
            TextView ok = mview.findViewById(R.id.ok);
            txt.setText(message);
            ok.setOnClickListener(v2 -> {
                dialog.dismiss();
                Intent intent = new Intent(getApplicationContext(),Wallet.class);
                startActivity(intent);
                finish();
            });
        }
        else
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(SendToBank.this);
            builder.setMessage(errorsModel.getMessage());
            builder.setIcon(R.drawable.ic_error);
            if (Language.equals("en"))
            {
                builder.setNegativeButton("Cancel", (dialogInterface, i) -> {
                });
            }
            else
            {
                builder.setNegativeButton("إلغاء", (dialogInterface, i) -> {
                });
            }
            builder.show();
        }
    }

    private void handleResponse(MetaDataResponseModel metaDataResponseModel) {
        //Toast.makeText(this, "11", Toast.LENGTH_SHORT).show();
        if (metaDataResponseModel.getModel()!=null)
        {
            if (metaDataResponseModel.getModel().getCarBrands()!=null)
            {
                banks       =metaDataResponseModel.getModel().getBanks();
                // carModels =metaDataResponseModel.getModel().getCarBrands().get(car_brand_Position).getCarModels();
                // Toast.makeText(this, metaDataResponseModel.getModel().getCountries().size()+"", Toast.LENGTH_SHORT).show();
            }
        }
        hud.dismiss();
    }
    private void handleError(Throwable throwable) {
        //Toast.makeText(getActivity(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
        String message="";
        try {
            if (throwable instanceof retrofit2.HttpException) {
                retrofit2.HttpException error = (HttpException) throwable;
                JSONObject jsonObject = new JSONObject(Objects.requireNonNull(((retrofit2.HttpException) throwable).response().errorBody()).string());

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
                    case "32":
                        message = getString(R.string.error_32);
                        break;

                    default:
                        message = getString(R.string.default_message);
                }
            }
            else
            {
                //Toast.makeText(AcceptOrRejectTrip.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                message = getString(R.string.default_message);            }

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
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        dialog.show();
        TextView txt = mview.findViewById(R.id.txt);
        TextView ok = mview.findViewById(R.id.ok);
        txt.setText(message);
        ok.setOnClickListener(v2 -> dialog.dismiss());
        hud.dismiss();
    }

    private void initionilizationOFFilter() {
        DialogList.clear();
        try {
            for (int k=0 ;k<banks.size();k++)
            {
                String name="";
                if (Language.equals("en"))
                {
                    name=banks.get(k).getName();
                }
                else
                {
                    name=banks.get(k).getNameLT();

                }
                DialogList.add(new filterAreaModelRecycler(
                        name
                        ,""
                        ,""
                        ,Integer.parseInt(banks.get(k).getId())));
            }
            filterAreaAdapter1.update(DialogList);
        }
        catch (Exception e)
        {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(),Wallet.class);
        startActivity(intent);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        //overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
        finish();
    }
}
