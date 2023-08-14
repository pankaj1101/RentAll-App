package com.rentall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.UUID;

public class Feedback extends AppCompatActivity {

    private Button submit_button,back_to_home;
    FirebaseAuth auth;
    private EditText feedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        feedback=findViewById(R.id.feedbackbox);
        submit_button = findViewById(R.id.submit);
        back_to_home=findViewById(R.id.back_to_home);
        auth = FirebaseAuth.getInstance();

        back_to_home.setOnClickListener(v -> {
            Intent intent = new Intent(Feedback.this, MainNavigation.class);
            intent.putExtra("FromReservation", "1");
            startActivity(intent);
        });
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.black));
        }

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String text = feedback.getText().toString();
                if (TextUtils.isEmpty(text)) {
                    Toast.makeText(Feedback.this, "Enter Something", Toast.LENGTH_SHORT).show();
                }
                else{
                    FirebaseUser user = auth.getCurrentUser();
                    DatabaseReference rootNode = FirebaseDatabase.getInstance().getReference();
                    DatabaseReference reference = rootNode.child("Users");
                    reference.child(user.getUid()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            //mobile
                            String mobile_no = snapshot.child("Mobile").getValue(String.class);
                            upload_feedback(mobile_no);

                        }

                        private void upload_feedback(String mobile_no) {
                            DatabaseReference reference = database.getReference("Feedback");
                            reference.child(mobile_no).child(UUID.randomUUID().toString().substring(0,9)).setValue(text);
                            Toast.makeText(Feedback.this, "Feedback sent", Toast.LENGTH_SHORT).show();
                            feedback.setText("");

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });

    }

}