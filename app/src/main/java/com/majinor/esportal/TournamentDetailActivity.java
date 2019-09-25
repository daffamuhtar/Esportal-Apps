package com.majinor.esportal;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import static com.majinor.esportal.Server.URL;
import static com.majinor.esportal.Server.URL_IMAGETOUR;
import static com.majinor.esportal.TournamentActivity.EXTRA_IDGAME;
import static com.majinor.esportal.TournamentActivity.EXTRA_IDTOUR;
import static com.majinor.esportal.TournamentActivity.EXTRA_NAMA;
import static com.majinor.esportal.TournamentActivity.EXTRA_JENIS;
import static com.majinor.esportal.TournamentActivity.EXTRA_DESK;
import static com.majinor.esportal.TournamentActivity.EXTRA_ROLEID;
import static com.majinor.esportal.TournamentActivity.EXTRA_SMAX;
import static com.majinor.esportal.TournamentActivity.EXTRA_SISI;
import static com.majinor.esportal.TournamentActivity.EXTRA_PANITIA;
import static com.majinor.esportal.TournamentActivity.EXTRA_BIAYA;
import static com.majinor.esportal.TournamentActivity.EXTRA_HADIAH;
import static com.majinor.esportal.TournamentActivity.EXTRA_STATUS;
import static com.majinor.esportal.TournamentActivity.EXTRA_TGLDAFTAR;
import static com.majinor.esportal.TournamentActivity.EXTRA_TGLTM;
import static com.majinor.esportal.TournamentActivity.EXTRA_TGLTOUR;
import static com.majinor.esportal.TournamentActivity.EXTRA_TEMPAT;
import static com.majinor.esportal.TournamentActivity.EXTRA_NOHP;
import static com.majinor.esportal.TournamentActivity.EXTRA_FOTO;
import static com.majinor.esportal.TournamentActivity.EXTRA_USERNAME;


public class TournamentDetailActivity extends AppCompatActivity {

    TextView txt_daftar,txt_full,txt_end,txt_terdaftar;
    Button btn_call;
    CardView cv_det_tentang;
    Intent intentdaftar;
    ImageView imageView;
    TextView textViewNama;
    String username,nama,desk,nohp,jenis,tgldaftar,tgltm,tgltour,tempat,foto,panitia;
    int id,idgame,roleid,biaya,hadiah,smax,sisi,isactive,estatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tournamen_detail);


//            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//            setSupportActionBar(toolbar);
        Intent intentnama = getIntent();
        String namas = intentnama.getStringExtra(EXTRA_NAMA);
//        int statuss = intentnama.getIntExtra(EXTRA_STATUS,0);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(namas);



        getData();


        Intent intent = getIntent();
        int status = intent.getIntExtra(EXTRA_STATUS,0);
        int slmax = intent.getIntExtra(EXTRA_SMAX,0);
        int slisi = intent.getIntExtra(EXTRA_SISI, 0);
