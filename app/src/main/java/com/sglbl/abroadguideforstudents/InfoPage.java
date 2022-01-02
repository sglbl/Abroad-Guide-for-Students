package com.sglbl.abroadguideforstudents;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.*;
import android.util.Log;
import android.view.*;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

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
    private int infoId, userId;

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
                infoId = Integer.parseInt( jsonObject.getString("id") );
                userId = Integer.parseInt( jsonObject.getString("u_id") );
                realText.setText( jsonObject.getString("text") );
                Bitmap bitmap = BitmapFactory.decodeStream((InputStream)new URL( jsonObject.getString("photo") ).getContent());
                photoLink.setImageBitmap(bitmap);
            }

        } //end of for loop.
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // it will add remove info option to menu.
        getMenuInflater().inflate(R.menu.info_page_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(SharedPrefManager.getInstance(this).getUserRole().equals("Informer") &&
            SharedPrefManager.getInstance(this).getUserId() == userId &&
                id == R.id.removeInfo) {
            removeInfo();
            //closing the activity because it's done.
            finish();
            //opening main page again.
            Toast.makeText(this,"Remove successful", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, DrawerActivity.class));
            return true;
        }
        else
            Toast.makeText(this,"Remove unsuccessful\n" +
                    "You are not the informer/owner of this info\n", Toast.LENGTH_SHORT).show();
            return false;
    }

    public void removeInfo(){
        progressDialog.setMessage("Removing info...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_INFO_REMOVE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            System.out.println("The message is ");
                            Log.i("tagconvertstr", "["+response+"]"); // to see error message if there is
                            JSONObject jsonObject = new JSONObject(response); //sending message with json method.
                            Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.hide();
                        Toast.makeText(getApplicationContext(),error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Nullable
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<>();
                params.put("id", Integer.toString(infoId) ); //sending info id to php file with json.
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}
