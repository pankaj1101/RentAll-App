package com.rentall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

public class AdminRemoveProduct extends AppCompatActivity {


    private EditText P_id_remove;
    private Button remove_button;
    private Spinner spino;
    String[] product = {"Electronic", "Fashion", "Books", "Hardware", "Real Estate", "Vehicle", "Gym Equipment"};
    String product_items = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_remove_product);

        P_id_remove = findViewById(R.id.P_id_remove);
        remove_button = findViewById(R.id.remove_button);
        spino = findViewById(R.id.coursesspinner);

        remove_button.setOnClickListener(v -> {
            String product_id = P_id_remove.getText().toString().trim();
            Log.d("Productid", product_id);
            removeproduct(product_id);
        });

        ArrayAdapter ad = new ArrayAdapter(this, android.R.layout.simple_spinner_item, product);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spino.setAdapter(ad);

        spino.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                product_items = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void removeproduct(String product_id) {

       Task<Void> voidTask = Util.removeProduct(product_id,product_items);
        voidTask.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(AdminRemoveProduct.this, "Removed", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AdminRemoveProduct.this, "Not Found Removed", Toast.LENGTH_SHORT).show();
            }
        });

    }
}