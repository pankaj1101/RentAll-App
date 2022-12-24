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
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;

public class Profile extends Fragment {

    TextView support, profilname, feedback;
    private TextView logout, about_app;

    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        support = view.findViewById(R.id.support);
        logout = view.findViewById(R.id.logout_btn);
        profilname = view.findViewById(R.id.profile_name);
        about_app = view.findViewById(R.id.about);
        feedback = view.findViewById(R.id.feedback);

        mAuth = FirebaseAuth.getInstance();

        support.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), Support.class));
        });

        about_app.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), ActivityAbout.class));
        });

        feedback.setOnClickListener(v -> {
            startActivity(new Intent(view.getContext(), Feedback.class));
        });

        logout.setOnClickListener(v -> {
            logout_alert_display();
        });
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
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


}