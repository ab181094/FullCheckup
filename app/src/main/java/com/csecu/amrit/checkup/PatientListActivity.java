package com.csecu.amrit.checkup;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class PatientListActivity extends AppCompatActivity {

    Button btpostpone;
    String id = "-1", name;
    ListView listView;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        context = this;

        listView = (ListView) findViewById(R.id.listview);
        btpostpone = (Button) findViewById(R.id.btpostpone);

        SharedPreferences sharedpreferences = getApplicationContext().
                getSharedPreferences(getString(R.string.CHOICE), Context.MODE_PRIVATE);
        name = sharedpreferences.getString(getString(R.string.NAME), "");

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            id = bundle.getString("id");
        } else {
            id = sharedpreferences.getString(getString(R.string.ID), "");
        }

        if (isNetworkAvailable()) {
            Data data = new Data();
            data.execute(id);
        }

        btpostpone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PatientListActivity.this, MessageActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("name", name);
                startActivity(intent);
            }
        });
    }

    class Data extends AsyncTask<String, Void, String> {
        String JSON_STRING = null;
        String getUrl = "http://10.2.3.100/checkup/patient.php";

        @Override
        protected String doInBackground(String... params) {
            String id = params[0];
            try {
                URL url = new URL(getUrl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter
                        (new OutputStreamWriter(outputStream, "UTF-8"));

                String data = URLEncoder.encode("key", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

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

            ArrayList<Patient> arrayList = parseAll(s);
            showList(arrayList);
        }

        private ArrayList<Patient> parseAll(String s) {
            String result = s;
            JSONObject jsonObject;
            JSONArray jsonArray;
            ArrayList<Patient> list = new ArrayList<Patient>();
            try {
                jsonObject = new JSONObject(result);
                jsonArray = jsonObject.getJSONArray("server_response");
                for (int count = 0; count < jsonArray.length(); count++) {
                    JSONObject object = jsonArray.getJSONObject(count);
                    int idInt = object.getInt("id");
                    String id = String.valueOf(idInt);
                    String name = object.getString("name");
                    String contact = object.getString("contact");
                    String age = object.getString("age");
                    String gender = object.getString("sex");

                    Patient patient = new Patient();
                    patient.setId(id);
                    patient.setName(name);
                    patient.setContact(contact);
                    patient.setAge(age);
                    patient.setGender(gender);
                    list.add(patient);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                // toastIt("Error in parsing. Please try again");
            }
            return list;
        }

        private void showList(ArrayList<Patient> arrayList) {
            if (arrayList != null && arrayList.size() > 0) {
                //CustomizedAdapter adapter = new CustomizedAdapter(this, arrayList);
                //listview.setAdapter(adapter);
                listView.setAdapter(new PatientCustomAdapter(context, arrayList));
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
