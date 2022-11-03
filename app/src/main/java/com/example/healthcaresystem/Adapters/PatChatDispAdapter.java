package com.example.healthcaresystem.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthcaresystem.DBHelper;
import com.example.healthcaresystem.Fragments.PatDocChatFragment;
import com.example.healthcaresystem.Models.PatChatDispModel;
import com.example.healthcaresystem.R;

import java.util.List;

public class PatChatDispAdapter extends RecyclerView.Adapter<PatChatDispAdapter.ViewHolder>{
    private Context context;
    private List<PatChatDispModel> data;
    private String id;
    private int work_id;
    private FragmentManager fragmentManager;


    public PatChatDispAdapter(Context context, List<PatChatDispModel> data, FragmentManager fragmentManager) {
        this.context = context;
        this.data = data;
        this.fragmentManager=fragmentManager;
        DBHelper db = new DBHelper(context);
        Cursor cursor = db.getData();
        cursor.moveToNext();
        id = cursor.getString(0);
        setHasStableIds(true);
    }

    @NonNull
    @Override
    public PatChatDispAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rc_pat_chat_display,
                parent,false);
        return new PatChatDispAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PatChatDispAdapter.ViewHolder holder, int position) {
        PatChatDispModel x = data.get(position);
            holder.name.setText(x.getName());
            holder.specialist.setText(x.getSpecialist());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView name, specialist;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            name = itemView.findViewById(R.id.rcPatChatDispName);
            specialist = itemView.findViewById(R.id.rcPatChatDispSpecialist);
        }

        @Override
        public void onClick(View v) {
            int position = this.getAdapterPosition();
            PatChatDispModel x = data.get(position);
            fragmentManager.beginTransaction().replace(R.id.patFragment, new
                    PatDocChatFragment(x.getUsername(), x.getName())).commit();
        }
    }
}
