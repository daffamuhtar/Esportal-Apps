package com.majinor.esportal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.majinor.esportal.adapter.MyEventWaitingAdapter;
import com.majinor.esportal.model.MyEventWaitingModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.majinor.esportal.HomeFragment.KEY_USERNAME;
import static com.majinor.esportal.Server.URL;

public class MyEventWaitingActivity extends AppCompatActivity implements MyEventWaitingAdapter.OnItemClickListener {
    public static final String EXTRA_FOTO = "foto";
    public static final String EXTRA_IDREGIS = "id";
    public static final String EXTRA_IDTOUR = "idtour";
    public static final String EXTRA_STATUS = "status";
    public static final String EXTRA_NAMATOUR = "nama";
    public static final String EXTRA_JENIS = "jenis";
    public static final String EXTRA_BIAYA = "biaya";
    public static final String EXTRA_TGLTM = "tgltm";
    public static final String EXTRA_TGLTOUR = "tgltour";
    public static final String EXTRA_TGLDAFTAR = "tgldaftar";
    public static final String EXTRA_NAMATIM = "namatim";
    public static final String EXTRA_USRKETUA = "usrketua";
    public static final String EXTRA_USRANG1 = "usrang1";
    public static final String EXTRA_USRANG2 = "usrang2";
    public static final String EXTRA_USRANG3 = "usrang3";
    public static final String EXTRA_USRANG4 = "usrang4";
    public static final String EXTRA_IGNKETUA = "ignketua";
    public static final String EXTRA_IGNANG1 = "ignang1";
    public static final String EXTRA_IGNANG2 = "ignang2";
    public static final String EXTRA_IGNANG3 = "ignang3";
    public static final String EXTRA_IGNANG4 = "ignang4";
    public static final String EXTRA_ACCANG1 = "accang1";
    public static final String EXTRA_ACCANG2 = "accang2";
    public static final String EXTRA_ACCANG3 = "accang3";
    public static final String EXTRA_ACCANG4 = "accang4";
    public static final String EXTRA_USERNAME = "username";
    public static final String TAG_USERNAME = "username";


