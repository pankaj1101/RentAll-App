package com.rentall;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Admin_category extends AppCompatActivity {

    private TextView electronics, fashions, books, equipment, hardWare, vehicles, estate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);

        electronics = findViewById(R.id.Electronics);
        fashions = findViewById(R.id.Fashions);
        books = findViewById(R.id.Books);
        equipment = findViewById(R.id.Equipment);
        hardWare = findViewById(R.id.HardWare);
        vehicles = findViewById(R.id.Vehicles);
        estate = findViewById(R.id.estate);

        electronics.setOnClickListener(v -> {
            Intent intent = new Intent(Admin_category.this, Admin_Product_Detail.class);
            intent.putExtra("admin_category_name", "Electronic");
            startActivity(intent);
        });
        fashions.setOnClickListener(v -> {
            Intent intent = new Intent(Admin_category.this, Admin_Product_Detail.class);
            intent.putExtra("admin_category_name", "Fashion");
            startActivity(intent);
        });
        books.setOnClickListener(v -> {
            Intent intent = new Intent(Admin_category.this, Admin_Product_Detail.class);
            intent.putExtra("admin_category_name", "Books");
            startActivity(intent);
        });
        equipment.setOnClickListener(v -> {
            Intent intent = new Intent(Admin_category.this, Admin_Product_Detail.class);
            intent.putExtra("admin_category_name", "Gym Equipment");
            startActivity(intent);
        });
        hardWare.setOnClickListener(v -> {
            Intent intent = new Intent(Admin_category.this, Admin_Product_Detail.class);
            intent.putExtra("admin_category_name", "Hardware");
            startActivity(intent);
        });
        vehicles.setOnClickListener(v -> {
            Intent intent = new Intent(Admin_category.this, Admin_Product_Detail.class);
            intent.putExtra("admin_category_name", "Vehicle");
            startActivity(intent);
        });
        estate.setOnClickListener(v -> {
            Intent intent = new Intent(Admin_category.this, Admin_Product_Detail.class);
            intent.putExtra("admin_category_name", "Real Estate");
            startActivity(intent);
        });

    }
}