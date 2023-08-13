package com.rentall;

import android.content.Context;
<<<<<<< HEAD
=======
import android.content.DialogInterface;
>>>>>>> 6d4b9b6f4faf82f2e6490200b9476c5d0991721f
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
<<<<<<< HEAD
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.rentall.model.ProductDetailsModel;
=======
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
>>>>>>> 6d4b9b6f4faf82f2e6490200b9476c5d0991721f

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    //    static final String Tag = "RecycleView";
<<<<<<< HEAD
    private ArrayList<ProductDetailsModel> messagesArrayList;
    private Context mContext;
    int total_amount = 0;

    public CartAdapter(ArrayList<ProductDetailsModel> messagesArrayList, Context mContext) {
=======
    private ArrayList<Messages> messagesArrayList;
    private Context mContext;
    int total_amount = 0;

    public CartAdapter(ArrayList<Messages> messagesArrayList, Context mContext) {
>>>>>>> 6d4b9b6f4faf82f2e6490200b9476c5d0991721f
        this.messagesArrayList = messagesArrayList;
        this.mContext = mContext;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView, btn_remove;
        private TextView textView, textView1, textView2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById(R.id.textView);
            textView1 = itemView.findViewById(R.id.price);
            textView2 = itemView.findViewById(R.id.refund);
            btn_remove = itemView.findViewById(R.id.btn_remove);
        }
    }

    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cartcomponent, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        holder.textView.setText(messagesArrayList.get(position).getName());
        holder.textView1.setText(messagesArrayList.get(position).getPrice());
        holder.textView2.setText(messagesArrayList.get(position).getRefund());
        Glide.with(mContext).load(messagesArrayList.get(position).getImageUrl()).into(holder.imageView);

        //Finding Total Amount of product...
        total_amount = total_amount + (Integer.parseInt(messagesArrayList.get(position).getPrice()) +
                Integer.parseInt(messagesArrayList.get(position).getRefund()));

        Intent intent = new Intent("Total_Product_Amount");
        intent.putExtra("total_amount", total_amount);
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);

        holder.btn_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CartRemove.getDataFromFirebase();
                Toast.makeText(mContext, "Removed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return messagesArrayList.size();
    }

}
