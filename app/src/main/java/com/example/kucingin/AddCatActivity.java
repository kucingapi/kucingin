package com.example.kucingin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.kucingin.Dataset.Card;
import com.example.kucingin.Dataset.CardType;
import com.example.kucingin.Dataset.CardUri;
import com.example.kucingin.databinding.ActivityAddCatBinding;
import com.example.kucingin.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class AddCatActivity extends AppCompatActivity {
    private ActivityAddCatBinding binding;
    private Intent intent;
    private ImageButton btnAdd;
    private TextInputEditText name, careIntruction;
    private MaterialButton add;
    private FirebaseFirestore db;
    private Uri imageUri;
    private StorageReference storageReference;
    private AppState appState;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent = getIntent();
        binding = ActivityAddCatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        name = binding.usernameInput;
        careIntruction = binding.usernameInput;
        add = binding.buttonMoreInfo;
        btnAdd = binding.btnAddCat;
        db = FirebaseFirestore.getInstance();
        appState = AppState.START;
        progressDialog = new ProgressDialog(AddCatActivity.this);

        btnAdd.setOnClickListener(v->{
            selectImage();
        });
        add.setOnClickListener(v -> {
            addData();
        });
    }

    public void addData() {
        progressDialog.setMessage("Uploading Image");
        progressDialog.setProgress(0);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.show();
        int lengthInstruction = careIntruction.getText().toString().trim().length();
        int lengthName = name.getText().toString().trim().length();
        if(lengthInstruction >= 0 && lengthName >= 0 && appState.equals(AppState.UPLOAD_IMAGE)) {
            uploadImage();
        }
    }

    private void uploadImage() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.CANADA);
        Date now = new Date();
        String fileName = formatter.format(now);
        storageReference = FirebaseStorage.getInstance().getReference(fileName);

        storageReference.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        progressDialog.setMessage("downloading URL");
                        progressDialog.setProgress(30);
                        getDownloadUri();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("storage failed", e.toString());
                Toast.makeText(AddCatActivity.this,"Failed to Upload",Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void getDownloadUri(){
        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                imageUri = uri;
                progressDialog.setMessage("Adding to Firebase");
                progressDialog.setProgress(60);
                addToFirebase();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
    }

    public void addToFirebase() {
        Card card = new CardUri(imageUri, Objects.requireNonNull(name.getText()).toString(),
                Objects.requireNonNull(careIntruction.getText()).toString(), CardType.CAT);
        db.collection("medicine")
                .add(card)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        progressDialog.setMessage("Added to Firebase");
                        progressDialog.setProgress(100);
                        progressDialog.dismiss();
                        Toast.makeText(AddCatActivity.this,"Data Successfully Uploaded",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("error adddata", "Error adding document", e);
                    }
                });
    }


    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,100);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && data != null && data.getData() != null){
            imageUri = data.getData();
            btnAdd.setImageURI(imageUri);
            btnAdd.setBackgroundResource(0);
            btnAdd.setOnClickListener(v->{
            });
            appState = AppState.UPLOAD_IMAGE;
        }
    }

}

enum AppState {
    START,
    UPLOAD_IMAGE,
    SUBMIT_FORM,
}
