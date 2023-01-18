package com.example.healthcaresystem.Fragments;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.UploadProgressListener;
import com.example.healthcaresystem.Adapters.DocChatDispAdapter;
import com.example.healthcaresystem.Adapters.DocPrescribedMedicineAdapter;
import com.example.healthcaresystem.ApiClasses;
import com.example.healthcaresystem.DBHelper;
import com.example.healthcaresystem.Models.PatChatDispModel;
import com.example.healthcaresystem.R;
import com.example.healthcaresystem.databinding.FragmentDoctorPrescribedMedicineBinding;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class DoctorPrescribedMedicineFragment extends Fragment {

    FragmentDoctorPrescribedMedicineBinding binding;
    private DocPrescribedMedicineAdapter recyclerViewAdapter;
    DBHelper db;
    String prescribedBy;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDoctorPrescribedMedicineBinding.inflate(inflater, container, false);
        db = new DBHelper(getActivity());
        Cursor cursor = db.getData();
        cursor.moveToNext();
        prescribedBy = cursor.getString(0);

        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        try {
            binding.recyclerView.setHasFixedSize(true);

            binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            String url = getString(R.string.url);
            AndroidNetworking.get(url+"doctorPrescribedMedicines")
                    .addQueryParameter("prescribedBy", prescribedBy)
                    .setPriority(Priority.HIGH)
                    .build()
                    .setUploadProgressListener(new UploadProgressListener() {
                        @Override
                        public void onProgress(long bytesUploaded, long totalBytes) {
                        }
                    }).getAsJSONArray(new JSONArrayRequestListener() {
                @Override
                public void onResponse(JSONArray response) {
                    Log.e("Api", "result" + response);
                    recyclerViewAdapter = new DocPrescribedMedicineAdapter(getActivity(),
                            response, getLayoutInflater(), 1);
                    binding.recyclerView.setAdapter(recyclerViewAdapter);
                }

                @Override
                public void onError(ANError anError) {
                    Log.e("error", anError.toString());
                }
            });



        }catch (Exception e){
            Log.e("error", e.toString());
        }
    }
}