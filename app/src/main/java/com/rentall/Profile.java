package com.rentall;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
<<<<<<< HEAD
import android.widget.RelativeLayout;
=======
>>>>>>> 6d4b9b6f4faf82f2e6490200b9476c5d0991721f
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;

public class Profile extends Fragment {

<<<<<<< HEAD
    RelativeLayout support,feedback, logout;
    private FirebaseAuth mAuth;
    FirebaseUser currentUser;

    TextView profilname;
=======
    TextView support, profilname, feedback;
    private TextView logout, about_app;

    private FirebaseAuth mAuth;
>>>>>>> 6d4b9b6f4faf82f2e6490200b9476c5d0991721f

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        support = view.findViewById(R.id.support);
        logout = view.findViewById(R.id.logout_btn);
        profilname = view.findViewById(R.id.profile_name);
<<<<<<< HEAD
        feedback = view.findViewById(R.id.feedback);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        getDisplayName();

=======
        about_app = view.findViewById(R.id.about);
        feedback = view.findViewById(R.id.feedback);

        mAuth = FirebaseAuth.getInstance();
>>>>>>> 6d4b9b6f4faf82f2e6490200b9476c5d0991721f

        support.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), Support.class));
        });

<<<<<<< HEAD
=======
        about_app.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), ActivityAbout.class));
        });

>>>>>>> 6d4b9b6f4faf82f2e6490200b9476c5d0991721f
        feedback.setOnClickListener(v -> {
            startActivity(new Intent(view.getContext(), Feedback.class));
        });

        logout.setOnClickListener(v -> {
            logout_alert_display();
        });
<<<<<<< HEAD
        return view;
    }

    private void getDisplayName() {
        if (currentUser != null) {
            String username = currentUser.getDisplayName();
            if (username != null) {
                profilname.setText(username);
            }
        }
    }

    private void logout_alert_display() {

=======
        fatch_data_for_name();
        return view;
    }

    private void fatch_data_for_name() {

        FirebaseUser user = mAuth.getCurrentUser();
        DatabaseReference rootNode = FirebaseDatabase.getInstance().getReference();
        DatabaseReference reference = rootNode.child("Users");

        reference.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                profilname.setText(snapshot.child("name").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                throw error.toException();
            }
        });

    }

    private void logout_alert_display() {
>>>>>>> 6d4b9b6f4faf82f2e6490200b9476c5d0991721f
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setMessage("Do you want to exit ?").setTitle("Alert !").setCancelable(false);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                mAuth.signOut();
                startActivity(new Intent(getContext(), LoginActivity.class));
                getActivity().finish();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
<<<<<<< HEAD

=======
>>>>>>> 6d4b9b6f4faf82f2e6490200b9476c5d0991721f
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
<<<<<<< HEAD

    }
=======
    }


>>>>>>> 6d4b9b6f4faf82f2e6490200b9476c5d0991721f
}