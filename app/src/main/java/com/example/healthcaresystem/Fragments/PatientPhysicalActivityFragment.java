package com.example.healthcaresystem.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.healthcaresystem.Adapters.PatientPhysicalActivityAdapter;
import com.example.healthcaresystem.ApiClasses;
import com.example.healthcaresystem.Models.PhysicalActivityModel;
import com.example.healthcaresystem.databinding.FragmentPatientPhysicalActivityBinding;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class PatientPhysicalActivityFragment extends Fragment {

    FragmentPatientPhysicalActivityBinding binding;
    private PatientPhysicalActivityAdapter recyclerViewAdapter;
    private ArrayList<PhysicalActivityModel> workArrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPatientPhysicalActivityBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        try {
            binding.recyclerView.setHasFixedSize(true);
            binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            workArrayList = new ArrayList<>();
            try {
                ApiClasses.PatientPhysicalActivityGet patientPhysicalActivityGet = new ApiClasses.PatientPhysicalActivityGet();
                patientPhysicalActivityGet.execute().get();
                JSONArray jsonArray = patientPhysicalActivityGet.getData();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    workArrayList.add(new PhysicalActivityModel(jsonObject.getString("category"),
                            jsonObject.getString("activity")));
                }
            } catch (Exception e) {
                Log.e("error", e.toString());
            }

            recyclerViewAdapter = new PatientPhysicalActivityAdapter(getActivity(),
                    workArrayList);
            binding.recyclerView.setAdapter(recyclerViewAdapter);
        }catch (Exception e){
            Log.e("error", e.toString());
        }
    }
}