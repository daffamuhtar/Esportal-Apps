package com.majinor.esportal;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.majinor.esportal.AccountSettingActivity;
import com.majinor.esportal.ChangePasswordActivity;
import com.majinor.esportal.LoginActivity;
import com.majinor.esportal.R;

import org.json.JSONException;
import org.json.JSONObject;

import static com.majinor.esportal.LoginActivity.TAG_PASS;
import static com.majinor.esportal.Server.URL;

public class ProfileFragment extends Fragment {

    private Button btn_cek;
    Intent intent, accset;
    TextView tvUsername,tvEmail,tvNohp,tvNama,tvPoint;
    String id,usernam,nama,email,nohp,password;
    SharedPreferences sharedpreferences;

    public static String KEY_USERNAME = "message";
    public static final String TAG_ID = "id";
    public static final String TAG_PASS = "password";
    public static final String TAG_USERNAME = "username";
    public static final String TAG_EMAIL = "email";
    public static final String TAG_NAMA = "name";
    public static final String TAG_NOHP = "nohp";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

           View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        tvUsername = (TextView) rootView.findViewById(R.id.tv_profile_username);
        tvEmail = (TextView) rootView.findViewById(R.id.tv_profile_email);
        tvNohp = (TextView) rootView.findViewById(R.id.tv_profile_nohp);
        tvNama = (TextView) rootView.findViewById(R.id.tv_profile_nick);
        tvPoint = (TextView) rootView.findViewById(R.id.tv_profile_point);

        getData();

        //getString Sharedpreferences
        SharedPreferences sharedpreferences = this.getActivity().getSharedPreferences(LoginActivity.my_shared_preferences, Context.MODE_PRIVATE);
        //get to uset/edit
        final SharedPreferences sharedpreferencesOut = this.getActivity().getSharedPreferences(LoginActivity.my_shared_preferences, Context.MODE_PRIVATE);
        id= sharedpreferences.getString(TAG_ID,"null");
        password= sharedpreferences.getString(TAG_PASS,"null");
//        usernam= sharedpreferences.getString(TAG_USERNAME,"null");
//        nama= sharedpreferences.getString(TAG_NAMA,"null");
//        email= sharedpreferences.getString(TAG_EMAIL,"null");
//        nohp= sharedpreferences.getString(TAG_NOHP,"null");

//        tvUsername.setText(usernam);
//        tvEmail.setText(email);
//        tvNohp.setText(nohp);
//        tvNama.setText(nama);

//        usernam= sharedpreferences.getString(TAG_USERNAME,"null");

        intent = new Intent(getActivity(), LoginActivity.class);
        final Button button = (Button) rootView.findViewById(R.id.btn_cek_string);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Logout Aplikasi?")
                        .setCancelable(true)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                SharedPreferences.Editor editor = sharedpreferencesOut.edit();
                                editor.putBoolean(LoginActivity.session_status, false);
                                editor.putString(TAG_ID, null);
                                editor.putString(TAG_USERNAME, null);
                                editor.putString(TAG_NAMA, null);
                                editor.putString(TAG_EMAIL, null);
                                editor.putString(TAG_NOHP, null);
                                editor.commit();
                                getActivity().finish();
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

        final Button setting = (Button) rootView.findViewById(R.id.btn_setting);
        setting.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                PopupMenu popupMenu = new PopupMenu(getActivity().getApplicationContext(), v);
                popupMenu.inflate(R.menu.option_menu_profil);
                popupMenu.show();

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.account:
                                Intent intdn = new Intent(getActivity(), AccountSettingActivity.class); // Your nxt activity name instead of List_Activity
                                intdn.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intdn.putExtra(TAG_USERNAME,tvUsername.getText());
                                intdn.putExtra(TAG_EMAIL,tvEmail.getText());
                                intdn.putExtra(TAG_ID,id);
                                intdn.putExtra(TAG_PASS,password);
                                intdn.putExtra(TAG_NAMA,tvNama.getText());
                                intdn.putExtra(TAG_NOHP,tvNohp.getText());
                                getActivity().startActivity(intdn);
                                break;

                            case R.id.change_pw:
                                Intent intdn2 = new Intent(getActivity(), ChangePasswordActivity.class); // Your nxt activity name instead of List_Activity
                                intdn2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                                intdn2.putExtra(TAG_ID,id);
                                intdn2.putExtra(TAG_PASS,password);
                                getActivity().startActivity(intdn2);
                                break;


                            default:
                                break;

                        }
                        return true;
                    }
                });
                popupMenu.show();

            }
        });

        return rootView;


    }


    void getData(){
// Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getActivity());

        SharedPreferences sharedpreferences = this.getActivity().getSharedPreferences(LoginActivity.my_shared_preferences, Context.MODE_PRIVATE);
        //get to uset/edit
        final SharedPreferences sharedpreferencesOut = this.getActivity().getSharedPreferences(LoginActivity.my_shared_preferences, Context.MODE_PRIVATE);
        id= sharedpreferences.getString(TAG_ID,"null");

        String url = URL+"showprofile.php?id="+id;

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

//men set data ke dalam tampilan
                            tvNama.setText(jsonPost.getString("name"));
                            tvEmail.setText(jsonPost.getString("email"));
                            tvUsername.setText(jsonPost.getString("username"));
                            tvNohp.setText(jsonPost.getString("nohp"));
                            tvPoint.setText(jsonPost.getString("userPoint"));

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



}