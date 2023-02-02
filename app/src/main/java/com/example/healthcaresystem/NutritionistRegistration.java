package com.example.healthcaresystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.UploadProgressListener;
import com.example.healthcaresystem.databinding.ActivityNutritionistRegistrationBinding;

import org.json.JSONObject;

public class NutritionistRegistration extends AppCompatActivity {
    ActivityNutritionistRegistrationBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNutritionistRegistrationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        DBHelper db = new DBHelper(this);

        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NutritionistRegistration.this, Login.class);
                intent.putExtra("type", 3);
                startActivity(intent);
                finish();
            }
        });

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
                String consultationCharge = binding.consultationChargeET.getText().toString().trim();

                    String gender;
                    if (z1) {
                        gender = "M";
                    } else {
                        gender = "F";
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
                    else if (consultationCharge.length()==0){
                        proceed = false;
                        binding.consultationChargeET.setError("Consultation Charge cannot be empty");
                    }
                    if (proceed){

                        JSONObject data = new JSONObject();
                        try{
                            data.put("name", name);
                            data.put("mobile", mobile);
                            data.put("gender", gender);
                            data.put("userName", userName);
                            data.put("email", email);
                            data.put("consultationCharge", consultationCharge);
                            data.put("password", password);
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                        String url = getString(R.string.url);
                        AndroidNetworking.post(url+"nutritionistRegister")
                                .addJSONObjectBody(data)
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
                                try {
                                    int message = response.getInt("message");
                                    if (message == 2) {
                                        Toast.makeText(getApplicationContext(), "User Name Already exists",
                                                Toast.LENGTH_SHORT).show();
                                    } else if (message == 0) {
                                        Toast.makeText(getApplicationContext(), "Some error occurred",
                                                Toast.LENGTH_SHORT).show();
                                    } else if (message == 1) {
                                        Toast.makeText(getApplicationContext(), "Registration Successful",
                                                Toast.LENGTH_SHORT).show();
                                        db.deleteData();
                                        db.insertData(userName, password, 3, mobile, email);
                                        Intent intent = new Intent(NutritionistRegistration.this,
                                                Nutritionist.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }catch(Exception e){
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(ANError anError) {
                                Log.e("error", anError.toString());
                            }
                        });


                    }

            }
        });
    }
}