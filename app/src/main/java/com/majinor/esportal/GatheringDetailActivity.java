package com.majinor.esportal;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.majinor.esportal.app.AppController;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.majinor.esportal.GatheringActivity.EXTRA_BIAYA;
import static com.majinor.esportal.GatheringActivity.EXTRA_DESK;
import static com.majinor.esportal.GatheringActivity.EXTRA_FOTO;
import static com.majinor.esportal.GatheringActivity.EXTRA_ID;
import static com.majinor.esportal.GatheringActivity.EXTRA_IDUSER;
import static com.majinor.esportal.GatheringActivity.EXTRA_NAMA;
import static com.majinor.esportal.GatheringActivity.EXTRA_NICK;
import static com.majinor.esportal.GatheringActivity.EXTRA_NOHP;
import static com.majinor.esportal.GatheringActivity.EXTRA_TEMPAT;
import static com.majinor.esportal.GatheringActivity.EXTRA_TGLGATH;
import static com.majinor.esportal.LoginActivity.TAG_ID;
import static com.majinor.esportal.MyEventGathFragment.EXTRA_IDFOLLOW;
import static com.majinor.esportal.Server.URL_IMAGEGATH;
import static com.majinor.esportal.Server.URL_IMAGETOUR;
import static com.majinor.esportal.TournamentActivity.EXTRA_IDGAME;


public class GatheringDetailActivity extends AppCompatActivity {

    TextView txt_follow,txt_unfollow;
    Button btn_call;
    CardView cv_gd_tentang;
    Intent intentdaftar;
    String id;
    int success;
    SharedPreferences sharedPreferences;
    private static final String TAG = GatheringDetailActivity.class.getSimpleName();

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    private String url = Server.URL + "followgath.php";
    private String UNFOLLOW_URL = Server.URL + "batalfollow.php";
    String tag_json_obj = "json_obj_req";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gathering_detail);


//            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//            setSupportActionBar(toolbar);
        Intent intentnama = getIntent();
        String namas = intentnama.getStringExtra(EXTRA_NAMA);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(namas);


        Intent intent = getIntent();
        final String nick = intent.getStringExtra(EXTRA_NICK);
        final int id = intent.getIntExtra(EXTRA_ID,0);
        final int idfollow = intent.getIntExtra(EXTRA_IDFOLLOW,0);
        String foto = intent.getStringExtra(EXTRA_FOTO);
        final String nama = intent.getStringExtra(EXTRA_NAMA);
        final String desk = intent.getStringExtra(EXTRA_DESK);
        final int iduser = intent.getIntExtra(EXTRA_IDUSER,0);
        int biaya = intent.getIntExtra(EXTRA_BIAYA, 0);
        String tglgath = intent.getStringExtra(EXTRA_TGLGATH);
        String tempat = intent.getStringExtra(EXTRA_TEMPAT);
        final String nohp = intent.getStringExtra(EXTRA_NOHP);


        ImageView imageView = findViewById(R.id.iv_gd_pamflet);
        TextView textViewNama = findViewById(R.id.tv_gd_nama);
        //TextView textViewDesk = findViewById(R.id.tv_detail_desk);
        TextView textViewNick = findViewById(R.id.tv_gd_nick);
        TextView textViewBiaya = findViewById(R.id.tv_gd_biaya);
        TextView textViewTglGath = findViewById(R.id.tv_gd_tglgath);
        TextView textViewTempat = findViewById(R.id.tv_gd_tempat);


        Picasso.with(this)
                .load(URL_IMAGEGATH+foto)
                .fit()
                .centerInside()
                .into(imageView);
        textViewNama.setText(nama);
        //textViewDesk.setText(desk);
        textViewNick.setText("By :"+nick);
        textViewBiaya.setText("Rp. " + biaya);
        textViewTglGath.setText(tglgath);
        textViewTempat.setText(tempat);
        //textViewNoHP.setText("Likes: " + nohp);

        txt_unfollow = (TextView) findViewById(R.id.txt_gd_unfollow);
        if(idfollow==0){
            txt_unfollow.setVisibility(View.GONE);
        }else{

        }


        btn_call  = (Button) findViewById(R.id.btn_gd_call);
        btn_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+nohp));
                startActivity(intent);
            }
        });

        cv_gd_tentang=(CardView) findViewById(R.id.cv_gd_tentang);
        cv_gd_tentang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentDetail = new Intent(GatheringDetailActivity.this, GatheringDetailTentangActivity.class);

                intentDetail.putExtra(EXTRA_DESK,desk);

                startActivity(intentDetail);


            }
        });

        txt_follow = (TextView) findViewById(R.id.txt_gd_follow);
        txt_follow.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
            AddFollow();

            }

        });

        txt_unfollow.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                batalRegis();

            }

        });

    }

    private void AddFollow () {

        sharedPreferences=getSharedPreferences(LoginActivity.my_shared_preferences, Context.MODE_PRIVATE);
        id=sharedPreferences.getString(TAG_ID,null);

        Intent intent = getIntent();
        final int idGath = intent.getIntExtra(EXTRA_ID,0);
        final String tglgath = intent.getStringExtra(EXTRA_TGLGATH);
        class AddRegis extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(
                        GatheringDetailActivity.this,
                        "Menambahkan...",
                        "Tunggu Sebentar...",
                        false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(GatheringDetailActivity.this, s, Toast.LENGTH_LONG).show();

            }

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String, String> params = new HashMap<>();

                params.put("iduser", String.valueOf(id));
                params.put("idgath", String.valueOf(idGath));
                params.put("tglgath", tglgath);

                RequestHandlers requestHandler = new RequestHandlers();
                return requestHandler.sendPostRequest(url, params);
            }
        }

        new AddRegis().execute();

    }

    private void batalRegis() {
        //menampilkan progress dialog
        Intent intent = getIntent();
        final int id = intent.getIntExtra(EXTRA_IDFOLLOW,0);

        final ProgressDialog loading = ProgressDialog.show(this, "Uploading...", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UNFOLLOW_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e(TAG, "Response: " + response.toString());

                        try {
                            JSONObject jObj = new JSONObject(response);
                            success = jObj.getInt(TAG_SUCCESS);


                            if (success == 1) {
                                Log.e("v Add", jObj.toString());

                                Toast.makeText(GatheringDetailActivity.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();





                            } else {
                                Toast.makeText(GatheringDetailActivity.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        //menghilangkan progress dialog
                        loading.dismiss();
                        Toast.makeText(GatheringDetailActivity.this, "unFollow berhasil", Toast.LENGTH_LONG).show();
                        Intent tomain=new Intent(GatheringDetailActivity.this,MainActivity.class);
                        startActivity(tomain);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //menghilangkan progress dialog
                        loading.dismiss();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                //membuat parameters
                Map<String, String> params = new HashMap<String, String>();

                //menambah parameter yang di kirim ke web servis
                params.put("id", String.valueOf(id));

                //kembali ke parameters
                Log.e(TAG, "" + params);
                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(stringRequest, tag_json_obj);
    }

}



