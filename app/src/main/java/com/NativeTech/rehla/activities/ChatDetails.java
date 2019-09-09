package com.NativeTech.rehla.activities;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.NativeTech.rehla.model.DataManager;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import com.NativeTech.rehla.model.data.dto.Models.Chats.ChatDetailsResponse;
import com.NativeTech.rehla.model.data.dto.Models.Chats.SendMessageModel;
import com.NativeTech.rehla.Network.NetworkUtil;
import com.NativeTech.rehla.R;
import com.NativeTech.rehla.Utills.Constant;
import com.NativeTech.rehla.Utills.Validation;
import com.NativeTech.rehla.adapters.Message;
import com.NativeTech.rehla.adapters.Messages;
import com.NativeTech.rehla.adapters.RecyclerViewAdapterChatRoom;
import com.NativeTech.rehla.services.SignalRService;
import retrofit2.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static com.NativeTech.rehla.Utills.Constant.PartnerIdentityId;
import static com.NativeTech.rehla.Utills.Constant.ReciverId;
import static com.NativeTech.rehla.Utills.Constant.ReciverName;
import static com.NativeTech.rehla.Utills.Constant.ReciverPhoto;
import static com.NativeTech.rehla.Utills.Constant.buildDialog;

public class ChatDetails extends AppCompatActivity {

    private AppCompatEditText editText;

    private SharedPreferences mSharedPreferences;
    private String token;
    private KProgressHUD hud;

    @SuppressLint("StaticFieldLeak")
    public static RecyclerView messagesView;
    private static Message message;
    @SuppressLint("StaticFieldLeak")
    //public static MessageAdapter adapter2;
    public static RecyclerViewAdapterChatRoom adapter2;
    public static List<Message> messageListView;

    private String ID = "";
    private String name = "";


    Messages messages = new Messages();

    private TextView no_exist;

    private final Context mContext = this;
    private SignalRService mService;
    private boolean mBound = false;


    private String Language;
    private CompositeSubscription mSubscriptions;

