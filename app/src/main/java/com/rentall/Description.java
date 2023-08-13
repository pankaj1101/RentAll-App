package com.rentall;

import androidx.annotation.NonNull;

import android.view.View;
import android.os.Bundle;
import android.widget.*;
import android.content.Intent;
import android.graphics.Color;

import com.google.firebase.auth.*;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.*;

import java.text.SimpleDateFormat;

import com.bumptech.glide.Glide;

import java.util.*;

public class Description extends AppCompatActivity {

    private ImageView imageView;
    private TextView tv1, tv2, tv3, tv4;
    private Button addCart;

    FirebaseAuth auth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    private String emailid, img, product_id;

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

        Intent intent = getIntent();
        img = intent.getExtras().getString("imageUrl");
        Glide.with(getApplicationContext()).load(img).into(imageView);

        imageView.setImageResource(getIntent().getIntExtra("imageUrl", 0));
        tv1.setText(getIntent().getStringExtra("name"));
        tv2.setText("Amount : " + getIntent().getStringExtra("price") + " Rs" + "/month");

        tv3.setText("Refundable amount : " + getIntent().getStringExtra("refund") + " Rs");
        tv4.setText(getIntent().getStringExtra("desc"));

        auth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        //database name -> Cart
        databaseReference = firebaseDatabase.getReference("Cart");

        emailid = auth.getCurrentUser().getEmail();
        fatch_data_for_mobile();

    }

    private void fatch_data_for_mobile() {

        FirebaseUser user = auth.getCurrentUser();
        DatabaseReference rootNode = FirebaseDatabase.getInstance().getReference();
        DatabaseReference reference = rootNode.child("Users");

        reference.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //mobile
                String mobile_no = snapshot.child("Mobile").getValue(String.class);

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

                        //product id
                        product_id = getIntent().getStringExtra("productid");

                        //HashMap Array
                        final Map<String, Object> cartMap = new HashMap<>();
                        cartMap.put("PID", product_id);
                        cartMap.put("User Email:", emailid);
                        cartMap.put("Name", tv1.getText().toString());
                        cartMap.put("Price", getIntent().getStringExtra("price"));
                        cartMap.put("Date", currentDate);
                        cartMap.put("Time", currentTime);
                        cartMap.put("Refund", getIntent().getStringExtra("refund"));

                        //Adding Image Url
                        cartMap.put("ImageUrl", img);

                        databaseReference.child(mobile_no).child(product_id).setValue(cartMap).addOnCompleteListener(new OnCompleteListener<Void>() {
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

