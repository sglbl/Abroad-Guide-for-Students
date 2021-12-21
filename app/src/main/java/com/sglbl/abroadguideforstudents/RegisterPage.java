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

public class RegisterPage extends AppCompatActivity implements View.OnClickListener {
    Button registerButton;
    private RadioGroup rGroup;
    private ProgressDialog progressDialog;
    private EditText idText, nameText, pwText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page);

        if(SharedPrefManager.getInstance(this).isLoggedIn()){ //if user is logged in turn this off.
            finish();
            startActivity(new Intent(this, DrawerActivity.class));
            return; //because we don't want the code below because we are already logged in.
        }

        rGroup  = findViewById(R.id.rGroup);
        registerButton = (Button) findViewById(R.id.regButton);
        registerButton.setOnClickListener(this);

        idText = (EditText) findViewById(R.id.editTextForId);
        nameText = (EditText) findViewById(R.id.editTextPersonName);
        pwText = (EditText) findViewById(R.id.editTextPassword);

        progressDialog = new ProgressDialog(this);

    }

    private void registerUser() {
        final String userID = idText.getText().toString().trim();
        final String name = nameText.getText().toString().trim();
        final String password = pwText.getText().toString().trim();
        final String system = "system";
        Button temp = findViewById( rGroup.getCheckedRadioButtonId() );
        final String role = temp.getText().toString();
        System.out.println("Role is " + role + " " + userID+ " " + name + " " + password + " "+ system );

        progressDialog.setMessage("Registering user...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_REGISTER,
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
                params.put("id", userID);
                params.put("role", role);
                params.put("password", password);
                params.put("name_surname", name);
                params.put("s_name", system);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    @Override
    public void onClick(View v) {
        if(v == registerButton)
            registerUser();
    }

    public void checkUserType(View v){
        Button rButton = findViewById(rGroup.getCheckedRadioButtonId());
        Toast.makeText(getApplicationContext(),"Selected user type: " + rButton.getText() ,Toast.LENGTH_SHORT).show();
    }

}
