package com.rentall;

import android.app.*;
import android.content.*;
import android.os.*;
import android.text.*;
<<<<<<< HEAD
import android.util.Log;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

=======
import android.util.Patterns;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
>>>>>>> 6d4b9b6f4faf82f2e6490200b9476c5d0991721f
import com.google.android.gms.tasks.*;
import com.google.firebase.auth.*;

public class LoginActivity extends AppCompatActivity {

<<<<<<< HEAD
    private EditText etEmail, etPassword;
    private Button btnLogin, newdnewaccount;
    private FirebaseAuth firebaseAuth;
    private TextView reocverpass;
    private ProgressDialog loadingBar;
=======
    private EditText email, password, name;
    private Button mlogin, newdnewaccount;
    private TextView reocverpass;
    private ProgressDialog loadingBar;
    private FirebaseAuth mAuth;
    FirebaseUser currentUser;

    SharedPreferences shp;
    SharedPreferences.Editor shpEditor;
>>>>>>> 6d4b9b6f4faf82f2e6490200b9476c5d0991721f

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // initialising the layout items
<<<<<<< HEAD
        etEmail = findViewById(R.id.login_email);
        etPassword = findViewById(R.id.login_password);
        btnLogin = findViewById(R.id.login_button);
        firebaseAuth = FirebaseAuth.getInstance();
        reocverpass = findViewById(R.id.forgetp);
        newdnewaccount = findViewById(R.id.needs_new_account);

        loadingBar = new ProgressDialog(this);
        btnLogin.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            loginUser(email, password);
=======
        email = findViewById(R.id.login_email);
        password = findViewById(R.id.login_password);

        shp = getSharedPreferences("myPreferences", MODE_PRIVATE);
        CheckLogin();

        newdnewaccount = findViewById(R.id.needs_new_account);
        reocverpass = findViewById(R.id.forgetp);
        mAuth = FirebaseAuth.getInstance();

        mlogin = findViewById(R.id.login_button);
        loadingBar = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();

//         checking if user is null or not
        if (mAuth != null) {
            currentUser = mAuth.getCurrentUser();
        }

        mlogin.setOnClickListener(v -> {

            String emaill = email.getText().toString().trim();
            String pass = password.getText().toString().trim();

            if (TextUtils.isEmpty(emaill)) {
                email.setError("Enter email");
            }
            // if format of email doesn't matches return null
            else if (!Patterns.EMAIL_ADDRESS.matcher(emaill).matches()) {
                email.setError("Invalid Email");
                email.setFocusable(true);
            } else if (pass.length() == 0) {
                password.setError("Enter password");
                password.setFocusable(true);
            } else {
                loginAdmin(emaill, pass);
            }
>>>>>>> 6d4b9b6f4faf82f2e6490200b9476c5d0991721f
        });

        newdnewaccount.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
<<<<<<< HEAD
=======

>>>>>>> 6d4b9b6f4faf82f2e6490200b9476c5d0991721f
        });

        reocverpass.setOnClickListener(v -> {
            showRecoverPasswordDialog();
        });
    }

<<<<<<< HEAD
=======
    private void CheckLogin() {
        if (shp == null)
            shp = getSharedPreferences("myPreferences", MODE_PRIVATE);

        String userName = shp.getString("name", "");

        if (userName != null && !userName.equals("")) {
            Intent i = new Intent(LoginActivity.this, AdminPage.class);
            startActivity(i);
            finish();
        }
    }

    private void loginAdmin(String emaill, String pass) {
        try {
            if (pass.equals("admin123")) {
                if (shp == null)
                    shp = getSharedPreferences("myPreferences", MODE_PRIVATE);

                shpEditor = shp.edit();
                shpEditor.putString("name", "admin123@gmail.com");
                shpEditor.commit();

                Intent i = new Intent(LoginActivity.this, AdminPage.class);
                startActivity(i);
                finish();
            } else {
                loginUser(emaill, pass);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

>>>>>>> 6d4b9b6f4faf82f2e6490200b9476c5d0991721f
    //Recovery Dialogue
    private void showRecoverPasswordDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Recover Password");

        LinearLayout linearLayout = new LinearLayout(this);
        final EditText emailet = new EditText(this);//write your registered email

        emailet.setText("Email");
        emailet.setSelectAllOnFocus(true);
        emailet.setMinEms(16);
        emailet.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        linearLayout.addView(emailet);
        linearLayout.setPadding(10, 10, 10, 10);
        builder.setView(linearLayout);

        builder.setPositiveButton("Recover", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String emaill = emailet.getText().toString().trim();
                beginRecovery(emaill);//send a mail on the mail to recover password
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create().show();
    }

    //Recovery Password
    private void beginRecovery(String emaill) {
        loadingBar.setMessage("Sending Email....");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        // send reset password email
<<<<<<< HEAD
        firebaseAuth.sendPasswordResetEmail(emaill).addOnCompleteListener(new OnCompleteListener<Void>() {
=======
        mAuth.sendPasswordResetEmail(emaill).addOnCompleteListener(new OnCompleteListener<Void>() {
>>>>>>> 6d4b9b6f4faf82f2e6490200b9476c5d0991721f
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                loadingBar.dismiss();
                if (task.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "Done sent", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(LoginActivity.this, "Error Occurred", Toast.LENGTH_LONG).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
<<<<<<< HEAD
                Toast.makeText(LoginActivity.this, "Error Failed", Toast.LENGTH_LONG).show();
                loadingBar.dismiss();
=======
                loadingBar.dismiss();
                Toast.makeText(LoginActivity.this, "Error Failed", Toast.LENGTH_LONG).show();
>>>>>>> 6d4b9b6f4faf82f2e6490200b9476c5d0991721f
            }
        });
    }

<<<<<<< HEAD
    private void loginUser(String email, String password) {
        loadingBar.setMessage("Logging In....");
        loadingBar.show();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this, MainNavigation.class));
                            loadingBar.dismiss();
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "Login failed.", Toast.LENGTH_SHORT).show();
                            Log.d("login_error", "Login failed. " + task.getException().getMessage());
                            loadingBar.dismiss();
                        }
                    }
                });
    }

=======
    private void loginUser(String emaill, String pass) {
        loadingBar.setMessage("Logging In....");
        loadingBar.show();

        //userLogin
        if (emaill.equals("admin123@gmail.com") && pass.equals("admin123")) {
            startActivity(new Intent(this, AdminPage.class));
            finish();
        } else {
            mAuth.signInWithEmailAndPassword(emaill, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "Login successful!!", Toast.LENGTH_LONG).show();
                        loadingBar.dismiss();

                        Intent mainIntent = new Intent(LoginActivity.this, MainNavigation.class);

                        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(mainIntent);
                        finish();

                    } else {
                        loadingBar.dismiss();
                        Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_LONG).show();
                    }
                }


            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    loadingBar.dismiss();
                    Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    //DoubleBackPress
>>>>>>> 6d4b9b6f4faf82f2e6490200b9476c5d0991721f
    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}