package com.sglbl.abroadguideforstudents;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterPage extends AppCompatActivity implements View.OnClickListener {
    private Button rButton, registerButton;
    private RadioGroup rGroup;

    public static final String EXTRA_TEXT2 = "com.sglbl.abroadguideforstudents.transfer.EXTRA_TEXT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page);

        rGroup  = findViewById(R.id.rGroup);
        registerButton = (Button)findViewById(R.id.regButton);
        registerButton.setOnClickListener(v-> saveToDatabase());

    }

    private void saveToDatabase() {
        System.out.println( "Register info will be saved to database" );
        //çç
//        Intent i; //This is for opening game.
//        i = new Intent(this, DatabaseSaver.class);
//
//        rButton = findViewById( rGroup.getCheckedRadioButtonId() );
//        i.putExtra(EXTRA_TEXT2, rButton.getText()  );
//
//        startActivity(i);
    }

    public void checkUserType(View v){
        rButton = findViewById( rGroup.getCheckedRadioButtonId() );
        Toast.makeText(getApplicationContext(),"Selected user type: " + rButton.getText() ,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {

    }
}
