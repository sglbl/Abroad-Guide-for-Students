package com.sglbl.abroadguideforstudents;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Main extends AppCompatActivity {
    private Button loginButton;
    private Button registerButton;
    private RadioGroup rGroup;
    private RadioButton rbutton;
    public static final String EXTRA_TEXT = "com.sglbl.hexgame.transfer.EXTRA_TEXT";
    public static final String EXTRA_NUMB = "com.sglbl.hexgame.transfer.EXTRA_NUMB";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        rGroup  = findViewById(R.id.rGroup);
        loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(v -> openSecondPage());
        registerButton = (Button)findViewById(R.id.regButton);
        registerButton.setOnClickListener(v-> openRegisterPage());
    }

    public void openSecondPage(){
        Intent i; //This is for opening game.
        i = new Intent(this, MainActivity.class);

        rbutton = findViewById( rGroup.getCheckedRadioButtonId() );
        i.putExtra(EXTRA_TEXT, rbutton.getText()  );

        startActivity(i);
    }

    public void openRegisterPage(){
        Intent i; //This is for opening game.
        i = new Intent(this, RegisterPage.class);

        startActivity(i);
    }




}