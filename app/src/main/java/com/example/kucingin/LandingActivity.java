package com.example.kucingin;

import android.content.Intent;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.kucingin.databinding.LandingPageBinding;
import com.google.android.material.button.MaterialButton;

public class LandingActivity extends AppCompatActivity {
    private LandingPageBinding binding;
    private MaterialButton getStarted;
    private Intent login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = LandingPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        login = new Intent(LandingActivity.this, UserpageActivity.class);

        getStarted = binding.buttonGetStarted;
        getStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(login);
            }
        });

    }
}