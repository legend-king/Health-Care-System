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
import com.example.healthcaresystem.Fragments.PatientReportViewFragment;
import com.example.healthcaresystem.Models.PatChatDispModel;
import com.example.healthcaresystem.R;

import java.util.List;

public class DocChatDispAdapter  extends RecyclerView.Adapter<DocChatDispAdapter.ViewHolder>{
    private Context context;
    private List<PatChatDispModel> data;
    private String id;
    private int work_id, type;
    private FragmentManager fragmentManager;
    private boolean check;


    public DocChatDispAdapter(Context context, List<PatChatDispModel> data,
                              FragmentManager fragmentManager, boolean check) {
        this.context = context;
        this.data = data;
        this.fragmentManager=fragmentManager;
        DBHelper db = new DBHelper(context);
        Cursor cursor = db.getData();
        cursor.moveToNext();
        id = cursor.getString(0);
        type = cursor.getInt(2);
        this.check=check;
        setHasStableIds(true);
    }

    @NonNull
    @Override
    public DocChatDispAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                            int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rc_doc_chat_disp,
                parent,false);
        return new DocChatDispAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DocChatDispAdapter.ViewHolder holder, int position) {
        PatChatDispModel x = data.get(position);
        holder.name.setText(x.getName());
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

        public TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            name = itemView.findViewById(R.id.rcDocChatDispName);
        }

        @Override
        public void onClick(View v) {
            int position = this.getAdapterPosition();
            PatChatDispModel x = data.get(position);
            if (check){
                if (type==1){
                    fragmentManager.beginTransaction().replace(R.id.docFragment, new
                            PatDocChatFragment(x.getUsername(), x.getName()), "chat").
                            addToBackStack("chat").commit();
                }
                else if (type==3){
                    fragmentManager.beginTransaction().replace(R.id.nutritionistFragment, new
                            PatDocChatFragment(x.getUsername(), x.getName()), "chat").
                            addToBackStack("chat").commit();
                }
            }
            else{
                if (type==1){
                    fragmentManager.beginTransaction().replace(R.id.docFragment, new
                            PatientReportViewFragment(x.getUsername()), "patientReportView").
                            addToBackStack("chat").commit();
                }
                else if (type==3){
                    fragmentManager.beginTransaction().replace(R.id.nutritionistFragment, new
                            PatientReportViewFragment(x.getUsername()), "patientReportView").
                            addToBackStack("chat").commit();
                }
            }


        }
    }
}