    private RecyclerView mRecyclerView;
    private MyEventWaitingAdapter mMyEventWaitingAdapter;
    private ArrayList<MyEventWaitingModel> mExampleList;
    private RequestQueue mRequestQueue;
    String username;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_event_waiting);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("My Event Waiting");

        mRecyclerView = findViewById(R.id.rv_mew);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mExampleList = new ArrayList<>();

        mRequestQueue = Volley.newRequestQueue(this);
        parseJSON();


    }

    private void parseJSON() {

        sharedpreferences = getSharedPreferences(LoginActivity.my_shared_preferences, Context.MODE_PRIVATE);

        username = sharedpreferences.getString(TAG_USERNAME, null);

        String url = URL+"showmytour.php?username="+username;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("result");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject hit = jsonArray.getJSONObject(i);

                                String imageUrl = hit.getString("foto");
                                String namaTour = hit.getString("namatour");
                                String tglTM = hit.getString("tgltm");
                                String jenisTour = hit.getString("jenis");
                                String tglDaftar = hit.getString("tgldaftar");
                                String tglTour = hit.getString("tgltour");
                                String namaTim = hit.getString("namatim");
                                String usrKetua = hit.getString("usrketua");
                                String usrAng1 = hit.getString("usrang1");
                                String usrAng2 = hit.getString("usrang2");
                                String usrAng3 = hit.getString("usrang3");
                                String usrAng4 = hit.getString("usrang4");
                                String ignKetua = hit.getString("ignketua");
                                String ignAng1 = hit.getString("ignang1");
                                String ignAng2 = hit.getString("ignang2");
                                String ignAng3 = hit.getString("ignang3");
                                String ignAng4 = hit.getString("ignang4");
                                int idRegis = hit.getInt("id");
                                int idTour = hit.getInt("idtour");
                                int status = hit.getInt("status");
                                int biaya = hit.getInt("biaya");
                                int accang1 = hit.getInt("accang1");
                                int accang2 = hit.getInt("accang2");
                                int accang3 = hit.getInt("accang3");
                                int accang4 = hit.getInt("accang4");

                                mExampleList.add(new MyEventWaitingModel (imageUrl, idRegis,idTour, status, namaTour,
                                        jenisTour,tglTour, tglDaftar, tglTM, biaya, namaTim, usrKetua, usrAng1,
                                        usrAng2, usrAng3, usrAng4, ignKetua, ignAng1, ignAng2, ignAng3, ignAng4,
                                        accang1, accang2,accang3, accang4));
                            }

                            mMyEventWaitingAdapter = new MyEventWaitingAdapter(MyEventWaitingActivity.this, mExampleList);
                            mRecyclerView.setAdapter(mMyEventWaitingAdapter);
                            mMyEventWaitingAdapter.setOnItemClickListener(MyEventWaitingActivity.this);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mRequestQueue.add(request);
    }

    @Override
    public void onItemClick(int position) {
        MyEventWaitingModel clickedItem = mExampleList.get(position);

        if(clickedItem.getmStatus()==3) {
            Intent intent = getIntent();
            String username = intent.getStringExtra(KEY_USERNAME);

            Intent detailIntent = new Intent(this, MyEventWaitingDetailActivity.class);
            detailIntent.putExtra(EXTRA_FOTO, clickedItem.getImageUrl());
            detailIntent.putExtra(EXTRA_IDREGIS, clickedItem.getmIdRegis());
            detailIntent.putExtra(EXTRA_IDTOUR, clickedItem.getmIdTour());
            detailIntent.putExtra(EXTRA_NAMATOUR, clickedItem.getmNamaTour());
            detailIntent.putExtra(EXTRA_JENIS, clickedItem.getmJenis());
            detailIntent.putExtra(EXTRA_BIAYA, clickedItem.getmBiaya());
            detailIntent.putExtra(EXTRA_TGLTM, clickedItem.getmTglTM());
            detailIntent.putExtra(EXTRA_TGLTOUR, clickedItem.getmTglTour());
            detailIntent.putExtra(EXTRA_TGLDAFTAR, clickedItem.getmTglDaftar());
            detailIntent.putExtra(EXTRA_NAMATIM, clickedItem.getmNamaTim());
            detailIntent.putExtra(EXTRA_USRKETUA, clickedItem.getmUsrKetua());
            detailIntent.putExtra(EXTRA_USRANG1, clickedItem.getmUsrAng1());
            detailIntent.putExtra(EXTRA_USRANG2, clickedItem.getmUsrAng2());
            detailIntent.putExtra(EXTRA_USRANG3, clickedItem.getmUsrAng3());
            detailIntent.putExtra(EXTRA_USRANG4, clickedItem.getmUsrAng4());
            detailIntent.putExtra(EXTRA_IGNKETUA, clickedItem.getmIgnKetua());
            detailIntent.putExtra(EXTRA_IGNANG1, clickedItem.getmIgnAng1());
            detailIntent.putExtra(EXTRA_IGNANG2, clickedItem.getmIgnAng2());
            detailIntent.putExtra(EXTRA_IGNANG3, clickedItem.getmIgnAng3());
            detailIntent.putExtra(EXTRA_IGNANG4, clickedItem.getmIgnAng4());
            detailIntent.putExtra(EXTRA_ACCANG1, clickedItem.getmAccAng1());
            detailIntent.putExtra(EXTRA_ACCANG2, clickedItem.getmAccAng2());
            detailIntent.putExtra(EXTRA_ACCANG3, clickedItem.getmAccAng3());
            detailIntent.putExtra(EXTRA_ACCANG4, clickedItem.getmAccAng4());

            startActivity(detailIntent);

        } else {
            Intent detailIntentlunas = new Intent(this, MyEventWaitingDetailLunasActivity.class);

            detailIntentlunas.putExtra(EXTRA_FOTO, clickedItem.getImageUrl());
            detailIntentlunas.putExtra(EXTRA_IDREGIS, clickedItem.getmIdRegis());
            detailIntentlunas.putExtra(EXTRA_IDTOUR, clickedItem.getmIdTour());
            detailIntentlunas.putExtra(EXTRA_NAMATOUR, clickedItem.getmNamaTour());
            detailIntentlunas.putExtra(EXTRA_JENIS, clickedItem.getmJenis());
            detailIntentlunas.putExtra(EXTRA_BIAYA, clickedItem.getmBiaya());
            detailIntentlunas.putExtra(EXTRA_TGLTM, clickedItem.getmTglTM());
            detailIntentlunas.putExtra(EXTRA_TGLTOUR, clickedItem.getmTglTour());
            detailIntentlunas.putExtra(EXTRA_TGLDAFTAR, clickedItem.getmTglDaftar());
            detailIntentlunas.putExtra(EXTRA_NAMATIM, clickedItem.getmNamaTim());
            detailIntentlunas.putExtra(EXTRA_USRKETUA, clickedItem.getmUsrKetua());
            detailIntentlunas.putExtra(EXTRA_USRANG1, clickedItem.getmUsrAng1());
            detailIntentlunas.putExtra(EXTRA_USRANG2, clickedItem.getmUsrAng2());
            detailIntentlunas.putExtra(EXTRA_USRANG3, clickedItem.getmUsrAng3());
            detailIntentlunas.putExtra(EXTRA_USRANG4, clickedItem.getmUsrAng4());
            detailIntentlunas.putExtra(EXTRA_IGNKETUA, clickedItem.getmIgnKetua());
            detailIntentlunas.putExtra(EXTRA_IGNANG1, clickedItem.getmIgnAng1());
            detailIntentlunas.putExtra(EXTRA_IGNANG2, clickedItem.getmIgnAng2());
            detailIntentlunas.putExtra(EXTRA_IGNANG3, clickedItem.getmIgnAng3());
            detailIntentlunas.putExtra(EXTRA_IGNANG4, clickedItem.getmIgnAng4());
            detailIntentlunas.putExtra(EXTRA_ACCANG1, clickedItem.getmAccAng1());
            detailIntentlunas.putExtra(EXTRA_ACCANG2, clickedItem.getmAccAng2());
            detailIntentlunas.putExtra(EXTRA_ACCANG3, clickedItem.getmAccAng3());
            detailIntentlunas.putExtra(EXTRA_ACCANG4, clickedItem.getmAccAng4());
            startActivity(detailIntentlunas);

        }
    }
}