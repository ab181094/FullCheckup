package com.csecu.amrit.checkup;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DoctorDetailsActivity extends AppCompatActivity {

    TextView tvname, tvcontact, tvchamber, tvdepartment, tvqualifications, tvdates, tvtime, tvfee;
    String name, contact, chamber, department, qualifications, dates, startTime, endTime, fee, id, time;
    Button btappointment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            id = bundle.getString("id");
            name = bundle.getString("nam");
            contact = bundle.getString("con");
            department = bundle.getString("dep");
            qualifications = bundle.getString("qua");
            chamber = bundle.getString("chm");
            dates = bundle.getString("date");
            startTime = bundle.getString("startTime");
            endTime = bundle.getString("endTime");
            fee = bundle.getString("fee");

            SharedPreferences sharedpreferences = getApplicationContext().
                    getSharedPreferences(getString(R.string.CHOICE), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(getString(R.string.tempid), id);
            editor.putString(getString(R.string.tempname), name);
            editor.putString(getString(R.string.tempcontact), contact);
            editor.putString(getString(R.string.tempdepartment), department);
            editor.putString(getString(R.string.tempqualifications), qualifications);
            editor.putString(getString(R.string.tempchamber), chamber);
            editor.putString(getString(R.string.tempdates), dates);
            editor.putString(getString(R.string.startTime), startTime);
            editor.putString(getString(R.string.tempendTime), endTime);
            editor.putString(getString(R.string.tempfee), fee);
            editor.commit();
        } else {
            SharedPreferences sharedpreferences = getApplicationContext().
                    getSharedPreferences(getString(R.string.CHOICE), Context.MODE_PRIVATE);
            id = sharedpreferences.getString(getString(R.string.tempid), "");
            name = sharedpreferences.getString(getString(R.string.tempname), "");
            contact = sharedpreferences.getString(getString(R.string.tempcontact), "");
            department = sharedpreferences.getString(getString(R.string.tempdepartment), "");
            qualifications = sharedpreferences.getString(getString(R.string.tempqualifications), "");
            chamber = sharedpreferences.getString(getString(R.string.tempchamber), "");
            dates = sharedpreferences.getString(getString(R.string.tempdates), "");
            startTime = sharedpreferences.getString(getString(R.string.startTime), "");
            endTime = sharedpreferences.getString(getString(R.string.tempendTime), "");
            fee = sharedpreferences.getString(getString(R.string.tempfee), "");
        }

        time = startTime + " - " + endTime;
        fee = fee + " Taka";

        initializeAll();

        tvname.setText(name);
        tvcontact.setText(contact);
        tvchamber.setText(chamber);
        tvdepartment.setText(department);
        tvqualifications.setText(qualifications);
        tvdates.setText(dates);
        tvtime.setText(time);
        tvfee.setText(fee);

        btappointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DoctorDetailsActivity.this, AppointmentActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });
    }

    private void initializeAll() {
        tvname = (TextView) findViewById(R.id.tvdetailsname);
        tvcontact = (TextView) findViewById(R.id.tvdetailscontact);
        tvchamber = (TextView) findViewById(R.id.tvdetailschamber);
        tvdepartment = (TextView) findViewById(R.id.tvdetailsdepartment);
        tvqualifications = (TextView) findViewById(R.id.tvdetailsqualifications);
        tvdates = (TextView) findViewById(R.id.tvdetailsdates);
        tvtime = (TextView) findViewById(R.id.tvdetailstime);
        tvfee = (TextView) findViewById(R.id.tvdetailsfee);

        btappointment = (Button) findViewById(R.id.btappointment);
    }
}
