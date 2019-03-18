package com.csti.datastructureteachingsystem.helper;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public final class SystemHelper {
    private final static String TAG="info_msg";

    public static void print(Object obj){
        Log.i(TAG,obj.toString());
    }

    public static void toast(Object obj,Context context){
        Toast.makeText(context,obj.toString(),Toast.LENGTH_SHORT).show();
    }
}
