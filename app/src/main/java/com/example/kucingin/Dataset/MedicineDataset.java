package com.example.kucingin.Dataset;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class MedicineDataset {
    private ArrayList<Card> medicines;
    public MedicineDataset() {
        medicines = new ArrayList<>();
    }

    public void setMedicines(@NonNull Task<QuerySnapshot> task){
        for (QueryDocumentSnapshot document : task.getResult()) {
            Map<String, Object> documentMap = document.getData();
            String title = documentMap.get("title").toString();
            String description = documentMap.get("description").toString();
            String imageId = documentMap.get("imageId").toString();
            Uri imageUri = Uri.parse(imageId);
            Card newCard =createCard(title, description, CardType.MEDICINE, imageUri);
            medicines.add(newCard);
        }
    }

    public Card[] getMedicineData() {
        Card[] medicines = new Card[this.medicines.size()];
        medicines = this.medicines.toArray(medicines);
        return medicines;
    }

    private Card createCard( String title, String description, CardType type, Uri imageUri){
        return new Card(title, description, type, imageUri);
    }
}
