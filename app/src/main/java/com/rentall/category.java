package com.rentall;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class category extends Fragment {

    private Button expand_electronics, expand_fashion,
            expand_books, Equipment, HardWare, Vehicles, estate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category, container, false);

        expand_electronics = view.findViewById(R.id.expand_electronics);
        expand_fashion = view.findViewById(R.id.expand_fashion);
        expand_books = view.findViewById(R.id.expand_books);
        Equipment = view.findViewById(R.id.Equipment);
        HardWare = view.findViewById(R.id.HardWare);
        Vehicles = view.findViewById(R.id.Vehicles);
        estate = view.findViewById(R.id.estate);

        expand_electronics.setOnClickListener(V -> {
            Intent intent = new Intent(getActivity(), ProductList.class);
            intent.putExtra("category_name", "Electronic");
            startActivity(intent);
        });
        expand_fashion.setOnClickListener(V -> {
            Intent intent = new Intent(getActivity(), ProductList.class);
            intent.putExtra("category_name", "Fashion");
            startActivity(intent);
        });
        expand_books.setOnClickListener(V -> {
            Intent intent = new Intent(getActivity(), ProductList.class);
            intent.putExtra("category_name", "Books");
            startActivity(intent);
        });
        Equipment.setOnClickListener(V -> {
            Intent intent = new Intent(getActivity(), ProductList.class);
            intent.putExtra("category_name", "Gym Equipment");
            startActivity(intent);
        });
        HardWare.setOnClickListener(V -> {
            Intent intent = new Intent(getActivity(), ProductList.class);
            intent.putExtra("category_name", "Hardware");
            startActivity(intent);
        });
        Vehicles.setOnClickListener(V -> {
            Intent intent = new Intent(getActivity(), ProductList.class);
            intent.putExtra("category_name", "Vehicle");
            startActivity(intent);
        });
        estate.setOnClickListener(V -> {
            Intent intent = new Intent(getActivity(), ProductList.class);
            intent.putExtra("category_name", "Real Estate");
            startActivity(intent);
        });

        return view;
    }
}