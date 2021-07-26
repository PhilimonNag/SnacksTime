package com.philimonnag.snackstime;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.philimonnag.snackstime.databinding.ActivityMainBinding;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    NavController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        controller= Navigation.findNavController(this,R.id.nav_host_fragment_activity_main);
        NavigationUI.setupWithNavController(binding.navView,controller);
        controller.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull @NotNull NavController controller, @NonNull @NotNull NavDestination destination, @Nullable @org.jetbrains.annotations.Nullable Bundle arguments) {
                switch (destination.getId()){
                    case  R.id.homeFragment:
                        show();
                        break;
                    case R.id.accountFragment:
                        show();
                        break;
                    case R.id.cartsFragment:
                        show();
                        break;
                    case R.id.searchFragment:
                        show();
                        break;
                    default:
                        hide();

                }
            }
        });
    }

    private void show() {
        binding.navView.setVisibility(View.VISIBLE);
    }
    private void hide(){
        binding.navView.setVisibility(View.GONE);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return super.onSupportNavigateUp();
    }
}