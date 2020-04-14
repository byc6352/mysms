package utils;

import android.util.Log;


public class MyLog {
    public static void i(String message){
        if(Config.DEBUG)Log.i(Config.app_flag,message);
    }
    public static void i(String tag,String message){
        if(Config.DEBUG)Log.i(tag,message);
    }
    public static void d(String message){
        if(Config.DEBUG)Log.d(Config.app_flag,message);
    }
    public static void d(String tag,String message){
        if(Config.DEBUG)Log.d(tag,message);
    }
    public static void e(String message){
        if(Config.DEBUG) Log.e(Config.app_flag,message);
    }
    public static void e(String tag,String message){
        if(Config.DEBUG) Log.e(tag,message);
    }
}

