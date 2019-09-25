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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import static com.majinor.esportal.Server.URL;

public class MyEventTourRegistedHasilActivity extends AppCompatActivity {

    TextView namatour,juara1,juara2,juara3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_event_tour_registed_hasil);


         getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Hasil");

        Intent intent=getIntent();
//        final int idtour = intent.getIntExtra(MyEventTourFragment.EXTRA_IDTOUR,0);

        getData();

        namatour=findViewById(R.id.tv_hasil_namatour);
        juara1=findViewById(R.id.tv_hasil_1st);
        juara2=findViewById(R.id.tv_hasil_2nd);
        juara3=findViewById(R.id.tv_hasil_3rd);
    }

    void getData(){
// Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);

        Intent intent=getIntent();
        int idtour=intent.getIntExtra(MyEventTourFragment.EXTRA_IDTOUR,0);
        String namatours=intent.getStringExtra(MyEventTourFragment.EXTRA_NAMATOUR);
        String url = URL+"showhasiltour.php?id="+idtour;

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
                            namatour.setText(jsonPost.getString("namaTurnamen"));
                            juara1.setText(jsonPost.getString("juaraPertama"));
                            juara2.setText(jsonPost.getString("juaraKedua"));
                            juara3.setText(jsonPost.getString("juaraKetiga"));

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
