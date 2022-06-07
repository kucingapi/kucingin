package com.example.kucingin;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.kucingin.Dataset.Card;
import com.example.kucingin.Dataset.CatDataset;
import com.example.kucingin.Dataset.CatFoodDataSet;
import com.example.kucingin.Dataset.MedicineDataset;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kucingin.databinding.ActivityMainBinding;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MainActivity extends AppCompatActivity  {

private ActivityMainBinding binding;
    private Intent intent;
    private String username;
    private RecyclerView popular;
    private Card[] dataset;
    private TextView usernameTextView;
    private MaterialButton logout, addData;
    private CardAdapter cardAdapter;
    private CatFoodDataSet foodDataset ;
    private CatDataset catDataset;
    private MedicineDataset medicineDataset;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent = getIntent();
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        usernameTextView = binding.usernameTextview;
        logout = binding.logout;
        addData = binding.addData;
        db = FirebaseFirestore.getInstance();

        addData.setOnClickListener(v->{
            Intent intent = new Intent(MainActivity.this, AddCatActivity.class);
            startActivity(intent);
        });

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
                case R.id.navigation_medicine:
                    initMedicineDataset();
                    break;
            }
            return true;
        });
        navView.setSelectedItemId(R.id.navigation_cat);
    }

    private void initDataset() {
        catDataset = new CatDataset();
        foodDataset = new CatFoodDataSet();
        medicineDataset = new MedicineDataset();
    }

    private void initMedicineDataset(){
        db = FirebaseFirestore.getInstance();
        db.collection("medicine")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            medicineDataset.setMedicines(task);
                            dataset = medicineDataset.getMedicineData();
                            cardAdapter.setLocalDataSet(dataset);
                        } else {
                            Log.d("dataset", "Error getting documents: ", task.getException());
                        }
                    }
                });
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