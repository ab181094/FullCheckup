package com.csecu.amrit.checkup;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

public class DoctorMenuActivity extends AppCompatActivity {
    GridView optionsGV;
    ArrayList<Option> options;

    int img[] = {R.drawable.doctor_icon, R.drawable.diagonostic_icon, R.drawable.map_icon,
            R.drawable.patient_icon, R.drawable.update_icon, R.drawable.logout_icon};

    OptionItemGridAdapter gridAdapter;

    String id, name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            id = bundle.getString("id");
            name = bundle.getString("name");
            SharedPreferences sharedpreferences = getApplicationContext().
                    getSharedPreferences(getString(R.string.CHOICE), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();

            editor.putString(getString(R.string.ID), id);
            editor.putString(getString(R.string.NAME), name);
            editor.commit();
        } else {
            SharedPreferences sharedpreferences = getApplicationContext().
                    getSharedPreferences(getString(R.string.CHOICE), Context.MODE_PRIVATE);
            id = sharedpreferences.getString(getString(R.string.ID), "");
        }

        optionsGV = (GridView) findViewById(R.id.gridView);

        options = getOptions();
        gridAdapter = new OptionItemGridAdapter(getApplicationContext(), options);
        optionsGV.setAdapter(gridAdapter);

        optionsGV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    Intent intent = new Intent(DoctorMenuActivity.this, DoctorsActivity.class);
                    startActivity(intent);
                } else if (i == 1) {

                } else if (i == 2) {

                } else if (i == 3) {
                    Intent intent = new Intent(DoctorMenuActivity.this, PatientListActivity.class);
                    intent.putExtra("id", id);
                    startActivity(intent);
                } else if (i == 4) {
                    Intent intent = new Intent(DoctorMenuActivity.this, SignupOptionsActivity.class);
                    startActivity(intent);
                } else if (i == 5) {
                    confirmDialog();
                }
            }
        });
    }

    private void logout() {
        Intent intent = new Intent(DoctorMenuActivity.this, GeneralMenuActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void deleteSession() {
        SharedPreferences sharedpreferences = getApplicationContext().
                getSharedPreferences(getString(R.string.CHOICE), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.commit();
    }

    private ArrayList<Option> getOptions() {

        Resources resources = getResources();
        String optionNames[] = resources.getStringArray(R.array.doctoroptions);

        ArrayList<Option> optionList = new ArrayList<Option>();

        for (int i = 0; i < optionNames.length; i++) {
            optionList.add(new Option(optionNames[i], img[i]));
        }
        return optionList;
    }

    private void toastIt(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    private void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder
                .setMessage(getString(R.string.logout_message))
                .setTitle(getString(R.string.logout_title))
                .setIcon(R.drawable.icon)
                .setPositiveButton("Yes",  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        deleteSession();
                        logout();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                    }
                })
                .show();
    }
}
