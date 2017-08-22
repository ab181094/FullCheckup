package com.csecu.amrit.checkup;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

public class GeneralMenuActivity extends AppCompatActivity {

    GridView optionsGV;
    ArrayList<Option> options;

    int img[] = {R.drawable.doctor_icon, R.drawable.diagonostic_icon, R.drawable.map_icon,
            R.drawable.login_icon, R.drawable.registration_icon};

    OptionItemGridAdapter gridAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        optionsGV = (GridView) findViewById(R.id.gridView);

        options = getOptions();
        gridAdapter = new OptionItemGridAdapter(getApplicationContext(), options);
        optionsGV.setAdapter(gridAdapter);

        optionsGV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    Intent intent = new Intent(GeneralMenuActivity.this, DoctorsActivity.class);
                    startActivity(intent);
                } else if (i == 1) {

                } else if (i == 2) {

                } else if (i == 3) {
                    Intent intent = new Intent(GeneralMenuActivity.this, DoctorSigninActivity.class);
                    startActivity(intent);
                } else if (i == 4) {
                    Intent intent = new Intent(GeneralMenuActivity.this, SignupOptionsActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void toastIt(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    private ArrayList<Option> getOptions() {

        Resources resources = getResources();
        String optionNames[] = resources.getStringArray(R.array.options);

        ArrayList<Option> optionList = new ArrayList<Option>();

        for (int i = 0; i < optionNames.length; i++) {
            optionList.add(new Option(optionNames[i], img[i]));
        }
        return optionList;
    }
}
