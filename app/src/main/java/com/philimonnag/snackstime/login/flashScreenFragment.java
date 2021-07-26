package com.philimonnag.snackstime.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.philimonnag.snackstime.R;
import com.philimonnag.snackstime.databinding.FragmentFlashScreenBinding;

public class flashScreenFragment extends Fragment {
private FragmentFlashScreenBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      binding=FragmentFlashScreenBinding.inflate(inflater,container,false);
      View root= binding.getRoot();
      binding.Login.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Navigation.findNavController(root).navigate(R.id.action_flashScreenFragment_to_signInFragment);
          }
      });
      binding.Register.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Navigation.findNavController(v).navigate(R.id.action_flashScreenFragment_to_mobileFragment);
          }
      });

      return root;
    }
}