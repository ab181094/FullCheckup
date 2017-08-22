package com.csecu.amrit.checkup;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class DoctorSigninActivity extends AppCompatActivity {

    EditText etcontact, etpassword;
    Context context;
    String contact, password, id, name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_signin);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        context = this;

        initializeAll();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contact = etcontact.getText().toString();
                contact = contact.trim();

                password = etpassword.getText().toString();
                password = password.trim();

                if (TextUtils.isEmpty(contact) || TextUtils.isEmpty(password)) {
                    if (TextUtils.isEmpty(contact)) {
                        etcontact.setError("This field can't be empty.");
                    } else if (TextUtils.isEmpty(contact)) {
                        etcontact.setError("This field can't be empty.");
                    } else if (TextUtils.isEmpty(password)) {
                        etpassword.setError("This field can't be empty.");
                    }
                } else {
                    if (isNetworkAvailable()) {
                        etcontact.setText("");
                        etcontact.setText("");
                        etpassword.setText("");

                        SharedPreferences sharedpreferences = getApplicationContext().
                                getSharedPreferences(getString(R.string.CHOICE), Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedpreferences.edit();

                        editor.putString(getString(R.string.CONTACT), contact);
                        editor.putString(getString(R.string.PASSWORD), password);
                        editor.commit();

                        Data data = new Data();
                        data.execute();
                    } else {
                        toastIt("Data Not Saved. Please check your Internet Connection.");
                    }
                }
            }
        });
    }

    private void initializeAll() {
        etcontact = (EditText) findViewById(R.id.etlogincontact);
        etpassword = (EditText) findViewById(R.id.etloginpassword);
    }

    public void signUp(View view) {
        Intent intent = new Intent(DoctorSigninActivity.this, SignupOptionsActivity.class);
        startActivity(intent);
        finish();
    }

    private void toastIt(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    class Data extends AsyncTask<Void, Void, String> {
        String JSON_STRING = null;
        String getUrl = "http://10.2.3.100/checkup/select.php";

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL(getUrl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();

                while ((JSON_STRING = bufferedReader.readLine()) != null) {
                    stringBuilder.append(JSON_STRING + "\n");
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return "Sorry! Can't Connect Now. Please Try Again Later";
            } catch (IOException e) {
                e.printStackTrace();
                return "Sorry! Can't Connect Now. Please Try Again Later";
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            ArrayList<Doctors> arrayList = parseAll(s);
            checkData(arrayList);
        }
        private void checkData(ArrayList<Doctors> arrayList) {
            SharedPreferences sharedpreferences = getApplicationContext().
                    getSharedPreferences(getString(R.string.CHOICE), Context.MODE_PRIVATE);
            String key = sharedpreferences.getString(getString(R.string.CONTACT), "");
            String value = sharedpreferences.getString(getString(R.string.PASSWORD), "");
            int count = 0;
            for (int i = 0; i < arrayList.size(); i++) {
                Doctors doctors = arrayList.get(i);
                String contact = doctors.getMobile();
                String password = doctors.getPassword();

                if ((contact.equals(key)) && password.equals(value)) {
                    id = doctors.getId();
                    name = doctors.getName();
                    count++;
                }
            }

            if (count!=0) {
                toastIt("Sign-In Successful");
                sharedpreferences = getApplicationContext().
                        getSharedPreferences(getString(R.string.CHOICE), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();

                editor.putString(getString(R.string.STATE), "true");
                editor.commit();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(context, DoctorMenuActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.putExtra("id", id);
                        intent.putExtra("name", name);
                        startActivity(intent);
                        finish();
                    }
                }, 1000);
            } else {
                toastIt("Sign-In Failed");
            }
        }

        private ArrayList<Doctors> parseAll(String s) {
            String result = s;
            JSONObject jsonObject;
            JSONArray jsonArray;
            ArrayList<Doctors> list = new ArrayList<Doctors>();
            try {
                jsonObject = new JSONObject(result);
                jsonArray = jsonObject.getJSONArray("server_response");
                for (int count = 0; count < jsonArray.length(); count++) {
                    JSONObject object = jsonArray.getJSONObject(count);
                    int idInt = object.getInt("id");
                    String id = String.valueOf(idInt);
                    String name = object.getString("name");
                    String contact = object.getString("contact");
                    String chamber = object.getString("chamber");
                    String department = object.getString("department");
                    String qualifications = object.getString("qualifications");
                    String dates = object.getString("dates");
                    String startTime = object.getString("startTime");
                    String endTime = object.getString("endTime");
                    String fee = object.getString("fee");
                    String password = object.getString("pass");

                    Doctors doctor = new Doctors();
                    doctor.setId(id);
                    doctor.setName(name);
                    doctor.setMobile(contact);
                    doctor.setDepartment(department);
                    doctor.setQualification(qualifications);
                    doctor.setChamber(chamber);
                    doctor.setFee(fee);
                    doctor.setDates(dates);
                    doctor.setStartTime(startTime);
                    doctor.setEndTime(endTime);
                    doctor.setPassword(password);

                    list.add(doctor);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                // toastIt("Error in parsing. Please try again");
            }
            return list;
        }
    }
}


