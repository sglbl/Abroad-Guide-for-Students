package com.sglbl.abroadguideforstudents;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.widget.LinearLayout.LayoutParams;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@SuppressLint("SetTextI18n")
public class Technical extends Fragment {
    private ProgressDialog progressDialog;
    private LinearLayout linearLayout;
    private Intent infoPageIntent;
    private int id = 0;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_technical, container, false);
        linearLayout = (LinearLayout) v.findViewById(R.id.ll_example);

        String textToMakeClickable = "programmatically created clickable text";
        addToLayout(textToMakeClickable);

        getAllInfoFromDatabase();

        return v;
    }

    public void addToLayout(String textToMakeClickable){
        //Making text clickable
        SpannableStringBuilder sb = new SpannableStringBuilder();
        sb.append(textToMakeClickable);
        sb.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                //sending title to other InfoPage activity.
                infoPageIntent.putExtra("EXTRA_TITLE", textToMakeClickable);
                startActivity( infoPageIntent ); // go to info page.
            }
        }, 0, sb.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait..");
        //we need to call getActivity because we cannot use 'this' in fragment as Context
        // Add textview dynamically
        TextView textView = new TextView(getActivity());
        LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(10, 15, 10, 10); // (left, top, right, bottom)
        textView.setLayoutParams(layoutParams);
        textView.setText(sb);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        linearLayout.addView(textView);

    }

    public void getAllInfoFromDatabase(){
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                Constants.URL_INFO,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            //first converting response to json array.
                            JSONArray jsonArray = new JSONArray(response);
                            //creating an intent for new class that will be called.
                            infoPageIntent = new Intent( getActivity(), InfoPage.class);
                            //sending id to other InfoPage class.
                            infoPageIntent.putExtra("EXTRA_JSON", response);

                            for(int i=0;i<jsonArray.length();i++) {
                                System.out.println(jsonArray.getString(i));
                                //then parsing jsonArray to jsonObjects one by one.
                                JSONObject jsonObject = (JSONObject) jsonArray.get(i);

                                if (jsonObject.isNull("error")) { //if "error" is null, then no error.
                                    Toast.makeText(
                                            getActivity().getApplicationContext(),
                                            "Getting info successful",
                                            Toast.LENGTH_LONG //it's about time that will stay.
                                    ).show();
                                    addToLayout( jsonObject.getString("title") );
                                } else { //if "error" is not null, then there is error.
                                    Toast.makeText(
                                            getActivity().getApplicationContext(),
                                            jsonObject.getString("message"),
                                            Toast.LENGTH_LONG
                                    ).show();
                                }
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
                                getActivity().getApplicationContext(),
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
                params.put("id", String.valueOf(id) );
                return params;
            }
        };

        RequestHandler.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }



}
