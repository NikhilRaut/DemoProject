package com.ormdatabase.API;

import android.content.Context;
import android.util.Log;


import retrofit.RequestInterceptor;

/**
 * Created by Nikhil on 05-06-2016.
 */
public class MyRequestInterceptor implements RequestInterceptor {
    Context context;

    public MyRequestInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public void intercept(RequestFacade request) {
//        if (token != null) {
//            Log.e("Token", "token-------------------------------" + token);
//            request.addHeader("Authorization", "Token token=" + token.replace("\"", ""));
//            //request.addHeader("Authorization", "Bearer 9e4952c254fedaa2c1e6eda30ed745ee9af8129432b99eb45c2d46744bac6b3f");
//
////            9e4952c254fedaa2c1e6eda30ed745ee9af8129432b99eb45c2d46744bac6b3f
//        }
    }
}
