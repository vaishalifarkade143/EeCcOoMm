package com.example.eeccoomm;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder>
{

    private List<CartModel> productModelList;
    private Context context;

    public CartAdapter(Context context) {
        this.context = context;
        productModelList = new ArrayList<>();
    }

    //adaptre me data send karne kliye
    public void addProduct(CartModel productModel)
    {
        productModelList.add(productModel);
        notifyDataSetChanged();
    }

    public List<CartModel> getSelectedItems()
    {
        List<CartModel> cartitems = new ArrayList<>();
        for(int i=0 ; i<productModelList.size();i++)
        {
            if (productModelList.get(i).selected)
            {
                cartitems.add(productModelList.get(i));
            }
        }
        return cartitems;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        //here we request model to fetch data in ProductModel
        //agar model se data Adapter me chahiye to MyViewHolder ke refernce holder me store hota hai or hum holder through get karenge
        CartModel productModel = productModelList.get(position);
        holder.title.setText(productModel.getProductName());
        holder.price.setText(productModel.getProductprice());
        holder.qty.setText(productModel.getProductQty());//its a cartmodel but we take name as productmodel just variable name

        Glide.with(context)
                .load(productModel.getProductImage())
                .into(holder.img);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (productModel.selected)
                {

                    holder.mainLayout.setBackgroundColor(context.getResources().getColor(R.color.white));
                    productModel.selected=false;
                }
                else
                {
                    //cart ke item jo order karne hai uspe select karne k bad green color ayega or nhi select kiya uska white rahega
                    holder.mainLayout.setBackgroundColor(context.getResources().getColor(R.color.teal_700));
                    productModel.selected=true;
                }
            }
        });

    }



    @Override
    public int getItemCount() {

        return productModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        private TextView title,qty,price;
        private ImageView img;
        private LinearLayout mainLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            qty = itemView.findViewById(R.id.qty);
            price = itemView.findViewById(R.id.price);
            img = itemView.findViewById(R.id.image);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
