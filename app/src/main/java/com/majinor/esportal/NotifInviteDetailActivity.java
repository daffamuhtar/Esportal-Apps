package com.majinor.esportal;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
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

import static com.majinor.esportal.LoginActivity.TAG_USERNAME;
import static com.majinor.esportal.MyEventTourFragment.EXTRA_IDTOUR;
import static com.majinor.esportal.MyEventWaitingActivity.EXTRA_ACCANG1;
import static com.majinor.esportal.MyEventWaitingActivity.EXTRA_ACCANG2;
import static com.majinor.esportal.MyEventWaitingActivity.EXTRA_ACCANG3;
import static com.majinor.esportal.MyEventWaitingActivity.EXTRA_ACCANG4;
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

public class NotifInviteDetailActivity extends AppCompatActivity {

    Button buttonChoose, buttonUpload,btninfo, buttonBatal, acc1,acc2,acc3,acc4,not1,not2,not3,not4,oke1,oke2,oke3,oke4;
    //    FloatingActionButton buttonUpload;
    Toolbar toolbar;
    ImageView imageView;
    EditText txt_name;
    Bitmap bitmap, decoded;
    String accAng1,accAng2,accAng3,accAng4,username;
    int success,idregiss;
    int PICK_IMAGE_REQUEST = 1;
    int bitmap_size = 60; // range 1 - 100
    SharedPreferences sharedpreferences;

