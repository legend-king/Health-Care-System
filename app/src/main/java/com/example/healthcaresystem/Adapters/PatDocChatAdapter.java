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
import com.example.healthcaresystem.Models.ChatModel;
import com.example.healthcaresystem.R;

import java.util.List;

public class PatDocChatAdapter extends RecyclerView.Adapter<PatDocChatAdapter.ViewHolder>{
    private Context context;
    private List<ChatModel> data;
    private String id;


    public PatDocChatAdapter(Context context, List<ChatModel> data) {
        this.context = context;
        this.data = data;
        DBHelper db = new DBHelper(context);
        Cursor cursor = db.getData();
        cursor.moveToNext();
        id = cursor.getString(0);
        setHasStableIds(true);
    }

    @NonNull
    @Override
    public PatDocChatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                                        int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rc_pat_doc_chat,
                parent,false);
        return new PatDocChatAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PatDocChatAdapter.ViewHolder holder, int position) {
        ChatModel x = data.get(position);
        if (x.getSender().equals(id)){
            holder.send.setText(x.getMessage());
            holder.receive.setVisibility(View.GONE);
        }
        else{
            holder.receive.setText(x.getMessage());
            holder.send.setVisibility(View.GONE);
        }
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

        public TextView send, receive;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            send = itemView.findViewById(R.id.rcPatDocChatSend);
            receive = itemView.findViewById(R.id.rcPatDocChatReceive);
        }

        @Override
        public void onClick(View v) {
            int position = this.getAdapterPosition();
            ChatModel x = data.get(position);
        }
    }
}
