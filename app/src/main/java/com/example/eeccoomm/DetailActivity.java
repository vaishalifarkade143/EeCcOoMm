package com.example.eeccoomm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.eeccoomm.databinding.ActivityDetailBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DetailActivity extends AppCompatActivity
{
    ActivityDetailBinding binding;
    private  ProductModel productModel;
    public static List<CartModel> cartItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //geting the intent
        Intent intent = getIntent();

        productModel = (ProductModel) intent.getSerializableExtra("model");

        binding.title.setText(productModel.getTitle());
        binding.description.setText(productModel.getDecription());
        binding.price.setText(productModel.getPrice());

        Glide.with(binding.getRoot())
                .load(productModel.getImage())
                .into(binding.image);

        binding.addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomSheet(0);
              //  addToCart(qty);
            }
        });

        //1.buy now
        binding.buyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //2.buy now
                showBottomSheet(1);
            }
        });
    }

    private void showBottomSheet(int i) {    //3. buy now    int i
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
      //  View view = LayoutInflater.from(DetailActivity.this).inflate(R.layout.bottom_layout,(LinearLayout)findViewById(R.id.mainLayout),false);
        View view = LayoutInflater.from(DetailActivity.this).inflate(R.layout.bottom_layout,(LinearLayout)findViewById(R.id.mainLayout),false);
        bottomSheetDialog.setContentView(view);
        EditText qty = view.findViewById(R.id.qty);
        Button btn = view.findViewById(R.id.checkout);
        bottomSheetDialog.show();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String quntity = qty.getText().toString();
                //Add to cart
                if(i==0) {
                    //Add to cart
                    addToCart(quntity);
                    bottomSheetDialog.cancel();
                }
                //4.buy now
                else if (i==1)
                {
                    CartModel cartModel = new CartModel(null, productModel.getTitle(),productModel.getImage(),productModel.getPrice(),quntity,FirebaseAuth.getInstance().getUid(), null);
                    cartItemList = new ArrayList<>();
                    cartItemList.add(cartModel);
                    startActivity(new Intent(DetailActivity.this,OrderPricingActivity.class));
                    bottomSheetDialog.cancel();
                }
            }
        });

    }

    private void addToCart(String qty) {
//        ProgressDialog progressDialog = new ProgressDialog(this);
//        progressDialog.setTitle("Adding...");
//        progressDialog.setMessage("Item in cart");
//        progressDialog.show();

        String id = UUID.randomUUID().toString();
        CartModel cartModel = new CartModel(id,productModel.getTitle(),productModel.getImage(),productModel.getPrice(),qty, FirebaseAuth.getInstance().getUid(),null);

        //cart ka collection banayenge
        FirebaseFirestore.getInstance()
                .collection("cart")
                .document(id)
                .set(cartModel);
        Toast.makeText(this, "Add To Cart", Toast.LENGTH_SHORT).show();
    }
}