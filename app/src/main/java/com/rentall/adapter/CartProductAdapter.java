package com.rentall.adapter;

import static com.rentall.Method.AmountFormat.formatAmount;
import static com.rentall.Method.CalculateAmountPercentage.getRefundAmountPercentage;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rentall.R;
import com.rentall.model.CartModel;

import java.util.List;

public class CartProductAdapter extends RecyclerView.Adapter<CartProductAdapter.ViewHolder> {

    private List<CartModel> cartProducts;
    private Context context;
    private CartEmptyListener cartEmptyListener;

    public CartProductAdapter(List<CartModel> cartProducts, Context context) {
        this.cartProducts = cartProducts;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CartModel cartProduct = cartProducts.get(position);
        holder.nameTextView.setText(cartProduct.getName());
        holder.priceTextView.setText("₹" + formatAmount(cartProduct.getPrice()) + "/Month");
        holder.depositTextView.setText("₹" + formatAmount(getRefundAmountPercentage(cartProduct.getPrice())));
        Glide.with(context)
                .load(cartProduct.getImageUrl())
                .into(holder.imageView);
        holder.remove_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int clickedPosition = holder.getAdapterPosition();
                CartModel cartProduct = cartProducts.get(clickedPosition);
                cartProducts.remove(clickedPosition);
                notifyItemRemoved(clickedPosition);
                removeFromFirebase(cartProduct.getPID());

                if (isCartEmpty() && cartEmptyListener != null) {
                    cartEmptyListener.onCartEmpty(true);
                    Log.d("debug", "Cart is Empty");
                }
            }

            private void removeFromFirebase(String productID) {
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                if (currentUser != null) {
                    String userID = currentUser.getUid();
                    DatabaseReference cartReference = FirebaseDatabase.getInstance().getReference("users")
                            .child(userID)  // Change to your user ID
                            .child("Cart Product")
                            .child(productID);

                    cartReference.removeValue()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                }
                            });
                }

            }
        });
    }

    public interface CartEmptyListener {
        void onCartEmpty(boolean isEmpty);
    }

    public void setCartEmptyListener(CartEmptyListener listener) {
        this.cartEmptyListener = listener;
    }


    @Override
    public int getItemCount() {
        return cartProducts.size();
    }

    public boolean isCartEmpty() {
        return cartProducts.isEmpty();
    }

    public List<CartModel> getCartProducts() {
        return cartProducts;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView nameTextView, priceTextView, depositTextView;
        private ImageView imageView, remove_btn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            remove_btn = itemView.findViewById(R.id.remove_btn);
            nameTextView = itemView.findViewById(R.id.nametext);
            priceTextView = itemView.findViewById(R.id.coursetext);
            depositTextView = itemView.findViewById(R.id.deposit);
        }
    }
}
