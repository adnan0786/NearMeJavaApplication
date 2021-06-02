
package com.example.nearmedemo.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.nearmedemo.Constant.AllConstant;
import com.example.nearmedemo.Permissions.AppPermissions;
import com.example.nearmedemo.R;
import com.example.nearmedemo.UserModel;
import com.example.nearmedemo.Utility.LoadingDialog;
import com.example.nearmedemo.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class SignUpActivity extends AppCompatActivity {
    private ActivitySignUpBinding binding;
    private Uri imageUri;
    private AppPermissions appPermissions;
    private LoadingDialog loadingDialog;
    private String email, username, password;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        appPermissions = new AppPermissions();
        loadingDialog = new LoadingDialog(this);
        storageReference = FirebaseStorage.getInstance().getReference();

        binding.btnBack.setOnClickListener(view -> {
            onBackPressed();
        });

        binding.txtLogin.setOnClickListener(view -> {
            onBackPressed();
        });

        binding.btnSignUp.setOnClickListener(view -> {
            if (areFieldReady()) {
                if (imageUri != null) {
                    signUp();
                } else {
                    Toast.makeText(this, "Image is required", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.imgPick.setOnClickListener(view -> {
            if (appPermissions.isStorageOk(this)) {
                pickImage();
            } else {
                appPermissions.requestStoragePermission(this);
            }
        });
    }

    private void pickImage() {
        CropImage.activity()
                .setCropShape(CropImageView.CropShape.OVAL)
                .start(this);
    }

    private boolean areFieldReady() {
        username = binding.edtUsername.getText().toString().trim();
        email = binding.edtEmail.getText().toString().trim();
        password = binding.edtPassword.getText().toString().trim();

        boolean flag = false;
        View requestView = null;

        if (username.isEmpty()) {
            binding.edtUsername.setError("Field is required");
            flag = true;
            requestView = binding.edtUsername;
        } else if (email.isEmpty()) {
            binding.edtEmail.setError("Field is required");
            flag = true;
            requestView = binding.edtEmail;
        } else if (password.isEmpty()) {
            binding.edtPassword.setError("Field is required");
            flag = true;
            requestView = binding.edtPassword;
        } else if (password.length() < 8) {
            binding.edtPassword.setError("Minimum 8 characters");
            flag = true;
            requestView = binding.edtPassword;
        }

        if (flag) {
            requestView.requestFocus();
            return false;
        } else {
            return true;
        }

    }

    private void signUp() {
        loadingDialog.startLoading();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> singUp) {

                if (singUp.isSuccessful()) {

                    storageReference.child(firebaseAuth.getUid() + AllConstant.IMAGE_PATH).putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            Task<Uri> image = taskSnapshot.getStorage().getDownloadUrl();
                            image.addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> imageTask) {

                                    if (imageTask.isSuccessful()) {

                                        String url = imageTask.getResult().toString();
                                        UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
                                                .setDisplayName(username)
                                                .setPhotoUri(Uri.parse(url))
                                                .build();

                                        firebaseAuth.getCurrentUser().updateProfile(profileChangeRequest).
                                                addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {

                                                        if (task.isSuccessful()) {
                                                            UserModel userModel = new UserModel(email,
                                                                    username, url, true);
                                                            databaseReference.child(firebaseAuth.getUid())
                                                                    .setValue(userModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void aVoid) {
                                                                    firebaseAuth.getCurrentUser().sendEmailVerification()
                                                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                @Override
                                                                                public void onSuccess(Void aVoid) {
                                                                                    loadingDialog.stopLoading();
                                                                                    Toast.makeText(SignUpActivity.this, "Verify email", Toast.LENGTH_SHORT).show();
                                                                                    onBackPressed();
                                                                                }
                                                                            });

                                                                }

                                                            });
                                                        } else {
                                                            loadingDialog.stopLoading();
                                                            Log.d("TAG", "onComplete: Update Profile" + task.getException());
                                                            Toast.makeText(SignUpActivity.this, "Update Profile" + task.getException(), Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });

                                    } else {
                                        loadingDialog.stopLoading();
                                        Log.d("TAG", "onComplete: Image Path" + imageTask.getException());
                                        Toast.makeText(SignUpActivity.this, "Image Path" + imageTask.getException(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        }
                    });

                } else {
                    loadingDialog.stopLoading();
                    Log.d("TAG", "onComplete: Create user" + singUp.getException());
                    Toast.makeText(SignUpActivity.this, "" + singUp.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                imageUri = result.getUri();
                Glide.with(this).load(imageUri).into(binding.imgPick);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception exception = result.getError();
                Log.d("TAG", "onActivityResult: " + exception);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == AllConstant.STORAGE_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                pickImage();
            } else {
                Toast.makeText(this, "Storage permission denied.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}