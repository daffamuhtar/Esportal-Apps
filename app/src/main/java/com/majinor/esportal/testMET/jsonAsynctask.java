package com.majinor.esportal.testMET;

import android.os.AsyncTask;

import com.google.gson.Gson;

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
import java.util.LinkedHashSet;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class jsonAsynctask extends AsyncTask<Void, Void, Void> {

    JSONObject deviceArray;
    JSONArray json2;

    String username = "xxx";
    String password = "xxx";
    String credentials = username + ":" + password;
    String xxxURL = "https://" + credentials + "@xxx.com/fetch.php?";

    String basicAuth, line, json_string, json, device;

    String data = "";

    List<String> asList;

    LinkedHashSet<String> uniqueStrings = new LinkedHashSet<String>();

    List<String> allDevice = new ArrayList<String>();

    Gson gson;

    HttpURLConnection connection;
    BufferedReader bufferedReader;

    URL url;

    private DataTabelFragment dataTabelFragment;

    public jsonAsynctask(jsonAsynctask.DataTabelFragment dataTabelFragment) {

        this.dataTabelFragment = dataTabelFragment;

    }

    public void inBackground() {

        try {

            url = new URL(xxxURL);

            connection = (HttpsURLConnection) url.openConnection();

            basicAuth = "Basic " + new String(encodeBase64URLSafeString(credentials.getBytes()));

            connection.setRequestProperty("Authorization", basicAuth);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Language", "en-US");
            connection.setUseCaches(true);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.connect();

            InputStream stream = connection.getInputStream();

            bufferedReader = new BufferedReader(new InputStreamReader(stream));

            line = "";

            while (line != null) {
                line = bufferedReader.readLine();
                data = data + line;
            }

            json2 = new JSONArray(data);

            for (int i = 0; i < json2.length(); i++) {

                deviceArray = json2.getJSONObject(i);

                device = deviceArray.getString("device");

                uniqueStrings.add(device);

                allDevice.add(device);

            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static String encodeBase64URLSafeString(byte[] binaryData) {

        return android.util.Base64.encodeToString(binaryData, android.util.Base64.URL_SAFE);

    }

    @Override
    protected Void doInBackground(Void... voids) {

        inBackground();

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        dataTabelFragment.onJobFinishListener(allDevice);

        gson = new Gson();

        json = gson.toJson(data);

        json_string = data;

    }

    public interface DataTabelFragment {

        void onJobFinishListener(List<String> allId);

    }
}
