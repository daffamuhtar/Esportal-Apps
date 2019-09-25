package com.majinor.esportal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.majinor.esportal.app.AppController;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.majinor.esportal.MyEventTourFragment.EXTRA_IDGAME;
import static com.majinor.esportal.MyEventTourFragment.EXTRA_IDTOUR;
import static com.majinor.esportal.MyEventWaitingActivity.EXTRA_IDREGIS;
import static com.majinor.esportal.MyEventWaitingActivity.EXTRA_TGLTM;
import static com.majinor.esportal.MyEventWaitingActivity.EXTRA_TGLDAFTAR;
import static com.majinor.esportal.MyEventWaitingActivity.EXTRA_TGLTOUR;
import static com.majinor.esportal.MyEventWaitingActivity.EXTRA_BIAYA;
import static com.majinor.esportal.MyEventWaitingActivity.EXTRA_JENIS;
import static com.majinor.esportal.MyEventWaitingActivity.EXTRA_FOTO;
import static com.majinor.esportal.MyEventWaitingActivity.EXTRA_NAMATOUR;
import static com.majinor.esportal.MyEventWaitingActivity.EXTRA_NAMATIM;
import static com.majinor.esportal.MyEventWaitingActivity.EXTRA_IGNKETUA;
import static com.majinor.esportal.MyEventWaitingActivity.EXTRA_IGNANG1;
import static com.majinor.esportal.MyEventWaitingActivity.EXTRA_IGNANG2;
import static com.majinor.esportal.MyEventWaitingActivity.EXTRA_IGNANG3;
import static com.majinor.esportal.MyEventWaitingActivity.EXTRA_IGNANG4;
import static com.majinor.esportal.MyEventWaitingActivity.EXTRA_USRKETUA;
import static com.majinor.esportal.MyEventWaitingActivity.EXTRA_USRANG1;
import static com.majinor.esportal.MyEventWaitingActivity.EXTRA_USRANG2;
import static com.majinor.esportal.MyEventWaitingActivity.EXTRA_USRANG3;
import static com.majinor.esportal.MyEventWaitingActivity.EXTRA_USRANG4;
import static com.majinor.esportal.Server.URL;
import static com.majinor.esportal.TournamentActivity.EXTRA_USERNAME;

public class MyEventTourRegistedDetailActivity extends AppCompatActivity {

    Button btnhasil,btnjadwal,btninfo,btncard;
    //    FloatingActionButton buttonUpload;
    Toolbar toolbar;
    ImageView imageView;
    EditText txt_name;
    Bitmap bitmap, decoded;
    String UPLOAD_URL,BATAL_URL;
    int success,idregiss;
    int PICK_IMAGE_REQUEST = 1;
    int bitmap_size = 60; // range 1 - 100

    private static final String TAG = MyEventTourRegistedDetailActivity.class.getSimpleName();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_event_tour_registed_detail);


        Intent intents = getIntent();
        final String namaTours = intents.getStringExtra(MyEventTourFragment.EXTRA_NAMATOUR);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(namaTours);


        imageView = (ImageView) findViewById(R.id.imageView);



        Intent intent = getIntent();
//        final int idregis = intent.getIntExtra(EXTRA_IDREGIS,0);
//        final int idtour = intent.getIntExtra(EXTRA_IDTOUR,0);
//        final int idgame = intent.getIntExtra(EXTRA_IDGAME,0);
//        final String namaTour = intent.getStringExtra(EXTRA_NAMATOUR);
        final int biaya = intent.getIntExtra(MyEventTourFragment.EXTRA_BIAYA,0);
        final String jenis = intent.getStringExtra(MyEventTourFragment.EXTRA_JENIS);
        final String tglTour = intent.getStringExtra(MyEventTourFragment.EXTRA_TGLTOUR);
//        final String namaTim = intent.getStringExtra(EXTRA_NAMATIM);
//        final String usrKetua = intent.getStringExtra(EXTRA_USRKETUA);
//        final String usrAng1 = intent.getStringExtra(EXTRA_USRANG1);
//        final String usrAng2 = intent.getStringExtra(EXTRA_USRANG2);
//        final String usrAng3 = intent.getStringExtra(EXTRA_USRANG3);
//        final String usrAng4 = intent.getStringExtra(EXTRA_USRANG4);
//        final String ignKetua = intent.getStringExtra(EXTRA_IGNKETUA);
//        final String ignAng1 = intent.getStringExtra(EXTRA_IGNANG1);
//        final String ignAng2 = intent.getStringExtra(EXTRA_IGNANG2);
//        final String ignAng3 = intent.getStringExtra(EXTRA_IGNANG3);
//        final String ignAng4 = intent.getStringExtra(EXTRA_IGNANG4);
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

        TextView textViewJenis = findViewById(R.id.tv_metri_jenis);
        TextView textViewNamaTour = findViewById(R.id.tv_metri_namatour);
        TextView textViewTglTour = findViewById(R.id.tv_metri_tgltour);
        TextView textViewBiaya = findViewById(R.id.tv_metri_biaya);
        TextView textViewNamaTim = findViewById(R.id.tv_metri_namatim);
        TextView textViewUsrKetua = findViewById(R.id.tv_metri_usrketua);
        TextView textViewUsrAng1 = findViewById(R.id.tv_metri_usrang1);
        TextView textViewUsrAng2 = findViewById(R.id.tv_metri_usrang2);
        TextView textViewUsrAng3 = findViewById(R.id.tv_metri_usrang3);
        TextView textViewUsrAng4 = findViewById(R.id.tv_metri_usrang4);
        TextView textViewIgnKetua = findViewById(R.id.tv_metri_ignketua);
        TextView textViewIgnAng1 = findViewById(R.id.tv_metri_ignang1);
        TextView textViewIgnAng2 = findViewById(R.id.tv_metri_ignang2);
        TextView textViewIgnAng3 = findViewById(R.id.tv_metri_ignang3);
        TextView textViewIgnAng4 = findViewById(R.id.tv_metri_ignang4);



        textViewNamaTour.setText(namaTour);
        textViewJenis.setText(jenis);
        textViewTglTour.setText(tglTour);
        textViewBiaya.setText("Rp. "+biaya);
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



