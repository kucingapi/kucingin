package com.example.kucingin;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.AppCompatImageButton;
import com.example.kucingin.databinding.ActivityDetailCatBinding;
import com.google.android.material.button.MaterialButton;

public class detail_cat extends AppCompatActivity {
    private ActivityDetailCatBinding binding;
    private MaterialButton moreInfo;
    private AppCompatImageButton backButton;
    private ImageView shareButton;
    private Intent sendIntent, browserIntent,shareIntent;

    private void createSendIntent(){
        sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "British Short Hair");
        sendIntent.setType("text/plain");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailCatBinding.inflate(getLayoutInflater());

        browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://en.wikipedia.org/wiki/British_Shorthair"));
        createSendIntent();
        shareIntent = Intent.createChooser(sendIntent, null);

        setContentView(binding.getRoot());

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
}