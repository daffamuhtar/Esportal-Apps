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
import static com.majinor.esportal.ProfileFragment.TAG_EMAIL;
import static com.majinor.esportal.ProfileFragment.TAG_NAMA;

public class ChangePasswordActivity extends AppCompatActivity{

    EditText etPassword,etNewPass,etConfNewPass;
    public static final String TAG_ID = "id";
    public static final String TAG_PASS = "password";
    public static final String TAG_USERNAME = "username";
    public static final String TAG_NOHP = "nohp";

    private String url = Server.URL + "changepass.php";

    Button simpane;
    SharedPreferences sharedpreferences;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        getSupportActionBar().setTitle("Change Password");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        etPassword = findViewById(R.id.et_as_pass);
        etNewPass = findViewById(R.id.et_as_newpass);
        etConfNewPass = findViewById(R.id.et_as_newpassconf);
        simpane = (Button) findViewById(R.id.btn_as_changepw);

        simpane.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String psnow = etPassword.getText().toString();
                String psnew = etNewPass.getText().toString();
                String pscnew = etConfNewPass.getText().toString();

                if(psnow.matches("") ) {
                    etPassword.setError("Isi Password");

                }else if(psnow.length()<6) {
                    etPassword.setError("Minimal 6 karakter");

                }else if(psnew.matches ("")) {
                    etNewPass.setError("isi Password Baru");

                }else if(psnow.length()<6) {
                    etNewPass.setError("Minimal 6 karakter");

                }else if(pscnew.matches ("")){
                    etConfNewPass.setError("Isi Confirm Password");

                }else if(pscnew.length()<6) {
                    etConfNewPass.setError("Minimal 6 karakter");

                }else if(psnew.equals(psnow)){
                    etNewPass.setError("Password harus Berbeda");

                }else if(!pscnew.equals(psnew)){
                    etConfNewPass.setError("Confirm Password Harus sama");
                }else {
                    AddEmployee();
                }
            }
        });


    }



    private void reset () {

    }
    private void AddEmployee () {
        sharedpreferences = getSharedPreferences(LoginActivity.my_shared_preferences, Context.MODE_PRIVATE);

        id = sharedpreferences.getString(TAG_ID, null);

        final String pswd = etPassword.getText().toString().trim();

        final String newpswd = etNewPass.getText().toString().trim();
        final String confnewpswd = etConfNewPass.getText().toString().trim();

        class AddRegis extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(
                        ChangePasswordActivity.this,
                        "Menambahkan...",
                        "Tunggu Sebentar...",
                        false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                reset();
                Toast.makeText(ChangePasswordActivity.this, s, Toast.LENGTH_LONG).show();

            }

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String, String> params = new HashMap<>();

                params.put("id", id);
                params.put("password",pswd);
                params.put("newpass",newpswd);
                params.put("confnewpass",confnewpswd);

                RequestHandlers requestHandler = new RequestHandlers();
                return requestHandler.sendPostRequest(url, params);
            }
        }

        new AddRegis().execute();

    }
//
//    @Override
//    public void onClick (View view){
//
//        if (view == simpan) {
//            AddEmployee();
//        }
//
//    }

    @Override
    public void onPointerCaptureChanged ( boolean hasCapture){

    }
}

