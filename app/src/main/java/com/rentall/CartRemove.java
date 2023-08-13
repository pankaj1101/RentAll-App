package com.rentall;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.*;
import com.google.firebase.database.*;

public class CartRemove {

    static FirebaseAuth auth = FirebaseAuth.getInstance();

    public static void getDataFromFirebase() {

        FirebaseUser user = auth.getCurrentUser();
        DatabaseReference rootNode = FirebaseDatabase.getInstance().getReference();
        DatabaseReference reference = rootNode.child("Users");

        reference.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String mobile_no = snapshot.child("Mobile").getValue(String.class);
                removeProduct(mobile_no);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                throw error.toException();
            }
        });
    }

    public static Task<Void> removeProduct(String mobile_no) {
        Task<Void> task = FirebaseDatabase.getInstance().getReference("Cart").child("User View").child(mobile_no).setValue(null);
        return task;
    }
}

