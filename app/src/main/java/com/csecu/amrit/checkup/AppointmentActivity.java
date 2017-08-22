package com.csecu.amrit.checkup;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AppointmentActivity extends AppCompatActivity {

    EditText etname, etcontact, etage;
    Spinner spsex;
    String id, token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final SharedPreferences sharedpreferences = getApplicationContext().
                getSharedPreferences(getString(R.string.CHOICE), Context.MODE_PRIVATE);
        token = sharedpreferences.getString(getString(R.string.FCM_TOKEN), "");

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            id = bundle.getString("id");
        }

        initializeAll();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etname.getText().toString();
                name = name.trim();

                String contact = etcontact.getText().toString();
                contact = contact.trim();

                String age = etage.getText().toString();
                age = age.trim();

                String sex = spsex.getSelectedItem().toString();

                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(contact) || TextUtils.isEmpty(age)) {
                    if (TextUtils.isEmpty(name)) {
                        etname.setError("This field can't be empty.");
                    } else if (TextUtils.isEmpty(contact)) {
                        etcontact.setError("This field can't be empty.");
                    } else if (TextUtils.isEmpty(age)) {
                        etage.setError("This field can't be empty.");
                    }
                }  else {
                    if (isNetworkAvailable()) {
                        etname.setText("");
                        etcontact.setText("");
                        etage.setText("");

                        String type = "addPatient";
                        AddDoctors addDoctors = new AddDoctors(getApplicationContext());
                        addDoctors.execute(type, name, contact, age, sex, id, token);
                    } else {
                        toastIt("Data Not Saved. Please check your Internet Connection.");
                    }
                }
            }
        });
    }

    private void initializeAll() {
        etname = (EditText) findViewById(R.id.etappname);
        etcontact = (EditText) findViewById(R.id.etappcontact);
        etage = (EditText) findViewById(R.id.etappage);

        spsex = (Spinner) findViewById(R.id.spsex);
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
