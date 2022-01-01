package com.sglbl.abroadguideforstudents;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class InfoAdder extends AppCompatActivity implements View.OnClickListener {
    Button infoAdderButton;
    private RadioGroup rGroup;
    private ProgressDialog progressDialog;
    private EditText titleText, photoLinkText, infoText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_adder);

        rGroup  = findViewById(R.id.rGroup);
        infoAdderButton = findViewById(R.id.addInfoButton);
        infoAdderButton.setOnClickListener(this);

        titleText = findViewById(R.id.editTextForTitle);
        photoLinkText = findViewById(R.id.editTextForLink);
        infoText = findViewById(R.id.editTextForText);

        progressDialog = new ProgressDialog(this);
    }

    private void addInfoToDatabase() {
        final String id = String.valueOf(SharedPrefManager.getInstance(this).getUserId() );
        final String title = titleText.getText().toString().trim();
        final String link = photoLinkText.getText().toString().trim();
        final String info = infoText.getText().toString().trim();
        Button temp = findViewById( rGroup.getCheckedRadioButtonId() );
        final String category = temp.getText().toString();

        System.out.println( id + " " + title + " " + link + " " + info + " " + category );

        progressDialog.setMessage("Saving info...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_INFO_ADD,
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
                params.put("id", id);
                params.put("category", category);
                params.put("title", title);
                params.put("photo", link);
                params.put("text", info);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    @Override
    public void onClick(View v) {
        if(v == infoAdderButton)
            addInfoToDatabase();
    }
//
//    public void checkCategoryType(View v){
//        Button rButton = findViewById(rGroup.getCheckedRadioButtonId());
//        Toast.makeText(getApplicationContext(),"Selected user type: " + rButton.getText() ,Toast.LENGTH_SHORT).show();
//        category = rButton.getText().toString();
//    }

}
