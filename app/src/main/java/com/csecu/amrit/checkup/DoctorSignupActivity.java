package com.csecu.amrit.checkup;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;

public class DoctorSignupActivity extends AppCompatActivity {

    EditText etname, etmobile, etchamber, etdepartment, etqualification, etfee, etpassword;
    ToggleButton tbsunday, tbmonday, tbtuesday, tbwednesday, tbthursday, tbfriday, tbsaturday;
    ArrayList<String> daysList = new ArrayList<>();
    Spinner spinnerhours1, spinnerhours2, spinnerminute1, spinnerminute2,
            spinnerperiod1, spinnerperiod2;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_signup);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.context = this;

        initializeAll();
        addItemtoSpinner();

        tbsunday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    daysList.add("Sunday");
                } else {
                    daysList.remove("Sunday");
                }
            }
        });

        tbmonday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    daysList.add("Monday");
                } else {
                    daysList.remove("Monday");
                }
            }
        });

        tbtuesday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    daysList.add("Tuesday");
                } else {
                    daysList.remove("Tuesday");
                }
            }
        });

        tbwednesday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    daysList.add("Wednesday");
                } else {
                    daysList.remove("Wednesday");
                }
            }
        });

        tbthursday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    daysList.add("Thursday");
                } else {
                    daysList.remove("Thursday");
                }
            }
        });

        tbfriday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    daysList.add("Friday");
                } else {
                    daysList.remove("Friday");
                }
            }
        });

        tbsaturday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    daysList.add("Saturday");
                } else {
                    daysList.remove("Saturday");
                }
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etname.getText().toString();
                name = name.trim();

                String mobile = etmobile.getText().toString();
                mobile = mobile.trim();

                String chamber = etchamber.getText().toString();
                chamber = chamber.trim();

                String department = etdepartment.getText().toString();
                department = department.trim();

                String qualification = etqualification.getText().toString();
                qualification = qualification.trim();

                String fee = etfee.getText().toString();
                fee = fee.trim();

                String password = etpassword.getText().toString();
                password = password.trim();

                String startTime = spinnerhours1.getSelectedItem() + ":" +
                        spinnerminute1.getSelectedItem() + " " + spinnerperiod1.getSelectedItem();

                String endTime = spinnerhours2.getSelectedItem() + ":" +
                        spinnerminute2.getSelectedItem() + " " + spinnerperiod2.getSelectedItem();

                String visitingDates = "";

                for (int i = 0; i < daysList.size(); i++) {
                    visitingDates = visitingDates + daysList.get(i) + " ";
                }

                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(mobile) || TextUtils.isEmpty(chamber) ||
                        TextUtils.isEmpty(department) || TextUtils.isEmpty(qualification) ||
                        TextUtils.isEmpty(visitingDates) || TextUtils.isEmpty(startTime) ||
                        TextUtils.isEmpty(endTime) || TextUtils.isEmpty(fee) || TextUtils.isEmpty(password)) {
                    if (TextUtils.isEmpty(name)) {
                        etname.setError("This field can't be empty.");
                    } else if (TextUtils.isEmpty(mobile)) {
                        etmobile.setError("This field can't be empty.");
                    } else if (TextUtils.isEmpty(chamber)) {
                        etchamber.setError("This field can't be empty.");
                    } else if (TextUtils.isEmpty(department)) {
                        etdepartment.setError("This field can't be empty.");
                    } else if (TextUtils.isEmpty(qualification)) {
                        etqualification.setError("This field can't be empty.");
                    } else if (TextUtils.isEmpty(visitingDates)) {
                        toastIt("You haven't selected any dates");
                    } else if (TextUtils.isEmpty(startTime)) {
                        toastIt("You haven't selected starting time");
                    } else if (TextUtils.isEmpty(endTime)) {
                        toastIt("You haven't selected ending time");
                    } else if (TextUtils.isEmpty(fee)) {
                        etfee.setError("This field can't be empty.");
                    } else if (TextUtils.isEmpty(password)) {
                        etpassword.setError("This field can't be empty.");
                    }
                } else {
                    Doctors doctors = new Doctors();
                    doctors.setName(name);
                    doctors.setMobile(mobile);
                    doctors.setChamber(chamber);
                    doctors.setDepartment(department);
                    doctors.setQualification(qualification);
                    doctors.setDates(visitingDates);
                    doctors.setStartTime(startTime);
                    doctors.setEndTime(endTime);
                    doctors.setFee(fee);
                    doctors.setPassword(password);

                    if (isNetworkAvailable()) {
                        etname.setText("");
                        etmobile.setText("");
                        etchamber.setText("");
                        etdepartment.setText("");
                        etqualification.setText("");
                        etfee.setText("");
                        etpassword.setText("");


                        String type = "add";
                        AddDoctors addDoctors = new AddDoctors(getApplicationContext());
                        addDoctors.execute(type, name, mobile, chamber, department, qualification
                                , visitingDates, startTime, endTime, fee, password);
                    } else {
                        toastIt("Data Not Saved. Please check your Internet Connection.");
                    }
                }
            }
        });
    }

    private void addItemtoSpinner() {
        ArrayList<String> hourList = new ArrayList<String>();
        for (int i = 1; i < 13; i++) {
            String number = String.valueOf(i);
            hourList.add(number);
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, hourList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerhours1.setAdapter(dataAdapter);
        spinnerhours2.setAdapter(dataAdapter);

        ArrayList<String> minuteList = new ArrayList<String>();
        for (int i = 0; i < 60; i++) {
            String number = null;
            if (i < 10) {
                number = "0"+String.valueOf(i);
            } else {
                number = String.valueOf(i);
            }
            minuteList.add(number);
        }
        dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, minuteList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerminute1.setAdapter(dataAdapter);
        spinnerminute2.setAdapter(dataAdapter);

        ArrayList<String> periodList = new ArrayList<String>();
        periodList.add(0, "AM");
        periodList.add(1, "PM");
        dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, periodList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerperiod1.setAdapter(dataAdapter);
        spinnerperiod2.setAdapter(dataAdapter);
    }

    private void toastIt(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    private void initializeAll() {
        etname = (EditText) findViewById(R.id.etname);
        etmobile = (EditText) findViewById(R.id.etmobile);
        etchamber = (EditText) findViewById(R.id.etchamber);
        etdepartment = (EditText) findViewById(R.id.etdepartment);
        etqualification = (EditText) findViewById(R.id.etqualification);
        etfee = (EditText) findViewById(R.id.etfee);
        etpassword = (EditText) findViewById(R.id.etpassword);

        tbsunday = (ToggleButton) findViewById(R.id.tbsunday);
        tbmonday = (ToggleButton) findViewById(R.id.tbmonday);
        tbtuesday = (ToggleButton) findViewById(R.id.tbtuesday);
        tbwednesday = (ToggleButton) findViewById(R.id.tbwednesday);
        tbthursday = (ToggleButton) findViewById(R.id.tbthursday);
        tbfriday = (ToggleButton) findViewById(R.id.tbfriday);
        tbsaturday = (ToggleButton) findViewById(R.id.tbsaturday);

        spinnerhours1 = (Spinner) findViewById(R.id.spinnerhours1);
        spinnerhours2 = (Spinner) findViewById(R.id.spinnerhours2);
        spinnerminute1 = (Spinner) findViewById(R.id.spinnerminute1);
        spinnerminute2 = (Spinner) findViewById(R.id.spinnerminute2);
        spinnerperiod1 = (Spinner) findViewById(R.id.spinnerperiod1);
        spinnerperiod2 = (Spinner) findViewById(R.id.spinnerperiod2);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
