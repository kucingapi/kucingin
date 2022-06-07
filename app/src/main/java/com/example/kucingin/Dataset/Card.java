package com.example.kucingin.Dataset;

import android.net.Uri;

import com.example.kucingin.R;

public class Card {
    public int imageId;
    public String title;
    public String description;
    public CardType type;
    public Uri imageUri;

    public Card(String title, String description, CardType type, Uri imageUri) {
        this.imageId = R.drawable.app_logo;
        this.title = title;
        this.description = description;
        this.type = type;
        this.imageUri = imageUri;
    }

    public Card(int imageId, String title, String description, CardType type) {
        this.imageId = imageId;
        this.title = title;
        this.description = description;
        this.type = type;
    }
}
