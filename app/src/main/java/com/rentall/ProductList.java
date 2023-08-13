package com.rentall;

import androidx.annotation.NonNull;
<<<<<<< HEAD
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

=======
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.*;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.ArrayList;
>>>>>>> 6d4b9b6f4faf82f2e6490200b9476c5d0991721f
import android.view.View;
import android.os.Bundle;

public class ProductList extends AppCompatActivity {

    RecyclerView recyclerView;
    private DatabaseReference myRef;
<<<<<<< HEAD
    private ArrayList<ProductDetailsModel> messagesArrayList;
    private DisplayProductAdapter recyclerAdapter;
    private ProgressBar progressBar;
=======
    private ArrayList<Messages> messagesArrayList;
    private RecyclerAdapter recyclerAdapter;
    private ProgressBar progressBar;
    private TextView list_header;
>>>>>>> 6d4b9b6f4faf82f2e6490200b9476c5d0991721f

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

<<<<<<< HEAD
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.RecyclerView);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
=======
        recyclerView = findViewById(R.id.RecyclerView);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        list_header=findViewById(R.id.list_header);
>>>>>>> 6d4b9b6f4faf82f2e6490200b9476c5d0991721f

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        String sessionId = getIntent().getStringExtra("category_name");
<<<<<<< HEAD
        getSupportActionBar().setTitle("");
=======
        list_header.setText(sessionId);
>>>>>>> 6d4b9b6f4faf82f2e6490200b9476c5d0991721f

        myRef = FirebaseDatabase.getInstance().getReference("Stock").child(sessionId);

        messagesArrayList = new ArrayList<>();

        ClearAll();
        getDataFromFirebase();
    }

<<<<<<< HEAD
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(view -> onBackPressed());
        return true;
    }

=======
>>>>>>> 6d4b9b6f4faf82f2e6490200b9476c5d0991721f
    private void getDataFromFirebase() {

        myRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ClearAll();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
<<<<<<< HEAD
                    ProductDetailsModel messages = new ProductDetailsModel();
=======
                    Messages messages = new Messages();
>>>>>>> 6d4b9b6f4faf82f2e6490200b9476c5d0991721f

                    messages.setId(snapshot1.child("p_id").getValue(String.class));
                    messages.setImageUrl(snapshot1.child("p_img").getValue(String.class));
                    messages.setName(snapshot1.child("p_name").getValue(String.class));
                    messages.setPrice(snapshot1.child("p_price").getValue(String.class));
                    messages.setRefund(snapshot1.child("p_refund").getValue(String.class));
                    messages.setDescription(snapshot1.child("p_desc").getValue(String.class));

                    messagesArrayList.add(messages);
                    progressBar.setVisibility(View.INVISIBLE);
                }
<<<<<<< HEAD
                recyclerAdapter = new DisplayProductAdapter(messagesArrayList, getApplicationContext());
                recyclerView.setAdapter(recyclerAdapter);
                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplication(), 2);
                recyclerView.setLayoutManager(layoutManager);
=======
                recyclerAdapter = new RecyclerAdapter(messagesArrayList, getApplicationContext());
                recyclerView.setAdapter(recyclerAdapter);
>>>>>>> 6d4b9b6f4faf82f2e6490200b9476c5d0991721f
                recyclerAdapter.notifyDataSetChanged();
            }

            @Override
<<<<<<< HEAD
            public void onCancelled(@NonNull DatabaseError error) {
            }
=======
            public void onCancelled(@NonNull DatabaseError error) { }
>>>>>>> 6d4b9b6f4faf82f2e6490200b9476c5d0991721f
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