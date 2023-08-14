package com.rentall.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rentall.Description;
import com.rentall.R;
import com.rentall.model.ProductDetailsModel;

import java.util.ArrayList;
import java.util.HashMap;

public class DisplayProductAdapter extends RecyclerView.Adapter<DisplayProductAdapter.ViewHolder> {

    private ArrayList<ProductDetailsModel> messagesArrayList;
    private Context mContext;

    public DisplayProductAdapter(ArrayList<ProductDetailsModel> messagesArrayList, Context mContext) {
        this.messagesArrayList = messagesArrayList;
        this.mContext = mContext;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView textView, textView1;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById(R.id.textView);
            textView1 = itemView.findViewById(R.id.price);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setText(messagesArrayList.get(position).getName());
        holder.textView1.setText(messagesArrayList.get(position).getPrice());
        Glide.with(mContext).load(messagesArrayList.get(position).getImageUrl()).into(holder.imageView);

        final ProductDetailsModel temp = messagesArrayList.get(position);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Sending Data to Description Page..

                HashMap<String, String> dataMap = new HashMap<>();
                dataMap.put("productid", temp.getId());
                dataMap.put("imageUrl", temp.getImageUrl());
                dataMap.put("name", temp.getName());
                dataMap.put("price", temp.getPrice());
                dataMap.put("refund", temp.getRefund());
                dataMap.put("desc", temp.getDescription());

                Intent intent = new Intent(mContext, Description.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("dataMap", dataMap);
                mContext.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return messagesArrayList.size();
    }
}
