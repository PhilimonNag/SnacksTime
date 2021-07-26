package com.philimonnag.snackstime.login;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.philimonnag.snackstime.Model.Buyers;
import com.philimonnag.snackstime.R;
import com.philimonnag.snackstime.databinding.FragmentSignUpBinding;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;


public class SignUpFragment extends Fragment {
private FragmentSignUpBinding binding;
FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding =FragmentSignUpBinding.inflate(inflater,container,false);
        View root= binding.getRoot();
        mAuth=FirebaseAuth.getInstance();
        assert getArguments() != null;
        String mobile=getArguments().getString("phone");
        binding.mobile.setText(mobile);
        binding.SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email=binding.email.getText().toString();
                String Password=binding.password.getText().toString();
                String userName=binding.name.getText().toString();
                if(TextUtils.isEmpty(Email)){
                    binding.email.setError("Email is Required");
                }else if(TextUtils.isEmpty(userName)){
                    binding.name.setError("Your Name is Required");
                }else if(Password.length()<6){
                    Toast.makeText(getContext(), "Password Length Must be greater than 6 ", Toast.LENGTH_SHORT).show();
                }else{
                    Register(Email,Password,userName,mobile,v);
                }
            }
        });

        return root;
    }

    private void Register(String email, String password, String userName,String mobile, View v) {
        FirebaseUser godIsGood= mAuth.getCurrentUser();
        AuthCredential credential = EmailAuthProvider.getCredential(email, password);
        godIsGood.linkWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                    Buyers(email,userName,mobile,v);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void Buyers(String email, String userName,String mobile, View v) {
        String userId= Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        Buyers buyers= new Buyers(userName,userId,email,mobile);
        FirebaseDatabase.getInstance().getReference().child("buyers").
                child(userId).setValue(buyers).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Navigation.findNavController(v).navigate(R.id.action_signUpFragment_to_homeFragment);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}