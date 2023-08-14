package com.rentall;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Profile extends Fragment {

    RelativeLayout support,feedback, logout;
    private FirebaseAuth mAuth;
    FirebaseUser currentUser;

    TextView profilname;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        support = view.findViewById(R.id.support);
        logout = view.findViewById(R.id.logout_btn);
        profilname = view.findViewById(R.id.profile_name);
        feedback = view.findViewById(R.id.feedback);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        getDisplayName();


        support.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), Support.class));
        });

        feedback.setOnClickListener(v -> {
            startActivity(new Intent(view.getContext(), Feedback.class));
        });

        logout.setOnClickListener(v -> {
            logout_alert_display();
        });
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