package com.rentall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;

import com.google.firebase.auth.*;
import com.google.firebase.database.*;

public class YourDetail extends AppCompatActivity {

    private EditText Ev1, Ev2, Ev3, Ev4, Ev5, adhar_no, pancard, pincode;
    private Button pay;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_detail);

        Ev1 = findViewById(R.id.namefield);
        Ev2 = findViewById(R.id.mobilefield);
        Ev3 = findViewById(R.id.addressfield);
        Ev4 = findViewById(R.id.productname);
        Ev5 = findViewById(R.id.pricefiled);
        pay = findViewById(R.id.payment);

        pincode = findViewById(R.id.pincode);
        adhar_no = findViewById(R.id.adhar_no);
        pancard = findViewById(R.id.pancard);
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check_field();
            }
        });
        mAuth = FirebaseAuth.getInstance();
        retrivedatafromdatabase();
    }

    private void check_field() {

        if (TextUtils.isEmpty(Ev3.getText().toString().trim())) {
            Ev3.setError("Enter address");
        } else if (TextUtils.isEmpty(adhar_no.getText().toString().trim())) {
            adhar_no.setError("Enter proper adhar no");
        } else if (adhar_no.length() != 15) {
            adhar_no.setError("Enter 15 digit adhar card no....");
        } else if (TextUtils.isEmpty(pancard.getText().toString().trim())) {
            pancard.setError("Enter pancard number");
        } else if (pancard.length() != 10) {
            pancard.setError("Enter proper pancard number");
        } else {
            final String address = Ev3.getText().toString();
            final String adharno = adhar_no.getText().toString().trim();
            final String panncard = pancard.getText().toString().trim();
            final String pincodd = pincode.getText().toString().trim();

            FirebaseUser user = mAuth.getCurrentUser();
            DatabaseReference rootNode = FirebaseDatabase.getInstance().getReference();
            DatabaseReference reference = rootNode.child("Users");
            reference.child(user.getUid()).child("Address").setValue(address);
            reference.child(user.getUid()).child("Adhar").setValue(adharno);
            reference.child(user.getUid()).child("Pancard").setValue(panncard);
            reference.child(user.getUid()).child("Pincode").setValue(pincodd);

            Intent intent = new Intent(YourDetail.this, Payment.class);
            intent.putExtra("Product_id", getIntent().getStringExtra("Product_id"));
            intent.putExtra("Price", getIntent().getStringExtra("Price"));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    private void retrivedatafromdatabase() {

        FirebaseUser user = mAuth.getCurrentUser();
        DatabaseReference rootNode = FirebaseDatabase.getInstance().getReference();
        DatabaseReference reference = rootNode.child("Users");

        reference.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Ev1.setText(snapshot.child("name").getValue(String.class));
                Ev1.setEnabled(false);
                Ev2.setText(snapshot.child("Mobile").getValue(String.class));
                Ev2.setEnabled(false);

                Ev3.setText(snapshot.child("Address").getValue(String.class));
                Ev4.setText(getIntent().getStringExtra("Product_name"));
                Ev4.setEnabled(false);

                Ev5.setText(getIntent().getStringExtra("Price") + " Rs");
                Ev5.setEnabled(false);

                adhar_no.setText(snapshot.child("Adhar").getValue(String.class));
                pancard.setText(snapshot.child("Pancard").getValue(String.class));
                pincode.setText(snapshot.child("Pincode").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                throw error.toException();
            }
        });
    }
}