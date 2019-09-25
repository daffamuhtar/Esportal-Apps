package com.majinor.esportal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.majinor.esportal.MyEventTourFragment.EXTRA_IDTOUR;
import static com.majinor.esportal.Server.URL;

public class MyEventTourRegistedJadwal2Activity extends AppCompatActivity {

    TextView team1,team2,team3,team4,team5,team6,team7,team8,team9,team10,team11,team12,team13,team14,team15
            ,team16,team17,team18,team19,team20,team21,team22,team23,team24,team25;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_event_tour_registed_jadwal2);

        Intent intents=getIntent();
        final int idtour = intents.getIntExtra(MyEventTourFragment.EXTRA_IDTOUR,0);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Jadwal");

        Intent intent=getIntent();
        String namatour=intent.getStringExtra(MyEventTourFragment.EXTRA_NAMATOUR);

        getData();

        team1=findViewById(R.id.tv_metrj2_t1);
        team2=findViewById(R.id.tv_metrj2_t2);
        team3=findViewById(R.id.tv_metrj2_t3);
        team4=findViewById(R.id.tv_metrj2_t4);
        team5=findViewById(R.id.tv_metrj2_t5);
        team6=findViewById(R.id.tv_metrj2_t6);
        team7=findViewById(R.id.tv_metrj2_t7);
        team8=findViewById(R.id.tv_metrj2_t8);
        team9=findViewById(R.id.tv_metrj2_t9);
        team10=findViewById(R.id.tv_metrj2_t10);
        team11=findViewById(R.id.tv_metrj2_t11);
        team12=findViewById(R.id.tv_metrj2_t12);
        team13=findViewById(R.id.tv_metrj2_t13);
        team14=findViewById(R.id.tv_metrj2_t14);
        team15=findViewById(R.id.tv_metrj2_t15);
        team16=findViewById(R.id.tv_metrj2_t16);
        team17=findViewById(R.id.tv_metrj2_t17);
        team18=findViewById(R.id.tv_metrj2_t18);
        team19=findViewById(R.id.tv_metrj2_t19);
        team20=findViewById(R.id.tv_metrj2_t20);
        team21=findViewById(R.id.tv_metrj2_t21);
        team22=findViewById(R.id.tv_metrj2_t22);
        team23=findViewById(R.id.tv_metrj2_t23);
        team24=findViewById(R.id.tv_metrj2_t24);
        team25=findViewById(R.id.tv_metrj2_t25);

    }

    void getData(){
// Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        Intent intent=getIntent();
        int idtour=intent.getIntExtra(MyEventTourFragment.EXTRA_IDTOUR,0);
        String namatours=intent.getStringExtra(MyEventTourFragment.EXTRA_NAMATOUR);
        String url = URL+"showjadwaltour.php?idtour="+idtour;

        final JSONObject jsonBody = new JSONObject();
        final String requestBody = jsonBody.toString();

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
//menaruh data JSON kkedalam variabel JSON Object
                            JSONObject jsonPost = new JSONObject(response.toString());

//men set data ke dalam tampilan
//                            int idbagan = jsonPost.getInt("idBagan");
//                            int idtour = jsonPost.getInt("idTurnamen");
//                            String namatour= jsonPost.getString("namaTurnamen");

                            team1.setText(jsonPost.getString("team1"));
                            team2.setText(jsonPost.getString("team2"));
                            team3.setText(jsonPost.getString("team3"));
                            team4.setText(jsonPost.getString("team4"));
                            team5.setText(jsonPost.getString("team5"));
                            team6.setText(jsonPost.getString("team6"));
                            team7.setText(jsonPost.getString("team7"));
                            team8.setText(jsonPost.getString("team8"));
                            team9.setText(jsonPost.getString("team9"));
                            team10.setText(jsonPost.getString("team10"));
                            team11.setText(jsonPost.getString("team11"));
                            team12.setText(jsonPost.getString("team12"));
                            team13.setText(jsonPost.getString("team13"));
                            team14.setText(jsonPost.getString("team14"));
                            team15.setText(jsonPost.getString("team15"));
                            team16.setText(jsonPost.getString("team16"));
                            team17.setText(jsonPost.getString("team17"));
                            team18.setText(jsonPost.getString("team18"));
                            team19.setText(jsonPost.getString("team19"));
                            team20.setText(jsonPost.getString("team20"));
                            team21.setText(jsonPost.getString("team21"));
                            team22.setText(jsonPost.getString("team22"));
                            team23.setText(jsonPost.getString("team23"));
                            team24.setText(jsonPost.getString("team24"));
                            team25.setText(jsonPost.getString("team25"));


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error Response",error.toString());
            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);

    }
}
