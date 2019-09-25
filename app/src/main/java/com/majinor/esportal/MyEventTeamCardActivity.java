package com.majinor.esportal;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.majinor.esportal.adapter.MyEventWaitingAdapter;
import com.majinor.esportal.model.MyEventWaitingModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.majinor.esportal.MyEventTourFragment.EXTRA_IDGAME;
import static com.majinor.esportal.MyEventTourFragment.EXTRA_IDTOUR;
import static com.majinor.esportal.MyEventTourFragment.EXTRA_NAMATOUR;
import static com.majinor.esportal.Server.URL;

public class MyEventTeamCardActivity extends AppCompatActivity {

    Button btnjadwal,btnhasil,btninfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_event_team_card);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("TEAM CARD");

        Intent intent = getIntent();
        final int idregis = intent.getIntExtra(MyEventTourFragment.EXTRA_IDREGIS,0);
        final int idtour = intent.getIntExtra(EXTRA_IDTOUR,0);
        final int idgame = intent.getIntExtra(EXTRA_IDGAME,0);
        final String namaTour = intent.getStringExtra(MyEventTourFragment.EXTRA_NAMATOUR);
        final String namaTim = intent.getStringExtra(MyEventTourFragment.EXTRA_NAMATIM);
        final String usrKetua = intent.getStringExtra(MyEventTourFragment.EXTRA_USRKETUA);
        final String usrAng1 = intent.getStringExtra(MyEventTourFragment.EXTRA_USRANG1);
        final String usrAng2 = intent.getStringExtra(MyEventTourFragment.EXTRA_USRANG2);
        final String usrAng3 = intent.getStringExtra(MyEventTourFragment.EXTRA_USRANG3);
        final String usrAng4 = intent.getStringExtra(MyEventTourFragment.EXTRA_USRANG4);
        final String ignKetua = intent.getStringExtra(MyEventTourFragment.EXTRA_IGNKETUA);
        final String ignAng1 = intent.getStringExtra(MyEventTourFragment.EXTRA_IGNANG1);
        final String ignAng2 = intent.getStringExtra(MyEventTourFragment.EXTRA_IGNANG2);
        final String ignAng3 = intent.getStringExtra(MyEventTourFragment.EXTRA_IGNANG3);
        final String ignAng4 = intent.getStringExtra(MyEventTourFragment.EXTRA_IGNANG4);

        TextView textIdRegis = findViewById(R.id.tv_metrd_idregis);
        TextView textViewNamaTour = findViewById(R.id.tv_metrd_namatour);
        TextView textViewNamaTim = findViewById(R.id.tv_metrd_namatim);
        TextView textViewUsrKetua = findViewById(R.id.tv_metrd_usrketua);
        TextView textViewUsrAng1 = findViewById(R.id.tv_metrd_usrang1);
        TextView textViewUsrAng2 = findViewById(R.id.tv_metrd_usrang2);
        TextView textViewUsrAng3 = findViewById(R.id.tv_metrd_usrang3);
        TextView textViewUsrAng4 = findViewById(R.id.tv_metrd_usrang4);
        TextView textViewIgnKetua = findViewById(R.id.tv_metrd_ignketua);
        TextView textViewIgnAng1 = findViewById(R.id.tv_metrd_ignang1);
        TextView textViewIgnAng2 = findViewById(R.id.tv_metrd_ignang2);
        TextView textViewIgnAng3 = findViewById(R.id.tv_metrd_ignang3);
        TextView textViewIgnAng4 = findViewById(R.id.tv_metrd_ignang4);



        textViewNamaTour.setText(namaTour);
        textIdRegis.setText(""+idregis);
        textViewNamaTim.setText(namaTim);
        textViewUsrKetua.setText(usrKetua);
        textViewUsrAng1.setText(usrAng1);
        textViewUsrAng2.setText(usrAng2);
        textViewUsrAng3.setText(usrAng3);
        textViewUsrAng4.setText(usrAng4);
        textViewIgnKetua.setText(ignKetua);
        textViewIgnAng1.setText(ignAng1);
        textViewIgnAng2.setText(ignAng2);
        textViewIgnAng3.setText(ignAng3);
        textViewIgnAng4.setText(ignAng4);

//        btnhasil=findViewById(R.id.btn_metrd_hasil);
//        btnjadwal=findViewById(R.id.btn_metrd_jadwal);
//        btninfo=findViewById(R.id.btn_metrd_info);
//        btninfo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intentinfo=new Intent(MyEventTeamCardActivity.this,TournamentDetailActivity.class);
//                intentinfo.putExtra(EXTRA_NAMATOUR,namaTour);
//                intentinfo.putExtra(EXTRA_IDTOUR,idtour);
//                startActivity(intentinfo);
//            }
//        });
//        btnjadwal.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (idgame==1) {
//                    Intent intentjadwal = new Intent(MyEventTeamCardActivity.this, MyEventTourRegistedJadwal2Activity.class);
//
//                    intentjadwal.putExtra(EXTRA_NAMATOUR, namaTour);
//                    intentjadwal.putExtra(EXTRA_IDTOUR, idtour);
//                    startActivity(intentjadwal);
//                }else{
//                    Intent intentjadwal = new Intent(MyEventTeamCardActivity.this, MyEventTourRegistedJadwalActivity.class);
//
//                    intentjadwal.putExtra(EXTRA_NAMATOUR, namaTour);
//                    intentjadwal.putExtra(EXTRA_IDTOUR, idtour);
//                    startActivity(intentjadwal);
//
//                }
//            }
//        });

//        btnhasil.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intenthasil=new Intent(MyEventTourRegistedDetailActivity.this,MyEventTourRegistedHasilActivity.class);
//                intenthasil.putExtra(EXTRA_NAMATOUR,namaTour);
//                intenthasil.putExtra(EXTRA_IDTOUR,idtour);
//                startActivity(intenthasil);
//            }
//        });


    }

}