    private int page=0;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_details);

        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
         no_exist = findViewById(R.id.no_exist);

        no_exist.setVisibility(View.GONE);

        try {
        mSharedPreferences = getSharedPreferences("tokenDetail", MODE_PRIVATE);
        mSubscriptions = new CompositeSubscription();
        Language = mSharedPreferences.getString(Constant.language, Locale.getDefault().getLanguage());
        token = DataManager.getInstance().getCashedAccessToken().getAccess_token();
        ID = mSharedPreferences.getString(Constant.ID, "");
        name = mSharedPreferences.getString(Constant.Username, "");
        hud = KProgressHUD.create(ChatDetails.this)
                .setStyle(KProgressHUD.Style.PIE_DETERMINATE)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .setMaxProgress(100);

         getSupportActionBar().setTitle(ReciverName);

        Intent intent = new Intent();
        intent.setClass(mContext, SignalRService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);


            messagesView = findViewById(R.id.messages_view);
            //Id= Objects.requireNonNull(getIntent().getExtras()).getString("Id");
            messageListView = new ArrayList<>();
           // messageListView.add( new Message("hello", "", "", true));
           // messageListView.add( new Message("hello", "ahmed", "https://img1.cgtrader.com/items/642249/05fb311cc8/large/cartoon-office-man-3d-model-animated-rigged-max-obj-fbx-ma-mb-mtl-tga.jpg", false));
            initSharedPreferences();
            editText = findViewById(R.id.editText);
            //initMessages();

        hud.show();
        getChat();
        } catch (Exception e) {
        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
    }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
        {
            Intent intent = new Intent(getApplicationContext(),AllChats.class);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void getChat()
    {
        Constant.PartnerId=Constant.PartnerId==null?"":Constant.PartnerId;
        if (Validation.isConnected(ChatDetails.this) && !Constant.PartnerId.contentEquals("")) {
            mSubscriptions.add(NetworkUtil.getRetrofitByToken(token)
                    .seeAllMessages(Constant.PartnerId)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(chatResponseModel -> handleResponseSeeAllMessage(), this::handleError));

            mSubscriptions.add(NetworkUtil.getRetrofitByToken(token)
                    .getChatMessages(page,Constant.PartnerId)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResponse, this::handleError));

        } else {
            buildDialog(ChatDetails.this).show().setCanceledOnTouchOutside(false);
        }
    }

    private void handleResponseSeeAllMessage() {
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

    private void handleResponse(ChatDetailsResponse chatDetailsResponse)
    {
        //messageListView.clear();
        //Toast.makeText(this, "ddd", Toast.LENGTH_SHORT).show();
        if (chatDetailsResponse.getModel() != null)
        {
            for (int i = 0; i < chatDetailsResponse.getModel().size(); i++)
            {
                if (chatDetailsResponse.getModel().get(i).getSenderId().contentEquals(ID))
                {
                    messageListView.add( new Message(chatDetailsResponse.getModel().get(i).getMessage(),name , "", true,chatDetailsResponse.getModel().get(i).getCreationDate()));
                }
                else
                {
                    messageListView.add( new Message(chatDetailsResponse.getModel().get(i).getMessage(), ReciverName
                            , ReciverPhoto, false,chatDetailsResponse.getModel().get(i).getCreationDate()));
                }
            }
            if (messageListView.size() == 0) {
                no_exist.setVisibility(View.VISIBLE);
                hud.dismiss();
            }
            else if (chatDetailsResponse.getModel().size()==30)
            {
                page++;
                getChat();
            }
            else
            {
                initMessages();
                hud.dismiss();
            }
        }

    }

    private void initMessages(){
        adapter2=new RecyclerViewAdapterChatRoom(ChatDetails.this,messageListView);
        messagesView.setHasFixedSize(true);
        LinearLayoutManager mRecyclerViewLayoutManager2 = new LinearLayoutManager(ChatDetails.this, LinearLayoutManager.VERTICAL, false);
        messagesView.setLayoutManager(mRecyclerViewLayoutManager2);
        messagesView.setAdapter(adapter2);
        messagesView.scrollToPosition(Objects.requireNonNull(messagesView.getAdapter()).getItemCount() - 1);
        adapter2.notifyDataSetChanged();
    }
  /*  public void initMessagesFromService(){
        adapter2=new MessageAdapter(ChatDetails.this,messageListView);
        messagesView.setAdapter(adapter2);
        messagesView.setSelection(messagesView.getCount() - 1);
        adapter2.notifyDataSetChanged();
    }
*/

    private void initSharedPreferences() {
        mSharedPreferences=getSharedPreferences("tokenDetail",MODE_PRIVATE);
        token = DataManager.getInstance().getCashedAccessToken().getAccess_token();
    }

    public void sendMessage(View view) {

        if (mBound) {
            // Call a method from the SignalRService.
            // However, if this call were something that might hang, then this request should
            // occur in a separate thread to avoid slowing down the activity performance.
            if (editText != null && Objects.requireNonNull(editText.getText()).length() > 0 && !ID.contentEquals("")) {
                String message_body = editText.getText().toString();
                SendMessageModel sendMessageModel=new SendMessageModel(
                        editText.getText().toString()
                        ,PartnerIdentityId
                        ,ID
                        ,ReciverId);
                mService.sendMessage(sendMessageModel);
                SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                try {
                    message = new Message(editText.getText().toString(), "", "", true, format.format(Calendar.getInstance().getTime()));
                }catch (Exception e)
                {
                    message = new Message(editText.getText().toString(), "", "", true, "");

                }

                if(adapter2==null)
                {
                    if(messageListView==null)
                        messageListView=new ArrayList<>();
                    messageListView.add(message);
                    initMessages();
                }

                adapter2.add(message);
                messagesView.scrollToPosition(Objects.requireNonNull(messagesView.getAdapter()).getItemCount() - 1);
                no_exist.setVisibility(View.GONE);
            }
            if (editText != null) {
                editText.getText().clear();
            }

        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        mBound = false;
        Intent intent = new Intent();
        intent.setClass(mContext, SignalRService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);

    }

    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(),AllChats.class);
        startActivity(intent);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
        finish();
    }

    @Override
    protected void onStop() {
        // Unbind from the service
        if (mBound) {
            unbindService(mConnection);
            mBound = false;
        }
        super.onStop();
    }

    /**
     * Defines callbacks for service binding, passed to bindService()
     */
    private final ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to SignalRService, cast the IBinder and get SignalRService instance
            SignalRService.LocalBinder binder = (SignalRService.LocalBinder) service;
            mService = binder.getService();
           // Toast.makeText(mContext, "onServiceConnected", Toast.LENGTH_SHORT).show();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
           // Toast.makeText(mContext, "Disconnected", Toast.LENGTH_SHORT).show();
            mBound = false;
        }
    };
}