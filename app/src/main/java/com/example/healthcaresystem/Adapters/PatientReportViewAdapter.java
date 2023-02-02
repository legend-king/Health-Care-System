package com.example.healthcaresystem.Adapters;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthcaresystem.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PatientReportViewAdapter extends RecyclerView.Adapter<PatientReportViewAdapter.ViewHolder>{
    private Context context;
    private JSONArray data;

    public PatientReportViewAdapter(Context context, JSONArray data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public PatientReportViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                             int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rc_patient_report_display,
                parent,false);
        return new PatientReportViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PatientReportViewAdapter.ViewHolder holder, int position) {
        JSONObject x = null;
        try {
            x = data.getJSONObject(position);
            holder.reportName.setText("Report Name : "+x.getString("report_name"));
            String remark = x.getString("remark").trim();
            if (remark.length()==0){
                remark="-";
            }
            holder.remark.setText("Remark : "+remark);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject finalX = x;
        holder.downloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url= context.getString(R.string.downloadurl);
                try {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (context.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                            Toast.makeText(context, "Permission not granted", Toast.LENGTH_SHORT).show();
                        } else {
                            startDownloading(url, finalX.getString("report"),
                                    finalX.getString("report_name") + ".pdf");
                        }
                    } else {
                        startDownloading(url, finalX.getString("report"),
                                finalX.getString("report_name") + ".pdf");
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });






    }

    public void startDownloading(String url, String name, String fileName){
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url+name));
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI |
                DownloadManager.Request.NETWORK_MOBILE);
        request.setTitle(fileName);
        request.setDescription("Downloading File.....");

        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);

        DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);
    }

    @Override
    public int getItemCount() {
        return data.length();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView reportName, remark;
        public Button downloadBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            reportName = itemView.findViewById(R.id.rcPatientReportViewReportName);
            remark = itemView.findViewById(R.id.rcPatientReportViewRemark);
            downloadBtn = itemView.findViewById(R.id.rcPatientReportViewDownloadBtn);

        }

        @Override
        public void onClick(View v) {
            int position = this.getAdapterPosition();
        }
    }
}