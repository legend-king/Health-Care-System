package com.example.healthcaresystem;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.UploadProgressListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiClasses {
    static String url = "http://192.168.1.30:8000/api/";
//    static String url = "http://172.16.9.232:8000/api/";

    public static class Specilaist extends AsyncTask<Void, Void, String> {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String resultJson = "";

        @Override
        protected String doInBackground(Void... params) {
            try {
                Log.e("error", url+"doctorSpecialist");
                String site_url_json = url+"doctorSpecialist";
                URL url = new URL(site_url_json);


                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                resultJson = buffer.toString();
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("error", e.toString());
            }
            return resultJson;
        }


        protected void onPostExecute(String strJson) {
            super.onPostExecute(strJson);
        }

        public JSONArray getData() throws Exception{
            Log.e("error","Result "+resultJson);
            return new JSONArray(resultJson);
        }
    }


    static class UserLogin extends AsyncTask<Void, Void, String> {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String resultJson = "";
        String user;
        String password;
        int type;

        UserLogin(String user, String password, int type){
            this.user=user;
            this.password=password;
            this.type = type;
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                Log.e("error", url+"login");
                String site_url_json = url+"login";
                URL url = new URL(site_url_json);


                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Accept-Charset", "UTF-8");
                urlConnection.connect();

                DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
                wr.writeBytes("userName=" + user + "&password=" + password + "&type=" + type);
                wr.flush();
                wr.close();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                resultJson = buffer.toString();
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("error", e.toString());
            }
            return resultJson;
        }


        protected void onPostExecute(String strJson) {
            super.onPostExecute(strJson);
        }

        public JSONObject getData() throws Exception{
            Log.e("error","Result "+resultJson);
            return new JSONObject(resultJson);
        }
    }


    static class DoctorRegisterPost extends AsyncTask<Void, Void, String> {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String resultJson = "";
        String user, password, name, mobile, email;
        int specialist;
        char gender;

        DoctorRegisterPost(String name, String mobile, String email, char gender, String user,
                           String password, int specialist){
            this.user=user;
            this.password=password;
            this.name=name;
            this.mobile=mobile;
            this.email=email;
            this.gender=gender;
            this.specialist=specialist;
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                Log.e("error", url+"doctorRegister");
                String site_url_json = url+"doctorRegister";
                URL url = new URL(site_url_json);


                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Accept-Charset", "UTF-8");
                urlConnection.connect();

                DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
                wr.writeBytes("userName=" + user + "&password=" + password + "&name="+name
                        + "&gender="+gender+ "&email="+email+"&mobile="+mobile+
                        "&specialist="+specialist);
                wr.flush();
                wr.close();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                resultJson = buffer.toString();
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("error", e.toString());
            }
            return resultJson;
        }


        protected void onPostExecute(String strJson) {
            super.onPostExecute(strJson);
        }

        public JSONObject getData() throws Exception{
            Log.e("error","Result "+resultJson);
            return new JSONObject(resultJson);
        }
    }


    public static class DoctorProfileGet extends AsyncTask<Void, Void, String> {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String resultJson = "";

        String userName;

        public DoctorProfileGet(String userName){
            this.userName=userName;
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                Log.e("error", url+"doctorProfile/"+userName);
                String site_url_json = url+"doctorProfile/"+userName;
                URL url = new URL(site_url_json);


                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                resultJson = buffer.toString();
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("error", e.toString());
            }
            return resultJson;
        }


        protected void onPostExecute(String strJson) {
            super.onPostExecute(strJson);
        }

        public JSONObject getData() throws Exception{
            Log.e("error","Result "+resultJson);
            return new JSONObject(resultJson);
        }
    }


    static class PatientRegisterPost extends AsyncTask<Void, Void, String> {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String resultJson = "";
        String user, password, name, mobile, email, dob;
        double height, weight;
        char gender;

        PatientRegisterPost(String name, String mobile, String email, char gender, String user,
                           String password, String dob, double height, double weight){
            this.user=user;
            this.password=password;
            this.name=name;
            this.mobile=mobile;
            this.email=email;
            this.gender=gender;
            this.dob=dob;
            this.height=height;
            this.weight=weight;
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                Log.e("error", url+"patientRegister");
                String site_url_json = url+"patientRegister";
                URL url = new URL(site_url_json);


                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Accept-Charset", "UTF-8");
                urlConnection.connect();

                DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
                wr.writeBytes("userName=" + user + "&password=" + password + "&name="+name
                        + "&gender="+gender+ "&email="+email+"&mobile="+mobile+
                        "&dob="+dob+"&height="+height+"&weight="+weight);
                wr.flush();
                wr.close();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                resultJson = buffer.toString();
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("error", e.toString());
            }
            return resultJson;
        }


        protected void onPostExecute(String strJson) {
            super.onPostExecute(strJson);
        }

        public JSONObject getData() throws Exception{
            Log.e("error","Result "+resultJson);
            return new JSONObject(resultJson);
        }
    }

    public static class PatientProfileGet extends AsyncTask<Void, Void, String> {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String resultJson = "";

        String userName;

        public PatientProfileGet(String userName){
            this.userName=userName;
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                Log.e("error", url+"patientProfile/"+userName);
                String site_url_json = url+"patientProfile/"+userName;
                URL url = new URL(site_url_json);


                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                resultJson = buffer.toString();
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("error", e.toString());
            }
            return resultJson;
        }


        protected void onPostExecute(String strJson) {
            super.onPostExecute(strJson);
        }

        public JSONObject getData() throws Exception{
            Log.e("error","Result "+resultJson);
            return new JSONObject(resultJson);
        }
    }


    public static class PatientPhysicalActivityGet extends AsyncTask<Void, Void, String> {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String resultJson = "";

        @Override
        protected String doInBackground(Void... params) {
            try {
                Log.e("error", url+"patientPhysicalActivity");
                String site_url_json = url+"patientPhysicalActivity";
                URL url = new URL(site_url_json);


                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                resultJson = buffer.toString();
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("error", e.toString());
            }
            return resultJson;
        }


        protected void onPostExecute(String strJson) {
            super.onPostExecute(strJson);
        }

        public JSONArray getData() throws Exception{
            Log.e("error","Result "+resultJson);
            return new JSONArray(resultJson);
        }
    }


    public static class PatientSearchDoctorGet extends AsyncTask<Void, Void, String> {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String resultJson = "";
        int id;

        public PatientSearchDoctorGet(int id){
            this.id=id;
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                Log.e("error", url+"patientSearchDoctor/"+id);
                String site_url_json = url+"patientSearchDoctor/"+id;
                URL url = new URL(site_url_json);


                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                resultJson = buffer.toString();
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("error", e.toString());
            }
            return resultJson;
        }


        protected void onPostExecute(String strJson) {
            super.onPostExecute(strJson);
        }

        public JSONArray getData() throws Exception{
            Log.e("error","Result "+resultJson);
            return new JSONArray(resultJson);
        }
    }

    public static class PatDocChatPost extends AsyncTask<Void, Void, String> {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String resultJson = "";
        String sender, receiver, message;

        public PatDocChatPost(String sender, String receiver, String message){
            this.sender=sender;
            this.receiver=receiver;
            this.message=message;
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                Log.e("error", url+"patDocChat");
                String site_url_json = url+"patDocChat";
                URL url = new URL(site_url_json);


                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Accept-Charset", "UTF-8");
                urlConnection.connect();

                DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
                wr.writeBytes("sender=" + sender + "&receiver=" + receiver + "&message="+message);
                wr.flush();
                wr.close();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                resultJson = buffer.toString();
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("error", e.toString());
            }
            return resultJson;
        }


        protected void onPostExecute(String strJson) {
            super.onPostExecute(strJson);
        }

        public JSONObject getData() throws Exception{
            Log.e("error","Result "+resultJson);
            return new JSONObject(resultJson);
        }
    }


    public static class PatDocChatGet extends AsyncTask<Void, Void, String> {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String resultJson = "";
        String sender, receiver;

        public PatDocChatGet(String sender, String receiver){
            this.sender=sender;
            this.receiver=receiver;
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                Log.e("error", url+"patDocGetChat");
                String site_url_json = url+"patDocGetChat";
                URL url = new URL(site_url_json);


                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Accept-Charset", "UTF-8");
                urlConnection.connect();

                DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
                wr.writeBytes("sender=" + sender + "&receiver=" + receiver);
                wr.flush();
                wr.close();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                resultJson = buffer.toString();
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("error", e.toString());
            }
            return resultJson;
        }


        protected void onPostExecute(String strJson) {
            super.onPostExecute(strJson);
        }

        public JSONArray getData() throws Exception{
            Log.e("error","Result "+resultJson);
            return new JSONArray(resultJson);
        }
    }


    public static class PatChatDispGet extends AsyncTask<Void, Void, String> {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String resultJson = "";
        String id;

        public PatChatDispGet(String id){
            this.id=id;
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                Log.e("error", url+"patChats/"+id);
                String site_url_json = url+"patChats/"+id;
                URL url = new URL(site_url_json);


                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                resultJson = buffer.toString();
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("error", e.toString());
            }
            return resultJson;
        }


        protected void onPostExecute(String strJson) {
            super.onPostExecute(strJson);
        }

        public JSONArray getData() throws Exception{
            Log.e("error","Result "+resultJson);
            return new JSONArray(resultJson);
        }
    }


    public static class DocChatDispGet extends AsyncTask<Void, Void, String> {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String resultJson = "";
        String id;

        public DocChatDispGet(String id){
            this.id=id;
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                Log.e("error", url+"docChats/"+id);
                String site_url_json = url+"docChats/"+id;
                URL url = new URL(site_url_json);


                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                resultJson = buffer.toString();
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("error", e.toString());
            }
            return resultJson;
        }


        protected void onPostExecute(String strJson) {
            super.onPostExecute(strJson);
        }

        public JSONArray getData() throws Exception{
            Log.e("error","Result "+resultJson);
            return new JSONArray(resultJson);
        }
    }

