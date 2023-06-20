package com.example.eeccoomm;



import static com.example.eeccoomm.CartActivity.cartItemList;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.example.eeccoomm.databinding.ActivityOrderPricingBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.Random;
import java.util.UUID;

public class OrderPricingActivity extends AppCompatActivity {

    ActivityOrderPricingBinding binding;
    int mainTotal = 0;
    private String name,number,address,city,postalcode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderPricingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//to get name automatically on field after totalcast
        binding.name.setText((FirebaseAuth.getInstance().getCurrentUser().getDisplayName()));

        binding.placeorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = binding.name.getText().toString();
                number = binding.number.getText().toString();
                address = binding.address.getText().toString();
                city = binding.city.getText().toString();
                placeOrder();
            }
        });

    }

    private void placeOrder() {

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Placing");
        progressDialog.setMessage("order");
        progressDialog.show();
        //123456
        //100000  999999
        String orderNumber = String.valueOf(getRandomNumber(100000,999999));
        OrderModel orderModel= new OrderModel(orderNumber,name,number,city,address,String.valueOf(mainTotal),"220",null,"Xpressbee",String.valueOf(Calendar.getInstance().getTimeInMillis()),"pending",FirebaseAuth.getInstance().getUid());

        FirebaseFirestore.getInstance()
                .collection("orders")
                .document(orderNumber)
                .set(orderModel)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        for (int i = 0;i<cartItemList.size();i++)
                        {
                            CartModel cartModel= cartItemList.get(i);
                            cartModel.setOrderNumber(orderNumber);

                            String id = UUID.randomUUID().toString();//creating id for eche order
                            cartModel.setCartId(id);

                            FirebaseFirestore.getInstance()
                                    .collection("orderProducts")
                                    .document(id)
                                    .set(cartModel);
                        }
                        finish();
                        progressDialog.cancel();
                    }
                });

    }

    public static int getRandomNumber(int min, int max) {
        return (new Random()).nextInt((max - min) + 1) + min;
    }

    @Override
    protected void onStart() {
        super.onStart();
        for(int i= 0 ; i< cartItemList.size();i++)
        {
            CartModel cartModel = cartItemList.get(i);
            int price =Integer.parseInt(cartModel.getProductprice());
            int qty =Integer.parseInt(cartModel.getProductQty());
            int total = price*qty;
            mainTotal+=total;
        }
        binding.expence.setText(String.valueOf(mainTotal));
        binding.delivery.setText("220");
        binding.totalcost.setText(String.valueOf(mainTotal+220));
    }
}