package com.example.kucingin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.kucingin.Dataset.Card;
import com.example.kucingin.Dataset.CardType;
import com.example.kucingin.databinding.ActivityUpdateBinding;
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

public class UpdateActivity extends AppCompatActivity {
    private ActivityUpdateBinding binding;
    private Intent intent;
    private ImageButton btnEdit;
    private TextInputEditText name, careIntruction;
    private MaterialButton update, delete;
    private FirebaseFirestore db;
    private Uri imageUri;
    private StorageReference storageReference;
    private AppState appState;
    private ProgressDialog progressDialog;
    private String id, title, instruction;
    private AppCompatImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent = getIntent();
        binding = ActivityUpdateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        name = binding.usernameInput;
        careIntruction = binding.careInstructionInput;
        update = binding.btnUpdate;
        delete = binding.btnDelete;
        btnEdit = binding.btnEditCat;
        backButton = binding.buttonBack;
        db = FirebaseFirestore.getInstance();
        appState = AppState.START;
        progressDialog = new ProgressDialog(UpdateActivity.this);

        getIntentData();
        putDefaultValue();


        delete.setOnClickListener(v -> {
            deleteDocument();
        });
        btnEdit.setOnClickListener(v->{
            selectImage();
        });
        update.setOnClickListener(v -> {
            addData();
        });
        backButton.setOnClickListener( v -> {
            finish();
        });
    }

    private void getIntentData(){
        id = intent.getStringExtra("id");
        title = intent.getStringExtra("title");
        instruction = intent.getStringExtra("description");
        String imageUri = intent.getStringExtra("image_uri");
        Log.d("instruction", "getIntentData: " + instruction);
        this.imageUri = Uri.parse(imageUri);
    }

    private void putDefaultValue() {
        name.setText(title);
        careIntruction.setText(instruction);
        Glide.with(UpdateActivity.this)
                .load(this.imageUri)
                .centerCrop()
                .placeholder(R.drawable.cat_angora)
                .into(btnEdit);
    }

    public void addData() {
        int lengthInstruction = careIntruction.getText().toString().trim().length();
        int lengthName = name.getText().toString().trim().length();
        if(lengthInstruction >= 0 && lengthName >= 0) {
            if(appState.equals(AppState.UPLOAD_IMAGE)){
                progressDialog.setMessage("Uploading Image");
                progressDialog.setProgress(0);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressDialog.show();
                uploadImage();
            }
            else{
                progressDialog.setMessage("Adding To Firebase");

                progressDialog.setProgress(0);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressDialog.show();
                addToFirebase();
            }
        }
        else{
            return;
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
                Toast.makeText(UpdateActivity.this,"Failed to Upload",Toast.LENGTH_SHORT).show();
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
        Card card = new Card(Objects.requireNonNull(name.getText()).toString(),
                Objects.requireNonNull(careIntruction.getText()).toString(), CardType.MEDICINE, imageUri);
        db.collection("medicine")
                .document(id)
                .set(card)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        progressDialog.setMessage("Added to Firebase");
                        progressDialog.setProgress(100);
                        progressDialog.dismiss();
                        Toast.makeText(UpdateActivity.this,"Data Successfully Edited",Toast.LENGTH_SHORT).show();
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

    private void deleteDocument(){
        db.collection("medicine")
                .document(id)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        progressDialog.setMessage("Data Deleted");
                        progressDialog.setProgress(100);
                        progressDialog.dismiss();
                        Toast.makeText(UpdateActivity.this,"Data Successfully Deleted",Toast.LENGTH_SHORT).show();
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
            btnEdit.setImageURI(imageUri);
            btnEdit.setBackgroundResource(0);
            btnEdit.setOnClickListener(v->{
            });
            appState = AppState.UPLOAD_IMAGE;
        }
    }
}