//    static class Test{
//        JSONObject test() {
//            final JSONObject[] jsonObject = {null};
//            AndroidNetworking.post(url + "prescribeMedicine")
//                    .addJSONArrayBody(data)
//                    .addQueryParameter("diagnosis", diagnosis)
//                    .addQueryParameter("advice", advice)
//                    .addQueryParameter("prescribedBy", prescribedBy)
//                    .addQueryParameter("prescribedTo", prescribedTo)
//                    .setPriority(Priority.HIGH)
//                    .build()
//                    .setUploadProgressListener(new UploadProgressListener() {
//                        @Override
//                        public void onProgress(long bytesUploaded, long totalBytes) {
//                        }
//                    }).getAsJSONObject(new JSONObjectRequestListener() {
//                @Override
//                public void onResponse(JSONObject response) {
//                    Log.e("Api", "result" + response);
//                    jsonObject[0] =response;
//                    PrescribeMedicineActivity prescribeMedicineActivity
//                    Toast.makeText(getApplicationContext(), "Prescribed Medicine",
//                            Toast.LENGTH_SHORT).show();
//                    finish();
//                }
//
//                @Override
//                public void onError(ANError anError) {
//                    Log.e("error", anError.toString());
//                }
//            });
//            return jsonObject[0];
//        }
//    }

}

