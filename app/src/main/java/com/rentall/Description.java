package com.rentall;

import static com.rentall.Method.AmountFormat.formatAmount;

import androidx.annotation.NonNull;

import android.view.Menu;
import android.view.View;
import android.os.Bundle;
import android.widget.*;
import android.content.Intent;
import android.graphics.Color;

import com.google.firebase.auth.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.*;

import java.io.Serializable;
import java.text.SimpleDateFormat;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.*;

public class Description extends AppCompatActivity {

    private ImageView imageView;
    private TextView tv1, tv2, tv3, tv4;
    private Button addCart;
    FirebaseAuth auth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private String emailid, productId, imageUrl, name, description, mobile_no;
    private double price, refund;

    HashMap<String, Object> receivedData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        imageView = findViewById(R.id.imageView);
        tv1 = findViewById(R.id.pname);
        tv2 = findViewById(R.id.pprice);
        tv3 = findViewById(R.id.prefund);
        tv4 = findViewById(R.id.pdesc);
        addCart = findViewById(R.id.add_cart_button);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        Intent intent = getIntent();
        Serializable serializableData = intent.getSerializableExtra("dataMap");

        receivedData = (HashMap<String, Object>) intent.getSerializableExtra("dataMap");

        if (serializableData != null && serializableData instanceof HashMap) {
            receivedData = (HashMap<String, Object>) serializableData;

            productId = (String) receivedData.get("productid");
            imageUrl = (String) receivedData.get("imageUrl");
            name = (String) receivedData.get("name");
            price = (double) receivedData.get("price");
            refund = (double) receivedData.get("refund");
            description = (String) receivedData.get("desc");
        }

        Picasso.get().load(imageUrl).into(imageView);
        tv1.setText(name);
        tv2.setText("₹" + formatAmount(price) + "/Month");
        tv3.setText("₹" + formatAmount(refund) + " Refundable Amount");
        tv4.setText(description);

        auth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        databaseReference = firebaseDatabase.getReference("users");
        emailid = auth.getCurrentUser().getEmail();
        fetch_data_for_mobile();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(view -> onBackPressed());
        return true;
    }

    private void fetch_data_for_mobile() {

        FirebaseUser user = auth.getCurrentUser();
        DatabaseReference rootNode = FirebaseDatabase.getInstance().getReference();
        DatabaseReference reference = rootNode.child("users");

        reference.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //mobile
                mobile_no = snapshot.child("mobile").getValue(String.class);
                addCart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        addCart.setBackgroundColor(Color.WHITE);
                        addCart.setTextColor(Color.BLACK);
                        addtocartlist();
                    }

                    private void addtocartlist() {
                        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                        String currentDate = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault()).format(new Date());

                        //HashMap Array
                        final Map<String, Object> cartMap = new HashMap<>();
                        cartMap.put("PID", productId);
                        cartMap.put("User Email:", emailid);
                        cartMap.put("Name", name);
                        cartMap.put("Price", price);
                        cartMap.put("Date", currentDate);
                        cartMap.put("Time", currentTime);
                        cartMap.put("Refund", refund);
                        cartMap.put("Mobile_no", mobile_no);
                        cartMap.put("ImageUrl", imageUrl);
                        databaseReference.child(user.getUid()).child("Cart Product").child(productId).setValue(cartMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(Description.this, "Added to Cart List", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(Description.this, MainNavigation.class));
                                    finish();
                                }
                            }
                        });
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                throw error.toException();
            }
        });
    }
}

