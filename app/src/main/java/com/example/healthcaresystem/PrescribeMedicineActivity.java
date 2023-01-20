package com.example.healthcaresystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.UploadProgressListener;
import com.example.healthcaresystem.databinding.ActivityPrescribeMedicineBinding;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONObject;

public class PrescribeMedicineActivity extends AppCompatActivity {
    ActivityPrescribeMedicineBinding binding;
    String prescribedBy, prescribedTo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPrescribeMedicineBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();

        prescribedBy = intent.getStringExtra("prescribedBy");
        prescribedTo = intent.getStringExtra("prescribedTo");

        binding.addMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View medicine = getLayoutInflater().inflate(R.layout.dynamic_add_medicine,
                        null,false);
                int medicineNumber = binding.medicineAddLayout.getChildCount();


                ImageView remove = medicine.findViewById(R.id.dRemoveMedicine);

                remove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        binding.medicineAddLayout.removeView(medicine);
                    }
                });
                AutoCompleteTextView dropdown = medicine.findViewById(R.id.dMedicineTypeDropDown);
                EditText medicineName = medicine.findViewById(R.id.dMedicineNameET);
                EditText beforeBreakFastQuantity = medicine.findViewById(R.id.dBeforeBreakFastQuantity);
                EditText afterBreakFastQuantity = medicine.findViewById(R.id.dAfterBreakFastQuantity);
                EditText beforeLunchQuantity = medicine.findViewById(R.id.dBeforeLunchQuantity);
                EditText afterLunchQuantity = medicine.findViewById(R.id.dAfterLunchQuantity);
                EditText beforeDinnerQuantity = medicine.findViewById(R.id.dBeforeDinnerQuantity);
                EditText afterDinnerQuantity = medicine.findViewById(R.id.dAfterDinnerQuantity);
                EditText eveningQuantity = medicine.findViewById(R.id.dEveningQuantity);
                EditText medicineQuantity = medicine.findViewById(R.id.dQuantityET);

                TextInputLayout medicineQuantityHint = medicine.findViewById(R.id.dQuantityHint);
                TextInputLayout beforeBreakFastQuantityHint = medicine.findViewById(R.id.
                        dBeforeBreakFastQuantityHint);
                TextInputLayout afterBreakFastQuantityHint = medicine.findViewById(R.id.
                        dAfterBreakFastQuantityHint);
                TextInputLayout beforeLunchQuantityHint = medicine.findViewById(R.id.
                        dBeforeLunchQuantityHint);
                TextInputLayout afterLunchQuantityHint = medicine.findViewById(R.id.
                        dAfterLunchQuantityHint);
                TextInputLayout beforeDinnerQuantityHint = medicine.findViewById(R.id.
                        dBeforeDinnerQuantityHint);
                TextInputLayout afterDinnerQuantityHint = medicine.findViewById(R.id.
                        dAfterDinnerQuantityHint);
                TextInputLayout eveningQuantityHint = medicine.findViewById(R.id.
                        dEveningQuantityHint);

                CheckBox beforeBreakFast = medicine.findViewById(R.id.dBeforeBreakFast);
                CheckBox afterBreakFast = medicine.findViewById(R.id.dAfterBreakFast);
                CheckBox beforeLunch = medicine.findViewById(R.id.dBeforeLunch);
                CheckBox afterLunch = medicine.findViewById(R.id.dAfterLunch);
                CheckBox beforeDinner = medicine.findViewById(R.id.dBeforeDinner);
                CheckBox afterDinner = medicine.findViewById(R.id.dAfterDinner);
                CheckBox evening = medicine.findViewById(R.id.dEvening);

                dropdown.setAdapter(new ArrayAdapter(getApplicationContext(),
                        android.R.layout.simple_spinner_dropdown_item, new String[] {"Tablet",
                        "Capsule", "Syrup"}));
                
                dropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        if (i==0){
                            try{
                                medicineQuantityHint.setHint(R.string.noOfTablets);
                                beforeBreakFastQuantityHint.setHint(R.string.noOfTablets);
                                afterBreakFastQuantityHint.setHint(R.string.noOfTablets);
                                beforeDinnerQuantityHint.setHint(R.string.noOfTablets);
                                afterDinnerQuantityHint.setHint(R.string.noOfTablets);
                                beforeLunchQuantityHint.setHint(R.string.noOfTablets);
                                afterLunchQuantityHint.setHint(R.string.noOfTablets);
                                eveningQuantityHint.setHint(R.string.noOfTablets);
                            }catch(Exception e){
                                e.printStackTrace();
                            }

                        }
                        else if (i==1){
                            try{
                                medicineQuantityHint.setHint(R.string.noOfCapsules);
                                beforeBreakFastQuantityHint.setHint(R.string.noOfCapsules);
                                afterBreakFastQuantityHint.setHint(R.string.noOfCapsules);
                                beforeDinnerQuantityHint.setHint(R.string.noOfCapsules);
                                afterDinnerQuantityHint.setHint(R.string.noOfCapsules);
                                beforeLunchQuantityHint.setHint(R.string.noOfCapsules);
                                afterLunchQuantityHint.setHint(R.string.noOfCapsules);
                                eveningQuantityHint.setHint(R.string.noOfCapsules);
                            }catch(Exception e){
                                e.printStackTrace();
                            }

                        }
                        else if (i==2){
                            try{
                                medicineQuantityHint.setHint(R.string.syrupAmount);
                                beforeBreakFastQuantityHint.setHint(R.string.syrupAmount);
                                afterBreakFastQuantityHint.setHint(R.string.syrupAmount);
                                beforeDinnerQuantityHint.setHint(R.string.syrupAmount);
                                afterDinnerQuantityHint.setHint(R.string.syrupAmount);
                                beforeLunchQuantityHint.setHint(R.string.syrupAmount);
                                afterLunchQuantityHint.setHint(R.string.syrupAmount);
                                eveningQuantityHint.setHint(R.string.syrupAmount);
                            }catch(Exception e){
                                e.printStackTrace();
                            }

                        }
                    }
                });

                evening.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (evening.isChecked()){
                            eveningQuantity.setVisibility(View.VISIBLE);
                        }
                        else{
                            eveningQuantity.setVisibility(View.GONE);
                        }
                    }
                });

                beforeBreakFast.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (beforeBreakFast.isChecked()){
                            beforeBreakFastQuantity.setVisibility(View.VISIBLE);
                        }else{
                            beforeBreakFastQuantity.setVisibility(View.GONE);
                        }
                    }
                });
                
                afterBreakFast.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (afterBreakFast.isChecked()){
                            afterBreakFastQuantity.setVisibility(View.VISIBLE);
                        }else{
                            afterBreakFastQuantity.setVisibility(View.GONE);
                        }
                    }
                });


                beforeLunch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if (b){
                            beforeLunchQuantity.setVisibility(View.VISIBLE);
                        }else{
                            beforeLunchQuantity.setVisibility(View.GONE);
                        }
                    }
                });

                afterLunch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if (b){
                            afterLunchQuantity.setVisibility(View.VISIBLE);
                        }else{
                            afterLunchQuantity.setVisibility(View.GONE);
                        }
                    }
                });

                beforeDinner.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if (b){
                            beforeDinnerQuantity.setVisibility(View.VISIBLE);
                        }else{
                            beforeDinnerQuantity.setVisibility(View.GONE);
                        }
                    }
                });

                afterDinner.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if (b){
                            afterDinnerQuantity.setVisibility(View.VISIBLE);
                        }else{
                            afterDinnerQuantity.setVisibility(View.GONE);
                        }
                    }
                });

                binding.medicineAddLayout.addView(medicine);
            }
        });

        binding.precribeMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONArray data = new JSONArray();
                boolean flag=true;
                try{

                    int n = binding.medicineAddLayout.getChildCount();
                    for (int i=0;i<n;i++){
                        JSONObject jsonObject = new JSONObject();
                        View medicine = binding.medicineAddLayout.getChildAt(i);
                        EditText medicineName = medicine.findViewById(R.id.dMedicineNameET);
                        EditText beforeBreakFastQuantity = medicine.findViewById(R.id.
                                dBeforeBreakFastQuantity);
                        EditText afterBreakFastQuantity = medicine.findViewById(R.id.
                                dAfterBreakFastQuantity);
                        EditText beforeLunchQuantity = medicine.findViewById(R.id.
                                dBeforeLunchQuantity);
                        EditText afterLunchQuantity = medicine.findViewById(R.id.
                                dAfterLunchQuantity);
                        EditText beforeDinnerQuantity = medicine.findViewById(R.id.
                                dBeforeDinnerQuantity);
                        EditText afterDinnerQuantity = medicine.findViewById(R.id.
                                dAfterDinnerQuantity);
                        EditText eveningQuantity = medicine.findViewById(R.id.dEveningQuantity);
                        EditText medicineQuantity = medicine.findViewById(R.id.dQuantityET);
                        EditText duration = medicine.findViewById(R.id.dDurationET);

                        CheckBox beforeBreakFast = medicine.findViewById(R.id.dBeforeBreakFast);
                        CheckBox afterBreakFast = medicine.findViewById(R.id.dAfterBreakFast);
                        CheckBox beforeLunch = medicine.findViewById(R.id.dBeforeLunch);
                        CheckBox afterLunch = medicine.findViewById(R.id.dAfterLunch);
                        CheckBox beforeDinner = medicine.findViewById(R.id.dBeforeDinner);
                        CheckBox afterDinner = medicine.findViewById(R.id.dAfterDinner);
                        CheckBox evening = medicine.findViewById(R.id.dEvening);

                        AutoCompleteTextView dropdown = medicine.findViewById(R.id.dMedicineTypeDropDown);
                        
                        if (medicineName.getText().toString().trim().length()==0){
                            flag=false;
                            medicineName.setError("Medicine Name cannot be empty");
                        }
                        else{
                            jsonObject.put("medicineName", medicineName.getText().toString().
                                    trim());
                        }
                        
                        if (beforeBreakFast.isChecked()){
                            if (beforeBreakFastQuantity.getText().toString().trim().length()==0){
                                flag=false;
                                beforeBreakFastQuantity.setError("Field cannot be empty");
                            }
                            else{
                                jsonObject.put("beforeBreakFastQuantity", Double.
                                        parseDouble(beforeBreakFastQuantity.getText().toString().
                                                trim()));
                            }
                        }
                        else{
                            jsonObject.put("beforeBreakFastQuantity", 0);
                        }

                        if (afterBreakFast.isChecked()){
                            if (afterBreakFastQuantity.getText().toString().trim().length()==0){
                                flag=false;
                                afterBreakFastQuantity.setError("Field cannot be empty");
                            }
                            else{
                                jsonObject.put("afterBreakFastQuantity", Double.
                                        parseDouble(afterBreakFastQuantity.getText().toString().
                                                trim()));
                            }
                        }
                        else{
                            jsonObject.put("afterBreakFastQuantity", 0);
                        }


                        if (beforeLunch.isChecked()){
                            if (beforeLunchQuantity.getText().toString().trim().length()==0){
                                flag=false;
                                beforeLunchQuantity.setError("Field cannot be empty");
                            }
                            else{
                                jsonObject.put("beforeLunchQuantity", Double.
                                        parseDouble(beforeLunchQuantity.getText().toString().
                                                trim()));
                            }
                        }
                        else{
                            jsonObject.put("beforeLunchQuantity", 0);
                        }

                        if (afterLunch.isChecked()){
                            if (afterLunchQuantity.getText().toString().trim().length()==0){
                                flag=false;
                                afterLunchQuantity.setError("Field cannot be empty");
                            }
                            else{
                                jsonObject.put("afterLunchQuantity", Double.
                                        parseDouble(afterLunchQuantity.getText().toString().
                                                trim()));
                            }
                        }
                        else{
                            jsonObject.put("afterLunchQuantity", 0);
                        }

                        if (beforeDinner.isChecked()){
                            if (beforeDinnerQuantity.getText().toString().trim().length()==0){
                                flag=false;
                                beforeDinnerQuantity.setError("Field cannot be empty");
                            }
                            else{
                                jsonObject.put("beforeDinnerQuantity", Double.
                                        parseDouble(beforeDinnerQuantity.getText().toString().
                                                trim()));
                            }
                        }
                        else{
                            jsonObject.put("beforeDinnerQuantity", 0);
                        }

                        if (afterDinner.isChecked()){
                            if (afterDinnerQuantity.getText().toString().trim().length()==0){
                                flag=false;
                                afterDinnerQuantity.setError("Field cannot be empty");
                            }
                            else{
                                jsonObject.put("afterDinnerQuantity", Double.
                                        parseDouble(afterDinnerQuantity.getText().toString().
                                                trim()));
                            }
                        }
                        else{
                            jsonObject.put("afterDinnerQuantity", 0);
                        }

                        if (evening.isChecked()){
                            if (eveningQuantity.getText().toString().trim().length()==0){
                                flag=false;
                                eveningQuantity.setError("Field cannot be empty");
                            }
                            else{
                                jsonObject.put("eveningQuantity", Double.
                                        parseDouble(eveningQuantity.getText().toString().
                                                trim()));
                            }
                        }
                        else{
                            jsonObject.put("eveningQuantity", 0);
                        }

                        if (medicineQuantity.getText().toString().trim().length()==0){
                            flag=false;
                            medicineQuantity.setError("Quantity cannot be empty");
                        }
                        else{
                            jsonObject.put("medicineQuantity", Integer.parseInt(medicineQuantity.getText().toString().
                                    trim()));
                        }

                        if (duration.getText().toString().trim().length()==0){
                            flag=false;
                            duration.setError("Duration cannot be empty");
                        }
                        else{
                            jsonObject.put("duration", Integer.parseInt(duration.
                                    getText().toString().trim()));
                        }

                        jsonObject.put("medicineType", dropdown.getText().toString().trim());
                        
                        
                        
//                        jsonObject.put("afterBreakFastQuantity", afterBreakFastQuantity.
//                                getText().toString().
//                                trim());
//                        jsonObject.put("beforeLunchQuantity", beforeLunchQuantity.getText().toString().
//                                trim());
//                        jsonObject.put("afterLunchQuantity", afterLunchQuantity.getText().toString().
//                                trim());
//                        jsonObject.put("beforeDinnerQuantity", beforeDinnerQuantity.getText().toString().
//                                trim());
//                        jsonObject.put("afterDinnerQuantity", afterDinnerQuantity.getText().toString().
//                                trim());
//                        jsonObject.put("eveningQuantity", eveningQuantity.getText().toString().
//                                trim());
//                        jsonObject.put("medicineQuantity", medicineQuantity.getText().toString().
//                                trim());
//                        jsonObject.put("duration", duration.getText().toString().
//                                trim());
                        data.put(i, jsonObject);
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
                if (data.length()<1){
                    flag=false;
                    Toast.makeText(getApplicationContext(), "At least one medicine must be " +
                            "added", Toast.LENGTH_SHORT).show();
                }
                String diagnosis = binding.diagnosisET.getText().toString().trim();
                String advice = binding.adviceET.getText().toString().trim();

                if (diagnosis.length()==0){
                    flag=false;
                    binding.diagnosisET.setError("Field cannot be empty");
                }
                if (flag){
                    String url = getString(R.string.url);
                    Log.e("Error", "Entered");

                    AndroidNetworking.post(url+"prescribeMedicine")
                            .addJSONArrayBody(data)
                            .addQueryParameter("diagnosis", diagnosis)
                            .addQueryParameter("advice", advice)
                            .addQueryParameter("prescribedBy", prescribedBy)
                            .addQueryParameter("prescribedTo", prescribedTo)
                            .setPriority(Priority.HIGH)
                            .build()
                            .setUploadProgressListener(new UploadProgressListener() {
                                @Override
                                public void onProgress(long bytesUploaded, long totalBytes) {
                                }
                            }).getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.e("Api", "result" + response);
                            Toast.makeText(getApplicationContext(), "Prescribed Medicine",
                                    Toast.LENGTH_SHORT).show();
                            finish();
                        }

                        @Override
                        public void onError(ANError anError) {
                            Log.e("error", anError.toString());
                        }
                    });
                    Log.e("Error", "Outside Exit");
                }

            }
        });
    }
    
}