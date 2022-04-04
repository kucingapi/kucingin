package com.example.kucingin;

import android.content.Intent;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.kucingin.databinding.ActivityUserpageBinding;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class UserpageActivity extends AppCompatActivity {
    private ActivityUserpageBinding binding;
    private TextInputEditText username;
    private MaterialButton login;
    private Intent dashboard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityUserpageBinding.inflate(getLayoutInflater());

        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        dashboard = new Intent(UserpageActivity.this, MainActivity.class);
        username = binding.usernameInput;
        login = binding.buttonLogin;

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dashboard.putExtra("username", username.getText().toString());
                startActivity(dashboard);
            }
        });
    }
}