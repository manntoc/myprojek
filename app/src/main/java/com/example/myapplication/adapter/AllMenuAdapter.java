package com.example.myapplication.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.DetailsBarang;
import com.example.myapplication.R;
import com.example.myapplication.model.Allmenu;

import java.util.List;

public class AllMenuAdapter extends RecyclerView.Adapter<AllMenuAdapter.AllMenuViewHolder> {

    Context context;
    List<Allmenu> allmenuList;

    public AllMenuAdapter(Context context, List<Allmenu> allmenuList) {
        this.context = context;
        this.allmenuList = allmenuList;
    }

    @NonNull
    @Override
    public AllMenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.allmenu_recycler_items, parent, false);

        return new AllMenuViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull AllMenuViewHolder holder, int position) {

        holder.allMenuName.setText(allmenuList.get(position).getName());
        holder.allMenuPrice.setText("₹ "+allmenuList.get(position).getPrice());
        holder.allMenuTime.setText(allmenuList.get(position).getDeliveryTime());
        holder.allMenuRating.setText(allmenuList.get(position).getRating());
        holder.allMenuCharges.setText(allmenuList.get(position).getDeliveryCharges());
        holder.allMenuNote.setText(allmenuList.get(position).getNote());

        Glide.with(context).load(allmenuList.get(position).getImageUrl()).into(holder.allMenuImage);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, DetailsBarang.class);
                i.putExtra("name", allmenuList.get(holder.getAdapterPosition()).getName());
                i.putExtra("price", allmenuList.get(holder.getAdapterPosition()).getPrice());
                i.putExtra("rating", allmenuList.get(holder.getAdapterPosition()).getRating());
                i.putExtra("image", allmenuList.get(holder.getAdapterPosition()).getImageUrl());

                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return allmenuList.size();
    }

    public static class AllMenuViewHolder extends RecyclerView.ViewHolder{

        TextView allMenuName, allMenuNote, allMenuRating, allMenuTime, allMenuCharges, allMenuPrice;
        ImageView allMenuImage;

        public AllMenuViewHolder(@NonNull View itemView) {
            super(itemView);

            allMenuName = itemView.findViewById(R.id.all_menu_name);
            allMenuNote = itemView.findViewById(R.id.all_menu_note);
            allMenuCharges = itemView.findViewById(R.id.all_menu_delivery_charge);
            allMenuRating = itemView.findViewById(R.id.all_menu_rating);
            allMenuTime = itemView.findViewById(R.id.all_menu_deliverytime);
            allMenuPrice = itemView.findViewById(R.id.all_menu_price);
            allMenuImage = itemView.findViewById(R.id.all_menu_image);
        }
    }

}
