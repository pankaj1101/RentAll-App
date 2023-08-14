package com.rentall;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;

public class category extends Fragment {

    String[] productList = {"Electronic", "Hardware", "Fashion", "Gym Equipment", "Books", "Vehicle", "Real Estate", "Other"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_category, container, false);

        GridLayout gridLayout = view.findViewById(R.id.product_grid);

        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            View childView = gridLayout.getChildAt(i);

            childView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    int position = gridLayout.indexOfChild(childView);
                    int row = position / gridLayout.getColumnCount();
                    int column = position % gridLayout.getColumnCount();

                    if (row == 0 && column == 0) {
                        showProductList(productList[0]);
                    } else if (row == 0 && column == 1) {
                        showProductList(productList[1]);
                    } else if (row == 1 && column == 0) {
                        showProductList(productList[2]);
                    } else if (row == 1 && column == 1) {
                        showProductList(productList[3]);
                    } else if (row == 2 && column == 0) {
                        showProductList(productList[4]);
                    } else if (row == 2 && column == 1) {
                        showProductList(productList[5]);
                    } else if (row == 3 && column == 0) {
                        showProductList(productList[6]);
                    } else if (row == 3 && column == 1) {
                        showProductList(productList[7]);
                    }
                    Log.d("gridLayout", row + "" + column);
                }
            });
        }
//        expand_electronics = view.findViewById(R.id.expand_electronics);
//        expand_fashion = view.findViewById(R.id.expand_fashion);
//        expand_books = view.findViewById(R.id.expand_books);
//        Equipment = view.findViewById(R.id.Equipment);
//        HardWare = view.findViewById(R.id.HardWare);
//        Vehicles = view.findViewById(R.id.Vehicles);
//        estate = view.findViewById(R.id.estate);
//
//        expand_electronics.setOnClickListener(V -> {
//            Intent intent = new Intent(getActivity(), ProductList.class);
//            intent.putExtra("category_name", "Electronic");
//            startActivity(intent);
//        });
//        expand_fashion.setOnClickListener(V -> {
//            Intent intent = new Intent(getActivity(), ProductList.class);
//            intent.putExtra("category_name", "Fashion");
//            startActivity(intent);
//        });
//        expand_books.setOnClickListener(V -> {
//            Intent intent = new Intent(getActivity(), ProductList.class);
//            intent.putExtra("category_name", "Books");
//            startActivity(intent);
//        });
//        Equipment.setOnClickListener(V -> {
//            Intent intent = new Intent(getActivity(), ProductList.class);
//            intent.putExtra("category_name", "Gym Equipment");
//            startActivity(intent);
//        });
//        HardWare.setOnClickListener(V -> {
//            Intent intent = new Intent(getActivity(), ProductList.class);
//            intent.putExtra("category_name", "Hardware");
//            startActivity(intent);
//        });
//        Vehicles.setOnClickListener(V -> {
//            Intent intent = new Intent(getActivity(), ProductList.class);
//            intent.putExtra("category_name", "Vehicle");
//            startActivity(intent);
//        });
//        estate.setOnClickListener(V -> {
//            Intent intent = new Intent(getActivity(), ProductList.class);
//            intent.putExtra("category_name", "Real Estate");
//            startActivity(intent);
//        });

        return view;
    }

    private void showProductList(String title) {
        Intent intent = new Intent(getActivity(), ProductList.class);
        intent.putExtra("category_name", title);
        intent.putExtra("category_list", true);
        startActivity(intent);
    }
}