package com.philimonnag.snackstime;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.philimonnag.snackstime.databinding.FragmentCartDetailsBinding;
import com.squareup.picasso.Picasso;

public class CartDetailsFragment extends Fragment {
    private FragmentCartDetailsBinding binding;
    String name,Description,FoodImg,Price;
    int count=1;
    String portionsCount;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       binding=FragmentCartDetailsBinding.inflate(inflater,container,false);
       View root=binding.getRoot();
        assert getArguments() != null;
        name=getArguments().getString("name");
        Description =getArguments().getString("Description");
        FoodImg=getArguments().getString("FoodImg");
        Price=getArguments().getString("Price");
        binding.DishName.setText(name);
        binding.FoodDescription.setText(Description);
        Picasso.get().load(FoodImg).into(binding.FoodImg);
        binding.foodPrice.setText(Price);
        binding.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ++count;
                portionsCount=String.valueOf(count);
                binding.Portion.setText(portionsCount);
                binding.foodPrice.setText(Price);
            }
        });
        binding.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                --count;
                portionsCount=String.valueOf(count);
                binding.Portion.setText(portionsCount);
                binding.foodPrice.setText(Price);
            }
        });
        binding.AddFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putString("FoodName",name);
                bundle.putString("FoodImg",FoodImg);
                bundle.putString("PortionCount",portionsCount);
                bundle.putString("FoodPrice",Price);
                Navigation.findNavController(v).navigate(R.id.action_cartDetailsFragment_to_paymentMethodFragment,bundle);
            }
        });
       return  root;
    }
}