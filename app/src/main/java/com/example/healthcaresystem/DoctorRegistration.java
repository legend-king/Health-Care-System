package com.example.healthcaresystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.healthcaresystem.databinding.ActivityDoctorRegistrationBinding;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class DoctorRegistration extends AppCompatActivity {
    ActivityDoctorRegistrationBinding binding;
    private String[] specialists;
    private JSONArray jsonArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDoctorRegistrationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        DBHelper db = new DBHelper(this);

        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DoctorRegistration.this, Login.class);
                intent.putExtra("type", 1);
                startActivity(intent);
                finish();
            }
        });

        ApiClasses.Specilaist specilaist = new ApiClasses.Specilaist();
        try {
            specilaist.execute().get();
            jsonArray = specilaist.getData();
            specialists = new String[jsonArray.length()];

            for (int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                specialists[i] = jsonObject.getString("name");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        binding.gender.check(R.id.male);

        binding.specialistDropDown.setAdapter(new ArrayAdapter(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item, specialists));



        binding.register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.nameET.getText().toString().trim();
                int z = binding.gender.getCheckedRadioButtonId();
                boolean z1 = R.id.male == z;
                String mobile = binding.mobileET.getText().toString().trim();
                String email = binding.emailET.getText().toString().trim();
                String password = binding.passwordET.getText().toString().trim();
                String confirmPassword = binding.confirmPasswordET.getText().toString().trim();
                String userName = binding.userET.getText().toString().trim();
                String specialist = binding.specialistDropDown.getText().toString();

                if (specialist.equals("Select")) {
                    Toast.makeText(getApplicationContext(), "Should select a specialist area",
                            Toast.LENGTH_SHORT).show();
                } else {

                    int specailistId = 0;
                    try {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            if (jsonObject.getString("name").equals(specialist)) {
                                specailistId = jsonObject.optInt("id");
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    char gender;
                    if (z1) {
                        gender = 'M';
                    } else {
                        gender = 'F';
                    }

                    boolean proceed = true;

                    if (name.equals("")) {
                        proceed = false;
                        binding.nameET.setError("Name should be filled");
                    } else if (mobile.equals("")) {
                        proceed = false;
                        binding.mobileET.setError("Mobile Number should be filled");
                    } else if (email.equals("")) {
                        proceed = false;
                        binding.emailET.setError("Email should be filled");
                    } else if (userName.equals("")) {
                        proceed = false;
                        binding.userET.setError("User Name should be filled");
                    } else if (password.equals("")) {
                        proceed = false;
                        binding.passwordET.setError("Password cannot be left blank");
                    } else if (name.length() > 50) {
                        proceed = false;
                        binding.nameET.setError("Name length should be less then 50 characters");
                    } else if (mobile.length() > 15) {
                        proceed = false;
                        binding.mobileET.setError("Mobile Number Length Cannot be greater than 15");
                    } else if (userName.length() > 50) {
                        proceed = false;
                        binding.userET.setError("User Name length cannot be greater " +
                                "than 50 characters");
                    } else if (password.length() < 4 || password.length() > 20) {
                        proceed = false;
                        binding.passwordET.setError("Password length should be 4 or more than" +
                                " 4 and less than 20");
                    } else if (!password.equals(confirmPassword)) {
                        proceed = false;
                        binding.confirmPasswordET.setError("Password Mismatch");
                    }
                    if (proceed){
                        ApiClasses.DoctorRegisterPost doctorRegisterPost = new ApiClasses.
                                DoctorRegisterPost(name, mobile,
                                email,gender, userName, password, specailistId);
                        try{
                            doctorRegisterPost.execute().get();
                            JSONObject jsonObject = doctorRegisterPost.getData();

                            int message = jsonObject.getInt("message");

                            if (message==2){
                                Toast.makeText(getApplicationContext(), "User Name Already exists",
                                        Toast.LENGTH_SHORT).show();
                            }
                            else if (message==0){
                                Toast.makeText(getApplicationContext(), "Some error occurred",
                                        Toast.LENGTH_SHORT).show();
                            }
                            else if (message==1){
                                Toast.makeText(getApplicationContext(), "Registration Successful",
                                        Toast.LENGTH_SHORT).show();
                                db.deleteData();
                                db.insertData(userName, password, 1, mobile, email);
                                Intent intent = new Intent(DoctorRegistration.this,
                                        Doctor.class);
                                startActivity(intent);
                                finish();
                            }
                        }catch(Exception e){
                            e.printStackTrace();
                        }


                    }
                }
            }
        });
    }
}