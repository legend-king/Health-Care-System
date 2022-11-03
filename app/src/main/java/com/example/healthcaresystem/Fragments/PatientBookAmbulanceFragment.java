package com.example.healthcaresystem.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.healthcaresystem.R;
import com.example.healthcaresystem.databinding.FragmentPatientBookAmbulanceBinding;

public class PatientBookAmbulanceFragment extends Fragment {

    private FragmentPatientBookAmbulanceBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPatientBookAmbulanceBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}