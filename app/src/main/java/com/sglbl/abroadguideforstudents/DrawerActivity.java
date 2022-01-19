package com.sglbl.abroadguideforstudents;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.sglbl.abroadguideforstudents.databinding.ActivityDrawerBinding;

public class DrawerActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityDrawerBinding binding;
    private TextView textViewId, textViewRole, textViewName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDrawerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if(!SharedPrefManager.getInstance(this).isLoggedIn()){ //if user is not logged in.
            finish();
            startActivity( new Intent(this, Main.class)); // go back to login(main) page again.
        }

        setSupportActionBar(binding.appBarDrawer.toolbar);
        binding.appBarDrawer.floatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!textViewRole.getText().toString().equals("Informer")) {
                    Snackbar.make(view, "User is not an informer", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return;
                }
                startActivity(new Intent(getApplicationContext(), InfoAdder.class ));
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.accommodation, R.id.transportation, R.id.general)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_drawer);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        // nav_view.xml is not the first layout that this class uses.
        // So we need to create a header view from navigation view
        // and then we can edit textViews here.
        //NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view); //no need because binded already.
        View headerView = navigationView.getHeaderView(0);
        textViewId = (TextView) headerView.findViewById(R.id.textViewUserId);
        textViewRole=(TextView) headerView.findViewById(R.id.textViewUserRole);
        textViewName =(TextView) headerView.findViewById(R.id.textViewUserName);

        textViewId.setText( String.valueOf(SharedPrefManager.getInstance(this).getUserId() ) );
        textViewRole.setText(SharedPrefManager.getInstance(this).getUserRole());
        textViewName.setText(SharedPrefManager.getInstance(this).getUserName());

    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_drawer);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // so it will add logout and profile option to menu.
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.logout) {
            //this method is for clearing data that is saved on sharedPreferences.
            SharedPrefManager.getInstance(this).logout();
            //closing the activity because it's done.
            finish();
            //opening main page again.
            startActivity(new Intent(this, Main.class));
        }
        else if(id == R.id.libraries){
            Toast.makeText(this,"This app uses free open source Volley library\n" +
                    "to make networking for this Android app easier and faster with RequestQueue\n" +
                    "It's available on https://developer.android.com/training/volley", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

}