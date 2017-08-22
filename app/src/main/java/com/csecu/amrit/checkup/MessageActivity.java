package com.csecu.amrit.checkup;

import android.content.Context;
import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

public class MessageActivity extends AppCompatActivity {

    String id = "-1", name;
    Context context;
    EditText etmessage;
    TextView tvdoctor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        context = this;

        initializeAll();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            id = bundle.getString("id");
            name = bundle.getString("name");
        }

        tvdoctor.setText("From: " + name);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = etmessage.getText().toString();
                message = message.trim();

                if (message.length() > 500) {
                    toastIt("The message is too long!");
                } else if (TextUtils.isEmpty(message)) {
                    etmessage.setError("This field can't be empty.");
                } else {
                    if (isNetworkAvailable()) {
                        name = "From: " + name;
                        etmessage.setText("");
                        String type = "notify";
                        AddDoctors addDoctors = new AddDoctors(getApplicationContext());
                        addDoctors.execute(type, name, id, message);
                    }
                }
            }
        });
    }

    private void initializeAll() {
        etmessage = (EditText) findViewById(R.id.etmessage);
        tvdoctor = (TextView) findViewById(R.id.tvdoctor);
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
