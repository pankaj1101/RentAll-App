package com.rentall;

import android.util.Log;
import android.view.*;
import android.os.Bundle;
import android.widget.ImageView;

import com.firebase.ui.database.FirebaseRecyclerOptions;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.*;
import com.google.firebase.database.*;
import com.rentall.adapter.CartProductAdapter;
import com.rentall.model.CartModel;

import androidx.recyclerview.widget.*;

import java.util.ArrayList;
import java.util.List;

public class cart extends Fragment {

    RecyclerView recview;
    CartAdapter adapter;
    FirebaseAuth mAuth;
    DatabaseReference reference;
    FirebaseUser user;

    ImageView emptyBox;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        emptyBox=view.findViewById(R.id.empty_box);
        emptyBox.setVisibility(View.GONE);

        mAuth = FirebaseAuth.getInstance();

        user = mAuth.getCurrentUser();
        DatabaseReference rootNode = FirebaseDatabase.getInstance().getReference();
        reference = rootNode.child("users");

        recview = view.findViewById(R.id.recview);
        recview.setLayoutManager(new LinearLayoutManager(getActivity()));
        getDataFromCart();
        return view;
    }

    private void getDataFromCart() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = database.getReference("users")
                .child(user.getUid()) // Replace with your user ID
                .child("Cart Product");


        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    List<CartModel> cartProducts = new ArrayList<>();

                    for (DataSnapshot productSnapshot : snapshot.getChildren()) {
                        CartModel cartProduct = productSnapshot.getValue(CartModel.class);
                        cartProducts.add(cartProduct);
                    }

                    CartProductAdapter adapter = new CartProductAdapter(cartProducts);
                    recview.setAdapter(adapter);
                } else {
                    emptyBox.setVisibility(View.VISIBLE);
                    Log.d("cart", "Empty Cart");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}