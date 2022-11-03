package com.example.healthcaresystem.Fragments;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.healthcaresystem.ApiClasses;
import com.example.healthcaresystem.DBHelper;
import com.example.healthcaresystem.R;
import com.example.healthcaresystem.databinding.FragmentPatientProfileBinding;

import org.json.JSONObject;

public class PatientProfileFragment extends Fragment {

    private FragmentPatientProfileBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPatientProfileBinding.inflate(inflater, container, false);
        try {
            DBHelper db = new DBHelper(getActivity());
            Cursor cursor = db.getData();
            cursor.moveToNext();
            String id = cursor.getString(0);

            try {
                ApiClasses.PatientProfileGet patientProfileGet = new ApiClasses.PatientProfileGet(id);
                patientProfileGet.execute().get();
                JSONObject jsonObject = patientProfileGet.getData();

                String gen = jsonObject.getString("gender");
                String gender;
                if (gen.equals("M")) {
                    gender = "Male";
                } else {
                    gender = "Female";
                }
                binding.gender.setText(gender);
                binding.name.setText(jsonObject.getString("name"));
                binding.userName.setText(jsonObject.getString("username"));
                binding.email.setText(jsonObject.getString("email"));
                binding.mobile.setText(jsonObject.getString("mobile"));
                binding.age.setText(String.valueOf(jsonObject.getInt("age")));
                binding.height.setText(String.valueOf(jsonObject.getDouble("height")));
                binding.weight.setText(String.valueOf(jsonObject.getDouble("weight")));
                binding.dob.setText(jsonObject.getString("dob"));

            } catch (Exception e) {
                e.printStackTrace();
                Log.e("error", e.toString());
            }
        }catch(Exception e){
            Log.e("error", e.toString());
        }
        return binding.getRoot();
    }
}