package com.example.kucingin.Dataset;

import com.example.kucingin.R;

public class CatFoodDataSet {
    private CardType[] foods;
    public CatFoodDataSet() {
        foods = new CardType[]{
                createCard(R.drawable.cf_royal_canin, "Royal Canin", "Royal Canin is a French manufacturer of cat and dog food. A subsidiary of Mars, Incorporated, the company also undertakes research into the formulation and testing of breed and symptom specific nutritional requirements of dogs and cats. There’s four type of cat food based on their breed, age, or lifestyle to deliver nutrition tailored to your pet’s health needs."),
                createCard(R.drawable.cf_whiskas, "Whiskas", " Whiskas is a brand of cat food sold internationally. It is owned by the American company Mars, Incorporated. It is available either as small meaty pieces in sauce, gravy or jelly packaged in tins, foil trays or pouches, as well as in the form of small biscuits sold in cartons or pouches and milk sold in small bottles."),
                createCard(R.drawable.cf_purina_pro_plan, "Purina Pro Plan", "Purina Pro Plan is a pet food brands owned by American subsidiary of Nestlé, i.e. Nestlé Purina Petcare. Purina Pro Plan has several lines and flavors to pick from, even the pickiest of eaters will love to eat these foods. The lines also come in both dry and wet food options, ensuring that your cat’s palette is perfectly pleased."),
                createCard(R.drawable.cf_kitchen_flavor, "Kitchen Flavor", "Kitchen Flavor is a cat and dog food from Bridge PetCare Co., Ltd., Shanghai, China. Moreover, Kitchen Flavor is distributed and imported by CP Petindo. There are three variants distinguished by cat’s age, Kitchen Flavor Baby Cat and Kitten, Kitchen Flavor Adult, and Kitchen Flavor Beauty that are suitable for any age with premium quality. Kitchen Flavor differentiate their package based on their varieties.")
        };
    }

    public CardType[] getCatFoods() {
        return foods;
    }

    private CardType createCard(int imageId,String title, String description){
        return new CardType(imageId, title, description);
    }
}
