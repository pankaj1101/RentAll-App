package com.rentall;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
<<<<<<< HEAD

public class SplashScreen extends AppCompatActivity {

    private static final long SPLASH_DISPLAY_DURATION = 2000;
    private FirebaseUser currentUser;
=======
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

public class SplashScreen extends AppCompatActivity {

    FirebaseUser currentUser;
    private FirebaseAuth mAuth;
>>>>>>> 6d4b9b6f4faf82f2e6490200b9476c5d0991721f

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

<<<<<<< HEAD
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            navigateToMainActivity();
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    navigateToLoginActivity();
                }
            }, SPLASH_DISPLAY_DURATION);
        }
    }

    private void navigateToMainActivity() {
        String uid = currentUser.getUid();
        String email = currentUser.getEmail();
        String displayName = currentUser.getDisplayName();
        Intent intent = new Intent(this, MainNavigation.class);
        startActivity(intent);
        finish();
    }

    private void navigateToLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}

=======
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        if (mAuth != null) {
            currentUser = mAuth.getCurrentUser();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (user == null) {
                    startActivity(new Intent(SplashScreen.this, LoginActivity.class));
                    finish();
                } else if(user!=null) {
                    DatabaseReference rootNode = FirebaseDatabase.getInstance().getReference();
                    DatabaseReference reference = rootNode.child("Users");

                    reference.child(user.getUid()).child("online").setValue(ServerValue.TIMESTAMP);

                    Intent mainIntent = new Intent(SplashScreen.this, MainNavigation.class);
                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(mainIntent);
                    finish();
                }
            }
        }, 3000);
    }
}
>>>>>>> 6d4b9b6f4faf82f2e6490200b9476c5d0991721f
