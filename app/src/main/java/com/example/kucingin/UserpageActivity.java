package com.example.kucingin;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.kucingin.databinding.ActivityUserpageBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserpageActivity extends AppCompatActivity {
    private ActivityUserpageBinding binding;
    private TextInputEditText email;
    private TextInputEditText password;
    private MaterialButton login, register;
    private Intent dashboard;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityUserpageBinding.inflate(getLayoutInflater());

        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();

        dashboard = new Intent(UserpageActivity.this, MainActivity.class);
        email = binding.usernameInput;
        password = binding.password;
        login = binding.buttonLogin;
        register = binding.buttonRegister;

        login.setOnClickListener(login());
        register.setOnClickListener(register());
    }
    
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            updateUI(currentUser);
        }
    }

    public boolean isValidate(String usernameInput, String passwordInput){
        if(usernameInput.trim().length() == 0 || passwordInput.trim().length() == 0){
            Toast.makeText(UserpageActivity.this, "Please Insert Username", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public View.OnClickListener login(){
       return v -> {
           String usernameInput = email.getText().toString();
           String passwordInput = password.getText().toString();
           boolean inputValidate = isValidate(usernameInput, passwordInput);
           if(inputValidate){
               mAuth.signInWithEmailAndPassword(usernameInput, passwordInput)
                       .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                           @Override
                           public void onComplete(@NonNull Task<AuthResult> task) {
                               if (task.isSuccessful()) {
                                   // Sign in success, update UI with the signed-in user's information
                                   Log.d("sign in", "signInWithEmail:success");
                                   FirebaseUser user = mAuth.getCurrentUser();
                                   updateUI(user);
                               } else {
                                   // If sign in fails, display a message to the user.
                                   Log.w("sign in", "signInWithEmail:failure", task.getException());
                                   Toast.makeText(UserpageActivity.this, "Authentication failed.",
                                           Toast.LENGTH_SHORT).show();
                                   updateUI(null);
                               }
                           }
                       });           }
       };
    }

    public View.OnClickListener register(){
        return v -> {
            String usernameInput = email.getText().toString();
            String passwordInput = password.getText().toString();
            boolean inputValidate = isValidate(usernameInput, passwordInput);
            if(inputValidate){
                dashboard.putExtra("username", usernameInput);
                startActivity(dashboard);
                mAuth.createUserWithEmailAndPassword(usernameInput, passwordInput)
                        .addOnCompleteListener(UserpageActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d("create user", "createUserWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w("create user", "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(UserpageActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                    updateUI(null);
                                }
                            }
                        });
            }
        };
    }

    public void updateUI(FirebaseUser user){
        if(user != null){
            dashboard.putExtra("username", user.getEmail());
            startActivity(dashboard);
        }
    }
}