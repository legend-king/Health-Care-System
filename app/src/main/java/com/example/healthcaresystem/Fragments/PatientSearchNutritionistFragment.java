package com.example.healthcaresystem.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.UploadProgressListener;
import com.example.healthcaresystem.Adapters.PatientSearchDoctorAdapter;
import com.example.healthcaresystem.ApiClasses;
import com.example.healthcaresystem.Models.SearchDoctorModel;
import com.example.healthcaresystem.R;
import com.example.healthcaresystem.databinding.FragmentPatientSearchNutritionistBinding;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class PatientSearchNutritionistFragment extends Fragment {

    FragmentPatientSearchNutritionistBinding binding;
    private PatientSearchDoctorAdapter recyclerViewAdapter;
    private ArrayList<SearchDoctorModel> workArrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPatientSearchNutritionistBinding.inflate(inflater, container, false);
        try {

            String url = getString(R.string.url);
            AndroidNetworking.get(url+"patientSearchNutritionist")
                    .setPriority(Priority.HIGH)
                    .build()
                    .setUploadProgressListener(new UploadProgressListener() {
                        @Override
                        public void onProgress(long bytesUploaded, long totalBytes) {

                        }
                    }).getAsJSONArray(new JSONArrayRequestListener() {
                @Override
                public void onResponse(JSONArray response) {
                    try {
                        binding.recyclerView.setHasFixedSize(true);
                        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        workArrayList = new ArrayList<>();
                        for (int i=0;i<response.length();i++){
                            workArrayList.add(new SearchDoctorModel(
                                    response.getJSONObject(i).getString("username"),
                                    response.getJSONObject(i).getString("name"),
                                    response.getJSONObject(i).getInt("consultation_charge")));
                        }
                        recyclerViewAdapter = new PatientSearchDoctorAdapter(getActivity(),
                                workArrayList, getParentFragmentManager());
                        binding.recyclerView.setAdapter(recyclerViewAdapter);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(ANError anError) {

                }
            });


        }catch (Exception e){
            Log.e("error", e.toString());
        }
        return binding.getRoot();
    }
}