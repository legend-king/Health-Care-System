package com.example.healthcaresystem.Fragments;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.healthcaresystem.Adapters.DocChatDispAdapter;
import com.example.healthcaresystem.Adapters.PatChatDispAdapter;
import com.example.healthcaresystem.ApiClasses;
import com.example.healthcaresystem.DBHelper;
import com.example.healthcaresystem.Models.PatChatDispModel;
import com.example.healthcaresystem.R;
import com.example.healthcaresystem.databinding.FragmentDoctorChatBinding;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Stack;

public class DoctorChatDisplayFragment extends Fragment {

    private FragmentDoctorChatBinding binding;
    private DocChatDispAdapter recyclerViewAdapter;
    private ArrayList<PatChatDispModel> workArrayList;
    DBHelper db;
    String sender;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDoctorChatBinding.inflate(inflater, container, false);
        db = new DBHelper(getActivity());
        Cursor cursor = db.getData();
        cursor.moveToNext();
        sender = cursor.getString(0);
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
                ApiClasses.DocChatDispGet docChatDispGet = new ApiClasses.DocChatDispGet(sender);
                docChatDispGet.execute().get();
                JSONArray jsonArray = docChatDispGet.getData();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    workArrayList.add(new PatChatDispModel(jsonObject.getString("username"),
                            jsonObject.getString("name")));
                }
            } catch (Exception e) {
                Log.e("error", e.toString());
            }

            recyclerViewAdapter = new DocChatDispAdapter(getActivity(),
                    workArrayList, getParentFragmentManager());
            binding.recyclerView.setAdapter(recyclerViewAdapter);
        }catch (Exception e){
            Log.e("error", e.toString());
        }
    }
}