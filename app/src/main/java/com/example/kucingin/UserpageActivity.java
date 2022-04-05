package com.example.kucingin;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;
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
                String usernameInput = username.getText().toString();
                if(usernameInput.trim().length() == 0){
                    Toast.makeText(UserpageActivity.this, "Please Insert Username", Toast.LENGTH_SHORT).show();
                }
                else {
                    dashboard.putExtra("username", usernameInput);
                    startActivity(dashboard);
                }
            }
        });
    }
}