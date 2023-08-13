package com.rentall;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.*;
import com.rentall.model.ProductDetailsModel;

import java.util.HashMap;

public class AdminAddProduct extends AppCompatActivity {

    private Button uploadBtn;
    private ProgressBar progressBar;
    private ImageView imageView;
    private EditText p_name, p_price, p_desc;
    private String name, price, description;
//    private String refund;

    String refund_amount;
    //list of category
    String[] product = {"Electronic", "Fashion", "Books", "Hardware", "Real Estate", "Vehicle", "Gym Equipment"};

    static char num[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    //product stock in firebase
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference("Stock");
    private StorageReference reference;
    private Uri imageUri;

    private HashMap<Object, String> productdetail = new HashMap<>();
    private Spinner spino;

    String product_items = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_product);

        uploadBtn = findViewById(R.id.uploadbtn);
        imageView = findViewById(R.id.firebaseimage);
        p_name = findViewById(R.id.product_name);
        p_price = findViewById(R.id.product_price);
        p_desc = findViewById(R.id.product_description);
        progressBar = findViewById(R.id.progressBar);
        spino = findViewById(R.id.coursesspinner);

        progressBar.setVisibility(View.INVISIBLE);

        ArrayAdapter ad = new ArrayAdapter(this, android.R.layout.simple_spinner_item, product);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spino.setAdapter(ad);

        spino.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                product_items = parent.getItemAtPosition(position).toString();

                reference = FirebaseStorage.getInstance().getReference("stock/" + product_items);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, 2);
            }
        });

        uploadBtn.setOnClickListener(v -> {

            name = p_name.getText().toString();
            price = p_price.getText().toString().trim();
            refund_amount = get_refund_amount(price);

            description = p_desc.getText().toString();

            if (imageUri == null && TextUtils.isEmpty(name) && TextUtils.isEmpty(price) && TextUtils.isEmpty(description)) {
                print_message("Please Fill the detail");
            } else {
                uploadToFirebase(name, price, description, refund_amount, imageUri);

            }
        });
    }

    private static String get_refund_amount(String price) {
        Double data = Double.parseDouble(price) * 0.60;
        int r_price = (int) Math.round(data);
        return r_price + "";
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
        }
    }

    private void uploadToFirebase(String name, String price, String description, String refund, Uri uri) {

        //my_product id

//        final String product_id = UUID.randomUUID().toString().substring(0, 15);
        final String product_id = generate_id();

        final StorageReference fileRef = reference.child(product_id).child(uri.toString().substring(0, 8));

        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        ProductDetailsModel messages = new ProductDetailsModel(name, price, description, refund, uri.toString());
//                        String modelId = root.push().getKey();

                        productdetail.put("p_id", product_id);
                        productdetail.put("p_name", messages.getName());
                        productdetail.put("p_price", messages.getPrice());
                        productdetail.put("p_desc", messages.getDescription());
                        productdetail.put("p_refund", messages.getRefund());
                        productdetail.put("p_img", messages.getImageUrl());
                        productdetail.put("p_category", product_items);

//                        root.child(product_id).setValue(productdetail);
                        root.child(product_items).child(product_id).setValue(productdetail);

                        progressBar.setVisibility(View.INVISIBLE);
                        print_message("Uploaded Successfully");
                        p_name.setText("");
                        p_price.setText("");
                        p_desc.setText("");
                        imageView.setImageResource(R.drawable.addimage);
                        startActivity(new Intent(AdminAddProduct.this, AdminPage.class));
                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                progressBar.setVisibility(View.VISIBLE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressBar.setVisibility(View.INVISIBLE);
                print_message("Uploading Failed !!");
            }
        });
    }

    public static String generate_id() {
        StringBuilder strBuilder = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            strBuilder.append(randomNum());
        }
        //  System.out.println("P" + strBuilder);
        return "P" + strBuilder;
    }

    public static char randomNum() {
        return num[(int) Math.floor(Math.random() * 10)];
    }

    public void print_message(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}
