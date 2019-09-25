package com.majinor.esportal;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;
import com.majinor.esportal.app.AppController;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.majinor.esportal.LoginActivity.TAG_EMAIL;
import static com.majinor.esportal.LoginActivity.TAG_ID;
import static com.majinor.esportal.LoginActivity.TAG_NAMA;
import static com.majinor.esportal.LoginActivity.TAG_NOHP;
import static com.majinor.esportal.LoginActivity.TAG_PASS;
import static com.majinor.esportal.LoginActivity.TAG_POINT;
import static com.majinor.esportal.LoginActivity.TAG_USERNAME;
import static com.majinor.esportal.LoginActivity.session_status;
import static com.majinor.esportal.Server.URL;
import static com.majinor.esportal.Server.URL_IMAGETOUR;
import static com.majinor.esportal.TournamentActivity.EXTRA_IDTOUR;

public class RegisterActivity extends AppCompatActivity  {

    ProgressDialog loadings;
    Button btn_register, btn_login;
    EditText txt_username, txt_password, txt_confirm_password,txt_email,txt_nohp,txt_nama;
    Intent intent;
    String nohpotp,username;
    int id;
    SharedPreferences sharedpreferences;


    int success;
    ConnectivityManager conMgr;

    private String url = Server.URL + "register.php";
    private String CONFIRM_URL = Server.URL + "verifnohp.php";

    private static final String TAG = RegisterActivity.class.getSimpleName();
    private static final int REQUEST_CODE = 1000;

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    String tag_json_obj = "json_obj_req";

