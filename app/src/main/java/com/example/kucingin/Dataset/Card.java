package com.example.kucingin.Dataset;

public class Card {
    public int imageId;
    public String title;
    public String description;
    public CardType type;

    public Card(int imageId, String title, String description, CardType type) {
        this.imageId = imageId;
        this.title = title;
        this.description = description;
        this.type = type;
    }
}