    private static final String TAG = NotifInviteDetailActivity.class.getSimpleName();

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
        setContentView(R.layout.activity_notif_invite_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Detail");


        acc1 = (Button) findViewById(R.id.btn_ind_acc1);
        acc2 = (Button) findViewById(R.id.btn_ind_acc2);
        acc3 = (Button) findViewById(R.id.btn_ind_acc3);
        acc4 = (Button) findViewById(R.id.btn_ind_acc4);
        not1 = (Button) findViewById(R.id.btn_ind_not1);
        not2 = (Button) findViewById(R.id.btn_ind_not2);
        not3 = (Button) findViewById(R.id.btn_ind_not3);
        not4 = (Button) findViewById(R.id.btn_ind_not4);
        oke1 = (Button) findViewById(R.id.btn_ind_oke1);
        oke2 = (Button) findViewById(R.id.btn_ind_oke2);
        oke3 = (Button) findViewById(R.id.btn_ind_oke3);
        oke4 = (Button) findViewById(R.id.btn_ind_oke4);

        sharedpreferences = getSharedPreferences(LoginActivity.my_shared_preferences, Context.MODE_PRIVATE);
        username = sharedpreferences.getString(TAG_USERNAME, null);


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

        TextView textViewJenis = findViewById(R.id.tv_ind_jenis);
        TextView textViewNamaTour = findViewById(R.id.tv_ind_namatour);
        TextView textViewTglTour = findViewById(R.id.tv_ind_tgltour);
        TextView textViewBiaya = findViewById(R.id.tv_ind_biaya);
        TextView textViewNamaTim = findViewById(R.id.tv_ind_namatim);
        TextView textViewUsrKetua = findViewById(R.id.tv_ind_usrketua);
        TextView textViewUsrAng1 = findViewById(R.id.tv_ind_usrang1);
        TextView textViewUsrAng2 = findViewById(R.id.tv_ind_usrang2);
        TextView textViewUsrAng3 = findViewById(R.id.tv_ind_usrang3);
        TextView textViewUsrAng4 = findViewById(R.id.tv_ind_usrang4);
        TextView textViewIgnKetua = findViewById(R.id.tv_ind_ignketua);
        TextView textViewIgnAng1 = findViewById(R.id.tv_ind_ignang1);
        TextView textViewIgnAng2 = findViewById(R.id.tv_ind_ignang2);
        TextView textViewIgnAng3 = findViewById(R.id.tv_ind_ignang3);
        TextView textViewIgnAng4 = findViewById(R.id.tv_ind_ignang4);



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


        accAng1 = URL+"accang1.php";
        accAng2 = URL+"accang2.php";
        accAng3 = URL+"accang3.php";
        accAng4 = URL+"accang4.php";

        idregiss=idregis;

        if(username.equals(usrAng1)){
            oke1.setVisibility(View.VISIBLE);
            not1.setVisibility(View.GONE);
        }else {}
        if(accang1==1){
            oke1.setVisibility(View.GONE);
            acc1.setVisibility(View.VISIBLE);
            not1.setVisibility(View.GONE);
        }else{}
        if(username.equals(usrAng2)){
            oke2.setVisibility(View.VISIBLE);
            not2.setVisibility(View.GONE);
        }else {}
        if(accang2==1){
            oke2.setVisibility(View.GONE);
            acc2.setVisibility(View.VISIBLE);
            not2.setVisibility(View.GONE);
        }else{}
        if(username.equals(usrAng3)){
            oke3.setVisibility(View.VISIBLE);
            not3.setVisibility(View.GONE);
        }else {}
        if(accang3==1){
            oke3.setVisibility(View.GONE);
            acc3.setVisibility(View.VISIBLE);
            not3.setVisibility(View.GONE);
        }else{}
        if(username.equals(usrAng4)){
            oke4.setVisibility(View.VISIBLE);
            not4.setVisibility(View.GONE);
        }else {}
        if(accang4==1){
            oke4.setVisibility(View.GONE);
            acc4.setVisibility(View.VISIBLE);
            not4.setVisibility(View.GONE);
        }else{}

        btninfo=findViewById(R.id.btn_ind_info);
        btninfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentinfo=new Intent(NotifInviteDetailActivity.this,TournamentDetailActivity.class);
                intentinfo.putExtra(MyEventTourFragment.EXTRA_NAMATOUR,namaTour);
                intentinfo.putExtra(EXTRA_IDTOUR,idtour);
                startActivity(intentinfo);
            }
        });


        oke1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(NotifInviteDetailActivity.this);
                builder.setMessage("Accept Invitation?")
                        .setCancelable(true)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                accAng1();
                                Toast.makeText(NotifInviteDetailActivity.this, "Accepted", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(NotifInviteDetailActivity.this, MainActivity.class);
                                oke1.setVisibility(View.GONE);
                                not1.setVisibility(View.GONE);
                                acc1.setVisibility(View.VISIBLE);
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

        oke2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(NotifInviteDetailActivity.this);
                builder.setMessage("Accept Invitation?")
                        .setCancelable(true)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                accAng1();
                                Toast.makeText(NotifInviteDetailActivity.this, "Accepted", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(NotifInviteDetailActivity.this, MainActivity.class);
                                oke2.setVisibility(View.GONE);
                                not2.setVisibility(View.GONE);
                                acc2.setVisibility(View.VISIBLE);
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

        oke3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(NotifInviteDetailActivity.this);
                builder.setMessage("Accept Invitation?")
                        .setCancelable(true)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                accAng3();
                                Toast.makeText(NotifInviteDetailActivity.this, "Accepted", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(NotifInviteDetailActivity.this, MainActivity.class);
                                oke3.setVisibility(View.GONE);
                                not3.setVisibility(View.GONE);
                                acc3.setVisibility(View.VISIBLE);
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

        oke4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(NotifInviteDetailActivity.this);
                builder.setMessage("Accept Invitation?")
                        .setCancelable(true)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                accAng4();
                                Toast.makeText(NotifInviteDetailActivity.this, "Accepted", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(NotifInviteDetailActivity.this, MainActivity.class);
                                oke4.setVisibility(View.GONE);
                                not4.setVisibility(View.GONE);
                                acc4.setVisibility(View.VISIBLE);
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



    }

    private void accAng1() {
        //menampilkan progress dialog
        final ProgressDialog loading = ProgressDialog.show(this, "Accepting...", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, accAng1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e(TAG, "Response: " + response.toString());

                        try {
                            JSONObject jObj = new JSONObject(response);
                            success = jObj.getInt(TAG_SUCCESS);

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
                        Toast.makeText(NotifInviteDetailActivity.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
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
    private void accAng2() {
        //menampilkan progress dialog
        final ProgressDialog loading = ProgressDialog.show(this, "Accepting...", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, accAng2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e(TAG, "Response: " + response.toString());

                        try {
                            JSONObject jObj = new JSONObject(response);
                            success = jObj.getInt(TAG_SUCCESS);

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
                        Toast.makeText(NotifInviteDetailActivity.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
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
    private void accAng3() {
        //menampilkan progress dialog
        final ProgressDialog loading = ProgressDialog.show(this, "Accepting...", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, accAng3,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e(TAG, "Response: " + response.toString());

                        try {
                            JSONObject jObj = new JSONObject(response);
                            success = jObj.getInt(TAG_SUCCESS);

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
                        Toast.makeText(NotifInviteDetailActivity.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
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
    private void accAng4() {
        //menampilkan progress dialog
        final ProgressDialog loading = ProgressDialog.show(this, "Accepting...", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, accAng4,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e(TAG, "Response: " + response.toString());

                        try {
                            JSONObject jObj = new JSONObject(response);
                            success = jObj.getInt(TAG_SUCCESS);

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
                        Toast.makeText(NotifInviteDetailActivity.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
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
