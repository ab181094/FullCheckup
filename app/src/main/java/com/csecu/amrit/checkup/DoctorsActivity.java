package com.csecu.amrit.checkup;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
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

public class DoctorsActivity extends AppCompatActivity {

    ListView listView;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        context = this;

        listView = (ListView) findViewById(R.id.listview);

        if (isNetworkAvailable()) {
            Data data = new Data();
            data.execute();
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Doctors doctor = (Doctors) parent.getAdapter().getItem(position);
                Intent intent = new Intent(DoctorsActivity.this, DoctorDetailsActivity.class);
                intent.putExtra("id", doctor.getId());
                intent.putExtra("nam", doctor.getName());
                intent.putExtra("con", doctor.getMobile());
                intent.putExtra("dep", doctor.getDepartment());
                intent.putExtra("qua", doctor.getQualification());
                intent.putExtra("chm", doctor.getChamber());
                intent.putExtra("date", doctor.getDates());
                intent.putExtra("startTime", doctor.getStartTime());
                intent.putExtra("endTime", doctor.getEndTime());
                intent.putExtra("fee", doctor.getFee());
                startActivity(intent);
            }
        });
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
            showList(arrayList);
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
                    doctor.setPassword("");

                    list.add(doctor);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                // toastIt("Error in parsing. Please try again");
            }
            return list;
        }

        private void showList(ArrayList<Doctors> arrayList) {
            if (arrayList != null && arrayList.size() > 0) {
                //CustomizedAdapter adapter = new CustomizedAdapter(this, arrayList);
                //listview.setAdapter(adapter);
                listView.setAdapter(new MyCustomAdapter(context, arrayList));
            } else if (arrayList.size() == 0) {
                toastIt("No data found");
            }
        }
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
}
