package com.philimonnag.snackstime;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.philimonnag.snackstime.Adapter.FoodAdapter;
import com.philimonnag.snackstime.Model.Food;
import com.philimonnag.snackstime.databinding.FragmentFoodBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class FoodFragment extends Fragment {
private FragmentFoodBinding binding;
    ArrayList<Food> arrayList;
    FoodAdapter adapter;
    String RestaurantId;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentFoodBinding.inflate(inflater,container,false);
        View root= binding.getRoot();
        assert getArguments() != null;
        RestaurantId=getArguments().getString("HotelId");
        arrayList=new ArrayList<>();
        adapter=new FoodAdapter(getContext(),arrayList);
        binding.FoodRV.setHasFixedSize(true);
        binding.FoodRV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));
        binding.FoodRV.setAdapter(adapter);
        if (RestaurantId!=null){
            loadFood(RestaurantId);
        }
        return root;

    }

    private void loadFood(String RestaurantId) {
        FirebaseDatabase.getInstance().getReference("FoodItems")
                .child(RestaurantId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            arrayList.clear();
                            for(DataSnapshot ds:snapshot.getChildren()){
                                Food food=ds.getValue(Food.class);
                                arrayList.add(food);
                            }

                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error)  {
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}