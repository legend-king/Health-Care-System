package com.example.healthcaresystem;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.healthcaresystem.databinding.ActivityPatientRegistrationBinding;

import org.json.JSONObject;

import java.util.Calendar;

public class PatientRegistration extends AppCompatActivity {
    ActivityPatientRegistrationBinding binding;
    private DatePicker datePicker;
    private Calendar calendar;
    private int year, month, day;
    private String currentDate, selectedDate;
    DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPatientRegistrationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        try {


            db = new DBHelper(this);

            calendar = Calendar.getInstance();
            year = calendar.get(Calendar.YEAR);

            month = calendar.get(Calendar.MONTH) + 1;
            day = calendar.get(Calendar.DAY_OF_MONTH);

            int a = day, b = month;
            String x1, y1;
            if (a < 10) {
                x1 = "0" + a;
            } else {
                x1 = "" + a;
            }
            if (b < 10) {
                y1 = "0" + b;
            } else {
                y1 = "" + b;
            }

            currentDate = x1 + "-" + y1 + "-" + year;
            selectedDate = year + "-" + y1 + "-" + x1;

            binding.dob.setText(new StringBuilder().append(x1).append("-")
                    .append(y1).append("-").append(year));
        }catch(Exception e){
            Log.e("error", e.toString());
        }

            binding.dob.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(PatientRegistration.this,
                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker view, int year1,
                                                      int monthOfYear, int dayOfMonth) {

                                    year = year1;
                                    month = monthOfYear + 1;
                                    day = dayOfMonth;

                                    int x = day, y = month;
                                    String x1, y1;
                                    if (x < 10) {
                                        x1 = "0" + x;
                                    } else {
                                        x1 = "" + x;
                                    }
                                    if (y < 10) {
                                        y1 = "0" + y;
                                    } else {
                                        y1 = "" + y;
                                    }
                                    selectedDate = year + "-" + y1 + "-" + x1;
                                    binding.dob.setText(new StringBuilder().append(x1).append("-")
                                            .append(y1).append("-").append(year));

                                }
                            }, year, month - 1, day);
                    datePickerDialog.show();
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
                    double height = Double.parseDouble(binding.heightET.getText().toString());
                    double weight = Double.parseDouble(binding.weightET.getText().toString());
                    String dob = binding.dob.getText().toString();

                    if (dob.equals(currentDate)) {
                        Toast.makeText(getApplicationContext(), "Select your birth date",
                                Toast.LENGTH_SHORT).show();
                    } else {

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
                        if (proceed) {
                            ApiClasses.PatientRegisterPost patientRegisterPost = new ApiClasses.
                                    PatientRegisterPost(name, mobile,
                                    email, gender, userName, password, selectedDate, height, weight);
                            try {
                                patientRegisterPost.execute().get();
                                JSONObject jsonObject = patientRegisterPost.getData();

                                int message = jsonObject.getInt("message");

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
                                    db.insertData(userName, password, 0, mobile, email);
                                    Intent intent = new Intent(PatientRegistration.this,
                                            Patient.class);
                                    startActivity(intent);
                                    finish();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                        }
                    }
                }
            });

            binding.login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(PatientRegistration.this, Login.class);
                    intent.putExtra("type", 0);
                    startActivity(intent);
                }
            });
    }
}