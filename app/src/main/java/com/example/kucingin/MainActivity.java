package com.example.kucingin;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.kucingin.Dataset.Card;
import com.example.kucingin.Dataset.CatDataset;
import com.example.kucingin.Dataset.CatFoodDataSet;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kucingin.databinding.ActivityMainBinding;
import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {

private ActivityMainBinding binding;
    private Intent intent;
    private String username;
    private RecyclerView popular;
    private Card[] dataset;
    private TextView usernameTextView;
    private MaterialButton logout;
    private CardAdapter cardAdapter;
    private CatFoodDataSet foodDataset ;
    private CatDataset catDataset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent = getIntent();
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        usernameTextView = binding.usernameTextview;
        logout = binding.logout;

        usernameTextView.setText(intent.getStringExtra("username"));
        cardAdapter = new CardAdapter();
        popular = binding.popularRecycleView;
        popular.setAdapter(cardAdapter);
        popular.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));

        initDataset();

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        navView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.navigation_cat:
                    initCatDataset();
                    break;
                case R.id.navigation_cat_food:
                    initCatFoodDataset();
                    break;
            }
            return true;
        });
        navView.setSelectedItemId(R.id.navigation_cat);
    }

    private void initDataset() {
        catDataset = new CatDataset();
        foodDataset = new CatFoodDataSet();
    }
    private void initCatDataset(){
        dataset = catDataset.getCats();
        cardAdapter.setLocalDataSet(dataset);
    }

    private void initCatFoodDataset(){
        dataset = foodDataset.getCatFoods();
        cardAdapter.setLocalDataSet(dataset);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        username = intent.getStringExtra("username");
        outState.putString("username",username);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String savedUsername = savedInstanceState.getString("username").toString();
        intent.putExtra("username",savedUsername);
    }
}