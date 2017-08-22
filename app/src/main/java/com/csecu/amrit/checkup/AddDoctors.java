package com.csecu.amrit.checkup;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Amrit on 12-07-2017.
 */

public class AddDoctors extends AsyncTask<String, Void, String> {
    String insert_url = "http://10.2.3.100/checkup/insert.php";
    String successString = "Congrats! Process completed.";
    String errorString = "Sorry! Failed.";
    Context context;

    public AddDoctors(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... strings) {
        String type = strings[0];
        if (type.equals("add")) {
            String nam = strings[1];
            String con = strings[2];
            String chm = strings[3];
            String dep = strings[4];
            String qua = strings[5];
            String day = strings[6];
            String start = strings[7];
            String end = strings[8];
            String fee = strings[9];
            String pass = strings[10];

            try {
                URL url = new URL(insert_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter
                        (new OutputStreamWriter(outputStream, "UTF-8"));

                String data = URLEncoder.encode("nam", "UTF-8") + "=" + URLEncoder.encode(nam, "UTF-8") + "&" +
                        URLEncoder.encode("con", "UTF-8") + "=" + URLEncoder.encode(con, "UTF-8") + "&" +
                        URLEncoder.encode("chm", "UTF-8") + "=" + URLEncoder.encode(chm, "UTF-8") + "&" +
                        URLEncoder.encode("dep", "UTF-8") + "=" + URLEncoder.encode(dep, "UTF-8") + "&" +
                        URLEncoder.encode("qua", "UTF-8") + "=" + URLEncoder.encode(qua, "UTF-8") + "&" +
                        URLEncoder.encode("day", "UTF-8") + "=" + URLEncoder.encode(day, "UTF-8") + "&" +
                        URLEncoder.encode("start", "UTF-8") + "=" + URLEncoder.encode(start, "UTF-8") + "&" +
                        URLEncoder.encode("end", "UTF-8") + "=" + URLEncoder.encode(end, "UTF-8") + "&" +
                        URLEncoder.encode("fee", "UTF-8") + "=" + URLEncoder.encode(fee, "UTF-8") + "&" +
                        URLEncoder.encode("pass", "UTF-8") + "=" + URLEncoder.encode(pass, "UTF-8");

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                inputStream.close();
                httpURLConnection.disconnect();
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return errorString;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return errorString;
            } catch (ProtocolException e) {
                e.printStackTrace();
                return errorString;
            } catch (IOException e) {
                e.printStackTrace();
                return errorString;
            }
        } else if (type.equals("addPatient")) {
            String nam = strings[1];
            String con = strings[2];
            String age = strings[3];
            String sex = strings[4];
            String id = strings[5];
            String token = strings[6];

            String insert_url = "http://10.2.3.100/checkup/appointment.php";

            try {
                URL url = new URL(insert_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter
                        (new OutputStreamWriter(outputStream, "UTF-8"));

                String data = URLEncoder.encode("nam", "UTF-8") + "=" + URLEncoder.encode(nam, "UTF-8") + "&" +
                        URLEncoder.encode("con", "UTF-8") + "=" + URLEncoder.encode(con, "UTF-8") + "&" +
                        URLEncoder.encode("age", "UTF-8") + "=" + URLEncoder.encode(age, "UTF-8") + "&" +
                        URLEncoder.encode("sex", "UTF-8") + "=" + URLEncoder.encode(sex, "UTF-8") + "&" +
                        URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8") + "&" +
                        URLEncoder.encode("token", "UTF-8") + "=" + URLEncoder.encode(token, "UTF-8");

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                inputStream.close();
                httpURLConnection.disconnect();
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return errorString;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return errorString;
            } catch (ProtocolException e) {
                e.printStackTrace();
                return errorString;
            } catch (IOException e) {
                e.printStackTrace();
                return errorString;
            }
        } else if (type.equals("notify")) {
            String nam = strings[1];
            String id = strings[2];
            String mes = strings[3];

            String insert_url = "http://10.2.3.100/checkup/send.php";

            try {
                URL url = new URL(insert_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter
                        (new OutputStreamWriter(outputStream, "UTF-8"));

                String data = URLEncoder.encode("nam", "UTF-8") + "=" + URLEncoder.encode(nam, "UTF-8") + "&" +
                        URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8") + "&" +
                        URLEncoder.encode("mes", "UTF-8") + "=" + URLEncoder.encode(mes, "UTF-8");

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                inputStream.close();
                httpURLConnection.disconnect();
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return errorString;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return errorString;
            } catch (ProtocolException e) {
                e.printStackTrace();
                return errorString;
            } catch (IOException e) {
                e.printStackTrace();
                return errorString;
            }
        }
        return successString;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        toastIt(s);
    }

    private void toastIt(String s) {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }
}
