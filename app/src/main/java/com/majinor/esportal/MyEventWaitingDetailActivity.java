package com.majinor.esportal;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;
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
import static com.majinor.esportal.MyEventWaitingActivity.EXTRA_ACCANG1;
import static com.majinor.esportal.MyEventWaitingActivity.EXTRA_ACCANG2;
import static com.majinor.esportal.MyEventWaitingActivity.EXTRA_ACCANG3;
import static com.majinor.esportal.MyEventWaitingActivity.EXTRA_ACCANG4;
import static com.majinor.esportal.MyEventWaitingActivity.EXTRA_IDREGIS;
import static com.majinor.esportal.MyEventWaitingActivity.EXTRA_TGLTOUR;
import static com.majinor.esportal.MyEventWaitingActivity.EXTRA_BIAYA;
import static com.majinor.esportal.MyEventWaitingActivity.EXTRA_JENIS;
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

public class MyEventWaitingDetailActivity extends AppCompatActivity {

    Button buttonChoose, buttonUpload,btninfo, buttonBatal, acc1,acc2,acc3,acc4,not1,not2,not3,not4,
            save,cancles;
//    FloatingActionButton buttonUpload;
    Toolbar toolbar;
    ImageView imageView;
    EditText tveNamatim,tveiKetua,tveuKetua,tveuAng1,tveiAng1,tveuAng2,tveiAng2,tveuAng3,tveiAng3,tveuAng4,tveiAng4;
    EditText txt_name;
    Bitmap bitmap, decoded;
    String UPLOAD_URL,BATAL_URL,EDIT_URL;
    AlertDialog editTeamDialog;
    int success,idregiss;
    int PICK_IMAGE_REQUEST = 1;
    int bitmap_size = 60; // range 1 - 100

