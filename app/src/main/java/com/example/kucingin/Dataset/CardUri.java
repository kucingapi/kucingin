package com.example.kucingin.Dataset;

import android.net.Uri;

public class CardUri extends Card{
    public Uri imageId;

    public CardUri(Uri imageId, String title, String description, CardType type) {
        super();
        this.imageId = imageId;
        this.title = title;
        this.description = description;
        this.type = type;
    }
}