//        btnhasil=findViewById(R.id.btn_metri_hasil);
        btnjadwal=findViewById(R.id.btn_metri_jadwal);
        btninfo=findViewById(R.id.btn_metri_info);
        btncard=findViewById(R.id.btn_metri_card);
        btninfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentinfo=new Intent(MyEventTourRegistedDetailActivity.this,TournamentDetailActivity.class);
                intentinfo.putExtra(MyEventTourFragment.EXTRA_NAMATOUR,namaTour);
                intentinfo.putExtra(EXTRA_IDTOUR,idtour);
                startActivity(intentinfo);
            }
        });
        btncard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailIntent = new Intent(MyEventTourRegistedDetailActivity.this, MyEventTeamCardActivity.class);
//                detailIntent.putExtra(EXTRA_FOTO, imageView);
                detailIntent.putExtra(MyEventTourFragment.EXTRA_IDREGIS, idregis);
                detailIntent.putExtra(MyEventTourFragment.EXTRA_IDTOUR, idtour);
                detailIntent.putExtra(MyEventTourFragment.EXTRA_IDGAME, idgame);
                detailIntent.putExtra(MyEventTourFragment.EXTRA_NAMATOUR, namaTour);
                detailIntent.putExtra(MyEventTourFragment.EXTRA_JENIS, jenis);
                detailIntent.putExtra(MyEventTourFragment.EXTRA_BIAYA, biaya);
//                detailIntent.putExtra(EXTRA_TGLTM, tglTm);
                detailIntent.putExtra(MyEventTourFragment.EXTRA_TGLTOUR, tglTour);
//                detailIntent.putExtra(EXTRA_TGLDAFTAR, tg);
                detailIntent.putExtra(MyEventTourFragment.EXTRA_NAMATIM, namaTim);
                detailIntent.putExtra(MyEventTourFragment.EXTRA_USRKETUA, usrKetua);
                detailIntent.putExtra(MyEventTourFragment.EXTRA_USRANG1, usrAng1);
                detailIntent.putExtra(MyEventTourFragment.EXTRA_USRANG2, usrAng2);
                detailIntent.putExtra(MyEventTourFragment.EXTRA_USRANG3, usrAng3);
                detailIntent.putExtra(MyEventTourFragment.EXTRA_USRANG4, usrAng4);
                detailIntent.putExtra(MyEventTourFragment.EXTRA_IGNKETUA, ignKetua);
                detailIntent.putExtra(MyEventTourFragment.EXTRA_IGNANG1, ignAng1);
                detailIntent.putExtra(MyEventTourFragment.EXTRA_IGNANG2, ignAng2);
                detailIntent.putExtra(MyEventTourFragment.EXTRA_IGNANG3, ignAng3);
                detailIntent.putExtra(MyEventTourFragment.EXTRA_IGNANG4, ignAng3);

                startActivity(detailIntent);
            }
        });

        btnjadwal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (idgame==1) {
                    Intent intentjadwal = new Intent(MyEventTourRegistedDetailActivity.this, MyEventTourRegistedJadwal2Activity.class);

                    intentjadwal.putExtra(MyEventTourFragment.EXTRA_NAMATOUR, namaTour);
                    intentjadwal.putExtra(EXTRA_IDTOUR, idtour);
                    startActivity(intentjadwal);
                }else{
                    Intent intentjadwal = new Intent(MyEventTourRegistedDetailActivity.this, MyEventTourRegistedJadwalActivity.class);

                    intentjadwal.putExtra(MyEventTourFragment.EXTRA_NAMATOUR, namaTour);
                    intentjadwal.putExtra(EXTRA_IDTOUR, idtour);
                    startActivity(intentjadwal);

                }
            }
        });
//
//        btnhasil.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intenthasil=new Intent(MyEventTourRegistedDetailActivity.this,MyEventTourRegistedHasilActivity.class);
//                intenthasil.putExtra(MyEventTourFragment.EXTRA_NAMATOUR,namaTour);
//                intenthasil.putExtra(EXTRA_IDTOUR,idtour);
//                startActivity(intenthasil);
//            }
//        });
    }


}