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

public class MyEventTourHistoryDetailActivity extends AppCompatActivity {

    Button btnhasil,btnjadwal,btninfo;
    //    FloatingActionButton buttonUpload;
    Toolbar toolbar;
    ImageView imageView;
    EditText txt_name;
    Bitmap bitmap, decoded;
    String UPLOAD_URL,BATAL_URL;
    int success,idregiss;
    int PICK_IMAGE_REQUEST = 1;
    int bitmap_size = 60; // range 1 - 100

    private static final String TAG = MyEventTourHistoryActivity.class.getSimpleName();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_event_tour_history_detail);


        Intent intents = getIntent();
        final String namaTours = intents.getStringExtra(EXTRA_NAMATOUR);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(namaTours);


        imageView = (ImageView) findViewById(R.id.imageView);



        Intent intent = getIntent();
        final int idregis = intent.getIntExtra(EXTRA_IDREGIS,0);
        final int idtour = intent.getIntExtra(EXTRA_IDTOUR,0);
        final int idgame = intent.getIntExtra(EXTRA_IDGAME,0);
        final String namaTour = intent.getStringExtra(EXTRA_NAMATOUR);
        final int biaya = intent.getIntExtra(EXTRA_BIAYA,0);
        final String jenis = intent.getStringExtra(EXTRA_JENIS);
        final String tglTour = intent.getStringExtra(EXTRA_TGLTOUR);
        final String namaTim = intent.getStringExtra(EXTRA_NAMATIM);
        final String usrKetua = intent.getStringExtra(EXTRA_USRKETUA);
        final String usrAng1 = intent.getStringExtra(EXTRA_USRANG1);
        final String usrAng2 = intent.getStringExtra(EXTRA_USRANG2);
        final String usrAng3 = intent.getStringExtra(EXTRA_USRANG3);
        final String usrAng4 = intent.getStringExtra(EXTRA_USRANG4);
        final String ignKetua = intent.getStringExtra(EXTRA_IGNKETUA);
        final String ignAng1 = intent.getStringExtra(EXTRA_IGNANG1);
        final String ignAng2 = intent.getStringExtra(EXTRA_IGNANG2);
        final String ignAng3 = intent.getStringExtra(EXTRA_IGNANG3);
        final String ignAng4 = intent.getStringExtra(EXTRA_IGNANG4);

        TextView textViewJenis = findViewById(R.id.tv_meth_jenis);
        TextView textViewNamaTour = findViewById(R.id.tv_meth_namatour);
        TextView textViewTglTour = findViewById(R.id.tv_meth_tgltour);
        TextView textViewBiaya = findViewById(R.id.tv_meth_biaya);
        TextView textViewNamaTim = findViewById(R.id.tv_meth_namatim);
        TextView textViewUsrKetua = findViewById(R.id.tv_meth_usrketua);
        TextView textViewUsrAng1 = findViewById(R.id.tv_meth_usrang1);
        TextView textViewUsrAng2 = findViewById(R.id.tv_meth_usrang2);
        TextView textViewUsrAng3 = findViewById(R.id.tv_meth_usrang3);
        TextView textViewUsrAng4 = findViewById(R.id.tv_meth_usrang4);
        TextView textViewIgnKetua = findViewById(R.id.tv_meth_ignketua);
        TextView textViewIgnAng1 = findViewById(R.id.tv_meth_ignang1);
        TextView textViewIgnAng2 = findViewById(R.id.tv_meth_ignang2);
        TextView textViewIgnAng3 = findViewById(R.id.tv_meth_ignang3);
        TextView textViewIgnAng4 = findViewById(R.id.tv_meth_ignang4);



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



        btnhasil=findViewById(R.id.btn_meth_hasil);
        btnjadwal=findViewById(R.id.btn_meth_jadwal);
        btninfo=findViewById(R.id.btn_meth_info);
        btninfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentinfo=new Intent(MyEventTourHistoryDetailActivity.this,TournamentDetailActivity.class);
                intentinfo.putExtra(MyEventTourFragment.EXTRA_NAMATOUR,namaTour);
                intentinfo.putExtra(EXTRA_IDTOUR,idtour);
                startActivity(intentinfo);
            }
        });

        btnjadwal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (idgame==1) {
                    Intent intentjadwal = new Intent(MyEventTourHistoryDetailActivity.this, MyEventTourRegistedJadwal2Activity.class);

                    intentjadwal.putExtra(MyEventTourFragment.EXTRA_NAMATOUR, namaTour);
                    intentjadwal.putExtra(EXTRA_IDTOUR, idtour);
                    startActivity(intentjadwal);
                }else{
                    Intent intentjadwal = new Intent(MyEventTourHistoryDetailActivity.this, MyEventTourRegistedJadwalActivity.class);

                    intentjadwal.putExtra(MyEventTourFragment.EXTRA_NAMATOUR, namaTour);
                    intentjadwal.putExtra(EXTRA_IDTOUR, idtour);
                    startActivity(intentjadwal);

                }
            }
        });

        btnhasil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intenthasil=new Intent(MyEventTourHistoryDetailActivity.this,MyEventTourRegistedHasilActivity.class);
                intenthasil.putExtra(MyEventTourFragment.EXTRA_NAMATOUR,namaTour);
                intenthasil.putExtra(EXTRA_IDTOUR,idtour);
                startActivity(intenthasil);
            }
        });
    }


}