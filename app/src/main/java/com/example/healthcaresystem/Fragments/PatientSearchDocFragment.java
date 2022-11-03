package com.example.healthcaresystem.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.healthcaresystem.Adapters.PatientPhysicalActivityAdapter;
import com.example.healthcaresystem.Adapters.PatientSearchDoctorAdapter;
import com.example.healthcaresystem.ApiClasses;
import com.example.healthcaresystem.Models.PhysicalActivityModel;
import com.example.healthcaresystem.Models.SearchDoctorModel;
import com.example.healthcaresystem.R;
import com.example.healthcaresystem.databinding.FragmentPatientSearchDocBinding;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class PatientSearchDocFragment extends Fragment {

   private FragmentPatientSearchDocBinding binding;
    private PatientSearchDoctorAdapter recyclerViewAdapter;
    private ArrayList<SearchDoctorModel> workArrayList;

    String[] specialists;
    int[] specialistsId;
    JSONArray jsonArray;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPatientSearchDocBinding.inflate(inflater, container, false);

        ApiClasses.Specilaist specilaist= new ApiClasses.Specilaist();

        try {
            specilaist.execute().get();
            jsonArray = specilaist.getData();
            specialists = new String[jsonArray.length()];
            specialistsId = new int[jsonArray.length()];

            for (int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                specialists[i] = jsonObject.getString("name");
                specialistsId[i] = jsonObject.getInt("id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        binding.specialistDropDown.setAdapter(new ArrayAdapter(getContext(),
                android.R.layout.simple_spinner_dropdown_item, specialists));

        binding.specialistDropDown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    binding.recyclerView.setHasFixedSize(true);
                    binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    workArrayList = new ArrayList<>();
                    try {
                        ApiClasses.PatientSearchDoctorGet patientSearchDoctorGet = new ApiClasses.PatientSearchDoctorGet(specialistsId[i]);
                        patientSearchDoctorGet.execute().get();
                        JSONArray jsonArray = patientSearchDoctorGet.getData();
                        for (int j = 0; j < jsonArray.length(); j++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(j);
                            workArrayList.add(new SearchDoctorModel(jsonObject.getString("username"),
                                    jsonObject.getString("name")));
                        }
                    } catch (Exception e) {
                        Log.e("error", e.toString());
                    }

                    recyclerViewAdapter = new PatientSearchDoctorAdapter(getActivity(),
                            workArrayList, getParentFragmentManager());
                    binding.recyclerView.setAdapter(recyclerViewAdapter);
                }catch (Exception e){
                    Log.e("error", e.toString());
                }
            }
        });
        return binding.getRoot();
    }
}