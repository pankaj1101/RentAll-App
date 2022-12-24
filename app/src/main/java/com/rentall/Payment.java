package com.rentall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class Payment extends AppCompatActivity implements PaymentResultListener {

    private Button button;
    EditText amountFiled;
    private FirebaseAuth auth;
    String product_id="";

    static char num[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        button = findViewById(R.id.buttonpay);
        amountFiled = findViewById(R.id.idEdtAmount);
        amountFiled.setText(getIntent().getStringExtra("Price"));
        amountFiled.setEnabled(false);

        product_id = getIntent().getStringExtra("Product_id");
        auth = FirebaseAuth.getInstance();
        String samount = amountFiled.getText().toString();
        int amount = Math.round(Float.parseFloat(samount) * 100);

        button.setOnClickListener(v -> {
            Checkout checkout = new Checkout();
            //razorPay key
            checkout.setKeyID("rzp_test_W6plWxNz9KAVzI");
            checkout.setImage(R.drawable.rlogo);

            JSONObject object = new JSONObject();
            try {
                object.put("name", "pankaj");
                object.put("theme.color", "#0093DD");
                object.put("currency", "INR");
                object.put("amount", amount);
                object.put("prefill.contact", "9765128109");
                object.put("prefill.email", "bpankaj@gmail.com");
                checkout.open(Payment.this, object);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(this, "Payment is successful : " + s, Toast.LENGTH_SHORT).show();
        getDataFromFirebase(s);
        startActivity(new Intent(Payment.this, Feedback.class));
        finish();
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this, "Payment Failed due to error : " + s, Toast.LENGTH_SHORT).show();
    }

    private void getDataFromFirebase(String s) {

        FirebaseUser user = auth.getCurrentUser();
        DatabaseReference rootNode = FirebaseDatabase.getInstance().getReference();
        DatabaseReference reference = rootNode.child("Users");

        reference.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String mobile_no = snapshot.child("Mobile").getValue(String.class);
                String email=snapshot.child("email").getValue(String.class);
                store_order_detail(mobile_no,s,email);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                throw error.toException();
            }
        });
    }

    public void store_order_detail(String mobile_no,String s,String email) {
        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        String currentDate = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault()).format(new Date());
        final String order_id = generate_id();

        HashMap<Object, String> hashMap = new HashMap<>();

        hashMap.put("Orderid", order_id);
        hashMap.put("user_email",email);
        hashMap.put("Payment Id", s);
        hashMap.put("Total Amount", getIntent().getStringExtra("Price")+"Rs");
        hashMap.put("Order_date", currentDate);
        hashMap.put("Order_time", currentTime);
        hashMap.put("PaymentMode","Online");
        hashMap.put("Sucessfull","Yes");
        hashMap.put("Product_Id", product_id);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Ordered Product");
        reference.child(mobile_no).child(order_id).setValue(hashMap);

    }

    private String generate_id() {
        StringBuilder strBuilder = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            strBuilder.append(randomNum());
        }
        return "O" + strBuilder;
    }

    public static char randomNum() {
        return num[(int) Math.floor(Math.random() * 10)];
    }
}