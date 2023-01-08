package com.example.healthcaresystem;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.healthcaresystem.databinding.ActivityPrescribeMedicineBinding;

public class PrescribeMedicineActivity extends AppCompatActivity {
    ActivityPrescribeMedicineBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPrescribeMedicineBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}