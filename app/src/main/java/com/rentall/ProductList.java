package com.rentall;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.*;
import com.rentall.adapter.DisplayProductAdapter;
import com.rentall.model.ProductDetailsModel;

import android.view.Menu;
import android.widget.ProgressBar;

import java.util.ArrayList;

import android.view.View;
import android.os.Bundle;

public class ProductList extends AppCompatActivity {

    RecyclerView recyclerView;
    private DatabaseReference myRef;
    private ArrayList<ProductDetailsModel> messagesArrayList;
    private DisplayProductAdapter recyclerAdapter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.RecyclerView);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        String sessionId = getIntent().getStringExtra("category_name");
        getSupportActionBar().setTitle("");

        myRef = FirebaseDatabase.getInstance().getReference("Stock").child(sessionId);

        messagesArrayList = new ArrayList<>();

        ClearAll();
        getDataFromFirebase();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(view -> onBackPressed());
        return true;
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
                    messages.setPrice(snapshot1.child("p_price").getValue(String.class));
                    messages.setRefund(snapshot1.child("p_refund").getValue(String.class));
                    messages.setDescription(snapshot1.child("p_desc").getValue(String.class));

                    messagesArrayList.add(messages);
                    progressBar.setVisibility(View.INVISIBLE);
                }
                recyclerAdapter = new DisplayProductAdapter(messagesArrayList, getApplicationContext());
                recyclerView.setAdapter(recyclerAdapter);
                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplication(), 2);
                recyclerView.setLayoutManager(layoutManager);
                recyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    void ClearAll() {
        if (messagesArrayList != null) {
            messagesArrayList.clear();
            if (recyclerAdapter != null) {
                recyclerAdapter.notifyDataSetChanged();
            }
        }
        messagesArrayList = new ArrayList<>();
    }
}