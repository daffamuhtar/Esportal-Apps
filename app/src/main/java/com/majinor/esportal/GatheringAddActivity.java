package com.majinor.esportal;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static com.majinor.esportal.LoginActivity.TAG_ID;
import static com.majinor.esportal.Server.URL;

public class GatheringAddActivity extends AppCompatActivity {

    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;
    EditText txt_tgl, txt_nama,txt_desk,txt_tempat,txt_htm;
    Button buttonChoose, buttonCreate;
    ImageView imageView;
    Bitmap bitmap, decoded;
    String CREATE_URL,id;
    int success,idregiss;
    int PICK_IMAGE_REQUEST = 1;
    int bitmap_size = 60; // range 1 - 100
    SharedPreferences sharedPreferences;


    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    private String KEY_IMAGE = "image";
    private String KEY_NAMAGATH = "namagath";
    private String KEY_DESK = "desk";
    private String KEY_TGLGATH = "tglgath";
    private String KEY_TEMPAT = "tempat";
    private String KEY_HTM = "biaya";
    private String KEY_IDUSER = "iduser";

    String tag_json_obj = "json_obj_req";

    private static final String TAG = GatheringAddActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gathering_add);

        buttonChoose = (Button) findViewById(R.id.buttonChooses);
        buttonCreate = (Button) findViewById(R.id.btn_ag_create);


        imageView = (ImageView) findViewById(R.id.imageViews);

        txt_tgl = (EditText) findViewById(R.id.txt_ag_tgl);
        txt_nama= (EditText) findViewById(R.id.txt_ag_namaevent);
        txt_desk= (EditText) findViewById(R.id.txt_ag_desk);
        txt_tempat= (EditText) findViewById(R.id.txt_ag_tempat);
        txt_htm= (EditText) findViewById(R.id.txt_ag_biaya);

        sharedPreferences=getSharedPreferences(LoginActivity.my_shared_preferences, Context.MODE_PRIVATE);
        id=sharedPreferences.getString(TAG_ID,null);

        myCalendar = Calendar.getInstance();
        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        txt_tgl.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub


                DatePickerDialog datePickerDialog=new DatePickerDialog(GatheringAddActivity.this,date,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));


                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
                datePickerDialog.show();
            }
        });

        CREATE_URL = URL+"addeventgath.php";
        buttonChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });


        buttonCreate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String nama = txt_nama.getText().toString();
                String desk = txt_desk.getText().toString();
                String tmpt = txt_tempat.getText().toString();
                String tgl = txt_tgl.getText().toString();
                String htm = txt_htm.getText().toString();

                if(nama.matches("")){
                    txt_nama.setError("Masukkan nama event");
                }else if(imageView.getDrawable()==null){
                    buttonChoose.setError("Pilih foto");
                }else if(desk.matches("")){
                    txt_desk.setError("Masukkan deskripsi event");
                }else if(tgl.matches("")){
                    txt_tgl.setError("Masukkan tanggal event");
                }else if(tmpt.matches("")){
                    txt_tempat.setError("Masukkan lokasi event");
                }else if(htm.matches("")){
                    txt_htm.setError("Masukkan Biaya event atau '0' apabila gratis");
                }else{
                uploadImage();
                }
            }
        });



    }
    private void updateLabel() {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        txt_tgl.setText(sdf.format(myCalendar.getTime()));
    }


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
        StringRequest stringRequest = new StringRequest(Request.Method.POST, CREATE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e(TAG, "Response: " + response.toString());

                        try {
                            JSONObject jObj = new JSONObject(response);
                            success = jObj.getInt(TAG_SUCCESS);

                            if (success == 1) {
                                Log.e("v Add", jObj.toString());

                                Toast.makeText(GatheringAddActivity.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

                                kosong();


                                Intent intent = new Intent(GatheringAddActivity.this, MainActivity.class);
                                startActivity(intent);

                            } else {
                                Toast.makeText(GatheringAddActivity.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
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
                        Toast.makeText(GatheringAddActivity.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                        Log.e(TAG, error.getMessage().toString());

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                //membuat parameters
                Map<String, String> params = new HashMap<String, String>();

                //menambah parameter yang di kirim ke web servis
                params.put(KEY_IMAGE, getStringImage(decoded));
                params.put(KEY_NAMAGATH, txt_nama.getText().toString().trim());
                params.put(KEY_DESK, txt_desk.getText().toString().trim());
                params.put(KEY_TGLGATH, txt_tgl.getText().toString().trim());
                params.put(KEY_TEMPAT, txt_tempat.getText().toString().trim());
                params.put(KEY_HTM, txt_htm.getText().toString().trim());
                params.put(KEY_IDUSER, id);


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