    private static final String TAG = MyEventWaitingDetailActivity.class.getSimpleName();

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
        setContentView(R.layout.activity_my_event_waiting_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Detail");


        buttonChoose = (Button) findViewById(R.id.buttonChoose);
        buttonUpload = (Button) findViewById(R.id.buttonUpload);
        buttonBatal = (Button) findViewById(R.id.buttonBatal);
        acc1 = (Button) findViewById(R.id.btn_mewd_acc1);
        acc2 = (Button) findViewById(R.id.btn_mewd_acc2);
        acc3 = (Button) findViewById(R.id.btn_mewd_acc3);
        acc4 = (Button) findViewById(R.id.btn_mewd_acc4);
        not1 = (Button) findViewById(R.id.btn_mewd_not1);
        not2 = (Button) findViewById(R.id.btn_mewd_not2);
        not3 = (Button) findViewById(R.id.btn_mewd_not3);
        not4 = (Button) findViewById(R.id.btn_mewd_not4);


        imageView = (ImageView) findViewById(R.id.imageView);



        Intent intent = getIntent();
        final int idregis = intent.getIntExtra(EXTRA_IDREGIS,0);
        final int idtour = intent.getIntExtra(EXTRA_IDTOUR,0);
        final String namaTour = intent.getStringExtra(EXTRA_NAMATOUR);
        final int biaya = intent.getIntExtra(EXTRA_BIAYA,0);
        final int accang1 = intent.getIntExtra(EXTRA_ACCANG1,0);
        final int accang2 = intent.getIntExtra(EXTRA_ACCANG2,0);
        final int accang3 = intent.getIntExtra(EXTRA_ACCANG3,0);
        final int accang4 = intent.getIntExtra(EXTRA_ACCANG4,0);
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

        TextView textViewJenis = findViewById(R.id.tv_mewd_jenis);
        TextView textViewNamaTour = findViewById(R.id.tv_mewd_namatour);
        TextView textViewTglTour = findViewById(R.id.tv_mewd_tgltour);
        TextView textViewBiaya = findViewById(R.id.tv_mewd_biaya);
        TextView textViewNamaTim = findViewById(R.id.tv_mewd_namatim);
        TextView textViewUsrKetua = findViewById(R.id.tv_mewd_usrketua);
        final TextView textViewUsrAng1 = findViewById(R.id.tv_mewd_usrang1);
        TextView textViewUsrAng2 = findViewById(R.id.tv_mewd_usrang2);
        TextView textViewUsrAng3 = findViewById(R.id.tv_mewd_usrang3);
        TextView textViewUsrAng4 = findViewById(R.id.tv_mewd_usrang4);
        TextView textViewIgnKetua = findViewById(R.id.tv_mewd_ignketua);
        final TextView textViewIgnAng1 = findViewById(R.id.tv_mewd_ignang1);
        TextView textViewIgnAng2 = findViewById(R.id.tv_mewd_ignang2);
        TextView textViewIgnAng3 = findViewById(R.id.tv_mewd_ignang3);
        TextView textViewIgnAng4 = findViewById(R.id.tv_mewd_ignang4);



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


        UPLOAD_URL = URL+"upload.php?idregis="+idregis;
        BATAL_URL = URL+"batalregistour.php";
        EDIT_URL = URL+"editteam.php";

        idregiss=idregis;

        if(accang1==1){
            acc1.setVisibility(View.VISIBLE);
            not1.setVisibility(View.GONE);
        }else{}
        if(accang2==1){
            acc2.setVisibility(View.VISIBLE);
            not2.setVisibility(View.GONE);
        }else{}
        if(accang3==1){
            acc3.setVisibility(View.VISIBLE);
            not3.setVisibility(View.GONE);
        }else{}
        if(accang4==1){
            acc4.setVisibility(View.VISIBLE);
            not4.setVisibility(View.GONE);
        }else{}

        btninfo=findViewById(R.id.btn_mewd_info);
        btninfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentinfo=new Intent(MyEventWaitingDetailActivity.this,TournamentDetailActivity.class);
                intentinfo.putExtra(MyEventTourFragment.EXTRA_NAMATOUR,namaTour);
                intentinfo.putExtra(EXTRA_IDTOUR,idtour);
                startActivity(intentinfo);
            }
        });

        buttonChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });
        buttonUpload.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(imageView.getDrawable()==null){
                    buttonChoose.setError("Pilih foto");
                }else if(accang1==0|accang2==0|accang3==0|accang4==0){
                    Toast.makeText(MyEventWaitingDetailActivity.this, "Seluruh Anggota harus konfirmasi terlebih dahulu", Toast.LENGTH_SHORT).show();
                }
                else {uploadImage();}
            }
        });
        buttonBatal.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MyEventWaitingDetailActivity.this);
                builder.setMessage("Batalkan Registrasi?")
                        .setCancelable(true)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                batalRegis();
                                Toast.makeText(MyEventWaitingDetailActivity.this, "Registrasi dibatalkan", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(MyEventWaitingDetailActivity.this, MainActivity.class);
                                finish();
                                startActivity(intent);

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();

            }
        });

        Button buttonEdit=(Button)findViewById(R.id.btn_mewd_edit);
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTeamDialog = new Builder(MyEventWaitingDetailActivity.this).create();
                View etView=getLayoutInflater().inflate(R.layout.dialog_edit_team,null);
                editTeamDialog.setView(etView);
                editTeamDialog.show();


                TextView textViewJenis = findViewById(R.id.tv_mewd_jenis);
                TextView textViewNamaTour = findViewById(R.id.tv_mewd_namatour);
                TextView textViewTglTour = findViewById(R.id.tv_mewd_tgltour);
                TextView textViewBiaya = findViewById(R.id.tv_mewd_biaya);
                TextView textViewNamaTim = findViewById(R.id.tv_mewd_namatim);
                TextView textViewUsrKetua = findViewById(R.id.tv_mewd_usrketua);
                TextView textViewUsrAng1 = findViewById(R.id.tv_mewd_usrang1);
                TextView textViewUsrAng2 = findViewById(R.id.tv_mewd_usrang2);
                TextView textViewUsrAng3 = findViewById(R.id.tv_mewd_usrang3);
                TextView textViewUsrAng4 = findViewById(R.id.tv_mewd_usrang4);
                TextView textViewIgnKetua = findViewById(R.id.tv_mewd_ignketua);
                TextView textViewIgnAng1 = findViewById(R.id.tv_mewd_ignang1);
                TextView textViewIgnAng2 = findViewById(R.id.tv_mewd_ignang2);
                TextView textViewIgnAng3 = findViewById(R.id.tv_mewd_ignang3);
                TextView textViewIgnAng4 = findViewById(R.id.tv_mewd_ignang4);

                String snamatim=textViewNamaTim.getText().toString();
                String susrketua=textViewUsrKetua.getText().toString();
                String signketua=textViewIgnKetua.getText().toString();
                String susrang1=textViewUsrAng1.getText().toString();
                String signang1=textViewIgnAng1.getText().toString();
                String susrang2=textViewUsrAng2.getText().toString();
                String signang2=textViewIgnAng2.getText().toString();
                String susrang3=textViewUsrAng3.getText().toString();
                String signang3=textViewIgnAng3.getText().toString();
                String susrang4=textViewUsrAng4.getText().toString();
                String signang4=textViewIgnAng4.getText().toString();

                tveNamatim=etView.findViewById(R.id.tv_det_namatim);
                tveuKetua=etView.findViewById(R.id.tv_det_usrketua);
                tveiKetua=etView.findViewById(R.id.tv_det_ignketua);
                tveuAng1=etView.findViewById(R.id.tv_det_usrang1);
                tveiAng1=etView.findViewById(R.id.tv_det_ignang1);
                tveuAng2=etView.findViewById(R.id.tv_det_usrang2);
                tveiAng2=etView.findViewById(R.id.tv_det_ignang2);
                tveuAng3=etView.findViewById(R.id.tv_det_usrang3);
                tveiAng3=etView.findViewById(R.id.tv_det_ignang3);
                tveuAng4=etView.findViewById(R.id.tv_det_usrang4);
                tveiAng4=etView.findViewById(R.id.tv_det_ignang4);

                tveuKetua.setEnabled(false);

                tveNamatim.setText(snamatim);
                tveuKetua.setText(susrketua);
                tveiKetua.setText(signketua);
                tveuAng1.setText(susrang1);
                tveiAng1.setText(signang1);
                tveuAng2.setText(susrang2);
                tveiAng2.setText(signang2);
                tveuAng3.setText(susrang3);
                tveiAng3.setText(signang3);
                tveuAng4.setText(susrang4);
                tveiAng4.setText(signang4);

                final String namatim=tveNamatim.getText().toString();
                final String ignketua=tveiKetua.getText().toString();
                final String usrang1=tveuAng1.getText().toString();
                final String ignang1=tveiAng1.getText().toString();
                final String usrang2=tveuAng2.getText().toString();
                final String ignang2=tveiAng2.getText().toString();
                final String usrang3=tveuAng3.getText().toString();
                final String ignang3=tveiAng3.getText().toString();
                final String usrang4=tveuAng4.getText().toString();
                final String ignang4=tveiAng4.getText().toString();

                save=etView.findViewById(R.id.btn_det_save);
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (namatim.isEmpty()){
                            tveNamatim.setError("Nama tim tidakboleh kosong");
                        }  else if (ignketua.isEmpty()){
                            tveiKetua.setError("Nickname Ketua tidakboleh kosong");
                        }  else if (usrang1.isEmpty()){
                            tveuAng1.setError("Masukkan Username Anggota 1");
                        }  else if (ignang1.isEmpty()){
                            tveiAng1.setError("Masukkan Nickname Anggota 1");
                        }  else if (usrang2.isEmpty()){
                            tveuAng2.setError("Masukkan Username Anggota 2");
                        }  else if (ignang2.isEmpty()){
                            tveiAng2.setError("Masukkan Nickname Anggota 2");
                        }  else if (usrang3.isEmpty()){
                            tveuAng3.setError("Masukkan Username Anggota 3");
                        }  else if (ignang3.isEmpty()){
                            tveiAng3.setError("Masukkan Nickname Anggota 3");
                        }  else if (usrang4.isEmpty()){
                            tveuAng4.setError("Masukkan Username Anggota 4");
                        }  else if (ignang4.isEmpty()){
                            tveiAng4.setError("Masukkan Nickname Anggota 4");
                        }  else {

                            editTeam();
                        }
                    }
                });
                cancles=etView.findViewById(R.id.btn_det_cancle);
                cancles.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editTeamDialog.dismiss();
                    }
                });

            }
        });
    }
