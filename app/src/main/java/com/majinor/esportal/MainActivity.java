package com.majinor.esportal;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static com.majinor.esportal.LoginActivity.TAG_ID;
import static com.majinor.esportal.LoginActivity.TAG_USERNAME;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedpreferences;
    String id, username;
    FragmentTransaction fragmentManager;
    boolean doubleBackToExitPressedOnce = false;

    public final static String EXTRA_USERNAME = "username";
    public final static String EXTRA_ID = "id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        printKeyHash();
        //Menampilkan halaman Fragment yang pertama kali muncul
        getFragmentPage(new HomeFragment());

        /*Inisialisasi BottomNavigationView beserta listenernya untuk
         *menangkap setiap kejadian saat salah satu menu item diklik
         */


        BottomNavigationView bottomNavigation = findViewById(R.id.bottomNavigationView);
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment fragment = null;

                //Menantukan halaman Fragment yang akan tampil
                switch (item.getItemId()) {
                    case R.id.home:
                        fragment = new HomeFragment();
                        break;

                    case R.id.notification:
                        fragment = new NotificationFragment();
                        break;

                    case R.id.leaderboard:
                        fragment = new LeaderboardFragment();
                        break;


                    case R.id.event:
                        fragment = new MyEventFragment();
                        break;

                    case R.id.profile:
                        fragment = new ProfileFragment();
                        break;
                }

                return getFragmentPage(fragment);

            }
        });
        sharedpreferences = getSharedPreferences(LoginActivity.my_shared_preferences, Context.MODE_PRIVATE);


        id = getIntent().getStringExtra(TAG_ID);
        username = getIntent().getStringExtra(TAG_USERNAME);

//        Intent intent = getIntent();
//        String username = intent.getStringExtra(TAG_USERNAME);
//        Bundle data = new Bundle();
//        data.putString( ProfileFragment.EXTRA_USERNAME, username);
//        ProfileFragment fragment2 =  new ProfileFragment();
//        fragment2.setArguments(data);
//        getSupportFragmentManager()
//                .beginTransaction()
//                .replace(R.id.containerpage, fragment2)
//                .commit();

//        String message = editText.getText().toString();
//        Bundle data = new Bundle();
//        data.putString(Fragtry.KEY_ACTIVITY, message);
//        Fragtry fragtry = new Fragtry();
//        fragtry.setArguments(data);
    }

    //Menampilkan halaman Fragment
    private boolean getFragmentPage(Fragment fragment) {

        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.page_container, fragment, username)
                    .commit();
            return true;
        }
        return false;
    }

    //tap again to exit
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Tap again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
    private void printKeyHash() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo("com.majinor.esportal", PackageManager.GET_SIGNATURES);
            for (Signature signature:info.signatures){
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KEYHASH", Base64.encodeToString(md.digest(),Base64.DEFAULT));
            }

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}