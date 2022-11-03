package com.example.healthcaresystem.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthcaresystem.DBHelper;
import com.example.healthcaresystem.Models.PhysicalActivityModel;
import com.example.healthcaresystem.R;

import java.util.List;

public class PatientPhysicalActivityAdapter extends RecyclerView.Adapter<PatientPhysicalActivityAdapter.ViewHolder>{
    private Context context;
    private List<PhysicalActivityModel> data;
    private String id;
    private int work_id;

    public PatientPhysicalActivityAdapter(Context context, List<PhysicalActivityModel> data) {
        this.context = context;
        this.data = data;
        DBHelper db = new DBHelper(context);
        Cursor cursor = db.getData();
        cursor.moveToNext();
        id = cursor.getString(0);
    }

    @NonNull
    @Override
    public PatientPhysicalActivityAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                                  int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rc_patient_physical_activity,
                parent,false);
        return new PatientPhysicalActivityAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PatientPhysicalActivityAdapter.ViewHolder holder, int position) {
        PhysicalActivityModel x = data.get(position);
        holder.category.setText("Category : "+x.getCategory());
        holder.activity.setText(x.getActivity());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView category, activity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            category = itemView.findViewById(R.id.rcPatCategory);
            activity = itemView.findViewById(R.id.rcPatActivity);
        }

        @Override
        public void onClick(View v) {
            int position = this.getAdapterPosition();
            PhysicalActivityModel x = data.get(position);
        }
    }
}
