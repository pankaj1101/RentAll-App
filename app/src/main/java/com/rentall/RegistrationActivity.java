package com.rentall;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rentall.model.RegistrationModel;

public class RegistrationActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;
    private EditText etEmail, etPassword, etDisplayName, etMobile;
    private Button btnRegister;
    private FirebaseAuth firebaseAuth;
    private TextView signinBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        etEmail = findViewById(R.id.register_email);
        etDisplayName = findViewById(R.id.register_name);
        etMobile = findViewById(R.id.register_mobile);
        etPassword = findViewById(R.id.register_password);
        btnRegister = findViewById(R.id.register_button);
        signinBtn = findViewById(R.id.btnSignin);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Register");

        signinBtn.setOnClickListener(view -> {
            startActivity(new Intent(this, LoginActivity.class));
        });

        btnRegister.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String displayName = etDisplayName.getText().toString().trim(); // Get display name
            String mobile = etMobile.getText().toString().trim(); // Get display name

            registerUser(email, password, displayName, mobile);
        });

    }

    private void registerUser(String email, String password, String displayName, String mobile) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Registration success
                            FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                            if (currentUser != null) {
                                // Set the display name for the user
                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(displayName)
                                        .build();

                                currentUser.updateProfile(profileUpdates)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    // Display name set successfully
                                                    Toast.makeText(RegistrationActivity.this, "Registration successful!", Toast.LENGTH_SHORT).show();

                                                    // Save user data to the Realtime Database
                                                    saveUserDataToDatabase(email, displayName, mobile);

                                                    // Open the login page after successful registration
                                                    openLoginPage();
                                                } else {
                                                    // Failed to set display name
                                                    Toast.makeText(RegistrationActivity.this, "Failed to set display name.", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            }
                        } else {
                            // Registration failed
                            Toast.makeText(RegistrationActivity.this, "Registration failed. " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void saveUserDataToDatabase(String email, String displayName, String mobile) {
        String userId = firebaseAuth.getCurrentUser().getUid();
        RegistrationModel user = new RegistrationModel(email, displayName, mobile);

        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");
        usersRef.child(userId).setValue(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                    }
                });
    }

    private void openLoginPage() {
        Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}