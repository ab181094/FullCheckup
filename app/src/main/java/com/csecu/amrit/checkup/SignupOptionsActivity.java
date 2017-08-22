package com.csecu.amrit.checkup;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

public class SignupOptionsActivity extends AppCompatActivity {

    GridView optionsGV;
    ArrayList<Option> options;

    int img[] = {R.drawable.doctor_icon, R.drawable.diagonostic_icon};

    OptionItemGridAdapter gridAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_options);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        optionsGV = (GridView) findViewById(R.id.gridView);

        options = getOptions();
        gridAdapter = new OptionItemGridAdapter(getApplicationContext(), options);
        optionsGV.setAdapter(gridAdapter);

        optionsGV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    Intent intent = new Intent(SignupOptionsActivity.this, DoctorSignupActivity.class);
                    startActivity(intent);
                } else if (i == 1) {

                }
            }
        });
    }

    private ArrayList<Option> getOptions() {

        Resources resources = getResources();
        String optionNames[] = resources.getStringArray(R.array.options);

        ArrayList<Option> optionList = new ArrayList<Option>();

        for (int i = 0; i < 2; i++) {
            optionList.add(new Option(optionNames[i], img[i]));
        }
        return optionList;
    }
}
