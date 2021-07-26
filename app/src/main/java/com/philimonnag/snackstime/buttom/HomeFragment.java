package com.philimonnag.snackstime.buttom;
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
import com.philimonnag.snackstime.Adapter.RestaurantAdapter;
import com.philimonnag.snackstime.Model.Restaurant;
import com.philimonnag.snackstime.databinding.FragmentHomeBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
public class HomeFragment extends Fragment {
private FragmentHomeBinding binding;
ArrayList<Restaurant>arrayList;
RestaurantAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
     binding= FragmentHomeBinding.inflate(inflater,container,false);
     View root=binding.getRoot();
     arrayList=new ArrayList<>();
     adapter=new RestaurantAdapter(getContext(),arrayList);
     binding.HotelsRV.setHasFixedSize(true);
     binding.HotelsRV.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
     binding.HotelsRV.setAdapter(adapter);
     loadRestaurant();

     return root;
    }

    private void loadRestaurant() {
        FirebaseDatabase.getInstance().getReference().child("Restaurant").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    arrayList.clear();
                    for(DataSnapshot ds:snapshot.getChildren()){
                        Restaurant restaurant=ds.getValue(Restaurant.class);
                        arrayList.add(restaurant);
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}