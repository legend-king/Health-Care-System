package com.example.healthcaresystem.Fragments;

import android.database.Cursor;
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
import com.example.healthcaresystem.Adapters.PatientReportViewAdapter;
import com.example.healthcaresystem.Adapters.PatientSearchDoctorAdapter;
import com.example.healthcaresystem.DBHelper;
import com.example.healthcaresystem.Models.SearchDoctorModel;
import com.example.healthcaresystem.R;
import com.example.healthcaresystem.databinding.FragmentPatientReportViewBinding;

import org.json.JSONArray;

import java.util.ArrayList;

public class PatientReportViewFragment extends Fragment {
    FragmentPatientReportViewBinding binding;
    private PatientReportViewAdapter recyclerViewAdapter;
    DBHelper db;
    String id;

    public PatientReportViewFragment(String id){
        this.id=id;
    }

    public PatientReportViewFragment(){
        this.id="";
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPatientReportViewBinding.inflate(inflater, container, false);
        db = new DBHelper(getActivity());
        Cursor cursor = db.getData();
        cursor.moveToNext();
        if (id.equals("")){
            id = cursor.getString(0);
        }
        try {

            String url = getString(R.string.url);
            AndroidNetworking.get(url+"patientReportHistory")
                    .addQueryParameter("userName", id)
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


                        recyclerViewAdapter = new PatientReportViewAdapter(getActivity(),
                                response);
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