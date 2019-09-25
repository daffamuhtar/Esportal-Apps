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

public class MyEventWaitingDetailLunasActivity extends AppCompatActivity {

    Button buttonChoose,btninfo, buttonUpload, buttonBatal;
    //    FloatingActionButton buttonUpload;
    Toolbar toolbar;
    ImageView imageView;
    EditText txt_name;
    Bitmap bitmap, decoded;
    String UPLOAD_URL,BATAL_URL;
    int success,idregiss;
    int PICK_IMAGE_REQUEST = 1;
    int bitmap_size = 60; // range 1 - 100

    private static final String TAG = MyEventWaitingDetailLunasActivity.class.getSimpleName();

    /* 10.0.2.2 adalah IP Address localhost Emulator Android Studio. Ganti IP Address tersebut dengan
    IP Address Laptop jika di RUN di HP/Genymotion. HP/Genymotion dan Laptop harus 1 jaringan! */


    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private String KEY_IMAGE = "image";
    private String KEY_IDREGIS = "idregis";

    String tag_json_obj = "json_obj_req";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_event_waiting_detail_lunas);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Detail");


        buttonBatal = (Button) findViewById(R.id.button_metdl_Batal);


        imageView = (ImageView) findViewById(R.id.imageView);



        Intent intent = getIntent();
        final int idregis = intent.getIntExtra(EXTRA_IDREGIS,0);
        final int idtour = intent.getIntExtra(EXTRA_IDTOUR,0);
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

        TextView textViewJenis = findViewById(R.id.tv_mewdl_jenis);
        TextView textViewNamaTour = findViewById(R.id.tv_mewdl_namatour);
        TextView textViewTglTour = findViewById(R.id.tv_mewdl_tgltour);
        TextView textViewBiaya = findViewById(R.id.tv_mewdl_biaya);
        TextView textViewNamaTim = findViewById(R.id.tv_mewdl_namatim);
        TextView textViewUsrKetua = findViewById(R.id.tv_mewdl_usrketua);
        TextView textViewUsrAng1 = findViewById(R.id.tv_mewdl_usrang1);
        TextView textViewUsrAng2 = findViewById(R.id.tv_mewdl_usrang2);
        TextView textViewUsrAng3 = findViewById(R.id.tv_mewdl_usrang3);
        TextView textViewUsrAng4 = findViewById(R.id.tv_mewdl_usrang4);
        TextView textViewIgnKetua = findViewById(R.id.tv_mewdl_ignketua);
        TextView textViewIgnAng1 = findViewById(R.id.tv_mewdl_ignang1);
        TextView textViewIgnAng2 = findViewById(R.id.tv_mewdl_ignang2);
        TextView textViewIgnAng3 = findViewById(R.id.tv_mewdl_ignang3);
        TextView textViewIgnAng4 = findViewById(R.id.tv_mewdl_ignang4);



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


        BATAL_URL = URL+"batalregistour.php";

        idregiss=idregis;

        btninfo=findViewById(R.id.btn_mewdl_info);
        btninfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentinfo=new Intent(MyEventWaitingDetailLunasActivity.this,TournamentDetailActivity.class);
                intentinfo.putExtra(MyEventTourFragment.EXTRA_NAMATOUR,namaTour);
                intentinfo.putExtra(EXTRA_IDTOUR,idtour);
                startActivity(intentinfo);
            }
        });
        buttonBatal.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                batalRegis();
                Toast.makeText(MyEventWaitingDetailLunasActivity.this, "Registrasi dibatalkan", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MyEventWaitingDetailLunasActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }


    private void batalRegis() {
        //menampilkan progress dialog
        final ProgressDialog loading = ProgressDialog.show(this, "Batalkan...", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BATAL_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e(TAG, "Response: " + response.toString());

                        try {
                            JSONObject jObj = new JSONObject(response);
                            success = jObj.getInt(TAG_SUCCESS);

                            if (success == 1) {
                                Log.e("v Add", jObj.toString());

                                Toast.makeText(MyEventWaitingDetailLunasActivity.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();



                                Intent intent = new Intent(MyEventWaitingDetailLunasActivity.this, MainActivity.class);
                                startActivity(intent);

                            } else {
                                Toast.makeText(MyEventWaitingDetailLunasActivity.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        //menghilangkan progress dialog
                        loading.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //menghilangkan progress dialog
                        loading.dismiss();

                        //menampilkan toast
                        Toast.makeText(MyEventWaitingDetailLunasActivity.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                        Log.e(TAG, error.getMessage().toString());

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                //membuat parameters
                Map<String, String> params = new HashMap<String, String>();

                //menambah parameter yang di kirim ke web servis
                params.put(KEY_IDREGIS, String.valueOf(idregiss));

                //kembali ke parameters
                Log.e(TAG, "" + params);
                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(stringRequest, tag_json_obj);
    }

}