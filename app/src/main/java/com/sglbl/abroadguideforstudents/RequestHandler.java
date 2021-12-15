package com.sglbl.abroadguideforstudents;

import android.annotation.SuppressLint;
import android.content.*;

import com.android.volley.*;
import com.android.volley.toolbox.Volley;

@SuppressLint("StaticFieldLeak")
public class RequestHandler {
    private static RequestHandler instance;
    private RequestQueue requestQueue;
    private static Context ctx;

    private RequestHandler(Context context) {
        ctx = context;
        requestQueue = getRequestQueue();

    }

    public static synchronized RequestHandler getInstance(Context context) {
        if (instance == null) {
            instance = new RequestHandler(context);
        }
        return instance;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            requestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

}