package com.example.healthcaresystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.healthcaresystem.databinding.ActivityLoginBinding;

import org.json.JSONObject;


public class Login extends AppCompatActivity {
    ActivityLoginBinding binding;
    private int type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        DBHelper db = new DBHelper(this);

        Intent intent = getIntent();
        type = intent.getIntExtra("type", 0);
        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = binding.userET.getText().toString().trim();
                String password = binding.passwordET.getText().toString().trim();

                try{
                    ApiClasses.UserLogin userLogin= new ApiClasses.UserLogin(userName, password, type);
                    userLogin.execute().get();
                    JSONObject jsonObject = userLogin.getData();

                    if (jsonObject.getInt("message")==1){
                        if (type==0){
                            db.deleteData();
                            db.insertData(userName, password, type,
                                    jsonObject.getString("mobile"),
                                    jsonObject.getString("email"));
                            Intent intent1 = new Intent(Login.this, Patient.class);
                            startActivity(intent1);
                        }
                        else if (type==1){
                            db.deleteData();
                            db.insertData(userName, password, type,
                                    jsonObject.getString("mobile"),
                                    jsonObject.getString("email"));
                            Intent intent1 = new Intent(Login.this, Doctor.class);
                            startActivity(intent1);
                        }
                    }
                    else{
                        binding.userET.setError("Invalid Credentials");
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    Log.e("error", e.toString());
                }
            }
        });

        binding.register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (type==0){
                    Intent intent = new Intent(Login.this, PatientRegistration.class);
                    startActivity(intent);
                    finish();
                }
                else if (type==1){
                    Intent intent = new Intent(Login.this, DoctorRegistration.class);
                    startActivity(intent);
                    finish();
                }

            }
        });

        binding.forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}