package com.rentall;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.*;
import android.view.ViewGroup;
import android.widget.ImageView;

public class Home extends Fragment {

    private ImageView imageView1, imageView2,imageView3,imageView4;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        imageView1 = view.findViewById(R.id.imageView1);
        imageView2 = view.findViewById(R.id.imageView2);
        imageView3 = view.findViewById(R.id.imageView3);
        imageView4 = view.findViewById(R.id.imageView4);

        imageView1.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ProductList.class);
            intent.putExtra("category_name", "Electronic");
            startActivity(intent);
        });
        imageView2.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ProductList.class);
            intent.putExtra("category_name", "Fashion");
            startActivity(intent);
        });
        imageView3.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ProductList.class);
            intent.putExtra("category_name", "Books");
            startActivity(intent);
        });
        imageView4.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ProductList.class);
            intent.putExtra("category_name", "Gym Equipment");
            startActivity(intent);
        });
        return view;
    }
}




