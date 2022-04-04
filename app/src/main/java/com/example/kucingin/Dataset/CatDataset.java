package com.example.kucingin.Dataset;

import com.example.kucingin.R;

public class CatDataset {
    private CardType[] cats;
    public CatDataset() {
        cats = new CardType[]{
            createCard(R.drawable.cat_shorthair, "British Shorthair", "The British Shorthair is the pedigreed version of the traditional British domestic cat, with a distinctively..."),
            createCard(R.drawable.cat_angora, "Turkish Angora", "The Turkish Angora is a breed of domestic cat. Turkish Angoras are one of the ancient, natural breeds of cat..."),
            createCard(R.drawable.cat_persian, "Persian", "The Persian cat is a long-haired breed of cat characterized by its round face and short muzzle. It is also known as..."),
            createCard(R.drawable.cat_sphynx, "Sphynx", "The sphynx cat is a breed of cat known for its lack of fur. Hairlessness in cats is a naturally occurring genetic mutation...")
        };
    }

    public CardType[] getCats() {
        return cats;
    }

    private CardType createCard(int imageId,String title, String description){
        return new CardType(imageId, title, description);
    }
}
