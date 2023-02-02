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
import android.widget.TextView;

import com.example.healthcaresystem.Fragments.DoctorChatDisplayFragment;
import com.example.healthcaresystem.Fragments.DoctorPrescribedMedicineFragment;
import com.example.healthcaresystem.Fragments.DoctorProfileFragment;
import com.example.healthcaresystem.Fragments.NutritionistChatDisplayFragment;
import com.example.healthcaresystem.Fragments.NutritionistProfileFragment;
import com.example.healthcaresystem.databinding.ActivityNutritionistBinding;
import com.google.android.material.navigation.NavigationView;

public class Nutritionist extends AppCompatActivity {
    ActivityNutritionistBinding binding;
    String id;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    FragmentManager fragmentManager;
    DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNutritionistBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        try {

            db = new DBHelper(this);

            Cursor cursor = db.getData();

            cursor.moveToNext();

            id = cursor.getString(0);


            fragmentManager = getSupportFragmentManager();


            actionBarDrawerToggle = new ActionBarDrawerToggle(this,
                    binding.myDrawerLayout,
                    R.string.nav_open, R.string.nav_close);

            binding.myDrawerLayout.addDrawerListener(actionBarDrawerToggle);
            actionBarDrawerToggle.syncState();
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            Fragment fragment = new NutritionistProfileFragment(id);
            fragmentManager.beginTransaction().replace(binding.nutritionistFragment.getId(),
                    fragment, "profile").commit();
        }catch(Exception e){
            Log.e("error", e.toString());
        }

        binding.nav.setNavigationItemSelectedListener(new NavigationView.
                OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                String tag = null;
                boolean flag=false;
                switch (item.getItemId()){
                    case R.id.nutriLogout:
                        db.deleteData();
                        Intent intent = new Intent(Nutritionist.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.nutriChats:
                        fragment = new NutritionistChatDisplayFragment();
                        tag = "chatDisplay";
                        break;
//                    case R.id.docPrescribedMedicine:
//                        fragment = new DoctorPrescribedMedicineFragment();
//                        tag = "prescribedMedicine";
//                        break;
                    default:
                        fragment = new NutritionistProfileFragment(id);
                        tag = "profile";
                }
                if (fragment!=null){
                    if (flag){
                        fragmentManager.beginTransaction().replace(binding.nutritionistFragment.getId(),
                                fragment, tag).commit();
                    }
                    else{
                        fragmentManager.beginTransaction().replace(binding.nutritionistFragment.getId(),
                                fragment, tag).addToBackStack(tag).commit();
                    }

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