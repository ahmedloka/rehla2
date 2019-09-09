package com.NativeTech.rehla.services;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

import com.NativeTech.rehla.App;
import com.NativeTech.rehla.R;
import com.NativeTech.rehla.model.DataManager;
import com.NativeTech.rehla.model.data.dto.Models.Chats.SendMessageModel;
import com.NativeTech.rehla.Utills.Constant;
import com.NativeTech.rehla.Utills.TerhalUtils;
import com.NativeTech.rehla.activities.ChatDetails;
import com.NativeTech.rehla.adapters.Message;

import microsoft.aspnet.signalr.client.Credentials;
import microsoft.aspnet.signalr.client.Platform;
import microsoft.aspnet.signalr.client.SignalRFuture;
import microsoft.aspnet.signalr.client.http.android.AndroidPlatformComponent;
import microsoft.aspnet.signalr.client.hubs.HubConnection;
import microsoft.aspnet.signalr.client.hubs.HubProxy;
import microsoft.aspnet.signalr.client.transport.ClientTransport;
import microsoft.aspnet.signalr.client.transport.ServerSentEventsTransport;

import static com.NativeTech.rehla.Utills.Constant.ReciverName;
import static com.NativeTech.rehla.Utills.Constant.ReciverPhoto;


public class SignalRService extends Service {
    private HubConnection mHubConnection;
    private HubProxy mHubProxy;
    private Handler mHandler; // to display Toast message
    private final IBinder mBinder = new LocalBinder(); // Binder given to clients


    private SharedPreferences mSharedPreferences;
    private String token;

    public SignalRService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mSharedPreferences=getSharedPreferences("tokenDetail",MODE_PRIVATE);
        token = DataManager.getInstance().getCashedAccessToken().getAccess_token();
        mHandler = new Handler(Looper.getMainLooper());


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int result = super.onStartCommand(intent, flags, startId);
        startSignalR();
        return result;
    }

    @Override
    public void onDestroy() {


        mHubProxy.invoke("Disconnect");
        mHubConnection.stop();
        super.onDestroy();
    }



    @Override
    public IBinder onBind(Intent intent) {
        // Return the communication channel to the service.
        startSignalR();
        return mBinder;
    }

    /**
     * Class used for the client Binder.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with IPC.
     */
    public class LocalBinder extends Binder {
        public SignalRService getService() {
            // Return this instance of SignalRService so clients can call public methods
            return SignalRService.this;
        }
    }

    /**
     * method for clients (activities)
     */
    public void sendMessage(SendMessageModel message) {
        String SERVER_METHOD_SEND = "SendToUser";
        mHubProxy.invoke(SERVER_METHOD_SEND,
                message.getMessage(),
                message.getReciverIdentifier()
                ,message.getMyId()
                ,message.getReciverId()).done(
                aVoid -> {
                    //   Toast.makeText(getApplicationContext(), "Join", Toast.LENGTH_SHORT).show();
                    Log.d("<Debug", "Join"); // won't work!
                }
        ).onError(throwable -> {
            Log.d("<Debug", throwable.getMessage()); // won't work!

            // Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
        }).onCancelled(() -> {
            Log.d("<Debug", "Join"); // won't work!

        });

        //Toast.makeText(this, "ffff", Toast.LENGTH_SHORT).show();
    }

    private void startSignalR() {
        try {
            Platform.loadPlatformComponent(new AndroidPlatformComponent());
            Credentials credentials = request -> request.addHeader("Authorization", "Bearer " + token);
            String serverUrl = "http://api.rehlacar.com/";
            mHubConnection = new HubConnection(serverUrl);
            mHubConnection.setCredentials(credentials);
            String SERVER_HUB_CHAT = "ChatHub";
            mHubProxy = mHubConnection.createHubProxy(SERVER_HUB_CHAT);
            ClientTransport clientTransport = new ServerSentEventsTransport(mHubConnection.getLogger());
            SignalRFuture<Void> signalRFuture = mHubConnection.start(clientTransport);
            try {
                signalRFuture.get();
            }
            catch (InterruptedException | ExecutionException e) {
                //e.printStackTrace();
                Toast.makeText(this, App.mContext.getResources().getString(R.string.default_message), Toast.LENGTH_SHORT).show();
                return;
            }

            new Handler().postDelayed(() -> {
                mHubProxy.invoke("Join").done(
                        aVoid -> {
                            //   Toast.makeText(getApplicationContext(), "Join", Toast.LENGTH_SHORT).show();
                            Log.d("<Debug", "Join"); // won't work!
                        }
                ).onError(throwable -> {
                    Log.d("<Debug", throwable.getMessage()); // won't work!

                    // Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                }).onCancelled(() -> {
                    Log.d("<Debug", "Join"); // won't work!
                });

                //String HELLO_MSG = "Hello from Android!";
                //sendMessage(HELLO_MSG);

                String CLIENT_METHOD_BROADAST_MESSAGE = "broadcastMessage";
                mHubProxy.on(CLIENT_METHOD_BROADAST_MESSAGE, (message, senderId, reciverId ,DateTime) -> {
                    final String finalMsg = message ;
                    final String DateTimen = DateTime ;
                    // display Toast message
                    mHandler.post(() -> {

                        ChatDetails.messageListView.add( new Message(finalMsg, ReciverName, ReciverPhoto, false,DateTimen));
                        //initMessages
                        TerhalUtils.initMessages(getApplicationContext());
                        //Toast.makeText(getApplicationContext(), finalMsg, Toast.LENGTH_LONG).show();
                    });
                },  String.class,String.class,String.class,String.class);
            }, 1000);

        }
        catch (Exception e)
        {
           // e.printStackTrace();
        }

    }
}