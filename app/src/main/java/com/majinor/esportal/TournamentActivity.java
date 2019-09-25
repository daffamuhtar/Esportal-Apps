package com.majinor.esportal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
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
import com.majinor.esportal.adapter.TournamentAdapter;
import com.majinor.esportal.model.TournamentModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.majinor.esportal.HomeFragment.KEY_USERNAME;
import static com.majinor.esportal.Server.URL;

public class TournamentActivity extends AppCompatActivity implements TournamentAdapter.OnItemClickListener {
    public static final String EXTRA_FOTO = "foto";
    public static final String EXTRA_IDTOUR = "idtour";
    public static final String EXTRA_STATUS = "status";
    public static final String EXTRA_IDGAME = "idgame";
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
    public static final String EXTRA_ROLEID = "roleid";
    public static final String EXTRA_USERNAME = "username";


    private RecyclerView mRecyclerView;
    private TournamentAdapter mTournamentAdapter;
    private ArrayList<TournamentModel> mExampleList;
    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tournament);

        Intent intentnama = getIntent();
        String username = intentnama.getStringExtra(KEY_USERNAME);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Turnamen");

        mRecyclerView = findViewById(R.id.rv_tour);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mExampleList = new ArrayList<>();

        mRequestQueue = Volley.newRequestQueue(this);
        parseJSON();

    }

    private void parseJSON() {
        String url = URL+"showtour.php";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("result");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject hit = jsonArray.getJSONObject(i);

                                String imageUrl = hit.getString("foto");
                                String namaTour = hit.getString("nama");
                                String deskTour = hit.getString("desk");
                                String jenisTour = hit.getString("jenis");
                                int idgame = hit.getInt("idgame");
                                int status = hit.getInt("status");
                                String panitia = hit.getString("panitia");
                                String tglDaftar = hit.getString("tgldaftar");
                                String tglTM = hit.getString("tgltm");
                                String tgltour = hit.getString("tgltour");
                                String tempat = hit.getString("tempat");
                                String noHP = hit.getString("nohp");
                                int idTour = hit.getInt("id");
                                int biaya = hit.getInt("biaya");
                                int hadiah = hit.getInt("hadiah");
                                int slotMax = hit.getInt("smax");
                                int slotIsi = hit.getInt("sisi");
                                int roleid = hit.getInt("roleid");

                                mExampleList.add(new TournamentModel(imageUrl, idTour, status ,namaTour, deskTour, jenisTour, idgame, panitia,
                                        tglDaftar,tglTM, tgltour, tempat, biaya, hadiah, slotMax, slotIsi, noHP,roleid));
                            }

                            mTournamentAdapter = new TournamentAdapter(TournamentActivity.this, mExampleList);
                            mRecyclerView.setAdapter(mTournamentAdapter);
                            mTournamentAdapter.setOnItemClickListener(TournamentActivity.this);

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

        Intent detailIntent = new Intent(this, TournamentDetailActivity.class);


        TournamentModel clickedItem = mExampleList.get(position);
        detailIntent.putExtra(EXTRA_USERNAME,username);
        detailIntent.putExtra(EXTRA_FOTO, clickedItem.getImageUrl());
        detailIntent.putExtra(EXTRA_IDTOUR, clickedItem.getmId());
        detailIntent.putExtra(EXTRA_IDGAME, clickedItem.getmIdGame());
        detailIntent.putExtra(EXTRA_STATUS, clickedItem.getmStatus());
        detailIntent.putExtra(EXTRA_NAMA, clickedItem.getmNama());;
        detailIntent.putExtra(EXTRA_JENIS, clickedItem.getmJenis());
        detailIntent.putExtra(EXTRA_DESK, clickedItem.getmDesk());
        detailIntent.putExtra(EXTRA_SMAX, clickedItem.getmSlotMax());
        detailIntent.putExtra(EXTRA_SISI, clickedItem.getmSlotIsi());
        detailIntent.putExtra(EXTRA_GAME, clickedItem.getmIdGame());
        detailIntent.putExtra(EXTRA_PANITIA, clickedItem.getmPanitia());
        detailIntent.putExtra(EXTRA_BIAYA, clickedItem.getmBiaya());
        detailIntent.putExtra(EXTRA_HADIAH, clickedItem.getmHadiah());
        detailIntent.putExtra(EXTRA_TGLDAFTAR, clickedItem.getmTglDaftar());
        detailIntent.putExtra(EXTRA_TGLTM, clickedItem.getmTglTM());
        detailIntent.putExtra(EXTRA_TGLTOUR, clickedItem.getmTglTour());
        detailIntent.putExtra(EXTRA_TEMPAT, clickedItem.getmTempat());
        detailIntent.putExtra(EXTRA_NOHP, clickedItem.getmNoHP());
        detailIntent.putExtra(EXTRA_ROLEID, clickedItem.getmRoleid());


        startActivity(detailIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_tournament, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setQueryHint("Search tour, panitia, tgl, tempat...");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mTournamentAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }
}