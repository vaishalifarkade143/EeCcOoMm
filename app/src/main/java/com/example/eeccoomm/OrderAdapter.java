package com.example.eeccoomm;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder>
{

    private List<OrderModel> productModelList;
    private Context context;

    public OrderAdapter(Context context) {
        this.context = context;
        productModelList = new ArrayList<>();
    }

    //adaptre me data send karne kliye
    public void addProduct(OrderModel productModel)
    {
        productModelList.add(productModel);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orders_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        //here we request model to fetch data in ProductModel
        OrderModel orderModel = productModelList.get(position);
      //yaha hum data fetch karvayenge OrderModel me jo data get hua hai database se
        holder.name.setText(orderModel.getCustomerName());
        holder.number.setText(orderModel.getCustomerNumber());
        holder.address.setText(orderModel.getCustomerAddress());
        holder.city.setText(orderModel.getCustemerCityName());
        holder.orderNumber.setText(orderModel.getOrderNumber());

        //2.order status check
        if (orderModel.getOrderTrackingNumber() !=  null)
        {
            holder.trackingNumber.setText(orderModel.getOrderTrackingNumber());
        }
        holder.status.setText(orderModel.getOrderStatus());


        int cod = Integer.parseInt(orderModel.getItemExpence())+Integer.parseInt(orderModel.getDeliveryCharges());
        holder.codAmount.setText(String.valueOf(cod));

        //2.to show data in cart along with image
        CartAdapter cartAdapter = new CartAdapter(context);
        holder.productsRecycler.setAdapter(cartAdapter);
        holder.productsRecycler.setLayoutManager(new LinearLayoutManager(context));

        FirebaseFirestore.getInstance()
                .collection("orderProducts")
                .whereEqualTo("orderNumber",orderModel.getOrderNumber())
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> dList = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot ds : dList) {
                            CartModel cartMode = ds.toObject(CartModel.class);
                            cartAdapter.addProduct(cartMode);
                        }
                    }
                });


    }



    @Override
    public int getItemCount() {

        return productModelList.size();
    }

    //yahapar hum set karvayenge data recycler view par
    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        private TextView name,number,address,city,codAmount,orderNumber,status,trackingNumber;

        private RecyclerView productsRecycler;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            number = itemView.findViewById(R.id.number);
            address = itemView.findViewById(R.id.address);
            city = itemView.findViewById(R.id.cityName);
            codAmount = itemView.findViewById(R.id.CodAmount);
            orderNumber = itemView.findViewById(R.id.orderNumber);

            //1.to show data in cart along with image
            productsRecycler = itemView.findViewById(R.id.OrderProductrecycler);

            //1 .order status check
            status = itemView.findViewById(R.id.orderStatus);
            trackingNumber = itemView.findViewById(R.id.trackingNumber);




        }
    }
}
