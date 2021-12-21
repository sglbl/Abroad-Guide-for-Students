package com.sglbl.abroadguideforstudents;

import android.app.ProgressDialog;
import android.content.Intent;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("FieldCanBeLocal")
public class InfoPage extends AppCompatActivity implements View.OnClickListener {
    Button closeButton;
    private TextView titleText, realText,photoLinkText;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_page);

        closeButton = (Button) findViewById(R.id.closeButton);
        closeButton.setOnClickListener(this);

        titleText = (TextView) findViewById(R.id.title);
        realText = (TextView) findViewById(R.id.text);
        photoLinkText = (TextView) findViewById(R.id.photoLink);
        //Changing the text in the layout of info page.
        titleText.setText( String.valueOf(SharedInfoManager.getInstance(this).getInfoId() ) );
        titleText.setText(SharedInfoManager.getInstance(this).getInfoTitle());
        realText.setText(SharedInfoManager.getInstance(this).getInfoText());
        photoLinkText.setText(SharedInfoManager.getInstance(this).getInfoPhoto());

        progressDialog = new ProgressDialog(this);
    }

    public int idGetterFromPreviousPage(){

    }

    private void gettingInfo(){
        final String id = çç;
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                Constants.URL_INFO,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            //first converting response to json object.
                            JSONObject jsonObject = new JSONObject(response); //sending message with json method.
                            if(!jsonObject.getBoolean("error")){
                                SharedInfoManager.getInstance(getApplicationContext()).enteringInfoPage(
                                        jsonObject.getInt("id"),
                                        jsonObject.getString("category"),
                                        jsonObject.getString("title"),
                                        jsonObject.getString("text"),
                                        jsonObject.getString("photo")
                                );
                                Toast.makeText(
                                        getApplicationContext(),
                                        "Getting info successful",
                                        Toast.LENGTH_LONG //it's about time that will stay.
                                ).show();
                            }else{
                                Toast.makeText(
                                        getApplicationContext(),
                                        jsonObject.getString("message"),
                                        Toast.LENGTH_LONG
                                ).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(
                                getApplicationContext(),
                                error.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("id", id);
                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

    }

    private void closeThePage() {
        finish();
        startActivity( new Intent(this, DrawerActivity.class)); // go back to login(main) page again.
    }

    @Override
    public void onClick(View v) {
        if(v == closeButton)
            closeThePage();
    }

}
