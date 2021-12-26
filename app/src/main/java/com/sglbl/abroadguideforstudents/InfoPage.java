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
public class InfoPage extends AppCompatActivity {
    private TextView titleText, realText,photoLinkText;
    private String jsonText;
    private ProgressDialog progressDialog;
    int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_page);
        titleText = (TextView) findViewById(R.id.title);
        realText = (TextView) findViewById(R.id.text);
        photoLinkText = (TextView) findViewById(R.id.photoLink);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait..");

        //gettingInfo();
        String infoId = getIntent().getStringExtra("EXTRA_ID");
        //Changing the text in the layout of info page.
        System.out.println(  "->Info id is " + SharedInfoManager.getInstance(this).getInfoId() );
        titleText.setText( String.valueOf(SharedInfoManager.getInstance(this).getInfoId() ) );
        titleText.setText(SharedInfoManager.getInstance(this).getInfoTitle());
        realText.setText(SharedInfoManager.getInstance(this).getInfoText());
        photoLinkText.setText(SharedInfoManager.getInstance(this).getInfoPhoto());
        jsonText = SharedInfoManager.getInstance(this).getJsonText();
    }

    private void gettingInfo(){
                        progressDialog.dismiss();
                        try {
                            //first converting response to json object.
                            String jsonText = SharedInfoManager.getInstance(this).getJsonText();
                            JSONObject jsonObject = new JSONObject(jsonText); //sending message with json method.
                            if(!jsonObject.getBoolean("error")){
                                SharedInfoManager.getInstance(getApplicationContext()).enteringInfoPage(
                                        id = jsonObject.getInt("id"),
                                        jsonObject.getString("category"),
                                        jsonObject.getString("title"),
                                        jsonObject.getString("text"),
                                        jsonObject.getString("photo"),
                                        jsonText //sending all json text.
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

}
