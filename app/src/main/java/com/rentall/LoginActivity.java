package com.rentall;

import android.app.*;
import android.content.*;
import android.os.*;
import android.text.*;
import android.util.Patterns;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.*;
import com.google.firebase.auth.*;

public class LoginActivity extends AppCompatActivity {

    private EditText email, password, name;
    private Button mlogin, newdnewaccount;
    private TextView reocverpass;
    private ProgressDialog loadingBar;
    private FirebaseAuth mAuth;
    FirebaseUser currentUser;

    SharedPreferences shp;
    SharedPreferences.Editor shpEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // initialising the layout items
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
        });

        newdnewaccount.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));

        });

        reocverpass.setOnClickListener(v -> {
            showRecoverPasswordDialog();
        });
    }

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
        mAuth.sendPasswordResetEmail(emaill).addOnCompleteListener(new OnCompleteListener<Void>() {
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
                loadingBar.dismiss();
                Toast.makeText(LoginActivity.this, "Error Failed", Toast.LENGTH_LONG).show();
            }
        });
    }

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