package com.example.healthcaresystem.Fragments;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.view.inputmethod.InputMethodManager;
import com.example.healthcaresystem.Adapters.PatDocChatAdapter;
import com.example.healthcaresystem.Adapters.PatientPhysicalActivityAdapter;
import com.example.healthcaresystem.ApiClasses;
import com.example.healthcaresystem.DBHelper;
import com.example.healthcaresystem.Models.ChatModel;
import com.example.healthcaresystem.Models.PhysicalActivityModel;
import com.example.healthcaresystem.PrescribeMedicineActivity;
import com.example.healthcaresystem.R;
import com.example.healthcaresystem.databinding.FragmentPatDocChatBinding;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class PatDocChatFragment extends Fragment {
    FragmentPatDocChatBinding binding;
    DBHelper db;
    String receiver, sender, receiverName;
    int type;
    private PatDocChatAdapter recyclerViewAdapter;
    private ArrayList<ChatModel> workArrayList;


    public PatDocChatFragment(String receiver, String receiverName) {

        this.receiver=receiver;
        this.receiverName=receiverName;
    }

    public PatDocChatFragment(){
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPatDocChatBinding.inflate(inflater, container, false);

        db = new DBHelper(getActivity());
        Cursor cursor = db.getData();
        cursor.moveToNext();
        sender = cursor.getString(0);
        type = cursor.getInt(2);
        binding.receiver.setText(receiverName);

        if (type==0){
            binding.precribeMedicine.setVisibility(View.GONE);
        }

        binding.precribeMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PrescribeMedicineActivity.class);
                intent.putExtra("prescribedBy", sender);
                intent.putExtra("prescribedTo", receiver);
                startActivity(intent);
            }
        });

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (type==0){
                    getParentFragmentManager().beginTransaction().replace(R.id.patFragment,
                            new PatientChatDisplayFragment()).commit();
                }
                else if (type==1){
                    getParentFragmentManager().beginTransaction().replace(R.id.docFragment,
                            new DoctorChatDisplayFragment()).commit();
                }
            }
        });

        binding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = binding.message.getText().toString();
                try{
                    ApiClasses.PatDocChatPost patDocChatPost = new ApiClasses.PatDocChatPost(sender,
                            receiver, message);
                    patDocChatPost.execute();
                    binding.message.setText("");
                    binding.recyclerView.setHasFixedSize(true);
                    binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    workArrayList = new ArrayList<>();
                    try {
                        ApiClasses.PatDocChatGet patDocChatGet = new ApiClasses.PatDocChatGet(sender, receiver);
                        patDocChatGet.execute().get();
                        JSONArray jsonArray = patDocChatGet.getData();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            workArrayList.add(new ChatModel(jsonObject.getString("sender"),
                                    jsonObject.getString("receiver"), jsonObject.getString("message")));
                        }
                    } catch (Exception e) {
                        Log.e("error", e.toString());
                    }

                    recyclerViewAdapter = new PatDocChatAdapter(getActivity(),
                            workArrayList);
                    binding.recyclerView.setAdapter(recyclerViewAdapter);
                    binding.recyclerView.scrollToPosition(workArrayList.size() - 1);
                }catch(Exception e){
                    e.printStackTrace();
                }
                    try {
                        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            }
        });
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
                ApiClasses.PatDocChatGet patDocChatGet = new ApiClasses.PatDocChatGet(sender, receiver);
                patDocChatGet.execute().get();
                JSONArray jsonArray = patDocChatGet.getData();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    workArrayList.add(new ChatModel(jsonObject.getString("sender"),
                            jsonObject.getString("receiver"), jsonObject.getString("message")));
                }
            } catch (Exception e) {
                Log.e("error", e.toString());
            }

            recyclerViewAdapter = new PatDocChatAdapter(getActivity(),
                    workArrayList);
            binding.recyclerView.setAdapter(recyclerViewAdapter);
            binding.recyclerView.scrollToPosition(workArrayList.size() - 1);
        }catch (Exception e){
            Log.e("error", e.toString());
        }
    }
}