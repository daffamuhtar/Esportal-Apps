package com.majinor.esportal;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


import static com.majinor.esportal.Server.URL_IMAGETOUR;
import static com.majinor.esportal.TournamentActivity.EXTRA_DESK;
import static com.majinor.esportal.TournamentActivity.EXTRA_IDTOUR;
import static com.majinor.esportal.TournamentActivity.EXTRA_IDGAME;
import static com.majinor.esportal.TournamentActivity.EXTRA_NAMA;
import static com.majinor.esportal.TournamentActivity.EXTRA_PANITIA;
import static com.majinor.esportal.TournamentActivity.EXTRA_ROLEID;
import static com.majinor.esportal.TournamentActivity.EXTRA_TGLTOUR;

public class TournamentRegisActivity extends AppCompatActivity implements View.OnClickListener {

    TextView etNamaTour,etKetuaUser,etKetuaNohp;
    EditText etNamaTim,etKetuaIgn,etAngUser1,
            etAngIgn1,etAngUser2,etAngIgn2,etAngUser3,etAngIgn3,etAngUser4,etAngIgn4;
    public static final String TAG_ID = "id";
    public static final String TAG_USERNAME = "username";
    public static final String TAG_NOHP = "nohp";

    public static final String EXTRA_IDTURNAMEN="idtour";
    public static final String EXTRA_NAMATIM = "namatim";
    public static final String EXTRA_KETUAUSER = "ketuauser";
    public static final String EXTRA_KETUANOHP = "ketuanohp";
    public static final String EXTRA_KETUAIGN = "ketuaign";
    public static final String EXTRA_ANGUSER1 = "anguser1";
    public static final String EXTRA_ANGIGN1 = "angign1";
    public static final String EXTRA_ANGUSER2 = "anguser1";
    public static final String EXTRA_ANGIGN2 = "angign2";
    public static final String EXTRA_ANGUSER3 = "anguser3";
    public static final String EXTRA_ANGIGN3 = "angign3";
    public static final String EXTRA_ANGUSER4 = "anguser4";
    public static final String EXTRA_ANGIGN4 = "angign4";

    private String url = Server.URL + "registour.php";

