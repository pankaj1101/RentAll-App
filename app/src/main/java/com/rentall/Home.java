package com.rentall;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.*;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rentall.adapter.DisplayProductAdapter;
import com.rentall.model.ProductDetailsModel;

import java.util.ArrayList;

public class Home extends Fragment {
    private ImageView imageView1, imageView2, imageView3, imageView4;

    RecyclerView recyclerView;
    private DatabaseReference myRef;
    private ArrayList<ProductDetailsModel> messagesArrayList;
    private DisplayProductAdapter recyclerAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        imageView1 = view.findViewById(R.id.imageView1);
        imageView2 = view.findViewById(R.id.imageView2);
        imageView3 = view.findViewById(R.id.imageView3);
        imageView4 = view.findViewById(R.id.imageView4);
        recyclerView = view.findViewById(R.id.RecyclerView);

        ImageSlider imageSlider = view.findViewById(R.id.image_slider);
        ArrayList<SlideModel> slideModels = new ArrayList<>();

        slideModels.add(new SlideModel(R.drawable.banner, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.banner, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.banner, ScaleTypes.FIT));

        imageSlider.setImageList(slideModels, ScaleTypes.FIT);

        imageView1.setOnClickListener(v -> openNextActivity("Electronic"));
        imageView2.setOnClickListener(v -> openNextActivity("Fashion"));
        imageView3.setOnClickListener(v -> openNextActivity("Books"));
        imageView4.setOnClickListener(v -> openNextActivity("Gym Equipment"));

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        myRef = FirebaseDatabase.getInstance().getReference("Stock").child("Electronic");
        messagesArrayList = new ArrayList<>();

        ClearAll();
        getDataFromFirebase();

        return view;
    }

    private void getDataFromFirebase() {
        myRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ClearAll();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    ProductDetailsModel messages = new ProductDetailsModel();

                    messages.setId(snapshot1.child("p_id").getValue(String.class));
                    messages.setImageUrl(snapshot1.child("p_img").getValue(String.class));
                    messages.setName(snapshot1.child("p_name").getValue(String.class));
                    messages.setPrice(snapshot1.child("p_price").getValue(Double.class));
                    messages.setRefund(snapshot1.child("p_refund").getValue(Double.class));
                    messages.setDescription(snapshot1.child("p_desc").getValue(String.class));

                    messagesArrayList.add(messages);

                }
                recyclerAdapter = new DisplayProductAdapter(messagesArrayList,getContext());
                recyclerView.setAdapter(recyclerAdapter);
                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
                recyclerView.setLayoutManager(layoutManager);
                recyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }

    private void ClearAll() {

    }

    private void openNextActivity(String text) {
        Intent intent = new Intent(getActivity(), ProductList.class);
        intent.putExtra("category_name", text);
        startActivity(intent);
    }
}




