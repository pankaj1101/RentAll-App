package com.rentall;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.*;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;

public class Home extends Fragment {
    private ImageView imageView1, imageView2, imageView3, imageView4;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        imageView1 = view.findViewById(R.id.imageView1);
        imageView2 = view.findViewById(R.id.imageView2);
        imageView3 = view.findViewById(R.id.imageView3);
        imageView4 = view.findViewById(R.id.imageView4);

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
        return view;
    }

    private void openNextActivity(String text) {
        Intent intent = new Intent(getActivity(), ProductList.class);
        intent.putExtra("category_name", text);
        startActivity(intent);
    }
}