    String emailPattern= "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        {
            if (conMgr.getActiveNetworkInfo() != null
                    && conMgr.getActiveNetworkInfo().isAvailable()
                    && conMgr.getActiveNetworkInfo().isConnected()) {
            } else {
                Toast.makeText(getApplicationContext(), "No Internet Connection",
                        Toast.LENGTH_LONG).show();
            }
        }

        btn_login = (Button) findViewById(R.id.btn_login);
        btn_register = (Button) findViewById(R.id.btn_register);
        txt_username = (EditText) findViewById(R.id.txt_username);
        txt_password = (EditText) findViewById(R.id.txt_password);
        txt_confirm_password = (EditText) findViewById(R.id.txt_confirm_password);
        txt_email = (EditText) findViewById(R.id.txt_email);
        txt_nama = (EditText) findViewById(R.id.txt_nama);


        btn_login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                intent = new Intent(RegisterActivity.this, LoginActivity.class);
                finish();
                startActivity(intent);
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String username = txt_username.getText().toString();
                String password = txt_password.getText().toString();
                String confirm_password = txt_confirm_password.getText().toString();
                String email = txt_email.getText().toString();
                String nama = txt_nama.getText().toString();

                if(username.matches("")) {
                    txt_username.setError("Username tidak boleh kosong");
                }else if(username.length()<8) {
                    txt_username.setError("Username minimal 8 karakter");
                }else if(password.matches("")) {
                    txt_password.setError("Username tidak boleh kosong");
                }else if(password.length()<6) {
                    txt_password.setError("password minimal 6 karakter");
                }else if(confirm_password.matches("")) {
                    txt_confirm_password.setError("Confirm password tidak boleh kosong");
                }else if(!confirm_password.equals(password)) {
                    txt_confirm_password.setError("Confirm password tidak sama");
                }else if(email.matches("")) {
                    txt_email.setError("Email tidak boleh kosong");
                }else if(!email.matches(emailPattern)) {
                    txt_email.setError("Masukkan email dengan benar");
                }else if(nama.matches("")) {
                    txt_nama.setError("Nickname tidak boleh kosong");
                }else if(nama.length()<4) {
                    txt_nama.setError("Nickname minimal 4 karakter");
                }else{
                   AddEmployee();
                }
            }
        });

    }

    private void reset(){
        txt_username.setText("");
        txt_password.setText("");
        txt_confirm_password.setText("");
        txt_email.setText("");
        txt_nama.setText("");

    }
    private void AddEmployee () {

        final String username = txt_username.getText().toString().trim();
        final String password = txt_password.getText().toString().trim();
        final String confirm_password = txt_confirm_password.getText().toString().trim();
        final String email = txt_email.getText().toString().trim();
        final String nama = txt_nama.getText().toString().trim();

        class AddRegis extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(
                        RegisterActivity.this,
                        "Register...",
                        "Please Wait...",
                        false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();

                startLoginKonfirmasi(LoginType.PHONE);
                Toast.makeText(RegisterActivity.this, s, Toast.LENGTH_LONG).show();

            }

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String, String> params = new HashMap<>();

                params.put("username", username);
                params.put("password", password);
                params.put("confirm_password", confirm_password);
                params.put("email", email);
                params.put("nama", nama);
                RequestHandlers requestHandler = new RequestHandlers();
                return requestHandler.sendPostRequest(url, params);
            }
        }

        new AddRegis().execute();

    }

    private void startLoginKonfirmasi(LoginType loginType) {
        Intent intent = new Intent(this, AccountKitActivity.class);
        AccountKitConfiguration.AccountKitConfigurationBuilder builder =
                new AccountKitConfiguration.AccountKitConfigurationBuilder(loginType,
                        AccountKitActivity.ResponseType.TOKEN);
//        builder.setInitialPhoneNumber(new PhoneNumber("+62","85600336781","US"));
        intent.putExtra(AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION,builder.build());
        startActivityForResult(intent,REQUEST_CODE);
    }


    @Override
    public void onBackPressed() {
        intent = new Intent(RegisterActivity.this, LoginActivity.class);
        finish();
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE)
        {
            AccountKitLoginResult result = data.getParcelableExtra(AccountKitLoginResult.RESULT_KEY);

            if (result.getError() != null)
            {
                Toast.makeText(this, ""+result.getError().getErrorType().getMessage(), Toast.LENGTH_SHORT).show();
            }
            else  if (result.wasCancelled())
            {
                Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show();
            }
            else
            {
                if (result.getAccessToken() != null)
                {
                    loadings = ProgressDialog.show(this, "Uploading...", "Please wait...", false, false);

                    AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
                        @Override
                        public void onSuccess(final Account account) {
                            nohpotp = account.getPhoneNumber().toString().trim();
                            username = txt_username.getText().toString().trim();
                            Log.d("catatanOTP",nohpotp);
                            simpanNohp();
                        }

                        @Override
                        public void onError(AccountKitError accountKitError) {

                            Log.d("ERROR",accountKitError.getErrorType().getMessage());
                        }
                    });
                }
            }
        }

    }

    void getData(){
// Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        final Intent intent = getIntent();
        int idt = intent.getIntExtra(EXTRA_IDTOUR,0);

        String url = URL+"getinfouser.php?username="+username;

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
                            id = jsonPost.getInt("id");
                            String username = jsonPost.getString("username");
                            String email = jsonPost.getString("email");
                            String point = jsonPost.getString("userPoint");
                            String names = jsonPost.getString("name");
                            String password = jsonPost.getString("password");

                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putBoolean(session_status, true);
                            editor.putString(TAG_ID, String.valueOf(id));
                            editor.putString(TAG_USERNAME, username);
                            editor.putString(TAG_NAMA, names);
                            editor.putString(TAG_EMAIL, email);
                            editor.putString(TAG_NOHP, nohpotp);
                            editor.putString(TAG_POINT, point);
                            editor.putString(TAG_PASS, password);
                            editor.commit();

                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                            intent.putExtra(TAG_ID, id);
                            intent.putExtra(TAG_USERNAME, username);
                            finish();
                            startActivity(intent);

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

    private void simpanNohp() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, CONFIRM_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e(TAG, "Response: " + response.toString());

                        try {
                            JSONObject jObj = new JSONObject(response);
                            success = jObj.getInt(TAG_SUCCESS);

                            if (success == 1) {
                                Log.e("v Add", jObj.toString());

                                getData();
                                Toast.makeText(RegisterActivity.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();


                            } else {
                                Toast.makeText(RegisterActivity.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        //menghilangkan progress dialog
                        loadings.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //menghilangkan progress dialog
                        loadings.dismiss();

                        //menampilkan toast
                        Toast.makeText(RegisterActivity.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                        Log.e(TAG, error.getMessage().toString());

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                //membuat parameters
                Map<String, String> params = new HashMap<String, String>();

                //menambah parameter yang di kirim ke web servis
                params.put("nohp", nohpotp);
                params.put("username", username);

                //kembali ke parameters
                Log.e(TAG, "" + params);
                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(stringRequest, tag_json_obj);

    }

}
