package com.example.kucingin;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.kucingin.databinding.ActivityDetailCatBinding;

public class detail_cat extends AppCompatActivity {
    private ActivityDetailCatBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailCatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}