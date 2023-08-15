package com.rentall.adapter;

import static com.rentall.Method.AmountFormat.formatAmount;
import static com.rentall.Method.CalculateAmountPercentage.getRefundAmountPercentage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rentall.R;
import com.rentall.model.CartModel;

import java.util.List;

public class CartProductAdapter extends RecyclerView.Adapter<CartProductAdapter.ViewHolder> {

    private List<CartModel> cartProducts;
    private Context context;

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
    }

    @Override
    public int getItemCount() {
        return cartProducts.size();
    }

    //Returning cart product information....
    public List<CartModel> getCartProducts() {
        return cartProducts;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView nameTextView, priceTextView, depositTextView;
        private ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            nameTextView = itemView.findViewById(R.id.nametext);
            priceTextView = itemView.findViewById(R.id.coursetext);
            depositTextView = itemView.findViewById(R.id.deposit);
        }
    }
}
