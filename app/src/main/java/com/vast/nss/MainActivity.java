package com.vast.nss;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.vast.nss.Fragment.EventFragment;
import com.vast.nss.Fragment.ProfileFragment;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, EventFragment.ClickListnerEvent {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.logo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        BottomNavigationView navigation =  findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);

        loadFragment(new EventFragment(this));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    private boolean loadFragment(Fragment fragment){
        if(fragment != null){

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();

            return true;
        }

        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;

        switch(menuItem.getItemId()){

            case R.id.navigation_event:
                fragment = new EventFragment(MainActivity.this);

                break;

            case R.id.navigation_attendance:
                fragment = new ProfileFragment();
                break;

            case R.id.navigation_profile:
                fragment = new ProfileFragment();
                break;

        }

        return loadFragment(fragment);
    }

    @Override
    public void clicked() {
        Intent intent = new Intent(MainActivity.this, EventCreationActivity.class);
        startActivity(intent);
    }
}
