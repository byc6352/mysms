package com.fyc.mysms;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import utils.MyLog;

public class HeadlessSmsSendService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
        MyLog.i("HeadlessSmsSendService onCreate");
        SmsObserver.registerServer(this);
        MyReceiver.register(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
        //return new com.fyc.cp.ProcessConnection.Stub() {};
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        SmsObserver.unRegisterServer();
        MyReceiver.unregister(this);
    }

    @Override
    public boolean stopService(Intent name) {
        MyLog.d( "stopService() executed");

        return super.stopService(name);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }
}


