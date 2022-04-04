package com.example.kucingin;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.kucingin.databinding.ActivityUserpageBinding;

public class UserpageActivity extends AppCompatActivity {
    private ActivityUserpageBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityUserpageBinding.inflate(getLayoutInflater());

        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
    }
}