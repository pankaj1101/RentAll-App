package com.rentall;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainNavigation extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    private Home obj_home = new Home();
    private cart obj_cart = new cart();
    private Profile obj_profile = new Profile();
    private category obj_category = new category();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_navigation);

        bottomNavigationView = findViewById(R.id.bottom_navigation);


        getSupportFragmentManager().beginTransaction().replace(R.id.container, obj_home).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, obj_home).commit();
                        return true;
                    case R.id.Category:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, obj_category).commit();
                        return true;
                    case R.id.cart:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, obj_cart).commit();
                        return true;
                    case R.id.Profile:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, obj_profile).commit();
                        return true;
                }
                return false;
            }
        });

        Intent i = getIntent();
        String data = i.getStringExtra("FromReservation");

        if (data != null && data.contentEquals("1")) {

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.container, new Home());
            fragmentTransaction.commitNow();

            //menu id...
            bottomNavigationView.setSelectedItemId(R.id.home);

        }
    }
}