//
//    public void openDialog() {
//        EditTeamDialog editTeamDialog = new EditTeamDialog();
//        editTeamDialog.show(getSupportFragmentManager(), "example dialog");
////        editTeamDialog.setB
////        editTeamDialog.getWindow().setBackgroundDrawableResource(android.R.color.background_dark);
//    }
//
//    @Override
//    public void applyTexts(String username, String password) {
//
//        TextView textViewNamaTim = findViewById(R.id.tv_mewd_namatim);
//        TextView textViewUsrKetua = findViewById(R.id.tv_mewd_usrketua);
//        TextView textViewUsrAng1 = findViewById(R.id.tv_mewd_usrang1);
//        TextView textViewUsrAng2 = findViewById(R.id.tv_mewd_usrang2);
//        TextView textViewUsrAng3 = findViewById(R.id.tv_mewd_usrang3);
//        TextView textViewUsrAng4 = findViewById(R.id.tv_mewd_usrang4);
//        TextView textViewIgnKetua = findViewById(R.id.tv_mewd_ignketua);
//        TextView textViewIgnAng1 = findViewById(R.id.tv_mewd_ignang1);
//        TextView textViewIgnAng2 = findViewById(R.id.tv_mewd_ignang2);
//        TextView textViewIgnAng3 = findViewById(R.id.tv_mewd_ignang3);
//        TextView textViewIgnAng4 = findViewById(R.id.tv_mewd_ignang4);
//
//        textViewUsrAng1.setText(username);
//        textViewIgnAng1.setText(password);
//    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, bitmap_size, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void uploadImage() {
        //menampilkan progress dialog
        final ProgressDialog loading = ProgressDialog.show(this, "Uploading...", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e(TAG, "Response: " + response.toString());

                        try {
                            JSONObject jObj = new JSONObject(response);
                            success = jObj.getInt(TAG_SUCCESS);

                            if (success == 1) {
                                Log.e("v Add", jObj.toString());


                                Toast.makeText(MyEventWaitingDetailActivity.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

                                kosong();


                                Intent intent = new Intent(MyEventWaitingDetailActivity.this, MainActivity.class);
                                startActivity(intent);

                            } else {
                                Toast.makeText(MyEventWaitingDetailActivity.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
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
                        Toast.makeText(MyEventWaitingDetailActivity.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                        Log.e(TAG, error.getMessage().toString());

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                //membuat parameters
                Map<String, String> params = new HashMap<String, String>();

                //menambah parameter yang di kirim ke web servis
                params.put(KEY_IMAGE, getStringImage(decoded));

                //kembali ke parameters
                Log.e(TAG, "" + params);
                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(stringRequest, tag_json_obj);
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }


    private void editTeam() {

        final String namatim=tveNamatim.getText().toString();
        final String ignketua=tveiKetua.getText().toString();
        final String usrang1=tveuAng1.getText().toString();
        final String ignang1=tveiAng1.getText().toString();
        final String usrang2=tveuAng2.getText().toString();
        final String ignang2=tveiAng2.getText().toString();
        final String usrang3=tveuAng3.getText().toString();
        final String ignang3=tveiAng3.getText().toString();
        final String usrang4=tveuAng4.getText().toString();
        final String ignang4=tveiAng4.getText().toString();
        //menampilkan progress dialog
        final ProgressDialog loading = ProgressDialog.show(this, "Uploading...", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, EDIT_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e(TAG, "Response: " + response.toString());

                        try {
                            JSONObject jObj = new JSONObject(response);
                            success = jObj.getInt(TAG_SUCCESS);

                            if (success == 1) {
                                Log.e("v Add", jObj.toString());

                                TextView textViewNamaTim = findViewById(R.id.tv_mewd_namatim);
                                TextView textViewUsrKetua = findViewById(R.id.tv_mewd_usrketua);
                                TextView textViewUsrAng1 = findViewById(R.id.tv_mewd_usrang1);
                                TextView textViewUsrAng2 = findViewById(R.id.tv_mewd_usrang2);
                                TextView textViewUsrAng3 = findViewById(R.id.tv_mewd_usrang3);
                                TextView textViewUsrAng4 = findViewById(R.id.tv_mewd_usrang4);
                                TextView textViewIgnKetua = findViewById(R.id.tv_mewd_ignketua);
                                TextView textViewIgnAng1 = findViewById(R.id.tv_mewd_ignang1);
                                TextView textViewIgnAng2 = findViewById(R.id.tv_mewd_ignang2);
                                TextView textViewIgnAng3 = findViewById(R.id.tv_mewd_ignang3);
                                TextView textViewIgnAng4 = findViewById(R.id.tv_mewd_ignang4);

                                if(!textViewUsrAng1.getText().toString().equals(usrang1)){
                                    acc1.setVisibility(View.GONE);
                                    not1.setVisibility(View.VISIBLE);
                                }else{}
                                if(!textViewUsrAng2.getText().toString().equals(usrang2)){
                                    acc2.setVisibility(View.GONE);
                                    not2.setVisibility(View.VISIBLE);
                                }else{}
                                if(!textViewUsrAng3.getText().toString().equals(usrang3)){
                                    acc3.setVisibility(View.GONE);
                                    not3.setVisibility(View.VISIBLE);
                                }else{}
                                if(!textViewUsrAng4.getText().toString().equals(usrang4)){
                                    acc4.setVisibility(View.GONE);
                                    not4.setVisibility(View.VISIBLE);
                                }else{}

                                textViewNamaTim.setText(namatim);
                                textViewIgnKetua.setText(ignketua);
                                textViewUsrAng1.setText(usrang1);
                                textViewIgnAng1.setText(ignang1);
                                textViewUsrAng2.setText(usrang2);
                                textViewIgnAng2.setText(ignang2);
                                textViewUsrAng3.setText(usrang3);
                                textViewIgnAng3.setText(ignang3);
                                textViewUsrAng4.setText(usrang4);
                                textViewIgnAng4.setText(ignang4);



                                Toast.makeText(MyEventWaitingDetailActivity.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

                                editTeamDialog.dismiss();



                            } else {
                                Toast.makeText(MyEventWaitingDetailActivity.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
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
                        Toast.makeText(MyEventWaitingDetailActivity.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                        Log.e(TAG, error.getMessage().toString());

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                //membuat parameters

                Map<String, String> params = new HashMap<String, String>();

                //menambah parameter yang di kirim ke web servis
                params.put(KEY_IDREGIS, String.valueOf(idregiss));
                params.put("namatim", namatim);
                params.put("ignketua", ignketua);
                params.put("usrang1", usrang1);
                params.put("ignang1", ignang1);
                params.put("usrang2", usrang2);
                params.put("ignang2", ignang2);
                params.put("usrang3", usrang3);
                params.put("ignang3", ignang3);
                params.put("usrang4", usrang4);
                params.put("ignang4", ignang4);

                //kembali ke parameters
                Log.e(TAG, "" + params);
                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(stringRequest, tag_json_obj);
    }

    private void batalRegis() {
        //menampilkan progress dialog
        final ProgressDialog loading = ProgressDialog.show(this, "Uploading...", "Please wait...", false, false);
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

                                Toast.makeText(MyEventWaitingDetailActivity.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

                                kosong();



                            } else {
                                Toast.makeText(MyEventWaitingDetailActivity.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
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
                        Toast.makeText(MyEventWaitingDetailActivity.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //mengambil fambar dari Gallery
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                // 512 adalah resolusi tertinggi setelah image di resize, bisa di ganti.
                setToImageView(getResizedBitmap(bitmap, 1024));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void kosong() {
        imageView.setImageResource(0);
    }

    private void setToImageView(Bitmap bmp) {
        //compress image
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, bitmap_size, bytes);
        decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(bytes.toByteArray()));

        //menampilkan gambar yang dipilih dari camera/gallery ke ImageView
        imageView.setImageBitmap(decoded);
    }

    // fungsi resize image
    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

}