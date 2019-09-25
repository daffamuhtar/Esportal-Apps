package com.majinor.esportal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import static com.majinor.esportal.HomeFragment.KEY_USERNAME;
import static com.majinor.esportal.TournamentActivity.EXTRA_DESK;

public class GatheringDetailTentangActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tournament_detail_tentang);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Tentang Gathering");

        Intent intent = getIntent();
        String tentang = intent.getStringExtra(EXTRA_DESK);


        TextView Tentang =  findViewById(R.id.tv_tdt_tentang);
        Tentang.setText(tentang);
    }
}
