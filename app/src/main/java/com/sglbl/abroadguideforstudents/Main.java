package com.sglbl.abroadguideforstudents;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Main extends AppCompatActivity implements View.OnClickListener {
    private RadioGroup rGroup;
    private RadioButton rbutton;
    private Button loginButton;
    private EditText idText, pwText;
    private ProgressDialog progressDialog;
    public static final String EXTRA_TEXT = "com.sglbl.abroadguideforstudents.transfer.EXTRA_TEXT";
    public static final String EXTRA_NUMB = "com.sglbl.abroadguideforstudents.transfer.EXTRA_NUMB";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        rGroup  = findViewById(R.id.rGroup);
        loginButton = (Button) findViewById(R.id.loginButton);
        //loginButton.setOnClickListener(v -> openSecondPage());

        Button registerButton = (Button) findViewById(R.id.regButton);
        registerButton.setOnClickListener(v-> openRegisterPage());

        Button documentationButton = (Button) findViewById(R.id.documentationButton);
        documentationButton.setOnClickListener(v-> openDocumentationPage());

        Button enteringButton = (Button)findViewById(R.id.enteringButton);
        enteringButton.setOnClickListener(v-> openInfoPage());

        idText = (EditText) findViewById(R.id.editTextForId);
        pwText = (EditText) findViewById(R.id.editTextPassword);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait..");

    }

    private void userLogin(){
        final String id = idText.getText().toString().trim();
        final String password = pwText.getText().toString().trim();
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            System.out.println("The message is ");
                            Log.i("tagconvertstr", "["+response+"]"); // to see error message if there is
                            //first converting response to json object.
                            JSONObject jsonObject = new JSONObject(response); //sending message with json method.
                            if(jsonObject.getBoolean("error")){
                                SharedPrefManager.getInstance(getApplicationContext()).userLogin(
                                    jsonObject.getInt("id"),
                                    jsonObject.getString("role"),
                                    jsonObject.getString("name_surname")
                                );
                                Toast.makeText(
                                        getApplicationContext(),
                                        "User login successful",
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
                params.put("password", password);
                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

    }

    private void openDocumentationPage() {
        Intent i; //This is for opening game.
        i = new Intent(this, DocumentationPage.class);

        startActivity(i);
    }

    private void openInfoPage() {
        Intent i; //This is for opening game.
        i = new Intent(this, DrawerActivity.class);

        startActivity(i);
    }

    public void openSecondPage(){
        Intent i; //This is for opening game.
        i = new Intent(this, MainActivity.class);

        rbutton = findViewById( rGroup.getCheckedRadioButtonId() );
        i.putExtra(EXTRA_TEXT, rbutton.getText()  );

        startActivity(i);
    }

    public void checkUserType(View v){
        rbutton = findViewById( rGroup.getCheckedRadioButtonId() );
        Toast.makeText(getApplicationContext(),"Selected user type: " + rbutton.getText() ,Toast.LENGTH_SHORT).show();
    }


    public void openRegisterPage(){
        Intent i; //This is for opening game.
        i = new Intent(this, RegisterPage.class);

        startActivity(i);
    }


    @Override
    public void onClick(View view) {
        if(view == loginButton){ //onclick listener checks if user clicked on that button
            userLogin();
        }
    }
}