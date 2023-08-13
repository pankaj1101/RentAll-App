package com.rentall;

import android.view.*;
import android.os.Bundle;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.google.firebase.auth.*;
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
//        Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();

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