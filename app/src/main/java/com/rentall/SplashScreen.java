package com.rentall;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreen extends AppCompatActivity {

    private static final long SPLASH_DISPLAY_DURATION = 2000;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

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

