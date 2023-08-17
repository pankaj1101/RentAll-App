package com.rentall;

import static com.rentall.Method.CalculateAmountPercentage.getRefundAmountPercentage;

import android.util.Log;
import android.view.*;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.*;
import com.google.firebase.database.*;
import com.rentall.adapter.CartProductAdapter;
import com.rentall.model.CartModel;

import androidx.recyclerview.widget.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class cart extends Fragment implements CartProductAdapter.CartEmptyListener {

    RecyclerView recview;
    FirebaseAuth mAuth;
    DatabaseReference reference;
    FirebaseUser user;
    ImageView emptyBox;
    Button proceed_to_buy;
    CartProductAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        emptyBox = view.findViewById(R.id.empty_box);
        proceed_to_buy = view.findViewById(R.id.proceed_to_buy);
        emptyBox.setVisibility(View.GONE);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        DatabaseReference rootNode = FirebaseDatabase.getInstance().getReference();
        reference = rootNode.child("users");

        recview = view.findViewById(R.id.recview);
        recview.setLayoutManager(new LinearLayoutManager(getActivity()));
        getDataFromCart();

        proceed_to_buy.setOnClickListener(v -> {
            Map totalAmountOfEachProduct = calculateTotalAmountOfEachProduct(adapter.getCartProducts());
            Log.d("Total Price", totalAmountOfEachProduct.toString());
        });
        return view;
    }

    private Map calculateTotalAmountOfEachProduct(List<CartModel> cartProducts) {
        HashMap<String, Object> priceMap = new HashMap<>();

        for (CartModel cartProduct : cartProducts) {
            double price = cartProduct.getPrice();
            priceMap.put(cartProduct.getPID(), price + getRefundAmountPercentage(price));
        }
        return priceMap;
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
                    adapter = new CartProductAdapter(cartProducts, getContext());
                    adapter.setCartEmptyListener(cart.this);
                    recview.setAdapter(adapter);

                } else {
                    emptyBox.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    @Override
    public void onCartEmpty(boolean isEmpty) {
        if (isEmpty) {
            emptyBox.setVisibility(View.VISIBLE);
            recview.setVisibility(View.GONE);
        } else {
            emptyBox.setVisibility(View.GONE);
            recview.setVisibility(View.VISIBLE);
        }
    }
}