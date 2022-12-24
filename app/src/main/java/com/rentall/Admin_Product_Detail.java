package com.rentall;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class Admin_Product_Detail extends AppCompatActivity {

    RecyclerView recyclerView;
    private DatabaseReference myRef;
    private ArrayList<Messages> messagesArrayList;
    private AdminAdapter recyclerAdapter;
    private ProgressBar progressBar;
    private TextView list_header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_product_detail);

        recyclerView = findViewById(R.id.RecyclerView);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        list_header=findViewById(R.id.list_header);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

//        myRef = FirebaseDatabase.getInstance().getReference("ProductForRent");
        final String sessionId = getIntent().getStringExtra("admin_category_name");
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

                    messages.setImageUrl(snapshot1.child("p_img").getValue(String.class));
                    messages.setId(snapshot1.child("p_id").getValue(String.class));
                    messages.setName(snapshot1.child("p_name").getValue(String.class));
                    messages.setPrice(snapshot1.child("p_price").getValue(String.class));
                    messages.setRefund(snapshot1.child("p_refund").getValue(String.class));
                    messages.setDescription(snapshot1.child("p_desc").getValue(String.class));

                    messagesArrayList.add(messages);
                    progressBar.setVisibility(View.INVISIBLE);
                }
                recyclerAdapter = new AdminAdapter(messagesArrayList, getApplicationContext());
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