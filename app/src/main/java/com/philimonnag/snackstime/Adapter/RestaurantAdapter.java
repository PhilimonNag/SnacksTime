package com.philimonnag.snackstime.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.philimonnag.snackstime.Model.Restaurant;
import com.philimonnag.snackstime.R;
import com.philimonnag.snackstime.databinding.RestaurantItemBinding;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ViewHolder> {
    Context context;
    ArrayList<Restaurant>arrayList;

    public RestaurantAdapter(Context context, ArrayList<Restaurant> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @NotNull
    @Override
    public RestaurantAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.restaurant_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RestaurantAdapter.ViewHolder holder, int position) {
          Restaurant restaurant=arrayList.get(position);
          holder.binding.Description.setText(restaurant.getDescription());
          holder.binding.Name.setText(restaurant.getName());
          if(restaurant.getRestaurantImg()!=null){
              Picasso.get().load(restaurant.getRestaurantImg()).into(holder.binding.hotelImg);
          }
          holder.itemView.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  Bundle bundle=new Bundle();
                  bundle.putString("HotelId",restaurant.getRestaurantId());
                  Navigation.findNavController(v).navigate(R.id.action_homeFragment_to_foodFragment,bundle);
              }
          });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RestaurantItemBinding binding;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            binding=RestaurantItemBinding.bind(itemView);
        }
    }
}
