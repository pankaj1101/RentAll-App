package com.rentall;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.*;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.ArrayList;
import android.view.View;
import android.os.Bundle;

public class ProductList extends AppCompatActivity {

    RecyclerView recyclerView;
    private DatabaseReference myRef;
    private ArrayList<Messages> messagesArrayList;
    private RecyclerAdapter recyclerAdapter;
    private ProgressBar progressBar;
    private TextView list_header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        recyclerView = findViewById(R.id.RecyclerView);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        list_header=findViewById(R.id.list_header);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        String sessionId = getIntent().getStringExtra("category_name");
        list_header.setText(sessionId);

        myRef = FirebaseDatabase.getInstance().getReference("Stock").child(sessionId);

        messagesArrayList = new ArrayList<>();

        ClearAll();
        getDataFromFirebase();
    }

    private void getDataFromFirebase() {

        myRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ClearAll();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    Messages messages = new Messages();

                    messages.setId(snapshot1.child("p_id").getValue(String.class));
                    messages.setImageUrl(snapshot1.child("p_img").getValue(String.class));
                    messages.setName(snapshot1.child("p_name").getValue(String.class));
                    messages.setPrice(snapshot1.child("p_price").getValue(String.class));
                    messages.setRefund(snapshot1.child("p_refund").getValue(String.class));
                    messages.setDescription(snapshot1.child("p_desc").getValue(String.class));

                    messagesArrayList.add(messages);
                    progressBar.setVisibility(View.INVISIBLE);
                }
                recyclerAdapter = new RecyclerAdapter(messagesArrayList, getApplicationContext());
                recyclerView.setAdapter(recyclerAdapter);
                recyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
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