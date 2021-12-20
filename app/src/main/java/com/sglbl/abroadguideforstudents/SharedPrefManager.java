package com.sglbl.abroadguideforstudents;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

@SuppressLint("StaticFieldLeak")
public class SharedPrefManager {
    private static SharedPrefManager instance;
    private static Context ctx;

    private static final String SHARED_PREF_NAME = "mysharedpref";
    private static final String KEY_ID = "userid";
    private static final String KEY_ROLE = "role";
    private static final String KEY_NAME = "name_surname";

    private SharedPrefManager(Context context) {
        ctx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPrefManager(context);
        }
        return instance;
    }

    public boolean userLogin(int id, String role, String name_surname){
        /* These means that only this application access these */
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        //After user login with correct data these will stored on application.
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //Need to add these to editor and apply.
        editor.putInt(KEY_ID,id);
        editor.putString(KEY_ROLE, role);
        editor.putString(KEY_NAME, name_surname);
        editor.apply();

        return true;
    }

    public boolean isLoggedIn(){
        /* These means that only this application access these */
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        /* Will check KEY_ID and if it's not same will return -1 (And then it will check if it's not -1)*/
        if(sharedPreferences.getInt(KEY_ID, -1) != -1){
            return true;
        }
        return false;
    }

    public String getUserName(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_NAME, null); //return name, if empty return null
    }

    public String getUserRole(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_ROLE, null); //return role, if empty return null
    }

    public int getUserId(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_ID, -1);
    }

    public boolean logout(){
        /* These means that only this application access these */
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear(); // clear all values from editor to log out and clear user info from system login.
        editor.apply();
        return true;
    }

}