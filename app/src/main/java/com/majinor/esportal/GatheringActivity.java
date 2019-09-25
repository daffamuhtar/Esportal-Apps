package com.majinor.esportal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.majinor.esportal.adapter.GatheringAdapter;
import com.majinor.esportal.model.GatheringModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.majinor.esportal.HomeFragment.KEY_USERNAME;
import static com.majinor.esportal.Server.URL;
//import static com.majinor.esportal.TournamentActivity.EXTRA_BIAYA;

public class GatheringActivity extends AppCompatActivity implements GatheringAdapter.OnItemClickListener {
    public static final String EXTRA_FOTO = "foto";
    public static final String EXTRA_ID = "id";
    public static final String EXTRA_IDGAME = "idgame";
    public static final String EXTRA_IDUSER = "iduser";
    public static final String EXTRA_NAMA = "nama";
    public static final String EXTRA_DESK = "desk";
    public static final String EXTRA_TGLGATH = "tglgath";
    public static final String EXTRA_TEMPAT = "tempat";
    public static final String EXTRA_NOHP = "nohp";
    public static final String EXTRA_BIAYA = "biaya";
    public static final String EXTRA_NICK = "namauser";


    private RecyclerView mRecyclerView;
    private GatheringAdapter mGatheringAdapter;
    private ArrayList<GatheringModel> mExampleList;
    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gathering);

        Intent intentnama = getIntent();
        String username = intentnama.getStringExtra(KEY_USERNAME);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Gathering");

        mRecyclerView = findViewById(R.id.rv_gath);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mExampleList = new ArrayList<>();

        mRequestQueue = Volley.newRequestQueue(this);
        parseJSON();



    }

    private void parseJSON() {
        String url = URL+"showgath.php";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("result");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject hit = jsonArray.getJSONObject(i);

                                String imageUrl = hit.getString("foto");
                                String namaGath = hit.getString("nama");
                                String deskGath = hit.getString("desk");
                                int iduser = hit.getInt("iduser");
                                String nick = hit.getString("namauser");
                                String tglGath = hit.getString("tglgath");
                                String tempat = hit.getString("tempat");
                                String noHP = hit.getString("nohp");
                                int idGath = hit.getInt("id");
                                int biaya = hit.getInt("biaya");

                                mExampleList.add(new GatheringModel(imageUrl, idGath, namaGath, deskGath,iduser,
                                        nick, tglGath, tempat, biaya, noHP));
                            }

                            mGatheringAdapter = new GatheringAdapter(GatheringActivity.this, mExampleList);
                            mRecyclerView.setAdapter(mGatheringAdapter);
                            mGatheringAdapter.setOnItemClickListener(GatheringActivity.this);

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

        Intent intent = getIntent();
        String username = intent.getStringExtra(KEY_USERNAME);

        Intent detailIntent = new Intent(this, GatheringDetailActivity.class);


        GatheringModel clickedItem = mExampleList.get(position);
        detailIntent.putExtra(EXTRA_FOTO, clickedItem.getImageUrl());
        detailIntent.putExtra(EXTRA_ID, clickedItem.getmId());
        detailIntent.putExtra(EXTRA_NAMA, clickedItem.getmNama());;
        detailIntent.putExtra(EXTRA_DESK, clickedItem.getmDesk());
        detailIntent.putExtra(EXTRA_IDUSER, clickedItem.getmIdUser());
        detailIntent.putExtra(EXTRA_NICK, clickedItem.getmNick());
        detailIntent.putExtra(EXTRA_BIAYA, clickedItem.getmBiaya());
        detailIntent.putExtra(EXTRA_TGLGATH, clickedItem.getmTglGath());
        detailIntent.putExtra(EXTRA_TEMPAT, clickedItem.getmTempat());
        detailIntent.putExtra(EXTRA_NOHP, clickedItem.getmNoHP());


        startActivity(detailIntent);
    }


    // Menu icons are inflated just as they were with actionbar
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_gath, menu);
//        return true;
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.menu_gath, menu);

    MenuItem searchItem = menu.findItem(R.id.action_search);
    SearchView searchView = (SearchView) searchItem.getActionView();

    searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            mGatheringAdapter.getFilter().filter(newText);
            return false;
        }

    });
    return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.addgath:
                Intent intent = new Intent(this, GatheringAddActivity.class);
                this.startActivity(intent);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }


}