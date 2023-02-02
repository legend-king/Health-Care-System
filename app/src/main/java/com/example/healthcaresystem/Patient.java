package com.example.healthcaresystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.healthcaresystem.Fragments.PatientBookAmbulanceFragment;
import com.example.healthcaresystem.Fragments.PatientChatDisplayFragment;
import com.example.healthcaresystem.Fragments.PatientPhysicalActivityFragment;
import com.example.healthcaresystem.Fragments.PatientPrescribedMedicineFragment;
import com.example.healthcaresystem.Fragments.PatientProfileFragment;
import com.example.healthcaresystem.Fragments.PatientReportUploadFragment;
import com.example.healthcaresystem.Fragments.PatientReportViewFragment;
import com.example.healthcaresystem.Fragments.PatientSearchDocFragment;
import com.example.healthcaresystem.Fragments.PatientSearchNutritionistFragment;
import com.example.healthcaresystem.databinding.ActivityPatientBinding;
import com.google.android.material.navigation.NavigationView;

import java.io.File;

public class Patient extends AppCompatActivity {
    ActivityPatientBinding binding;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    FragmentManager fragmentManager;
    DBHelper db;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPatientBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        File myDir = new File(getCacheDir(), "folder");
        myDir.mkdir();

        File[] files = myDir.listFiles();
        if(files!=null) {
            for(File f: files) {
                f.delete();
            }
        }

        try {

            db = new DBHelper(this);

            Cursor cursor = db.getData();

            cursor.moveToNext();

            id = cursor.getString(0);


            fragmentManager = getSupportFragmentManager();


            actionBarDrawerToggle = new ActionBarDrawerToggle(this, binding.myDrawerLayout,
                    R.string.nav_open, R.string.nav_close);

            binding.myDrawerLayout.addDrawerListener(actionBarDrawerToggle);
            actionBarDrawerToggle.syncState();
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            Fragment fragment = new PatientProfileFragment();
            fragmentManager.beginTransaction().replace(binding.patFragment.getId(), fragment,
                    "profile").commit();
        }catch(Exception e){
            Log.e("error", e.toString());
        }


        binding.nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                String tag="";
                switch (item.getItemId()){
                    case R.id.patLogout:
                        db.deleteData();
                        Intent intent = new Intent(Patient.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.patChats:
                        fragment = new PatientChatDisplayFragment();
                        tag = "chatDisplay";
                        break;
                    case R.id.patPrescribedMedicine:
                        fragment = new PatientPrescribedMedicineFragment();
                        tag = "prescribedMedicine";
                        break;
                    case R.id.patSearchNutri:
                        fragment = new PatientSearchNutritionistFragment();
                        tag="nutritionistSearch";
                        break;
                    case R.id.patReportUpload:
                        fragment = new PatientReportUploadFragment();
                        tag="reportUpload";
                        break;
                    case R.id.patReportView:
                        fragment = new PatientReportViewFragment();
                        tag="reportView";
                        break;
                    case R.id.patSearch:
                        fragment = new PatientSearchDocFragment();
                        tag = "doctorSearch";
                        break;
                    case R.id.patAmbulance:
                        fragment = new PatientBookAmbulanceFragment();
                        tag = "ambulance";
                        break;
                    case R.id.patPhysicalActivity:
                        fragment = new PatientPhysicalActivityFragment();
                        tag = "physicalActivity";
                        break;
                    default:
                        fragment = new PatientProfileFragment();
                        tag = "profile";
                }
                if (fragment!=null){
                    fragmentManager.beginTransaction().replace(R.id.patFragment, fragment, tag)
                            .addToBackStack(tag).commit();
                }
                binding.myDrawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
        } else {
            super.onBackPressed();
        }

    }
}