package com.example.kucingin;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.AppCompatImageButton;

import com.bumptech.glide.Glide;
import com.example.kucingin.Dataset.CardType;
import com.example.kucingin.databinding.ActivityDetailCatBinding;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DetailCat extends AppCompatActivity {
    private ActivityDetailCatBinding binding;
    private MaterialButton moreInfo;
    private AppCompatImageButton backButton;
    private ImageView shareButton;
    private Intent sendIntent, browserIntent, shareIntent, intent, smsIntent;
    private FirebaseAuth mAuth;
    private String id, title, description, imageUri;

    private void createSendIntent(){
        sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "British Short Hair");
        sendIntent.setType("text/plain");
    }

    private void sendSmsIntent(String title){
        String number = "+62895328079912";  // The number on which you want to send SMS
        smsIntent = new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", number, null));
        String message = "Hello i would like to purchase this food " + title;
        smsIntent.putExtra("sms_body", message);
        startActivity(smsIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailCatBinding.inflate(getLayoutInflater());

        mAuth = FirebaseAuth.getInstance();
        browserIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("https://en.wikipedia.org/wiki/British_Shorthair"));
        createSendIntent();
        shareIntent = Intent.createChooser(sendIntent, null);
        intent = getIntent();
        setContentView(binding.getRoot());

        id = intent.getStringExtra("id");
        title = intent.getStringExtra("title");
        description = intent.getStringExtra("description");
        imageUri = intent.getStringExtra("image_uri");
        int image = intent.getIntExtra("image_id", 1);
        CardType type = (CardType) intent.getSerializableExtra("type");

        switch (type){
            case CAT:
                binding.catFood.setVisibility(View.GONE);
                binding.messageCat.setVisibility(View.GONE);
                break;
            case FOOD:
                binding.cat.setVisibility(View.GONE);
                binding.messageCat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sendSmsIntent(title);
                    }
                });
                break;
            case MEDICINE:
                binding.medicineCatFor.setVisibility(View.VISIBLE);
                break;
        }

        if(imageUri != null)
            Glide.with(DetailCat.this)
                    .load(imageUri)
                    .centerCrop()
                    .placeholder(R.drawable.cat_angora)
                    .into(binding.detailImage);
        else
            binding.detailImage.setImageResource(image);



        binding.detailTitle.setText(title);
        binding.detailDescription.setText(description);

        moreInfo = binding.buttonMoreInfo;
        shareButton = binding.shareButton;
        backButton = binding.buttonBack;

        moreInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(browserIntent);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(shareIntent);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            updateUI();
        }
    }

    private void updateUI() {
        binding.editCat.setVisibility(View.VISIBLE);
        binding.editCat.setOnClickListener(v -> {
            Intent intent = new Intent(DetailCat.this, UpdateActivity.class);
            intent.putExtra("title", title);
            intent.putExtra("description", description);
            intent.putExtra("id", id);
            intent.putExtra("image_uri", imageUri);
            startActivity(intent);
        });
    }
}