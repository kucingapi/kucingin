package com.example.kucingin.Dataset;

import com.example.kucingin.R;

public class CatDataset {
    private Card[] cats;
    public CatDataset() {
        cats = new Card[]{
            createCard(R.drawable.cat_shorthair, "British Shorthair", "The British Shorthair is the pedigreed version of the traditional British domestic cat, with a distinctively stocky body, dense coat, and broad face. The most familiar colour variant is the \"British Blue\", with a solid grey-blue coat, orange eyes, and a medium-sized tail. The breed has also been developed in a wide range of other colours and patterns, including tabby and colourpoint.", CardType.CAT),
            createCard(R.drawable.cat_angora, "Turkish Angora", "The longhaired Angora cat is not the source for angora sweaters, although his fur is certainly just as soft and beautiful. This natural breed takes his name from the city of Ankara in Turkey, which was formerly known as Angora. For centuries, the cats have been attractive souvenirs for invaders of or visitors to Turkey and may have been the first longhaired cats to arrive in Europe. One theory suggests that Vikings brought them from Turkey more than a thousand years ago.", CardType.CAT),
            createCard(R.drawable.cat_persian, "Persian", "he Persian is the most popular pedigreed cat in North America, if not the world. He first came into vogue during the Victorian era, but he existed long before then. Little is known about his early history, though.\u2028\u2028The Persian comes in two types: show and traditional. The show Persian has a round head enhanced with a thick ruff, small ears, a flat nose, big round copper eyes, a broad, short body with heavy boning atop short tree-trunk legs, and a thick, flowing plume of a tail. The traditional Persian, also known as the Doll Face, does not have the extreme features of the show Persian, and his nose is a normal length, giving him a sweet expression. Both types have a long, glamorous coat that comes in many colors and patterns, and both share the same wonderful personality.", CardType.CAT),
            createCard(R.drawable.cat_sphynx, "Sphynx", "The most obvious feature of this striking cat is their lack of a fur coat; however, Sphynx cats vary in the degree of hairlessness, with some having a very fine ‘peach fuzz’ all over and others just a fine fuzz over the extremities. Due to the lack of fur, the Sphynx cats bone structure and musculature is there for all to see, and this is a remarkably robust cat, built on elegant long lines, with somewhat loose skin that forms wrinkles in some places. ", CardType.CAT)
        };
    }

    public Card[] getCats() {
        return cats;
    }

    private Card createCard(int imageId, String title, String description, CardType type){
        return new Card(imageId, title, description, type);
    }
}
