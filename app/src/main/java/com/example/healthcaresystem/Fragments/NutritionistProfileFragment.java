package com.example.healthcaresystem.Fragments;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.UploadProgressListener;
import com.example.healthcaresystem.ApiClasses;
import com.example.healthcaresystem.DBHelper;
import com.example.healthcaresystem.R;
import com.example.healthcaresystem.databinding.FragmentNutritionistProfileBinding;

import org.json.JSONObject;

public class NutritionistProfileFragment extends Fragment {
    FragmentNutritionistProfileBinding binding;
    String id;

    public NutritionistProfileFragment(String username) {
        this.id=username;
    }

    public NutritionistProfileFragment() {
        this.id="";
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentNutritionistProfileBinding.inflate(inflater, container, false);
        if (id.equals("")){
            DBHelper db = new DBHelper(getActivity());
            Cursor cursor = db.getData();
            cursor.moveToNext();
            id = cursor.getString(0);
        }

        try {
            String url = getString(R.string.url);
            AndroidNetworking.get(url+"nutritionistProfile")
                    .addQueryParameter("userName",id)
                    .setPriority(Priority.HIGH)
                    .build()
                    .setUploadProgressListener(new UploadProgressListener() {
                @Override
                public void onProgress(long bytesUploaded, long totalBytes) {

                }
            }).getAsJSONObject(new JSONObjectRequestListener() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.e("API resposne", response.toString());
                    try {
                        String gen = response.getString("gender");
                        String gender;
                        if (gen.equals("M")) {
                            gender = "Male";
                        } else {
                            gender = "Female";
                        }
                        binding.gender.setText(gender);
                        binding.name.setText(response.getString("name"));
                        binding.userName.setText(response.getString("username"));
                        binding.email.setText(response.getString("email"));
                        binding.mobile.setText(response.getString("mobile"));
                        binding.consultationCharge.setText(response.getString("consultation_charge"));
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(ANError anError) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("error", e.toString());
        }
        return binding.getRoot();
    }
}