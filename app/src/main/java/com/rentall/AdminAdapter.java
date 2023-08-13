package com.rentall;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.rentall.model.ProductDetailsModel;

import java.util.ArrayList;

public class AdminAdapter extends RecyclerView.Adapter<AdminAdapter.ViewHolder> {

    private ArrayList<ProductDetailsModel> messagesArrayList;
    private Context mContext;

    public AdminAdapter(ArrayList<ProductDetailsModel> messagesArrayList, Context mContext) {
        this.messagesArrayList = messagesArrayList;
        this.mContext = mContext;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView, btn_remove;
        private TextView textView, textView1, textView2, textView3, textView4;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById(R.id.pid);
            textView1 = itemView.findViewById(R.id.p_name);
            textView2 = itemView.findViewById(R.id.p_price);
            textView3 = itemView.findViewById(R.id.r_price);
            textView4 = itemView.findViewById(R.id.p_desc);

        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(mContext).load(messagesArrayList.get(position).getImageUrl()).into(holder.imageView);
        holder.textView.setText(messagesArrayList.get(position).getId());
        holder.textView1.setText(messagesArrayList.get(position).getName());
        holder.textView2.setText(messagesArrayList.get(position).getPrice());
        holder.textView3.setText(messagesArrayList.get(position).getRefund());
        holder.textView4.setText(messagesArrayList.get(position).getDescription());

    }

    @Override
    public int getItemCount() {
        return messagesArrayList.size();
    }
}
