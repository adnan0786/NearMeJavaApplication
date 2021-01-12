package com.example.nearmedemo.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.nearmedemo.R;
import com.example.nearmedemo.databinding.ActivitySignUpBinding;

public class SignUpActivity extends AppCompatActivity {
    private ActivitySignUpBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnBack.setOnClickListener(view -> {
            onBackPressed();
        });

        binding.txtLogin.setOnClickListener(view -> {
            onBackPressed();
        });
    }
}