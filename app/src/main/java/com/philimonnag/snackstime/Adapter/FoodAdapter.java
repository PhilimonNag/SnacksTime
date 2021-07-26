package com.philimonnag.snackstime.Adapter;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.philimonnag.snackstime.Model.Food;
import com.philimonnag.snackstime.R;
import com.philimonnag.snackstime.databinding.FoodItemBinding;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {
    Context context;
    ArrayList<Food> arrayList;

    public FoodAdapter(Context context, ArrayList<Food> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.food_item,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull FoodAdapter.ViewHolder holder, int position) {
        Food food=arrayList.get(position);
        holder.binding.FoodName.setText(food.getName());
        holder.binding.FoodDescription.setText(food.getDescription());
        Picasso.get().load(food.getFoodImg()).into(holder.binding.FoodImg);
        holder.binding.FoodPrice.setText(" Rs."+ food.getPrice());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putString("name",food.getName());
                bundle.putString("Description",food.getDescription());
                bundle.putString("FoodImg",food.getFoodImg());
                bundle.putString("Price", food.getPrice());
                Navigation.findNavController(v).navigate(R.id.action_foodFragment_to_cartDetailsFragment,bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        FoodItemBinding binding;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            binding=FoodItemBinding.bind(itemView);
        }
    }
}
