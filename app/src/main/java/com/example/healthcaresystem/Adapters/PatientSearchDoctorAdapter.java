package com.example.healthcaresystem.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthcaresystem.DBHelper;
import com.example.healthcaresystem.Fragments.DoctorProfileFragment;
import com.example.healthcaresystem.Fragments.PatDocChatFragment;
import com.example.healthcaresystem.Models.SearchDoctorModel;
import com.example.healthcaresystem.R;

import java.util.List;

public class PatientSearchDoctorAdapter extends RecyclerView.Adapter<PatientSearchDoctorAdapter.ViewHolder>{
    private Context context;
    private List<SearchDoctorModel> data;
    private String id;
    private int work_id;
    private FragmentManager fragmentManager;

    public PatientSearchDoctorAdapter(Context context, List<SearchDoctorModel> data, FragmentManager fragmentManager) {
        this.context = context;
        this.data = data;
        DBHelper db = new DBHelper(context);
        Cursor cursor = db.getData();
        cursor.moveToNext();
        id = cursor.getString(0);
        this.fragmentManager=fragmentManager;
    }

    @NonNull
    @Override
    public PatientSearchDoctorAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                                        int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rc_patient_search_doctor,
                parent,false);
        return new PatientSearchDoctorAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PatientSearchDoctorAdapter.ViewHolder holder, int position) {
        SearchDoctorModel x = data.get(position);
        holder.name.setText(x.getName());
        holder.docImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager.beginTransaction().replace(R.id.patFragment,
                        new DoctorProfileFragment(x.getUsername()), "doctorProfile").
                        addToBackStack("doctorProfile").commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView name;
        public ImageView docImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            name = itemView.findViewById(R.id.rcPatSearchDoctorName);
            docImage = itemView.findViewById(R.id.rcPatDocProfile);
        }

        @Override
        public void onClick(View v) {
            int position = this.getAdapterPosition();
            SearchDoctorModel x = data.get(position);
            fragmentManager.beginTransaction().replace(R.id.patFragment, new
                    PatDocChatFragment(x.getUsername(), x.getName()), "chat")
                    .addToBackStack("chat").commit();
        }
    }
}
