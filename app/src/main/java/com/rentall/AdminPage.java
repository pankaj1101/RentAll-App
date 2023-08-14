package com.rentall;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

public class AdminPage extends AppCompatActivity {

    private TextView btn_add, btn_view, btn_remove;
    ImageView logout_btn;
    SharedPreferences shp;
    SharedPreferences.Editor shpEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);

//        Toolbar toolbar = findViewById(R.id.toolbar);

        btn_add = findViewById(R.id.btn_add);
        btn_view = findViewById(R.id.btn_view);
        btn_remove = findViewById(R.id.btn_remove);
        logout_btn = findViewById(R.id.logout_btn);

        btn_add.setOnClickListener(v -> {
            Intent intent = new Intent(this, AdminAddProduct.class);
            startActivity(intent);
        });
        btn_view.setOnClickListener(v -> {
            startActivity(new Intent(this, Admin_category.class));
        });
        btn_remove.setOnClickListener(v -> {
            startActivity(new Intent(AdminPage.this, AdminRemoveProduct.class));
        });

        shp = getSharedPreferences("myPreferences", MODE_PRIVATE);
        logout_btn.setOnClickListener(v -> {
            Log.d("logout","Clicked");
            logout_alert_display();
        });

    }


    private void logout_alert_display() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to Logout?").setTitle("Alert !").setCancelable(false);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    if (shp == null)

                        shp = getSharedPreferences("myPreferences", MODE_PRIVATE);
                    shpEditor = shp.edit();
                    shpEditor.putString("name", "");
                    shpEditor.commit();

                    Intent i = new Intent(AdminPage.this, LoginActivity.class);
                    startActivity(i);
                    finish();

                } catch (Exception ex) {
                    Toast.makeText(AdminPage.this, ex.getMessage().toString(), Toast.LENGTH_LONG).show();
                }
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}