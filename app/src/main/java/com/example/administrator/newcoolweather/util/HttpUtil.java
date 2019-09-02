package com.example.administrator.newcoolweather.util;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by Administrator on 2019/8/30 0030.
 */

public class HttpUtil {
    //参数如下
    public static void  sendHttpRequest(final String address, Callback callback){
        OkHttpClient client = new OkHttpClient ();
        Request request = new Request.Builder ().url (address).build ();
        client.newCall (request).
                enqueue (callback);
    }
}
