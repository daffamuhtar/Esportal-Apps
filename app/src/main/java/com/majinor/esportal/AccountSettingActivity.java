package com.majinor.esportal;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

import static com.majinor.esportal.ProfileFragment.TAG_ID;
import static com.majinor.esportal.ProfileFragment.TAG_USERNAME;
import static com.majinor.esportal.ProfileFragment.TAG_EMAIL;
import static com.majinor.esportal.ProfileFragment.TAG_NAMA;
import static com.majinor.esportal.ProfileFragment.TAG_NOHP;

public class AccountSettingActivity extends AppCompatActivity implements View.OnClickListener{

    EditText etNama,etId,etpasscek,etNoHp,etPassword;
    public static final String TAG_ID = "id";
    public static final String TAG_PASS = "password";
    public static final String TAG_USERNAME = "username";
    public static final String TAG_NOHP = "nohp";

    private String url = Server.URL + "editprofile.php";

    Button simpan;
    SharedPreferences sharedpreferences;
    String id,pswd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_setting);

        getSupportActionBar().setTitle("Edit Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        String nama = intent.getStringExtra(TAG_NAMA);
        String username = intent.getStringExtra(TAG_USERNAME);
        String nohp = intent.getStringExtra(TAG_NOHP);
        String email = intent.getStringExtra(TAG_EMAIL);

        sharedpreferences = getSharedPreferences(LoginActivity.my_shared_preferences, Context.MODE_PRIVATE);

        id = sharedpreferences.getString(TAG_ID, null);

        etpasscek=findViewById(R.id.et_as_passcek);
        etNama = findViewById(R.id.et_as_nickname);
        etNoHp = findViewById(R.id.et_as_nohp);
        etId = findViewById(R.id.et_as_id);
        etPassword = findViewById(R.id.et_as_pass);
        simpan = (Button) findViewById(R.id.btn_as_simpan);

        //settextpanggil
        etNama.setText(nama);
        etId.setText(id);
        etNoHp.setText(nohp);

        simpan.setOnClickListener(this);


    }



    private void reset () {

    }
    private void AddEmployee () {
        sharedpreferences = getSharedPreferences(LoginActivity.my_shared_preferences, Context.MODE_PRIVATE);

        pswd = etPassword.getText().toString().trim();

        final String ids = etId.getText().toString().trim();

        final String nama = etNama.getText().toString().trim();
        final String nohp = etNoHp.getText().toString().trim();

        class AddRegis extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(
                        AccountSettingActivity.this,
                        "Menambahkan...",
                        "Tunggu Sebentar...",
                        false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                reset();
                Toast.makeText(AccountSettingActivity.this, s, Toast.LENGTH_LONG).show();

            }

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String, String> params = new HashMap<>();

                params.put("id", ids);
                params.put("nama", nama);
                params.put("password",pswd);
                params.put("nohp", nohp);

                RequestHandlers requestHandler = new RequestHandlers();
                return requestHandler.sendPostRequest(url, params);
            }
        }

        new AddRegis().execute();

    }

    @Override
    public void onClick (View view){

        if (view == simpan) {
            AddEmployee();
            }

    }

    @Override
    public void onPointerCaptureChanged ( boolean hasCapture){

    }
}

