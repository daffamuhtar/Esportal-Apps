package com.majinor.esportal.testMET;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.android.volley.RequestQueue;
import com.majinor.esportal.LoginActivity;
import com.majinor.esportal.Server;
import com.majinor.esportal.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class Sendrequest extends Fragment {
    private RequestQueue requestQueue;
    ArrayList<HashMap<String, String>> UserList;
    String id,username;
    private static final String TAG_ID = "id";
    private static final String TAG_USERNAME = "username";
//    private static final String TAG_NAME = "name";
//    private static final String TAG_PHONE = "phone";
//    private static final String TAG_PROFILE = "profile";

    public static final String EXTRA_FOTO = "foto";
    public static final String EXTRA_ID = "id";
    public static final String EXTRA_NAMA = "nama";
    public static final String EXTRA_DESK = "desk";
    public static final String EXTRA_JENIS = "jenis";
    public static final String EXTRA_SMAX = "smax";
    public static final String EXTRA_SISI = "sisi";
    public static final String EXTRA_GAME = "game";
    public static final String EXTRA_PANITIA = "panitia";
    public static final String EXTRA_BIAYA = "biaya";
    public static final String EXTRA_HADIAH = "hadiah";
    public static final String EXTRA_TGLDAFTAR = "tgldaftar";
    public static final String EXTRA_TGLTM = "tgltm";
    public static final String EXTRA_TGLTOUR = "tgltour";
    public static final String EXTRA_TEMPAT = "tempat";
    public static final String EXTRA_NOHP = "nohp";
    public static final String EXTRA_USERNAME = "username";

    ListView listView;
    public Sendrequest() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_my_event_tour, container, false);
        listView=(ListView)v.findViewById(R.id.rv_met);
        new GetStudents().execute();

        SharedPreferences sharedpreferences = this.getActivity().getSharedPreferences(LoginActivity.my_shared_preferences, Context.MODE_PRIVATE);
        //get to uset/edit
        final SharedPreferences sharedpreferencesOut = this.getActivity().getSharedPreferences(LoginActivity.my_shared_preferences, Context.MODE_PRIVATE);

        id= sharedpreferences.getString(TAG_ID,"null");
        username= sharedpreferences.getString(TAG_USERNAME,"null");


        return v;
    }

    private class GetStudents extends AsyncTask<Void, Void, Void> {

        // Hashmap for ListView

        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            GetAllUsers webreq = new GetAllUsers();

            String url = Server.URL+"showmytour.php?username="+username;

            // Making a request to url and getting response
            String jsonStr = webreq.makeWebServiceCall(url, GetAllUsers.GET);

            Log.d("Response: ", "> " + jsonStr);

            UserList = ParseJSON(jsonStr);

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            /**
             * Updating parsed JSON data into ListView
             li * */

            ListAdapter adapter = new SimpleAdapter(
                    getActivity(), UserList,
                    R.layout.item_myeventtour,
                    new String[]{EXTRA_NAMA, EXTRA_JENIS},
                    new int[]{R.id.tv_met_nama,R.id.tv_met_jenis});
            listView.setAdapter(adapter);
        }

    }

    private ArrayList<HashMap<String, String>> ParseJSON(String json) {
        if (json != null) {
            try {
                // Hashmap for ListView
                ArrayList<HashMap<String, String>> studentList = new ArrayList<HashMap<String, String>>();

                JSONObject jsonObj = new JSONObject(json);

                // Getting JSON Array node
                JSONArray userrs = jsonObj.getJSONArray(null);

                // looping through All Students
                for (int i = 0; i < userrs.length(); i++) {
                    JSONObject hit = userrs.getJSONObject(i);

//                    String id = c.getString(TAG_ID);
//                    String username = c.getString(TAG_USERNAME);
//                    String name = c.getString(TAG_NAME);
//                    String phone = c.getString(TAG_PHONE);
//                    String profile = c.getString(TAG_PROFILE)
                    String imageUrl = hit.getString("foto");
                    String namaTour = hit.getString("nama");
                    String deskTour = hit.getString("desk");
                    String jenisTour = hit.getString("jenis");
                    String game = hit.getString("game");
                    String panitia = hit.getString("panitia");
                    String tglDaftar = hit.getString("tgldaftar");
                    String tglTM = hit.getString("tgltm");
                    String tgltour = hit.getString("tgltour");
                    String tempat = hit.getString("tempat");
                    int noHP = hit.getInt("nohp");
                    int idTour = hit.getInt("id");
                    int biaya = hit.getInt("biaya");
                    int hadiah = hit.getInt("hadiah");
                    int slotMax = hit.getInt("smax");
                    int slotIsi = hit.getInt("sisi");



                    // tmp hashmap for single student
                    HashMap<String, String> user = new HashMap<String, String>();

                    // adding each child node to HashMap key => value
                    user.put(EXTRA_NAMA, namaTour);
                    user.put(EXTRA_TGLTOUR, tgltour);
//                    user.put(EXTRA_JENIS, jenisTour);
//                    user.put(EXTRA_TEMPAT, tempat);
                   // user.put(EXTRA_FOTO,imageUrl);


                    // adding student to students list
                    studentList.add(user);
                }
                return studentList;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            Log.e("GetCatalog", "Couldn't get any data from the url");
            return null;
        }
    }



}
