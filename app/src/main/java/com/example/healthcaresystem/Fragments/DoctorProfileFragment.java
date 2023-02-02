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
import com.example.healthcaresystem.databinding.FragmentDoctorProfileBinding;

import org.json.JSONObject;

public class DoctorProfileFragment extends Fragment {
    private FragmentDoctorProfileBinding binding;
    private String id;

    public DoctorProfileFragment(String username) {
        this.id=username;
    }

    public DoctorProfileFragment() {
        this.id="";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentDoctorProfileBinding.inflate(inflater,container,false);
        try {
            if (id.equals("")){
                DBHelper db = new DBHelper(getActivity());
                Cursor cursor = db.getData();
                cursor.moveToNext();
                id = cursor.getString(0);
            }



            try {
                ApiClasses.DoctorProfileGet doctorProfileGet = new ApiClasses.DoctorProfileGet(id);
                doctorProfileGet.execute().get();
                JSONObject jsonObject = doctorProfileGet.getData();

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
                binding.specialist.setText(jsonObject.getString("specialist"));
                binding.consultationCharge.setText(jsonObject.getString("consultation_charge"));
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