//


        txt_terdaftar=findViewById(R.id.txt_terdaftar);
        txt_end=findViewById(R.id.txt_tourend);
        txt_full=findViewById(R.id.txt_slotfull);


        if (status==3){
            txt_full.setVisibility(View.GONE);
            txt_terdaftar.setVisibility(View.GONE);
            txt_end.setVisibility(View.VISIBLE);

        }  else{
            if (slmax==0 && slisi==0){
                txt_full.setVisibility(View.GONE);
                txt_end.setVisibility(View.GONE);
                txt_terdaftar.setVisibility(View.VISIBLE);

            }else if (slmax==slisi){
                txt_full.setVisibility(View.VISIBLE);
                txt_end.setVisibility(View.GONE);
                txt_terdaftar.setVisibility(View.GONE);


            } else {
                    txt_full.setVisibility(View.GONE);
                    txt_end.setVisibility(View.GONE);
                    txt_terdaftar.setVisibility(View.GONE);

                }
        }





        btn_call  = (Button) findViewById(R.id.btn_call);
        btn_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+nohp));
                startActivity(intent);
            }
        });

        cv_det_tentang=(CardView) findViewById(R.id.cv_detail_tentang);
        cv_det_tentang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentDetail = new Intent(TournamentDetailActivity.this, TournamentDetailTentangActivity.class);

                intentDetail.putExtra(EXTRA_DESK,desk);

                startActivity(intentDetail);

            }
        });

        txt_daftar = (TextView) findViewById(R.id.txt_daftar);
        txt_daftar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                Intent intentDaftar = new Intent(TournamentDetailActivity.this, TournamentRegisActivity.class);

                intentDaftar.putExtra(EXTRA_IDTOUR,id);
                intentDaftar.putExtra(EXTRA_IDGAME,idgame);
                intentDaftar.putExtra(EXTRA_NAMA,nama);
                intentDaftar.putExtra(EXTRA_PANITIA,panitia);
                intentDaftar.putExtra(EXTRA_USERNAME,username);
                intentDaftar.putExtra(EXTRA_ROLEID,roleid);
                intentDaftar.putExtra(EXTRA_TGLTOUR,tgltour);

                startActivity(intentDaftar);

            }

        });

    }

    void getData(){
// Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        Intent intent = getIntent();
        int idt = intent.getIntExtra(EXTRA_IDTOUR,0);

        String namatours=intent.getStringExtra(MyEventTourFragment.EXTRA_NAMATOUR);
        String url = URL+"showtourdetail.php?idtour="+idt;

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

                            id = jsonPost.getInt("idTurnamen");
                            isactive = jsonPost.getInt("is_active");
                            estatus = jsonPost.getInt("status_event");
                            foto = jsonPost.getString("fotoTurnamen");
                            nama = jsonPost.getString("namaTurnamen");
                            desk = jsonPost.getString("deskTurnamen");
                            jenis = jsonPost.getString("jenisTurnamen");
                            smax = jsonPost.getInt("slotMax");
                            sisi = jsonPost.getInt("slotTerisi");
                            idgame = jsonPost.getInt("idGame");
                            roleid = jsonPost.getInt("role_id");
                            panitia = jsonPost.getString("namaPanitia");
                            biaya = jsonPost.getInt("biayaPendaftaran");
                            hadiah = jsonPost.getInt("totalHadiah");
                            tgldaftar = jsonPost.getString("batasPendaftaran");
                            tgltm = jsonPost.getString("tanggalTM");
                            tgltour = jsonPost.getString("tanggalTurnamen");
                            tempat = jsonPost.getString("tempatTurnamen");
                            nohp = jsonPost.getString("noHP");

                            getSupportActionBar().setTitle(nama);

                            imageView = findViewById(R.id.iv_detail_pamflet);
                            textViewNama = findViewById(R.id.tv_detail_nama);
                            TextView textViewPanitia = findViewById(R.id.tv_detail_panitia);
                            TextView textViewJenis = findViewById(R.id.tv_detail_jenis);
                            TextView textViewSlotMax = findViewById(R.id.tv_detail_slotmax);
                            //TextView textViewSlotIsi = findViewById(R.id.tv_detail_slotisi);
                            //TextView textViewGame = findViewById(R.id.tv_detail_game);
                            //TextView textViewPanitia = findViewById(R.id.tv_detail_panitia);
                            TextView textViewBiaya = findViewById(R.id.tv_detail_biaya);
                            TextView textViewHadiah = findViewById(R.id.tv_detail_hadiah);
                            TextView textViewTglDaftar = findViewById(R.id.tv_detail_tgldaftar);
                            //TextView textViewTglTM = findViewById(R.id.tv_detail_tgltm);
                            TextView textViewTglTour = findViewById(R.id.tv_detail_tgltour);
                            TextView textViewTempat = findViewById(R.id.tv_detail_tempat);
                            //TextView textViewNoHP = findViewById(R.id.tv_detail_nohp);


                            Picasso.with(getApplicationContext()).load(URL_IMAGETOUR+foto).fit().centerInside().into(imageView);
                            textViewNama.setText(nama);
                            textViewPanitia.setText("By : "+panitia);
                            textViewJenis.setText(jenis);
                            textViewSlotMax.setText("Slot : " + smax+ " / " + sisi);
                            //textViewSlotIsi.setText("Slot : " + sisi);
                            // textViewGame.setText(game);
                            //textViewPanitia.setText(panitia);
                            textViewBiaya.setText("Rp. " + biaya);
                            textViewHadiah.setText("Rp. " + hadiah);
                            textViewTglDaftar.setText(tgldaftar);
                            //textViewTglTM.setText(tgltm);
                            textViewTglTour.setText(tgltour);
                            textViewTempat.setText(tempat);
                            //textViewNoHP.setText("Likes: " + nohp);

                            if (estatus==3){
                                txt_full.setVisibility(View.GONE);
                                txt_terdaftar.setVisibility(View.GONE);
                                txt_end.setVisibility(View.VISIBLE);
                            }else{

                            }


//

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





