package com.example.kucingin.Dataset;

import com.example.kucingin.R;

public class CatDataset {
    private CardType[] cats;
    public CatDataset() {
        cats = new CardType[]{
            createCard(R.drawable.british_short_hair, "British Shorthair", "The British Shorthair is the pedigreed version of the traditional British domestic cat, with a distinctively..."),
            createCard(R.drawable.british_short_hair, "British Shorthair", "The British Shorthair is the pedigreed version of the traditional British domestic cat, with a distinctively..."),
            createCard(R.drawable.british_short_hair, "British Shorthair", "The British Shorthair is the pedigreed version of the traditional British domestic cat, with a distinctively...")
        };
    }

    public CardType[] getCats() {
        return cats;
    }

    private CardType createCard(int imageId,String title, String description){
        return new CardType(imageId, title, description);
    }
}
