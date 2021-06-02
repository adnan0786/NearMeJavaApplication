package com.example.nearmedemo.Fragments;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.nearmedemo.Constant.AllConstant;
import com.example.nearmedemo.Permissions.AppPermissions;
import com.example.nearmedemo.R;
import com.example.nearmedemo.Utility.LoadingDialog;
import com.example.nearmedemo.databinding.FragmentSettingsBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import  com.example.nearmedemo.Fragments.SettingsFragmentDirections;

import java.util.HashMap;
import java.util.Map;


public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;
    private FirebaseAuth firebaseAuth;
    private LoadingDialog loadingDialog;
    private AppPermissions appPermissions;
    private Uri imageUri;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        firebaseAuth = FirebaseAuth.getInstance();
        loadingDialog = new LoadingDialog(getActivity());
        appPermissions = new AppPermissions();

        binding.imgCamera.setOnClickListener(camera -> {

            if (appPermissions.isStorageOk(getContext())) {
                pickImage();
            } else {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE
                        , Manifest.permission.WRITE_EXTERNAL_STORAGE}, AllConstant.STORAGE_REQUEST_CODE);
            }
        });

        binding.txtUsername.setOnClickListener(username -> {
            usernameDialog();
        });

        binding.cardEmail.setOnClickListener(view -> {
            SettingsFragmentDirections.ActionBtnSettingToEmailConfirmationFragment directions =
                    SettingsFragmentDirections.actionBtnSettingToEmailConfirmationFragment();

            Navigation.findNavController(getView()).navigate(directions);
        });

        binding.cardPassword.setOnClickListener(view -> {

            SettingsFragmentDirections.ActionBtnSettingToEmailConfirmationFragment directions =
                    SettingsFragmentDirections.actionBtnSettingToEmailConfirmationFragment();
            directions.setIsPassword(true);

            Navigation.findNavController(requireView()).navigate(directions);

        });


        return binding.getRoot();
    }

    private void pickImage() {

        CropImage.activity()
                .setCropShape(CropImageView.CropShape.OVAL)
                .start(getContext(), this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Settings");
        binding.txtEmail.setText(firebaseAuth.getCurrentUser().getEmail());
        binding.txtUsername.setText(firebaseAuth.getCurrentUser().getDisplayName());

        Glide.with(requireContext()).load(firebaseAuth.getCurrentUser().getPhotoUrl()).into(binding.imgProfile);

        if (firebaseAuth.getCurrentUser().isEmailVerified()) {
            binding.txtEEmail.setVisibility(View.GONE);
        } else {
            binding.txtEEmail.setVisibility(View.VISIBLE);
        }

        binding.txtEEmail.setOnClickListener(verify -> {
            firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(getContext(), "Mail sent verify the email", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "" + task.getException(), Toast.LENGTH_SHORT).show();
                        Log.d("TAG", "onComplete: profile email " + task.getException());
                    }
                }
            });
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == AllConstant.STORAGE_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                pickImage();
            } else {
                Toast.makeText(getContext(), "Storage Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == Activity.RESULT_OK) {
                imageUri = result.getUri();
                uploadImage(imageUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception exception = result.getError();
                Toast.makeText(getContext(), "" + exception.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void uploadImage(Uri imageUri) {

        loadingDialog.startLoading();
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        storageReference.child(firebaseAuth.getUid() + AllConstant.IMAGE_PATH).putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Task<Uri> image = taskSnapshot.getStorage().getDownloadUrl();
                image.addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            String url = task.getResult().toString();

                            UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
                                    .setPhotoUri(Uri.parse(url))
                                    .build();

                            firebaseAuth.getCurrentUser().updateProfile(profileChangeRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> profile) {

                                    if (profile.isSuccessful()) {

                                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
                                        Map<String, Object> map = new HashMap<>();
                                        map.put("image", url);
                                        databaseReference.child(firebaseAuth.getUid()).updateChildren(map);
                                        Glide.with(requireContext()).load(url).into(binding.imgProfile);
                                        loadingDialog.stopLoading();
                                        Toast.makeText(getContext(), "Image Updated", Toast.LENGTH_SHORT).show();

                                    } else {
                                        loadingDialog.stopLoading();
                                        Log.d("TAG", "Profile : " + profile.getException());
                                        Toast.makeText(getContext(), "Profile : " + profile.getException(), Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });


                        } else {
                            loadingDialog.stopLoading();
                            Toast.makeText(getContext(), "" + task.getException(), Toast.LENGTH_SHORT).show();
                            Log.d("TAG", "onComplete: image url  " + task.getException());
                        }

                    }
                });
            }
        });
    }

    private void usernameDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        View view = LayoutInflater.from(requireContext()).inflate(R.layout.username_dialog_layout, null, false);
        builder.setView(view);
        TextInputEditText edtUsername = view.findViewById(R.id.edtDialogUsername);

        builder.setTitle("Edit Username");

        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String username = edtUsername.getText().toString().trim();
                if (!username.isEmpty()) {

                    UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
                            .setDisplayName(username)
                            .build();
                    firebaseAuth.getCurrentUser().updateProfile(profileChangeRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {

                                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
                                Map<String, Object> map = new HashMap<>();
                                map.put("username", username);
                                databaseReference.child(firebaseAuth.getUid()).updateChildren(map);

                                binding.txtUsername.setText(username);
                                Toast.makeText(getContext(), "Username is updated", Toast.LENGTH_SHORT).show();

                            } else {
                                Log.d("TAG", "onComplete: " + task.getException());
                                Toast.makeText(getContext(), "" + task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(getContext(), "Username is required", Toast.LENGTH_SHORT).show();
                }
            }
        })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create().show();
    }
}