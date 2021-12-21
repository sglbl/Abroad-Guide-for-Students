package com.sglbl.abroadguideforstudents;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

@SuppressLint("StaticFieldLeak")
public class SharedInfoManager {
    private static SharedInfoManager instance;
    private static Context ctx;

    private static final String SHARED_PREF_NAME = "mysharedinfo";
    private static final String KEY_ID = "infoid";
    private static final String KEY_CATEGORY = "category";
    private static final String KEY_TITLE = "title";
    private static final String KEY_TEXT = "text";
    private static final String KEY_PHOTO = "photo"; //link of photo

    private SharedInfoManager(Context context) {
        ctx = context;
    }

    public static synchronized SharedInfoManager getInstance(Context context) {
        if (instance == null) {
            instance = new SharedInfoManager(context);
        }
        return instance;
    }

    public boolean enteringInfoPage(int id, String category, String title, String text, String photo){
        /* These means that only this application access these */
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        //After user login with correct data these will stored on application.
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //Need to add these to editor and apply.
        editor.putInt(KEY_ID,id);
        editor.putString(KEY_CATEGORY, category);
        editor.putString(KEY_TITLE, title);
        editor.putString(KEY_TEXT, text);
        editor.putString(KEY_PHOTO, photo);
        editor.apply();

        return true;
    }

    public boolean isInfoPageOpen(){
        /* These means that only this application access these */
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        /* Will check KEY_ID and if it's not same will return -1 (And then it will check if it's not -1)*/
        if(sharedPreferences.getInt(KEY_ID, -1) != -1){
            return true;
        }
        return false;
    }

    public int getInfoId(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_ID, -1);
    }

    public String getInfoTitle(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_TITLE, null); //return title, if empty return null
    }

    public String getInfoText(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_TEXT, null); //return text, if empty return null
    }

    public String getInfoPhoto(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_PHOTO, null); //return photo link, if empty return null
    }

    public void clearInfoPage(){
        /* These means that only this application access these */
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear(); // clear all info values from editor.
        editor.apply();
    }

}