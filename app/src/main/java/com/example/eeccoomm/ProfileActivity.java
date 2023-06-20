package com.example.eeccoomm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.example.eeccoomm.databinding.ActivityProfileBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class ProfileActivity extends AppCompatActivity {
    ActivityProfileBinding binding;
    private OrderAdapter orderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.name.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        binding.email.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());

        orderAdapter = new OrderAdapter(this);
        binding.orderRecycler.setAdapter(orderAdapter);
        binding.orderRecycler.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart() {
        super.onStart();
        getOrders();
    }

    private  void  getOrders()
    {
        FirebaseFirestore.getInstance()
                .collection("orders")
                .whereEqualTo("uid",FirebaseAuth.getInstance().getUid())
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        //hum yahapar pure order ki list mili
                        List<DocumentSnapshot> dList = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot ds : dList)
                        {
                            OrderModel orderModel = ds.toObject(OrderModel.class);
                            //yaha tak order aa gaya===//

                            //ab order ko adapter se match karna padega for tht we create order Adapter
                            orderAdapter.addProduct(orderModel);
                        }
                    }
                });
    }
}