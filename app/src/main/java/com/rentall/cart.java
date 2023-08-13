package com.rentall;

import android.view.*;
import android.os.Bundle;
<<<<<<< HEAD
import com.firebase.ui.database.FirebaseRecyclerOptions;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.google.firebase.auth.*;
=======
import android.widget.Toast;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
>>>>>>> 6d4b9b6f4faf82f2e6490200b9476c5d0991721f
import com.google.firebase.database.*;
import androidx.recyclerview.widget.*;
public class cart extends Fragment {

    RecyclerView recview;
    myadapter adapter;
    FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        ManageDatabase.getMobileNo();
        String m = ManageDatabase.getmobileno("");
<<<<<<< HEAD
//        Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();
=======
        Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();
>>>>>>> 6d4b9b6f4faf82f2e6490200b9476c5d0991721f

        mAuth = FirebaseAuth.getInstance();
        recview = view.findViewById(R.id.recview);
        recview.setLayoutManager(new LinearLayoutManager(getActivity()));
        getMobileNo();
        return view;
    }

    private void getMobileNo() {

        FirebaseUser user = mAuth.getCurrentUser();
        DatabaseReference rootNode = FirebaseDatabase.getInstance().getReference();
        DatabaseReference reference = rootNode.child("Users");

        reference.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String mobile_no = snapshot.child("Mobile").getValue(String.class);
                getDataFromCart(mobile_no);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                throw error.toException();
            }
        });
    }

    private void getDataFromCart(String mobile_no) {
        FirebaseRecyclerOptions<CartModel> options = new FirebaseRecyclerOptions.Builder<CartModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Cart").child(mobile_no), CartModel.class)
                .build();
        adapter = new myadapter(options,mobile_no);
        recview.setAdapter(adapter);
        adapter.startListening();
    }
}