package com.example.myshop.dataBase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myshop.R;
import com.example.myshop.model.Product;
import org.jetbrains.annotations.NotNull;

import java.security.AccessController;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>
{
    List<Product> productList;
    Context context;


    public RecyclerViewAdapter(List<Product> productList, Context context)
    {
        this.productList=productList;
        this.context=context;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item,parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        return null;
    }


    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerViewAdapter.MyViewHolder holder, int position)
    {
        holder.tv_row_product_name.setText(productList.get(position).getName());
        holder.tv_row_product_category.setText(productList.get(position).getCategoryString());
        holder.tv_row_product_phoneNumber.setText(productList.get(position).getSellerPhoneNumber());
        holder.iv_row_prod_pic.setImageBitmap(productList.get(position).getImage());
    }

    @Override
    public int getItemCount()
    {
        return productList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        ImageView iv_row_prod_pic;
        TextView tv_row_product_name;
        TextView tv_row_product_category;
        TextView tv_row_product_phoneNumber;
        public MyViewHolder(@NonNull @NotNull View itemView)
        {
            super(itemView);
            iv_row_prod_pic=itemView.findViewById(R.id.imageView_row_product_pic);
            tv_row_product_category=itemView.findViewById(R.id.text_view_row_product_category);
            tv_row_product_name=itemView.findViewById(R.id.text_view_row_product_name);
            tv_row_product_phoneNumber=itemView.findViewById(R.id.text_view_row_product_phoneNumber);
        }
    }
}
