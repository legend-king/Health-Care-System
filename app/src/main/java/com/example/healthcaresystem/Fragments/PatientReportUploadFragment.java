package com.example.healthcaresystem.Fragments;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.UploadProgressListener;
import com.example.healthcaresystem.DBHelper;
import com.example.healthcaresystem.R;
import com.example.healthcaresystem.databinding.FragmentPatientReportUploadBinding;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class PatientReportUploadFragment extends Fragment {
    FragmentPatientReportUploadBinding binding;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    File source;
    String path;
    DBHelper db;
    InputStream inputStream;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private static final int PICKFILE_RESULT_CODE = 9544;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPatientReportUploadBinding.inflate(inflater, container, false);

        path = getActivity().getCacheDir().getPath()+"/folder/";

        binding.upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.reportNameET.getText().toString().trim();
                String remark = binding.remarkET.getText().toString().trim();

                if (name.length()==0){
                    binding.reportNameET.setError("Report Name cannot be empty");
                }
                else if (source==null || !source.exists()){
                    Toast.makeText(getActivity(), "Please select the report file", Toast.LENGTH_SHORT).show();
                }
                else{
                    ProgressDialog progressDialog = new ProgressDialog(getActivity());
                    progressDialog.setTitle("Uploading File");
                    progressDialog.setMessage("Uploading to server, please wait");
                    progressDialog.show();
                    String url = getString(R.string.url);
                    Log.e("Api", url + "patReportUpload");
                    db = new DBHelper(getActivity());
                    Cursor cursor = db.getData();
                    cursor.moveToNext();

                    String id = String.valueOf(cursor.getString(0));
                    AndroidNetworking.post(url + "patReportUpload")
                            .addFileBody(source)
                            .addQueryParameter("userName", id)
                            .addQueryParameter("reportName", name)
                            .addQueryParameter("remark", remark)
                            .setPriority(Priority.HIGH)
                            .build()
                            .setUploadProgressListener(new UploadProgressListener() {
                                @Override
                                public void onProgress(long bytesUploaded, long totalBytes) {
                                }
                            }).getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.e("Api", "result" + response);
                            progressDialog.dismiss();
                            try{
                                String message = response.getString("message");
                                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                                if (message.equals("Uploaded Successfully")){
                                    binding.remarkET.setText("");
                                    binding.fileSelected.setText("No File Selected");
                                    source=null;
                                    binding.reportNameET.setText("");
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                                Log.e("error", e.toString());
                            }

                        }

                        @Override
                        public void onError(ANError anError) {
                            Log.e("error", anError.toString());
                        }
                    });
                }
            }


        });


        binding.selectFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyStoragePermissions(getActivity());
//                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//                intent.setType("image/*");
//                requireActivity().startActivityForResult(Intent.createChooser(intent, "Open Gallery"), PICK_IMAGE_REQUEST);
                Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
                chooseFile.addCategory(Intent.CATEGORY_OPENABLE);
                chooseFile.setType("application/pdf");
                startActivityForResult(
                        Intent.createChooser(chooseFile, "Choose a file"),
                        PICKFILE_RESULT_CODE
                );
            }
        });



        return binding.getRoot();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICKFILE_RESULT_CODE && resultCode == Activity.RESULT_OK){
            Uri content_describer = data.getData();
            File root = android.os.Environment.getExternalStorageDirectory();


            String src = content_describer.getPath();
            try {
                inputStream = getActivity().getContentResolver().openInputStream(content_describer);
                File file = new File(path+"appgeneratedtemporaryfile.pdf");
                FileOutputStream outputStream = new FileOutputStream(file, false);
                int read;
                byte[] bytes = new byte[8192];
                while ((read = inputStream.read(bytes)) != -1) {
                    outputStream.write(bytes, 0, read);
                }
                source = new File(path+"appgeneratedtemporaryfile.pdf");
                if (source.exists()){
//                    String filename = src.substring(src.lastIndexOf("/")+1);
                    binding.fileSelected.setText("File Selected");
                }
                else{
                    Toast.makeText(getActivity(), "Not able to locate the file. ",
                            Toast.LENGTH_SHORT).show();
                }
            } catch (IOException e) {
                Toast.makeText(getActivity(), "Not able to locate the file. " ,
                        Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
//            Log.e("error", src);
//            Log.e("src", content_describer.toString());
//            if (src.contains(":")){
//                src = src.substring(src.indexOf(':')+1);
//            }

//            String youFilePath = Environment.getExternalStorageDirectory().toString()+"/iv.jpeg";
//            String youFilePath = "/storage/emulated/0/Download/iv.jpeg";

//            File source1 = new File(src);
//            Log.e("src is ", source.toString());
//            Log.e("error", source.exists()+"");
            //            String filename = content_describer.getLastPathSegment();
//            String url=getString(R.string.url);
//            Log.e("error", url);
//            Log.e("source", String.valueOf(source));
//            Log.e("FileName is ",filename);
//            Log.e("error source1", source1.exists()+"");
//            if (source.exists()){
//                Log.e("Api", url+"test");
//                AndroidNetworking.post(url+"test")
//                        .addFileBody(source)
//                        .addQueryParameter("test", "test data for checking whether it will work correctly or not")
//                        .setPriority(Priority.HIGH)
//                        .build()
//                        .setUploadProgressListener(new UploadProgressListener() {
//                            @Override
//                            public void onProgress(long bytesUploaded, long totalBytes) {
//                            }
//                        }).getAsJSONObject(new JSONObjectRequestListener() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        Log.e("Api", "result" + response);
//                    }
//                    @Override
//                    public void onError(ANError anError) {
//                        Log.e("error", anError.toString());
//                    }
//                });
//            }

        }
    }

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

}