package com.sglbl.abroadguideforstudents;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.*;
import android.view.*;
import android.widget.*;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("FieldCanBeLocal")
public class InfoPage extends AppCompatActivity {
    private TextView titleText, realText;
    private ImageView photoLink;
    private JSONArray jsonArray;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_page);
        titleText = (TextView) findViewById(R.id.title);
        realText = (TextView) findViewById(R.id.text);
        photoLink = (ImageView) findViewById(R.id.photo);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait..");

        String json = getIntent().getStringExtra("EXTRA_JSON");
        try {
            jsonArray = new JSONArray(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String title = getIntent().getStringExtra("EXTRA_TITLE");
        try {
            putInfo(title);
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }

    private void putInfo(String title) throws JSONException, IOException {
        for(int i=0;i<jsonArray.length();i++) {
            //parsing jsonArray to jsonObjects one by one.
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
            //if "error" is null, then no error. //checking title value in json is equals with title.
            if(jsonObject.isNull("error") && jsonObject.getString("title").equals(title)){
                //Solving NetworkOnMainThreadException
                // solved this problem with the help of https://stackoverflow.com/a/46035352/13428129
                StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(threadPolicy);
                titleText.setText( jsonObject.getString("title") );
                //categoryText.setText( jsonObject.getString("category") );
                realText.setText( jsonObject.getString("text") );
                Bitmap bitmap = BitmapFactory.decodeStream((InputStream)new URL( jsonObject.getString("photo") ).getContent());
                photoLink.setImageBitmap(bitmap);
            }

        } //end of for loop.
    }

}
