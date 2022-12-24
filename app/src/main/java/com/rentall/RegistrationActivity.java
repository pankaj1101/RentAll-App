package com.rentall;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.*;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegistrationActivity extends AppCompatActivity {

    private String MobilePattern = "[0-9]{10}";
    private EditText email, password, name, mobile;
    private Button mRegister;
    private TextView existaccount;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        email = findViewById(R.id.register_email);
        name = findViewById(R.id.register_name);
        mobile = findViewById(R.id.register_mobile);
        password = findViewById(R.id.register_password);
        mRegister = findViewById(R.id.register_button);

        existaccount = findViewById(R.id.homepage);
        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Register");

        mRegister.setOnClickListener(v -> {
            check_validation();
        });

        existaccount.setOnClickListener(v -> {
            startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
        });
    }

    //checking all the field are correct
    private void check_validation() {
        String emaill = email.getText().toString().trim();
        String uname = name.getText().toString();
        String pass = password.getText().toString().trim();
        String mob = mobile.getText().toString().trim();

        if (TextUtils.isEmpty(uname)) {
            name.setError("FIELD CANNOT BE EMPTY");
            name.requestFocus();
        } else if (!uname.matches("[a-zA-Z ]+")) {
            name.requestFocus();
            name.setError("ENTER ONLY ALPHABETICAL CHARACTER");
        } else if (TextUtils.isEmpty(emaill)) {
            email.setError("FIELD CANNOT BE EMPTY");
            email.requestFocus();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emaill).matches()) {
            email.setError("Invalid Email");
            email.requestFocus();
        } else if (mob.length() < 10 || !mob.matches(MobilePattern)) {
            mobile.setError("Enter 10 digit mobile number");
            mobile.requestFocus();
        } else if (pass.length() < 6) {
            password.setError("Length Must be greater than 6 character");
            password.requestFocus();
        } else {
            registerUser(emaill, pass, uname, mob);
        }
    }

    private void registerUser(String emaill, final String pass, final String uname, final String mob) {
        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(emaill, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    progressDialog.dismiss();

                    FirebaseUser user = mAuth.getCurrentUser();
                    String email = user.getEmail();
                    String uid = user.getUid();
                    HashMap<Object, String> hashMap = new HashMap<>();

                    hashMap.put("email:", email);
                    hashMap.put("uid", uid);
                    hashMap.put("name", uname);
                    hashMap.put("Mobile", mob);
                    hashMap.put("Address", "");
                    hashMap.put("Adhar", "");
                    hashMap.put("Pancard", "");
                    hashMap.put("Pincode", "");

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference reference = database.getReference("Users");
                    reference.child(uid).setValue(hashMap);

                    Toast.makeText(RegistrationActivity.this, "Registered successfully", Toast.LENGTH_LONG).show();
                    Intent mainIntent = new Intent(RegistrationActivity.this, MainNavigation.class);
                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(mainIntent);
                    finish();
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(RegistrationActivity.this, "Email Already Registered", Toast.LENGTH_LONG).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(RegistrationActivity.this, "Failed to Login", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}