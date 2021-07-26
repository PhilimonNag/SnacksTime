package com.philimonnag.snackstime;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.philimonnag.snackstime.Model.Buyers;
import com.philimonnag.snackstime.Model.Order;
import com.philimonnag.snackstime.databinding.FragmentPaymentMethodBinding;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

public class PaymentMethodFragment extends Fragment {
    private FragmentPaymentMethodBinding binding;
    String FoodName,FoodPrice,FoodImg,PortionCount,Address;
    int price;
    String Cost,ContactNo,CustomerName;
    String paymentMethod="Cash";
    FirebaseUser firebaseUser;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       binding=FragmentPaymentMethodBinding.inflate(inflater,container,false);
       View root=binding.getRoot();
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        assert getArguments() != null;
        FoodName=getArguments().getString("FoodName");
        FoodPrice=getArguments().getString("FoodPrice");
        FoodImg=getArguments().getString("FoodImg");
        PortionCount=getArguments().getString("PortionCount");
        Picasso.get().load(FoodImg).into(binding.FoodImage);
        binding.foodName.setText(FoodName);
        price=Integer.parseInt(FoodPrice)*Integer.parseInt(PortionCount);
        Cost=String.valueOf(price);
        FirebaseDatabase.getInstance().getReference().child("Users").
                child(firebaseUser.getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            Buyers buyers=snapshot.getValue(Buyers.class);
                            CustomerName= buyers.getName();
                            ContactNo=buyers.getmNumber();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        binding.foodOrderDetails.setText(FoodPrice+" * "+PortionCount+" = "+"Rs."+Cost);
        binding.cash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paymentMethod="Cash";
            }
        });
        binding.upi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paymentMethod="Upi";
            }
        });
        binding.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paymentMethod="Card";
            }
        });
        binding.Proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Address=binding.deliveryAddress.getText().toString();
                if(TextUtils.isEmpty(Address)){
                    binding.deliveryAddress.setError("Address is required");
                }else{
                    updateDelivery();
                }
            }
        });
       return root;
    }

    private void updateDelivery() {
        Order order= new Order(FoodName,CustomerName,FoodPrice,FoodImg,PortionCount,Address,ContactNo,Cost);
        FirebaseDatabase.getInstance().getReference().child("Order")
                .child(firebaseUser.getUid()).setValue(order).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getContext(), "your Order Placed Successfully", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}