    Button daftar;
    String id, username, nohp ,s1,s2;
    int success;
    SharedPreferences sharedpreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tournament_regis);

        getSupportActionBar().setTitle("Daftar");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        String id = intent.getStringExtra(EXTRA_IDTOUR);
        String idgame=intent.getStringExtra(EXTRA_IDGAME);
        String nama = intent.getStringExtra(EXTRA_NAMA);

        sharedpreferences = getSharedPreferences(LoginActivity.my_shared_preferences, Context.MODE_PRIVATE);

        username = sharedpreferences.getString(TAG_USERNAME, null);
        nohp = sharedpreferences.getString(TAG_NOHP, null);


        etNamaTour = findViewById(R.id.et_regis_namatour);
        etNamaTim = findViewById(R.id.et_regis_tim);
        etKetuaUser = findViewById(R.id.et_regis_ketuauser);
        etKetuaNohp = findViewById(R.id.et_regis_ketuano);
        etKetuaIgn = findViewById(R.id.et_regis_ketuaign);
        etAngUser1 = findViewById(R.id.et_regis_anguser1);
        etAngIgn1 = findViewById(R.id.et_regis_angign1);
        etAngUser2 = findViewById(R.id.et_regis_anguser2);
        etAngIgn2 = findViewById(R.id.et_regis_angign2);
        etAngUser3 = findViewById(R.id.et_regis_anguser3);
        etAngIgn3 = findViewById(R.id.et_regis_angign3);
        etAngUser4 = findViewById(R.id.et_regis_anguser4);
        etAngIgn4 = findViewById(R.id.et_regis_angign4);
        daftar = (Button) findViewById(R.id.tv_daftar);

        //settextpanggil
        etNamaTour.setText(nama);
        etKetuaUser.setText(username);
        etKetuaNohp.setText(nohp);

        daftar.setOnClickListener(this);
    }


    private void reset () {

    }
    private void AddEmployee () {
        final Intent intent = getIntent();
        final int id = intent.getIntExtra(EXTRA_IDTOUR,0);
        final int roleid = intent.getIntExtra(EXTRA_ROLEID,0);
        final int idgame = intent.getIntExtra(EXTRA_IDGAME,0);
        final String tgltour = intent.getStringExtra(EXTRA_TGLTOUR);

        final String panitia = intent.getStringExtra(EXTRA_PANITIA);

        final String namaTour = etNamaTour.getText().toString().trim();
        final String namaTim = etNamaTim.getText().toString().trim();
        final String ketuaUser = etKetuaUser.getText().toString().trim();
        final String ketuaNohp = etKetuaNohp.getText().toString().trim();
        final String ketuaIgn = etKetuaIgn.getText().toString().trim();
        final String angUser1 = etAngUser1.getText().toString().trim();
        final String angIgn1 = etAngIgn1.getText().toString().trim();
        final String angUser2 = etAngUser2.getText().toString().trim();
        final String angIgn2 = etAngIgn2.getText().toString().trim();
        final String angUser3 = etAngUser3.getText().toString().trim();
        final String angIgn3 = etAngIgn3.getText().toString().trim();
        final String angUser4 = etAngUser4.getText().toString().trim();
        final String angIgn4 = etAngIgn4.getText().toString().trim();
        class AddRegis extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(
                        TournamentRegisActivity.this,
                        "Menambahkan...",
                        "Tunggu Sebentar...",
                        false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
//menaruh data JSON kkedalam variabel JSON Object
                            JSONObject jsonPost = new JSONObject(response.toString());


                            success = jsonPost.getInt("success");

                            if (success==1){
                                Intent intentDetail = new Intent(TournamentRegisActivity.this, TournamentDetailTentangActivity.class);
                                startActivity(intentDetail);
                            } else {
                                Intent intentDetail = new Intent(TournamentRegisActivity.this, GatheringActivity.class);
                                startActivity(intentDetail);
                            }


//

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                reset();
                Toast.makeText(TournamentRegisActivity.this, s, Toast.LENGTH_LONG).show();

            }

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String, String> params = new HashMap<>();

                params.put("idtour", String.valueOf(id));
                params.put("roleid", String.valueOf(roleid));
                params.put("namatour", namaTour);
                params.put("tgltour", tgltour);
                params.put("panitia", panitia);
                params.put("namatim",namaTim);
                params.put("idgame", String.valueOf(idgame));
                params.put("usrketua", ketuaUser);
                params.put("noketua", ketuaNohp);
                params.put("ignketua", ketuaIgn);
                params.put("usrang1", angUser1);
                params.put("ignang1", angIgn1);
                params.put("usrang2", angUser2);
                params.put("ignang2", angIgn2);
                params.put("usrang3", angUser3);
                params.put("ignang3", angIgn3);
                params.put("usrang4", angUser4);
                params.put("ignang4", angIgn4);

                RequestHandlers requestHandler = new RequestHandlers();
                return requestHandler.sendPostRequest(url, params);
            }
        }

        new AddRegis().execute();

    }

    @Override
    public void onClick (View view){

        etNamaTim = findViewById(R.id.et_regis_tim);
        etKetuaUser = findViewById(R.id.et_regis_ketuauser);
        etKetuaNohp = findViewById(R.id.et_regis_ketuano);
        etKetuaIgn = findViewById(R.id.et_regis_ketuaign);
        etAngUser1 = findViewById(R.id.et_regis_anguser1);
        etAngIgn1 = findViewById(R.id.et_regis_angign1);
        etAngUser2 = findViewById(R.id.et_regis_anguser2);
        etAngIgn2 = findViewById(R.id.et_regis_angign2);
        etAngUser3 = findViewById(R.id.et_regis_anguser3);
        etAngIgn3 = findViewById(R.id.et_regis_angign3);
        etAngUser4 = findViewById(R.id.et_regis_anguser4);
        etAngIgn4 = findViewById(R.id.et_regis_angign4);
        daftar = (Button) findViewById(R.id.tv_daftar);

        String tim=etNamaTim.getText().toString();
        String kusr=etKetuaUser.getText().toString();
        String kign=etKetuaIgn.getText().toString();
        String ausr1=etAngUser1.getText().toString();
        String ausr2=etAngUser2.getText().toString();
        String ausr3=etAngUser3.getText().toString();
        String ausr4=etAngUser4.getText().toString();
        String aign1=etAngIgn1.getText().toString();
        String aign2=etAngIgn2.getText().toString();
        String aign3=etAngIgn3.getText().toString();
        String aign4=etAngIgn4.getText().toString();
        if (view == daftar) {
            if(tim.matches("")){
                etNamaTim.setError("Masukkan Nama Tim");
            } else if(kign.matches("")){
                etKetuaIgn.setError("Masukkan Nickname");
            }else if(ausr1.matches("")){
                etAngUser1.setError("Masukkan username");
            }else if(ausr1.equals(kusr) ){
                etAngUser1.setError("Tidak dapat menggunakan username yang sama");
            }else if(aign1.matches("")){
                etAngIgn1.setError("Masukkan nickname");
            }else if(ausr2.matches("")){
                etAngUser2.setError("Masukkan username");
            }else if(ausr2.equals(kusr)||ausr2.equals(ausr1) ){
                etAngUser2.setError("Tidak dapat menggunakan username yang sama");
            }else if(aign2.matches("")){
                etAngIgn2.setError("Masukkan nickname");
            }else if(ausr3.matches("")){
                etAngUser3.setError("Masukkan username");
            }else if(ausr3.equals(kusr)||ausr3.equals(ausr1)||ausr3.equals(ausr2) ){
                etAngUser3.setError("Tidak dapat menggunakan username yang sama");
            }else if(aign3.matches("")){
                etAngIgn3.setError("Masukkan nickname");
            }else if(ausr4.matches("")){
                etAngUser4.setError("Masukkan username");
            }else if(ausr4.equals(kusr)||ausr4.equals(ausr1)||ausr4.equals(ausr2)||ausr4.equals(ausr3) ){
                etAngUser4.setError("Tidak dapat menggunakan username yang sama");
            }else if(aign4.matches("")){
                etAngIgn4.setError("Masukkan nickname");
            } else {


                AddEmployee();
            }
        }

    }

    @Override
    public void onPointerCaptureChanged ( boolean hasCapture){

    }
}