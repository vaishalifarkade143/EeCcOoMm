package com.example.eeccoomm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.eeccoomm.databinding.ActivityDashboardBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class DashboardActivity extends AppCompatActivity {
    ActivityDashboardBinding binding;
    private ProductAdapter productAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //set toolbar
        setSupportActionBar(binding.mytoolbar);

        productAdapter = new ProductAdapter(this);
        binding.productRecycler.setAdapter(productAdapter);

        binding.productRecycler.setLayoutManager(new LinearLayoutManager(this));

        getProducts();

        //on cart icon to check the product added in Cart
        binding.cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this,CartActivity.class));
            }
        });

        //on profile imageview icon
        binding.profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this,ProfileActivity.class));
            }
        });

    }

//
    private void getProducts() {
        FirebaseFirestore.getInstance()
                .collection("products")
                .whereEqualTo("show",true)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    List<DocumentSnapshot> dsList = queryDocumentSnapshots.getDocuments();
                            for(DocumentSnapshot ds : dsList)
                            {
                                ProductModel productModel = ds.toObject(ProductModel.class);
                                productAdapter.addProduct(productModel);
                            }
                    }
                });

    }
}