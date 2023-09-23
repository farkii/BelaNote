package com.example.belanote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.belanote.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private BottomNavigationView navBar;

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        navBar = this.findViewById(R.id.bottomNavBar);

        binding.bottomNavBar.setOnItemSelectedListener(item -> {
            switch(item.getItemId()){
                case R.id.pageHome:{
                    zamijeniFragment(new HomeFragment());
                    break;
                }
                case R.id.pageStatistics:{
                    zamijeniFragment(new StatsFragment());
                    break;
                }
                case R.id.pageSettings:{
                    zamijeniFragment(new PostavkeFragment());
                    break;
                }
            }

            return true;
        });

        navBar.setSelectedItemId(R.id.pageHome);
    }


    private void zamijeniFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentFrame, fragment);
        fragmentTransaction.commit();
    }
}