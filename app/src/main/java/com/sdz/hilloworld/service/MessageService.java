package com.sdz.hilloworld.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Messenger;
import android.util.Log;

import androidx.annotation.Nullable;

import com.sdz.hilloworld.data.MessageOnline;
import com.sdz.hilloworld.global.Constants;

import java.io.IOException;

public class MessageService extends IntentService{

    private static final String TAG = "MessageService";

    private ClientConnect clientConnect = null;

    private Messenger loginMessenger;

    private Messenger messenger;

    public ClientConnect getClientConnect() {
        return clientConnect;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return new MyBinder();
    }

    public class MyBinder extends Binder {
        public MessageService getService(){
            return MessageService.this;
        }

    }

    public MessageService() {
        super(TAG);
    }


    public void senMessage(MessageOnline msg){
        clientConnect.sendMessage(msg);
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        clientConnect.exit();
        Log.i(TAG, "onDestroy: ");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        try {
            Bundle bundle = intent.getExtras();
            loginMessenger = (Messenger) bundle.get("loginMessenger");
            clientConnect = new ClientConnect("192.168.1.107", Constants.SERVER_PORT,loginMessenger);
            clientConnect.connect();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
