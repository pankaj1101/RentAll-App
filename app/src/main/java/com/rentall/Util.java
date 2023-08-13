package com.rentall;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;


public class Util {
    public static Task<Void> removeProduct(String product_id,String productitem) {
            Task<Void>  task = FirebaseDatabase.getInstance().getReference("Stock").child(productitem).child(product_id).setValue(null);
        return task;
    }
}
